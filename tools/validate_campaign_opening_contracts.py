#!/usr/bin/env python3
"""Guard the bundled REIGN opening and first civilization slices.

This validator deliberately checks implementation invariants only. It does not
print authored dialogue or expose concealed later campaign content.
"""
from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
BUNDLED = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
OPENING_QUESTS = BUNDLED / "quests/campaign/opening"
TOWER_QUESTS = BUNDLED / "quests/campaign/tower"
EXPANSION_QUESTS = BUNDLED / "quests/campaign/expansion"
CIVILIZATION_QUESTS = BUNDLED / "quests/campaign/civilizations"
INDEX = BUNDLED / "index.json"

OPENING = OPENING_QUESTS / "a_new_master.json"
BROWN = OPENING_QUESTS / "restore_browns.json"
BROWN_REACTION = OPENING_QUESTS / "browns_return.json"
DIRECT = OPENING_QUESTS / "make_an_impression.json"
DIRECT_REACTION = OPENING_QUESTS / "direct_action_reaction.json"
FORGE = TOWER_QUESTS / "prepare_the_forge.json"
FORGE_REACTION = TOWER_QUESTS / "forge_prepared_reaction.json"
INITIAL_FOUNDATION = EXPANSION_QUESTS / "the_reign_takes_shape.json"
GOBLIN_CONTACT = CIVILIZATION_QUESTS / "goblins/first_contact.json"
GNUMU_CONTACT = CIVILIZATION_QUESTS / "gnumus/first_contact.json"

OPENING_ID = "questlog:campaign/opening/a_new_master"
BROWN_ID = "questlog:campaign/opening/restore_browns"
BROWN_REACTION_ID = "questlog:campaign/opening/browns_return"
DIRECT_ID = "questlog:campaign/opening/make_an_impression"
DIRECT_REACTION_ID = "questlog:campaign/opening/direct_action_reaction"
FORGE_ID = "questlog:campaign/tower/prepare_the_forge"
FORGE_REACTION_ID = "questlog:campaign/tower/forge_prepared_reaction"
STAFF_ID = "minionsremastered:masters_staff"
FORGE_FACT = "overlord_reign:tower/forge_prepared"
INITIAL_FOUNDATION_FACT = "overlord_reign:reign/initial_foundation_established"


def load(path: Path, errors: list[str]) -> dict[str, Any]:
    try:
        data = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: unreadable JSON: {exc}")
        return {}
    if not isinstance(data, dict):
        errors.append(f"{path.relative_to(ROOT)}: root must be an object")
        return {}
    return data


def has_quest_complete(data: dict[str, Any], quest_id: str) -> bool:
    prerequisites = data.get("prerequisites", [])
    return isinstance(prerequisites, list) and any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:quest_complete"
        and entry.get("quest") == quest_id
        for entry in prerequisites
    )


def nested_quest_complete_ids(entry: Any) -> set[str]:
    if not isinstance(entry, dict):
        return set()
    if entry.get("type") == "questlog:quest_complete" and isinstance(entry.get("quest"), str):
        return {entry["quest"]}
    if entry.get("type") in {"questlog:and", "questlog:or"}:
        found: set[str] = set()
        children = entry.get("objectives", [])
        if isinstance(children, list):
            for child in children:
                found.update(nested_quest_complete_ids(child))
        return found
    if entry.get("type") == "questlog:not":
        return nested_quest_complete_ids(entry.get("objective"))
    return set()


def validate_provider_contact(
    data: dict[str, Any],
    errors: list[str],
    *,
    label: str,
    entity_type: str,
    anchor_tag: str,
    civilization: str,
    contact_fact: str,
    role: str | None = None,
) -> None:
    provider = data.get("provider")
    if not isinstance(provider, dict):
        errors.append(f"{label} must remain provider-bound to its designated anchor NPC")
    else:
        if provider.get("entity_types") != [entity_type]:
            errors.append(f"{label} must target only its source-backed anchor entity type")
        if provider.get("scoreboard_tags") != [anchor_tag]:
            errors.append(f"{label} must remain scoped to its designated local civilization anchor")
        if provider.get("civilization") != civilization:
            errors.append(f"{label} civilization identity changed unexpectedly")
        if provider.get("required_facts") != [INITIAL_FOUNDATION_FACT]:
            errors.append(f"{label} must remain gated behind the semi-open campaign foundation")
        if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
            errors.append(f"{label} must remain bound to the exact issuing anchor NPC")
        if role is None:
            if "role" in provider:
                errors.append(f"{label} must not acquire an unsupported provider role")
        elif provider.get("role") != role:
            errors.append(f"{label} must retain its authored local social role")

        dialogue = provider.get("dialogue")
        if not isinstance(dialogue, dict):
            errors.append(f"{label} must retain authored provider dialogue")
        else:
            for phase in ("offer", "ready_to_turn_in", "completed"):
                if not isinstance(dialogue.get(phase), str) or not dialogue.get(phase, "").strip():
                    errors.append(f"{label} must retain non-empty {phase} provider dialogue")
            if "in_progress" in dialogue:
                errors.append(f"{label} must not expose a fake in-progress phase when no intervening gameplay objective exists")

    prerequisites = data.get("prerequisites", [])
    if not isinstance(prerequisites, list) or len(prerequisites) != 1:
        errors.append(f"{label} must retain one initial-foundation prerequisite")
    else:
        prerequisite = prerequisites[0]
        if not isinstance(prerequisite, dict) or not (
            prerequisite.get("type") == "questlog:fact"
            and prerequisite.get("fact") == INITIAL_FOUNDATION_FACT
            and prerequisite.get("required_amount") == 1
        ):
            errors.append(f"{label} must remain gated by the established reign foundation fact")

    if data.get("objectives") != []:
        errors.append(f"{label} must remain a provider-native conversation with no artificial journal objective")

    rewards = data.get("rewards", [])
    if not isinstance(rewards, list) or len(rewards) != 1:
        errors.append(f"{label} must write exactly one contact-history fact")
    else:
        reward = rewards[0]
        if not isinstance(reward, dict) or not (
            reward.get("type") == "questlog:set_fact"
            and reward.get("fact") == contact_fact
            and reward.get("auto_claim") is True
        ):
            errors.append(f"{label} must persist its designated-anchor contact fact")

    serialized = json.dumps(data, sort_keys=True)
    if "questlog:set_disposition" in serialized or "required_dispositions" in serialized:
        errors.append(f"{label} must not prematurely resolve or require a political disposition")
    if data.get("show_popup_on_unlock") is not False:
        errors.append(f"{label} must remain an in-world provider interaction rather than a remote popup")


def collect_errors() -> list[str]:
    errors: list[str] = []
    opening = load(OPENING, errors)
    brown = load(BROWN, errors)
    brown_reaction = load(BROWN_REACTION, errors)
    direct = load(DIRECT, errors)
    direct_reaction = load(DIRECT_REACTION, errors)
    forge = load(FORGE, errors)
    forge_reaction = load(FORGE_REACTION, errors)
    initial_foundation = load(INITIAL_FOUNDATION, errors)
    goblin_contact = load(GOBLIN_CONTACT, errors)
    gnumu_contact = load(GNUMU_CONTACT, errors)
    index = load(INDEX, errors)

    bundled_quests = index.get("quests", [])
    required_paths = {
        "campaign/opening/a_new_master.json",
        "campaign/opening/restore_browns.json",
        "campaign/opening/browns_return.json",
        "campaign/opening/make_an_impression.json",
        "campaign/opening/direct_action_reaction.json",
        "campaign/tower/prepare_the_forge.json",
        "campaign/tower/forge_prepared_reaction.json",
        "campaign/expansion/the_reign_takes_shape.json",
        "campaign/civilizations/goblins/first_contact.json",
        "campaign/civilizations/gnumus/first_contact.json",
    }
    if not isinstance(bundled_quests, list) or not required_paths.issubset(set(bundled_quests)):
        errors.append("bundled definition index is missing one or more guarded production campaign definitions")

    if opening.get("show_popup_on_unlock") is not True:
        errors.append("opening campaign entry must remain an automatic speaker popup")
    if opening.get("speaker_id") != "overlord_reign:gnarl":
        errors.append("opening campaign entry must remain assigned to Gnarl")
    opening_prerequisites = opening.get("prerequisites", [])
    if not isinstance(opening_prerequisites, list) or len(opening_prerequisites) != 1:
        errors.append("opening campaign entry must retain exactly one initial-play trigger prerequisite")
    else:
        trigger = opening_prerequisites[0]
        if not isinstance(trigger, dict) or not (
            trigger.get("type") == "questlog:stat"
            and trigger.get("stat") == "minecraft:play_time"
            and trigger.get("required_amount") == 1
            and trigger.get("retroactive") is True
        ):
            errors.append("opening campaign initial-play trigger contract changed")

    if not has_quest_complete(brown, OPENING_ID):
        errors.append("Brown recovery branch must unlock from the opening campaign entry")
    if not has_quest_complete(direct, OPENING_ID):
        errors.append("direct-action branch must unlock from the opening campaign entry")
    if has_quest_complete(brown, DIRECT_ID):
        errors.append("Brown recovery must not be serialized behind the direct-action branch")
    if has_quest_complete(direct, BROWN_ID):
        errors.append("direct action must not be serialized behind Brown recovery")

    brown_objectives = brown.get("objectives", [])
    if not isinstance(brown_objectives, list) or len(brown_objectives) != 1:
        errors.append("Brown recovery must retain one authoritative observation objective")
    else:
        objective = brown_objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:item_craft_stat"
            and objective.get("item") == STAFF_ID
            and objective.get("required_amount") == 1
        ):
            errors.append("Brown recovery must remain sequence-break-safe on the exact Master's Staff craft")
    if brown.get("rewards") not in ([], None):
        errors.append("Brown recovery must not grant the Brown slot from Questlog")

    if not has_quest_complete(brown_reaction, BROWN_ID):
        errors.append("Brown recovery reaction must depend on completed Brown recovery")
    if brown_reaction.get("speaker_id") != "overlord_reign:gnarl":
        errors.append("Brown recovery reaction must remain a Gnarl speaker entry")
    if brown_reaction.get("speaker_reaction") != "approving":
        errors.append("Brown recovery reaction semantic state changed unexpectedly")

    direct_objectives = direct.get("objectives", [])
    if not isinstance(direct_objectives, list) or len(direct_objectives) != 1:
        errors.append("direct-action branch must retain one bounded first-action objective")
    else:
        objective = direct_objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:stat"
            and objective.get("stat") == "minecraft:mob_kills"
            and objective.get("required_amount") == 1
            and objective.get("retroactive") is False
        ):
            errors.append("direct-action opening contract changed unexpectedly")

    if not has_quest_complete(direct_reaction, DIRECT_ID):
        errors.append("direct-action reaction must depend on completed direct action")
    if direct_reaction.get("speaker_id") != "overlord_reign:gnarl":
        errors.append("direct-action reaction must remain a Gnarl speaker entry")
    if direct_reaction.get("speaker_reaction") != "mocking":
        errors.append("direct-action reaction semantic state changed unexpectedly")

    forge_prerequisites = forge.get("prerequisites", [])
    if not isinstance(forge_prerequisites, list) or len(forge_prerequisites) != 1:
        errors.append("first Tower infrastructure quest must retain one OR convergence prerequisite")
    else:
        convergence = forge_prerequisites[0]
        if not isinstance(convergence, dict) or convergence.get("type") != "questlog:or":
            errors.append("first Tower infrastructure quest must converge through questlog:or")
        else:
            dependency_ids = nested_quest_complete_ids(convergence)
            if dependency_ids != {BROWN_REACTION_ID, DIRECT_REACTION_ID}:
                errors.append("first Tower infrastructure quest must remain reachable from either completed opening direction")

    forge_objectives = forge.get("objectives", [])
    if not isinstance(forge_objectives, list) or len(forge_objectives) != 1:
        errors.append("first Tower infrastructure quest must retain one native progression objective")
    else:
        objective = forge_objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:advancement"
            and objective.get("advancement") == "hot_iron:local_smithery"
            and objective.get("required_amount") == 1
        ):
            errors.append("first Tower forge preparation must remain tied to Hot Iron native progression")

    forge_rewards = forge.get("rewards", [])
    if not isinstance(forge_rewards, list) or len(forge_rewards) != 1:
        errors.append("first Tower infrastructure quest must write one restoration marker")
    else:
        reward = forge_rewards[0]
        if not isinstance(reward, dict) or not (
            reward.get("type") == "questlog:set_fact"
            and reward.get("fact") == FORGE_FACT
            and reward.get("auto_claim") is True
        ):
            errors.append("Tower forge preparation must persist its restoration fact")

    if not has_quest_complete(forge_reaction, FORGE_ID):
        errors.append("Tower forge reaction must depend on completed forge preparation")
    if forge_reaction.get("speaker_id") != "overlord_reign:gnarl":
        errors.append("Tower forge reaction must remain a Gnarl speaker entry")
    if forge_reaction.get("speaker_reaction") != "approving":
        errors.append("Tower forge reaction semantic state changed unexpectedly")
    if forge_reaction.get("show_popup_on_unlock") is not True:
        errors.append("Tower forge reaction must remain an automatic popup")

    foundation_prerequisites = initial_foundation.get("prerequisites", [])
    if not isinstance(foundation_prerequisites, list) or len(foundation_prerequisites) != 2:
        errors.append("initial-foundation convergence must require exactly Brown recovery and first Tower restoration")
    else:
        dependency_ids = {
            entry.get("quest")
            for entry in foundation_prerequisites
            if isinstance(entry, dict) and entry.get("type") == "questlog:quest_complete"
        }
        if dependency_ids != {BROWN_REACTION_ID, FORGE_REACTION_ID}:
            errors.append("initial-foundation convergence must wait for Brown recovery and first Tower restoration reactions")

    foundation_objectives = initial_foundation.get("objectives", [])
    if not isinstance(foundation_objectives, list) or len(foundation_objectives) != 1:
        errors.append("initial-foundation convergence must remain a one-step Gnarl assessment")
    else:
        objective = foundation_objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:read"
            and objective.get("required_amount") == 1
        ):
            errors.append("initial-foundation convergence must remain a read objective")

    foundation_rewards = initial_foundation.get("rewards", [])
    if not isinstance(foundation_rewards, list) or len(foundation_rewards) != 1:
        errors.append("initial-foundation convergence must write one semantic campaign marker")
    else:
        reward = foundation_rewards[0]
        if not isinstance(reward, dict) or not (
            reward.get("type") == "questlog:set_fact"
            and reward.get("fact") == INITIAL_FOUNDATION_FACT
            and reward.get("auto_claim") is True
        ):
            errors.append("initial-foundation convergence must persist its semantic campaign fact")

    if initial_foundation.get("speaker_id") != "overlord_reign:gnarl":
        errors.append("initial-foundation convergence must remain assigned to Gnarl")
    if initial_foundation.get("speaker_reaction") != "directive":
        errors.append("initial-foundation convergence semantic speaker state changed unexpectedly")
    if initial_foundation.get("show_popup_on_unlock") is not True:
        errors.append("initial-foundation convergence must remain an automatic popup")
    if initial_foundation.get("include_in_main") is not True:
        errors.append("initial-foundation convergence must remain part of the main campaign")

    validate_provider_contact(
        goblin_contact,
        errors,
        label="Goblin first contact",
        entity_type="goblins_tyranny:leader_goblin",
        anchor_tag="overlord_anchor:goblin_main",
        civilization="overlord_reign:goblins",
        contact_fact="overlord_reign:civilizations/goblins/contact_established",
    )
    validate_provider_contact(
        gnumu_contact,
        errors,
        label="Gnumu first contact",
        entity_type="gnumus:gnumus_shaman",
        anchor_tag="overlord_anchor:gnumu_main_elder",
        civilization="overlord_reign:gnumus",
        contact_fact="overlord_reign:civilizations/gnumus/contact_established",
        role="elder_shaman",
    )

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Production campaign contract validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("OVERLORD production campaign contracts: PASS")
    print("opening concurrency: preserved")
    print("Gnarl lifecycle reactions: preserved for both opening directions and first Tower restoration")
    print("Brown bootstrap authority: Minions Remastered")
    print("Brown craft observation: retrospective exact-item statistic")
    print("first Tower convergence: native Hot Iron progression with persistent restoration fact")
    print("early-recovery convergence: Brown recovery plus first Tower restoration records the semi-open campaign foundation")
    print("civilization contacts: Goblin and Gnumu anchors are local, provider-native, and disposition-unresolved")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
