# Gnarl Visual Alignment Checklist

Status: DESIGN / IMPLEMENTATION REFERENCE

This document controls visual consistency between the quest-popup Gnarl and the separate in-game Gnarl model. It does not establish new OVERLORD REIGN story canon.

## Current design decision

The existing popup Gnarl remains the visual baseline for OVERLORD QUESTS.

The only approved change to that baseline at this stage is:

- replace the current pupils with square pupils.

Do not otherwise redesign the popup portrait to match the current in-game model while that model remains work in progress.

The square-pupil edit is a visual correction only. It does not authorize changes to eye size, eye placement, brow shape, head silhouette, coloration, clothing, lantern, pose, or parchment composition.

## Current in-game model reference

The supplied WIP `gnarl_model.geo.json` uses `geometry.gnarl` with a 64 x 64 texture atlas. Its current silhouette is built around:

- a 9 x 6 x 6 main head cuboid;
- a forward 4 x 2 x 2 muzzle element plus a broader forward facial element;
- extremely wide planar ears, each 12 x 5, rotated outward from the head;
- a narrow 7 x 11 x 4 body;
- long thin arms;
- short legs;
- a large planar cape/drape;
- a multi-segment overhead lantern support and hanging lantern.

These measurements describe the WIP implementation. They are not automatically design targets for the popup portrait.

## Alignment hierarchy

When the in-game model becomes stable enough for a cross-pass, compare the two Gnarl representations in this order.

### Tier 1: identity-critical silhouette

These should agree strongly before any lower-priority details are considered:

- extreme ear width relative to the skull;
- flattened, broad facial read;
- heavy brow and recessed eye region;
- short projecting muzzle rather than a long goblin snout;
- hunched, narrow-bodied proportion;
- lantern rig clearly rising above and behind the body.

If a future in-game model revision materially changes one of these traits, do not automatically propagate it to the popup. Review which representation better matches the approved Gnarl reference first.

### Tier 2: face anchors

Compare:

- pupil shape and apparent scale;
- eye separation;
- brow-to-eye spacing;
- muzzle width and projection;
- jaw/chin width;
- cheek silhouette;
- beard/whisker placement when present in the finalized model.

Square pupils are already approved for the popup and should become the common pupil-shape target unless a later explicit design decision replaces that choice.

### Tier 3: costume and equipment

Compare only after the face and silhouette are stable:

- cloak/cape mass and drop;
- red chest cloth placement;
- lantern housing silhouette;
- lantern support curvature and lean;
- visible straps, rigging, or attachment points;
- hand and forearm exposure from the cloak.

The popup does not need to expose every structural detail used by the 3D model. It only needs enough matching landmarks that both assets unmistakably depict the same Gnarl.

### Tier 4: color and light behavior

The supplied model set contains a normal texture and an emissive texture. During final alignment, verify:

- skin hue family;
- cloth and cloak value range;
- eye glow color;
- lantern glow color;
- which details are emissive versus merely bright in the base texture;
- whether the popup exaggerates glow for readability without changing the underlying color identity.

The 2D popup is allowed to use stronger painted contrast than the Minecraft entity texture. Exact pixel-for-pixel color matching is not required.

## Popup-specific invariants

Unless explicitly revised by the Overlord, preserve these while aligning against the final model:

- the current popup illustration remains the primary 2D design baseline;
- only the pupils are presently scheduled for redesign;
- the portrait should read clearly at quest-popup scale before close-detail fidelity is considered;
- transparency around Gnarl must remain clean so the portrait can overlap parchment without a rectangular backdrop;
- the portrait must not be forced into the geometric stiffness of the Minecraft model;
- no model-derived detail should be added merely because it exists in the `.geo.json`.

## In-game model-specific invariants

Do not alter the WIP model merely to imitate the 2D popup. The model must remain viable as a Minecraft/GeckoLib entity. In particular:

- major parts must remain independently animatable where animation requires it;
- ear silhouette must survive ordinary gameplay camera distances;
- the lantern rig must remain readable in profile and three-quarter views;
- thin decorative forms may use planes where appropriate;
- model proportions should be judged in rendered 3D, not only from UV texture appearance.

## Cross-alignment review gate

Do not perform a full visual merge until the in-game Gnarl model has a stable face, ears, body silhouette, cloak, and lantern rig.

At that point produce side-by-side checks at minimum for:

1. front view;
2. three-quarter view;
3. side silhouette;
4. popup portrait at actual quest UI scale;
5. in-game entity at ordinary player viewing distance.

For every discrepancy classify it as one of:

- POPUP SHOULD CHANGE;
- MODEL SHOULD CHANGE;
- INTENTIONAL MEDIUM DIFFERENCE;
- UNKNOWN / NEEDS DESIGN DECISION.

No discrepancy should be resolved by silently averaging both versions together.

## Current status

POPUP BASELINE: PLANNED / retained.

SQUARE PUPILS: PLANNED / approved design change, asset edit pending.

FULL POPUP / MODEL MIX: NOT APPROVED YET. Defer until the in-game model reaches the cross-alignment review gate.
