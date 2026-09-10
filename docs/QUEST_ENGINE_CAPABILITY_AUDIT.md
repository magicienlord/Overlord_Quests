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

Common reward behavior supports `auto_claim`, except that OVERLORD QUESTS now rejects `auto_claim` on `questlog:choice` because a choice requires explicit player selection.

`questlog:item` gives an ItemStack to the player. `questlog:experience` can award points or levels. `questlog:loot_table` evaluates a named loot table for the player. `questlog:choice` contains ordinary reward choices and requires exactly `pick_count` unique valid selections before it is claimable.

Nested `questlog:choice` rewards are currently rejected. The inherited UI can recursively display them, but the client-to-server claim packet transfers only the selected indices of the top-level choice reward. Allowing nested choices would therefore display a selectable nested branch whose own selection state cannot be reproduced authoritatively on the server. This is a protocol limitation, not a story decision.

`questlog:command` is deliberately powerful: the configured command executes as the player command source with a configurable permission level, defaulting to level 2, and substitutes the target player's name for `{player}`, `%player%`, `@p`, and `@s`. Production quest authoring must therefore treat command rewards as privileged implementation, not ordinary flavor data.

## 3. Definition distribution

OVERLORD QUESTS supports two definition layers:

1. approved definitions bundled in the mod JAR through `assets/questlog/overlord/definitions/index.json`;
2. external `config/questlog/` definitions loaded afterward as higher-priority overrides.

The bundled manifest remains intentionally empty. Development fixtures are not production content and the validator rejects `_dev` definitions from the bundled manifest.

The in-game editor writes only into `config/questlog/quests` and `config/questlog/chapters`. The config loader derives all file IDs in those roots using the technical `questlog` namespace, so editor save/remove packets now reject other namespaces rather than accepting an ID that could not round-trip through the loader.

## 4. Validation hardening

The repository validator understands the registered Questlog objective and reward type surface rather than checking only generic JSON shape.

It validates:

- known `questlog:` objective IDs;
- known `questlog:` reward IDs;
- recursive `and`, `or`, and `not` objectives;
- choice reward structure and selection counts;
- `#namespace:tag` registry predicates for matching fields;
- required block/resource/range/bounds fields for source-defined built-in objective types;
- common reward field types;
- bundled-manifest safety and the development-content boundary.

Custom non-`questlog` namespaces remain extension points. The validator applies the common structural contract to them but does not invent schemas for future compatibility objectives.

A repository self-test script exercises positive and negative validator cases and is part of normal Forge CI. The authoritative Forge workflow also validates the Gnarl portrait, reports static popup geometry, builds/reobfuscates the Forge JAR, performs JAR smoke checks, and assembles the Foundation B test kit.

Runtime choice-reward validation has advanced beyond the original validator rules: nested choice rewards and choice rewards using `auto_claim` are rejected at runtime because the present claim protocol cannot represent them safely. The repository validator should mirror these two restrictions before any production definitions are bundled.

## 5. Position objective dimensional safety

### Upstream behavior

The original `visit_position` objective compared only the player's block coordinates with its configured bounding box. It did not record or test a dimension.

That behavior is safe for intentionally dimension-agnostic coordinates, but it is unsafe for future world-specific quests because identical coordinates in another dimension could satisfy the objective.

### OVERLORD QUESTS correction

`VisitPositionObjective` supports an optional `dimension` resource ID.

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

## 8. Triggers library packaging and reload listeners

Many inherited Questlog objectives use the Triggers event library. The Forge build embeds `maven.modrinth:triggers:1.0.1-1.20.1-forge` through jar-in-jar packaging.

The build emits a warning that jar-in-jar packaging could conflict if another mod also supplies the same library through a normal Maven/runtime path. The current captured 207-JAR OVERLORD REIGN roster does not list a standalone Triggers JAR, so this is a regression watch rather than a demonstrated current conflict.

Triggers 1.0.1 exposes listener registration and global `removeAllListeners`, but no individual-listener removal. Quest definitions can be hot-reloaded, which recreates Quest and Objective instances. Clearing the entire Triggers bus during a reload would also remove listeners belonging to other consumers of the library, so OVERLORD QUESTS does not do that.

Instead, every Objective now checks that its parent Quest is still the current instance installed in its QuestManager before mutating units. Obsolete listeners may remain registered in the shared event bus, but they become inert and cannot drive stale quest state or redundant synchronization.

Status: HARDENED / JAR-IN-JAR DUPLICATION STILL WATCH ONLY.

## 9. Persistence and reload review

Quest state is stored per player in `<uuid>.questlog.dat` under the world's playerdata directory. A manager reload serializes current in-memory quest state, recreates quests from the current definitions, then restores compatible state by quest ID. Disk load subsequently overlays the player's persisted state. This allows definitions to change without blindly discarding progress for IDs that still exist.

`Quest.serialize()` persists objective, prerequisite, failure, reward, repeatable/global, and sent-trigger/completion state. Deserialization limits list restoration to the shorter of the saved and current lists, preventing an index failure when a definition changes objective counts.

This behavior is adequate for the current bootstrap. However, objective and reward state remains position-based within each list. Reordering entries in a live production quest can therefore associate existing saved state with a different entry. Production authoring should treat prerequisite/objective/failure/reward ordering as save-compatible data once released, unless an explicit migration is implemented.

Choice rewards additionally sanitize persisted selected indices against the current definition. Duplicate or out-of-range indices are discarded. If an old save claims a choice reward but its surviving selected indices no longer satisfy the current `pick_count`, the reward is reopened instead of preserving an incoherent claimed state.

Status: AUTHORING COMPATIBILITY RULE.

## 10. Client-to-server packet authority

The inherited packet surface includes client-originating requests for reward collection, quest read acknowledgement, repeatable reset, and in-game editor save/remove operations.

OVERLORD QUESTS now treats the server as authoritative for each of these requests:

- `QuestRewardCollectPacket` requires a server player sender, a live manager, an existing quest, completed quest state, a valid reward index, and an unclaimed reward. Choice selections must contain exactly `pick_count` unique in-range indices and are validated before authoritative state is mutated. Selection data attached to a non-choice reward is rejected.
- `QuestReadPacket` requires a server player sender, a live manager, and an existing quest before posting the read event.
- `QuestResetPacket` requires a server player sender, a live manager, an existing quest, and `repeatable=true`. Global and per-player reset paths are then handled by their existing server-side routines.
- Quest/chapter editor save and remove packets require a permission-level-2 server player. They reject unsupported namespaces. Save packets reject malformed or null JSON. All four editor operations use a normalized filesystem boundary check so an ID path cannot escape its assigned definition root.

These checks are defense-in-depth for a local single-player project and keep malformed or stale client state from becoming authoritative server state.

## 11. Choice reward protocol

The choice-reward protocol now has an explicit supported contract:

- one top-level `questlog:choice` may contain non-choice rewards;
- `pick_count` must be at least 1 and cannot exceed the number of choices;
- the player must supply exactly `pick_count` unique valid indices;
- `auto_claim` is not supported on choice rewards;
- nested choice rewards are not supported until the network protocol can carry nested selection state.

The claim packet validates selections without mutating the server-side ChoiceReward first. Only a valid packet updates the authoritative selected-index list and applies the reward. This prevents an invalid packet from overwriting a valid selection state before being rejected.

## 12. Gnarl popup queue lifecycle

Automatic full-screen popups are deliberately limited to unpublished local single-player sessions.

The queue now follows these invariants:

- a quest ID cannot be queued twice simultaneously;
- logout clears the popup queue and resets the retry timer;
- the runtime session scope is checked again immediately before display, so a popup queued before Open to LAN cannot later appear as a full-screen popup in the published session;
- queued entries resolve the current quest instance by ID before display, so a stale Quest object retained across a definition reload is not opened;
- removed or reset quests are discarded before display;
- an automatic popup waits while any GUI screen is active and opens only after screenless gameplay resumes.

The all-GUI deferral rule prevents the popup from closing a live container, replacing chat/editor input, or retaining a stale previous-screen reference. The Foundation B test protocol now contains a deterministic delayed-item procedure for testing this queue behavior.

## 13. Foundation B boundary

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

## 14. Current authoring policy

Future quest content should use the narrowest native objective that accurately expresses the approved design. New custom objectives or reward bridges should be added only when an approved quest cannot be represented reliably with the existing engine.

Once a production quest has persistent player progress, changing the order or semantic meaning of entries in its prerequisite, objective, failure, or reward lists should be treated as a save migration concern rather than a harmless JSON edit.

Engine capability must not be mistaken for story authorization. In particular, the presence of visit-position, command-reward, structure, dimension, Origins, global, repeatable, or other technical primitives does not establish that OVERLORD REIGN uses them in any specific quest.
