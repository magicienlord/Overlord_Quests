# OVERLORD QUESTS Campaign Authoring Contract

Status: PROJECT IMPLEMENTATION AUTHORITY CONTRACT

## Repository authority

Campaign work uses a strict two-repository boundary.

`magicienlord/Overlord_Lore_and_Canon` is read-only design and source authority. It contains the current OVERLORD REIGN lore decisions, franchise-source reconstruction, civilization decisions, campaign constraints, voice guidance, and documented unknowns.

`magicienlord/Overlord_Quests` is implementation authority. It contains the executable quest engine, authored campaign definitions, dialogue, provider rules, compatibility hooks, validation, and runtime-specific state handling.

OVERLORD QUESTS must consult the lore repository before inventing an answer or escalating an already-resolved question.

## Decision threshold

Normal quest-writing and implementation decisions do not require external approval when they are already constrained by:

- established OVERLORD REIGN canon or planned design;
- the primary-source Overlord game corpus;
- installed mod behavior that has been inspected or validated;
- existing technical architecture in this repository.

Escalation is reserved for a genuinely unresolved decision that cannot be safely inferred from those authorities and would materially affect lore, player experience, compatibility, or technical feasibility.

An unresolved detail must remain unresolved rather than being silently converted into story canon.

## Authority order for authored content

When writing or implementing campaign content, use this order:

1. explicit OVERLORD REIGN canon and project decisions in `Overlord_Lore_and_Canon`;
2. direct franchise primary-source evidence and its reconciled reference documents;
3. established REIGN design constraints and adoption decisions;
4. verified installed-mod mechanics and source-level compatibility facts;
5. narrow implementation inference needed to make the approved design executable.

Implementation inference may choose how to represent an established requirement. It may not decide an unresolved historical, geographical, character, or faction fact merely because the engine needs a value.

## Four-layer quest model

Campaign quests should preserve the source-derived separation between:

1. **State layer**: prerequisites, explicit facts, objectives, branches, completion state, provider state, civilization disposition, and persistent consequences.
2. **Gnarl layer**: opportunity/threat framing, direction, reminders, warnings, interpretation, branch framing, and reaction.
3. **World layer**: what actually changes in Minecraft or in installed-mod state because of the player's action.
4. **Record layer**: concise quest-log text recording the current objective and, where useful, the outcome actually achieved.

A quest is not complete as a design merely because its JSON objective can become true. The authored state, narration, world consequence, and historical record should agree.

## Gnarl writing boundary

Gnarl dialogue follows `Overlord_Lore_and_Canon/reference/GNARL_WRITING_RULES.md`.

Operational defaults include:

- prefer `Sire` and `Master` over repetitive use of `Overlord`;
- keep routine gameplay lines compact;
- convey actionable information before or alongside humor;
- treat cruelty as practical policy more often than theatrical self-description;
- give contempt a target and a gameplay purpose;
- preserve Gnarl's knowledge limits;
- distinguish directive, tactical, administrative, historical, mocking, reactive, reward, choice, ceremonial, and ominous registers;
- prepare lifecycle variants for important quests rather than only one acceptance and one completion line;
- preserve delivery-direction metadata during authoring.

Gnarl is an institutional witness and adviser, not an omniscient narrator and not a neutral tutorial voice.

## Explicit-state rule

OVERLORD REIGN does not use a hidden global numeric morality, corruption, friendship, or reputation meter as its campaign backbone.

Use explicit state instead:

- quest completion where that fact alone is sufficient;
- `questlog:fact` for reusable historical truths;
- civilization disposition for the current authored political state of a defined polity;
- provider eligibility and binding for NPC-sidequest ownership;
- native mod state where the installed system already exposes the authoritative progression signal;
- sparse branch outputs where a materially different later reaction is required.

Do not create a fact for every trivial objective step. Persistent facts should represent information that later content actually needs to know independently.

## Sequence-break rule

Exploration may let the player accomplish something before the campaign points toward it.

When that happens, the campaign should recognize reliable surviving evidence rather than requiring a duplicate boss, structure, event, or progression step. See `docs/SEQUENCE_BREAK_TRACKING.md` for the technical signal-selection hierarchy and retrospective exact-entity kill fallback.

Native progression remains authoritative where possible. Questlog frames, records, and reacts to installed-mod progression rather than replacing it without cause.

## Native-mod integration depth

Compatibility investigation scales with the gameplay importance and state complexity of the mod rather than applying a full forensic audit indiscriminately.

Use the LIGHTWEIGHT, STANDARD, and DEEP classes defined in `docs/NATIVE_MOD_INTEGRATION_POLICY.md`.

A simple boss, structure, advancement, or isolated capability normally needs only the narrow authoritative milestone and sequence-break check. A multi-stage system that may become campaign backbone, Tower infrastructure, magic progression, dimension progression, civilization state, or cross-mod persistent progression receives a deeper state/ownership audit.

Evidence quality does not scale down with audit depth. Every production hook still requires a verified installed mechanic or stable owning-mod contract. The depth class determines how much of the surrounding system must be understood before the hook is safe.

Stop a deep audit when the relevant capability graph, ownership boundary, persistence model, and safe integration surface are understood. Do not inspect unrelated internals merely because source is available.

## Sidequest rule

Sidequests should have self-interested motives appropriate to the world and the Overlord. They are not a generic heroic-help system.

Main-quest progression may unlock local provider pools through real narrative facts or completed quest markers. Declining an offer does not create hidden rejection points. If a sidequest matters later, its consequence must be represented explicitly through completion state, a named fact, a civilization outcome, a provider/world change, or another authored persistent result.

## Optional-objective rule

Optional objectives are appropriate when the optional action has a concrete authored purpose such as an additional consequence, reward, shortcut, cruelty, piece of intelligence, or other meaningful result.

They are not arbitrary bonus-score tasks. `docs/OPTIONAL_OBJECTIVES.md` defines the runtime semantics.

## Failure-consequence rule

Meaningful failure should normally become explicit authored history rather than a generic dead-end state.

Use top-level `failures` to detect the condition and `failure_rewards` only for the automatic persistent outputs that genuinely follow from that condition. Every `failure_rewards` entry must use `auto_claim: true`; choice rewards are not valid failure outputs because a failed quest has no success-reward selection flow.

Failure consequences are applied only after the quest has triggered. This prevents locked content from writing campaign history prematurely.

Typical outputs are sparse facts, a politically justified disposition change, or a narrowly controlled world transition. A retryable mechanical mistake should not be converted into permanent history merely because the engine can write a fact.

See `docs/FAILURE_CONSEQUENCES.md` for runtime, persistence, reset, and save-compatibility semantics.

## Civilization scope rule

Civilization questlines target the deliberately authored anchor polity defined by the lore authority. A disposition outcome for one anchor settlement, Den, Grove, Village, Camp, or equivalent does not automatically rewrite every naturally generated member of that species or culture across the world.

Provider selectors and compatibility hooks must therefore be narrow enough to distinguish the authored anchor population from unrelated procedural populations.

Quest-critical NPC anchors that must survive until an authored transition may use the explicit `overlord_quest_protected` scoreboard tag. Protection is separate from provider identity and must be removed by authored integration when a valid destructive route makes that NPC killable. See `docs/QUEST_ANCHOR_PROTECTION.md`.

When the death of a specific authored anchor is itself meaningful campaign state, use `questlog:entity_died` with that anchor's unique identity tag. Do not repurpose the inherited `questlog:entity_death` objective, because its historical meaning is that the player died to the matching entity. `entity_died` is event-driven, so the relevant quest or persistent tracking state must be active before a legitimate kill window opens unless another surviving signal records an earlier death. See `docs/ENTITY_DEATH_TRACKING.md`.

Exact coordinates remain a world-integration concern unless the lore authority has explicitly established them.

## Campaign concurrency

The campaign is not authored as one permanently selected linear objective. Multiple major objectives may coexist when the design permits it. Hard sequencing is reserved for genuine narrative or mechanical dependencies.

Civilization arcs are substantial but are not a mandatory checklist for completing the central campaign.

## Persistence and end state

Ordinary player death does not alter campaign canon.

The campaign is designed for a persistent playable world after its ending. End-state presentation and post-ending reactions must therefore preserve world continuity rather than treating completion as a disposable save-state boundary.

## Spoiler firewall

Internal campaign structure, branch conditions, reveals, outcome mapping, and hidden reactions belong in implementation data and internal design records.

Routine development reports should expose technical status and milestone completion without summarizing concealed campaign content. A spoiler should be surfaced only when the Overlord explicitly requests that material or when an unresolved decision cannot be explained without the relevant context.

This firewall does not prohibit implementing hidden campaign material. It governs what development communication reveals.

## Canon discipline

Development fixtures prefixed or described as `[DEV]` are non-canon and exist only to validate mechanics.

A technical example must never be promoted into story canon merely because it exists in a test kit or validator.

Production quest definitions should carry only lore and state that are supported by the authority chain above. New campaign implementation may operationalize established decisions, but unresolved questions stay unresolved until the design authority resolves them.
