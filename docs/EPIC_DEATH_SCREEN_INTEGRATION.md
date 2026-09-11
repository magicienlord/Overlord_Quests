# Epic Death Screen integration

Status: PLANNED / TECHNICAL

This document records the approved integration boundary for the Epic Death Screen mechanics inside OVERLORD QUESTS. It does not establish OVERLORD REIGN story canon.

## Source baseline

The reference implementation is `epicdeathscreen-forge-1.20.1-1.1.0-beta.2.jar`, SHA-256 `a54e337cd4879c410aa67a4bfb4d158268880dec49f11ee14ca61971c6a35107`.

The inspected reference code is MIT licensed, Copyright (c) 2026 Litewer. Its original license is preserved separately as `reference/EPIC_DEATH_SCREEN_MIT_LICENSE.txt`.

## Approved mechanics to preserve

The integrated death flow should preserve the useful mechanical behavior of the reference implementation:

- replace the exact vanilla death screen without indiscriminately replacing another mod's custom death UI;
- a bounded cinematic delay before respawn controls become available;
- optional automatic respawn after the delay in non-hardcore play;
- manual skip support for the delay;
- hardcore-safe behavior;
- robust screen lifecycle handling if the world/player changes or revival mechanics recover the player;
- compatibility blockers for revival systems so the custom screen fails closed rather than interfering with a revive/knockout flow;
- responsive cause-of-death text and controls;
- configuration through OVERLORD QUESTS rather than a second standalone mod/config surface.

## Explicitly rejected presentation from the reference

The Epic Death Screen visual/audio theme is not the OVERLORD REIGN presentation target. The integration must not carry forward its VHS identity.

Do not integrate as production presentation:

- VHS grain;
- scanlines;
- chromatic/RGB separation;
- tape tracking glitches;
- cassette insertion/ejection framing;
- heartbeat/breathing/tape soundscape;
- cassette/tape audio cues;
- the reference mod's stock humorous death phrases as OVERLORD narrative text.

No Epic Death Screen audio assets are required for the OVERLORD QUESTS implementation at this stage.

## Current visual boundary

IMPLEMENTATION may use a deliberately neutral temporary scaffold while the mechanics are stabilized: dark background, actual Minecraft cause-of-death text, and ordinary controls. That scaffold is not a final design.

The final death screen visual language remains PLANNED and requires a dedicated OVERLORD visual pass. Until that pass is explicitly approved, temporary visuals must not invent lore, faction imagery, quotations, Gnarl dialogue, or other story content.

## Regression requirement

The death-screen integration is client-side presentation/control flow and must not alter Questlog quest progression, Gnarl popup behavior, persistent provider bindings, narrative disposition state, rewards, or save compatibility.
