# NEXT CONVERSATION HANDOFF

Repository: `magicienlord/Overlord_Quests`
Preserved implementation checkpoint: `423ab3290b743633be22580e01629af1dd3c74e1`
Implementation branch at packaging: `gnarl-bootstrap`
Preservation branch: `conversation-handoff-2026-09-12-run565`
Minecraft: Java 1.20.1
Forge: 47.4.10
Java: 17

## Mandatory first action

Re-fetch the live `gnarl-bootstrap` branch and compare it with the preserved checkpoint. The user explicitly warned that the conversation had been rolled back and repository files could be newer than expected. The repository, not conversation history, is the implementation source of truth.

Do not continue from the preservation branch by default. It exists to preserve this exact handoff state plus `HANDOFF/` records.

## Project authority and reporting

`magicienlord/Overlord_Lore_and_Canon` is the read-only lore, design, and source authority.

`magicienlord/Overlord_Quests` is the writable implementation authority.

Detailed campaign construction remains behind the spoiler firewall. Ordinary reports may describe framework capability, broad campaign structure, validation, and already-exposed opening logic, but should not reveal concealed branch outcomes, surprise encounters, reveal timing, boss framing, traps, or dialogue payoffs unless the Overlord explicitly asks.

## Current implementation state

### Presentation

The visual foundation is accepted. Incorporeal Questlog speakers use parchment quest content with a dedicated right reaction lane, while in-world providers use a separate parchment-family provider UI without portraits.

Approved incorporeal popup personnel currently include Gnarl, Mortis, and Quaver. Semantic reaction states are exactly:

`neutral`, `directive`, `mocking`, `approving`, `severe`

Speaker artwork is generated only when a speaker is actually needed by authored campaign content.

The accepted layout includes vertically centered speaker portraits, action controls beneath the parchment body rather than beneath the reaction lane, no-shadow provider-centered text, and lower dialogue scroll controls centered in their available control band.

### Provider system

The provider backend is server-authoritative for offer, accept, turn-in, and state transitions. It supports durable issuing-provider binding, non-persistent decline, authored state dialogue including completed aftermath, main-quest and narrative-fact gating, civilization disposition gating, entity/tag/role/dimension/location eligibility, native Villager profession matching, and explicit quest-anchor protection.

Development validation exists for provider state, profession matching, narrative facts, consequences, and anchor behavior.

Direct full-modpack runtime evidence already covers provider discovery, offer presentation, long-dialogue scrolling, acceptance, and transition to in-progress dialogue. Some persistence, same-provider turn-in, rejection-gate, interaction-distance, and newer profession-path robustness cases remain separate runtime checks.

### Narrative state and consequences

World-scoped explicit facts and civilization dispositions are implemented. Do not introduce a hidden global morality, corruption, friendship, domination, or reputation number.

Persistent failure consequences, explicit tagged target death tracking through `questlog:entity_died`, and quest-anchor protection exist. Ordinary destructive choices must not create hidden central-campaign dead ends.

### Sequence breaking

The campaign recognizes surviving native evidence where technically possible rather than forcing repeated actions. Implemented reusable surfaces include exact item-craft statistics, exact entity-kill statistics, persistent exact dimension history, persistent exact structure history, bounded position history, and native advancements. Native owner progression is preferred where available.

### Minions progression

The cross-mod interface is no longer blocked. The stable public progression surface from OVERLORD Minions Build #118 is authoritative:

`OverlordMinionProgression.unlock(server, MinionSlot.RED)`
`OverlordMinionProgression.unlock(server, MinionSlot.GREEN)`
`OverlordMinionProgression.unlock(server, MinionSlot.BLUE)`

Fixed order is Brown 0, Red 1, Green 2, Blue 3.

Brown requires no Questlog unlock and remains the Master's Staff bootstrap. Questlog provides `questlog:unlock_minion` for Red, Green, and Blue and `questlog:minion_unlocked` to verify authoritative owner state before later tiers open. Questlog does not manipulate the Minions Remastered roster, intercept the Master's Staff, or duplicate unlock persistence. Successful external handoff refreshes the quest graph and completed pending handoffs are retried idempotently on player load.

The remaining evidence gap is direct full-modpack runtime validation of the complete Brown to Red to Green to Blue sequence against the compatible Minions build.

### Campaign authoring

The first production campaign slice is bundled. It preserves a concurrent opening rather than forcing a tutorial corridor. Brown recovery is observed retrospectively through the Master's Staff craft. The first Tower infrastructure convergence delegates mechanical progression to Hot Iron and records the resulting Tower restoration fact.

After the opening and early recovery, campaign structure is semi-open and capability-driven. Civilization arcs are substantial parallel content but resolving every civilization is not required for central campaign completion. The world remains playable after the central ending.

### Death and ending presentation

The death-screen integration replaces only the exact vanilla death screen and preserves bounded delay, optional non-hardcore auto-respawn, manual skip, hardcore safety, lifecycle cleanup, revival compatibility boundaries, and the actual Minecraft death cause. Its current presentation is a restrained charcoal frame with dark-red accents and vanilla/localized death copy. VHS grain, scanlines, chromatic separation, cassette framing, heartbeat, breathing, and stock joke text are explicitly rejected.

The ending screen is still a guarded development scaffold for the End poem boundary only. Its preview gate defaults off, ordinary menu credits are untouched, and the original vanilla completion callback is preserved exactly once so the same world remains playable. Final ending content remains unassigned behind the spoiler firewall.

## Native-mod integration policy

Audit depth now scales with gameplay importance and state complexity.

LIGHTWEIGHT is appropriate for isolated bosses, structures, durable advancements, unique crafts, simple dimensions, or stable public API milestones.

STANDARD is appropriate for compact self-contained progression chains with several relevant milestones.

DEEP is appropriate when the mod may become campaign backbone, Tower infrastructure, magic progression, major dimension progression, civilization state, Minion progression, or another durable cross-mod state system.

Evidence quality never scales down. Prefer stable public APIs, durable native advancements, supported owner saved-state interfaces, persistent vanilla statistics, then durable world evidence. Do not duplicate owning-mod persistence or infer durable state from transient GUI/client conditions.

Stop a deep audit when the relevant capability graph, persistence and ownership boundary, and safe integration surface are understood.

## Latest Theurgy state

Theurgy received a DEEP technical audit because its gameplay can potentially matter as a multi-stage magical system.

Installed artifact baseline:

`theurgy-1.20.1-1.30.0-OverlordReign-A4R2.jar`
SHA-256: `ff42cb0c12b19191d9923af7f136d820fce2ac41d4d2cd0d4c85e4a17d050755`

Forensic evidence established that A4R2 changes only `AccumulationRecipe.class` and `SalAmmoniacAccumulatorBlockEntity.class` relative to the relevant upstream code surface, so upstream advancement resources remain applicable.

Verified native advancement signals include `theurgy:has_basic_rod`, higher rod milestones, and `theurgy:has_liquefaction_cauldron`. The exact `has_liquefaction_cauldron` criterion is inventory acquisition of `theurgy:liquefaction_cauldron`.

A development-only retrospective fixture exists at:

`examples/questlog/quests/overlord_theurgy_progression_dev.json`

Its runtime protocol is:

`docs/THEURGY_PROGRESSION_TEST_PROTOCOL.md`

The technical topology is understood well enough that no new generic objective is currently justified. The native `questlog:advancement` and persistent `questlog:item_craft_stat` surfaces cover the verified durable milestone classes.

Critically, technical evidence has NOT assigned a production Tower room, campaign role, story meaning, or mandatory progression importance to Theurgy. Those remain subject to lore and campaign authority.

## Sea Dwellers note

The prior exact 1.20.1 2.9.9 audit found nine usable Sea Dwellers advancements but no reliable Ocean Dragon quest, advancement, persistent Dragon state, or equivalent authoritative progression identifier. Treat the intended Ocean Dragon handoff as a technical source discrepancy until reliable owner evidence exists. Do not invent a surrogate progression trigger.

## Recommended resume sequence

After re-fetching and reconciling the live branch, continue production campaign work under the spoiler firewall. Apply the scaled native-mod audit policy rather than performing deep archaeology indiscriminately. Use deep treatment for central systems such as Theurgy only when gameplay/state complexity justifies it.

Do not assume the Theurgy development fixture is production content. If Theurgy is selected for a concrete production milestone later, use the verified native signals and then validate the chosen integration in the full modpack.

The most concrete outstanding cross-mod runtime validation remains the Minions Build #118 progression sequence. Whether that is the immediate next task should be decided from the live repository and current campaign needs at resume time rather than from this handoff alone.

## Packaging boundary

No implementation work was started after the handoff instruction. Handoff packaging only inspected current repository state, reran the already-existing failed CI job unchanged after an external Maven 502, created the dedicated preservation branch, and wrote `HANDOFF/` records.