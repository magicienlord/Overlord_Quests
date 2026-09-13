# OVERLORD QUESTS Current Implementation Status

Status: LIVE ENGINEERING LEDGER

This file records the current repository implementation boundary. It is not a source of new OVERLORD REIGN world or story canon.

For engineering status, this ledger supersedes status statements in older milestone and handoff documents such as `FOUNDATION_B_HANDOFF.md` and early sections of `OVERLORD_ADAPTATION.md`. Those files remain useful historical records of the decisions and tests made at their checkpoints.

## Runtime target

```text
Minecraft Java 1.20.1
Forge 47.4.10
Java 17
Primary branch: gnarl-bootstrap
```

OVERLORD REIGN remains a single-player project. Automatic full-screen speaker and ending presentation targets an unpublished local integrated-server session. Dedicated-server boot remains a compatibility smoke boundary, not a player-facing multiplayer support promise.

## Engine state

Implemented and repository-validated:

- inherited Questlog quest/chapter loading and config override workflow;
- bundled production definitions under `assets/questlog/overlord/definitions/`;
- strict definition validation, wire-size checks, dependency-cycle checks, cache authority, event-bus lifecycle guards and server packet hardening;
- persistent narrative facts and civilization dispositions;
- NPC provider rules, exact provider UUID binding, same-provider turn-in, distance revalidation, provider dialogue and protected quest anchors;
- native/provider compatibility bridges used only where exact mod mechanics require them;
- Minion owner-state bridge through the Overlord Minions public progression API;
- sequence-break history objectives for structures, dimensions, positions, kills, crafting and Ender Dragon defeat;
- optional objectives whose progress persists without blocking completion when marked optional;
- failure-consequence support;
- Gnarl/incorporeal speaker presentation and provider presentation split;
- OVERLORD death-screen compatibility boundary;
- server-authoritative central-ending activation transport, one-time presentation persistence and prior-Dragon sequence-break delivery;
- dedicated static contracts for Illager campaign behavior, ending activation and optional-objective semantics;
- Forge development dedicated-server bootstrap smoke;
- Forge development client bootstrap smoke under a virtual display;
- two-boot real-server narrative-state persistence smoke driven through authenticated localhost RCON, covering fact and disposition write, save, restart, read, cleanup and second clean shutdown.

The current standalone Quest checkpoint has therefore exercised both client and server bootstrap and has proved narrative fact/disposition SavedData round-tripping across a real server restart. These checks remain narrower than the complete OVERLORD REIGN instance.

Manual in-game qualification is still required for player-facing presentation details and several exact full-modpack interaction paths. Standalone static validation, Forge development client/server bootstrap and the narrative-state persistence smoke do not replace those checks.

## Bundled production campaign

The bundled production manifest is populated. Current production definitions include:

### Opening and Tower

- `campaign/opening/a_new_master`
- `campaign/opening/restore_browns`
- `campaign/opening/browns_return`
- `campaign/opening/make_an_impression`
- `campaign/opening/direct_action_reaction`
- `campaign/tower/prepare_the_forge`
- `campaign/tower/forge_prepared_reaction`
- `campaign/expansion/the_reign_takes_shape`

### Civilization campaign and sidequest slices

Dwarves:
- `campaign/civilizations/dwarves/first_contact`

Gnumus:
- `campaign/civilizations/gnumus/first_contact`
- `campaign/civilizations/gnumus/merchant_business`

Goblins:
- `campaign/civilizations/goblins/first_contact`
- `campaign/civilizations/goblins/engineer_workbench`
- `campaign/civilizations/goblins/merchant_business`
- `campaign/civilizations/goblins/tavern_business`

Illagers:
- `campaign/civilizations/illagers/break_the_bastille`

Kobolds:
- `campaign/civilizations/kobolds/first_contact`

Ribbits:
- `campaign/civilizations/ribbits/first_contact`

Sea Dwellers:
- `campaign/civilizations/sea_dwellers/first_contact`
- `campaign/civilizations/sea_dwellers/aquamarine_barter`

Umvuthana:
- `campaign/civilizations/umvuthana/first_contact`
- `campaign/civilizations/umvuthana/suns_blessing`

Development fixtures under `examples/questlog/` remain excluded from the bundled production manifest.

## Source-backed mod integration already present

The repository contains selective integration work for exact mechanics already justified by approved quest design, including:

- Overlord Minions progression ownership and unlock observation;
- Hot Iron Tower forge progression;
- Goblins Tyranny provider/business roles;
- Gnumus provider/business roles;
- Ribbits native profession role matching;
- Kobolds Captain provider identity;
- Dwarven Forge Dwarf/Forger provider identity through the inherited vanilla Villager profession channel;
- Dwarven Forge price-path source support through inherited Villager reputation, MerchantOffer special-price adjustment and separate Hero of the Village discounts, with no authored political price-change trigger bundled yet;
- Realm RPG Sea Dwellers Mermorph provider/barter surfaces;
- Mowzie's Mobs Umvuthi audience and blessing behavior;
- vanilla/structure-backed Illager Bastille campaign observation.

These integrations are intentionally narrow. Native mod progression remains authoritative where possible rather than being duplicated inside Questlog.

## Central ending boundary

The final mechanical resolution remains the Ender Dragon defeat, but the hidden production quest that makes the central campaign ending-ready is not authored yet.

Technical support is implemented:

- the server-authoritative implementation fact is `overlord_reign:campaign/ending_armed`;
- no bundled production quest sets that fact;
- Minecraft's persistent prior-Dragon defeat state is recognized;
- one-time presentation acknowledgement is stored separately from narrative state;
- an already-defeated Dragon can satisfy the presentation boundary without being respawned or replayed;
- the current visible ending is still a neutral development scaffold.

Final narration, Gnarl dialogue, art, music, credits treatment and hidden prerequisite authoring remain unresolved or intentionally spoiler-protected.

## Current blocked authoring boundaries

These are not safe to fill by assumption:

### Villagers

The principal village/provider for the production main arc has not been explicitly selected. Do not invent it.

### Illagers

`break_the_bastille` is implemented, but the exact surviving fearful/cowed provider who carries the follow-up political phase is not defined. Do not invent that NPC role.

### Piglins

Canon establishes one designated Nether Village and one protected marked Piglin Brute Chieftain, while preserving ordinary native hostility before political resolution. The exact initial audience mechanism that lets the hostile Chieftain participate in a provider flow is unresolved. Do not author a normal peaceful provider interaction that contradicts this boundary.

### Red, Green and Blue Minion recovery

The four-slot Brown/Red/Green/Blue progression ownership, summon gating and Questlog unlock bridge exist, but the diegetic recovery routes for Red, Green and Blue remain unresolved. Do not invent biome, boss, item or unrelated-mod mappings.

### Final central quest

The technical ending transport exists, but the actual hidden prerequisite chain and production setter for `overlord_reign:campaign/ending_armed` are not defined in the visible implementation authority. Do not bundle a speculative final quest.

## Validation boundary

A green repository workflow means the checked contracts compiled and/or passed their stated standalone static/runtime boundary. It does not by itself mean that every interaction has been reproduced in the full OVERLORD REIGN modpack.

The current standalone validation surface includes:

- exact Forge build and assembled-JAR validation;
- dedicated Forge server bootstrap;
- Forge client bootstrap under a virtual display;
- real two-boot narrative fact/disposition persistence through server commands and SavedData;
- focused static contracts for provider/civilization integrations, ending activation, optional objectives and sequence-break behavior.

Manual full-instance validation still matters particularly for:

- Gnarl and provider UI composition;
- Ribbits reflection-backed native profession matching;
- Umvuthi native hostility/misbehaviour transitions;
- full Brown -> Red -> Green -> Blue Minion progression and reload reconciliation;
- central ending normal-victory and prior-Dragon presentation paths;
- Dwarven reputation-driven price behavior in the complete target instance before an authored political branch relies on it;
- local civilization disposition consequences once production writers are authored.

## Maintenance rule

When a blocked authoring boundary is explicitly decided, update this file and the appropriate focused integration/test document in the same implementation pass. Do not silently promote a proposal or technical possibility into production campaign canon.
