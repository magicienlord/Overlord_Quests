# OVERLORD QUESTS

OVERLORD QUESTS is the quest and narrative presentation layer being built for **OVERLORD REIGN**, targeting Minecraft Java 1.20.1 on Forge 47.4.10.

The project begins from Infernal Studios' **Questlog 3.3.3** source baseline and preserves its JSON-driven quest engine while adapting the presentation around Gnarl and the Overlord setting.

## Current status

**Foundation A is complete. The visual-foundation gate is closed and production campaign authoring is underway from the lore/source authority.**

The first Gnarl runtime pass proved popup delivery, the READ interaction, quest transition, completion flow, and queue lifecycle. Direct visual review then established the parchment-dominant composition, dedicated right-side reaction lane, and shared provider visual language as a solid base. The review requested three final refinements: vertically center the incorporeal portrait against the parchment body, remove the doubled-looking provider text caused by the active font/resource stack, and move dialogue Up/Down controls into the middle of the available lower parchment whitespace. Those corrections are implemented and structurally guarded. The Overlord explicitly waived an additional visual regression pass once those requested changes were confirmed implemented.

The visual foundation separates two NPC presentation systems:

- **Questlog incorporeal speakers** use the dedicated parchment + right-side reaction lane. Their semantic reaction vocabulary is locked to `neutral`, `directive`, `mocking`, `approving`, and `severe`. Action controls stay beneath parchment.
- **In-world providers** use the Villager-Retaliation-derived server-authoritative provider framework. They do not receive reaction portraits. Their screen has been restyled into the same parchment/separator/button family so both surfaces belong to OVERLORD QUESTS without becoming the same interface.

Reaction art does not need to exist for every future incorporeal speaker before campaign authoring. Definitions store `speaker_id` and `speaker_reaction`; the renderer resolves a speaker-specific reaction asset when available, falls back to that speaker's neutral asset, then to the entry's explicit development/legacy portrait. Concrete visual rosters are requested only when the hidden campaign actually establishes that a speaker is needed.

The Forge project builds on Java 17 against Forge 47.4.10, passes repository validators, survives the reobfuscation stage, passes assembled-JAR smoke checks, and produces dedicated validation kits through GitHub Actions. Run 376 completed successfully after an unchanged retry of a transient NeoForged Maven HTTP 502 dependency-resolution failure.

Additional implementation surfaces on the active branch include:

- an NPC sidequest-provider backend with server-authoritative offer/accept/turn-in handling, explicit non-persistent decline, authored state-specific dialogue, bounded overflow scrolling, durable provider binding, main-quest marker and narrative-fact gating, entity/tag/role/dimension/location eligibility, authored civilization-disposition gating, and development/admin validation commands;
- an Epic Death Screen-derived mechanical scaffold that keeps useful timing, skip, respawn, hardcore, and lifecycle behavior while excluding the source mod's VHS/cassette presentation and soundscape;
- first-class optional objective semantics: top-level `optional: true` objectives persist and display normally but do not gate quest completion or provider turn-in and freeze unfinished progress once the quest completes;
- explicit world-scoped narrative facts for sparse historical state and provider gating rather than a hidden morality/reputation score;
- retrospective exact-entity kill and exact-item craft tracking for sequence-break-safe campaign hooks where Minecraft's persistent statistics provide reliable surviving evidence.

Direct full-modpack runtime testing has validated provider discovery, offer presentation, long-dialogue scrolling through its terminal sentinel, explicit acceptance, transition to authored in-progress provider dialogue, the shared visual baseline for Gnarl/provider presentation, and the basic death-screen replacement path.

The first production campaign slice is now bundled from the read-only lore/source authority. It establishes the concurrent opening structure rather than a single forced tutorial chain, includes the Brown Minion recovery bootstrap through the verified Minions Remastered Master's Staff craft, and uses retrospective crafting statistics so a legitimate early craft is not invalidated by quest activation timing. The external Minions Remastered fork remains authoritative for the actual Brown slot unlock and for later Red, Green, and Blue slot progression.

The provider engine renders only dialogue explicitly authored in quest definitions and does not generate story speech. No canonical civilization IDs, faction outcomes, or death-screen lore are invented by the technical systems.

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

Keeping the `questlog` technical id is deliberate. It avoids needless breakage of config paths, commands, JSON IDs, packet channels, saved quest state, and existing integrations while presentation and content systems are adapted.

## Build

The Forge development artifact is built with:

```text
./gradlew :forge:build
```

GitHub Actions self-tests the definition validators, provider/narrative/presentation contracts, runtime-required fields, optional-objective semantics, dependency graphs, definition-cache and editor authority, event-listener lifecycle, definition wire-size limits, sequence-break tracking contracts, death-screen theme boundary, and the Gnarl popup asset/static layout contract. It then builds and reobfuscates the Forge artifact, performs assembled-JAR smoke checks, and uploads the normal Forge artifact plus dedicated Gnarl popup, NPC-provider, and death-screen validation kits.

## Adaptation strategy

The fork keeps Questlog's underlying quest state machine, objectives, rewards, synchronization, editor, and JSON format. Work is concentrated on OVERLORD REIGN presentation, Gnarl-facing quest delivery, packaged project content, and modpack-specific integrations.

Bundled definition support is implemented: approved quest and chapter definitions ship inside the mod JAR, while `config/questlog/` files remain higher-priority overrides. The bundled manifest now contains the first source-authorized production opening slice alongside the separately maintained non-canon development fixtures under `examples/`.

The repository validator checks the source-defined built-in objective and reward surface, recursive logic/choice structures, registry-tag matchers, specialized runtime-required fields, provider/disposition/narrative-fact extensions, authored provider dialogue/location fields, optional-objective placement and runtime semantics, speaker/reaction presentation boundaries, retrospective exact-stat objectives, and bundled-content boundaries. Definition loading and packet handling enforce the same synchronization-size contract so an oversized external definition cannot load successfully and fail only when a player sync occurs.

The in-game editor keeps integrated-server definition authority on the server side for chapter membership changes and can directly author runtime-supported failure-condition objective trees alongside prerequisites, objectives, rewards, and settings. Existing failure conditions are no longer opaque editor-preserved JSON. Quest and chapter IDs are checked client-side against the retained `questlog` namespace before save. Optional-objective and speaker-presentation authoring are currently source-definition driven rather than exposed as dedicated editor controls.

Generic Questlog redevelopment is not an active project goal. Engine changes are made when an approved OVERLORD REIGN requirement, active campaign content, an active presentation test, or a demonstrated modpack integration requires them.

Inherited upstream CurseForge, Modrinth, and external wiki publication tooling has been removed. This fork produces private/local and GitHub Actions artifacts unless the Overlord explicitly establishes another publication target.

## Authority and content gate

`magicienlord/Overlord_Lore_and_Canon` is the read-only design/source authority. `magicienlord/Overlord_Quests` is the implementation authority.

Story text, quest progression, rewards, canonical disposition states, and world-specific objectives are not invented from implementation convenience. With the visual foundation accepted, production campaign work proceeds from the lore/source authority behind the spoiler firewall without asking for routine approvals. Questions are reserved for genuinely unresolved decisions that materially affect lore, player experience, or technical feasibility.

See `docs/PRESENTATION_SYSTEM.md` for the locked presentation split and reaction contract, `docs/CAMPAIGN_AUTHORING_CONTRACT.md` for authority/spoiler rules, `docs/GNARL_POPUP_VERTICAL_SLICE.md` and `docs/FOUNDATION_B_TEST_PROTOCOL.md` for the speaker visual slice, `docs/NPC_PROVIDER_SYSTEM.md` and `docs/NPC_PROVIDER_TEST_PROTOCOL.md` for the provider system, `docs/EPIC_DEATH_SCREEN_INTEGRATION.md` and `docs/DEATH_SCREEN_TEST_PROTOCOL.md` for death-screen integration, `docs/OPTIONAL_OBJECTIVES.md`, `docs/NARRATIVE_FACTS.md`, and `docs/SEQUENCE_BREAK_TRACKING.md` for state authoring, `docs/QUEST_ENGINE_CAPABILITY_AUDIT.md` for source-derived engine findings, and `UPSTREAM_BASELINE.md` for provenance.
