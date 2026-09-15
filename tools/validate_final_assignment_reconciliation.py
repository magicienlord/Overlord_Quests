#!/usr/bin/env python3
"""Validate source-authority reconciliation after implementation closure."""
from __future__ import annotations

import json
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
INDEX = DEFINITIONS / "index.json"
QUEST_ROOT = DEFINITIONS / "quests"
RECONCILIATION = ROOT / "docs/FINAL_ASSIGNMENT_RECONCILIATION.md"
DEBT = ROOT / "docs/RECONCILIATION_DEBT.md"
GNARL_VALIDATOR = ROOT / "tools/validate_gnarl_commentary_contract.py"
AUTHORITY_HEAD = "649bae2fe49f9da210bcf6d7400316f3e4413464"

ADVENTURE_PREFIXES = {
    "Twilight Forest": "campaign/adventures/twilight/",
    "The Bumblezone": "campaign/adventures/bumblezone/",
    "L_Ender's Cataclysm": "campaign/adventures/cataclysm/",
    "The Graveyard": "campaign/adventures/graveyard/",
    "Knight Quest": "campaign/adventures/knight/",
    "The Lost Castle": "campaign/adventures/lost_castle/",
    "Rats": "campaign/adventures/rats/",
    "Church of Sin": "campaign/adventures/church_of_sin/",
    "Oddities": "campaign/adventures/oddities/",
}
MAGIC_PREFIXES = {
    "Iron's Spells 'n Spellbooks": "campaign/magic/irons/",
    "Farmer's Spell / Gluttony": "campaign/magic/gluttony/",
    "Theurgy": "campaign/magic/theurgy/",
    "Ars Elixirum": "campaign/magic/alchemy/",
    "Biomancy": "campaign/magic/biomancy/",
    "Eidolon: Repraised": "campaign/magic/eidolon/",
}
CIVILIZATIONS = {
    "villagers", "illagers", "dwarves", "gnumus", "goblins",
    "kobolds", "ribbits", "sea_dwellers", "piglins", "umvuthana",
}
SPECIAL_PREFIXES = {
    "Quaver's Tower Band": "campaign/personnel/quaver/",
    "Pet Cemetery": "campaign/sidequests/pet_cemetery/",
    "NightWalker / Lestat": "campaign/sidequests/nightwalker/",
    "Overlord Depths / Fathoms": "campaign/sidequests/fathoms/",
    "Central End campaign": "campaign/end/",
}
CORE_TOWER = {
    "campaign/tower/claim_the_throne.json",
    "campaign/tower/prepare_the_forge.json",
    "campaign/tower/wake_minion_infrastructure.json",
    "campaign/tower/open_gates_room.json",
    "campaign/tower/secure_treasury.json",
    "campaign/tower/provision_storage_room.json",
    "campaign/tower/establish_armory.json",
}
MAGIC_TOWER = {
    "campaign/tower/magic/open_alchemy_laboratory.json",
    "campaign/tower/magic/establish_theurgy_laboratory.json",
    "campaign/tower/magic/open_gluttony_kitchen.json",
    "campaign/tower/magic/establish_spell_study.json",
    "campaign/tower/magic/prepare_eidolon_chamber.json",
    "campaign/tower/magic/prepare_biomancy_chamber.json",
}
ALLOWED_TOWER = CORE_TOWER | MAGIC_TOWER | {
    "campaign/tower/forge_prepared_reaction.json",
    "campaign/tower/restoration_complete.json",
}
ALLOWED_PREFIXES = [
    "campaign/opening/", "campaign/expansion/", "campaign/end/",
    "campaign/personnel/quaver/", "campaign/sidequests/pet_cemetery/",
    "campaign/sidequests/nightwalker/", "campaign/sidequests/fathoms/",
] + list(ADVENTURE_PREFIXES.values()) + list(MAGIC_PREFIXES.values()) + [
    f"campaign/civilizations/{name}/" for name in sorted(CIVILIZATIONS)
]


def require(condition: bool, message: str, errors: list[str]) -> None:
    if not condition:
        errors.append(message)


def load_json(path: Path, errors: list[str]) -> dict:
    try:
        value = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: cannot load JSON: {exc}")
        return {}
    if not isinstance(value, dict):
        errors.append(f"{path.relative_to(ROOT)}: JSON root must be an object")
        return {}
    return value


def collect_errors() -> list[str]:
    errors: list[str] = []
    index = load_json(INDEX, errors)
    raw = index.get("quests", [])
    require(isinstance(raw, list), "production manifest quests must be a list", errors)
    quests = {entry for entry in raw if isinstance(entry, str)} if isinstance(raw, list) else set()
    if isinstance(raw, list):
        require(len(quests) == len(raw), "production manifest quest paths must be unique strings", errors)

    for label, prefix in {**ADVENTURE_PREFIXES, **MAGIC_PREFIXES, **SPECIAL_PREFIXES}.items():
        require(any(path.startswith(prefix) for path in quests), f"missing mandatory authored assignment: {label}", errors)
    for civilization in sorted(CIVILIZATIONS):
        prefix = f"campaign/civilizations/{civilization}/"
        require(any(path.startswith(prefix) for path in quests), f"missing civilization entry coverage: {civilization}", errors)
    for path in sorted(quests):
        if path.startswith("campaign/tower/"):
            require(path in ALLOWED_TOWER, f"Tower quest is not reconciled to current authority: {path}", errors)
        else:
            require(any(path.startswith(prefix) for prefix in ALLOWED_PREFIXES),
                    f"production quest namespace is not reconciled to the assignment ledger: {path}", errors)

    require("campaign/tower/magic/prepare_biomancy_chamber.json" in quests,
            "authority-required Biomancy Tower activation is missing", errors)
    require(not any(path.startswith("campaign/civilizations/demons/") for path in quests),
            "Demons must remain outside the generalized civilization branch", errors)

    completion = load_json(QUEST_ROOT / "campaign/tower/restoration_complete.json", errors)
    actual = {
        entry.get("quest") for entry in completion.get("prerequisites", [])
        if isinstance(entry, dict)
        and entry.get("type") == "questlog:quest_complete"
        and entry.get("required_amount") == 1
    } if isinstance(completion.get("prerequisites", []), list) else set()
    expected = {"questlog:" + path[:-5] for path in CORE_TOWER}
    require(actual == expected,
            f"formal Tower completion must require exactly seven core milestones; missing={sorted(expected - actual)}, extra={sorted(actual - expected)}",
            errors)

    nightwalker_paths = [
        "campaign/sidequests/nightwalker/lestat_arrives.json",
        "campaign/sidequests/nightwalker/hunger_is_a_fact.json",
        "campaign/sidequests/nightwalker/the_vampire_altar.json",
        "campaign/sidequests/nightwalker/choose_the_price.json",
    ]
    for rel in nightwalker_paths:
        quest = load_json(QUEST_ROOT / rel, errors)
        require("provider" not in quest, f"{rel}: contextual Lestat content must not require a physical provider", errors)
        require("overlord_anchor:lestat" not in json.dumps(quest), f"{rel}: stale Lestat anchor remains", errors)

    require(GNARL_VALIDATOR.is_file(), "generalized Gnarl commentary validation contract is missing", errors)

    final_doc = RECONCILIATION.read_text(encoding="utf-8") if RECONCILIATION.is_file() else ""
    for token in (
        "IMPLEMENTATION CLOSED - FULL-INSTANCE QUALIFICATION PENDING",
        AUTHORITY_HEAD,
        "Gnarl's Ramblings",
        "Biomancy",
        "10/10",
        "contextual Lestat",
    ):
        require(token in final_doc, f"final assignment reconciliation document missing: {token}", errors)
    require("REOPENED - SOURCE AUTHORITY RECONCILIATION IN PROGRESS" not in final_doc,
            "final reconciliation still claims source implementation is reopened", errors)

    debt = DEBT.read_text(encoding="utf-8") if DEBT.is_file() else ""
    for token in (
        "SOURCE RECONCILIATION CLOSED - FULL-INSTANCE QUALIFICATION PENDING",
        AUTHORITY_HEAD,
        "Gnarl's Ramblings: CLOSED",
        "Biomancy Tower activation: CLOSED",
        "Lestat physical-placement assumption: CLOSED",
        "Minion Hive-proxy assumption: CLOSED",
    ):
        require(token in debt, f"reconciliation debt ledger missing closure token: {token}", errors)
    require("Status: OPEN - BLOCKS CONTENT-COMPLETE CLAIM" not in debt,
            "old open source-reconciliation status remains active", errors)

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Final assignment reconciliation FAILED with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1
    print("Final assignment reconciliation guard: PASS")
    print("source implementation debt: closed against pinned Lore authority")
    print("release qualification: full assembled-instance/manual validation remains separately pending")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
