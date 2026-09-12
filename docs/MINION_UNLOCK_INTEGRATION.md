# OVERLORD QUESTS Minion Type Unlock Integration

Status: PLANNED CROSS-MOD CONTRACT

This document records the approved gameplay integration boundary between OVERLORD QUESTS and the planned Minions Remastered fork. It does not define the story quests that grant the unlocks.

## Approved progression model

The Minions Remastered fork will expose four zero-based Minion-type slots with a fixed type mapping:

- slot `0` = Brown
- slot `1` = Red
- slot `2` = Green
- slot `3` = Blue

Crafting the Minions Remastered staff unlocks only slot `0`, Brown.

Red, Green, and Blue remain locked after staff crafting and are intended to be unlocked later by quest progression through durable quest markers.

The campaign unlock sequence is fixed and must follow the slot order:

`Brown -> Red -> Green -> Blue`

Quest progression must therefore unlock slot `1` before slot `2`, and slot `2` before slot `3`. Production quest authoring must not create a route that grants Green before Red or Blue before Green.

This ordering is a technical and campaign progression constraint. The exact quests, narrative circumstances, and final marker IDs that grant Red, Green, and Blue remain UNKNOWN until campaign authoring reaches those sections and the Minions Remastered fork exposes its final integration surface.

## Ownership boundary

OVERLORD QUESTS owns the campaign decision that the next Minion type has been earned.

The Minions Remastered fork owns:

- the four-slot Minion-type system;
- the fixed slot-to-type mapping `0=Brown, 1=Red, 2=Green, 3=Blue`;
- the initial Brown unlock caused by staff crafting;
- persistence of unlocked Minion slots/types on its side;
- enforcing which Minion types can actually be selected, summoned, or assigned;
- the technical API, command, or marker-consumption surface used by OVERLORD QUESTS.

OVERLORD QUESTS must not infer Minion unlocks from inventory possession once Brown has been established. Red, Green, and Blue unlock only from explicit campaign markers and only in the approved sequence.

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

## Early Gnarl branch anchor

The Brown bootstrap is also a PLANNED early Gnarl campaign branch.

Gnarl will have an opening branch whose gameplay task is to craft the Minions Remastered staff and thereby regain access to the Brown Minions. Questlog observes the staff-crafting milestone and advances the branch, while Minions Remastered remains the authority that actually unlocks slot `0`.

This establishes the first Minion-recovery step as a real gameplay action rather than a purely narrative grant.

Only the structural anchor is decided at this stage:

- speaker/quest source: Gnarl;
- task: craft the Minions Remastered staff;
- gameplay consequence: Minions Remastered unlocks Brown, slot `0`;
- quest consequence: the Gnarl branch recognizes that Brown access has been restored and can progress onward;
- later Minion recovery remains ordered Red, then Green, then Blue.

The exact quest title, dialogue, prerequisite circumstances, follow-up branch, and production marker IDs remain UNKNOWN until campaign authoring begins after the visual-foundation gate is complete. Do not invent those details early.

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

The later unlocks are not order-independent. Their prerequisite relationship is fixed:

- Red, slot `1`, requires the Brown bootstrap state from slot `0`;
- Green, slot `2`, requires Red to have been unlocked;
- Blue, slot `3`, requires Green to have been unlocked.

Quest definitions should enforce this sequence authoritatively. The Minions Remastered bridge should also reject, defer, or safely reconcile an out-of-order marker rather than exposing a later slot while an earlier slot is still locked.

The quest that grants each type is UNKNOWN until campaign authoring establishes it from the lore/source authority.

## Compatibility requirement

The integration must avoid a fragile direct dependency from ordinary quest definitions onto Minions Remastered implementation internals.

Preferred order of implementation quality:

1. stable public unlock API or event exposed by the Minions Remastered fork;
2. stable server command exposed by the fork and invoked by an auto-claimed Questlog command reward;
3. stable marker/fact polling bridge owned by the Minions fork.

Direct reflection, mixin access into private Minions Remastered fields, hard-coded save-NBT mutation, or client-only unlock state should be avoided.

## Sequence-break behavior

Because unlocks are permanent campaign capabilities, the bridge must reconcile existing progress safely without violating the fixed slot order.

If a world loads after a relevant unlock quest is already complete, or after its durable fact is already present, the corresponding Minion type must become available even if the original completion event is no longer replayed. Reconciliation must walk progression in order so a later stored marker does not bypass an earlier required slot.

This is important for:

- existing development worlds;
- migration after the Minions fork is updated;
- recovery after temporary mod removal;
- save/load ordering;
- future quest-definition revisions that preserve the same production marker.

## Current implementation boundary

OVERLORD QUESTS does not yet contain production Red/Green/Blue unlock rewards because the external Minions Remastered unlock API/marker IDs are not yet available in this repository.

The early Gnarl Brown branch is also not yet authored as production content because the current project priority is the shared visual foundation. Its structural role is nevertheless fixed by this document.

Once the Minions Remastered interface is exposed, the Quest side should add one bounded integration adapter plus a development fixture that proves:

- Brown remains staff-crafting-owned as slot `0`;
- the early Gnarl branch can observe the staff-crafting/Brown bootstrap milestone without becoming the authority that unlocks Brown;
- Red/Green/Blue begin locked;
- the Red marker unlocks only slot `1`;
- the Green marker cannot expose slot `2` before Red is unlocked;
- the Blue marker cannot expose slot `3` before Green is unlocked;
- the complete valid sequence is `0 Brown -> 1 Red -> 2 Green -> 3 Blue`;
- repeated application is harmless;
- save/reload preserves each unlock;
- already-present markers reconcile correctly on world load in slot order;
- removing one development marker through admin tooling does not silently revoke a type in normal production semantics unless the Minions fork explicitly defines reversible debug behavior.

## Repository consistency note

The current `magicienlord/Overlord_Minions` status file still describes a thin visual addon with UUID-derived visual assignment. That architecture predates this four-slot progression decision and will need to be superseded or moved into the new Minions Remastered fork implementation. OVERLORD QUESTS should treat the four-slot model in this document as the planned integration target, not the older UUID-only visual-selection model.
