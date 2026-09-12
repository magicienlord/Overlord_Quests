# OVERLORD REIGN Sequence-Break Tracking

Status: TECHNICAL IMPLEMENTATION OF APPROVED CAMPAIGN RULE

## Authority

`magicienlord/Overlord_Lore_and_Canon` establishes that Minecraft exploration may let the player complete content before Gnarl or Questlog formally directs them toward it. Legitimate prior accomplishments should be recognized whenever technically possible, and completed bosses, crafts, discoveries, dimension visits, authored-area visits, or world events must not be duplicated merely because their corresponding REIGN quest was not active yet.

The same authority requires native-mod progression to be tracked through the cleanest reliable signal available, preferring advancements, boss/world state, items, structure discovery, capabilities, documented APIs, and similarly stable hooks. It also explicitly states that dimensions should not be artificially blocked merely because the central campaign has not formally introduced them, and that a legitimate early visit should be recognized where technically possible. Quest-critical civilization anchors likewise exist in the world before formal quest activation.

This document records narrow Questlog fallbacks for exact entity kills, exact item crafts, pre-activation exact-dimension visits, pre-activation authored-area visits, and pre-activation exact-structure visits when a stronger native persistent signal is unavailable. It does not authorize any particular boss, item, dimension, area, structure, quest, sequence, or story reaction.

## Event-based objectives

`questlog:entity_kill`, `questlog:item_craft`, `questlog:visit_dimension`, `questlog:visit_position`, and `questlog:visit_structure` remain ordinary active-state objectives.

They support the normal event or polling behavior appropriate to tasks that must occur while the quest is active. Their progress is accepted through the ordinary Questlog trigger boundary, so they are not general historical-accomplishment detectors.

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

## Pre-activation exact-dimension visit history

Minecraft does not provide Questlog with a universal persistent answer to whether a player has ever entered an arbitrary dimension. The inherited `questlog:visit_dimension` objective therefore cannot reconstruct a visit made while its parent quest was still locked.

For bundled campaign definitions that exist from the beginning of the Questlog world lifecycle, OVERLORD QUESTS adds:

```json
{
  "type": "questlog:visit_dimension_history",
  "dimension": "minecraft:the_nether",
  "required_amount": 1
}
```

`questlog:visit_dimension_history` observes the player's exact current dimension at a one-second cadence. When the configured dimension is reached, the objective records a persistent boolean observation immediately, even if the parent quest has not yet triggered.

That observation survives save/reload in the quest's normal objective state. When later prerequisites expose the quest, the objective is already complete and campaign dialogue can acknowledge that the player arrived early instead of pretending the dimension is still unknown.

This directly supports the approved REIGN dimension sequence-breaking rule without bypassing native dimension-access mechanics. The objective does not open portals, grant keys, alter dimension registration, or circumvent another mod's progression gate.

Its history is intentionally scoped to what Questlog actually observed. It cannot reconstruct visits that happened before OVERLORD QUESTS was installed, before the relevant definition existed in the loaded definition set, or before the player's QuestManager began tracking that definition.

If a native mod exposes a real advancement, capability, world-state flag, or other durable dimension-access signal, that stronger source remains preferable.

The objective accepts one exact dimension ID and represents a boolean history fact. `required_amount` is therefore fixed at `1` by repository validation.

## Pre-activation authored-area visit history

Some REIGN quest anchors are intentionally authored locations rather than one globally unique structure registry entry. A settlement, room, ruin, audience area, or other bounded site may therefore need to remember that the player already entered it before the campaign formally exposed the corresponding quest.

For that case OVERLORD QUESTS adds:

```json
{
  "type": "questlog:visit_position_history",
  "bounds": [1000, 64, 1000, 1002, 66, 1002],
  "dimension": "minecraft:overworld",
  "required_amount": 1
}
```

`questlog:visit_position_history` reuses the inherited position-bound format and observes the player at a one-second cadence. When the player enters the authored bounds, it records a persistent boolean observation even while its parent quest is locked.

The optional `dimension` guard should be used for world-specific authored anchors. Without it, identical coordinates in another dimension remain eligible, matching the inherited cross-dimensional `visit_position` behavior. Repository validation checks the bounds shape, optional dimension resource ID, and boolean `required_amount` contract.

This objective records only that the area was entered. It does not identify a civilization, set disposition, infer an audience, complete a political event, or claim that an arbitrary settlement was the canonical quest anchor. Those meanings still require explicit campaign facts, provider scope, or later objectives.

Exact world coordinates remain implementation data until canonical world integration establishes them. Adding this objective does not choose those coordinates or promote a proposed location to canon.

## Pre-activation exact-structure visit history

Vanilla Minecraft does not expose a general persistent statistic answering whether a player has ever visited an arbitrary structure. The inherited `questlog:visit_structure` objective therefore cannot reconstruct a visit that happened while its parent quest was still locked.

For bundled campaign definitions that exist from the beginning of the Questlog world lifecycle, OVERLORD QUESTS adds:

```json
{
  "type": "questlog:visit_structure_history",
  "structure": "minecraft:mineshaft",
  "required_amount": 1
}
```

`questlog:visit_structure_history` observes one exact registered structure at a one-second cadence. When the player is physically inside a piece belonging to that structure, the objective records a persistent boolean observation immediately, even if the parent quest has not yet triggered.

The observation then survives save/reload in the quest's normal objective state. When later prerequisites expose the quest, the objective is already complete and the campaign can acknowledge that the discovery happened earlier instead of sending the player back merely to satisfy activation order.

This history is intentionally scoped to what Questlog actually observed. It is not a fabricated vanilla statistic and cannot reconstruct visits that happened before OVERLORD QUESTS was installed, before the relevant definition existed in the loaded definition set, or before the player's QuestManager began tracking that definition.

If a native mod exposes a real advancement, capability, world-state flag, or other durable discovery signal, that stronger source remains preferable.

The objective accepts one exact structure ID and represents a boolean history fact. `required_amount` is therefore fixed at `1` by repository validation.

## Deliberate limitations

The statistic-backed retrospective objectives accept one exact namespaced registry ID.

They do not support tag predicates or arbitrary matcher payloads because vanilla statistics are keyed by exact registry entries. `entity_kill_stat` also does not support NBT, custom-name filters, or entity predicates. `item_craft_stat` does not support NBT-sensitive crafting distinctions.

`visit_dimension_history` uses one exact dimension ID. It does not mean the player completed any particular objective, dungeon, boss, ritual, or native progression inside that dimension.

`visit_position_history` records one authored bounding region and optional dimension. It is unsuitable as a substitute for a native progression signal or for recognizing a procedural settlement whose identity has not actually been established.

`visit_structure_history` likewise uses one exact structure ID. It does not represent a structure tag, an arbitrary bounding box, a whole civilization settlement class, or a claim that the structure was discovered before Questlog began observing the world.

Use ordinary active-state objectives when rich matching or post-activation action matters more than retrospective recognition.

Minecraft's kill statistic reflects kills credited to the player. A boss death caused exclusively by another actor or by a mod-specific mechanic may not increment it. Likewise, a mod may create an item through a machine, ritual, scripted transformation, or custom recipe path without incrementing vanilla `ITEM_CRAFTED`. Authors must verify the actual native signal before selecting either statistic fallback.

## Signal selection rule

For an actual REIGN milestone, choose the most authoritative available signal in this order of preference:

1. a documented native-mod progression state or API that directly represents the accomplishment;
2. a stable native advancement or comparable persistent milestone;
3. an explicit persistent world or campaign fact produced by a reliable compatibility hook;
4. a reliable item, dimension, structure, authored location, or other existing state signal when it uniquely proves the accomplishment;
5. Questlog's own pre-activation observation history when the campaign definition can observe the event before formal activation and no stronger native persistence exists;
6. a matching vanilla persistent statistic such as `questlog:entity_kill_stat` or `questlog:item_craft_stat` when that statistic honestly proves the event;
7. event-only tracking when the design specifically requires the event to happen after activation.

The order is a technical preference, not a story hierarchy. The objective is to recognize what actually happened without rewriting another mod's native progression or pretending evidence exists when it does not.

## No event duplication

Retrospective recognition does not respawn, recreate, re-craft, reopen, or replay an already completed encounter, discovery, dimension visit, authored-area visit, or milestone. It only allows the REIGN quest graph to observe surviving persistent evidence and advance or select the appropriate authored response.

If a native mod does not expose trustworthy evidence that a unique event happened, that compatibility gap must be handled deliberately. The quest system must not infer a historical event from unrelated circumstantial state merely to avoid an unresolved technical limitation.

## Development fixtures

`examples/questlog/quests/overlord_sequence_break_dev.json` checks retrospective exact-entity kills with a vanilla zombie.

`examples/questlog/quests/overlord_item_craft_sequence_break_dev.json` checks retrospective exact-item crafting with a vanilla crafting table.

`examples/questlog/quests/overlord_dimension_history_dev.json` checks pre-activation exact-dimension visit history with the vanilla Nether. Its debug-stick prerequisite deliberately keeps the quest locked while the dimension observation can occur.

`examples/questlog/quests/overlord_position_history_dev.json` checks pre-activation authored-area history in a small Overworld test box. Its debug-stick prerequisite keeps the quest locked while the area observation can occur.

`examples/questlog/quests/overlord_structure_history_dev.json` checks pre-activation exact-structure visit history with a vanilla mineshaft. Its debug-stick prerequisite deliberately keeps the quest locked while the structure observation can occur.

All five are non-canon development fixtures.

For the dimension-history fixture, enter `minecraft:the_nether` without the debug stick, remain there for at least one second, return to the Overworld, then obtain the debug stick. The objective must complete without another Nether visit and must retain the observation across a save/reload inserted before the prerequisite is satisfied.

For the position-history fixture, teleport to `1001 65 1001` in the Overworld without the debug stick, remain inside the test box for at least one second, leave the box, then obtain the debug stick. The objective must complete without revisiting the box and must retain the observation across save/reload.

For the structure-history fixture, enter a `minecraft:mineshaft` without the debug stick, remain inside a valid structure piece for at least one second, leave the structure, then obtain the debug stick. The objective must complete without a second mineshaft visit and must retain the observation across save/reload.

These fixtures validate bounded sequence-break behavior without claiming arbitrary pre-install exploration history.
