# OVERLORD REIGN Narrative Facts

Status: TECHNICAL IMPLEMENTATION OF APPROVED QUEST ARCHITECTURE

## Authority

`magicienlord/Overlord_Lore_and_Canon` defines Main Quest markers as runtime representations of real narrative facts, requires sidequests to leave persistent quest facts or completion state where appropriate, and requires sparse conditional architecture based on explicit facts rather than numeric reputation or morality systems.

This document defines how OVERLORD QUESTS represents those facts technically. It does not define any canonical fact ID or story event.

## Model

A narrative fact is a world-scoped `ResourceLocation` that means one authored historical statement has become true.

Examples in this document use synthetic development IDs only:

```text
questlog:dev_fact_open
questlog:dev_provider_closed
```

Production IDs must be chosen by campaign content from the authoritative lore and campaign design. The engine does not infer them from NPC names, factions, locations, or mod content.

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

For hidden campaign state transitions, authors should normally use `auto_claim: true`. A manual claim is appropriate only when the act of claiming the visible reward is itself intentionally the moment the fact becomes true.

The reward is monotonic. It has no `clear` form.

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

Use a named narrative fact when the truth is materially reusable outside the quest that first established it. Examples of legitimate categories from the approved architecture include discovery, local-ruler outcomes, crisis resolution, Tower restoration milestones, NPC survival/death, branch outputs, persistent capability unlocks, and other persistent consequences.

Do not create facts for:

- arbitrary numerical progress;
- generic friendliness or reputation accumulation;
- cosmetic statistics with no later consequence;
- every trivial objective step;
- information already represented cleanly by an ordinary completed quest and never queried independently.

Where simple quest completion is sufficient, `questlog:quest_complete` remains the smaller representation.

## Cross-mod capability markers

Narrative facts may also serve as the quest-side representation of a permanent capability that another OVERLORD REIGN mod owns, provided the external integration contract treats the fact as a durable, idempotent marker rather than reaching directly into Questlog internals.

The approved first use is Minion-type progression. The planned Minions Remastered fork owns four Minion-type slots. Crafting its staff unlocks Brown directly. Red, Green, and Blue remain locked until campaign quests establish three later unlock markers.

On the Questlog side, those later unlocks fit the narrative-fact model because they are:

- boolean;
- monotonic in normal gameplay;
- persistent across save/reload;
- reusable by later quests if campaign design needs to know which Minion types have been acquired;
- materially meaningful outside the quest that first grants them.

The exact production fact/marker IDs are currently UNKNOWN and must not be invented until the Minions Remastered fork exposes its final integration surface and campaign authoring identifies the relevant unlock quests.

See `docs/MINION_UNLOCK_INTEGRATION.md` for the cross-mod ownership and reconciliation contract.

## Development fixtures

`examples/questlog/quests/overlord_narrative_fact_dev.json` validates a fact prerequisite and an auto-claimed set-fact reward using synthetic `questlog:dev_*` IDs.

`examples/questlog/quests/overlord_provider_fact_dev.json` validates provider `required_facts` and `forbidden_facts` using synthetic IDs.

Neither fixture is production story content or setting canon.
