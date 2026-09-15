# OVERLORD REIGN Narrative Facts

Status: TECHNICAL IMPLEMENTATION OF APPROVED QUEST ARCHITECTURE

## Authority and model

`magicienlord/Overlord_Lore_and_Canon` is the read-only authority for the narrative architecture. This file is the implementation-facing registry for durable production fact IDs used by bundled OVERLORD QUESTS content. It does not create setting canon independently.

Narrative facts are world-scoped `ResourceLocation` keys stored by `OverlordNarrativeState`. Production facts are monotonic historical/campaign statements: absent means the statement has not been recorded; present means it has become true. A fact is not a civilization disposition and is not automatically the owner of cross-mod capability state.

Use a durable fact only when later content materially benefits from a reusable historical/campaign truth. Generic progress, presentation state and owner-state already represented completely by another mod do not need duplicate facts.

Production references are validated by `tools/validate_narrative_fact_documentation.py`.

## Opening, Minions and early reign

| Fact | Writer | Meaning / boundary |
| --- | --- | --- |
| `overlord_reign:minions/brown_recovered` | `campaign/opening/browns_return` | Historical confirmation that Brown recovery occurred. The Master's Staff remains the Brown bootstrap owner. |
| `overlord_reign:minions/red_recovered` | `campaign/expansion/reds_return` | Historical confirmation after the authored Red recovery and Minion-owner-state confirmation. |
| `overlord_reign:minions/green_recovered` | `campaign/expansion/greens_return` | Historical confirmation after the authored Green recovery and Minion-owner-state confirmation. |
| `overlord_reign:minions/blue_recovered` | `campaign/expansion/blues_return` | Historical confirmation after the authored Blue recovery and Minion-owner-state confirmation. |
| `overlord_reign:reign/initial_foundation_established` | `campaign/expansion/the_reign_takes_shape` | The first practical foundation of the semi-open reign is established. It does not imply later Minion, Tower, civilization or optional-system completion. |

The Minion facts remember authored campaign events. `Overlord_Minions` remains authoritative for actual command/unlock state. Red, Green and Blue material objectives are authored recovery anchors, not temporary Hive substitutes and not claims that physical source-game Hives exist in this implementation.

## Dark Tower and personnel

| Fact | Writer | Meaning / boundary |
| --- | --- | --- |
| `overlord_reign:tower/throne_room_operational` | `campaign/tower/claim_the_throne` | The throne room is reclaimed as the operational seat of rule. |
| `overlord_reign:tower/minion_infrastructure_operational` | `campaign/tower/wake_minion_infrastructure` | First practical Minion-support infrastructure is operational. |
| `overlord_reign:tower/forge_prepared` | `campaign/tower/prepare_the_forge` | The Tower forge has reached its authored preparation milestone. |
| `overlord_reign:tower/storage_room_operational` | `campaign/tower/provision_storage_room` | The authored storage-room function is operational. |
| `overlord_reign:tower/armory_operational` | `campaign/tower/establish_armory` | The authored armory function is operational. |
| `overlord_reign:tower/treasury_operational` | `campaign/tower/secure_treasury` | The authored treasury function is operational. |
| `overlord_reign:tower/gates_operational` | `campaign/tower/open_gates_room` | The authored gates-room function is operational. |
| `overlord_reign:tower/alchemy_room_operational` | `campaign/tower/magic/open_alchemy_laboratory` | A Tower alchemical/Ars Elixirum work area is operational; this is not mastery of the discipline. |
| `overlord_reign:tower/theurgy_room_operational` | `campaign/tower/magic/establish_theurgy_laboratory` | A Tower Theurgy work area is operational; this is not mastery of Theurgy. |
| `overlord_reign:tower/gluttony_room_operational` | `campaign/tower/magic/open_gluttony_kitchen` | The Tower Gluttony/kitchen work area is operational; this is not mastery of the discipline. |
| `overlord_reign:tower/spell_study_operational` | `campaign/tower/magic/establish_spell_study` | The Tower spell-study function is operational; this does not complete Iron's progression. |
| `overlord_reign:tower/eidolon_room_operational` | `campaign/tower/magic/prepare_eidolon_chamber` | A Tower Eidolon ritual-study chamber is operational; this is distinct from ritual mastery. |
| `overlord_reign:tower/restoration_complete` | `campaign/tower/restoration_complete` | Formal production Tower Restoration has reached its seven-core-facility capstone. Optional magical facilities, personnel and other campaign content may remain incomplete. |
| `overlord_reign:tower/quaver_band_established` | `campaign/personnel/quaver/first_tower_performance` | Quaver's optional Tower ensemble has reached its first authored performance. |

Biomancy Tower activation is represented directly by completion of `campaign/tower/magic/prepare_biomancy_chamber`, backed by the native Bio-Forge milestone. No separate durable fact is currently needed because later production content does not consume a distinct Biomancy-room state key.

## Magic disciplines

| Fact | Writer | Meaning / boundary |
| --- | --- | --- |
| `overlord_reign:magic/irons/spellcraft_established` | `campaign/magic/irons/master_the_ink` | Active mana spellcraft has reached the selected Iron's progression milestone. |
| `overlord_reign:magic/gluttony/mastery_established` | `campaign/magic/gluttony/banquet_of_power` | Farmer's Spell / Gluttony has reached its selected REIGN mastery milestone. |
| `overlord_reign:magic/theurgy/mastery_established` | `campaign/magic/theurgy/precious_matter` | Theurgy has reached its selected REIGN mastery milestone. |
| `overlord_reign:magic/alchemy/pharmacology_established` | `campaign/magic/alchemy/pharmacologist` | Ars Elixirum pharmacological practice has reached its selected campaign milestone. |
| `overlord_reign:magic/biomancy/discipline_established` | `campaign/magic/biomancy/the_living_laboratory` | Biomancy has matured enough to be treated as an established discipline. |
| `overlord_reign:magic/eidolon/ritual_path_established` | `campaign/magic/eidolon/choose_a_rite` | The selected Eidolon ritual-study path has been established. |

Native mods remain authoritative for their actual mechanics and deeper progression.

## Adventure campaign wrappers

| Fact | Writer | Meaning |
| --- | --- | --- |
| `overlord_reign:adventure/twilight_forest_progression_completed` | `campaign/adventures/twilight/forest_without_barriers` | Selected Twilight Forest progression reached its authored wrapper conclusion. |
| `overlord_reign:adventure/cataclysm_capstone_completed` | `campaign/adventures/cataclysm/cataclysm_conquered` | The Cataclysm wrapper reached its selected capstone. |
| `overlord_reign:adventure/graveyard_expedition_completed` | `campaign/adventures/graveyard/not_one_death` | The Graveyard expedition wrapper concluded. |
| `overlord_reign:adventure/bumblezone_essence_reached` | `campaign/adventures/bumblezone/essence_of_the_hive` | The Bumblezone wrapper reached its selected native Essence milestone. |
| `overlord_reign:adventure/knight_quest_completed` | `campaign/adventures/knight/the_knight_beyond_the_chalice` | The Knight Quest wrapper reached its authored conclusion. |
| `overlord_reign:adventure/lost_castle_expedition_completed` | `campaign/adventures/lost_castle/nothing_left_to_rule` | The Lost Castle expedition wrapper concluded. |
| `overlord_reign:adventure/ratlantis_campaign_completed` | `campaign/adventures/rats/break_the_ratlantean_powers` | The Rats / Ratlantis wrapper reached its authored conclusion. |
| `overlord_reign:adventure/church_of_sin_expedition_completed` | `campaign/adventures/church_of_sin/break_the_dead_congregation` | The Church of Sin / Cursed Cathedral wrapper concluded. |
| `overlord_reign:adventure/oddities_orchid_queen_defeated` | `campaign/adventures/oddities/cut_down_the_queen` | The Oddities / Orchid Shrine wrapper recorded the selected Orchid Queen capstone. |

These facts record REIGN wrapper conclusions and do not transfer ownership of native progression to Questlog.

## Personal and conditional sidequests

| Fact | Writer | Meaning / boundary |
| --- | --- | --- |
| `overlord_reign:personal/pet_resurrection_completed` | `campaign/sidequests/pet_cemetery/return_from_the_grave` | A supported personal pet-resurrection path has completed through native Pet Cemetery mechanics. |
| `overlord_reign:personal/nightwalker/lestat_joined_tower` | `campaign/sidequests/nightwalker/lestat_arrives` | After confirmed NightWalker vampirism, contextual Lestat guidance has entered the Tower-side personal arc and Lestat has voluntarily remained around the Tower as adviser. No physical provider UUID or permanent entity is implied. |
| `overlord_reign:personal/nightwalker/transition_guided` | `campaign/sidequests/nightwalker/choose_the_price` | Lestat's transition guidance reached its capstone after blood practice, Vampire Altar use and at least one real Nycto power purchase. |

Nycto remains authoritative for current vampire state and purchased powers. These facts preserve separate campaign history.

## Civilization facts

Civilization facts are scoped to the deliberately designated local polity/quest context. They do not establish species-wide behavior or a universal state.

| Fact | Writer | Meaning / boundary |
| --- | --- | --- |
| `overlord_reign:civilizations/villagers/contact_established` | `campaign/civilizations/villagers/first_contact` | Formal contact with the authored local Villager polity was established; no disposition or fixed coordinate is implied. |
| `overlord_reign:civilizations/goblins/contact_established` | `campaign/civilizations/goblins/first_contact` | Formal contact with the designated principal Goblin polity was established. |
| `overlord_reign:civilizations/gnumus/contact_established` | `campaign/civilizations/gnumus/first_contact` | Formal contact with the designated principal Gnumu polity was established. |
| `overlord_reign:civilizations/ribbits/contact_established` | `campaign/civilizations/ribbits/first_contact` | Formal contact with the designated principal Ribbit polity was established. |
| `overlord_reign:civilizations/kobolds/contact_established` | `campaign/civilizations/kobolds/first_contact` | Formal contact with the designated principal Kobold polity was established. |
| `overlord_reign:civilizations/sea_dwellers/contact_established` | `campaign/civilizations/sea_dwellers/first_contact` | Formal contact with the designated principal Sea Dweller polity was established. |
| `overlord_reign:civilizations/dwarves/contact_established` | `campaign/civilizations/dwarves/first_contact` | Formal contact with the designated Dwarven successor polity was established. |
| `overlord_reign:civilizations/umvuthana/contact_established` | `campaign/civilizations/umvuthana/first_contact` | The legitimate mask-gated first audience with the designated Umvuthi was completed. |
| `overlord_reign:civilizations/illagers/authority_established` | `campaign/civilizations/illagers/break_the_bastille` | The designated Bastille's local command was broken through its authored commander encounter. This fact does not write disposition, does not globally pacify Illagers, and does not mean the later cowed audience has occurred. |
| `overlord_reign:civilizations/illagers/bastille_cowed` | `campaign/civilizations/illagers/the_bastille_bows` | The designated local Illager polity completed the fearful/cowed audience. This records fear/restraint, not friendship or species-wide surrender. A later explicit non-peaceful disposition may restore local native hostility while this historical fact remains true. |
| `overlord_reign:civilizations/piglins/contact_established` | `campaign/civilizations/piglins/first_contact` | Formal first audience with the designated local Piglin polity was completed. |

## Central End / dimensional Wasteland

| Fact | Writer | Meaning / boundary |
| --- | --- | --- |
| `overlord_reign:campaign/dimensional_wasteland_reached` | `campaign/end/the_wound_beyond_the_world` | The player entered the production campaign's dimensional Wasteland context in `minecraft:the_end`. |
| `overlord_reign:campaign/ending_armed` | `campaign/end/the_wound_beyond_the_world` | The production ending presentation is eligible to resolve when the final mechanical trigger occurs. |
| `overlord_reign:campaign/central_campaign_completed` | `campaign/end/break_the_dragon` | The Ender Dragon was defeated through the production central-ending sequence. The same world remains playable and optional content is not retroactively completed. |

## Development fixtures

Synthetic `questlog:dev_*` facts used by examples validate engine behavior only. They are excluded from the production registry contract.
