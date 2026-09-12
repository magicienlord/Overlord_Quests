# OVERLORD QUESTS Central Ending Presentation

Status: TECHNICAL PREVIEW SCAFFOLD / PRODUCTION ACTIVATION NOT YET AUTHORED

## Authority

The REIGN runtime authority establishes the following campaign rules:

- the Ender Dragon defeat remains the underlying Minecraft final-boss event;
- the ordinary Minecraft ending presentation should ultimately be replaced or modified by an OVERLORD REIGN-specific ending presentation;
- the player returns to the same persistent world afterward;
- unresolved civilization arcs, sidequests, native progression, Tower work, exploration, and sandbox play remain available;
- detailed ending narrative material remains behind the spoiler firewall.

This implementation does not add new ending lore.

## Current technical boundary

The repository now contains a deliberately neutral development scaffold that proves the presentation handoff without activating it in normal gameplay.

`OverlordEndingScreens` inspects a newly opened vanilla `WinScreen`. It will replace that screen only when all of these development conditions are true:

1. the Questlog ending-screen feature is enabled;
2. `developmentPreview` is explicitly enabled in the client configuration;
3. the session is an unpublished local single-player integrated server;
4. the vanilla `WinScreen` is the End poem form, not the manually opened credits form.

`developmentPreview` defaults to `false`. Therefore normal Ender Dragon victory remains on Minecraft's vanilla presentation until the hidden production campaign has an explicit, source-authorized activation condition.

## Vanilla continuation ownership

Minecraft 1.20.1 `WinScreen` stores two private values that matter to this integration:

- `poem`, distinguishing the End poem from ordinary credits;
- `onFinished`, the callback that completes Minecraft's normal post-ending transition.

`WinScreenAccessor` exposes only those two fields through Mixin. The replacement screen receives the original `onFinished` runnable and invokes it at most once when the player continues.

The scaffold does not teleport the player, respawn the player, construct a new world, recreate the End transition, or bypass Minecraft's normal completion callback.

## Current presentation

The screen is intentionally not a final visual design. In preview mode it renders only:

- a black background;
- the project title;
- an explicit development-scaffold status line;
- a delayed Continue control.

No final narration, Gnarl dialogue, branch outcome summary, credits treatment, artwork, music, Cataclysm exposition, or campaign reveal is encoded here.

## Production gate still required

Dragon defeat by itself must not silently become proof that the hidden central campaign has reached its authored ending state. Exploration and sequence breaking are valid project concerns, so production activation requires an explicit campaign gate once the final campaign dependency is authored.

Until that gate exists, the technical scaffold remains preview-only.

The production gate must satisfy all of the following:

- be derived from authoritative campaign state rather than a client preference;
- survive save and reload through the campaign's normal persistence model;
- tolerate the Dragon having been defeated before the final campaign objective if the hidden campaign design permits such sequence breaking;
- not alter or reset unrelated quest, civilization, Tower, Minion, or native-mod progression;
- preserve the vanilla post-ending callback so the same world remains playable.

## Configuration

The client configuration category `ending_screen` contains:

- `enabled`, master presentation switch, default `true`;
- `developmentPreview`, explicit preview bypass, default `false`;
- `minimumDisplayTicks`, minimum preview dwell time, default `80`;
- `allowSkip`, permits Escape, Enter, or Space to continue after the minimum dwell time, default `true`.

`developmentPreview` is a development control only. It is not a campaign fact and must never be used as the production ending condition.

## Acceptance boundary

Current implementation can be considered mechanically validated after a disposable single-player End victory confirms that, with development preview enabled:

1. the End poem is replaced by the neutral scaffold;
2. ordinary menu credits remain vanilla;
3. the Continue action invokes the original vanilla completion path exactly once;
4. the player returns to the same persistent world;
5. the replacement does not activate in LAN-published or dedicated multiplayer contexts;
6. with development preview disabled, normal End victory remains untouched.

Final OVERLORD REIGN ending presentation and production activation remain PLANNED.
