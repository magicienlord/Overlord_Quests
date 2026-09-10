# Gnarl Popup Vertical Slice

Status: FOUNDATION B IMPLEMENTATION TEST - TECHNICAL BUILD GREEN - IN-GAME ACCEPTANCE PENDING

This document defines the first visual validation pass for Gnarl's quest presentation. It contains no OVERLORD REIGN story canon.

## Purpose

The first vertical slice deliberately uses Questlog 3.3.3's existing per-quest presentation fields rather than adding a custom renderer before one is proven necessary.

The development definition is:

`examples/questlog/quests/overlord_gnarl_popup_dev.json`

The current test portrait asset is:

`common/src/main/resources/assets/questlog/textures/gui/overlord/gnarl_popup.png`

The complete manual procedure is recorded in `docs/FOUNDATION_B_TEST_PROTOCOL.md`.

## Current design decision

The popup Gnarl character-design target is locked for this milestone. The final corrected PNG is not yet approved as a binary.

The locked target preserves the established portrait and changes only the pupil treatment:

- pupils are square;
- pupils look toward the player;
- each pupil remains perspective-aligned to the eye it sits within;
- the original snout geometry, muzzle volume, nostrils, and mouth/jaw relationship are preserved unchanged;
- Gnarl retains the established sly, amused, non-angry expression without brow, eyelid, grin, or facial-proportion drift.

Do not otherwise blend the popup portrait with the current WIP in-game Gnarl model yet. Full cross-alignment is deferred until the model has a stable face, ears, body silhouette, cloak, and lantern rig. The controlled comparison criteria are recorded in `docs/GNARL_VISUAL_ALIGNMENT.md`.

These decisions lock the target character design for Foundation B. They do not lock the current raster file, parchment placement, portrait scale, or screen composition. The raster file requires explicit visual acceptance once a correction satisfies all locked face invariants.

## Current test asset integration

A 1254 x 1254 RGBA test portrait is physically present at the runtime resource path above. Mechanical validation of that binary reports:

```text
sha256: 699140666288f84fea0e916c0f77ad719e25acdec60f65d3f8838a0e616444ed
size: 1154559 bytes
dimensions: 1254 x 1254
PNG color type: RGBA
fully transparent pixels: 713423
partially transparent pixels: 858065
```

These facts prove PNG integrity and usable alpha only. They do not prove that the image satisfies the final direct-gaze square-pupil correction, and they must not be used as evidence of final portrait approval.

Questlog renders the texture into the fixture's 160 x 160 overlay rectangle. Source texture resolution and on-screen presentation size are therefore separate concerns.

## Runtime scope

Automatic Gnarl popups are an unpublished local single-player feature for OVERLORD REIGN.

LAN-published worlds and dedicated multiplayer are not part of the target runtime and are not Foundation B acceptance cases.

## What this pass validates

The prototype is intended to answer only presentation and implementation questions:

1. Does `show_popup_on_unlock` open the quest details screen reliably when a prerequisite transitions to complete?
2. Does the transparent Gnarl overlay render outside the parchment without clipping or corrupting the panel texture?
3. Is the left-page text still readable when the portrait overlaps the parchment edge?
4. Does the composition remain usable across relevant GUI scales and window sizes?
5. Does the unlock cue play exactly once for the automatic popup?
6. Does a temporarily blocked popup remain queued until it can safely open?
7. Is Questlog's native overlay system sufficient, or does OVERLORD QUESTS need a dedicated speaker/portrait rendering field?

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

A vanilla experience-orb pickup sound is attached only as a development cue. It exists to prove that the trigger sound fires exactly once. It is not a proposed Gnarl production sound.

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

Static analysis of the current `QuestDetails` placement gives:

- 20 px horizontal portrait/parchment overlap;
- 440 scaled GUI px minimum width for complete horizontal visibility;
- 229 scaled GUI px minimum height for the panel, portrait, and primary button to remain fully visible;
- a 2 x 122 px geometric portrait/description intersection before portrait transparency is considered.

The last value is deliberately treated as a warning only. The portrait's transparent pixels may make the actual visual overlap harmless, and title/body readability must be judged from the Minecraft render.

No conclusion should be drawn from the left-side placement until an in-game comparison has been reviewed.

## Popup queue behavior

Queued automatic popups store only quest resource IDs. When the retry fires, the client resolves the current quest instance by ID before opening the details screen. The same current-state resolution is used if the session leaves the unpublished-local-single-player scope and the queued event is eligible to fall back to an ordinary unlock toast.

This prevents an obsolete Quest object or obsolete display definition from being retained across a hot definition reload. Removed or reset quests are discarded rather than opened from stale queue state.

## Test installation

The GitHub Actions build publishes a dedicated `overlord-quests-gnarl-popup-test-kit` artifact prepared in an instance-shaped layout.

Install the OVERLORD QUESTS JAR, ensure a separate upstream Questlog JAR is not present, place the development quest under `config/questlog/quests/`, and launch an unpublished local single-player test world. Do not use Open to LAN for the acceptance pass.

Use a disposable test world or reset quest state before repeating the unlock test. Remove the debug stick before resetting so the inventory objective cannot immediately retrigger.

## Static validation aids

`tools/check_gnarl_popup_layout.py` mirrors the current `QuestDetails` placement constants and reports horizontal/vertical clipping plus the description-rectangle intersection.

`tools/check_gnarl_popup_asset.py` validates the repository PNG's mechanical contract, including dimensions, PNG integrity, alpha content, size, and SHA-256. It does not assess whether the picture matches the locked character-design target.

Static checks are not acceptance evidence. Minecraft still needs to render the popup directly.

## Acceptance

This vertical slice is accepted only after direct in-game review and final corrected-portrait approval. A successful build or static report alone does not lock the portrait binary or composition.

If native overlay controls remain stable and readable, the renderer should stay unchanged and the presentation can remain data-driven. If clipping, scaling, anchoring, layering, or interaction problems cannot be corrected through the existing fields, the next implementation pass may introduce a dedicated Gnarl speaker/portrait primitive.
