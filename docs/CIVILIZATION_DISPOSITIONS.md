# OVERLORD REIGN Civilization Dispositions

Status: TECHNICAL IMPLEMENTATION OF APPROVED QUEST ARCHITECTURE

## Authority

OVERLORD REIGN uses explicit civilization political states rather than a numeric reputation, friendship, morality, domination, destruction, or corruption meter.

A disposition is world-scoped current state for one authored civilization polity. It is distinct from a narrative fact:

- a narrative fact records that an event happened and is normally monotonic;
- a disposition records the polity's current exclusive political state and may be replaced by a later authored outcome.

The generalized disposition roster is:

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

Current bundled writers:

```text
campaign/civilizations/umvuthana/first_contact
campaign/civilizations/illagers/the_bastille_bows
```

For the designated canonical Umvuthana Grove, NEUTRAL follows the legitimate first audience. The exact designated Umvuthi may remain peacefully accessible subject to Mowzie's native misbehaviour behavior; unrelated Umvuthis and Groves remain unchanged.

For the designated Illager Bastille, NEUTRAL is written only after the Overlord has broken the local command structure and completed the fearful/cowed audience with the exact protected intermediary. It represents local restraint under demonstrated force, not friendship, alliance, civilization-wide pacification, or a claim that all Illagers have surrendered.

The historical reason for the Illager transition is separately preserved by:

```text
overlord_reign:civilizations/illagers/authority_established
overlord_reign:civilizations/illagers/bastille_cowed
```

The disposition and historical facts remain separate state dimensions.

### `overlord_reign:subjugated`

Status: PLANNED / IMPLEMENTATION-RECOGNIZED, NOT YET WRITTEN BY BUNDLED CAMPAIGN CONTENT

The civilization decisions establish SUBJUGATED as a legitimate authored outcome where an individual civilization design permits it.

The exact Umvuthi, Piglin Chieftain and Illager Bastille audience bridges recognize this state only for their specifically authored local anchors so a future explicit local subjugation does not accidentally restore ordinary access hostility.

No current bundled quest sets `overlord_reign:subjugated`. Its first production writer must be introduced together with the relevant authored political outcome and consequences.

## Current first-contact and opening behavior

Production content intentionally does not force one generic political result across civilizations.

- Umvuthana first audience writes `overlord_reign:neutral` for the designated Grove.
- Illager hostile opening records `overlord_reign:civilizations/illagers/authority_established` but writes no disposition. The subsequent provider-native cowed audience records `overlord_reign:civilizations/illagers/bastille_cowed` and writes `overlord_reign:neutral` for that designated Bastille polity.
- Villager first contact records `overlord_reign:civilizations/villagers/contact_established` and leaves disposition unresolved.
- Piglin first audience records contact only and leaves disposition unresolved. Gold is an audience-access condition for the exact protected Chieftain, not a political outcome.
- Goblin, Gnumu, Ribbit, Kobold, Sea Dweller and Dwarven first contacts leave disposition unresolved.

This asymmetry is intentional. Political outcomes belong to each civilization's authored branch rather than to a universal first-contact rule.

## Illager transition precedence

The designated protected Illager intermediary has one narrow temporary exception so the post-victory cowed audience can actually occur.

The bridge applies restraint when:

- the Illager disposition is still technical `questlog:unresolved` and the local authority fact is present; or
- the current disposition is `overlord_reign:neutral`; or
- the current disposition is `overlord_reign:subjugated`.

Once any other explicit disposition is authored, that later disposition takes precedence over the old monotonic authority fact. The authority fact therefore cannot permanently pacify the intermediary after a future non-peaceful political outcome.

This precedence rule does not create a new production disposition ID. It ensures the bridge remains compatible with future authored states.

## Authoring rules

Disposition changes must come from explicit player actions and authored political quests. There is no autonomous drift.

Do not use disposition as a substitute for historical event facts. For example, the Illager authority and cowed facts record what happened; NEUTRAL records the designated Bastille polity's current political state after the cowed audience.

Local anchor outcomes must remain local. A disposition attached to the designated Goblin Camp, Kobold Den, Ribbit Village, Sea Village, Dwarven successor hold, Piglin Nether Village, Umvuthana Grove, Illager Bastille, Villager successor settlement, or another authored local polity must not silently rewrite every naturally generated member of that species or faction.

Do not add a new disposition state merely to represent a temporary quest phase when an ordinary quest/fact state is sufficient. The Illager fearful/cowed phase is represented by explicit history plus a normal disposition rather than by a special global `cowed` disposition.

## Administrative surface

Authoring and test recovery use:

```text
/questlog narrative disposition get <civilization>
/questlog narrative disposition set <civilization> <state>
/questlog narrative disposition clear <civilization>
```

These commands are administrative/testing tools. Production political changes must be written by approved quest consequences rather than manual command use.
