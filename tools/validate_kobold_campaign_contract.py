#!/usr/bin/env python3
"""Guard the production Kobold first-contact slice."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST = DEFINITIONS / "quests/campaign/civilizations/kobolds/first_contact.json"
INDEX = DEFINITIONS / "index.json"
INTEGRATION = ROOT / "docs/KOBOLDS_PROVIDER_INTEGRATION.md"
RUNTIME_PROTOCOL = ROOT / "docs/KOBOLD_CIVILIZATION_TEST_PROTOCOL.md"

QUEST_PATH = "campaign/civilizations/kobolds/first_contact.json"
FOUNDATION_FACT = "overlord_reign:reign/initial_foundation_established"
CONTACT_FACT = "overlord_reign:civilizations/kobolds/contact_established"
CIVILIZATION = "overlord_reign:kobolds"
ENTITY_TYPE = "kobolds:kobold_captain"
ANCHOR_TAG = "overlord_anchor:kobold_main_captain"


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

    if not INTEGRATION.exists():
        errors.append("docs/KOBOLDS_PROVIDER_INTEGRATION.md: exact installed-mod provider audit is missing")
    if not RUNTIME_PROTOCOL.exists():
        errors.append("docs/KOBOLD_CIVILIZATION_TEST_PROTOCOL.md: production runtime protocol is missing")

    quest = load(QUEST, errors)
    index = load(INDEX, errors)

    quests = index.get("quests", [])
    if not isinstance(quests, list) or QUEST_PATH not in quests:
        errors.append("bundled definition index must include the production Kobold first-contact quest")

    if quest.get("include_in_main") is not True:
        errors.append("Kobold first contact must remain part of the main campaign")
    if quest.get("show_popup_on_unlock") is not False:
        errors.append("Kobold first contact must remain an in-world provider interaction")
    if quest.get("objectives") != []:
        errors.append("Kobold first contact must remain provider-native with no artificial journal objective")

    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append("Kobold first contact must remain provider-bound")
    else:
        if provider.get("entity_types") != [ENTITY_TYPE]:
            errors.append("Kobold first contact must target only the installed Kobold Captain entity type")
        if provider.get("scoreboard_tags") != [ANCHOR_TAG]:
            errors.append("Kobold first contact must remain scoped to the designated principal-Den Captain")
        if provider.get("civilization") != CIVILIZATION:
            errors.append("Kobold first-contact civilization identity changed unexpectedly")
        if provider.get("required_facts") != [FOUNDATION_FACT]:
            errors.append("Kobold first contact must remain gated behind the semi-open campaign foundation")
        if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
            errors.append("Kobold first contact must remain bound to the exact issuing Captain")
        if "role" in provider:
            errors.append("Kobold Captain must not acquire an invented native profession or role selector")
        if "required_dispositions" in provider:
            errors.append("Kobold first contact must not require a pre-resolved political disposition")

        dialogue = provider.get("dialogue")
        if not isinstance(dialogue, dict):
            errors.append("Kobold first contact must retain authored provider dialogue")
        else:
            for phase in ("offer", "ready_to_turn_in", "completed"):
                if not isinstance(dialogue.get(phase), str) or not dialogue.get(phase, "").strip():
                    errors.append(f"Kobold first contact must retain non-empty {phase} dialogue")
            if "in_progress" in dialogue:
                errors.append("Kobold first contact must not insert a fake in-progress phase")

    prerequisites = quest.get("prerequisites", [])
    if not isinstance(prerequisites, list) or len(prerequisites) != 1:
        errors.append("Kobold first contact must retain exactly one campaign-foundation prerequisite")
    else:
        prerequisite = prerequisites[0]
        if not isinstance(prerequisite, dict) or not (
            prerequisite.get("type") == "questlog:fact"
            and prerequisite.get("fact") == FOUNDATION_FACT
            and prerequisite.get("required_amount") == 1
        ):
            errors.append("Kobold first contact must remain gated by the established foundation fact")

    rewards = quest.get("rewards", [])
    if not isinstance(rewards, list) or len(rewards) != 1:
        errors.append("Kobold first contact must write exactly one contact-history fact")
    else:
        reward = rewards[0]
        if not isinstance(reward, dict) or not (
            reward.get("type") == "questlog:set_fact"
            and reward.get("fact") == CONTACT_FACT
            and reward.get("auto_claim") is True
        ):
            errors.append("Kobold first contact must persist only the designated-Den contact fact")

    serialized = json.dumps(quest, sort_keys=True)
    if "questlog:set_disposition" in serialized:
        errors.append("Kobold first contact must not prematurely resolve political disposition")
    if "pirate" in serialized.lower():
        errors.append("Kobold first contact must not fold Pirate Kobolds into the principal-Den contact state")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Kobold production campaign contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1

    print("Kobold production first-contact contract: PASS")
    print("provider identity: installed kobolds:kobold_captain plus designated principal-Den anchor")
    print("political state: contact only, disposition unresolved")
    print("Pirate Kobolds: not generalized into principal-Den contact")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
