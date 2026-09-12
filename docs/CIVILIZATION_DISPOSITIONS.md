# OVERLORD REIGN Civilization Dispositions

Status: TECHNICAL IMPLEMENTATION OF APPROVED QUEST ARCHITECTURE

## Authority

OVERLORD REIGN uses explicit civilization political states rather than a numeric reputation, friendship, morality, domination, destruction, or corruption meter.

A disposition is world-scoped current state for one authored civilization polity. It is distinct from a narrative fact:

- a narrative fact records that an event happened and is normally monotonic;
- a disposition records the polity's current exclusive political state and may be replaced by a later authored outcome.

The generalized disposition roster is the newer ten-civilization roster established by `magicienlord/Overlord_Lore_and_Canon`:

- Villagers;
- Illagers;
- Dwarves;
- Gnumus;
- Goblins;
- Kobolds;
- Ribbits;
- Sea Dwellers;
- Piglins;
- Umvuthana.

Demons are not part of the generalized disposition system.

## Runtime default

`OverlordNarrativeState` returns the technical default:

```text
questlog:unresolved
```

when no explicit state has been written for a civilization.

`questlog:unresolved` is not a political outcome. It means campaign content has not yet committed one.

## Production state registry

### `overlord_reign:neutral`

Status: ACTIVE PRODUCTION STATE

Current production writer:

```text
campaign/civilizations/umvuthana/first_contact
```

The designated canonical Grove enters this state only after the Overlord completes the legitimate mask-gated first audience with its Umvuthi.

For the canonical Umvuthana Grove this means:

- the peaceful audience has been completed;
- ordinary renewed hostility toward the non-offending Overlord is suppressed for the designated Umvuthi;
- the Overlord no longer needs to keep wearing an Umvuthana mask merely for ordinary peaceful presence before that Umvuthi;
- Mowzie's Mobs native misbehaviour state remains authoritative and can override that peace for the offending player;
- unrelated Umvuthis and Groves remain unchanged.

It does NOT mean:

- subjugation;
- alliance;
- universal Umvuthana neutrality;
- immunity from authored destructive consequences;
- erasure of Mowzie's native boss route or offence tracking.

No other bundled civilization first-contact definition currently writes `overlord_reign:neutral`. Goblin, Gnumu, Ribbit, Kobold, Sea Dweller, and Dwarven first contacts intentionally leave disposition unresolved.

### `overlord_reign:subjugated`

Status: PLANNED / IMPLEMENTATION-RECOGNIZED, NOT YET WRITTEN BY BUNDLED CAMPAIGN CONTENT

The newer civilization decisions establish SUBJUGATED as a legitimate authored outcome for civilizations where their individual design permits it.

The Umvuthi local-peace bridge already recognizes this state alongside `overlord_reign:neutral` so a future canonical-Grove subjugation does not accidentally restore ordinary mask-based hostility. No current bundled quest sets this state.

Its first production writer must be introduced together with the relevant civilization outcome quest and runtime consequences. The ID must not be used as a generic reward before that authored outcome exists.

## Authoring rules

Disposition changes must come from explicit player actions and authored quests. There is no autonomous drift.

Do not use disposition as a substitute for historical event facts. For example:

```text
overlord_reign:civilizations/umvuthana/contact_established
```

records that the first formal audience occurred, while:

```text
overlord_reign:neutral
```

records the canonical Grove's current political state after that audience.

Local anchor outcomes must remain local. A disposition attached to the designated Goblin Camp, Kobold Den, Ribbit Village, Sea Village, Dwarven successor hold, Piglin Nether Village, Umvuthana Grove, Illager Bastille, or another authored local polity must not silently rewrite every naturally generated member of that species or faction.

Do not add a new disposition state merely to represent a temporary quest phase when an ordinary quest/fact state is sufficient. The current Illager fearful/cowed phase is specifically expected to remain a quest-state phase layered over a normal disposition rather than automatically creating an extra global disposition value.

## Administrative surface

Authoring and test recovery use:

```text
/questlog narrative disposition get <civilization>
/questlog narrative disposition set <civilization> <state>
/questlog narrative disposition clear <civilization>
```

These commands are administrative/testing tools. Production political changes must be written by approved quest consequences rather than manual command use.
