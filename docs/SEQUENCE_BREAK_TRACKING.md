# OVERLORD REIGN Sequence-Break Tracking

Status: TECHNICAL IMPLEMENTATION OF APPROVED CAMPAIGN RULE

## Authority

`magicienlord/Overlord_Lore_and_Canon` establishes that Minecraft exploration may let the player complete content before Gnarl or Questlog formally directs them toward it. Legitimate prior accomplishments should be recognized whenever technically possible, and completed bosses or world events must not be duplicated merely because their corresponding REIGN quest was not active yet.

The same authority requires native-mod progression to be tracked through the cleanest reliable signal available, preferring advancements, boss/world state, items, structure discovery, capabilities, documented APIs, and similarly stable hooks.

This document records one narrow Questlog fallback for entity kills. It does not authorize any particular boss, quest, sequence, or story reaction.

## Existing event-based kill objective

`questlog:entity_kill` remains the general event-driven kill objective.

It can use Questlog's full entity matcher surface, including exact entity IDs, tags, names, and vanilla entity-predicate data. Its progress begins only when the objective can receive and accept kill events for the active quest state.

That makes it appropriate for authored tasks such as "kill several matching enemies after this quest begins", but it is not a historical-accomplishment detector.

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

Consequences:

- a qualifying player-attributed kill from before formal quest activation can satisfy the objective;
- the same objective can be used as a prerequisite to recognize an already-accomplished fact;
- progress survives normal Minecraft stat persistence independently of Questlog's activation timing;
- `required_amount` may be greater than one if an authored quest genuinely cares about a historical kill count.

## Deliberate limitations

The retrospective objective accepts only one exact namespaced entity ID.

It does not accept:

- entity tags;
- NBT predicates;
- custom-name filters;
- arbitrary vanilla entity predicates;
- a synthetic list of several entity types.

Those features cannot be mapped honestly onto one vanilla per-entity kill statistic. Use the ordinary `questlog:entity_kill` objective when rich matching matters more than retrospective recognition.

Minecraft's `ENTITY_KILLED` statistic also reflects kills credited to the player by Minecraft. A boss death caused exclusively by another actor or by a mod-specific mechanic may not increment that statistic. In particular, authors must not assume this fallback recognizes every Minion-attributed or scripted boss death.

## Signal selection rule

For an actual REIGN milestone, choose the most authoritative available signal in this order of preference:

1. a documented native-mod progression state or API that directly represents the accomplishment;
2. a stable native advancement or comparable persistent milestone;
3. an explicit persistent world or campaign fact produced by a reliable compatibility hook;
4. a reliable item, structure, or other existing state signal when it uniquely proves the accomplishment;
5. `questlog:entity_kill_stat` when an exact player-attributed entity kill statistic is genuinely sufficient;
6. event-only tracking when the design specifically requires the event to happen after activation.

The order is a technical preference, not a story hierarchy. The objective is to recognize what actually happened without rewriting another mod's native progression.

## No boss duplication

Retrospective recognition does not respawn, recreate, or replay an already completed boss encounter. It only allows the REIGN quest graph to observe a surviving persistent signal and advance or select the appropriate authored response.

If a native mod does not expose any trustworthy evidence that a unique event happened, that compatibility gap must be handled deliberately. The quest system must not infer a historical event from unrelated circumstantial state merely to avoid an unresolved technical limitation.

## Development fixture

`examples/questlog/quests/overlord_sequence_break_dev.json` is a non-canon development fixture. It checks whether the current player has ever killed at least one vanilla zombie.

A useful direct runtime test is:

1. kill a zombie before installing or activating the fixture;
2. load/reload the fixture;
3. verify that its objective becomes complete without killing another zombie;
4. repeat with a fresh player/world statistic state and verify that the objective stays incomplete until the first player-credited zombie kill.

The fixture exists only to validate the mechanism and establishes no OVERLORD REIGN quest content.
