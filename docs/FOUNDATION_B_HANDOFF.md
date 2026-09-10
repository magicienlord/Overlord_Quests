# OVERLORD QUESTS Foundation B Handoff

Status: ACTIVE IMPLEMENTATION MILESTONE

This handoff is repository-local development state, not OVERLORD REIGN world or story canon.

## Foundation A

Foundation A is complete and validated on Java 17 / Minecraft 1.20.1 / Forge 47.4.10.

The normal GitHub Actions build compiles, reobfuscates, verifies the assembled JAR, and uploads the Forge artifact.

## Foundation B objective

Foundation B validates Gnarl's quest-popup presentation using Questlog's existing data-driven overlay controls before any dedicated portrait renderer is introduced.

## Locked character decisions

The popup Gnarl character baseline is approved for this milestone:

- preserve the established popup design;
- square pupils;
- player-directed gaze;
- pupils perspective-aligned with each eye;
- preserve the restored snout geometry;
- preserve the sly/non-angry expression;
- do not merge in WIP in-game-model traits automatically.

The approved portrait generated during the design pass is the intended replacement for `assets/questlog/textures/gui/overlord/gnarl_popup.png`. Repository asset integration remains mechanical Foundation B work before manual acceptance.

## Runtime scope: DECIDED

OVERLORD REIGN is a single-player project.

Automatic full-screen Gnarl quest popups are intentionally limited to an unpublished local single-player world. The existing Questlog restriction is therefore retained as project behavior rather than treated as a missing feature.

LAN-published worlds and dedicated multiplayer are outside the OVERLORD REIGN popup acceptance scope and do not block Foundation B.

## Still under test

The following are implementation-test values and are not locked design:

- portrait scale;
- parchment placement;
- portrait-to-parchment overlap;
- minimum supported scaled GUI width and height;
- whether left-side placement is final;
- whether the native Questlog overlay path is sufficient.

## Development fixture

Quest:

`examples/questlog/quests/overlord_gnarl_popup_dev.json`

Deterministic prerequisite:

`minecraft:debug_stick`

Reset and trigger sequence:

```text
/clear @s minecraft:debug_stick
/questlog reset_all_progress_and_reload
/give @s minecraft:debug_stick 1
```

The debug stick replaced the original ordinary-stick trigger so a normal test inventory is much less likely to satisfy the prerequisite before the tester is ready.

The fixture uses one harmless vanilla trigger sound so Foundation B also verifies that the unlock cue plays exactly once rather than once at trigger time and again when the delayed popup opens.

## Current layout facts

The prototype uses a 300 x 190 left panel and a 160 x 160 portrait with x offsets of +70 for the panel and -140 for the overlay.

Static source-derived geometry currently yields:

- 20 px horizontal portrait/parchment overlap;
- 440 scaled GUI px minimum width for full horizontal visibility;
- 229 scaled GUI px minimum height for the panel, portrait, and primary button to remain fully visible;
- the portrait rectangle reaches 2 px into the description rectangle horizontally, producing a 2 x 122 px geometric intersection before alpha is considered.

The description intersection is a warning, not proof of visible text obstruction. The actual portrait contains transparency and only the in-game render can determine whether visible pixels interfere with glyphs.

Use `tools/check_gnarl_popup_layout.py` for the static geometry report. It is an implementation aid only and does not replace in-game validation.

## Popup reliability corrections

Foundation B source review found and repaired two upstream presentation defects that matter to Gnarl delivery:

1. If the popup retry fired while the player was carrying an item stack in a container GUI, the code returned without resetting the retry timer. The quest remained queued but the timer dropped below zero on the next tick, so the popup could be stranded indefinitely. The carried-stack path now resets the retry delay.
2. `triggered_sound` was played once when the quest triggered and then played a second time when a queued popup opened. The second playback was removed so a popup unlock sound is emitted once per trigger.

Both corrections have passed the Forge build pipeline. The Foundation B manual protocol contains regression checks for them.

## Test artifact

The standard build workflow publishes:

- `overlord-quests-forge-1.20.1`
- `overlord-quests-gnarl-popup-test-kit`

The test kit is prepared as an instance-shaped directory with the mod under `mods/`, the development quest under `config/questlog/quests/`, documentation, static reports, build metadata, and checksums.

## Canon boundary

Do not author story quests, chronology, rewards, settlement assumptions, final Dark Tower coordinates/geometry, or faction outcomes during Foundation B unless separately approved.

The bundled production definition manifest remains intentionally empty.

## Remaining Foundation B gate

Before Foundation B can close:

1. integrate the approved final portrait into the repository asset path;
2. obtain a green CI build containing that exact asset;
3. run the direct unpublished-single-player acceptance protocol;
4. review the resulting screenshots/logs and decide the final popup composition.
