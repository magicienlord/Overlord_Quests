# OVERLORD QUESTS Minion Type Unlock Integration

Status: ACTIVE CAMPAIGN CONTRACT / QUEST BRIDGE IMPLEMENTED AGAINST VALIDATED BUILD #118

This document records the approved gameplay integration boundary between OVERLORD QUESTS and OVERLORD Minions.

The Red, Green, and Blue progression interface is now stable enough for Quest integration. Build #118 is the current validated development dependency. The remaining OVERLORD Minions work is renderer-side and does not change this progression contract.

## Stable external progression API

The owning mod is `overlord_minions`.

The stable public integration surface is:

```java
com.overlordreign.minions.api.OverlordMinionProgression
```

with fixed slot identity from:

```java
com.overlordreign.minions.progression.MinionSlot
```

The quest-side transition calls are:

```java
OverlordMinionProgression.unlock(server, MinionSlot.RED);
OverlordMinionProgression.unlock(server, MinionSlot.GREEN);
OverlordMinionProgression.unlock(server, MinionSlot.BLUE);
```

Questlog also uses the public read operation:

```java
OverlordMinionProgression.isUnlocked(server, slot);
```

This read is used only to confirm owner-side progression before opening a later campaign tier. Questlog does not mirror that state into its own persistence.

The fixed slot order is part of the cross-mod contract:

- slot `0` = Brown
- slot `1` = Red
- slot `2` = Green
- slot `3` = Blue

The external state implementation accepts exactly the next slot, treats an already-unlocked slot as idempotent success, rejects an out-of-order later slot, and keeps Brown owned by the Master's Staff bootstrap.

## Approved progression model

The campaign unlock sequence is fixed:

`Brown -> Red -> Green -> Blue`

Brown requires no quest-side unlock trigger.

Crafting the Minions Remastered Master's Staff is the bootstrap action that grants Brown access. Questlog observes that milestone for campaign progression but does not intercept the staff and does not grant slot `0` itself.

Red, Green, and Blue are quest-earned capabilities and must be unlocked sequentially. Production authoring must not create a valid route that grants Green before Red or Blue before Green.

## Quest-side reward

OVERLORD QUESTS exposes the bounded reward type:

```json
{
  "type": "questlog:unlock_minion",
  "slot": "red",
  "auto_claim": true
}
```

Valid `slot` values are only:

- `red`
- `green`
- `blue`

Brown is intentionally not a valid reward slot.

The reward delegates the state transition to the public OVERLORD Minions progression API. It does not write Minion unlock NBT, manipulate the Minions Remastered roster, change spawned Minions, intercept the Master's Staff, or duplicate external persistence.

The reward is required to use `auto_claim: true` and is not valid inside a choice reward or as a failure consequence.

## Owner-state prerequisite

Quest completion and successful external progression are deliberately separate states. A quest can reach its completion boundary while its optional external owner is temporarily unavailable, so later Minion recovery content must not rely on `questlog:quest_complete` alone.

OVERLORD QUESTS therefore exposes the server-authoritative objective/prerequisite:

```json
{
  "type": "questlog:minion_unlocked",
  "slot": "red",
  "required_amount": 1
}
```

Valid `slot` values are only Red, Green, and Blue. Brown is excluded because Brown availability is owned by the Master's Staff bootstrap rather than the later-tier progression state.

For a later tier, production authoring must require both the intended campaign milestone and the authoritative preceding owner state. For example, Green recovery should not become available merely because the Red recovery quest completed. The public Minion API must also confirm that Red is actually unlocked.

The objective does not write progression state. On the server it queries `OverlordMinionProgression.isUnlocked(...)`; the client receives only the derived one-unit completion snapshot needed for Questlog presentation.

If the optional API is absent or incompatible, this objective remains unsatisfied. This fail-closed behavior prevents campaign sequence drift.

## Runtime result handling

The external API returns one of four progression results:

- `UNLOCKED`
- `ALREADY_UNLOCKED`
- `OUT_OF_ORDER`
- `BOOTSTRAP_OWNED_BY_STAFF`

Questlog marks its reward as applied only after `UNLOCKED` or `ALREADY_UNLOCKED`.

`ALREADY_UNLOCKED` is deliberately treated as success. This makes repeated quest-side reconciliation harmless.

`OUT_OF_ORDER` leaves the reward unapplied so the campaign cannot silently claim a tier the owning mod rejected.

If the external API is temporarily unavailable or incompatible, the reward also remains unapplied. Questlog therefore does not convert a missing dependency or temporary integration failure into false progression success.

## Linkage boundary

OVERLORD QUESTS remains buildable without shipping OVERLORD Minions as a hard class-link dependency. The bounded compatibility adapter resolves only the documented public API class, documented slot enum, and documented `unlock(MinecraftServer, MinionSlot)` and `isUnlocked(MinecraftServer, MinionSlot)` methods.

This is not access to private Minions internals. No mixin, private-field access, saved-data mutation, roster mutation, or client-side progression state is used.

Build #118 is the development validation baseline for this API contract. The current source contract confirms the same public API and fixed slot identities while renderer work continues independently.

## Brown bootstrap exception

The production Brown branch remains different from later tiers.

The branch uses `questlog:item_craft_stat` against:

```text
minionsremastered:masters_staff
```

That objective recognizes the surviving vanilla crafted-item statistic when the player crafted the staff before Questlog formally introduced the objective.

This is observation only. Questlog does not grant Brown and does not intercept the staff.

## Red, Green, and Blue unlock semantics

For each later tier:

- the relevant authored quest milestone decides that the type has been earned;
- the milestone uses `questlog:unlock_minion` with the correct slot;
- the following tier checks `questlog:minion_unlocked` for the preceding owner-side slot as well as the intended quest milestone;
- OVERLORD Minions remains authoritative for persistent slot ownership;
- repeated application is harmless;
- current spawned-Minions counts do not affect ownership;
- temporary inventory state does not affect ownership;
- provider disposition does not substitute for the unlock state.

The external API independently rejects sequence skipping, while campaign quest prerequisites preserve the same order and fail closed when an earlier external handoff is still pending.

## REIGN Minion retrieval model

OVERLORD REIGN does not use physical Hive transport as the required gameplay mechanism for recovering a Minion type.

Each type is recovered through an authored quest and gameplay sequence. The successful recovery milestone makes that Minion type available and establishes the associated Hive return as part of the same campaign outcome.

The player is not required to locate, carry, escort, or physically retrieve the Hive as the unlock mechanism.

The exact diegetic mechanism for each Hive return remains authored campaign material and is not invented by the integration layer.

## Ownership boundary

OVERLORD QUESTS owns:

- the campaign conditions under which Red, Green, or Blue has been earned;
- the quest milestone that calls the public unlock API;
- the surrounding Gnarl framing and persistent campaign record;
- quest sequencing that prevents intentional tier skips.

OVERLORD Minions owns:

- the fixed slot mapping;
- Brown bootstrap ownership;
- Red, Green, and Blue persistent unlock state;
- sequence enforcement at the progression API;
- actual Minion roster and summon gating;
- Minions Remastered compatibility behavior;
- any owner-side implementation needed to manifest the recovered capability.

OVERLORD QUESTS must not infer Red, Green, or Blue ownership from inventory possession, UUID-derived appearance, current Minion counts, or provider state.

## Reconciliation requirement

The API is idempotent by contract, so an already-open tier may safely receive the same unlock call again.

Questlog itself does not duplicate the Minion unlock state. Its only local persistence is the normal quest reward-application flag that records whether the campaign milestone successfully handed the transition to the owning mod.

If the API is absent or rejects the tier as out of order, that reward is left pending rather than falsely recorded as applied. Completed pending Minion rewards are retried on player load.

The owner-state prerequisite prevents the next recovery tier from opening until the owning Minions system confirms the previous slot is actually available.

The campaign must still author later recovery quests in fixed order so ordinary gameplay reaches:

`0 Brown -> 1 Red -> 2 Green -> 3 Blue`

## Runtime validation

The dedicated integration procedure is recorded in `docs/MINION_PROGRESSION_TEST_PROTOCOL.md`.

That protocol uses the non-canon Red, Green, and Blue development fixtures together with `/overlord_minions status` to verify owner-side sequence rejection, Questlog handoff, save/reload persistence, and idempotent reconciliation. It requires a fresh disposable integration world because the production progression state is intentionally irreversible.

Static and build validation do not substitute for that full-modpack runtime pass.

## Current implementation boundary

IMPLEMENTED in OVERLORD QUESTS:

- Brown recovery remains staff-owned and is observed through the exact Master's Staff craft statistic;
- stable slot order is recorded as Brown `0`, Red `1`, Green `2`, Blue `3`;
- a bounded public-API bridge targets `OverlordMinionProgression.unlock` and `OverlordMinionProgression.isUnlocked`;
- `questlog:unlock_minion` is registered for Red, Green, and Blue only;
- `questlog:minion_unlocked` confirms authoritative later-tier owner state for Red, Green, and Blue only;
- auto-claim is mandatory for the unlock reward;
- already-unlocked tiers reconcile as success;
- out-of-order tiers remain unapplied;
- later development tiers require both the previous Questlog milestone and the previous owner-side Minion slot;
- completed pending external unlock rewards are retried on player load;
- Questlog does not manipulate Minions Remastered roster or unlock persistence;
- the definition validator rejects Brown, unknown slot values, manual-claim usage, choice nesting, and failure-consequence usage;
- Minion progression integration contracts now run through the standard definition-validation path.

STILL TO AUTHOR OR RUNTIME-VALIDATE:

- the concealed production Red recovery milestone;
- the concealed production Green recovery milestone;
- the concealed production Blue recovery milestone;
- full-modpack runtime validation against the supplied Build #118 development JAR;
- save/reload verification of the complete Red -> Green -> Blue sequence in an integration world;
- any separate Hive manifestation implementation required by the owning Minions system.

The Minion renderer may continue changing visually without reopening this progression contract unless the owning mod explicitly changes the public API, which the current renderer pass is not expected to do.

## Repository consistency note

Any older Quest documentation that says the Red, Green, and Blue bridge is blocked on an unknown external interface is superseded by this document.
