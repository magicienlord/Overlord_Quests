# OVERLORD QUESTS Minion Type Unlock Integration

Status: PLANNED CROSS-MOD CONTRACT

This document records the approved gameplay integration boundary between OVERLORD QUESTS and the planned Minions Remastered fork. It does not define the story quests that grant the unlocks.

## Approved progression model

The Minions Remastered fork will expose four Minion-type slots corresponding to the four traditional Minion types:

1. Brown
2. Red
3. Green
4. Blue

Crafting the Minions Remastered staff unlocks only the first slot, Brown.

Red, Green, and Blue remain locked after staff crafting and are intended to be unlocked later by quest progression through durable quest markers.

The three later unlocks are independent. A player does not receive all four Minion types simply by obtaining the staff.

## Ownership boundary

OVERLORD QUESTS owns the campaign decision that a Minion type has been earned.

The Minions Remastered fork owns:

- the four-slot Minion-type system;
- the initial Brown unlock caused by staff crafting;
- persistence of unlocked Minion slots/types on its side;
- enforcing which Minion types can actually be selected, summoned, or assigned;
- the technical API, command, or marker-consumption surface used by OVERLORD QUESTS.

OVERLORD QUESTS must not infer Minion unlocks from inventory possession once Brown has been established. Red, Green, and Blue unlock only from explicit campaign markers.

## Quest-side representation

OVERLORD QUESTS already has monotonic world-scoped narrative facts and auto-claimed `questlog:set_fact` rewards. That mechanism is suitable for the quest-side meaning of a Minion unlock because the unlock represents a persistent campaign milestone rather than a reversible reputation value.

The preferred integration is therefore:

1. the relevant production quest completes;
2. the quest records one durable Minion-unlock marker/fact;
3. the Minions Remastered fork consumes or is notified of that marker and permanently opens the corresponding Minion slot/type;
4. subsequent reloads preserve the unlock without requiring the quest to fire again.

The exact production marker IDs are currently UNKNOWN. They must be fixed only after the Minions Remastered fork exposes its final integration surface and the campaign identifies the quests that unlock Red, Green, and Blue.

Do not invent final marker IDs in story content before that contract exists.

## Brown bootstrap exception

Brown is deliberately different from the other three types.

Brown unlocks when the Minions Remastered staff is crafted. This is a Minions Remastered gameplay bootstrap and does not require a quest reward or narrative fact from OVERLORD QUESTS.

A quest may still observe staff crafting or Brown availability for progression purposes if campaign design requires it, but Questlog must not be the authority that grants the first Brown slot.

## Red, Green, and Blue unlock semantics

For each later type, the unlock marker should be:

- boolean;
- monotonic during normal play;
- idempotent if applied more than once;
- durable across save/reload;
- safe if the Minions Remastered fork processes it after the originating quest has already completed;
- independent of the current number of spawned Minions;
- independent of temporary inventory state;
- independent of provider disposition or sidequest reputation.

The quest that grants each type is UNKNOWN until campaign authoring establishes it from the lore/source authority.

## Compatibility requirement

The integration must avoid a fragile direct dependency from ordinary quest definitions onto Minions Remastered implementation internals.

Preferred order of implementation quality:

1. stable public unlock API or event exposed by the Minions Remastered fork;
2. stable server command exposed by the fork and invoked by an auto-claimed Questlog command reward;
3. stable marker/fact polling bridge owned by the Minions fork.

Direct reflection, mixin access into private Minions Remastered fields, hard-coded save-NBT mutation, or client-only unlock state should be avoided.

## Sequence-break behavior

Because unlocks are permanent campaign capabilities, the bridge must reconcile existing progress safely.

If a world loads after a relevant unlock quest is already complete, or after its durable fact is already present, the corresponding Minion type must become available even if the original completion event is no longer replayed.

This is important for:

- existing development worlds;
- migration after the Minions fork is updated;
- recovery after temporary mod removal;
- save/load ordering;
- future quest-definition revisions that preserve the same production marker.

## Current implementation boundary

OVERLORD QUESTS does not yet contain production Red/Green/Blue unlock rewards because the external Minions Remastered unlock API/marker IDs are not yet available in this repository.

Once that interface is exposed, the Quest side should add one bounded integration adapter plus a development fixture that proves:

- Brown remains staff-crafting-owned;
- Red/Green/Blue begin locked;
- each synthetic quest marker unlocks only its assigned type;
- repeated application is harmless;
- save/reload preserves each unlock;
- an already-present marker reconciles correctly on world load;
- removing one development marker through admin tooling does not silently revoke a type in normal production semantics unless the Minions fork explicitly defines reversible debug behavior.

## Repository consistency note

The current `magicienlord/Overlord_Minions` status file still describes a thin visual addon with UUID-derived visual assignment. That architecture predates this four-slot progression decision and will need to be superseded or moved into the new Minions Remastered fork implementation. OVERLORD QUESTS should treat the four-slot model in this document as the planned integration target, not the older UUID-only visual-selection model.
