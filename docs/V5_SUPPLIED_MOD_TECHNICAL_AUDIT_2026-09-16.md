# OVERLORD REIGN V5 Supplied Mod Technical Audit

Status: VERIFIED TECHNICAL FACT / V5 SUPPORTING AUDIT

Date: 2026-09-16

Purpose: record source-owned signals and implementation boundaries from the missing artifacts supplied during V5 authoring. This file does not create new lore or silently decide unresolved campaign allocation.

## 1. Exact supplied artifacts

| Artifact | SHA-256 | Role in current V5 audit |
| --- | --- | --- |
| `overlord_minions-0.1.0-dev.jar` | `5db98127d176ad12d63a9ecf0572bcb6fc6dddfc1ca7d1e45789a1ad18914e56` | authoritative Minion progression API |
| `block_factorys_bosses-2.1.2-forge-1.20.1.jar` | `8c61087aa483f90861451682519582e03fa76e773d855e35180a8ed3428938bc` | Bosses'Rise central checkpoints |
| `iceandfire-2.1.13-1.20.1-beta-5.jar` | `2b80245fc9b7d6fdc61d71f9892f4c6114eb7f303f65634845aaab25f84d1e82` | dragons, Myrmex, Dragon Forge |
| `ice_and_fire_delight-forge-1.20.1-0.2.5.jar` | `29a13d459b907a57ecaebeffef6b1b1fa8a0386e30e8420222c5699d3ec809f8` | Ice & Fire / Farmer's Delight crossover content |
| `ice_and_fire_spellbooks-2.3.2-1.20.1.jar` | `c15f6234274407976c791f2d1b3776d0aacc5138341aaf81367bd9e47a7f13a5` | Ice & Fire / Iron's Spells crossover content |
| `overlord-depths-1.0.5-overlord.0.2.jar` | `9992949e47e97097be36c8d7f2835b3df15d3e1568af0ce087f3695ed524e4e1` | Fathoms native progression evidence |
| `nycto-forge-1.20.1-overlordreign-1.0.0-alpha.4.jar` | `975aafff0bf35714229f173c27cc78fd17f3d1ed36dae500cbd9b963a5a206e9` | current NightWalker runtime state surface |

## 2. Overlord Minions

The exact supplied Minion JAR exposes a source-owned progression API through `OverlordMinionProgression`.

Relevant public operations include:

```text
isUnlocked(MinecraftServer, MinionSlot)
highestUnlocked(MinecraftServer)
unlock(MinecraftServer, MinionSlot)
```

`MinionSlot` contains the exact traditional sequence:

```text
BROWN
RED
GREEN
BLUE
```

The persistent owner state is stored through the Minion implementation rather than Questlog.

V5 implementation consequence:

- Questlog should track the authored restoration feat;
- completion should invoke the Minion owner's unlock operation;
- later checks should query Minion owner state instead of duplicating tribe ownership in a second persistent quest flag.

Classification: STRONG OWNER API AVAILABLE.

## 3. Bosses'Rise 2.1.2

The exact JAR provides direct `player_killed_entity` advancements for all five central V5 bosses.

| V5 identity | Entity ID | Native kill advancement |
| --- | --- | --- |
| Ashlord | `block_factorys_bosses:infernal_dragon` | `block_factorys_bosses:kill_dragon` |
| Nerakyss | `block_factorys_bosses:kraken` | `block_factorys_bosses:kill_kraken` |
| Sirok | `block_factorys_bosses:sandworm` | `block_factorys_bosses:kill_sandworm` |
| Helvar | `block_factorys_bosses:underworld_knight` | `block_factorys_bosses:kill_underworld_knight` |
| Skor | `block_factorys_bosses:yeti` | `block_factorys_bosses:kill_yeti` |

The JAR also provides the corresponding structure IDs:

```text
block_factorys_bosses:dragon_tower
block_factorys_bosses:kraken_ship
block_factorys_bosses:sandworm_nest
block_factorys_bosses:underworld_arena
block_factorys_bosses:yeti_hideout
```

`block_factorys_bosses:kill_all_bosses` also exists and requires all five kills, but V5 should retain five separate subcampaign completions and use the aggregate only if a later technical gate benefits from it. It should not replace the authored five-checkpoint structure.

Several challenge advancements use `minecraft:impossible`, including the under-one-minute challenge. Those are not needed for central progression.

Classification: DIRECT DURABLE SIGNALS AVAILABLE.

## 4. Ice & Fire 2.1.13 beta 5

### 4.1 Dragon research and kills

Useful packaged advancements include:

```text
iceandfire:iceandfire/bestiary
iceandfire:iceandfire/kill_if_dragon
iceandfire:iceandfire/dragon_egg
```

`bestiary` is possession based.

`kill_if_dragon` is a real `player_killed_entity` advancement accepting Fire, Ice, or Lightning Dragon. It does not distinguish dragon stage or adulthood.

`dragon_egg` is possession based and accepts the packaged dragon egg variants. It does not prove the sex, age, or exact defeated parent of the dragon that produced the egg.

Therefore V5's more specific adult-dragon and ancient-female requirements require either a narrow source-aware bridge or an authored encounter whose source state proves the intended condition. They must not silently be weakened to the broad native advancements.

### 4.2 Dragon age and riding

The source exposes dragon age/stage state directly through `EntityDragonBase`, including dragon stage and age in days.

The Bestiary describes stage 4 as mature adult and stage 5 as the rare stronger elder stage. Stage 4 and later are the source-backed adult range relevant to breeding/egg context.

Tamed dragons become mountable once they are over stage 2. This makes stage 3 the native minimum rideable stage.

The exact beta 5 dragon inventory provides a banner slot and four dragon-armor slots. No dragon saddle slot or saddle requirement was found in the supplied source surface. The Bestiary describes mounting by sneaking and interacting with a tamed dragon over stage 2, with no saddle step.

V5 consequence: the previously approved phrase `saddle and ride it` conflicts with the exact installed build and requires an explicit V5 correction before the final dragon-mastery objective is authored.

### 4.3 Dragon Forge

The native Bestiary describes Dragon Forge operation through dragon breath from a tame or chained wild dragon. Dragonsteel production therefore has a real dependence on containing/accessing a dragon.

This supports the existing V5 decision to place Dragon Forge development in the Tower Dragon Den rather than inside the four-part general dragon-mastery line.

### 4.4 Myrmex

The exact native Myrmex reputation and hive-binding audit is recorded in `docs/CIVILIZATION_NATIVE_PROGRESSION_AUDIT.md`.

Key technical facts:

```text
0 to 24 reputation: hostile
25+ reputation: non-hostile
50+ reputation: native trade access
75+ reputation: Myrmex Staff command access
100 reputation: used by the player-founded queen-colony path
```

Existing-hive command at 75 is technically distinct from founding/owning a new colony at 100.

V5 political mapping remains unresolved pending explicit Overlord decision.

## 5. Ice and Fire Delight 0.2.5

This add-on contains 15 packaged advancements and a broad compatibility cuisine surface built from Ice & Fire materials.

Examples include:

- Dragon Omelet related progression;
- dragon minced meat;
- dragon-scale/spicy food;
- special cocktails and food effects;
- Myrmex resin foods;
- creature-derived cuisine across several Ice & Fire mobs.

This is substantial enough to be meaningful content, but its technical existence does not establish a standalone V5 questline.

Current V5 allocation: UNKNOWN pending explicit content-allocation decision.

Low-complexity proposal for later approval: absorb selected meaningful recipes/effects into existing Farming/Gluttony progression or sparse contextual Ramblings rather than create a separate add-on questline.

## 6. Ice and Fire: Spellbooks 2.3.2

The exact add-on contains no packaged advancements.

Its content includes crossover equipment and recipes such as:

```text
Dragonmancer's Oathbook
Dragon Priest Staff
Infernal Dragon Priest Staff
Glacial Dragon Priest Staff
Thunderous Dragon Priest Staff
Fire / Ice / Lightning Dragon Priest armor sets
```

The add-on also injects loot into Fire Dragon, Ice Dragon, Lightning Dragon, Ancient City, and End City loot sources. Several staff recipes are explicitly Dragon Forge recipes.

Technical consequence: there is no native advancement ladder that V5 needs to mirror. The content naturally crosses the existing Iron's Spell Study and Dragon Forge / Dragon Den systems.

Current V5 allocation: UNKNOWN pending explicit content-allocation decision.

Low-complexity proposal for later approval: no standalone questline; use selected crossover content inside the existing magic or Dragon Den progression where it adds a meaningful accomplishment, leaving the rest native or suitable for Ramblings.

## 7. Fathoms: Overlord Depths 1.0.5 overlord.0.2

The exact supplied artifact packages 67 advancements.

Several are useful exact native signals, including:

```text
fathoms:nautical/enter_ancient_reservoir
fathoms:nautical/enter_rocky_waters
fathoms:nautical/catch_aberration
fathoms:nautical/activate_conduit
```

`enter_ancient_reservoir` directly checks biome `fathoms:ancient_reservoir` and grounded/non-swimming location state.

`enter_rocky_waters` directly checks biome `fathoms:rocky_waters`.

`catch_aberration` uses the fishing hook trigger over the mod's aberration item set.

Other meaningful progression entries intentionally use `minecraft:impossible`, indicating that Fathoms code awards them when its own action occurs. Examples include ritual execution, apparatus use, coffer/message actions, fish processing, kelpie creation, and several other source-owned events.

V5 consequence: the existing dedicated Fathoms investigation allocation has abundant native progression signals and does not need to be reduced to inventory-possession proxies. Exact objectives should be selected only after the authored investigation sequence is fixed.

Classification: STRONG NATIVE PROGRESSION SURFACE AVAILABLE.

## 8. Nycto alpha.4

The exact JAR contains no packaged advancement layer for vampire progression.

Instead, `VampireData` owns persistent player state directly. Public source surface includes:

```text
isVampire(Player)
setVampire(Player, boolean)
isTransforming(Player)
transformationTicks(Player)
startTransformation(Player)
blood(Player)
addBlood(Player, int)
consumeBlood(Player, int)
powers(Player)
weaknesses(Player)
hasPower(Player, VampirePower)
hasWeakness(Player, VampireWeakness)
configure(Player, int, int)
addAltarPurchase(Player, VampirePower, VampireWeakness)
removePowerOrCure(ServerPlayer)
cure(ServerPlayer)
```

The exact alpha.4 power enum includes:

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

The weakness enum includes:

```text
HUMANITY
HYDROPHOBIA
PYROPHOBIA
RICH_TASTES
THIN_BLOOD
VILE_PRESENCE
```

V5 can therefore use a narrow direct integration against source-owned state rather than inventing Questlog copies of vampirism, power ownership, weakness ownership, blood, or cure state.

The supplied alpha.4 JAR and the live `release/nightwalker-alpha4-2026-09-14` source tree contain no Lestat presenter asset or Lestat runtime implementation. No Lestat-named portrait/reaction asset was found in that exact source tree.

V5 consequence: Lestat remains a Quest-side noncorporeal presenter decision. His five reaction-state presentation cannot be inferred from Nycto itself and must be sourced from existing quest/canon material or returned to the Overlord if exact labels/assets remain undefined when that authoring section is reached.

Classification: STRONG SOURCE-OWNED PLAYER STATE AVAILABLE; PRESENTER ASSET SOURCE SEPARATE.

## 9. Integration discipline

For all supplied artifacts:

1. use exact source-owned signals when they match the authored accomplishment;
2. do not treat a broad advancement as proof of a narrower V5 accomplishment;
3. add narrow compatibility bridges only where the authoring requires a fact the source does not durably expose;
4. preserve native owner state instead of duplicating it in Questlog;
5. do not create a questline merely because an add-on contains many items or advancements;
6. return unresolved lore, progression intent, terminal-state mapping, or content-allocation decisions to the Overlord before promoting them into V5 authority.
