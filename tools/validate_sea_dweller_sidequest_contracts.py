#!/usr/bin/env python3
"""Guard source-backed Sea Dweller barter sidequest content."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST_PATH = "campaign/civilizations/sea_dwellers/aquamarine_barter.json"
QUEST = DEFINITIONS / f"quests/{QUEST_PATH}"
INDEX = DEFINITIONS / "index.json"
INTEGRATION = ROOT / "docs/SEA_DWELLERS_BARTER_INTEGRATION.md"
PROTOCOL = ROOT / "docs/SEA_DWELLER_BARTER_SIDEQUEST_TEST_PROTOCOL.md"
CONTACT_FACT = "overlord_reign:civilizations/sea_dwellers/contact_established"


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


def collect_errors() -> list[str]:
    errors: list[str] = []
    for path in (INTEGRATION, PROTOCOL):
        if not path.exists():
            errors.append(f"{path.relative_to(ROOT)}: required Sea Dweller barter evidence is missing")

    quest = load(QUEST, errors)
    index = load(INDEX, errors)
    if QUEST_PATH not in index.get("quests", []):
        errors.append("bundled index must include the Sea Dweller aquamarine-barter sidequest")

    if quest.get("include_in_main") is not False or quest.get("rewards") != []:
        errors.append("Sea Dweller barter must remain a reward-neutral sidequest")

    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append("Sea Dweller barter must remain provider-bound")
    else:
        if provider.get("entity_type_tags") != ["seadwellers:mermorphs"]:
            errors.append("Sea Dweller barter must use the source-owned Mermorph entity tag")
        if "entity_types" in provider:
            errors.append("Sea Dweller barter must not arbitrarily pin trade to one Mermorph subtype")
        if provider.get("scoreboard_tags") != ["overlord_anchor:sea_dweller_main_trader"]:
            errors.append("Sea Dweller barter must remain scoped to the designated principal-village trader")
        if provider.get("civilization") != "overlord_reign:sea_dwellers":
            errors.append("Sea Dweller barter civilization identity changed unexpectedly")
        if provider.get("required_facts") != [CONTACT_FACT]:
            errors.append("Sea Dweller barter must remain gated by formal Sea Village contact")
        if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
            errors.append("Sea Dweller barter must retain same-provider turn-in")
        if "required_dispositions" in provider or "location" in provider:
            errors.append("Sea Dweller barter must not invent political or coordinate gates")

    objectives = quest.get("objectives")
    if not isinstance(objectives, list) or len(objectives) != 1:
        errors.append("Sea Dweller barter must have exactly one native advancement objective")
    else:
        objective = objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:advancement"
            and objective.get("advancement") == "seadwellers:adv_barter_aquamarine"
            and objective.get("required_amount") == 1
        ):
            errors.append("Sea Dweller barter must track exact native aquamarine barter advancement")

    prerequisites = quest.get("prerequisites")
    if not isinstance(prerequisites, list) or len(prerequisites) != 1 or not isinstance(prerequisites[0], dict):
        errors.append("Sea Dweller barter must retain one formal-contact prerequisite")
    else:
        p = prerequisites[0]
        if p.get("type") != "questlog:fact" or p.get("fact") != CONTACT_FACT or p.get("required_amount") != 1:
            errors.append("Sea Dweller barter prerequisite must be the formal-contact fact")

    serialized = json.dumps(quest, sort_keys=True).lower()
    if "adv_barter_nautilus" in serialized:
        errors.append("Sea Dweller sidequest must not use the unaudited/broken nautilus barter advancement")
    for forbidden in ("questlog:set_fact", "questlog:set_disposition", "dragon"):
        if forbidden in serialized:
            errors.append(f"Sea Dweller barter must not introduce forbidden state/assumption: {forbidden}")

    if INTEGRATION.exists():
        text = INTEGRATION.read_text(encoding="utf-8")
        for fragment in (
            "6cf9dd9c5ba8dfb9644b0598b1b3453fb3b4cefc1ffa3ae676e4be82309d90f5",
            "seadwellers:adv_barter_aquamarine",
            "Do an aquamarine barter with mermorph",
            "MermorphBarterProcedure",
            "seadwellers:adv_barter_nautilus",
            "no code reference",
        ):
            if fragment not in text:
                errors.append(f"Sea Dweller barter integration audit is missing evidence: {fragment}")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Sea Dweller sidequest contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1
    print("Sea Dweller aquamarine-barter production contract: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
