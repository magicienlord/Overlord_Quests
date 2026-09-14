# Adventure Questline Wrapper Contract

Status: PRODUCTION QUEST CONTENT / NATIVE-PROGRESSION AND FINITE EXPEDITION WRAPPERS

The dedicated non-civilization adventure assignments currently implemented are Twilight Forest, L_Ender's Cataclysm, The Graveyard, The Bumblezone, Knight Quest, The Lost Castle, Rats, Church of Sin, and Oddities.

These arcs are authored REIGN content with a beginning, progression and capstone appropriate to each source. Questlog does not replace a mod's native progression when durable native state already exists. Compact location mods use finite authored expeditions instead of being inflated into large campaign pillars.

Quaver's Tower Band is tracked separately as a Tower personnel arc. Pet Cemetery is tracked separately as a conditional sidequest. The End remains central-campaign material rather than an adventure-wrapper branch.

## Twilight Forest

The wrapper follows the existing Forest progression sequence from Naga and Lich through Labyrinth/Hydra, Knight/Ur-Ghast/Yeti/Glacier, then Troll/Merge/Trophy Pedestal/native progression end.

Questlog does not replace Twilight Forest biome locking, trophies, boss kills, or access logic.

Final marker: `overlord_reign:adventure/twilight_forest_progression_completed`.

## L_Ender's Cataclysm

The wrapper first observes representative structure discovery, then several defining boss milestones, then closes on Cataclysm's own `cataclysm:kill_all_bosses` advancement.

Using the native all-bosses capstone avoids copying the entire boss roster into a second Questlog checklist while still giving the assigned questline a real end state.

Final marker: `overlord_reign:adventure/cataclysm_capstone_completed`.

## The Graveyard

The wrapper covers exploration, the native Lich sequence, and later Wraith/Coffin milestones. Its text explicitly preserves REIGN's distinctions among death, ghosts, persistent souls, undead bodies, resurrection, Lifeforce, and other occult mechanisms.

The Graveyard's proximity of these themes is not treated as proof that they are one substance or one metaphysical process.

Final marker: `overlord_reign:adventure/graveyard_expedition_completed`.

## The Bumblezone

The installed pack uses Bumblezone 7.13.4 for Minecraft 1.20.1. Production uses the native `the_bumblezone` advancement namespace and samples major progression rather than requiring every advancement.

Observed milestones include the dimension root, Beehemoth progression, and essence progression. Questlog does not replace Bumblezone access, Beehemoth mechanics, or essence systems.

Final marker: `overlord_reign:adventure/bumblezone_essence_reached`.

## Knight Quest

Knight Quest exposes a large recipe-advancement surface but only one useful non-recipe entry advancement, `knightquest:knightquest`, granted when Small Essence is obtained. REIGN therefore supplies the authored progression instead of pretending the recipe book is a native campaign.

The arc proceeds through Small Essence, the Great Chalice and Great Essence, Radiant Essence, the native Chalice interaction, Netherman summoning, and a persistent Netherman kill-history capstone.

Questlog does not simulate Chalice charge state.

Final marker: `overlord_reign:adventure/knight_quest_completed`.

## The Lost Castle

Assignment scale: finite expedition.

Production wrapper:

1. `campaign/adventures/lost_castle/a_castle_off_the_map`
2. `campaign/adventures/lost_castle/break_the_lost_court`
3. `campaign/adventures/lost_castle/nothing_left_to_rule`

The arc treats the authored castle as one coherent expedition: discover it, overcome its court, and conclude the site. It does not extrapolate a wider civilization or political system from the location.

## Rats / Ratlantis

Production wrapper:

1. `campaign/adventures/rats/empire_beneath_the_cheese`
2. `campaign/adventures/rats/wealth_of_a_fallen_empire`
3. `campaign/adventures/rats/break_the_ratlantean_powers`

Questlog observes the native Ratlantis entry and durable material/boss milestones. It does not recreate the portal, native crafting progression, Rat Baron encounter, or Flying Dutchrat encounter.

Final marker: `overlord_reign:adventure/ratlantis_campaign_completed`.

## Church of Sin

Assignment scale: compact finite sinister-location arc.

Production wrapper:

1. `campaign/adventures/church_of_sin/find_the_cursed_cathedral`
2. `campaign/adventures/church_of_sin/break_the_dead_congregation`

The installed content exposes the Cursed Cathedral and ordinary undead population, not a Church-specific boss or faction entity. The first stage therefore uses retrospective structure discovery and the second uses live Zombie/Skeleton kills after discovery.

The combat objectives use Minecraft 1.20.1 `EntityPredicate` location matching against the native `church_of_sin:cursedcathedral` structure. Defender kills therefore count only while the killed entity is inside that structure. This preserves the compact expedition boundary without fixed coordinates or an invented boss.

Final marker: `overlord_reign:adventure/church_of_sin_expedition_completed`.

## Oddities / Orchid Shrine

Production wrapper:

1. `campaign/adventures/oddities/find_the_orchid_shrine`
2. `campaign/adventures/oddities/heart_for_the_altar`
3. `campaign/adventures/oddities/cut_down_the_queen`

The installed Orchid Altar consumes an Orchid Heart and drives the native Queen of Orchid summon state. Questlog observes the shrine, Heart acquisition and persistent Queen kill outcome instead of duplicating the ritual.

Final marker: `overlord_reign:adventure/oddities_orchid_queen_defeated`.

## Sequence-break rule

Where a source exposes durable advancements, persistent kill history, or retrospective location history, the wrapper uses those signals so content completed before quest discovery can still be recognized where appropriate.

Inventory observations are used only when they are the narrowest defensible signal and remain persisted after Questlog observes them.

## Validation

The adventure contract and focused validators guard manifest inclusion, prerequisite chains, exact native identifiers, capstone facts and sequence-break behavior. The compact Church of Sin validator additionally fixes both combat predicates to the native cathedral structure ID. New adventure content should follow the same rule: observe the real source mechanic when possible and avoid building a duplicate progression system inside Questlog.
