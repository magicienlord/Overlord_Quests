#!/usr/bin/env python3
"""Guard the source-backed Goblin engineer workbench sidequest."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST_PATH = "campaign/civilizations/goblins/engineer_workbench.json"
QUEST = DEFINITIONS / f"quests/{QUEST_PATH}"
INDEX = DEFINITIONS / "index.json"
INTEGRATION = ROOT / "docs/GOBLINS_TYRANNY_ENGINEER_INTEGRATION.md"
PROTOCOL = ROOT / "docs/GOBLIN_ENGINEER_SIDEQUEST_TEST_PROTOCOL.md"
CONTACT_FACT = "overlord_reign:civilizations/goblins/contact_established"
ENTITY_TYPES = ["goblins_tyranny:engineer_goblin", "goblins_tyranny:engineeress_goblin"]


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
            errors.append(f"{path.relative_to(ROOT)}: required engineer integration evidence is missing")

    quest = load(QUEST, errors)
    index = load(INDEX, errors)
    if QUEST_PATH not in index.get("quests", []):
        errors.append("bundled index must include the Goblin engineer sidequest")

    if quest.get("include_in_main") is not False:
        errors.append("Goblin engineer work must remain sidequest content")
    if quest.get("rewards") != []:
        errors.append("Goblin engineer sidequest must not add synthetic rewards")

    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append("Goblin engineer sidequest must remain provider-bound")
    else:
        if provider.get("entity_types") != ENTITY_TYPES:
            errors.append("Goblin engineer sidequest must retain both exact installed engineer entity variants")
        if provider.get("scoreboard_tags") != ["overlord_anchor:goblin_main_engineer"]:
            errors.append("Goblin engineer sidequest must remain scoped to the designated principal-camp engineer")
        if provider.get("civilization") != "overlord_reign:goblins":
            errors.append("Goblin engineer civilization identity changed unexpectedly")
        if provider.get("required_facts") != [CONTACT_FACT]:
            errors.append("Goblin engineer sidequest must remain gated by formal Goblin contact")
        if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
            errors.append("Goblin engineer sidequest must retain same-provider turn-in")
        if "required_dispositions" in provider or "location" in provider:
            errors.append("Goblin engineer sidequest must not invent political or coordinate gates")

    objectives = quest.get("objectives")
    if not isinstance(objectives, list) or len(objectives) != 1:
        errors.append("Goblin engineer sidequest must have exactly one native advancement objective")
    else:
        objective = objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:advancement"
            and objective.get("advancement") == "goblins_tyranny:engineer_success"
            and objective.get("required_amount") == 1
        ):
            errors.append("Goblin engineer sidequest must track exact native engineer_success advancement")

    prerequisites = quest.get("prerequisites")
    if not isinstance(prerequisites, list) or len(prerequisites) != 1:
        errors.append("Goblin engineer sidequest must retain exactly one contact prerequisite")
    else:
        prerequisite = prerequisites[0]
        if not isinstance(prerequisite, dict) or not (
            prerequisite.get("type") == "questlog:fact"
            and prerequisite.get("fact") == CONTACT_FACT
            and prerequisite.get("required_amount") == 1
        ):
            errors.append("Goblin engineer sidequest prerequisite must be the formal-contact fact")

    serialized = json.dumps(quest, sort_keys=True)
    for forbidden in ("questlog:set_fact", "questlog:set_disposition"):
        if forbidden in serialized:
            errors.append(f"Goblin engineer sidequest must not write {forbidden}")

    if INTEGRATION.exists():
        text = INTEGRATION.read_text(encoding="utf-8")
        for fragment in (
            "aa9d337c58a0bfeb0378ab205fb2c70c82a84c85a6e21e5c5c56ee025ec48174",
            "goblins_tyranny:engineer_goblin",
            "goblins_tyranny:engineeress_goblin",
            "goblins_tyranny:engineer_success",
            "Use the engineer workbench",
            "OpenBombGuiProcedure",
            "OpenDroneGuiProcedure",
        ):
            if fragment not in text:
                errors.append(f"Goblin engineer integration audit is missing evidence: {fragment}")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Goblin engineer production contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1
    print("Goblin engineer production sidequest contract: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
