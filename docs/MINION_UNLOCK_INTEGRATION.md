# OVERLORD QUESTS Minion Type Unlock Integration

Status: ACTIVE CAMPAIGN CONTRACT / EXTERNAL BRIDGE PENDING

This document records the approved gameplay integration boundary between OVERLORD QUESTS and the planned Minions Remastered fork. The Brown bootstrap is now represented in the first bundled production campaign slice. The later Red, Green, and Blue integration still waits for the Minions Remastered fork to expose its stable unlock and hive-return surface.

## Approved progression model

The Minions Remastered fork exposes four zero-based Minion-type slots with a fixed type mapping:

- slot `0` = Brown
- slot `1` = Red
- slot `2` = Green
- slot `3` = Blue

Crafting the Minions Remastered Master's Staff unlocks only slot `0`, Brown.

Red, Green, and Blue remain locked after staff crafting and are intended to be unlocked later by quest progression through durable quest markers.

The campaign unlock sequence is fixed:

`Brown -> Red -> Green -> Blue`

Quest progression must therefore unlock slot `1` before slot `2`, and slot `2` before slot `3`. Production quest authoring must not create a route that grants Green before Red or Blue before Green.

The exact later quests, narrative circumstances, marker IDs, and bridge calls remain UNKNOWN until the Minions Remastered fork exposes its final integration surface and campaign authoring reaches those sections.

## REIGN Minion retrieval model

OVERLORD REIGN deliberately does not reproduce the original-game gameplay loop of physically retrieving a Minion hive as the means of recovering that Minion type.

Instead, each Minion type is recovered through an authored sequence of quests and gameplay actions. Completing the relevant recovery sequence causes the Minion type to return together with its associated hive through an unseen, magical, or otherwise non-physical-return mechanism appropriate to the final authored lore.

The stable design rule is:

- the player performs the required quest chain and gameplay actions;
- the campaign reaches the recovery milestone for the next Minion type;
- that Minion type becomes available in its fixed Minions Remastered slot;
- its associated hive returns as part of the same recovery outcome;
- the player is not required to locate, carry, escort, or physically retrieve that hive as the unlock mechanism.

The exact diegetic mechanism of each return remains UNKNOWN until the relevant campaign section is authored from `magicienlord/Overlord_Lore_and_Canon` and the primary-source material.

The hive is therefore a consequence and manifestation of successful Minion recovery, not the physical quest object whose transportation unlocks the type.

## Ownership boundary

OVERLORD QUESTS owns the campaign decision that a later Minion type has been earned.

The Minions Remastered fork owns:

- the four-slot Minion-type system;
- the fixed slot mapping `0=Brown, 1=Red, 2=Green, 3=Blue`;
- the initial Brown unlock caused by staff crafting;
- persistence of unlocked Minion slots/types on its side;
- enforcing which Minion types can actually be selected, summoned, or assigned;
- the stable technical API, command, event, or marker-consumption surface used by OVERLORD QUESTS.

The technical ownership of spawning, restoring, activating, or otherwise manifesting each associated hive remains UNKNOWN until the Minions Remastered fork exposes its final hive/integration surface. The campaign requirement is fixed: the hive returns with the corresponding Minion type and is not the item the player physically retrieves to unlock it.

OVERLORD QUESTS must not infer Red, Green, or Blue unlocks from inventory possession or current spawned Minion counts.

## Quest-side representation

OVERLORD QUESTS has monotonic world-scoped narrative facts and auto-claimed `questlog:set_fact` rewards. That mechanism is suitable for later Minion unlock milestones because those unlocks represent persistent campaign state rather than reversible reputation values.

The preferred later integration is:

1. the relevant production quest completes;
2. the quest records one durable Minion-unlock marker/fact;
3. the Minions Remastered bridge consumes or is notified of that marker and permanently opens the corresponding slot/type;
4. the associated hive is restored or manifested as part of the same recovery milestone through the final agreed integration owner;
5. reload reconciliation preserves the unlock and hive-return state without requiring the original completion event to replay.

Exact production marker IDs remain UNKNOWN and must not be invented before the external contract exists.

## Brown bootstrap exception

Brown is deliberately different from the other three types.

Brown unlocks when the Minions Remastered Master's Staff is crafted. This is a Minions Remastered gameplay bootstrap and does not require a Questlog reward or narrative fact to grant slot `0`.

The bundled opening campaign now observes that craft as the Brown-recovery milestone. Questlog is not the authority that opens the Brown slot.

The Brown branch follows the same REIGN retrieval principle as the later types: crafting the staff is the decisive gameplay action that leads to Brown access being restored, with the Brown hive returning as part of that recovery outcome rather than being physically carried back by the player.

## Sequence-break-safe Brown observation

The production Brown branch uses `questlog:item_craft_stat` against the exact verified item ID:

```text
minionsremastered:masters_staff
```

This objective reads Minecraft's persistent per-item crafted statistic. If the player legitimately crafts the Master's Staff before the quest formally becomes active, Questlog can still recognize the surviving craft evidence instead of asking for a second staff.

This is observation only. Minions Remastered remains authoritative for the actual slot `0` unlock and for the final Brown hive manifestation behavior.

The retrospective objective is intentionally exact-item-only. If a future Minions Remastered revision stops incrementing the vanilla crafted-item statistic for the staff, the integration must move to a more authoritative native signal rather than pretending the old statistic still proves the event.

## Red, Green, and Blue unlock semantics

For each later type, the unlock marker should be:

- boolean;
- monotonic during normal play;
- idempotent if applied more than once;
- durable across save/reload;
- safe if the Minions Remastered fork processes it after the originating quest already completed;
- independent of the current number of spawned Minions;
- independent of temporary inventory state;
- independent of provider disposition or sidequest reputation.

The later unlocks are ordered:

- Red, slot `1`, requires Brown slot `0`;
- Green, slot `2`, requires Red slot `1`;
- Blue, slot `3`, requires Green slot `2`.

Quest definitions should enforce this sequence authoritatively. The Minions Remastered bridge should also reject, defer, or safely reconcile an out-of-order marker rather than exposing a later slot while an earlier slot is locked.

Each later Minion-recovery sequence must culminate in both the relevant slot unlock and the return of that type's associated hive. Hive manifestation must be idempotent and save-persistent so reconciliation cannot duplicate it.

## Compatibility requirement

The integration must avoid a fragile direct dependency from ordinary quest definitions onto Minions Remastered implementation internals.

Preferred integration order:

1. stable public unlock API or event exposed by the Minions Remastered fork;
2. stable server command exposed by the fork and invoked by an auto-claimed Questlog command reward;
3. stable marker/fact polling bridge owned by the Minions fork.

The same preference applies to hive restoration if Minions Remastered owns hive manifestation. Direct reflection, mixin access into private Minions Remastered fields, hard-coded save-NBT mutation, and client-only unlock/hive state should be avoided.

## Reconciliation requirement

Because unlocks are permanent campaign capabilities, the bridge must reconcile existing progress safely without violating slot order.

If a world loads after a relevant later unlock quest is already complete, or after its durable fact is already present, the corresponding Minion type and hive-return state must reconcile correctly even if the original completion event is no longer replayed. Reconciliation must walk progression in order so a later stored marker cannot bypass an earlier required slot.

This applies to:

- existing development worlds;
- migration after the Minions fork is updated;
- recovery after temporary mod removal;
- save/load ordering;
- future quest-definition revisions that preserve the same production marker.

## Current implementation boundary

IMPLEMENTED in OVERLORD QUESTS:

- the Brown recovery branch is authored and bundled as production campaign content;
- the branch observes the exact Master's Staff craft through retrospective persistent statistics;
- Questlog does not grant Brown directly;
- the campaign records Brown recovery using the non-physical hive-retrieval model;
- the opening Gnarl presentation can react after that recovery milestone.

PENDING external Minions Remastered work:

- the stable Red/Green/Blue unlock integration surface;
- the exact production marker IDs for slots `1`, `2`, and `3`;
- the final ownership/API for hive manifestation and reconciliation;
- development tests proving actual slot and hive state across save/reload.

When the external interface exists, the Quest side should add one bounded adapter and a development fixture that proves:

- Brown remains staff-crafting-owned as slot `0`;
- Red/Green/Blue begin locked;
- Red unlocks only slot `1`;
- Green cannot expose slot `2` before Red;
- Blue cannot expose slot `3` before Green;
- the complete valid sequence is `0 Brown -> 1 Red -> 2 Green -> 3 Blue`;
- no path requires physical hive transport;
- repeated application is harmless and does not duplicate hive manifestation;
- save/reload preserves each unlock and hive-return state;
- already-present markers reconcile in slot order.

## Repository consistency note

Any older Minion-fork documentation that describes UUID-derived visual assignment without the four-slot progression model predates this contract. OVERLORD QUESTS treats the fixed four-slot model documented here as the integration target.
