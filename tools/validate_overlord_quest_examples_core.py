#!/usr/bin/env python3
"""Validate OVERLORD QUESTS definitions and development fixtures.

The validator keeps inherited Questlog examples outside the OVERLORD REIGN policy
surface. Files named ``overlord_*.json`` under ``examples/questlog/quests`` are
validated as development fixtures. Definitions explicitly listed by the bundled
manifest are validated as distributable mod content.

These checks are structural and repository-local. They do not establish story
canon, gameplay balance, or successful in-game presentation.
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
BUNDLED_ROOT = RESOURCE_ROOT / "questlog" / "overlord" / "definitions"
BUNDLED_INDEX = BUNDLED_ROOT / "index.json"
RESOURCE_ID = re.compile(r"^[a-z0-9_.-]+:[a-z0-9_./-]+$")
REGISTRY_PREDICATE = re.compile(r"^#?[a-z0-9_.-]+:[a-z0-9_./-]+$")

KNOWN_QUESTLOG_OBJECTIVES = {
    "questlog:block_mine",
    "questlog:block_place",
    "questlog:block_interact",
    "questlog:entity_breed",
    "questlog:entity_death",
    "questlog:entity_kill",
    "questlog:entity_approach",
    "questlog:entity_tame",
    "questlog:and",
    "questlog:or",
    "questlog:not",
    "questlog:item_craft",
    "questlog:item_drop",
    "questlog:item_equip",
    "questlog:item_obtain",
    "questlog:item_use",
    "questlog:stat",
    "questlog:trample",
    "questlog:enchant",
    "questlog:effect_added",
    "questlog:visit_biome",
    "questlog:visit_dimension",
    "questlog:visit_position",
    "questlog:visit_structure",
    "questlog:quest_complete",
    "questlog:read",
    "questlog:advancement",
    "questlog:unobtainable",
    "questlog:origin",
}

KNOWN_QUESTLOG_REWARDS = {
    "questlog:item",
    "questlog:command",
    "questlog:experience",
    "questlog:loot_table",
    "questlog:choice",
}

BLOCK_OBJECTIVES = {
    "questlog:block_mine",
    "questlog:block_place",
    "questlog:block_interact",
}
ITEM_OBJECTIVES = {
    "questlog:item_craft",
    "questlog:item_drop",
    "questlog:item_equip",
    "questlog:item_obtain",
    "questlog:item_use",
}
ENTITY_OBJECTIVES = {
    "questlog:entity_breed",
    "questlog:entity_death",
    "questlog:entity_kill",
    "questlog:entity_approach",
    "questlog:entity_tame",
}


def fail(path: Path, message: str, errors: list[str]) -> None:
    try:
        display_path = path.relative_to(ROOT)
    except ValueError:
        display_path = path
    errors.append(f"{display_path}: {message}")


def require_string(data: dict[str, Any], key: str, path: Path, errors: list[str]) -> None:
    value = data.get(key)
    if not isinstance(value, str) or not value.strip():
        fail(path, f"'{key}' must be a non-empty string", errors)


def require_list(data: dict[str, Any], key: str, path: Path, errors: list[str]) -> None:
    if not isinstance(data.get(key), list):
        fail(path, f"'{key}' must be a list", errors)


def is_int(value: Any) -> bool:
    return isinstance(value, int) and not isinstance(value, bool)


def validate_resource_id(value: Any, field: str, path: Path, errors: list[str]) -> None:
    if not isinstance(value, str) or not RESOURCE_ID.fullmatch(value):
        fail(path, f"'{field}' must be a namespaced resource id, got {value!r}", errors)


def validate_registry_predicate(value: Any, field: str, path: Path, errors: list[str]) -> None:
    """Validate the exact-id or #tag syntax used by CachedRegistryPredicate."""
    if not isinstance(value, str) or not REGISTRY_PREDICATE.fullmatch(value):
        fail(
            path,
            f"'{field}' must be a namespaced registry id or #tag predicate, got {value!r}",
            errors,
        )


def validate_boolean_fields(data: dict[str, Any], path: Path, errors: list[str]) -> None:
    for key in (
        "show_popup_on_unlock",
        "toast_on_unlock",
        "toast_on_complete",
        "disable_details_button",
        "details_open_by_default",
        "include_in_main",
        "hidden",
        "hide_when_completed",
        "repeatable",
        "global",
    ):
        if key in data and not isinstance(data[key], bool):
            fail(path, f"'{key}' must be a boolean", errors)


def validate_panel_geometry(data: dict[str, Any], path: Path, errors: list[str]) -> None:
    for key in ("left_panel_width", "right_panel_width", "panel_height"):
        if key in data:
            value = data[key]
            if not is_int(value) or value <= 0:
                fail(path, f"'{key}' must be a positive integer", errors)

    for key in (
        "left_panel_x_offset",
        "left_panel_y_offset",
        "right_panel_x_offset",
        "right_panel_y_offset",
    ):
        if key in data:
            value = data[key]
            if not is_int(value):
                fail(path, f"'{key}' must be an integer", errors)


def validate_sounds(data: dict[str, Any], path: Path, errors: list[str]) -> None:
    for key in ("triggered_sound", "completed_sound"):
        if key in data:
            validate_resource_id(data[key], key, path, errors)


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
        if not is_int(value) or value <= 0:
            fail(path, f"'{dimension}' must be a positive integer when an overlay is used", errors)

    for offset in ("overlay_x_offset", "overlay_y_offset"):
        value = data.get(offset)
        if value is not None and not is_int(value):
            fail(path, f"'{offset}' must be an integer", errors)


def validate_item_matcher(value: Any, field: str, path: Path, errors: list[str]) -> None:
    if value is None:
        return
    if isinstance(value, str):
        validate_registry_predicate(value, field, path, errors)
        return
    if isinstance(value, dict):
        key = "id" if "id" in value else "item" if "item" in value else None
        if key is not None:
            validate_registry_predicate(value[key], f"{field}.{key}", path, errors)
        # ItemMatcher deliberately permits an object with only NBT and therefore
        # no exact item predicate. Keep that wildcard behavior source-faithful.
        return
    fail(path, f"'{field}' must be a registry id/#tag string or item matcher object", errors)


def validate_entity_matcher(value: Any, field: str, path: Path, errors: list[str]) -> None:
    if value is None:
        return
    if isinstance(value, str):
        validate_registry_predicate(value, field, path, errors)
        return
    if isinstance(value, dict):
        key = "id" if "id" in value else "type" if "type" in value else None
        if key is None:
            fail(path, f"'{field}' object must contain 'id' or 'type'", errors)
        else:
            validate_registry_predicate(value[key], f"{field}.{key}", path, errors)
        return
    fail(path, f"'{field}' must be a registry id/#tag string or entity matcher object", errors)


def validate_bounds(value: Any, field: str, path: Path, errors: list[str]) -> None:
    # Util.bbFromJson accepts coordinate strings, arrays containing at least three
    # coordinates, or objects using x/y/z and min/max aliases. Reject only shapes
    # that are guaranteed to collapse to the silent 0,0,0 fallback.
    if isinstance(value, str):
        if not value.strip():
            fail(path, f"'{field}' coordinate string must not be empty", errors)
        return
    if isinstance(value, list):
        if len(value) not in (3, 6):
            fail(path, f"'{field}' coordinate array must contain exactly 3 or 6 integers", errors)
            return
        if any(not is_int(v) for v in value):
            fail(path, f"'{field}' coordinate array must contain integers only", errors)
        return
    if isinstance(value, dict):
        first_axes = (
            ("x", "x1", "minX", "min_x"),
            ("y", "y1", "minY", "min_y"),
            ("z", "z1", "minZ", "min_z"),
        )
        if not all(any(key in value for key in aliases) for aliases in first_axes):
            fail(path, f"'{field}' object must define x, y, and z minimum/point coordinates", errors)
            return
        for key, coordinate in value.items():
            if key in {
                "x", "y", "z", "x1", "y1", "z1", "x2", "y2", "z2",
                "minX", "minY", "minZ", "maxX", "maxY", "maxZ",
                "min_x", "min_y", "min_z", "max_x", "max_y", "max_z",
            } and not is_int(coordinate):
                fail(path, f"'{field}.{key}' must be an integer", errors)
        return
    fail(path, f"'{field}' must be a coordinate string, 3/6 integer array, or coordinate object", errors)


def validate_objective_entry(
    entry: Any,
    field: str,
    path: Path,
    errors: list[str],
) -> None:
    if not isinstance(entry, dict):
        fail(path, f"'{field}' must be an object", errors)
        return

    type_value = entry.get("type")
    if type_value is None:
        fail(path, f"'{field}' is missing objective type", errors)
        return

    validate_resource_id(type_value, f"{field}.type", path, errors)
    if not isinstance(type_value, str) or not RESOURCE_ID.fullmatch(type_value):
        return

    if type_value.startswith("questlog:") and type_value not in KNOWN_QUESTLOG_OBJECTIVES:
        fail(path, f"'{field}.type' is not a registered Questlog objective: {type_value}", errors)
        return

    required_amount = entry.get("required_amount")
    if required_amount is not None and (not is_int(required_amount) or required_amount < 1):
        fail(path, f"'{field}.required_amount' must be an integer >= 1", errors)

    # Custom namespaces are extension points. Their payload schemas are not known
    # to this repository validator, so only the common Objective contract applies.
    if not type_value.startswith("questlog:"):
        return

    if type_value in BLOCK_OBJECTIVES:
        if "block" not in entry:
            fail(path, f"'{field}.block' is required by {type_value}", errors)
        else:
            validate_registry_predicate(entry["block"], f"{field}.block", path, errors)

    if type_value in ITEM_OBJECTIVES:
        validate_item_matcher(entry.get("item"), f"{field}.item", path, errors)

    if type_value in ENTITY_OBJECTIVES:
        validate_entity_matcher(entry.get("entity"), f"{field}.entity", path, errors)
        if type_value == "questlog:entity_approach":
            range_value = entry.get("range")
            if not is_int(range_value) or range_value < 1:
                fail(path, f"'{field}.range' must be an integer >= 1", errors)

    if type_value in {"questlog:and", "questlog:or"}:
        children = entry.get("objectives")
        if not isinstance(children, list):
            fail(path, f"'{field}.objectives' must be a list", errors)
        else:
            for index, child in enumerate(children):
                validate_objective_entry(child, f"{field}.objectives[{index}]", path, errors)

    if type_value == "questlog:not":
        child = entry.get("objective")
        if not isinstance(child, dict):
            fail(path, f"'{field}.objective' must be an objective object", errors)
        else:
            validate_objective_entry(child, f"{field}.objective", path, errors)

    resource_fields = {
        "questlog:stat": "stat",
        "questlog:effect_added": "effect",
        "questlog:visit_biome": "biome",
        "questlog:visit_dimension": "dimension",
        "questlog:visit_structure": "structure",
        "questlog:quest_complete": "quest",
        "questlog:advancement": "advancement",
        "questlog:origin": "origin",
    }
    resource_field = resource_fields.get(type_value)
    if resource_field is not None:
        if resource_field not in entry:
            fail(path, f"'{field}.{resource_field}' is required by {type_value}", errors)
        else:
            validate_resource_id(entry[resource_field], f"{field}.{resource_field}", path, errors)

    if type_value == "questlog:stat" and "retroactive" in entry and not isinstance(entry["retroactive"], bool):
        fail(path, f"'{field}.retroactive' must be a boolean", errors)

    if type_value == "questlog:enchant":
        if "enchantment" in entry:
            validate_resource_id(entry["enchantment"], f"{field}.enchantment", path, errors)
        if "item" in entry:
            validate_resource_id(entry["item"], f"{field}.item", path, errors)
        if "level" in entry and (not is_int(entry["level"]) or entry["level"] < 1):
            fail(path, f"'{field}.level' must be an integer >= 1", errors)

    if type_value == "questlog:visit_position":
        if "bounds" not in entry:
            fail(path, f"'{field}.bounds' is required by {type_value}", errors)
        else:
            validate_bounds(entry["bounds"], f"{field}.bounds", path, errors)


def validate_objective_list(data: dict[str, Any], key: str, path: Path, errors: list[str]) -> None:
    values = data.get(key, [])
    if not isinstance(values, list):
        return
    for index, entry in enumerate(values):
        validate_objective_entry(entry, f"{key}[{index}]", path, errors)


def validate_reward_entry(
    entry: Any,
    field: str,
    path: Path,
    errors: list[str],
    *,
    inside_choice: bool = False,
) -> None:
    if not isinstance(entry, dict):
        fail(path, f"'{field}' must be an object", errors)
        return

    type_value = entry.get("type")
    if type_value is None:
        fail(path, f"'{field}' is missing reward type", errors)
        return

    validate_resource_id(type_value, f"{field}.type", path, errors)
    if not isinstance(type_value, str) or not RESOURCE_ID.fullmatch(type_value):
        return

    if type_value.startswith("questlog:") and type_value not in KNOWN_QUESTLOG_REWARDS:
        fail(path, f"'{field}.type' is not a registered Questlog reward: {type_value}", errors)
        return

    if "auto_claim" in entry and not isinstance(entry["auto_claim"], bool):
        fail(path, f"'{field}.auto_claim' must be a boolean", errors)

    if not type_value.startswith("questlog:"):
        return

    if type_value == "questlog:item":
        if "item" not in entry:
            fail(path, f"'{field}.item' is required by {type_value}", errors)
        else:
            value = entry["item"]
            if isinstance(value, str):
                validate_resource_id(value, f"{field}.item", path, errors)
            elif isinstance(value, dict):
                candidate = value.get("id", value.get("item"))
                if candidate is not None:
                    validate_resource_id(candidate, f"{field}.item.id", path, errors)
            else:
                fail(path, f"'{field}.item' must be a resource id or ItemStack object", errors)
        if "count" in entry and (not is_int(entry["count"]) or entry["count"] < 1):
            fail(path, f"'{field}.count' must be an integer >= 1", errors)

    elif type_value == "questlog:command":
        command = entry.get("command")
        if not isinstance(command, str) or not command.strip():
            fail(path, f"'{field}.command' must be a non-empty string", errors)
        if "permission_level" in entry and not is_int(entry["permission_level"]):
            fail(path, f"'{field}.permission_level' must be an integer", errors)

    elif type_value == "questlog:experience":
        experience = entry.get("experience")
        if not is_int(experience):
            fail(path, f"'{field}.experience' must be an integer", errors)
        if "levels" in entry and not isinstance(entry["levels"], bool):
            fail(path, f"'{field}.levels' must be a boolean", errors)

    elif type_value == "questlog:loot_table":
        if "loot_table" not in entry:
            fail(path, f"'{field}.loot_table' is required by {type_value}", errors)
        else:
            validate_resource_id(entry["loot_table"], f"{field}.loot_table", path, errors)

    elif type_value == "questlog:choice":
        if inside_choice:
            fail(
                path,
                f"'{field}' is a nested choice reward, which the current claim protocol cannot represent",
                errors,
            )
        if entry.get("auto_claim") is True:
            fail(path, f"'{field}.auto_claim' cannot be true for a choice reward", errors)

        choices = entry.get("choices")
        if not isinstance(choices, list):
            fail(path, f"'{field}.choices' must be a list", errors)
            return
        pick_count = entry.get("pick_count", 1)
        if not is_int(pick_count) or pick_count < 1:
            fail(path, f"'{field}.pick_count' must be an integer >= 1", errors)
        elif pick_count > len(choices):
            fail(path, f"'{field}.pick_count' cannot exceed the number of choices", errors)
        for index, choice in enumerate(choices):
            validate_reward_entry(
                choice,
                f"{field}.choices[{index}]",
                path,
                errors,
                inside_choice=True,
            )


def validate_reward_list(data: dict[str, Any], path: Path, errors: list[str]) -> None:
    values = data.get("rewards", [])
    if not isinstance(values, list):
        return
    for index, entry in enumerate(values):
        validate_reward_entry(entry, f"rewards[{index}]", path, errors)


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


def load_json_object(path: Path, errors: list[str]) -> dict[str, Any] | None:
    try:
        data = json.loads(path.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError) as exc:
        fail(path, f"invalid JSON: {exc}", errors)
        return None

    if not isinstance(data, dict):
        fail(path, "root must be a JSON object", errors)
        return None
    return data


def validate_quest(path: Path, errors: list[str], *, development_fixture: bool) -> None:
    data = load_json_object(path, errors)
    if data is None:
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

    validate_boolean_fields(data, path, errors)
    validate_panel_geometry(data, path, errors)
    validate_sounds(data, path, errors)
    validate_objective_list(data, "prerequisites", path, errors)
    validate_objective_list(data, "objectives", path, errors)
    validate_objective_list(data, "failures", path, errors)
    validate_reward_list(data, path, errors)
    validate_overlay(data, path, errors)

    if development_fixture:
        validate_dev_boundary(data, path, errors)
        if data.get("show_popup_on_unlock") is True and not data.get("prerequisites"):
            fail(
                path,
                "popup-on-unlock development test has no prerequisites; Questlog initializes such quests as already triggered",
                errors,
            )


def validate_bundled_manifest(errors: list[str]) -> int:
    index = load_json_object(BUNDLED_INDEX, errors)
    if index is None:
        return 0

    count = 0
    for category in ("quests", "chapters"):
        entries = index.get(category)
        if not isinstance(entries, list):
            fail(BUNDLED_INDEX, f"'{category}' must be a list", errors)
            continue

        seen: set[str] = set()
        for position, entry in enumerate(entries):
            if not isinstance(entry, str) or not entry.strip():
                fail(BUNDLED_INDEX, f"'{category}[{position}]' must be a non-empty relative JSON path", errors)
                continue

            relative = entry.replace("\\", "/")
            parts = Path(relative).parts
            if (
                not relative.endswith(".json")
                or relative.startswith("/")
                or ".." in parts
                or ":" in relative
            ):
                fail(BUNDLED_INDEX, f"unsafe bundled {category} path: {entry!r}", errors)
                continue

            if relative in seen:
                fail(BUNDLED_INDEX, f"duplicate bundled {category} entry: {relative}", errors)
                continue
            seen.add(relative)

            target = BUNDLED_ROOT / category / relative
            if not target.is_file():
                fail(BUNDLED_INDEX, f"listed bundled {category} definition does not exist: {relative}", errors)
                continue

            if target.stem.endswith("_dev"):
                fail(target, "development definitions must never be listed for bundled distribution", errors)

            if category == "quests":
                validate_quest(target, errors, development_fixture=False)
            else:
                load_json_object(target, errors)
            count += 1

    return count


def main() -> int:
    quests = sorted(QUEST_DIR.glob("overlord_*.json"))
    if not quests:
        print("No OVERLORD QUESTS example definitions found.", file=sys.stderr)
        return 1

    errors: list[str] = []
    for quest in quests:
        validate_quest(quest, errors, development_fixture=True)

    bundled_count = validate_bundled_manifest(errors)

    if errors:
        print(f"OVERLORD QUESTS validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print(
        f"Validated {len(quests)} OVERLORD QUESTS development quest definition(s) "
        f"and {bundled_count} bundled definition(s)."
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
