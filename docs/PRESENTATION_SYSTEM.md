# OVERLORD QUESTS Presentation System

Status: PLANNED IMPLEMENTATION / VISUAL FOUNDATION

This document records the presentation boundary that must be stabilized before production campaign content is authored into OVERLORD QUESTS.

## Visual-foundation gate

Production campaign implementation is blocked on a coherent visual and interaction baseline.

Before real campaign content is added, the project should stabilize:

- the Questlog parchment layout;
- the right-side incorporeal speaker reaction area;
- action-button placement beneath the parchment body;
- clean portrait alpha/silhouette rendering;
- shared spacing, framing, typography, and control hierarchy;
- the Villager-Retaliation-derived provider interface so it belongs to the same visual family without becoming the same interaction surface.

Development fixtures remain non-canon scaffolding until this gate is satisfied.

## Two presentation surfaces

OVERLORD QUESTS has two deliberately different NPC presentation classes.

### Questlog incorporeal speaker surface

This surface is for non-corporeal or remotely presented personnel who communicate through the Questlog framework, such as Gnarl and other future characters assigned to that presentation model.

The main parchment quest body occupies most of the screen. A dedicated area on the right is reserved for the active speaker reaction visual. The reaction visual must not overlap the parchment body.

Quest action controls remain grouped beneath the parchment body. Controls must not be stranded beneath the reaction pane.

Only characters using this incorporeal Questlog presentation receive a reaction-visual roster.

### In-world provider surface

The generalized NPC-provider system derived from the Villager Retaliation fork is for ordinary in-world quest givers and local NPC interactions.

Those providers do NOT receive the five-state reaction-visual roster merely because they can provide dialogue or quests. Their presentation is grounded in the actual in-world entity plus the provider interface.

The provider screen should share the broader OVERLORD QUESTS visual language with the Questlog parchment, including framing, typography, spacing, button treatment, and hierarchy, but it remains a distinct interaction surface.

## Reaction-state contract

The Questlog incorporeal speaker system uses five abstract reaction keys:

1. `neutral`
2. `directive`
3. `mocking`
4. `approving`
5. `severe`

These are semantic speaker states, not assumptions about human facial animation.

Each incorporeal speaker may later map the five keys to character-specific visuals through posture, expression, eye intensity, aura, gesture, lighting, props, or other appropriate treatment.

The framework may author quests against these five reaction keys before every final character-specific visual asset exists.

## Asset-production boundary

A complete visual roster is NOT required for every future incorporeal NPC before campaign authoring begins.

The reaction-state vocabulary is sufficient for quest implementation. When campaign work establishes that a specific incorporeal speaker is actually required, that character can be added to a visual-production list and supplied with the needed reaction assets.

Do not generate speculative reaction sets for characters that may never use the Questlog incorporeal-speaker surface.

## Current Gnarl correction target

The current Gnarl development popup is not a final visual baseline.

Required corrections include:

- eliminate visible alpha fringe/halo artifacts around the portrait silhouette;
- move Gnarl out of the parchment-body overlap;
- reserve the right side for reaction presentation;
- preserve the parchment as the dominant quest-information surface;
- preserve action controls beneath the parchment body;
- validate the composition at supported GUI scales before treating the layout as locked.

## Minion visual-identity dependency

OVERLORD REIGN will have Brown, Red, Green, and Blue Minion visual types through the planned Minions Remastered visual fork. Spawned Minions are assigned one of the four visual types while retaining the gameplay behavior of the underlying Minions Remastered implementation.

Quest and dialogue authoring may therefore recognize the four traditional Minion identities as lore and presentation categories without assuming four separate AI/combat implementations.
