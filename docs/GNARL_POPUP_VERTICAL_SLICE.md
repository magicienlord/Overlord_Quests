# Gnarl Popup Vertical Slice

Status: FOUNDATION B IMPLEMENTATION TEST

This document defines the first visual validation pass for Gnarl's quest presentation. It contains no OVERLORD REIGN story canon.

## Purpose

The first vertical slice deliberately uses Questlog 3.3.3's existing per-quest presentation fields rather than adding a custom renderer before one is proven necessary.

The development definition is:

`examples/questlog/quests/overlord_gnarl_popup_dev.json`

The portrait asset is:

`common/src/main/resources/assets/questlog/textures/gui/overlord/gnarl_popup.png`

The complete manual procedure is recorded in `docs/FOUNDATION_B_TEST_PROTOCOL.md`.

## Current design decision

The popup Gnarl character design is now approved for this milestone.

The approved baseline preserves the established portrait and changes only the pupil treatment:

- pupils are square;
- pupils look toward the player;
- each pupil remains perspective-aligned to the eye it sits within;
- the original snout geometry is preserved;
- Gnarl retains the established sly/non-angry expression.

Do not otherwise blend the popup portrait with the current WIP in-game Gnarl model yet. Full cross-alignment is deferred until the model has a stable face, ears, body silhouette, cloak, and lantern rig. The controlled comparison criteria are recorded in `docs/GNARL_VISUAL_ALIGNMENT.md`.

This approval locks the popup character design for Foundation B. It does not lock parchment placement, portrait scale, or screen composition. Those remain subject to the in-game vertical-slice test below.

## What this pass validates

The prototype is intended to answer only presentation and implementation questions:

1. Does `show_popup_on_unlock` open the quest details screen reliably when a prerequisite transitions to complete?
2. Does the transparent Gnarl overlay render outside the parchment without clipping or corrupting the panel texture?
3. Is the left-page text still readable when the portrait overlaps the parchment edge?
4. Does the composition remain usable across relevant GUI scales and window sizes?
5. Is Questlog's native overlay system sufficient, or does OVERLORD QUESTS need a dedicated speaker/portrait rendering field?

## Trigger

The development quest uses one `questlog:item_obtain` prerequisite for `minecraft:debug_stick`.

A prerequisite is required because Questlog initializes quests with no prerequisites as already triggered. The test therefore needs an actual locked-to-unlocked transition in order to exercise popup-on-unlock behavior.

A debug stick is used instead of a common survival item so an ordinary inventory is unlikely to satisfy the prerequisite accidentally before the tester is ready. The intended sequence is:

```text
/clear @s minecraft:debug_stick
/questlog reset_all_progress_and_reload
/give @s minecraft:debug_stick 1
```

The quest uses a `questlog:read` objective so the details screen remains interactive after the popup appears. Rewards and all story-facing progression are intentionally absent.

## Initial layout

The first baseline places Gnarl on the left side of a single parchment panel. These values are test coordinates, not a locked visual design:

```text
left panel width: 300
panel height: 190
left panel x offset: +70
Gnarl overlay: 160 x 160
overlay x offset: -140
overlay y offset: +18
```

The panel is shifted right while the overlay is shifted back left, leaving most of the portrait outside the text area with a 20-pixel horizontal overlap at the parchment edge.

With the details panel closed, the current composition is 440 scaled GUI pixels wide. It therefore fits horizontally without clipping only when the scaled GUI width is at least 440 pixels. This is a property of the test coordinates, not yet a minimum-resolution requirement for the final mod.

No conclusion should be drawn from the left-side placement until an in-game comparison has been reviewed.

## Test installation

The GitHub Actions build publishes a dedicated `overlord-quests-gnarl-popup-test-kit` artifact containing the Forge build, development quest, and relevant test documentation.

For a manual vertical-slice test, install the OVERLORD QUESTS JAR, ensure a separate upstream Questlog JAR is not present, and copy the development quest JSON into the active instance's `config/questlog/quests/` directory. The portrait asset itself is packaged by OVERLORD QUESTS and does not need to be copied separately once the mod JAR is installed.

Use a disposable test world or reset quest state before repeating the unlock test. Remove the debug stick before resetting so the inventory objective cannot immediately retrigger.

## Static layout aid

`tools/check_gnarl_popup_layout.py` mirrors the current `QuestDetails` horizontal placement formula and reports clipping at representative scaled GUI widths. Its output is included in the test kit when produced by CI.

Static geometry is not acceptance evidence. Minecraft still needs to render the popup directly.

## Acceptance

This vertical slice is accepted only after direct in-game review. A successful build or static layout report alone does not lock the composition.

If native overlay controls remain stable and readable, the renderer should stay unchanged and the presentation can remain data-driven. If clipping, scaling, anchoring, layering, or interaction problems cannot be corrected through the existing fields, the next implementation pass may introduce a dedicated Gnarl speaker/portrait primitive.
