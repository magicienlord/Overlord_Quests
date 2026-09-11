#!/usr/bin/env python3
"""Guard definition-cache authority and editor-owned definition integrity.

DefinitionUtil uses static maps, so an integrated client and server share the same
objects. Client editor code may construct and send modified JSON, but only the
server reload path may mutate the shared authoritative cache. Remote clients may
maintain their own mirror through the explicit putClientMirror* methods.

Definitions loaded from the bundled JAR are immutable production content. Config
files may override them, but an editor action labelled Delete must not ambiguously
remove an override and reveal the bundled base.
"""

from __future__ import annotations

import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
JAVA_ROOT = ROOT / "common" / "src" / "main" / "java"
QUESTLOG_JAVA = JAVA_ROOT / "org" / "infernalstudios" / "questlog"
DEFINITION_UTIL = QUESTLOG_JAVA / "core" / "DefinitionUtil.java"
CLIENT_HANDLER = QUESTLOG_JAVA / "network" / "ClientPacketHandler.java"
CHAPTER_EDITOR = QUESTLOG_JAVA / "client" / "gui" / "screen" / "ChapterEditorScreen.java"
QUEST_EDITOR = QUESTLOG_JAVA / "client" / "gui" / "screen" / "QuestEditorScreen.java"
QUEST_REMOVE = QUESTLOG_JAVA / "network" / "packet" / "QuestEditRemovePacket.java"
CHAPTER_REMOVE = QUESTLOG_JAVA / "network" / "packet" / "ChapterEditRemovePacket.java"
LANG_EN_US = ROOT / "common" / "src" / "main" / "resources" / "assets" / "questlog" / "lang" / "en_us.json"


def fail(message: str, errors: list[str]) -> None:
    errors.append(message)


def main() -> int:
    errors: list[str] = []
    definition_source = DEFINITION_UTIL.read_text(encoding="utf-8")
    client_source = CLIENT_HANDLER.read_text(encoding="utf-8")
    chapter_editor_source = CHAPTER_EDITOR.read_text(encoding="utf-8")
    quest_editor_source = QUEST_EDITOR.read_text(encoding="utf-8")
    quest_remove_source = QUEST_REMOVE.read_text(encoding="utf-8")
    chapter_remove_source = CHAPTER_REMOVE.read_text(encoding="utf-8")
    language_source = LANG_EN_US.read_text(encoding="utf-8")

    # Authoritative cache objects must never escape to callers as mutable JSON.
    quest_getter = re.search(
        r"getCachedQuest\(ResourceLocation path\).*?return\s+definition\.deepCopy\(\);",
        definition_source,
        re.S,
    )
    if quest_getter is None:
        fail("DefinitionUtil.getCachedQuest must return a deep copy", errors)

    chapter_getter = re.search(
        r"getCachedChapter\(ResourceLocation path\).*?return\s+definition\s*==\s*null\s*\?\s*null\s*:\s*definition\.deepCopy\(\);",
        definition_source,
        re.S,
    )
    if chapter_getter is None:
        fail("DefinitionUtil.getCachedChapter must return a deep copy", errors)

    # The remote-client mirror path is the only supported client cache writer.
    for method in ("putClientMirrorQuest", "putClientMirrorChapter"):
        if f"DefinitionUtil.{method}(" not in client_source:
            fail(f"ClientPacketHandler must use DefinitionUtil.{method}", errors)

    # Bundled identities must remain tracked separately even when a config override
    # replaces the active JSON value in the ordinary cache.
    for token in (
        "BUNDLED_QUEST_IDS",
        "BUNDLED_CHAPTER_IDS",
        "isBundledQuest(ResourceLocation id)",
        "isBundledChapter(ResourceLocation id)",
    ):
        if token not in definition_source:
            fail(f"DefinitionUtil is missing bundled-authority marker: {token}", errors)

    if "DefinitionUtil.isBundledQuest(packet.id)" not in quest_remove_source:
        fail("QuestEditRemovePacket must reject editor deletion of bundled quests", errors)
    if "DefinitionUtil.isBundledChapter(packet.id)" not in chapter_remove_source:
        fail("ChapterEditRemovePacket must reject editor deletion of bundled chapters", errors)

    # Legacy optimistic cache writers may remain as no-op compatibility methods in
    # DefinitionUtil, but no external call site may depend on them anymore.
    legacy_calls: dict[str, list[Path]] = {"putCachedQuest": [], "putCachedChapter": []}
    mirror_calls: dict[str, list[Path]] = {"putClientMirrorQuest": [], "putClientMirrorChapter": []}
    for path in JAVA_ROOT.rglob("*.java"):
        if path == DEFINITION_UTIL:
            continue
        text = path.read_text(encoding="utf-8")
        for method in legacy_calls:
            if f"DefinitionUtil.{method}(" in text:
                legacy_calls[method].append(path)
        for method in mirror_calls:
            if f"DefinitionUtil.{method}(" in text:
                mirror_calls[method].append(path)

    for method, paths in legacy_calls.items():
        if paths:
            fail(
                f"{method} is a legacy no-op sink but is still called from: "
                + ", ".join(str(path.relative_to(ROOT)) for path in paths),
                errors,
            )

    for method, paths in mirror_calls.items():
        unexpected = [path for path in paths if path != CLIENT_HANDLER]
        if unexpected:
            fail(
                f"{method} is reserved for authoritative remote sync but is called from: "
                + ", ".join(str(path.relative_to(ROOT)) for path in unexpected),
                errors,
            )

    # Chapter membership edits must be constructed from an isolated JSON snapshot,
    # sent to the logical server, and refreshed only from the resulting server sync.
    for forbidden in (
        "DefinitionUtil.putCachedQuest(",
        "DefinitionUtil.putCachedChapter(",
        "String futureId =",
    ):
        if forbidden in chapter_editor_source:
            fail(f"ChapterEditorScreen still contains forbidden optimistic state path: {forbidden}", errors)

    for required, label in (
        ("JsonObject updatedQuest = qJson.deepCopy();", "isolated quest membership edit"),
        ("new QuestEditSavePacket(qKey, updatedQuest.toString())", "server-authoritative quest membership save"),
        ("if (this.chapterToEdit == null) {\n                actionButton.active = false;", "unsaved chapter membership guard"),
        ("!Questlog.MODID.equals(rl.getNamespace())", "client-side chapter namespace guard"),
    ):
        if required not in chapter_editor_source:
            fail(f"ChapterEditorScreen is missing {label}", errors)

    # Runtime supports failure conditions as ordinary Objective trees. The editor
    # must therefore round-trip and author them rather than silently preserving an
    # opaque original JSON field with no editable state.
    if 'loadList(definition.getAsJsonArray("failures"), null)' in quest_editor_source:
        fail("QuestEditorScreen still discards failure conditions from editable state", errors)

    for required, label in (
        ("private final List<JsonObject> tempFailures = new ArrayList<>();", "editable failure list"),
        ('loadList(definition.getAsJsonArray("failures"), this.tempFailures);', "failure definition loading"),
        ("case FAILURES -> Component.translatable(\"questlog.editor.failures\")", "failure tab label"),
        ("this.activeTab == ActiveTab.FAILURES", "failure tab objective editing path"),
        ("return this.tempFailures;", "failure active-list routing"),
        ('json.add("failures", failureArr);', "failure definition serialization"),
        ("FAILURES,\n        REWARDS,", "failure tab enum entry"),
        ("!Questlog.MODID.equals(rl.getNamespace())", "client-side quest namespace guard"),
        ("questlog.editor.error.quest_namespace", "quest namespace validation feedback"),
    ):
        if required not in quest_editor_source:
            fail(f"QuestEditorScreen is missing {label}", errors)

    # Editor feedback must resolve to actual localized text rather than leaking a
    # translation key into the authoring UI.
    for key in (
        '"questlog.editor.failures"',
        '"questlog.editor.tooltip.chapter_membership_requires_save"',
        '"questlog.editor.error.chapter_namespace"',
        '"questlog.editor.error.quest_namespace"',
    ):
        if key not in language_source:
            fail(f"en_us.json is missing editor localization {key}", errors)

    if errors:
        print("Definition/editor authority validation failed:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1

    print("Definition-cache authority and editor definition contracts are intact.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
