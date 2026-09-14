#!/usr/bin/env python3
"""Validate the source-backed two-phase Illager Bastille campaign contract."""
from __future__ import annotations

import json
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
QUESTS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions/quests/campaign/civilizations/illagers"
OPENING = QUESTS / "break_the_bastille.json"
AUDIENCE = QUESTS / "the_bastille_bows.json"
INDEX = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions/index.json"
DOC = ROOT / "docs/ILLAGER_BASTILLE_INTEGRATION.md"
FACTS = ROOT / "docs/NARRATIVE_FACTS.md"
SEQUENCE = ROOT / "docs/SEQUENCE_BREAK_TRACKING.md"
PROTOCOL = ROOT / "docs/ILLAGER_CIVILIZATION_TEST_PROTOCOL.md"
BRIDGE = ROOT / "forge/src/main/java/org/infernalstudios/questlog/overlord/provider/IllagerBastilleAudienceBridgeForge.java"

OPENING_INDEX = "campaign/civilizations/illagers/break_the_bastille.json"
AUDIENCE_INDEX = "campaign/civilizations/illagers/the_bastille_bows.json"
FOUNDATION_FACT = "overlord_reign:reign/initial_foundation_established"
AUTHORITY_FACT = "overlord_reign:civilizations/illagers/authority_established"
COWED_FACT = "overlord_reign:civilizations/illagers/bastille_cowed"
COMMANDER_TAG = "overlord_anchor:illager_bastille_commander"
INTERMEDIARY_TAG = "overlord_anchor:illager_bastille_intermediary"
PROTECTED_TAG = "overlord_quest_protected"


def load_json(path: Path, errors: list[str]) -> dict:
    try:
        value = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: failed to load: {exc}")
        return {}
    if not isinstance(value, dict):
        errors.append(f"{path.relative_to(ROOT)}: root must be an object")
        return {}
    return value


def require(condition: bool, message: str, errors: list[str]) -> None:
    if not condition:
        errors.append(message)


def collect_errors() -> list[str]:
    errors: list[str] = []
    opening = load_json(OPENING, errors)
    audience = load_json(AUDIENCE, errors)
    index = load_json(INDEX, errors)
    bundled = index.get("quests", [])

    require(OPENING_INDEX in bundled, f"{INDEX.relative_to(ROOT)}: missing bundled hostile Illager opening", errors)
    require(AUDIENCE_INDEX in bundled, f"{INDEX.relative_to(ROOT)}: missing bundled cowed Illager continuation", errors)

    # Phase 1: designated hostile Bastille commander.
    require("provider" not in opening, f"{OPENING.relative_to(ROOT)}: hostile opening must not be an NPC provider quest", errors)
    prerequisites = opening.get("prerequisites", [])
    require(
        len(prerequisites) == 1
        and prerequisites[0].get("type") == "questlog:fact"
        and prerequisites[0].get("fact") == FOUNDATION_FACT,
        f"{OPENING.relative_to(ROOT)}: must require only the initial reign foundation fact",
        errors,
    )

    objectives = opening.get("objectives", [])
    history = [entry for entry in objectives if isinstance(entry, dict) and entry.get("type") == "questlog:entity_kill_history"]
    require(len(objectives) == 1 and len(history) == 1, f"{OPENING.relative_to(ROOT)}: opening must rely only on exact marked commander history", errors)
    if history:
        entry = history[0]
        require(entry.get("entity") == "takesapillage:legioner", f"{OPENING.relative_to(ROOT)}: commander must remain exact takesapillage:legioner", errors)
        require(entry.get("scoreboard_tag") == COMMANDER_TAG, f"{OPENING.relative_to(ROOT)}: commander tag changed", errors)
        require(entry.get("required_amount", 1) == 1, f"{OPENING.relative_to(ROOT)}: commander history must remain boolean", errors)
    require(not any(isinstance(entry, dict) and entry.get("type") == "questlog:advancement" for entry in objectives), f"{OPENING.relative_to(ROOT)}: global Bastille advancement cannot identify the designated Bastille", errors)

    opening_rewards = opening.get("rewards", [])
    opening_facts = [entry for entry in opening_rewards if isinstance(entry, dict) and entry.get("type") == "questlog:set_fact"]
    require(
        len(opening_facts) == 1
        and opening_facts[0].get("fact") == AUTHORITY_FACT
        and opening_facts[0].get("auto_claim") is True,
        f"{OPENING.relative_to(ROOT)}: opening must auto-record only local authority",
        errors,
    )
    require(not any(isinstance(entry, dict) and entry.get("type") == "questlog:set_disposition" for entry in opening_rewards), f"{OPENING.relative_to(ROOT)}: hostile opening must not resolve disposition", errors)

    # Phase 2: exact protected local Pillager intermediary after authority.
    provider = audience.get("provider", {})
    require(provider.get("entity_types") == ["minecraft:pillager"], f"{AUDIENCE.relative_to(ROOT)}: intermediary must remain exact minecraft:pillager", errors)
    require(provider.get("scoreboard_tags") == [INTERMEDIARY_TAG, PROTECTED_TAG], f"{AUDIENCE.relative_to(ROOT)}: intermediary tags changed", errors)
    require(provider.get("civilization") == "overlord_reign:illagers", f"{AUDIENCE.relative_to(ROOT)}: civilization id changed", errors)
    require(provider.get("required_facts") == [AUTHORITY_FACT], f"{AUDIENCE.relative_to(ROOT)}: provider must require local authority fact", errors)
    require(provider.get("lock_to_provider") is True, f"{AUDIENCE.relative_to(ROOT)}: provider must remain UUID-bound", errors)
    require(provider.get("turn_in") == "same_provider", f"{AUDIENCE.relative_to(ROOT)}: continuation must turn in to same intermediary", errors)
    require("location" not in provider, f"{AUDIENCE.relative_to(ROOT)}: must not invent fixed Bastille coordinates", errors)

    audience_prereq = audience.get("prerequisites", [])
    require(
        len(audience_prereq) == 1
        and audience_prereq[0].get("type") == "questlog:fact"
        and audience_prereq[0].get("fact") == AUTHORITY_FACT,
        f"{AUDIENCE.relative_to(ROOT)}: cowed audience must require authority",
        errors,
    )
    require(audience.get("objectives") == [], f"{AUDIENCE.relative_to(ROOT)}: cowed audience must remain provider-native", errors)

    audience_rewards = audience.get("rewards", [])
    require(any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:set_fact"
        and entry.get("fact") == COWED_FACT
        and entry.get("auto_claim") is True
        for entry in audience_rewards
    ), f"{AUDIENCE.relative_to(ROOT)}: cowed historical fact missing", errors)
    require(any(
        isinstance(entry, dict)
        and entry.get("type") == "questlog:set_disposition"
        and entry.get("civilization") == "overlord_reign:illagers"
        and entry.get("state") == "overlord_reign:neutral"
        and entry.get("auto_claim") is True
        for entry in audience_rewards
    ), f"{AUDIENCE.relative_to(ROOT)}: local neutral disposition transition missing", errors)

    for quest, label in ((opening, "opening"), (audience, "audience")):
        require(quest.get("show_popup_on_unlock") is False, f"Illager {label}: popup-on-unlock must remain disabled", errors)
        require(quest.get("toast_on_unlock") is False, f"Illager {label}: unlock toast must remain disabled", errors)
        require(quest.get("toast_on_complete") is False, f"Illager {label}: completion toast must remain disabled", errors)

    doc = DOC.read_text(encoding="utf-8") if DOC.is_file() else ""
    required_doc_fragments = {
        "takesapillage-1.0.3-1.20.1.jar": "installed artifact identity",
        "b8ebc7ea467637dc918ffa9c8eaa273f8723e6e81be93cf5f69618f8072baa11": "installed artifact SHA-256",
        "No dedicated Bastille-leader entity type or native commander role was found": "native leader absence boundary",
        COMMANDER_TAG: "authored commander tag",
        INTERMEDIARY_TAG: "authored intermediary tag",
        "minecraft:pillager": "exact intermediary entity",
        "IllagerBastilleAudienceBridgeForge": "local restraint bridge",
        "does not set civilization disposition": "unresolved opening disposition boundary",
        "does not identify which Bastille was entered": "global advancement scope boundary",
        "unrelated Bastilles": "locality boundary",
    }
    for fragment, label in required_doc_fragments.items():
        require(fragment in doc, f"{DOC.relative_to(ROOT)}: missing {label}", errors)

    facts = FACTS.read_text(encoding="utf-8") if FACTS.is_file() else ""
    authority_pos = facts.find(AUTHORITY_FACT)
    cowed_pos = facts.find(COWED_FACT)
    require(authority_pos >= 0, f"{FACTS.relative_to(ROOT)}: missing authority fact registry entry", errors)
    require(cowed_pos >= 0, f"{FACTS.relative_to(ROOT)}: missing cowed fact registry entry", errors)
    if authority_pos >= 0:
        authority_section = facts[authority_pos:cowed_pos if cowed_pos > authority_pos else None].lower()
        for boundary in ("does not write disposition", "globally pacify illagers", "later cowed audience"):
            require(boundary in authority_section, f"{FACTS.relative_to(ROOT)}: authority fact lacks negative boundary: {boundary}", errors)
    if cowed_pos >= 0:
        cowed_section = facts[cowed_pos:].lower()
        for boundary in ("fear/restraint", "species-wide surrender", "later explicit non-peaceful disposition"):
            require(boundary in cowed_section, f"{FACTS.relative_to(ROOT)}: cowed fact lacks boundary: {boundary}", errors)

    bridge = BRIDGE.read_text(encoding="utf-8") if BRIDGE.is_file() else ""
    bridge_fragments = {
        'new ResourceLocation("minecraft", "pillager")': "exact intermediary type",
        'new ResourceLocation("overlord_reign", "illagers")': "local civilization key",
        'new ResourceLocation("overlord_reign", "civilizations/illagers/authority_established")': "authority fact gate",
        INTERMEDIARY_TAG: "intermediary anchor tag",
        "QuestAnchorProtection.isProtected": "protection gate",
        "OverlordNarrativeState.UNRESOLVED": "pre-audience unresolved state",
        "NEUTRAL": "neutral continuation",
        "SUBJUGATED": "subjugated continuation",
        "LivingChangeTargetEvent": "target suppression",
        "LivingAttackEvent": "outgoing attack suppression",
    }
    for fragment, label in bridge_fragments.items():
        require(fragment in bridge, f"{BRIDGE.relative_to(ROOT)}: missing {label}", errors)

    sequence = SEQUENCE.read_text(encoding="utf-8") if SEQUENCE.is_file() else ""
    require("questlog:entity_kill_history" in sequence and "cannot reconstruct" in sequence, f"{SEQUENCE.relative_to(ROOT)}: missing bounded kill-history documentation", errors)

    protocol = PROTOCOL.read_text(encoding="utf-8") if PROTOCOL.is_file() else ""
    for fragment in ("pre-activation commander defeat", "first cowed audience while disposition is unresolved", "later explicit non-peaceful disposition restores native hostility", "unrelated Illagers"):
        require(fragment.lower() in protocol.lower(), f"{PROTOCOL.relative_to(ROOT)}: missing runtime qualification boundary: {fragment}", errors)

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Illager campaign contract validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1
    print("OVERLORD Illager Bastille two-phase campaign contract: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
