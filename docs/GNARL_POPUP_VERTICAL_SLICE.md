# Gnarl Popup Vertical Slice

Status: FOUNDATION B IMPLEMENTATION TEST - TECHNICAL BUILD GREEN - IN-GAME ACCEPTANCE PENDING

This document defines the first visual validation pass for Gnarl's quest presentation. It contains no OVERLORD REIGN story canon.

## Purpose

The first vertical slice deliberately uses Questlog 3.3.3's existing per-quest presentation fields rather than adding a custom renderer before one is proven necessary.

The development definition is:

`examples/questlog/quests/overlord_gnarl_popup_dev.json`

The portrait asset is:

`common/src/main/resources/assets/questlog/textures/gui/overlord/gnarl_popup.png`

The complete manual procedure is recorded in `docs/FOUNDATION_B_TEST_PROTOCOL.md`.

## Current design decision

The popup Gnarl character design is approved for this milestone.

The approved baseline preserves the established portrait and changes only the pupil treatment:

- pupils are square;
- pupils look toward the player;
- each pupil remains perspective-aligned to the eye it sits within;
- the original snout geometry is preserved;
- Gnarl retains the established sly/non-angry expression.

Do not otherwise blend the popup portrait with the current WIP in-game Gnarl model yet. Full cross-alignment is deferred until the model has a stable face, ears, body silhouette, cloak, and lantern rig. The controlled comparison criteria are recorded in `docs/GNARL_VISUAL_ALIGNMENT.md`.

This approval locks the popup character design for Foundation B. It does not lock parchment placement, portrait scale, or screen composition. Those remain subject to the in-game vertical-slice test below.

## Approved asset integration

The approved portrait is now physically present at the runtime resource path above. The repository keeps the exact approved 1254 x 1254 RGBA source rather than silently resampling the reviewed artwork.

Mechanical validation of that binary reports:

```text
sha256: 699140666288f84fea0e916c0f77ad719e25acdec60f65d3f8838a0e616444ed
size: 1154559 bytes
dimensions: 1254 x 1254
PNG color type: RGBA
fully transparent pixels: 713423
partially transparent pixels: 858065
```

Questlog still renders the texture into the fixture's 160 x 160 overlay rectangle. Source texture resolution and on-screen presentation size are therefore separate concerns.

The approved asset and current popup implementation passed GitHub Actions run `34528438059` at commit `51319a85fde584e4b44c95b43c6a55b1e3f444c5`. That establishes a green technical build, not visual acceptance.

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

## Test installation

The GitHub Actions build publishes a dedicated `overlord-quests-gnarl-popup-test-kit` artifact prepared in an instance-shaped layout.

The current green technical gate produced test-kit artifact ID `10172531627` and Forge artifact ID `10172530649`.

Install the OVERLORD QUESTS JAR, ensure a separate upstream Questlog JAR is not present, place the development quest under `config/questlog/quests/`, and launch an unpublished local single-player test world. Do not use Open to LAN for the acceptance pass.

Use a disposable test world or reset quest state before repeating the unlock test. Remove the debug stick before resetting so the inventory objective cannot immediately retrigger.

## Static validation aids

`tools/check_gnarl_popup_layout.py` mirrors the current `QuestDetails` placement constants and reports horizontal/vertical clipping plus the description-rectangle intersection.

`tools/check_gnarl_popup_asset.py` validates the repository PNG's mechanical contract, including dimensions, PNG integrity, alpha content, size, and SHA-256. It does not assess whether the picture matches the approved character design.

Static checks are not acceptance evidence. Minecraft still needs to render the popup directly.

## Acceptance

This vertical slice is accepted only after direct in-game review. A successful build or static report alone does not lock the composition.

If native overlay controls remain stable and readable, the renderer should stay unchanged and the presentation can remain data-driven. If clipping, scaling, anchoring, layering, or interaction problems cannot be corrected through the existing fields, the next implementation pass may introduce a dedicated Gnarl speaker/portrait primitive.
