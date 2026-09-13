#!/usr/bin/env python3
"""Validate production coverage and ownership for the six core magic arcs."""
from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST_ROOT = DEFS / "quests"
INDEX = DEFS / "index.json"

ARCS = {
    "irons": {
        "files": ["open_the_spellbook.json", "shape_the_spell.json", "master_the_ink.json"],
        "start": "questlog:campaign/tower/magic/establish_spell_study",
        "advancements": {
            "irons_spellbooks:irons_spellbooks/spell_book_equip",
            "irons_spellbooks:irons_spellbooks/make_scroll_forge",
            "irons_spellbooks:irons_spellbooks/make_arcane_anvil",
            "irons_spellbooks:irons_spellbooks/ink_legendary",
        },
        "fact": "overlord_reign:magic/irons/spellcraft_established",
    },
    "gluttony": {
        "files": ["feed_the_spell.json", "food_shaman.json", "banquet_of_power.json"],
        "start": "questlog:campaign/tower/magic/open_gluttony_kitchen",
        "advancements": {"farmers_spell:amethyst_beetroot", "farmers_spell:arcane_cocoa", "farmers_spell:food_shaman", "farmers_spell:chef_ratatouille"},
        "fact": "overlord_reign:magic/gluttony/mastery_established",
    },
    "theurgy": {
        "files": ["first_rod.json", "refine_the_rod.json", "precious_matter.json"],
        "start": "questlog:campaign/tower/magic/establish_theurgy_laboratory",
        "advancements": {"theurgy:has_basic_rod", "theurgy:has_amethyst_rod", "theurgy:has_t2_rod", "theurgy:has_t3_rod", "theurgy:has_t4_rod", "theurgy:has_precious_rod"},
        "fact": "overlord_reign:magic/theurgy/mastery_established",
    },
    "alchemy": {
        "files": ["beyond_the_first_dose.json", "deepen_the_codex.json", "pharmacologist.json"],
        "start": "questlog:campaign/tower/magic/open_alchemy_laboratory",
        "mastery": [5, 20, 50],
        "fact": "overlord_reign:magic/alchemy/pharmacology_established",
    },
    "biomancy": {
        "files": ["a_lesson_from_the_silence.json", "flesh_as_machinery.json", "the_living_laboratory.json"],
        "start": "questlog:campaign/expansion/the_reign_takes_shape",
        "advancements": {
            "biomancy:biomancy/craft_primal_core", "biomancy:biomancy/living_flesh", "biomancy:biomancy/bio_forge",
            "biomancy:biomancy/decomposer", "biomancy:biomancy/digester", "biomancy:biomancy/bio_lab",
            "biomancy:biomancy/bio_injector", "biomancy:biomancy/cradle",
        },
        "fact": "overlord_reign:magic/biomancy/discipline_established",
    },
    "eidolon": {
        "files": ["raise_the_altar.json", "bind_the_unseen.json", "choose_a_rite.json"],
        "start": "questlog:campaign/tower/magic/prepare_eidolon_chamber",
        "advancements": {"eidolon:wooden_altar", "eidolon:stone_altar", "eidolon:research_notes", "eidolon:soul_shard", "eidolon:sacred_path", "eidolon:wicked_path"},
        "fact": "overlord_reign:magic/eidolon/ritual_path_established",
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


def walk_objectives(value: Any):
    if isinstance(value, dict):
        if "type" in value:
            yield value
        for child in value.values():
            yield from walk_objectives(child)
    elif isinstance(value, list):
        for child in value:
            yield from walk_objectives(child)


def auto_fact(data: dict[str, Any], fact: str) -> bool:
    rewards = data.get("rewards", [])
    return any(isinstance(r, dict) and r.get("type") == "questlog:set_fact" and r.get("fact") == fact and r.get("auto_claim") is True for r in rewards if isinstance(rewards, list))


def collect_errors() -> list[str]:
    errors: list[str] = []
    index = load(INDEX, errors)
    indexed = set(index.get("quests", [])) if isinstance(index.get("quests", []), list) else set()

    for arc, contract in ARCS.items():
        folder = QUEST_ROOT / "campaign/magic" / arc
        docs = []
        for filename in contract["files"]:
            rel = f"campaign/magic/{arc}/{filename}"
            if rel not in indexed:
                errors.append(f"{rel}: production quest is not indexed")
            docs.append(load(folder / filename, errors))

        first_prereqs = docs[0].get("prerequisites", []) if docs else []
        if not any(isinstance(p, dict) and p.get("type") == "questlog:quest_complete" and p.get("quest") == contract["start"] for p in first_prereqs if isinstance(first_prereqs, list)):
            errors.append(f"{arc}: first quest must start from {contract['start']}")

        for idx in range(1, len(docs)):
            expected = f"questlog:campaign/magic/{arc}/{contract['files'][idx - 1][:-5]}"
            prereqs = docs[idx].get("prerequisites", [])
            if not any(isinstance(p, dict) and p.get("type") == "questlog:quest_complete" and p.get("quest") == expected for p in prereqs if isinstance(prereqs, list)):
                errors.append(f"{arc}/{contract['files'][idx]}: must chain from {expected}")

        all_objectives = [o for doc in docs for o in walk_objectives(doc.get("objectives", []))]
        if "advancements" in contract:
            actual = {o.get("advancement") for o in all_objectives if o.get("type") == "questlog:advancement"}
            if actual != contract["advancements"]:
                errors.append(f"{arc}: native advancement set mismatch; expected={sorted(contract['advancements'])}, actual={sorted(x for x in actual if x)}")
        else:
            actual_mastery = [o.get("required_amount") for o in all_objectives if o.get("type") == "questlog:elixirum_mastery"]
            if actual_mastery != contract["mastery"]:
                errors.append(f"{arc}: Ars Elixirum mastery thresholds must remain {contract['mastery']}, got {actual_mastery}")

        if not docs or not auto_fact(docs[-1], contract["fact"]):
            errors.append(f"{arc}: final quest must auto-claim {contract['fact']}")

    biomancy_text = "\n".join((QUEST_ROOT / "campaign/magic/biomancy" / f).read_text(encoding="utf-8") for f in ARCS["biomancy"]["files"])
    if "tower/magic/biomancy" in biomancy_text or "tower/biomancy" in biomancy_text:
        errors.append("Biomancy dedicated progression must remain independent of Tower-room ownership")

    eidolon_final = load(QUEST_ROOT / "campaign/magic/eidolon/choose_a_rite.json", errors)
    top = eidolon_final.get("objectives", [])
    if len(top) != 1 or not isinstance(top[0], dict) or top[0].get("type") != "questlog:or":
        errors.append("Eidolon final path milestone must accept either native ritual path through questlog:or")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print("Core magic questline contract FAILED:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1
    print("Core magic questline contract OK: six dedicated REIGN arcs preserve native progression and domain ownership.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
