# OVERLORD REIGN Narrative Facts

Status: TECHNICAL IMPLEMENTATION OF APPROVED QUEST ARCHITECTURE

## Authority

`magicienlord/Overlord_Lore_and_Canon` defines Main Quest markers as runtime representations of real narrative facts, requires persistent quest facts or completion state where later content needs them, and rejects a hidden numeric reputation or morality system.

This document is the implementation-facing registry for durable production fact IDs used by the bundled OVERLORD QUESTS campaign. It does not create setting canon independently of the read-only lore authority or the bundled quest that actually writes a fact.

## Model

A narrative fact is a world-scoped `ResourceLocation` stored by `OverlordNarrativeState`.

Normal production behavior is monotonic:

- absent means the authored historical or campaign statement has not been recorded;
- present means it has become true;
- `questlog:set_fact` may add it;
- production content does not erase it.

Administrative `clear` exists only for testing/recovery.

A fact is not a civilization disposition. Facts preserve history; disposition represents one current exclusive political state and may later be replaced by another authored state.

A fact is also not automatically the owner of a cross-mod capability. Where another mod owns durable gameplay state, Questlog may observe that owner state directly and use a separate fact only for a distinct historical statement.

## Quest definition surface

### Fact objective

```json
{
  "type": "questlog:fact",
  "fact": "overlord_reign:some_authored_fact",
  "required_amount": 1
}
```

`required_amount` is exactly `1`. On the logical server, the objective reads world narrative state directly.

### Set-fact reward

```json
{
  "type": "questlog:set_fact",
  "fact": "overlord_reign:some_authored_fact",
  "auto_claim": true
}
```

Hidden state transitions should normally use `auto_claim: true` so dependent objectives and provider gates can react immediately.

### Provider gates

```json
"provider": {
  "required_facts": ["overlord_reign:required_fact"],
  "forbidden_facts": ["overlord_reign:forbidden_fact"]
}
```

All required facts must exist and all forbidden facts must be absent before acceptance. These gates do not silently fail an already accepted quest.

## Persistence and administration

Facts are stored in the world-scoped `overlord_quests_narrative` `SavedData`. IDs are save-history keys: once shipped, renaming one is a save migration, not a cosmetic refactor.

Administrative/test commands are:

```text
/questlog narrative fact get <fact>
/questlog narrative fact set <fact>
/questlog narrative fact clear <fact>
```

## Authoring rules

Use a fact only when later content materially benefits from a reusable historical or campaign truth. Do not create facts for generic numerical progress, hidden reputation, every trivial objective, or a capability already represented completely by another owner's state.

Local civilization facts remain local to the designated authored polity. They do not silently rewrite every procedural settlement or species member.

Production facts referenced by bundled quests must appear in this registry. `tools/validate_narrative_fact_documentation.py` enforces that boundary.

---

# Production fact registry

## Opening, Minions and early reign

### `overlord_reign:minions/brown_recovered`

Writer: `campaign/opening/browns_return`.

Meaning: the campaign has confirmed the Brown tribe's return under the current Overlord.

Boundary: this is historical campaign confirmation, not an independent replacement for Minion-system owner state or the Master's Staff bootstrap.

### `overlord_reign:minions/red_recovered`

Writer: `campaign/expansion/reds_return`.

Meaning: after the Red recovery quest and owner-state confirmation, Gnarl/the campaign has recorded the Red tribe as recovered.

Boundary: `Overlord_Minions` remains authoritative for whether Red command is actually unlocked. This fact remembers the corresponding campaign event.

### `overlord_reign:minions/green_recovered`

Writer: `campaign/expansion/greens_return`.

Meaning: after the Green recovery quest and owner-state confirmation, the campaign has recorded the Green tribe as recovered.

Boundary: it does not replace Green Minion owner state and does not claim a fabricated Hive implementation.

### `overlord_reign:minions/blue_recovered`

Writer: `campaign/expansion/blues_return`.

Meaning: after the Blue recovery quest and owner-state confirmation, the campaign has recorded the Blue tribe as recovered.

Boundary: it does not replace Blue Minion owner state and does not claim a fabricated Hive implementation.

### `overlord_reign:reign/initial_foundation_established`

Writer: `campaign/expansion/the_reign_takes_shape`.

Meaning: Brown recovery and the first practical Tower infrastructure milestone have converged sufficiently for the semi-open campaign structure to begin.

Boundary: this does not imply recovery of Red/Green/Blue Minions, completion of all Tower facilities, any civilization outcome, or any optional magic/adventure progression.

---

## Dark Tower and personnel

### `overlord_reign:tower/throne_room_operational`

Writer: `campaign/tower/claim_the_throne`.

Meaning: the current Overlord has reclaimed the Tower's throne-room function as the seat of the reign.

Boundary: it does not mean the rest of the Tower is restored.

### `overlord_reign:tower/minion_infrastructure_operational`

Writer: `campaign/tower/wake_minion_infrastructure`.

Meaning: the first practical Minion-support infrastructure of the Tower is operational.

Boundary: it does not imply all Minion tribes are recovered or all Tower infrastructure is complete.

### `overlord_reign:tower/forge_prepared`

Writer: `campaign/tower/prepare_the_forge`.

Meaning: the Overlord has acquired the core smithing equipment/material progression needed to treat the Tower forge as prepared for its campaign role.

Boundary: it does not fix architectural coordinates, exhaust Hot Iron progression, or imply every future forge upgrade is complete.

### `overlord_reign:tower/storage_room_operational`

Writer: `campaign/tower/provision_storage_room`.

Meaning: the Tower's authored storage-room function has reached its production campaign milestone.

Boundary: it does not require or imply completion of every storage mod or every possible storage upgrade.

### `overlord_reign:tower/armory_operational`

Writer: `campaign/tower/establish_armory`.

Meaning: the Tower armory has reached its authored operational milestone.

Boundary: it does not imply acquisition of every weapon, armor set, combat mod, or relic in the pack.

### `overlord_reign:tower/treasury_operational`

Writer: `campaign/tower/secure_treasury`.

Meaning: the Tower treasury has reached its authored operational milestone.

Boundary: it does not establish a global economy, complete every wealth objective, or fix a final room coordinate in canon.

### `overlord_reign:tower/gates_operational`

Writer: `campaign/tower/open_gates_room`.

Meaning: the Tower gates-room function has reached its authored operational milestone.

Boundary: it does not imply every dimension, portal system, or optional travel mod has been completed.

### `overlord_reign:tower/alchemy_room_operational`

Writer: `campaign/tower/magic/open_alchemy_laboratory`.

Meaning: an authored Tower laboratory is available for the Ars Elixirum / alchemical campaign context.

Boundary: room readiness is distinct from mastery of Ars Elixirum itself.

### `overlord_reign:tower/theurgy_room_operational`

Writer: `campaign/tower/magic/establish_theurgy_laboratory`.

Meaning: an authored Tower work area for Theurgy has reached its operational milestone.

Boundary: it does not itself establish Theurgy mastery.

### `overlord_reign:tower/gluttony_room_operational`

Writer: `campaign/tower/magic/open_gluttony_kitchen`.

Meaning: the Tower kitchen/Gluttony work area has reached its authored operational milestone.

Boundary: it does not itself establish Farmer's Spell / Gluttony mastery.

### `overlord_reign:tower/spell_study_operational`

Writer: `campaign/tower/magic/establish_spell_study`.

Meaning: the Tower has an operational authored spell-study context for active spellcraft.

Boundary: it does not itself complete Iron's Spells 'n Spellbooks progression.

### `overlord_reign:tower/eidolon_room_operational`

Writer: `campaign/tower/magic/prepare_eidolon_chamber`.

Meaning: the Tower has an authored chamber prepared for Eidolon ritual study.

Boundary: room readiness is distinct from establishing the ritual discipline.

### `overlord_reign:tower/restoration_complete`

Writer: `campaign/tower/restoration_complete`.

Meaning: the production Tower-restoration campaign has reached its intended capstone across the facilities assigned to that arc.

Boundary: this does not mean every possible room, mod integration, optional system, civilization arc, Minion tribe, or campaign branch is complete.

### `overlord_reign:tower/quaver_band_established`

Writer: `campaign/personnel/quaver/first_tower_performance`.

Meaning: Quaver's optional Tower ensemble has been assembled far enough to give its first authored Tower performance.

Boundary: this does not make every Immersive Melodies instrument mandatory or turn the band into a central-campaign requirement.

---

## Magic disciplines

### `overlord_reign:magic/irons/spellcraft_established`

Writer: `campaign/magic/irons/master_the_ink`.

Meaning: the player has carried Iron's own progression through the selected legendary-ink milestone, allowing REIGN to treat active mana spellcraft as an established discipline.

Boundary: Questlog observes native Iron's progression; it does not replace or claim exhaustion of that mod's deeper progression.

### `overlord_reign:magic/gluttony/mastery_established`

Writer: `campaign/magic/gluttony/banquet_of_power`.

Meaning: the selected Farmer's Spell / Gluttony progression has reached the authored REIGN mastery milestone.

Boundary: it does not imply completion of every Farmer's Delight-family recipe or food system.

### `overlord_reign:magic/theurgy/mastery_established`

Writer: `campaign/magic/theurgy/precious_matter`.

Meaning: the selected native Theurgy progression has reached the authored mastery threshold used by REIGN.

Boundary: it does not replace Theurgy's own progression ownership or require every optional Theurgy path.

### `overlord_reign:magic/alchemy/pharmacology_established`

Writer: `campaign/magic/alchemy/pharmacologist`.

Meaning: Ars Elixirum pharmacological/alchemical practice has reached the selected durable mastery milestone for the reign.

Boundary: the native Ars Elixirum profile remains the owner of its mastery data; this fact records the campaign-level conclusion.

### `overlord_reign:magic/biomancy/discipline_established`

Writer: `campaign/magic/biomancy/the_living_laboratory`.

Meaning: the selected Biomancy progression has matured enough for REIGN to treat Biomancy as an established discipline.

Boundary: it does not replace Biomancy's systems or imply every Biomancy mechanic has been exhausted.

### `overlord_reign:magic/eidolon/ritual_path_established`

Writer: `campaign/magic/eidolon/choose_a_rite`.

Meaning: the player has completed the authored Eidolon ritual-study path far enough for that discipline to be established in the reign.

Boundary: it does not assert completion of every Eidolon ritual, item, or optional progression route.

---

## Adventure campaign wrappers

These facts record completion of REIGN's dedicated wrapper around the corresponding installed-mod progression. They do not claim that Questlog owns the native progression, that every optional collectible was exhausted, or that procedural instances elsewhere inherit the same history.

### `overlord_reign:adventure/twilight_forest_progression_completed`

Writer: `campaign/adventures/twilight/forest_without_barriers`.

Meaning: the Twilight Forest's implemented native progression chain has been carried through the selected native end state without inventing an unsupported finale.

### `overlord_reign:adventure/cataclysm_capstone_completed`

Writer: `campaign/adventures/cataclysm/cataclysm_conquered`.

Meaning: the dedicated Cataclysm conquest wrapper has reached its authored capstone after the selected native great-enemy progression.

### `overlord_reign:adventure/graveyard_expedition_completed`

Writer: `campaign/adventures/graveyard/not_one_death`.

Meaning: the dedicated Graveyard expedition wrapper has reached its authored conclusion.

### `overlord_reign:adventure/bumblezone_essence_reached`

Writer: `campaign/adventures/bumblezone/essence_of_the_hive`.

Meaning: the dedicated Bumblezone expedition has reached the selected native Essence milestone used as its REIGN capstone.

### `overlord_reign:adventure/knight_quest_completed`

Writer: `campaign/adventures/knight/the_knight_beyond_the_chalice`.

Meaning: the dedicated Knight Quest wrapper has reached its authored conclusion through the selected native quest progression.

### `overlord_reign:adventure/lost_castle_expedition_completed`

Writer: `campaign/adventures/lost_castle/nothing_left_to_rule`.

Meaning: the dedicated Lost Castle expedition has reached its authored conclusion after the selected native castle progression.

### `overlord_reign:adventure/ratlantis_campaign_completed`

Writer: `campaign/adventures/rats/break_the_ratlantean_powers`.

Meaning: the dedicated Rats / Ratlantis wrapper has reached its authored campaign conclusion.

### `overlord_reign:adventure/church_of_sin_expedition_completed`

Writer: `campaign/adventures/church_of_sin/break_the_dead_congregation`.

Meaning: the dedicated Church of Sin / Cursed Cathedral expedition has reached its authored conclusion.

Boundary: current kill objectives are not structure-location-bound after discovery; this fact records quest completion, not proof that every qualifying kill physically occurred inside the cathedral.

### `overlord_reign:adventure/oddities_orchid_queen_defeated`

Writer: `campaign/adventures/oddities/cut_down_the_queen`.

Meaning: the dedicated Oddities / Orchid Shrine arc has recorded the selected Orchid Queen defeat capstone.

---

## Personal and conditional sidequests

### `overlord_reign:personal/pet_resurrection_completed`

Writer: `campaign/sidequests/pet_cemetery/return_from_the_grave`.

Meaning: after the player's own supported tame has died and the native Pet Cemetery resurrection path has been completed, REIGN records that personal resurrection event.

Boundary: it does not imply immortality for all pets, resurrection of an arbitrary entity, or replacement of Pet Cemetery's own mechanics.

### `overlord_reign:personal/nightwalker/lestat_joined_tower`

Writer: `campaign/sidequests/nightwalker/lestat_arrives`.

Meaning: after the player is confirmed as a vampire through the supplied NightWalker/Nycto system, the authored REIGN-native Lestat anchor has entered the Tower-side personal arc and made contact with the current Overlord.

Boundary: Lestat is not transported from another continuity, and this fact does not import modern Earth, television continuity, or a second vampire cosmology into REIGN.

### `overlord_reign:personal/nightwalker/transition_guided`

Writer: `campaign/sidequests/nightwalker/choose_the_price`.

Meaning: the Lestat-led transition sidequest has reached its authored guidance capstone after blood-consumption practice, Vampire Altar interaction, and at least one real Nycto power purchase.

Boundary: this fact records Lestat's campaign guidance. Nycto remains authoritative for whether the player is currently a vampire and which powers are actually purchased; curing or changing native vampire state does not erase the historical fact that the guided transition occurred.

---

## Civilization facts

Civilization facts below are scoped to the deliberately designated local polity/anchor used by the campaign. They do not establish species-wide obedience, one universal state, or global AI changes.

### `overlord_reign:civilizations/villagers/contact_established`

Writer: `campaign/civilizations/villagers/first_contact`.

Meaning: formal contact has been established with the deliberately authored biome-appropriate historical human remnant/successor settlement represented by the exact marked local Villager.

Boundary: no Villager disposition is written; no universal human kingdom, capital, monarch, profession, or exact settlement coordinate is implied.

### `overlord_reign:civilizations/goblins/contact_established`

Writer: `campaign/civilizations/goblins/first_contact`.

Meaning: the designated principal Goblin Camp has formally entered current campaign history.

Boundary: disposition remains separate and unrelated procedural Goblins are not automatically included.

### `overlord_reign:civilizations/gnumus/contact_established`

Writer: `campaign/civilizations/gnumus/first_contact`.

Meaning: formal contact has been established with the designated principal Gnumu settlement through its authored local Elder Shaman.

Boundary: disposition remains separate, unrelated settlements remain independent, and this contact does not reveal the hidden Halfling ancestry to Gnumus.

### `overlord_reign:civilizations/ribbits/contact_established`

Writer: `campaign/civilizations/ribbits/first_contact`.

Meaning: formal contact has been established with the designated principal Ribbit Village through the authored local Gardener Elder.

Boundary: disposition and unrelated Ribbit Villages remain separate.

### `overlord_reign:civilizations/kobolds/contact_established`

Writer: `campaign/civilizations/kobolds/first_contact`.

Meaning: formal contact has been established with the designated principal Kobold Den through its exact marked Captain.

Boundary: Pirate Kobolds, unrelated Dens and ordinary Captains are not folded into the same polity or disposition.

### `overlord_reign:civilizations/sea_dwellers/contact_established`

Writer: `campaign/civilizations/sea_dwellers/first_contact`.

Meaning: formal contact has been established with the designated principal Sea Village through its authored Sea Elder within the installed Mermorph family.

Boundary: disposition remains separate, unrelated Sea Villages remain independent, and no removed/nonexistent Ocean Dragon progression is implied.

### `overlord_reign:civilizations/dwarves/contact_established`

Writer: `campaign/civilizations/dwarves/first_contact`.

Meaning: formal contact has been established with the designated Golden Hills successor hold through the exact local Dwarven Forger carrying the authored Forge-Thane role.

Boundary: this does not restore the old Golden Hills kingdom, create a universal Dwarven ruler, or settle disposition.

### `overlord_reign:civilizations/umvuthana/contact_established`

Writer: `campaign/civilizations/umvuthana/first_contact`.

Meaning: the current Overlord has completed the legitimate mask-gated first audience with the designated canonical Umvuthi.

Boundary: the same quest separately writes the designated Grove's disposition to `overlord_reign:neutral`; unrelated Umvuthana/Umvuthis and native destructive behavior remain outside that local result.

### `overlord_reign:civilizations/illagers/authority_established`

Writer: `campaign/civilizations/illagers/break_the_bastille`.

Meaning: the current Overlord has personally broken the designated Bastille's local command by defeating the exact marked `takesapillage:legioner` commander.

Boundary: the commander role is an authored local layer, not a native universal Legioner class; this fact alone does not write disposition, globally pacify Illagers, identify every Bastille with the designated polity, or imply the later cowed audience has occurred.

### `overlord_reign:civilizations/illagers/bastille_cowed`

Writer: `campaign/civilizations/illagers/the_bastille_bows`.

Meaning: after local authority was established, the exact protected Bastille intermediary has completed the fearful/cowed audience and acknowledged restraint under the Overlord's demonstrated force.

Boundary: the same quest writes the designated Bastille polity to `overlord_reign:neutral`, but the fact means historical fear/restraint rather than friendship, alliance, species-wide surrender, or permanent pacification. A later explicit non-peaceful disposition may restore native hostility while this historical fact remains true.

### `overlord_reign:civilizations/piglins/contact_established`

Writer: `campaign/civilizations/piglins/first_contact`.

Meaning: formal first audience has been completed with the exact protected Piglin Brute Chieftain of the designated authored Nether Village.

Boundary: gold armor is an access/restraint condition for that local audience, not a political outcome. The contact writes no disposition and does not change ordinary Piglins or Piglin Brutes elsewhere.

---

## Central End / dimensional Wasteland

### `overlord_reign:campaign/dimensional_wasteland_reached`

Writer: `campaign/end/the_wound_beyond_the_world`.

Meaning: the player has entered `minecraft:the_end`, which the production campaign uses as the dimensional Wasteland left by the old catastrophe around the Tower.

Boundary: this fact records entry only; it does not mean the Ender Dragon is defeated or the central campaign is complete.

### `overlord_reign:campaign/ending_armed`

Writer: `campaign/end/the_wound_beyond_the_world`.

Meaning: after the dimensional Wasteland has been reached, the production ending presentation is eligible to resolve when its final mechanical trigger occurs.

Boundary: this is a campaign/presentation readiness marker. It does not require all civilizations, all Tower restoration, all optional content, or every parallel arc.

### `overlord_reign:campaign/central_campaign_completed`

Writer: `campaign/end/break_the_dragon`.

Meaning: the Ender Dragon has been defeated through the production central-ending sequence and the central OVERLORD REIGN campaign has reached its mechanical conclusion.

Boundary: the same world remains playable; unresolved civilizations, optional arcs, Tower work and sidequests do not become retroactively completed.

---

## Cross-mod capability ownership

Narrative facts must not silently replace state owned by another mod.

### Overlord Minions

`Overlord_Minions` owns actual Minion command/unlock state. Questlog's `questlog:minion_unlocked` objective and `questlog:unlock_minion` reward read/write that owner system.

The four `overlord_reign:minions/*_recovered` facts in this registry are deliberately different: they remember that the authored recovery/confirmation event occurred. They are not the capability source of truth and must not be used to fabricate an unlocked Minion slot when the owner system disagrees.

Red, Green and Blue recovery currently has a documented fidelity limitation in the physical proof objectives. Their historical facts do not convert practical item proxies into claims that the source-game Hives were recreated exactly.

### NightWalker / Nycto

Nycto owns current vampire state and purchased power state. Questlog observes the supplied alpha.3 persistent player surface and records only the separate Lestat-sidequest history facts above.

### Native adventure/magic mods

Where a quest observes a native advancement, profile, statistic or owner state, that native mod remains authoritative for its mechanics. The REIGN fact records the campaign-level conclusion reached after the selected source-backed milestone.

---

## Development fixtures

`examples/questlog/quests/overlord_narrative_fact_dev.json` and `overlord_provider_fact_dev.json` use synthetic `questlog:dev_*` IDs to validate the engine surface. They are not production story content and are intentionally excluded from the production registry check.
