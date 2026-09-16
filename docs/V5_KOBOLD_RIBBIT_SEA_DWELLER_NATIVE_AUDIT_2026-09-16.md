# OVERLORD REIGN V5 Kobold, Ribbit and Sea Dweller Native Audit

Status: VERIFIED TECHNICAL FACT / V5 SUPPORTING AUDIT

Date: 2026-09-16

Purpose: record exact source-owned mechanics for the next civilization-authoring pass. This file is technical evidence only and does not assign political meaning without explicit Overlord approval.

## 1. Kobolds 2.12.0

Exact supplied artifact:

```text
Kobolds-2.12.0.jar
SHA-256 f5f5dd31ab42e3bda1a1148b91ae62c07daafb9e52905d263c0bbcfbdf9cbabe
```

The JAR contains no native non-recipe advancement layer.

Source-owned recurring roles include:

```text
kobolds:kobold_captain
kobolds:kobold_engineer
kobolds:kobold_enchanter
kobolds:kobold_warrior
kobolds:kobold_rascal
kobolds:kobold_pirate
```

Generated structural content includes five central Kobold Den templates, multiple Kobold mineshaft pieces, side rooms, tunnels, gravel piles and a separate Pirate Den family.

Captain interaction is source-owned and accepts items from three captain-value tiers before native AI returns loot. The packaged tier tags include ordinary gold and golden equipment at tier one, stronger valuables such as Gold Blocks, Golden Apples, Bells and Ender Chests at tier two, and rare valuables such as a Heart of the Sea, Totem of Undying, Nether Star, Enchanted Golden Apple and Wither Skeleton Skull at tier three. Captain return loot is emerald-based.

Engineer interaction is source-owned and uses its own trade AI. Its native loot pool is engineering-heavy, including Redstone, Redstone Lamps, Repeaters, Rails, Pistons, Hoppers, Dispensers, Droppers, Observers, Minecarts and rarer TNT.

Enchanter interaction is source-owned and has a dedicated trade AI plus Kobold Potion handling. Its packaged loot includes a randomly enchanted book using the Kobold `prospector` enchantment.

Technical consequence:

- Captain, Engineer and Enchanter are real mechanically distinct service roles;
- Warrior is a real military role;
- Dens and mineshafts give the canonical anchor an underground material identity;
- exact provider transactions may require a narrow Questlog bridge because the JAR does not expose durable player advancements for these interactions;
- Pirate Kobolds remain technically separable through their own entity/structure family.

None of these facts determines NEUTRAL, SUBJUGATED or DESTROYED by itself.

## 2. Ribbits 3.0.5

Exact supplied artifact:

```text
Ribbits-1.20.1-Forge-3.0.5.jar
SHA-256 e04aa665df7e96844fe8833f29cc343feefac8ad7b949399d7b4a60475123e2b
```

The JAR contains no meaningful accomplishment advancement ladder. Its packaged advancements are recipe unlocks.

Source-owned professions and village roles include:

```text
Gardener
Fisherman
Merchant
Sorcerer
Nitwit
```

Village structures include profession-specific Fisherman and Sorcerer houses plus generated profession entities and ordinary houses.

Native AI gives the roles actual gameplay distinction:

- Gardeners water crops;
- Fishermen perform fishing behavior;
- Sorcerers apply buffs through native spell behavior;
- Merchants use a real MerchantOffer system;
- Ribbits can play native music, with bass, bongos, flute and guitar sound behavior;
- the mod includes the Maraca as player-facing music content.

The native trade system is amethyst-centered. Merchant Ribbits receive a larger offer pool than other trading professions. Trade code includes item-for-amethyst, amethyst-for-item, enchanted-item and potion offer types.

Technical consequence:

- the village already expresses agriculture, fishing, magical support, trade and music without requiring a fabricated military problem;
- if a V5 quest requires proving a transaction with a specific Ribbit role, a narrow filtered bridge is preferable to the broad vanilla trade statistic;
- no source mechanic requires Ribbits to become aggressive or militarized for their civilization arc.

None of these facts determines terminal political state by itself.

## 3. Realm RPG Sea Dwellers 2.9.9

Exact supplied artifact:

```text
realmrpg_seadwellers_2.9.9_forge_1.20.1.jar
SHA-256 6cf9dd9c5ba8dfb9644b0598b1b3453fb3b4cefc1ffa3ae676e4be82309d90f5
```

The JAR exposes multiple persistent Mermorph professions, including:

```text
Worker
Collector
Architect
Blacksmith
Hunter
Farmer
```

It also provides biome/environment variants including River, Polar, Coral and Depth Mermorphs.

Native accomplishment advancements include:

```text
seadwellers:adv_barter_aquamarine
seadwellers:adv_barter_fish
seadwellers:adv_barter_nautilus
seadwellers:adv_break_sea_lantern
seadwellers:adv_depth_ingot
seadwellers:adv_open_casket
seadwellers:adv_turn_villager_into_mermorph
seadwellers:adv_use_seashell
```

Their installed descriptions establish the following source-owned actions:

- Aquamarine barter with a Mermorph;
- fish barter;
- Nautilus barter with a Collector;
- make a Depth Ingot;
- open a Mermorph casket;
- use a profession Seashell to change a Mermorph profession;
- turn a Villager into a Mermorph;
- enrage Mermorphs by breaking one of their Sea Lanterns.

The source supplies profession-specific Seashells and caskets, Aquamarine, Depth metal/equipment, Oxygen Coral, Sea Village generation and a large set of underwater civic structures including smithy, mine, warehouse, plantation, chapel, library, prison, shrine and other settlement pieces.

Native cultural enforcement is mechanically strong:

- `minecraft:sea_lantern` is included in the Mermorph aggro block tag;
- breaking one applies `Mermorph Rage` for a long duration;
- attacking a Mermorph triggers rage;
- killing a Mermorph produces a still longer rage response.

Technical consequence:

- Sea Dweller property taboo and retaliation are real source behavior, not invented lore;
- profession control through Seashells is a real player-facing mechanic;
- Aquamarine, Nautilus and fish barters are durable native milestones;
- Depth metallurgy and caskets provide real material/service progression;
- the canonical Sea Village can support political authoring around trade, labor/profession structure, local resources and property sovereignty without adding a numeric reputation simulation.

None of these mechanics automatically chooses a terminal civilization state.

## 4. Authority boundary

Established REIGN/V5 constraints remain separate from the JAR facts:

- Kobold-Dwarf rivalry is current but optional shared content and cannot be required to finish either civilization;
- Ribbits retain peaceful, cozy, musical and slightly silly characterization, with dark comedy arising from the Overlord's possible domination or destruction rather than a fabricated grim threat;
- Sea Dwellers may preserve remembered Elven ancestry, but V5 does not automatically tie their anchor to a named historical coast or old city;
- all three use runtime-selected canonical anchors rather than fixed coordinates;
- terminal outcomes are explicit authored political resolutions, not hidden scores or automatic consequences of native mechanics;
- `DESTROYED` means the canonical anchor ceases to function as a polity, not mandatory block-by-block erasure.

The next authoring step must return to the Overlord for any political interpretation not already established by these technical facts.