# OVERLORD REIGN V5 Presenter Journal History Authority

Status: V5 CAMPAIGN AND PRESENTATION AUTHORITY

Date: 2026-09-18

Branch: `v5-clean-authority-2026-09-16`

Purpose: define how quest presenter dialogue and Ramblings are persisted and exposed in the Questlog journal.

This document records an explicit Overlord decision. It governs the V5 journal/presentation model and supersedes any implementation assumption that presenter popups are transient-only UI.

---

## 1. Core journal rule

The V5 journal is a durable narrative history.

Presenter dialogue shown during campaign play is not disposable presentation. Once a presenter interaction is emitted to the player, the journal must preserve that interaction as a readable historical entry.

The journal therefore records what the player actually experienced, in the order it occurred.

It must not reconstruct old dialogue later from the player's current state.

---

## 2. Two presenter records per visible quest

A visible authored V5 quest may have two distinct presenter moments:

1. quest unlock / quest order;
2. quest completion / presenter response.

These are separate journal entries.

The quest unlock entry records the presenter's initial order or framing.

The quest completion entry records the presenter's reaction after the authored objective has been completed.

They may use:

- the same presenter with different reactions;
- the same presenter with the same reaction where explicitly authored;
- different text;
- branch-dependent completion reactions where V5 authority requires them.

The implementation must not collapse both moments into one mutable quest-description field.

Conceptual sequence:

```text
QUEST_UNLOCKED
-> presenter popup
-> durable journal entry

...player completes the objective...

QUEST_COMPLETED
-> presenter popup
-> second durable journal entry
```

The two records belong to the same quest narrative but remain independent historical entries.

---

## 3. Successive-entry reading model

The unlock and completion records should read as successive entries in the campaign history.

They are not required to remain physically adjacent if other valid presenter events occur between them.

For example:

```text
Quest unlock
Rambling
Rambling
Quest completion
```

is correct if that is the order in which the player experienced those events.

The journal is therefore chronological history, not a reconstructed two-line quest summary.

---

## 4. Immutable historical records

Once a presenter entry has been emitted and recorded, its historical content is immutable.

At minimum, the durable record must preserve:

- entry identity;
- sequence/order;
- entry type;
- presenter identity;
- reaction state;
- authored text identity or resolved authored text;
- related quest ID when applicable;
- related native/source event when applicable;
- occurrence time or world tick where useful.

Later campaign-state changes must not silently rewrite earlier journal history.

This is especially important for branch-sensitive presentation.

Example:

A NightWalker completion reaction selected because the player fed from a living victim must remain the reaction that was actually shown even if later state changes make another branch fact true elsewhere.

---

## 5. Recommended event classes

The implementation should treat journal history as first-class presentation events rather than as a side effect of the current quest description.

The minimum event classes are:

```text
QUEST_UNLOCK
QUEST_COMPLETE
RAMBLING
```

Additional future event classes may be added only when a real V5 presentation need exists.

The journal event model should remain sparse and intentional, consistent with V5's general state discipline.

---

## 6. Questlog gameplay view versus journal history

The normal Questlog quest view and the presenter journal serve different purposes.

Questlog answers:

```text
What am I supposed to do?
What is currently active?
What objectives are complete?
```

The presenter journal answers:

```text
What did this character say to me?
When did they say it?
How did they react?
What happened between the order and the completion response?
```

A quest may therefore appear in both systems without duplication of responsibility.

The active quest UI is gameplay state.

The journal is narrative history.

---

## 7. Ramblings remain popup presentation

Ramblings remain presenter popups triggered by approved native advancements, milestones, discoveries, completion accomplishments, or equivalent source-owned events according to the controlling V5 Rambling authority.

A Rambling does not become a visible quest merely because it is stored in the journal.

A Rambling has:

- no quest objectives;
- no quest completion state;
- no authored quest reward;
- no requirement to appear in the active quest list.

The runtime flow is:

```text
approved source event
-> presenter Rambling popup
-> popup closes
-> Rambling remains permanently readable in that presenter's journal category
```

---

## 8. Presenter-specific Rambling categories

Ramblings are organized in the journal by presenter.

There is no single generic player-facing category simply called `Ramblings`.

The player-facing category names are:

```text
Gnarl's Ramblings
Mortis's Ramblings
Quaver's Ramblings
Historian's Ramblings
Lestat's Ramblings
Gristle's Ramblings
Grubbison Jr's Ramblings
Giblet the Sixth's Ramblings
```

These names correspond to the locked V5 presenter roster.

The category records the Ramblings actually delivered by that presenter.

A Rambling belongs to exactly one presenter category according to V5 presenter ownership/precedence rules.

Do not duplicate one Rambling into multiple character categories merely because several presenters could theoretically comment on the same source event.

---

## 9. Character journal organization

Each registered presenter should expose a readable journal history containing that presenter's emitted narrative entries.

That history may contain both:

- quest-related entries;
- Ramblings.

Example:

```text
Gnarl

[Quest Unlock] A New Master
<unlock/order text>
reaction: neutral

[Rambling]
<rambling text>
reaction: amused

[Quest Complete] A New Master
<completion response>
reaction: approving
```

The exact UI layout remains implementation work, but the underlying history must preserve this semantic ordering.

The dedicated Rambling category for the same presenter is still named:

```text
Gnarl's Ramblings
```

and filters or exposes the Rambling subset of Gnarl's recorded history.

The same model applies to all eight V5 presenters.

---

## 10. Presenter reaction state is stored per entry

The final V5 five-state visual contract is:

```text
neutral
approving
amused
displeased
severe
```

Each journal entry records the reaction state actually used when that popup was shown.

A quest therefore does not have one permanent presenter reaction.

It may have:

```text
unlock reaction != completion reaction
```

and branch-sensitive completion logic may select among valid reactions where explicitly authored.

The journal must retain the selected historical state.

---

## 11. Quest definition implication

The production quest schema must be able to represent unlock and completion presentation independently.

Conceptually:

```jsonc
"presenter": {
  "speaker_id": "overlord_reign:gnarl",
  "unlock": {
    "reaction": "displeased",
    "text": "..."
  },
  "completion": {
    "reaction": "approving",
    "text": "..."
  }
}
```

This example defines the required semantic separation, not a mandatory final JSON field layout.

The implementation may use equivalent normalized data structures as long as the two event records remain distinct.

---

## 12. Rambling definition implication

A production Rambling entry must identify at least:

- source/native trigger;
- presenter;
- reaction;
- authored text;
- stable Rambling identity.

Conceptually:

```jsonc
{
  "id": "overlord_reign:ramblings/...",
  "presenter": "overlord_reign:lestat",
  "reaction": "amused",
  "trigger": "...",
  "text": "..."
}
```

When emitted, it is added to:

```text
Lestat's Ramblings
```

and to the broader chronological Lestat journal history if that UI exposes unified history.

---

## 13. Persistence boundary

The current legacy Gnarl commentary state only records whether certain commentary phases were shown and timing/cadence state.

That model is insufficient for V5 journal history because it records presentation control state rather than the historical content actually experienced.

V5 implementation therefore requires two distinct persistence concerns:

1. runtime presentation state:
   - whether an event is eligible;
   - whether it has already fired;
   - cooldown/reminder/cadence state where relevant;

2. journal history:
   - immutable records of emitted presenter events.

Do not use one mutable structure to serve both responsibilities.

---

## 14. Migration principle

Existing Questlog quest state remains gameplay state.

Existing Gnarl commentary state may be migrated or replaced as needed for generalized presenter runtime control.

The new journal-history layer should be generalized for all eight presenters from the beginning.

Do not create eight separate hard-coded history engines.

Presenter identity must be data-driven.

---

## 15. Authority relationship

This document works with:

- `V5_CAMPAIGN_SYSTEM_AUTHORITY.md`;
- `V5_PRESENTER_SYSTEM_AUTHORITY.md`;
- `V5_RAMBLING_SYSTEM_AUTHORITY.md`;
- `V5_CAMPAIGN_DATA.json`;
- Q241 and Q242 authority.

Q242 remains important:

Mechanical objective specification does not itself authorize presenter dialogue.

Unlock and completion journal records only exist when their presenter content has been explicitly authored/approved.

Q241 remains unchanged:

Journal presentation does not create quest rewards.

---

## 16. Locked V5 decision

The V5 implementation must support the following final presentation-history model:

```text
visible quest
-> quest-unlock presenter popup
-> immutable quest-unlock journal entry

quest completion
-> quest-completion presenter popup
-> immutable quest-completion journal entry

approved Rambling trigger
-> presenter Rambling popup
-> immutable Rambling journal entry
-> categorized under <Presenter>'s Ramblings
```

This is the required journal architecture for the next V5 implementation pass.
