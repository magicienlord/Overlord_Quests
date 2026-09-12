# Tagged Anchor Death Runtime Test Protocol

Status: TECHNICAL RUNTIME VALIDATION / DEVELOPMENT CONTENT ONLY

This protocol validates `questlog:entity_died`, explicit entity scoreboard-tag matching, anchor protection release, and automatic failure consequence output as one integrated chain. The Villagers and synthetic facts below are development fixtures and establish no OVERLORD REIGN story canon.

## Installation

Use the CI-generated provider test kit from the exact commit under test. The kit includes:

```text
config/questlog/quests/overlord_anchor_death_dev.json
```

Exactly one mod with technical ID `questlog` must be present.

Launch an unpublished local single-player world with commands available.

## Clean state

Remove old fixtures and synthetic state:

```text
/kill @e[type=minecraft:villager,tag=oq_anchor_death_dev]
/questlog narrative fact clear questlog:dev_anchor_death_gate
/questlog narrative fact clear questlog:dev_anchor_died
/questlog progress reset questlog:overlord_anchor_death_dev
```

## Locked death pass

Summon an unprotected tagged target while the synthetic gate is still absent:

```text
/summon minecraft:villager ~2 ~ ~ {Tags:["oq_anchor_death_dev","oq_anchor_death_target"],NoAI:1b,CustomName:'{"text":"[DEV] Locked Death Target"}'}
```

Kill it with unattributed generic damage:

```text
/damage @e[type=minecraft:villager,tag=oq_anchor_death_target,limit=1] 100 minecraft:generic
```

Then query:

```text
/questlog narrative fact get questlog:dev_anchor_died
```

Expected result: the consequence fact is absent. `entity_died` may observe the world event, but ordinary objective mutation is blocked while the quest is still locked.

Reset the mechanical fixture before the active pass:

```text
/questlog progress reset questlog:overlord_anchor_death_dev
/questlog narrative fact clear questlog:dev_anchor_died
```

## Tag-filter pass

Open the synthetic gate:

```text
/questlog narrative fact set questlog:dev_anchor_death_gate
```

Summon one control Villager without the target identity tag:

```text
/summon minecraft:villager ~-2 ~ ~ {Tags:["oq_anchor_death_dev","oq_anchor_death_control"],NoAI:1b,CustomName:'{"text":"[DEV] Anchor Death Control"}'}
```

Kill the control with generic damage:

```text
/damage @e[type=minecraft:villager,tag=oq_anchor_death_control,limit=1] 100 minecraft:generic
```

Query the consequence fact again. It must still be absent. Matching entity type alone is insufficient because the fixture also requires the exact `oq_anchor_death_target` scoreboard tag.

## Protection and release pass

Summon a tagged protected target:

```text
/summon minecraft:villager ~2 ~ ~ {Tags:["oq_anchor_death_dev","oq_anchor_death_target","overlord_quest_protected"],NoAI:1b,CustomName:'{"text":"[DEV] Protected Death Target"}'}
```

Read its health, then attempt ordinary generic damage:

```text
/data get entity @e[type=minecraft:villager,tag=oq_anchor_death_target,limit=1] Health
/damage @e[type=minecraft:villager,tag=oq_anchor_death_target,limit=1] 5 minecraft:generic
/data get entity @e[type=minecraft:villager,tag=oq_anchor_death_target,limit=1] Health
```

Expected result: health is unchanged while `overlord_quest_protected` remains present.

Now perform the authored-release analogue by removing only the generic protection tag:

```text
/tag @e[type=minecraft:villager,tag=oq_anchor_death_target,limit=1] remove overlord_quest_protected
```

Do not remove `oq_anchor_death_target`. That is the persistent identity selector the objective needs at the death boundary.

Kill the target with unattributed generic damage:

```text
/damage @e[type=minecraft:villager,tag=oq_anchor_death_target,limit=1] 100 minecraft:generic
```

Then query:

```text
/questlog narrative fact get questlog:dev_anchor_died
```

Expected result: the fact is present.

This proves the death detector is matching the victim entity and does not require direct player kill credit.

## Save and reload pass

Save and quit to title, reload the world, then query:

```text
/questlog narrative fact get questlog:dev_anchor_died
```

Expected result: the synthetic death fact remains present.

## No retrospective reconstruction pass

Clear the synthetic death fact administratively without resetting the failed quest:

```text
/questlog narrative fact clear questlog:dev_anchor_died
```

Wait several seconds and query again.

Expected result: it remains absent. `entity_died` is event-driven and the already-dead entity no longer exists. The persisted failure consequence applied bit also prevents repeated replay merely because an administrator erased the monotonic fact.

This mismatch is deliberately artificial and exists only to validate the boundary.

## Failure interpretation

The pass fails if any of the following occur:

- a locked target death writes `questlog:dev_anchor_died`;
- killing the untagged control satisfies the tagged death condition;
- normal generic damage harms the protected target before protection removal;
- the tagged target dies after protection removal but the active quest does not fail;
- the failure occurs but `questlog:dev_anchor_died` is not written;
- a direct player killer is required for the death to count;
- the synthetic fact disappears after normal save/reload.

A command or mod that directly discards an entity without producing the normal entity-death event remains a separate compatibility case. This protocol uses the server `/damage` path specifically to validate death-event behavior rather than raw entity removal.

## Evidence to retain

Retain:

- `latest.log`;
- protected health before and after the blocked damage attempt;
- the absent fact after the locked death;
- the absent fact after the untagged control death;
- the present fact after the tagged active death;
- the present fact after world reload;
- any quest synchronization, listener, reward, or entity-predicate error.
