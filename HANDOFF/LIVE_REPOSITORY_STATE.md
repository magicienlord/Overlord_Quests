# Live Repository State at Handoff

Date captured: 2026-09-16
Repository: `magicienlord/Overlord_Quests`

## Exact implementation continuation point

```text
branch: gnarl-bootstrap
head: e0a0c8667e389f735b8c138227cf62cc889e28c3
commit: Pin exact Quest qualification artifact
commit date: 2026-09-15T03:19:58Z
```

`main` is not current implementation authority:

```text
main: 72bcc0d13e571d2d231b859029aaec210749fca1
```

The former handoff implementation checkpoint `526aeef195dbb7530ae1c0302a68c8ce42a459da` is 16 commits behind the preserved `gnarl-bootstrap` head.

## Exact-head validation evidence

GitHub exposes 17 check runs for `e0a0c866...`. During packaging the exact-head check set was inspected. The returned runs were completed and no `failure` or `cancelled` conclusion was found. Examples visible on the exact head include:

- `forge-build` — success;
- `dedicated-server-smoke` — success;
- `narrative-state-persistence` — success;
- `adventure-questline-contract` — success;
- `optional-objectives-contract` — success;
- `Ending Contract` / ending contract workflow — success;
- `Remaining Questline Closure` — success.

No CI was re-run during this handoff.

## Current repository self-description

At `e0a0c866...`, `docs/CURRENT_IMPLEMENTATION_STATUS.md` describes the source as repository-qualified/source-complete with final assembled-instance gameplay qualification still pending. It records an earlier fully qualified artifact checkpoint `3a5c9f266a52dca9c6a29180bcebc50075bca816`, and states that later production changes must establish their own exact-head evidence. The exact `e0a0c866...` check set provides the newer repository evidence noted above.

## Important V5 divergence from current production source

The repository is technically newer than the September 14 handoff, but its authored production content predates a large part of the current V5 design interview. These are known mismatches that must **not** be mistaken for current design authority.

### 1. Fresh single-player assumption

Current source contains sequence-break/history/backfill and server smoke machinery. The Overlord has now explicitly stated the intended world will be played fresh and solely by the Overlord. V5 authoring therefore does not need multiplayer design or defensive backtracking/recovery logic as a design requirement. Existing engine support may remain, but V5 should not be shaped around those concerns.

### 2. Gnarl reminder behavior

Current source implements generalized lifecycle commentary with reminder variants and progress updates. Newer V5 direction explicitly rejects repeated/persistent main-quest reminder clutter.

Current V5 rule:

- ordinary quests present the objective and complete normally;
- no periodic/persistent Gnarl reminders about open objectives;
- important campaign context may be referenced naturally from related quest text;
- occasional NPC Ramblings may acknowledge native achievements when appropriate;
- Ramblings must not become disguised reminder spam.

This newer rule supersedes the broad reminder behavior currently described in `docs/CURRENT_IMPLEMENTATION_STATUS.md`, `docs/RECONCILIATION_DEBT.md`, and existing Gnarl commentary implementation.

### 3. Tower Restoration gate

Current production documentation says formal Tower completion uses seven milestones and treats magical rooms as optional branches. V5 design has changed.

Current V5 initial Tower gate includes the defined initial functional set:

- Throne;
- Forge;
- Storage;
- Armory;
- Treasury;
- WayGates;
- Arena;
- Jail;
- Alchemy;
- Theurgy;
- Gluttony;
- Spell Study;
- Eidolon;
- Biomancy.

Minion Infrastructure is considered present/already restored and receives no restoration objective. Dragon Den remains excluded from the initial gate and is a later independent Tower development.

There is no standalone visible “Tower complete” quest/pop-up; aggregate readiness may be tracked silently for campaign gating.

### 4. Tower restoration anchors

V5 approved initial restoration blocks include:

- Throne — Necrolord Chair from Fantasy's Furniture;
- Treasury — Gold Barrel from Goblins Tyranny;
- Arena — Supplementaries Cage;
- Jail — Big Iron Grate from Abyssal Decor.

Other previously established anchors remain subject to technical registry-ID resolution from the instance.

### 5. Minion restoration

Current source uses Blaze Rod / Spider Eye / Prismarine Crystals as simple recovery anchors. V5 now has authored feats:

- Opening: craft the Master's Staff and summon the first Brown in the **same quest**;
- Red: kill a Blaze and return with a Blaze Rod;
- Green: brew Poison, poison the Overlord, then kill a Witch while poisoned;
- Blue: prepare Water Breathing, enter an Ocean Monument and kill an Elder Guardian.

Red → Green → Blue remains the fixed order. Minions themselves must not be required as controllable quest actors.

### 6. Civilization roster

Current source describes generalized civilization coverage as 10/10. V5 roster is eleven civilizations because Myrmex is now a dedicated civilization category. Demons remain excluded from generalized disposition.

### 7. Church of Sin

The older authoritative mod-assignment ledger and current repository include Church of Sin as a compact questline. The later V5 interview explicitly changed this: Church of Sin should remain a self-contained atmospheric Adventure discovery/dungeon with contextual acknowledgement/Rambling as appropriate, **not** a dedicated questline.

### 8. Bumblezone scope

Older assignment material says full native-progression questline. Later V5 decision narrows this to a compact Adventure arc around discovery, major civilization/Queen content and one meaningful culmination, while leaving the mod's large native advancement/tutorial surface alone.

### 9. Waystones

Older assignment says no Waystones quest integration. Newer V5 rule keeps that generally true but uses Waystones as the Dark Tower WayGates room implementation/restoration substrate. This does **not** create a standalone Waystones progression or quest XP cost.

### 10. NightWalker version boundary

Current Quest documentation references supplied alpha.3. The project later supplied/used alpha.4 (`nycto-forge-1.20.1-overlordreign-1.0.0-alpha.4.jar`) and the separate NightWalker repository has continued moving. Re-verify live NightWalker source before any future implementation changes. The V5 narrative rule remains that vampires are independent of the Great Cataclysm and Lestat is a noncorporeal presenter with five visual reaction states.

## Do not “fix” these during handoff

This package records the divergence only. The user explicitly requested handoff packaging and no other work. Production reconciliation belongs to the next implementation phase after V5 is authored and approved.
