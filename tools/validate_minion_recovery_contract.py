#!/usr/bin/env python3
"""Validate the OVERLORD REIGN traditional Minion recovery contract.

Questlog owns authored recovery progression. OVERLORD Minions owns the durable
slot state. Brown remains the Master's Staff bootstrap; Red, Green, and Blue are
unlocked through the public Minion progression API in its enforced order.
"""
from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUESTS = DEFINITIONS / "quests/campaign"
INDEX = DEFINITIONS / "index.json"
BRIDGE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/minions/OverlordMinionProgressionBridge.java"
REWARD = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/minions/UnlockMinionReward.java"

BROWN_ACTION = QUESTS / "opening/restore_browns.json"
BROWN_REACTION = QUESTS / "opening/browns_return.json"
FOUNDATION_ID = "questlog:campaign/expansion/the_reign_takes_shape"

SLOTS = {
    "red": {
        "action": QUESTS / "expansion/restore_reds.json",
        "reaction": QUESTS / "expansion/reds_return.json",
        "action_id": "questlog:campaign/expansion/restore_reds",
        "reaction_id": "questlog:campaign/expansion/reds_return",
        "previous": FOUNDATION_ID,
        "item": "minecraft:blaze_rod",
    },
    "green": {
        "action": QUESTS / "expansion/restore_greens.json",
        "reaction": QUESTS / "expansion/greens_return.json",
        "action_id": "questlog:campaign/expansion/restore_greens",
        "reaction_id": "questlog:campaign/expansion/greens_return",
        "previous": "questlog:campaign/expansion/reds_return",
        "item": "minecraft:spider_eye",
    },
    "blue": {
        "action": QUESTS / "expansion/restore_blues.json",
        "reaction": QUESTS / "expansion/blues_return.json",
        "action_id": "questlog:campaign/expansion/restore_blues",
        "reaction_id": "questlog:campaign/expansion/blues_return",
        "previous": "questlog:campaign/expansion/greens_return",
        "item": "minecraft:prismarine_crystals",
    },
}


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


def contains_entry(entries: Any, *, type_id: str, key: str, value: str) -> bool:
    return isinstance(entries, list) and any(
        isinstance(entry, dict)
        and entry.get("type") == type_id
        and entry.get(key) == value
        and entry.get("required_amount", 1) == 1
        for entry in entries
    )


def exact_auto_reward(data: dict[str, Any], type_id: str, key: str, value: str) -> bool:
    rewards = data.get("rewards", [])
    return isinstance(rewards, list) and len(rewards) == 1 and isinstance(rewards[0], dict) and (
        rewards[0].get("type") == type_id
        and rewards[0].get(key) == value
        and rewards[0].get("auto_claim") is True
    )


def collect_errors() -> list[str]:
    errors: list[str] = []
    index = load(INDEX, errors)
    brown_action = load(BROWN_ACTION, errors)
    brown_reaction = load(BROWN_REACTION, errors)

    # Brown is narratively quest-owned but technically remains the staff bootstrap.
    for reward in brown_action.get("rewards", []):
        if isinstance(reward, dict) and reward.get("type") == "questlog:unlock_minion":
            errors.append("Brown recovery must never call questlog:unlock_minion; the Master's Staff owns Brown bootstrap")

    if not exact_auto_reward(
        brown_reaction,
        "questlog:set_fact",
        "fact",
        "overlord_reign:minions/brown_recovered",
    ):
        errors.append("Brown confirmation must persist overlord_reign:minions/brown_recovered exactly once")

    indexed = index.get("quests", [])
    if not isinstance(indexed, list):
        errors.append("definition index quests field must be a list")
        indexed = []

    for slot, contract in SLOTS.items():
        action = load(contract["action"], errors)
        reaction = load(contract["reaction"], errors)

        action_rel = contract["action"].relative_to(DEFINITIONS / "quests").as_posix()
        reaction_rel = contract["reaction"].relative_to(DEFINITIONS / "quests").as_posix()
        if action_rel not in indexed or reaction_rel not in indexed:
            errors.append(f"{slot}: action/reaction production definitions must both be indexed")

        if not contains_entry(
            action.get("prerequisites", []),
            type_id="questlog:quest_complete",
            key="quest",
            value=contract["previous"],
        ):
            errors.append(f"{slot}: recovery action must follow {contract['previous']}")

        objectives = action.get("objectives", [])
        if not (
            isinstance(objectives, list)
            and len(objectives) == 1
            and isinstance(objectives[0], dict)
            and objectives[0].get("type") == "questlog:item_obtain"
            and objectives[0].get("item") == contract["item"]
            and objectives[0].get("required_amount") == 1
        ):
            errors.append(f"{slot}: recovery action must retain its one sequence-break-safe material test")

        if not exact_auto_reward(action, "questlog:unlock_minion", "slot", slot):
            errors.append(f"{slot}: recovery action must auto-claim exactly one {slot} Minion API unlock")

        prerequisites = reaction.get("prerequisites", [])
        if not contains_entry(
            prerequisites,
            type_id="questlog:quest_complete",
            key="quest",
            value=contract["action_id"],
        ):
            errors.append(f"{slot}: confirmation must depend on its recovery action")
        if not contains_entry(
            prerequisites,
            type_id="questlog:minion_unlocked",
            key="slot",
            value=slot,
        ):
            errors.append(f"{slot}: confirmation must wait for the owning Minion API state")

        fact = f"overlord_reign:minions/{slot}_recovered"
        if not exact_auto_reward(reaction, "questlog:set_fact", "fact", fact):
            errors.append(f"{slot}: confirmation must persist {fact} only after API confirmation")
        if reaction.get("speaker_id") != "overlord_reign:gnarl":
            errors.append(f"{slot}: recovery confirmation must remain assigned to Gnarl")

    # No production quest may attempt to create a second Brown unlock system.
    for path in sorted(QUESTS.rglob("*.json")):
        data = load(path, errors)
        for reward in data.get("rewards", []):
            if not isinstance(reward, dict) or reward.get("type") != "questlog:unlock_minion":
                continue
            slot = reward.get("slot")
            if slot == "brown":
                errors.append(f"{path.relative_to(ROOT)}: production quest illegally duplicates Brown bootstrap")
            elif slot not in SLOTS:
                errors.append(f"{path.relative_to(ROOT)}: unknown Minion unlock slot {slot!r}")

    try:
        bridge_text = BRIDGE.read_text(encoding="utf-8")
        reward_text = REWARD.read_text(encoding="utf-8")
    except OSError as exc:
        errors.append(f"unable to read Minion bridge sources: {exc}")
    else:
        for token in ("RED", "GREEN", "BLUE"):
            if token not in bridge_text:
                errors.append(f"Minion progression bridge no longer exposes {token}")
        if "BROWN" in bridge_text.split("enum Slot", 1)[-1].split("}", 1)[0]:
            errors.append("Questlog bridge must not expose Brown as an API-controlled unlock slot")
        if "unlock_minion rewards must use auto_claim: true" not in reward_text:
            errors.append("unlock_minion reward must continue enforcing automatic delivery")
        if "BOOTSTRAP_OWNED_BY_STAFF" not in reward_text:
            errors.append("unlock_minion reward must retain explicit Brown-bootstrap rejection handling")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print("Traditional Minion recovery contract FAILED:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1
    print("Traditional Minion recovery contract OK: Brown staff bootstrap + Red -> Green -> Blue API recovery are guarded.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
