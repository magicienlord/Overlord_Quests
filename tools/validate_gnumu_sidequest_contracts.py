#!/usr/bin/env python3
"""Guard the source-backed Gnumu merchant sidequest."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST_PATH = "campaign/civilizations/gnumus/merchant_business.json"
QUEST = DEFINITIONS / f"quests/{QUEST_PATH}"
INDEX = DEFINITIONS / "index.json"
INTEGRATION = ROOT / "docs/GNUMUS_MERCHANT_INTEGRATION.md"
PROTOCOL = ROOT / "docs/GNUMU_MERCHANT_SIDEQUEST_TEST_PROTOCOL.md"
CONTACT_FACT = "overlord_reign:civilizations/gnumus/contact_established"


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
            errors.append(f"{path.relative_to(ROOT)}: required Gnumu merchant evidence is missing")

    quest = load(QUEST, errors)
    index = load(INDEX, errors)
    if QUEST_PATH not in index.get("quests", []):
        errors.append("bundled index must include the Gnumu merchant sidequest")

    if quest.get("include_in_main") is not False or quest.get("rewards") != []:
        errors.append("Gnumu merchant work must remain a reward-neutral sidequest")

    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append("Gnumu merchant sidequest must remain provider-bound")
    else:
        if provider.get("entity_types") != ["gnumus:gnumus_merchant"]:
            errors.append("Gnumu merchant sidequest must target the exact native merchant entity")
        if provider.get("scoreboard_tags") != ["overlord_anchor:gnumu_main_merchant"]:
            errors.append("Gnumu merchant sidequest must remain scoped to the principal-settlement merchant")
        if provider.get("civilization") != "overlord_reign:gnumus":
            errors.append("Gnumu merchant civilization identity changed unexpectedly")
        if provider.get("required_facts") != [CONTACT_FACT]:
            errors.append("Gnumu merchant sidequest must remain gated by formal Gnumu contact")
        if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
            errors.append("Gnumu merchant sidequest must retain same-provider turn-in")
        if "required_dispositions" in provider or "location" in provider:
            errors.append("Gnumu merchant sidequest must not invent political or coordinate gates")

    objectives = quest.get("objectives")
    if not isinstance(objectives, list) or len(objectives) != 1:
        errors.append("Gnumu merchant sidequest must have exactly one native advancement objective")
    else:
        objective = objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:advancement"
            and objective.get("advancement") == "gnumus:business_approach"
            and objective.get("required_amount") == 1
        ):
            errors.append("Gnumu merchant sidequest must track exact native business_approach advancement")

    prerequisites = quest.get("prerequisites")
    if not isinstance(prerequisites, list) or len(prerequisites) != 1 or not isinstance(prerequisites[0], dict):
        errors.append("Gnumu merchant sidequest must retain one formal-contact prerequisite")
    else:
        p = prerequisites[0]
        if p.get("type") != "questlog:fact" or p.get("fact") != CONTACT_FACT or p.get("required_amount") != 1:
            errors.append("Gnumu merchant prerequisite must be the formal-contact fact")

    serialized = json.dumps(quest, sort_keys=True).lower()
    for forbidden in ("questlog:set_fact", "questlog:set_disposition", "halfling", "ancestry"):
        if forbidden in serialized:
            errors.append(f"Gnumu merchant sidequest must not introduce forbidden state/lore: {forbidden}")

    if INTEGRATION.exists():
        text = INTEGRATION.read_text(encoding="utf-8")
        for fragment in (
            "e805b1fcf9c173ae7d488763d67cef7a49d658884da95f9268e87caed7a905cf",
            "gnumus:gnumus_merchant",
            "gnumus:business_approach",
            "Trade with Gnumus Merchant using Gnumus Doubloons or Pile of Gnumus Doubloons",
            "GnumusMerchantClicProcedure",
        ):
            if fragment not in text:
                errors.append(f"Gnumu merchant integration audit is missing evidence: {fragment}")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Gnumu sidequest contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1
    print("Gnumu merchant production sidequest contract: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
