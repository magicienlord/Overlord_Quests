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

The approved portrait generated during the design pass is the intended replacement for `assets/questlog/textures/gui/overlord/gnarl_popup.png`. Repository asset integration is part of the remaining Foundation B mechanical work before manual acceptance.

## Still under test

The following are implementation-test values and are not locked design:

- portrait scale;
- parchment placement;
- portrait-to-parchment overlap;
- minimum supported scaled GUI width;
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

## Current layout facts

The prototype uses a 300 x 190 left panel and a 160 x 160 portrait with x offsets of +70 for the panel and -140 for the overlay.

That yields:

- 20 px horizontal portrait/parchment overlap;
- 440 scaled GUI px total horizontal composition;
- unavoidable clipping below 440 scaled GUI px with the current coordinates.

Use `tools/check_gnarl_popup_layout.py` for a static geometry report. This report is an implementation aid only and does not replace in-game validation.

## Popup reliability corrections

Foundation B source review found and repaired two upstream presentation defects that matter to Gnarl delivery:

1. If the popup retry fired while the player was carrying an item stack in a container GUI, the code returned without resetting the retry timer. The quest remained queued but the timer dropped below zero on the next tick, so the popup could be stranded indefinitely. The carried-stack path now resets the retry delay.
2. `triggered_sound` was played once when the quest triggered and then played a second time when a queued popup opened. The second playback was removed so a popup unlock sound is emitted once per trigger.

These are implementation reliability fixes. They do not alter quest content or story behavior.

## Multiplayer popup policy: UNKNOWN

Upstream Questlog only queues `show_popup_on_unlock` screens when the client owns an unpublished single-player server. LAN-published worlds and dedicated multiplayer clients do not receive the automatic popup through that path.

OVERLORD QUESTS has not yet changed this restriction because multiplayer popup policy is a product/design decision rather than a compiler or correctness repair.

Before Foundation B is declared final, the Overlord should decide whether Gnarl's automatic quest popups are intended to be:

- single-player only, preserving upstream behavior; or
- available in LAN/dedicated multiplayer as well.

The current manual Foundation B acceptance path remains valid for ordinary single-player testing.

## Test artifact

The standard build workflow publishes:

- `overlord-quests-forge-1.20.1`
- `overlord-quests-gnarl-popup-test-kit`

The test kit contains the Forge build, development quest, Foundation B protocol, alignment reference, and static layout report.

## Canon boundary

Do not author story quests, chronology, rewards, settlement assumptions, final Dark Tower coordinates/geometry, or faction outcomes during Foundation B unless separately approved.

The bundled production definition manifest remains intentionally empty.
