# Lost Castle REIGN Questline

Status: PRODUCTION QUEST CONTENT / FINITE EXPEDITION

The Lost Castle is treated as a finite expedition, not as a progression mod with invented tiers or bosses.

Source verification against The Lost Castle 1.20.x implementation establishes:

- the generated structure is `tlc:lost_castle`;
- the mod provides a Cartographer map leading to the structure;
- the native castle garrison includes Vindicators, Evokers, and Witches;
- the castle contains multiple native loot/chamber tables;
- no unique Lost Castle boss or persistent campaign state exists to wrap.

REIGN therefore uses three authored stages:

1. **A Castle Off the Map** — retrospectively observe a real visit to `tlc:lost_castle` through `questlog:visit_structure_history`.
2. **Break the Lost Court** — after discovery, require fresh player kills of a Vindicator and an Evoker. This deliberately uses non-retroactive `questlog:entity_kill`; old global kills do not satisfy the assault before the castle is found.
3. **Nothing Left to Rule** — Gnarl closes the expedition and records `overlord_reign:adventure/lost_castle_expedition_completed`.

The second stage does not technically bind the killed vanilla mobs to castle coordinates. The discovery prerequisite is the least invasive reliable boundary available without patching The Lost Castle or adding a location-aware kill objective. That limitation does not justify inventing a boss, token, or parallel progression state.

The native castle remains explorable and lootable after the REIGN arc closes.

## Personnel continuity

The Dark Tower's Minion Cook is named **Gristle**. `open_gluttony_kitchen.json` records the name directly so “Minion Cook” remains a role rather than the character's displayed identity.
