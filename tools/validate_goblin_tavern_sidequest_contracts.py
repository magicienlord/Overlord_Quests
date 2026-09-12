#!/usr/bin/env python3
"""Guard the source-backed Goblin tavern sidequest."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST_PATH = "campaign/civilizations/goblins/tavern_business.json"
QUEST = DEFINITIONS / f"quests/{QUEST_PATH}"
INDEX = DEFINITIONS / "index.json"
INTEGRATION = ROOT / "docs/GOBLINS_TYRANNY_TAVERN_INTEGRATION.md"
PROTOCOL = ROOT / "docs/GOBLIN_TAVERN_SIDEQUEST_TEST_PROTOCOL.md"
CONTACT_FACT = "overlord_reign:civilizations/goblins/contact_established"


def load(path: Path, errors: list[str]) -> dict[str, Any]:
    try:
        data = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: unreadable JSON: {exc}")
        return {}
    return data if isinstance(data, dict) else {}


def collect_errors() -> list[str]:
    errors: list[str] = []
    for path in (INTEGRATION, PROTOCOL):
        if not path.exists():
            errors.append(f"{path.relative_to(ROOT)}: required tavern integration evidence is missing")

    quest = load(QUEST, errors)
    index = load(INDEX, errors)
    if QUEST_PATH not in index.get("quests", []):
        errors.append("bundled index must include the Goblin tavern sidequest")

    if quest.get("include_in_main") is not False or quest.get("rewards") != []:
        errors.append("Goblin tavern quest must remain a reward-neutral sidequest")

    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append("Goblin tavern sidequest must remain provider-bound")
    else:
        if provider.get("entity_types") != ["goblins_tyranny:bartender_goblin"]:
            errors.append("Goblin tavern sidequest must target the exact native bartender entity")
        if provider.get("scoreboard_tags") != ["overlord_anchor:goblin_main_bartender"]:
            errors.append("Goblin tavern sidequest must remain scoped to the designated principal-camp bartender")
        if provider.get("required_facts") != [CONTACT_FACT]:
            errors.append("Goblin tavern sidequest must remain gated by formal Goblin contact")
        if provider.get("civilization") != "overlord_reign:goblins":
            errors.append("Goblin tavern civilization identity changed unexpectedly")
        if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
            errors.append("Goblin tavern sidequest must retain same-provider turn-in")
        if "required_dispositions" in provider or "location" in provider:
            errors.append("Goblin tavern sidequest must not invent political or coordinate gates")

    objectives = quest.get("objectives")
    if not isinstance(objectives, list) or len(objectives) != 1:
        errors.append("Goblin tavern sidequest must have exactly one native advancement objective")
    else:
        objective = objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:advancement"
            and objective.get("advancement") == "goblins_tyranny:liquor_success"
            and objective.get("required_amount") == 1
        ):
            errors.append("Goblin tavern sidequest must track exact native liquor_success advancement")

    prerequisites = quest.get("prerequisites")
    if not isinstance(prerequisites, list) or len(prerequisites) != 1 or not isinstance(prerequisites[0], dict):
        errors.append("Goblin tavern sidequest must retain one formal-contact prerequisite")
    else:
        p = prerequisites[0]
        if p.get("type") != "questlog:fact" or p.get("fact") != CONTACT_FACT or p.get("required_amount") != 1:
            errors.append("Goblin tavern prerequisite must be the formal-contact fact")

    serialized = json.dumps(quest, sort_keys=True)
    for forbidden in ("questlog:set_fact", "questlog:set_disposition"):
        if forbidden in serialized:
            errors.append(f"Goblin tavern sidequest must not write {forbidden}")

    if INTEGRATION.exists():
        text = INTEGRATION.read_text(encoding="utf-8")
        for fragment in (
            "aa9d337c58a0bfeb0378ab205fb2c70c82a84c85a6e21e5c5c56ee025ec48174",
            "goblins_tyranny:bartender_goblin",
            "goblins_tyranny:liquor_success",
            "Purchase some liquor from the bartender",
            "GoblinLiquorProcedure",
            "BlazingLiquorProcedure",
            "DeadlyLiquorProcedure",
        ):
            if fragment not in text:
                errors.append(f"Goblin tavern integration audit is missing evidence: {fragment}")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Goblin tavern production contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1
    print("Goblin tavern production sidequest contract: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
