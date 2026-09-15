# OVERLORD QUESTS Minion Type Unlock Integration

Status: PRODUCTION QUEST CHAIN IMPLEMENTED / REPOSITORY CONTRACT QUALIFIED / ASSEMBLED-INSTANCE ACCEPTANCE PENDING

This document records the approved gameplay integration boundary between OVERLORD QUESTS and OVERLORD Minions.

The Red, Green, and Blue progression interface is stable enough for production Quest integration. Build #118 remains the validated runtime dependency baseline for the public progression contract. Renderer work in the owner mod does not alter this contract unless it changes the public progression API.

## Owner-side progression contract

The owning mod is `overlord_minions`.

The stable public integration surface is:

```java
com.overlordreign.minions.api.OverlordMinionProgression
```

with slot identity from:

```java
com.overlordreign.minions.progression.MinionSlot
```

Questlog delegates Red, Green, and Blue transitions to:

```java
OverlordMinionProgression.unlock(server, MinionSlot.RED);
OverlordMinionProgression.unlock(server, MinionSlot.GREEN);
OverlordMinionProgression.unlock(server, MinionSlot.BLUE);
```

and reads owner state through:

```java
OverlordMinionProgression.isUnlocked(server, slot);
```

Questlog does not mirror that durable owner state into its own persistence.

The fixed order is:

```text
0 Brown
1 Red
2 Green
3 Blue
```

The owner accepts exactly the next slot, treats an already-unlocked slot as idempotent success, rejects an out-of-order later slot, and keeps Brown owned by the Master's Staff bootstrap.

## Approved campaign model

The campaign unlock sequence is fixed:

```text
Brown -> Red -> Green -> Blue
```

Brown requires no quest-side unlock trigger. Crafting the Minions Remastered Master's Staff is the bootstrap action that grants Brown access. Questlog observes that milestone for campaign progression but does not grant slot 0 itself.

Red, Green, and Blue are quest-earned capabilities and are recovered sequentially.

## Quest-side reward

OVERLORD QUESTS exposes:

```json
{
  "type": "questlog:unlock_minion",
  "slot": "red",
  "auto_claim": true
}
```

Valid reward slots are only:

- `red`
- `green`
- `blue`

Brown is intentionally invalid for this reward.

The reward delegates to the public OVERLORD Minions progression API. It does not write Minion unlock NBT, manipulate the Minions Remastered roster, alter spawned Minions, intercept the Master's Staff, or duplicate owner persistence.

`auto_claim: true` is mandatory. The reward is not valid inside a choice reward or as a failure consequence.

## Owner-state prerequisite

Quest completion and successful external progression are separate states. A quest can reach its objective boundary while the optional owner API is unavailable, so later Minion recovery content must not rely on `questlog:quest_complete` alone.

OVERLORD QUESTS exposes:

```json
{
  "type": "questlog:minion_unlocked",
  "slot": "red",
  "required_amount": 1
}
```

This objective reads owner state only. If the optional API is absent or incompatible, it remains unsatisfied. That fail-closed behavior prevents sequence drift.

## Runtime result handling

The owner API returns:

```text
UNLOCKED
ALREADY_UNLOCKED
OUT_OF_ORDER
BOOTSTRAP_OWNED_BY_STAFF
```

Questlog marks the reward as applied only after `UNLOCKED` or `ALREADY_UNLOCKED`.

`ALREADY_UNLOCKED` is successful reconciliation. `OUT_OF_ORDER`, missing API, incompatible API, or invocation errors leave the reward unapplied. Completed pending external rewards are retried on player load.

## Linkage boundary

OVERLORD QUESTS remains buildable without hard-linking OVERLORD Minions at class-load time. The compatibility adapter resolves only the documented public API class, documented slot enum, and public `unlock` and `isUnlocked` methods.

No mixin, private-field access, owner SavedData mutation, roster mutation, or client-side progression ownership is used.

## Brown bootstrap

The production Brown branch observes:

```text
minionsremastered:masters_staff
```

through `questlog:item_craft_stat`.

The surviving crafted-item statistic is sequence-break safe if the player crafted the staff before the objective became active. Questlog observes Brown recovery but does not grant Brown.

## Production Red, Green, and Blue recovery

The later production milestones are authored and indexed. They are no longer pending work.

Production chain:

```text
campaign/expansion/restore_reds.json
campaign/expansion/reds_return.json
campaign/expansion/restore_greens.json
campaign/expansion/greens_return.json
campaign/expansion/restore_blues.json
campaign/expansion/blues_return.json
```

Recovery anchors:

```text
Red   -> minecraft:blaze_rod
Green -> minecraft:spider_eye
Blue  -> minecraft:prismarine_crystals
```

These are authored recovery tests associated with the traditional Minion specialties. They are not temporary Hive substitutes and do not create a second Minion progression model.

The exact production semantics are:

1. `restore_reds` follows `the_reign_takes_shape`, requires one Blaze Rod, and auto-claims `questlog:unlock_minion` for Red.
2. `reds_return` requires both `restore_reds` completion and authoritative Red owner state before recording `overlord_reign:minions/red_recovered`.
3. `restore_greens` follows `reds_return`, requires one Spider Eye, and auto-claims the Green unlock.
4. `greens_return` requires both `restore_greens` completion and authoritative Green owner state before recording `overlord_reign:minions/green_recovered`.
5. `restore_blues` follows `greens_return`, requires Prismarine Crystals, and auto-claims the Blue unlock.
6. `blues_return` requires both `restore_blues` completion and authoritative Blue owner state before recording `overlord_reign:minions/blue_recovered`.

The confirmation quests are Gnarl presentation and narrative history. Durable Minion capability state remains external.

## REIGN Minion retrieval model

OVERLORD REIGN does not require physical Hive transport as the gameplay mechanism for recovering a Minion type.

Each traditional type is recovered through authored quest progression. Successful recovery establishes that type as available through the owning Minion progression system and records the campaign outcome.

The player is not required to locate, carry, escort, or physically retrieve a Hive as the unlock mechanism. Any separate Hive manifestation or presentation remains owner-side implementation if such behavior is desired.

## Ownership boundary

OVERLORD QUESTS owns:

- authored recovery conditions;
- quest prerequisites and sequencing;
- the milestone that requests a Red, Green, or Blue unlock;
- Gnarl framing;
- narrative recovery facts;
- campaign prevention of intentional tier skips.

OVERLORD Minions owns:

- fixed slot mapping;
- Brown bootstrap ownership;
- Red, Green, and Blue persistent unlock state;
- sequence enforcement at the progression API;
- actual Minion roster and summon gating;
- Minions Remastered compatibility behavior;
- any owner-side visual manifestation of recovered capability.

Questlog must not infer Red, Green, or Blue ownership from inventory possession, renderer appearance, UUID ordering, live Minion counts, or provider state.

## Production validation

`tools/validate_minion_recovery_contract.py` guards:

- Brown exclusion from `questlog:unlock_minion`;
- exact Red, Green, and Blue production action and confirmation definitions;
- production index inclusion;
- Blaze Rod, Spider Eye, and Prismarine Crystal recovery anchors;
- Red to Green to Blue ordering;
- exact auto-claimed API unlock rewards;
- authoritative `questlog:minion_unlocked` confirmation;
- narrative facts only after owner-state confirmation;
- the bridge's Red, Green, and Blue slot surface.

The Minion Recovery Contract workflow is green on the repository checkpoint recorded in `docs/FULL_INSTANCE_QUALIFICATION.md`.

## Runtime acceptance

The dedicated assembled-runtime procedure remains `docs/MINION_PROGRESSION_TEST_PROTOCOL.md`.

That protocol verifies owner-side sequence rejection, Questlog handoff, Red to Green to Blue save/reload persistence, idempotent reconciliation, and the missing-owner recovery boundary in disposable worlds.

Those tests require the compatible owner mod to be present in an assembled runtime. Repository source/build validation does not substitute for them.

## Current implementation boundary

IMPLEMENTED AND REPOSITORY-QUALIFIED:

- Brown staff-owned bootstrap observation;
- fixed Brown 0, Red 1, Green 2, Blue 3 contract;
- bounded public-API bridge;
- `questlog:unlock_minion` for Red, Green, and Blue only;
- `questlog:minion_unlocked` owner-state checks;
- mandatory auto-claim;
- idempotent already-unlocked reconciliation;
- fail-closed out-of-order and unavailable-owner behavior;
- player-load retry of pending completed external rewards;
- complete production Red recovery pair;
- complete production Green recovery pair;
- complete production Blue recovery pair;
- exact authored recovery anchors;
- production sequence and narrative fact validation;
- no Questlog mutation of Minions roster or owner persistence.

PENDING ASSEMBLED-INSTANCE ACCEPTANCE:

- runtime pass with the compatible OVERLORD Minions artifact installed;
- save/reload verification of the full Red to Green to Blue sequence;
- owner-side sequence rejection and idempotence verification in that runtime;
- any owner-side Hive manifestation behavior, if implemented by the Minions project.

The Minion renderer may continue changing without reopening this progression contract unless the owner mod changes the public progression API.

## Repository consistency note

Any older Quest documentation that says the Red, Green, and Blue bridge or production recovery milestones are unimplemented is superseded by this document, `docs/MINION_TYPE_RECOVERY_CONTRACT.md`, and the executable production definitions.