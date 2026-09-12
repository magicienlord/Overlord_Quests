# OVERLORD QUESTS Targeted Entity Death Tracking

Status: TECHNICAL IMPLEMENTATION CONTRACT

This document defines the event-driven objective used when campaign logic needs to know that a specific authored entity has died. It does not identify any production NPC, decide whether an NPC should be killable, or establish a story consequence by itself.

## Authority

`magicienlord/Overlord_Lore_and_Canon/reference/27_REIGN_QUEST_ARCHITECTURE_DECISIONS.md` requires important NPC death to be representable as explicit persistent consequence state when later content needs that history.

`reference/16_CIVILIZATION_QUEST_ANCHORS.md` separately requires authored civilization starter NPCs to remain distinct from procedural populations and allows deliberately destructive routes where permanent loss is legitimate.

The implementation therefore needs two separate capabilities:

1. identify the exact authored anchor rather than every entity of the same type;
2. observe that target's actual death regardless of whether the final damage came directly from the player.

## Existing `questlog:entity_death` semantics

The inherited Questlog objective named `questlog:entity_death` does not mean that the configured entity died.

Its historical runtime semantics are:

```text
the player died, and the configured entity was the damaging entity
```

OVERLORD QUESTS preserves that behavior for save and definition compatibility. Renaming or silently changing it would alter existing Questlog content.

## `questlog:entity_died`

OVERLORD QUESTS adds:

```json
{
  "type": "questlog:entity_died",
  "entity": "minecraft:villager",
  "scoreboard_tag": "example_anchor_identity",
  "required_amount": 1
}
```

`questlog:entity_died` listens to the same server-side entity-death event stream but matches the entity that actually died. The damage source is irrelevant.

The example tag is documentation only and is not a production campaign identifier.

At least one explicit matcher selector is required. A bare `entity_died` objective that could match every death in the world is rejected by the OVERLORD definition validator.

## Scoreboard-tag matching

All entity objectives now accept one optional `scoreboard_tag` matcher in addition to their existing entity type, custom name, and vanilla entity-predicate filters.

The tag may be declared at the objective level:

```json
{
  "type": "questlog:entity_died",
  "entity": "minecraft:villager",
  "scoreboard_tag": "example_anchor_identity"
}
```

or inside an entity matcher object:

```json
{
  "type": "questlog:entity_died",
  "entity": {
    "id": "minecraft:villager",
    "scoreboard_tag": "example_anchor_identity"
  }
}
```

When a scoreboard tag is present, the entity must carry that exact persistent tag at the event boundary.

For quest-critical anchors, use the unique anchor identity tag here. Do not use `overlord_quest_protected` as identity because that protection tag is intentionally shared by unrelated anchors.

## Interaction with anchor protection

`docs/QUEST_ANCHOR_PROTECTION.md` controls whether an explicitly marked anchor can take ordinary damage. `questlog:entity_died` controls whether an active quest notices that an identified target has died.

A typical destructive-route transition is therefore:

1. the relevant quest or narrative state becomes active;
2. the target anchor still carries its unique identity tag;
3. authored world integration removes `overlord_quest_protected` when killing the anchor becomes legitimate;
4. if the anchor dies, `questlog:entity_died` detects that death;
5. a failure condition or objective transition can then write the explicit persistent consequence through `failure_rewards` or another authored output.

The order matters. The death objective is event-driven, not retrospective. If world integration removes protection and permits a target to die before the relevant quest/state is active, the later quest cannot reconstruct that death from an entity that no longer exists unless another persistent signal recorded it.

## Sequence-break boundary

For a valid pre-activation death route, use surviving authoritative evidence instead of pretending the event can be replayed. Appropriate evidence can be an existing world fact, native mod state, an authored marker written at death time, or another persistent signal covered by `docs/SEQUENCE_BREAK_TRACKING.md`.

Do not spawn a duplicate NPC merely to satisfy the quest objective.

When campaign design requires the target to survive until a particular audience, choice, or quest trigger, keep `overlord_quest_protected` until that transition so there is no legitimate earlier death to reconstruct.

## Multiplayer scope

OVERLORD REIGN targets local single-player. `questlog:entity_died` observes a world entity death for every active player quest instance whose matcher selects that entity.

This is appropriate for authored world-anchor history. It must not be used for per-player competitive kill credit. Use `questlog:entity_kill` or another player-attributed objective when the identity of the killer matters.

## Development fixture

`examples/questlog/quests/overlord_anchor_death_dev.json` is a non-canon fixture. It requires a synthetic gate fact, matches a tagged Villager through `questlog:entity_died`, and writes a synthetic death fact as an automatic failure consequence.

The fixture validates the full technical chain without naming or deciding a production NPC.
