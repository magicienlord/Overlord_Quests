# OVERLORD QUESTS Optional Objectives

## Authority and scope

`magicienlord/Overlord_Lore_and_Canon` establishes that optional objectives are desirable when they have a concrete consequence, reward, shortcut, additional cruelty, or other authored purpose, while avoiding arbitrary bonus scoring. That authority previously left clean Questlog representation as an implementation unknown.

This document closes only that implementation question. It does not authorize any particular story objective, reward, branch, or campaign outcome.

## Authoring contract

An optional objective is an ordinary top-level entry in a quest's `objectives` array with:

```json
{
  "type": "questlog:item_obtain",
  "item": "minecraft:diamond",
  "optional": true
}
```

The `optional` field is legal only on a top-level entry in `objectives`.

Do not put `optional` on:

- prerequisites or legacy requirements;
- failure conditions;
- children nested inside `and`, `or`, or `not` objectives.

For a logic objective, place `optional: true` on the top-level logic objective. Its whole child tree then belongs to the same optional branch.

Repository validation rejects misplaced or non-boolean optional flags.

## Runtime semantics

Optional objectives use the normal Questlog objective machinery for event listeners, progress, synchronization, persistence, reset behavior, and details-screen display.

They differ from required objectives in four deliberate ways:

1. An incomplete optional objective does not block quest completion.
2. An incomplete optional objective does not block provider turn-in readiness.
3. The top-level optional objective is visibly labeled `(Optional)` in the quest details panel.
4. Once the quest crosses its completion boundary, unfinished optional objective progress is frozen. Optional objectives are opportunities during the live quest, not post-completion score counters.

Required objectives continue to use the existing completion contract. If a triggered quest has no required objectives, it can complete immediately, even if its objective list contains optional entries. Authors should therefore use an optional-only objective set only when immediate completion is actually intended.

## Logic-objective behavior

A top-level optional `and`, `or`, or `not` objective marks its entire nested objective tree as part of the optional branch for runtime progress freezing. Only the top-level entry receives the `(Optional)` presentation label so nested logic trees do not repeat the label on every child.

Nested `optional` flags are invalid because they would make the completion semantics of the enclosing logic objective ambiguous.

## Persistence and definition authority

The optional flag is definition-owned behavior. It is not duplicated into player NBT.

Objective progress remains stored in the existing positional `objectives` list, so the normal save-compatibility rule still applies: after a production quest has saved progress, do not reorder or repurpose objective positions without treating the change as a save migration.

The in-game quest editor currently has no dedicated optional-objective toggle. It starts edits from a deep copy of the authoritative definition, so an existing `optional` field is preserved when an authored JSON quest is opened and saved. Production optional-objective authoring should therefore remain source-definition driven until a dedicated editor control is justified.

## Consequences and rewards

`optional: true` changes completion gating only. It does not automatically grant bonus XP, score, morality, reputation, hidden points, or a branch outcome.

If an optional objective is meant to matter later, its consequence must be represented by an explicit authored mechanism supported by the campaign architecture, such as a later conditional fact, linked quest, world consequence, provider response, or another concrete state transition. This preserves REIGN's explicit-fact model instead of introducing a hidden numeric bonus system.

## Development fixture

`examples/questlog/quests/overlord_optional_objective_dev.json` is a non-canon development fixture. It contains one required stick objective and one optional diamond objective.

Expected behavior:

- both objectives appear in the details panel;
- the diamond objective is visibly labeled `(Optional)`;
- obtaining the diamond before the stick records its progress normally;
- obtaining the stick completes the quest whether or not the diamond was obtained;
- after completion, an unfinished optional diamond objective no longer advances;
- reset returns both objective progress values to zero.

The repository validator also checks the runtime source contract so a future refactor cannot silently make optional objectives completion-gating again.
