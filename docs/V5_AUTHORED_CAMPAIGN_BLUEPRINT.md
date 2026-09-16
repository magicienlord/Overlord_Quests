# OVERLORD REIGN V5 Authored Campaign Blueprint

Status: WORKING V5 QUEST BLUEPRINT

Purpose: convert `V5_CAMPAIGN_SYSTEM_AUTHORITY.md` into the concrete authored quest graph that later `Overlord_Quests` implementation must translate without reopening campaign design.

This document does not contain final quest names or final dialogue. Beat IDs are internal authoring references only.

## 1. Beat contract

Every V5 authored beat must eventually specify:

- campaign role and purpose;
- prerequisite state;
- visible player accomplishment;
- authoritative completion signal or required technical bridge;
- completion state written;
- content unlocked;
- presenter or provider ownership where relevant;
- sequence-break behavior where relevant;
- what native progression remains outside Questlog.

A beat exists only when it performs a real campaign function. Native progression, ambient play, or a Rambling should not be promoted into a quest merely to fill space.

## 2. Central opening

### CEN-000: Awakening and Tower summit

Status: PLANNED

Purpose:

- establish the current Overlord as active in the new Dark Tower;
- establish Gnarl as the principal early campaign presenter;
- move the player from awakening into the first actionable restoration objective.

Prerequisite:

- fresh campaign start.

Visible accomplishment:

- reach the Tower summit / designated opening presentation point and complete the opening Gnarl presentation.

Completion state:

- ordinary quest completion only unless later implementation proves a durable campaign fact is required.

Unlocks:

- `CEN-010`;
- the initial Tower Restoration branch surface once `CEN-010` completes.

Implementation boundary:

- the exact summit trigger and presentation trigger are technical translation details. V5 does not require fixed world coordinates.

### CEN-010: Master's Staff and first Brown proof

Status: PLANNED

Purpose:

- put the Overlord's core command tool in hand;
- prove that the Minion system is functioning;
- restore the first usable Brown capability without splitting Staff acquisition and first Minion proof into separate quests.

Prerequisite:

- `CEN-000` complete.

Visible accomplishments:

1. craft the Master's Staff;
2. use the restored Minion capability to summon the first Brown.

Authoritative state:

- Brown ownership / unlock belongs to the Overlord Minions owner API;
- Questlog must not duplicate Brown ownership as an ordinary narrative fact.

Completion:

- completes only after both Staff acquisition and first Brown proof are satisfied;
- implementation must reconcile the exact API ordering required to make the Brown summon possible while preserving the one-quest player experience.

Unlocks:

- semi-open initial Tower Restoration;
- Red restoration;
- ordinary world, civilization, Adventure, farming, magic, and side content as their own access conditions permit.

## 3. Initial Tower Restoration quest graph

Status: PLANNED

Tower Restoration is one overarching questline with semi-open internal facility beats. It is not fourteen unrelated mod tutorials.

After `CEN-010`, the player may pursue the following facility beats in any order unless a native recipe or capability creates a genuine dependency.

Completion of a facility beat means that the corresponding Tower function has become operational. It does not require mastery of the underlying mod.

No facility beat should use a fixed coordinate requirement. Exact technical verification may use craft, obtain, place, interact, activation, or other narrow source-backed signals during implementation.

### TWR-THRONE: Throne function

Purpose:

- establish the physical seat of rule.

Visible accomplishment:

- acquire and install the approved Necrolord Chair from Fantasy's Furniture as the functional Throne anchor.

Completion state:

- quest completion.

Native / sandbox remainder:

- later throne-room decoration remains ordinary Tower building.

### TWR-FORGE: Forge function

Purpose:

- make the Tower's forge operational.

Visible accomplishment:

- install a functional Hot Iron forge setup using the approved Smithing Anvil / Crucible family as technically appropriate.

Completion state:

- quest completion.

Implementation boundary:

- exact minimal block combination must be resolved from the installed Hot Iron build without expanding this beat into Hot Iron mastery.

Native / sandbox remainder:

- later smithing and Hot Iron progression remain native or belong to other authored content where justified.

### TWR-STORAGE: Storage function

Purpose:

- establish operational centralized Tower storage.

Visible accomplishment:

- establish a functional Storage Drawers storage network using controller / drawer substrate.

Completion state:

- quest completion.

Native / sandbox remainder:

- capacity expansion, organization, upgrades, and ordinary storage use remain sandbox play.

### TWR-ARMORY: Armory function

Purpose:

- establish a real equipment storage and display facility rather than an empty room.

Visible accomplishment:

- install a mixed functional armory using multiple approved equipment display / storage families.

Completion state:

- quest completion.

Implementation boundary:

- exact minimum family set is technical tuning work and must not turn into collectionism.

Native / sandbox remainder:

- filling the Armory with every weapon or armor set is never required.

### TWR-TREASURY: Treasury function

Purpose:

- establish the Tower's dedicated wealth-storage function.

Visible accomplishment:

- acquire and install the approved Gold Barrel from Goblins Tyranny.

Completion state:

- quest completion.

Native / sandbox remainder:

- no mandatory gold threshold or treasury tier grind follows.

### TWR-WAYGATES: WayGate function

Purpose:

- restore the Tower's world-travel infrastructure.

Visible accomplishment:

- establish and activate the Tower's Waystone-based WayGate anchor.

Completion state:

- quest completion.

Consequences:

- Waystones remain the implementation substrate;
- free post-discovery travel is preserved.

Native / sandbox remainder:

- discovering and placing additional Waystones remains ordinary progression unless a later authored quest specifically needs one.

### TWR-ARENA: Arena function

Purpose:

- make the Tower Arena visibly usable as a containment / combat facility.

Visible accomplishment:

- acquire and install the approved Supplementaries Cage anchor.

Completion state:

- quest completion.

Native / sandbox remainder:

- no generic arena fight checklist is created by this restoration beat.

### TWR-JAIL: Jail function

Purpose:

- make the Tower Jail operational as a secure confinement facility.

Visible accomplishment:

- acquire and install the approved Big Iron Grate from Abyssal Decor.

Completion state:

- quest completion.

Native / sandbox remainder:

- ordinary cell furnishing and architecture remain Tower building.

### TWR-ALCHEMY: Alchemy room function

Purpose:

- activate the selected Tower Alchemy workspace.

Visible accomplishment:

- install and make usable the established Alchemy workstation substrate.

Completion state:

- quest completion.

Boundary:

- deeper alchemical progression is not part of Tower Restoration.

### TWR-THEURGY: Theurgy room function

Purpose:

- activate the selected Tower Theurgy workspace.

Visible accomplishment:

- install and make usable the established Theurgy workstation substrate.

Completion state:

- quest completion.

Boundary:

- material mastery and transmutation progression belong to the Theurgy arc.

### TWR-GLUTTONY: Gluttony room function

Purpose:

- activate the selected Tower Gluttony / magical-cuisine workspace.

Visible accomplishment:

- install and make usable the established Gluttony workstation substrate.

Completion state:

- quest completion.

Boundary:

- Farmer's Spell / Gluttony mastery remains its own compact magic progression.

### TWR-SPELL-STUDY: Spell Study function

Purpose:

- establish the Tower space from which the Overlord can pursue formal spell study and spellmaking.

Visible accomplishment:

- install and make usable the established spell-study workstation substrate.

Completion state:

- quest completion.

Boundary:

- Iron's Spells mastery is independent progression after room activation.

### TWR-EIDOLON: Eidolon room function

Purpose:

- activate the selected Tower ritual-occult workspace.

Visible accomplishment:

- install and make usable the established Eidolon workstation substrate.

Completion state:

- quest completion.

Boundary:

- Sacred / Wicked mastery belongs to the Eidolon arc.

### TWR-BIOMANCY: Biomancy room function

Purpose:

- activate the selected Tower flesh-magic / biological-engineering workspace.

Visible accomplishment:

- install and make usable the established Biomancy workstation substrate.

Completion state:

- quest completion.

Boundary:

- Primordial, serum, decomposition, Bio Forge, Bio Lab, and advanced engineering progression belong to the Biomancy arc.

## 4. Minion Restoration quest graph

Status: PLANNED

Minion Restoration runs alongside Tower Restoration after `CEN-010`, but tribe order is fixed.

Actual Minions are not required to accompany the Overlord during the feats. The feat proves the Master's capability, then Questlog invokes the Minion owner API to establish the authoritative unlock.

### MIN-RED

Prerequisites:

- `CEN-010` complete;
- Brown capability established.

Visible accomplishments:

1. reach the Netherworld as normal progression allows;
2. kill a Blaze;
3. obtain / return with a Blaze Rod.

Authoritative completion consequence:

- invoke the Overlord Minions owner API to unlock Red.

Sequence breaking:

- prior legitimate Blaze kills and Blaze Rod acquisition should be recognized where technically possible rather than forcing an artificial respawn or duplicate feat.

### MIN-GREEN

Prerequisites:

- Red owner-state unlock established.

Visible accomplishments:

1. brew a Potion of Poison;
2. deliberately become poisoned;
3. kill a Witch while poisoned.

Authoritative completion consequence:

- invoke the owner API to unlock Green.

Implementation boundary:

- the final kill must prove the Witch died while the player carried Poison. If existing Questlog primitives cannot express the conjunction precisely, use the narrowest compatibility bridge rather than weakening the accomplishment.

### MIN-BLUE

Prerequisites:

- Green owner-state unlock established.

Visible accomplishments:

1. prepare Water Breathing;
2. enter an Ocean Monument;
3. kill an Elder Guardian.

Authoritative completion consequence:

- invoke the owner API to unlock Blue.

Sequence breaking:

- an already-discovered Monument or legitimate prior Elder Guardian kill must be reconciled rather than invalidated where durable history exists.

## 5. Silent initial-readiness gate

Status: PLANNED

There is no visible "Tower complete" quest and no completion popup for the aggregate gate.

The central readiness condition becomes true only when both groups below are satisfied.

### 5.1 Tower readiness set

All of the following facility beats are complete:

- `TWR-THRONE`;
- `TWR-FORGE`;
- `TWR-STORAGE`;
- `TWR-ARMORY`;
- `TWR-TREASURY`;
- `TWR-WAYGATES`;
- `TWR-ARENA`;
- `TWR-JAIL`;
- `TWR-ALCHEMY`;
- `TWR-THEURGY`;
- `TWR-GLUTTONY`;
- `TWR-SPELL-STUDY`;
- `TWR-EIDOLON`;
- `TWR-BIOMANCY`.

Minion Infrastructure is already present and is not a restoration beat.

Dragon Den is not part of this gate.

### 5.2 Horde readiness set

Authoritative Minion owner state confirms all four traditional slots are available:

- Brown;
- Red;
- Green;
- Blue.

### 5.3 Gate consequence

When Tower readiness and Horde readiness are both true:

- the five Bosses'Rise subcampaign roots become eligible concurrently;
- no civilization resolution count is checked;
- no optional Adventure completion count is checked;
- no visible aggregate readiness quest is completed in front of the player.

## 6. Bosses'Rise concurrency shell

Status: PLANNED, beat-level subcampaigns still require source-backed authoring

Five dedicated central subcampaigns open from the readiness gate:

- BR-SKOR;
- BR-SIROK;
- BR-ASHLORD;
- BR-HELVAR;
- BR-NERAKYSS.

Rules:

- all five are central Campaign Checkpoints;
- all five may be pursued concurrently once unlocked;
- each receives a dedicated multi-quest authored sequence rather than one generic boss-kill quest;
- each sequence must establish its own historical / environmental mechanism before the confrontation;
- early legitimate discovery or boss death must be reconciled where source state permits;
- there is no visible master quest that reduces the phase to "kill five bosses";
- each terminal boss beat writes its own explicit checkpoint completion;
- a silent aggregate checks for all five checkpoints.

When all five checkpoints are complete, one short Gnarl interpretation / preparation transition becomes available. That transition must add no grind and leads directly into the End / Cataclysm Dimension expedition.

Exact BR subcampaign beats are the next central-story authoring target.

## 7. Blueprint continuity rules

1. Side content discovered after `CEN-010` remains available according to its own world and capability conditions.
2. Civilizations are not required for central readiness or Bosses'Rise completion.
3. Native dimension access is not artificially blocked merely because a later quest has not introduced that dimension.
4. Sequence breaking should be acknowledged rather than punished whenever durable source state can prove the accomplishment.
5. No destructive optional choice may create a hidden central-campaign soft-lock.
6. The Dark Tower remains an evolving Minecraft base after these initial functions are operational.
7. Questlog tracks authored campaign accomplishments, not every sandbox activity performed inside the Tower.
