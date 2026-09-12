#!/usr/bin/env python3
"""Guard the first bundled REIGN campaign slice against structural regressions.

This validator deliberately checks implementation invariants only. It does not
print authored dialogue or expose concealed later campaign content.
"""
from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
BUNDLED = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUESTS = BUNDLED / "quests/campaign/opening"
INDEX = BUNDLED / "index.json"

OPENING = QUESTS / "a_new_master.json"
BROWN = QUESTS / "restore_browns.json"
BROWN_REACTION = QUESTS / "browns_return.json"
DIRECT = QUESTS / "make_an_impression.json"

OPENING_ID = "questlog:campaign/opening/a_new_master"
BROWN_ID = "questlog:campaign/opening/restore_browns"
STAFF_ID = "minionsremastered:masters_staff"


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


def has_quest_complete(data: dict[str, Any], quest_id: str) -> bool:
    prerequisites = data.get("prerequisites", [])
    return isinstance(prerequisites, list) and any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:quest_complete"
        and entry.get("quest") == quest_id
        for entry in prerequisites
    )


def collect_errors() -> list[str]:
    errors: list[str] = []
    opening = load(OPENING, errors)
    brown = load(BROWN, errors)
    brown_reaction = load(BROWN_REACTION, errors)
    direct = load(DIRECT, errors)
    index = load(INDEX, errors)

    bundled_quests = index.get("quests", [])
    required_paths = {
        "campaign/opening/a_new_master.json",
        "campaign/opening/restore_browns.json",
        "campaign/opening/browns_return.json",
        "campaign/opening/make_an_impression.json",
    }
    if not isinstance(bundled_quests, list) or not required_paths.issubset(set(bundled_quests)):
        errors.append("bundled definition index is missing one or more opening campaign definitions")

    if opening.get("show_popup_on_unlock") is not True:
        errors.append("opening campaign entry must remain an automatic speaker popup")
    if opening.get("speaker_id") != "overlord_reign:gnarl":
        errors.append("opening campaign entry must remain assigned to Gnarl")
    opening_prerequisites = opening.get("prerequisites", [])
    if not isinstance(opening_prerequisites, list) or len(opening_prerequisites) != 1:
        errors.append("opening campaign entry must retain exactly one initial-play trigger prerequisite")
    else:
        trigger = opening_prerequisites[0]
        if not isinstance(trigger, dict) or not (
            trigger.get("type") == "questlog:stat"
            and trigger.get("stat") == "minecraft:play_time"
            and trigger.get("required_amount") == 1
            and trigger.get("retroactive") is True
        ):
            errors.append("opening campaign initial-play trigger contract changed")

    if not has_quest_complete(brown, OPENING_ID):
        errors.append("Brown recovery branch must unlock from the opening campaign entry")
    if not has_quest_complete(direct, OPENING_ID):
        errors.append("direct-action branch must unlock from the opening campaign entry")
    if has_quest_complete(brown, "questlog:campaign/opening/make_an_impression"):
        errors.append("Brown recovery must not be serialized behind the direct-action branch")
    if has_quest_complete(direct, BROWN_ID):
        errors.append("direct action must not be serialized behind Brown recovery")

    brown_objectives = brown.get("objectives", [])
    if not isinstance(brown_objectives, list) or len(brown_objectives) != 1:
        errors.append("Brown recovery must retain one authoritative observation objective")
    else:
        objective = brown_objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:item_craft_stat"
            and objective.get("item") == STAFF_ID
            and objective.get("required_amount") == 1
        ):
            errors.append("Brown recovery must remain sequence-break-safe on the exact Master's Staff craft")
    if brown.get("rewards") not in ([], None):
        errors.append("Brown recovery must not grant the Brown slot from Questlog")

    if not has_quest_complete(brown_reaction, BROWN_ID):
        errors.append("Brown recovery reaction must depend on completed Brown recovery")
    if brown_reaction.get("speaker_id") != "overlord_reign:gnarl":
        errors.append("Brown recovery reaction must remain a Gnarl speaker entry")
    if brown_reaction.get("speaker_reaction") != "approving":
        errors.append("Brown recovery reaction semantic state changed unexpectedly")

    direct_objectives = direct.get("objectives", [])
    if not isinstance(direct_objectives, list) or len(direct_objectives) != 1:
        errors.append("direct-action branch must retain one bounded first-action objective")
    else:
        objective = direct_objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:stat"
            and objective.get("stat") == "minecraft:mob_kills"
            and objective.get("required_amount") == 1
            and objective.get("retroactive") is False
        ):
            errors.append("direct-action opening contract changed unexpectedly")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Opening campaign contract validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("OVERLORD opening campaign contracts: PASS")
    print("opening concurrency: preserved")
    print("Brown bootstrap authority: Minions Remastered")
    print("Brown craft observation: retrospective exact-item statistic")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
