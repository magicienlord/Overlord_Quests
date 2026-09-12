# OVERLORD QUESTS Presentation System

Status: IMPLEMENTED VISUAL FOUNDATION / DIRECT IN-GAME ACCEPTANCE PENDING

This document records the presentation boundary that must be stabilized before production campaign content is authored into OVERLORD QUESTS.

## Visual-foundation gate

Production campaign implementation remains blocked on direct acceptance of the coherent visual and interaction baseline.

The source implementation now provides:

- a dedicated Questlog parchment layout for remotely presented speakers;
- a disjoint right-side incorporeal speaker reaction lane;
- action-button placement centered beneath the parchment body;
- balanced responsive width allocation so constrained GUI scales preserve both parchment and readable reaction art;
- horizontal portrait clamping so speaker offsets cannot push art back across the parchment boundary;
- opt-in runtime cleanup for legacy portrait alpha/matte fringes, including transparent-edge colour bleed to avoid dark filtering halos;
- the locked five-state semantic reaction vocabulary;
- reaction-aware portrait lookup with a neutral/fallback path;
- a shared `OverlordPresentationTheme` used by both Questlog speaker and in-world provider surfaces for core parchment/header/action geometry;
- a Villager-Retaliation-derived provider interface restyled into the same Questlog parchment/button family without becoming the same interaction surface;
- static validators that prevent provider quests from accidentally acquiring the incorporeal portrait system.

Development fixtures remain non-canon scaffolding until the visual gate is accepted in Minecraft.

## Two presentation surfaces

OVERLORD QUESTS has two deliberately different NPC presentation classes.

### Questlog incorporeal speaker surface

This surface is for non-corporeal or remotely presented personnel who communicate through the Questlog framework, such as Gnarl and other future characters assigned to that presentation model.

The main parchment quest body occupies most of the composition. A dedicated area on the right is reserved for the active speaker reaction visual. The reaction visual is laid out as a separate surface and does not overlap the parchment body.

At narrower GUI scales, `OverlordSpeakerScreen` reduces the parchment and reaction lane proportionally rather than collapsing the reaction lane first. The parchment remains the dominant surface, while the reaction remains legible enough to carry the five-state visual language.

Quest action controls remain grouped beneath the parchment body. Controls are never anchored beneath the reaction pane.

Automatic popup delivery routes any quest with speaker presentation metadata to `OverlordSpeakerScreen`; ordinary Questlog entries without that metadata continue to use the inherited quest-details presentation.

Only characters using this incorporeal Questlog presentation receive a reaction-visual roster.

### In-world provider surface

The generalized NPC-provider system derived from the Villager Retaliation fork is for ordinary in-world quest givers and local NPC interactions.

Those providers do NOT receive the five-state reaction-visual roster merely because they can provide dialogue or quests. Their presentation is grounded in the actual in-world entity plus the provider interface.

`QuestProviderScreen` shares the broader OVERLORD QUESTS visual language with Questlog through parchment framing, title hierarchy, spacing, separators, stretchable parchment-style buttons, and the common `OverlordPresentationTheme`. It remains a distinct server-authoritative interaction surface and has no dependency on `SpeakerPresentation`.

## Reaction-state contract

The Questlog incorporeal speaker system uses five abstract reaction keys:

1. `neutral`
2. `directive`
3. `mocking`
4. `approving`
5. `severe`

These are semantic speaker states, not assumptions about human facial animation.

Each incorporeal speaker may later map the five keys to character-specific visuals through posture, expression, eye intensity, aura, gesture, lighting, props, or other appropriate treatment.

Quest definitions can already carry:

```text
speaker_id
speaker_reaction
speaker_pane_width
```

The framework can therefore author against the five semantic states before every final character-specific visual asset exists.

## Reaction portrait lookup

`SpeakerPortraitTextures` resolves visual assets from `speaker_id` and `speaker_reaction` through the stable convention:

```text
assets/questlog/textures/gui/overlord/speakers/<speaker namespace>/<speaker path>/<reaction>.png
```

Example for a hypothetical completed Gnarl roster:

```text
assets/questlog/textures/gui/overlord/speakers/overlord_reign/gnarl/neutral.png
assets/questlog/textures/gui/overlord/speakers/overlord_reign/gnarl/directive.png
assets/questlog/textures/gui/overlord/speakers/overlord_reign/gnarl/mocking.png
assets/questlog/textures/gui/overlord/speakers/overlord_reign/gnarl/approving.png
assets/questlog/textures/gui/overlord/speakers/overlord_reign/gnarl/severe.png
```

Resolution order is:

1. exact requested reaction asset;
2. the same speaker's `neutral` asset if the requested reaction art does not yet exist;
3. the quest's explicit `overlay` resource as the development/legacy fallback.

This means campaign authoring can lock the semantic reaction now. Missing art does not require changing quest state later when the visual roster is produced.

## Asset-production boundary

A complete visual roster is NOT required for every future incorporeal NPC before campaign authoring begins.

The reaction-state vocabulary is sufficient for quest implementation. When campaign work establishes that a specific incorporeal speaker is actually required, that character is added to the visual-production list and receives the necessary reaction assets.

Do not generate speculative reaction sets for characters that may never use the Questlog incorporeal-speaker surface.

The present implementation retains an explicit portrait resource on the quest as a development/fallback asset. The semantic `speaker_id` and `speaker_reaction` fields are the durable authoring contract.

## Portrait alpha cleanup

Speaker artwork should normally be delivered with clean transparency and requires no runtime processing.

For a legacy/source portrait whose transparent edge pixels contain a visible matte colour, an entry may opt into:

```text
speaker_alpha_cleanup: true
```

`SpeakerPortraitTextures` then creates a client-side dynamic texture once for that source resource. RGB on low/medium-alpha boundary pixels is borrowed from nearby opaque source pixels while authored alpha is retained. Fully transparent boundary pixels also receive the nearest opaque RGB with zero alpha instead of transparent black. This is standard edge colour bleed and prevents an interpolation path from replacing the original warm matte with a dark halo.

This is deliberately opt-in. It must not be applied indiscriminately to future spectral glows, smoke, magical auras, or other artwork where partially transparent colour is intentional.

The Gnarl development fixture enables this cleanup specifically because the current supplied portrait contains a visible red/orange edge matte in its low-alpha pixels.

Generated cleanup textures are released on client logout rather than being retained across sessions.

## Current Gnarl implementation target

The previous left-side overlapping prototype has been retired.

The current development definition uses:

```text
parchment width: 480
panel height: 200
speaker: overlord_reign:gnarl
reaction: neutral
speaker lane width: 184
portrait display rectangle: 176 x 176
speaker alpha cleanup: enabled
```

The dedicated speaker screen keeps Gnarl in the right-side lane, bottom-anchors the portrait beside the parchment, clamps horizontal offsets to the speaker lane, preserves the parchment as the dominant quest-information surface, and centers the Read/Done action under parchment.

When the full requested width does not fit, the parchment and reaction lane shrink proportionally. The renderer still preserves a minimum parchment width and a bounded reaction lane instead of allowing either surface to consume the other.

Direct Minecraft review still has authority over final spacing, scale, edge quality, and GUI-scale behavior. Passing CI establishes build correctness, not visual acceptance.

## Presentation validation boundary

`tools/validate_presentation_contracts.py` guards the structural contract. In particular it rejects a definition that combines an in-world `provider` with the incorporeal speaker reaction pane and checks that both presentation surfaces retain their common theme boundary.

`tools/check_gnarl_popup_layout.py` mirrors the responsive parchment/speaker-lane geometry across representative GUI widths, verifies that the two surfaces remain disjoint, checks that parchment remains dominant, and checks for horizontal or action-button clipping.

The Forge workflow still requires an actual build and JAR smoke pass after these checks.

## Minion visual-identity dependency

OVERLORD REIGN will have Brown, Red, Green, and Blue Minion visual types through the planned Minions Remastered visual fork. Spawned Minions are assigned one of the four visual types while retaining the gameplay behavior of the underlying Minions Remastered implementation.

Quest and dialogue authoring may therefore recognize the four traditional Minion identities as lore and presentation categories without assuming four separate AI/combat implementations.
