# OVERLORD QUESTS

OVERLORD QUESTS is the quest and narrative presentation layer being built for **OVERLORD REIGN**, targeting Minecraft Java 1.20.1 on Forge 47.4.10.

The project begins from Infernal Studios' **Questlog 3.3.3** source baseline and preserves its proven JSON quest engine while adapting the presentation around Gnarl and the Overlord setting.

## Current status

Implementation foundation is in progress on the `gnarl-bootstrap` branch.

The imported upstream baseline is pinned exactly to Questlog commit `72edfa8cc2a411265ad20a916ce0282ec7be56c0`. The original Apache 2.0 license and credits are retained. The supplied upstream Forge JAR is preserved under `reference/` for binary comparison.

## Technical baseline

- Minecraft Java 1.20.1
- Forge 47.4.10
- Java 17
- Upstream engine: Questlog 3.3.3
- Technical mod id during the initial compatibility phase: `questlog`
- Planned artifact prefix: `overlord-quests`

Keeping the `questlog` technical id initially is deliberate. It avoids needless breakage of config paths, commands, JSON IDs, packet channels, saved quest state, and existing integrations while the visual and content systems are adapted.

## Build

The Forge development artifact is built with:

```text
./gradlew :forge:build
```

GitHub Actions also performs a Forge build on the active adaptation branch.

## Adaptation strategy

The first implementation phase keeps Questlog's underlying quest state machine, objectives, rewards, synchronization, editor, and JSON format intact. Work is concentrated on the OVERLORD REIGN presentation layer, Gnarl-facing quest delivery, packaged project content, and modpack-specific integrations.

Story text, quest progression, rewards, and world-specific objectives are not being invented by the bootstrap. Those will be added only from approved OVERLORD REIGN design and canon decisions.

See `docs/OVERLORD_ADAPTATION.md` for the current engineering contract and `UPSTREAM_BASELINE.md` for provenance.
