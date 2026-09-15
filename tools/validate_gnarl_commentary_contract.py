#!/usr/bin/env python3
"""Validate the generalized Gnarl lifecycle-commentary contract."""
from __future__ import annotations

import json
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
CATALOG = ROOT / "common/src/main/resources/assets/questlog/overlord/gnarl_commentary.json"
INDEX = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions/index.json"
ENGINE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/commentary/GnarlCommentaryEngine.java"
STATE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/commentary/GnarlCommentaryState.java"
PACKETS = ROOT / "common/src/main/java/org/infernalstudios/questlog/network/QuestlogPackets.java"
EVENTS = ROOT / "common/src/main/java/org/infernalstudios/questlog/QuestlogEvents.java"
FORGE = ROOT / "forge/src/main/java/org/infernalstudios/questlog/QuestlogForgeEventForwarder.java"

REQUIRED_QUESTS = {
    "questlog:campaign/opening/a_new_master",
    "questlog:campaign/tower/restoration_complete",
    "questlog:campaign/tower/magic/prepare_biomancy_chamber",
    "questlog:campaign/magic/biomancy/the_living_laboratory",
    "questlog:campaign/end/the_wound_beyond_the_world",
    "questlog:campaign/end/break_the_dragon",
}

ADVENTURE_PREFIXES = (
    "questlog:campaign/adventures/twilight/",
    "questlog:campaign/adventures/bumblezone/",
    "questlog:campaign/adventures/cataclysm/",
    "questlog:campaign/adventures/graveyard/",
    "questlog:campaign/adventures/knight/",
    "questlog:campaign/adventures/lost_castle/",
    "questlog:campaign/adventures/rats/",
    "questlog:campaign/adventures/church_of_sin/",
    "questlog:campaign/adventures/oddities/",
)

MAGIC_PREFIXES = (
    "questlog:campaign/magic/irons/",
    "questlog:campaign/magic/gluttony/",
    "questlog:campaign/magic/theurgy/",
    "questlog:campaign/magic/alchemy/",
    "questlog:campaign/magic/biomancy/",
    "questlog:campaign/magic/eidolon/",
)


def require(condition: bool, message: str, errors: list[str]) -> None:
    if not condition:
        errors.append(message)


def load(path: Path, errors: list[str]) -> dict:
    try:
        value = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: cannot load JSON: {exc}")
        return {}
    if not isinstance(value, dict):
        errors.append(f"{path.relative_to(ROOT)}: root must be an object")
        return {}
    return value


def collect_errors() -> list[str]:
    errors: list[str] = []
    catalog = load(CATALOG, errors)
    index = load(INDEX, errors)
    quests = catalog.get("quests", {})
    require(isinstance(quests, dict), "Gnarl commentary catalogue quests must be an object", errors)
    if not isinstance(quests, dict):
        return errors

    indexed = {
        "questlog:" + path[:-5]
        for path in index.get("quests", [])
        if isinstance(path, str) and path.endswith(".json")
    }
    require(len(quests) >= 20, "Gnarl commentary needs deliberate major-quest coverage, not a token sample", errors)
    for quest_id in REQUIRED_QUESTS:
        require(quest_id in quests, f"Gnarl commentary missing required major quest {quest_id}", errors)
    for prefix in ADVENTURE_PREFIXES:
        require(any(q.startswith(prefix) for q in quests), f"Gnarl commentary missing dedicated adventure family {prefix}", errors)
    for prefix in MAGIC_PREFIXES:
        require(any(q.startswith(prefix) for q in quests), f"Gnarl commentary missing core magic family {prefix}", errors)

    lifecycle_fields: set[str] = set()
    for quest_id, definition in quests.items():
        require(quest_id in indexed, f"Gnarl commentary references non-production quest {quest_id}", errors)
        require(isinstance(definition, dict), f"{quest_id}: commentary definition must be an object", errors)
        if not isinstance(definition, dict):
            continue
        require(bool(str(definition.get("register", "")).strip()), f"{quest_id}: register is required", errors)
        require(bool(str(definition.get("delivery_direction", "")).strip()), f"{quest_id}: delivery_direction is required", errors)
        require(bool(str(definition.get("objective_clarification", "")).strip()), f"{quest_id}: objective clarification is required", errors)
        require(bool(str(definition.get("first_reminder", "")).strip()), f"{quest_id}: first reminder is required", errors)
        require(bool(str(definition.get("success", "")).strip()), f"{quest_id}: success reaction is required", errors)

        repeats = definition.get("repeat_reminders", [])
        require(isinstance(repeats, list) and 1 <= len(repeats) <= 3,
                f"{quest_id}: repeat reminder pool must be finite and contain one to three variants", errors)

        for key in (
            "objective_clarification", "branch_framing", "first_reminder", "warning",
            "success", "failure", "post_quest"
        ):
            value = definition.get(key)
            if isinstance(value, str) and value.strip():
                lifecycle_fields.add(key)
                require(len(value) <= 280, f"{quest_id}: {key} is too long for gameplay-readable commentary", errors)
        updates = definition.get("objective_updates", {})
        if isinstance(updates, dict) and updates:
            lifecycle_fields.add("objective_updates")
            for key, value in updates.items():
                require(str(key).isdigit() and int(key) > 0, f"{quest_id}: invalid objective update key {key!r}", errors)
                require(isinstance(value, str) and 0 < len(value) <= 280, f"{quest_id}: invalid objective update text", errors)
        for value in repeats if isinstance(repeats, list) else []:
            require(isinstance(value, str) and 0 < len(value) <= 280, f"{quest_id}: invalid repeat reminder text", errors)

    for field in ("objective_clarification", "first_reminder", "warning", "success", "failure", "post_quest", "objective_updates"):
        require(field in lifecycle_fields, f"Gnarl lifecycle surface is missing authored {field} coverage", errors)

    engine = ENGINE.read_text(encoding="utf-8") if ENGINE.is_file() else ""
    state = STATE.read_text(encoding="utf-8") if STATE.is_file() else ""
    packets = PACKETS.read_text(encoding="utf-8") if PACKETS.is_file() else ""
    events = EVENTS.read_text(encoding="utf-8") if EVENTS.is_file() else ""
    forge = FORGE.read_text(encoding="utf-8") if FORGE.is_file() else ""

    for token in (
        "quest.isFailed()",
        "latestObjectiveUpdate",
        "firstReminder",
        "repeatReminders",
        "baselineExistingQuest",
        "postDueTick",
    ):
        require(token in engine, f"Gnarl commentary engine missing lifecycle contract token {token}", errors)
    require("SavedData" in state and "overlord_quests_gnarl_commentary" in state,
            "Gnarl commentary must persist presentation state separately from quest narrative state", errors)
    system_pos = packets.find('"system_reaction"')
    gnarl_pos = packets.find('"gnarl_commentary"')
    require(system_pos >= 0 and gnarl_pos > system_pos,
            "Gnarl commentary packet must append after existing system_reaction packet discriminator", errors)
    require("GnarlCommentaryEngine.onQuestTriggered" in events,
            "Quest trigger boundary is not wired to Gnarl commentary", errors)
    require("GnarlCommentaryEngine.onQuestCompleted" in events,
            "Quest completion boundary is not wired to Gnarl commentary", errors)
    require("TickEvent.ServerTickEvent" in forge and "GnarlCommentaryEngine.tick" in forge,
            "Forge server tick bridge is not wired to Gnarl commentary polling", errors)

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Gnarl commentary contract FAILED with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1
    print("Gnarl commentary contract: PASS")
    print("bounded lifecycle commentary, all core magic families, nine adventure families and End coverage are guarded")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
