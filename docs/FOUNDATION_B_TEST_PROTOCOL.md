# Foundation B: Gnarl Popup Test Protocol

Status: IMPLEMENTATION TEST PROCEDURE - APPROVED PORTRAIT INTEGRATED

This protocol validates presentation behavior only. It does not establish OVERLORD REIGN story canon, quest chronology, rewards, or progression.

## Test inputs

Use the current `gnarl-bootstrap` Forge artifact and the development quest:

`examples/questlog/quests/overlord_gnarl_popup_dev.json`

The development quest is deliberately not bundled into the normal mod manifest.

The exact approved Gnarl portrait is integrated in the runtime resources. The user-supplied main-branch file and the runtime asset are the same Git blob, `40c74f6613f0cc23fbf6be0911dedfcfae82865b`. Runtime validation records SHA-256 `699140666288f84fea0e916c0f77ad719e25acdec60f65d3f8838a0e616444ed`. Foundation B now evaluates how that approved portrait behaves inside the Questlog UI rather than re-opening portrait design.

## Runtime scope

Foundation B targets an unpublished local single-player world only.

Do not use Open to LAN during the primary acceptance test. Dedicated multiplayer and LAN-published worlds are outside the OVERLORD REIGN runtime scope for automatic full-screen Gnarl popups.

Automatic popups wait until normal screenless gameplay before opening. They must not replace an inventory, container, chat screen, Questlog editor, or other active GUI. This is intentional: interrupting a live GUI can close server menus, discard typed input, or leave stale screen state.

## Installation

1. Back up the test instance.
2. Remove any separate upstream Questlog JAR so there is exactly one implementation of the `questlog` technical mod id.
3. Install the current OVERLORD QUESTS Forge JAR.
4. Copy `overlord_gnarl_popup_dev.json` into `config/questlog/quests/`.
5. Launch a disposable local single-player world with commands available and keep it unpublished.

The CI test-kit artifact is arranged with `mods/` and `config/` directories so these test files can be inspected or overlaid without manually reconstructing their target paths.

## Deterministic trigger preparation

The fixture uses `minecraft:debug_stick`, not an ordinary survival item. This prevents an item already present in a normal inventory from immediately satisfying the prerequisite before the tester is ready.

Before each run:

```text
/clear @s minecraft:debug_stick
/questlog reset_all_progress_and_reload
```

Confirm the development quest has not opened yet. Then trigger the actual inventory objective with:

```text
/give @s minecraft:debug_stick 1
```

Do not use `/questlog trigger` as the primary acceptance path. The milestone specifically needs the normal objective transition to prove popup-on-unlock behavior.

## Functional checks

On receipt of the debug stick:

1. The quest must transition from locked to triggered.
2. The quest details screen must open automatically once no other GUI is active.
3. No unlock or completion toast should appear, because both toast flags are disabled in the fixture.
4. Gnarl's transparent overlay must render above the parchment background without a rectangular image backdrop.
5. The overlay must not suppress title, description, buttons, or input handling.
6. The read objective must remain usable so the development screen can be acknowledged normally.
7. The development `minecraft:entity.experience_orb.pickup` trigger cue must be heard exactly once. A second identical cue when the popup opens is a regression.

The experience-orb sound is a test signal only. It is not approved production audio for Gnarl.

## Deferred-popup regression check

Foundation B deliberately defers automatic full-screen presentation while any GUI is open. The queue must survive that deferral and open the current quest instance after the GUI closes.

A deterministic way to exercise this path is:

1. Reset the development quest and remove any existing debug stick:

```text
/clear @s minecraft:debug_stick
/questlog reset_all_progress_and_reload
```

2. Spawn a debug-stick item at the player with a five-second pickup delay:

```text
/summon minecraft:item ~ ~ ~ {Item:{id:"minecraft:debug_stick",Count:1b},PickupDelay:100s}
```

3. Immediately open the inventory. Optionally pick up another inventory stack with the cursor and leave it attached long enough for the spawned debug stick to become collectible.
4. Remain close enough to the summoned item for it to enter the inventory when the pickup delay expires. The quest should unlock while the inventory GUI remains open.
5. Confirm the Gnarl popup does **not** replace the open inventory. The trigger cue should still occur once at unlock time.
6. Return any carried stack to a slot if one was used, then close the inventory normally.
7. Confirm the Gnarl popup opens on a later retry rather than disappearing permanently.

If five seconds is too short for the test setup, increase `PickupDelay` rather than changing the quest definition. Minecraft stores this field in ticks, so `200s` provides approximately ten seconds.

This regression case also verifies that the queue is not tied to the original Quest object. If definitions or progress are reloaded while a popup waits, the queue resolves the quest by ID again before opening and must not display a removed or reset quest.

## Popup queue lifecycle checks

The following are defensive behavior checks and do not change story behavior:

- Triggering the same quest twice before its popup is consumed must not enqueue duplicate popups for that quest ID.
- Logging out clears queued popups and resets their retry timer.
- If a queued quest is removed or reset before display, the stale popup must be discarded.
- If a definition is reloaded while the popup waits, the eventual popup must use the current quest instance and current display definition rather than retained stale data.
- If the integrated server is published to LAN after a popup is queued but before it is consumed, automatic full-screen presentation must be cancelled. A normal unlock toast may be used only when that quest already permits unlock toasts.

The development Gnarl fixture disables unlock toasts, so the LAN-published cancellation case is expected to clear its queued popup without substituting a toast.

## Locked character target

The approved Foundation B portrait preserves:

- the established popup Gnarl design;
- square pupils;
- pupils directed toward the player;
- each square pupil perspective-aligned to its own eye rather than pasted as a flat screen-facing square;
- the original snout geometry, muzzle volume, nostrils, nose, and mouth/jaw relationship;
- the established sly, amused, non-angry expression, including brows, eyelids, grin, and facial proportions;
- separation from the WIP in-game model until that model reaches its own review gate.

These are locked design constraints. Foundation B must not use composition testing as an excuse to redraw the approved portrait.

## Current composition geometry

The development fixture currently uses:

```text
left panel width: 300
panel height: 190
left panel x offset: +70
portrait overlay: 160 x 160
overlay x offset: -140
overlay y offset: +18
```

Static analysis currently predicts:

- 20 px horizontal portrait/parchment overlap;
- 440 scaled GUI px minimum width for full horizontal visibility;
- 229 scaled GUI px minimum height for the panel, overlay, and primary button;
- a 2 x 122 px geometric portrait/description intersection before alpha is considered.

The description intersection is not automatically a failure. The test must determine whether visible portrait pixels actually obscure glyphs or reduce readability.

## GUI-scale matrix

At minimum, review the popup in:

- the normal GUI scale used by the OVERLORD REIGN instance;
- one scale step smaller;
- one scale step larger when Minecraft permits it;
- a windowed configuration narrow enough to approach the current 440-scaled-pixel threshold;
- a vertically constrained configuration approaching the current 229-scaled-pixel threshold if practical.

The static helper `tools/check_gnarl_popup_layout.py` reports expected clipping and geometric intersections. It does not replace in-game review.

## Evidence to retain

For the acceptance pass, retain screenshots of:

1. the initial automatic popup immediately after the debug-stick objective triggers;
2. the same popup at the normal instance GUI scale;
3. the narrowest tested configuration that remains acceptable;
4. any configuration that visibly clips or overlaps text;
5. the deferred-popup regression case if it can be reproduced cleanly.

Also retain `latest.log` if the test reveals GUI errors, missing texture messages, quest-loading exceptions, packet/state anomalies, or unexpected repeated trigger events.

## Acceptance decision

Foundation B can close when the direct unpublished-local-single-player test confirms that the approved portrait and native overlay presentation are stable, readable, and sufficiently adaptable through data fields alone.

If the only runtime failure is narrow-screen clipping, evaluate data-driven layout adjustments before creating a new renderer primitive.

A dedicated Gnarl speaker/portrait renderer becomes justified only if native overlay controls cannot solve an observed anchoring, clipping, scaling, layering, or interaction problem without compromising ordinary Questlog behavior.
