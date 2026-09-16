# OVERLORD REIGN V5 Rambling System Authority

Status: V5 CAMPAIGN AUTHORITY

Date: 2026-09-17

Authority: explicit Overlord decisions made after review of Decision Batch 03.

Purpose: define the global eligibility, allocation, and exclusion rules for presenter Ramblings so later V5 authoring and implementation cannot incorrectly treat them as quest-exclusive, non-quest-only, or arbitrarily sparse content.

This authority applies to every registered popup presenter and every mod or campaign domain. It is not limited to the Historian, Cataclysm, Fathoms, or any one questline.

## 1. Core definition

A Rambling is a popup-presenter character or lore reaction attached to a relevant native advancement, achievement, milestone, discovery, completion accomplishment, or equivalent source-owned event.

Ramblings are presentation content. They are not visible quests, quest objectives, quest rewards, reminders, or substitutes for authored progression.

Their purpose may include:

- characterization;
- lore;
- interpretation;
- recognition;
- humor;
- approval or criticism;
- historical context;
- specialist commentary;
- acknowledgement that the player deliberately completed a significant native progression set, collection, mastery track, or equivalent pursuit.

## 2. Quest overlap is allowed

A milestone does NOT become ineligible for a Rambling merely because the same milestone is also used by a visible quest.

Quest allocation and Rambling allocation are independent presentation decisions.

Therefore all of the following may validly coexist on the same native event:

```text
native advancement or milestone
-> visible quest objective or completion signal
-> presenter Rambling
```

Do not suppress a Rambling because:

- the advancement completes a quest objective;
- the advancement is part of a visible questline;
- Questlog already tracks the event;
- the same native event has another campaign consequence.

The quest and Rambling serve different functions. The quest is authored progression. The Rambling is character or lore presentation reacting to what happened.

## 3. Positive eligibility rule

A native advancement or milestone is eligible for Rambling treatment when at least one of the following is true:

1. the event is meaningful enough that an approved presenter has something worthwhile and character-specific to say about it;
2. the event supports lore, historical recognition, interpretation, humor, approval, criticism, specialist knowledge, or contextual reaction;
3. the event represents a significant completion accomplishment, such as finishing a substantial collection, advancement family, mastery track, bestiary/catalog pursuit, or equivalent native progression set, even when the final completion advancement itself is narratively thin.

The Historian's acknowledgement of whole-collection completion is the explicit model for rule 3, but the rule applies globally to all registered presenters.

## 4. Completion Ramblings

Completion milestones are a distinct valid Rambling category.

A completion advancement may qualify even when it contains little standalone narrative information, because the player's deliberate pursuit and completion of the broader native progression is itself worth acknowledging.

Examples of the intended pattern include:

```text
complete a meaningful collection
complete a substantial native advancement family
finish a mastery track
finish a bestiary or catalog pursuit
complete another coherent source-owned progression set
```

Do not reject such a milestone merely as 'narratively empty' if it clearly represents substantial pursued completion.

## 5. What does not qualify

A milestone should normally NOT receive a Rambling when it is only:

- trivial mechanical noise;
- repetitive recurrence of an accomplishment already acknowledged, with no new character or completion significance;
- routine bookkeeping;
- a tiny incremental progress tick with no meaningful threshold;
- a hidden technical advancement used only as an internal detector;
- debug, compatibility, migration, synchronization, or implementation plumbing;
- a source event that gives no approved presenter anything worthwhile to say and does not represent meaningful completion.

This exclusion must be applied narrowly.

Do not confuse 'already used by a quest' with 'does not qualify.' Quest overlap is explicitly allowed.

Do not confuse 'narratively thin completion advancement' with 'does not qualify.' Significant completion milestones are explicitly eligible.

## 6. Presenter assignment

After a milestone is judged eligible for Rambling treatment:

1. use the specialist presenter when the event clearly belongs to an approved specialist theme;
2. otherwise use Gnarl;
3. do not create a new presenter merely because an advancement or milestone exists.

Presenter assignment remains governed by `docs/V5_PRESENTER_SYSTEM_AUTHORITY.md`.

## 7. Advancement audit rule

When auditing a mod for Ramblings, inspect the complete relevant native advancement and milestone surface rather than only milestones left unused by quests.

The audit question is:

```text
Is this event meaningful or completion-significant enough for one of the approved presenters to acknowledge?
```

The audit question is NOT:

```text
Is this advancement unused by a quest?
```

A complete Rambling pass must therefore consider quest-used and non-quest-used milestones alike.

## 8. Duplication discipline

Quest overlap is permitted, but repetitive Rambling spam is not.

Multiple source events that are effectively the same repeated accomplishment do not each require separate popup treatment unless they carry distinct character, lore, specialist, or completion significance.

Ramblings must remain meaningful reactions rather than progress reminders.

## 9. Production boundary

This document establishes global V5 Rambling authority.

It does not by itself select the exact Rambling catalog for every mod. Exact per-mod milestone allocation, speaker assignment where ambiguous, presentation text, and implementation hooks must be derived from this rule and the relevant approved V5 decisions.

Any future V5 document, workbook, audit, or implementation note that excludes a milestone from Rambling treatment solely because it is already used by a quest conflicts with this authority and must be corrected.