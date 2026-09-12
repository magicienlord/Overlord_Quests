# OVERLORD QUESTS Death-Screen Runtime Test Protocol

Status: TECHNICAL VALIDATION / RESTRAINED OVERLORD VISUAL PASS / NOT STORY CANON

This protocol validates the client-side death-screen mechanics integrated from the approved Epic Death Screen boundary and the current restrained OVERLORD presentation pass. It does not establish dialogue, imagery, soundscape, or story canon.

## Test scope

The pass verifies:

- only the exact vanilla `DeathScreen` is replaced;
- the OVERLORD composition displays the actual Minecraft cause of death;
- the central framed presentation remains legible across normal GUI scales;
- the configured scene delay hides and disables vanilla death controls until release;
- manual skip releases the controls when enabled;
- non-hardcore automatic respawn occurs only when enabled and after the delay;
- disabling automatic respawn leaves the ordinary death controls usable after release;
- the screen closes cleanly if the player is revived or the client player/world identity changes;
- hardcore mode never performs automatic respawn;
- revival compatibility blockers fail closed when their supported mods own the death/revival flow;
- the integration does not disturb quest progress, provider binding, disposition state, or the Gnarl popup queue.

## Environment

Use the Forge artifact built from the exact commit under test in the normal OVERLORD REIGN 1.20.1 instance.

For the primary pass:

1. use an unpublished local single-player survival world with commands available;
2. keep OVERLORD QUESTS death-screen support enabled;
3. temporarily set automatic respawn off so the manual control-release behavior can be observed;
4. keep manual skip enabled;
5. use a clearly nonzero scene delay, such as the current default 120 ticks.

The death-screen settings are client configuration owned by OVERLORD QUESTS. Use the generated Questlog client configuration or its configuration UI rather than adding a second death-screen mod/config surface.

## Pass A: presentation, exact vanilla replacement, delayed controls

With automatic respawn disabled, run:

```text
/kill @s
```

Expected behavior:

- the vanilla death screen is replaced by the OVERLORD QUESTS presentation;
- the background is near-black rather than a copied VHS treatment;
- a bounded charcoal central panel is visible with thin frame lines and restrained dark-red accents;
- the localized vanilla death title remains centered in the upper part of the panel;
- the displayed cause remains the actual Minecraft death message and wraps within the same central panel geometry;
- respawn/title controls are not visible or usable before the configured scene delay expires;
- no custom quotation, Gnarl dialogue, faction emblem, lore symbol, VHS grain, scanlines, chromatic separation, cassette imagery, heartbeat, breathing, tape ambience, or stock joke line is introduced;
- once the delay expires, the ordinary death controls become visible and usable below the presentation area.

Repeat this pass at a second GUI scale if practical. The framed panel must remain centered and bounded, and the cause text must remain readable without escaping the frame.

Use the respawn control and confirm normal survival gameplay resumes.

## Pass B: manual skip

Die again with automatic respawn still disabled. Before the configured delay expires, press one of the supported skip keys:

```text
Escape
Enter
Space
```

Expected behavior:

- the scene clock releases immediately;
- the ordinary death controls become available;
- the skip itself does not force respawn while automatic respawn is disabled;
- mouse clicks before release do not activate hidden controls.

Respawn normally after the controls appear.

## Pass C: automatic respawn

Enable automatic respawn, retain the non-hardcore world, and die again.

Expected behavior:

- the OVERLORD presentation remains active for the configured delay;
- the player is not automatically respawned before the delay completes;
- once the delay completes, the client issues the normal respawn action once;
- the death screen closes after the live player state returns;
- the screen does not reopen against the already-respawned player.

Repeat once using manual skip while automatic respawn is enabled. Skip may release the scene clock early, after which the configured automatic-respawn behavior may occur. It must still issue only one effective respawn transition.

## Pass D: hardcore safety

Use a disposable hardcore test world. Keep automatic respawn enabled in configuration and die once.

Expected behavior:

- the OVERLORD death presentation may still replace the exact vanilla screen;
- automatic respawn must not occur in hardcore;
- the hardcore-safe vanilla control flow remains available after the scene delay or manual skip;
- no survival-world respawn action is forced by OVERLORD QUESTS.

Do not use the production world for this test.

## Pass E: lifecycle and revival ownership

The screen is required to relinquish control when the client no longer represents the original dead player/world.

At minimum, confirm an ordinary respawn closes the presentation. If Hardcore Revival or PlayerRevive is installed in the validation instance, perform that mod's normal knocked-out/bleeding flow as a separate compatibility pass.

Expected behavior with a supported revival mod:

- OVERLORD QUESTS must not replace the revival mod's owned flow while its compatibility blocker reports the player as recoverable;
- if the optional revival API cannot be resolved, OVERLORD QUESTS must fail closed and leave that mod's death flow untouched rather than guessing.

Absence of either optional revival mod is not a failure of the primary OVERLORD REIGN single-player test.

## Pass F: quest-system regression check

Before one death, leave an ordinary quest or the provider development fixture in a known in-progress state. After respawn, verify that the state is unchanged.

Also verify that death-screen activity does not itself:

- trigger, complete, reset, or reward a quest;
- clear or fabricate an NPC provider binding;
- alter a civilization disposition state;
- generate a Gnarl unlock popup unrelated to an actual quest trigger.

This pass is a regression boundary, not a new gameplay requirement.

## Evidence to retain

Retain:

- `latest.log` covering the complete test session;
- one screenshot before death controls are released;
- one screenshot after manual skip or natural release;
- one screenshot at an alternate GUI scale if the visual pass is being reviewed for acceptance;
- one screenshot from the hardcore pass if performed;
- any warning mentioning an unavailable revival compatibility API;
- any crash report or unexpected screen-transition log.

## Acceptance criteria

The death-screen implementation passes technical validation only when the actual Forge 1.20.1 instance confirms the framed presentation, delayed controls, skip, non-hardcore auto-respawn, hardcore safety, exact-vanilla-screen ownership, lifecycle cleanup, and quest-system non-interference.

The current code establishes an implemented DESIGN candidate, not accepted final presentation. Promotion to an accepted visual baseline requires direct in-game review of the rendered composition.
