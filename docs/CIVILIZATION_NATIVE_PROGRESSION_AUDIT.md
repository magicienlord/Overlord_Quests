# Civilization Native Progression Audit

Status: TECHNICAL SOURCE AUDIT

Date: 2026-09-16

## Purpose

This audit records which durable source-owned signals can safely support OVERLORD QUESTS civilization objectives for The Dwarven Forge, Ribbits, Kobolds, and Ice & Fire Myrmex.

It does not create new civilization lore, political outcomes, provider identities, or sidequests. `Overlord_Lore_and_Canon` remains read-only authority for authored campaign scope.

The project integration rule is unchanged: preserve meaningful native progression, observe durable signals where possible, and add a narrow compatibility bridge only when the owning mod exposes no sufficiently specific durable player signal.

## Exact installed artifacts

The supplied target instance and later user-supplied missing artifact set are binary authority for this audit.

| Mod | Installed file | SHA-256 |
| --- | --- | --- |
| The Dwarven Forge | `dwarven_forge-1.0.0.jar` | `f43bbe67330f7e92756b4665f6d24fd2d267aeca35cfeb4d4c9ee3cb5423d85b` |
| Ribbits | `Ribbits-1.20.1-Forge-3.0.5.jar` | `e04aa665df7e96844fe8833f29cc343feefac8ad7b949399d7b4a60475123e2b` |
| Kobolds | `Kobolds-2.12.0.jar` | `f5f5dd31ab42e3bda1a1148b91ae62c07daafb9e52905d263c0bbcfbdf9cbabe` |
| Ice & Fire | `iceandfire-2.1.13-1.20.1-beta-5.jar` | `2b80245fc9b7d6fdc61d71f9892f4c6114eb7f303f65634845aaab25f84d1e82` |

The successful source-audit workflow artifact from run `34772158429` independently reported the same first three hashes. A later Ribbits checksum value beginning `f6ff9517...` was an incorrect transcription and is not an alternate release identity.

## Advancement inventory

The exact JAR audit reports:

| Mod | Packaged advancement JSON files | Campaign interpretation |
| --- | ---: | --- |
| The Dwarven Forge 1.0.0 | 24 | recipe-unlock advancements only |
| Ribbits 3.0.5 | 6 | recipe-unlock advancements only |
| Kobolds 2.12.0 | 0 | no packaged advancements |
| Ice & Fire 2.1.13 beta 5 | 362 total, with 2 direct Myrmex-themed non-recipe advancements | Myrmex advancement signals are possession based and do not encode colony political resolution |

The existence of a recipe-unlock advancement is not proof that the player crafted, traded, served, visited, or otherwise completed a civilization activity. These files therefore must not be promoted into accomplishment objectives merely because they are durable advancements.

## The Dwarven Forge

### Source-owned progression surface

The 24 packaged advancements are recipe unlocks for Dwarven tools, armor, and rune smithing recipes. They are useful recipe-discovery state but are not direct records of completing the corresponding craft.

### Durable craft signal

The exact Dwarven Forge 1.0.0 `RuneSmithingRecipe` implements vanilla `SmithingRecipe`; it does not install a custom result menu or output slot.

Minecraft 1.20.1 `SmithingMenu.onTake` calls:

```text
itemstack.onCraftedBy(player.level(), player, itemstack.getCount())
```

and Minecraft 1.20.1 `ItemStack.onCraftedBy` increments:

```text
Stats.ITEM_CRAFTED.get(this.getItem())
```

Therefore taking a Dwarven rune-smithing result increments the persistent per-item crafted statistic for the exact output item. Ordinary crafting-table Dwarven equipment uses the same vanilla crafted-stat mechanism through the crafting result slot.

Questlog's existing `questlog:item_craft_stat` objective is therefore a valid lightweight durable integration for an exact Dwarven output item when an authored quest explicitly requires such a craft.

### Boundary

This technical capability does not itself authorize a Dwarven crafting sidequest. The exact item, provider, political meaning, reward, and branch consequence remain authored campaign decisions.

Classification: **LIGHTWEIGHT SIGNAL AVAILABLE**, content still requires authored use.

## Ribbits

### Source-owned progression surface

The six packaged advancements are recipe unlocks for mossy oak building recipes. They do not record Ribbit trade, gardening, fishing, music, Sorcerer activity, or service completion.

`RibbitEntity` implements vanilla `Merchant`. Its trade path opens the normal merchant menu and receives completed trades through `Merchant.notifyTrade`.

Minecraft 1.20.1 `MerchantResultSlot` increments the vanilla custom statistic `minecraft:traded_with_villager` after a successful merchant transaction. However, that statistic is global to merchant trades and is not keyed to the merchant entity, entity type, village, profession, or Ribbit provider.

Questlog's non-retroactive `questlog:statistic` baseline support prevents pre-quest trades from leaking into a later objective, but it cannot prevent a Villager or other merchant trade performed after quest trigger from satisfying the same global statistic. It is therefore too broad for a Ribbit-specific accomplishment objective.

### Boundary

If later authored Ribbit content needs to prove a native trade, the correct implementation is a narrow filtered bridge that records the completed trade only when the owning merchant is the intended Ribbit entity/provider. Do not substitute inventory possession, an open merchant screen, current home state, or the global trade statistic.

Classification: **NO SUFFICIENTLY SPECIFIC DURABLE SIGNAL YET**. A small bridge is justified only when an authored objective requires it.

## Kobolds

### Source-owned progression surface

The exact Kobolds 2.12.0 JAR contains no advancement JSON definitions.

Its Captain and specialist trading is implemented through Kobold AI goals rather than the vanilla Merchant menu. Exact bytecode inspection of `KoboldTradeGoal` and `KoboldEnchanterTradeGoal` shows the source goal later throwing generated trade output toward a nearby player after its native item-transfer condition succeeds. The inspected path does not award a player statistic or advancement.

No source-owned durable player accomplishment signal has yet been found that can distinguish completion of the intended Kobold trade from ordinary inventory state.

### Boundary

If an authored Kobold sidequest later requires native Captain or specialist trade completion, OVERLORD QUESTS should add the smallest source-specific completion bridge that can observe that native transaction without replacing the Kobold AI or reward logic. Do not infer completion from possession of possible loot because the same item may have another origin.

Classification: **NO DURABLE NATIVE PLAYER SIGNAL FOUND**. Bridge only when authored content actually requires it.

## Ice & Fire Myrmex

### Exact native colony state

Ice & Fire owns a persistent per-hive reputation system in `com.github.alexthe666.iceandfire.entity.util.MyrmexHive`.

The exact beta 5 JAR exposes:

```text
int getPlayerReputation(UUID)
int modifyPlayerReputation(UUID, int)
boolean isPlayerReputationTooLowToTrade(UUID)
boolean canPlayerCommandHive(UUID)
boolean isPlayerReputationLowEnoughToFight(UUID)
```

Bytecode verifies these thresholds:

```text
0 to 24  hostile
25+      non-hostile
50+      native trading allowed
75+      Myrmex Staff command allowed
```

Reputation is clamped from 0 through 100.

`MyrmexHive` has a persistent `hiveUUID`, and `MyrmexWorldData` exposes `getHiveFromUUID(UUID)`. This gives a future integration a stable source-owned way to bind the canonical Myrmex quest anchor to one exact native hive rather than applying state to every Myrmex in the world.

### Native reputation changes

The exact source behavior changes colony opinion through ordinary Ice & Fire gameplay.

Verified examples include:

- a worker accepting player-thrown resin raises the hive reputation by 5;
- a completed native Myrmex trade raises reputation by 1;
- damaging or killing hive members lowers reputation;
- a player-founded colony produced by hatching a queen egg binds ownership to that player and establishes reputation 100.

The packaged Bestiary states the same progression model and identifies resin gifting and trading as intended reputation-building mechanics.

### Native advancement surface

The two direct Myrmex-themed non-recipe advancements are:

```text
iceandfire:iceandfire/kill_myrmex
iceandfire:iceandfire/myrmex_resin
```

Despite the first advancement's name, its criterion is possession of either Desert or Jungle Myrmex Chitin, not a direct `player_killed_entity` criterion.

`myrmex_resin` is also inventory-possession based and, in the examined JSON, specifically tests `iceandfire:myrmex_desert_resin`.

Neither advancement proves a specific canonical-hive interaction, reputation threshold, political choice, Queen outcome, trade, or terminal state.

### Command authority distinction

A native reputation of 75 is sufficient to command an existing hive through a Myrmex Staff.

A reputation of 100 has a stronger native meaning in the player-founded queen-egg path because the new colony records the player as owner.

V5 should preserve that distinction. Political subjugation of a pre-existing canonical hive should not silently rewrite its origin into a player-founded colony.

### V5 boundary

Native Myrmex reputation is a useful civilization-specific access and consequence mechanism, but it is not the V5 political state machine.

The V5 civilization Questlog must remain authoritative for terminal `NEUTRAL`, `SUBJUGATED`, or `DESTROYED` resolution. Natural reputation gain must not by itself complete one of those political outcomes.

The final mapping of terminal outcomes to native reputation thresholds remains an explicit V5 design decision and is not established by this technical audit.

Classification: **STRONG SOURCE-OWNED STATE AVAILABLE**, with political mapping still requiring explicit campaign authority.

## Production decision matrix

| Civilization | First-contact provider | Native accomplishment integration | Current decision |
| --- | --- | --- | --- |
| Dwarves | already supported | exact output `ITEM_CRAFTED` statistic | lightweight objective available |
| Ribbits | already supported | recipe unlocks and global merchant stat are insufficiently specific | retain first contact; bridge only for an authored native-action sidequest |
| Kobolds | already supported | no advancement/stat signal found for native AI trade | retain first contact; bridge only for an authored native-action sidequest |
| Myrmex | V5 anchor pending final authoring | per-hive UUID and persistent native reputation are precise; packaged advancements are too broad for politics | preserve native reputation as local mechanic; map it to terminal routes only after explicit V5 decision |

## Validation requirements

Before a future production sidequest uses one of these signals:

1. the objective must be justified by current lore/campaign authority;
2. the exact installed mod identity must remain pinned;
3. the signal must identify the intended accomplishment rather than mere possession or GUI state;
4. provider anchoring must remain local to the designated civilization anchor where the lore requires it;
5. static contract validation and target-instance runtime validation must cover the new objective;
6. political disposition must remain a separate authored branch outcome rather than being inferred from a mechanical accomplishment.
