# OVERLORD REIGN V5 — Implementation Specifications

Status: TECHNICAL SPECIFICATION
Date: 2026-09-18

Companion to `V5_CAMPAIGN_SYSTEM_AUTHORITY.md`. Covers the two implementation items the authority
document leaves open, plus one trap in the reward model that will otherwise cost the builder a day.

---

## 1. `reward: NONE` does not mean an empty rewards array

**Read this before implementing any quest.**

`V5_CAMPAIGN_SYSTEM_AUTHORITY.md` §2.1 states that every quest carries `reward: NONE`. That is a
statement about **player-facing prizes**: no item, no XP, no loot-table payment. Gameplay rewards the
player.

It is **not** a statement about the quest JSON's `rewards` array.

Questlog implements narrative-fact writing as a reward type. `QuestRewardRegistry` registers
`questlog:set_fact` → `SetFactReward`, and `SetFactReward.applyReward()` is what calls
`OverlordNarrativeState.setFact()`. So a quest that must write a fact carries a `set_fact` entry in its
rewards array **while still being `reward: NONE` in V5 terms**.

This matches Q155 exactly: campaign state changes are recorded as completion consequences, not as
rewards, and the reward field stays `NONE` even when completion causes an important state transition.
The mechanism happens to live in the reward system; the design meaning does not.

```jsonc
// A quest that is reward: NONE in V5 terms and still writes its fact
"rewards": [
  { "type": "questlog:set_fact", "fact": "overlord_reign:civilizations/goblins/resolved_subjugated" }
]
```

A builder who reads `reward: NONE` and ships an empty rewards array will silently break **90 narrative
facts**, and with them every branch that reads one.

---

## 2. `overlord_reign:tower/restoration_complete` — the silent readiness gate

### The problem

§3.4 states there is **no visible Tower-complete quest or popup**; aggregate readiness is tracked
silently. But Bosses'Rise activates on that readiness (Q159), so something must evaluate it, and the
fact needs a writer that is not a visible quest.

### The mechanism already exists

Three pieces are implemented and need no new code:

- `questlog:fact` → `FactObjective` — an objective satisfied while a named fact is present.
  Requires `required_amount: 1`.
- `questlog:set_fact` → `SetFactReward` — writes a fact and calls `syncAllQuestState()` immediately,
  so downstream gates re-evaluate the moment it lands.
- `QuestDisplayData` supports `"hidden": true`.

### The specification

Each of the fourteen Tower Restoration quests writes its own room fact through `set_fact`. Those facts
are already registered in `NARRATIVE_FACTS.md`:

```
tower/throne_room_operational      tower/theurgy_room_operational
tower/forge_prepared               tower/gluttony_room_operational
tower/storage_room_operational     tower/spell_study_operational
tower/armory_operational           tower/eidolon_room_operational
tower/treasury_operational         tower/alchemy_room_operational
tower/gates_operational            (+ arena, jail, biomancy room facts to be added)
```

Add **one hidden aggregate quest**, not part of the visible 147:

```jsonc
{
  "title": "",                       // never displayed
  "chapter": "main",
  "include_in_main": false,
  "hidden": true,
  "objectives": [
    { "type": "questlog:fact", "fact": "overlord_reign:tower/throne_room_operational",  "required_amount": 1 },
    { "type": "questlog:fact", "fact": "overlord_reign:tower/forge_prepared",           "required_amount": 1 }
    // ... one FactObjective per Tower room fact, fourteen total
  ],
  "rewards": [
    { "type": "questlog:set_fact", "fact": "overlord_reign:tower/restoration_complete" }
  ]
}
```

Because `SetFactReward` triggers a full quest-state sync, the five Bosses'Rise `locate` quests —
which activate together on the readiness gate (Q159) — become available in the same tick.

### Why a hidden quest rather than a code check

It keeps the gate in data rather than in Java, so a later change to the Tower room set is a JSON edit.
It reuses two registered types instead of adding a fifteenth objective type. And it satisfies §3.4
literally: nothing visible, no popup, no Questlog entry the player can see.

**Requirement:** the Arena, Jail and Biomancy room facts are not yet in `NARRATIVE_FACTS.md`. Add them
alongside the other eleven, or the aggregate cannot reach fourteen.

---

## 3. `VampireFeedingEvents` bridge — `OR-ADV-NIGH-001` arm A

### The problem

The NightWalker first-blood quest is a branch-recording OR:

- **Arm A** — feed from a living victim
- **Arm B** — drink a Blood Bottle

Arm B resolves natively: `consume_item` on `nycto:blood_bottle`. Arm A has **no native advancement**.
Nycto handles feeding in code through `overlordreign.nightwalker.logic.VampireFeedingEvents`, with
`BloodSystem` and `BloodReserveEvents` holding the blood pool.

This is the only code bridge in the campaign, and it is load-bearing: without it the branch collapses
and only the bottle arm can ever fire, which means Lestat's `DARKENED` first reaction becomes the only
one a player can see.

### The specification

Add one objective type, `overlord_reign:vampire_feed`, registered in `QuestObjectiveRegistry`
alongside the existing `overlord_reign:nightwalker_power_count`, which is the precedent for a
Nycto-backed custom objective.

Behaviour:

- Subscribe to the Nycto feeding completion path. A successful feed is one that **increases the
  player's native blood value** — the same condition Q120 used before it was superseded.
- A feed from a Blood Bottle must **not** satisfy it. Arm A means a living victim; `BloodSystem`
  distinguishes the sources.
- Fire once. `required_amount: 1`.
- Blocked feeds do not count — Nycto emits `message.nycto.blood_drain_blocked` when a target resists.

### Branch recording

The completing arm writes its fact, and the two are mutually exclusive:

```jsonc
"rewards": [
  { "type": "questlog:set_fact", "fact": "overlord_reign:nightwalker/first_blood_victim" }
  // arm B writes overlord_reign:nightwalker/first_blood_bottled instead
]
```

Lestat's completion popup reads the fact to choose its reaction: `PLEASED` on the victim arm — the
Overlord has stopped denying what he is — and `DARKENED` on the bottle arm, restraint revealing fear
of identity (`reference/40` §20).

### Fallback if the bridge is not built

Do **not** ship arm A gated on the Blood Bottle. That would make the two arms identical and silently
delete the branch. If the bridge cannot be built, raise it with the Overlord: the quest either loses
its branch or the objective changes.
