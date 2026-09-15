# OVERLORD REIGN Minion Recovery Authoring Contract

Status: PRODUCTION RECOVERY ANCHORS IMPLEMENTED / DEEPER DIEGETIC MANIFESTATION UNKNOWN

Implementation state: RED, GREEN, AND BLUE PRODUCTION RECOVERY SCENARIOS IMPLEMENTED

## 1. Purpose

This document records the authoring boundary for the traditional Red, Green, and Blue Minion recovery milestones after their production implementation.

The earlier version of this contract correctly prohibited inventing unsupported present-day geography, arbitrary Hive locations, or native-mod dependencies while the production recovery scenarios were unresolved. That authoring phase is now superseded by the implemented campaign definitions.

The implemented gameplay recovery anchors do not resolve every in-universe detail. In particular, they do not invent a physical Hive transport mechanic, a new Hive location, or a visible Hive manifestation that the owning Minion implementation does not provide.

## 2. Authority

Authoring and maintenance must reconcile:

1. `magicienlord/Overlord_Lore_and_Canon` as read-only lore/source authority;
2. `docs/CAMPAIGN_AUTHORING_CONTRACT.md` for campaign-state and spoiler rules;
3. `docs/MINION_UNLOCK_INTEGRATION.md` for cross-mod ownership;
4. `docs/MINION_TYPE_RECOVERY_CONTRACT.md` for the implemented production sequence;
5. the public `OverlordMinionProgression` API in the validated OVERLORD Minions progression baseline;
6. verified current modpack mechanics before adding any new advancement, structure, item, biome, boss, dimension or provider dependency.

Implementation convenience must not be used to fill a remaining lore UNKNOWN.

## 3. Established REIGN constraints

The following constraints remain authoritative for the implementation:

- Brown, Red, Green and Blue are the four traditional Minion tribes.
- Brown is the bootstrap tribe and is restored through the Master's Staff path owned by the Minion implementation.
- Later capability order is fixed as Brown, Red, Green, Blue.
- Red, Green and Blue are campaign-earned capabilities.
- The vanilla Nether is the Netherworld in OVERLORD REIGN.
- During the Silence, Minion forces became scattered or dormant and ordinary spawning was impaired without an active Master.
- The current Overlord restores the Minion forces.
- Original-game Hive recovery is a source precedent for restoration of corresponding tribe availability.
- OVERLORD REIGN does not require the player to physically carry a Hive as the implementation mechanic.

The exact physical or magical manifestation of a recovered Hive in the present-day world remains outside Questlog unless separately established by authority and implemented by the owning system.

## 4. Source precedents

Original-game recovery material constrains identity, ordering, tone and environmental associations. It does not automatically establish present-day REIGN geography.

### 4.1 Red

Overlord 1 source material associates Red recovery with the Hells Kitchen sequence and state records such as:

```text
D1_FINDREDS
D1_REDMINIONS
TOWER_REDHIVE
```

Red identity includes fire association, ranged combat and fire immunity.

This does not establish a present-day REIGN location named Hells Kitchen or a mandatory imported source-game structure.

### 4.2 Green

Overlord 1 source material associates Green recovery with the Green Cave / Viridian Caverns sequence and records such as:

```text
D2_GREENLAIR
D2S1_GREENHIVE
TOWER_GREENHIVE
```

Green identity includes stealth, back attacks and poison immunity.

This does not establish a present-day REIGN Viridian Caverns location or a mandatory imported source-game structure.

### 4.3 Blue

Overlord 1 source material associates Blue recovery with the Blue Cave / Moist Hollows sequence and records such as:

```text
D3_BLUECAVE
D3S1_SAVEBLUES
D3S1_SERPENT
D3S1_GEYSER
D3S1_BLUEHIVE
TOWER_BLUEHIVE
```

Blue identity includes magical aptitude, water traversal, relative combat fragility and resurrection.

The source sequence informs Blue identity and environmental logic but does not establish a present-day Moist Hollows location, a mandatory aquatic boss, geyser mechanic or imported source-game corridor.

## 5. Adopted production gameplay anchors

The production campaign now uses sequence-break-safe ordinary Minecraft materials as the recovery tests:

```text
Red   -> minecraft:blaze_rod
Green -> minecraft:spider_eye
Blue  -> minecraft:prismarine_crystals
```

These are implemented Quest objectives and are the actual current recovery anchors. They are not placeholders, temporary Hive proxies, fictional crafting ingredients consumed by OVERLORD Minions, or claims that a physical Hive object exists in the Minecraft implementation.

The production action/confirmation pairs are:

```text
campaign/expansion/restore_reds.json
campaign/expansion/reds_return.json
campaign/expansion/restore_greens.json
campaign/expansion/greens_return.json
campaign/expansion/restore_blues.json
campaign/expansion/blues_return.json
```

Each action quest performs the authored field test and requests the corresponding owner-side unlock. Each following Gnarl confirmation waits for authoritative owner state before recording the recovery as narrative history.

## 6. Technical unlock contract

The permanent capability owner is `Overlord_Minions`.

Production unlock rewards use the Questlog integration surface rather than modifying Minion state directly.

### Red

`restore_reds` follows `questlog:campaign/expansion/the_reign_takes_shape`, requires one Blaze Rod, then auto-claims:

```json
{
  "type": "questlog:unlock_minion",
  "slot": "red",
  "auto_claim": true
}
```

`reds_return` requires both completion of the action quest and authoritative `questlog:minion_unlocked` Red state.

### Green

`restore_greens` follows `reds_return`, requires one Spider Eye and auto-claims the Green unlock.

`greens_return` requires both completion of the action quest and authoritative Green owner state.

### Blue

`restore_blues` follows `greens_return`, requires Prismarine Crystals and auto-claims the Blue unlock.

`blues_return` requires both completion of the action quest and authoritative Blue owner state.

### Ownership rules

Questlog must not:

- maintain a duplicate Red, Green or Blue capability boolean;
- mutate Minion roster internals directly;
- create a second Brown unlock route;
- treat a completed action quest as proof that the owner API accepted the unlock;
- infer Minion capability from renderer appearance, current Minion count, UUID order, provider state or inventory possession alone.

The recovery facts written by confirmation quests are narrative history, not duplicate capability ownership.

## 7. Sequence-break policy

The material objectives use normal item-obtain observation, allowing already-held legitimate items to satisfy the recovery test when Questlog evaluates the objective.

The owner-side sequence is independently fail-closed. Green cannot become narratively confirmed until Red has been accepted by the owner system, and Blue cannot become narratively confirmed until Green has been accepted.

Future changes should prefer verified native history/state surfaces where the player may legitimately complete an underlying action before receiving a formal quest. Do not invent a duplicate collectible or advancement merely to make recovery trackable.

## 8. Authoring prohibitions still in force

Do not:

- assign a new Red, Green or Blue location solely because its colour/theme matches;
- import an original-game named location into current REIGN geography without explicit adoption;
- claim the player physically carries a Hive unless that mechanic is explicitly adopted;
- add Theurgy, Ars Elixirum, Biomancy, Eidolon or another mod as a mandatory recovery dependency merely because its theme is convenient;
- invent a boss, structure, advancement, item ID, dimension gate or provider role without verification and authority;
- turn every source-local event into a hard quest prerequisite;
- create a hidden morality, friendship, domination or reputation variable around Minion recovery;
- use civilization disposition as a surrogate Minion progression flag;
- use Questlog narrative facts as duplicate owner unlock state.

## 9. Remaining UNKNOWNs

The following are not defined by the current Quest implementation and must not be silently invented:

- any exact present-day physical Hive location for Red, Green or Blue;
- whether a recovered Hive receives a visible world-state manifestation;
- the exact physical/magical mechanism by which the campaign milestone corresponds to Hive restoration beyond the established capability recovery outcome;
- whether any original-game named recovery location will later be deliberately adopted into current REIGN geography.

These UNKNOWNs do not make the implemented production recovery quests provisional. They constrain only additional diegetic/world manifestation beyond the established recovery progression.

## 10. Validation

`tools/validate_minion_recovery_contract.py` guards the production action/confirmation pairs, fixed order, exact material anchors, auto-claimed owner API unlocks, owner-state confirmation, narrative facts, production index membership and Brown exclusion.

The corresponding Minion Recovery Contract workflow is part of the repository qualification suite.

The assembled cross-mod runtime boundary remains `docs/MINION_PROGRESSION_TEST_PROTOCOL.md`.

Required assembled evidence includes:

```text
Brown bootstrap
-> Red recovery and owner unlock
-> save/reload
-> Green recovery and owner unlock
-> save/reload
-> Blue recovery and owner unlock
-> save/reload
```

It also includes owner-side out-of-order rejection and idempotent repeated handoff.

Production Red, Green, and Blue recovery scenarios: IMPLEMENTED.

Repository contract validation: QUALIFIED AT THE CHECKPOINT RECORDED IN `docs/FULL_INSTANCE_QUALIFICATION.md`.

Assembled-instance Minion progression acceptance: PENDING.
