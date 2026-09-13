# Adventure Questline Wrapper Contract

Status: PRODUCTION QUEST CONTENT / NATIVE-PROGRESSION WRAPPERS

The five mandatory adventure integrations implemented here are Twilight Forest, L_Ender's Cataclysm, The Graveyard, The Bumblezone, and Knight Quest.

They are full REIGN questlines in the sense that each has an authored beginning, progression, and capstone. They are not replacement progression systems. Questlog observes durable native milestones where available and supplies REIGN framing plus sparse completion facts.

## Twilight Forest

The wrapper follows the existing Forest progression sequence from Naga and Lich through Labyrinth/Hydra, Knight/Ur-Ghast/Yeti/Glacier, then Troll/Merge/Trophy Pedestal/native progression end.

Questlog does not replace Twilight Forest biome locking, trophies, boss kills, or access logic.

Final marker: `overlord_reign:adventure/twilight_forest_progression_completed`.

## L_Ender's Cataclysm

The wrapper first observes representative structure discovery, then several defining boss milestones, then closes on Cataclysm's own `cataclysm:kill_all_bosses` advancement.

Using the native all-bosses capstone deliberately avoids copying the entire boss roster into a second Questlog checklist while still giving the assigned full questline a real end state.

Final marker: `overlord_reign:adventure/cataclysm_capstone_completed`.

## The Graveyard

The wrapper covers exploration, the native Lich sequence, and later Wraith/Coffin milestones. Its text explicitly preserves REIGN's distinctions among death, ghosts, persistent souls, undead bodies, resurrection, Lifeforce, and other occult mechanisms.

The Graveyard's proximity of these themes is not treated as proof that they are one substance or one metaphysical process.

Final marker: `overlord_reign:adventure/graveyard_expedition_completed`.

## The Bumblezone

The installed pack uses Bumblezone 7.13.4 for Minecraft 1.20.1. The upstream `1.20.x-Arch` source tree confirms advancement resources directly under the `the_bumblezone` namespace, including `root.json`, Beehemoth milestones, and essence milestones. The production wrapper therefore uses:

- `the_bumblezone:root`
- `the_bumblezone:beehemoth/tamed_beehemoth`
- `the_bumblezone:beehemoth/queen_beehemoth`
- `the_bumblezone:essence/bee_essence_infusion`

The older duplicated `the_bumblezone:the_bumblezone/...` form seen in some historical third-party quest packs is not used.

The wrapper intentionally samples major native progression rather than requiring all 135 non-recipe advancements.

Final marker: `overlord_reign:adventure/bumblezone_essence_reached`.

## Knight Quest

Knight Quest exposes a large recipe-advancement surface but only one useful non-recipe entry advancement: `knightquest:knightquest`, granted when Small Essence is obtained. REIGN therefore does not pretend the recipe book is a native campaign.

The authored progression follows mechanics verified in Knight Quest and Knight-Lib:

1. acquire Small Essence and trigger the native Knight Quest root;
2. secure `knightlib:great_chalice` and `knightlib:great_essence` for the Chalice progression;
3. obtain `knightquest:radiant_essence`, whose native recipe uses Small Essence and a Nether Star;
4. fill the Great Chalice through Knight-Lib's native essence interaction, use Radiant Essence on the full vessel to summon `knightquest:netherman`, and defeat it.

Questlog does not simulate Chalice charge state. The final `questlog:entity_kill_history` objective records the Netherman kill retrospectively, so the capstone itself proves the player reached the native summoning outcome without requiring a fragile duplicate state machine.

Final marker: `overlord_reign:adventure/knight_quest_completed`.

## Sequence breaks

Twilight Forest, Cataclysm, Graveyard, and Bumblezone primarily use `questlog:advancement`, which polls the player's real advancement completion state retrospectively.

Knight Quest uses its persistent native root advancement for entry and the persistent Questlog kill-history bridge for the Netherman capstone. The intermediate Great Chalice, Great Essence, and Radiant Essence objectives are inventory observations; their completion is persisted by Questlog once observed.
