# OVERLORD REIGN V5 Myrmex Civilization Blueprint

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is a focused civilization blueprint under `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md`, `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md`, `docs/V5_ICE_AND_FIRE_CAMPAIGN_AUTHORITY.md`, `docs/V5_DECISION_BATCH_01_AUTHORITY.md`, and `docs/V5_DECISION_BATCH_02_AUTHORITY.md`. It records explicit V5 decisions approved by the Overlord and uses the exact Ice & Fire beta 5 native per-hive opinion and command systems.

## 1. Canonical anchor

The canonical Myrmex polity is one runtime-selected native Myrmex hive.

Its political state is local to that hive. Other Myrmex hives remain independent and source-owned.

The universal Batch 01 civilization-start rule applies to Myrmex exactly as it does to the other civilizations:

```text
place the approved banner at a viable local Myrmex anchor
-> validate the candidate hive
-> permanently bind that native hive as the canonical Myrmex anchor for this playthrough
-> bind its Queen and any required quest-role entities
-> spawn/select required quest-role entities if needed
-> begin the Myrmex civilization arc
```

The banner placement is the authored line-start signal. V5 must bind one real native hive and its Queen rather than creating a synthetic settlement or global species state.

## 2. Political spine

The approved Myrmex political problem is how far practical access to an alien hive becomes political authority.

Ice & Fire already owns the per-hive opinion system and the thresholds that change native access.

Verified thresholds:

```text
0 to 24 -> hostile
25+ -> non-hostile
50+ -> trade access
75+ -> Myrmex Staff command access
100 -> player-founded Queen colony path
```

V5 uses those thresholds as native ingredients but never treats opinion alone as a political disposition.

The authored civilization route determines whether the canonical hive remains independent, submits, or is destroyed.

## 3. Native-opinion boundary

Before terminal resolution, source-owned opinion may rise or fall normally and may be used by authored objectives to demonstrate increasing access.

It may support:

- peaceful entry;
- trade;
- Queen/provider contact;
- Myrmex Staff access;
- proof that a specific relationship stage has been reached.

It must never automatically write `NEUTRAL`, `SUBJUGATED` or `DESTROYED` solely because a number was reached.

After terminal resolution:

```text
NEUTRAL -> opinion floor 50
SUBJUGATED -> opinion floor 75
```

The floor exists only to prevent native AI from contradicting a permanent authored settlement through later incidental reputation loss.

There is no V5 floor for `DESTROYED`.

## 4. NEUTRAL route

Batch 02 Q140 closes the exact native relationship proof.

Approved structure:

```text
canonical hive bound
-> build native opinion through source-owned behavior
-> give resin to a worker at least once
-> complete one native trade with the canonical hive
-> reach 50+ native opinion
-> explicitly recognize the Queen and canonical hive as independent
-> NEUTRAL
-> protect native opinion from falling below 50
```

Both positive behaviors are required at least once. No fixed repetition count is imposed beyond whatever source-owned actions are necessary to reach 50+ opinion.

Reaching 50, resin gifting, or trading alone does not automatically choose `NEUTRAL`. The explicit independence resolution remains mandatory.

## 5. SUBJUGATED route

Batch 02 Q141 closes the qualifying non-destructive Staff command.

Approved structure:

```text
canonical hive bound
-> develop the hive relationship through source-native actions
-> reach 75+ native opinion
-> obtain legitimate Myrmex Staff command access for the existing canonical hive
-> use a Staff bound to that hive
-> designate one new FOOD or NURSERY room
-> demonstrate practical authority rather than mere friendship
-> final Queen-facing submission resolution
-> canonical hive is recorded as operating under the Overlord
-> SUBJUGATED
-> protect native opinion from falling below 75
```

Either one new FOOD room or one new NURSERY room qualifies. Room deletion and entrance-removal actions do not qualify as the submission proof.

### 5.1 Why 100 is excluded

Native opinion 100 has a separate source meaning associated with the player's own Queen-founded colony path.

V5 must not use that state to represent conquest or submission of the pre-existing canonical hive.

The approved submission threshold is 75+, where native Myrmex Staff command becomes available.

## 6. DESTROYED route

Batch 02 Q139 supersedes the earlier conditional wording that allowed a second indispensable hive target if source analysis found one.

Approved structure:

```text
choose and commit to the destructive route
-> turn existing knowledge of the hive against it
-> deliberately attack the canonical hive
-> deliberately kill the canonical Queen
-> canonical hive ceases to function as the selected polity
-> DESTROYED
```

The canonical Queen's deliberate death is sufficient political proof for `DESTROYED`.

V5 must not add a second indispensable hive target merely to preserve the earlier conditional wording. It must not require central-resin destruction, clearing every Myrmex, species extermination, total hive demolition, a generic kill count, or a numeric opinion threshold.

Other Myrmex hives remain extant.

## 7. Caste and provider use

The detailed source pass should use native caste distinctions only where they support real authored functions.

V5 requires:

- the Queen as the final political authority;
- source-native caste behavior where it supports the inner route;
- native trade and Staff systems as route mechanics.

It does not authorize invented Myrmex offices or a fixed recurring cast beyond source-supported roles.

Any additional provider must correspond to a stable native entity/caste or another exact source surface and still requires explicit authored allocation where that allocation affects campaign design.

## 8. Cataclysm interpretation

On first encounter, Gnarl does not know the Myrmex origin. He recognizes them as an anomaly rather than presenting a complete explanation.

Later evidence may support a Cataclysmic-emergence interpretation under the established V5 history rules.

The Myrmex civilization arc remains optional to the central campaign and is not required to prove the main End/Cataclysm conclusion.

The political terminal state and the historical interpretation are separate authored facts.

## 9. Technical objective rule

Ice & Fire owns Myrmex opinion, hive membership and native command mechanics.

OVERLORD QUESTS should integrate with those systems rather than implement a parallel reputation or colony simulation.

The detailed technical pass must verify:

- exact source hooks for resin gifting and trade completion against the bound hive;
- exact native opinion query and persistence for the bound hive UUID;
- exact Myrmex Staff acquisition and hive-binding behavior at 75+;
- exact signal for adding a new FOOD or NURSERY room with the bound Staff;
- exact canonical Queen death signal tied to the selected hive;
- the least invasive method for enforcing post-resolution opinion floors.

These are technical detector questions. They do not reopen Q139, Q140, or Q141.

## 10. Presenter use

The canonical Queen is a corporeal world authority, not automatically a new PNG presenter. Any direct Questlog speaking allocation for a character outside the locked presenter roster requires explicit Overlord approval.

The universal registered presenter states remain:

```text
neutral
approving
amused
displeased
severe
```

The alien character of Myrmex world presentation should be preserved rather than making the hive behave like an ordinary Villager council.

## 11. Remaining authoring boundary

The political spine, banner anchor start, native thresholds, exact NEUTRAL relationship proof, exact SUBJUGATED Staff proof, and exact DESTROYED proof are approved.

The following remain unresolved only where another authored choice is genuinely required:

- activation triggers for visible inner Myrmex quests beyond the already-fixed banner line start;
- prerequisite boundaries where distinct from ordinary sequence;
- completion consequences beyond the approved terminal state writes and opinion floors;
- completion rewards or explicit `NONE` for every visible Myrmex quest;
- any additional provider allocation not already approved.

The following are technical-resolution work rather than new campaign decisions unless source research exposes materially different authored experiences:

- detector implementation for resin gifting, trade, opinion thresholds, Staff room designation, and Queen death;
- bound-hive UUID handling;
- opinion-floor enforcement.

## 12. Production boundary

This document is V5 campaign authority only. Do not reconcile production content, Ice & Fire reputation hooks, Myrmex AI, Staff behavior, Queen handling or presenter assets until V5 authority is complete and approved.