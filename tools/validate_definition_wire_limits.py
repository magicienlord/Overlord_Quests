#!/usr/bin/env python3
"""Validate repository-controlled quest/chapter definitions against the 1.20.1 UTF wire cap."""

from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Iterable

ROOT = Path(__file__).resolve().parents[1]
EXAMPLE_QUESTS = ROOT / "examples" / "questlog" / "quests"
BUNDLED_ROOT = ROOT / "common" / "src" / "main" / "resources" / "assets" / "questlog" / "overlord" / "definitions"
BUNDLED_INDEX = BUNDLED_ROOT / "index.json"
MAX_SYNCED_JSON_CHARS = 32_767


def java_utf16_length(text: str) -> int:
    """Return Java String.length() semantics, including supplementary characters."""
    return len(text.encode("utf-16-le", errors="surrogatepass")) // 2


def compact_json(data: object) -> str:
    return json.dumps(data, ensure_ascii=False, separators=(",", ":"))


def iter_controlled_definitions() -> Iterable[Path]:
    if EXAMPLE_QUESTS.is_dir():
        yield from sorted(EXAMPLE_QUESTS.glob("overlord_*.json"))

    if not BUNDLED_INDEX.is_file():
        return

    index = json.loads(BUNDLED_INDEX.read_text(encoding="utf-8"))
    if not isinstance(index, dict):
        raise ValueError("bundled definition index root must be an object")

    for category in ("quests", "chapters"):
        entries = index.get(category, [])
        if not isinstance(entries, list):
            raise ValueError(f"bundled definition index '{category}' must be a list")
        category_root = (BUNDLED_ROOT / category).resolve()
        for entry in entries:
            if not isinstance(entry, str):
                raise ValueError(f"bundled definition index '{category}' contains a non-string entry")
            path = (category_root / entry).resolve()
            if category_root not in path.parents:
                raise ValueError(f"bundled definition path escapes {category}: {entry}")
            if path.is_file():
                yield path


def validate_path(path: Path) -> str | None:
    try:
        data = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        return f"{path.relative_to(ROOT)}: cannot parse JSON: {exc}"

    if not isinstance(data, dict):
        return f"{path.relative_to(ROOT)}: definition root must be an object"

    chars = java_utf16_length(compact_json(data))
    if chars > MAX_SYNCED_JSON_CHARS:
        return (
            f"{path.relative_to(ROOT)}: compact definition is {chars} Java UTF-16 characters; "
            f"wire maximum is {MAX_SYNCED_JSON_CHARS}"
        )
    return None


def main() -> int:
    try:
        paths = list(dict.fromkeys(path.resolve() for path in iter_controlled_definitions()))
    except Exception as exc:
        print(f"Definition wire-size validation failed: {exc}", file=sys.stderr)
        return 1

    errors = [error for path in paths if (error := validate_path(path)) is not None]
    if errors:
        print("Definition wire-size validation failed:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1

    print(
        f"Definition wire-size contract valid across {len(paths)} repository-controlled definition(s); "
        f"maximum {MAX_SYNCED_JSON_CHARS} Java UTF-16 characters each."
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
