# OVERLORD REIGN System Reactions

Status: IMPLEMENTATION

Authority: `Overlord_Lore_and_Canon/reference/36_REIGN_MOD_QUESTLINE_ASSIGNMENTS_FINAL.md` at the pinned read-only lore checkpoint used by the implementation pass.

## Purpose

Some installed systems are assigned a one-time acknowledgement or sparse milestone popups, explicitly not a formal questline. Questlog therefore exposes a separate server-authoritative system-reaction channel instead of creating hidden or disposable quests.

Presentation state is intentionally separate from narrative facts. Seeing a toast records only that the player has already seen that acknowledgement in the current world.

## Implemented reactions

- Enchanting System Overhaul: first main-hand interaction with the real vanilla enchanting table while `enchanting_system_overhaul` is loaded.
- LevelUP: first detected base-stat investment while `levelup` is loaded. The bridge reads LevelUP's `PLAYER_STATS` capability reflectively and acknowledges only once a base stat is above zero.
- RPG Skill Trees: first actual skill unlock in a Pufferfish Skills category whose namespace is `rpg_skill_trees`. The Pufferfish event API is registered reflectively so it remains optional.
- Spice of Life: Carrot Edition: milestone acknowledgements at exactly 10, 25, 50, 75, and 100 distinct foods, read from SolCarrot's own `FoodList` after its food-finish event has committed.
- Legendary Farming: first successful player harvest of a registered `legendary_farming:mega_*_block`. The event is ignored if the block break is cancelled.
- Crop Critters: first detection of a tamed `cropcritters` entity within 32 blocks whose native owner UUID is the player. The bounded once-per-second scan is used because Crop Critters commits taming directly and does not emit Forge's optional `AnimalTameEvent` hook.
- Golem Overhaul: first main-hand interaction with a `golemoverhaul:*_golem` entity. This is an encounter acknowledgement only; it does not claim that the player constructed or owns that golem.
- BloomingNature: first main-hand interaction with the exact `bloomingnature:wandering_gardener`, preserving its role as supporting provider content rather than a new civilization.

## Source boundaries

The installed artifacts used to select these triggers are:

- `legendary_farming-1.20.1-1.7.0.jar`, mod id `legendary_farming`, whose registered mega crop blocks use the `mega_*_block` naming family;
- `cropcritters-forge-1.20.1-1.5.0.jar`, mod id `cropcritters`, whose crop critters extend `TamableAnimal` and commit native owner state through their custom tame path;
- `golemoverhaul-forge-1.20.1-1.1.1.jar`, mod id `golemoverhaul`, whose golem entity registry uses `*_golem` identifiers;
- `letsdo-bloomingnature-forge-1.0.12.jar`, mod id `bloomingnature`, whose exact trader entity is `bloomingnature:wandering_gardener`.

The reaction bridge deliberately observes those real mechanics and registry identities. It does not add substitute progression, fabricate achievements, or create new civilization membership.

## Technical constraints

All external integrations are optional. Questlog has no hard compile dependency on the content mods. Reflection is used only where a direct optional API is required, while ordinary Forge events, registry IDs and vanilla owner state are used for the event-driven acknowledgements above.

The reaction packet is server-to-client only and the server persists once-only presentation flags by player UUID.

These reactions are intentionally absent from the production quest manifest. They must not appear as chapters, sidequests, or narrative disposition facts unless the lore authority is revised.
