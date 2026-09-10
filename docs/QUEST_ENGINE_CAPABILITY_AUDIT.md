# OVERLORD QUESTS Engine Capability Audit

Status: TECHNICAL / PREPARATORY - NOT STORY CANON

This document records source-derived quest-engine capabilities and limitations relevant to future OVERLORD REIGN quest authoring. It does not define quest chronology, story text, locations, rewards, faction outcomes, or other world canon.

Foundation B remains the active presentation milestone until the Gnarl popup receives direct in-game acceptance. The work here is preparatory hardening that can proceed without inventing story content.

## 1. Objective surface

Questlog 3.3.3 currently registers the following objective families in `QuestObjectiveRegistry`.

### Block

```text
questlog:block_mine
questlog:block_place
questlog:block_interact
```

These use `CachedRegistryPredicate<Block>` and therefore accept either an exact block ID or a `#namespace:tag` predicate.

### Entity

```text
questlog:entity_breed
questlog:entity_death
questlog:entity_kill
questlog:entity_approach
questlog:entity_tame
```

Entity matching supports exact entity IDs, entity tags, custom-name filtering, and a vanilla entity-predicate payload. `entity_approach` additionally requires a range and checks at one-second intervals.

### Logic

```text
questlog:and
questlog:or
questlog:not
```

These recursively compose ordinary Objective instances. Child objectives receive the same parent quest and register their own event listeners.

### Item

```text
questlog:item_craft
questlog:item_drop
questlog:item_equip
questlog:item_obtain
questlog:item_use
```

Item matching accepts exact item IDs, `#namespace:tag` predicates, and optional NBT predicates. The matcher may also intentionally operate without an exact item predicate.

### Miscellaneous

```text
questlog:stat
questlog:trample
questlog:enchant
questlog:effect_added
questlog:visit_biome
questlog:visit_dimension
questlog:visit_position
questlog:visit_structure
questlog:quest_complete
questlog:read
questlog:advancement
questlog:unobtainable
questlog:origin
```

`visit_biome`, `visit_dimension`, `visit_position`, `visit_structure`, `stat`, `advancement`, and `origin` use periodic polling rather than per-tick heavy world scans.

## 2. Reward surface

`QuestRewardRegistry` currently registers:

```text
questlog:item
questlog:command
questlog:experience
questlog:loot_table
questlog:choice
```

Common reward behavior supports `auto_claim`.

`questlog:item` gives an ItemStack to the player. `questlog:experience` can award points or levels. `questlog:loot_table` evaluates a named loot table for the player. `questlog:choice` recursively contains other rewards and requires exactly `pick_count` selections before it is claimable.

`questlog:command` is deliberately powerful: the configured command executes as the player command source with a configurable permission level, defaulting to level 2, and substitutes the target player's name for `{player}`, `%player%`, `@p`, and `@s`. Production quest authoring must therefore treat command rewards as privileged implementation, not ordinary flavor data.

## 3. Definition distribution

OVERLORD QUESTS now supports two definition layers:

1. approved definitions bundled in the mod JAR through `assets/questlog/overlord/definitions/index.json`;
2. external `config/questlog/` definitions loaded afterward as higher-priority overrides.

The bundled manifest remains intentionally empty. Development fixtures are not production content and the validator rejects `_dev` definitions from the bundled manifest.

## 4. Validation hardening

The repository validator now understands the registered Questlog objective and reward type surface rather than checking only generic JSON shape.

It additionally validates:

- known `questlog:` objective IDs;
- known `questlog:` reward IDs;
- recursive `and`, `or`, and `not` objectives;
- recursive choice rewards;
- `#namespace:tag` registry predicates for matching fields;
- required block/resource/range/bounds fields for source-defined built-in objective types;
- impossible choice-reward selection counts;
- common reward field types.

Custom non-`questlog` namespaces remain extension points. The validator applies the common structural contract to them but does not invent schemas for future compatibility objectives.

A repository self-test script exercises positive and negative validator cases and is part of normal Forge CI. The first authoritative run containing the self-test stage, run `34530724069`, passed the full Forge pipeline.

## 5. Position objective dimensional safety

### Upstream behavior

The original `visit_position` objective compared only the player's block coordinates with its configured bounding box. It did not record or test a dimension.

That behavior is safe for intentionally dimension-agnostic coordinates, but it is unsafe for future world-specific quests because identical coordinates in another dimension could satisfy the objective.

### OVERLORD QUESTS correction

`VisitPositionObjective` now supports an optional `dimension` resource ID.

If `dimension` is absent, original Questlog behavior is preserved. If it is present, both the dimension and bounding box must match during the same poll before progress can increment.

This is an engine capability only. No Dark Tower, settlement, quest, or other canonical location has been assigned coordinates by this change.

## 6. Trample editor metadata correction

The inherited `QuestObjectiveRegistry` advertised a `block` editor field for `questlog:trample`, but `TrampleObjective` does not read a block predicate. It listens specifically for the farmland-trample event.

Status: FIXED.

The misleading block field has been removed from the editor metadata. `questlog:trample` now exposes only `required_amount`, matching the actual runtime objective contract. This does not change trample gameplay behavior or establish any OVERLORD REIGN quest design.

## 7. Origins compatibility

`questlog:origin` delegates to the platform helper. On Forge, `ForgePlatformHelper.hasOrigin` returns `false` when the `origins` mod is not loaded.

The current captured OVERLORD REIGN 207-JAR baseline does not list Origins. Therefore `questlog:origin` is currently available as inherited engine capability but is not a useful objective for the captured pack state.

No Origins dependency should be added merely to justify retaining this inherited objective.

## 8. Triggers library packaging

Many inherited Questlog objectives use the Triggers event library. The Forge build embeds `maven.modrinth:triggers:1.0.1-1.20.1-forge` through jar-in-jar packaging.

The build emits a warning that jar-in-jar packaging could conflict if another mod also supplies the same library through a normal Maven/runtime path. The current captured 207-JAR OVERLORD REIGN roster does not list a standalone Triggers JAR, so this is a regression watch rather than a demonstrated current conflict.

Status: WATCH ONLY.

## 9. Persistence and reload review

Quest state is stored per player in `<uuid>.questlog.dat` under the world's playerdata directory. A manager reload serializes current in-memory quest state, recreates quests from the current definitions, then restores compatible state by quest ID. Disk load subsequently overlays the player's persisted state. This allows definitions to change without blindly discarding progress for IDs that still exist.

`Quest.serialize()` persists objective, prerequisite, failure, reward, repeatable/global, and sent-trigger/completion state. Deserialization limits list restoration to the shorter of the saved and current lists, preventing an index failure when a definition changes objective counts.

This behavior is adequate for the current bootstrap. However, objective state remains position-based within each list. Reordering objectives in a live production quest can therefore associate existing saved units with a different objective. Production authoring should treat objective/prerequisite/failure ordering as save-compatible data once released, unless an explicit migration is implemented.

Status: AUTHORING COMPATIBILITY RULE.

## 10. Foundation B boundary

None of this preparatory hardening closes Foundation B.

Foundation B still requires direct unpublished-local-single-player review of the approved Gnarl popup for:

- actual transparency in Minecraft;
- clipping and anchoring;
- title/body readability;
- GUI-scale behavior;
- exactly-once unlock audio;
- queued-popup retry reliability;
- final decision on whether native Questlog overlay controls are sufficient.

Until that review is complete, portrait scale, parchment placement, and popup composition remain implementation-test values.

## 11. Current authoring policy

Future quest content should use the narrowest native objective that accurately expresses the approved design. New custom objectives or reward bridges should be added only when an approved quest cannot be represented reliably with the existing engine.

Once a production quest has persistent player progress, changing the order or semantic meaning of entries in its prerequisite, objective, failure, or reward lists should be treated as a save migration concern rather than a harmless JSON edit.

Engine capability must not be mistaken for story authorization. In particular, the presence of visit-position, command-reward, structure, dimension, Origins, or other technical primitives does not establish that OVERLORD REIGN uses them in any specific quest.
