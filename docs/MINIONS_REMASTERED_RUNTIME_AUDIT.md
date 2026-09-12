# Minions Remastered Runtime Integration Audit

Status: HISTORICAL UPSTREAM RUNTIME AUDIT / BUILD #118 PROGRESSION BLOCKER RESOLVED

Date: 2026-09-12

## Scope

This document preserves the original audit of the installed upstream Minions Remastered `0.3.0` gameplay JAR, then records the later OVERLORD Minions progression interface that resolved the Red, Green, and Blue integration blocker.

The historical findings remain useful because they explain why OVERLORD QUESTS must not infer campaign progression from the upstream Minions Remastered capability, live Minion slots, UUID order, or renderer state.

The current authoritative cross-mod contract is `docs/MINION_UNLOCK_INTEGRATION.md`.

## Historical installed gameplay JAR audit

The audited OVERLORD REIGN modpack contained:

```text
minionsremastered-0.3.0.jar
```

The JAR declared:

```text
modId=minionsremastered
version=0.3.0
Minecraft 1.20.1
Forge loader 47+
```

The Master's Staff was registered as:

```text
minionsremastered:masters_staff
```

Its installed shaped recipe used raw gold, emerald, and stick. The exact item ID remains the Brown bootstrap observation point used by OVERLORD QUESTS.

## Historical upstream capability findings

The audited upstream `IMasterCapability` exposed gameplay ownership and command state for Minions, including:

- player ownership;
- a list of Minion UUIDs;
- adding and removing Minions;
- four live Minion positions through the current hard cap;
- per-position Minion inventory data;
- current order;
- container target state;
- pause state;
- synchronization.

The implementation's maximum of four was a cap on simultaneously tracked Minion UUID positions. It was not a Brown, Red, Green, Blue type-unlock model.

The upstream capability serialized Minion UUID positions, Minion inventory data, order state, container targeting, and pause state. It did not serialize Brown, Red, Green, or Blue campaign unlock ownership, Minion type identity per summon position, or traditional Hive-return state.

Summoning created the single upstream gameplay entity type and recorded its UUID in the master capability. The summon packet's list position represented a stored Minion position, not a traditional Minion type.

Those findings remain valid for the audited upstream `0.3.0` JAR. OVERLORD QUESTS must never infer campaign progression from:

- the live Minion-list index;
- current number of summoned Minions;
- Minion UUID ordering;
- UUID-derived renderer appearance;
- current entity colour data;
- the presence of the Master's Staff after the initial Brown bootstrap milestone.

## Historical blocker

At the time of the original audit, neither the upstream Minions Remastered `0.3.0` capability nor the then-current visual-addon architecture exposed a durable four-type progression API. Therefore Red, Green, and Blue handoff was correctly considered blocked at that point.

That conclusion is now superseded for the OVERLORD REIGN project by the newer OVERLORD Minions progression implementation described below. It must not be read as the current integration state.

## Current OVERLORD Minions progression authority

OVERLORD Minions Build #118 is the validated development dependency recorded by `docs/MINION_UNLOCK_INTEGRATION.md`.

The preserved implementation checkpoint `35dc7da09f39eb93f5c47eac2b5aad983bab0b95` in `magicienlord/Overlord_Minions` exposes the stable server-side API:

```java
com.overlordreign.minions.api.OverlordMinionProgression
```

with fixed slot identity from:

```java
com.overlordreign.minions.progression.MinionSlot
```

The public integration surface is:

```java
OverlordMinionProgression.isUnlocked(MinecraftServer server, MinionSlot slot)
OverlordMinionProgression.highestUnlocked(MinecraftServer server)
OverlordMinionProgression.unlock(MinecraftServer server, MinionSlot slot)
```

The fixed owner-side slot order is:

```text
0 Brown
1 Red
2 Green
3 Blue
```

The owner-side state is world-scoped `SavedData` named `overlord_minions_unlocks`. It persists the highest unlocked slot, clamps loaded state to the Brown through Blue range, treats already-open slots as idempotent success, rejects skipped slots, and keeps Brown owned by the Master's Staff bootstrap.

The owner API returns:

```text
UNLOCKED
ALREADY_UNLOCKED
OUT_OF_ORDER
BOOTSTRAP_OWNED_BY_STAFF
```

This resolves the original missing-progression-interface blocker without requiring Questlog to mutate private Minions state.

## Current quest-side integration

OVERLORD QUESTS now implements a bounded optional bridge to the documented public API only.

For later Minion tiers it exposes:

```json
{
  "type": "questlog:unlock_minion",
  "slot": "red",
  "auto_claim": true
}
```

and the authoritative owner-state objective:

```json
{
  "type": "questlog:minion_unlocked",
  "slot": "red",
  "required_amount": 1
}
```

Only Red, Green, and Blue are valid quest-side slots. Brown remains intentionally excluded because the Master's Staff bootstrap owns Brown access.

The bridge:

- calls the public `unlock` and `isUnlocked` methods only;
- treats `UNLOCKED` and `ALREADY_UNLOCKED` as successful reconciliation;
- leaves out-of-order or unavailable handoffs pending;
- retries completed pending external unlock rewards on player load;
- fails closed if the optional API is absent or incompatible;
- does not mirror owner-side Minion unlock state into Questlog persistence;
- does not mutate Minions Remastered roster state, private capability fields, or save NBT.

## Brown bootstrap

The production Brown recovery branch continues to observe the exact Master's Staff craft through `questlog:item_craft_stat` against:

```text
minionsremastered:masters_staff
```

This is sequence-break safe because the crafted-item statistic survives if the staff was crafted before the Questlog objective became active.

Questlog observes the milestone only. It does not grant Brown.

## Current implementation boundary

RESOLVED:

- stable Red, Green, and Blue owner-side progression interface;
- fixed Brown, Red, Green, Blue slot identities;
- persistent monotonic owner state;
- ordered unlock enforcement;
- idempotent repeated application;
- bounded Questlog reward bridge;
- bounded Questlog owner-state prerequisite;
- fail-closed behavior when the owner API is unavailable;
- player-load retry for pending completed handoffs.

STILL TO AUTHOR OR RUNTIME-VALIDATE:

- concealed production Red recovery milestone;
- concealed production Green recovery milestone;
- concealed production Blue recovery milestone;
- full-modpack runtime validation against the supplied Build #118 development JAR;
- save and reload verification of the complete Red to Green to Blue sequence in a disposable integration world;
- any separate Hive manifestation implementation required by the owning Minions system.

Static source verification and Questlog build validation do not substitute for that full-modpack runtime pass.

## Current decision

Brown quest observation: IMPLEMENTED ON QUEST SIDE.

Brown capability bootstrap: EXTERNAL OWNER, Master's Staff.

Red, Green, Blue Questlog bridge: IMPLEMENTED AGAINST VALIDATED BUILD #118 CONTRACT.

Red, Green, Blue owner persistence: EXTERNAL OWNER, PUBLIC API VERIFIED AT PRESERVED CHECKPOINT.

Production Red, Green, Blue campaign authoring: PENDING AUTHORED RECOVERY MILESTONES.

Full cross-mod runtime validation: PENDING.

The original external-interface blocker is closed. Any older Quest documentation that still describes that interface as unknown or unavailable is superseded by `docs/MINION_UNLOCK_INTEGRATION.md` and this updated audit.