#!/usr/bin/env python3
"""Guard the integrated-server definition-cache authority boundary.

DefinitionUtil uses static maps, so an integrated client and server share the same
objects. Client editor code may construct and send modified JSON, but only the
server reload path may mutate the shared authoritative cache. Remote clients may
maintain their own mirror through the explicit putClientMirror* methods.
"""

from __future__ import annotations

import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
JAVA_ROOT = ROOT / "common" / "src" / "main" / "java"
DEFINITION_UTIL = JAVA_ROOT / "org" / "infernalstudios" / "questlog" / "core" / "DefinitionUtil.java"
CLIENT_HANDLER = JAVA_ROOT / "org" / "infernalstudios" / "questlog" / "network" / "ClientPacketHandler.java"
CHAPTER_EDITOR = JAVA_ROOT / "org" / "infernalstudios" / "questlog" / "client" / "gui" / "screen" / "ChapterEditorScreen.java"


def fail(message: str, errors: list[str]) -> None:
    errors.append(message)


def main() -> int:
    errors: list[str] = []
    definition_source = DEFINITION_UTIL.read_text(encoding="utf-8")
    client_source = CLIENT_HANDLER.read_text(encoding="utf-8")

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

    print("Definition-cache authority boundary is intact.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
