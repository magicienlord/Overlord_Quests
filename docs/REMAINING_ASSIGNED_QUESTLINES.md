# Assigned Questline Coverage Closure

Status: PRODUCTION COVERAGE LEDGER

This document reconciles the Overlord Quests implementation against the read-only quest authority in `magicienlord/Overlord_Lore_and_Canon`. It records implementation coverage; it does not create new setting canon.

## Dedicated content implemented

Core campaign/Tower, core magic, assigned adventure content, central End completion, Minion recovery, Quaver's optional Tower-band arc, Pet Cemetery, civilization entry coverage and the conditional NightWalker/Lestat transition arc all have production Quest definitions or deliberate systemic treatment.

Implemented dedicated adventure arcs include Twilight Forest, L_Ender's Cataclysm, The Graveyard, The Bumblezone, Knight Quest, The Lost Castle, Rats / Ratlantis, Church of Sin and Oddities / Orchid Shrine.

Implemented dedicated magic arcs include Iron's Spells 'n Spellbooks, Farmer's Spell / Gluttony, Theurgy, Ars Elixirum, Biomancy and Eidolon: Repraised.

## Civilization coverage

Generalized civilization roster: 10.

Implemented main-entry coverage: **10/10**.

Implemented:

- Villagers through the designated biome-appropriate historical remnant/successor settlement;
- Illagers through the hostile Bastille authority step and the later fearful/cowed peaceful-interaction phase;
- Dwarves;
- Gnumus;
- Goblins;
- Kobolds;
- Piglins;
- Ribbits;
- Sea Dwellers;
- Umvuthana.

The Villager settlement/provider and Illager post-Bastille intermediary are implementation choices explicitly delegated to Quest Maker/technical discretion by the authority layer. They are not unanswered lore questions.

Demons remain outside the generalized civilization system.

## Central End campaign

Production sequence:

1. `campaign/end/the_wound_beyond_the_world`
2. `campaign/end/break_the_dragon`

The End is treated as the dimensional Wasteland context; Ender Dragon defeat is the final mechanical trigger. The ending does not require all civilizations, all optional content or full Tower completion, and the same world remains playable afterward.

## Minion recovery fidelity boundary

Red, Green and Blue recovery uses the real Overlord Minions progression owner bridge, with current practical proof proxies:

- Red: Blaze Rod;
- Green: Spider Eye;
- Blue: Prismarine Crystal.

These are explicit implementation proxies, not claims about the source-game Hives. Do not fabricate fake Hive blocks, bosses or arbitrary cross-mod replacements merely to imitate source geography.

## Systemic / popup-only assignments

The production system-reaction channel covers the source-backed sparse acknowledgement cases for Enchanting System Overhaul, LevelUP, RPG Skill Trees, Spice of Life: Carrot Edition, Legendary Farming, Crop Critters, Golem Overhaul and BloomingNature.

Other assignments explicitly categorized as absorbed, ambient, provider-support, quest-location, Tower-substrate, systemic, popup-only or no quest-facing treatment remain deliberately outside dedicated questlines. Absence from the journal can be the correct completion state.

## Personal backport sidequests

### Overlord NightWalker / Nycto

Status: **IMPLEMENTED AGAINST SUPPLIED ALPHA.3**.

Technical authority used for this pass:

```text
nycto-forge-1.20.1-overlordreign-1.0.0-alpha.3.jar
SHA-256 a24de1fb23b83bc15263e40511782a8b46f6dd151b55e541e8727dfa0fe21503
```

The implementation observes the real Nycto persistent player state rather than inventing advancements:

- completed transformation: `Nycto.vampire`;
- Vampire Altar purchased powers: `Nycto.powerMask`;
- runtime presence guard: `nycto:vampirism`.

The conditional production arc is:

1. `campaign/sidequests/nightwalker/lestat_arrives`;
2. `campaign/sidequests/nightwalker/hunger_is_a_fact`;
3. `campaign/sidequests/nightwalker/the_vampire_altar`;
4. `campaign/sidequests/nightwalker/choose_the_price`.

Lestat is one protected tagged `nycto:vampire` used as a REIGN-authored Tower provider. He is not a character supplied by Nycto, not part of formal Tower Restoration, and not a crossover from another continuity.

See `docs/NIGHTWALKER_LESTAT_INTEGRATION.md`.

### Overlord Depths / Fathoms

Assignment: dedicated Historian-led sidequest when the backport has a validated implementation boundary.

Status: DEFERRED BY CURRENT EXTERNAL TECHNICAL VALIDATION ONLY.

Fresh live check on 2026-09-14:

```text
branch: validation/source-closure-direct-2026-09-13
head: 9e1c5ed6dab40da0bc728abd9dcb62f59710ef4f
message: Align runtime advancement count with source
```

The exact head has five observed workflows. Source Resource Closure Audit succeeds, but **three exact-head workflows still fail**:

- Build Forge 1.20.1 Backport;
- Target YUNG Rocky Waters;
- Target Core Worldgen Rocky Waters.

Therefore the Historian arc remains the sole assigned external-backport quest deferred by a live technical boundary. This is not an unresolved lore/design question. Recheck the live Depths branch and exact-head CI before the next integration attempt; do not bind production Quest objectives to this failing checkpoint.

## Known implementation limitations

- Church of Sin kill objectives are not structure-location-bound after cathedral discovery.
- Red/Green/Blue recovery uses practical item proxies rather than direct source-game Hive recreations.
- Villager, Illager intermediary and Lestat anchors require final-world placement/tagging and manual full-instance qualification.
- Full-instance presentation and optional-mod paths still require manual validation beyond standalone CI.

## Remaining assigned-work boundary

There are no remaining authority questions blocking Villager, Illager or NightWalker content.

The only currently deferred assigned dedicated arc is Overlord Depths / Fathoms, and the deferral is strictly technical: its live backport checkpoint is not yet green. Once a stable exact Depths checkpoint exists, inspect that implementation's real Historian, structures, items, bosses and progression signals and author the mandatory Historian-led sidequest from those mechanics.
