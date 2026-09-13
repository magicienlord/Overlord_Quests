# OVERLORD QUESTS Ending-Screen Runtime Protocol

Status: TECHNICAL ACTIVATION AND PRESENTATION VALIDATION / FINAL ENDING CONTENT NOT APPROVED

This protocol validates the mechanical ending boundary described in `OVERLORD_ENDING_SCREEN_INTEGRATION.md`. It does not approve final ending art, dialogue, music, epilogue text, or hidden campaign prerequisites.

Use disposable unpublished local single-player worlds with commands enabled. Do not Open to LAN.

Install `examples/questlog/quests/overlord_ending_arm_dev.json` into `config/questlog/quests/` for the production-arm tests. It is a development fixture and must not be bundled as campaign content.

## Pass A: fail-closed default

1. Keep `ending_screen.developmentPreview=false`.
2. Do not set `overlord_reign:campaign/ending_armed`.
3. Reach a fresh End victory and use the generated exit portal.

Expected result: Minecraft follows its normal vanilla End presentation. Dragon defeat alone does not arm REIGN's ending.

## Pass B: preview replacement

Use a second fresh disposable world.

1. Set `ending_screen.enabled=true`.
2. Set `ending_screen.developmentPreview=true`.
3. Keep `ending_screen.minimumDisplayTicks=80` and `ending_screen.allowSkip=true`.
4. Enter the End, defeat the Ender Dragon, and use the generated exit portal.

Expected result:

- the vanilla End poem is replaced by the neutral `OVERLORD REIGN` scaffold;
- Continue remains unavailable until the minimum dwell time;
- Continue, Escape, Enter, or Space works after that delay when `allowSkip=true`;
- Minecraft's original completion callback returns the player through the normal post-End flow;
- preview mode does not write or require the production arm fact.

## Pass C: server-authoritative normal path

Use a fresh world whose Dragon is still alive.

1. Set `developmentPreview=false` and `enabled=true`.
2. Run `/clear @s minecraft:debug_stick` and reset/reload the development quest as needed.
3. Give yourself one debug stick. Confirm the development quest completes and records `overlord_reign:campaign/ending_armed`.
4. Defeat the Dragon normally.
5. Use the generated exit portal.

Expected result:

- the End poem is replaced even though development preview is disabled;
- normal menu credits remain untouched;
- continuing acknowledges the one-time presentation and invokes the preserved vanilla completion callback;
- the same world remains playable.

Relog and confirm the ending scaffold does not reopen after it has been acknowledged.

## Pass D: prior-Dragon sequence break

Use another disposable world.

1. Keep the production arm fact absent and `developmentPreview=false`.
2. Defeat the Dragon and complete the ordinary vanilla End transition first.
3. Back in normal gameplay, give the debug stick so the development quest sets `overlord_reign:campaign/ending_armed` only after the Dragon is already historically defeated.
4. Close any open Questlog or inventory screen if necessary.

Expected result:

- the server recognizes Minecraft's persistent previous-Dragon-defeat state;
- the REIGN scaffold opens directly once screenless gameplay is available;
- no Dragon is summoned, respawned or modified;
- continuing closes the scaffold back to the same active world;
- relogging does not replay the acknowledged presentation.

## Pass E: reload recovery

Before acknowledging the direct sequence-break scaffold, disconnect or close the disposable test session if practical, then relaunch the same world.

Expected result: because the arm fact is persistent and the server-side presentation latch is still false, login requests the pending direct presentation again. After it is acknowledged once, later logins remain quiet.

## Pass F: scope guard

In a disposable LAN-published copy, arm the ending and exercise either available transition path.

Expected result: the OVERLORD full-screen replacement does not activate in the published session. Dedicated multiplayer remains outside the target presentation runtime.

## Pass G: ordinary credits boundary

Open a normal non-End credits path with development preview enabled or the production ending armed.

Expected result: ordinary credits remain vanilla. The replacement requires the private WinScreen `poem` discriminator rather than matching the class alone.

## Resetting a disposable test

Production rewards do not clear narrative facts. For test recovery only:

1. run `/questlog narrative fact clear overlord_reign:campaign/ending_armed`;
2. relog once while the fact is absent so the technical presentation latch resets;
3. reset/reload the development quest and clear the debug stick;
4. repeat the desired pass.

## Evidence to retain

Retain `latest.log`, `debug.log` when available, screenshots of both the normal-path and direct-path scaffold, confirmation of same-world continuation, and any crash report.

A successful protocol validates the transport and persistence boundary only. Final OVERLORD REIGN ending presentation and the hidden production quest remain separate authoring work.
