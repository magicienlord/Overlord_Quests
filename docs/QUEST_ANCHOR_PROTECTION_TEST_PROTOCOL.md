# Quest Anchor Protection Runtime Test Protocol

Status: TECHNICAL RUNTIME VALIDATION / DEVELOPMENT CONTENT ONLY

This protocol validates the explicit quest-anchor protection contract described in `docs/QUEST_ANCHOR_PROTECTION.md`. The test entity and tags below are development-only and establish no OVERLORD REIGN story canon.

## Scope

The pass verifies that:

- protection is opt-in and does not affect an otherwise identical unmarked entity;
- `overlord_quest_protected` blocks the normal server-side living damage path;
- a protected Mob is marked persistence-required when it joins the level;
- the protection scoreboard tag survives save and reload;
- removing the tag immediately returns the entity to ordinary damage behavior;
- the protection tag is independent from provider eligibility and does not itself make an entity a quest provider.

## Setup

Use the CI-generated `overlord-quests-provider-test-kit` for the exact commit under test.

In an unpublished local single-player world with commands available, remove any previous fixtures:

```text
/kill @e[type=minecraft:villager,tag=oq_anchor_protection_dev]
```

Summon one protected and one control Villager. Do not set `Invulnerable` or `PersistenceRequired`; those would hide the behavior under test.

```text
/summon minecraft:villager ~2 ~ ~ {Tags:["oq_anchor_protection_dev","oq_anchor_protected","overlord_quest_protected"],NoAI:1b,CustomName:'{"text":"[DEV] Protected Anchor"}'}
/summon minecraft:villager ~-2 ~ ~ {Tags:["oq_anchor_protection_dev","oq_anchor_control"],NoAI:1b,CustomName:'{"text":"[DEV] Control Anchor"}'}
```

## Protection pass

Inspect both health values:

```text
/data get entity @e[type=minecraft:villager,tag=oq_anchor_protected,limit=1] Health
/data get entity @e[type=minecraft:villager,tag=oq_anchor_control,limit=1] Health
```

Attack each Villager once with the same weapon and no strength/effect modifiers.

Expected result:

- the protected Villager keeps its original health;
- the control Villager loses health normally.

The protected Villager must not become a provider merely because it carries `overlord_quest_protected`.

## Persistence-required pass

Inspect the protected entity:

```text
/data get entity @e[type=minecraft:villager,tag=oq_anchor_protected,limit=1] PersistenceRequired
```

Expected result: the protected Mob reports `1b` after its server-level join hook has executed.

The control Villager is not required to report the same value.

## Save and reload pass

Save and quit to the title screen, reload the world, then run:

```text
/tag @e[type=minecraft:villager,tag=oq_anchor_protected,limit=1] list
/data get entity @e[type=minecraft:villager,tag=oq_anchor_protected,limit=1] PersistenceRequired
```

Expected result:

- `overlord_quest_protected` is still present;
- the entity still reports persistence-required state;
- attacking it still causes no normal living damage.

## Deliberate release pass

Remove protection from the exact development entity:

```text
/tag @e[type=minecraft:villager,tag=oq_anchor_protected,limit=1] remove overlord_quest_protected
```

Attack it again.

Expected result: it now takes normal damage. No restart, quest reload, provider refresh, or client UI action is required because protection is evaluated from the entity's current server-side scoreboard tags on each living attack.

## Failure interpretation

If the protected Villager takes damage, retain `latest.log` and record whether the source was an ordinary melee attack, projectile, environmental damage, or another mod's custom removal path.

If an ordinary melee attack bypasses protection, the runtime contract has failed.

A mod that directly discards or replaces an entity without using the normal living-attack path is a separate compatibility case and should not be described as covered by this protection layer until explicitly handled.

## Evidence

Retain:

- `latest.log`;
- one before/after health readout for the protected entity;
- one health readout proving the unmarked control takes damage;
- the `PersistenceRequired` readout;
- the scoreboard-tag list after world reload;
- the final health change after explicit protection removal.
