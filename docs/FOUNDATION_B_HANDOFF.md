# OVERLORD QUESTS Foundation B Handoff

Status: ACTIVE VISUAL-FOUNDATION MILESTONE / SOURCE IMPLEMENTATION COMPLETE / MANUAL IN-GAME ACCEPTANCE PENDING

This handoff records repository-local development state, not OVERLORD REIGN world or story canon.

## Foundation A

Foundation A is complete and validated on Java 17, Minecraft 1.20.1, and Forge 47.4.10. The normal GitHub Actions build compiles, reobfuscates, verifies the assembled JAR, validates quest-definition contracts, and uploads the Forge artifact.

## Foundation B objective

Foundation B now validates the shared OVERLORD QUESTS presentation baseline before production campaign authoring begins.

The earlier native QuestDetails overlay prototype was sufficient to prove popup delivery, but direct in-game review rejected its final composition because Gnarl intruded into the parchment and the source alpha edge showed a coloured matte.

The replacement foundation therefore uses a dedicated Questlog incorporeal-speaker screen with a separate right-side reaction lane while preserving the inherited quest state machine and popup lifecycle.

## Locked presentation boundary

For Questlog-delivered incorporeal speakers:

- parchment is the dominant quest-information surface;
- the active speaker occupies a dedicated right-side reaction lane;
- portrait pixels must not intrude into parchment;
- action controls remain centered beneath parchment;
- no action control is placed beneath the reaction lane;
- the semantic reaction vocabulary is `neutral`, `directive`, `mocking`, `approving`, and `severe`;
- complete reaction art is produced only for incorporeal speakers the campaign actually uses.

For Villager-Retaliation-derived in-world providers:

- the actual world entity remains the visual NPC;
- providers do not receive an incorporeal five-image reaction roster;
- the provider screen shares parchment, title/separator, spacing, and button language with the speaker surface through `OverlordPresentationTheme`;
- provider interaction remains server-authoritative and mechanically distinct.

## Locked character-design target

The following Gnarl target is decided for this milestone:

- preserve the established popup Gnarl design;
- square pupils;
- player-directed gaze;
- each square pupil remains perspective-aligned with its eye plane;
- preserve the original snout geometry, muzzle volume, nostrils, and mouth/jaw relationship;
- preserve the original sly, amused, non-angry expression, including brows, eyelids, grin, and facial proportions;
- do not merge WIP in-game-model traits into the popup automatically.

The source portrait binary is retained unchanged. Presentation cleanup is performed at runtime only when the definition opts in.

## Repository portrait

The source portrait is present at:

`common/src/main/resources/assets/questlog/textures/gui/overlord/gnarl_popup.png`

Mechanical identity:

```text
git blob: 40c74f6613f0cc23fbf6be0911dedfcfae82865b
bytes: 1154559
sha256: 699140666288f84fea0e916c0f77ad719e25acdec60f65d3f8838a0e616444ed
dimensions: 1254 x 1254
PNG color type: RGBA
```

The remaining visual question is how the portrait composes after the current runtime edge cleanup and right-side layout inside Minecraft.

## Current composition

The development fixture requests:

```text
parchment width: 480
panel height: 200
speaker lane width: 184
portrait display rectangle: 176 x 176
speaker gap: 14
speaker reaction: neutral
speaker alpha cleanup: enabled
```

`OverlordSpeakerScreen` uses those dimensions when space permits. At narrower GUI widths it contracts parchment and reaction lane proportionally, preserving a minimum parchment width while preventing the reaction lane from collapsing immediately to its minimum.

The portrait is bottom-anchored beside the parchment. Its horizontal draw position is clamped to the reaction lane, so an authored portrait offset cannot recreate the old overlap defect.

The Read/Done control is centered beneath parchment only.

## Portrait alpha handling

The source PNG remains untouched in resources.

When `speaker_alpha_cleanup: true` is present, `SpeakerPortraitTextures` creates a client-side dynamic texture for the selected reaction/fallback source:

- low/medium-alpha boundary pixels borrow RGB from nearby opaque source pixels while preserving their alpha;
- fully transparent boundary pixels receive nearby opaque RGB with alpha zero rather than transparent black;
- generated textures are cached for the session and released on logout;
- cleanup remains opt-in so future spectral glow, smoke, aura, or other intentional translucent colour is not destroyed.

The current Gnarl development definition opts in because direct review identified a visible red/orange edge matte.

## Runtime scope: DECIDED

OVERLORD REIGN is a single-player project. Automatic full-screen Questlog speaker popups are intentionally limited to an unpublished local single-player world.

LAN-published worlds and dedicated multiplayer remain outside the target runtime for automatic full-screen presentation. Any fallback behavior continues to respect the current quest definition rather than inventing new story presentation.

## Development fixture

The development quest is:

`examples/questlog/quests/overlord_gnarl_popup_dev.json`

It uses `minecraft:debug_stick` as a deterministic prerequisite, one `questlog:read` objective, no rewards, and a harmless vanilla trigger sound used only to test exactly-once unlock audio.

Primary reset/trigger sequence:

```text
/clear @s minecraft:debug_stick
/questlog reset_all_progress_and_reload
/give @s minecraft:debug_stick 1
```

The fixture is development content and is deliberately excluded from the bundled production definition manifest.

## Popup reliability state

The automatic-popup path follows these lifecycle rules:

- popup entries are queued by quest resource ID rather than by retaining Quest objects;
- duplicate IDs are not queued simultaneously;
- any active GUI defers automatic full-screen presentation instead of being replaced;
- each retry resolves the current quest instance and current display data by ID;
- removed or reset quests are discarded before display;
- the unpublished-local-single-player scope is checked again at consumption time;
- logout clears the popup queue and retry state;
- unlock audio is emitted at the trigger event and is not replayed when a deferred popup eventually opens.

These safeguards still require the focused runtime regression described in `FOUNDATION_B_TEST_PROTOCOL.md`.

## Technical gate

The authoritative Forge pipeline covers:

- definition-validator self-tests;
- OVERLORD quest-definition validation;
- presentation-contract validation, including the provider/incorporeal split and shared theme tokens;
- specialized runtime-required objective/reward field validation;
- definition wire-size and loader-boundary validation;
- definition-cache authority validation;
- private event-bus lifecycle validation;
- Gnarl PNG integrity and alpha checks;
- responsive static popup-layout reporting;
- Java 17 Forge compilation and reobfuscation;
- assembled-JAR resource/class inspection;
- Foundation B, provider, and death-screen test-kit preparation;
- Forge artifact and test-kit upload.

A green workflow is necessary but cannot close Foundation B by itself.

## Visual gate still under test

The following remain subject to direct Minecraft acceptance:

- final perceived portrait scale in the reaction lane;
- parchment/reaction balance at the normal OVERLORD REIGN GUI scale;
- alpha-edge quality after runtime cleanup;
- neighboring GUI-scale behavior;
- title/body readability;
- whether the shared provider and speaker presentation now reads as one coherent UI family.

The underlying surface split, five reaction-state vocabulary, and action-button ownership are no longer open design questions.

## Canon boundary

Do not author production story quests, chronology, rewards, settlement assumptions, final Dark Tower coordinates/geometry, or faction outcomes during Foundation B.

The bundled production definition manifest remains intentionally empty.

## Remaining Foundation B gate

Foundation B has one direct acceptance gate:

1. install the latest green Gnarl popup test kit in the OVERLORD REIGN test instance and perform `docs/FOUNDATION_B_TEST_PROTOCOL.md`, retaining screenshots and relevant logs.

The runtime review must cover right-side placement, alpha edge, parchment hierarchy, text readability, GUI-scale behavior, exactly-once unlock audio, and deferred-popup reliability.

If the current dedicated speaker surface passes that review, production campaign authoring can proceed against the already-implemented semantic reaction system without requiring every future incorporeal speaker asset in advance.
