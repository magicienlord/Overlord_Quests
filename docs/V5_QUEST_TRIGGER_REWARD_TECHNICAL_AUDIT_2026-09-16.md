# OVERLORD REIGN V5 Quest Trigger and Reward Technical Audit

Status: VERIFIED TECHNICAL FACT / V5 SUPPORTING AUDIT

Date: 2026-09-16

Purpose: record how the current Questlog runtime can represent the Overlord's newly required V5 quest activation-trigger and completion-reward fields. This audit does not select any authored trigger or reward.

Technical evidence branch: `gnarl-bootstrap`.

Production Questlog is implementation evidence only. Existing production trigger/reward choices do not become V5 authority by existing in code or quest JSON.

## 1. Quest activation model

The current Quest class has no separate arbitrary `activation_trigger` field.

A quest definition contains:

```text
prerequisites / requirements
objectives
failures
rewards
failure_rewards
optional provider rule
```

Runtime `Quest.isTriggered()` requires:

1. the quest manager to be active;
2. an assigned provider binding when a provider rule exists;
3. every prerequisite objective to be complete.

Therefore the authored V5 field `Activation trigger` translates technically into the appropriate prerequisite objective or prerequisite composition, plus provider binding when the approved quest is provider-owned.

This does not mean V5 should phrase activation in implementation vocabulary. V5 should continue to state the player-facing authored event, for example `after Gnarl's opening presentation` or `after the Red restoration quest completes`, while technical translation selects the exact prerequisite detector.

### 1.1 No-prerequisite quests

If a quest has no prerequisites and no provider rule, Questlog initializes it as already triggered.

Therefore a visible V5 quest intended to begin only after a specific authored event must not be emitted with an empty prerequisite set merely because the objective itself cannot be completed early.

### 1.2 Provider-owned quests

When a provider rule exists, the quest is not triggered until a provider is bound even if all ordinary prerequisite objectives are complete.

A provider may also require explicit turn-in before quest completion is final.

This supports civilization/provider quest structures without requiring the provider interaction itself to be duplicated as a generic narrative flag where the provider system can own that boundary directly.

## 2. Trigger event boundary

`QuestManager.sync()` detects the transition from locked to triggered.

On the first triggered boundary it:

- calls `onQuestTriggered()` on each quest objective, allowing non-retroactive objectives to establish their baseline;
- persists `hasSentTrigger`;
- emits the Questlog triggered event;
- sends the client trigger packet;
- invokes the existing commentary hook.

This is technically significant for V5 sequence behavior. An objective can be intentionally retrospective or intentionally activation-relative depending on the selected objective type. The authored campaign decision must be made first; implementation must then choose a detector whose history semantics match it.

## 3. Quest completion model

A quest is complete when:

- it is triggered;
- no failure condition has completed;
- every non-optional objective is complete;
- and, for provider quests requiring turn-in, provider turn-in has occurred.

Optional objectives never gate completion.

A quest with prerequisites but no required objectives completes immediately after its prerequisites trigger. V5 should use that intentionally only for an explicitly approved presentation/state transition quest. It must not happen accidentally because an objective was omitted.

## 4. Registered completion reward types

The current `QuestRewardRegistry` exposes these reward types:

```text
questlog:item
questlog:command
questlog:experience
questlog:loot_table
questlog:choice
questlog:set_disposition
questlog:set_fact
questlog:unlock_minion
```

These are technical capabilities, not recommended V5 rewards.

The reward inventory proves that V5 can represent ordinary material rewards, XP, loot-table rewards, alternatives, commands, civilization-state writes, narrative-fact writes, and Minion owner-state unlocks without inventing a second reward subsystem.

## 5. Claim behavior

Every Reward has an `auto_claim` boolean.

Default behavior when the field is absent is:

```text
auto_claim = false
```

On quest completion, Questlog automatically applies only rewards that explicitly have `auto_claim=true`.

Non-auto rewards remain claimable through the ordinary reward flow.

Therefore V5 reward authoring should distinguish, when materially relevant:

- reward identity;
- whether the reward should apply immediately at the completion boundary or be explicitly claimed by the player.

If that distinction changes presentation or campaign meaning, it is authored and must be approved. If a reward is inherently a state transition that must occur exactly on completion, such as a Minion unlock or terminal political state, automatic application is the natural technical translation once that consequence is approved.

## 6. Consequence versus reward

Questlog represents several state transitions through the reward infrastructure, including:

- `set_disposition`;
- `set_fact`;
- `unlock_minion`.

That implementation detail must not collapse the V5 conceptual distinction between:

```text
completion consequence / unlock
completion reward
```

Example:

- the Red restoration quest completing and unlocking Red Minions may be an authored completion consequence;
- a separate item, XP grant, or `NONE` is the completion reward decision.

The runtime may technically execute both through Reward objects, but V5 must continue documenting their campaign meaning separately.

## 7. Automatic external-owner progression

The Minion unlock reward is treated specially because the Minion implementation remains the authoritative persistence owner.

Questlog can reapply an idempotent completed Minion unlock on login when the owner API was previously unavailable. This does not justify duplicating Minion ownership as a Questlog narrative fact.

V5 should use the same discipline for other source-owned systems where an approved quest consequence changes external authoritative state: observe or invoke the owner state directly rather than creating a parallel ownership flag merely because reward infrastructure exists.

## 8. Failure consequences

Questlog also supports `failure_rewards`.

Every failure consequence is required by the definition loader to use `auto_claim=true` because a failed quest cannot expose a normal success-reward claim flow.

This capability is available if V5 later authors a quest with a meaningful failure branch. Its existence does not by itself authorize failure states or punitive rewards.

## 9. V5 authoring translation rule

For every visible quest, the later implementation pass should translate the approved V5 specification in this order:

```text
V5 authored activation trigger
-> exact prerequisite objective(s) and provider binding needed to observe it

V5 authored objective / completion condition
-> exact Questlog objective detector(s)

V5 authored completion consequence
-> next-quest prerequisite, owner API operation, fact/disposition transition, presentation, or other approved state effect

V5 authored completion reward
-> one or more registered reward implementations, or no reward entry when V5 says NONE
```

Technical convenience must never reverse this process.

## 10. Questions that remain authored

This audit does not decide:

- which event activates any specific V5 quest;
- whether sequential quests activate immediately at prior completion or after a completion presentation;
- whether optional Adventures are discovery-triggered, presenter-triggered, provider-triggered, or otherwise gated;
- which quests grant items, XP, loot, access, services, choices, or no separate reward;
- whether an approved reward is auto-claimed when that affects player-facing presentation;
- whether a consequence implemented through Questlog's reward machinery should be described narratively as a reward.

Those remain explicit Overlord decisions and belong in later decision batches unless already fixed by prior authority.

## 11. Production boundary

This is a technical-support audit only.

It does not authorize production quest edits, trigger wiring, reward assignment, or reuse of current production reward values before V5 authoring is complete and explicitly released for implementation.
