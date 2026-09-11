# NPC Provider Runtime Test Protocol

Status: TECHNICAL VALIDATION / DEVELOPMENT CONTENT ONLY

This protocol validates the temporary NPC sidequest provider scaffold. The fixtures and commands below are not OVERLORD REIGN story canon.

## Test scope

The test verifies:

- the provider quest is unavailable as an accepted quest before provider interaction;
- sneak + main-hand interaction opens the temporary provider menu;
- ordinary non-sneaking villager interaction is not consumed by OVERLORD QUESTS;
- acceptance binds the exact issuing NPC;
- the provider menu refreshes without remaining action-locked;
- provider binding survives save/quit/reload;
- a second eligible villager cannot satisfy `same_provider` turn-in;
- the original provider can complete the turn-in after the objective is complete;
- authored disposition state can gate provider eligibility without a numeric reputation system;
- clearing a disposition removes eligibility for an unaccepted gated sidequest;
- the provider menu closes when the player leaves the interaction boundary;
- completed provider quests disappear from that provider's actionable menu;
- command resets clear provider binding rather than leaving a stale accepted state;
- no story quest, canonical civilization state, or production reward is introduced by the fixtures.

## Installation

Use the CI-generated `overlord-quests-provider-test-kit` for the exact commit under test.

1. Back up the test instance.
2. Remove any separate upstream Questlog JAR. Exactly one mod with technical ID `questlog` must remain.
3. Copy the kit's `mods/` contents into the instance `mods/` directory.
4. Copy the kit's `config/` contents into the instance `config/` directory.
5. Launch an unpublished local single-player world with commands available.
6. Do not use Open to LAN for the primary acceptance pass.

## Deterministic setup

Run:

```text
/clear @s minecraft:debug_stick
/questlog narrative disposition clear questlog:dev_civilization
/questlog reset_all_progress_and_reload
/kill @e[type=minecraft:villager,tag=oq_provider_dev]
/summon minecraft:villager ~2 ~ ~ {Tags:["oq_provider_dev","oq_provider_a"],NoAI:1b,Invulnerable:1b,PersistenceRequired:1b,CustomName:'{"text":"[DEV] Provider A"}'}
/summon minecraft:villager ~-2 ~ ~ {Tags:["oq_provider_dev","oq_provider_b"],NoAI:1b,Invulnerable:1b,PersistenceRequired:1b,CustomName:'{"text":"[DEV] Provider B"}'}
```

Keep both villagers within easy reach for the initial acceptance test.

## Ordinary acceptance pass

First interact normally with Provider A without sneaking. OVERLORD QUESTS must not open its provider screen. This confirms the temporary scaffold is not stealing the ordinary non-sneaking entity interaction path.

Then sneak and interact with Provider A using the main hand. The provider screen must open and show `[DEV] NPC Provider Prototype` as available for acceptance. `[DEV] Disposition-Gated Provider Prototype` must not be present while `questlog:dev_civilization` is unresolved.

Select the ordinary provider prototype's accept action. The same screen should refresh from the server and show the quest as in progress. The controls must not remain permanently disabled after the response.

Close the screen and sneak-interact with Provider A again. The quest must still show as in progress.

## Reset contract pass

Before completing the accepted quest, run:

```text
/questlog progress reset questlog:overlord_provider_dev
```

Sneak-interact with Provider A again. The quest must be available for acceptance again, not remain bound to the previous provider. This validates that administrative reset delegates to the provider-aware quest reset contract.

Accept the quest again before proceeding with the persistence pass.

## Persistence pass

After accepting but before obtaining the debug stick:

1. save and quit to the title screen;
2. reload the same world;
3. locate Provider A;
4. sneak-interact again.

The quest must remain bound and in progress. Provider A's UUID-backed identity must survive the save/load cycle.

If the villager is missing after reload, treat that as an invalid test environment rather than a provider-binding failure. The setup deliberately uses `PersistenceRequired:1b` to prevent ordinary despawn.

## Same-provider turn-in pass

Give the objective item:

```text
/give @s minecraft:debug_stick 1
```

Sneak-interact with Provider B first. Provider B must not present the accepted quest as ready for turn-in. The development definition uses `lock_to_provider=true` and `turn_in=same_provider`.

Sneak-interact with Provider A. The quest must now appear as ready for turn-in.

Select turn in. The server should refresh the provider screen. The completed quest should no longer appear as an actionable provider entry.

## Disposition-gating pass

Clear the synthetic development state and reset quest progress:

```text
/questlog narrative disposition clear questlog:dev_civilization
/questlog progress reset questlog:overlord_provider_disposition_dev
/questlog narrative disposition get questlog:dev_civilization
```

The `get` command must report `questlog:unresolved`. Sneak-interact with either development villager. `[DEV] Disposition-Gated Provider Prototype` must not be offered.

Set the synthetic development state:

```text
/questlog narrative disposition set questlog:dev_civilization questlog:dev_open
/questlog narrative disposition get questlog:dev_civilization
```

The `get` command must report `questlog:dev_open`. On the next sneak-interaction, `[DEV] Disposition-Gated Provider Prototype` must appear as available.

Do not accept it yet. Clear the state again:

```text
/questlog narrative disposition clear questlog:dev_civilization
```

On the next provider interaction, the unaccepted disposition-gated quest must disappear. This confirms provider eligibility is evaluated from the authoritative world-scoped disposition fact rather than cached as a client-side reputation value.

Finally, set the state again, accept the gated quest, and obtain the debug stick. Because this fixture uses `turn_in=none`, provider turn-in must not be required for final quest completion.

The IDs `questlog:dev_civilization` and `questlog:dev_open` are synthetic test identifiers only. They establish no OVERLORD REIGN civilization or disposition canon.

## Distance/lifecycle pass

Reset the ordinary fixture and accept it again if necessary. Open Provider A's menu, then move more than 8 blocks away while the screen remains open. The menu must close automatically.

Repeat with the provider killed or otherwise removed in a disposable test world if desired. The menu must not remain active against a stale entity identity.

## Evidence to retain

Retain:

- `latest.log` from the complete pass;
- one screenshot of the available state;
- one screenshot of the in-progress state;
- one screenshot of the ready-to-turn-in state;
- one screenshot showing the disposition-gated quest absent while unresolved and present while `questlog:dev_open` is set;
- any unexpected packet rejection or quest load error from the log.

## Acceptance criteria

The provider scaffold passes this milestone only when the server-authoritative accept, reset, persistence, same-provider turn-in, disposition gating, refresh, ordinary-interaction preservation, and distance lifecycle behaviors all work in the actual Forge 1.20.1 instance.

Visual styling of this temporary provider screen is not an acceptance target. Final NPC quest-giver presentation remains a separate DESIGN pass.
