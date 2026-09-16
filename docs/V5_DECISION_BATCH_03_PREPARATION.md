# OVERLORD REIGN V5 Decision Batch 03 Preparation

Status: PROPOSAL QUEUE / NOT AUTHORITY

Date: 2026-09-16

Purpose: preserve the exact proposed questions packaged into `OVERLORD_REIGN_V5_Decision_Batch_03.xlsx` so the next Overlord decision pass is reproducible. Nothing in this file becomes V5 authority unless explicitly approved in the filled workbook or by a later explicit Overlord decision.

Basis:

- `docs/V5_DECISION_BATCH_02_AUTHORITY.md`
- `docs/V5_BATCH_02_RECONCILIATION_2026-09-16.md`
- `docs/V5_BATCH_02_TECHNICAL_FOLLOWUP_2026-09-16.md`
- `docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md`
- `docs/V5_QUEST_SPECIFICATION_SCHEMA.md`

## Batch scope

Batch 03 contains 18 authored decisions:

- Q143-Q147: native replacements for the five rejected Bosses'Rise investigation props;
- Q148: non-duplicative Cataclysm Rambling allocation;
- Q149-Q153: global activation-trigger policies;
- Q154-Q158: global reward policies;
- Q159-Q160: two central-campaign trigger exceptions that remain materially authored after the global policies.

The proposals below are deliberately concrete so Approve has an exact meaning. Modify or Reject may replace them.

## Q143: Skor native investigation proof

Domain: Bosses'Rise

Priority: High

Question: What exact native action should replace the rejected authored evidence object in Skor's pre-kill investigation stage?

Proposed V5 answer:

> Engage Skor without killing him and trigger one qualifying Phase 2 frost / icicle attack event. That native boss-specific behavior completes the investigation proof and allows Gnarl to interpret Skor as a Nordberg creature empowered by the wound. Do not use an authored evidence prop or generic loot item.

Why / impact: Batch 02 forbids authored investigation props. Skor's native frost / icicle Phase 2 behavior is boss-specific and pre-kill, whereas the currently known gauntlet is post-kill and the Trial/Vault path is generic to several Bosses'Rise dungeons.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_BATCH_02_TECHNICAL_FOLLOWUP_2026-09-16.md

## Q144: Sirok native investigation proof

Domain: Bosses'Rise

Priority: High

Question: What exact native action should replace the rejected authored evidence object in Sirok's pre-kill investigation stage?

Proposed V5 answer:

> Engage Sirok without killing him, crack one armored body segment, then strike the cracked segment again to trigger the native poisonous-blood spill. That boss-specific pre-kill behavior completes the investigation proof and allows Gnarl to interpret Sirok's abnormal enlargement / energy.

Why / impact: Batch 02 forbids authored residue or rift props. The armor-break and poisonous-blood mechanic is native, uniquely Sirok-specific, pre-kill, and materially distinct from the final defeat.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_BATCH_02_TECHNICAL_FOLLOWUP_2026-09-16.md

## Q145: Ashlord native investigation proof

Domain: Bosses'Rise

Priority: High

Question: What exact native action should replace the rejected authored historical evidence object in Ashlord's pre-kill investigation stage?

Proposed V5 answer:

> Recover one native `block_factorys_bosses:dragon_banner` from the bound Dragon Tower before fighting Ashlord. Gnarl uses the tower's native dragon heraldry as the trigger for the already-approved historical interpretation that Ashlord is the first resurrected dragon.

Why / impact: The Dragon Banner is native, structure-specific, pre-kill, and item-based as requested. It does not itself create new lore; it only triggers Gnarl's already-approved interpretation.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_BATCH_02_TECHNICAL_FOLLOWUP_2026-09-16.md

## Q146: Helvar native identity proof

Domain: Bosses'Rise

Priority: High

Question: What exact native action should replace the rejected authored identity relic in Helvar's pre-kill investigation stage?

Proposed V5 answer:

> Explore the bound Underworld dungeon, obtain the native `block_factorys_bosses:underworld_arena_key`, and use it to unlock / pass the boss-door progression. That native domain-specific access proof triggers Gnarl's already-approved recognition of the arena's occupant as Helvar, the Third Overlord.

Why / impact: The Underworld Arena Key is native, domain-specific, pre-kill, and already part of the source-owned boss access flow. The key does not invent Helvar's identity; Gnarl supplies the established historical recognition.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_BATCH_02_TECHNICAL_FOLLOWUP_2026-09-16.md

## Q147: Nerakyss native contamination proof

Domain: Bosses'Rise

Priority: High

Question: What exact native action should replace the rejected authored contamination object in Nerakyss's pre-kill investigation stage?

Proposed V5 answer:

> Inside the bound Kraken Ship, defeat the three native pirate guard variants that protect the encounter before Nerakyss appears. Their source-owned ship occupation and guard sequence is the investigation proof; Gnarl then supplies the already-approved interpretation of long-term oceanic contamination before the boss kill.

Why / impact: The pirate-guard sequence is native, unique to the Kraken Ship encounter, pre-kill, and distinct from Nerakyss's defeat. It avoids fabricating contaminated samples or custom corruption props.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_BATCH_02_TECHNICAL_FOLLOWUP_2026-09-16.md

## Q148: Cataclysm replacement Rambling catalog

Domain: Ramblings

Priority: High

Question: Which L_Ender's Cataclysm native milestones should replace the rejected structure-discovery Ramblings without duplicating visible boss quests?

Proposed V5 answer:

> Use exactly three sparse Gnarl Ramblings, each on first completion of one native non-major kill advancement: `cataclysm:kill_ender_golem`, `cataclysm:kill_revenant`, and `cataclysm:kill_clawdian`. Do not use the eight structure discoveries, eight major-boss kills, or `kill_all_bosses` as Rambling triggers because those already belong to visible quest / capstone progression.

Why / impact: The exact installed 3.31 JAR exposes these three one-time non-major kill advancements outside the eight-boss capstone set. They are the cleanest native contextual milestones that do not duplicate the visible Cataclysm Adventure objectives.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_BATCH_02_TECHNICAL_FOLLOWUP_2026-09-16.md

## Q149: Ordinary sequential activation policy

Domain: Triggers

Priority: High

Question: When a visible quest is the direct next beat of the same line and no separate discovery, provider, presentation, or world-state event is intended, when should it activate?

Proposed V5 answer:

> Activate the next visible quest immediately when the prior visible quest completes. Add another activation condition only when an approved campaign meaning genuinely requires one.

Why / impact: This gives ordinary sequences an explicit deterministic trigger without inventing filler gates and maps cleanly to Questlog prerequisites.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Q150: Completion-presentation boundary policy

Domain: Triggers

Priority: High

Question: When a quest has an approved completion presentation, should the next quest activate at completion or only after that presentation plays?

Proposed V5 answer:

> If the completion presentation communicates information required to understand the next objective, activate the next quest after that presentation completes. Otherwise the next quest may activate on quest completion while the presentation closes the prior beat.

Why / impact: This preserves narrative dependencies without serializing every line behind presentation playback when the presentation is only acknowledgement.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Q151: Optional Adventure discovery policy

Domain: Triggers

Priority: High

Question: How should the first visible quest of an optional Adventure intended to be found naturally usually activate?

Proposed V5 answer:

> Activate it on the first meaningful native discovery / encounter for that Adventure, unless an established presenter or provider explicitly offers the Adventure first. Do not expose all optional Adventures in Questlog from campaign start.

Why / impact: This preserves the established anti-MMO presentation rule and keeps optional content from filling Questlog before it matters.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Q152: Magic-line availability policy

Domain: Triggers

Priority: High

Question: When should a dedicated magic progression line become visible after its corresponding Tower room has been restored?

Proposed V5 answer:

> Activate the magic line immediately after the corresponding room-restoration quest completes and its required completion presentation, if any, has finished. Do not add a separate first-use discovery gate unless a later system-specific exception is explicitly approved.

Why / impact: The room restoration already establishes deliberate access to that system. This keeps progression explicit and avoids a second redundant discovery gate.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Q153: Tower Restoration availability policy

Domain: Triggers

Priority: High

Question: When should the fourteen initial Tower Restoration quests become available after the opening Brown quest?

Proposed V5 answer:

> Make all fourteen initial Tower Restoration quests available together immediately after the opening Brown quest completes. Preserve the phase's established semi-open structure and rely only on real capability / world-access limits rather than arbitrary internal sequencing.

Why / impact: V5 already defines Tower restoration as semi-open. Making all fourteen visible together avoids creating unsupported checklist order while retaining the silent all-functions readiness gate.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Q154: Default reward philosophy

Domain: Rewards

Priority: High

Question: Should ordinary V5 quests receive generic XP, materials, or random loot merely for completion?

Proposed V5 answer:

> No. Default separate completion reward is `NONE` unless the quest fiction or progression specifically justifies an authored reward. Native boss loot, crafted outputs, items obtained during the objective, and access gained through native play are not duplicated as Questlog rewards.

Why / impact: This preserves the anti-MMO design and prevents rewards from being added simply because Questlog can issue them.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Q155: Consequence versus reward policy

Domain: Rewards

Priority: High

Question: How should campaign state changes such as Minion unlocks, next-quest availability, civilization dispositions, remembered facts, and ending access be classified?

Proposed V5 answer:

> Record them as completion consequences / unlocks, not as separate rewards. The reward field may still be `NONE` even when the quest completion causes an important state transition.

Why / impact: This keeps narrative and progression state semantically distinct from prizes even if the implementation later uses reward-like code paths to apply them.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Q156: Presenter acknowledgement as reward

Domain: Rewards

Priority: Normal

Question: Should an approved completion presentation normally count as the quest's separate completion reward?

Proposed V5 answer:

> No. Treat the presentation as authored closure / acknowledgement, not as a reward, unless a specific quest explicitly establishes information, recognition, or revelation itself as the reward.

Why / impact: This prevents the reward field from becoming a duplicate description of the presentation layer.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Q157: Physical reward preference

Domain: Rewards

Priority: Normal

Question: When a quest genuinely does justify a separate physical or service reward, what reward style should V5 prefer?

Proposed V5 answer:

> Prefer one specifically authored item, material, service, tribute, or access benefit tied to the quest over generic XP or random loot-table payment. Use generic XP or random loot only if that experience is explicitly intended.

Why / impact: This keeps rewards thematically legible and avoids generic quest-payment behavior.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Q158: Auto-claim policy

Domain: Rewards

Priority: Normal

Question: How should automatic state consequences and tangible optional rewards be delivered?

Proposed V5 answer:

> Apply guaranteed narrative state consequences and unlocks automatically. Keep tangible optional rewards manually claimed unless a specific quest requires immediate delivery.

Why / impact: This cleanly separates unavoidable campaign state from optional player-facing reward collection and maps to Questlog's verified auto-claim behavior.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Q159: Five-subcampaign opening trigger

Domain: Bosses'Rise

Priority: High

Question: When the Tower / four-tribe readiness gate is satisfied, how should the five concurrent Bosses'Rise subcampaigns first appear?

Proposed V5 answer:

> Activate all five first 'locate domain / anomaly' quests simultaneously as soon as the readiness gate is satisfied. Do not add five separate trigger popups or one-by-one forced introductions; Gnarl's quest presentation may frame each line when opened.

Why / impact: Concurrency is already fixed. This closes the remaining activation behavior without adding arbitrary order or popup saturation.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Q160: Ender Dragon quest activation

Domain: Endgame

Priority: High

Question: When should the visible Ender Dragon quest activate after the End / Cataclysm Dimension entry quest?

Proposed V5 answer:

> Activate the Ender Dragon quest only after the End-entry quest's completion presentation has played, because that presentation establishes the Ender Dragon as the living anchor sustaining the active wound. Then the Dragon quest becomes the direct next visible objective.

Why / impact: The living-anchor interpretation is necessary context for why the Dragon is the central target, so this is a justified presentation-gated exception to ordinary immediate sequential activation.

Source authority: https://github.com/magicienlord/Overlord_Quests/blob/v5-clean-authority-2026-09-16/docs/V5_TRIGGER_REWARD_AUTHORING_QUEUE_2026-09-16.md

## Production boundary

This file does not authorize quest implementation, detector code, rewards, activation wiring, or Rambling registration. It only records the proposal set submitted for Overlord decision.