# OVERLORD REIGN Sequence-Break Tracking

Status: TECHNICAL IMPLEMENTATION OF APPROVED CAMPAIGN RULE

## Authority

`magicienlord/Overlord_Lore_and_Canon` establishes that Minecraft exploration may let the player complete content before Gnarl or Questlog formally directs them toward it. Legitimate prior accomplishments should be recognized whenever technically possible, and completed bosses, crafts, or world events must not be duplicated merely because their corresponding REIGN quest was not active yet.

The same authority requires native-mod progression to be tracked through the cleanest reliable signal available, preferring advancements, boss/world state, items, structure discovery, capabilities, documented APIs, and similarly stable hooks.

This document records narrow Questlog fallbacks for exact entity kills and exact item crafts when vanilla statistics are the best persistent evidence available. It does not authorize any particular boss, item, quest, sequence, or story reaction.

## Event-based objectives

`questlog:entity_kill` and `questlog:item_craft` remain the general event-driven objectives.

They support the ordinary matcher behavior appropriate to events that must occur while the quest is active. Their progress begins only when the active objective can receive and accept the relevant event, so they are not historical-accomplishment detectors.

## Retrospective exact-entity objective

OVERLORD QUESTS adds:

```json
{
  "type": "questlog:entity_kill_stat",
  "entity": "minecraft:zombie",
  "required_amount": 1
}
```

`questlog:entity_kill_stat` reads Minecraft's persistent `Stats.ENTITY_KILLED` counter for one exact registered entity type. The objective polls the server player's statistic at a one-second cadence and projects the historical count into ordinary Questlog objective progress.

This allows a qualifying player-attributed kill from before formal quest activation to satisfy the objective without recreating the encounter.

## Retrospective exact-item craft objective

OVERLORD QUESTS also adds:

```json
{
  "type": "questlog:item_craft_stat",
  "item": "minecraft:crafting_table",
  "required_amount": 1
}
```

`questlog:item_craft_stat` reads Minecraft's persistent `Stats.ITEM_CRAFTED` counter for one exact registered item. Like the entity variant, it polls at a one-second cadence and can recognize a craft that happened before the quest became active.

This is intended for campaign milestones where forcing the player to manufacture a second copy would be false to what already happened. It is particularly useful when the crafted item is itself the durable proof of a native progression step but possession cannot be guaranteed because the item may have been moved, consumed, lost, or stored elsewhere after crafting.

## Deliberate limitations

Both retrospective objectives accept one exact namespaced registry ID.

They do not support tag predicates or arbitrary matcher payloads because vanilla statistics are keyed by exact registry entries. `entity_kill_stat` also does not support NBT, custom-name filters, or entity predicates. `item_craft_stat` does not support NBT-sensitive crafting distinctions.

Use the ordinary event objectives when rich matching matters more than retrospective recognition.

Minecraft's kill statistic reflects kills credited to the player. A boss death caused exclusively by another actor or by a mod-specific mechanic may not increment it. Likewise, a mod may create an item through a machine, ritual, scripted transformation, or custom recipe path without incrementing vanilla `ITEM_CRAFTED`. Authors must verify the actual native signal before selecting either fallback.

## Signal selection rule

For an actual REIGN milestone, choose the most authoritative available signal in this order of preference:

1. a documented native-mod progression state or API that directly represents the accomplishment;
2. a stable native advancement or comparable persistent milestone;
3. an explicit persistent world or campaign fact produced by a reliable compatibility hook;
4. a reliable item, structure, or other existing state signal when it uniquely proves the accomplishment;
5. a matching vanilla persistent statistic such as `questlog:entity_kill_stat` or `questlog:item_craft_stat` when that statistic honestly proves the event;
6. event-only tracking when the design specifically requires the event to happen after activation.

The order is a technical preference, not a story hierarchy. The objective is to recognize what actually happened without rewriting another mod's native progression.

## No event duplication

Retrospective recognition does not respawn, recreate, re-craft, or replay an already completed encounter or milestone. It only allows the REIGN quest graph to observe surviving persistent evidence and advance or select the appropriate authored response.

If a native mod does not expose trustworthy evidence that a unique event happened, that compatibility gap must be handled deliberately. The quest system must not infer a historical event from unrelated circumstantial state merely to avoid an unresolved technical limitation.

## Development fixtures

`examples/questlog/quests/overlord_sequence_break_dev.json` checks retrospective exact-entity kills with a vanilla zombie.

`examples/questlog/quests/overlord_item_craft_sequence_break_dev.json` checks retrospective exact-item crafting with a vanilla crafting table.

Both are non-canon development fixtures. A useful runtime check is to perform the target action before exposing the fixture, then verify that the objective completes from the already-persisted vanilla statistic without repeating the action.
