# OVERLORD QUESTS Engine Capability Audit

Status: TECHNICAL / PREPARATORY - NOT STORY CANON

This document records source-derived quest-engine capabilities and limitations relevant to future OVERLORD REIGN quest authoring. It does not define quest chronology, story text, locations, rewards, faction outcomes, or other world canon.

Foundation B remains an active presentation milestone. The current Gnarl portrait is a technical test asset that passes the mechanical repository gate; direct in-game popup-composition regression and exact final portrait-binary approval remain separate pending acceptance conditions.

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

These recursively compose ordinary Objective instances. Child objectives receive the same parent quest and register their own event listeners. Read capability now propagates through the objective tree so a read objective nested under a supported logic objective can still be recognized by the quest-details UI and server read gate.

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

Common reward behavior supports `auto_claim`, except that OVERLORD QUESTS rejects `auto_claim` on `questlog:choice` because a choice requires explicit player selection.

`questlog:item` gives an ItemStack to the player. `questlog:experience` can award points or levels. `questlog:loot_table` evaluates a named loot table for the player. `questlog:choice` contains ordinary reward choices and requires exactly `pick_count` unique valid selections before it is claimable.

Nested `questlog:choice` rewards are rejected. The inherited UI can recursively display them, but the client-to-server claim packet transfers only the selected indices of the top-level choice reward. Allowing nested choices would therefore display a selectable nested branch whose own selection state cannot be reproduced authoritatively on the server. This is a protocol limitation, not a story decision.

`questlog:command` is deliberately powerful: the configured command executes as the player command source with a configurable permission level, defaulting to level 2, and substitutes the target player's name for `{player}`, `%player%`, `@p`, and `@s`. Production quest authoring must therefore treat command rewards as privileged implementation, not ordinary flavor data.

## 3. Definition distribution

OVERLORD QUESTS supports two definition layers:

1. approved definitions bundled in the mod JAR through `assets/questlog/overlord/definitions/index.json`;
2. external `config/questlog/` definitions loaded afterward as higher-priority overrides.

The bundled manifest remains intentionally empty. Development fixtures are not production content and the validator rejects `_dev` definitions from the bundled manifest.

The in-game editor writes only into `config/questlog/quests` and `config/questlog/chapters`. The config loader derives all file IDs in those roots using the technical `questlog` namespace, so editor save/remove packets reject other namespaces rather than accepting an ID that cannot round-trip through the loader.

Quest and chapter definitions that cross the Questlog network have an explicit ceiling of 32,767 Java UTF-16 characters after compact JSON serialization. Repository-controlled definitions are checked by CI; editor packets, bundled definitions, config definitions, and remote mirror insertion enforce the same runtime contract. Oversized external quests enter the normal broken-quest fallback rather than loading successfully and failing later at synchronization.

## 4. Validation hardening

The repository validator understands the registered Questlog objective and reward type surface rather than checking only generic JSON shape.

It validates:

- known `questlog:` objective IDs;
- known `questlog:` reward IDs;
- recursive `and`, `or`, and `not` objectives;
- choice reward structure and selection counts;
- rejection of nested choice rewards;
- rejection of `auto_claim` on choice rewards;
- `#namespace:tag` registry predicates for matching fields;
- required block/resource/range/bounds fields for source-defined built-in objective types;
- common reward field types;
- bundled-manifest safety and the development-content boundary;
- definition wire-size limits and runtime loader guards.

Custom non-`questlog` namespaces remain extension points. The validator applies the common structural contract to them but does not invent schemas for future compatibility objectives.

A repository self-test script exercises positive and negative validator cases and is part of normal Forge CI. The authoritative Forge workflow also validates the current Gnarl popup test asset mechanically, reports static popup geometry, builds/reobfuscates the Forge JAR, performs JAR smoke checks, and assembles the Foundation B test kit.

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

The captured OVERLORD REIGN baseline used during this audit did not list Origins. Therefore `questlog:origin` is available as inherited engine capability but is not assumed to be a useful objective for the current pack state.

No Origins dependency should be added merely to justify retaining this inherited objective.

## 8. Triggers library packaging and reload listeners

Many inherited Questlog objectives use the Triggers event library. The Forge build embeds `maven.modrinth:triggers:1.0.1-1.20.1-forge` through jar-in-jar packaging.

The build emits a warning that jar-in-jar packaging could conflict if another mod also supplies the same library through a normal Maven/runtime path. The captured OVERLORD REIGN roster used during this audit did not list a standalone Triggers JAR, so this is a regression watch rather than a demonstrated current conflict.

Triggers 1.0.1 exposes listener registration and global `removeAllListeners`, but no individual-listener removal. Quest definitions can be hot-reloaded, which recreates Quest and Objective instances. Clearing the entire Triggers bus during a reload would also remove listeners belonging to other consumers of the library, so OVERLORD QUESTS does not do that.

Every Objective therefore checks that its parent Quest is still the exact instance installed in an active QuestManager before mutating units. QuestManager generations are explicitly deactivated on replacement or server/client shutdown. This makes callbacks retained by Triggers inert across both ordinary definition reloads and whole manager-generation replacement.

Questlog's private event bus is different because it is owned by this mod. It supports precise listener removal. Read and quest-completion objectives retain their exact listener instances and unregister them when a Quest is disposed. Quest replacement, manager reload, quest removal, and manager shutdown dispose the affected listener trees before references are dropped.

The private event bus now requires explicit event classes for registration rather than using runtime generic inference. This removes the inherited TypeTools runtime dependency and makes owned listener registration/removal deterministic.

The remaining limitation is retention inside the shared Triggers bus itself. Obsolete callbacks can still consume a small amount of memory and dispatch overhead until that shared bus is cleared by its own lifecycle. They cannot mutate current quest state. Calling Triggers' global `removeAllListeners` during a Questlog reload remains prohibited because it could disable listeners belonging to another consumer.

Status: CORRECTNESS HARDENED / SHARED-BUS RETENTION AND JAR-IN-JAR DUPLICATION WATCH ONLY.

## 9. Persistence and reload review

Quest state is stored per player in `<uuid>.questlog.dat` under the world's playerdata directory. A manager reload serializes current in-memory quest state, disposes the superseded Quest instances, recreates quests from the current definitions, then restores compatible progress by quest ID. Disk load subsequently overlays the player's persisted state.

Objective progress is clamped at runtime to the range from 0 through the current definition's `required_amount`. A malformed non-positive runtime `required_amount` is normalized to 1. This prevents corrupt or stale NBT from creating negative progress or progress values beyond the current objective contract.

Player quest data and the global-quest data file are written through a sibling temporary file and then replaced. Filesystems that support atomic moves use `ATOMIC_MOVE`; other filesystems fall back to a normal replace only after the temporary compressed NBT has been written successfully. This materially reduces the chance of a process interruption leaving a partially-written quest save.

`repeatable` and `global` are definition-authoritative fields. They remain written into NBT for compatibility with inherited readers, but current OVERLORD QUESTS deserialization does not allow persisted values to override the active definition. The inherited behavior could silently undo an editor/config change to either flag and, in the `global` case, could keep a quest participating in cross-manager synchronization after its definition stopped being global.

Objective and reward state remains position-based within each list. Reordering entries in a live production quest can therefore associate existing saved state with a different entry. Choice rewards also persist selected indices positionally. Production authoring should treat prerequisite/objective/failure/reward ordering, including choice ordering, as save-compatible data once released unless an explicit migration is implemented.

Choice rewards sanitize persisted selected indices against the current definition. Duplicate or out-of-range indices are discarded. If an old save claims a choice reward but its surviving selected indices no longer satisfy the current `pick_count`, the reward is reopened instead of preserving an incoherent claimed state.

Status: PERSISTENCE HARDENED / POSITIONAL AUTHORING COMPATIBILITY RULE REMAINS.

## 10. Client-to-server packet authority

The inherited packet surface includes client-originating requests for reward collection, quest read acknowledgement, repeatable reset, and in-game editor save/remove operations.

OVERLORD QUESTS treats the server as authoritative for each request:

- `QuestRewardCollectPacket` requires a server player sender, a live manager, an existing quest, completed quest state, a valid reward index, and an unclaimed reward. Choice selections must contain exactly `pick_count` unique in-range indices and are validated before authoritative state is mutated. Selection data attached to a non-choice reward is rejected.
- `QuestReadPacket` requires a server player sender, a live manager, an existing quest, triggered and nonfailed state, and at least one incomplete read objective in the objective tree before the server posts the read event. Completed quests cannot be advanced by a forged read packet.
- `QuestResetPacket` requires a server player sender, a live manager, an existing quest, `repeatable=true`, completed state, and fully rewarded state. A forged packet therefore cannot erase an in-progress repeatable quest or bypass a pending reward/choice.
- Quest/chapter editor save and remove packets require a permission-level-2 server player, an active manager, and server-side Questlog edit mode. They reject unsupported namespaces. Save packets reject malformed or null JSON and enforce the definition wire-size ceiling. All four editor operations use a normalized filesystem boundary check so an ID path cannot escape its assigned definition root.

Forge registers packet handlers with `consumerMainThread`, so the above state and filesystem mutation occurs on the logical main thread rather than the Netty networking thread.

These checks are defense-in-depth for a local single-player project and keep malformed or stale client state from becoming authoritative server state.

## 11. Network compatibility boundary

The fork retains the technical `questlog` mod ID during bootstrap, but its packet contract is no longer guaranteed to be wire-compatible with unmodified Questlog 3.3.3.

The Forge SimpleChannel advertises an OVERLORD QUESTS-specific protocol version and requires exact equality on both sides. The inherited channel previously accepted every remote protocol string. Exact matching prevents a mismatched Questlog client or server from being admitted merely because both sides expose the same technical mod ID and channel name.

This does not expand multiplayer support. Automatic Gnarl full-screen popups remain intentionally scoped to unpublished local single-player. The strict protocol is a correctness boundary for any connection path that does exist.

## 12. Choice reward protocol

The choice-reward protocol has an explicit supported contract:

- one top-level `questlog:choice` may contain non-choice rewards;
- `pick_count` must be at least 1 and cannot exceed the number of choices or the protocol selection ceiling;
- the player must supply exactly `pick_count` unique valid indices;
- `auto_claim` is not supported on choice rewards;
- nested choice rewards are not supported until the network protocol can carry nested selection state.

The claim packet validates selections without mutating the server-side ChoiceReward first. Only a valid packet updates the authoritative selected-index list and applies the reward. This prevents an invalid packet from overwriting a valid selection state before being rejected.

## 13. Gnarl popup queue lifecycle

Automatic full-screen popups are deliberately limited to unpublished local single-player sessions.

The queue follows these invariants:

- the queue stores only quest resource IDs, not Quest object references;
- a quest ID cannot be queued twice simultaneously;
- logout clears the popup queue and resets the retry timer;
- the runtime session scope is checked again immediately before display, so a popup queued before Open to LAN cannot later appear as a full-screen popup in the published session;
- every queue-consumption path resolves the current quest instance by ID, including the LAN fallback-to-toast path, so a definition reload cannot leave stale display data attached to a queued notification;
- removed or reset quests are discarded before display or fallback notification;
- an automatic popup waits while any GUI screen is active and opens only after screenless gameplay resumes.

The all-GUI deferral rule prevents the popup from closing a live container, replacing chat/editor input, or retaining a stale previous-screen reference. Storing IDs rather than Quest objects also removes a retained-reference path across hot definition reloads. The Foundation B test protocol contains a deterministic delayed-item procedure for testing this queue behavior.

## 14. Definition behavior versus saved progress

A quest definition owns behavior and presentation. Player NBT owns progress and claim state.

Current definition authority includes at least:

- `repeatable`;
- `global`;
- objective structure and required amounts;
- reward structure and choice `pick_count`;
- display data and popup behavior.

Persisted data is accepted only where it represents compatible progress. This separation is important for the in-game editor and config override workflow because editing behavioral flags must take effect immediately after a reload rather than being overwritten by the previous save.

The remaining positional-list limitation means structural edits still need migration discipline. Definition authority does not make arbitrary list reordering save-safe.

## 15. Foundation B boundary

None of this preparatory hardening closes Foundation B.

The current runtime Gnarl portrait is a technical test asset. Runtime validation records SHA-256 `699140666288f84fea0e916c0f77ad719e25acdec60f65d3f8838a0e616444ed`, but that mechanical identity check must not be misread as approval of the exact final corrected portrait binary.

Foundation B still requires direct unpublished-local-single-player review of the Gnarl popup implementation for:

- actual transparency in Minecraft;
- clipping and anchoring;
- title/body readability;
- GUI-scale behavior;
- exactly-once unlock audio;
- queued-popup retry reliability;
- final decision on whether native Questlog overlay controls are sufficient.

Portrait scale, parchment placement, and popup composition remain implementation-test values until that runtime review is accepted. Exact final portrait-binary approval remains a separate acceptance condition.

## 16. Current authoring policy

Future quest content should use the narrowest native objective that accurately expresses the approved design. New custom objectives or reward bridges should be added only when an approved quest cannot be represented reliably with the existing engine.

Once a production quest has persistent player progress, changing the order or semantic meaning of entries in its prerequisite, objective, failure, reward, or choice lists should be treated as a save migration concern rather than a harmless JSON edit.

Engine capability must not be mistaken for story authorization. In particular, the presence of visit-position, command-reward, structure, dimension, Origins, global, repeatable, or other technical primitives does not establish that OVERLORD REIGN uses them in any specific quest.

## 17. Integrated single-player definition-cache concurrency

`DefinitionUtil` stores quest and chapter definitions in static maps. In the target integrated single-player runtime, the logical client and integrated server execute in the same JVM and therefore share those exact static map objects.

The inherited client full-sync path treated the caches as client-local and cleared/repopulated them from the client thread. In integrated single-player that created a real concurrency hazard: the server could observe an empty or partially rebuilt definition cache while a full sync was being processed.

OVERLORD QUESTS now treats integrated-server synchronization differently:

- the server loads and owns the authoritative static definition cache;
- an integrated client consumes the received full-sync definitions directly to rebuild its local QuestManager but does not clear or repopulate the shared static caches;
- a remote multiplayer client, which has no integrated server sharing the JVM, continues to maintain its own definition mirror from the sync packet;
- static definition-cache readers and writers use the same `DefinitionUtil` class monitor, preventing GUI-side iteration while a server-side definition reload is rebuilding the fastutil maps.

Client logout deliberately does not clear the shared DefinitionUtil maps because integrated-server shutdown may still be in progress. Client-only transient advancement metadata and deferred packet state are cleared separately.

Status: TARGET-RUNTIME RACE HARDENED.

## 18. Full-sync input bounds

`QuestSyncPacket` carries four potentially large collections: quest definitions, chapter definitions, quest-state NBT, and advancement IDs.

The decoder validates collection counts before allocation. Current generous ceilings are:

```text
quest definitions:   65536
chapter definitions: 8192
quest data entries:  65536
advancement IDs:     131072
```

The packet also rejects duplicate map keys, null map values, null outgoing map keys/values, and null outgoing list entries. These checks do not alter normal wire structure or expand multiplayer support. They bound malformed or corrupt packet behavior before it can drive uncontrolled allocation or ambiguous last-write-wins state.

A full sync received before the local player exists still keeps only the newest pending full-sync packet. Individual deferred quest definitions are deduplicated by quest ID. Disconnect clears deferred connection state so one world/server cannot leak queued definitions into the next connection.

Status: MALFORMED FULL-SYNC INPUT HARDENED.

## 19. Administrative command targeting

Questlog's command suggestions expose chapter IDs as namespaced resource locations such as `questlog:main`, while quest JSON commonly stores the same chapter as the bare string `main`.

The inherited chapter-target comparison used raw string equality. As a result, selecting a suggested namespaced chapter could fail to match quests whose display data used the equivalent bare chapter form.

OVERLORD QUESTS normalizes both command targets and quest chapter strings to resource IDs before chapter-scoped progress operations. Bare chapter names are normalized under the retained technical `questlog` namespace. Command paths that require the server QuestManager also fail cleanly when that manager is unavailable rather than dereferencing a null singleton.

This is administrative tooling consistency only. It does not change quest chronology, progression design, or story content.

## 20. Active integration policy

Generic Questlog refactoring remains outside scope. Engine changes now require a concrete OVERLORD REIGN presentation, quest-authoring, or modpack-integration need.

The NPC sidequest-provider layer and the death-screen replacement are explicit active integration requirements, so their narrowly scoped engine work is permitted under this policy. This does not reopen unrelated upstream cleanup.

The inherited CurseForge, Modrinth, and external wiki publication targets remain removed from the private fork. Build output is local or GitHub Actions based unless the Overlord explicitly establishes another publication target.

Status: GENERIC HARDENING CLOSED; APPROVED INTEGRATION WORK ACTIVE.

## 21. NPC provider and disposition extensions

OVERLORD QUESTS now has a server-authoritative NPC sidequest-provider layer derived from the useful quest-giver boundary identified in the Villager Retaliation reference, without importing its numeric reputation system as the governing progression model.

Provider definitions can select entity IDs or entity tags, require role tags, restrict dimensions, restrict providers to authored coordinate bounds, require completed quest markers, and require authored world-scoped civilization disposition states. Provider entries are presented in deterministic authored quest order. Definitions may also provide state-specific dialogue for available, in-progress, ready-to-turn-in, and failed states; the engine does not invent missing story dialogue.

Selecting an available sidequest opens its authored offer state before acceptance. Acceptance is explicit, while declining is deliberately non-persistent and creates no hidden reputation, cooldown, mood, or rejection state. Accepted quests persist the issuing NPC identity and can require no provider turn-in, exact-provider turn-in, or any eligible provider turn-in. Client actions never decide eligibility; the logical server re-resolves provider identity, distance, quest existence, and current eligibility before mutating progress.

Administrative quest resets delegate to the provider-aware `Quest.resetProgress()` contract, and `/questlog trigger` does not bypass an unaccepted provider binding. Synthetic development fixtures and `/questlog narrative disposition` commands provide deterministic validation without defining canonical civilizations or states.

The optional `pool` field remains metadata only. No random, weighted, daily, rotating, cooldown, or limited-capacity scheduler is implied by its presence.

Status: IMPLEMENTATION SCAFFOLD GREEN IN CI; DIRECT FORGE RUNTIME ACCEPTANCE PENDING.

## 22. Death-screen integration boundary

The useful mechanical core of the Epic Death Screen reference has been integrated as an OVERLORD QUESTS client scaffold: exact vanilla-screen replacement, bounded delay, optional skip, optional non-hardcore auto-respawn, hardcore safety, lifecycle handling, and compatibility veto hooks.

The reference mod's VHS grain, scanlines, chromatic/tape effects, cassette framing, heartbeat/breathing/tape ambience, cassette audio, and stock humorous phrases are explicitly outside the OVERLORD presentation target and are guarded by a repository validator. The current dark neutral screen is implementation scaffolding only.

Status: MECHANICS GREEN IN CI; OVERLORD-SPECIFIC VISUAL PASS PLANNED.

## 23. Chapter-editor definition authority

The inherited chapter editor performed optimistic writes into `DefinitionUtil` before the logical server had validated or persisted the corresponding save packet. In integrated single-player those static caches are shared by client and server, so a client GUI could modify server-visible definition state before crossing the server authorization boundary.

OVERLORD QUESTS now keeps the chapter editor on the same authority model as the rest of the definition system:

- cache getters return isolated JSON snapshots;
- chapter membership changes are constructed from a deep-copied quest snapshot and sent only through `QuestEditSavePacket`;
- chapter saves are sent only through `ChapterEditSavePacket`;
- the editor no longer calls the legacy optimistic `putCachedQuest` or `putCachedChapter` sinks;
- server save, reload, and full-sync results are the only source of refreshed editor state;
- a new unsaved chapter cannot receive quest memberships before its own definition exists authoritatively;
- chapter IDs using a namespace other than the retained technical `questlog` namespace are rejected client-side before the editor closes, matching the existing server rule.

CI scans all Java call sites so the legacy optimistic cache writers cannot silently re-enter active editor code.

Status: INTEGRATED-SERVER EDITOR AUTHORITY HARDENED.

## 24. Cyclic quest-completion dependencies

`questlog:quest_complete` objectives dynamically query the target quest's completion state. A self-reference or multi-quest cycle can therefore recurse indefinitely if external configuration is malformed.

Repository-controlled definitions are checked as a directed dependency graph in CI and cyclic `quest_complete` edges are rejected before packaging. Runtime remains defensive for external config: `Quest.isCompleted()` tracks the active evaluation graph by quest-object identity and treats a detected cycle as incomplete rather than recursing into `StackOverflowError`.

A persistent malformed external cycle may be evaluated frequently by UI or objective checks, so the runtime warning is emitted only once per re-entered quest ID for the process lifetime. The completion result remains false on every detected recurrence; only duplicate log noise is suppressed.

Status: STATIC AUTHORING REJECTION PLUS RUNTIME FAIL-CLOSED GUARD.
