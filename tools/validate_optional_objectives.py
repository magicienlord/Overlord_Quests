#!/usr/bin/env python3
"""Validate OVERLORD QUESTS optional-objective semantics.

Optional objectives are a concrete REIGN authoring requirement. They remain
ordinary Questlog objectives for progress, persistence, and display, but a
strict top-level `optional: true` flag removes them from quest completion gates.
Nested optional flags are rejected because the enclosing logic objective owns
whether its whole branch is required or optional.
"""

from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any, Iterable

ROOT = Path(__file__).resolve().parents[1]
EXAMPLE_ROOT = ROOT / "examples" / "questlog" / "quests"
BUNDLED_ROOT = ROOT / "common" / "src" / "main" / "resources" / "assets" / "questlog" / "overlord" / "definitions"
BUNDLED_INDEX = BUNDLED_ROOT / "index.json"
QUEST_SOURCE = ROOT / "common" / "src" / "main" / "java" / "org" / "infernalstudios" / "questlog" / "core" / "quests" / "Quest.java"
OBJECTIVE_SOURCE = ROOT / "common" / "src" / "main" / "java" / "org" / "infernalstudios" / "questlog" / "core" / "quests" / "objectives" / "Objective.java"
DISPLAY_SOURCE = ROOT / "common" / "src" / "main" / "java" / "org" / "infernalstudios" / "questlog" / "core" / "quests" / "display" / "ObjectiveDisplayData.java"


def iter_quest_files() -> Iterable[Path]:
    if EXAMPLE_ROOT.is_dir():
        yield from sorted(EXAMPLE_ROOT.glob("overlord_*.json"))

    if not BUNDLED_INDEX.is_file():
        return

    index = json.loads(BUNDLED_INDEX.read_text(encoding="utf-8"))
    quests = index.get("quests", []) if isinstance(index, dict) else []
    if not isinstance(quests, list):
        return

    quest_root = (BUNDLED_ROOT / "quests").resolve()
    for entry in quests:
        if not isinstance(entry, str):
            continue
        path = (quest_root / entry).resolve()
        if quest_root in path.parents and path.is_file() and path.suffix == ".json":
            yield path


def normalize_type(value: Any) -> str:
    if not isinstance(value, str):
        return ""
    return value if ":" in value else f"questlog:{value}"


def validate_entry(entry: Any, location: str, errors: list[str], *, allow_optional: bool) -> int:
    if not isinstance(entry, dict):
        return 0

    optional_count = 0
    if "optional" in entry:
        value = entry["optional"]
        if not isinstance(value, bool):
            errors.append(f"{location}.optional must be a boolean")
        elif value:
            if not allow_optional:
                errors.append(
                    f"{location}.optional is only legal on a top-level entry in the objectives list"
                )
            else:
                optional_count += 1

    objective_type = normalize_type(entry.get("type"))
    if objective_type in {"questlog:and", "questlog:or"}:
        children = entry.get("objectives", [])
        if isinstance(children, list):
            for index, child in enumerate(children):
                optional_count += validate_entry(
                    child,
                    f"{location}.objectives[{index}]",
                    errors,
                    allow_optional=False,
                )
    elif objective_type == "questlog:not":
        optional_count += validate_entry(
            entry.get("objective"),
            f"{location}.objective",
            errors,
            allow_optional=False,
        )

    return optional_count


def validate_definition(path: Path, errors: list[str]) -> int:
    try:
        data = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: cannot parse JSON: {exc}")
        return 0

    if not isinstance(data, dict):
        return 0

    optional_count = 0
    relative = path.relative_to(ROOT)
    for key in ("prerequisites", "requirements", "failures"):
        entries = data.get(key, [])
        if isinstance(entries, list):
            for index, entry in enumerate(entries):
                optional_count += validate_entry(
                    entry,
                    f"{relative}:{key}[{index}]",
                    errors,
                    allow_optional=False,
                )

    objectives = data.get("objectives", [])
    if isinstance(objectives, list):
        for index, entry in enumerate(objectives):
            optional_count += validate_entry(
                entry,
                f"{relative}:objectives[{index}]",
                errors,
                allow_optional=True,
            )

    return optional_count


def validate_runtime_contract() -> list[str]:
    errors: list[str] = []
    quest = QUEST_SOURCE.read_text(encoding="utf-8")
    objective = OBJECTIVE_SOURCE.read_text(encoding="utf-8")
    display = DISPLAY_SOURCE.read_text(encoding="utf-8")

    required_tokens = {
        QUEST_SOURCE: {
            'JsonUtils.getOrDefault(objectiveDefinition, "optional", false)': "definition-owned optional flag parsing",
            "objective.markAsOptional();": "optional objective marking",
            "!objective.isOptional() && !objective.isCompleted()": "completion gate exclusion",
        },
        OBJECTIVE_SOURCE: {
            "private boolean isOptionalRoot": "optional root state",
            "private boolean isPartOfOptionalObjective": "nested optional-branch state",
            "public void markAsOptional()": "optional branch propagation",
            "this.getParent().hasSentCompletion": "post-completion progress freeze",
        },
        DISPLAY_SOURCE: {
            "this.objective.isOptional()": "optional display discrimination",
            'Component.literal(" (Optional)")': "player-visible optional label",
        },
    }

    sources = {
        QUEST_SOURCE: quest,
        OBJECTIVE_SOURCE: objective,
        DISPLAY_SOURCE: display,
    }
    for path, tokens in required_tokens.items():
        source = sources[path]
        for token, label in tokens.items():
            if token not in source:
                errors.append(f"{path.relative_to(ROOT)} is missing {label}")

    return errors


def main() -> int:
    errors = validate_runtime_contract()
    files = list(dict.fromkeys(iter_quest_files()))
    optional_count = 0
    for path in files:
        optional_count += validate_definition(path, errors)

    if errors:
        print("Optional objective validation failed:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1

    print(
        f"Optional objective contract valid across {len(files)} repository-controlled quest definition(s); "
        f"{optional_count} authored optional objective(s)."
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
