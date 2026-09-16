# OVERLORD REIGN V5 Quest Specification Schema

Status: V5 AUTHORING CONTROL / EXPLICIT OVERLORD REQUIREMENT

Date: 2026-09-16

Purpose: define the minimum record that every visible authored V5 quest must have before the campaign authority can be considered implementation-ready.

Authority: the Overlord explicitly required that V5 record the trigger that activates each quest and the potential reward associated with completing it. This schema operationalizes that requirement without selecting any quest's trigger or reward by inference.

## 1. Required record for every visible authored quest

Each visible quest specification must contain all of the following fields:

```text
Quest role / purpose
Presenter or provider
Activation trigger
Prerequisite state or prerequisite quest, if distinct
Player-facing objective or objective set
Completion condition
Completion consequence / unlock
Completion reward
Persistent fact or native owner-state effect, if any
Sequence-break handling, only where materially required
Technical signal / detector, once verified
```

`Completion reward` must never be left implicit. Use `NONE` when the approved design intentionally has no separate reward.

## 2. Activation trigger

The activation trigger is the exact authored event or state transition that makes the quest active or visible to the player.

Examples of trigger classes that may be used only after explicit approval include:

- completion of another visible quest;
- completion of a presentation-only transition;
- installation or restoration of a required capability;
- entering an approved world state or dimension;
- binding a civilization anchor;
- accepting an offer from an approved provider;
- a source-owned transformation or milestone;
- a remembered fact becoming true;
- several prerequisites becoming simultaneously true.

A technically available trigger is not automatically the authored trigger.

The implementation pass may choose the lowest-level detector for an approved trigger, but it may not replace the approved trigger with a different player-facing event because it is easier to detect.

## 3. Reward

The reward field records what the player receives specifically because the quest is completed.

Possible reward classes include, only when explicitly approved for the quest:

- item or material reward;
- currency or resource reward;
- capability or source-owned unlock;
- access to a service, location, recipe, provider, or system;
- explicit political or civilization consequence;
- persistent authored fact;
- a new quest or questline becoming available;
- presentation or acknowledgement where that presentation is itself the intended reward;
- `NONE`.

Ordinary progression consequences and rewards should be distinguished where useful. For example, activating the next quest may be a completion consequence while a granted item is the reward.

No reward should be invented merely because the runtime supports rewards or because an older production quest had one.

## 4. Non-quest authored beats

Presentation-only transitions, Ramblings, silent readiness gates, native milestones left outside Questlog, and automatic source-owned state changes are not forced into the visible-quest schema.

Where V5 relies on such a beat, its own trigger and consequence must still be explicit enough that implementation does not need to invent campaign behavior.

## 5. Closure rule

A visible quest is not fully authored for V5 implementation until its activation trigger and completion reward or explicit `NONE` are closed by Overlord approval in addition to its already-required objective, prerequisite, branching, presenter, and consequence decisions.

Batch decisions that define quest objectives but not activation or reward remain valid, but those quests stay incomplete until the missing fields are explicitly closed in a later decision pass.

## 6. Production boundary

This schema is campaign-authoring authority only. It does not authorize production quest implementation before the V5 campaign authority is complete and explicitly released for implementation.