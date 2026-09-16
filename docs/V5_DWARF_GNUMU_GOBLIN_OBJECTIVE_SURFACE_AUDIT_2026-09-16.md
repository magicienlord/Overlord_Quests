# OVERLORD REIGN V5 Dwarf, Gnumu and Goblin Objective Surface Audit

Status: VERIFIED TECHNICAL FACT / V5 SUPPORTING AUDIT

Date: 2026-09-16

Purpose: identify exact source-owned objective signals for the approved Dwarf, Gnumu and Goblin political route allocations. This file does not invent route meaning. Political meaning remains governed by the V5 civilization blueprints and explicit Overlord decisions.

## 1. Dwarven Forge 1.0.0

### 1.1 Native recurring roles

The exact installed language surface exposes:

```text
none -> Dwarf
toolsmith -> Dwarven Forger
butcher -> Dwarven Butcher
leatherworker -> Dwarven Leatherworker
farmer -> Dwarven Farmer
mason -> Dwarven Miner
warrior -> Dwarven Warrior
```

This supports the approved Forge-Thane, second Forger and Warrior roles without adding new entity classes.

The mod does not expose a native Archivist, Historian or Record Keeper profession. The approved historical-record keeper therefore remains an authored local role layered onto one selected Dwarf. V5 must not claim such a profession exists species-wide.

### 1.2 Settlement and material surfaces

The exact mod provides:

```text
dwarven_forge:dwarven_village
```

with generated forge, mine, barracks and ordinary settlement components.

Relevant items include:

```text
dwarven_forge:dwarven_metal_ingot
dwarven_forge:dwarven_sword
dwarven_forge:dwarven_pickaxe
dwarven_forge:rune_*
```

The forge chest loot can contain Dwarven Metal, lesser runes, gold, iron tools/material and obsidian. The mine chest can contain amethyst, raw metals, diamond and stone material.

### 1.3 Craft and rune signals

The mod provides normal crafting recipes for Dwarven tools and armor and a dedicated `dwarven_forge:rune_smithing` recipe family for lesser, normal and greater Strength, Speed, Armor, Toughness and Health runes.

The packaged advancements are recipe unlocks, not accomplishment triggers.

Therefore V5 should track meaningful Dwarven craft/rune actions through Questlog craft statistics, item state, provider turn-in or another exact action rather than pretending recipe-unlock advancement completion proves mastery.

### 1.4 Native economy

The installed Dwarf implementation uses an amethyst-centered native trade system and retains inherited Villager reputation pricing.

Trade tables expose Dwarven Metal and multiple rune tiers through the native economy. An authored political consequence may alter or preserve pricing through the source-owned reputation path, but native reputation never selects disposition by itself.

### 1.5 Historical-evidence limitation

The exact Dwarven Forge JAR contains no source-native historical-record item, archive advancement or Golden Hills relic mechanic.

Consequently the approved historical-record keeper chain cannot be implemented honestly by claiming that a native mod item is an archival record.

A later V5 objective design may use:

- provider dialogue / explicit Questlog read state;
- material proof from the surviving Dwarven forge/mine tradition;
- a deliberately authored record object only if V5 explicitly chooses to add one.

The third option would be new authored content and must not be introduced silently.

## 2. Gnumus 1.0

### 2.1 Exact persistent provider

The JAR registers:

```text
gnumus:gnumus_hunter_not_dispawn
```

with installed display name `Gnumus Warrior`.

This is the preferred source-native recurring defense/hunter provider when V5 needs a persistent martial Gnumu character.

### 2.2 Vintage antiquity surface

Exact source items include:

```text
gnumus:vintage_metal
gnumus:vintage_parts
gnumus:vintage_alloy_ingot
gnumus:vintage_gnumus_pickaxe
gnumus:vintage_gnumus_cutlass
gnumus:hammer_vintage_universal_tool
gnumus:vintage_rifle
gnumus:gnumus_totem
```

The native advancement:

```text
gnumus:antiquity_shard
```

has description `Get Vintage Metal` and uses a real `minecraft:inventory_changed` criterion for at least one `gnumus:vintage_metal`.

This is an excellent durable first signal for the approved ancestral-technology chain.

### 2.3 Reconstruction signals

The mod owns the following source-awarded progression:

```text
gnumus:vintage_blacksmith    -> make Vintage Alloy Ingot
gnumus:vintage_improvement   -> upgrade Gnumus Pickaxe or Cutlass to Vintage
gnumus:vintage_technology    -> make Vintage Universal Tool
```

The JSON criteria for these are `minecraft:impossible`; bytecode verifies that the relevant mod procedures award the advancements when the source-owned craft/upgrade action occurs.

These are therefore usable durable source signals even though vanilla does not grant them directly.

### 2.4 Totem link

The exact recipe for `gnumus:gnumus_totem` is shapeless:

```text
1 x gnumus:vintage_metal
1 x gnumus:gnumus_horn
-> gnumus:gnumus_totem
```

The native `gnumus:weight_loss_protection` advancement is an inventory trigger for obtaining the Totem.

This establishes a real material link between the mod's Vintage antiquity surface and a surviving Gnumu magical/cultural object.

It does not by itself prove Halfling ancestry or Gluttony transformation. That interpretation remains REIGN canon and must be supplied by the approved Mellow Hills / Gnarl historical layer.

### 2.5 Other useful exact signals

```text
gnumus:business_approach     -> successful Merchant trade using Gnumus Doubloons
gnumus:plan_boar             -> kill Big Snout
gnumus:hearty_dish           -> make Hearty Dinner
gnumus:worthy_traders_hat    -> obtain Business Hat
```

Several are source-awarded through mod procedures and may be observed retrospectively through Questlog's advancement objective.

V5 must select only those that serve an approved provider or political purpose.

## 3. Goblins Tyranny 1.2.3

### 3.1 NEUTRAL native signals

The exact mod provides:

```text
goblins_tyranny:merchant_success
```

Description:

```text
Purchase something from the merchant
```

and:

```text
goblins_tyranny:liquor_success
```

Description:

```text
Purchase some liquor from the bartender
```

Both are source-awarded by the native interaction/purchase procedures. They are strong low-overhead signals for the approved Merchant + Bartender independent-coexistence route.

### 3.2 SUBJUGATED Engineer signals

The exact mod provides:

```text
goblins_tyranny:engineer_success
```

Description:

```text
Use the engineer workbench
```

plus source-owned item progression:

```text
goblins_tyranny:prototype_success
-> obtain Goblin's Prototype chestplate

goblins_tyranny:upgrade_success
-> upgrade the Goblin's Prototype
```

The prototype/upgrade advancements use real inventory criteria and can be read retrospectively.

Bomb access also has exact inventory advancement signals for normal, fire and gas/poison bombs.

### 3.3 SUBJUGATED Blacksmith signal

The exact mod registers:

```text
goblins_tyranny:blacksmith_goblin
```

and contains a native Blacksmith GUI/service.

The Blacksmith interaction is normally gated by the source-owned Goblin disguise effect. The installed guide/JEI text states that Goblin Ingot can be forged through the Goblin Blacksmith from:

```text
Gold Ingot
Netherite Scrap
Engineer Scrap
```

This is a strong native link between Overlord-supplied high-value resources, Engineer material and the approved Blacksmith production pillar.

No fabricated Blacksmith profession is required.

### 3.4 Additional Merchant patronage signal

The native advancement:

```text
goblins_tyranny:mushroom_success
```

has installed description:

```text
Give a mushroom stew to the merchant
```

This may be useful for characterization but is not automatically strong enough to represent terminal political dependency by itself.

### 3.5 DESTROYED martial surface

The exact source registers:

```text
goblins_tyranny:champion_goblin
goblins_tyranny:leader_goblin
```

and exposes multiple bomb types plus the source advancement:

```text
goblins_tyranny:goblins_slayer_success
```

with description:

```text
We do not come in peace
```

The advancement is source-awarded through a mod procedure rather than a self-describing vanilla criterion, so it must not be assigned an exact political meaning without tracing the awarding condition or using more direct Questlog kill/action objectives.

The approved destructive route therefore still needs an authored sequence that targets the canonical camp's leadership and functional continuity rather than a global Goblin kill count.

## 4. Current authoring boundary

Technical source truth now cleanly supports:

```text
Dwarves: Forger / Warrior / mine / forge / rune / trade actions
Gnumus: Vintage antiquity -> Totem -> reconstruction progression
Goblins: Merchant + Bartender peace signals, Engineer + Prototype + Blacksmith production signals, Champion/Leader/bomb destructive surfaces
```

Remaining choices are campaign-authoring decisions only where V5 must decide:

- the in-world presentation of the Dwarven historical-record keeper;
- whether Dwarven history remains dialogue/material proof or gains an authored physical record object;
- the exact remembered evidence sequence by which Gnarl interprets Gnumu Vintage/Totem material as Mellow Hills ancestry evidence;
- the exact dependency facts written by the Gnumu and Goblin SUBJUGATED branches;
- the exact anchor-local destructive sequences for all three civilizations.

Those choices must not be inferred merely from the existence of native items or advancements.
