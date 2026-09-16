# OVERLORD REIGN V5 Quest Trigger and Reward Closure Register

Status: V5 AUTHORING CONTROL / NON-AUTHORING INVENTORY

Date: 2026-09-16

Purpose: track the new completeness requirement that every visible authored V5 quest must have an explicitly approved activation trigger and completion reward, including explicit `NONE` when no separate reward is intended.

This register does not select triggers or rewards. It records where authoring is already structurally defined and where trigger/reward closure remains required before implementation may begin.

Authority:

- `docs/V5_00_OVERLORD_APPROVAL_GOVERNANCE.md`
- `docs/V5_QUEST_SPECIFICATION_SCHEMA.md`
- `docs/V5_DECISION_BATCH_01_AUTHORITY.md`
- later explicit Overlord decisions once processed

## 1. Closure rule

A visible quest is not implementation-ready merely because its purpose, presenter, objective, order, branch, or completion consequence is known.

For every visible quest, V5 must separately close:

```text
activation trigger
prerequisite state or prerequisite quest, if distinct
objective or objective set
completion condition
completion consequence / unlock
completion reward, including explicit NONE
```

Rewards must not be inferred from older production quests, technically available Questlog reward types, native mod loot, or convenience.

Likewise, the activation trigger must not be inferred merely from whatever detector is easiest to implement.

## 2. Central campaign quest families requiring trigger/reward closure

### Opening

Known visible structure:

- one opening quest combining Master's Staff and first Brown proof.

Already fixed:

- it activates automatically after the opening Gnarl presentation.

Still requires closure:

- completion reward or explicit `NONE`;
- exact completion consequence wording where needed beyond the already approved Brown / campaign progression effect.

### Dark Tower Restoration

Known visible structure:

- fourteen separate restoration quests inside one Tower Restoration Questlog:
  - Throne;
  - Forge;
  - Storage;
  - Armory;
  - Treasury;
  - WayGates;
  - Arena;
  - Jail;
  - Alchemy;
  - Theurgy;
  - Gluttony;
  - Spell Study;
  - Eidolon;
  - Biomancy.

Already fixed:

- installation is the ordinary restoration proof;
- WayGates tracks only placement of the Tower Waystone;
- deeper mastery belongs to system questlines rather than restoration.

Still requires closure for each of the fourteen quests:

- exact activation trigger;
- prerequisite where distinct;
- completion consequence / unlock where not already implicit;
- completion reward or explicit `NONE`.

### Minion restoration

Known visible structure:

- Red restoration;
- Green restoration;
- Blue restoration.

Already fixed:

- order is Red -> Green -> Blue after Brown;
- each is one visible quest;
- Gnarl presents all three;
- completion invokes the authoritative Minion owner unlock immediately.

Still requires closure for each:

- exact activation trigger;
- completion reward or explicit `NONE` distinct from the Minion unlock consequence.

### Bosses'Rise

Known visible structure:

- five concurrent boss subcampaigns after Tower / tribe readiness;
- each uses the approved broad shape:
  - locate domain / anomaly;
  - investigate / interpret the boss-specific Cataclysm connection;
  - defeat the boss.

Still requires closure for every visible quest inside all five subcampaigns:

- exact activation trigger;
- prerequisites where distinct;
- exact objective where not yet approved;
- completion consequence / unlock;
- completion reward or explicit `NONE`.

The presentation-only Gnarl synthesis after the fifth boss is not a visible quest. Its own trigger and consequence must remain explicit, but it receives no fabricated quest reward.

### End phase

Known visible structure:

- End / Cataclysm Dimension entry quest;
- Ender Dragon quest.

Already fixed:

- the entry quest activates after the post-five Gnarl synthesis;
- defeating the Ender Dragon leads to the one-time Gnarl ending presentation;
- no visible campaign-complete quest follows.

Still requires closure:

- exact reward or explicit `NONE` for the entry quest;
- exact activation trigger for the Dragon quest;
- exact reward or explicit `NONE` for the Dragon quest;
- distinction between Dragon quest reward and ending consequence.

## 3. Magic quest families requiring trigger/reward closure

The visible counts are already fixed:

```text
Iron's Spells        4
Eidolon              4
Gluttony             3
Theurgy              4
Ars Elixirum         3
Biomancy             5
```

For every visible quest in these six lines, V5 must still explicitly record:

- activation trigger;
- prerequisite where distinct;
- completion reward or `NONE`;
- completion consequence / unlock where not already fixed by the approved progression sequence.

Where a final quest records a remembered path or inclination, that state write is a consequence unless the Overlord explicitly defines it as the reward itself.

## 4. Farming and automation

Known visible structure:

```text
productive farm
-> Crop Critters
-> Hay Golem
-> assisted estate
```

Gristle presents the line.

Still requires for all four quests:

- activation trigger;
- completion consequence / unlock;
- completion reward or `NONE`.

## 5. Adventure quest families requiring trigger/reward closure

Batch 01 fixed the following visible structures:

- The Graveyard: 4 quests;
- KnightQuest: 4 quests;
- Lost Castle: 3 quests;
- Queen of Orchid: 3 quests;
- Pet Cemetery: 3 quests;
- Rats / Ratlantis: 5 quests;
- Twilight Forest: approximately 5 chapter quests, exact grouping requiring explicit approval;
- Bumblezone: 3 quests;
- L_Ender's Cataclysm: one boss-specific visible quest per major boss plus native all-boss capstone allocation;
- NightWalker: 3 quests;
- Fathoms: 5 quests;
- Post-Credits End expedition: 4 quests;
- Dragon Mastery: 4 quests;
- Dragon Den / Dragon Forge: 2 quests.

Quaver's curated instrument acquisition is approved in concept, but its complete visible quest allocation must remain whatever the explicit Overlord decisions establish. Do not infer an extra quest count here.

For every visible Adventure quest, V5 still requires:

- activation trigger;
- prerequisite where distinct;
- completion consequence / unlock;
- completion reward or explicit `NONE`.

Ramblings are not visible quests and do not receive quest rewards. Each approved Rambling must instead have an explicit triggering milestone and speaker.

## 6. Civilization questlines

Civilization content cannot be closed by assigning one trigger or reward to the entire civilization.

Each visible inner quest, provider quest, route-resolution quest, and terminal-resolution quest must eventually record its own:

- activation trigger;
- provider / presenter;
- prerequisite state where distinct;
- objective;
- completion consequence;
- completion reward or `NONE`.

Already fixed globally:

- placing a banner in a viable civilization anchor starts the civilization questline, locks the canonical local anchor, and selects / spawns required quest-role NPCs;
- terminal outcomes are authored results of inner questlines;
- destructive target eligibility begins only after the destructive route has been revealed and committed;
- the result is local to the selected anchor polity.

The global banner event is therefore a civilization-line start trigger. It does not automatically determine the activation trigger of every later provider or route quest.

## 7. Non-quest authored beats

The following do not receive fabricated visible-quest rewards:

- opening Awakening / Tower summit presentation;
- post-five Bosses'Rise Gnarl synthesis;
- ending presentation;
- Ramblings;
- silent Tower-readiness gate;
- native milestones left outside Questlog;
- automatic source-owner state changes.

Where V5 relies on any such beat, its exact trigger and consequence must still be explicit.

## 8. Decision-batch integration rule

When a decision batch closes objectives, allocations, chapter grouping, Ramblings, or other quest content, processing that batch must now also ask:

1. Is the visible quest activation trigger already explicitly approved?
2. Is the completion reward already explicitly approved, including `NONE`?
3. Is a consequence being incorrectly treated as a reward or vice versa?
4. Does the chosen trigger materially alter sequence or presentation?

If either trigger or reward remains authored and unresolved, add it to a later decision batch rather than inferring it during implementation.

## 9. Production boundary

This is an authoring-control inventory only.

It does not authorize production implementation, reward assignment, Questlog wiring, or trigger selection before the complete V5 authority is approved.