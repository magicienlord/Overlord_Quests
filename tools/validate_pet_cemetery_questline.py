#!/usr/bin/env python3
"""Validate the conditional Pet Cemetery resurrection sidequest and its trigger objective."""
from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUESTS = DEFS / "quests"
INDEX = DEFS / "index.json"
REGISTRY = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/QuestObjectiveRegistry.java"
OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/entity/OwnedTameDeathObjective.java"
WORKFLOW = ROOT / ".github/workflows/adventure-questline-contract.yml"

OPENING = "campaign/sidequests/pet_cemetery/a_collar_left_behind.json"
RETURN = "campaign/sidequests/pet_cemetery/return_from_the_grave.json"


def load(path: Path, errors: list[str]) -> dict[str, Any]:
    try:
        value = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: unreadable JSON: {exc}")
        return {}
    if not isinstance(value, dict):
        errors.append(f"{path.relative_to(ROOT)}: root must be an object")
        return {}
    return value


def entries(data: dict[str, Any], field: str, type_id: str) -> list[dict[str, Any]]:
    value = data.get(field, [])
    if not isinstance(value, list):
        return []
    return [entry for entry in value if isinstance(entry, dict) and entry.get("type") == type_id]


def main() -> int:
    errors: list[str] = []
    index = load(INDEX, errors)
    opening = load(QUESTS / OPENING, errors)
    returning = load(QUESTS / RETURN, errors)

    indexed = set(index.get("quests", [])) if isinstance(index.get("quests", []), list) else set()
    for rel in (OPENING, RETURN):
        if rel not in indexed:
            errors.append(f"{rel}: production quest is not indexed")

    death = entries(opening, "prerequisites", "overlord_reign:owned_tame_death")
    if len(death) != 1:
        errors.append("Pet Cemetery opening must have exactly one owned-tame-death trigger")
    elif death[0].get("entity") != "#pet_cemetery:drops_pet_collar":
        errors.append("Pet Cemetery death trigger must be restricted to the mod's drops_pet_collar entity tag")

    if opening.get("show_popup_on_unlock") is not True:
        errors.append("Pet Cemetery opening must surface only when the death trigger unlocks it")

    collars = entries(opening, "objectives", "questlog:item_obtain")
    if len(collars) != 1 or collars[0].get("item") != "pet_cemetery:pet_collar":
        errors.append("Pet Cemetery opening must recover the native pet collar")

    prereqs = entries(returning, "prerequisites", "questlog:quest_complete")
    if len(prereqs) != 1 or prereqs[0].get("quest") != "questlog:campaign/sidequests/pet_cemetery/a_collar_left_behind":
        errors.append("Pet Cemetery resurrection quest must follow collar recovery")

    advancements = entries(returning, "objectives", "questlog:advancement")
    if len(advancements) != 1 or advancements[0].get("advancement") != "pet_cemetery:nether/respawn_pet":
        errors.append("Pet Cemetery finale must observe the native respawn_pet advancement")

    rewards = returning.get("rewards", [])
    expected_fact = any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:set_fact"
        and entry.get("fact") == "overlord_reign:personal/pet_resurrection_completed"
        and entry.get("value") is True
        and entry.get("auto_claim") is True
        for entry in rewards
    ) if isinstance(rewards, list) else False
    if not expected_fact:
        errors.append("Pet Cemetery finale must persist pet_resurrection_completed")

    objective_source = OBJECTIVE.read_text(encoding="utf-8") if OBJECTIVE.exists() else ""
    if "instanceof TamableAnimal" not in objective_source:
        errors.append("owned_tame_death must require a tameable entity")
    if "getOwnerUUID()" not in objective_source or "manager.player.getUUID()" not in objective_source:
        errors.append("owned_tame_death must bind the dead tameable to the Questlog player")
    if "this.test(tameable)" not in objective_source:
        errors.append("owned_tame_death must retain authored entity filtering")

    registry_source = REGISTRY.read_text(encoding="utf-8") if REGISTRY.exists() else ""
    if 'new ResourceLocation("overlord_reign", "owned_tame_death")' not in registry_source:
        errors.append("owned_tame_death objective is not registered under the OVERLORD REIGN extension namespace")
    if "OwnedTameDeathObjective::new" not in registry_source:
        errors.append("owned_tame_death registry entry does not construct the intended objective")

    workflow_source = WORKFLOW.read_text(encoding="utf-8") if WORKFLOW.exists() else ""
    if "python3 tools/validate_pet_cemetery_questline.py" not in workflow_source:
        errors.append("Pet Cemetery validator is not wired into CI")

    if errors:
        print("Pet Cemetery questline contract FAILED:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1

    print("Pet Cemetery questline contract OK: the sidequest stays death-gated and observes the native resurrection path.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
