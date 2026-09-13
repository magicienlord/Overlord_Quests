# Dark Tower Restoration Contract

Status: IMPLEMENTATION CONTRACT / PARTIALLY DEFERRED ONLY WHERE TECHNICAL ANCHORS DO NOT EXIST

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
| Treasury | retrospective craft evidence for secure entrance and dedicated container | `overlord_reign:tower/treasury_operational` |
| Storage Room | retrospective chest + barrel craft evidence | `overlord_reign:tower/storage_room_operational` |
| Armory | retrospective armor-stand + item-frame craft evidence | `overlord_reign:tower/armory_operational` |

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

## Gates: explicit technical deferment

The Tower Gates are required by the authoritative Tower design, but the currently inspected Minion implementation exposes no Gate / Netherworld transport API, event, advancement, block, or other quest-facing activation hook. `Overlord_Quests` likewise has no existing Gate integration.

The Gates milestone is therefore **explicitly deferred**, not omitted and not faked.

Current rules:

- do not set `overlord_reign:tower/gates_operational` from a read-only popup;
- do not substitute Waystones or ordinary Nether portals and call them Minion Gates;
- do not fabricate a Gate block or transport mechanic inside Questlog;
- when a real Gate implementation or narrow compatibility hook exists, add the operational milestone here and to the production definitions;
- do not mark formal Tower Restoration complete until that required Gate boundary is technically represented.

This is the least-invasive implementation consistent with the project's technical-truth rule.

## Completion boundary

No production quest currently declares the entire Dark Tower restoration complete. That omission is deliberate while the required Gates facility remains technically deferred.

Once the Gate hook exists, formal Tower Restoration completion may require the core operational facilities specified by the authority files. Optional personnel content such as Quaver's band and deeper magic mastery must remain outside that completion gate.

## Validation

`tools/validate_tower_restoration_contract.py` guards this boundary and `.github/workflows/tower-restoration-contract.yml` runs it in CI.

The validator verifies the exact room list, owner-state milestones, narrative markers, index inclusion, the existing Hot Iron forge milestone, absence of a fabricated Gate-completion marker, and absence of a Tower Biomancy room.
