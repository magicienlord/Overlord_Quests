# OVERLORD QUESTS

OVERLORD QUESTS is the quest and narrative presentation layer being built for **OVERLORD REIGN**, targeting Minecraft Java 1.20.1 on Forge 47.4.10.

The project begins from Infernal Studios' **Questlog 3.3.3** source baseline and preserves its JSON-driven quest engine while adapting the presentation around Gnarl and the Overlord setting.

## Current status

**Foundation A is complete. Foundation B, Gnarl presentation validation, is active on `gnarl-bootstrap`. The approved Gnarl portrait is integrated and the automated technical gate is green; direct in-game acceptance remains pending.**

The exact user-supplied Gnarl portrait uploaded to `main` is integrated unchanged in the runtime resources. The main upload and runtime asset share Git blob `40c74f6613f0cc23fbf6be0911dedfcfae82865b`; runtime validation records SHA-256 `699140666288f84fea0e916c0f77ad719e25acdec60f65d3f8838a0e616444ed`. The locked character target is square pupils, player-directed gaze, perspective alignment to each eye, unchanged snout geometry, and the established sly/non-angry expression.

The Forge project builds successfully on Java 17 against Forge 47.4.10, passes the repository quest-definition and runtime-contract validators, survives the reobfuscation stage, passes assembled-JAR smoke checks, and uploads both the Forge artifact and the Foundation B test kit through GitHub Actions.

OVERLORD REIGN is a single-player project. Automatic full-screen quest popups are intentionally scoped to unpublished local single-player worlds; LAN-published and dedicated multiplayer sessions are outside the target runtime for this presentation layer.

The imported upstream baseline is pinned exactly to Questlog commit `72edfa8cc2a411265ad20a916ce0282ec7be56c0`. The original Apache 2.0 license and credits are retained. The supplied upstream Forge JAR is preserved under `reference/` for binary comparison.

## Technical baseline

- Minecraft Java 1.20.1
- Forge 47.4.10
- Java 17
- Upstream engine: Questlog 3.3.3
- Technical mod id during the compatibility phase: `questlog`
- Artifact prefix: `overlord-quests`
- Intended gameplay runtime: single-player

Keeping the `questlog` technical id is deliberate. It avoids needless breakage of config paths, commands, JSON IDs, packet channels, saved quest state, and existing integrations while the presentation and content systems are adapted.

## Build

The Forge development artifact is built with:

```text
./gradlew :forge:build
```

GitHub Actions self-tests the OVERLORD definition validator, validates runtime-required fields, dependency graphs, definition-cache authority, event-listener lifecycle, and definition wire-size limits, checks the Gnarl popup asset and static layout contract, builds and reobfuscates the Forge artifact, verifies required classes/resources inside the assembled JAR, and uploads both the normal Forge artifact and a Foundation B test kit.

## Adaptation strategy

The fork keeps Questlog's underlying quest state machine, objectives, rewards, synchronization, editor, and JSON format. Work is concentrated on OVERLORD REIGN presentation, Gnarl-facing quest delivery, packaged project content, and modpack-specific integrations.

Bundled definition support is implemented: approved quest and chapter definitions may ship inside the mod JAR, while `config/questlog/` files remain higher-priority overrides. The bundled manifest is intentionally empty until actual OVERLORD REIGN story content is approved.

The repository validator checks the source-defined built-in objective and reward surface, recursive logic/choice structures, registry-tag matchers, specialized runtime-required fields, and bundled-content boundaries. Definition loading and packet handling also enforce the same synchronization-size contract so an oversized external definition cannot load successfully and fail only when a player sync occurs.

Generic Questlog redevelopment is now frozen. Additional engine changes should be made only when an approved OVERLORD REIGN quest cannot be represented correctly, the Foundation B in-game test exposes a reproducible defect, or an actual modpack compatibility problem is demonstrated.

Inherited upstream CurseForge, Modrinth, and external wiki publication tooling has been removed. This fork produces private/local and GitHub Actions artifacts unless the Overlord explicitly establishes another publication target.

Story text, quest progression, rewards, and world-specific objectives are not invented by the fork. Those are added only from approved OVERLORD REIGN design and canon decisions.

See `docs/OVERLORD_ADAPTATION.md` for the engineering contract, `docs/GNARL_POPUP_VERTICAL_SLICE.md` for the active presentation test, `docs/GNARL_VISUAL_ALIGNMENT.md` for Gnarl asset-alignment rules, `docs/FOUNDATION_B_TEST_PROTOCOL.md` for the manual acceptance procedure, `docs/FOUNDATION_B_HANDOFF.md` for the current milestone state, `docs/QUEST_ENGINE_CAPABILITY_AUDIT.md` for source-derived engine findings, and `UPSTREAM_BASELINE.md` for provenance.
