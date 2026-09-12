# OVERLORD QUESTS Ending-Screen Runtime Protocol

Status: DEVELOPMENT PREVIEW VALIDATION ONLY

This protocol validates the mechanical replacement boundary described in `OVERLORD_ENDING_SCREEN_INTEGRATION.md`. It does not approve final ending art, dialogue, music, epilogue text, or campaign activation logic.

Use a disposable unpublished local single-player world with commands enabled. Do not Open to LAN.

## Pass A: fail-closed default

1. Keep `ending_screen.developmentPreview` at its default `false` value.
2. Reach a fresh End victory in the disposable world and use the generated exit portal.
3. Confirm Minecraft follows its normal vanilla End presentation.

Expected result: OVERLORD QUESTS does not replace the End poem while development preview is disabled.

## Pass B: preview replacement

Use a second fresh disposable world so Minecraft has not already recorded the End credits as seen for that player.

1. Set `ending_screen.enabled=true`.
2. Set `ending_screen.developmentPreview=true`.
3. Keep `ending_screen.minimumDisplayTicks=80` and `ending_screen.allowSkip=true` for the baseline pass.
4. Enter the End, defeat the Ender Dragon, and use the generated exit portal.

Commands may be used to accelerate only the disposable test setup. The replacement itself must still be reached through Minecraft's real End-victory WinScreen path.

Expected result:

- the vanilla End poem is replaced by the neutral `OVERLORD REIGN` development scaffold;
- the screen is visibly marked `ENDING PRESENTATION DEVELOPMENT SCAFFOLD`;
- the Continue control is hidden until the configured minimum dwell time has elapsed;
- after the dwell time, Continue becomes available;
- Escape, Enter, or Space may also continue when `allowSkip=true`;
- the continuation runs Minecraft's preserved vanilla completion callback and returns the player through the normal post-End flow;
- the same world remains playable afterward.

## Pass C: single-callback safety

During Pass B, activate Continue once and then attempt no further interaction until the transition finishes.

Confirm from behavior and logs that there is no duplicate world transition, duplicate respawn, repeated screen opening, disconnect, or second completion action.

The implementation latches completion before invoking the vanilla callback. Any duplicate transition is a failure.

## Pass D: scope guard

Repeat only if convenient in a disposable LAN-published copy.

1. Enable development preview.
2. Open the world to LAN before reaching the End presentation.
3. Complete the End-victory presentation path.

Expected result: the OVERLORD replacement does not activate in the published session.

Dedicated multiplayer is outside OVERLORD REIGN's target presentation runtime and should likewise remain untouched.

## Pass E: ordinary credits boundary

If a normal non-End credits path is available in the active client build, open it with development preview enabled.

Expected result: ordinary credits remain vanilla. `WinScreen` is shared by more than one credits context, so the replacement must require its private `poem` flag rather than matching the class alone.

## Evidence to retain

For a completed validation pass, retain:

- `latest.log`;
- `debug.log` when available;
- a screenshot of the preview scaffold;
- confirmation that Continue returned to the same persistent world;
- confirmation that Pass A remained vanilla;
- any crash report if a transition fails.

Do not promote this scaffold to final presentation status from a successful mechanical test. Production activation and final ending presentation remain separate authoring work.
