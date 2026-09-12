# Failure Consequence Runtime Test Protocol

Status: TECHNICAL RUNTIME VALIDATION / DEVELOPMENT CONTENT ONLY

This protocol validates `docs/FAILURE_CONSEQUENCES.md` with the synthetic `overlord_failure_consequence_dev.json` fixture. Nothing in this fixture is OVERLORD REIGN story canon.

## Installation

Use a Forge build from the exact commit under test and place `examples/questlog/quests/overlord_failure_consequence_dev.json` in the instance at:

```text
config/questlog/quests/overlord_failure_consequence_dev.json
```

Exactly one mod with technical ID `questlog` must be present.

Launch an unpublished local single-player world with commands available.

## Clean setup

Run:

```text
/clear @s minecraft:barrier
/questlog narrative fact clear questlog:dev_failure_gate
/questlog narrative fact clear questlog:dev_failure_consequence
/questlog progress reset questlog:overlord_failure_consequence_dev
```

The quest must remain locked because `questlog:dev_failure_gate` is absent.

## Locked-state safety pass

While the quest is still locked, run:

```text
/give @s minecraft:barrier 1
```

Wait at least one second, then query:

```text
/questlog narrative fact get questlog:dev_failure_consequence
```

Expected result: the consequence fact is absent. Merely satisfying the failure condition while the quest is locked must not write history.

Do not remove the barrier yet.

## Trigger and failure pass

Open the synthetic gate:

```text
/questlog narrative fact set questlog:dev_failure_gate
```

The narrative-state command immediately synchronizes active quest state. The quest should now trigger. Because the player still carries the barrier, its failure condition should become true on the next item-obtain poll.

Wait at least one second, then query:

```text
/questlog narrative fact get questlog:dev_failure_consequence
```

Expected result: the consequence fact is present.

The failed quest must not expose an ordinary success-reward claim flow for this consequence.

## Persistence and idempotence pass

Save and quit to the title screen, reload the world, then query the consequence fact again. It must remain present.

Next clear only the consequence fact, without resetting the quest:

```text
/questlog narrative fact clear questlog:dev_failure_consequence
```

Wait several seconds and query it again.

Expected result: it remains absent. Clearing a fact through the administrative escape hatch does not clear the persisted `failure_rewards` applied bit, so ordinary quest synchronization must not repeatedly reapply an already consumed consequence.

This artificial state mismatch exists only because an administrator manually erased monotonic history.

## Reset and replay pass

To prove that the development fixture can be exercised again, run:

```text
/clear @s minecraft:barrier
/questlog narrative fact clear questlog:dev_failure_gate
/questlog narrative fact clear questlog:dev_failure_consequence
/questlog progress reset questlog:overlord_failure_consequence_dev
```

Then set the gate and give the barrier in that order:

```text
/questlog narrative fact set questlog:dev_failure_gate
/give @s minecraft:barrier 1
```

After at least one second, `questlog:dev_failure_consequence` must be present again.

The reset has reopened the quest's internal failure-consequence application state. The explicit fact clears are separate because administrative quest reset is not a world rollback.

## Evidence to retain

Retain:

- `latest.log`;
- the fact query showing absence during the locked-state pass;
- the fact query showing presence after the active failure;
- the fact query showing persistence after reload;
- the fact query showing no automatic replay after manually clearing only the fact;
- any quest-load, synchronization, recursive-update, or reward error.

## Acceptance criteria

The feature is runtime-validated only if all of the following hold:

1. locked failure conditions do not write consequences;
2. a triggered failed quest writes the synthetic consequence automatically;
3. the consequence survives save/reload;
4. the same failed quest does not reapply an already persisted consequence on every synchronization;
5. administrative reset can reopen the mechanical test without pretending to roll back world history.
