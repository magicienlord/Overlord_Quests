# Foundation B: Incorporeal Speaker Visual Test Protocol

Status: VISUAL FOUNDATION ACCEPTANCE PROCEDURE

This protocol validates presentation behavior only. It does not establish OVERLORD REIGN story canon, chronology, rewards, or campaign progression.

## Test inputs

Use the current `gnarl-bootstrap` Forge artifact and:

`examples/questlog/quests/overlord_gnarl_popup_dev.json`

The development quest is deliberately not bundled as production story content.

## Runtime scope

Foundation B targets an unpublished local single-player world only.

Automatic full-screen speaker popups wait until normal screenless gameplay before opening. They must not replace an inventory, container, chat screen, Questlog editor, or other active GUI.

## Installation

1. Back up the test instance.
2. Remove any separate upstream Questlog JAR so exactly one implementation of technical mod id `questlog` remains.
3. Install the current OVERLORD QUESTS Forge JAR.
4. Copy `overlord_gnarl_popup_dev.json` into `config/questlog/quests/`.
5. Launch a disposable local single-player world with commands available and keep it unpublished.

The CI Gnarl test-kit artifact already contains the JAR and development definition in instance-shaped directories.

## Deterministic trigger

Before each run:

```text
/clear @s minecraft:debug_stick
/questlog reset_all_progress_and_reload
```

Then trigger the normal prerequisite transition:

```text
/give @s minecraft:debug_stick 1
```

Do not use `/questlog trigger` as the primary acceptance path.

## Required visual result

When the popup opens:

1. The parchment occupies the primary left/central area.
2. Gnarl appears in a dedicated reaction lane on the RIGHT.
3. No visible part of Gnarl overlaps the parchment body.
4. The action control is under the parchment body only.
5. There is no isolated button below Gnarl's reaction lane.
6. The source portrait's previous red/orange alpha fringe is no longer visibly objectionable after its opt-in runtime cleanup.
7. Title and description text remain readable and visually subordinate to neither portrait nor controls.
8. The portrait keeps its intended proportions and is not stretched to fill the speaker lane.
9. Parchment and portrait remain visually balanced at the normal instance GUI scale.

The current fixture uses `speaker_reaction: neutral`. This pass validates the reaction slot and semantic infrastructure, not a complete five-image Gnarl expression roster.

## Functional regression checks

The visual rework must preserve the previously working popup lifecycle:

- normal prerequisite completion opens the popup;
- unlock/completion toasts remain disabled in the development fixture;
- the development trigger sound occurs exactly once;
- the `questlog:read` objective remains usable;
- after the read acknowledgement, the control updates normally;
- closing the popup returns to gameplay without leaving a stale GUI state.

## Deferred-popup regression

The popup must still wait rather than replacing another active GUI.

A deterministic procedure is:

```text
/clear @s minecraft:debug_stick
/questlog reset_all_progress_and_reload
/summon minecraft:item ~ ~ ~ {Item:{id:"minecraft:debug_stick",Count:1b},PickupDelay:100s}
```

Immediately open inventory and remain close enough to collect the item after the delay.

Expected behavior:

1. the quest unlocks while inventory remains open;
2. the speaker popup does not replace inventory;
3. the unlock cue still occurs once;
4. after inventory closes, the queued popup opens on a later retry;
5. it uses the current quest definition rather than stale retained presentation state.

## GUI-scale matrix

At minimum, inspect:

- the normal OVERLORD REIGN GUI scale;
- one scale step smaller;
- one scale step larger when Minecraft permits it;
- one narrower window configuration where the parchment and speaker lane visibly contract.

The composition may compact at narrow sizes, but parchment and reaction lane must remain disjoint and the action must remain associated with parchment.

## Evidence to retain

Retain screenshots of:

1. the automatic popup at normal GUI scale;
2. the same popup one GUI scale step smaller or in a narrower window;
3. a close enough view of the Gnarl silhouette to judge the former coloured alpha fringe;
4. any configuration that clips, overlaps, stretches, or visually unbalances the composition.

Retain `latest.log` if the test reveals missing textures, dynamic-texture errors, quest-loading errors, packet/state anomalies, or repeated trigger events.

## Acceptance

Foundation B visual presentation can close only after direct Minecraft review confirms that the right-side speaker composition, portrait edge, parchment hierarchy, controls, and adjacent GUI scales are acceptable.

Until then, production campaign content remains blocked by the visual-foundation gate.
