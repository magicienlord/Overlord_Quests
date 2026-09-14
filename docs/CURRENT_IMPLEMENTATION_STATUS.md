# OVERLORD QUESTS Current Implementation Status

Status: LIVE ENGINEERING LEDGER

This file records the repository implementation boundary. It is not a source of new OVERLORD REIGN canon. Lore authority remains `magicienlord/Overlord_Lore_and_Canon`, read-only from this repository.

## Runtime target

```text
Minecraft Java 1.20.1
Forge 47.4.10
Java 17
Primary branch: gnarl-bootstrap
```

OVERLORD REIGN is a single-player project. Dedicated-server boot remains a compatibility smoke boundary rather than a player-facing multiplayer support promise.

## Engine state

Implemented and repository-validated at the previous checkpoint, with the current closure pass extending the same architecture:

- bundled production quest definitions and config override workflow;
- persistent narrative facts and civilization dispositions;
- NPC providers with exact UUID binding, same-provider turn-in, dialogue, protection and narrow native-role bridges;
- sequence-break history objectives;
- Overlord Minions owner-state integration;
- production central ending and persistence;
- sparse one-time system reactions for content intentionally kept outside the journal;
- Forge server/client bootstrap smoke boundaries and narrative-state persistence checks.

The current pass additionally adds:

- player-specific optional NightWalker owner-state objectives with no hard Nycto class dependency;
- a local Illager post-Bastille restraint bridge that affects only the designated protected intermediary;
- production Villager main-entry content;
- production Illager fearful/cowed continuation;
- the conditional Lestat-led NightWalker transition arc;
- the source-backed Historian-led Overlord Depths / Fathoms nautical arc.

## Bundled production campaign coverage

Opening/Tower, assigned core-magic arcs, assigned adventure arcs, Quaver, Pet Cemetery, central End completion and Minion recovery are implemented as previously documented.

### Civilization coverage

Generalized civilization roster: 10.

Main-entry coverage implemented: **10/10**.

Implemented civilization entries:

- Villagers;
- Illagers, including hostile Bastille authority and post-authority fearful/cowed audience;
- Dwarves;
- Gnumus;
- Goblins;
- Kobolds;
- Piglins;
- Ribbits;
- Sea Dwellers;
- Umvuthana.

The Villager and Illager local-provider choices are delegated implementation decisions, not unresolved authority questions.

Demons remain explicitly outside the generalized civilization disposition system.

### Villager boundary

The main human anchor is one protected vanilla Villager at a deliberately authored, biome-appropriate historical remnant/successor settlement. Questlog stores no fixed historical-site name, profession or coordinates. First contact records only `overlord_reign:civilizations/villagers/contact_established` and does not resolve political disposition.

### Illager boundary

The hostile opener still uses the marked `takesapillage:legioner` commander and records `overlord_reign:civilizations/illagers/authority_established`.

A separate protected `minecraft:pillager` marked `overlord_anchor:illager_bastille_intermediary` becomes the designated local fearful intermediary after authority is established. Only that NPC is restrained by Questlog. Completing the peaceful audience records `overlord_reign:civilizations/illagers/bastille_cowed` and sets the local civilization runtime state to `overlord_reign:neutral` without creating a global Illager truce.

### NightWalker / Lestat boundary

Status: IMPLEMENTED AGAINST SUPPLIED ALPHA.3.

Exact supplied build:

```text
nycto-forge-1.20.1-overlordreign-1.0.0-alpha.3.jar
SHA-256 a24de1fb23b83bc15263e40511782a8b46f6dd151b55e541e8727dfa0fe21503
```

Questlog reads the real Nycto player-owned state:

- `Nycto.vampire` for completed transformation;
- `Nycto.powerMask` for choosable Vampire Altar purchases;
- registered `nycto:vampirism` as the runtime-presence guard.

The Lestat sequence covers arrival after completed transformation, deliberate blood replenishment, Vampire Altar use and at least one real altar power purchase. It deliberately stops before duplicating the rest of NightWalker's progression.

Lestat is represented by one protected tagged `nycto:vampire` at the Dark Tower. Exact placement remains world integration; Questlog does not invent Tower coordinates.

## Central End campaign

Production central completion remains:

1. `campaign/end/the_wound_beyond_the_world`;
2. `campaign/end/break_the_dragon`.

The ending does not require every civilization, optional sidequest or Tower branch, and the same world remains playable afterward.

## Minion recovery fidelity

Red, Green and Blue recovery uses the actual Minion progression owner bridge with practical proof proxies: Blaze Rod, Spider Eye and Prismarine Crystal respectively. These remain explicit implementation proxies rather than claims about source-game Hive identity.

## Sparse system reactions

The one-time system-reaction channel remains the production treatment for Enchanting System Overhaul, LevelUP, RPG Skill Trees, Spice of Life: Carrot Edition, Legendary Farming, Crop Critters, Golem Overhaul and BloomingNature milestones already documented in `docs/SYSTEM_REACTIONS.md`.

## Overlord Depths / Fathoms

Status: IMPLEMENTED AGAINST VALIDATED DEPTHS CHECKPOINT.

Validated source boundary checked during this pass:

```text
repository: magicienlord/Overlord_Depths
branch: validation/source-closure-direct-2026-09-13
head: b00ef7a33550267a826f98d4989f6ea63e6909a9
tree: 25e8c3a3de67543fe4f62a02ae3625a471423068
```

All five exact-head workflows observed on this checkpoint succeeded. The earlier external technical deferral is therefore closed.

The sidequest uses the real `fathoms:historian` Villager profession and observes five native nautical advancements: bait use, aberration catch, coffer opening, full fish catalogue, and the source-owned `fathoms:nautical/make_a_bad_decision` ritual capstone. Questlog does not replace Fathoms mechanics and does not invent a boss or lore ending.

See `docs/FATHOMS_HISTORIAN_INTEGRATION.md`.

## Known technical limitations

- Church of Sin kill objectives are not location-bound after cathedral discovery.
- Minion recovery uses practical item proxies instead of direct Hive recreations.
- Villager, Illager intermediary and Lestat anchors require final-world placement/tagging.
- The Fathoms arc still requires full-instance qualification of presentation, native Historian interaction and advancement handoff.
- Full-instance manual validation remains necessary for presentation and optional-mod interaction paths.

## Validation rule

A green repository workflow proves only its stated static/build/smoke contract. It does not substitute for full-instance gameplay qualification.

The mandatory assigned dedicated-arc inventory is now represented in production definitions. Future work should focus on exact-head validation, full-instance qualification, refinement from new source evidence, and later explicitly approved expansion rather than restoring superseded Villager, Illager, NightWalker or Fathoms blocker language.
