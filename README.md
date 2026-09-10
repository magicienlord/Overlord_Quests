# OVERLORD QUESTS

OVERLORD QUESTS is the quest and narrative presentation layer being built for **OVERLORD REIGN**, targeting Minecraft Java 1.20.1 on Forge 47.4.10.

The project begins from Infernal Studios' **Questlog 3.3.3** source baseline and preserves its proven JSON quest engine while adapting the presentation around Gnarl and the Overlord setting.

## Current status

**Foundation A is complete. Foundation B, Gnarl presentation validation, is active on `gnarl-bootstrap`. Its technical asset/build gate is green; direct in-game acceptance remains pending.**

The approved Gnarl popup portrait is integrated in the runtime resources and has passed PNG integrity, alpha-content, static-layout, Forge compilation, reobfuscation, assembled-JAR, and test-kit packaging checks. The portrait remains subject to direct Minecraft review for placement, clipping, readability, and GUI-scale behavior before Foundation B can close.

The Forge project builds successfully on Java 17 against Forge 47.4.10, passes the repository quest-definition validator and validator self-tests, survives the reobfuscation stage, passes assembled-JAR smoke checks, and uploads a usable Forge artifact through GitHub Actions.

OVERLORD REIGN is a single-player project. Automatic full-screen quest popups are intentionally scoped to unpublished local single-player worlds; LAN-published and dedicated multiplayer sessions are outside the target runtime for this presentation layer.

The imported upstream baseline is pinned exactly to Questlog commit `72edfa8cc2a411265ad20a916ce0282ec7be56c0`. The original Apache 2.0 license and credits are retained. The supplied upstream Forge JAR is preserved under `reference/` for binary comparison.

## Technical baseline

- Minecraft Java 1.20.1
- Forge 47.4.10
- Java 17
- Upstream engine: Questlog 3.3.3
- Technical mod id during the initial compatibility phase: `questlog`
- Artifact prefix: `overlord-quests`
- Intended gameplay runtime: single-player

Keeping the `questlog` technical id initially is deliberate. It avoids needless breakage of config paths, commands, JSON IDs, packet channels, saved quest state, and existing integrations while the visual and content systems are adapted.

## Build

The Forge development artifact is built with:

```text
./gradlew :forge:build
```

GitHub Actions self-tests the OVERLORD definition validator, validates OVERLORD quest examples, checks the Gnarl popup asset and static layout contract, builds and reobfuscates the Forge artifact, verifies required classes/resources inside the assembled JAR, and uploads both the normal Forge artifact and a Foundation B test kit.

## Adaptation strategy

The first implementation phase keeps Questlog's underlying quest state machine, objectives, rewards, synchronization, editor, and JSON format intact. Work is concentrated on the OVERLORD REIGN presentation layer, Gnarl-facing quest delivery, packaged project content, and modpack-specific integrations.

Bundled definition support is already implemented: approved quest and chapter definitions may ship inside the mod JAR, while `config/questlog/` files remain higher-priority overrides. The bundled manifest is intentionally empty until actual OVERLORD REIGN story content is approved.

The repository validator now checks the source-defined built-in objective and reward surface, recursive logic/choice structures, registry-tag matchers, and required fields. Preparatory engine hardening is documented separately and does not authorize story content.

Story text, quest progression, rewards, and world-specific objectives are not being invented by the bootstrap. Those will be added only from approved OVERLORD REIGN design and canon decisions.

See `docs/OVERLORD_ADAPTATION.md` for the engineering contract, `docs/GNARL_POPUP_VERTICAL_SLICE.md` for the active presentation test, `docs/GNARL_VISUAL_ALIGNMENT.md` for Gnarl asset-alignment rules, `docs/FOUNDATION_B_TEST_PROTOCOL.md` for the manual acceptance procedure, `docs/FOUNDATION_B_HANDOFF.md` for the current milestone state, `docs/QUEST_ENGINE_CAPABILITY_AUDIT.md` for preparatory engine capability findings, and `UPSTREAM_BASELINE.md` for provenance.
