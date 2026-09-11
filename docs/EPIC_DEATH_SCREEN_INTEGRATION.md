# Epic Death Screen integration

Status: MECHANICAL SCAFFOLD IMPLEMENTED / OVERLORD VISUAL PASS PLANNED

This document records the approved integration boundary for the Epic Death Screen mechanics inside OVERLORD QUESTS. It does not establish OVERLORD REIGN story canon.

## Source baseline

The reference implementation is `epicdeathscreen-forge-1.20.1-1.1.0-beta.2.jar`, SHA-256 `a54e337cd4879c410aa67a4bfb4d158268880dec49f11ee14ca61971c6a35107`.

The inspected reference code is MIT licensed, Copyright (c) 2026 Litewer. Its original license is preserved separately as `reference/EPIC_DEATH_SCREEN_MIT_LICENSE.txt`.

## Implemented mechanical boundary

The current OVERLORD QUESTS death flow preserves the useful mechanical behavior of the reference implementation while keeping presentation neutral:

- replace the exact vanilla death screen without indiscriminately replacing another mod's custom death UI;
- bounded cinematic delay before respawn controls become available;
- optional automatic respawn after the delay in non-hardcore play;
- manual skip support for the delay;
- hardcore-safe behavior;
- screen lifecycle handling if the world/player changes or revival mechanics recover the player;
- compatibility blockers for revival systems so the custom screen fails closed rather than interfering with a revive/knockout flow;
- actual Minecraft cause-of-death text;
- configuration through OVERLORD QUESTS rather than a second standalone mod/config surface.

The mechanical scaffold is integrated in the Forge runtime and covered by normal build/JAR validation. This does not approve the current neutral visual composition as the final OVERLORD REIGN death screen.

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

No Epic Death Screen audio assets are required for the OVERLORD QUESTS implementation at this stage. CI contains a theme-boundary validator specifically to prevent the rejected VHS/cassette treatment from being reintroduced accidentally.

## Current visual boundary

IMPLEMENTATION currently uses a deliberately neutral scaffold while the mechanics are stabilized: dark background, actual Minecraft cause-of-death text, and ordinary controls. That scaffold is not a final design.

The final death-screen visual language remains PLANNED and requires a dedicated OVERLORD visual pass. Until that pass is explicitly approved, temporary visuals must not invent lore, faction imagery, quotations, Gnarl dialogue, or other story content.

The visual pass may replace the neutral scaffold without changing the proven timing, skip, respawn, hardcore, compatibility, and lifecycle mechanics underneath it.

## Regression requirement

The death-screen integration is client-side presentation/control flow and must not alter Questlog quest progression, Gnarl popup behavior, persistent provider bindings, narrative disposition state, rewards, or save compatibility.
