# Adventure Questline Wrapper Contract

Status: PRODUCTION QUEST CONTENT / NATIVE-PROGRESSION WRAPPERS

The four mandatory adventure integrations implemented here are Twilight Forest, L_Ender's Cataclysm, The Graveyard, and The Bumblezone.

They are full REIGN questlines in the sense that each has an authored beginning, progression, and capstone. They are not replacement progression systems. Questlog observes durable native advancements retrospectively and supplies only REIGN framing and sparse completion facts.

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

## Sequence breaks

All four integrations use `questlog:advancement`. It polls the player's real advancement completion state and is retrospective. Legitimate native progress made before a REIGN wrapper activates therefore counts automatically.
