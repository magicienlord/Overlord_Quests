# OVERLORD QUESTS Presentation System

Status: IMPLEMENTED VISUAL FOUNDATION / DIRECT IN-GAME ACCEPTANCE PENDING

This document records the presentation boundary that must be stabilized before production campaign content is authored into OVERLORD QUESTS.

## Visual-foundation gate

Production campaign implementation remains blocked on direct acceptance of the coherent visual and interaction baseline.

The source implementation now provides:

- a dedicated Questlog parchment layout for remotely presented speakers;
- a disjoint right-side incorporeal speaker reaction lane;
- action-button placement beneath the parchment body;
- opt-in runtime cleanup for legacy portrait alpha/matte fringes;
- the locked five-state semantic reaction vocabulary;
- reaction-aware portrait lookup with a neutral/fallback path;
- a Villager-Retaliation-derived provider interface restyled into the same Questlog parchment/button family without becoming the same interaction surface;
- static validators that prevent provider quests from accidentally acquiring the incorporeal portrait system.

Development fixtures remain non-canon scaffolding until the visual gate is accepted in Minecraft.

## Two presentation surfaces

OVERLORD QUESTS has two deliberately different NPC presentation classes.

### Questlog incorporeal speaker surface

This surface is for non-corporeal or remotely presented personnel who communicate through the Questlog framework, such as Gnarl and other future characters assigned to that presentation model.

The main parchment quest body occupies most of the screen. A dedicated area on the right is reserved for the active speaker reaction visual. The reaction visual is laid out as a separate surface and does not overlap the parchment body.

Quest action controls remain grouped beneath the parchment body. Controls are never anchored beneath the reaction pane.

Automatic popup delivery routes any quest with speaker presentation metadata to `OverlordSpeakerScreen`; ordinary Questlog entries without that metadata continue to use the inherited quest-details presentation.

Only characters using this incorporeal Questlog presentation receive a reaction-visual roster.

### In-world provider surface

The generalized NPC-provider system derived from the Villager Retaliation fork is for ordinary in-world quest givers and local NPC interactions.

Those providers do NOT receive the five-state reaction-visual roster merely because they can provide dialogue or quests. Their presentation is grounded in the actual in-world entity plus the provider interface.

`QuestProviderScreen` now shares the broader OVERLORD QUESTS visual language with Questlog through parchment framing, title hierarchy, spacing, separators, and stretchable parchment-style buttons. It remains a distinct server-authoritative interaction surface and has no dependency on `SpeakerPresentation`.

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

`SpeakerPortraitTextures` then creates a client-side dynamic texture once for that source resource. Fully negligible alpha is removed and RGB on low/medium-alpha boundary pixels is borrowed from nearby opaque source pixels while the original alpha value is retained.

This is deliberately opt-in. It must not be applied indiscriminately to future spectral glows, smoke, magical auras, or other artwork where partially transparent colour is intentional.

The Gnarl development fixture enables this cleanup specifically because the current supplied portrait contains a visible red/orange edge matte in its low-alpha pixels.

Generated cleanup textures are released on client logout rather than being retained across sessions.

## Current Gnarl implementation target

The previous left-side overlapping prototype has been retired.

The current development definition uses:

```text
parchment width: 360
panel height: 200
speaker: overlord_reign:gnarl
reaction: neutral
speaker lane width: 176
portrait display rectangle: 168 x 168
speaker alpha cleanup: enabled
```

The dedicated speaker screen keeps Gnarl in the right-side lane, preserves the parchment as the dominant quest-information surface, and keeps the Read/Done action under parchment.

Direct Minecraft review still has authority over final spacing, scale, edge quality, and GUI-scale behavior. Passing CI establishes build correctness, not visual acceptance.

## Presentation validation boundary

`tools/validate_presentation_contracts.py` guards the structural contract. In particular it rejects a definition that combines an in-world `provider` with the incorporeal speaker reaction pane.

`tools/check_gnarl_popup_layout.py` audits the current parchment/speaker-lane geometry and checks that the declared portrait fits its lane.

The Forge workflow still requires an actual build and JAR smoke pass after these checks.

## Minion visual-identity dependency

OVERLORD REIGN will have Brown, Red, Green, and Blue Minion visual types through the planned Minions Remastered visual fork. Spawned Minions are assigned one of the four visual types while retaining the gameplay behavior of the underlying Minions Remastered implementation.

Quest and dialogue authoring may therefore recognize the four traditional Minion identities as lore and presentation categories without assuming four separate AI/combat implementations.
