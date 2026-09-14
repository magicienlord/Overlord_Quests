# OVERLORD QUESTS Current Implementation Status

Status: LIVE ENGINEERING LEDGER

This file records the current repository implementation boundary. It is not a source of new OVERLORD REIGN world or story canon. Lore authority remains `magicienlord/Overlord_Lore_and_Canon`, read-only from the Quest implementation.

## Runtime target

```text
Minecraft Java 1.20.1
Forge 47.4.10
Java 17
Primary branch: gnarl-bootstrap
```

OVERLORD REIGN remains a single-player project. Automatic full-screen speaker and ending presentation targets an unpublished local integrated-server session. Dedicated-server boot remains a compatibility smoke boundary, not a player-facing multiplayer support promise.

## Engine state

Implemented and repository-validated:

- inherited Questlog quest/chapter loading and config override workflow;
- bundled production definitions under `assets/questlog/overlord/definitions/`;
- strict definition validation, wire-size checks, dependency-cycle checks, cache authority, event-bus lifecycle guards and packet hardening;
- persistent narrative facts and civilization dispositions;
- NPC provider rules, exact provider UUID binding, same-provider turn-in, distance revalidation, provider dialogue and protected quest anchors;
- native/provider compatibility bridges used only where exact mod mechanics require them;
- Minion owner-state bridge through the Overlord Minions public progression API;
- sequence-break history objectives for structures, dimensions, positions, kills, crafting and Ender Dragon defeat;
- optional objectives whose progress persists without blocking completion when marked optional;
- failure-consequence support;
- Gnarl/incorporeal speaker presentation and provider presentation split;
- OVERLORD death-screen compatibility boundary;
- production central-ending activation, one-time presentation persistence and prior-Dragon sequence-break delivery;
- a separate one-time system-reaction channel for sparse popup-only assignments, without manufacturing hidden quests or narrative facts;
- Forge development dedicated-server and client bootstrap smoke boundaries;
- real two-boot narrative-state persistence coverage through authenticated localhost RCON.

Manual in-game qualification is still required for player-facing presentation details and exact full-modpack interaction paths. Standalone static validation and Forge bootstrap checks do not replace those checks.

## Bundled production campaign coverage

### Opening and Dark Tower

The opening, Brown Minion restoration, throne claim, Minion infrastructure, forge, storage, armory, treasury, gates room, magic-room restoration and Tower restoration capstone are implemented.

### Core magic arcs

Dedicated production progression exists for:

- Iron's Spells 'n Spellbooks;
- Farmer's Spell / Gluttony;
- Theurgy;
- Ars Elixirum;
- Biomancy;
- Eidolon: Repraised.

### Adventure and personnel arcs

Dedicated production content exists for:

- Twilight Forest;
- L_Ender's Cataclysm;
- The Graveyard;
- The Bumblezone;
- Knight Quest;
- The Lost Castle;
- Rats / Ratlantis;
- Church of Sin;
- Oddities / Orchid Shrine;
- Quaver's Tower Band as an optional Tower personnel arc;
- Pet Cemetery as a conditional pet-resurrection sidequest.

### Central End campaign

Production central-campaign content is implemented:

1. `campaign/end/the_wound_beyond_the_world` requires only the established first foundation and retrospective entry into `minecraft:the_end`.
2. That quest auto-claims `overlord_reign:campaign/ending_armed` and records the dimensional Wasteland entry.
3. `campaign/end/break_the_dragon` resolves on the persistent Ender Dragon defeat boundary.
4. The custom ending presentation is production text, not a development scaffold, and returns the player to the still-playable world.

The ending does not require all civilizations, all optional content, or formal completion of every Tower branch.

## Civilization coverage

Generalized civilization roster: 10.

Main-entry coverage implemented: 9/10.

Implemented civilization entries:

- Dwarves;
- Gnumus;
- Goblins;
- Illagers through the hostile Bastille opening;
- Kobolds;
- Piglins;
- Ribbits;
- Sea Dwellers;
- Umvuthana.

Still unresolved at the authority level:

- Villagers: the principal settlement/provider for the main civilization entry has not been selected.
- Illagers: the surviving fearful/cowed post-Bastille provider has not been selected.

Demons remain explicitly outside the generalized civilization disposition system.

### Piglin implementation boundary

The designated local Chieftain is the exact protected `minecraft:piglin_brute` carrying `overlord_anchor:piglin_main_chieftain`.

Gold armor provides only enough temporary restraint for the initial audience. The bridge suppresses hostility only for that protected designated Chieftain and only while the player is wearing at least one gold armor piece, or later if an explicitly authored peaceful disposition exists.

The first audience records `overlord_reign:civilizations/piglins/contact_established`. It deliberately does not assign the Piglin village's final political disposition.

## Red, Green and Blue Minion recovery

Production recovery quests exist for all three later traditional Minion slots and use the real Minion progression owner bridge.

Current practical recovery proofs are:

- Red: Blaze Rod;
- Green: Spider Eye;
- Blue: Prismarine Crystal.

These are implementation proxies, not claims that the source-game Hives were those items. Source-game identity is known: Red Hive at Melvin's Kitchen / Mellow Hills, Green Hive in the Viridian Caverns / Evernight, and Blue Hive in the Moist Hollows / Heaven's Peak.

The current quests are therefore IMPLEMENTED WITH A FIDELITY LIMITATION. Do not replace the proxies with invented fake Hive items, structures, bosses, or unrelated-mod mappings merely to imitate source geography.

## Sparse system reactions

The final assignment ledger explicitly keeps several systems outside the quest log. Questlog has a persistent one-time reaction channel for source-backed cases where an introductory or milestone popup is specifically useful.

Implemented bindings:

- Enchanting System Overhaul: first main-hand interaction with the real enchanting table;
- LevelUP: first detected base-stat investment;
- RPG Skill Trees: first actual skill unlock in the `rpg_skill_trees` Pufferfish Skills category;
- Spice of Life: Carrot Edition: distinct-food milestones at 10, 25, 50, 75 and 100;
- Legendary Farming: first successful harvest of a registered mega crop block;
- Crop Critters: first nearby native owner-state detection for a tamed Crop Critter owned by the player;
- Golem Overhaul: first direct interaction with a Golem Overhaul golem;
- BloomingNature: first interaction with the exact Wandering Gardener provider.

These reactions are intentionally not production quest definitions and do not write narrative facts.

Other content assigned popup-only, absorbed, ambient or no quest-facing treatment remains deliberately absent from the quest log unless a later eventful source-backed acknowledgement is worth adding. Absence is not itself a coverage defect.

## Known technical limitations

### Church of Sin location binding

The Church finale uses live Zombie and Skeleton kill objectives after the Cursed Cathedral is discovered. Questlog entity-kill objectives are not structure-location-bound, so those kills can technically occur elsewhere after unlock. Runtime validation should perform them inside the cathedral. This limitation does not justify inventing a Church-specific boss.

### Minion recovery fidelity

Red, Green and Blue recovery currently uses the practical item proxies listed above rather than source-game location reproductions.

### Full-instance presentation

Standalone validation cannot replace manual checks for Gnarl/provider composition, all optional-mod reflection paths, Minion progression across reloads, and central-ending presentation inside the complete OVERLORD REIGN instance.

## Personal backport sidequests

Lore authority makes both arcs mandatory only when the corresponding personal backport is implemented and stable enough to expose real mechanics.

### Overlord Depths

Status: DEFERRED BY EXTERNAL IMPLEMENTATION BOUNDARY.

At the latest checked live branch `validation/source-closure-direct-2026-09-13`, head `11740ee915e34e1fac5c8c7bd6c90d7341cc9e53`, source-resource closure is still under active validation and four exact-head workflows are failing. Do not bind a production Historian arc to this moving target.

### Overlord NightWalker

Status: DEFERRED BY EXTERNAL IMPLEMENTATION BOUNDARY.

At the latest checked live branch `fix/nightwalker-runtime-closure-2026-09-14`, head `4da22b27755351d9038f0c63ee5b1137d2ece5f2`, the runtime-closure work has no exact-head GitHub Actions evidence. Do not author the Lestat-led vampire transition from unvalidated intermediate mechanics.

## Validation rule

A green repository workflow means the checked contracts compiled and/or passed their stated standalone static/runtime boundary. It does not by itself prove the complete OVERLORD REIGN instance.

When a blocked authoring boundary is explicitly resolved, update this file and the appropriate focused integration/test document in the same implementation pass. Do not silently promote a proposal, provisional backport mechanic, or unspecified provider into production campaign canon.
