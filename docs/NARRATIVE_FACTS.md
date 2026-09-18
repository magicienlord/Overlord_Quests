# OVERLORD REIGN Narrative Facts

Status: TECHNICAL IMPLEMENTATION OF APPROVED QUEST ARCHITECTURE

Reconciled against `V5_CAMPAIGN_SYSTEM_AUTHORITY.md`, 2026-09-18.

## Authority and model

`magicienlord/Overlord_Lore_and_Canon` is the read-only authority for the narrative architecture. This file is the implementation-facing registry for durable production fact IDs. It does not create setting canon independently.

Narrative facts are world-scoped `ResourceLocation` keys stored by `OverlordNarrativeState`. They are monotonic historical statements: absent means not recorded, present means it has become true. A fact is not a civilization disposition and is not the owner of cross-mod capability state.

Use a durable fact only where later content materially benefits from a reusable campaign truth. Generic progress, presentation state, and owner-state already represented by another mod do not need duplicate facts.

Validated by `tools/validate_narrative_fact_documentation.py`.

### Reconciliation note, 2026-09-18

Every writer in the previous version of this file named a quest from the **legacy quest graph**, which is being rebuilt. All writers below are V5 quest IDs from `V5_CAMPAIGN_SYSTEM_AUTHORITY.md` §8. Of the 51 previous facts, 45 remapped cleanly; the remaining six are under **Retired** or **Open**.

---

## Remapped facts

| Fact | V5 writer | Quest |
| --- | --- | --- |
| `overlord_reign:minions/brown_recovered` | `OR-OPN-001` | opening/a_new_master |
| `overlord_reign:minions/red_recovered` | `OR-MIN-001` | minions/restore_red |
| `overlord_reign:minions/green_recovered` | `OR-MIN-002` | minions/restore_green |
| `overlord_reign:minions/blue_recovered` | `OR-MIN-003` | minions/restore_blue |
| `overlord_reign:tower/throne_room_operational` | `OR-TWR-001` | tower/throne |
| `overlord_reign:tower/forge_prepared` | `OR-TWR-002` | tower/forge |
| `overlord_reign:tower/storage_room_operational` | `OR-TWR-003` | tower/storage |
| `overlord_reign:tower/armory_operational` | `OR-TWR-004` | tower/armory |
| `overlord_reign:tower/treasury_operational` | `OR-TWR-005` | tower/treasury |
| `overlord_reign:tower/gates_operational` | `OR-TWR-006` | tower/waygates |
| `overlord_reign:tower/alchemy_room_operational` | `OR-TWR-009` | tower/alchemy |
| `overlord_reign:tower/theurgy_room_operational` | `OR-TWR-010` | tower/theurgy |
| `overlord_reign:tower/gluttony_room_operational` | `OR-TWR-011` | tower/gluttony |
| `overlord_reign:tower/spell_study_operational` | `OR-TWR-012` | tower/spell_study |
| `overlord_reign:tower/eidolon_room_operational` | `OR-TWR-013` | tower/eidolon |
| `overlord_reign:tower/quaver_band_established` | `OR-ADV-QUAV-001` | adventures/quaver/ensemble |
| `overlord_reign:magic/irons/spellcraft_established` | `OR-MAG-IRO-004` | magic/irons/mastery_or |
| `overlord_reign:magic/gluttony/mastery_established` | `OR-MAG-GLU-003` | magic/gluttony/culmination |
| `overlord_reign:magic/theurgy/mastery_established` | `OR-MAG-THE-004` | magic/theurgy/advanced_transmutation |
| `overlord_reign:magic/alchemy/pharmacology_established` | `OR-MAG-ELI-003` | magic/elixirum/mastery |
| `overlord_reign:magic/biomancy/discipline_established` | `OR-MAG-BIO-005` | magic/biomancy/advanced_proof |
| `overlord_reign:magic/eidolon/ritual_path_established` | `OR-MAG-EID-004` | magic/eidolon/mastery_or |
| `overlord_reign:adventure/twilight_forest_progression_completed` | `OR-ADV-TWIL-005` | adventures/twilight/ch5 |
| `overlord_reign:adventure/cataclysm_capstone_completed` | `OR-CAT-009` | adventures/cataclysm/capstone |
| `overlord_reign:adventure/graveyard_expedition_completed` | `OR-ADV-GRAV-004` | adventures/graveyard/lich |
| `overlord_reign:adventure/bumblezone_essence_reached` | `OR-ADV-BUMB-003` | adventures/bumblezone/essence |
| `overlord_reign:adventure/knight_quest_completed` | `OR-ADV-KNIG-004` | adventures/knightquest/netherman |
| `overlord_reign:adventure/lost_castle_expedition_completed` | `OR-ADV-LOST-003` | adventures/lost_castle/treasure |
| `overlord_reign:adventure/ratlantis_campaign_completed` | `OR-ADV-RATS-005` | adventures/rats/closure |
| `overlord_reign:adventure/oddities_orchid_queen_defeated` | `OR-ADV-ORCH-003` | adventures/orchid/queen |
| `overlord_reign:personal/pet_resurrection_completed` | `OR-ADV-PET_-003` | adventures/pet_cemetery/cure |
| `overlord_reign:personal/nightwalker/lestat_joined_tower` | `OR-ADV-NIGH-001` | adventures/nightwalker/transform |
| `overlord_reign:personal/nightwalker/transition_guided` | `OR-ADV-NIGH-003` | adventures/nightwalker/altar |
| `overlord_reign:civilizations/villagers/contact_established` | `OR-CIV-001A` | civilizations/villagers/anchor |
| `overlord_reign:civilizations/goblins/contact_established` | `OR-CIV-005A` | civilizations/goblins/anchor |
| `overlord_reign:civilizations/gnumus/contact_established` | `OR-CIV-004A` | civilizations/gnumus/anchor |
| `overlord_reign:civilizations/ribbits/contact_established` | `OR-CIV-007A` | civilizations/ribbits/anchor |
| `overlord_reign:civilizations/kobolds/contact_established` | `OR-CIV-006A` | civilizations/kobolds/anchor |
| `overlord_reign:civilizations/sea_dwellers/contact_established` | `OR-CIV-008A` | civilizations/sea_dwellers/anchor |
| `overlord_reign:civilizations/dwarves/contact_established` | `OR-CIV-003A` | civilizations/dwarves/anchor |
| `overlord_reign:civilizations/umvuthana/contact_established` | `OR-CIV-010A` | civilizations/umvuthana/anchor |
| `overlord_reign:civilizations/piglins/contact_established` | `OR-CIV-009A` | civilizations/piglins/anchor |
| `overlord_reign:campaign/dimensional_wasteland_reached` | `OR-END-001` | end/expedition |
| `overlord_reign:campaign/ending_armed` | `OR-END-001` | end/expedition |
| `overlord_reign:campaign/central_campaign_completed` | `OR-END-002` | end/ender_dragon |

---

## New facts

| Fact | V5 writer | Meaning / boundary |
| --- | --- | --- |
| `overlord_reign:civilizations/villagers/resolved_neutral` | `OR-CIV-001B` | The designated villagers polity reached NEUTRAL. Scoped to the authored anchor; no species-wide state implied. |
| `overlord_reign:civilizations/villagers/resolved_subjugated` | `OR-CIV-001B` | The designated villagers polity reached SUBJUGATED. |
| `overlord_reign:civilizations/villagers/resolved_hostile` | `OR-CIV-001B` | The designated Villager polity reached HOSTILE. DESTROYED is a local settlement outcome, never a Villager civilization state (Q-026). |
| `overlord_reign:civilizations/illagers/resolved_neutral` | `OR-CIV-002B` | The designated illagers polity reached NEUTRAL. Scoped to the authored anchor; no species-wide state implied. |
| `overlord_reign:civilizations/illagers/resolved_subjugated` | `OR-CIV-002B` | The designated illagers polity reached SUBJUGATED. |
| `overlord_reign:civilizations/illagers/resolved_destroyed` | `OR-CIV-002B` | The designated illagers polity reached DESTROYED. |
| `overlord_reign:civilizations/dwarves/resolved_neutral` | `OR-CIV-003B` | The designated dwarves polity reached NEUTRAL. Scoped to the authored anchor; no species-wide state implied. |
| `overlord_reign:civilizations/dwarves/resolved_subjugated` | `OR-CIV-003B` | The designated dwarves polity reached SUBJUGATED. |
| `overlord_reign:civilizations/dwarves/resolved_destroyed` | `OR-CIV-003B` | The designated dwarves polity reached DESTROYED. |
| `overlord_reign:civilizations/gnumus/resolved_neutral` | `OR-CIV-004B` | The designated gnumus polity reached NEUTRAL. Scoped to the authored anchor; no species-wide state implied. |
| `overlord_reign:civilizations/gnumus/resolved_subjugated` | `OR-CIV-004B` | The designated gnumus polity reached SUBJUGATED. |
| `overlord_reign:civilizations/gnumus/resolved_destroyed` | `OR-CIV-004B` | The designated gnumus polity reached DESTROYED. |
| `overlord_reign:civilizations/goblins/resolved_neutral` | `OR-CIV-005B` | The designated goblins polity reached NEUTRAL. Scoped to the authored anchor; no species-wide state implied. |
| `overlord_reign:civilizations/goblins/resolved_subjugated` | `OR-CIV-005B` | The designated goblins polity reached SUBJUGATED. |
| `overlord_reign:civilizations/goblins/resolved_destroyed` | `OR-CIV-005B` | The designated goblins polity reached DESTROYED. |
| `overlord_reign:civilizations/kobolds/resolved_neutral` | `OR-CIV-006B` | The designated kobolds polity reached NEUTRAL. Scoped to the authored anchor; no species-wide state implied. |
| `overlord_reign:civilizations/kobolds/resolved_subjugated` | `OR-CIV-006B` | The designated kobolds polity reached SUBJUGATED. |
| `overlord_reign:civilizations/kobolds/resolved_destroyed` | `OR-CIV-006B` | The designated kobolds polity reached DESTROYED. |
| `overlord_reign:civilizations/ribbits/resolved_neutral` | `OR-CIV-007B` | The designated ribbits polity reached NEUTRAL. Scoped to the authored anchor; no species-wide state implied. |
| `overlord_reign:civilizations/ribbits/resolved_subjugated` | `OR-CIV-007B` | The designated ribbits polity reached SUBJUGATED. |
| `overlord_reign:civilizations/ribbits/resolved_destroyed` | `OR-CIV-007B` | The designated ribbits polity reached DESTROYED. |
| `overlord_reign:civilizations/sea_dwellers/resolved_neutral` | `OR-CIV-008B` | The designated sea_dwellers polity reached NEUTRAL. Scoped to the authored anchor; no species-wide state implied. |
| `overlord_reign:civilizations/sea_dwellers/resolved_subjugated` | `OR-CIV-008B` | The designated sea_dwellers polity reached SUBJUGATED. |
| `overlord_reign:civilizations/sea_dwellers/resolved_destroyed` | `OR-CIV-008B` | The designated sea_dwellers polity reached DESTROYED. |
| `overlord_reign:civilizations/piglins/resolved_neutral` | `OR-CIV-009B` | The designated piglins polity reached NEUTRAL. Scoped to the authored anchor; no species-wide state implied. |
| `overlord_reign:civilizations/piglins/resolved_subjugated` | `OR-CIV-009B` | The designated piglins polity reached SUBJUGATED. |
| `overlord_reign:civilizations/piglins/resolved_destroyed` | `OR-CIV-009B` | The designated piglins polity reached DESTROYED. |
| `overlord_reign:civilizations/umvuthana/resolved_neutral` | `OR-CIV-010B` | The designated umvuthana polity reached NEUTRAL. Scoped to the authored anchor; no species-wide state implied. |
| `overlord_reign:civilizations/umvuthana/resolved_subjugated` | `OR-CIV-010B` | The designated umvuthana polity reached SUBJUGATED. |
| `overlord_reign:civilizations/umvuthana/resolved_destroyed` | `OR-CIV-010B` | The designated umvuthana polity reached DESTROYED. |
| `overlord_reign:civilizations/myrmex/resolved_neutral` | `OR-CIV-011B` | The designated myrmex polity reached NEUTRAL. Scoped to the authored anchor; no species-wide state implied. |
| `overlord_reign:civilizations/myrmex/resolved_subjugated` | `OR-CIV-011B` | The designated myrmex polity reached SUBJUGATED. |
| `overlord_reign:civilizations/myrmex/resolved_destroyed` | `OR-CIV-011B` | The designated myrmex polity reached DESTROYED. |
| `overlord_reign:magic/irons/inclination_holy` | `OR-MAG-IRO-004` | First Iron's mastery inclination recorded as Holy (Q024). Mutually exclusive with Blood. |
| `overlord_reign:magic/irons/inclination_blood` | `OR-MAG-IRO-004` | First Iron's mastery inclination recorded as Blood (Q024). |
| `overlord_reign:magic/eidolon/path_sacred` | `OR-MAG-EID-004` | Eidolon mastery path recorded as Sacred (Q025). |
| `overlord_reign:magic/eidolon/path_wicked` | `OR-MAG-EID-004` | Eidolon mastery path recorded as Wicked (Q025). |
| `overlord_reign:rivalry/investigated` | `OR-RIV-001` | Paired rivalry evidence presented; the four resolution routes are exposed (Q172). |
| `overlord_reign:rivalry/favoured_dwarves` | `OR-RIV-002` | Resolved in the Dwarves' favour. Writes a named rivalry fact only; sets no disposition (Q173). |
| `overlord_reign:rivalry/favoured_kobolds` | `OR-RIV-003` | Resolved in the Kobolds' favour. Sets no disposition. |
| `overlord_reign:rivalry/forced_truce` | `OR-RIV-004` | A working arrangement was imposed on both polities. Sets no disposition. |
| `overlord_reign:rivalry/exploited` | `OR-RIV-005` | Left unresolved and turned to the Overlord's advantage. Sets no disposition. |
| `overlord_reign:nightwalker/first_blood_victim` | `OR-ADV-NIGH-001` | First blood taken from a living victim. Lestat reacts to this arm; later content may read it. |
| `overlord_reign:nightwalker/first_blood_bottled` | `OR-ADV-NIGH-001` | First blood taken from a Blood Bottle. Mutually exclusive with the victim arm. |
| `overlord_reign:ice_and_fire/dragon_bonded` | `OR-ICF-004` | A dragon was hatched, raised, bonded and flown. Ice and Fire remains authoritative for the dragon's state. |
| `overlord_reign:tower/arena_operational` | `OR-TWR-007` | The Tower Arena is installed and operational (Supplementaries Cage). Required by the silent readiness aggregate. |
| `overlord_reign:tower/jail_operational` | `OR-TWR-008` | The Tower Jail is installed and operational (Big Iron Grate). Required by the silent readiness aggregate. |
| `overlord_reign:tower/biomancy_room_operational` | `OR-TWR-014` | The Tower Biomancy chamber is installed and operational. Required by the silent readiness aggregate. Distinct from `magic/biomancy/discipline_established`, which records deeper mastery. |

Provider quests write a further 42 facts, one per quest, listed in `V5_CAMPAIGN_SYSTEM_AUTHORITY.md` §7.5.

---

## Retired

No V5 writer exists. Remove unless a later decision creates one.

| Fact | Why retired |
| --- | --- |
| `overlord_reign:reign/initial_foundation_established` | Legacy writer `campaign/expansion/the_reign_takes_shape` has no V5 equivalent. |
| `overlord_reign:tower/minion_infrastructure_operational` | V5 §3.4: Minion Infrastructure is already restored and receives no objective. |
| `overlord_reign:adventure/church_of_sin_expedition_completed` | Church of Sin receives no dedicated questline (Q230, Q248b). Ramblings only. |

---

## Aggregate facts

### `overlord_reign:tower/restoration_complete`

V5 §3.4 states there is **no visible Tower-complete quest or popup**; aggregate readiness is tracked silently. The fact remains useful as the silent gate that opens Bosses'Rise, but its writer cannot be a quest. It needs a non-quest writer: an aggregate check firing when all fourteen Tower Restoration quests are complete.

### Illager gateway — RESOLVED 2026-09-18

**Overlord decision:** a fourth Illager provider quest was added.

`OR-PRV-ILL-001` — **Bastille commander / gateway.** Overpower the designated Bastille by defeating its
authored commander encounter, then complete the fearful audience that follows. It writes both facts:

| Fact | Writer | Meaning / boundary |
| --- | --- | --- |
| `overlord_reign:civilizations/illagers/authority_established` | `OR-PRV-ILL-001` | The designated Bastille's local command was broken through its authored commander encounter. Writes no disposition, does not globally pacify Illagers, and does not mean the cowed audience has occurred. |
| `overlord_reign:civilizations/illagers/bastille_cowed` | `OR-PRV-ILL-001` | The designated local Illager polity completed the fearful audience. Records fear and restraint, not friendship or species-wide surrender. A later non-peaceful disposition may restore local native hostility while this historical fact remains true. |

`OR-PRV-ILL-002`, `OR-PRV-ILL-003` and `OR-PRV-ILL-004` — the NEUTRAL, SUBJUGATED and DESTROYED routes of
Q198, Q199 and Q200 — now require `bastille_cowed` as a prerequisite. The Illager arc therefore reads:
initially hostile, authority established by force, fearful phase, then the terminal route opens.
