# OVERLORD QUESTS Adaptation Contract

Status: FOUNDATION A COMPLETE / FOUNDATION B ACTIVE, APPROVED PORTRAIT INTEGRATED, TECHNICAL GATE GREEN

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

Preserving an upstream system does not forbid correctness or authority hardening around it. The adaptation keeps the inherited behavior surface while rejecting malformed, stale, or unsupported state that the original client could send to the server.

## OVERLORD adaptation targets

The initial targets are:

1. Establish a green Forge 47.4.10 build from the exact 3.3.3 baseline. **COMPLETE.**
2. Create and validate the Gnarl visual presentation layer without disturbing quest mechanics. **ACTIVE, APPROVED PORTRAIT INTEGRATED, TECHNICAL ASSET/BUILD GATE COMPLETE, MANUAL IN-GAME REVIEW PENDING.**
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

`DefinitionUtil` loads approved definitions bundled under `assets/questlog/overlord/definitions/` before reading external config definitions.

The bundled `index.json` explicitly lists packaged quests and chapters. External definitions in `config/questlog/` load afterward and therefore remain higher-priority overrides for development and pack maintenance.

The repository validator checks the bundled manifest and prevents development fixtures from being promoted through that path. The manifest currently contains no story quests or chapters.

The in-game editor remains a config-layer editor. Because config definitions are reconstructed under the technical `questlog` namespace, editor save/remove packets reject other namespaces rather than allowing an ID that cannot round-trip through the loader.

Quest/chapter definitions that cross the Questlog network use an explicit 32,767 Java-character ceiling. Repository-controlled definitions are validated before build, editor packets enforce the same limit, and config/bundled definitions are rejected at load time if their compact JSON cannot safely cross that wire boundary. An oversized external quest is represented by the ordinary broken-quest fallback rather than loading successfully and failing later during synchronization.

## Definition validation contract

The repository validator is hardened against the actual built-in objective and reward registries rather than validating generic JSON shape only. It checks known `questlog:` type IDs, recursive logic objectives, choice rewards, registry/tag matcher syntax, built-in required fields, panel/sound/overlay fields, and development-content boundaries.

A self-test suite exercises positive and deliberately invalid definitions and runs in normal Forge CI before production/example validation. Custom non-`questlog` namespaces remain permitted extension points so future OVERLORD compatibility objectives can define their own schemas without the bootstrap validator inventing them prematurely.

Runtime choice-reward rules are intentionally stricter than upstream: `pick_count` must be satisfiable, a choice reward cannot `auto_claim`, and nested choice rewards are rejected because the current claim packet has no representation for nested selection state. The repository validator must mirror these restrictions before production content is bundled.

This validation layer is technical. Passing it does not imply that a quest is approved story content, balanced, or correct for OVERLORD REIGN progression.

## Quest engine hardening

`visit_position` supports an optional `dimension` field. When absent it retains inherited coordinate-only behavior. When present, both the configured dimension and bounding box must match before the objective progresses. This prevents future location objectives from being accidentally satisfied by the same coordinates in another dimension. No canonical coordinates are assigned by this capability.

The inherited editor metadata for `questlog:trample` advertised a block filter even though `TrampleObjective` ignores such a field and listens specifically for farmland-trample events. The misleading block input has been removed from the editor metadata so authoring UI and runtime semantics agree.

Quest persistence is positional inside each prerequisite, objective, failure, and reward list. Once a production quest has live saved progress, reordering those entries should therefore be treated as a save migration concern rather than a harmless JSON cleanup.

The Triggers 1.0.1 shared event bus cannot unregister one listener. Hot-reloading definitions can therefore leave listeners belonging to old Objective instances registered. OVERLORD QUESTS avoids clearing the shared bus, which could break other consumers, and instead makes stale Objective instances inert by checking that their parent Quest is still the current QuestManager instance before state mutation.

The private Questlog event bus no longer relies on runtime generic inference through TypeTools. All owned registrations use explicit event classes, which makes precise unregistering deterministic and removes an unnecessary runtime library dependency from the fork.

The client-to-server packet surface has also been hardened. Reward collection, read acknowledgement, repeatable reset, and quest/chapter editor operations now validate their server-side sender and authoritative state before acting. Reward collection rejects early claims and invalid indices. Choice selections are validated before state mutation. Editor operations require permission level 2, reject unsupported namespaces, reject malformed save JSON, and confine filesystem operations to their intended config roots.

Detailed source-derived capability notes are maintained in `docs/QUEST_ENGINE_CAPABILITY_AUDIT.md`.

## Foundation B: Gnarl presentation

The active milestone is direct in-game validation of Gnarl's quest popup.

Questlog 3.3.3 already supports the relevant presentation primitives on individual quests, including `show_popup_on_unlock`, custom background and peripheral textures, arbitrary panel dimensions and offsets, and an unrestricted overlay texture positioned relative to the left panel. The first visual vertical slice continues to exploit those native controls before changing rendering code.

The popup-character baseline is locked: square pupils, player-directed gaze, perspective alignment to each eye, unchanged snout geometry, and the established sly/non-angry expression.

The exact user-supplied portrait uploaded to `main` is integrated unchanged at `assets/questlog/textures/gui/overlord/gnarl_popup.png`. Both the main upload and runtime resource resolve to Git blob `40c74f6613f0cc23fbf6be0911dedfcfae82865b`; runtime validation records SHA-256 `699140666288f84fea0e916c0f77ad719e25acdec60f65d3f8838a0e616444ed`. It is displayed in the current development fixture through a 160 x 160 overlay rectangle, so source texture resolution is not the same thing as on-screen size.

The integrated portrait passed PNG integrity and alpha-content validation. The technical Foundation B pipeline has also passed definition validation, static layout reporting, Java 17 Forge compilation, reobfuscation, assembled-JAR inspection, test-kit preparation, and artifact upload. Current popup queue and engine hardening continue through the same full workflow.

The separate in-game Gnarl model remains work in progress and is not a source for automatic popup redesign. Full cross-alignment is deferred until the model reaches the review gate defined in `docs/GNARL_VISUAL_ALIGNMENT.md`.

Automatic full-screen popup behavior remains single-player only by explicit project decision. The client code keeps a named local-singleplayer gate around the popup queue rather than expanding the feature to multiplayer.

The popup queue now delays automatic full-screen presentation while any other GUI is active. It does not replace inventory, container, chat, editor, or other screens. When normal gameplay resumes, the queue resolves the current quest by ID and opens only if it is still triggered. Duplicate queued IDs are suppressed, logout clears queue state, and the unpublished-singleplayer scope is checked again immediately before display.

The trigger sound remains bound to the trigger event, not delayed popup display, so queue deferral must not produce a second identical cue when the screen eventually opens.

If the native overlay path cannot achieve a stable readable Gnarl layout at normal GUI scales, only then should the renderer receive a dedicated speaker/portrait field.

## Fork closure policy

The fork is now in closure mode rather than open-ended Questlog redevelopment.

Generic Questlog refactoring is out of scope. Additional engine changes are justified only by a concrete OVERLORD REIGN quest requirement, a reproducible defect discovered during the Foundation B in-game test, or an actual modpack compatibility failure.

The imported upstream CurseForge, Modrinth, and external wiki publication tooling has been removed. This project produces private/local and GitHub Actions artifacts only unless the Overlord explicitly establishes a publication target.

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
- an automatic popup does not steal focus from another active GUI;
- a temporarily deferred queued popup resumes after the GUI closes rather than becoming stranded;
- the composition remains usable across the agreed GUI scales/window sizes;
- the native Questlog overlay path is either accepted or rejected based on observed behavior rather than assumption.

Parchment placement, portrait scale, and final UI composition remain implementation-test values until that review is complete.
