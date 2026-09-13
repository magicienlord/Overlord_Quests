#!/usr/bin/env python3
"""Validate the four mandatory native adventure questline wrappers."""
from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST_ROOT = DEFS / "quests/campaign/adventures"
INDEX = DEFS / "index.json"
FOUNDATION = "questlog:campaign/expansion/the_reign_takes_shape"

ARCS = {
    "twilight": {
        "files": ["old_forest_new_master.json", "maze_and_fire.json", "towers_and_ice.json", "forest_without_barriers.json"],
        "advancements": {
            "twilightforest:progress_naga", "twilightforest:progress_lich", "twilightforest:progress_labyrinth", "twilightforest:progress_hydra",
            "twilightforest:progress_knights", "twilightforest:progress_ur_ghast", "twilightforest:progress_yeti", "twilightforest:progress_glacier",
            "twilightforest:progress_troll", "twilightforest:progress_merge", "twilightforest:progress_trophy_pedestal", "twilightforest:progression_end",
        },
        "fact": "overlord_reign:adventure/twilight_forest_progression_completed",
    },
    "cataclysm": {
        "files": ["ruins_that_wake.json", "break_the_great_ones.json", "cataclysm_conquered.json"],
        "advancements": {
            "cataclysm:find_cursed_pyramid", "cataclysm:find_sunken_city", "cataclysm:find_ruined_citadel", "cataclysm:find_burning_arena",
            "cataclysm:kill_ignis", "cataclysm:kill_leviathan", "cataclysm:kill_maledictus", "cataclysm:kill_all_bosses",
        },
        "fact": "overlord_reign:adventure/cataclysm_capstone_completed",
    },
    "graveyard": {
        "files": ["places_the_dead_keep.json", "the_lich_question.json", "not_one_death.json"],
        "advancements": {
            "graveyard:graveyard/large_graveyard", "graveyard:graveyard/crypt", "graveyard:graveyard/haunted_house",
            "graveyard:graveyard/summon_lich", "graveyard:graveyard/lich_prison", "graveyard:graveyard/kill_wraith", "graveyard:graveyard/craft_coffin",
        },
        "fact": "overlord_reign:adventure/graveyard_expedition_completed",
    },
    "bumblezone": {
        "files": ["enter_the_hive.json", "the_queens_domain.json", "essence_of_the_hive.json"],
        "advancements": {
            "the_bumblezone:root", "the_bumblezone:beehemoth/tamed_beehemoth", "the_bumblezone:beehemoth/queen_beehemoth", "the_bumblezone:essence/bee_essence_infusion",
        },
        "fact": "overlord_reign:adventure/bumblezone_essence_reached",
    },
}


def load(path: Path, errors: list[str]) -> dict[str, Any]:
    try:
        value = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: {exc}")
        return {}
    if not isinstance(value, dict):
        errors.append(f"{path.relative_to(ROOT)}: root must be an object")
        return {}
    return value


def walk(value: Any):
    if isinstance(value, dict):
        if "type" in value:
            yield value
        for child in value.values():
            yield from walk(child)
    elif isinstance(value, list):
        for child in value:
            yield from walk(child)


def has_fact(data: dict[str, Any], fact: str) -> bool:
    rewards = data.get("rewards", [])
    return any(isinstance(r, dict) and r.get("type") == "questlog:set_fact" and r.get("fact") == fact and r.get("auto_claim") is True for r in rewards if isinstance(rewards, list))


def main() -> int:
    errors: list[str] = []
    index = load(INDEX, errors)
    indexed = set(index.get("quests", [])) if isinstance(index.get("quests", []), list) else set()

    for arc, contract in ARCS.items():
        docs = []
        for filename in contract["files"]:
            rel = f"campaign/adventures/{arc}/{filename}"
            if rel not in indexed:
                errors.append(f"{rel}: not indexed")
            docs.append(load(QUEST_ROOT / arc / filename, errors))

        first_prereqs = docs[0].get("prerequisites", []) if docs else []
        if not any(isinstance(p, dict) and p.get("type") == "questlog:quest_complete" and p.get("quest") == FOUNDATION for p in first_prereqs if isinstance(first_prereqs, list)):
            errors.append(f"{arc}: first quest must branch from {FOUNDATION}")

        for i in range(1, len(docs)):
            expected = f"questlog:campaign/adventures/{arc}/{contract['files'][i-1][:-5]}"
            prereqs = docs[i].get("prerequisites", [])
            if not any(isinstance(p, dict) and p.get("type") == "questlog:quest_complete" and p.get("quest") == expected for p in prereqs if isinstance(prereqs, list)):
                errors.append(f"{arc}/{contract['files'][i]}: must chain from {expected}")

        actual = {o.get("advancement") for doc in docs for o in walk(doc.get("objectives", [])) if o.get("type") == "questlog:advancement"}
        if actual != contract["advancements"]:
            errors.append(f"{arc}: advancement set mismatch; expected={sorted(contract['advancements'])}, actual={sorted(x for x in actual if x)}")
        if not docs or not has_fact(docs[-1], contract["fact"]):
            errors.append(f"{arc}: final quest must auto-claim {contract['fact']}")

    cataclysm = load(QUEST_ROOT / "cataclysm/cataclysm_conquered.json", errors)
    cat_adv = {o.get("advancement") for o in walk(cataclysm.get("objectives", [])) if o.get("type") == "questlog:advancement"}
    if cat_adv != {"cataclysm:kill_all_bosses"}:
        errors.append("Cataclysm capstone must delegate exhaustive boss completion to cataclysm:kill_all_bosses")

    graveyard_text = "\n".join((QUEST_ROOT / "graveyard" / f).read_text(encoding="utf-8") for f in ARCS["graveyard"]["files"])
    required_distinctions = ["souls", "Lifeforce", "resurrection"]
    for word in required_distinctions:
        if word not in graveyard_text:
            errors.append(f"Graveyard framing must preserve the REIGN death-system distinction including {word!r}")

    bumble_text = "\n".join((QUEST_ROOT / "bumblezone" / f).read_text(encoding="utf-8") for f in ARCS["bumblezone"]["files"])
    if "the_bumblezone:the_bumblezone/" in bumble_text:
        errors.append("Bumblezone wrapper must not use the obsolete duplicated namespace path")

    if errors:
        print("Adventure questline contract FAILED:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1
    print("Adventure questline contract OK: four full REIGN wrappers preserve native progression ownership.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
