# Assigned Questline Coverage Closure

Status: PRODUCTION COVERAGE LEDGER

This document reconciles the Overlord Quests implementation against the read-only quest authority in `magicienlord/Overlord_Lore_and_Canon`, especially `reference/32_REIGN_QUESTLINE_COVERAGE_LEDGER.md`, `reference/36_REIGN_MOD_QUESTLINE_ASSIGNMENTS_FINAL.md`, and `reference/37_REIGN_PERSONAL_MOD_SIDEQUEST_DECISIONS.md` at the pinned authority checkpoint used by this implementation pass.

It does not create new world canon. It records what the Quest implementation has actually supplied, what is deliberately absorbed/systemic, and what remains blocked by unresolved authority or unfinished external backports.

## Dedicated content implemented

### Core campaign and Tower

Implemented:

- opening and Brown Minion return;
- throne claim and Minion infrastructure;
- Tower forge, storage, armory, treasury and gates-room restoration;
- selected Tower magic-room restoration;
- Tower restoration capstone;
- expansion foundation;
- Red, Green and Blue Minion progression unlock quests;
- central End campaign and production ending presentation.

### Core magic

Implemented dedicated arcs:

- Iron's Spells 'n Spellbooks;
- Farmer's Spell / Gluttony;
- Theurgy;
- Ars Elixirum;
- Biomancy;
- Eidolon: Repraised.

### Dedicated adventure content

Implemented:

- Twilight Forest;
- L_Ender's Cataclysm;
- The Graveyard;
- The Bumblezone;
- Knight Quest;
- The Lost Castle;
- Rats / Ratlantis;
- Church of Sin;
- Oddities / Orchid Shrine.

### Small and conditional authored content

Implemented:

- Immersive Melodies / Quaver's Tower Band as an optional Tower personnel arc;
- Pet Cemetery as a conditional sidequest that becomes visible only after the player's own supported tame dies and then follows the mod's real resurrection advancement.

## Civilization coverage

Generalized civilization roster: 10.

Implemented main-entry coverage: 9/10.

Implemented:

- Dwarves;
- Gnumus;
- Goblins;
- Illagers through the hostile Bastille opening;
- Kobolds;
- Piglins;
- Ribbits;
- Sea Dwellers;
- Umvuthana.

Authority-blocked:

- Villagers: the principal settlement/provider for the main civilization entry is still undefined.
- Illagers after Bastille authority: the surviving fearful/cowed provider is still undefined.

These gaps must not be filled by arbitrarily selecting an NPC role.

Demons remain explicitly outside the generalized civilization system.

## Central End campaign

Implemented production sequence:

1. `campaign/end/the_wound_beyond_the_world`
2. `campaign/end/break_the_dragon`

The first quest records entry into the End as the dimensional Wasteland and auto-claims `overlord_reign:campaign/ending_armed`. The second resolves on persistent Ender Dragon defeat.

YUNG's Better End Island, The Outer End, Better End Cities, Enderman Overhaul and related End extensions remain absorbed into this context rather than becoming separate quest branches.

The ending does not require all civilizations, all optional content, or full Tower completion. The same world remains playable afterward.

## Minion recovery fidelity boundary

Red, Green and Blue recovery is implemented through the actual Minion progression owner bridge, but the current proof objectives use practical proxies:

- Red: Blaze Rod;
- Green: Spider Eye;
- Blue: Prismarine Crystal.

This is an explicit fidelity limitation. The source-game recovery identities are known, but the current pack does not provide direct replicas of those exact Hive locations. Do not manufacture fake Hive blocks, fake bosses, or arbitrary cross-mod substitutions solely to make the quest text look closer to source geography.

## Popup-only and systemic assignments

The final authority explicitly says that absence from the quest log can be correct.

A dedicated system-reaction channel covers the source-backed cases that require or clearly benefit from a one-time or milestone acknowledgement without becoming quests:

- Enchanting System Overhaul introductory acknowledgement;
- LevelUP introductory acknowledgement after real stat investment;
- RPG Skill Trees introductory acknowledgement after a real skill unlock;
- Spice of Life: Carrot Edition milestones at 10, 25, 50, 75 and 100 distinct foods;
- Legendary Farming acknowledgement after a real mega-crop harvest;
- Crop Critters acknowledgement after native player ownership of a tamed critter is observed;
- Golem Overhaul acknowledgement on direct encounter with one of its golem entities;
- BloomingNature acknowledgement on direct interaction with its Wandering Gardener provider.

See `docs/SYSTEM_REACTIONS.md`.

Other reviewed content assigned popup-only, absorbed, ambient, provider-support, quest-location, Tower-substrate, systemic, or no quest-facing treatment is deliberately not expanded into fake questlines merely to increase coverage count. Examples include Darker Depths, Born in Chaos, Realm RPG: Imps & Demons, mounts and companions, Artifacts/Relics, general equipment systems, Pale Garden Backport, Nether Depths Upgrade, Small Ships, Creeper Overhaul, storage/infrastructure mods, navigation mods and combat frameworks.

Future sparse acknowledgements may be added only when there is a genuinely eventful source-backed trigger. The authority does not require one popup for every reviewed mod.

## Absorbed assignments

The following categories are accounted for by their owning campaign context rather than by independent questlines:

- Hot Iron in Tower forge restoration;
- storage/display systems in Tower storage/armory contexts;
- Minion implementations in Minion/Tower progression;
- Farmer's Delight family in Gluttony/food contexts;
- Cataclysm: Spellbooks between Cataclysm and spell-study contexts;
- Enderman Overhaul and End extensions in End/Wasteland content;
- Pillager Caravans, Savage & Ravage and The Conjurer in Illager material;
- VillagersPlus and VillagerTradingPlus in the future Villager civilization framework;
- agricultural/worldgen/support mods in the contexts assigned by the lore ledger.

Absorption is a deliberate completion state, not an omission.

## Personal backport sidequests

### Overlord Depths / Fathoms

Assignment: dedicated Historian-led sidequest when the backport is implemented and stable enough to expose real mechanics.

Current implementation state: DEFERRED.

Latest checked live branch: `validation/source-closure-direct-2026-09-13`.
Latest checked head: `11740ee915e34e1fac5c8c7bd6c90d7341cc9e53`.
Exact-head validation still has four failing workflows.

Do not bind production objectives to this moving source boundary.

### Overlord NightWalker / Nycto

Assignment: Lestat-led vampire-transition sidequest when the player becomes a vampire through the implemented NightWalker system.

Current implementation state: DEFERRED.

Latest checked live branch: `fix/nightwalker-runtime-closure-2026-09-14`.
Latest checked head: `4da22b27755351d9038f0c63ee5b1137d2ece5f2`.
There are no exact-head GitHub Actions runs proving this runtime-closure state.

Do not author the vampire transition from intermediate mechanics or generic vampire assumptions. Lestat characterization must use the dedicated lore writing authority when implementation becomes stable.

## Known implementation limitations

- Church of Sin kill objectives are not structure-location-bound after cathedral discovery.
- Red/Green/Blue recovery uses practical item proxies rather than direct source-game Hive recreations.
- Full-instance manual validation remains necessary for presentation and optional-mod reflection paths.

These are explicit technical limitations, not authority gaps that may be filled with invented lore.

## Content-complete boundary

Within the current read-only lore authority and current external backport state, the remaining authored Quest blockers are:

1. Villager principal settlement/provider selection;
2. Illager post-Bastille fearful/cowed provider selection;
3. a stable, validated Overlord Depths implementation boundary;
4. a stable, validated Overlord NightWalker implementation boundary.

All other dedicated questline assignments in the current final assignment ledger are implemented or deliberately accounted for through their approved absorbed/systemic treatment.
