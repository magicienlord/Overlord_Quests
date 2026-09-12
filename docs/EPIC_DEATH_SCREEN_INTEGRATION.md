# Epic Death Screen integration

Status: MECHANICS IMPLEMENTED / RESTRAINED OVERLORD VISUAL PASS IMPLEMENTED / DIRECT VISUAL ACCEPTANCE PENDING

This document records the approved integration boundary for the Epic Death Screen mechanics inside OVERLORD QUESTS. It does not establish OVERLORD REIGN story canon.

## Source baseline

The reference implementation is `epicdeathscreen-forge-1.20.1-1.1.0-beta.2.jar`, SHA-256 `a54e337cd4879c410aa67a4bfb4d158268880dec49f11ee14ca61971c6a35107`.

The inspected reference code is MIT licensed, Copyright (c) 2026 Litewer. Its original license is preserved separately as `reference/EPIC_DEATH_SCREEN_MIT_LICENSE.txt`.

## Implemented mechanical boundary

The current OVERLORD QUESTS death flow preserves the useful mechanical behavior of the reference implementation while keeping story authority outside the screen:

- replace the exact vanilla death screen without indiscriminately replacing another mod's custom death UI;
- bounded cinematic delay before respawn controls become available;
- optional automatic respawn after the delay in non-hardcore play;
- manual skip support for the delay;
- hardcore-safe behavior;
- screen lifecycle handling if the world/player changes or revival mechanics recover the player;
- compatibility blockers for revival systems so the custom screen fails closed rather than interfering with a revive/knockout flow;
- actual Minecraft cause-of-death text;
- configuration through OVERLORD QUESTS rather than a second standalone mod/config surface.

The mechanical layer remains unchanged by the visual pass.

## Explicitly rejected presentation from the reference

The Epic Death Screen visual/audio theme is not the OVERLORD REIGN presentation target. The integration does not carry forward its VHS identity.

Do not integrate as production presentation:

- VHS grain;
- scanlines;
- chromatic/RGB separation;
- tape tracking glitches;
- cassette insertion/ejection framing;
- heartbeat/breathing/tape soundscape;
- cassette/tape audio cues;
- the reference mod's stock humorous death phrases as OVERLORD narrative text.

No Epic Death Screen audio assets are required for the OVERLORD QUESTS implementation. CI contains a theme-boundary validator specifically to prevent the rejected VHS/cassette treatment from being reintroduced accidentally.

## Implemented visual boundary

IMPLEMENTATION now replaces the original plain black scaffold with a restrained code-rendered OVERLORD composition while preserving the accepted mechanical base.

The pass uses:

- a near-black background;
- a bounded central charcoal death panel;
- thin recessed frame lines;
- restrained dark-red accent bars and divider treatment;
- the vanilla localized death-screen title;
- the actual Minecraft cause-of-death text;
- the inherited ordinary death controls after the configured scene delay.

The pass deliberately adds no custom story copy, quotation, Gnarl dialogue, faction emblem, character art, lore symbol, audio, animation, or external texture dependency. `OverlordDeathScreen` does not use `Component.literal(...)`, which keeps authored death copy out of this presentation layer.

The central panel width is clamped for narrow screens, and cause-of-death wrapping is derived from the same panel geometry so the text and frame cannot drift into separate layout systems.

This visual pass is IMPLEMENTED but not yet promoted to an accepted final visual baseline. Direct in-game review remains the DESIGN acceptance boundary.

## Static contract

`tools/validate_overlord_death_screen.py` now guards both sides of the presentation boundary:

- rejected VHS and cassette implementation remains absent;
- the restrained frame, dark-red accent, localized vanilla title, actual cause text, and delayed-control contract remain present;
- authored narrative copy through `Component.literal(...)` is rejected in the death screen;
- the existing ending-screen presentation boundary continues to run from the same validator.

## Regression requirement

The death-screen integration is client-side presentation/control flow and must not alter Questlog quest progression, Gnarl popup behavior, persistent provider bindings, narrative disposition state, rewards, or save compatibility.

Any future visual revision must preserve the validated timing, skip, respawn, hardcore, compatibility, and lifecycle mechanics underneath it.
