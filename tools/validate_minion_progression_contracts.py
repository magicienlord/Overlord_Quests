#!/usr/bin/env python3
"""Static contracts for the OVERLORD Minions public progression bridge."""
from __future__ import annotations

import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
BRIDGE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/minions/OverlordMinionProgressionBridge.java"
REWARD = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/minions/UnlockMinionReward.java"
OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/minions/MinionUnlockedObjective.java"
REWARD_REGISTRY = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/QuestRewardRegistry.java"
OBJECTIVE_REGISTRY = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/QuestObjectiveRegistry.java"
EVENTS = ROOT / "common/src/main/java/org/infernalstudios/questlog/QuestlogEvents.java"
MANAGER = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/QuestManager.java"
PLAYER_MANAGER = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/ServerPlayerManager.java"
MODS_TOML = ROOT / "forge/src/main/resources/META-INF/mods.toml"
DOC = ROOT / "docs/MINION_UNLOCK_INTEGRATION.md"
PROTOCOL = ROOT / "docs/MINION_PROGRESSION_TEST_PROTOCOL.md"
FIXTURES = {
    "red": ROOT / "examples/questlog/quests/overlord_minion_unlock_red_dev.json",
    "green": ROOT / "examples/questlog/quests/overlord_minion_unlock_green_dev.json",
    "blue": ROOT / "examples/questlog/quests/overlord_minion_unlock_blue_dev.json",
}
BROWN_QUEST = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions/quests/campaign/opening/restore_browns.json"


def require(condition: bool, message: str, errors: list[str]) -> None:
    if not condition:
        errors.append(message)


def read(path: Path, errors: list[str]) -> str:
    try:
        return path.read_text(encoding="utf-8")
    except OSError as exc:
        errors.append(f"{path.relative_to(ROOT)}: {exc}")
        return ""


def load_json(path: Path, errors: list[str]) -> dict:
    try:
        value = json.loads(path.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError) as exc:
        errors.append(f"{path.relative_to(ROOT)}: {exc}")
        return {}
    if not isinstance(value, dict):
        errors.append(f"{path.relative_to(ROOT)}: root must be an object")
        return {}
    return value


def has_minion_prerequisite(prerequisites: object, slot: str) -> bool:
    return isinstance(prerequisites, list) and any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:minion_unlocked"
        and entry.get("slot") == slot
        and entry.get("required_amount", 1) == 1
        for entry in prerequisites
    )


def main() -> int:
    errors: list[str] = []

    bridge = read(BRIDGE, errors)
    reward = read(REWARD, errors)
    objective = read(OBJECTIVE, errors)
    reward_registry = read(REWARD_REGISTRY, errors)
    objective_registry = read(OBJECTIVE_REGISTRY, errors)
    events = read(EVENTS, errors)
    manager = read(MANAGER, errors)
    player_manager = read(PLAYER_MANAGER, errors)
    mods_toml = read(MODS_TOML, errors)
    doc = read(DOC, errors)
    protocol = read(PROTOCOL, errors)

    require(
        '"com.overlordreign.minions.api.OverlordMinionProgression"' in bridge,
        "bridge must target the stable public OverlordMinionProgression API",
        errors,
    )
    require(
        '"com.overlordreign.minions.progression.MinionSlot"' in bridge,
        "bridge must target the stable MinionSlot enum",
        errors,
    )
    require("RED," in bridge and "GREEN," in bridge and "BLUE;" in bridge, "quest bridge must expose Red, Green, Blue", errors)
    require("BROWN" not in bridge, "quest bridge must not expose Brown as a quest-owned tier", errors)
    require('getMethod("unlock", MinecraftServer.class, slotClass)' in bridge, "bridge must invoke the documented unlock method", errors)
    require('getMethod("isUnlocked", MinecraftServer.class, slotClass)' in bridge, "bridge must query the documented owner-state method", errors)

    require("class UnlockMinionReward" in reward, "unlock_minion reward implementation is missing", errors)
    require("if (!this.isAutoClaim())" in reward, "unlock_minion must enforce auto_claim", errors)
    require("case UNLOCKED, ALREADY_UNLOCKED" in reward, "unlock_minion must treat idempotent already-unlocked state as success", errors)
    require("case OUT_OF_ORDER" in reward, "unlock_minion must preserve owner-side sequence rejection", errors)
    require("super.applyReward(player);" in reward, "successful external unlock must persist the Questlog reward claim", errors)

    require("class MinionUnlockedObjective" in objective, "minion_unlocked owner-state objective is missing", errors)
    require("OverlordMinionProgressionBridge.isUnlocked" in objective, "minion_unlocked must read owner state through the bounded bridge", errors)
    require("required_amount must be 1" in objective, "minion_unlocked must remain a one-unit boolean objective", errors)

    require('new ResourceLocation("questlog", "unlock_minion")' in reward_registry, "unlock_minion reward is not registered", errors)
    require('new ResourceLocation("questlog", "minion_unlocked")' in objective_registry, "minion_unlocked objective is not registered", errors)
    require("reconcileExternalProgressionRewards" in manager, "pending external Minion unlocks need login reconciliation", errors)
    require("reconcileExternalProgressionRewards(serverPlayer)" in player_manager, "player load must invoke Minion unlock reconciliation", errors)
    require("reward instanceof UnlockMinionReward && reward.hasRewarded()" in events, "successful Minion reward must be detected at the completion boundary", errors)
    require("ServerPlayerManager.INSTANCE.syncAllQuestState();" in events, "successful Minion handoff must immediately refresh owner-backed prerequisites", errors)

    require('modId = "overlord_minions"' in mods_toml, "Forge metadata must declare the optional OVERLORD Minions integration", errors)
    require("mandatory = false" in mods_toml[mods_toml.find('modId = "overlord_minions"'):], "OVERLORD Minions integration must remain optional at loader level", errors)

    require("Build #118" in doc, "Minion integration doc must record the validated Build #118 baseline", errors)
    require("Brown `0`, Red `1`, Green `2`, Blue `3`" in doc, "Minion integration doc must preserve the fixed slot order", errors)
    require("MINION_PROGRESSION_TEST_PROTOCOL.md" in doc, "Minion integration doc must link the runtime validation protocol", errors)
    require("/overlord_minions status" in protocol, "runtime protocol must verify owner-side progression state", errors)

    fixture_data = {slot: load_json(path, errors) for slot, path in FIXTURES.items()}
    for slot, data in fixture_data.items():
        rewards = data.get("rewards", [])
        matching = [entry for entry in rewards if isinstance(entry, dict) and entry.get("type") == "questlog:unlock_minion"]
        require(len(matching) == 1, f"{slot} fixture must contain exactly one unlock_minion reward", errors)
        if matching:
            require(matching[0].get("slot") == slot, f"{slot} fixture unlock slot mismatch", errors)
            require(matching[0].get("auto_claim") is True, f"{slot} fixture unlock must auto-claim", errors)

    green_prereqs = fixture_data.get("green", {}).get("prerequisites", [])
    blue_prereqs = fixture_data.get("blue", {}).get("prerequisites", [])
    require(
        any(isinstance(entry, dict) and entry.get("quest") == "questlog:overlord_minion_unlock_red_dev" for entry in green_prereqs),
        "Green development unlock must depend on the Red milestone",
        errors,
    )
    require(
        has_minion_prerequisite(green_prereqs, "red"),
        "Green development unlock must also confirm Red owner state",
        errors,
    )
    require(
        any(isinstance(entry, dict) and entry.get("quest") == "questlog:overlord_minion_unlock_green_dev" for entry in blue_prereqs),
        "Blue development unlock must depend on the Green milestone",
        errors,
    )
    require(
        has_minion_prerequisite(blue_prereqs, "green"),
        "Blue development unlock must also confirm Green owner state",
        errors,
    )

    brown = load_json(BROWN_QUEST, errors)
    brown_text = json.dumps(brown, sort_keys=True)
    require("minionsremastered:masters_staff" in brown_text, "Brown bootstrap must remain tied to the Master's Staff", errors)
    require("questlog:item_craft_stat" in brown_text, "Brown bootstrap must remain observational through the craft statistic", errors)
    require("questlog:unlock_minion" not in brown_text, "Brown must never be granted through the quest-side unlock reward", errors)
    require("questlog:minion_unlocked" not in brown_text, "Brown bootstrap must not depend on the later-tier owner-state objective", errors)

    if errors:
        print(f"OVERLORD Minion progression contracts failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1

    print("OVERLORD Minion progression contracts: PASS")
    print("slot ownership: Brown=staff bootstrap, Red=1, Green=2, Blue=3")
    print("quest-side persistence: delegated to public OverlordMinionProgression API")
    print("later-tier gating: quest milestone plus authoritative previous-slot owner state")
    print("post-handoff refresh: immediate active-graph synchronization")
    print("reconciliation: idempotent login retry for completed pending milestones")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
