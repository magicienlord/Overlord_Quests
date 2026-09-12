# NPC Provider Runtime and Visual Test Protocol

Status: TECHNICAL + VISUAL FOUNDATION VALIDATION / DEVELOPMENT CONTENT ONLY

This protocol validates the NPC sidequest provider scaffold. The fixtures and commands below are not OVERLORD REIGN story canon.

Direct full-modpack runtime evidence has already validated provider discovery, offer presentation, long-dialogue scrolling through its terminal sentinel, explicit acceptance, transition to authored in-progress dialogue, and the shared provider visual baseline. Persistence, same-provider turn-in, disposition/fact rejection, distance closure, and the newer native Villager-profession role bridge remain separate robustness checks until directly evidenced.

## Test scope

The test verifies:

- the provider quest is unavailable as an accepted quest before provider interaction;
- sneak + main-hand interaction opens the provider menu;
- ordinary non-sneaking villager interaction is not consumed by OVERLORD QUESTS;
- selecting an available quest opens its authored offer dialogue before acceptance;
- authored offer dialogue that exceeds the detail-view height remains reachable through bounded scrolling rather than being silently truncated;
- explicit decline returns to the provider list without accepting or persisting a rejection state;
- acceptance binds the exact issuing NPC;
- authored in-progress and ready-to-turn-in dialogue follows server-derived quest state;
- provider binding survives save/quit/reload;
- a second eligible villager cannot satisfy `same_provider` turn-in;
- the original provider can complete the turn-in after the objective is complete;
- a Villager's registered profession can satisfy the provider `role` field without manual `overlord_role` tagging;
- authored disposition/fact state can gate provider eligibility without a numeric reputation system;
- the provider menu closes when the player leaves the interaction boundary;
- command resets clear provider binding rather than leaving stale accepted state;
- the screen uses the same parchment, separator, typography hierarchy, and parchment-style controls as Questlog;
- the screen remains visibly distinct from the incorporeal speaker popup;
- no five-state reaction portrait pane appears for an ordinary in-world provider;
- action controls remain associated with the parchment interface rather than appearing as disconnected vanilla buttons.

## Installation

Use the CI-generated `overlord-quests-provider-test-kit` for the exact commit under test.

1. Back up the test instance.
2. Remove any separate upstream Questlog JAR. Exactly one mod with technical ID `questlog` must remain.
3. Copy the kit's `mods/` contents into the instance `mods/` directory.
4. Copy the kit's `config/` contents into the instance `config/` directory.
5. Launch an unpublished local single-player world with commands available.
6. Do not use Open to LAN for the primary acceptance pass.

The current CI provider kit contains the established provider, disposition, and narrative-fact fixtures. The native-profession fixture may be copied manually from `examples/questlog/quests/overlord_provider_profession_dev.json` until the kit manifest is expanded to include it.

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

## Visual baseline pass

Sneak and interact with Provider A using the main hand.

Before testing quest-state behavior, inspect the presentation itself.

Expected visual result:

1. the provider name is centered in a Questlog-style parchment header;
2. a separator visually divides the header from provider content;
3. available quest rows use parchment-style Questlog controls rather than vanilla gray buttons;
4. list spacing is regular and does not collide with the parchment border;
5. page navigation, when present, belongs visually to the same control family;
6. selecting a quest preserves the same parchment frame and places the quest title beneath the provider heading;
7. Accept/Decline, Turn In/Back, and dialogue Up/Down controls use the same visual family;
8. provider text remains readable at the normal OVERLORD REIGN GUI scale;
9. there is NO right-side incorporeal reaction portrait lane for the villager;
10. the screen clearly feels related to Questlog without pretending the in-world villager is an incorporeal Questlog speaker.

Repeat the provider list and one dialogue view at one adjacent GUI scale or a narrower window configuration. Report clipping, text collisions, undersized parchment, or controls leaving the visible screen.

## Ordinary acceptance and dialogue pass

First interact normally with Provider A without sneaking. OVERLORD QUESTS must not open its provider screen.

Then sneak and interact with Provider A. The provider screen must show `[DEV] NPC Provider Prototype` as available. `[DEV] Disposition-Gated Provider Prototype` must not be present while `questlog:dev_civilization` is unresolved.

Select `[DEV] NPC Provider Prototype`. The first visible offer dialogue must include the fixture's initial `[DEV]` lines, including the line stating that declining must not alter quest state.

Use `Down` or the mouse wheel to move through the authored offer text. The final line `[DEV] DIALOGUE SCROLL END.` must become visible. Use `Up` or the wheel in the opposite direction and confirm earlier lines can be reached again.

Choose `Decline`. The screen must return to the provider list and the quest must still be available. Close and reopen once to confirm decline created no hidden rejection state, cooldown, or binding.

Select the quest again. Dialogue must reopen at the beginning. Choose `Accept`. The screen should refresh from the server and show the quest as in progress. Selecting the in-progress entry must show the fixture's `[DEV]` in-progress response, with controls still responsive after the refresh.

## Reset and persistence spot-check

Before completing the accepted quest, run:

```text
/questlog progress reset questlog:overlord_provider_dev
```

The quest must become available again rather than remaining bound.

Accept it again, save/quit, reload the world, and reopen Provider A. The quest must remain bound and in progress.

This persistence check is required before provider-binding reload behavior is promoted from source/CI coverage to direct runtime validation.

## Same-provider turn-in spot-check

Give the objective item:

```text
/give @s minecraft:debug_stick 1
```

Provider B must not present the accepted quest as ready for `same_provider` turn-in.

Provider A must present it as ready. Select it, confirm the authored ready-to-turn-in response, then choose `Turn In`. The completed quest should disappear from actionable provider entries after the authoritative refresh.

## Native Villager profession-role spot-check

This check validates the provider bridge required by the REIGN rule that Villager professions may be sidequest-provider roles.

Copy `overlord_provider_profession_dev.json` into `config/questlog/quests/` if the current provider kit does not already contain it, then reload quest definitions or restart the test world.

Spawn a Farmer Villager with no `overlord_role` tag:

```text
/summon minecraft:villager ~3 ~ ~ {VillagerData:{profession:"minecraft:farmer",level:2,type:"minecraft:plains"},NoAI:1b,Invulnerable:1b,PersistenceRequired:1b,CustomName:'{"text":"[DEV] Native Farmer"}'}
```

Sneak and interact with that Villager. `[DEV] Villager Profession Provider` must be offered even though the entity has no `overlord_role:minecraft:farmer` scoreboard tag.

Then use a non-Farmer Villager with no explicit role tag. The profession-gated fixture must not appear on that provider.

Acceptance:

- `role: minecraft:farmer` resolves through the registered Villager profession;
- no manual role tagging is required for the native profession path;
- the entity-type selector still applies;
- profession matching does not make unrelated Villagers eligible.

Modded professions should later be spot-checked with one installed profession provider once a production sidequest actually depends on that modded profession. The engine lookup is registry-driven and does not hard-code vanilla professions.

## Disposition-gating spot-check

Run:

```text
/questlog narrative disposition clear questlog:dev_civilization
/questlog progress reset questlog:overlord_provider_disposition_dev
```

The disposition-gated prototype must be absent.

Then run:

```text
/questlog narrative disposition set questlog:dev_civilization questlog:dev_open
```

The gated prototype must appear on the next provider interaction.

The IDs in this fixture are synthetic test identifiers only and establish no civilization canon.

## Distance/lifecycle spot-check

Open Provider A's menu and move more than 8 blocks away. The menu must close automatically rather than remaining attached to a stale remote entity interaction.

## Evidence to retain

Retain:

- `latest.log` from the pass;
- one screenshot of the provider quest LIST at normal GUI scale;
- one screenshot of the selected offer with Accept/Decline visible;
- one screenshot with `[DEV] DIALOGUE SCROLL END.` visible;
- one screenshot of the native Farmer profession fixture being offered without an explicit role tag;
- one screenshot at the adjacent/narrower GUI configuration if its composition differs materially;
- any visual clipping, text collision, packet rejection, or quest-load error.

## Acceptance criteria

The provider visual foundation itself is already accepted. This protocol now serves as the remaining runtime robustness procedure for provider persistence, same-provider turn-in, narrative gating, distance lifecycle, and native Villager profession-role matching.

A failure in one of those robustness checks blocks promotion of that specific behavior to runtime-validated status, but does not by itself reopen the accepted parchment visual foundation.
