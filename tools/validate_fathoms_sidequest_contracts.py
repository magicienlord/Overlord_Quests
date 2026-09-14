#!/usr/bin/env python3
"""Validate the source-backed Overlord Depths / Fathoms Historian sidequest."""
from __future__ import annotations

import json
from pathlib import Path
import sys
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUESTS = DEFS / "quests"
INDEX = DEFS / "index.json"
FOUNDATION = "overlord_reign:reign/initial_foundation_established"

FILES = [
    "campaign/sidequests/fathoms/sounding_the_depths.json",
    "campaign/sidequests/fathoms/an_aberration_on_the_line.json",
    "campaign/sidequests/fathoms/open_the_coffer.json",
    "campaign/sidequests/fathoms/catalogue_the_depths.json",
    "campaign/sidequests/fathoms/a_bad_decision.json",
]
ADVANCEMENTS = [
    "fathoms:nautical/use_bait",
    "fathoms:nautical/catch_aberration",
    "fathoms:nautical/open_coffer",
    "fathoms:nautical/catch_all_fish",
    "fathoms:nautical/make_a_bad_decision",
]


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


def has_quest_prereq(data: dict[str, Any], quest: str) -> bool:
    return any(
        entry.get("quest") == quest and entry.get("required_amount", 1) == 1
        for entry in typed(data, "prerequisites", "questlog:quest_complete")
    )


def main() -> int:
    errors: list[str] = []
    index = load(INDEX, errors)
    indexed = set(index.get("quests", [])) if isinstance(index.get("quests"), list) else set()
    docs = [load(QUESTS / relative, errors) for relative in FILES]

    for relative in FILES:
        if relative not in indexed:
            errors.append(f"{relative}: production quest is not indexed")

    for relative, quest, advancement in zip(FILES, docs, ADVANCEMENTS):
        provider = quest.get("provider", {})
        if not isinstance(provider, dict):
            errors.append(f"{relative}: provider block missing")
            continue
        if provider.get("entity_types") != ["minecraft:villager"]:
            errors.append(f"{relative}: provider must remain a vanilla Villager")
        if provider.get("villager_professions") != ["fathoms:historian"]:
            errors.append(f"{relative}: provider must remain the native fathoms:historian profession")
        if provider.get("required_facts") != [FOUNDATION]:
            errors.append(f"{relative}: provider gate must remain the initial reign foundation")
        if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
            errors.append(f"{relative}: accepted quest must bind and return to the same Historian")
        if "location" in provider or "scoreboard_tags" in provider:
            errors.append(f"{relative}: Historian must be profession-selected without invented coordinates or anchor tags")

        objectives = typed(quest, "objectives", "questlog:advancement")
        if len(objectives) != 1 or objectives[0].get("advancement") != advancement or objectives[0].get("required_amount", 1) != 1:
            errors.append(f"{relative}: expected exactly native advancement {advancement}")

    first_facts = typed(docs[0], "prerequisites", "questlog:fact") if docs else []
    if len(first_facts) != 1 or first_facts[0].get("fact") != FOUNDATION or first_facts[0].get("required_amount", 1) != 1:
        errors.append("Fathoms opening must branch from the initial reign foundation fact")

    for index_value in range(1, len(FILES)):
        previous = "questlog:" + FILES[index_value - 1][:-5]
        if not has_quest_prereq(docs[index_value], previous):
            errors.append(f"{FILES[index_value]}: must follow {previous}")

    if any(typed(quest, "rewards", "questlog:set_fact") for quest in docs):
        errors.append("Fathoms wrapper must not invent durable narrative facts beyond native progression")

    combined = json.dumps(docs).lower()
    for forbidden in ("fathoms boss", "leviathan", "dredge", "eldritch god", "fixed coordinates"):
        if forbidden in combined:
            errors.append(f"Fathoms quest text crossed the source-evidence boundary: {forbidden}")

    integration = (ROOT / "docs/FATHOMS_HISTORIAN_INTEGRATION.md").read_text(encoding="utf-8")
    remaining = (ROOT / "docs/REMAINING_ASSIGNED_QUESTLINES.md").read_text(encoding="utf-8")
    current = (ROOT / "docs/CURRENT_IMPLEMENTATION_STATUS.md").read_text(encoding="utf-8")
    for name, text in (("integration", integration), ("remaining", remaining), ("current", current)):
        if "b00ef7a33550267a826f98d4989f6ea63e6909a9" not in text:
            errors.append(f"{name}: validated Depths checkpoint is not recorded")
    if "IMPLEMENTED AGAINST VALIDATED DEPTHS CHECKPOINT" not in remaining:
        errors.append("remaining ledger does not close the Depths technical deferral")
    if "IMPLEMENTED AGAINST VALIDATED DEPTHS CHECKPOINT" not in current:
        errors.append("current status does not record the implemented Depths boundary")

    if errors:
        print("Fathoms Historian sidequest contract FAILED:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1

    print("Fathoms Historian sidequest contract OK: native profession, native nautical advancements, source-bounded climax and production indexing are intact.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
