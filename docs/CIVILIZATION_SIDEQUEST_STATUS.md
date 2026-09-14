# Civilization Sidequest Integration Status

Status: TECHNICAL / IMPLEMENTATION STATUS

This file records production civilization coverage. It is not a source of new setting canon; world and quest authority remains the read-only OVERLORD REIGN lore repository.

Generalized civilization main-entry coverage is now **10/10**:

- Villagers;
- Illagers;
- Dwarves;
- Gnumus;
- Goblins;
- Kobolds;
- Ribbits;
- Sea Dwellers;
- Piglins;
- Umvuthana.

Demons remain explicitly outside the generalized civilization framework.

## Authoring threshold

A civilization contact quest does not automatically justify generic sidequests. Production sidequest content should have a real native activity signal, a concrete approved world requirement, or an established political/service consequence. Do not create filler merely because a provider entity exists.

## Villagers

Main entry: IMPLEMENTED.

Production quest:

```text
campaign/civilizations/villagers/first_contact
```

The provider is one protected `minecraft:villager` marked `overlord_anchor:villager_main` at the deliberately authored biome-appropriate historical remnant/successor settlement. No profession or fixed historical-site name is forced by Questlog; those exact world-fit choices were delegated to Quest Maker/world integration.

Completion records `overlord_reign:civilizations/villagers/contact_established` and deliberately leaves political disposition unresolved.

See `docs/VILLAGER_CIVILIZATION_INTEGRATION.md`.

## Illagers

Hostile opening and fearful/cowed continuation: IMPLEMENTED.

Opening:

```text
campaign/civilizations/illagers/break_the_bastille
```

It uses persistent player-attributed kill history for the exact `takesapillage:legioner` marked `overlord_anchor:illager_bastille_commander` and records `overlord_reign:civilizations/illagers/authority_established`.

Continuation:

```text
campaign/civilizations/illagers/the_bastille_bows
```

One protected `minecraft:pillager` marked `overlord_anchor:illager_bastille_intermediary` becomes the local fearful intermediary after authority is established. A narrow Forge bridge restrains only that exact protected NPC. The peaceful audience records `overlord_reign:civilizations/illagers/bastille_cowed` and layers the local polity over the generalized `overlord_reign:neutral` state.

This is a cowed peace, not friendship or a global Illager truce. Unrelated Illagers remain native and independent.

See `docs/ILLAGER_BASTILLE_INTEGRATION.md`.

## Goblins

First contact: IMPLEMENTED.

Current source-backed post-contact pool:

- `campaign/civilizations/goblins/merchant_business` using native `goblins_tyranny:merchant_success`;
- `campaign/civilizations/goblins/engineer_workbench` using native `goblins_tyranny:engineer_success`;
- `campaign/civilizations/goblins/tavern_business` using native `goblins_tyranny:liquor_success`.

Additional roles require an actual objective or consequence rather than role-slot filling.

## Gnumus

First contact: IMPLEMENTED.

Current post-contact pool:

- `campaign/civilizations/gnumus/merchant_business`, using native `gnumus:business_approach` and the Gnumu Doubloon trade path.

The hidden Halfling ancestry remains outside ordinary cultural sidequests unless a later approved reveal uses it.

## Sea Dwellers

First contact: IMPLEMENTED.

Current post-contact pool:

- `campaign/civilizations/sea_dwellers/aquamarine_barter`, using native `seadwellers:adv_barter_aquamarine`.

`seadwellers:adv_barter_nautilus` remains rejected because the installed binary audit found no award reference. `seadwellers:adv_barter_fish` belongs to the wild Mermorph path. No Ocean Dragon content belongs to OVERLORD REIGN.

## Umvuthana

First audience: IMPLEMENTED.

Current neutral-Grove pool:

- `campaign/civilizations/umvuthana/suns_blessing`, using native `mowziesmobs:suns_blessing` after the designated Grove is neutral.

The destructive native Umvuthi route remains separate and does not satisfy the blessing sidequest.

## Dwarves

First contact: IMPLEMENTED.

Post-contact pool: OPTIONAL AUTHORING AREA.

The exact Dwarven Forge audit established a technically usable persistent `Stats.ITEM_CRAFTED` path for rune smithing, but no additional production sidequest is created solely because that signal exists. A specific craft, provider purpose and campaign consequence still need to justify a future quest.

## Ribbits

First contact: IMPLEMENTED.

Post-contact pool: OPTIONAL TECHNICAL AUTHORING AREA.

Native Ribbit trading uses the vanilla Merchant path, whose generic trade statistic cannot distinguish a Ribbit transaction from another merchant. Any future Ribbit-specific service quest should use a narrow filtered bridge rather than the global statistic.

## Kobolds

First contact: IMPLEMENTED.

Post-contact pool: OPTIONAL TECHNICAL AUTHORING AREA.

The installed Kobolds artifact exposes native Captain/specialist behavior but no durable player advancement for the relevant transactions. Any future transaction quest should observe the native action narrowly rather than replacing Kobold AI or reward logic.

## Piglins

First contact: IMPLEMENTED.

The designated local Chieftain is the exact protected `minecraft:piglin_brute` marked `overlord_anchor:piglin_main_chieftain`. Gold armor grants only initial local restraint for the audience. Completion records `overlord_reign:civilizations/piglins/contact_established` and does not resolve the final political disposition.

Ordinary Piglins and Piglin Brutes remain native elsewhere.

## Validation rule

Every production civilization activity must preserve:

- exact installed-mod/source evidence where it depends on a native system;
- a narrow provider/anchor identity;
- a runtime qualification protocol where world integration is required;
- static CI contracts for implementation-critical invariants;
- explicit boundaries against accidental global AI, disposition, lore or geography expansion.

The Villager and Illager main-entry work is no longer an authority blocker. Exact site placement and selected local intermediary are implementation choices already delegated by the authority documents.
