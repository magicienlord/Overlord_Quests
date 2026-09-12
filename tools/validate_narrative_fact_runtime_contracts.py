#!/usr/bin/env python3
"""Validate OVERLORD REIGN explicit narrative-fact runtime boundaries."""
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
STATE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/narrative/OverlordNarrativeState.java"
OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/narrative/FactObjective.java"
REWARD = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/narrative/SetFactReward.java"
OBJECTIVE_REGISTRY = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/QuestObjectiveRegistry.java"
REWARD_REGISTRY = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/QuestRewardRegistry.java"
COMMANDS = ROOT / "common/src/main/java/org/infernalstudios/questlog/commands/OverlordNarrativeCommands.java"
SERVICE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderService.java"

errors: list[str] = []


def require(path: Path, fragment: str, label: str) -> None:
    text = path.read_text(encoding="utf-8")
    if fragment not in text:
        errors.append(f"{path.relative_to(ROOT)}: missing {label}")


require(STATE, "private final Set<ResourceLocation> facts", "world-scoped fact set")
require(STATE, "public boolean setFact(ResourceLocation fact)", "monotonic fact setter")
require(STATE, "public boolean hasFact(ResourceLocation fact)", "fact lookup")
require(STATE, "public boolean clearFact(ResourceLocation fact)", "administrative fact reset")
require(STATE, 'root.contains("facts", Tag.TAG_LIST)', "backward-compatible fact load")
require(STATE, 'root.put("facts", factsTag)', "fact persistence")
require(STATE, ".sorted()", "deterministic fact serialization")

require(OBJECTIVE, 'JsonUtils.getString(definition, "fact")', "fact objective field parsing")
require(OBJECTIVE, "this.getRequiredAmount() != 1", "unit fact objective contract")
require(OBJECTIVE, "OverlordNarrativeState.get(player.server).hasFact(this.fact)", "server-authoritative fact objective")
require(OBJECTIVE, 'tag.putInt("units", this.serverSatisfied() ? 1 : 0)', "fact objective sync projection")

require(REWARD, 'JsonUtils.getString(definition, "fact")', "set_fact reward field parsing")
require(REWARD, "OverlordNarrativeState.get(player.server).setFact(this.fact)", "monotonic set_fact reward")
require(REWARD, "ServerPlayerManager.INSTANCE.syncAllQuestState()", "fact-change quest resync")
if "clearFact(" in REWARD.read_text(encoding="utf-8"):
    errors.append("SetFactReward.java: production reward must not erase historical narrative facts")

require(OBJECTIVE_REGISTRY, 'new ResourceLocation("questlog", "fact")', "fact objective registration")
require(REWARD_REGISTRY, 'new ResourceLocation("questlog", "set_fact")', "set_fact reward registration")

require(COMMANDS, 'Commands.literal("fact")', "narrative fact admin command branch")
require(COMMANDS, "narrative.setFact(fact)", "admin fact set operation")
require(COMMANDS, "narrative.clearFact(fact)", "admin fact clear operation")

require(SERVICE, "rule.requiredFacts()", "required fact provider gate")
require(SERVICE, "rule.forbiddenFacts()", "forbidden fact provider gate")

if errors:
    print(f"Narrative fact runtime contract validation failed with {len(errors)} error(s):", file=sys.stderr)
    for error in errors:
        print(f"  * {error}", file=sys.stderr)
    raise SystemExit(1)

print("OVERLORD narrative fact runtime contracts: PASS")
