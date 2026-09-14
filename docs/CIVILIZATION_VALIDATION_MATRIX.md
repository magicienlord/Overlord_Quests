# OVERLORD QUESTS Civilization Validation Matrix

Status: TECHNICAL / MANUAL FULL-INSTANCE VALIDATION INDEX

This file is an engineering index for the consolidated civilization validation artifact. It does not establish new OVERLORD REIGN story canon or promote a test setup into a final world decision.

## Purpose

Repository CI validates compilation, packaged production definitions, provider contracts, narrative-state persistence, client/server bootstrap, quest-anchor protection, ending transport, optional objectives and focused civilization contracts. Several civilization integrations still depend on behavior owned by other installed mods and therefore require the complete OVERLORD REIGN instance for final player-facing qualification.

The `Civilization Validation Kit` workflow packages the exact Quest JAR from a successful same-commit `Build Forge 1.20.1` run with the protocols in this matrix. A successful package build means the kit is internally consistent; it does not mean every protocol has been manually completed in the full modpack.

## Production civilization entry protocols

Generalized civilization roster: 10.

Production main-entry coverage: 10/10.

| Civilization | Production entry | Protocol | Principal full-instance concern |
| --- | --- | --- | --- |
| Villagers | `campaign/civilizations/villagers/first_contact` | `VILLAGER_CIVILIZATION_TEST_PROTOCOL.md` | Exact local representative at the deliberately authored world-fit human successor/remnant settlement; no universal polity or profession leakage |
| Illagers | `campaign/civilizations/illagers/break_the_bastille` + `campaign/civilizations/illagers/the_bastille_bows` | `ILLAGER_CIVILIZATION_TEST_PROTOCOL.md` | Exact tagged commander history, protected cowed intermediary, local restraint, disposition precedence and unrelated-Illager locality |
| Dwarves | `campaign/civilizations/dwarves/first_contact` | `DWARVEN_CIVILIZATION_TEST_PROTOCOL.md` | Dwarven Forge provider identity, native trade preservation and the non-canon reputation/price probe |
| Gnumus | `campaign/civilizations/gnumus/first_contact` | `GNUMU_CIVILIZATION_TEST_PROTOCOL.md` | Elder Shaman authored-role scoping and native Gnumu interaction preservation |
| Goblins | `campaign/civilizations/goblins/first_contact` | `GOBLIN_CIVILIZATION_TEST_PROTOCOL.md` | Principal-camp leader scoping and native Goblins Tyranny interaction preservation |
| Kobolds | `campaign/civilizations/kobolds/first_contact` | `KOBOLD_CIVILIZATION_TEST_PROTOCOL.md` | Marked Captain provider behavior while preserving native Captain interaction/trade |
| Ribbits | `campaign/civilizations/ribbits/first_contact` | `RIBBIT_CIVILIZATION_TEST_PROTOCOL.md` | Reflection-backed Gardener profession matching against Ribbits 3.0.5 |
| Sea Dwellers | `campaign/civilizations/sea_dwellers/first_contact` | `SEA_DWELLER_CIVILIZATION_TEST_PROTOCOL.md` | Mermorph tag matching, authored Sea Elder role scoping and native barter preservation |
| Piglins | `campaign/civilizations/piglins/first_contact` | `PIGLIN_CIVILIZATION_TEST_PROTOCOL.md` | Exact protected Brute Chieftain, gold-gated local audience, native hostility elsewhere and no automatic disposition |
| Umvuthana | `campaign/civilizations/umvuthana/first_contact` | `UMVUTHANA_CIVILIZATION_TEST_PROTOCOL.md` | Exact designated Umvuthi audience behavior and native hostility/misbehaviour transitions |

Demons remain outside the generalized civilization disposition framework.

## Production sidequest protocols

| Civilization | Production quest | Protocol | Native owner signal |
| --- | --- | --- | --- |
| Gnumus | `campaign/civilizations/gnumus/merchant_business` | `GNUMU_MERCHANT_SIDEQUEST_TEST_PROTOCOL.md` | `gnumus:business_approach` |
| Goblins | `campaign/civilizations/goblins/engineer_workbench` | `GOBLIN_ENGINEER_SIDEQUEST_TEST_PROTOCOL.md` | `goblins_tyranny:engineer_success` |
| Goblins | `campaign/civilizations/goblins/merchant_business` | `GOBLIN_MERCHANT_SIDEQUEST_TEST_PROTOCOL.md` | `goblins_tyranny:merchant_success` |
| Goblins | `campaign/civilizations/goblins/tavern_business` | `GOBLIN_TAVERN_SIDEQUEST_TEST_PROTOCOL.md` | `goblins_tyranny:liquor_success` |
| Sea Dwellers | `campaign/civilizations/sea_dwellers/aquamarine_barter` | `SEA_DWELLER_BARTER_SIDEQUEST_TEST_PROTOCOL.md` | `seadwellers:adv_barter_aquamarine` |
| Umvuthana | `campaign/civilizations/umvuthana/suns_blessing` | `UMVUTHI_BLESSING_SIDEQUEST_TEST_PROTOCOL.md` | `mowziesmobs:suns_blessing` |

These signals remain owned by their native mods. Questlog observes them and must not replace their native mechanics.

## Shared supporting protocols

The kit also carries:

- `NPC_PROVIDER_TEST_PROTOCOL.md` for provider binding, same-provider turn-in, distance and persistence semantics;
- `QUEST_ANCHOR_PROTECTION_TEST_PROTOCOL.md` for protected-anchor persistence, gameplay-damage rejection and explicit tag-release behavior;
- `NARRATIVE_FACTS.md` for fact semantics and ownership;
- `CIVILIZATION_DISPOSITIONS.md` for generalized disposition semantics;
- `CIVILIZATION_SIDEQUEST_STATUS.md` for source-backed sidequest boundaries;
- `CURRENT_IMPLEMENTATION_STATUS.md` for the current engineering boundary.

## Implemented boundaries that remain manual-runtime qualifications

The following are implemented production content, not unresolved authoring gaps, but still require complete-instance manual validation where their behavior depends on the target world or another mod:

- Villager final-world anchor selection and visual historical/biome/terrain fit;
- Illager designated Bastille commander/intermediary setup and local AI restraint under the complete combat stack;
- Piglin Chieftain gold-audience behavior under the complete Nether/combat stack;
- native civilization provider interactions and advancements listed above;
- provider presentation, exact anchor persistence and save/reload behavior in the complete instance.

## External technical deferral outside this kit

The personal Overlord Depths / Fathoms Historian sidequest remains deferred while the external backport has failing exact-head validation. That is an external technical integration boundary, not an unresolved civilization design question and not a reason to reduce civilization coverage below 10/10.

The NightWalker / Lestat transition arc is implemented separately and is validated by its own closure/static/build surfaces rather than by this civilization kit.

## Qualification rule

For each protocol, record:

1. Quest JAR SHA-256 from the kit;
2. exact tested native-mod JAR/version where the protocol depends on another mod;
3. world setup and synthetic tags/facts used;
4. expected result;
5. observed result;
6. whether the native interaction still functions outside the Questlog gesture;
7. persistence/reload result where required;
8. any log exception, mixin conflict, missing registry entry, or behavioral divergence.

Only promote a source-backed integration to full-instance runtime-qualified after its complete protocol passes against the target OVERLORD REIGN instance. Repository CI success alone remains a narrower engineering result.
