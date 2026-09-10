# OVERLORD QUESTS Adaptation Contract

Status: FOUNDATION A COMPLETE / FOUNDATION B ACTIVE

This file records engineering decisions for the Gnarl quest system. It is not a source of new OVERLORD REIGN world or story canon.

## Baseline

The source tree is derived from Infernal Studios Questlog 3.3.3, exact upstream commit `72edfa8cc2a411265ad20a916ce0282ec7be56c0`, under Apache License 2.0.

Target runtime:

```text
Minecraft Java 1.20.1
Forge 47.4.10
Java 17
```

OVERLORD REIGN itself is a single-player project. Automatic full-screen quest popups target an unpublished local integrated-server session only. LAN-published and dedicated multiplayer sessions are outside the project acceptance scope.

## Compatibility rule

During the initial adaptation phase the technical mod id remains `questlog`.

This is an implementation choice, not branding. It preserves the existing `config/questlog/` data layout, `/questlog` command surface, packet identifiers, saved quest state, default resource namespace, and interoperability while the fork is brought under OVERLORD REIGN control.

The user-facing name is `Overlord Quests`, and generated Forge artifacts use the `overlord-quests` prefix.

A future technical-id migration must be explicit and must include migration handling. It should not be done merely for cosmetic cleanliness.

## Preserved upstream systems

The following Questlog 3.3.3 systems are retained unless a concrete OVERLORD REIGN requirement demonstrates that they need modification:

- JSON-driven quests and chapters
- prerequisites, objectives, failures, and rewards
- repeatable and global quest behavior
- client/server quest synchronization
- notification badges and toasts
- popup-on-unlock behavior
- per-quest panel, peripheral, overlay, sizing, sound, and palette controls
- in-game quest editor
- external `config/questlog/quests` and `config/questlog/chapters` workflow

## OVERLORD adaptation targets

The initial targets are:

1. Establish a green Forge 47.4.10 build from the exact 3.3.3 baseline. **COMPLETE.**
2. Create and validate the Gnarl visual presentation layer without disturbing quest mechanics. **ACTIVE.**
3. Make OVERLORD REIGN quest content distributable with the project rather than relying on manual user setup alone, while retaining config overrides for development. **ENGINE COMPLETE, CONTENT MANIFEST INTENTIONALLY EMPTY.**
4. Add modpack-specific objective and reward bridges only when required by approved quest design. **NOT STARTED.**
5. Keep world-specific assumptions out of generic engine code so unfinished systems such as the final Dark Tower world and ongoing personal-mod backports do not block development. **ONGOING RULE.**

## Foundation A result

Foundation A is complete.

The validated Forge pipeline now:

- compiles on Java 17 against Forge 47.4.10;
- retains the narrow editor-button compatibility correction required by the current mapped Forge compiler path;
- runs the Forge reobfuscation stage successfully;
- verifies required common and Forge classes in the assembled JAR;
- verifies the Gnarl presentation asset and `META-INF/mods.toml` are packaged;
- uploads the resulting Forge artifact through GitHub Actions;
- retains the original Questlog license/provenance and reference JAR;
- does not ship unapproved OVERLORD REIGN story content.

The earlier compile-common-once experiment was rejected because it prevented the Forge Mixin refmap mapping file from being generated for `reobfJar`. The final solution keeps the normal loader source aggregation and applies the narrow concrete-button compatibility fix instead.

## Bundled definitions

`DefinitionUtil` now loads approved definitions bundled under `assets/questlog/overlord/definitions/` before reading external config definitions.

The bundled `index.json` explicitly lists packaged quests and chapters. External definitions in `config/questlog/` load afterward and therefore remain higher-priority overrides for development and pack maintenance.

The repository validator checks the bundled manifest and prevents development fixtures from being promoted through that path. The manifest currently contains no story quests or chapters.

## Foundation B: Gnarl presentation

The active milestone is direct in-game validation of Gnarl's quest popup.

Questlog 3.3.3 already supports the relevant presentation primitives on individual quests, including `show_popup_on_unlock`, custom background and peripheral textures, arbitrary panel dimensions and offsets, and an unrestricted overlay texture positioned relative to the left panel. The first visual vertical slice continues to exploit those native controls before changing rendering code.

The approved popup-character baseline keeps the established portrait design. The approved face correction is square pupils that look toward the player while respecting each eye's perspective. Gnarl's existing snout geometry and non-angry expression are invariants and must not be altered by that correction.

The separate in-game Gnarl model remains work in progress and is not a source for automatic popup redesign. Full cross-alignment is deferred until the model reaches the review gate defined in `docs/GNARL_VISUAL_ALIGNMENT.md`.

Automatic full-screen popup behavior remains single-player only by explicit project decision. The client code keeps a named local-singleplayer gate around the popup queue rather than expanding the feature to multiplayer.

Foundation B has also repaired two popup-delivery defects found during source review: a queued popup can no longer become permanently stranded merely because the player was carrying a stack when the retry fired, and the quest trigger sound is no longer replayed when the delayed popup screen opens.

If the native overlay path cannot achieve a stable readable Gnarl layout at normal GUI scales, only then should the renderer receive a dedicated speaker/portrait field.

## Canon boundary

Bootstrap code must not invent quest chronology, named settlements, final coordinates, Dark Tower biome geometry, faction outcomes, or unapproved rewards.

The engine may provide test-only definitions for validation, but they must be unmistakably development content and must not be treated as canonical quests.

## Foundation B acceptance

Foundation B is complete only when direct in-game review confirms:

- the approved Gnarl portrait renders with clean transparency;
- the portrait is not clipped when extending outside the parchment at the accepted target GUI configuration;
- quest title and body text remain readable;
- popup-on-unlock works from an actual locked-to-unlocked transition in unpublished local single-player;
- the trigger cue occurs once rather than being replayed when the popup appears;
- a temporarily blocked queued popup resumes rather than becoming stranded;
- the composition remains usable across the agreed GUI scales/window sizes;
- the native Questlog overlay path is either accepted or rejected based on observed behavior rather than assumption.

Parchment placement, portrait scale, and final UI composition remain implementation-test values until that review is complete.
