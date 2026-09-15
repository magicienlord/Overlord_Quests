# OVERLORD QUESTS Current Implementation Status

Status: SOURCE IMPLEMENTATION CLOSED / REPOSITORY CI QUALIFIED / FULL-INSTANCE QUALIFICATION PENDING

This file records the repository implementation boundary. It does not create OVERLORD REIGN canon. Lore authority remains `magicienlord/Overlord_Lore_and_Canon`, read-only from this repository.

## Runtime target

```text
Minecraft Java 1.20.1
Forge 47.4.10
Java 17
Primary branch: gnarl-bootstrap
Lore authority head: 649bae2fe49f9da210bcf6d7400316f3e4413464
```

OVERLORD REIGN is a single-player project. Dedicated-server boot remains a compatibility smoke boundary rather than a player-facing multiplayer support promise.

## Repository qualification checkpoint

The last implementation checkpoint before this documentation reconciliation is:

```text
dbc2d328fd90c061cf9f85ff216c80f8110be346
Resolve provider smoke runtime before offline launch
```

All 17 workflows triggered for that exact checkpoint completed successfully. This includes Forge build, dedicated-server smoke, client bootstrap smoke, narrative-state persistence smoke, provider runtime smoke, remaining-questline closure, Minion recovery, Tower restoration, civilization validation, quest-anchor protection, core magic, adventure, ending, optional objectives, system reactions, Illager and Piglin campaign contracts.

Provider Runtime Smoke #3 is green. Its two preceding failures were CI dependency-resolution defects that occurred before provider assertions. The corrected smoke resolves the launch runtime online, then performs the actual server launch and provider assertions offline.

Repository qualification therefore no longer blocks source-complete status. A later production implementation change must establish its own exact-head green CI evidence.

## Engine state

Implemented in the repository:

- bundled production quest definitions and config override workflow;
- persistent narrative facts and civilization dispositions;
- local NPC-provider infrastructure with exact UUID binding where a physical provider is actually required;
- sequence-break-safe history objectives;
- Overlord Minions owner-state integration;
- production central ending and persistence;
- sparse one-time system reactions for systems intentionally kept outside the journal;
- generalized Gnarl lifecycle commentary with persisted presentation state, bounded reminders, progress updates, warnings, success/failure reactions and delayed post-quest remarks;
- optional NightWalker owner-state objectives without a hard Nycto Java dependency;
- contextual Lestat guidance without a required spawned Lestat entity;
- source-backed Historian-led Overlord Depths / Fathoms integration;
- Forge server/client bootstrap and narrative-state smoke boundaries.

## Campaign coverage

Opening, Tower restoration, six assigned core-magic families, nine assigned adventure families, Quaver, Pet Cemetery, central End completion, Minion recovery, NightWalker/Lestat, Overlord Depths/Fathoms and the generalized civilization surface are represented in production content.

Generalized civilization main-entry coverage is **10/10**: Villagers, Illagers, Dwarves, Gnumus, Goblins, Kobolds, Piglins, Ribbits, Sea Dwellers and Umvuthana. Demons remain explicitly outside that generalized system.

Villager and Illager provider handling remains local-role/provider logic. No fixed final-world coordinate is a Quest completion dependency.

## Tower restoration

Formal Tower Restoration remains gated by exactly seven core operational milestones:

1. Throne Room;
2. Forge;
3. Minion infrastructure;
4. Gates;
5. Treasury;
6. Storage Room;
7. Armory.

Selected magical Tower functions are separate optional activation branches: Alchemy, Theurgy, Gluttony, spell study/making, Eidolon and Biomancy. Biomancy activation is represented by establishing a working `biomancy:biomancy/bio_forge`. It does not absorb Decomposer, Digester, Bio-Lab, Injector, Cradle or deeper Biomancy mastery, and it does not gate formal Tower completion.

## Gnarl's Ramblings

Gnarl's Ramblings is implemented as a dedicated lifecycle-commentary layer rather than hidden quests or an expansion of narrative facts.

The runtime provides bounded authored commentary for major quest transitions, including objective clarification, branch framing where applicable, first and repeated reminder variants, objective-count updates, warnings, success/failure reactions and delayed post-quest comments. Coverage deliberately includes all six core magic families, all nine dedicated adventure families, major campaign/Tower transitions and the central End sequence.

Presentation state is world-scoped and player-specific, separate from quest/narrative state. Existing saves baseline already-active quests so a newly installed build does not spam historical introductions.

## NightWalker / Lestat boundary

Status: IMPLEMENTED AGAINST SUPPLIED ALPHA.3.

Exact supplied build:

```text
nycto-forge-1.20.1-overlordreign-1.0.0-alpha.3.jar
SHA-256 a24de1fb23b83bc15263e40511782a8b46f6dd151b55e541e8727dfa0fe21503
```

Questlog reads Nycto-owned player state:

- `Nycto.vampire` for completed transformation;
- `Nycto.powerMask` for choosable Vampire Altar purchases;
- registered `nycto:vampirism` as the runtime-presence guard.

The four-step Lestat sequence covers arrival after completed transformation, deliberate blood replenishment, Vampire Altar use and at least one real altar power purchase. Lestat is contextual quest presentation, not a permanent physical provider requirement. No dedicated Lestat anchor tag, protection tag or Tower coordinate is required to progress the arc.

## Minion recovery fidelity

Overlord Quests owns the authored Brown, Red, Green and Blue recovery progression. `Overlord_Minions` remains authoritative for durable command/unlock state.

Brown remains the Master's Staff bootstrap. The production Red, Green and Blue action/confirmation pairs are authored, indexed and guarded by `tools/validate_minion_recovery_contract.py`.

Their recovery anchors are:

```text
Red   -> Blaze Rod
Green -> Spider Eye
Blue  -> Prismarine Crystals
```

Each action calls the public Minion progression API in enforced order. Each confirmation waits for authoritative owner state before writing the narrative recovery fact. These materials are authored recovery objectives, not temporary Hive substitutes.

## Overlord Depths / Fathoms

Status: IMPLEMENTED AGAINST VALIDATED DEPTHS CHECKPOINT.

```text
repository: magicienlord/Overlord_Depths
branch: validation/source-closure-direct-2026-09-13
head: b00ef7a33550267a826f98d4989f6ea63e6909a9
tree: 25e8c3a3de67543fe4f62a02ae3625a471423068
```

The five-step sidequest uses the real `fathoms:historian` profession and observes native Fathoms nautical progression. Questlog does not replace Fathoms mechanics or invent a boss/lore ending.

## Remaining qualification boundary

Repository source/content implementation is closed against the pinned Lore authority, and the exact implementation checkpoint above is green across the complete 17-workflow repository suite.

What remains is assembled-instance acceptance, not unimplemented Quest source. The manual/integration boundary includes direct gameplay confirmation of optional-mod handoffs, presentation, save/reload behavior where the owner mod is required, and the final reconciled campaign flow in the assembled OVERLORD REIGN instance.

The detailed evidence and remaining acceptance matrix are recorded in `docs/FULL_INSTANCE_QUALIFICATION.md`.

Until those assembled-instance checks are recorded, this repository should be described as source-complete and repository-qualified, not fully gameplay-qualified for release.