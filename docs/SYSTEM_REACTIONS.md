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

## Technical constraints

All four external integrations are optional. Questlog has no hard compile dependency on the content mods and fails closed if a reflective API is unavailable. The reaction packet is server-to-client only and the server persists once-only presentation flags by player UUID.

These reactions are intentionally absent from the production quest manifest. They must not appear as chapters, sidequests, or narrative disposition facts unless the lore authority is revised.
