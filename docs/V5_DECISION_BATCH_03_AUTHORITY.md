# OVERLORD REIGN V5 Decision Batch 03 Authority

Status: V5 AUTHORING AUTHORITY

Date: 2026-09-17

Source workbook: `OVERLORD_REIGN_V5_Decision_Batch_03_Filled.xlsx`

Purpose: record the Overlord's explicit decisions for Q143 through Q160 from the completed Decision Batch sheet. The filled workbook is the source authority for those questions. Later explicit Overlord clarification controls where it further corrects a rejected proposal, especially the global Rambling rule following Q148.

## 1. Processing result

The complete populated Decision Batch sheet was read across all columns and compared against the unfilled Batch 03 workbook, not merely the Decision column.

```text
Questions: 18
Approved: 15
Modified: 1
Rejected: 2
Unanswered: 0
```

Material cell edits outside the Decision column:

- Q157 directly rewrites the Proposed V5 Answer cell and clears the former Why / Impact cell. That rewritten proposal is the modification.
- Q158 clears the former Why / Impact cell and rejects the proposal on the basis that the Questlog Framework reward system must first be read accurately.

The filled workbook contains only the `Decision Batch` sheet. The original unfilled Batch 03 workbook remains the comparison source for the missing template sheets.

## 2. Later Rambling clarification controlling Q148

Q148 was rejected in the workbook. Subsequent explicit Overlord clarification establishes the controlling global rule recorded in `docs/V5_RAMBLING_SYSTEM_AUTHORITY.md`:

- Ramblings are not restricted to advancements or milestones unused by quests;
- a quest-used advancement or milestone may also receive a Rambling because quest progression and presenter reaction serve different functions;
- every popup presenter follows the same eligibility model;
- relevant character or lore milestones may receive Ramblings when the presenter has something worthwhile to say;
- significant completion advancements may receive Ramblings even when the final completion milestone is narratively thin, because pursued completion itself deserves acknowledgement;
- trivial repetition, technical plumbing, bookkeeping, debug/compatibility events, or insignificant incremental noise normally do not qualify.

This later clarification supersedes any earlier V5 wording that excludes a Rambling solely because a visible quest uses the same native event.

## 3. Q143 through Q160

### Q143: Skor native investigation proof

Decision: APPROVED

Approved answer:

Engage Skor without killing him and trigger one qualifying Phase 2 frost / icicle attack event. That native boss-specific behavior completes the investigation proof and allows Gnarl to interpret Skor as a Nordberg creature empowered by the wound. Do not use an authored evidence prop or generic loot item.

Authority effect: the Skor investigation stage uses native combat behavior, not an authored evidence object.

### Q144: Sirok native investigation proof

Decision: APPROVED

Approved answer:

Engage Sirok without killing him, crack one armored body segment, then strike the cracked segment again to trigger the native poisonous-blood spill. That boss-specific pre-kill behavior completes the investigation proof and allows Gnarl to interpret Sirok's abnormal enlargement / energy.

Authority effect: the Sirok investigation stage uses the native damaged-segment poison response, not an authored residue or rift prop.

### Q145: Ashlord native investigation proof

Decision: APPROVED

Approved answer:

Recover one native `block_factorys_bosses:dragon_banner` from the bound Dragon Tower before fighting Ashlord. Gnarl uses the tower's native dragon heraldry as the trigger for the already-approved historical interpretation that Ashlord is the first resurrected dragon.

Authority effect: the Dragon Banner is the native pre-kill investigation proof. It does not itself create new lore.

### Q146: Helvar native identity proof

Decision: APPROVED

Approved answer:

Explore the bound Underworld dungeon, obtain the native `block_factorys_bosses:underworld_arena_key`, and use it to unlock / pass the boss-door progression. That native domain-specific access proof triggers Gnarl's already-approved recognition of the arena's occupant as Helvar, the Third Overlord.

Authority effect: the native Underworld Arena Key and boss-door progression are the identity investigation proof. No authored identity relic is added.

### Q147: Nerakyss native contamination proof

Decision: APPROVED

Approved answer:

Inside the bound Kraken Ship, defeat the three native pirate guard variants that protect the encounter before Nerakyss appears. Their source-owned ship occupation and guard sequence is the investigation proof; Gnarl then supplies the already-approved interpretation of long-term oceanic contamination before the boss kill.

Authority effect: the source-owned pirate guard sequence is the investigation proof. No fabricated contaminated sample or custom corruption prop is added.

### Q148: Cataclysm Rambling allocation

Decision: REJECTED

Rejected proposal:

Use exactly three sparse Gnarl Ramblings, each on first completion of one native non-major kill advancement: `cataclysm:kill_ender_golem`, `cataclysm:kill_revenant`, and `cataclysm:kill_clawdian`. Do not use the eight structure discoveries, eight major-boss kills, or `kill_all_bosses` as Rambling triggers because those already belong to visible quest / capstone progression.

Workbook replacement:

`Ramblings are characters lore entries that rewards advancements that are unrelated to the quests, So when you ask yourself which advancements should get a ramblings... ITS ALL OF THE RELEVANT ONES`

Later explicit clarification:

The phrase about being unrelated to quests is not a restriction. Quest overlap does not disqualify an advancement or milestone. The controlling rule is the global authority in `docs/V5_RAMBLING_SYSTEM_AUTHORITY.md`.

Authority effect: the three-entry Cataclysm shortlist and all quest-overlap exclusions are rejected. Cataclysm must receive a complete relevance audit under the global Rambling rule before exact per-milestone allocation is approved.

### Q149: Ordinary sequential activation policy

Decision: APPROVED

Approved answer:

Activate the next visible quest immediately when the prior visible quest completes. Add another activation condition only when an approved campaign meaning genuinely requires one.

Authority effect: this is the default activation rule for direct sequential beats.

### Q150: Completion-presentation boundary policy

Decision: APPROVED

Approved answer:

If the completion presentation communicates information required to understand the next objective, activate the next quest after that presentation completes. Otherwise the next quest may activate on quest completion while the presentation closes the prior beat.

Authority effect: presentation gating is required only when the presentation conveys information necessary for the following objective.

### Q151: Optional Adventure discovery policy

Decision: APPROVED

Approved answer:

Activate the first visible quest on the first meaningful native discovery / encounter for that Adventure, unless an established presenter or provider explicitly offers the Adventure first. Do not expose all optional Adventures in Questlog from campaign start.

Authority effect: natural discovery is the default start model for optional Adventures.

### Q152: Magic-line availability policy

Decision: APPROVED

Approved answer:

Activate the magic line immediately after the corresponding room-restoration quest completes and its required completion presentation, if any, has finished. Do not add a separate first-use discovery gate unless a later system-specific exception is explicitly approved.

Authority effect: Tower-room restoration normally opens that room's dedicated magic progression directly.

### Q153: Tower Restoration availability policy

Decision: APPROVED

Approved answer:

Make all fourteen initial Tower Restoration quests available together immediately after the opening Brown quest completes. Preserve the phase's established semi-open structure and rely only on real capability / world-access limits rather than arbitrary internal sequencing.

Authority effect: no artificial authored order is imposed across the fourteen initial Tower functions.

### Q154: Default reward philosophy

Decision: APPROVED

Approved answer:

No. Default separate completion reward is `NONE` unless the quest fiction or progression specifically justifies an authored reward. Native boss loot, crafted outputs, items obtained during the objective, and access gained through native play are not duplicated as Questlog rewards.

Authority effect: V5 does not add generic completion payment merely because a quest exists.

### Q155: Consequence versus reward policy

Decision: APPROVED

Approved answer:

Record campaign state changes as completion consequences / unlocks, not as separate rewards. The reward field may still be `NONE` even when the quest completion causes an important state transition.

Authority effect: state transition and prize semantics remain distinct in the V5 specification even when implementation code later uses a reward-like execution path.

### Q156: Presenter acknowledgement as reward

Decision: APPROVED

Approved answer:

No. Treat the presentation as authored closure / acknowledgement, not as a reward, unless a specific quest explicitly establishes information, recognition, or revelation itself as the reward.

Authority effect: ordinary completion presentation does not populate the quest reward field.

### Q157: Physical reward preference

> **Q157 — Physical reward preference. Decision: MODIFIED.**
>
> Prefer one relevant item, material (following difficulty and overall campaign stage) over generic XP
> or random loot-table payment. Use generic XP or random loot only if no relevant items can be
> justified.
>
> **Status under Q241:** retained authority, **unexercised**. The V5 campaign authors no quest rewards,
> so no reward is ever justified. This rule governs only if a later decision supersedes Q241.

Decision: MODIFIED

The Overlord directly rewrote the proposed answer cell to:

`Prefer one relevant item, material (following difficulty and overall campaign stage) over generic XP or random loot-table payment. Use generic XP or random loot only if no relevant items can be justfied.`

Authority effect: when a separate tangible completion reward is justified, prefer a relevant item or material scaled to the quest's difficulty and the campaign stage. Generic XP or random loot is a fallback only when no relevant item or material can be justified.

The cleared former Why / Impact cell does not survive as authority.

### Q158: Questlog reward delivery policy

Decision: REJECTED

Rejected proposal:

Apply guaranteed narrative state consequences and unlocks automatically. Keep tangible optional rewards manually claimed unless a specific quest requires immediate delivery.

Workbook replacement:

`The Questlog Framework has a reward system you dimwit. If you actually read the sources I give you.`

Authority effect: the proposal is not authority. The Questlog Framework reward implementation must be inspected before presenting a replacement authored delivery policy. Q158 remains open pending a framework-aware decision.

### Q159: Five-subcampaign Bosses'Rise opening trigger

Decision: APPROVED

Approved answer:

Activate all five first `locate domain / anomaly` quests simultaneously as soon as the readiness gate is satisfied. Do not add five separate trigger popups or one-by-one forced introductions; Gnarl's quest presentation may frame each line when opened.

Authority effect: the five Bosses'Rise lines open concurrently and without popup saturation.

### Q160: Ender Dragon quest activation

Decision: APPROVED

Approved answer:

Activate the Ender Dragon quest only after the End-entry quest's completion presentation has played, because that presentation establishes the Ender Dragon as the living anchor sustaining the active wound. Then the Dragon quest becomes the direct next visible objective.

Authority effect: this is an explicit presentation-gated exception to ordinary immediate sequential activation.

## 4. Production boundary

This document records V5 campaign-authoring authority only. It does not authorize production quest reconciliation, trigger wiring, reward assignment, popup implementation, or detector code before the complete V5 campaign authority is closed and explicitly approved for implementation.