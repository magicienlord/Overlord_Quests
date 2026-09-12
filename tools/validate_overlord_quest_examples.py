#!/usr/bin/env python3
"""OVERLORD QUESTS definition validator with narrative/provider extensions.

The original Questlog-derived structural validator is retained in the sibling
core module. This front end extends its registered type surface and validates the
OVERLORD narrative/provider fields without weakening the inherited checks.
"""
from __future__ import annotations

from pathlib import Path
from typing import Any
import validate_campaign_opening_contracts as campaign_contract
import validate_optional_objectives as optional_contract
import validate_overlord_quest_examples_core as core
import validate_presentation_contracts as presentation_contract

core.KNOWN_QUESTLOG_OBJECTIVES.update({
    "questlog:disposition",
    "questlog:fact",
    "questlog:entity_died",
    "questlog:entity_kill_stat",
    "questlog:item_craft_stat",
    "questlog:visit_dimension_history",
    "questlog:visit_position_history",
    "questlog:visit_structure_history",
})
# entity_died deliberately reuses the source-faithful EntityMatcher payload while
# changing only which entity in a death event is treated as the objective target.
core.ENTITY_OBJECTIVES.add("questlog:entity_died")
core.KNOWN_QUESTLOG_REWARDS.update({
    "questlog:set_disposition",
    "questlog:set_fact",
    "questlog:unlock_minion",
})

_CORE_OBJECTIVE_ENTRY = core.validate_objective_entry
_CORE_REWARD_ENTRY = core.validate_reward_entry
_CORE_VALIDATE_QUEST = core.validate_quest


def validate_scoreboard_tag(value: Any, field: str, path: Path, errors: list[str]) -> None:
    if not isinstance(value, str) or not value.strip():
        core.fail(path, f"'{field}' must be a non-empty scoreboard tag string", errors)
    elif len(value) > 1024:
        # Entity scoreboard tags are persisted strings. Keep configuration input
        # bounded well below arbitrary definition abuse while preserving Minecraft's
        # normal practical tag use.
        core.fail(path, f"'{field}' must not exceed 1024 characters", errors)


def validate_objective_entry(entry: Any, field: str, path: Path, errors: list[str]) -> None:
    _CORE_OBJECTIVE_ENTRY(entry, field, path, errors)
    if not isinstance(entry, dict):
        return

    objective_type = entry.get("type")

    if objective_type in core.ENTITY_OBJECTIVES:
        if "scoreboard_tag" in entry:
            validate_scoreboard_tag(entry["scoreboard_tag"], f"{field}.scoreboard_tag", path, errors)
        entity_matcher = entry.get("entity")
        if isinstance(entity_matcher, dict) and "scoreboard_tag" in entity_matcher:
            validate_scoreboard_tag(
                entity_matcher["scoreboard_tag"],
                f"{field}.entity.scoreboard_tag",
                path,
                errors,
            )

        if objective_type == "questlog:entity_died":
            has_selector = any(
                key in entry for key in ("entity", "custom_name", "entity_name", "scoreboard_tag", "predicate")
            )
            if not has_selector:
                core.fail(
                    path,
                    f"'{field}' questlog:entity_died requires an explicit entity matcher selector",
                    errors,
                )

    if objective_type == "questlog:disposition":
        for key in ("civilization", "state"):
            if key not in entry:
                core.fail(path, f"'{field}.{key}' is required by questlog:disposition", errors)
            else:
                core.validate_resource_id(entry[key], f"{field}.{key}", path, errors)

        amount = entry.get("required_amount")
        if amount is not None and amount != 1:
            core.fail(path, f"'{field}.required_amount' must be exactly 1 for questlog:disposition", errors)
        return

    if objective_type == "questlog:fact":
        if "fact" not in entry:
            core.fail(path, f"'{field}.fact' is required by questlog:fact", errors)
        else:
            core.validate_resource_id(entry["fact"], f"{field}.fact", path, errors)

        amount = entry.get("required_amount")
        if amount is not None and amount != 1:
            core.fail(path, f"'{field}.required_amount' must be exactly 1 for questlog:fact", errors)
        return

    if objective_type == "questlog:entity_kill_stat":
        if "entity" not in entry:
            core.fail(path, f"'{field}.entity' is required by questlog:entity_kill_stat", errors)
        else:
            core.validate_resource_id(entry["entity"], f"{field}.entity", path, errors)
        return

    if objective_type == "questlog:item_craft_stat":
        if "item" not in entry:
            core.fail(path, f"'{field}.item' is required by questlog:item_craft_stat", errors)
        else:
            core.validate_resource_id(entry["item"], f"{field}.item", path, errors)
        return

    if objective_type == "questlog:visit_dimension_history":
        if "dimension" not in entry:
            core.fail(path, f"'{field}.dimension' is required by questlog:visit_dimension_history", errors)
        else:
            core.validate_resource_id(entry["dimension"], f"{field}.dimension", path, errors)
        amount = entry.get("required_amount")
        if amount is not None and amount != 1:
            core.fail(path, f"'{field}.required_amount' must be exactly 1 for questlog:visit_dimension_history", errors)
        return

    if objective_type == "questlog:visit_position_history":
        if "bounds" not in entry:
            core.fail(path, f"'{field}.bounds' is required by questlog:visit_position_history", errors)
        else:
            core.validate_bounds(entry["bounds"], f"{field}.bounds", path, errors)
        if "dimension" in entry:
            core.validate_resource_id(entry["dimension"], f"{field}.dimension", path, errors)
        amount = entry.get("required_amount")
        if amount is not None and amount != 1:
            core.fail(path, f"'{field}.required_amount' must be exactly 1 for questlog:visit_position_history", errors)
        return

    if objective_type == "questlog:visit_structure_history":
        if "structure" not in entry:
            core.fail(path, f"'{field}.structure' is required by questlog:visit_structure_history", errors)
        else:
            core.validate_resource_id(entry["structure"], f"{field}.structure", path, errors)
        amount = entry.get("required_amount")
        if amount is not None and amount != 1:
            core.fail(path, f"'{field}.required_amount' must be exactly 1 for questlog:visit_structure_history", errors)
        return


def validate_reward_entry(
    entry: Any,
    field: str,
    path: Path,
    errors: list[str],
    *,
    inside_choice: bool = False,
) -> None:
    _CORE_REWARD_ENTRY(entry, field, path, errors, inside_choice=inside_choice)
    if not isinstance(entry, dict):
        return

    reward_type = entry.get("type")
    if reward_type == "questlog:set_disposition":
        for key in ("civilization", "state"):
            if key not in entry:
                core.fail(path, f"'{field}.{key}' is required by questlog:set_disposition", errors)
            else:
                core.validate_resource_id(entry[key], f"{field}.{key}", path, errors)
        return

    if reward_type == "questlog:set_fact":
        if "fact" not in entry:
            core.fail(path, f"'{field}.fact' is required by questlog:set_fact", errors)
        else:
            core.validate_resource_id(entry["fact"], f"{field}.fact", path, errors)
        return

    if reward_type == "questlog:unlock_minion":
        slot = entry.get("slot")
        if slot not in {"red", "green", "blue"}:
            core.fail(path, f"'{field}.slot' must be one of: red, green, blue", errors)
        if entry.get("auto_claim") is not True:
            core.fail(path, f"'{field}.auto_claim' must be true for questlog:unlock_minion", errors)
        if inside_choice:
            core.fail(path, f"'{field}' questlog:unlock_minion cannot be nested inside a choice reward", errors)
        return


def validate_failure_rewards(data: dict[str, Any], path: Path, errors: list[str]) -> None:
    if "failure_rewards" not in data:
        return

    values = data.get("failure_rewards")
    if not isinstance(values, list):
        core.fail(path, "'failure_rewards' must be a list", errors)
        return

    for index, entry in enumerate(values):
        field = f"failure_rewards[{index}]"
        validate_reward_entry(entry, field, path, errors)
        if not isinstance(entry, dict):
            continue
        if entry.get("type") == "questlog:choice":
            core.fail(path, f"'{field}' cannot be a choice reward because quest failure has no claim-selection flow", errors)
        if entry.get("type") == "questlog:unlock_minion":
            core.fail(path, f"'{field}' cannot unlock a Minion tier as a failure consequence", errors)
        if entry.get("auto_claim") is not True:
            core.fail(path, f"'{field}.auto_claim' must be true for an automatic failure consequence", errors)


def validate_provider_rule(
    data: dict[str, Any],
    path: Path,
    errors: list[str],
    *,
    development_fixture: bool = False,
) -> None:
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

    for key in (
        "entity_types",
        "entity_type_tags",
        "dimensions",
        "unlock_quests",
        "required_facts",
        "forbidden_facts",
    ):
        if key not in provider:
            continue
        values = provider[key]
        if not isinstance(values, list):
            core.fail(path, f"'provider.{key}' must be a list", errors)
            continue
        for index, value in enumerate(values):
            core.validate_resource_id(value, f"provider.{key}[{index}]", path, errors)

    required_facts = provider.get("required_facts", [])
    forbidden_facts = provider.get("forbidden_facts", [])
    if isinstance(required_facts, list) and isinstance(forbidden_facts, list):
        overlap = {
            value for value in required_facts
            if isinstance(value, str) and value in forbidden_facts
        }
        if overlap:
            core.fail(
                path,
                "provider cannot both require and forbid narrative fact(s): " + ", ".join(sorted(overlap)),
                errors,
            )

    entity_types = provider.get("entity_types", [])
    entity_tags = provider.get("entity_type_tags", [])
    if not isinstance(entity_types, list) or not isinstance(entity_tags, list) or (not entity_types and not entity_tags):
        core.fail(path, "provider requires at least one entity_types or entity_type_tags selector", errors)

    scoreboard_tags = provider.get("scoreboard_tags", [])
    if "scoreboard_tags" in provider:
        if not isinstance(scoreboard_tags, list):
            core.fail(path, "'provider.scoreboard_tags' must be a list", errors)
        else:
            for index, value in enumerate(scoreboard_tags):
                if not isinstance(value, str) or not value.strip():
                    core.fail(path, f"'provider.scoreboard_tags[{index}]' must be a non-empty string", errors)

    location = provider.get("location")
    if "location" in provider:
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
                    continue
                if any(value < -2147483648 or value > 2147483647 for value in coordinates):
                    core.fail(path, f"'provider.location.{key}' values must fit signed 32-bit integers", errors)

    if not development_fixture and provider.get("civilization") is not None:
        has_tag_scope = isinstance(scoreboard_tags, list) and any(
            isinstance(value, str) and value.strip() for value in scoreboard_tags
        )
        has_location_scope = isinstance(location, dict)
        if not has_tag_scope and not has_location_scope:
            core.fail(
                path,
                "production civilization provider must be anchor-scoped by scoreboard_tags or location",
                errors,
            )

    if "dialogue" in provider:
        dialogue = provider["dialogue"]
        if not isinstance(dialogue, dict):
            core.fail(path, "'provider.dialogue' must be an object", errors)
        else:
            allowed_keys = {"offer", "in_progress", "ready_to_turn_in", "failed", "completed"}
            for key, value in dialogue.items():
                if key not in allowed_keys:
                    core.fail(path, f"'provider.dialogue.{key}' is not a supported dialogue phase", errors)
                    continue
                if isinstance(value, str):
                    if not value.strip():
                        core.fail(path, f"'provider.dialogue.{key}' must not be empty", errors)
                elif isinstance(value, list) and value:
                    for index, line in enumerate(value):
                        if not isinstance(line, str) or not line.strip():
                            core.fail(path, f"'provider.dialogue.{key}[{index}]' must be a non-empty string", errors)
                else:
                    core.fail(path, f"'provider.dialogue.{key}' must be a non-empty string or string list", errors)

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
        validate_failure_rewards(data, path, errors)
        validate_provider_rule(data, path, errors, development_fixture=development_fixture)


core.validate_objective_entry = validate_objective_entry
core.validate_reward_entry = validate_reward_entry
core.validate_quest = validate_quest

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
    core_result = core.main()
    if core_result != 0:
        return core_result

    optional_result = optional_contract.main()
    if optional_result != 0:
        return optional_result

    presentation_result = presentation_contract.main()
    if presentation_result != 0:
        return presentation_result

    return campaign_contract.main()


if __name__ == "__main__":
    raise SystemExit(main())
