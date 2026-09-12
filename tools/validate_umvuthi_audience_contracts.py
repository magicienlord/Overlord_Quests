#!/usr/bin/env python3
"""Guard the source-faithful Umvuthi peaceful-audience integration."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST = DEFINITIONS / "quests/campaign/civilizations/umvuthana/first_contact.json"
INDEX = DEFINITIONS / "index.json"
RULE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderRule.java"
SERVICE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderService.java"
BRIDGE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/UmvuthiAudienceBridge.java"
FORWARDER = ROOT / "forge/src/main/java/org/infernalstudios/questlog/QuestlogForgeEventForwarder.java"
INTEGRATION = ROOT / "docs/UMVUTHI_AUDIENCE_INTEGRATION.md"
PROTOCOL = ROOT / "docs/UMVUTHANA_CIVILIZATION_TEST_PROTOCOL.md"

QUEST_PATH = "campaign/civilizations/umvuthana/first_contact.json"
FOUNDATION_FACT = "overlord_reign:reign/initial_foundation_established"
CONTACT_FACT = "overlord_reign:civilizations/umvuthana/contact_established"
CIVILIZATION = "overlord_reign:umvuthana"
NEUTRAL = "overlord_reign:neutral"
MASKS = {
    "mowziesmobs:umvuthana_mask_fury",
    "mowziesmobs:umvuthana_mask_fear",
    "mowziesmobs:umvuthana_mask_rage",
    "mowziesmobs:umvuthana_mask_bliss",
    "mowziesmobs:umvuthana_mask_misery",
    "mowziesmobs:umvuthana_mask_faith",
}


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


def require(path: Path, fragment: str, label: str, errors: list[str]) -> None:
    if not path.exists():
        errors.append(f"{path.relative_to(ROOT)}: missing {label}")
        return
    if fragment not in path.read_text(encoding="utf-8"):
        errors.append(f"{path.relative_to(ROOT)}: missing {label}")


def collect_errors() -> list[str]:
    errors: list[str] = []

    quest = load(QUEST, errors)
    index = load(INDEX, errors)
    bundled = index.get("quests", [])
    if not isinstance(bundled, list) or QUEST_PATH not in bundled:
        errors.append("bundled definition index must include the Umvuthana first-audience quest")

    if quest.get("include_in_main") is not True or quest.get("show_popup_on_unlock") is not False:
        errors.append("Umvuthana first audience must remain an in-world main-campaign provider interaction")
    if quest.get("objectives") != []:
        errors.append("Umvuthana first audience must remain a conversation-only provider milestone")

    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append("Umvuthana first audience must remain provider-bound")
    else:
        if provider.get("entity_types") != ["mowziesmobs:umvuthi"]:
            errors.append("Umvuthana first audience must target only the exact installed Umvuthi entity")
        if "entity_type_tags" in provider:
            errors.append("Umvuthana first audience must not broaden to an Umvuthana family tag")
        if provider.get("scoreboard_tags") != ["overlord_anchor:umvuthana_main_umvuthi"]:
            errors.append("Umvuthana first audience must remain scoped to the designated canonical Grove")
        if provider.get("civilization") != CIVILIZATION:
            errors.append("Umvuthana first audience civilization identity changed unexpectedly")
        if provider.get("required_facts") != [FOUNDATION_FACT]:
            errors.append("Umvuthana first audience must remain gated behind the semi-open campaign foundation")
        if set(provider.get("required_head_items", [])) != MASKS or len(provider.get("required_head_items", [])) != 6:
            errors.append("Umvuthana first audience must accept exactly the six installed Mowzie 1.8.2 Umvuthana masks")
        if provider.get("lock_to_provider") is not True or provider.get("turn_in") != "same_provider":
            errors.append("Umvuthana first audience must remain bound to the exact issuing Umvuthi")
        if "location" in provider:
            errors.append("Umvuthana first audience must not fabricate final Grove coordinates")
        if "required_dispositions" in provider:
            errors.append("Umvuthana first audience must begin from unresolved political state rather than requiring a resolved disposition")

    prerequisites = quest.get("prerequisites", [])
    if not isinstance(prerequisites, list) or len(prerequisites) != 1:
        errors.append("Umvuthana first audience must retain exactly one foundation prerequisite")
    else:
        prerequisite = prerequisites[0]
        if not isinstance(prerequisite, dict) or not (
            prerequisite.get("type") == "questlog:fact"
            and prerequisite.get("fact") == FOUNDATION_FACT
            and prerequisite.get("required_amount") == 1
        ):
            errors.append("Umvuthana first audience foundation prerequisite changed unexpectedly")

    rewards = quest.get("rewards", [])
    if not isinstance(rewards, list) or len(rewards) != 2:
        errors.append("Umvuthana first audience must write contact history and establish NEUTRAL disposition")
    else:
        fact_rewards = [r for r in rewards if isinstance(r, dict) and r.get("type") == "questlog:set_fact"]
        disposition_rewards = [r for r in rewards if isinstance(r, dict) and r.get("type") == "questlog:set_disposition"]
        if len(fact_rewards) != 1 or fact_rewards[0].get("fact") != CONTACT_FACT or fact_rewards[0].get("auto_claim") is not True:
            errors.append("Umvuthana first audience must auto-record exactly its local contact fact")
        if len(disposition_rewards) != 1 or not (
            disposition_rewards[0].get("civilization") == CIVILIZATION
            and disposition_rewards[0].get("state") == NEUTRAL
            and disposition_rewards[0].get("auto_claim") is True
        ):
            errors.append("Umvuthana first audience must set only the canonical Grove's disposition to overlord_reign:neutral")

    for path, label in ((INTEGRATION, "Umvuthi source audit"), (PROTOCOL, "Umvuthana runtime protocol")):
        if not path.exists():
            errors.append(f"{path.relative_to(ROOT)}: missing {label}")

    require(RULE, 'idSet(json, "required_head_items")', "required-head-item definition parsing", errors)
    require(RULE, "public boolean matchesPlayer(ServerPlayer player)", "server-player provider eligibility gate", errors)
    require(RULE, "player.getItemBySlot(EquipmentSlot.HEAD)", "head-slot equipment lookup", errors)
    require(SERVICE, "UmvuthiAudienceBridge.allowsProviderInteraction(provider, player)", "source-native Umvuthi offence eligibility bridge", errors)
    if SERVICE.exists() and SERVICE.read_text(encoding="utf-8").count("UmvuthiAudienceBridge.allowsProviderInteraction(provider, player)") < 2:
        errors.append("QuestProviderService.java: Umvuthi offence gate must protect both acceptance and turn-in")

    require(BRIDGE, 'UMVUTHI = new ResourceLocation("mowziesmobs", "umvuthi")', "exact Umvuthi registry boundary", errors)
    require(BRIDGE, 'ANCHOR_TAG = "overlord_anchor:umvuthana_main_umvuthi"', "canonical Grove anchor boundary", errors)
    require(BRIDGE, 'getMethod("getMisbehavedPlayerId")', "reflection-only native misbehaviour lookup", errors)
    require(BRIDGE, "Boolean.FALSE.equals(misbehaving)", "fail-closed target suppression", errors)
    require(BRIDGE, "PEACEFUL_STATES = Set.of(NEUTRAL, SUBJUGATED)", "local peaceful disposition boundary", errors)
    require(FORWARDER, "LivingChangeTargetEvent", "Forge target-change hook", errors)
    require(FORWARDER, "UmvuthiAudienceBridge.shouldSuppressPlayerTarget", "narrow Umvuthi target suppression handoff", errors)
    require(FORWARDER, "event.setCanceled(true)", "cancelable ordinary target acquisition", errors)

    for java_path in (RULE, SERVICE, BRIDGE, FORWARDER):
        if java_path.exists() and "com.bobmowzie" in java_path.read_text(encoding="utf-8"):
            errors.append(f"{java_path.relative_to(ROOT)}: Mowzie compatibility must not introduce a hard class-link dependency")

    if INTEGRATION.exists():
        audit = INTEGRATION.read_text(encoding="utf-8")
        for fragment in (
            "e8ce1768cda6f1e1fadd2321c92921b473bd0cee45ba4b8387fb30f8326cb31c",
            "getMisbehavedPlayerId()",
            "mowziesmobs:umvuthi",
        ):
            if fragment not in audit:
                errors.append(f"UMVUTHI_AUDIENCE_INTEGRATION.md: missing exact installed-source evidence: {fragment}")
        for mask in MASKS:
            if mask not in audit:
                errors.append(f"UMVUTHI_AUDIENCE_INTEGRATION.md: missing installed mask id {mask}")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Umvuthi audience contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1

    print("Umvuthi peaceful-audience contracts: PASS")
    print("pre-audience: exact native mask gate and native hostility preserved")
    print("offender state: Mowzie getMisbehavedPlayerId remains authoritative")
    print("post-audience: only designated Grove Umvuthi ordinary targeting is suppressed in NEUTRAL/SUBJUGATED state")
    print("dependency boundary: reflection/registry only, no hard Mowzie class link")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
