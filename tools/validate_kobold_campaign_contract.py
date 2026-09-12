#!/usr/bin/env python3
"""Guard the production Kobold and Sea Dweller first-contact slices."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
INDEX = DEFINITIONS / "index.json"
FOUNDATION_FACT = "overlord_reign:reign/initial_foundation_established"

KOBOLD_QUEST = DEFINITIONS / "quests/campaign/civilizations/kobolds/first_contact.json"
KOBOLD_PATH = "campaign/civilizations/kobolds/first_contact.json"
KOBOLD_CONTACT_FACT = "overlord_reign:civilizations/kobolds/contact_established"
KOBOLD_INTEGRATION = ROOT / "docs/KOBOLDS_PROVIDER_INTEGRATION.md"
KOBOLD_PROTOCOL = ROOT / "docs/KOBOLD_CIVILIZATION_TEST_PROTOCOL.md"

SEA_QUEST = DEFINITIONS / "quests/campaign/civilizations/sea_dwellers/first_contact.json"
SEA_PATH = "campaign/civilizations/sea_dwellers/first_contact.json"
SEA_CONTACT_FACT = "overlord_reign:civilizations/sea_dwellers/contact_established"
SEA_INTEGRATION = ROOT / "docs/SEA_DWELLERS_PROVIDER_INTEGRATION.md"
SEA_PROTOCOL = ROOT / "docs/SEA_DWELLER_CIVILIZATION_TEST_PROTOCOL.md"


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


def validate_common_contact(
    quest: dict[str, Any],
    errors: list[str],
    *,
    label: str,
    civilization: str,
    contact_fact: str,
    anchor_tag: str,
) -> None:
    if quest.get("include_in_main") is not True:
        errors.append(f"{label} must remain part of the main campaign")
    if quest.get("show_popup_on_unlock") is not False:
        errors.append(f"{label} must remain an in-world provider interaction")
    if quest.get("objectives") != []:
        errors.append(f"{label} must remain provider-native with no artificial journal objective")

    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append(f"{label} must remain provider-bound")
        return

    if provider.get("scoreboard_tags") != [anchor_tag]:
        errors.append(f"{label} must remain scoped to its designated local anchor NPC")
    if provider.get("civilization") != civilization:
        errors.append(f"{label} civilization identity changed unexpectedly")
    if provider.get("required_facts") != [FOUNDATION_FACT]:
        errors.append(f"{label} must remain gated behind the semi-open campaign foundation")
    if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
        errors.append(f"{label} must remain bound to the exact issuing provider")
    if "required_dispositions" in provider:
        errors.append(f"{label} must not require a pre-resolved political disposition")

    dialogue = provider.get("dialogue")
    if not isinstance(dialogue, dict):
        errors.append(f"{label} must retain authored provider dialogue")
    else:
        for phase in ("offer", "ready_to_turn_in", "completed"):
            if not isinstance(dialogue.get(phase), str) or not dialogue.get(phase, "").strip():
                errors.append(f"{label} must retain non-empty {phase} dialogue")
        if "in_progress" in dialogue:
            errors.append(f"{label} must not insert a fake in-progress phase")

    prerequisites = quest.get("prerequisites", [])
    if not isinstance(prerequisites, list) or len(prerequisites) != 1:
        errors.append(f"{label} must retain exactly one campaign-foundation prerequisite")
    else:
        prerequisite = prerequisites[0]
        if not isinstance(prerequisite, dict) or not (
            prerequisite.get("type") == "questlog:fact"
            and prerequisite.get("fact") == FOUNDATION_FACT
            and prerequisite.get("required_amount") == 1
        ):
            errors.append(f"{label} must remain gated by the established foundation fact")

    rewards = quest.get("rewards", [])
    if not isinstance(rewards, list) or len(rewards) != 1:
        errors.append(f"{label} must write exactly one contact-history fact")
    else:
        reward = rewards[0]
        if not isinstance(reward, dict) or not (
            reward.get("type") == "questlog:set_fact"
            and reward.get("fact") == contact_fact
            and reward.get("auto_claim") is True
        ):
            errors.append(f"{label} must persist only its designated-anchor contact fact")

    serialized = json.dumps(quest, sort_keys=True)
    if "questlog:set_disposition" in serialized:
        errors.append(f"{label} must not prematurely resolve political disposition")
    if "location" in provider:
        errors.append(f"{label} must not fabricate final-world coordinates before world integration")


def collect_errors() -> list[str]:
    errors: list[str] = []

    for path, message in (
        (KOBOLD_INTEGRATION, "exact installed Kobolds provider audit is missing"),
        (KOBOLD_PROTOCOL, "Kobold production runtime protocol is missing"),
        (SEA_INTEGRATION, "exact installed Sea Dwellers provider audit is missing"),
        (SEA_PROTOCOL, "Sea Dweller production runtime protocol is missing"),
    ):
        if not path.exists():
            errors.append(f"{path.relative_to(ROOT)}: {message}")

    index = load(INDEX, errors)
    bundled = index.get("quests", [])
    for path, label in ((KOBOLD_PATH, "Kobold"), (SEA_PATH, "Sea Dweller")):
        if not isinstance(bundled, list) or path not in bundled:
            errors.append(f"bundled definition index must include the production {label} first-contact quest")

    kobold = load(KOBOLD_QUEST, errors)
    validate_common_contact(
        kobold,
        errors,
        label="Kobold first contact",
        civilization="overlord_reign:kobolds",
        contact_fact=KOBOLD_CONTACT_FACT,
        anchor_tag="overlord_anchor:kobold_main_captain",
    )
    kobold_provider = kobold.get("provider", {})
    if isinstance(kobold_provider, dict):
        if kobold_provider.get("entity_types") != ["kobolds:kobold_captain"]:
            errors.append("Kobold first contact must target only the installed Kobold Captain entity type")
        if "entity_type_tags" in kobold_provider:
            errors.append("Kobold first contact must not broaden into an unsupported Kobold entity family tag")
        if "role" in kobold_provider:
            errors.append("Kobold Captain must not acquire an invented native profession or role selector")
    if "pirate" in json.dumps(kobold, sort_keys=True).lower():
        errors.append("Kobold first contact must not fold Pirate Kobolds into the principal-Den contact state")

    sea = load(SEA_QUEST, errors)
    validate_common_contact(
        sea,
        errors,
        label="Sea Dweller first contact",
        civilization="overlord_reign:sea_dwellers",
        contact_fact=SEA_CONTACT_FACT,
        anchor_tag="overlord_anchor:sea_dweller_main_elder",
    )
    sea_provider = sea.get("provider", {})
    if isinstance(sea_provider, dict):
        if sea_provider.get("entity_type_tags") != ["seadwellers:mermorphs"]:
            errors.append("Sea Dweller first contact must use the exact installed #seadwellers:mermorphs entity family")
        if "entity_types" in sea_provider:
            errors.append("Sea Dweller first contact must not arbitrarily pin the Elder to one Mermorph subtype")
        if sea_provider.get("role") != "sea_elder":
            errors.append("Sea Dweller first contact must retain the authored Sea Elder social role")

    sea_serialized = json.dumps(sea, sort_keys=True).lower()
    if "dragon" in sea_serialized:
        errors.append("Sea Dweller first contact must not reintroduce the removed Ocean Dragon assumption")

    if SEA_INTEGRATION.exists():
        integration = SEA_INTEGRATION.read_text(encoding="utf-8")
        if "6cf9dd9c5ba8dfb9644b0598b1b3453fb3b4cefc1ffa3ae676e4be82309d90f5" not in integration:
            errors.append("Sea Dwellers integration audit must retain the exact installed 2.9.9 JAR SHA-256")
        if "seadwellers:mermorphs" not in integration:
            errors.append("Sea Dwellers integration audit must retain the source-owned Mermorph family tag")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Civilization production contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1

    print("Kobold and Sea Dweller production first-contact contracts: PASS")
    print("Kobold provider: exact installed Captain plus designated principal-Den anchor")
    print("Sea Dweller provider: #seadwellers:mermorphs plus principal-village anchor and Sea Elder role")
    print("political state: contact only, dispositions unresolved")
    print("Ocean Dragon assumption: excluded from Sea Dweller production content")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
