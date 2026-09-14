# Remaining Assigned Questlines

Status: PRODUCTION QUEST CONTENT / SOURCE-BACKED IMPLEMENTATION

This document records the production implementation for four dedicated assignments that were still absent from the bundled quest manifest: Rats, Church of Sin, Oddities, and Immersive Melodies / Quaver's Tower Band.

It does not create new world canon. The assignment authority remains the OVERLORD REIGN lore repository. This document records the technical mapping used by Overlord Quests.

## Rats / Ratlantis

Installed artifact: `Rats-1.20.1-8.1.3.jar`.

The installed JAR includes the Ratlantis datapack and durable native advancements for entering Ratlantis, crafting the Gem of Ratlantis, obtaining Oratchalcum, defeating the Rat Baron, and defeating the Flying Dutchrat.

Production wrapper:

1. `campaign/adventures/rats/empire_beneath_the_cheese`
2. `campaign/adventures/rats/wealth_of_a_fallen_empire`
3. `campaign/adventures/rats/break_the_ratlantean_powers`

Questlog observes native persistent advancements. It does not recreate the Chunky Cheese Token, Ratlantis portal, material progression, or boss fights.

Final fact: `overlord_reign:adventure/ratlantis_campaign_completed`.

## Church of Sin

Installed artifact: `church_of_sin_Forge_v1.0.jar`, mod id `church_of_sin`, version `1.0.0`.

The JAR contains worldgen structure `church_of_sin:cursedcathedral`. Inspection of that exact template shows embedded vanilla Zombies, Skeletons, Zombie spawners, books, bells, skulls, banners, and containers. It exposes no Church-specific boss or faction entity.

Production wrapper:

1. `campaign/adventures/church_of_sin/find_the_cursed_cathedral`
2. `campaign/adventures/church_of_sin/break_the_dead_congregation`

The first stage uses retrospective structure history. The second stage uses live post-discovery Zombie and Skeleton kills. Entity objectives are not location-bound by Questlog, so runtime validation must perform those kills inside the discovered cathedral. This is a known technical limitation, not permission to invent a Church-specific boss.

Final fact: `overlord_reign:adventure/church_of_sin_expedition_completed`.

## Oddities

Installed artifact: `oddities-1.0.1-forge-1.20.1.jar`, mod id `oddities`, version `1.0.1`.

Exact installed resources establish structure `oddities:orchid_shrine`, block `oddities:orchid_altar`, item `oddities:orchid_heart`, entity `oddities:queen_of_orchid`, and native Queen loot. Bytecode inspection of `OrchidAltarOnBlockRightclickedProcedure` confirms an unsummoned Orchid Altar consumes an Orchid Heart for non-creative players and begins the Queen summon state. Questlog observes the Heart and Queen outcome but does not duplicate the altar ritual.

Production wrapper:

1. `campaign/adventures/oddities/find_the_orchid_shrine`
2. `campaign/adventures/oddities/heart_for_the_altar`
3. `campaign/adventures/oddities/cut_down_the_queen`

The final quest uses persistent Questlog kill history so an already-defeated Queen can satisfy the capstone when the branch is discovered late.

Final fact: `overlord_reign:adventure/oddities_orchid_queen_defeated`.

## Immersive Melodies / Quaver's Tower Band

Installed artifact: `immersive_melodies-0.7.0+1.20.1-forge.jar`, mod id `immersive_melodies`, version `0.7.0+1.20.1`.

The installed instrument registry includes Lute, Tiny Drum, Flute, Trumpet and other instruments. Its packaged advancement only unlocks recipes from Copper Ingot acquisition, so that advancement is not evidence of forming or performing with a band.

The source-game reference for Quaver establishes his minstrel/court role and the original Overlord II Netherworld band uses string/percussion court music. Immersive Melodies has no harp item, so the production implementation uses Lute plus Tiny Drum as the direct string/percussion nod, then adds Flute and Trumpet to satisfy the approved full-band treatment.

Production wrapper:

1. `campaign/personnel/quaver/instruments_for_the_court`
2. `campaign/personnel/quaver/fill_out_the_band`
3. `campaign/personnel/quaver/first_tower_performance`

The first two stages observe possession of the four selected instruments. The finale uses live `questlog:item_use` objectives for Lute and Tiny Drum, so merely owning the instruments does not complete the performance stage.

The arc branches from `campaign/tower/claim_the_throne` and deliberately does not require `campaign/tower/restoration_complete`. Quaver's band remains optional court-life content, separate from formal Tower Restoration.

Final fact: `overlord_reign:tower/quaver_band_established`.

## Validation

`tools/validate_remaining_assigned_questlines.py` statically guards all eleven production definitions, manifest inclusion, prerequisite chains, exact native identifiers, capstone facts, and Quaver's separation from formal Tower Restoration.

The normal adventure contract workflow also runs this validator. A separate Quaver-only workflow is unnecessary because this validator covers the complete four-assignment block in one contract.
