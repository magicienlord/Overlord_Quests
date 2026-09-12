#!/usr/bin/env python3
"""Guard the source-backed first production Goblin merchant sidequest."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST_PATH = "campaign/civilizations/goblins/merchant_business.json"
QUEST = DEFINITIONS / f"quests/{QUEST_PATH}"
INDEX = DEFINITIONS / "index.json"
INTEGRATION = ROOT / "docs/GOBLINS_TYRANNY_PROVIDER_INTEGRATION.md"
PROTOCOL = ROOT / "docs/GOBLIN_MERCHANT_SIDEQUEST_TEST_PROTOCOL.md"
CONTACT_FACT = "overlord_reign:civilizations/goblins/contact_established"


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
            errors.append(f"{path.relative_to(ROOT)}: required source/runtime documentation is missing")

    quest = load(QUEST, errors)
    index = load(INDEX, errors)
    if QUEST_PATH not in index.get("quests", []):
        errors.append("bundled index must include the Goblin merchant production sidequest")

    if quest.get("include_in_main") is not False:
        errors.append("Goblin merchant work must remain sidequest content rather than a main-campaign objective")
    if quest.get("show_popup_on_unlock") is not False:
        errors.append("Goblin merchant sidequest must remain an in-world provider offer")

    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append("Goblin merchant sidequest must remain provider-bound")
    else:
        if provider.get("entity_types") != ["goblins_tyranny:merchant"]:
            errors.append("Goblin merchant sidequest must target the exact native merchant entity")
        if provider.get("scoreboard_tags") != ["overlord_anchor:goblin_main_merchant"]:
            errors.append("Goblin merchant sidequest must remain scoped to the designated principal-camp merchant")
        if provider.get("civilization") != "overlord_reign:goblins":
            errors.append("Goblin merchant sidequest civilization identity changed unexpectedly")
        if provider.get("required_facts") != [CONTACT_FACT]:
            errors.append("Goblin merchant sidequest must remain gated by formal principal-camp contact")
        if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
            errors.append("Goblin merchant sidequest must retain exact issuing-merchant turn-in")
        if "required_dispositions" in provider:
            errors.append("Goblin merchant sidequest must not require or imply a resolved Goblin disposition")
        if "location" in provider:
            errors.append("Goblin merchant sidequest must not fabricate final-world coordinates")

    prerequisites = quest.get("prerequisites")
    if not isinstance(prerequisites, list) or len(prerequisites) != 1:
        errors.append("Goblin merchant sidequest must retain exactly one contact prerequisite")
    else:
        prerequisite = prerequisites[0]
        if not isinstance(prerequisite, dict) or not (
            prerequisite.get("type") == "questlog:fact"
            and prerequisite.get("fact") == CONTACT_FACT
            and prerequisite.get("required_amount") == 1
        ):
            errors.append("Goblin merchant sidequest prerequisite must be the established-contact fact")

    objectives = quest.get("objectives")
    if not isinstance(objectives, list) or len(objectives) != 1:
        errors.append("Goblin merchant sidequest must have exactly one native progression objective")
    else:
        objective = objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:advancement"
            and objective.get("advancement") == "goblins_tyranny:merchant_success"
            and objective.get("required_amount") == 1
        ):
            errors.append("Goblin merchant sidequest must track exact native merchant_success advancement")

    if quest.get("rewards") != []:
        errors.append("Goblin merchant sidequest must not add synthetic rewards on top of native commerce")

    serialized = json.dumps(quest, sort_keys=True)
    for forbidden in ("questlog:set_fact", "questlog:set_disposition"):
        if forbidden in serialized:
            errors.append(f"Goblin merchant sidequest must not write {forbidden}")

    if INTEGRATION.exists():
        text = INTEGRATION.read_text(encoding="utf-8")
        for fragment in (
            "aa9d337c58a0bfeb0378ab205fb2c70c82a84c85a6e21e5c5c56ee025ec48174",
            "goblins_tyranny:merchant",
            "goblins_tyranny:merchant_success",
            "retrospective",
        ):
            if fragment not in text:
                errors.append(f"Goblins Tyranny integration audit is missing required evidence: {fragment}")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Goblin merchant production contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1
    print("Goblin merchant production sidequest contract: PASS")
    print("provider: designated principal-camp goblins_tyranny:merchant")
    print("objective owner: native goblins_tyranny:merchant_success advancement")
    print("persistent state: ordinary quest completion only; no disposition or extra fact")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
