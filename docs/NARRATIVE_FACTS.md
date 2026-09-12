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

### `overlord_reign:civilizations/ribbits/contact_established`

Category: civilization-anchor contact milestone.

Set by `campaign/civilizations/ribbits/first_contact` after the Overlord completes the first formal interaction with the designated Gardener Elder at the principal Ribbit Village.

Meaning:

- the designated Ribbit anchor polity has formally entered the current Overlord's campaign history;
- the provider is both the authored local Elder and an actual native `ribbits:gardener` profession in the installed Ribbits 3.0.5 implementation;
- later Ribbit content may distinguish established contact from unrelated procedural Ribbit Villages and Gardeners.

This fact does NOT mean:

- that the Ribbit civilization disposition has been resolved;
- that the village is NEUTRAL, SUBJUGATED, HOSTILE, destroyed, or otherwise politically settled;
- that every Ribbit Village recognizes the Overlord's authority;
- that exact village coordinates have been fixed in canon;
- that later Ribbit branch outcomes have been selected;
- that Ribbits' native peaceful behavior or trade systems have been globally rewritten.

The fact records local formal contact only and preserves the source mod's ordinary Ribbit identity outside the authored anchor.

### `overlord_reign:civilizations/kobolds/contact_established`

Category: civilization-anchor contact milestone.

Set by `campaign/civilizations/kobolds/first_contact` after the Overlord completes the first formal interaction with the designated Captain of the principal Kobold Den.

Meaning:

- the designated Kobold anchor polity has formally entered the current Overlord's campaign history;
- the provider is the exact installed `kobolds:kobold_captain` entity selected for the principal Den and marked with the authored local anchor identity;
- later Kobold content may distinguish established contact with that Den from unrelated Kobold Dens, ordinary Captains, and Pirate Kobolds.

This fact does NOT mean:

- that the Kobold civilization disposition has been resolved;
- that the Den is NEUTRAL, SUBJUGATED, HOSTILE, destroyed, or otherwise politically settled;
- that the selected Captain has authority over every Kobold population;
- that Pirate Kobolds have entered the same political relationship;
- that exact Den coordinates have been fixed in canon;
- that later Kobold branch outcomes have been selected;
- that Kobolds 2.12.0 native trade or combat behavior has been globally rewritten.

The fact records formal contact with one designated Den only. Political state and later consequences remain separate authored systems.

### `overlord_reign:civilizations/sea_dwellers/contact_established`

Category: civilization-anchor contact milestone.

Set by `campaign/civilizations/sea_dwellers/first_contact` after the Overlord completes the first formal interaction with the designated Sea Elder at the principal Sea Village.

Meaning:

- the designated Sea Village has formally entered the current Overlord's campaign history;
- the provider belongs to the exact installed `#seadwellers:mermorphs` native entity family and carries the authored local Sea Elder role;
- later Sea Dweller content may distinguish the selected village from unrelated Sea Villages and naturally occurring Mermorphs.

This fact does NOT mean:

- that Sea Dweller disposition has been resolved;
- that the village is NEUTRAL, SUBJUGATED, HOSTILE, destroyed, or otherwise politically settled;
- that every Sea Dweller recognizes the Overlord's authority;
- that exact village coordinates have been fixed in canon;
- that native Sea Dweller trade behavior has been globally rewritten;
- that any removed or nonexistent Ocean Dragon progression is part of the campaign.

The fact records formal contact with one designated Sea Village only.

### `overlord_reign:civilizations/dwarves/contact_established`

Category: civilization-anchor contact milestone.

Set by `campaign/civilizations/dwarves/first_contact` after the Overlord completes the first formal interaction with the designated Forge-Thane of the Golden Hills successor hold.

Meaning:

- the designated Dwarven successor hold has formally entered the current Overlord's campaign history;
- the provider is the exact installed `dwarven_forge:dwarf` entity with native `minecraft:toolsmith` profession, which the source mod presents as Dwarven Forger;
- the authored Forge-Thane identity applies to that local political anchor rather than every Dwarven Forger.

This fact does NOT mean:

- that Dwarven disposition has been resolved;
- that the successor hold is NEUTRAL, SUBJUGATED, HOSTILE, destroyed, or otherwise politically settled;
- that every Dwarf recognizes the Forge-Thane as a universal ruler;
- that the old Golden Hills kingdom has been restored;
- that exact hold coordinates have been fixed in canon;
- that native Dwarven amethyst trade behavior has been globally rewritten.

The fact records formal contact with the designated successor hold only.

### `overlord_reign:civilizations/umvuthana/contact_established`

Category: civilization-anchor first-audience milestone.

Set by `campaign/civilizations/umvuthana/first_contact` after the Overlord completes the legitimate mask-gated audience with the designated canonical Umvuthi.

Meaning:

- the Overlord has formally met the creator-god and political center of the designated Grove;
- the audience was entered through the exact installed Umvuthana-mask recognition path rather than by globally disabling Umvuthi hostility;
- the designated Grove has crossed from pre-audience hostility into the explicit local political state written by the same quest.

This fact does NOT mean:

- that all Umvuthana or all Umvuthis are neutral;
- that the designated Umvuthi is subjugated;
- that the native boss/destructive route has been removed;
- that a player marked by Mowzie's Mobs as the Umvuthi's misbehaving player receives permanent immunity;
- that exact Grove coordinates have been fixed in canon.

Unlike the other current civilization contact facts, this audience also writes the designated Grove's disposition to `overlord_reign:neutral` because the newer Umvuthana decisions explicitly define first-audience completion as the transition to local neutrality. The historical contact fact and the current disposition remain separate state dimensions. See `docs/CIVILIZATION_DISPOSITIONS.md` and `docs/UMVUTHI_AUDIENCE_INTEGRATION.md`.

### `overlord_reign:civilizations/illagers/authority_established`

Category: local civilization authority milestone.

Set by `campaign/civilizations/illagers/break_the_bastille` after the Overlord has entered the designated Take a Pillage Bastille and personally killed the one `takesapillage:legioner` marked as that Bastille's authored local commander.

Meaning:

- the designated Bastille's local command has been broken by the Overlord;
- the local warband has received the hostile demonstration of authority required to move into later Illager political content;
- later Illager quests may distinguish this specific event from arbitrary combat against patrols, raids, outposts, mansions, unrelated Bastilles, or unmarked Legioners.

This fact does NOT mean:

- that Illager disposition has been resolved;
- that the designated Bastille is already NEUTRAL, SUBJUGATED, HOSTILE as a resolved political state, destroyed, or otherwise settled;
- that `takesapillage:legioner` is a native commander class;
- that every Legioner or every Illager recognizes the marked NPC's authored local role;
- that unrelated Illager warbands have been defeated or pacified;
- that exact Bastille coordinates have been fixed in canon;
- that later fearful/cowed or submission outcomes have been selected.

The commander role is an OVERLORD REIGN world-integration layer on one source-backed elite Bastille soldier because the exact installed Take a Pillage 1.0.3 implementation exposes no dedicated Bastille-leader entity or native commander role. See `docs/ILLAGER_BASTILLE_INTEGRATION.md`.

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
