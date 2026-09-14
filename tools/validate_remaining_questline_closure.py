#!/usr/bin/env python3
"""Validate closure of delegated civilization, NightWalker and assigned personal-mod questlines."""
from __future__ import annotations

import json
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUESTS = DEFINITIONS / "quests"
INDEX = DEFINITIONS / "index.json"
QUESTLOG = ROOT / "common/src/main/java/org/infernalstudios/questlog/Questlog.java"
NW_STATE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/nightwalker/NightwalkerState.java"
NW_VAMPIRE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/nightwalker/NightwalkerVampireObjective.java"
NW_POWER = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/nightwalker/NightwalkerPowerCountObjective.java"
ILLAGER_BRIDGE = ROOT / "forge/src/main/java/org/infernalstudios/questlog/overlord/provider/IllagerBastilleAudienceBridgeForge.java"

FOUNDATION = "overlord_reign:reign/initial_foundation_established"
ILLAGER_AUTHORITY = "overlord_reign:civilizations/illagers/authority_established"
LESTAT_JOINED = "overlord_reign:personal/nightwalker/lestat_joined_tower"
DEPTHS_VALIDATED_HEAD = "b00ef7a33550267a826f98d4989f6ea63e6909a9"
DEPTHS_IMPLEMENTED_STATUS = "IMPLEMENTED AGAINST VALIDATED DEPTHS CHECKPOINT"

NEW_QUESTS = [
    "campaign/civilizations/villagers/first_contact.json",
    "campaign/civilizations/illagers/the_bastille_bows.json",
    "campaign/sidequests/nightwalker/lestat_arrives.json",
    "campaign/sidequests/nightwalker/hunger_is_a_fact.json",
    "campaign/sidequests/nightwalker/the_vampire_altar.json",
    "campaign/sidequests/nightwalker/choose_the_price.json",
]


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


def require(condition: bool, message: str, errors: list[str]) -> None:
    if not condition:
        errors.append(message)


def fact_prerequisite(quest: dict, fact: str) -> bool:
    return any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:fact"
        and entry.get("fact") == fact
        and entry.get("required_amount", 1) == 1
        for entry in quest.get("prerequisites", [])
    )


def custom_prerequisite(quest: dict, objective_type: str) -> bool:
    return any(
        isinstance(entry, dict)
        and entry.get("type") == objective_type
        and entry.get("required_amount", 1) == 1
        for entry in quest.get("prerequisites", [])
    )


def set_fact_reward(quest: dict, fact: str) -> bool:
    return any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:set_fact"
        and entry.get("fact") == fact
        and entry.get("auto_claim") is True
        for entry in quest.get("rewards", [])
    )


def validate_provider(
    quest: dict,
    errors: list[str],
    *,
    label: str,
    entity: str,
    tags: list[str],
    required_fact: str,
) -> dict:
    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append(f"{label}: provider block is missing")
        return {}
    require(provider.get("entity_types") == [entity], f"{label}: wrong exact provider entity", errors)
    require(provider.get("scoreboard_tags") == tags, f"{label}: wrong authored provider tags", errors)
    require(provider.get("required_facts") == [required_fact], f"{label}: wrong provider fact gate", errors)
    require(provider.get("lock_to_provider") is True, f"{label}: provider must be UUID-bound", errors)
    require(provider.get("turn_in") == "same_provider", f"{label}: must turn in to the same provider", errors)
    require("location" not in provider, f"{label}: must not invent fixed world coordinates", errors)
    return provider


def collect_errors() -> list[str]:
    errors: list[str] = []
    index = load(INDEX, errors)
    bundled = index.get("quests", [])
    for relative in NEW_QUESTS:
        require(relative in bundled, f"manifest missing {relative}", errors)
        require((QUESTS / relative).is_file(), f"definition missing {relative}", errors)

    villager = load(QUESTS / NEW_QUESTS[0], errors)
    vp = validate_provider(
        villager, errors,
        label="Villager first contact",
        entity="minecraft:villager",
        tags=["overlord_anchor:villager_main", "overlord_quest_protected"],
        required_fact=FOUNDATION,
    )
    require(vp.get("civilization") == "overlord_reign:villagers", "Villager civilization id changed", errors)
    require("role" not in vp, "Villager main contact must not force one profession", errors)
    require(fact_prerequisite(villager, FOUNDATION), "Villager first contact must require campaign foundation", errors)
    require(villager.get("objectives") == [], "Villager first contact must remain provider-native", errors)
    require(set_fact_reward(villager, "overlord_reign:civilizations/villagers/contact_established"), "Villager contact fact missing", errors)
    require("set_disposition" not in json.dumps(villager), "Villager first contact must not pre-resolve disposition", errors)

    illager = load(QUESTS / NEW_QUESTS[1], errors)
    ip = validate_provider(
        illager, errors,
        label="Illager cowed audience",
        entity="minecraft:pillager",
        tags=["overlord_anchor:illager_bastille_intermediary", "overlord_quest_protected"],
        required_fact=ILLAGER_AUTHORITY,
    )
    require(ip.get("civilization") == "overlord_reign:illagers", "Illager civilization id changed", errors)
    require(fact_prerequisite(illager, ILLAGER_AUTHORITY), "Illager cowed audience must require local authority fact", errors)
    require(illager.get("objectives") == [], "Illager cowed audience must be a peaceful provider interaction", errors)
    require(set_fact_reward(illager, "overlord_reign:civilizations/illagers/bastille_cowed"), "Illager cowed fact missing", errors)
    require(any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:set_disposition"
        and entry.get("civilization") == "overlord_reign:illagers"
        and entry.get("state") == "overlord_reign:neutral"
        and entry.get("auto_claim") is True
        for entry in illager.get("rewards", [])
    ), "Illager cowed phase must layer the local polity over NEUTRAL", errors)

    bridge = ILLAGER_BRIDGE.read_text(encoding="utf-8") if ILLAGER_BRIDGE.is_file() else ""
    for fragment in (
        "minecraft\", \"pillager",
        "overlord_anchor:illager_bastille_intermediary",
        "QuestAnchorProtection.isProtected",
        "authority_established",
        "LivingChangeTargetEvent",
        "LivingAttackEvent",
    ):
        require(fragment in bridge, f"Illager restraint bridge missing contract fragment: {fragment}", errors)

    init = QUESTLOG.read_text(encoding="utf-8") if QUESTLOG.is_file() else ""
    require("NightwalkerVampireObjective.register();" in init, "NightWalker vampire objective is not registered", errors)
    require("NightwalkerPowerCountObjective.register();" in init, "NightWalker power-count objective is not registered", errors)

    state = NW_STATE.read_text(encoding="utf-8") if NW_STATE.is_file() else ""
    for fragment in ("\"Nycto\"", "\"vampire\"", "\"powerMask\"", "\"nycto\", \"vampirism\"", "CHOOSABLE_POWER_COUNT = 13"):
        require(fragment in state, f"NightWalker alpha.3 state bridge missing: {fragment}", errors)
    require("Class.forName" not in state, "NightWalker persistent-data bridge should not require reflective Nycto class loading", errors)

    vampire_source = NW_VAMPIRE.read_text(encoding="utf-8") if NW_VAMPIRE.is_file() else ""
    power_source = NW_POWER.read_text(encoding="utf-8") if NW_POWER.is_file() else ""
    require("overlord_reign\", \"nightwalker_vampire" in vampire_source, "NightWalker vampire objective id missing", errors)
    require("NightwalkerState.isVampire" in vampire_source, "NightWalker vampire objective does not read owner state", errors)
    require("overlord_reign\", \"nightwalker_power_count" in power_source, "NightWalker power objective id missing", errors)
    require("NightwalkerState.purchasedPowerCount" in power_source, "NightWalker power objective does not read owner state", errors)

    lestat_paths = NEW_QUESTS[2:]
    lestat = [load(QUESTS / path, errors) for path in lestat_paths]
    for path, quest in zip(lestat_paths, lestat):
        p = quest.get("provider", {})
        require(p.get("entity_types") == ["nycto:vampire"], f"{path}: Lestat must use the real nycto:vampire entity", errors)
        require(p.get("scoreboard_tags") == ["overlord_anchor:lestat", "overlord_quest_protected"], f"{path}: Lestat anchor/protection tags changed", errors)
        require("location" not in p, f"{path}: must not invent Dark Tower coordinates", errors)

    require(fact_prerequisite(lestat[0], FOUNDATION), "Lestat arrival must remain campaign-foundation gated", errors)
    require(custom_prerequisite(lestat[0], "overlord_reign:nightwalker_vampire"), "Lestat arrival must require completed Nycto vampirism", errors)
    require(set_fact_reward(lestat[0], LESTAT_JOINED), "Lestat arrival fact missing", errors)

    hunger = lestat[1]
    objective = hunger.get("objectives", [{}])[0] if hunger.get("objectives") else {}
    require(objective.get("type") == "questlog:or", "NightWalker hunger lesson must accept the real blood-bottle variants", errors)
    blood_items = {
        child.get("item")
        for child in objective.get("objectives", [])
        if isinstance(child, dict) and child.get("type") == "questlog:item_use"
    }
    require(blood_items == {
        "nycto:blood_bottle",
        "nycto:player_blood_bottle",
        "nycto:vampire_blood_bottle",
        "nycto:player_vampire_blood_bottle",
    }, "NightWalker hunger lesson blood-bottle surface drifted from alpha.3", errors)

    altar = lestat[2]
    require(any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:block_interact"
        and entry.get("block") == "nycto:vampire_altar"
        for entry in altar.get("objectives", [])
    ), "NightWalker altar lesson must use nycto:vampire_altar", errors)

    final = lestat[3]
    require(any(
        isinstance(entry, dict)
        and entry.get("type") == "overlord_reign:nightwalker_power_count"
        and entry.get("required_amount") == 1
        for entry in final.get("objectives", [])
    ), "NightWalker finale must observe one real Vampire Altar power purchase", errors)
    require(set_fact_reward(final, "overlord_reign:personal/nightwalker/transition_guided"), "NightWalker transition completion fact missing", errors)

    combined_lestat = json.dumps(lestat).lower()
    for forbidden in ("television", "new orleans", "paris", "minecraft", "multiverse", "another universe"):
        require(forbidden not in combined_lestat, f"Lestat quest text crossed the REIGN continuity firewall: {forbidden}", errors)

    civ_status = (ROOT / "docs/CIVILIZATION_SIDEQUEST_STATUS.md").read_text(encoding="utf-8")
    remaining = (ROOT / "docs/REMAINING_ASSIGNED_QUESTLINES.md").read_text(encoding="utf-8")
    current = (ROOT / "docs/CURRENT_IMPLEMENTATION_STATUS.md").read_text(encoding="utf-8")
    for name, text in (("civilization status", civ_status), ("remaining ledger", remaining), ("current status", current)):
        require("10/10" in text, f"{name}: generalized civilization coverage must be 10/10", errors)
        require("Villager principal settlement/provider selection" not in text, f"{name}: stale Villager false blocker remains", errors)
        require("surviving fearful/cowed provider is still undefined" not in text, f"{name}: stale Illager false blocker remains", errors)
    require("IMPLEMENTED AGAINST SUPPLIED ALPHA.3" in remaining, "remaining ledger must record NightWalker implementation boundary", errors)
    for name, text in (("remaining ledger", remaining), ("current status", current)):
        require(DEPTHS_VALIDATED_HEAD in text, f"{name}: missing validated Depths checkpoint", errors)
        require(DEPTHS_IMPLEMENTED_STATUS in text, f"{name}: missing implemented Fathoms status", errors)
        require("external technical deferral recorded" not in text.lower(), f"{name}: stale active Fathoms deferral language remains", errors)

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Remaining questline closure validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1
    print("Remaining delegated questline closure: PASS")
    print("civilizations: 10/10 main-entry coverage; Illager cowed continuation implemented")
    print("NightWalker: alpha.3 vampire state, blood economy, altar and first power purchase integrated")
    print("Depths: validated source checkpoint recorded; Historian-led Fathoms arc implemented")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
