# OVERLORD REIGN V5 Trigger and Reward Authoring Queue

Status: PROPOSAL QUEUE / NOT AUTHORITY

Date: 2026-09-16

Purpose: organize the Overlord's new requirement that every visible V5 quest record its activation trigger and completion reward without creating one decision question per quest when a reusable policy can settle many quests coherently.

Nothing in this file is approved campaign design unless separately recorded as an existing decision.

## 1. Authoring strategy

Do not ask the Overlord to approve hundreds of mechanically repetitive trigger/reward cells if a higher-level policy can determine them without changing quest meaning.

Use this order:

1. preserve triggers/rewards already explicitly fixed by prior authority;
2. ask global policy questions that can govern ordinary sequential quests;
3. ask category-level policies where Tower, central campaign, magic, Adventures, or civilizations need different behavior;
4. ask quest-specific questions only for genuine exceptions;
5. translate approved authored rules into Questlog prerequisites and registered reward classes technically afterward.

## 2. Already-fixed activation facts

These should not be reopened unless a later explicit decision supersedes them.

### Opening

The first visible Master's Staff / Brown quest activates automatically after the opening Gnarl presentation.

### Bosses'Rise gate

The five Bosses'Rise subcampaigns become available concurrently after the defined initial Tower-readiness gate and all four Minion tribes are restored.

A later trigger decision may still need to define whether each first locate quest appears immediately, is presented through Gnarl one by one, or uses another approved presentation boundary if prior authority is not specific enough. The concurrency itself is fixed.

### Post-five transition

After the fifth Bosses'Rise completion, Gnarl's synthesis is presentation-only and the End expedition quest becomes active immediately afterward.

### Post-Credits

Post-Credits content becomes available immediately after the one-time Gnarl ending presentation. There is no visible campaign-complete quest.

### Civilization line start

Placing the approved banner in a viable civilization anchor starts that civilization questline, binds the canonical local anchor, and selects / spawns the required quest-role NPCs.

This is the line-start trigger. It does not automatically determine the activation of every later provider or route quest.

### NightWalker source event

Completed vampiric transformation is the source event that brings Lestat into the authored NightWalker progression. Exact visible-quest activation around that event must preserve the approved three-quest structure once Batch 02 is reconciled.

## 3. Global trigger policy questions for a later decision batch

### TR-POL-001: Ordinary sequential quest activation

Question to settle:

When a visible quest is the direct next beat of the same authored line and no separate presentation, discovery, provider, or world-state event is intended, should the next quest activate immediately when the prior quest completes?

Low-complexity proposal:

```text
YES.
```

Use prior visible quest completion as the ordinary activation trigger for direct sequential beats. Add another trigger only when the campaign meaning actually requires one.

Reason:

This would make sequence explicit without inventing filler triggers, and it matches Questlog's native prerequisite model cleanly.

Status: PROPOSAL ONLY.

### TR-POL-002: Completion presentation boundary

Question to settle:

If a quest has an approved completion presentation, does the next quest normally activate on the quest-completion state itself or only after the completion presentation has played?

Options have materially different presentation behavior and must be approved.

Low-complexity proposal:

```text
If the presentation communicates information required to understand the next objective,
activate after the presentation.
Otherwise the quest may technically activate at completion while the presentation closes the prior beat.
```

Status: PROPOSAL ONLY.

### TR-POL-003: Optional Adventure discovery

Question to settle:

For optional Adventures intended to be found naturally, should their first visible quest generally activate on the first meaningful native discovery / encounter rather than appearing in Questlog from campaign start?

Low-complexity proposal:

```text
YES, unless the Adventure is explicitly offered by an established presenter or provider first.
```

Reason:

This preserves the established anti-MMO presentation rule and keeps optional content from filling Questlog before it matters.

Status: PROPOSAL ONLY.

### TR-POL-004: Magic-line availability

Question to settle:

When should each dedicated magic progression line become visible after its corresponding Tower room is restored?

Possible authored policies include:

- immediately on room restoration;
- after first native interaction with the system;
- after Gnarl / specialist introduction following room restoration;
- another explicit system-specific trigger.

A global policy may cover most magic lines, with specialist exceptions where necessary.

Status: OPEN AUTHORING QUESTION.

### TR-POL-005: Tower Restoration quest availability

Question to settle:

After the opening Brown quest, should all fourteen initial Tower Restoration quests become available together, or should subsets be introduced by capability / presentation gates?

The overall Tower phase is already semi-open, but that does not by itself specify exactly when each visible restoration quest appears.

Status: OPEN AUTHORING QUESTION.

## 4. Global reward policy questions for a later decision batch

### RW-POL-001: Default reward philosophy

Question to settle:

Should ordinary V5 quests receive generic material / XP rewards merely for completion, or should separate rewards exist only when they are thematically or mechanically justified by the quest?

Low-complexity proposal:

```text
No generic completion payment.
Default completion reward = NONE unless the quest fiction or progression specifically justifies a reward.
```

Native boss loot, native crafting output, items acquired while completing the objective, and ordinary access gained through native play are not automatically additional Questlog rewards.

Reason:

This matches the established rule that OVERLORD REIGN should not become an MMO-style checklist and avoids duplicating native progression loot.

Status: PROPOSAL ONLY.

### RW-POL-002: Consequences are not automatically rewards

Question to settle:

Should campaign state transitions such as Minion unlock, next-quest availability, civilization disposition, route facts, and ending access be recorded as completion consequences while the separate reward field may still be `NONE`?

Low-complexity proposal:

```text
YES.
```

Reason:

Questlog can technically execute some consequences through Reward classes, but V5 should preserve their narrative function rather than treating every state change as a prize.

Status: PROPOSAL ONLY.

### RW-POL-003: Presenter acknowledgement as reward

Question to settle:

When a quest already has a completion presentation, should that presentation normally count as the separate completion reward?

Low-complexity proposal:

```text
NO by default.
```

Treat the presentation as the authored closure/presentation beat, not as a reward, unless a specific quest intentionally makes recognition, information, or revelation the reward itself.

Status: PROPOSAL ONLY.

### RW-POL-004: Physical rewards

Question to settle:

When a quest does justify a physical reward, should V5 prefer a specifically authored item/material/service over generic XP or random loot-table payment?

Low-complexity proposal:

```text
YES.
```

Use generic XP or random loot only where that is itself the intended reward experience.

Status: PROPOSAL ONLY.

### RW-POL-005: Auto-claim versus manual claim

Question to settle:

Should state consequences and guaranteed narrative unlocks apply automatically, while tangible optional rewards remain manually claimed unless a specific quest requires immediate delivery?

Low-complexity proposal:

```text
YES.
```

This maps naturally to Questlog's verified `auto_claim` behavior while retaining an explicit player-facing claim interaction for tangible rewards.

Status: PROPOSAL ONLY.

## 5. Category-specific trigger questions

### Tower Restoration

If TR-POL-005 does not settle all fourteen rooms, determine only the exceptional room triggers.

Potential source of exceptions:

- room depends on an already-restored capability;
- presenter must introduce the function first;
- world access makes the objective impossible until another central milestone.

Do not sequence rooms merely to create a longer checklist.

### Minion restoration

The fixed order is Brown -> Red -> Green -> Blue.

If TR-POL-001 is approved, ordinary activation can be:

```text
Red after Brown completion
Green after Red completion
Blue after Green completion
```

unless another explicit presentation boundary is desired.

The Minion API unlock remains an approved immediate completion consequence for each tribe quest.

### Bosses'Rise

First-quest availability is tied to the Tower / four-tribe readiness gate.

Within each boss subcampaign, TR-POL-001 can cover locate -> investigate -> defeat if approved, unless a separate Gnarl interpretation presentation must sit between investigation and confrontation.

### Endgame

The End entry trigger is already fixed after the post-five Gnarl synthesis.

The Ender Dragon quest trigger still requires an explicit choice if prior authority does not sufficiently establish whether it activates:

- immediately on completing the End-entry quest;
- after an End-entry completion presentation;
- after another approved discovery inside the Cataclysm Dimension.

Do not infer one from technical convenience.

### Magic

TR-POL-004 should ideally settle the common start rule.

TR-POL-001 can then govern ordinary internal sequence unless an approved final path choice or source progression requires a different trigger.

### Farming / automation

The four-stage sequence is fixed. A later decision only needs to determine the first quest's start trigger if ordinary sequential activation is approved for stages two through four.

### Adventures

Use TR-POL-003 as the proposed default for naturally discovered optional Adventures.

Exceptions likely requiring explicit authored start rules include:

- Quaver's Tower-instrument request;
- Pet Cemetery, which depends on an owned supported pet death;
- NightWalker, whose source event is vampiric transformation;
- Fathoms, whose Historian involvement begins after anomalous evidence;
- Dragon Mastery, which may begin from Bestiary / dragon evidence rather than generic discovery;
- Post-Credits End expedition, whose availability is already gated by the ending.

### Civilizations

The banner placement is the line-start trigger.

Later provider quests should generally activate from their bound provider's authored availability and the facts / route state that make that provider relevant.

Terminal route quests require explicit route reveal and commitment prerequisites. DESTROYED-target quests must also respect the already-approved target-protection gate.

A global provider-quest trigger policy may reduce repetitive per-provider questions after Batch 02 and remaining civilization micro-sequences are resolved.

## 6. Category-specific reward questions

If RW-POL-001 through RW-POL-005 are approved, only genuine exceptions need separate reward decisions.

Likely exception categories:

- Master's Staff / Brown opening if completion intentionally grants a physical or capability reward beyond Brown unlock;
- Minion tribe quests if any separate reward is desired beyond owner-state unlock;
- major central bosses if Gnarl / Tower grants a deliberate reward beyond native boss loot;
- magic mastery finales if mastery should grant a curated artifact, access, or only the recorded inclination / completion;
- civilization providers if a service, trade, tribute, or item is explicitly earned;
- civilization terminal resolutions if tribute / service access is an actual reward rather than only political consequence;
- Quaver instrument completion if Tower music service itself is the reward;
- Adventure conclusions where a presenter deliberately awards something beyond native loot;
- Ender Dragon / ending if the campaign grants any authored final reward before Post-Credits access.

Everything else can safely use explicit `NONE` only after the Overlord approves the default reward policy.

## 7. Batch integration

The next decision workbook after Batch 02 reconciliation should include:

1. the small global trigger-policy set;
2. the small global reward-policy set;
3. quest-specific trigger/reward exceptions already identifiable from approved campaign structure;
4. remaining authored content questions uncovered by technical source research.

Do not ask trigger and reward separately for every ordinary sequential quest if an approved global rule would determine the answer without changing its meaning.

## 8. Production boundary

This queue does not authorize trigger wiring, reward assignment, production quest changes, or campaign state changes.

It exists only to prepare efficient explicit Overlord decisions for the V5 authority pass.