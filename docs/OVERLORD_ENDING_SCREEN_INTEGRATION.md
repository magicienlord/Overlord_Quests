# OVERLORD QUESTS Central Ending Presentation

Status: TECHNICAL ACTIVATION INFRASTRUCTURE IMPLEMENTED / FINAL PRESENTATION AND CENTRAL QUEST NOT YET AUTHORED

## Authority

The REIGN runtime authority establishes the following campaign rules:

- the Ender Dragon defeat remains the underlying Minecraft final-boss event;
- the ordinary Minecraft ending presentation should ultimately be replaced or modified by an OVERLORD REIGN-specific ending presentation;
- legitimate prior Dragon defeat must be recognized rather than undone;
- the player returns to the same persistent world afterward;
- unresolved civilization arcs, sidequests, native progression, Tower work, exploration, and sandbox play remain available;
- detailed ending narrative material remains behind the spoiler firewall.

This implementation does not add new ending lore.

## Production activation boundary

The ending surface now has a fail-closed server-authoritative arm signal:

`overlord_reign:campaign/ending_armed`

This is an implementation marker, not a substitute for the hidden final campaign quest. No bundled production quest currently sets it.

A future authoritative central-campaign definition may set the fact only when its real authored ending gate has been reached. Dragon defeat alone never creates the fact and therefore cannot activate the REIGN ending by itself.

The client receives only a derived boolean projection of that server state. Client configuration cannot arm the production ending.

## One-time presentation persistence

`OverlordEndingPresentationState` stores a separate world-scoped `presented` latch in `overlord_quests_ending` SavedData.

This latch is presentation state, not narrative canon. It prevents the ending screen from replaying on every login or every later credits transition after the player has completed it once.

The client acknowledges completion through a dedicated server-bound packet. The server accepts that acknowledgement only when:

- the ending-arm fact is present; and
- Minecraft's End fight reports that the Dragon has previously been defeated.

A forged or premature acknowledgement therefore cannot suppress a future legitimate ending presentation.

Clearing the arm fact is an administrative/testing action. On a later login while the fact is absent, the presentation latch is reset so the technical protocol can be repeated. Production quest rewards remain monotonic and do not clear the arm fact.

## Normal Dragon-victory path

If the ending is armed before the final Dragon victory, `OverlordEndingScreens` may replace a newly opened vanilla `WinScreen` only when all of these conditions are true:

1. the ending-screen feature is enabled;
2. the server has projected the ending as armed and not yet presented;
3. the session is an unpublished local single-player integrated server;
4. the vanilla `WinScreen` is the End poem form, not manually opened credits.

`WinScreenAccessor` exposes only the private `poem` discriminator and the original `onFinished` callback. The replacement invokes that callback at most once after acknowledging presentation, preserving Minecraft's normal post-End transition.

## Prior-Dragon sequence-break path

A WinScreen-only implementation is insufficient when the Dragon was defeated before the central campaign becomes ending-ready because there may be no future first-victory poem to intercept.

When the arm fact becomes true or the player logs in, the server also checks Minecraft's persistent `EndDragonFight.hasPreviouslyKilledDragon()` state.

If the Dragon was already defeated and the ending has not yet been presented, the server requests the direct sequence-break presentation. The client waits until screenless gameplay in an unpublished local single-player session before opening the same ending scaffold.

That direct path does not:

- summon or respawn the Dragon;
- modify the End fight;
- replay vanilla credits;
- teleport or respawn the player;
- alter unrelated quest, civilization, Tower, Minion, or native-mod progression.

Continuing simply acknowledges the presentation and returns to the same active world.

For the intended fresh-victory flow, campaign authoring should arm the ending before the expected Dragon fight so the normal WinScreen handoff owns the transition. The direct path exists to preserve legitimate sequence breaking and recovery after a missed first-victory presentation.

## Development preview

`ending_screen.developmentPreview` remains available and defaults to `false`.

Preview mode bypasses the server arm state only for the neutral WinScreen replacement test. It does not write the arm fact, does not mark the production presentation as completed, and does not become campaign authority.

## Current presentation

The screen is intentionally not a final visual design. It renders only:

- a black background;
- the project title;
- an explicit development-scaffold status line;
- a delayed Continue control.

No final narration, Gnarl dialogue, branch outcome summary, credits treatment, artwork, music, Cataclysm exposition, or campaign reveal is encoded here.

## Configuration

The client configuration category `ending_screen` contains:

- `enabled`, master presentation switch, default `true`;
- `developmentPreview`, explicit preview bypass, default `false`;
- `minimumDisplayTicks`, minimum scaffold dwell time, default `80`;
- `allowSkip`, permits Escape, Enter, or Space to continue after the minimum dwell time, default `true`.

Disabling the screen does not mutate server campaign state. A pending direct sequence-break presentation remains pending until the feature is enabled or the session is reloaded.

## Remaining boundary

The technical activation and sequence-break transport are implemented, but no production quest currently arms the ending and the visible screen is still a neutral scaffold.

Still PLANNED:

- the hidden central-campaign prerequisite chain;
- final ending dialogue/narration;
- final artwork, sound, timing and credits treatment;
- any explicit post-ending authored reactions.

Those remain behind the spoiler firewall and must not be inferred from this infrastructure.
