#!/usr/bin/env python3
"""Validate the production central End campaign and ending-arm boundary."""
from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUESTS = DEFS / "quests"
INDEX = DEFS / "index.json"

ENTRY = "campaign/end/the_wound_beyond_the_world.json"
FINALE = "campaign/end/break_the_dragon.json"
FOUNDATION = "questlog:campaign/expansion/the_reign_takes_shape"
ENDING_ARM = "overlord_reign:campaign/ending_armed"
COMPLETION = "overlord_reign:campaign/central_campaign_completed"


def load(path: Path, errors: list[str]) -> dict[str, Any]:
    try:
        value = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: unreadable JSON: {exc}")
        return {}
    if not isinstance(value, dict):
        errors.append(f"{path.relative_to(ROOT)}: root must be an object")
        return {}
    return value


def typed(data: dict[str, Any], field: str, type_id: str) -> list[dict[str, Any]]:
    value = data.get(field, [])
    if not isinstance(value, list):
        return []
    return [entry for entry in value if isinstance(entry, dict) and entry.get("type") == type_id]


def has_fact_reward(data: dict[str, Any], fact: str) -> bool:
    rewards = data.get("rewards", [])
    return isinstance(rewards, list) and any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:set_fact"
        and entry.get("fact") == fact
        and entry.get("value") is True
        and entry.get("auto_claim") is True
        for entry in rewards
    )


def main() -> int:
    errors: list[str] = []
    index = load(INDEX, errors)
    entry = load(QUESTS / ENTRY, errors)
    finale = load(QUESTS / FINALE, errors)

    indexed = set(index.get("quests", [])) if isinstance(index.get("quests", []), list) else set()
    for rel in (ENTRY, FINALE):
        if rel not in indexed:
            errors.append(f"{rel}: production central-campaign quest is not indexed")

    entry_prereqs = typed(entry, "prerequisites", "questlog:quest_complete")
    if len(entry_prereqs) != 1 or entry_prereqs[0].get("quest") != FOUNDATION:
        errors.append("central End entry must branch directly from the established initial foundation")
    if len(entry.get("prerequisites", [])) != 1:
        errors.append("central End entry must not add civilization, full-Tower, or checklist gates")

    visits = typed(entry, "objectives", "questlog:visit_dimension_history")
    if len(visits) != 1 or visits[0].get("dimension") != "minecraft:the_end":
        errors.append("central End entry must observe sequence-break-safe entry into minecraft:the_end")

    if not has_fact_reward(entry, ENDING_ARM):
        errors.append("central End entry must arm the production ending before the Dragon resolution")

    finale_prereqs = typed(finale, "prerequisites", "questlog:quest_complete")
    if len(finale_prereqs) != 1 or finale_prereqs[0].get("quest") != "questlog:campaign/end/the_wound_beyond_the_world":
        errors.append("Dragon finale must follow the dimensional-Wasteland entry quest")

    dragon = typed(finale, "objectives", "questlog:ender_dragon_defeated")
    if len(dragon) != 1:
        errors.append("Dragon finale must use the persistent EnderDragonDefeated objective")

    if not has_fact_reward(finale, COMPLETION):
        errors.append("Dragon finale must persist central_campaign_completed")

    serialized = json.dumps([entry, finale], sort_keys=True)
    for forbidden in ("questlog:disposition", "restoration_complete", "blues_return"):
        if forbidden in serialized:
            errors.append(f"central ending must not be gated by parallel political or completion checklists: found {forbidden}")

    if errors:
        print("Central End campaign contract FAILED:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1

    print("Central End campaign contract OK: foundation -> End entry -> persistent Dragon resolution.")
    print("Ending access adds no civilization or full-Tower checklist and remains sequence-break safe.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
