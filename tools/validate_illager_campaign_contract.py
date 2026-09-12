#!/usr/bin/env python3
"""Validate the source-backed Illager Bastille opening contract."""
from __future__ import annotations

import json
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
QUEST = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions/quests/campaign/civilizations/illagers/break_the_bastille.json"
INDEX = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions/index.json"
DOC = ROOT / "docs/ILLAGER_BASTILLE_INTEGRATION.md"
FACTS = ROOT / "docs/NARRATIVE_FACTS.md"
SEQUENCE = ROOT / "docs/SEQUENCE_BREAK_TRACKING.md"

QUEST_INDEX_PATH = "campaign/civilizations/illagers/break_the_bastille.json"
FOUNDATION_FACT = "overlord_reign:reign/initial_foundation_established"
AUTHORITY_FACT = "overlord_reign:civilizations/illagers/authority_established"
COMMANDER_TAG = "overlord_anchor:illager_bastille_commander"


def collect_errors() -> list[str]:
    errors: list[str] = []

    try:
        quest = json.loads(QUEST.read_text(encoding="utf-8"))
    except Exception as exc:
        return [f"{QUEST.relative_to(ROOT)}: failed to load: {exc}"]

    try:
        index = json.loads(INDEX.read_text(encoding="utf-8"))
    except Exception as exc:
        return [f"{INDEX.relative_to(ROOT)}: failed to load: {exc}"]

    if QUEST_INDEX_PATH not in index.get("quests", []):
        errors.append(f"{INDEX.relative_to(ROOT)}: missing bundled Illager Bastille quest")

    if "provider" in quest:
        errors.append(f"{QUEST.relative_to(ROOT)}: hostile Bastille opening must not be an NPC provider quest")

    prerequisites = quest.get("prerequisites", [])
    if len(prerequisites) != 1 or prerequisites[0].get("type") != "questlog:fact" or prerequisites[0].get("fact") != FOUNDATION_FACT:
        errors.append(f"{QUEST.relative_to(ROOT)}: must require only the initial reign foundation fact")

    objectives = quest.get("objectives", [])
    history = [entry for entry in objectives if entry.get("type") == "questlog:entity_kill_history"]
    if len(objectives) != 1 or len(history) != 1:
        errors.append(f"{QUEST.relative_to(ROOT)}: opening must rely only on the exact marked commander history signal")
    else:
        entry = history[0]
        if entry.get("entity") != "takesapillage:legioner":
            errors.append(f"{QUEST.relative_to(ROOT)}: commander must use exact source-backed takesapillage:legioner entity")
        if entry.get("scoreboard_tag") != COMMANDER_TAG:
            errors.append(f"{QUEST.relative_to(ROOT)}: commander must use exact authored local anchor tag")
        if entry.get("required_amount", 1) != 1:
            errors.append(f"{QUEST.relative_to(ROOT)}: commander kill history must remain boolean")

    if any(entry.get("type") == "questlog:advancement" for entry in objectives):
        errors.append(f"{QUEST.relative_to(ROOT)}: global takesapillage:bastille advancement cannot prove the designated REIGN Bastille")

    rewards = quest.get("rewards", [])
    fact_rewards = [entry for entry in rewards if entry.get("type") == "questlog:set_fact"]
    if len(fact_rewards) != 1 or fact_rewards[0].get("fact") != AUTHORITY_FACT or fact_rewards[0].get("auto_claim") is not True:
        errors.append(f"{QUEST.relative_to(ROOT)}: must auto-record only the Illager local authority fact")

    if any(entry.get("type") == "questlog:set_disposition" for entry in rewards):
        errors.append(f"{QUEST.relative_to(ROOT)}: opening authority quest must not resolve Illager disposition")

    if quest.get("show_popup_on_unlock") is not False or quest.get("toast_on_unlock") is not False or quest.get("toast_on_complete") is not False:
        errors.append(f"{QUEST.relative_to(ROOT)}: civilization opening must keep popup/toast noise disabled")

    doc = DOC.read_text(encoding="utf-8")
    required_doc_fragments = {
        "takesapillage-1.0.3-1.20.1.jar": "installed artifact identity",
        "b8ebc7ea467637dc918ffa9c8eaa273f8723e6e81be93cf5f69618f8072baa11": "installed artifact SHA-256",
        "No dedicated Bastille-leader entity type or native commander role was found": "native leader absence boundary",
        COMMANDER_TAG: "authored commander anchor tag",
        "does not identify which Bastille was entered": "global advancement scope boundary",
        "does not set civilization disposition": "unresolved disposition boundary",
    }
    for fragment, label in required_doc_fragments.items():
        if fragment not in doc:
            errors.append(f"{DOC.relative_to(ROOT)}: missing {label}")

    facts = FACTS.read_text(encoding="utf-8")
    if AUTHORITY_FACT not in facts:
        errors.append(f"{FACTS.relative_to(ROOT)}: missing Illager authority fact registry entry")
    if "does NOT mean" not in facts[facts.find(AUTHORITY_FACT):]:
        errors.append(f"{FACTS.relative_to(ROOT)}: Illager authority fact lacks negative semantic boundary")

    sequence = SEQUENCE.read_text(encoding="utf-8")
    if "questlog:entity_kill_history" not in sequence or "cannot reconstruct" not in sequence:
        errors.append(f"{SEQUENCE.relative_to(ROOT)}: missing bounded entity_kill_history documentation")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Illager campaign contract validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1
    print("OVERLORD Illager Bastille campaign contracts: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
