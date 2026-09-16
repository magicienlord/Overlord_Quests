# OVERLORD REIGN V5 Ice & Fire Campaign Authority

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this focused companion resolves the Ice & Fire questions raised by `docs/V5_SUPPLIED_MOD_TECHNICAL_AUDIT_2026-09-16.md` and works with the civilization authority in `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md` and the detailed Myrmex route authority in `docs/V5_MYRMEX_CIVILIZATION_BLUEPRINT.md`. Later explicit decisions in this file supersede earlier pending or UNKNOWN notes.

## 1. Exact target artifacts

Primary Ice & Fire artifact:

`iceandfire-2.1.13-1.20.1-beta-5.jar`

SHA-256:

`2b80245fc9b7d6fdc61d71f9892f4c6114eb7f303f65634845aaab25f84d1e82`

Relevant compatibility add-ons:

- `ice_and_fire_delight-forge-1.20.1-0.2.5.jar`
- `ice_and_fire_spellbooks-2.3.2-1.20.1.jar`

## 2. Campaign allocation

Ice & Fire is deliberately distributed across existing V5 campaign categories rather than receiving one monolithic mod questline.

```text
Myrmex -> Civilization
Dragon research/mastery -> dedicated Ice & Fire progression
Dragon Forge / Dragon Den -> Dark Tower development
Ashlord -> Bosses'Rise central campaign
Ice and Fire Delight -> absorbed into relevant Farming / Gluttony content
Ice and Fire: Spellbooks -> absorbed into relevant Spell Study / Dragon Den content
miscellaneous creatures/items -> native play, Adventure use where justified, or sparse NPC Ramblings
```

Do not create extra Questlog categories merely because compatibility add-ons contain substantial item sets.

## 3. Myrmex civilization mapping

Ice & Fire owns the native per-hive opinion state. V5 owns the authored political terminal state of the designated canonical hive.

The systems are related but not interchangeable.

The complete approved inner political routes are recorded in `docs/V5_MYRMEX_CIVILIZATION_BLUEPRINT.md`.

### 3.1 Native thresholds

Verified native thresholds:

```text
0 to 24 -> hostile
25+ -> non-hostile
50+ -> native trade access
75+ -> Myrmex Staff command access
100 -> player-founded queen-colony path
```

A value of 100 has a specific native meaning associated with a colony founded from the player's own hatched Queen. V5 must not use 100 to represent conquest of an existing canonical hive.

### 3.2 Terminal political mapping

Approved V5 mapping:

#### NEUTRAL

The canonical hive reaches a final authored independent settlement at native opinion 50 or higher.

The political result remains `NEUTRAL` because the hive survives as an independent polity. Native trade access represents the practical peace reached by the authored route.

The approved route learns the hive's caste behavior, reaches 50+ through source-backed actions, gains trade access, completes a meaningful authored trade/colony relationship sequence, then ends with an explicit Queen-facing recognition of independence.

Native opinion alone does not create this terminal state.

#### SUBJUGATED

The canonical hive reaches a final authored submission at native opinion 75 or higher.

The Myrmex Staff command threshold represents practical authority over the existing hive without falsely claiming that the Overlord founded it.

The approved route reaches 75+, gains legitimate Staff command access for the existing canonical hive, performs at least one meaningful non-destructive hive command, then ends with a Queen-facing authored submission.

Native opinion alone does not create this terminal state.

#### DESTROYED

The destructive route deliberately turns against the designated canonical hive and kills its Queen.

The Queen kill is the political core of the route. Any additional colony function may be required only if the final source/objective pass proves it indispensable to the authored polity.

This is a local anchor result. Myrmex remain extant elsewhere.

The exact objective micro-sequence remains a source/objective detail. The political meaning is no longer unresolved, and the route must not be reduced to an arbitrary reputation threshold or generic Myrmex kill count.

### 3.3 Opinion protection after terminal resolution

Once the canonical hive reaches terminal `NEUTRAL`, its native opinion toward the Overlord must not fall below 50.

Once it reaches terminal `SUBJUGATED`, native opinion must not fall below 75.

This protection exists so native Ice & Fire AI cannot contradict the permanent authored political state through later incidental reputation loss.

The implementation should use the least invasive mechanism available. V5 establishes the required behavior, not the exact code path.

This is not a hidden OVERLORD QUESTS reputation system. It is stabilization of a source-owned mechanic after an authored terminal political resolution.

### 3.4 Pre-resolution reputation

Before terminal resolution, ordinary Ice & Fire reputation changes may be used as native mechanics inside the Myrmex civilization arc.

They may:

- open peaceful access;
- permit trade;
- support provider or Queen interaction;
- demonstrate growing practical access to the hive;
- satisfy a specific inner quest when the authored route genuinely requires that threshold.

They must never automatically complete `NEUTRAL`, `SUBJUGATED`, or `DESTROYED` merely because a numeric threshold was reached.

The civilization Questlog remains the political authority.

## 4. Dedicated dragon progression

The four-part dedicated dragon progression remains:

1. establish dragon knowledge through the Bestiary / research system;
2. kill a wild adult dragon and harvest meaningful dragon materials;
3. defeat or locate a sufficiently ancient female dragon and obtain a dragon egg;
4. hatch the egg, raise the owned dragon to native rideable maturity, and ride it.

### 4.1 Adult kill requirement

The broad native `kill_if_dragon` advancement proves a dragon kill but does not prove stage or adulthood.

The authored objective remains specifically an adult wild dragon. Implementation must preserve that requirement through a narrow source-aware bridge or another source-backed signal rather than weakening the design to the broad advancement.

### 4.2 Egg-producing ancient female

For V5, `sufficiently ancient female dragon` means a female Stage 4 or Stage 5 dragon in the exact beta 5 source model.

Stage 4 is already the mature adult range relevant to reproduction/egg context. Stage 5 remains the rarer elder tier but is not mandatory merely for difficulty inflation.

The egg objective requires the authored sequence to establish the intended female/age context. Mere later possession of any dragon egg is not equivalent if another acquisition path exists.

### 4.3 Rideable maturation correction

The previous phrase `saddle and ride it` is superseded.

The exact beta 5 build has no dragon saddle slot or native saddle requirement. A tamed dragon becomes mountable once it is over Stage 2.

The final authored requirement is therefore:

```text
hatch the acquired egg
-> raise the owned dragon to Stage 3 or later
-> ride the dragon
```

Do not add an artificial saddle, armor, or unrelated equipment gate.

## 5. Dragon Den and Dragon Forge

Dragon Forge development remains a Dark Tower / Dragon Den responsibility, not part of the four-step general dragon-mastery line.

The source-backed requirement for dragon breath makes the Dragon Den a meaningful functional Tower development.

Dragonsteel is not required to close the dedicated dragon-mastery progression.

Ashlord remains independent of this mastery arc and is handled through Bosses'Rise.

## 6. Ice and Fire Delight allocation

Approved V5 allocation: no standalone Ice and Fire Delight questline.

Selected recipes, ingredients, effects, or dishes may be absorbed into existing Farming, cuisine, Farmer's Spell, or Gluttony content when they materially improve those authored progressions.

Examples of content that may qualify include dragon-derived cuisine, unusual Myrmex-resin food, or a magical preparation whose effect supports a meaningful mastery objective.

The rest remains native gameplay or may receive sparse contextual NPC Ramblings when a native milestone is interesting enough to acknowledge.

Do not add filler merely to represent the compatibility add-on.

## 7. Ice and Fire: Spellbooks allocation

Approved V5 allocation: no standalone Ice and Fire: Spellbooks questline.

Selected content may be absorbed into:

- Iron's Spell Study progression where it meaningfully demonstrates magical crossover mastery;
- Dragon Den / Dragon Forge progression where the source recipe genuinely depends on dragon infrastructure.

The remainder stays native or may receive sparse contextual NPC Ramblings.

Equipment names such as `Dragon Priest` do not authorize the invention of a new Dragon Priest civilization, religion, institution, historical order, or quest faction.

Use item names as item identity only unless later source/canon authority establishes more.

## 8. Rambling discipline

Ramblings are acknowledgements, not a dumping ground for every unused compatibility item.

A leftover Ice & Fire or compatibility milestone qualifies for a Rambling only when:

- the player accomplished something meaningfully notable;
- an established presenter has a worthwhile in-character reaction;
- the acknowledgement does not duplicate an authored quest objective;
- the result helps the world feel observed without turning Questlog into a checklist.

Otherwise the content remains ordinary native play.

## 9. Production boundary

These are V5 campaign decisions only.

Do not modify current production quest definitions, Ice & Fire compatibility code, Myrmex AI, Dragon Forge integration, or presenter assets during this authoring pass.

The later implementation pass must translate these requirements using the exact pinned artifacts and the least invasive verified mechanisms.
