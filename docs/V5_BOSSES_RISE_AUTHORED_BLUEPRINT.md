# OVERLORD REIGN V5 Bosses'Rise Authored Blueprint

Status: V5 QUEST BLUEPRINT

Purpose: define the five central Bosses'Rise subcampaigns and their convergence into the Cataclysm Dimension expedition. This document is subordinate to `V5_CAMPAIGN_SYSTEM_AUTHORITY.md` and is part of the concrete campaign graph initiated in `V5_AUTHORED_CAMPAIGN_BLUEPRINT.md`.

No final quest names or final dialogue are assigned here. Beat IDs are authoring references.

## 1. Shared Bosses'Rise rules

All five roots become eligible concurrently when the silent initial-readiness gate is satisfied.

Each subcampaign uses the same structural rhythm without making the five stories identical:

1. locate the source-owned structure that contains the anomaly;
2. confront the actual boss closely enough for the campaign to identify and interpret what it is;
3. defeat the boss and record that central checkpoint.

This rhythm exists because all five exact Bosses' Rise structures and all five durable boss-kill advancements are available in the supplied 2.1.2 JAR.

The five arcs are not a checklist UI. There is no visible master quest whose objective is "kill five bosses".

The confrontation beat is narratively important. It is where Gnarl or the relevant campaign presentation explains the distinct relationship between that creature and the Great Cataclysm. The kill beat then proves that the specific wound-linked threat has been removed.

No boss trophy, loot drop, challenge advancement, timed kill, or commodity quota is required for central progression.

### 1.1 Sequence-breaking rule

Bosses and their structures remain naturally discoverable before their authored roots open.

If a boss has already been legitimately killed when its V5 subcampaign becomes available:

- the source-owned kill advancement is stronger evidence than an earlier structure-visit or entity-approach beat;
- the discovery and confrontation beats must resolve retrospectively rather than requiring a respawn;
- the appropriate interpretation presentation may still be shown once so that the campaign meaning is not lost;
- the terminal checkpoint becomes complete without forcing a duplicate boss kill.

A prior structure visit without a boss kill may be recognized if durable visit history exists. If it does not, the player may simply revisit the surviving structure.

## 2. Skor subcampaign

Established meaning:

- Skor is a Nordberg-region creature that existed independently of the Cataclysm;
- Cataclysmic rifts fed and empowered it;
- Skor is therefore evidence that the wound can amplify existing Overworld life rather than only create new beings.

### BR-SKOR-010: Locate the frozen anomaly

Prerequisite:

- Bosses'Rise readiness gate satisfied.

Visible accomplishment:

- enter `block_factorys_bosses:yeti_hideout`.

Campaign purpose:

- establish that a specific unnatural threat is rooted in the Nordberg ecological sphere;
- direct the player toward Skor without using a generic boss marker quest.

Authoritative completion signal:

- source-backed structure visit to `block_factorys_bosses:yeti_hideout`.

Unlocks:

- `BR-SKOR-020`.

### BR-SKOR-020: Confront Skor

Prerequisite:

- `BR-SKOR-010` complete, unless retrospectively satisfied by an existing Skor kill.

Visible accomplishment:

- approach the source-owned boss entity `block_factorys_bosses:yeti` closely enough to establish direct confrontation.

Campaign interpretation:

- the creature is identified as a local Nordberg threat whose abnormal scale and power were fed by Cataclysmic rifts;
- the Cataclysm is not presented as having created the species or the northern region itself.

Completion signal:

- Questlog entity-approach tracking against the exact Yeti entity, or the narrowest source-backed equivalent if its emergence behavior makes generic approach tracking unreliable.

Unlocks:

- `BR-SKOR-030`.

### BR-SKOR-030: Defeat Skor

Visible accomplishment:

- kill `block_factorys_bosses:yeti`.

Authoritative completion signal:

- advancement `block_factorys_bosses:kill_yeti`.

Completion state:

- record Skor central checkpoint complete.

Narrative consequence:

- one major rift-fed manifestation is removed;
- this narratively weakens the active wound connection without requiring a mechanical biome or world-state transformation.

## 3. Sirok subcampaign

Established meaning:

- Sirok is a giant worm associated with the Ruborian/desert sphere;
- the creature was enlarged and empowered through Cataclysmic rift energy;
- like Skor, it demonstrates corruption/amplification of existing life rather than universal Cataclysmic creation.

### BR-SIROK-010: Locate the desert anomaly

Prerequisite:

- Bosses'Rise readiness gate satisfied.

Visible accomplishment:

- enter `block_factorys_bosses:sandworm_nest`.

Campaign purpose:

- establish a major buried threat in the desert/Ruborian ecological sphere;
- give the player a concrete investigation site before the confrontation.

Authoritative completion signal:

- source-backed structure visit to `block_factorys_bosses:sandworm_nest`.

Unlocks:

- `BR-SIROK-020`.

### BR-SIROK-020: Confront Sirok

Visible accomplishment:

- approach `block_factorys_bosses:sandworm` closely enough to trigger or establish direct confrontation.

Campaign interpretation:

- Gnarl identifies the creature as a desert giant whose present scale and danger were magnified by rift energy;
- the desert and its older history remain independent of that later corruption.

Completion signal:

- exact entity-approach tracking where reliable;
- if the Sandworm's emergence behavior prevents a stable approach event, use the narrowest technical bridge tied to its source-owned encounter state rather than weakening this into mere structure entry.

Unlocks:

- `BR-SIROK-030`.

### BR-SIROK-030: Defeat Sirok

Visible accomplishment:

- kill `block_factorys_bosses:sandworm`.

Authoritative completion signal:

- advancement `block_factorys_bosses:kill_sandworm`.

Completion state:

- record Sirok central checkpoint complete.

Narrative consequence:

- a second major rift-fed manifestation is removed;
- no global desert transformation is required.

## 4. Ashlord subcampaign

Established chronology:

Great Cataclysm
→ Ashlord is resurrected as the first returned dragon
→ during the Glorious Empire his strength and dragon population develop
→ dragons spread back into the wider world during the Silence
→ present Ice & Fire dragon ecology exists independently of Ashlord's continued survival.

Defeating Ashlord does not remove or disable modern dragons.

### BR-ASHLORD-010: Discover the Dragon Tower

Prerequisite:

- Bosses'Rise readiness gate satisfied.

Visible accomplishment:

- enter `block_factorys_bosses:dragon_tower`.

Campaign purpose:

- establish Ashlord's dedicated stronghold as a historical Cataclysm-linked site;
- connect the central campaign to the already-established dragon chronology without making the modern Ice & Fire dragon arc dependent on this boss.

Authoritative completion signal:

- source-backed structure visit to `block_factorys_bosses:dragon_tower`.

Unlocks:

- `BR-ASHLORD-020`.

### BR-ASHLORD-020: Reach Ashlord

Visible accomplishment:

- ascend the native Dragon Tower far enough to approach `block_factorys_bosses:infernal_dragon` and establish direct confrontation.

Campaign interpretation:

- Ashlord is identified as the first dragon resurrected after the Great Cataclysm;
- his later growth and reproduction helped begin the historical return of dragons;
- current wild dragons are no longer dependent on him.

Completion signal:

- exact entity-approach tracking against `block_factorys_bosses:infernal_dragon`;
- the tower traversal remains native gameplay and is not decomposed into floor-by-floor quest objectives.

Unlocks:

- `BR-ASHLORD-030`.

### BR-ASHLORD-030: Defeat Ashlord

Visible accomplishment:

- kill `block_factorys_bosses:infernal_dragon`.

Authoritative completion signal:

- advancement `block_factorys_bosses:kill_dragon`.

Completion state:

- record Ashlord central checkpoint complete.

Narrative consequence:

- a powerful historical product of the Cataclysm is removed and the wound connection is weakened;
- existing dragons, eggs, Dragon Den progression, Myrmex, and modern Ice & Fire ecology persist normally.

## 5. Helvar subcampaign

Established identity:

- Helvar is the Third Overlord from the earlier Overlord era;
- Cataclysmic destabilization of dimensional boundaries allowed him to emerge or escape from the Infernal Abyss;
- centuries of Abyss corruption combined with later Cataclysmic damage left a mad, warped remnant;
- he guards a useless throne and is not a coherent rival claimant to the current Overlord.

### BR-HELVAR-010: Locate the Underworld Arena

Prerequisite:

- Bosses'Rise readiness gate satisfied.

Visible accomplishment:

- enter `block_factorys_bosses:underworld_arena` in the Netherworld.

Campaign purpose:

- expose a place where Infernal/Abyssal history and Cataclysmic dimensional instability intersect;
- begin the identity reveal through the encounter rather than treating Helvar as an unexplained generic knight.

Authoritative completion signal:

- source-backed structure visit to `block_factorys_bosses:underworld_arena`.

Unlocks:

- `BR-HELVAR-020`.

### BR-HELVAR-020: Confront Helvar

Visible accomplishment:

- reach and approach `block_factorys_bosses:underworld_knight`.

Campaign interpretation:

- Gnarl identifies the warped figure as Helvar, the Third Overlord;
- the presentation makes clear that the current creature is not a functioning rival Overlord seeking to reclaim the Tower;
- his presence demonstrates that the Cataclysm destabilized boundaries strongly enough to dislodge ancient Infernal remnants.

Completion signal:

- exact entity-approach tracking against `block_factorys_bosses:underworld_knight`.

Unlocks:

- `BR-HELVAR-030`.

### BR-HELVAR-030: Defeat Helvar

Visible accomplishment:

- kill `block_factorys_bosses:underworld_knight`.

Authoritative completion signal:

- advancement `block_factorys_bosses:kill_underworld_knight`.

Completion state:

- record Helvar central checkpoint complete.

Narrative consequence:

- the warped Third Overlord remnant is ended;
- no succession claim, throne inheritance, or new Overlord title is created by the kill.

## 6. Nerakyss subcampaign

Established meaning:

- Nerakyss is an oceanic result of centuries of Cataclysmic contamination and leakage;
- it is parallel evidence to Fathoms corruption and the Leviathan/Sunken City history, not subordinate to either;
- the ocean was not created or wholly defined by the Cataclysm.

### BR-NERAKYSS-010: Locate the oceanic anomaly

Prerequisite:

- Bosses'Rise readiness gate satisfied.

Visible accomplishment:

- enter `block_factorys_bosses:kraken_ship`.

Campaign purpose:

- establish a concrete oceanic wound-linked site;
- distinguish the central Nerakyss threat from ordinary ocean exploration.

Authoritative completion signal:

- source-backed structure visit to `block_factorys_bosses:kraken_ship`.

Conditional interpretation:

- if the player has already completed meaningful Fathoms investigation evidence, Gnarl may acknowledge that the earlier findings support the same broad conclusion about long-term leakage;
- Fathoms completion is never required to advance this subcampaign.

Unlocks:

- `BR-NERAKYSS-020`.

### BR-NERAKYSS-020: Confront Nerakyss

Visible accomplishment:

- reach and approach `block_factorys_bosses:kraken`.

Campaign interpretation:

- Nerakyss is identified as a product of prolonged contamination/leakage from the still-active wound;
- the presentation reinforces that these effects accumulated over centuries rather than appearing only in the current campaign.

Completion signal:

- exact entity-approach tracking against `block_factorys_bosses:kraken`.

Unlocks:

- `BR-NERAKYSS-030`.

### BR-NERAKYSS-030: Defeat Nerakyss

Visible accomplishment:

- kill `block_factorys_bosses:kraken`.

Authoritative completion signal:

- advancement `block_factorys_bosses:kill_kraken`.

Completion state:

- record Nerakyss central checkpoint complete.

Narrative consequence:

- the major oceanic wound-linked manifestation is removed;
- Fathoms ecology, Sea Dwellers, other oceanic consequences, and historical contamination remain in the world.

## 7. Silent convergence

The central campaign silently checks for completion of all five terminal checkpoints:

- Skor;
- Sirok;
- Ashlord;
- Helvar;
- Nerakyss.

The native `block_factorys_bosses:kill_all_bosses` advancement may be used as a technical corroboration signal if useful, but it is not the authored campaign authority and does not replace the five individual checkpoint states.

When all five are complete, unlock `BR-SYNTHESIS`.

## 8. BR-SYNTHESIS: Gnarl's interpretation and expedition handoff

Status: PLANNED

Purpose:

- synthesize the evidence from the five central subcampaigns;
- establish before the End expedition that the Great Cataclysm's dimensional wound remains active;
- direct the campaign toward the Cataclysm Dimension as the source that must be stabilized;
- avoid adding another preparation grind between the five bosses and the End.

Prerequisite:

- all five Bosses'Rise terminal checkpoints complete.

Visible player accomplishment:

- return to the Dark Tower and deliberately consult the central Tower campaign presentation point after the five victories.

Presentation:

- Gnarl is the principal speaker;
- the five defeated threats are interpreted as distinct consequences or manifestations connected to one still-active dimensional wound;
- the player is told before entering the End that the Cataclysm Dimension is the source-side problem;
- if approved optional evidence such as Fathoms or Myrmex origin evidence has already been established, Gnarl may acknowledge it as corroboration without implying it was required.

Completion state:

- record the central campaign as ready for the Cataclysm Dimension expedition.

Unlocks:

- the authored End / Cataclysm Dimension expedition sequence.

Implementation boundary:

- the exact Tower interaction used to invoke this one-time consultation should reuse an established Tower campaign anchor or presenter surface rather than add a new physical NPC solely for this beat;
- no fixed world coordinates are required.

## 9. What remains native

The following remain ordinary Bosses' Rise gameplay unless another approved V5 arc explicitly uses them:

- challenge advancements;
- timed boss kills;
- boss-specific loot collection after the central kill;
- complete dungeon loot tables;
- repeat boss farming;
- every ordinary hostile mob inside each dungeon;
- exhaustive room-clearing objectives.

The central campaign uses only the native play needed to discover, reach, understand, and defeat each major wound-linked threat.
