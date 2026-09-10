# Gnarl Popup Vertical Slice

Status: IMPLEMENTATION TEST

This document defines the first visual validation pass for Gnarl's quest presentation. It contains no OVERLORD REIGN story canon.

## Purpose

The first vertical slice deliberately uses Questlog 3.3.3's existing per-quest presentation fields rather than adding a custom renderer before one is proven necessary.

The development definition is:

`examples/questlog/quests/overlord_gnarl_popup_dev.json`

The portrait asset is:

`common/src/main/resources/assets/questlog/textures/gui/overlord/gnarl_popup.png`

## What this pass validates

The prototype is intended to answer only presentation and implementation questions:

1. Does `show_popup_on_unlock` open the quest details screen reliably when a prerequisite transitions to complete?
2. Does the transparent Gnarl overlay render outside the parchment without clipping or corrupting the panel texture?
3. Is the left-page text still readable when the portrait overlaps the parchment edge?
4. Does the composition remain usable across common GUI scales and window sizes?
5. Is Questlog's native overlay system sufficient, or does OVERLORD QUESTS need a dedicated speaker/portrait rendering field?

## Trigger

The development quest uses one `questlog:item_obtain` prerequisite for `minecraft:stick`.

A prerequisite is required because Questlog initializes quests with no prerequisites as already triggered. The test therefore needs an actual locked-to-unlocked transition in order to exercise popup-on-unlock behavior.

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

The panel is shifted right while the overlay is shifted back left, leaving most of the portrait outside the text area with a small intentional overlap at the parchment edge.

No conclusion should be drawn from the left-side placement until an in-game comparison has been reviewed.

## Test installation

For a manual vertical-slice test, copy the development quest JSON into the active instance's `config/questlog/quests/` directory, then reload/restart Questlog as required by the current development workflow. The asset itself is packaged by OVERLORD QUESTS and does not need to be copied separately once the mod JAR is installed.

Use a test world or reset the quest state before repeating the unlock test. Obtain one stick only after the quest data is loaded so the locked-to-unlocked transition can be observed.

## Acceptance

This vertical slice is accepted only after direct in-game review. A successful render alone does not lock the composition.

If native overlay controls remain stable and readable, the renderer should stay unchanged and the presentation can remain data-driven. If clipping, scaling, anchoring, or interaction problems cannot be corrected through the existing fields, the next implementation pass may introduce a dedicated Gnarl speaker/portrait primitive.
