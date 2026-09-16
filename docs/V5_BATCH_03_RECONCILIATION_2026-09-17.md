# OVERLORD REIGN V5 Batch 03 Reconciliation

Status: V5 AUTHORING CONTROL / CLOSURE OVERLAY

Date: 2026-09-17

Authority:

- `docs/V5_DECISION_BATCH_03_AUTHORITY.md`
- `docs/V5_RAMBLING_SYSTEM_AUTHORITY.md`
- `docs/V5_00_OVERLORD_APPROVAL_GOVERNANCE.md`
- `docs/V5_QUEST_SPECIFICATION_SCHEMA.md`

Purpose: record how completed Decision Batch 03 changes the live V5 closure state and prevent older proposal queues or closure registers from being read as if their pre-Batch-03 questions remained open.

## 1. Batch result

```text
Q143-Q160: 18 decisions
Approved: 15
Modified: 1
Rejected: 2
Unanswered: 0
```

Q158 remains open because its proposal was rejected and the replacement required accurate source inspection before a new authored policy could be proposed.

Q148 is not an ordinary unresolved rejection. Later explicit Overlord clarification established the global Rambling model in `docs/V5_RAMBLING_SYSTEM_AUTHORITY.md`; the remaining work is exact per-mod relevance allocation, not reopening the false question of whether quest-used milestones can Ramble.

## 2. Bosses'Rise investigation closure

Status: CLOSED FOR AUTHORING / TECHNICAL DETECTORS STILL REQUIRED

Q143 through Q147 close the five pre-kill investigation proofs:

```text
Skor -> native frost / icicle combat event
Sirok -> damaged armored segment followed by poisonous response
Ashlord -> native Dragon Banner from the bound Dragon Tower
Helvar -> obtain and use native Underworld Arena Key through boss-door progression
Nerakyss -> clear the three source-owned Kraken Ship pirate guards before boss appearance
```

The exact supplied Bosses'Rise 2.1.2 JAR confirms source surfaces for all five. No authored evidence prop is permitted or needed.

Remaining work is implementation translation only, such as exact event hooks, bound-structure context, or persistent detector state.

## 3. Global Rambling correction

Status: GLOBAL RULE CLOSED / EXACT CATALOGS STILL REQUIRE AUTHORED ALLOCATION

The old assumption that Ramblings are reserved for milestones unused by visible quests is superseded.

Controlling rules now include:

- quest use does not disqualify an advancement or milestone;
- every popup presenter uses the same eligibility model;
- meaningful lore / character reaction milestones qualify;
- significant completion accomplishments qualify even when the final completion milestone is narratively thin;
- trivial recurrence and technical plumbing normally do not qualify;
- specialist theme ownership determines the speaker, with Gnarl as fallback.

Consequences for older files:

- the restrictive implication in Batch 02 Q115 no longer controls;
- the restrictive three-entry proposal rejected by Q148 does not control;
- any older audit sentence saying a quest-used advancement cannot also receive a Rambling is stale and must be read through `docs/V5_RAMBLING_SYSTEM_AUTHORITY.md`.

Exact per-mod milestone catalogs remain authored allocation work because V5 governance requires explicit approval of Rambling inclusion, trigger, and speaker. Those catalogs must be prepared from complete source surfaces, not from leftover unused advancements.

## 4. Trigger policy closure

Status: CLOSED GLOBALLY EXCEPT EXPLICIT AUTHORED EXCEPTIONS

Q149 through Q153 and Q159 through Q160 establish:

### Ordinary direct sequence

```text
prior visible quest completion
-> next visible quest activates immediately
```

Use another gate only when an approved discovery, presentation, provider, world-state, or capability dependency genuinely requires it.

### Completion presentation dependency

If the completion presentation communicates information necessary to understand the next objective, activate the next quest after the presentation. Otherwise immediate activation at quest completion is permitted.

### Optional Adventures

Naturally discovered optional Adventures start on their first meaningful native discovery / encounter unless an established presenter or provider explicitly offers them first.

### Magic lines

A dedicated magic line opens after its corresponding Tower-room restoration quest and any required informative completion presentation. No redundant first-use discovery gate is added by default.

### Tower Restoration

All fourteen initial Tower Restoration quests become available together after the opening Brown quest completes.

### Bosses'Rise opening

All five first locate-domain / anomaly quests activate simultaneously when the approved Tower / four-tribe readiness gate is satisfied.

### Ender Dragon

The Dragon quest activates after the End-entry completion presentation because that presentation establishes the living-anchor interpretation required for the objective.

These rules supersede the corresponding open/proposal entries in `docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md`.

## 5. Reward philosophy closure

Status: PARTIALLY CLOSED

Q154 through Q157 close the following authored rules:

- default separate completion reward is `NONE` unless the quest fiction or progression actually justifies a reward;
- native loot, crafted outputs, obtained objective items, and ordinary native access are not duplicated merely as Questlog payment;
- campaign state changes and unlocks are consequences, not automatically rewards;
- ordinary presenter acknowledgement is closure, not a reward;
- when a separate tangible reward is justified, prefer a relevant item or material appropriate to difficulty and campaign stage;
- generic XP or random loot is fallback reward treatment only when no relevant item or material can be justified.

These rules do NOT by themselves select a specific item/material for every rewarded quest. When more than one materially different reward would change the authored experience, the exact selection remains an authored question.

## 6. Reward delivery remains open

Status: RESEARCH COMPLETE / APPROVAL REQUIRED

Q158's proposed automatic-versus-manual policy was rejected.

Exact Questlog source inspection now establishes:

- base rewards support `auto_claim`, default false;
- completed quest rewards can be collected through the framework's reward-collection path;
- item, experience, loot-table, command, and choice reward types exist;
- choice rewards require explicit player selection and cannot use `auto_claim`.

The remaining authored question is therefore not whether Questlog has a reward system. It is which delivery behavior V5 should use for ordinary tangible rewards, guaranteed rewards, and optional choice rewards within that real system.

This must return to the Overlord in the next decision batch.

## 7. Trigger/reward propagation rule after Batch 03

The old per-quest closure register must now be interpreted through the approved global policies.

For every visible quest:

1. apply an already approved specific trigger if one exists;
2. otherwise apply the Q149 ordinary-sequence rule where the quest is a direct next beat;
3. apply Q150 where an informative presentation separates beats;
4. apply Q151 to naturally discovered optional Adventures;
5. apply Q152 to magic-line starts;
6. use the specific Q153, Q159 and Q160 exceptions where applicable;
7. assign `NONE` by Q154 unless a justified reward is independently identified;
8. where a tangible reward is justified, use Q157's relevant item/material preference;
9. do not infer the still-open reward claim/delivery policy from implementation convenience.

Only residual exceptions that materially change campaign meaning should return as authored questions.

## 8. Civilization impact

Batch 03 does not invent missing civilization micro-sequences.

The remaining civilization-authoring work must still be source-audited and narrowed to decisions where several valid native actions would create materially different political meaning. Technical details such as entity IDs, exact detector code, provider persistence, and registry IDs remain technical resolution.

Myrmex relationship and Staff-command proofs are already closed by Batch 02 and exact-source verification. They must not be reopened merely because older closure registers list them as unresolved.

The Dwarf / Kobold rivalry topology is already closed by Q142. Only its exact source-compatible objectives remain to be authored where existing authority does not already determine them.

## 9. Next-batch boundary

The next full decision batch should contain only:

- the framework-aware replacement for Q158;
- exact per-mod Rambling catalogs or grouped allocation decisions that remain authored under the global Rambling rule;
- source-backed civilization objective choices that still materially affect political meaning;
- residual trigger or reward exceptions after applying the Batch 03 global policies;
- any other genuinely authored gap found by reconciling the complete V5 document against exact supplied mod sources.

Do not create questions for registry IDs, source hooks, detector mechanics, or other technical facts that can be established directly.

## 10. Production boundary

This reconciliation is V5 authoring control only. It does not authorize implementation before the complete singular V5 campaign authority is closed and explicitly approved.