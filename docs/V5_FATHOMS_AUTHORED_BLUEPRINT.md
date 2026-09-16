# OVERLORD REIGN V5 Fathoms Authored Blueprint

Status: V5 QUEST BLUEPRINT

Purpose: define the Historian-led Overlord Depths / Fathoms investigation arc from the completed source-backed mechanics without importing Dredge's plot or forcing exhaustive native progression.

No final quest names or final dialogue are assigned here. Beat IDs are authoring references.

## 1. Campaign role

Fathoms is an optional investigation Adventure.

Established V5 meaning:

- centuries of oceanic contamination / leakage are one lasting consequence of the Cataclysm;
- this is parallel evidence to Nerakyss and the Cataclysm Leviathan, not subordinate to either;
- Fathoms may strengthen the player's understanding of the Cataclysm but is not required for the central ending;
- the Historian profession is the principal quest-facing NPC when available;
- the progression should move from ordinary maritime activity into increasingly disturbing evidence and a coherent investigation conclusion;
- native fishing collection, every fish, every ritual, every Jinx and every novelty item remain Fathoms-owned progression rather than a duplicate Questlog checklist.

The exact escalation and climax were deliberately delegated to Quest Maker discretion once the completed Fathoms mechanics could be inspected. This blueprint resolves that delegated work against the current 1.2.7 source-backed implementation.

## 2. Why the old production climax is not V5 authority

Older production used:

```text
use bait
-> catch aberration
-> open coffer
-> catch all fish
-> make a bad decision
```

The current source proves that `BAD_DECISION` is hard-gated by native completion of `fathoms:nautical/catch_all_fish`.

V5 therefore does not use `make_a_bad_decision` as its authored climax because doing so would make exhaustive fish collection mandatory for a narrative investigation arc.

`catch_all_fish` and `make_a_bad_decision` remain valid native Fathoms progression and may receive one contextual Historian or Gnarl Rambling when achieved.

## 3. Authored sequence

### FATHOMS-010: Encounter an aberration

Availability:

- naturally available after the central opening once ordinary fishing and Fathoms content can be encountered.

Visible accomplishment:

- catch one source-recognized aberrated fish or equivalent source-owned aberration catch.

Preferred proof:

- native Fathoms aberration catch progression / persistent fishing state.

Purpose:

- begin from a real abnormality rather than asking the player to go fishing because the mod exists;
- establish that something in the ocean ecology is materially wrong.

Presentation:

- this event should direct the player toward a Historian rather than immediately explain the Cataclysmic cause.

### FATHOMS-020: Put the evidence in learned hands

Prerequisite:

- `FATHOMS-010` complete.

Quest-facing NPC:

- a bound Historian becomes the principal provider / speaker for this Adventure.

Visible accomplishments:

- establish access to the Historian's Calligraphy Table / decoding capability;
- recover one source-owned encoded maritime text or Sunken Scrawl suitable for decoding;
- successfully decode it.

Purpose:

- turn the anomaly into an investigation;
- demonstrate that older maritime knowledge exists and that the strange ocean phenomena have enough history to have been studied before.

Implementation boundary:

- provider acceptance and turn-in may occur through the provider framework;
- visible completion must use the actual decoded-item state or another concrete source-owned signal, not generic entity interaction.

### FATHOMS-030: Recover a route into the disturbed waters

Prerequisite:

- `FATHOMS-020` complete.

Visible accomplishment:

- obtain a source-owned Rocky Waters Nautical Map or the exact equivalent quest-valid route marker;
- use that route to enter the naturally generated `fathoms:rocky_waters` region.

Purpose:

- move the investigation from anomalous catches and old writing into a physical affected region.

Authoring allowance:

- the bound Historian may provide the source-owned Rocky Waters map as the quest reward / lead once sufficient evidence is returned, rather than requiring random message-in-a-bottle grinding;
- this does not invent a new map mechanic, the source already supports persistent Rocky Waters Nautical Maps.

Technical proof:

- native Rocky Waters location history / biome or structure state where available.

### FATHOMS-040: Decode the deeper local evidence

Prerequisite:

- `FATHOMS-030` complete.

Visible accomplishment:

- recover and decode at least one Rocky Waters Sunken Scrawl associated with a source-owned local ritual.

Qualifying scrawls:

- `conjure_beasts`;
- `divine_gift`.

Structure:

- either qualifying local scrawl is sufficient;
- V5 does not require collecting both or completing the Ocean 4/4 plus Rocky Waters 2/2 scrawl catalogue.

Purpose:

- prove that the affected region has a deeper magical practice tied to the strange ocean materials;
- prepare a practical test rather than concluding the mystery from text alone.

### FATHOMS-050: Reproduce the phenomenon deliberately

Prerequisite:

- `FATHOMS-040` complete.

Visible accomplishment:

- use an Aberration Idol and source-valid fish essences to perform the decoded Rocky Waters ritual represented by the recovered scrawl;
- either successful `CONJURE_BEASTS` or successful `DIVINE_GIFT` closes the authored test.

Structure:

- source-valid OR closure matching the decoded scrawl;
- do not require maximum amplification or every ritual.

Purpose:

- demonstrate that the same strange material signatures found in aberrated marine life participate in a repeatable magical system;
- give the Historian and Gnarl enough concrete evidence to interpret the phenomenon within established REIGN history.

Historical interpretation:

- the observed corruption is consistent with the established centuries-long oceanic leakage left by the Cataclysm;
- this does not mean every Fathoms creature, artifact or ritual was created directly by the Cataclysm;
- this evidence parallels Nerakyss and the Sunken City / Leviathan contamination history rather than being caused by those entities.

Completion state:

- Fathoms investigation Adventure complete;
- remember that the Overlord has independently investigated an oceanic Cataclysm consequence;
- the central campaign may conditionally acknowledge this evidence during the post-Bosses'Rise Gnarl synthesis, but never require it.

## 4. Native progression left outside the authored arc

The following remain native Fathoms play unless separately used for sparse contextual Ramblings:

- catching every fish;
- `make_a_bad_decision`;
- exhaustive scrawl collection;
- every Aberration Idol ritual;
- maximum ritual amplification;
- complete Jinx collection;
- all ornate equipment;
- dredging mastery;
- all coffers and special loot;
- all nautical map destinations;
- Kelpie progression;
- Augur progression;
- Gastronomicon completion;
- Ancient Reservoir exploration;
- broad fish processing and bait optimization.

This exclusion is intentional. V5 uses only the material needed to tell the investigation.

## 5. Rambling allocation

Sparse popup reactions are appropriate for later native accomplishments that materially deepen the investigation after its authored conclusion.

Strong candidates include:

- `catch_all_fish`, because it demonstrates exhaustive local knowledge but is not required;
- `make_a_bad_decision`, because it is a distinctive irreversible source event and deserves contextual reaction;
- entering the Ancient Reservoir;
- creating a Kelpie;
- significant Augur / Pylon discoveries;
- a major late Fathoms ritual or equipment culmination.

These reactions provide lore context without reopening the completed Adventure.

## 6. Presenter and provider boundary

The Historian is the principal local quest-facing speaker for this arc.

Gnarl may provide strategic or historical interpretation where the evidence intersects known Cataclysm history, but the investigation should not be converted into a Gnarl monologue.

If the bound Historian receives popup / Questlog speech, that speaking role enters the final V5 presenter-roster review and requires the universal five-state visual set.

## 7. Technical boundary

Implementation should prefer existing source state:

- aberration catch state / advancement;
- decoded-item tag state;
- Rocky Waters map identity;
- Rocky Waters location history;
- Sunken Scrawl ritual identity and decoded state;
- successful ritual advancement / source state.

Do not duplicate Fathoms fishing statistics or ritual state inside Questlog when source state already exists.

The V5 blueprint does not authorize production changes yet.
