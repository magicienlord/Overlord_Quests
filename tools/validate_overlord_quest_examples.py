#!/usr/bin/env python3
"""OVERLORD QUESTS definition validator with narrative/provider extensions.

The original Questlog-derived structural validator is retained in the sibling
core module. This front end extends its registered type surface and validates the
OVERLORD narrative/provider fields without weakening the inherited checks.
"""
from __future__ import annotations

from pathlib import Path
from typing import Any
import validate_overlord_quest_examples_core as core

core.KNOWN_QUESTLOG_OBJECTIVES.add("questlog:disposition")
core.KNOWN_QUESTLOG_REWARDS.add("questlog:set_disposition")

_CORE_OBJECTIVE_ENTRY = core.validate_objective_entry
_CORE_REWARD_ENTRY = core.validate_reward_entry
_CORE_VALIDATE_QUEST = core.validate_quest


def validate_objective_entry(entry: Any, field: str, path: Path, errors: list[str]) -> None:
    _CORE_OBJECTIVE_ENTRY(entry, field, path, errors)
    if not isinstance(entry, dict) or entry.get("type") != "questlog:disposition":
        return

    for key in ("civilization", "state"):
        if key not in entry:
            core.fail(path, f"'{field}.{key}' is required by questlog:disposition", errors)
        else:
            core.validate_resource_id(entry[key], f"{field}.{key}", path, errors)

    amount = entry.get("required_amount")
    if amount is not None and amount != 1:
        core.fail(path, f"'{field}.required_amount' must be exactly 1 for questlog:disposition", errors)


def validate_reward_entry(
    entry: Any,
    field: str,
    path: Path,
    errors: list[str],
    *,
    inside_choice: bool = False,
) -> None:
    _CORE_REWARD_ENTRY(entry, field, path, errors, inside_choice=inside_choice)
    if not isinstance(entry, dict) or entry.get("type") != "questlog:set_disposition":
        return

    for key in ("civilization", "state"):
        if key not in entry:
            core.fail(path, f"'{field}.{key}' is required by questlog:set_disposition", errors)
        else:
            core.validate_resource_id(entry[key], f"{field}.{key}", path, errors)


def validate_provider_rule(data: dict[str, Any], path: Path, errors: list[str]) -> None:
    provider = data.get("provider")
    if provider is None:
        return
    if not isinstance(provider, dict):
        core.fail(path, "'provider' must be an object", errors)
        return

    for key in ("pool", "civilization"):
        if key in provider:
            core.validate_resource_id(provider[key], f"provider.{key}", path, errors)

    if "role" in provider and (not isinstance(provider["role"], str) or not provider["role"].strip()):
        core.fail(path, "'provider.role' must be a non-empty string", errors)

    for key in ("entity_types", "entity_type_tags", "dimensions", "unlock_quests"):
        if key not in provider:
            continue
        values = provider[key]
        if not isinstance(values, list):
            core.fail(path, f"'provider.{key}' must be a list", errors)
            continue
        for index, value in enumerate(values):
            core.validate_resource_id(value, f"provider.{key}[{index}]", path, errors)

    entity_types = provider.get("entity_types", [])
    entity_tags = provider.get("entity_type_tags", [])
    if not isinstance(entity_types, list) or not isinstance(entity_tags, list) or (not entity_types and not entity_tags):
        core.fail(path, "provider requires at least one entity_types or entity_type_tags selector", errors)

    if "scoreboard_tags" in provider:
        values = provider["scoreboard_tags"]
        if not isinstance(values, list):
            core.fail(path, "'provider.scoreboard_tags' must be a list", errors)
        else:
            for index, value in enumerate(values):
                if not isinstance(value, str) or not value.strip():
                    core.fail(path, f"'provider.scoreboard_tags[{index}]' must be a non-empty string", errors)

    if "location" in provider:
        location = provider["location"]
        if not isinstance(location, dict):
            core.fail(path, "'provider.location' must be an object", errors)
        else:
            for key in ("min", "max"):
                coordinates = location.get(key)
                if not isinstance(coordinates, list) or len(coordinates) != 3:
                    core.fail(path, f"'provider.location.{key}' must be a three-integer list", errors)
                    continue
                if any(not core.is_int(value) for value in coordinates):
                    core.fail(path, f"'provider.location.{key}' must contain integers only", errors)

    if "lock_to_provider" in provider and not isinstance(provider["lock_to_provider"], bool):
        core.fail(path, "'provider.lock_to_provider' must be a boolean", errors)

    turn_in = provider.get("turn_in")
    if turn_in is not None and turn_in not in {"none", "same_provider", "any_eligible"}:
        core.fail(path, "'provider.turn_in' must be one of: none, same_provider, any_eligible", errors)

    if "required_dispositions" in provider:
        requirements = provider["required_dispositions"]
        if not isinstance(requirements, dict):
            core.fail(path, "'provider.required_dispositions' must be an object", errors)
        else:
            for civilization, states in requirements.items():
                core.validate_resource_id(civilization, "provider.required_dispositions civilization", path, errors)
                if isinstance(states, str):
                    core.validate_resource_id(states, f"provider.required_dispositions.{civilization}", path, errors)
                elif isinstance(states, list) and states:
                    for index, state in enumerate(states):
                        core.validate_resource_id(state, f"provider.required_dispositions.{civilization}[{index}]", path, errors)
                else:
                    core.fail(
                        path,
                        f"'provider.required_dispositions.{civilization}' must be a resource id or non-empty list",
                        errors,
                    )


def validate_quest(path: Path, errors: list[str], *, development_fixture: bool) -> None:
    _CORE_VALIDATE_QUEST(path, errors, development_fixture=development_fixture)
    data = core.load_json_object(path, errors)
    if data is not None:
        validate_provider_rule(data, path, errors)


# Install wrappers into the retained core module so its recursive validation and
# main manifest walk use the OVERLORD extensions too.
core.validate_objective_entry = validate_objective_entry
core.validate_reward_entry = validate_reward_entry
core.validate_quest = validate_quest

# Re-export helpers used by the existing validator self-test.
fail = core.fail
validate_resource_id = core.validate_resource_id
validate_registry_predicate = core.validate_registry_predicate
validate_bounds = core.validate_bounds
validate_item_matcher = core.validate_item_matcher
validate_entity_matcher = core.validate_entity_matcher
KNOWN_QUESTLOG_OBJECTIVES = core.KNOWN_QUESTLOG_OBJECTIVES
KNOWN_QUESTLOG_REWARDS = core.KNOWN_QUESTLOG_REWARDS
ROOT = core.ROOT
QUEST_DIR = core.QUEST_DIR
BUNDLED_ROOT = core.BUNDLED_ROOT
BUNDLED_INDEX = core.BUNDLED_INDEX


def main() -> int:
    return core.main()


if __name__ == "__main__":
    raise SystemExit(main())
