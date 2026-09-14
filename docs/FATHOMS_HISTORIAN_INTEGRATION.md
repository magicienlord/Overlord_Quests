# Overlord Depths / Fathoms Historian Integration

Status: IMPLEMENTED AGAINST VALIDATED DEPTHS CHECKPOINT

This document records the implementation boundary for the dedicated Overlord Depths / Fathoms sidequest. It does not create Fathoms lore beyond the read-only quest authority or the mechanics demonstrated by the validated backport.

## Authority

Read-only quest authority requires a dedicated sidequest if Overlord Depths is implemented, prefers the existing Historian profession as the lead, permits a discovery and investigation escalation, and forbids inventing unsupported mechanics or a speculative climax.

## Validated technical source

The implementation was authored against the live validated Depths source boundary checked on 2026-09-14:

```text
repository: magicienlord/Overlord_Depths
branch: validation/source-closure-direct-2026-09-13
head: b00ef7a33550267a826f98d4989f6ea63e6909a9
tree: 25e8c3a3de67543fe4f62a02ae3625a471423068
commit: Audit Resin Clump compatibility bridge
```

All five exact-head workflows observed for that checkpoint succeeded, including the Forge build and both Core and YUNG Rocky Waters worldgen validation. This supersedes the earlier failing `9e1c5ed6dab40da0bc728abd9dcb62f59710ef4f` boundary.

## Source-backed provider

The backport preserves the source namespace `fathoms` and registers:

```text
villager profession: fathoms:historian
POI / workstation: fathoms:calligraphy_table
```

Questlog already supports exact Villager-profession provider matching. The sidequest therefore uses ordinary `minecraft:villager` entities whose native profession is exactly `fathoms:historian`. It does not invent a fixed Historian identity, coordinates, scoreboard role or replacement NPC.

Each accepted step is UUID-bound and returns to the same Historian who issued that step. Later steps may be offered by any eligible Historian once the preceding quest is complete, which preserves procedural-world freedom while keeping each individual transaction coherent.

## Production sequence

The five production definitions are:

1. `campaign/sidequests/fathoms/sounding_the_depths`
2. `campaign/sidequests/fathoms/an_aberration_on_the_line`
3. `campaign/sidequests/fathoms/open_the_coffer`
4. `campaign/sidequests/fathoms/catalogue_the_depths`
5. `campaign/sidequests/fathoms/a_bad_decision`

They observe these Fathoms-owned player-facing advancements in order:

```text
fathoms:nautical/use_bait
fathoms:nautical/catch_aberration
fathoms:nautical/open_coffer
fathoms:nautical/catch_all_fish
fathoms:nautical/make_a_bad_decision
```

The capstone is not an invented boss or imported Dredge plot. Depths' own source-closure audit treats `nautical/make_a_bad_decision` as a player-facing advancement and validates the native explicit grant path from the reconstructed Fathoms ritual logic. Questlog therefore uses that source-owned ritual milestone as the conclusion.

## Boundaries

- Questlog observes Fathoms progression; it does not replace fishing, coffers, items, rituals or advancement ownership.
- The arc begins after `overlord_reign:reign/initial_foundation_established`, keeping it optional to central progression.
- No fixed structure coordinates or named canonical ocean location are introduced.
- No Fathoms boss, deity, apocalyptic outcome or historical explanation is invented.
- No Dredge plot element is imported into OVERLORD REIGN.
- The wrapper writes no new durable narrative fact. Completion state of the five quests is sufficient for this optional investigation.

## Validation boundary

Repository validation checks production indexing, exact quest ordering, native Historian profession matching, exact native advancement IDs, same-provider turn-in and the absence of speculative narrative substitutions.

Full-instance qualification still needs to confirm presentation and the complete live handoff between the installed Depths Historian, native advancements and Questlog in the final modpack environment. That manual qualification is not a reason to keep the production definitions source-blocked.
