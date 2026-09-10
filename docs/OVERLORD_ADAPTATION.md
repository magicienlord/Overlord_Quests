# OVERLORD QUESTS Adaptation Contract

Status: IMPLEMENTATION FOUNDATION

This file records engineering decisions for the Gnarl quest system. It is not a source of new OVERLORD REIGN world or story canon.

## Baseline

The source tree is derived from Infernal Studios Questlog 3.3.3, exact upstream commit `72edfa8cc2a411265ad20a916ce0282ec7be56c0`, under Apache License 2.0.

Target runtime:

```text
Minecraft Java 1.20.1
Forge 47.4.10
Java 17
```

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

The first custom work is deliberately narrow:

1. Establish a green Forge 47.4.10 build from the exact 3.3.3 baseline.
2. Create the Gnarl visual presentation layer without disturbing quest mechanics.
3. Make OVERLORD REIGN quest content distributable with the project rather than relying on manual user setup alone, while retaining config overrides for development.
4. Add modpack-specific objective and reward bridges only when required by approved quest design.
5. Keep world-specific assumptions out of generic engine code so unfinished systems such as the final Dark Tower world and ongoing personal-mod backports do not block development.

## Gnarl presentation

The supplied `Gnarl_Popup_VisualTest.png` is the current visual reference for the first popup prototype.

Questlog 3.3.3 already supports the relevant presentation primitives on individual quests, including `show_popup_on_unlock`, custom background and peripheral textures, arbitrary panel dimensions and offsets, and an unrestricted overlay texture positioned relative to the left panel. The first visual vertical slice should exploit those native controls before changing rendering code.

If the native overlay path cannot achieve a stable readable Gnarl layout at normal GUI scales, only then should the renderer receive a dedicated speaker/portrait field.

## Canon boundary

Bootstrap code must not invent quest chronology, named settlements, final coordinates, Dark Tower biome geometry, faction outcomes, or unapproved rewards.

The engine may provide test-only definitions for validation, but they must be unmistakably development content and must not be treated as canonical quests.

## Build acceptance for Foundation A

Foundation A is complete when:

- `:forge:build` succeeds on Java 17 against Forge 47.4.10;
- the produced artifact is clearly named as OVERLORD QUESTS;
- upstream license/provenance remain present;
- the upstream reference JAR remains available for comparison;
- no story or progression content is silently promoted into the build.
