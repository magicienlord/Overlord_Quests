#!/usr/bin/env python3
"""Guard the source-backed Piglin Chieftain first-contact contract."""
from __future__ import annotations

import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST_PATH = "campaign/civilizations/piglins/first_contact.json"
FOUNDATION_FACT = "overlord_reign:reign/initial_foundation_established"
CONTACT_FACT = "overlord_reign:civilizations/piglins/contact_established"
ANCHOR_TAG = "overlord_anchor:piglin_main_chieftain"
PROTECTED_TAG = "overlord_quest_protected"


def main() -> int:
    errors: list[str] = []

    index = json.loads((DEFINITIONS / "index.json").read_text(encoding="utf-8"))
    if QUEST_PATH not in index.get("quests", []):
        errors.append("production definition index must include the Piglin first-contact quest")

    quest = json.loads((DEFINITIONS / "quests" / QUEST_PATH).read_text(encoding="utf-8"))
    if quest.get("include_in_main") is not True:
        errors.append("Piglin first contact must remain in the main campaign")
    if quest.get("sort_order") != 180:
        errors.append("Piglin first contact sort order must remain 180")
    if quest.get("show_popup_on_unlock") is not False:
        errors.append("Piglin first contact must remain an in-world provider interaction")
    if quest.get("objectives") != []:
        errors.append("Piglin first contact must remain provider-native with no artificial journal objective")

    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append("Piglin first contact must remain provider-bound")
        provider = {}
    if provider.get("entity_types") != ["minecraft:piglin_brute"]:
        errors.append("Piglin first contact must target only the designated vanilla Piglin Brute Chieftain type")
    if provider.get("scoreboard_tags") != [ANCHOR_TAG]:
        errors.append("Piglin first contact must remain scoped to the designated local Chieftain")
    if provider.get("civilization") != "overlord_reign:piglins":
        errors.append("Piglin first contact civilization identity changed unexpectedly")
    if provider.get("required_facts") != [FOUNDATION_FACT]:
        errors.append("Piglin first contact must remain gated behind the campaign foundation")
    if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
        errors.append("Piglin first contact must remain bound to the exact issuing Chieftain")
    for forbidden in ("entity_type_tags", "role", "required_head_items", "required_dispositions", "location"):
        if forbidden in provider:
            errors.append(f"Piglin first contact must not add unsupported provider selector: {forbidden}")

    prerequisites = quest.get("prerequisites", [])
    if len(prerequisites) != 1 or prerequisites[0].get("type") != "questlog:fact" or prerequisites[0].get("fact") != FOUNDATION_FACT:
        errors.append("Piglin first contact must retain the exact foundation prerequisite")

    rewards = quest.get("rewards", [])
    if len(rewards) != 1 or rewards[0].get("type") != "questlog:set_fact" or rewards[0].get("fact") != CONTACT_FACT or rewards[0].get("auto_claim") is not True:
        errors.append("Piglin first contact must write only its contact-history fact")
    if "questlog:set_disposition" in json.dumps(quest, sort_keys=True):
        errors.append("Piglin first contact must not equate an audience with political submission")

    bridge_path = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/PiglinChieftainAudienceBridge.java"
    service_path = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderService.java"
    interaction_path = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderInteraction.java"
    forwarder_path = ROOT / "forge/src/main/java/org/infernalstudios/questlog/QuestlogForgeEventForwarder.java"
    protocol_path = ROOT / "docs/PIGLIN_CIVILIZATION_TEST_PROTOCOL.md"

    if not bridge_path.exists():
        errors.append("Piglin Chieftain audience bridge is missing")
    else:
        bridge = bridge_path.read_text(encoding="utf-8")
        for fragment in (
            'new ResourceLocation("minecraft", "piglin_brute")',
            'new ResourceLocation("overlord_reign", "piglins")',
            f'ANCHOR_TAG = "{ANCHOR_TAG}"',
            "QuestAnchorProtection.isProtected",
            "ArmorMaterials.GOLD",
            "PEACEFUL_STATES",
        ):
            if fragment not in bridge:
                errors.append(f"Piglin audience bridge is missing required narrow-boundary fragment: {fragment}")

    for path, fragment, label in (
        (service_path, "PiglinChieftainAudienceBridge.allowsProviderInteraction", "provider eligibility"),
        (interaction_path, "PiglinChieftainAudienceBridge.allowsProviderInteraction", "provider menu"),
        (forwarder_path, "PiglinChieftainAudienceBridge.shouldSuppressPlayerTarget", "Forge target suppression"),
    ):
        if not path.exists() or fragment not in path.read_text(encoding="utf-8"):
            errors.append(f"Piglin audience bridge is not wired into {label}")

    if not protocol_path.exists():
        errors.append("Piglin civilization runtime protocol is missing")
    else:
        protocol = protocol_path.read_text(encoding="utf-8")
        for fragment in (ANCHOR_TAG, PROTECTED_TAG, "gold", "untagged Piglin Brute", "no disposition"):
            if fragment.lower() not in protocol.lower():
                errors.append(f"Piglin runtime protocol is missing boundary evidence: {fragment}")

    if errors:
        print(f"Piglin campaign contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1

    print("Piglin Chieftain campaign contract: PASS")
    print("provider: exact protected minecraft:piglin_brute local Chieftain anchor")
    print("audience: gold armor or later peaceful disposition, exact anchor only")
    print("political state: contact only, no first-contact disposition")
    print("native boundary: ordinary Piglins and Piglin Brutes remain unchanged")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
