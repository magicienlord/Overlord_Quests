# OVERLORD QUESTS Incorporeal Speaker Roster

Status: CANON IMPLEMENTATION DESIGN / ASSETS PLANNED AS NEEDED

## Purpose

This file records which established OVERLORD REIGN personnel are approved to use the Questlog incorporeal speaker popup surface. It is an implementation roster, not a requirement that every listed character appear immediately or receive a full reaction-art set before campaign content needs them.

The authoritative lore remains `magicienlord/Overlord_Lore_and_Canon`. The popup framework remains generic and does not hard-code this roster into runtime eligibility.

## Approved roster

### Gnarl

Status: ACTIVE IMPLEMENTATION

Speaker ID:

```text
overlord_reign:gnarl
```

Gnarl is the principal adviser and current production user of the popup surface. His five-state semantic reaction lane is the visual-foundation baseline.

### Mortis

Status: APPROVED FUTURE POPUP SPEAKER

Speaker ID reserved for campaign authoring:

```text
overlord_reign:mortis
```

Mortis survives into the current REIGN era and may participate through character-specific popup interjections rather than requiring a permanently spawned physical NPC. His franchise role remains tied to Minion life, death, the Spawning Pit, and resurrection administration. Campaign content must not expand that remit without source or REIGN authority.

No Mortis reaction-art roster is required until an authored quest actually uses him.

### Quaver

Status: APPROVED FUTURE POPUP SPEAKER

Speaker ID reserved for campaign authoring:

```text
overlord_reign:quaver
```

Quaver survives into the current REIGN era and is explicitly approved for the same non-corporeal popup presentation class as Gnarl and Mortis.

Primary-source material establishes Quaver as the Minion bard/minstrel, a participant in Netherworld court life, and a diegetic recorder/commentator on the Overlord's achievements and choices. That makes popup interjections suitable when campaign authoring needs his minstrel, commemorative, or reactive function. It does not make Quaver a generic narrator or substitute for Gnarl's strategic-adviser role.

No Quaver reaction-art roster is required until an authored quest actually uses him.

## Reaction contract

Approved speakers use the shared semantic reaction keys:

```text
neutral
directive
mocking
approving
severe
```

These keys describe presentation intent, not identical facial expressions or poses. Each speaker may interpret them through character-appropriate posture, props, lighting, gesture, aura, or expression.

Asset lookup remains convention-based:

```text
assets/questlog/textures/gui/overlord/speakers/<namespace>/<speaker path>/<reaction>.png
```

For example, Quaver's future neutral asset would resolve from:

```text
assets/questlog/textures/gui/overlord/speakers/overlord_reign/quaver/neutral.png
```

The existing renderer already supports these IDs without code changes. If an exact reaction asset is absent it falls back to that speaker's neutral asset, then to the quest's explicit fallback overlay.

## Asset-production rule

Do not generate complete five-image sets merely because a character is listed here. The production sequence is:

1. campaign authoring establishes an actual speaker use;
2. the quest definition stores the correct `speaker_id` and semantic `speaker_reaction`;
3. character-specific visual assets are produced when that use reaches the art boundary;
4. missing reaction art may temporarily use the established fallback chain during development.

This prevents speculative asset work while keeping approved personnel available to the hidden campaign.

## Boundary with in-world providers

The roster applies only to the incorporeal Questlog speaker surface.

Ordinary local NPCs and sidequest givers continue to use the server-authoritative in-world provider framework. A character being able to speak does not automatically qualify them for reaction portraits.

Additional high-value personnel may be added later only when the lore authority and campaign establish that this presentation model is appropriate.