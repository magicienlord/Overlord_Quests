#!/usr/bin/env python3
"""Validate OVERLORD QUESTS development/example quest definitions.

This validator intentionally scopes itself to files named ``overlord_*.json`` under
``examples/questlog/quests``. Upstream Questlog examples remain upstream fixtures and
are not silently rewritten to satisfy OVERLORD REIGN conventions.

The checks here are structural and repository-local. They do not claim that a quest
is valid story canon, balanced gameplay, or a successful in-game presentation.
"""

from __future__ import annotations

import json
import re
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
QUEST_DIR = ROOT / "examples" / "questlog" / "quests"
RESOURCE_ROOT = ROOT / "common" / "src" / "main" / "resources" / "assets"
RESOURCE_ID = re.compile(r"^[a-z0-9_.-]+:[a-z0-9_./-]+$")


class ValidationError(Exception):
    pass


def fail(path: Path, message: str, errors: list[str]) -> None:
    errors.append(f"{path.relative_to(ROOT)}: {message}")


def require_string(data: dict[str, Any], key: str, path: Path, errors: list[str]) -> None:
    value = data.get(key)
    if not isinstance(value, str) or not value.strip():
        fail(path, f"'{key}' must be a non-empty string", errors)


def require_list(data: dict[str, Any], key: str, path: Path, errors: list[str]) -> None:
    if not isinstance(data.get(key), list):
        fail(path, f"'{key}' must be a list", errors)


def validate_resource_id(value: Any, field: str, path: Path, errors: list[str]) -> None:
    if not isinstance(value, str) or not RESOURCE_ID.fullmatch(value):
        fail(path, f"'{field}' must be a namespaced resource id, got {value!r}", errors)


def validate_overlay(data: dict[str, Any], path: Path, errors: list[str]) -> None:
    overlay = data.get("overlay", data.get("overlay_texture"))
    if overlay is None:
        return

    validate_resource_id(overlay, "overlay", path, errors)
    if not isinstance(overlay, str) or not RESOURCE_ID.fullmatch(overlay):
        return

    namespace, resource_path = overlay.split(":", 1)
    asset = RESOURCE_ROOT / namespace / resource_path
    if not asset.is_file():
        fail(path, f"overlay asset does not exist in resources: {overlay}", errors)

    for dimension in ("overlay_width", "overlay_height"):
        value = data.get(dimension)
        if not isinstance(value, int) or isinstance(value, bool) or value <= 0:
            fail(path, f"'{dimension}' must be a positive integer when an overlay is used", errors)

    for offset in ("overlay_x_offset", "overlay_y_offset"):
        value = data.get(offset)
        if value is not None and (not isinstance(value, int) or isinstance(value, bool)):
            fail(path, f"'{offset}' must be an integer", errors)


def validate_objective_list(data: dict[str, Any], key: str, path: Path, errors: list[str]) -> None:
    values = data.get(key, [])
    if not isinstance(values, list):
        return

    for index, entry in enumerate(values):
        if not isinstance(entry, dict):
            fail(path, f"'{key}[{index}]' must be an object", errors)
            continue
        if "type" not in entry:
            fail(path, f"'{key}[{index}]' is missing objective type", errors)
        else:
            validate_resource_id(entry["type"], f"{key}[{index}].type", path, errors)
        required_amount = entry.get("required_amount")
        if required_amount is not None and (
            not isinstance(required_amount, int)
            or isinstance(required_amount, bool)
            or required_amount < 1
        ):
            fail(path, f"'{key}[{index}].required_amount' must be an integer >= 1", errors)


def validate_dev_boundary(data: dict[str, Any], path: Path, errors: list[str]) -> None:
    # Development fixtures are deliberately named *_dev.json. Keep that status
    # visible in both title and description so a test quest cannot be mistaken for
    # approved OVERLORD REIGN story content if copied into an instance.
    if not path.stem.endswith("_dev"):
        return

    title = data.get("title", "")
    description = data.get("description", "")
    if not isinstance(title, str) or not title.startswith("[DEV]"):
        fail(path, "development quest title must start with '[DEV]'", errors)
    if not isinstance(description, str) or "not an OVERLORD REIGN story quest" not in description:
        fail(path, "development quest description must explicitly state that it is not story canon", errors)


def validate_quest(path: Path, errors: list[str]) -> None:
    try:
        data = json.loads(path.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError) as exc:
        fail(path, f"invalid JSON: {exc}", errors)
        return

    if not isinstance(data, dict):
        fail(path, "quest root must be a JSON object", errors)
        return

    require_string(data, "title", path, errors)
    require_string(data, "description", path, errors)
    require_list(data, "objectives", path, errors)
    require_list(data, "rewards", path, errors)

    chapter = data.get("chapter")
    if chapter is not None and (not isinstance(chapter, str) or not chapter.strip()):
        fail(path, "'chapter' must be a non-empty string when present", errors)

    icon = data.get("icon")
    if icon is not None:
        if not isinstance(icon, dict):
            fail(path, "'icon' must be an object", errors)
        elif "item" in icon:
            validate_resource_id(icon["item"], "icon.item", path, errors)

    validate_objective_list(data, "prerequisites", path, errors)
    validate_objective_list(data, "objectives", path, errors)
    validate_overlay(data, path, errors)
    validate_dev_boundary(data, path, errors)

    if data.get("show_popup_on_unlock") is True and not data.get("prerequisites"):
        fail(
            path,
            "popup-on-unlock test has no prerequisites; Questlog initializes such quests as already triggered",
            errors,
        )


def main() -> int:
    quests = sorted(QUEST_DIR.glob("overlord_*.json"))
    if not quests:
        print("No OVERLORD QUESTS example definitions found.", file=sys.stderr)
        return 1

    errors: list[str] = []
    for quest in quests:
        validate_quest(quest, errors)

    if errors:
        print(f"OVERLORD QUESTS validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print(f"Validated {len(quests)} OVERLORD QUESTS example quest definition(s).")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
