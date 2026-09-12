# Minions Remastered Runtime Integration Audit

Status: TECHNICAL SOURCE CHECK / EXTERNAL INTEGRATION BLOCKER CONFIRMED

Date: 2026-09-12

## Scope

This audit records the actual Minions Remastered integration surface presently available to OVERLORD QUESTS. It distinguishes the installed upstream gameplay JAR and the current `magicienlord/Overlord_Minions` visual addon from the planned four-type progression contract in `docs/MINION_UNLOCK_INTEGRATION.md`.

No external Minions repository or JAR is modified by this audit.

## Installed gameplay JAR

The current OVERLORD REIGN modpack contains:

```text
minionsremastered-0.3.0.jar
```

The JAR declares:

```text
modId=minionsremastered
version=0.3.0
Minecraft 1.20.1
Forge loader 47+
```

The Master's Staff remains registered as:

```text
minionsremastered:masters_staff
```

Its installed shaped recipe uses raw gold, emerald, and stick. OVERLORD QUESTS may therefore continue to use the exact item ID as the Brown bootstrap observation point while the replacement fork preserves that registry ID.

## Current master capability

The installed `IMasterCapability` exposes gameplay ownership and command state for Minions. Its public surface includes:

- player ownership;
- a list of Minion UUIDs;
- adding/removing Minions;
- four live Minion positions through the current hard cap;
- per-position Minion inventory data;
- current order;
- container target state;
- pause state;
- synchronization.

The implementation's maximum of four is a cap on simultaneously tracked Minion UUID positions. It is NOT a Brown/Red/Green/Blue type-unlock model.

This distinction is critical. The existing four UUID positions must not be treated as the four REIGN Minion-type progression slots.

## Persistence surface

The installed master capability serializes Minion UUID positions, Minion inventory data, order state, container targeting, and pause state.

It does not serialize:

- Brown unlocked;
- Red unlocked;
- Green unlocked;
- Blue unlocked;
- a Minion type associated with each summon position;
- Hive-return state for the four traditional tribes.

Therefore the installed capability does not provide a durable progression signal that OVERLORD QUESTS can safely target for Red, Green, or Blue recovery.

## Staff and summon behavior

The installed Master's Staff opens/operates the existing Minions Remastered control path. Summoning creates the single gameplay entity type and records the resulting Minion UUID in the master capability. The current summon packet accepts a Minion-list position for restoring a specific stored Minion, not a traditional Minion type.

The installed mod contains one gameplay entity registration surface for the Minion rather than four traditional type entities.

OVERLORD QUESTS must not infer Brown/Red/Green/Blue progression from:

- the Minion-list index;
- current number of summoned Minions;
- Minion UUID ordering;
- current entity colour data;
- the presence of the Master's Staff alone after the initial Brown bootstrap milestone.

## Current `magicienlord/Overlord_Minions` repository

The current `Overlord_Minions` main branch still describes a thin client-side visual addon. Its documented architecture:

- leaves the Minions Remastered gameplay entity untouched;
- replaces the renderer only;
- chooses Brown, Red, Green, or Blue appearance deterministically from entity UUID;
- deliberately adds no gameplay Minion type state;
- adds no type-unlock persistence or synchronization.

That repository therefore does not yet satisfy the four-slot progression contract required by OVERLORD QUESTS.

Its current UUID-derived visual assignment must not be mistaken for campaign progression authority.

## Quest-side consequence

The current production Brown recovery quest may safely observe the exact Master's Staff craft because:

- the item ID exists in the installed gameplay mod;
- the planned replacement fork is expected to preserve the staff as the Brown bootstrap action;
- `questlog:item_craft_stat` makes that observation sequence-break safe;
- Questlog does not grant Brown itself.

The later Red, Green, and Blue recovery rewards remain blocked from production implementation until the external Minions gameplay layer exposes a stable type-unlock interface.

This is an external integration blocker, not missing Questlog state machinery. Questlog already has durable monotonic narrative facts suitable for representing the campaign decision once the owning Minions implementation can consume it.

## Required external contract

The Minions gameplay implementation needs a stable server-authoritative surface equivalent to:

```text
isTypeUnlocked(player, type)
unlockType(player, type)
```

or an equally stable command/event/marker bridge.

The external owner must also define durable Hive-return reconciliation if Hive manifestation belongs to the Minions implementation.

Required semantic properties remain:

- Brown bootstrap from the Master's Staff;
- Red, then Green, then Blue ordering;
- idempotent repeated application;
- save-persistent unlock state;
- reconciliation on load when the Questlog marker predates the integration update;
- no accidental use of the existing four live-Minion UUID positions as type slots;
- no dependence on client-only renderer assignment;
- no direct Questlog mutation of private Minions save NBT.

## Preferred bridge order

Implementation preference remains:

1. stable public server API/event from the Minions fork;
2. stable server command owned by that fork;
3. marker/fact reconciliation bridge owned by that fork.

Reflection into private capability fields, mixin mutation of private state, or direct save-NBT surgery remains rejected for production integration.

## Current decision

Brown quest observation: IMPLEMENTED ON QUEST SIDE.

Brown capability unlock: EXTERNAL OWNER.

Red/Green/Blue campaign markers: DEFERRED UNTIL EXTERNAL INTERFACE EXISTS.

Red/Green/Blue capability unlock and Hive-return persistence: EXTERNAL OWNER.

No further Questlog-side guesswork is justified at this boundary. Campaign authoring may continue around the blocker without serializing unrelated early content behind it.