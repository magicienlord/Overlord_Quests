#!/usr/bin/env python3
"""Validate final quest-assignment reconciliation against the reviewed lore ledger."""
from __future__ import annotations

import json
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
INDEX = DEFINITIONS / "index.json"
QUEST_ROOT = DEFINITIONS / "quests"
RECONCILIATION = ROOT / "docs/FINAL_ASSIGNMENT_RECONCILIATION.md"
AUTHORITY_HEAD = "235845c4985b61cba9c106b2f4c4af8894bb98ae"

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
    "villagers",
    "illagers",
    "dwarves",
    "gnumus",
    "goblins",
    "kobolds",
    "ribbits",
    "sea_dwellers",
    "piglins",
    "umvuthana",
}

SPECIAL_PREFIXES = {
    "Quaver's Tower Band": "campaign/personnel/quaver/",
    "Pet Cemetery": "campaign/sidequests/pet_cemetery/",
    "NightWalker / Lestat": "campaign/sidequests/nightwalker/",
    "Overlord Depths / Fathoms": "campaign/sidequests/fathoms/",
    "Central End campaign": "campaign/end/",
}

MINION_RECOVERY = {
    "campaign/opening/restore_browns.json",
    "campaign/expansion/restore_reds.json",
    "campaign/expansion/restore_greens.json",
    "campaign/expansion/restore_blues.json",
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
}

ALLOWED_TOWER = CORE_TOWER | MAGIC_TOWER | {
    "campaign/tower/forge_prepared_reaction.json",
    "campaign/tower/restoration_complete.json",
}

ALLOWED_PREFIXES = [
    "campaign/opening/",
    "campaign/expansion/",
    "campaign/end/",
    "campaign/personnel/quaver/",
    "campaign/sidequests/pet_cemetery/",
    "campaign/sidequests/nightwalker/",
    "campaign/sidequests/fathoms/",
] + list(ADVENTURE_PREFIXES.values()) + list(MAGIC_PREFIXES.values()) + [
    f"campaign/civilizations/{name}/" for name in sorted(CIVILIZATIONS)
]


def require(condition: bool, message: str, errors: list[str]) -> None:
    if not condition:
        errors.append(message)


def load_json(path: Path, errors: list[str]) -> dict:
    try:
        data = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: cannot load JSON: {exc}")
        return {}
    if not isinstance(data, dict):
        errors.append(f"{path.relative_to(ROOT)}: JSON root must be an object")
        return {}
    return data


def collect_errors() -> list[str]:
    errors: list[str] = []
    index = load_json(INDEX, errors)
    raw_quests = index.get("quests", [])
    require(isinstance(raw_quests, list), "production manifest quests must be a list", errors)
    quests = {q for q in raw_quests if isinstance(q, str)}
    if isinstance(raw_quests, list):
        require(len(quests) == len(raw_quests), "production manifest quest paths must be unique strings", errors)

    for label, prefix in {**ADVENTURE_PREFIXES, **MAGIC_PREFIXES, **SPECIAL_PREFIXES}.items():
        require(any(q.startswith(prefix) for q in quests), f"missing mandatory authored assignment: {label}", errors)

    for civilization in sorted(CIVILIZATIONS):
        prefix = f"campaign/civilizations/{civilization}/"
        require(any(q.startswith(prefix) for q in quests), f"missing civilization entry coverage: {civilization}", errors)

    civilization_root = "campaign/civilizations/"
    for quest in sorted(q for q in quests if q.startswith(civilization_root)):
        tail = quest[len(civilization_root):]
        civilization = tail.split("/", 1)[0]
        require(civilization in CIVILIZATIONS, f"unauthorized generalized civilization branch in manifest: {quest}", errors)

    for quest in sorted(quests):
        if quest.startswith("campaign/tower/"):
            require(quest in ALLOWED_TOWER, f"Tower quest is not reconciled to the reviewed Tower authority: {quest}", errors)
        else:
            require(any(quest.startswith(prefix) for prefix in ALLOWED_PREFIXES),
                    f"production quest namespace is not reconciled to the reviewed assignment ledger: {quest}", errors)

    for quest in sorted(MINION_RECOVERY | CORE_TOWER | MAGIC_TOWER):
        require(quest in quests, f"required absorbed/Tower assignment missing from manifest: {quest}", errors)

    require(not any(q.startswith("campaign/civilizations/demons/") for q in quests),
            "Demons must remain outside the generalized civilization quest branch", errors)
    require(not any(q.startswith("campaign/tower/magic/biomancy") for q in quests),
            "Biomancy must not acquire a Tower room without later explicit Tower authority", errors)

    completion = load_json(QUEST_ROOT / "campaign/tower/restoration_complete.json", errors)
    prereqs = completion.get("prerequisites", [])
    actual = {
        p.get("quest")
        for p in prereqs
        if isinstance(p, dict)
        and p.get("type") == "questlog:quest_complete"
        and p.get("required_amount") == 1
    } if isinstance(prereqs, list) else set()
    expected = {"questlog:" + path[:-5] for path in CORE_TOWER}
    require(actual == expected,
            f"formal Tower completion must require exactly the seven core operational milestones; missing={sorted(expected - actual)}, extra={sorted(actual - expected)}",
            errors)

    doc = RECONCILIATION.read_text(encoding="utf-8") if RECONCILIATION.is_file() else ""
    for token in (
        "STATIC REPOSITORY CONTENT COMPLETE",
        AUTHORITY_HEAD,
        "reference/32_REIGN_QUESTLINE_COVERAGE_LEDGER.md",
        "reference/34_REIGN_TOWER_RESTORATION_DECISIONS.md",
        "reference/36_REIGN_MOD_QUESTLINE_ASSIGNMENTS_FINAL.md",
        "reference/37_REIGN_PERSONAL_MOD_SIDEQUEST_DECISIONS.md",
        "10/10",
    ):
        require(token in doc, f"final assignment reconciliation document missing: {token}", errors)

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Final assignment reconciliation FAILED with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1
    print("Final assignment reconciliation: PASS")
    print("mandatory authored arcs: present; generalized civilizations: 10/10")
    print("formal Tower completion: seven core operational milestones")
    print("ambient, absorbed, popup-only and no-treatment mods: no unauthorized dedicated branch")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
