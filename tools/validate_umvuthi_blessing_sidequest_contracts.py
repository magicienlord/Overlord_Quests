#!/usr/bin/env python3
"""Guard the source-backed neutral-Grove Umvuthi blessing sidequest."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST_PATH = "campaign/civilizations/umvuthana/suns_blessing.json"
QUEST = DEFINITIONS / f"quests/{QUEST_PATH}"
INDEX = DEFINITIONS / "index.json"
INTEGRATION = ROOT / "docs/UMVUTHI_BLESSING_INTEGRATION.md"
PROTOCOL = ROOT / "docs/UMVUTHI_BLESSING_SIDEQUEST_TEST_PROTOCOL.md"
CONTACT_FACT = "overlord_reign:civilizations/umvuthana/contact_established"
CIVILIZATION = "overlord_reign:umvuthana"
NEUTRAL = "overlord_reign:neutral"


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
            errors.append(f"{path.relative_to(ROOT)}: required Umvuthi blessing evidence is missing")

    quest = load(QUEST, errors)
    index = load(INDEX, errors)
    if QUEST_PATH not in index.get("quests", []):
        errors.append("bundled index must include the Umvuthi blessing sidequest")

    if quest.get("include_in_main") is not False or quest.get("rewards") != []:
        errors.append("Umvuthi blessing must remain a native-reward sidequest with no duplicate Questlog rewards")

    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append("Umvuthi blessing sidequest must remain provider-bound")
    else:
        if provider.get("entity_types") != ["mowziesmobs:umvuthi"]:
            errors.append("Umvuthi blessing must target only the exact native Umvuthi entity")
        if provider.get("scoreboard_tags") != ["overlord_anchor:umvuthana_main_umvuthi"]:
            errors.append("Umvuthi blessing must remain scoped to the designated Grove Umvuthi")
        if provider.get("civilization") != CIVILIZATION:
            errors.append("Umvuthi blessing civilization identity changed unexpectedly")
        if provider.get("required_facts") != [CONTACT_FACT]:
            errors.append("Umvuthi blessing must require completed formal audience history")
        if provider.get("required_dispositions") != {CIVILIZATION: [NEUTRAL]}:
            errors.append("Umvuthi blessing must be available only while the designated Grove is neutral")
        if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
            errors.append("Umvuthi blessing must retain exact issuing-Umvuthi turn-in")
        if "location" in provider:
            errors.append("Umvuthi blessing must not fabricate final Grove coordinates")

    prerequisites = quest.get("prerequisites")
    if not isinstance(prerequisites, list) or len(prerequisites) != 2:
        errors.append("Umvuthi blessing must retain contact and neutral-disposition prerequisites")
    else:
        facts = [p for p in prerequisites if isinstance(p, dict) and p.get("type") == "questlog:fact"]
        dispositions = [p for p in prerequisites if isinstance(p, dict) and p.get("type") == "questlog:disposition"]
        if len(facts) != 1 or facts[0].get("fact") != CONTACT_FACT or facts[0].get("required_amount") != 1:
            errors.append("Umvuthi blessing fact prerequisite must be formal audience contact")
        if len(dispositions) != 1 or not (
            dispositions[0].get("civilization") == CIVILIZATION
            and dispositions[0].get("state") == NEUTRAL
            and dispositions[0].get("required_amount") == 1
        ):
            errors.append("Umvuthi blessing disposition prerequisite must be designated-Grove neutral state")

    objectives = quest.get("objectives")
    if not isinstance(objectives, list) or len(objectives) != 1:
        errors.append("Umvuthi blessing must have exactly one native advancement objective")
    else:
        objective = objectives[0]
        if not isinstance(objective, dict) or not (
            objective.get("type") == "questlog:advancement"
            and objective.get("advancement") == "mowziesmobs:suns_blessing"
            and objective.get("required_amount") == 1
        ):
            errors.append("Umvuthi blessing must track exact native suns_blessing advancement")

    serialized = json.dumps(quest, sort_keys=True)
    for forbidden in ("questlog:set_fact", "questlog:set_disposition", "mowziesmobs:kill_umvuthi"):
        if forbidden in serialized:
            errors.append(f"Umvuthi blessing sidequest must not introduce forbidden output/route: {forbidden}")

    if INTEGRATION.exists():
        text = INTEGRATION.read_text(encoding="utf-8")
        for fragment in (
            "e8ce1768cda6f1e1fadd2321c92921b473bd0cee45ba4b8387fb30f8326cb31c",
            "MessageUmvuthiTrade$Handler",
            "hasTradedWith",
            "fulfillDesire",
            "rememberTrade",
            "SUNS_BLESSING",
            "mowziesmobs:kill_umvuthi",
        ):
            if fragment not in text:
                errors.append(f"Umvuthi blessing integration audit is missing evidence: {fragment}")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Umvuthi blessing sidequest contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1
    print("Umvuthi native blessing production sidequest contract: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
