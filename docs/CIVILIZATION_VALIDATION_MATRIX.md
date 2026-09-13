# OVERLORD QUESTS Civilization Validation Matrix

Status: TECHNICAL / MANUAL FULL-INSTANCE VALIDATION INDEX

This file is an engineering index for the consolidated civilization validation artifact. It does not establish new OVERLORD REIGN story canon and does not convert an unresolved provider, consequence, political state, or reward into production content.

## Purpose

The standalone repository now validates compilation, packaged production definitions, provider contracts, narrative-state persistence, client/server bootstrap, quest-anchor protection, ending transport, optional objectives, and other focused contracts. Several civilization integrations still depend on behavior owned by other installed mods and therefore require a complete OVERLORD REIGN instance for player-facing qualification.

The `Civilization Validation Kit` workflow packages the exact Quest JAR from a successful `Build Forge 1.20.1` run with the protocols in this matrix. The workflow records the source commit and build run identity and verifies the package checksums before upload.

A successful package build means the kit is internally consistent. It does not mean every protocol has been manually completed in the full modpack.

## Production civilization entry protocols

| Civilization | Production entry | Protocol | Principal full-instance concern |
| --- | --- | --- | --- |
| Dwarves | `campaign/civilizations/dwarves/first_contact` | `DWARVEN_CIVILIZATION_TEST_PROTOCOL.md` | Dwarven Forge provider identity, native trade preservation, and the non-canon reputation/price probe |
| Gnumus | `campaign/civilizations/gnumus/first_contact` | `GNUMU_CIVILIZATION_TEST_PROTOCOL.md` | Elder Shaman authored-role scoping and native Gnumu interaction preservation |
| Goblins | `campaign/civilizations/goblins/first_contact` | `GOBLIN_CIVILIZATION_TEST_PROTOCOL.md` | Principal-camp leader scoping and native Goblins Tyranny interaction preservation |
| Illagers | `campaign/civilizations/illagers/break_the_bastille` | `ILLAGER_CIVILIZATION_TEST_PROTOCOL.md` | Exact tagged Bastille command target and player-attributed historical kill behavior |
| Kobolds | `campaign/civilizations/kobolds/first_contact` | `KOBOLD_CIVILIZATION_TEST_PROTOCOL.md` | Marked Captain provider behavior while preserving native Captain interaction/trade |
| Ribbits | `campaign/civilizations/ribbits/first_contact` | `RIBBIT_CIVILIZATION_TEST_PROTOCOL.md` | Reflection-backed Gardener profession matching against Ribbits 3.0.5 |
| Sea Dwellers | `campaign/civilizations/sea_dwellers/first_contact` | `SEA_DWELLER_CIVILIZATION_TEST_PROTOCOL.md` | Mermorph tag matching, authored Sea Elder role scoping, and native barter preservation |
| Umvuthana | `campaign/civilizations/umvuthana/first_contact` | `UMVUTHANA_CIVILIZATION_TEST_PROTOCOL.md` | Exact designated Umvuthi audience behavior and native hostility/misbehaviour transitions |

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

## Intentionally absent civilization entries

### Villagers

Production main-entry validation is not packaged because the principal village/provider has not been explicitly selected. Existing provider architecture is not permission to invent that anchor.

### Piglins

Production main-entry validation is not packaged because the designated Piglin Brute Chieftain remains natively hostile and the initial political audience transition is unresolved. A generic peaceful provider interaction would contradict the current authoring boundary.

## Other unresolved campaign boundaries

This civilization kit does not resolve:

- Red, Green, or Blue Minion diegetic recovery routes;
- the surviving Illager provider for the post-Bastille political phase;
- Dwarven political disposition consequences or any authored reputation/price reward;
- final central quest prerequisites or the production setter for `overlord_reign:campaign/ending_armed`;
- final ending narration, art, audio, credits treatment, or other presentation decisions.

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
