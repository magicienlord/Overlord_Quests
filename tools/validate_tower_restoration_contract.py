#!/usr/bin/env python3
"""Validate the Dark Tower restoration ownership contract."""
from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST_ROOT = DEFINITIONS / "quests"
INDEX = DEFINITIONS / "index.json"
FOUNDATION = "questlog:campaign/expansion/the_reign_takes_shape"

ROOMS = {
    "campaign/tower/claim_the_throne.json": {"fact": "overlord_reign:tower/throne_room_operational", "objective": ("questlog:read", None, None)},
    "campaign/tower/wake_minion_infrastructure.json": {"fact": "overlord_reign:tower/minion_infrastructure_operational", "objective": ("questlog:read", None, None)},
    "campaign/tower/open_gates_room.json": {"fact": "overlord_reign:tower/gates_operational", "crafts": {"waystones:waystone"}},
    "campaign/tower/provision_storage_room.json": {"fact": "overlord_reign:tower/storage_room_operational", "crafts": {"minecraft:chest", "minecraft:barrel"}},
    "campaign/tower/establish_armory.json": {"fact": "overlord_reign:tower/armory_operational", "crafts": {"minecraft:armor_stand", "minecraft:item_frame"}},
    "campaign/tower/secure_treasury.json": {"fact": "overlord_reign:tower/treasury_operational", "crafts": {"minecraft:iron_door", "minecraft:trapped_chest"}},
    "campaign/tower/magic/open_alchemy_laboratory.json": {"fact": "overlord_reign:tower/alchemy_room_operational", "objective": ("questlog:elixirum_mastery", "required_amount", 1)},
    "campaign/tower/magic/establish_theurgy_laboratory.json": {"fact": "overlord_reign:tower/theurgy_room_operational", "objective": ("questlog:advancement", "advancement", "theurgy:has_liquefaction_cauldron")},
    "campaign/tower/magic/open_gluttony_kitchen.json": {"fact": "overlord_reign:tower/gluttony_room_operational", "objective": ("questlog:advancement", "advancement", "farmers_spell:alchemist_pot")},
    "campaign/tower/magic/establish_spell_study.json": {"fact": "overlord_reign:tower/spell_study_operational", "objective": ("questlog:advancement", "advancement", "irons_spellbooks:irons_spellbooks/make_inscription_table")},
    "campaign/tower/magic/prepare_eidolon_chamber.json": {"fact": "overlord_reign:tower/eidolon_room_operational", "objective": ("questlog:advancement", "advancement", "eidolon:worktable")},
    # Biomancy activation is deliberately represented by quest completion rather
    # than another permanent narrative fact because no later production content
    # currently consumes a separate room-state key.
    "campaign/tower/magic/prepare_biomancy_chamber.json": {"objective": ("questlog:advancement", "advancement", "biomancy:biomancy/bio_forge")},
}

FORGE_REL = "campaign/tower/prepare_the_forge.json"
COMPLETION_REL = "campaign/tower/restoration_complete.json"
CORE_COMPLETION_QUESTS = {
    "questlog:campaign/tower/claim_the_throne",
    "questlog:campaign/tower/prepare_the_forge",
    "questlog:campaign/tower/wake_minion_infrastructure",
    "questlog:campaign/tower/open_gates_room",
    "questlog:campaign/tower/secure_treasury",
    "questlog:campaign/tower/provision_storage_room",
    "questlog:campaign/tower/establish_armory",
}
MAGIC_BRANCHES = {
    "campaign/tower/magic/open_alchemy_laboratory.json",
    "campaign/tower/magic/establish_theurgy_laboratory.json",
    "campaign/tower/magic/open_gluttony_kitchen.json",
    "campaign/tower/magic/establish_spell_study.json",
    "campaign/tower/magic/prepare_eidolon_chamber.json",
    "campaign/tower/magic/prepare_biomancy_chamber.json",
}


def load(path: Path, errors: list[str]) -> dict[str, Any]:
    try:
        value = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: {exc}")
        return {}
    if not isinstance(value, dict):
        errors.append(f"{path.relative_to(ROOT)}: JSON root must be an object")
        return {}
    return value


def has_auto_fact(data: dict[str, Any], fact: str) -> bool:
    rewards = data.get("rewards", [])
    return isinstance(rewards, list) and any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:set_fact"
        and entry.get("fact") == fact
        and entry.get("auto_claim") is True
        for entry in rewards
    )


def collect_errors() -> list[str]:
    errors: list[str] = []
    index = load(INDEX, errors)
    indexed = set(index.get("quests", [])) if isinstance(index.get("quests", []), list) else set()

    for rel, contract in ROOMS.items():
        data = load(QUEST_ROOT / rel, errors)
        if rel not in indexed:
            errors.append(f"{rel}: production definition is not indexed")
        fact = contract.get("fact")
        if isinstance(fact, str) and not has_auto_fact(data, fact):
            errors.append(f"{rel}: missing auto-claimed narrative marker {fact}")

        objectives = data.get("objectives", [])
        if not isinstance(objectives, list):
            errors.append(f"{rel}: objectives must be a list")
            continue
        if "crafts" in contract:
            crafts = {
                entry.get("item") for entry in objectives
                if isinstance(entry, dict)
                and entry.get("type") == "questlog:item_craft_stat"
                and entry.get("required_amount") == 1
            }
            if crafts != contract["crafts"]:
                errors.append(f"{rel}: expected retrospective craft set {sorted(contract['crafts'])}, got {sorted(x for x in crafts if x)}")
        else:
            type_id, key, expected = contract["objective"]
            matches = [entry for entry in objectives if isinstance(entry, dict) and entry.get("type") == type_id]
            if len(matches) != 1:
                errors.append(f"{rel}: expected exactly one {type_id} objective")
            elif key is not None and matches[0].get(key) != expected:
                errors.append(f"{rel}: expected {key}={expected!r}")

    forge = load(QUEST_ROOT / FORGE_REL, errors)
    if FORGE_REL not in indexed:
        errors.append("Tower Forge definition is not indexed")
    if not any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:advancement"
        and entry.get("advancement") == "hot_iron:local_smithery"
        for entry in forge.get("objectives", []) if isinstance(forge.get("objectives", []), list)
    ):
        errors.append("Tower Forge must remain backed by hot_iron:local_smithery")
    if not has_auto_fact(forge, "overlord_reign:tower/forge_prepared"):
        errors.append("Tower Forge must keep overlord_reign:tower/forge_prepared")

    completion = load(QUEST_ROOT / COMPLETION_REL, errors)
    if COMPLETION_REL not in indexed:
        errors.append("Formal Tower Restoration completion definition is not indexed")
    if not has_auto_fact(completion, "overlord_reign:tower/restoration_complete"):
        errors.append("Formal Tower Restoration must keep overlord_reign:tower/restoration_complete")
    actual = {
        entry.get("quest") for entry in completion.get("prerequisites", [])
        if isinstance(entry, dict)
        and entry.get("type") == "questlog:quest_complete"
        and entry.get("required_amount") == 1
    } if isinstance(completion.get("prerequisites", []), list) else set()
    if actual != CORE_COMPLETION_QUESTS:
        errors.append(
            "Formal Tower Restoration must require exactly the seven core operational milestones; "
            f"missing={sorted(CORE_COMPLETION_QUESTS - actual)}, extra={sorted(actual - CORE_COMPLETION_QUESTS)}"
        )

    for rel in sorted(MAGIC_BRANCHES | {
        "campaign/tower/open_gates_room.json",
        "campaign/tower/provision_storage_room.json",
        "campaign/tower/establish_armory.json",
        "campaign/tower/secure_treasury.json",
    }):
        data = load(QUEST_ROOT / rel, errors)
        prereqs = data.get("prerequisites", [])
        if not any(
            isinstance(entry, dict)
            and entry.get("type") == "questlog:quest_complete"
            and entry.get("quest") == FOUNDATION
            for entry in prereqs if isinstance(prereqs, list)
        ):
            errors.append(f"{rel}: must branch from {FOUNDATION}")

    biomancy = load(QUEST_ROOT / "campaign/tower/magic/prepare_biomancy_chamber.json", errors)
    biomancy_text = json.dumps(biomancy)
    if biomancy.get("rewards") != []:
        errors.append("Biomancy Tower activation should not create an unused durable fact or absorb deeper mastery rewards")
    for forbidden in (
        "biomancy:biomancy/decomposer",
        "biomancy:biomancy/digester",
        "biomancy:biomancy/bio_lab",
        "biomancy:biomancy/bio_injector",
        "biomancy:biomancy/cradle",
    ):
        if forbidden in biomancy_text:
            errors.append(f"Biomancy Tower activation must not absorb deeper mastery milestone {forbidden}")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print("Tower restoration contract FAILED:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1
    print("Tower restoration contract OK: seven core facilities gate formal completion; six selected magical facilities remain optional Tower-owned activation branches.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
