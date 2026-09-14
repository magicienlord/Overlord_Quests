# Villager Civilization Test Protocol

Status: TECHNICAL / RUNTIME VALIDATION

This protocol validates the production Villager main-entry slice at one deliberately authored historical human remnant or successor settlement. It does not canonize the test settlement's coordinates, profession mix, historical site name, ruler, or final political disposition.

## Scope

Production quest:

```text
campaign/civilizations/villagers/first_contact
```

Exact provider contract:

- entity type: `minecraft:villager`;
- local anchor tag: `overlord_anchor:villager_main`;
- protection tag: `overlord_quest_protected`;
- civilization: `overlord_reign:villagers`;
- campaign gate: `overlord_reign:reign/initial_foundation_established`;
- result: `overlord_reign:civilizations/villagers/contact_established`;
- disposition: unchanged / technically unresolved unless another authored event has already written one.

No Villager profession is part of the main-entry contract.

## World-fit preparation

Use a disposable test copy of the intended OVERLORD REIGN world.

1. Select the deliberately authored biome-appropriate historical human remnant or successor settlement intended to anchor the Villager main arc.
2. Select exactly one existing `minecraft:villager` in that settlement as the local representative.
3. Add `overlord_anchor:villager_main` to that exact Villager.
4. Add `overlord_quest_protected` to that exact Villager.
5. Keep at least one untagged Villager in the same settlement and, where practical, one Villager in an unrelated settlement as controls.
6. Establish the campaign foundation fact through normal progression or the repository's administrative test surface.

The selected settlement must be justified by final-world historical, biome and terrain fit. This protocol does not convert an arbitrary procedural village into canon merely by tagging an NPC.

## Test A: exact local provider scope

1. Sneak/main-hand interact with the marked representative.
2. Confirm `A Village That Remembers` is offered.
3. Repeat the same gesture on an untagged Villager in the same settlement.
4. Repeat on a Villager in an unrelated settlement if available.

Expected:

- only the exact marked representative exposes the production main-entry quest;
- ordinary Villager trading and behavior remain available through their native interaction path;
- no profession is required by the quest-provider rule;
- unrelated Villagers do not inherit the authored political role.

## Test B: campaign gate

1. On a reset state, clear/withhold `overlord_reign:reign/initial_foundation_established`.
2. Interact with the marked representative.
3. Restore the foundation fact and interact again.

Expected:

- the production main entry is unavailable before the foundation fact;
- it becomes available after the fact without retagging or replacing the provider.

## Test C: contact completion semantics

1. Accept the production quest from the marked representative.
2. Confirm it binds to that exact provider.
3. Turn it in to the same provider.
4. Inspect narrative state.

Expected:

- `overlord_reign:civilizations/villagers/contact_established` becomes true exactly once;
- the quest remains completed;
- no Villager disposition is written by this contact;
- no universal human ruler, kingdom, capital or profession is created by the completion.

## Test D: anchor protection and native behavior

1. Attempt ordinary combat damage against the protected representative.
2. Confirm the anchor survives ordinary accidental combat.
3. Confirm untagged Villagers are not made invulnerable by this integration.
4. Confirm normal Villager AI and trading outside the Questlog sneak-interaction gesture remain native.

Expected:

- protection is local to the exact authored anchor;
- no species-wide behavior change occurs.

## Test E: persistence and locality

1. Save and quit after completing first contact.
2. Reload the same world.
3. Confirm the quest remains completed and the contact fact persists.
4. Visit or interact with unrelated Villager settlements.

Expected:

- persistence survives reload;
- unrelated settlements remain ordinary local communities;
- the one contact fact does not imply universal Villager submission, neutrality, hostility, alliance or recognition of one ruler.

## Pass criteria

The Villager main-entry slice passes only if one deliberately selected world-fit settlement representative provides the quest, contact persists without assigning a disposition, the protected anchor remains stable, and all unrelated Villagers retain native behavior.
