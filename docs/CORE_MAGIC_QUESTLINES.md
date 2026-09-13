# Core Magic Questline Contract

Status: PRODUCTION QUEST CONTENT / AUTHORITY-DRIVEN

The six dedicated OVERLORD REIGN magic arcs are implemented as authored wrappers around durable native progression rather than replacements for the owning mods.

## Domain ownership

- Iron's Spells 'n Spellbooks: active mana spellcasting and spell expression.
- Farmer's Spell: Gluttony magic and magical cuisine; Farmer's Delight remains mundane cuisine.
- Theurgy: matter, minerals, transmutation, replication, and occult material law. Theurgic Sulfur is not a personal soul.
- Ars Elixirum: potion effects, extracts, effect composition, and magical pharmacology.
- Biomancy: flesh, mutation, bio-alchemy, and biological engineering; in REIGN it is Gnarl's recent Silence-developed Evil-Mana discipline inspired especially by magical overexposure and Solarius.
- Eidolon: rites, souls, ritual necromancy, occult binding, and transformation. It does not absorb Iron's Blood spellcraft and does not define one universal afterlife.

## Mechanical ownership

Questlog observes native persistent evidence and records only sparse REIGN semantic state.

Iron's milestones: spell-book equipment, Scroll Forge, Arcane Anvil, legendary ink.

Gluttony milestones: Amethyst Beetroot, Arcane Cocoa, Food Shaman, Chef Ratatouille.

Theurgy milestones: basic/amethyst rods, T2/T3 rods, T4/precious rods.

Ars Elixirum milestones: native persisted mastery levels 5, 20, and 50. These are authored quest boundaries, not a claim that native mastery ends at 50; the upstream mastery implementation supports progression far beyond that point.

Biomancy milestones: Primordial Core/Living Flesh, Bio-Forge/Decomposer/Digester, Bio-Lab/Bio-Injector/Cradle.

Eidolon milestones: wooden/stone altars, research notes/Soul Shard, then either the native sacred or wicked ritual path. REIGN treats those path labels as native ritual progression rather than a universal moral law.

## Tower boundary

The first five systems have selected Tower rooms whose initial activation remains part of Tower Restoration. Their three-quest dedicated arcs begin from those room milestones and cover deeper progression.

Biomancy has no Tower room. Its dedicated arc branches directly from `the_reign_takes_shape` and is not folded into Tower Restoration.

## Sequence-break behavior

Advancement-backed objectives are retrospective. Ars Elixirum uses its persisted per-player AlchemyProfile. Legitimate prior progression therefore satisfies later-opened REIGN quests without forcing replay.

## Final narrative markers

- `overlord_reign:magic/irons/spellcraft_established`
- `overlord_reign:magic/gluttony/mastery_established`
- `overlord_reign:magic/theurgy/mastery_established`
- `overlord_reign:magic/alchemy/pharmacology_established`
- `overlord_reign:magic/biomancy/discipline_established`
- `overlord_reign:magic/eidolon/ritual_path_established`

These facts state only that the authored REIGN arc reached its selected mature boundary. They do not mean the owning mod has been exhausted or fully completed.
