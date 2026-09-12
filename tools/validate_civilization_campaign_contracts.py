#!/usr/bin/env python3
"""Guard source-backed production civilization first-contact slices."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
INDEX = DEFINITIONS / "index.json"
FOUNDATION_FACT = "overlord_reign:reign/initial_foundation_established"


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
) -> dict[str, Any]:
    if quest.get("include_in_main") is not True:
        errors.append(f"{label} must remain part of the main campaign")
    if quest.get("show_popup_on_unlock") is not False:
        errors.append(f"{label} must remain an in-world provider interaction")
    if quest.get("objectives") != []:
        errors.append(f"{label} must remain provider-native with no artificial journal objective")

    provider = quest.get("provider")
    if not isinstance(provider, dict):
        errors.append(f"{label} must remain provider-bound")
        return {}

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
    if "location" in provider:
        errors.append(f"{label} must not fabricate final-world coordinates before world integration")

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

    if "questlog:set_disposition" in json.dumps(quest, sort_keys=True):
        errors.append(f"{label} must not prematurely resolve political disposition")

    return provider


def require_docs(errors: list[str], pairs: list[tuple[Path, str]]) -> None:
    for path, message in pairs:
        if not path.exists():
            errors.append(f"{path.relative_to(ROOT)}: {message}")


def collect_errors() -> list[str]:
    errors: list[str] = []

    kobold_path = "campaign/civilizations/kobolds/first_contact.json"
    sea_path = "campaign/civilizations/sea_dwellers/first_contact.json"
    dwarf_path = "campaign/civilizations/dwarves/first_contact.json"

    require_docs(errors, [
        (ROOT / "docs/KOBOLDS_PROVIDER_INTEGRATION.md", "exact installed Kobolds provider audit is missing"),
        (ROOT / "docs/KOBOLD_CIVILIZATION_TEST_PROTOCOL.md", "Kobold production runtime protocol is missing"),
        (ROOT / "docs/SEA_DWELLERS_PROVIDER_INTEGRATION.md", "exact installed Sea Dwellers provider audit is missing"),
        (ROOT / "docs/SEA_DWELLER_CIVILIZATION_TEST_PROTOCOL.md", "Sea Dweller production runtime protocol is missing"),
        (ROOT / "docs/DWARVEN_FORGE_PROVIDER_INTEGRATION.md", "exact installed Dwarven Forge provider audit is missing"),
        (ROOT / "docs/DWARVEN_CIVILIZATION_TEST_PROTOCOL.md", "Dwarven production runtime protocol is missing"),
    ])

    index = load(INDEX, errors)
    bundled = index.get("quests", [])
    for path, label in ((kobold_path, "Kobold"), (sea_path, "Sea Dweller"), (dwarf_path, "Dwarven")):
        if not isinstance(bundled, list) or path not in bundled:
            errors.append(f"bundled definition index must include the production {label} first-contact quest")

    kobold = load(DEFINITIONS / f"quests/{kobold_path}", errors)
    kobold_provider = validate_common_contact(
        kobold,
        errors,
        label="Kobold first contact",
        civilization="overlord_reign:kobolds",
        contact_fact="overlord_reign:civilizations/kobolds/contact_established",
        anchor_tag="overlord_anchor:kobold_main_captain",
    )
    if kobold_provider:
        if kobold_provider.get("entity_types") != ["kobolds:kobold_captain"]:
            errors.append("Kobold first contact must target only the installed Kobold Captain entity type")
        if "entity_type_tags" in kobold_provider:
            errors.append("Kobold first contact must not broaden into an unsupported Kobold entity family tag")
        if "role" in kobold_provider:
            errors.append("Kobold Captain must not acquire an invented native profession or role selector")
    if "pirate" in json.dumps(kobold, sort_keys=True).lower():
        errors.append("Kobold first contact must not fold Pirate Kobolds into the principal-Den contact state")

    sea = load(DEFINITIONS / f"quests/{sea_path}", errors)
    sea_provider = validate_common_contact(
        sea,
        errors,
        label="Sea Dweller first contact",
        civilization="overlord_reign:sea_dwellers",
        contact_fact="overlord_reign:civilizations/sea_dwellers/contact_established",
        anchor_tag="overlord_anchor:sea_dweller_main_elder",
    )
    if sea_provider:
        if sea_provider.get("entity_type_tags") != ["seadwellers:mermorphs"]:
            errors.append("Sea Dweller first contact must use the exact installed #seadwellers:mermorphs entity family")
        if "entity_types" in sea_provider:
            errors.append("Sea Dweller first contact must not arbitrarily pin the Elder to one Mermorph subtype")
        if sea_provider.get("role") != "sea_elder":
            errors.append("Sea Dweller first contact must retain the authored Sea Elder social role")
    if "dragon" in json.dumps(sea, sort_keys=True).lower():
        errors.append("Sea Dweller first contact must not reintroduce the removed Ocean Dragon assumption")

    dwarf = load(DEFINITIONS / f"quests/{dwarf_path}", errors)
    dwarf_provider = validate_common_contact(
        dwarf,
        errors,
        label="Dwarven first contact",
        civilization="overlord_reign:dwarves",
        contact_fact="overlord_reign:civilizations/dwarves/contact_established",
        anchor_tag="overlord_anchor:dwarf_forge_thane",
    )
    if dwarf_provider:
        if dwarf_provider.get("entity_types") != ["dwarven_forge:dwarf"]:
            errors.append("Dwarven first contact must target only the installed Dwarf entity type")
        if "entity_type_tags" in dwarf_provider:
            errors.append("Dwarven first contact must not broaden into an unsupported entity family tag")
        if dwarf_provider.get("role") != "minecraft:toolsmith":
            errors.append("Forge-Thane must remain a source-native Dwarven Forger using minecraft:toolsmith")

    sea_integration = ROOT / "docs/SEA_DWELLERS_PROVIDER_INTEGRATION.md"
    if sea_integration.exists():
        text = sea_integration.read_text(encoding="utf-8")
        if "6cf9dd9c5ba8dfb9644b0598b1b3453fb3b4cefc1ffa3ae676e4be82309d90f5" not in text:
            errors.append("Sea Dwellers integration audit must retain the exact installed 2.9.9 JAR SHA-256")
        if "seadwellers:mermorphs" not in text:
            errors.append("Sea Dwellers integration audit must retain the source-owned Mermorph family tag")

    dwarf_integration = ROOT / "docs/DWARVEN_FORGE_PROVIDER_INTEGRATION.md"
    if dwarf_integration.exists():
        text = dwarf_integration.read_text(encoding="utf-8")
        if "f43bbe67330f7e92756b4665f6d24fd2d267aeca35cfeb4d4c9ee3cb5423d85b" not in text:
            errors.append("Dwarven Forge integration audit must retain the exact installed 1.0.0 JAR SHA-256")
        for fragment in ("dwarven_forge:dwarf", "minecraft:toolsmith", "Dwarven Forger"):
            if fragment not in text:
                errors.append(f"Dwarven Forge integration audit is missing source-backed identity: {fragment}")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Civilization production contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1

    print("Source-backed civilization first-contact contracts: PASS")
    print("Kobold provider: exact installed Captain plus designated principal-Den anchor")
    print("Sea Dweller provider: #seadwellers:mermorphs plus principal-village anchor and Sea Elder role")
    print("Dwarven provider: dwarven_forge:dwarf plus native minecraft:toolsmith and Forge-Thane anchor")
    print("political state: contact only, dispositions unresolved")
    print("removed assumptions: Ocean Dragon excluded from Sea Dweller content")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
