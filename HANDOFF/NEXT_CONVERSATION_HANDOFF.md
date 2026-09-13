# NEXT CONVERSATION HANDOFF — Overlord_Quests

## Authoritative restart point

Preserved source: `magicienlord/Overlord_Quests@18dd600ddbde3a12b5776d1c9998c5799c7f138d`
Implementation branch: `gnarl-bootstrap`
Observed lore authority: `magicienlord/Overlord_Lore_and_Canon@235845c4985b61cba9c106b2f4c4af8894bb98ae`

This package follows a conversation rollback. Before doing any implementation work, re-fetch both live branches. Newer repository state always overrides this snapshot.

## Current implementation boundary at the preserved source

### Tower and opening
The opening/Tower framework is in production. Tower Restoration includes throne, forge, Minion infrastructure, storage, armory, treasury, the Waystones-backed Gates Room, selected magic facilities, and a formal restoration completion quest. The Gates Room is a Tower-restoration use of Waystones infrastructure; it is not a standalone Waystones questline and is not Minion Gate machinery.

### Minions
Brown/Red/Green/Blue are the canonical Minion types. The current quest implementation contains Brown bootstrap/recovery framing and Red → Green → Blue recovery/unlock progression. Brown remains the bootstrap/staff case rather than an API unlock step.

The Tower cook's proper name is **Gristle**. “Minion Cook” is his role.

### Core magic
Authored production questlines exist for:
- Iron's Spells 'n Spellbooks
- Farmer's Spell / Gluttony
- Theurgy
- Ars Elixirum
- Biomancy
- Eidolon

Biomancy is not a Tower room. Initial Tower facility activation and deeper discipline progression remain distinct concerns.

### Adventure wrappers
Authored production wrappers exist for:
- Twilight Forest
- L_Ender's Cataclysm
- The Graveyard
- The Bumblezone
- Knight Quest
- Lost Castle

Lost Castle is already a finite three-step expedition in production (`a_castle_off_the_map` → `break_the_lost_court` → `nothing_left_to_rule`) and has its own CI contract. Do not recreate it.

### Civilization coverage
Implemented production entries currently include:
- Goblins: first contact + merchant + engineer + tavern sidequests.
- Gnumus: first contact + merchant sidequest.
- Sea Dwellers: first contact + aquamarine barter. Ocean Dragon is explicitly excluded from REIGN content.
- Umvuthana: first audience + `suns_blessing`; destructive Umvuthi handling remains separate.
- Dwarves: first contact.
- Ribbits: first contact.
- Kobolds: first contact.
- Illagers: hostile `break_the_bastille` opening with a marked `takesapillage:legioner` authority target.

There is no Villager/Human or Piglin production quest in the current manifest at this checkpoint.

## Remaining coverage to reconcile — not started by this handoff

The following are remaining/uncovered assignments visible from the current production manifest and authority ledgers. Re-verify against current lore and repository state before implementing them:
- Rats: full progression including Ratlantis/boss content.
- Church of Sin: compact full sinister-location questline.
- Oddities: compact Orchid Shrine → Orchid Altar → Queen of Orchid arc.
- Immersive Melodies / Quaver: small character-driven Tower personnel arc; not formal Tower Restoration.
- central End campaign completion/refinement.
- Villager/Human civilization main entry; principal settlement/provider is unresolved.
- Piglin civilization entry around a designated marked Piglin Brute Chieftain; the hostility-to-audience transition remains unresolved and must not be invented as generic pacification.
- Illager post-authority fearful/cowed provider phase; surviving provider identity remains unresolved.
- Dwarf/Ribbit/Kobold post-contact work only where mechanics and authored justification support a defensible signal.
- conditional Pet Cemetery/pet-resurrection content after pet death.
- conditional Overlord Depths/Fathoms content only when the backport exists and final mechanics can be inspected.
- conditional Overlord NightWalker/Nycto vampire transition only when the backport exists and final mechanics can be inspected. Before Lestat authoring, re-fetch and read lore authority documents 40, 44, and 45.
- sparse popup/absorbed-content treatment and final coverage reconciliation against authority documents 32, 34, 36, 37, 38, and 39.

This list is a restart aid, not permission to override newer live repository work.

## Architecture and canon guardrails

- Minecraft Java 1.20.1, Forge 47.4.10.
- Do not reintroduce the removed `reputation!` mod or a numerical global reputation system.
- Civilization state is authored persistent branch/world state around designated anchors/providers; native species behavior elsewhere must remain untouched.
- Canonical civilization roster is exactly: Villagers/Humans, Illagers, Dwarves, Gnumus, Goblins, Kobolds, Ribbits, Sea Dwellers, Piglins, Umvuthana.
- Main-story markers should remain sparse and deterministic; sidequests may gate on main/civilization markers.
- The Dark Tower shell is purpose-built rather than a ruin; restoration means provisioning, installation, activation, and reconnection.
- Waystones has no standalone questline. Its Gates Room use is the specific Tower-restoration substrate exception.
- No Ocean Dragon REIGN content.
- Keep quest narrative ownership separate from native mod progression where native progression already exists.

## Status-document caveat

`docs/CURRENT_IMPLEMENTATION_STATUS.md` is stale/incomplete at this exact source SHA. Use the production `definitions/index.json`, focused docs/contracts, exact Git history, and current CI as implementation truth. Do not “fix” that file based on this handoff without first re-reading the live branch.

## Resume sequence

1. Re-fetch live `gnarl-bootstrap` and lore `main`.
2. If either has advanced, reconcile against live state and disregard conflicting snapshot claims here.
3. Confirm exact-head CI before editing.
4. Read the applicable lore authority files for the next assignment.
5. Inspect native mod/backport mechanics before choosing quest objectives.
6. Make one coherent implementation checkpoint at a time and validate the exact resulting SHA.

No gameplay, narrative, configuration, resource, or lore implementation was started as part of this handoff packaging.
