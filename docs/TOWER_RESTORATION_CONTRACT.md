# Dark Tower Restoration Contract

Status: IMPLEMENTED PRODUCTION CONTRACT

This document records how the current `Overlord_Quests` production definitions implement the authoritative Dark Tower restoration boundary.

## Governing boundary

Tower Restoration owns initial installation, furnishing, activation, or operational recovery of a Tower facility.

It does not own deeper mastery of the system housed in that room. Dedicated magic progression, equipment progression, storage expansion, wealth accumulation, and other later use remain outside Tower Restoration unless a separate authored quest explicitly needs them.

## Implemented core milestones

| Facility | Production evidence | Narrative marker |
| --- | --- | --- |
| Throne room | explicit `Take the Throne` authored action | `overlord_reign:tower/throne_room_operational` |
| Forge | native Hot Iron `hot_iron:local_smithery` advancement | `overlord_reign:tower/forge_prepared` |
| Minion infrastructure | confirmed Brown recovery / Master's Staff bootstrap | `overlord_reign:tower/minion_infrastructure_operational` |
| Gates Room | retrospective craft evidence for `waystones:waystone` | `overlord_reign:tower/gates_operational` |
| Treasury | retrospective craft evidence for secure entrance and dedicated container | `overlord_reign:tower/treasury_operational` |
| Storage Room | retrospective chest + barrel craft evidence | `overlord_reign:tower/storage_room_operational` |
| Armory | retrospective armor-stand + item-frame craft evidence | `overlord_reign:tower/armory_operational` |

The Gates Room uses the installed Waystones implementation as its travel substrate. The active pack contains `waystones-forge-1.20.1-14.1.20.jar`; its 1.20.1 recipe data produces `waystones:waystone`. The room restoration therefore uses Questlog's persistent exact-item craft statistic for one Waystone anchor.

This is an absorbed Tower-restoration use of Waystones, not a standalone Waystones questline and not a claim that Waystones are franchise Minion Gate machinery. Later destination discovery and ordinary fast travel remain owned by Waystones gameplay.

The Storage Room and Armory tasks establish only minimal functional furnishing. They do not create capacity tiers, collection checklists, or equipment completionism.

The Treasury task requires secure fittings, not an arbitrary quantity of gold. Wealth can later be expressed by normal play without becoming a restoration gate.

## Implemented selected magic facilities

The current authoritative room list is exactly Alchemy, Theurgy, Gluttony, spell study/making, and Eidolon. Their initial Tower milestones deliberately observe the owning system's first durable evidence that the facility is usable:

| Facility | Owner-state milestone | Narrative marker |
| --- | --- | --- |
| Alchemy | `questlog:elixirum_mastery`, level 1 | `overlord_reign:tower/alchemy_room_operational` |
| Theurgy | `theurgy:has_liquefaction_cauldron` | `overlord_reign:tower/theurgy_room_operational` |
| Gluttony | `farmers_spell:alchemist_pot` | `overlord_reign:tower/gluttony_room_operational` |
| Spell study / making | `irons_spellbooks:irons_spellbooks/make_inscription_table` | `overlord_reign:tower/spell_study_operational` |
| Eidolon | `eidolon:worktable` | `overlord_reign:tower/eidolon_room_operational` |

These objectives are historical or persistent owner-state checks, so a player who legitimately progressed before the Tower branch becomes visible is not forced to repeat the accomplishment.

Biomancy is intentionally not present in this list. It remains a major dedicated magic arc but has no Tower room under the current Tower authority.

## Formal completion

`campaign/tower/restoration_complete.json` closes the overarching Tower-restoration questline once all required core and selected magical facilities above are operational.

It records:

`overlord_reign:tower/restoration_complete`

The completion quest does not demand later system mastery, room upgrades, arbitrary wealth, complete equipment collections, optional court personnel, trophies, or architectural completionism. Quaver's band and other optional Tower content remain outside this gate.

## Validation

`tools/validate_tower_restoration_contract.py` guards this boundary and `.github/workflows/tower-restoration-contract.yml` runs it in CI.

The validator verifies the exact room list, Waystones Gates implementation, owner-state milestones, narrative markers, index inclusion, the existing Hot Iron forge milestone, formal completion prerequisites, and absence of an unauthorized Tower Biomancy room.
