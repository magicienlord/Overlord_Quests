# Gnarl Popup Vertical Slice

Status: FOUNDATION B VISUAL REWORK IMPLEMENTED / IN-GAME ACCEPTANCE PENDING

This document defines the current visual validation pass for Gnarl's Questlog presentation. It contains no OVERLORD REIGN story canon.

## Purpose

The earlier native-overlay experiment proved the Questlog trigger and popup lifecycle, but direct review exposed two presentation problems:

1. Gnarl occupied and visually intruded into the parchment area rather than having a deliberate reaction space;
2. low-alpha pixels in the supplied portrait carried a visible red/orange matte fringe.

The visual foundation now uses a dedicated incorporeal-speaker screen rather than attempting to force the final composition through ordinary QuestDetails overlay offsets.

The development definition remains:

`examples/questlog/quests/overlord_gnarl_popup_dev.json`

The source portrait remains:

`common/src/main/resources/assets/questlog/textures/gui/overlord/gnarl_popup.png`

The source artwork itself has not been redrawn by this pass. Its edge is optionally cleaned into a client-side dynamic texture for presentation.

## Dedicated speaker surface

`OverlordSpeakerScreen` is used automatically when a popup-enabled quest declares `speaker_id`.

The surface has three locked layout rules:

1. parchment is the dominant quest-information surface;
2. the speaker owns a separate lane on the RIGHT and cannot overlap the parchment body;
3. the quest action remains directly below the parchment, not below the speaker lane.

Ordinary Questlog entries without speaker metadata continue to use the inherited details screen. In-world Villager-Retaliation-derived providers remain a separate interaction surface.

## Reaction metadata

The development Gnarl entry currently declares:

```text
speaker_id: overlord_reign:gnarl
speaker_reaction: neutral
speaker_pane_width: 176
speaker_alpha_cleanup: true
```

The supported semantic reaction keys are:

```text
neutral
directive
mocking
approving
severe
```

Only the semantic contract is required now. A full five-image Gnarl roster is not required before the engine and layout are validated.

## Current composition geometry

The development fixture requests:

```text
parchment width: 360
panel height: 200
speaker lane: 176
portrait display rectangle: 168 x 168
speaker gap: 10
portrait offset inside lane: x=0, y=+4
```

The screen contracts the parchment/speaker combination responsively when the scaled GUI becomes narrow. The right-side speaker lane is still treated as a distinct surface even in the compact layout.

The primary action remains below the parchment at every layout size.

`tools/check_gnarl_popup_layout.py` statically checks these invariants and reports clipping thresholds across representative scaled GUI sizes.

## Alpha cleanup

The supplied portrait is a valid RGBA PNG, but visual inspection found a coloured matte in translucent boundary pixels.

`SpeakerPortraitTextures` provides an opt-in client-side correction for that specific class of source problem:

- negligible-alpha pixels are made fully transparent;
- low/medium-alpha edge pixels keep their original alpha;
- their RGB is replaced by colour sampled from a nearby opaque source pixel;
- the source PNG in resources remains unchanged;
- future speaker assets are NOT cleaned unless their definition explicitly opts in.

That last rule is important because intentional spectral glow, smoke, aura, and magical transparency must retain its authored colour.

## Trigger and runtime scope

The development quest still uses a `minecraft:debug_stick` prerequisite and a `questlog:read` objective. It exists only to exercise a genuine locked-to-triggered transition, popup queue behavior, and the action control.

Automatic full-screen speaker popups remain scoped to an unpublished local single-player world. They wait while another GUI is active rather than replacing an inventory, container, chat screen, or editor.

## Visual acceptance target

The next direct in-game pass should verify:

- Gnarl appears wholly in the right-side reaction lane;
- no visible portrait pixel intrudes into the parchment;
- the coloured alpha fringe is eliminated or reduced below visible concern;
- the parchment occupies the main visual weight of the screen;
- title and body remain readable;
- the Read/Done action appears under parchment only;
- there is no isolated action beneath Gnarl;
- the composition remains coherent at the normal OVERLORD REIGN GUI scale and adjacent scale settings;
- the original exactly-once unlock cue and deferred-popup lifecycle remain intact.

If any of those fail, the failure is still Foundation B presentation work and must be corrected before production campaign content begins.
