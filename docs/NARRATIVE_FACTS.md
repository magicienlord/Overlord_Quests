# OVERLORD REIGN Narrative Facts

Status: TECHNICAL IMPLEMENTATION OF APPROVED QUEST ARCHITECTURE

## Authority

`magicienlord/Overlord_Lore_and_Canon` defines Main Quest markers as runtime representations of real narrative facts, requires sidequests to leave persistent quest facts or completion state where appropriate, and requires sparse conditional architecture based on explicit facts rather than numeric reputation or morality systems.

This document defines how OVERLORD QUESTS represents those facts technically and records stable production IDs once campaign authoring actually needs them.

## Model

A narrative fact is a world-scoped `ResourceLocation` that means one authored historical statement has become true.

Development examples use synthetic IDs such as:

```text
questlog:dev_fact_open
questlog:dev_provider_closed
```

Production IDs are chosen by campaign content from the authoritative lore and campaign design. The engine does not infer them from NPC names, factions, locations, or mod content.

Narrative facts are deliberately boolean and monotonic during normal gameplay:

- absent means the authored fact has not been recorded;
- present means it has become true;
- production quest rewards may add a fact;
- production quest rewards do not erase facts.

This makes facts suitable for persistent historical consequences without becoming a disguised numeric score.

Civilization disposition remains a separate exclusive-state mechanism. Use a disposition when exactly one current political state matters. Use a narrative fact when later content needs to know that a specific event or outcome happened.

## Quest definition surface

### Fact objective

```json
{
  "type": "questlog:fact",
  "fact": "overlord_reign:some_authored_fact"
}
```

A fact objective has an implicit `required_amount` of `1`. Any authored `required_amount` must also be exactly `1`.

On the logical server the objective reads `OverlordNarrativeState` directly. Its synchronized objective units are only a client presentation projection of the authoritative world fact.

Fact objectives are valid anywhere ordinary objective trees are valid, including prerequisites and logic objectives.

### Set-fact reward

```json
{
  "type": "questlog:set_fact",
  "fact": "overlord_reign:some_authored_fact",
  "auto_claim": true
}
```

The reward records the fact in world narrative state and immediately re-synchronizes active quest state so dependent fact objectives and provider gates react without waiting for an unrelated event.

For hidden campaign state transitions, authors should normally use `auto_claim: true`. A manual claim is appropriate only when claiming the visible reward is intentionally the moment the fact becomes true.

The reward is monotonic. It has no production `clear` form.

## Provider gates

Provider rules support two fact sets:

```json
"provider": {
  "required_facts": [
    "overlord_reign:fact_that_must_be_true"
  ],
  "forbidden_facts": [
    "overlord_reign:fact_that_must_not_be_true"
  ]
}
```

All `required_facts` must be present and every `forbidden_facts` entry must be absent before the quest can be accepted.

A provider definition that both requires and forbids the same fact is invalid.

As with disposition and ordinary quest-marker gating, fact gates apply to acceptance. They are not re-applied to an already accepted quest during turn-in. If a later narrative event should invalidate, fail, or redirect an active quest, that consequence must be authored explicitly rather than produced by silently making its provider inaccessible.

## Persistence

Facts are stored in the existing world-scoped `overlord_quests_narrative` `SavedData` alongside civilization dispositions.

The fact set is serialized deterministically and loads backward-compatibly when an older world has no `facts` field.

Fact IDs are history keys. Once production content ships, renaming a fact ID is a save migration and must not be treated as a cosmetic refactor.

## Administration and validation

Permission-gated commands are available for authoring and test recovery:

```text
/questlog narrative fact get <fact>
/questlog narrative fact set <fact>
/questlog narrative fact clear <fact>
```

`clear` exists only as an administrative/testing escape hatch. Its presence does not make narrative facts reversible campaign variables.

`set` and `clear` immediately re-synchronize active quest state.

## Authoring rules

Use a named narrative fact when the truth is materially reusable outside the quest that first established it. Legitimate categories from the approved architecture include discovery, local-ruler outcomes, crisis resolution, Tower restoration milestones, NPC survival/death, branch outputs, persistent capability unlocks, and other persistent consequences.

Do not create facts for:

- arbitrary numerical progress;
- generic friendliness or reputation accumulation;
- cosmetic statistics with no later consequence;
- every trivial objective step;
- information already represented cleanly by an ordinary completed quest and never queried independently.

Where simple quest completion is sufficient, `questlog:quest_complete` remains the smaller representation.

A semantic convergence fact may be appropriate when several implementation-level quest completions jointly establish one reusable campaign truth. In that case the fact should describe the stable meaning of the convergence rather than duplicate one source quest's completion state.

## Production fact registry

The following production fact IDs are currently defined by bundled campaign content.

### `overlord_reign:tower/forge_prepared`

Category: Tower restoration milestone.

Set when the first Tower forge preparation quest completes its native Hot Iron progression requirement.

Meaning:

- the Overlord has acquired the core smithing equipment needed to furnish the Tower's purpose-built forge chamber;
- the campaign may treat the forge restoration process as having reached its prepared-material stage;
- later Tower content may use this fact without depending directly on the implementation details of the originating quest.

This fact does NOT mean:

- that a specific anvil or workstation has been placed at a fixed world coordinate;
- that every future forge upgrade is complete;
- that Hot Iron progression has been exhausted;
- that the final architectural installation or world-state presentation has been implemented.

Those boundaries remain separate so the fact stays truthful even before exact Tower-room world integration is finalized.

### `overlord_reign:reign/initial_foundation_established`

Category: early campaign convergence milestone.

Set by `campaign/expansion/the_reign_takes_shape` after both of the following have been completed and acknowledged:

- Brown Minion recovery;
- the first practical Tower infrastructure restoration.

Meaning:

- the current Overlord has recovered the Brown tribe and established the first practical Tower infrastructure milestone;
- the opening foundation is complete enough for the campaign to enter its semi-open, capability-driven structure;
- later campaign and provider content may gate on one stable semantic marker instead of depending on the implementation details of two early quest branches.

This fact does NOT mean:

- that Red, Green, or Blue Minions have been restored;
- that any civilization disposition has been resolved;
- that any later Tower facility is complete;
- that any named region, dungeon, or settlement has been discovered;
- that Theurgy or any other optional native system has been assigned a mandatory story role;
- that a concealed later campaign branch or outcome has been selected.

The fact is intentionally broader than either source quest completion while remaining strictly bounded to the two established opening recoveries.

### `overlord_reign:civilizations/goblins/contact_established`

Category: civilization-anchor contact milestone.

Set by `campaign/civilizations/goblins/first_contact` after the Overlord accepts and completes the designated Goblin leader's first formal interaction at the principal Goblin Camp.

Meaning:

- the designated Goblin anchor polity has formally entered the current Overlord's campaign history;
- later Goblin content may distinguish established contact from an undiscovered or unrelated procedural Goblin population;
- the issuing leader and camp are the authored local polity defined by the Goblin civilization decisions, not every Goblin generated elsewhere.

This fact does NOT mean:

- that the Goblin civilization disposition has been resolved;
- that the camp is NEUTRAL, SUBJUGATED, HOSTILE, destroyed, or otherwise politically settled;
- that all Goblins recognize the Overlord's authority;
- that exact camp coordinates have been fixed in canon;
- that later Goblin branch outcomes have been selected.

The fact intentionally records contact only. Political state remains a separate authored disposition or consequence when later campaign content actually resolves it.

### `overlord_reign:civilizations/gnumus/contact_established`

Category: civilization-anchor contact milestone.

Set by `campaign/civilizations/gnumus/first_contact` after the Overlord completes the first formal interaction with the designated Elder Shaman at the principal Gnumu settlement.

Meaning:

- the designated Gnumu anchor polity has formally entered the current Overlord's campaign history;
- later Gnumu content may distinguish established contact from unrelated procedural Gnumu settlements;
- the Elder Shaman is an authored political role layered onto the source-backed `gnumus:gnumus_shaman` entity at the selected main settlement.

This fact does NOT mean:

- that the Gnumu civilization disposition has been resolved;
- that the settlement is NEUTRAL, SUBJUGATED, HOSTILE, destroyed, or otherwise politically settled;
- that every Gnumu settlement shares the anchor's later political state;
- that the Gnumus know their hidden Halfling ancestry;
- that exact settlement coordinates have been fixed in canon;
- that later Gnumu branch outcomes have been selected.

The ancestry boundary is deliberate. Formal contact does not reveal information that the current Gnumus canonically do not know.

## Cross-mod capability ownership

Not every persistent capability should be mirrored as a Questlog narrative fact.

The current Minion integration is the authoritative example. Build #118 of `Overlord_Minions` owns Minion progression state. Questlog uses:

```text
questlog:unlock_minion
questlog:minion_unlocked
```

The reward calls the public Minion progression API, and the objective/prerequisite reads owner state. Questlog does not duplicate Red, Green, or Blue unlock state in `OverlordNarrativeState` and does not require parallel Minion fact IDs.

Brown remains the Master's Staff bootstrap owned by the Minion system. Red, Green, and Blue remain sequential campaign-earned capabilities, but their durable capability state belongs to `Overlord_Minions`.

A separate narrative fact should be added around a Minion recovery only if campaign content later needs to remember a distinct historical statement that is not equivalent to "this Minion slot is unlocked". Such a fact must be justified by authored narrative semantics, not created as a technical mirror.

See `docs/MINION_UNLOCK_INTEGRATION.md` and `docs/MINION_RECOVERY_AUTHORING_CONTRACT.md` for the cross-mod ownership and authoring boundaries.

## Development fixtures

`examples/questlog/quests/overlord_narrative_fact_dev.json` validates a fact prerequisite and an auto-claimed set-fact reward using synthetic `questlog:dev_*` IDs.

`examples/questlog/quests/overlord_provider_fact_dev.json` validates provider `required_facts` and `forbidden_facts` using synthetic IDs.

Neither fixture is production story content or setting canon.
