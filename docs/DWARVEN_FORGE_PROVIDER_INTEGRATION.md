# OVERLORD QUESTS Dwarven Forge Provider Integration

Status: TECHNICAL SOURCE AUDIT / PRODUCTION PROVIDER SUPPORT

Date: 2026-09-12

## Authority

The current OVERLORD REIGN lore authority establishes a Golden Hills successor hold led by a local Forge-Thane. Forge-Thane is a political title layered onto an existing Dwarven Forger rather than a new mob class or new profession. Ordinary Dwarves elsewhere must not automatically become civilization quest starters.

This document records the exact installed The Dwarven Forge 1.0.0 behavior relevant to that provider. It does not define later Dwarven political branches or disposition outcomes.

## Installed artifact authority

The supplied target instance contains:

```text
dwarven_forge-1.0.0.jar
```

SHA-256:

```text
f43bbe67330f7e92756b4665f6d24fd2d267aeca35cfeb4d4c9ee3cb5423d85b
```

Its packaged `META-INF/mods.toml` declares:

```text
modId="dwarven_forge"
version="1.0.0"
minecraft="[1.20.1,1.21)"
loaderVersion="[47,)"
```

The installed JAR is therefore the compatibility authority for this integration.

## Native Dwarf identity

Bytecode inspection of `dwarven_forge.registry.DwarvenForge_Entities` confirms one Dwarf entity registered at path:

```text
dwarven_forge:dwarf
```

`dwarven_forge.entity.DwarfEntity` extends vanilla `Villager` directly. Its profession is therefore carried by ordinary `VillagerData` and can be read through Questlog's existing native Villager-profession provider bridge without a Dwarven-specific reflection adapter.

## Native Forger profession

The exact installed English language file contains:

```text
entity.dwarven_forge.dwarf.toolsmith = Dwarven Forger
```

The installed renderer builds Dwarf profession textures from the underlying vanilla Villager profession name, and the JAR contains:

```text
assets/dwarven_forge/textures/entity/dwarf/profession/toolsmith.png
```

The source-native identity corresponding to the lore's Dwarven Forger is therefore:

```text
minecraft:toolsmith
```

The production Forge-Thane provider can require that native profession while layering the political title through the authored anchor identity.

## Forge-Thane identity

The production provider requires three independent conditions:

```text
entity: dwarven_forge:dwarf
native profession: minecraft:toolsmith
anchor tag: overlord_anchor:dwarf_forge_thane
```

Quest-critical protection remains separate:

```text
overlord_quest_protected
```

The separation is mandatory:

- `dwarven_forge:dwarf` proves source-native species/entity identity;
- `minecraft:toolsmith` proves the selected Dwarf is a native Dwarven Forger;
- `overlord_anchor:dwarf_forge_thane` proves this particular Forger is the political Forge-Thane of the designated successor hold;
- `overlord_quest_protected` prevents accidental loss before an authored destructive route permits death.

No custom `forge_thane` native profession is invented.

## Native economy and interaction preservation

The exact installed mod contains its own `DwarfTrades` system and amethyst-denominated trade classes, including item-for-amethyst and amethyst-for-item listings. This matches the current REIGN decision to preserve the mod's amethyst-centered Dwarven economy.

Because `DwarfEntity` is a Villager subclass with native trade interaction, OVERLORD QUESTS retains its separate provider gesture:

```text
sneak + main-hand entity interaction
```

The provider event is consumed only when the selected entity currently exposes relevant Questlog provider content. Ordinary non-sneaking Dwarf trading remains the responsibility of The Dwarven Forge 1.0.0.

## Production boundary

The installed 1.0.0 audit establishes enough technical support for a first-contact provider:

- exact installed version and JAR identity are known;
- exact Dwarf registry ID is known;
- DwarfEntity is a native Villager subclass;
- the mod explicitly maps vanilla `toolsmith` to `Dwarven Forger`;
- the existing Questlog Villager-profession bridge can enforce that profession;
- native amethyst-centered Dwarf trading remains mechanically authoritative;
- one authored anchor tag can narrow the provider to the designated Forge-Thane before final coordinates exist.

It does NOT establish:

- exact final successor-hold coordinates;
- a new Forge-Thane entity or profession;
- later Dwarven disposition outcomes;
- destruction, subjugation, tribute, rune rewards, price changes, or branch rewards;
- global political authority over all Dwarves;
- that ordinary Dwarves possess complete historical knowledge of Goldo or the old Golden Hills state.

Those later outcomes remain governed by explicit campaign authoring and the newer Dwarven civilization decisions.
