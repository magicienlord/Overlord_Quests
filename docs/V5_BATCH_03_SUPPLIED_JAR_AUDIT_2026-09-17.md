# OVERLORD REIGN V5 Batch 03 Supplied JAR Audit

Status: VERIFIED TECHNICAL FACT / AUTHORING SUPPORT

Date: 2026-09-17

Purpose: record technical facts established directly from the exact JARs supplied by the Overlord after Decision Batch 03. These facts constrain implementation and later proposals. They do not make new authored campaign choices.

## 1. Exact inspected artifacts

```text
5db98127d176ad12d63a9ecf0572bcb6fc6dddfc1ca7d1e45789a1ad18914e56  overlord_minions-0.1.0-dev.jar
4a95d74896f02ca2107ff0b48a14453a72bb13a5128ce8973eeda8a571b79915  Overlord_Quests-1.20.1-e0a0c866-statfix.jar
8c61087aa483f90861451682519582e03fa76e773d855e35180a8ed3428938bc  block_factorys_bosses-2.1.2-forge-1.20.1.jar
2b80245fc9b7d6fdc61d71f9892f4c6114eb7f303f65634845aaab25f84d1e82  iceandfire-2.1.13-1.20.1-beta-5.jar
29a13d459b907a57ecaebeffef6b1b1fa8a0386e30e8420222c5699d3ec809f8  ice_and_fire_delight-forge-1.20.1-0.2.5.jar
c15f6234274407976c791f2d1b3776d0aacc5138341aaf81367bd9e47a7f13a5  ice_and_fire_spellbooks-2.3.2-1.20.1.jar
9992949e47e97097be36c8d7f2835b3df15d3e1568af0ce087f3695ed524e4e1  overlord-depths-1.0.5-overlord.0.2.jar
975aafff0bf35714229f173c27cc78fd17f3d1ed36dae500cbd9b963a5a206e9  nycto-forge-1.20.1-overlordreign-1.0.0-alpha.4.jar
```

L_Ender's Cataclysm 3.31 remains separately pinned by the prior exact audit.

## 2. Questlog reward system

Exact supplied Questlog reward classes include:

```text
questlog:item
questlog:experience
questlog:loot_table
questlog:command
questlog:choice
```

Verified behavior:

- every base Reward reads an optional boolean `auto_claim`;
- `auto_claim` defaults to `false` when omitted;
- reward state records whether the reward has already been applied;
- the framework exposes an explicit `QuestRewardCollectPacket` path for collecting rewards after a quest is complete;
- the packet rejects early collection from incomplete quests and duplicate collection of an already rewarded entry;
- choice rewards use `pick_count` plus explicit selected indices;
- choice rewards explicitly reject `auto_claim` because player selection is required;
- nested choice rewards are not supported by the current claim protocol.

Authoring consequence:

Q158 cannot be answered by assuming that the framework lacks reward collection. It supports both automatic and player-initiated reward delivery, with choice rewards necessarily player-selected. The V5 policy governing which mode to use remains authored and requires a replacement Overlord decision.

## 3. Bosses'Rise 2.1.2 investigation surfaces

### 3.1 Skor

The Yeti source exposes explicit combat states including:

```text
THROW_SPIKE
ICE_BARRAGE
ENRAGED
```

It also carries synchronized source state for ultimate/enraged behavior and emits the native ice attack effects.

Technical interpretation:

Q143 can be implemented against a narrow native frost / icicle attack state or event. The implementation should map the approved player-facing wording to the actual source state rather than fabricate a separate Phase-2 evidence object.

### 3.2 Sirok

The Sandworm source tracks damaged armor segments.

Verified sequence:

```text
accumulated damage reaches segment break threshold
-> source marks that body segment damaged
-> damaged segment receives increased damage multiplier
-> a later valid hit on an already damaged segment triggers the source poison-spit response
```

Technical interpretation:

Q144 maps directly to source behavior. A detector may observe the damaged-segment transition plus the subsequent poisonous response without creating custom residue or rift content.

### 3.3 Ashlord

Native registry content includes:

```text
block_factorys_bosses:dragon_banner
```

The Dragon Tower is a source-owned structure/domain.

Technical interpretation:

Q145 can use native Dragon Banner possession or acquisition in the bound Dragon Tower context.

### 3.4 Helvar

Native registry content includes:

```text
block_factorys_bosses:underworld_arena_key
```

The huge-door implementation checks the held item against its valid-key predicate, opens the source door, and consumes one key outside creative mode.

Technical interpretation:

Q146 can prove both obtaining and actually using the native Underworld Arena Key rather than relying on inventory possession alone.

### 3.5 Nerakyss

The Kraken spawner defines exactly three source pirate guard types:

```text
Crossbow Pirate
Pirate Rook
Pirate Captain
```

The spawner records the pirates by UUID. Once pirates have been spawned, Kraken spawning is gated until the tracked pirate list is empty. Only then does the source create the Kraken and remove the spawner block.

Technical interpretation:

Q147 is a true source-owned encounter gate. Clearing the three tracked pirate variants before Nerakyss appears is directly verifiable.

## 4. Bosses'Rise advancement surface relevant to Ramblings

The exact JAR includes direct boss-kill advancements for all five central Bosses'Rise bosses, an all-boss completion advancement, the death-to-boss advancement, a sub-minute boss challenge, and five individual no-hit boss challenge advancements.

Notable challenge surface includes:

```text
Dragon Hunter
Wraith of the Tide
Desert Power
Beyond Death
King of the Hill
Unbound
Overclocker
```

Under `V5_RAMBLING_SYSTEM_AUTHORITY.md`, these must be considered during the complete relevance audit even when a boss-kill advancement also completes a visible quest. Quest overlap is not a disqualifier.

## 5. Overlord Minions owner progression

The exact Minion JAR exposes:

```text
com.overlordreign.minions.api.OverlordMinionProgression.isUnlocked(...)
com.overlordreign.minions.api.OverlordMinionProgression.highestUnlocked(...)
com.overlordreign.minions.api.OverlordMinionProgression.unlock(...)
```

`MinionUnlockState` is persistent world saved data.

Verified semantics:

- Brown is the bootstrap slot and is treated as owned by the Staff rather than unlocked through the later progression call;
- Red, Green, and Blue are ordered slots;
- `unlock(...)` rejects out-of-order progression;
- successful unlock advances the authoritative saved owner state.

Authoring consequence:

The existing V5 Brown -> Red -> Green -> Blue sequence maps directly to source-owned progression. Questlog should invoke/read this state rather than duplicate Minion ownership as an ordinary narrative fact.

## 6. Ice and Fire 2.1.13 beta 5 Myrmex surface

Exact-source inspection remains consistent with the focused Myrmex authority:

- gifting resin to a worker raises hive reputation by 5;
- a completed native trade raises reputation by 1;
- native trading access begins at 50 reputation;
- Staff command access begins at 75 reputation;
- Staff room designation includes FOOD and NURSERY room types;
- the source maintains hive identity and opinion as native state.

Authoring consequence:

Q140 and Q141 now have direct exact-source support. No new authored Myrmex decision is needed merely to select another relationship or Staff-command proof.

## 7. Ice and Fire advancement surface

The exact Ice and Fire JAR contains a broad native accomplishment surface including:

- dragon egg acquisition;
- dragon meal, horn, staff, flute and armor progression;
- dragonbone tools and weapons;
- Dragon Forge / Dragonsteel progression;
- major creature kills including Dragon, Myrmex, Cyclops, Hydra, Sea Serpent, Siren and others;
- tame Amphithere, Cockatrice, Hippocampus, Hippogryph and Pixie milestones;
- lectern / bestiary-related progression.

These events must be evaluated under the global Rambling rule, including events already used by Dragon Mastery or other visible quests.

## 8. Ice and Fire Delight 0.2.5 advancement surface

The addon exposes fifteen packaged advancements, including food, dragon-themed cuisine, challenge, and completion-style milestones.

Examples include:

```text
Almost 4 elements
Cafeteria pizza
Delicacy!
Feel like a dragon!
Feel spicy
The Power of Three Dragons
Rest in peace Dragon
Too Much Power
You've been spotted
```

One hidden first-join/book-grant style milestone is implementation plumbing and does not qualify merely because an advancement exists.

Because Gristle owns food-related Ramblings, relevant culinary milestones belong to the Gristle audit unless another approved presenter theme clearly takes precedence.

## 9. Ice and Fire Spellbooks 2.3.2

The exact addon JAR contains no packaged native advancement surface.

Its content integrates Ice and Fire with Iron's Spells and Spellbooks. Any V5 use should therefore rely on the actual spell/item/system interaction it contributes rather than inventing an advancement catalog that does not exist.

## 10. Fathoms / Overlord Depths 1.0.5 advancement surface

The exact JAR exposes a substantial native advancement catalog, including the already-approved five-quest investigation signals and many additional milestones.

Relevant examples include:

```text
catch_aberration
open_message_in_a_bottle
obtain_all_sunken_scrawls
enter_rocky_waters
perform_ritual
perform_enhanced_ritual
enter_ancient_reservoir
open_coffer
dredging_apparatus
thaw_wishing_well
create_kelpie
catch_all_fish
all_max_level_rituals
```

`catch_all_fish` is a large explicit completion challenge, making it a direct example of the completion-Rambling rule clarified by the Overlord.

Authoring consequence:

Historian Rambling allocation must inspect the full relevant Fathoms milestone surface. Quest use does not exclude a milestone, and major completion achievements remain eligible even when the final advancement is narratively thin.

## 11. Nycto alpha.4 state surface

The exact Nycto JAR contains no packaged native advancements.

It stores authoritative player vampirism state in persistent `VampireData`, including:

```text
vampire state
transformation ticks
blood
power mask / owned powers
selected power
weakness mask
altar purchase/order state
```

Public source methods include direct checks for vampire state, blood amount, owned powers and related progression.

Authoring consequence:

NightWalker quest and Lestat Rambling implementation can read source-owned state and first-acquisition transitions directly. No fabricated advancement layer is required merely to make the content trackable.

## 12. Rambling audit rule resulting from the supplied artifacts

The supplied JARs reinforce the global authority already recorded in `docs/V5_RAMBLING_SYSTEM_AUTHORITY.md`.

When preparing final V5 Rambling allocation:

```text
inspect complete relevant native advancement / milestone surface
-> keep quest-used and non-quest-used events in scope
-> include meaningful character/lore milestones
-> include significant completion milestones
-> exclude trivial recurrence and technical plumbing
-> assign specialist presenter when the theme is owned
-> otherwise assign Gnarl
```

No source audit may pre-filter out an advancement merely because Questlog also uses it.

## 13. Production boundary

This audit records technical evidence only. It does not authorize runtime detector implementation, reward wiring, production quest changes, or exact Rambling catalogs that remain authored choices.