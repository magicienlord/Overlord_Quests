# OVERLORD QUESTS

OVERLORD QUESTS is the quest and narrative presentation layer being built for **OVERLORD REIGN**, targeting Minecraft Java 1.20.1 on Forge 47.4.10.

The project begins from Infernal Studios' **Questlog 3.3.3** source baseline and preserves its proven JSON quest engine while adapting the presentation around Gnarl and the Overlord setting.

## Current status

**Foundation A is complete. Foundation B, Gnarl presentation validation, is in progress on `gnarl-bootstrap`.**

The Forge project now builds successfully on Java 17 against Forge 47.4.10, passes the repository quest-definition validator, survives the reobfuscation stage, passes assembled-JAR smoke checks, and uploads a usable Forge artifact through GitHub Actions.

The imported upstream baseline is pinned exactly to Questlog commit `72edfa8cc2a411265ad20a916ce0282ec7be56c0`. The original Apache 2.0 license and credits are retained. The supplied upstream Forge JAR is preserved under `reference/` for binary comparison.

## Technical baseline

- Minecraft Java 1.20.1
- Forge 47.4.10
- Java 17
- Upstream engine: Questlog 3.3.3
- Technical mod id during the initial compatibility phase: `questlog`
- Artifact prefix: `overlord-quests`

Keeping the `questlog` technical id initially is deliberate. It avoids needless breakage of config paths, commands, JSON IDs, packet channels, saved quest state, and existing integrations while the visual and content systems are adapted.

## Build

The Forge development artifact is built with:

```text
./gradlew :forge:build
```

GitHub Actions validates OVERLORD quest examples, builds and reobfuscates the Forge artifact, verifies required classes/resources inside the assembled JAR, and uploads the result.

## Adaptation strategy

The first implementation phase keeps Questlog's underlying quest state machine, objectives, rewards, synchronization, editor, and JSON format intact. Work is concentrated on the OVERLORD REIGN presentation layer, Gnarl-facing quest delivery, packaged project content, and modpack-specific integrations.

Bundled definition support is already implemented: approved quest and chapter definitions may ship inside the mod JAR, while `config/questlog/` files remain higher-priority overrides. The bundled manifest is intentionally empty until actual OVERLORD REIGN story content is approved.

Story text, quest progression, rewards, and world-specific objectives are not being invented by the bootstrap. Those will be added only from approved OVERLORD REIGN design and canon decisions.

See `docs/OVERLORD_ADAPTATION.md` for the current engineering contract, `docs/GNARL_POPUP_VERTICAL_SLICE.md` for the active presentation test, `docs/GNARL_VISUAL_ALIGNMENT.md` for Gnarl asset-alignment rules, and `UPSTREAM_BASELINE.md` for provenance.
