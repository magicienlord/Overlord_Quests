# Major Native Progression Audit

Status: TECHNICAL SOURCE AUDIT / READ-ONLY LORE SUPPORT

Date: 2026-09-13

## Purpose

This audit records durable progression surfaces for the mandatory Class A and Class B integrations identified by the read-only `Overlord_Lore_and_Canon/reference/32_REIGN_QUESTLINE_COVERAGE_LEDGER.md`.

It does not create new story facts, Tower rooms, dialogue, political outcomes, geography, or quest ordering. It answers a narrower implementation question: which exact installed systems can OVERLORD QUESTS observe without replacing their native progression?

The integration rule is:

1. prefer the owning mod's durable advancement, profile, statistic, or public state;
2. use Questlog to frame, gate, remember REIGN interpretation, and react to sequence breaks;
3. do not make the player repeat a native accomplishment merely because a REIGN quest activated late;
4. do not convert recipe-unlock data, inventory possession, GUI state, or marketing language into historical progression without proof.

## Installed artifact matrix

| System | Exact installed artifact | SHA-256 | Durable native surface |
| --- | --- | --- | --- |
| Twilight Forest | `twilightforest-1.20.1-4.3.2508-universal.jar` | `0bdc89263616d1b35c32ef82c5e9c14cbd20368e2fe8b468c72a28320be7a778` | rich advancement progression |
| Bumblezone | `the_bumblezone-7.13.4+1.20.1-forge.jar` | `04cff7be7d9dfbf775af1b6ef5818d1d296913a9705ba3fd38c71947c88050e1` | rich advancement progression |
| L_Ender's Cataclysm | `L_Enders_Cataclysm-3.31.jar` | `c29dcf940168208517b72f2d9965828b106a6b94cbea55d5001558c69a27b362` | boss/structure advancements |
| The Graveyard | `The_Graveyard_3.1_(FORGE)_for_1.20.1.jar` | `9a53d019eef20bbb70fef89ff7d41f1d175e1c29faf9c6380b8ed0b18832f27c` | boss/structure/ritual advancements |
| Knight Quest | `knightquest-forge-1.20.1-1.9.6.jar` | `244947907c9257bc416b766a5a755c5eb18099aeb9901bcb4c2e48e78df80c89` | one non-recipe root milestone; no rich quest-state surface found |
| Iron's Spells 'n Spellbooks | `irons_spellbooks-1.20.1-3.16.3.jar` | `54b5aaa52887c38f570fbd820288171234d8541c17a5e953f9271189fdd480fb` | staged advancements |
| Farmer's Spell | `farmers_spell-1.0.5-1.20.1-all.jar` | `0576f3df39ab6e928beca64acd817e5ee6d97674b913b1a8131d7b69dd9040cd` | authored advancements |
| Theurgy | `theurgy-1.20.1-1.30.0-OverlordReign-A4R2.jar` | `ff42cb0c12b19191d9923af7f136d820fce2ac41d4d2cd0d4c85e4a17d050755` | native advancements plus exact-item craft statistics |
| Ars Elixirum | `elixirum-forge-1.20.1-0.12.0-OverlordReign-B4.jar` | `3d793ab53248f42be0e6cd23fb81aa3a49dbcac8eb63bc95cfa1ca04032a8c8d` | persistent per-player AlchemyProfile/mastery state |
| Biomancy | `biomancy-forge-1.20.1-2.9.0.1-alpha.0.jar` | `2ac4465fe05f4c577d38b193a6eebd8ebc36722663f00013a8cd61ef4bfa827c` | staged advancements |
| Eidolon: Repraised | `eidolon_repraised-1.20.1-0.3.13.jar` | `a8cff6bf65b6fd9ebf74bfd4b685623d3abde6ec541506dae106bc3d5ba7527c` | staged and branching advancements |
| Cataclysm Spellbooks addon | `cataclysm_spellbooks-1.2.9-1.20.1-Overlord.jar` | `643bbb0f793c33ec24ad9b2968a9c09e6c4b8531db3c64a7d62698fcef5d1dff` | no standalone advancement surface; support integration only |

## Class B adventure systems

### Twilight Forest

The exact artifact contains 321 advancement JSON files, of which 42 are non-recipe progression/exploration advancements.

The native progression chain includes the durable milestones:

```text
twilightforest:progress_naga
twilightforest:progress_lich
twilightforest:progress_labyrinth
twilightforest:progress_hydra
twilightforest:progress_knights
twilightforest:progress_ur_ghast
twilightforest:progress_yeti
twilightforest:progress_glacier
twilightforest:progress_troll
twilightforest:progress_merge
twilightforest:progress_trophy_pedestal
twilightforest:progression_end
```

Questlog's existing `questlog:advancement` objective can observe these retrospectively. OVERLORD QUESTS should not replace the Twilight Forest's own gating sequence with duplicate kill counters.

Classification: **DIRECT NATIVE ADVANCEMENT INTEGRATION AVAILABLE**.

### Bumblezone

The exact artifact contains 135 non-recipe advancement files. The surface covers dimension entry and exploration, bee protection/wrath, Beehemoth/taming, Queen interaction, essence infusion and essence branches, Royal Jelly, Crystalline Flower progression, pollen systems, music discs, and other native discoveries.

This is a deep native progression system, not a single boss checkbox. Production integration should select only lore-relevant milestones from the native chain and leave the rest under Bumblezone ownership.

Classification: **DIRECT NATIVE ADVANCEMENT INTEGRATION AVAILABLE; MILESTONE SELECTION STILL REQUIRES AUTHORING**.

### L_Ender's Cataclysm

The exact artifact contains 21 non-recipe advancements. Strong durable boss/structure milestones include:

```text
cataclysm:find_acropolis
cataclysm:find_ancient_factory
cataclysm:find_burning_arena
cataclysm:find_cursed_pyramid
cataclysm:find_frosted_prison
cataclysm:find_ruined_citadel
cataclysm:find_soul_black_smith
cataclysm:find_sunken_city
cataclysm:kill_clawdian
cataclysm:kill_ender_golem
cataclysm:kill_ender_guardian
cataclysm:kill_harbinger
cataclysm:kill_ignis
cataclysm:kill_leviathan
cataclysm:kill_maledictus
cataclysm:kill_monstrosity
cataclysm:kill_remnant
cataclysm:kill_revenant
cataclysm:kill_scylla
cataclysm:kill_all_bosses
```

Questlog should observe the native advancement appropriate to the authored REIGN target instead of duplicating boss death state.

Classification: **DIRECT NATIVE ADVANCEMENT INTEGRATION AVAILABLE**.

### The Graveyard

The exact artifact contains 30 non-recipe advancements. Relevant durable milestones include:

```text
graveyard:graveyard/summon_lich
graveyard:graveyard/lich_prison
graveyard:graveyard/kill_wraith
graveyard:graveyard/large_graveyard
graveyard:graveyard/medium_graveyard
graveyard:graveyard/desert_graveyard
graveyard:graveyard/crypt
graveyard:graveyard/haunted_house
graveyard:graveyard/craft_coffin
graveyard:graveyard/corruption
```

These are technically straightforward to observe, but narrative interpretation must preserve REIGN's established distinction between death, souls, undeath, resurrection, and related occult disciplines. A Graveyard advancement name is not permission to collapse those lore concepts.

Classification: **DIRECT NATIVE ADVANCEMENT INTEGRATION AVAILABLE; LORE INTERPRETATION REQUIRED**.

### Knight Quest

The exact installed 1.9.6 artifact contains 180 advancement files, but 179 are recipe unlocks. The only non-recipe advancement is:

```text
knightquest:knightquest
```

Its criterion is inventory possession of:

```text
knightlib:small_essence
```

A resource/class audit found no separate quest, mission, contract, task, or player-progression framework in the installed JAR. The numerous Knight Quest entities, bosses, loot, armor, weapons, and recipes are real gameplay, but the installed build does not expose the rich native quest-state surface implied by the coverage ledger wording.

Therefore the ledger phrase that Knight Quest "already advertises a native questline" is treated as a planning description that does not match the exact installed 1.9.6 progression surface. OVERLORD QUESTS must not invent a nonexistent native quest API.

Classification: **INSTALLED-PACK DISCREPANCY / DEEPER CONTENT AUTHORING REQUIRED**. The root essence advancement may be observed where relevant. Later boss/entity progression may use exact durable kill-history or native item/craft evidence only after a concrete handoff is authored.

## Core magic systems

### Iron's Spells 'n Spellbooks

The exact artifact contains 30 non-recipe advancements. Verified progression milestones include the native root, inscription table, arcane anvil, scroll forge, spell-book equipment and material tiers, ink tiers, Catacombs entry, school/special books, and several staff milestones.

Representative IDs include:

```text
irons_spellbooks:irons_spellbooks/root
irons_spellbooks:irons_spellbooks/make_inscription_table
irons_spellbooks:irons_spellbooks/make_arcane_anvil
irons_spellbooks:irons_spellbooks/make_scroll_forge
irons_spellbooks:irons_spellbooks/spell_book_equip
irons_spellbooks:irons_spellbooks/spell_book_iron
irons_spellbooks:irons_spellbooks/spell_book_gold
irons_spellbooks:irons_spellbooks/spell_book_diamond
irons_spellbooks:irons_spellbooks/spell_book_netherite
irons_spellbooks:irons_spellbooks/ink_root
irons_spellbooks:irons_spellbooks/ink_legendary
irons_spellbooks:irons_spellbooks/enter_catacombs
```

Classification: **DIRECT NATIVE ADVANCEMENT INTEGRATION AVAILABLE; CLASS A REIGN FRAMING REQUIRED**.

### Farmer's Spell

The exact artifact contains 19 non-recipe advancements, including:

```text
farmers_spell:root
farmers_spell:alchemist_pot
farmers_spell:amethyst_beetroot
farmers_spell:amethyst_sugar
farmers_spell:arcane_cocoa
farmers_spell:blaze_scroll
farmers_spell:blood_tofu
farmers_spell:butter
farmers_spell:butter_hit
farmers_spell:catacombs_wine
farmers_spell:chef_ratatouille
farmers_spell:drink_butterbeer
farmers_spell:energized_caramel
farmers_spell:food_shaman
farmers_spell:icebreaker_bread
farmers_spell:icy_egg
farmers_spell:ink_beer
farmers_spell:paofu
farmers_spell:phantom_loot
```

The ledger gives Farmer's Spell an explicit Gluttony-magic role. The native milestones are technically sufficient for retrospective observation, but the central/optional split remains an authoring decision.

Classification: **DIRECT NATIVE ADVANCEMENT INTEGRATION AVAILABLE; CLASS A REIGN FRAMING REQUIRED**.

### Theurgy

The installed A4R2 artifact has already received a dedicated audit in `THEURGY_NATIVE_PROGRESSION_AUDIT.md`.

Native durable advancement milestones are:

```text
theurgy:book_root
theurgy:has_basic_rod
theurgy:has_amethyst_rod
theurgy:has_t2_rod
theurgy:has_t3_rod
theurgy:has_t4_rod
theurgy:has_rare_rod
theurgy:has_precious_rod
theurgy:has_liquefaction_cauldron
```

Later apparatus can be observed with persistent exact-item craft statistics when crafting that apparatus is itself the correct milestone.

Classification: **DIRECT ADVANCEMENT/CRAFT-STAT INTEGRATION VERIFIED; CLASS A REIGN FRAMING REQUIRED**.

### Ars Elixirum

The exact installed `0.12.0-OverlordReign-B4` artifact contains no advancement JSON files.

It does, however, expose a durable owner-state progression system:

```text
ServerAlchemy.get(MinecraftServer)
ServerAlchemy.profileOf(Player)
AlchemyProfile.mastery()
AlchemyMastery.level()
```

`ServerAlchemyProfile.load()` and `save()` persist a packed profile through Archivist's `CodexArchive`, keyed by the player's UUID. `_AlchemyMastery` persists:

- mastery level;
- mastery XP;
- per-recipe XP.

The profile also carries known effects, known recipes, known ingredients, and recipe collection data.

This is stronger than a synthetic Questlog counter. OVERLORD QUESTS now provides the optional objective:

```json
{
  "type": "questlog:elixirum_mastery",
  "required_amount": 5
}
```

`required_amount` is the minimum native mastery level. The objective observes Elixirum reflectively, once per second, and has no hard Elixirum compile/runtime dependency. If Elixirum is absent or the expected API is unavailable, it remains unsatisfied rather than fabricating progress.

Classification: **DIRECT PERSISTENT OWNER-STATE INTEGRATION IMPLEMENTED; CLASS A REIGN FRAMING REQUIRED**.

### Biomancy

The exact artifact contains 795 advancement files, of which 26 are non-recipe progression milestones. Relevant native milestones include:

```text
biomancy:biomancy/root
biomancy:biomancy/living_flesh
biomancy:biomancy/craft_primal_core
biomancy:biomancy/bio_forge
biomancy:biomancy/decomposer
biomancy:biomancy/digester
biomancy:biomancy/bio_lab
biomancy:biomancy/bio_injector
biomancy:biomancy/cradle
biomancy:biomancy/primal_orifice
biomancy:biomancy/primal_vision
biomancy:biomancy/malignant_growth
```

REIGN lore explicitly makes Biomancy a Gnarl/Silence discipline, so production treatment must be substantially authored rather than a generic mod tutorial. Technically, the native advancement chain already supplies the durable accomplishment layer.

Classification: **DIRECT NATIVE ADVANCEMENT INTEGRATION AVAILABLE; HIGH-PRIORITY CLASS A AUTHORING REQUIRED**.

### Eidolon: Repraised

The exact artifact contains 79 advancement files, of which 27 are non-recipe milestones. Relevant native progression includes:

```text
eidolon:root
eidolon:worktable
eidolon:pewter_ingot
eidolon:wooden_altar
eidolon:stone_altar
eidolon:crucible
eidolon:brazier
eidolon:research_notes
eidolon:soul_shard
eidolon:sacrifice
eidolon:villager_sacrifice
eidolon:enthrall_undead
eidolon:sacred_path
eidolon:wicked_path
eidolon:holy_symbol
eidolon:unholy_symbol
eidolon:lay_on_hands
eidolon:smite_undead
eidolon:reaper_scythe
eidolon:zombify
eidolon:cure_zombie
```

These milestones are technically easy to observe but semantically broad. REIGN must preserve its own distinctions among souls, undeath, rites, occult binding, transformation, and resurrection rather than treating every Eidolon advancement as the same form of necromancy.

Classification: **DIRECT NATIVE ADVANCEMENT INTEGRATION AVAILABLE; CLASS A BRANCH/LORE AUTHORING REQUIRED**.

### Cataclysm Spellbooks addon

The exact OVERLORD artifact exposes no standalone advancement chain. It should be treated as supporting content connecting Cataclysm rewards/themes into the Iron's spell ecosystem rather than as an independent questline requiring duplicate progression.

Classification: **SUPPORTING INTEGRATION, NOT A STANDALONE ARC**.

## Production implementation order

Technical readiness now supports the following order without replacing native systems:

1. author the Class A magic framing against read-only lore, using native advancements/mastery as objectives;
2. add Class B adventure wrappers that observe the owning mod's durable milestones and remain sequence-break safe;
3. resolve Knight Quest's installed-version discrepancy before promising a native questline handoff;
4. integrate End/Ender Dragon separately because it is a central REIGN endgame arc rather than merely another external-mod progression chain;
5. review Class C candidates only after the mandatory coverage above exists.

The audit deliberately does not choose quest dialogue, branch outcomes, Tower-room assignments, or exact campaign ordering where the lore authority has not already decided them.
