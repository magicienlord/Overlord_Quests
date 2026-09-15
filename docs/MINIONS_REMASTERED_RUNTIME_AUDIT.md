# Minions Remastered Runtime Integration Audit

Status: QUEST-SIDE PRODUCTION INTEGRATION IMPLEMENTED / ASSEMBLED-INSTANCE MINION PROGRESSION QUALIFICATION PENDING

Date: 2026-09-15

## Scope

This document preserves the important findings from the original audit of the installed upstream Minions Remastered `0.3.0` gameplay JAR and records the current OVERLORD Minions progression contract used by OVERLORD QUESTS.

The historical audit still matters because it explains why campaign progression must not be inferred from live Minion roster slots, UUID order, renderer appearance, summon counts, or other upstream gameplay state that was never a traditional Brown/Red/Green/Blue unlock model.

The current cross-mod contract is defined together with `docs/MINION_UNLOCK_INTEGRATION.md` and `docs/MINION_TYPE_RECOVERY_CONTRACT.md`.

## Historical upstream findings

The audited modpack contained:

```text
minionsremastered-0.3.0.jar
modId=minionsremastered
version=0.3.0
Minecraft 1.20.1
Forge loader 47+
```

The Master's Staff is:

```text
minionsremastered:masters_staff
```

The upstream `IMasterCapability` stores gameplay ownership and command state such as Minion UUIDs, current live positions, inventory data, orders, container targeting, pause state, and synchronization. Its four-position limit is a live roster cap, not a Brown/Red/Green/Blue campaign progression model.

The audited upstream capability did not persist traditional Minion type ownership or Hive-return campaign state. Therefore OVERLORD QUESTS must never infer traditional tribe recovery from:

- live Minion-list index;
- number of summoned Minions;
- Minion UUID order;
- UUID-derived renderer appearance;
- entity colour state;
- possession of the Master's Staff after the Brown bootstrap milestone.

The original conclusion that Red, Green, and Blue required a separate durable progression surface was correct at that time.

## Current OVERLORD Minions progression authority

OVERLORD Minions Build #118 is the validated development dependency baseline recorded by `docs/MINION_UNLOCK_INTEGRATION.md`.

The preserved owner implementation exposes:

```java
com.overlordreign.minions.api.OverlordMinionProgression
com.overlordreign.minions.progression.MinionSlot
```

with public server-side operations equivalent to:

```java
OverlordMinionProgression.isUnlocked(MinecraftServer server, MinionSlot slot)
OverlordMinionProgression.highestUnlocked(MinecraftServer server)
OverlordMinionProgression.unlock(MinecraftServer server, MinionSlot slot)
```

The fixed owner-side order is:

```text
0 Brown
1 Red
2 Green
3 Blue
```

The owner stores world-scoped progression in SavedData named `overlord_minions_unlocks`. It enforces monotonic ordered progression, treats already-open slots idempotently, rejects skipped slots, and keeps Brown owned by the Master's Staff bootstrap.

The owner API result set is:

```text
UNLOCKED
ALREADY_UNLOCKED
OUT_OF_ORDER
BOOTSTRAP_OWNED_BY_STAFF
```

This closes the historical missing-progression-interface blocker without allowing Questlog to mutate private Minion state.

## Quest-side bridge

OVERLORD QUESTS implements an optional bounded bridge to the documented public API only.

For Red, Green, and Blue it exposes the reward:

```json
{
  "type": "questlog:unlock_minion",
  "slot": "red",
  "auto_claim": true
}
```

and the owner-state prerequisite/objective:

```json
{
  "type": "questlog:minion_unlocked",
  "slot": "red",
  "required_amount": 1
}
```

Brown is intentionally excluded from the quest-side unlock API because the Master's Staff remains its bootstrap owner.

The bridge:

- calls only the public `unlock` and `isUnlocked` operations;
- accepts `UNLOCKED` and `ALREADY_UNLOCKED` as successful reconciliation;
- leaves out-of-order or unavailable handoffs pending;
- retries completed pending external unlock rewards on player load;
- fails closed when the optional API is absent or incompatible;
- does not copy Minion unlock ownership into Questlog persistence;
- does not mutate Minions Remastered roster state, private capability fields, or save NBT.

## Brown bootstrap

The production Brown recovery branch observes the exact Master's Staff craft through `questlog:item_craft_stat` against:

```text
minionsremastered:masters_staff
```

The crafted-item statistic is sequence-break safe when the staff was crafted before the Questlog objective became active. Questlog observes this milestone only and never grants Brown through `questlog:unlock_minion`.

## Production Red, Green, and Blue recovery

The previous statement that these production milestones were still to author is obsolete.

The production chain is now:

```text
campaign/expansion/restore_reds.json
campaign/expansion/reds_return.json
campaign/expansion/restore_greens.json
campaign/expansion/greens_return.json
campaign/expansion/restore_blues.json
campaign/expansion/blues_return.json
```

The authored recovery anchors are:

```text
Red   -> minecraft:blaze_rod
Green -> minecraft:spider_eye
Blue  -> minecraft:prismarine_crystals
```

These materials are authored recovery tests. They are not temporary Hive proxies and do not imply that Questlog manufactures physical source-game Hive objects.

Each action quest auto-claims exactly one corresponding `questlog:unlock_minion` reward. Each following Gnarl confirmation requires both the action quest and the authoritative `questlog:minion_unlocked` state before writing the narrative recovery fact.

The resulting facts are:

```text
overlord_reign:minions/red_recovered
overlord_reign:minions/green_recovered
overlord_reign:minions/blue_recovered
```

Those facts are narrative history only. Durable command state remains owned by OVERLORD Minions.

`tools/validate_minion_recovery_contract.py` guards this complete production chain, its ordering, the material anchors, API unlock rewards, owner-state confirmations, indexed definitions, Brown exclusion, and bridge slot surface. The Minion Recovery Contract workflow is green on the qualified repository checkpoint recorded in `docs/FULL_INSTANCE_QUALIFICATION.md`.

## Current implementation boundary

IMPLEMENTED AND REPOSITORY-VALIDATED:

- Brown staff-owned bootstrap observation;
- fixed Brown, Red, Green, Blue slot identities;
- persistent owner-side progression contract;
- ordered and idempotent API semantics;
- bounded Questlog reward bridge;
- bounded owner-state prerequisite;
- fail-closed missing-owner behavior;
- player-load retry for pending completed handoffs;
- production Red recovery quest and Gnarl confirmation;
- production Green recovery quest and Gnarl confirmation;
- production Blue recovery quest and Gnarl confirmation;
- production sequence enforcement from Red to Green to Blue;
- narrative recovery facts only after owner-state confirmation;
- CI contract protection for the full production chain.

STILL REQUIRES ASSEMBLED-INSTANCE ACCEPTANCE:

- direct runtime validation with the compatible OVERLORD Minions artifact installed in the assembled OVERLORD REIGN instance;
- disposable-world save and reload verification of the complete Red to Green to Blue sequence;
- direct confirmation of owner-side out-of-order rejection and idempotent reconciliation in that assembled runtime;
- any separate Hive manifestation behavior owned by OVERLORD Minions, if the owner mod implements such presentation.

These remaining items are runtime acceptance, not missing Quest authoring.

## Current decision

Brown quest observation: IMPLEMENTED.

Brown capability bootstrap: EXTERNAL OWNER, MASTER'S STAFF.

Red, Green, Blue production campaign authoring: IMPLEMENTED.

Red, Green, Blue Questlog bridge: IMPLEMENTED AGAINST THE VALIDATED PUBLIC API CONTRACT.

Red, Green, Blue owner persistence: EXTERNAL OWNER.

Repository contract validation: QUALIFIED AT THE CHECKPOINT RECORDED IN `docs/FULL_INSTANCE_QUALIFICATION.md`.

Assembled-instance cross-mod progression acceptance: PENDING.

The historical external-interface blocker and the production-authoring blocker are both closed. Do not reopen either unless the public Minion API or governing quest authority changes.