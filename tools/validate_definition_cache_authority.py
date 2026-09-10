#!/usr/bin/env python3
"""Guard the integrated-server definition-cache and bundled-content authority boundary.

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
QUEST_REMOVE = QUESTLOG_JAVA / "network" / "packet" / "QuestEditRemovePacket.java"
CHAPTER_REMOVE = QUESTLOG_JAVA / "network" / "packet" / "ChapterEditRemovePacket.java"


def fail(message: str, errors: list[str]) -> None:
    errors.append(message)


def main() -> int:
    errors: list[str] = []
    definition_source = DEFINITION_UTIL.read_text(encoding="utf-8")
    client_source = CLIENT_HANDLER.read_text(encoding="utf-8")
    quest_remove_source = QUEST_REMOVE.read_text(encoding="utf-8")
    chapter_remove_source = CHAPTER_REMOVE.read_text(encoding="utf-8")

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

    # Legacy optimistic editor writers are retained only as compatibility sinks.
    # No new call site may depend on them for authoritative state.
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
        unexpected = [path for path in paths if path != CHAPTER_EDITOR]
        if unexpected:
            fail(
                f"{method} is a legacy editor sink but is called from: "
                + ", ".join(str(path.relative_to(ROOT)) for path in unexpected),
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

    if errors:
        print("Definition-cache authority validation failed:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1

    print("Definition-cache and bundled-content authority boundaries are intact.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
