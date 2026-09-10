# Foundation B: Gnarl Popup Test Protocol

Status: IMPLEMENTATION TEST PROCEDURE

This protocol validates presentation behavior only. It does not establish OVERLORD REIGN story canon, quest chronology, rewards, or progression.

## Test inputs

Use the current `gnarl-bootstrap` Forge artifact and the development quest:

`examples/questlog/quests/overlord_gnarl_popup_dev.json`

The development quest is deliberately not bundled into the normal mod manifest.

## Installation

1. Back up the test instance.
2. Remove any separate upstream Questlog JAR so there is exactly one implementation of the `questlog` technical mod id.
3. Install the current OVERLORD QUESTS Forge JAR.
4. Copy `overlord_gnarl_popup_dev.json` into `config/questlog/quests/`.
5. Launch a disposable test world with commands available.

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
2. The quest details screen must open automatically.
3. No unlock or completion toast should appear, because both toast flags are disabled in the fixture.
4. Gnarl's transparent overlay must render above the parchment background without a rectangular image backdrop.
5. The overlay must not suppress title, description, buttons, or input handling.
6. The read objective must remain usable so the development screen can be acknowledged normally.

## Approved character baseline

For the portrait itself, the approved Foundation B character baseline is:

- the established popup Gnarl design;
- square pupils;
- pupils directed toward the player;
- each square pupil remains perspective-aligned to its eye rather than being pasted as a flat screen-facing square;
- the restored original snout geometry;
- the established non-angry, sly expression;
- no automatic blending with the WIP in-game model.

Character design is therefore not part of the remaining composition experiment except where a rendering defect changes how that approved asset appears.

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

With the right details panel closed, this creates a 20-pixel horizontal portrait/parchment overlap. The total horizontal composition is 440 scaled GUI pixels wide. It is centered at a scaled GUI width of 440 or greater; narrower scaled widths necessarily clip some of the current test composition.

This 440-pixel threshold is an implementation fact of the present prototype, not an approved minimum-screen requirement. Whether Foundation B must support narrower scaled GUI widths is a later design/compatibility decision if actual testing demonstrates that it matters for the intended instance.

## GUI-scale matrix

At minimum, review the popup in:

- the normal GUI scale used by the OVERLORD REIGN instance;
- one scale step smaller;
- one scale step larger when Minecraft permits it;
- a windowed configuration narrow enough to approach the current 440-scaled-pixel threshold.

The static helper `tools/check_gnarl_popup_layout.py` reports expected horizontal clipping for representative scaled GUI widths. It does not replace in-game review.

## Evidence to retain

For the acceptance pass, retain screenshots of:

1. the initial automatic popup immediately after the debug-stick objective triggers;
2. the same popup at the normal instance GUI scale;
3. the narrowest tested configuration that remains acceptable;
4. any configuration that visibly clips or overlaps text.

Also retain `latest.log` if the test reveals GUI errors, missing texture messages, quest-loading exceptions, or packet/state anomalies.

## Acceptance decision

Foundation B can accept the native Questlog overlay path only if the direct test confirms that it is stable, readable, and sufficiently adaptable through data fields alone.

If the only failure is narrow-screen clipping, evaluate data-driven layout adjustments before creating a new renderer primitive.

A dedicated Gnarl speaker/portrait renderer becomes justified only if native overlay controls cannot solve an observed anchoring, clipping, scaling, layering, or interaction problem without compromising ordinary Questlog behavior.
