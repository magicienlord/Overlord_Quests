#!/usr/bin/env python3
"""Validate narrow retrospective sequence-break tracking contracts."""
from pathlib import Path
import sys

import validate_campaign_opening_contracts as opening_contracts
import validate_statistic_trigger_contracts as statistic_contracts

ROOT = Path(__file__).resolve().parents[1]
ENTITY_OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/entity/EntityKillStatObjective.java"
ENTITY_HISTORY_OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/entity/EntityKillHistoryObjective.java"
ITEM_OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/item/ItemCraftStatObjective.java"
DIMENSION_OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/misc/VisitDimensionHistoryObjective.java"
POSITION_OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/misc/VisitPositionHistoryObjective.java"
STRUCTURE_OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/misc/VisitStructureHistoryObjective.java"
DRAGON_OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/misc/EnderDragonDefeatedObjective.java"
REGISTRY = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/QuestObjectiveRegistry.java"
VALIDATOR = ROOT / "tools/validate_overlord_quest_examples.py"
ENTITY_FIXTURE = ROOT / "examples/questlog/quests/overlord_sequence_break_dev.json"
ENTITY_HISTORY_FIXTURE = ROOT / "examples/questlog/quests/overlord_entity_kill_history_dev.json"
ITEM_FIXTURE = ROOT / "examples/questlog/quests/overlord_item_craft_sequence_break_dev.json"
DIMENSION_FIXTURE = ROOT / "examples/questlog/quests/overlord_dimension_history_dev.json"
POSITION_FIXTURE = ROOT / "examples/questlog/quests/overlord_position_history_dev.json"
STRUCTURE_FIXTURE = ROOT / "examples/questlog/quests/overlord_structure_history_dev.json"
DRAGON_FIXTURE = ROOT / "examples/questlog/quests/overlord_ender_dragon_defeated_dev.json"


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

    require(ENTITY_HISTORY_OBJECTIVE, "class EntityKillHistoryObjective", "tagged entity-kill history implementation")
    require(ENTITY_HISTORY_OBJECTIVE, "event.damageSource.getEntity() instanceof ServerPlayer player", "player-attributed tagged kill authority")
    require(ENTITY_HISTORY_OBJECTIVE, "!this.isActiveForPlayer(player)", "active tagged history lifecycle guard")
    require(ENTITY_HISTORY_OBJECTIVE, "this.test(event.entity)", "full EntityMatcher tagged kill test")
    require(ENTITY_HISTORY_OBJECTIVE, "private boolean seen = false", "persistent tagged kill observation state")
    require(ENTITY_HISTORY_OBJECTIVE, 'data.putBoolean("seen", this.seen)', "tagged kill history persistence")
    require(ENTITY_HISTORY_OBJECTIVE, 'data.getBoolean("seen")', "tagged kill history reload")
    require(ENTITY_HISTORY_OBJECTIVE, "this.isPartOfOptionalObjective()", "optional tagged kill history membership check")
    require(ENTITY_HISTORY_OBJECTIVE, "this.getParent().hasSentCompletion", "optional tagged kill post-completion freeze")

    require(ITEM_OBJECTIVE, "Stats.ITEM_CRAFTED.get(this.item)", "vanilla persistent item-craft statistic lookup")
    require(ITEM_OBJECTIVE, "player.getStats().getValue", "server-player item statistics authority")
    require(ITEM_OBJECTIVE, "this.ticksUntilCheck = 20", "bounded item polling cadence")
    require(ITEM_OBJECTIVE, "!this.isActiveForPlayer(player)", "active item objective lifecycle guard")
    require(ITEM_OBJECTIVE, "itemText.startsWith(\"#\")", "item-tag rejection")
    require(ITEM_OBJECTIVE, "BuiltInRegistries.ITEM.containsKey(itemId)", "exact registered item validation")

    require(DIMENSION_OBJECTIVE, "class VisitDimensionHistoryObjective", "dimension-history objective implementation")
    require(DIMENSION_OBJECTIVE, "private boolean seen = false", "persistent dimension observation state")
    require(DIMENSION_OBJECTIVE, "this.ticksUntilCheck = 20", "bounded dimension polling cadence")
    require(DIMENSION_OBJECTIVE, "!this.isActiveForPlayer(player)", "active dimension objective lifecycle guard")
    require(DIMENSION_OBJECTIVE, "player.level().dimension().location().equals(this.dimension)", "exact current dimension detection")
    require(DIMENSION_OBJECTIVE, "this.isPartOfOptionalObjective()", "optional dimension-history membership check")
    require(DIMENSION_OBJECTIVE, "this.getParent().hasSentCompletion", "optional dimension-history post-completion freeze")
    require(DIMENSION_OBJECTIVE, 'data.putBoolean("seen", this.seen)', "dimension-history persistence")
    require(DIMENSION_OBJECTIVE, 'data.getBoolean("seen")', "dimension-history reload")
    require(DIMENSION_OBJECTIVE, "this.getParent().markForUpdate()", "dimension-history quest synchronization")

    require(POSITION_OBJECTIVE, "class VisitPositionHistoryObjective", "position-history objective implementation")
    require(POSITION_OBJECTIVE, "private boolean seen = false", "persistent position observation state")
    require(POSITION_OBJECTIVE, "this.ticksUntilCheck = 20", "bounded position polling cadence")
    require(POSITION_OBJECTIVE, "!this.isActiveForPlayer(player)", "active position objective lifecycle guard")
    require(POSITION_OBJECTIVE, "this.bounds.isInside(player.blockPosition())", "authored position bounds detection")
    require(POSITION_OBJECTIVE, "player.level().dimension().location().equals(this.dimension)", "optional position dimension guard")
    require(POSITION_OBJECTIVE, "this.isPartOfOptionalObjective()", "optional position-history membership check")
    require(POSITION_OBJECTIVE, "this.getParent().hasSentCompletion", "optional position-history post-completion freeze")
    require(POSITION_OBJECTIVE, 'data.putBoolean("seen", this.seen)', "position-history persistence")
    require(POSITION_OBJECTIVE, 'data.getBoolean("seen")', "position-history reload")
    require(POSITION_OBJECTIVE, "this.getParent().markForUpdate()", "position-history quest synchronization")

    require(STRUCTURE_OBJECTIVE, "class VisitStructureHistoryObjective", "structure-history objective implementation")
    require(STRUCTURE_OBJECTIVE, "private boolean seen = false", "persistent structure observation state")
    require(STRUCTURE_OBJECTIVE, "this.ticksUntilCheck = 20", "bounded structure polling cadence")
    require(STRUCTURE_OBJECTIVE, "!this.isActiveForPlayer(player)", "active structure objective lifecycle guard")
    require(STRUCTURE_OBJECTIVE, ".getStructureWithPieceAt(player.blockPosition(), target)", "exact current structure-piece detection")
    require(STRUCTURE_OBJECTIVE, "this.isPartOfOptionalObjective()", "optional structure-history membership check")
    require(STRUCTURE_OBJECTIVE, "this.getParent().hasSentCompletion", "optional structure-history post-completion freeze")
    require(STRUCTURE_OBJECTIVE, 'data.putBoolean("seen", this.seen)', "structure-history persistence")
    require(STRUCTURE_OBJECTIVE, 'data.getBoolean("seen")', "structure-history reload")
    require(STRUCTURE_OBJECTIVE, "this.getParent().markForUpdate()", "structure-history quest synchronization")

    require(DRAGON_OBJECTIVE, "class EnderDragonDefeatedObjective", "Ender Dragon world-state objective implementation")
    require(DRAGON_OBJECTIVE, "player.getServer().getLevel(Level.END)", "authoritative End level lookup")
    require(DRAGON_OBJECTIVE, "end.getDragonFight()", "persistent End fight lookup")
    require(DRAGON_OBJECTIVE, "fight.hasPreviouslyKilledDragon()", "persistent first-defeat world-state check")
    require(DRAGON_OBJECTIVE, "this.ticksUntilCheck = 20", "bounded Dragon-state polling cadence")
    require(DRAGON_OBJECTIVE, "!this.isActiveForPlayer(player)", "active Dragon objective lifecycle guard")
    require(DRAGON_OBJECTIVE, "this.setUnits(1)", "ordinary Questlog completion handoff")

    require(REGISTRY, 'new ResourceLocation("questlog", "entity_kill_history")', "entity_kill_history objective registration")
    require(REGISTRY, 'new EditorMetadata("entity", "Entity ID / Matcher:"', "tagged entity matcher editor metadata")
    require(REGISTRY, 'new ResourceLocation("questlog", "entity_kill_stat")', "entity_kill_stat objective registration")
    require(REGISTRY, 'new EditorMetadata("entity", "Exact Entity ID:"', "exact-entity editor metadata")
    require(REGISTRY, 'new ResourceLocation("questlog", "item_craft_stat")', "item_craft_stat objective registration")
    require(REGISTRY, 'new EditorMetadata("item", "Exact Item ID:"', "exact-item editor metadata")
    require(REGISTRY, 'new ResourceLocation("questlog", "visit_dimension_history")', "visit_dimension_history objective registration")
    require(REGISTRY, 'new EditorMetadata("dimension", "Exact Dimension ID:"', "exact-dimension editor metadata")
    require(REGISTRY, 'new ResourceLocation("questlog", "visit_position_history")', "visit_position_history objective registration")
    require(REGISTRY, 'new ResourceLocation("questlog", "visit_structure_history")', "visit_structure_history objective registration")
    require(REGISTRY, 'new EditorMetadata("structure", "Exact Structure ID:"', "exact-structure editor metadata")
    require(REGISTRY, 'new ResourceLocation("questlog", "ender_dragon_defeated")', "ender_dragon_defeated objective registration")

    require(VALIDATOR, '"questlog:entity_kill_history"', "tagged kill history definition validator registration")
    require(VALIDATOR, "must be exactly 1 for questlog:entity_kill_history", "boolean tagged kill history amount guard")
    require(VALIDATOR, '"questlog:entity_kill_stat"', "entity-kill definition validator registration")
    require(VALIDATOR, "core.validate_resource_id(entry[\"entity\"]", "exact entity resource-id schema validation")
    require(VALIDATOR, '"questlog:item_craft_stat"', "item-craft definition validator registration")
    require(VALIDATOR, "core.validate_resource_id(entry[\"item\"]", "exact item resource-id schema validation")
    require(VALIDATOR, '"questlog:visit_dimension_history"', "dimension-history definition validator registration")
    require(VALIDATOR, "core.validate_resource_id(entry[\"dimension\"]", "exact dimension resource-id schema validation")
    require(VALIDATOR, '"questlog:visit_position_history"', "position-history definition validator registration")
    require(VALIDATOR, "core.validate_bounds(entry[\"bounds\"]", "position-history bounds schema validation")
    require(VALIDATOR, '"questlog:visit_structure_history"', "structure-history definition validator registration")
    require(VALIDATOR, "core.validate_resource_id(entry[\"structure\"]", "exact structure resource-id schema validation")
    require(VALIDATOR, 'objective_type == "questlog:ender_dragon_defeated"', "Dragon world-state schema validation")
    require(VALIDATOR, "must be exactly 1 for questlog:ender_dragon_defeated", "boolean Dragon objective amount guard")

    require(ENTITY_FIXTURE, '"type": "questlog:entity_kill_stat"', "entity development fixture objective")
    require(ENTITY_FIXTURE, '"entity": "minecraft:zombie"', "entity development fixture exact entity")
    require(ENTITY_HISTORY_FIXTURE, '"type": "questlog:entity_kill_history"', "tagged entity history development fixture")
    require(ENTITY_HISTORY_FIXTURE, '"scoreboard_tag": "questlog_dev_kill_history_target"', "tagged entity history fixture selector")
    require(ITEM_FIXTURE, '"type": "questlog:item_craft_stat"', "item development fixture objective")
    require(ITEM_FIXTURE, '"item": "minecraft:crafting_table"', "item development fixture exact item")
    require(DIMENSION_FIXTURE, '"type": "questlog:visit_dimension_history"', "dimension development fixture objective")
    require(DIMENSION_FIXTURE, '"dimension": "minecraft:the_nether"', "dimension development fixture exact dimension")
    require(POSITION_FIXTURE, '"type": "questlog:visit_position_history"', "position development fixture objective")
    require(POSITION_FIXTURE, '"dimension": "minecraft:overworld"', "position development fixture dimension guard")
    require(STRUCTURE_FIXTURE, '"type": "questlog:visit_structure_history"', "structure development fixture objective")
    require(STRUCTURE_FIXTURE, '"structure": "minecraft:mineshaft"', "structure development fixture exact structure")
    require(DRAGON_FIXTURE, '"type": "questlog:ender_dragon_defeated"', "Dragon world-state development fixture")
    require(DRAGON_FIXTURE, '"required_amount": 1', "Dragon development fixture boolean amount")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Sequence-break tracking contract validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("OVERLORD sequence-break tracking contracts: PASS")
    statistic_result = statistic_contracts.main()
    if statistic_result != 0:
        return statistic_result
    return opening_contracts.main()


if __name__ == "__main__":
    raise SystemExit(main())
