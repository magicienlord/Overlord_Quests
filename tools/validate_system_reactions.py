#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def read(path: str) -> str:
    p = ROOT / path
    if not p.is_file():
        raise AssertionError(f"missing required file: {path}")
    return p.read_text(encoding="utf-8")


reactions = read("common/src/main/java/org/infernalstudios/questlog/overlord/reaction/OverlordSystemReactions.java")
bridge = read("forge/src/main/java/org/infernalstudios/questlog/overlord/reaction/OverlordSystemReactionBridgeForge.java")
packets = read("common/src/main/java/org/infernalstudios/questlog/network/QuestlogPackets.java")
forge_packets = read("forge/src/main/java/org/infernalstudios/questlog/networking/QuestlogPacketsForge.java")
forge_entry = read("forge/src/main/java/org/infernalstudios/questlog/QuestlogForge.java")
manifest = read("common/src/main/resources/assets/questlog/overlord/definitions/index.json")

for reaction_id in (
    "system/enchanting_system_overhaul",
    "system/levelup",
    "system/rpg_skill_trees",
    "system/spice_of_life_10",
    "system/spice_of_life_25",
    "system/spice_of_life_50",
    "system/spice_of_life_75",
    "system/spice_of_life_100",
):
    assert reaction_id in reactions, f"missing system reaction ID {reaction_id}"

assert '"enchanting_system_overhaul"' in bridge
assert "Blocks.ENCHANTING_TABLE" in bridge
assert '"levelup"' in bridge
assert "getStatsBaseArr" in bridge
assert '"puffish_skills"' in bridge
assert '"rpg_skill_trees"' in bridge
assert "registerSkillUnlockEvent" in bridge
assert "category.getNamespace()" in bridge
assert '"solcarrot"' in bridge
assert "getEatenFoodCount" in bridge
for threshold in (10, 25, 50, 75, 100):
    assert f"case {threshold} ->" in bridge, f"missing Spice of Life threshold {threshold}"

ending_position = packets.index('"ending_presented"')
reaction_position = packets.index('"system_reaction"')
assert reaction_position > ending_position, "system reaction packet must be append-only after ending packets"
assert 'PROTOCOL_VERSION = "overlord-quests-5"' in forge_packets
assert "OverlordSystemReactionBridgeForge.initialize()" in forge_entry
assert "MinecraftForge.EVENT_BUS.register(OverlordSystemReactionBridgeForge.class)" in forge_entry

for forbidden in (
    "system/enchanting_system_overhaul",
    "system/levelup",
    "system/rpg_skill_trees",
    "system/spice_of_life_10",
):
    assert forbidden not in manifest, f"popup-only system reaction leaked into quest manifest: {forbidden}"

print("PASS: sparse system-reaction contract is intact")
