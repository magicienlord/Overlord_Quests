#!/usr/bin/env python3
"""Validate objective fields that are mandatory at runtime.

This is a narrow companion to validate_overlord_quest_examples.py. It exists so
runtime constructor contracts stay enforced even when an inherited editor does
not expose every specialized field yet. It validates only OVERLORD development
fixtures and definitions explicitly listed by the bundled manifest.
"""

from __future__ import annotations

import json
import re
import sys
from pathlib import Path
from typing import Any, Iterable

ROOT = Path(__file__).resolve().parents[1]
EXAMPLE_ROOT = ROOT / "examples" / "questlog" / "quests"
BUNDLED_ROOT = ROOT / "common" / "src" / "main" / "resources" / "assets" / "questlog" / "overlord" / "definitions"
BUNDLED_INDEX = BUNDLED_ROOT / "index.json"
RESOURCE_ID = re.compile(r"^[a-z0-9_.-]+:[a-z0-9_./-]+$")
EQUIPMENT_SLOTS = {"mainhand", "offhand", "feet", "legs", "chest", "head"}


def iter_quest_files() -> Iterable[Path]:
    if EXAMPLE_ROOT.is_dir():
        yield from sorted(EXAMPLE_ROOT.glob("overlord_*.json"))

    if not BUNDLED_INDEX.is_file():
        return

    try:
        index = json.loads(BUNDLED_INDEX.read_text(encoding="utf-8"))
    except Exception as exc:
        raise RuntimeError(f"cannot parse bundled definition index: {exc}") from exc

    quests = index.get("quests", [])
    if not isinstance(quests, list):
        raise RuntimeError("bundled definition index 'quests' must be a list")

    for entry in quests:
        if not isinstance(entry, str):
            continue
        path = (BUNDLED_ROOT / "quests" / entry).resolve()
        root = (BUNDLED_ROOT / "quests").resolve()
        if root not in path.parents:
            continue
        if path.is_file():
            yield path


def normalize_type(value: Any) -> str:
    if not isinstance(value, str):
        return ""
    return value if ":" in value else f"questlog:{value}"


def validate_objective(entry: Any, location: str, errors: list[str]) -> None:
    if not isinstance(entry, dict):
        return

    objective_type = normalize_type(entry.get("type"))

    if objective_type == "questlog:item_equip":
        slot = entry.get("slot")
        if not isinstance(slot, str) or slot not in EQUIPMENT_SLOTS:
            errors.append(
                f"{location}: item_equip requires slot to be one of "
                + ", ".join(sorted(EQUIPMENT_SLOTS))
            )

    if objective_type == "questlog:entity_approach":
        range_value = entry.get("range")
        if isinstance(range_value, bool) or not isinstance(range_value, int) or range_value < 1:
            errors.append(f"{location}: entity_approach requires integer range >= 1")

    if objective_type == "questlog:visit_position" and "dimension" in entry:
        dimension = entry.get("dimension")
        if not isinstance(dimension, str) or not RESOURCE_ID.fullmatch(dimension):
            errors.append(f"{location}: visit_position dimension must be a namespaced resource id")

    if objective_type in {"questlog:and", "questlog:or"}:
        children = entry.get("objectives", [])
        if isinstance(children, list):
            for index, child in enumerate(children):
                validate_objective(child, f"{location}.objectives[{index}]", errors)
    elif objective_type == "questlog:not":
        validate_objective(entry.get("objective"), f"{location}.objective", errors)


def validate_file(path: Path, errors: list[str]) -> None:
    try:
        data = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: cannot parse JSON: {exc}")
        return

    if not isinstance(data, dict):
        return

    for key in ("prerequisites", "requirements", "objectives", "failures"):
        entries = data.get(key, [])
        if not isinstance(entries, list):
            continue
        for index, entry in enumerate(entries):
            validate_objective(entry, f"{path.relative_to(ROOT)}:{key}[{index}]", errors)


def main() -> int:
    errors: list[str] = []
    seen: set[Path] = set()
    try:
        files = list(iter_quest_files())
    except RuntimeError as exc:
        print(f"ERROR: {exc}", file=sys.stderr)
        return 1

    for path in files:
        resolved = path.resolve()
        if resolved in seen:
            continue
        seen.add(resolved)
        validate_file(path, errors)

    if errors:
        print("Runtime-required objective field validation failed:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1

    print(f"Runtime-required objective fields valid across {len(seen)} OVERLORD quest definition(s).")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
