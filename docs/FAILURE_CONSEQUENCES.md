# OVERLORD QUESTS Failure Consequences

Status: TECHNICAL IMPLEMENTATION OF APPROVED QUEST ARCHITECTURE

This document defines how an authored quest failure writes persistent consequence state. It does not define any production death, betrayal, faction outcome, settlement destruction, or other story event by itself.

## Authority

`magicienlord/Overlord_Lore_and_Canon/reference/27_REIGN_QUEST_ARCHITECTURE_DECISIONS.md` establishes that OVERLORD REIGN should prefer consequence states over generic fail screens whenever possible. An important NPC death, for example, may need to record an explicit death fact that closes one route and opens another rather than merely setting an opaque failed flag.

The same authority requires sparse explicit state. Failure handling must therefore record only the consequences later content actually needs, not a generalized failure score.

## Definition surface

Questlog already supports top-level `failures`, which are objective conditions that make a quest failed when any one becomes complete.

OVERLORD QUESTS adds an optional sibling list:

```json
"failure_rewards": [
  {
    "type": "questlog:set_fact",
    "fact": "overlord_reign:example/consequence",
    "auto_claim": true
  }
]
```

The field name is retained as `failure_rewards` because the implementation reuses the existing server-side Reward registry and persistence contract. In campaign design these entries are consequences, not success prizes.

Every failure consequence must use `auto_claim: true`. A failed quest has no success reward-claim flow through which a player could legitimately collect a consequence later.

Choice rewards are not valid failure consequences. Failure state cannot pause for a success-reward selection UI. Branch selection should instead be represented by the action or world condition that caused the failure, followed by the explicit consequence facts, dispositions, commands, or other authored outputs appropriate to that branch.

## Runtime boundary

Failure consequences are applied only on the logical server and only when:

- the quest has actually triggered;
- at least one authored failure condition is complete;
- the consequence entry has not already been applied.

A failure condition becoming true while a quest is still locked therefore cannot write campaign history prematurely. If the same surviving condition is still true after the quest legitimately triggers, the normal objective semantics may then make the quest failed and its consequences become eligible.

Each consequence keeps the normal Reward `rewarded` persistence bit. Application is therefore idempotent across repeated synchronization, save/reload, and whole-manager synchronization triggered by narrative-state changes.

## Appropriate consequence outputs

Use the smallest explicit output that accurately records what happened.

Typical production uses include:

- `questlog:set_fact` for durable historical truths such as a specific NPC death, destroyed local anchor, escaped target, or other branch result;
- `questlog:set_disposition` only when the failed event itself is politically significant enough to justify an immediate authored civilization-state change;
- `questlog:command` for a narrowly controlled world transition when no safer native API exists;
- another registered reward type only when receiving that output as an automatic consequence is semantically correct.

Failure consequences must not become a disguised generic punishment bundle. Ordinary mechanical failure that is intended to be retried can continue to use failure detection without persistent consequence outputs, or should use a retryable objective structure instead.

## Interaction with provider quests

A provider quest may expose its authored `provider.dialogue.failed` state when a failure condition becomes true. `failure_rewards` supply the persistent world or narrative outputs that the failed dialogue may later refer to.

The provider remains server-authoritative. Client dialogue does not create the consequence state.

If a provider's death is itself a legitimate failure condition, world integration still needs a reliable objective or surviving signal for that death. The new consequence layer records the result after detection; it does not invent death detection or infer which entity was narratively important.

## Reset semantics

Administrative quest reset revokes the internal applied bit for each failure consequence along with ordinary quest progress. This makes the quest mechanically testable again.

A reset is not a world rollback.

In particular, monotonic narrative facts written by `questlog:set_fact` are intentionally not erased when a quest is reset. Command side effects, destroyed blocks, spawned entities, disposition changes, and other external state are likewise not generically reversible from the Reward abstraction.

For development testing, clear synthetic facts explicitly with the narrative admin commands before resetting the fixture. Production campaign design must never rely on an admin reset to undo history.

## Save compatibility

Older saves have no `failure_rewards` NBT list. Loading them leaves all newly introduced failure consequence entries unapplied.

If such a save contains a quest that is currently triggered and failed under its present definition, the next authoritative quest synchronization applies any still-unapplied failure consequences. This is intentional reconciliation: the stored failed state and the new explicit consequence state are brought into agreement.

Changing the meaning of a shipped failure consequence or its fact ID is therefore a save-migration decision. Production IDs should be treated as historical keys.

## Development fixture

`examples/questlog/quests/overlord_failure_consequence_dev.json` is a non-canon fixture. It uses a synthetic barrier-item failure condition and writes `questlog:dev_failure_consequence` through an automatic `questlog:set_fact` output.

The fixture exists only to validate the runtime contract and must not be bundled as campaign content.
