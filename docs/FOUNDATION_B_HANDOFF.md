# OVERLORD QUESTS Foundation B Handoff

Status: ACTIVE IMPLEMENTATION MILESTONE - TECHNICAL BUILD GATE GREEN - APPROVED PORTRAIT INTEGRATED - MANUAL IN-GAME ACCEPTANCE PENDING

This handoff records repository-local development state, not OVERLORD REIGN world or story canon.

## Foundation A

Foundation A is complete and validated on Java 17, Minecraft 1.20.1, and Forge 47.4.10. The normal GitHub Actions build compiles, reobfuscates, verifies the assembled JAR, validates quest-definition contracts, and uploads the Forge artifact.

## Foundation B objective

Foundation B validates Gnarl's quest-popup presentation using Questlog's existing data-driven overlay controls before any dedicated portrait renderer is introduced.

## Locked character-design target

The following target is decided for this milestone:

- preserve the established popup Gnarl design;
- square pupils;
- player-directed gaze;
- each square pupil remains perspective-aligned with its eye plane;
- preserve the original snout geometry, muzzle volume, nostrils, and mouth/jaw relationship;
- preserve the original sly, amused, non-angry expression, including brows, eyelids, grin, and facial proportions;
- do not merge WIP in-game-model traits into the popup automatically.

These are approved design constraints, and the exact approved portrait binary is now integrated.

## Approved repository portrait

The user-supplied portrait uploaded to `main` is present unchanged at:

`common/src/main/resources/assets/questlog/textures/gui/overlord/gnarl_popup.png`

The main-branch upload and runtime asset resolve to the same Git blob:

```text
git blob: 40c74f6613f0cc23fbf6be0911dedfcfae82865b
bytes: 1154559
sha256: 699140666288f84fea0e916c0f77ad719e25acdec60f65d3f8838a0e616444ed
dimensions: 1254 x 1254
PNG color type: RGBA
fully transparent pixels: 713423
partially transparent pixels: 858065
```

Mechanical validation proves the file is intact and alpha-capable. Portrait-binary approval is no longer a separate Foundation B blocker. The remaining visual question is whether this approved image composes correctly inside Minecraft at the intended GUI scales.

## Runtime scope: DECIDED

OVERLORD REIGN is a single-player project. Automatic full-screen Gnarl quest popups are intentionally limited to an unpublished local single-player world.

LAN-published worlds and dedicated multiplayer remain outside the target runtime for automatic full-screen presentation. If a queued popup becomes ineligible because the integrated server is published, the client may fall back to an ordinary unlock toast only when the current quest definition already permits that toast.

## Development fixture

The development quest is:

`examples/questlog/quests/overlord_gnarl_popup_dev.json`

It uses `minecraft:debug_stick` as a deterministic prerequisite, one `questlog:read` objective, no rewards, and a harmless vanilla trigger sound used only to test exactly-once unlock audio.

Primary reset/trigger sequence:

```text
/clear @s minecraft:debug_stick
/questlog reset_all_progress_and_reload
/give @s minecraft:debug_stick 1
```

The fixture is development content and is deliberately excluded from the bundled production definition manifest.

## Current layout facts

The prototype uses a 300 x 190 left panel and a 160 x 160 portrait with x offsets of +70 for the panel and -140 for the overlay.

Static source-derived geometry currently yields:

- 20 px horizontal portrait/parchment overlap;
- 440 scaled GUI px minimum width for full horizontal visibility;
- 229 scaled GUI px minimum height for the panel, portrait, and primary button to remain fully visible;
- a 2 x 122 px geometric portrait/description intersection before alpha is considered.

The intersection is only a static warning. Direct Minecraft rendering determines whether visible portrait pixels actually obstruct text.

## Popup reliability state

The automatic-popup path now follows these lifecycle rules:

- popup entries are queued by quest resource ID rather than by retaining Quest objects;
- duplicate IDs are not queued simultaneously;
- any active GUI defers automatic full-screen presentation instead of being replaced;
- each retry resolves the current quest instance and current display data by ID;
- removed or reset quests are discarded before display;
- the unpublished-local-single-player scope is checked again at consumption time;
- the LAN fallback path also resolves current quest state rather than using stale queued display data;
- logout clears the popup queue and retry state;
- unlock audio is emitted at the trigger event and is not replayed when a deferred popup eventually opens.

These are implementation safeguards. They still require runtime validation through the Foundation B protocol.

## Technical gate

The authoritative Forge pipeline has reached green state with the current engine-hardening baseline. It covers:

- definition-validator self-tests;
- OVERLORD quest-definition validation;
- specialized runtime-required objective/reward field validation;
- definition wire-size and loader-boundary validation;
- definition-cache authority validation;
- private event-bus lifecycle validation;
- Gnarl PNG integrity and alpha checks;
- static popup-layout reporting;
- Java 17 Forge compilation and reobfuscation;
- assembled-JAR resource/class inspection;
- Foundation B test-kit preparation;
- Forge artifact and test-kit artifact upload.

Subsequent source and documentation corrections continue through the same workflow. A green workflow is necessary but cannot close Foundation B by itself.

## Fork closure boundary

Generic Questlog refactoring is now out of scope. The fork retains Questlog's underlying quest engine and should receive additional engine changes only when one of the following is true:

- a concrete OVERLORD REIGN quest requirement cannot be represented correctly with the current engine;
- the Foundation B in-game test exposes a reproducible defect;
- a modpack integration exposes a concrete compatibility failure.

The fork has also removed inherited CurseForge, Modrinth, and external wiki publication tooling. Builds are private/local or GitHub Actions artifacts unless the Overlord explicitly establishes a publication target.

## Still under test

The following remain implementation-test values rather than locked design:

- portrait scale;
- parchment placement;
- portrait-to-parchment overlap;
- minimum supported scaled GUI width and height;
- whether left-side placement is final;
- whether the native Questlog overlay path is sufficient.

## Canon boundary

Do not author story quests, chronology, rewards, settlement assumptions, final Dark Tower coordinates/geometry, or faction outcomes during Foundation B unless separately approved.

The bundled production definition manifest remains intentionally empty.

## Remaining Foundation B gate

Foundation B now has one direct acceptance gate:

1. install a green Foundation B test kit in an OVERLORD REIGN test instance and perform the unpublished-local-single-player presentation protocol, retaining screenshots and relevant logs.

The runtime review must cover transparency, clipping, anchoring, text readability, GUI-scale behavior, exactly-once unlock audio, deferred-popup reliability, and current-state resolution after queue deferral.

Do not introduce a dedicated Gnarl renderer unless direct in-game evidence shows that Questlog's native overlay controls cannot satisfy the presentation requirements.
