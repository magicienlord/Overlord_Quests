#!/usr/bin/env python3
"""Validate narrow retrospective sequence-break tracking contracts."""
from pathlib import Path
import sys

import validate_campaign_opening_contracts as opening_contracts

ROOT = Path(__file__).resolve().parents[1]
ENTITY_OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/entity/EntityKillStatObjective.java"
ITEM_OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/item/ItemCraftStatObjective.java"
STRUCTURE_OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/misc/VisitStructureHistoryObjective.java"
REGISTRY = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/QuestObjectiveRegistry.java"
VALIDATOR = ROOT / "tools/validate_overlord_quest_examples.py"
ENTITY_FIXTURE = ROOT / "examples/questlog/quests/overlord_sequence_break_dev.json"
ITEM_FIXTURE = ROOT / "examples/questlog/quests/overlord_item_craft_sequence_break_dev.json"
STRUCTURE_FIXTURE = ROOT / "examples/questlog/quests/overlord_structure_history_dev.json"


def collect_errors() -> list[str]:
    errors: list[str] = []

    def require(path: Path, fragment: str, label: str) -> None:
        text = path.read_text(encoding="utf-8")
        if fragment not in text:
            errors.append(f"{path.relative_to(ROOT)}: missing {label}")

    require(ENTITY_OBJECTIVE, "Stats.ENTITY_KILLED.get(this.entityType)", "vanilla persistent entity-kill statistic lookup")
    require(ENTITY_OBJECTIVE, "player.getStats().getValue", "server-player entity statistics authority")
    require(ENTITY_OBJECTIVE, "this.ticksUntilCheck = 20", "bounded entity polling cadence")
    require(ENTITY_OBJECTIVE, "!this.isActiveForPlayer(player)", "active entity objective lifecycle guard")
    require(ENTITY_OBJECTIVE, "entityText.startsWith(\"#\")", "entity-tag rejection")
    require(ENTITY_OBJECTIVE, "BuiltInRegistries.ENTITY_TYPE.containsKey(entityId)", "exact registered entity validation")

    require(ITEM_OBJECTIVE, "Stats.ITEM_CRAFTED.get(this.item)", "vanilla persistent item-craft statistic lookup")
    require(ITEM_OBJECTIVE, "player.getStats().getValue", "server-player item statistics authority")
    require(ITEM_OBJECTIVE, "this.ticksUntilCheck = 20", "bounded item polling cadence")
    require(ITEM_OBJECTIVE, "!this.isActiveForPlayer(player)", "active item objective lifecycle guard")
    require(ITEM_OBJECTIVE, "itemText.startsWith(\"#\")", "item-tag rejection")
    require(ITEM_OBJECTIVE, "BuiltInRegistries.ITEM.containsKey(itemId)", "exact registered item validation")

    require(STRUCTURE_OBJECTIVE, "class VisitStructureHistoryObjective", "structure-history objective implementation")
    require(STRUCTURE_OBJECTIVE, "private boolean seen = false", "persistent structure observation state")
    require(STRUCTURE_OBJECTIVE, "this.ticksUntilCheck = 20", "bounded structure polling cadence")
    require(STRUCTURE_OBJECTIVE, "!this.isActiveForPlayer(player)", "active structure objective lifecycle guard")
    require(STRUCTURE_OBJECTIVE, ".getStructureWithPieceAt(player.blockPosition(), target)", "exact current structure-piece detection")
    require(STRUCTURE_OBJECTIVE, 'data.putBoolean("seen", this.seen)', "structure-history persistence")
    require(STRUCTURE_OBJECTIVE, 'data.getBoolean("seen")', "structure-history reload")
    require(STRUCTURE_OBJECTIVE, "this.getParent().markForUpdate()", "structure-history quest synchronization")

    require(REGISTRY, 'new ResourceLocation("questlog", "entity_kill_stat")', "entity_kill_stat objective registration")
    require(REGISTRY, 'new EditorMetadata("entity", "Exact Entity ID:"', "exact-entity editor metadata")
    require(REGISTRY, 'new ResourceLocation("questlog", "item_craft_stat")', "item_craft_stat objective registration")
    require(REGISTRY, 'new EditorMetadata("item", "Exact Item ID:"', "exact-item editor metadata")
    require(REGISTRY, 'new ResourceLocation("questlog", "visit_structure_history")', "visit_structure_history objective registration")
    require(REGISTRY, 'new EditorMetadata("structure", "Exact Structure ID:"', "exact-structure editor metadata")

    require(VALIDATOR, '"questlog:entity_kill_stat"', "entity-kill definition validator registration")
    require(VALIDATOR, "core.validate_resource_id(entry[\"entity\"]", "exact entity resource-id schema validation")
    require(VALIDATOR, '"questlog:item_craft_stat"', "item-craft definition validator registration")
    require(VALIDATOR, "core.validate_resource_id(entry[\"item\"]", "exact item resource-id schema validation")
    require(VALIDATOR, '"questlog:visit_structure_history"', "structure-history definition validator registration")
    require(VALIDATOR, "core.validate_resource_id(entry[\"structure\"]", "exact structure resource-id schema validation")

    require(ENTITY_FIXTURE, '"type": "questlog:entity_kill_stat"', "entity development fixture objective")
    require(ENTITY_FIXTURE, '"entity": "minecraft:zombie"', "entity development fixture exact entity")
    require(ITEM_FIXTURE, '"type": "questlog:item_craft_stat"', "item development fixture objective")
    require(ITEM_FIXTURE, '"item": "minecraft:crafting_table"', "item development fixture exact item")
    require(STRUCTURE_FIXTURE, '"type": "questlog:visit_structure_history"', "structure development fixture objective")
    require(STRUCTURE_FIXTURE, '"structure": "minecraft:mineshaft"', "structure development fixture exact structure")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Sequence-break tracking contract validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("OVERLORD sequence-break tracking contracts: PASS")
    return opening_contracts.main()


if __name__ == "__main__":
    raise SystemExit(main())
