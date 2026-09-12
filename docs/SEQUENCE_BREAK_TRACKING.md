# OVERLORD REIGN Sequence-Break Tracking

Status: TECHNICAL IMPLEMENTATION OF APPROVED CAMPAIGN RULE

## Authority

`magicienlord/Overlord_Lore_and_Canon` establishes that Minecraft exploration may let the player complete content before Gnarl or Questlog formally directs them toward it. Legitimate prior accomplishments should be recognized whenever technically possible, and completed bosses, crafts, discoveries, dimension visits, or world events must not be duplicated merely because their corresponding REIGN quest was not active yet.

The same authority requires native-mod progression to be tracked through the cleanest reliable signal available, preferring advancements, boss/world state, items, structure discovery, capabilities, documented APIs, and similarly stable hooks. It also explicitly states that dimensions should not be artificially blocked merely because the central campaign has not formally introduced them, and that a legitimate early visit should be recognized where technically possible.

This document records narrow Questlog fallbacks for exact entity kills, exact item crafts, pre-activation exact-dimension visits, and pre-activation exact-structure visits when a stronger native persistent signal is unavailable. It does not authorize any particular boss, item, dimension, structure, quest, sequence, or story reaction.

## Event-based objectives

`questlog:entity_kill`, `questlog:item_craft`, `questlog:visit_dimension`, and `questlog:visit_structure` remain ordinary active-state objectives.

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

Its history is intentionally scoped to what Questlog actually observed. It cannot reconstruct visits that happened before:

- OVERLORD QUESTS was installed;
- the relevant definition existed in the loaded definition set;
- the player's QuestManager began tracking that definition.

If a native mod exposes a real advancement, capability, world-state flag, or other durable dimension-access signal, that stronger source remains preferable.

The objective accepts one exact dimension ID and represents a boolean history fact. `required_amount` is therefore fixed at `1` by repository validation.

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

This history is intentionally scoped to what Questlog actually observed. It is not a fabricated vanilla statistic and cannot reconstruct visits that happened before:

- OVERLORD QUESTS was installed;
- the relevant definition existed in the loaded definition set;
- the player's QuestManager began tracking that definition.

If a native mod exposes a real advancement, capability, world-state flag, or other durable discovery signal, that stronger source remains preferable.

The objective accepts one exact structure ID and represents a boolean history fact. `required_amount` is therefore fixed at `1` by repository validation.

## Deliberate limitations

The statistic-backed retrospective objectives accept one exact namespaced registry ID.

They do not support tag predicates or arbitrary matcher payloads because vanilla statistics are keyed by exact registry entries. `entity_kill_stat` also does not support NBT, custom-name filters, or entity predicates. `item_craft_stat` does not support NBT-sensitive crafting distinctions.

`visit_dimension_history` uses one exact dimension ID. It does not mean the player completed any particular objective, dungeon, boss, ritual, or native progression inside that dimension.

`visit_structure_history` likewise uses one exact structure ID. It does not represent a structure tag, an arbitrary bounding box, a whole civilization settlement class, or a claim that the structure was discovered before Questlog began observing the world.

Use ordinary active-state objectives when rich matching or post-activation action matters more than retrospective recognition.

Minecraft's kill statistic reflects kills credited to the player. A boss death caused exclusively by another actor or by a mod-specific mechanic may not increment it. Likewise, a mod may create an item through a machine, ritual, scripted transformation, or custom recipe path without incrementing vanilla `ITEM_CRAFTED`. Authors must verify the actual native signal before selecting either statistic fallback.

## Signal selection rule

For an actual REIGN milestone, choose the most authoritative available signal in this order of preference:

1. a documented native-mod progression state or API that directly represents the accomplishment;
2. a stable native advancement or comparable persistent milestone;
3. an explicit persistent world or campaign fact produced by a reliable compatibility hook;
4. a reliable item, dimension, structure, or other existing state signal when it uniquely proves the accomplishment;
5. Questlog's own pre-activation observation history when the campaign definition can observe the event before formal activation and no stronger native persistence exists;
6. a matching vanilla persistent statistic such as `questlog:entity_kill_stat` or `questlog:item_craft_stat` when that statistic honestly proves the event;
7. event-only tracking when the design specifically requires the event to happen after activation.

The order is a technical preference, not a story hierarchy. The objective is to recognize what actually happened without rewriting another mod's native progression or pretending evidence exists when it does not.

## No event duplication

Retrospective recognition does not respawn, recreate, re-craft, reopen, or replay an already completed encounter, discovery, dimension visit, or milestone. It only allows the REIGN quest graph to observe surviving persistent evidence and advance or select the appropriate authored response.

If a native mod does not expose trustworthy evidence that a unique event happened, that compatibility gap must be handled deliberately. The quest system must not infer a historical event from unrelated circumstantial state merely to avoid an unresolved technical limitation.

## Development fixtures

`examples/questlog/quests/overlord_sequence_break_dev.json` checks retrospective exact-entity kills with a vanilla zombie.

`examples/questlog/quests/overlord_item_craft_sequence_break_dev.json` checks retrospective exact-item crafting with a vanilla crafting table.

`examples/questlog/quests/overlord_dimension_history_dev.json` checks pre-activation exact-dimension visit history with the vanilla Nether. Its debug-stick prerequisite deliberately keeps the quest locked while the dimension observation can occur.

`examples/questlog/quests/overlord_structure_history_dev.json` checks pre-activation exact-structure visit history with a vanilla mineshaft. Its debug-stick prerequisite deliberately keeps the quest locked while the structure observation can occur.

All four are non-canon development fixtures.

For the dimension-history fixture, a direct runtime check is:

1. ensure the fixture definition is loaded and the player does not possess a debug stick;
2. enter `minecraft:the_nether` and remain there for at least one second;
3. return to the Overworld;
4. obtain the debug stick to satisfy the prerequisite;
5. confirm the objective completes immediately without requiring another Nether visit;
6. repeat across save/reload between steps 3 and 4 to confirm the observation persists.

For the structure-history fixture, a direct runtime check is:

1. ensure the fixture definition is loaded and the player does not possess a debug stick;
2. enter a `minecraft:mineshaft` and remain inside a valid structure piece for at least one second;
3. leave the structure;
4. obtain the debug stick to satisfy the prerequisite;
5. confirm the objective completes immediately without requiring a second mineshaft visit;
6. repeat across save/reload between steps 3 and 4 to confirm the observation persists.

These fixtures validate bounded sequence-break behavior without claiming arbitrary pre-install exploration history.
