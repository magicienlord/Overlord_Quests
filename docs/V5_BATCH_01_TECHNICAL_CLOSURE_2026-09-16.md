# OVERLORD REIGN V5 Batch 01 Technical Closure

Status: VERIFIED TECHNICAL FACT / AUTHORING SUPPORT

Date: 2026-09-16

Purpose: record source facts verified after Decision Batch 01 that constrain implementation without adding campaign intent. This document does not select unresolved authored alternatives.

## 1. Exact supplemental artifacts inspected

```text
block_factorys_bosses-2.1.2-forge-1.20.1.jar
SHA-256 8c61087aa483f90861451682519582e03fa76e773d855e35180a8ed3428938bc

iceandfire-2.1.13-1.20.1-beta-5.jar
SHA-256 2b80245fc9b7d6fdc61d71f9892f4c6114eb7f303f65634845aaab25f84d1e82

ice_and_fire_delight-forge-1.20.1-0.2.5.jar
SHA-256 29a13d459b907a57ecaebeffef6b1b1fa8a0386e30e8420222c5699d3ec809f8

ice_and_fire_spellbooks-2.3.2-1.20.1.jar
SHA-256 c15f6234274407976c791f2d1b3776d0aacc5138341aaf81367bd9e47a7f13a5

overlord-depths-1.0.5-overlord.0.2.jar
SHA-256 9992949e47e97097be36c8d7f2835b3df15d3e1568af0ce087f3695ed524e4e1

nycto-forge-1.20.1-overlordreign-1.0.0-alpha.4.jar
SHA-256 975aafff0bf35714229f173c27cc78fd17f3d1ed36dae500cbd9b963a5a206e9

overlord_minions-0.1.0-dev.jar
SHA-256 5db98127d176ad12d63a9ecf0572bcb6fc6dddfc1ca7d1e45789a1ad18914e56

Overlord_Quests-1.20.1-e0a0c866-statfix.jar
SHA-256 4a95d74896f02ca2107ff0b48a14453a72bb13a5128ce8973eeda8a571b79915
```

## 2. Q005 WayGates placement tracking

Decision Batch 01 plus the later Overlord clarification requires the Tower WayGates restoration objective to track only placement of the Tower Waystone.

Verified current Questlog support:

- objective type `block_place` exists;
- `BlockPlaceObjective` listens to the block placement event;
- it tests the placed `BlockState` against the configured objective predicate;
- it increments the objective only when the placed state matches.

Therefore no authored acquisition or travel objective is required to make Q005 technically expressible.

The intended wild acquisition of the Waystone remains campaign context only. The tracked restoration signal can remain the placement event itself.

## 3. Minion progression ownership

The supplied Minions build exposes:

```text
com.overlordreign.minions.api.OverlordMinionProgression
```

with server-owned progression methods including:

```text
isUnlocked(MinecraftServer, MinionSlot)
highestUnlocked(MinecraftServer)
unlock(MinecraftServer, MinionSlot)
```

The persistent owner state is:

```text
com.overlordreign.minions.progression.MinionUnlockState
```

The exact slot enum is:

```text
BROWN
RED
GREEN
BLUE
```

The supplied Questlog build already exposes `MinionUnlockedObjective` and an `OverlordMinionProgressionBridge` that query the server-owned Minion progression state.

Result: Q011 can be translated without inventing a duplicate Questlog-owned Minion state. The visible quest can invoke the Minion owner API at completion and later state checks can read that same authoritative owner state.

## 4. Bosses' Rise exact native surfaces

The supplied Block Factory's Bosses artifact contains exactly five named boss structures matching the approved central bosses:

```text
block_factorys_bosses:dragon_tower
block_factorys_bosses:kraken_ship
block_factorys_bosses:sandworm_nest
block_factorys_bosses:underworld_arena
block_factorys_bosses:yeti_hideout
```

It also contains dedicated kill advancements:

```text
block_factorys_bosses:kill_dragon
block_factorys_bosses:kill_kraken
block_factorys_bosses:kill_sandworm
block_factorys_bosses:kill_underworld_knight
block_factorys_bosses:kill_yeti
```

and a native all-boss challenge:

```text
block_factorys_bosses:kill_all_bosses
```

The exact boss entity identities are:

```text
block_factorys_bosses:infernal_dragon      -> Ashlord, The Infernal Dragon
block_factorys_bosses:kraken               -> Nerakyss, The Kraken
block_factorys_bosses:sandworm             -> Sirok, The Sandworm
block_factorys_bosses:underworld_knight    -> Helvar, the Underworld Knight
block_factorys_bosses:yeti                 -> Skor, The Yeti
```

The source therefore supplies exact visit and kill surfaces for the locate and defeat stages of the approved three-stage Bosses'Rise skeleton.

It does not supply a native advancement that proves the V5-specific Cataclysm interpretation required by each investigation stage. Exact investigation objectives remain authored work and must not be invented from the existence of the lair structures.

## 5. Ice & Fire dragon mastery technical signals

The pinned beta 5 source confirms:

- `EntityDragonBase.getDragonStage()` exists;
- Stage 3 begins at 50 dragon age-days;
- Stage 4 begins at 75 days;
- Stage 5 begins at 100 days;
- `EntityDragonBase.isMale()` exposes sex;
- hatched dragons are set tamed and receive the egg owner's UUID;
- `EntityDragonEgg` stores its owner UUID;
- egg hatching creates the owned `EntityDragonBase` and transfers ownership;
- the native broad `iceandfire:iceandfire/kill_if_dragon` advancement proves a dragon kill but not stage, sex, or wild status;
- `iceandfire:iceandfire/dragon_egg` proves egg possession but not the approved Stage 4/5 female acquisition context.

Therefore the approved adult-wild kill, sufficiently ancient female egg acquisition, and final owned Stage 3+ ride require narrow source-aware predicates or bridges rather than weakening the authored objectives to the broad native advancements.

### 5.1 Dragon Forge

`TileEntityDragonforgeInput.onHitWithFlame()` and the connected Dragon Forge tile's flame-transfer state provide source-owned evidence that actual dragon breath reached an assembled forge.

The core tile exposes operational fields and methods including:

```text
lastDragonFlameTimer
assembled()
transferPower(int)
```

This makes the approved Q049 requirement technically detectable without using dragonsteel possession as a proxy if a narrow compatibility detector is preferred.

## 6. Myrmex native state

The pinned Ice & Fire source verifies the exact canonical-hive native state surfaces:

```text
MyrmexHive.getPlayerReputation(UUID)
MyrmexHive.modifyPlayerReputation(UUID, int)
MyrmexHive.isPlayerReputationTooLowToTrade(UUID)
MyrmexHive.canPlayerCommandHive(UUID)
MyrmexHive.getQueen()
MyrmexHive.getMyrmexMembers()
MyrmexHive.getVillageCenter()
```

Thresholds are source-defined:

```text
below 25 -> Myrmex may fight player
50+      -> native trade permitted
75+      -> player may command hive
```

`ItemMyrmexStaff` stores the hive UUID and resolves that hive through Myrmex world data.

### 6.1 Verified positive opinion actions

At least two technically distinct native positive-opinion paths exist:

1. successful native Myrmex trading, which raises hive opinion by +1;
2. throwing the matching Myrmex resin to a Worker so that it picks up an item whose thrower is the player, which raises the Worker's hive opinion by +5.

This confirms that Q093 can be implemented as a curated mix of distinct native positive behaviors rather than repetition of one action.

The authored selection and exact required counts remain campaign decisions if more than one source-valid mix is possible.

### 6.2 Hive-function limitation

`MyrmexHive.isAnnihilated()` in this exact artifact returns `false` unconditionally and is not a usable native collapse signal.

The hive explicitly tracks:

- FOOD rooms;
- NURSERY rooms;
- EMPTY/misc rooms;
- entrances;
- reproduction state;
- Queen and member population.

The Myrmex Staff GUI can manipulate room registrations and reproduction state once command authority is available.

The source therefore exposes multiple potentially meaningful hive functions. Source inspection alone does not uniquely select which one Q092 should require after the Queen kill. That selection remains authored and should return to the Overlord rather than being silently chosen.

## 7. Fathoms native milestones

The supplied Fathoms build contains 66 non-recipe advancement definitions in its main Nautical advancement surface.

Important exact source milestones include:

```text
fathoms:nautical/open_message_in_a_bottle
fathoms:nautical/obtain_all_sunken_scrawls
fathoms:nautical/enter_rocky_waters
fathoms:nautical/enter_ancient_reservoir
fathoms:nautical/dredging_apparatus
fathoms:nautical/open_coffer
fathoms:nautical/perform_ritual
fathoms:nautical/perform_enhanced_ritual
fathoms:nautical/all_max_level_rituals
fathoms:nautical/make_a_bad_decision
fathoms:nautical/apply_jinx
fathoms:nautical/thaw_wishing_well
```

The exact Rocky Waters structure IDs are:

```text
fathoms:rocky_waters/small
fathoms:rocky_waters/medium
fathoms:rocky_waters/large
```

The artifact also defines `fathoms:oasis`, `fathoms:wishing_well`, and the Ancient Reservoir progression surface.

Many Fathoms advancements use a custom `impossible` criterion and are awarded by native code at the actual event. They are still valid durable native milestones for Questlog observation where an authored decision allocates them.

Q046 authorizes unused Fathoms advancements or milestones to become Historian Ramblings, subject to the Batch 01 Rambling eligibility policy. Exact milestone-to-Rambling allocation remains authored.

## 8. Nycto native milestones

The supplied Nycto artifact contains no ordinary static data advancement definitions suitable for the NightWalker campaign. Its meaningful progression is primarily code-owned state.

Verified source-owned state includes:

```text
VampireData.isVampire(Player)
VampireData.isTransforming(Player)
VampireData.transformationTicks(Player)
VampireData.blood(Player)
VampireData.powers(Player)
VampireData.weaknesses(Player)
VampireData.hasPower(Player, VampirePower)
VampireData.hasWeakness(Player, VampireWeakness)
VampireData.addAltarPurchase(...)
VampireData.cure(ServerPlayer)
```

The exact power roster in this build is:

```text
BAT_FORM
BAT_SWARM
BATSTEP
BLOOD_BARRIER
BLOOD_FLECHETTES
BLOODRUSH
CARNAGE
DARK_FORM
HAEMOGENESIS
HYPNOTIZE
KEEN_SENSES
MIST_FORM
VAMPIRIC_THRALL
NIGHT_VISION
```

The exact weakness roster is:

```text
HUMANITY
HYDROPHOBIA
PYROPHOBIA
RICH_TASTES
THIN_BLOOD
VILE_PRESENCE
```

The Vampire Altar menu exposes purchase/upgrade state and the current power/weakness configuration.

The supplied Questlog build already has `NightwalkerVampireObjective` and `NightwalkerPowerCountObjective` bridges.

Q045 therefore remains technically feasible even without advancement JSON. Additional Lestat Ramblings should be based on approved durable Nycto state transitions or native events, not on nonexistent static advancement IDs.

## 9. Quest-critical NPC protection and Q104 ghosts

The supplied Questlog artifact already defines the protection tag:

```text
overlord_quest_protected
```

`QuestAnchorProtection.isProtected(Entity)` recognizes that tag.

The Forge event forwarder cancels `LivingAttackEvent` when the target is protected, and the protection utility also makes protected mobs persistent.

Therefore the existing framework provides a real invulnerability/persistence primitive for bound critical providers.

The active modpack contains the entity:

```text
ghosts:ghost
```

The Ghosts entity is a spawnable mob with persistent entity state. A later implementation bridge can therefore spawn a civilization aftermath ghost, mark it with the same protection system, bind the originating civilization/provider identity, and route interaction through a Villager-Retaliation-style dialogue/provider surface.

The exact dialogue is deferred with other final dialogue writing. The authored consequence itself is already fixed by Q104.

## 10. Technical conclusions closed without new campaign decisions

The following Batch 01 requirements are technically expressible without reopening campaign intent:

- Q005 Tower Waystone placement-only tracking;
- Q009 to Q011 one quest per restored tribe and owner-API unlock synchronization;
- Bosses'Rise lair visit and boss-kill detection;
- Ice & Fire dragon stage, sex, ownership, hatching and riding predicates through narrow source-aware bridges;
- actual Dragon Forge dragon-breath detection;
- Myrmex 50/75 opinion thresholds, trade access and Staff command state;
- Fathoms durable milestone observation;
- Nycto vampire/power/weakness observation;
- provider invulnerability/persistence;
- spawning and permanently protecting Q104 aftermath ghosts.

## 11. Genuine authored questions exposed by source research

The following are not technical facts and must not be selected autonomously:

- the exact evidence objective for each Bosses'Rise investigation stage where the source provides a lair and kill but no V5 Cataclysm-proof advancement;
- the exact Myrmex positive-action mix/count used by Q093 if more than one valid combination remains practical;
- which hive function Q092 requires after killing the canonical Queen;
- exact Fathoms milestone-to-Historian-Rambling allocation;
- exact Nycto milestone-to-Lestat-Rambling allocation;
- any exact representative capstone still left open by an approved broad quest structure.

These should be accumulated into the next V5 decision workbook after enough source work is complete.

## 12. Production boundary

No production quest definitions, provider implementation, compatibility code, Minion code, Nycto code, Fathoms code, Ice & Fire code, Ghosts code, or current campaign files are changed by this technical closure record.