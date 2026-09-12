#!/usr/bin/env python3
"""Validate provider runtime/reset invariants that are easy to regress."""
from pathlib import Path
import sys
import validate_narrative_fact_runtime_contracts as narrative_facts
import validate_presentation_contracts as presentation

ROOT = Path(__file__).resolve().parents[1]
COMMANDS = ROOT / "common/src/main/java/org/infernalstudios/questlog/commands/QuestlogCommands.java"
FORWARDER = ROOT / "forge/src/main/java/org/infernalstudios/questlog/QuestlogForgeEventForwarder.java"
OPEN_PACKET = ROOT / "common/src/main/java/org/infernalstudios/questlog/network/packet/QuestProviderOpenPacket.java"
ACTION_PACKET = ROOT / "common/src/main/java/org/infernalstudios/questlog/network/packet/QuestProviderActionPacket.java"
INTERACTION = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderInteraction.java"
SERVICE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderService.java"
RULE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderRule.java"
DIALOGUE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderDialogue.java"
SCREEN = ROOT / "common/src/main/java/org/infernalstudios/questlog/client/provider/QuestProviderScreen.java"
PROVIDER_FIXTURE = ROOT / "examples/questlog/quests/overlord_provider_dev.json"

errors: list[str] = []

def require(path: Path, fragment: str, label: str) -> None:
    text = path.read_text(encoding="utf-8")
    if fragment not in text:
        errors.append(f"{path.relative_to(ROOT)}: missing {label}")

commands = COMMANDS.read_text(encoding="utf-8")
if commands.count("quest.resetProgress();") < 3:
    errors.append("QuestlogCommands.java: all three reset paths must delegate to Quest.resetProgress()")
if "quest.hasSentTrigger = quest.prerequisites.isEmpty();" in commands:
    errors.append("QuestlogCommands.java: provider-unsafe direct hasSentTrigger reset remains")
require(
    COMMANDS,
    "quest.getProviderRule() != null && quest.getProviderBinding() == null",
    "provider-binding guard in /questlog trigger",
)
require(FORWARDER, "!player.isShiftKeyDown()", "sneak-only temporary provider interaction")
require(FORWARDER, "event.getHand() != InteractionHand.MAIN_HAND", "main-hand-only provider interaction")
require(FORWARDER, "OverlordNarrativeCommands.register(event.getDispatcher())", "narrative admin command registration")
require(OPEN_PACKET, "MAX_ENTRIES = 256", "bounded provider-menu entry count")
require(OPEN_PACKET, "MAX_PROVIDER_NAME = 128", "bounded provider display name")
require(OPEN_PACKET, "buf.writeByte(entry.state().ordinal())", "provider state ordinal packet encoding")
require(ACTION_PACKET, "QuestProviderInteraction.sendMenu(player, provider, true)", "authoritative menu refresh after provider action")
require(INTERACTION, "List.copyOf(entries.subList(0, QuestProviderOpenPacket.MAX_ENTRIES))", "server-side provider snapshot truncation")
require(SERVICE, "AVAILABLE,\n        IN_PROGRESS,\n        READY_TO_TURN_IN,\n        FAILED,\n        COMPLETED", "append-only completed provider interaction state")
require(SERVICE, ".comparingInt((InteractionEntry entry) -> questSortOrder(manager, entry.questId()))", "deterministic provider interaction ordering")
require(SERVICE, ".thenComparing(entry -> entry.questId().toString())", "provider interaction id tie-breaker")
require(SERVICE, "for (ResourceLocation fact : rule.requiredFacts())", "required narrative fact provider gating")
require(SERVICE, "for (ResourceLocation fact : rule.forbiddenFacts())", "forbidden narrative fact provider gating")
require(SERVICE, "narrative.hasFact(fact)", "world narrative fact eligibility lookup")
require(SERVICE, "binding.matches(provider)", "durable provider identity matching")
require(SERVICE, "rule.dialogue().hasLinesFor(InteractionState.COMPLETED)", "authored completion follow-up eligibility")
require(SERVICE, "new InteractionEntry(quest.getId(), InteractionState.COMPLETED)", "provider completion follow-up snapshot state")
require(RULE, "record LocationBounds", "authored provider location contract")
require(RULE, "this.location != null && !this.location.contains(entity)", "provider location eligibility check")
require(RULE, "Questlog.MODID + \":\" + value", "bare provider unlock quest normalization")
require(RULE, "QuestProviderDialogue.fromProviderDefinition(json)", "definition-owned provider dialogue attachment")
require(RULE, 'idSet(json, "required_facts")', "required narrative fact definition parsing")
require(RULE, 'idSet(json, "forbidden_facts")', "forbidden narrative fact definition parsing")
require(RULE, "contradictoryFacts.retainAll(forbiddenFacts)", "contradictory narrative fact gate rejection")
require(RULE, "entity.getTags().contains(\"overlord_role:\" + this.role)", "explicit scoreboard provider role compatibility")
require(RULE, "entity instanceof Villager villager", "native Villager profession provider role bridge")
require(RULE, "BuiltInRegistries.VILLAGER_PROFESSION.getKey", "registered Villager profession lookup")
require(RULE, "professionId.toString().equals(this.role)", "namespaced Villager profession role matching")
require(DIALOGUE, "case READY_TO_TURN_IN -> this.readyToTurnIn", "state-specific provider dialogue mapping")
require(DIALOGUE, "case COMPLETED -> this.completed", "completed provider dialogue mapping")
require(DIALOGUE, 'parseLines(dialogue, "completed")', "completed provider dialogue parsing")
require(SCREEN, "Component.literal(\"Decline\")", "explicit non-persistent decline action")
require(SCREEN, "rule.dialogue().linesFor(entry.state())", "authored dialogue rendering")
require(SCREEN, "this.selectedQuestId = entry.questId()", "offer selection before acceptance")
require(SCREEN, "private int dialogueScrollPixels;", "provider dialogue scroll state")
require(SCREEN, "private int maxDialogueScroll", "bounded provider dialogue overflow calculation")
require(SCREEN, "public boolean mouseScrolled", "mouse-wheel provider dialogue scrolling")
require(SCREEN, "Component.literal(\"Up\")", "explicit provider dialogue scroll-up control")
require(SCREEN, "Component.literal(\"Down\")", "explicit provider dialogue scroll-down control")
require(SCREEN, 'case COMPLETED -> Component.literal("Completed: ")', "completed provider list presentation")
require(SCREEN, "case IN_PROGRESS, FAILED, COMPLETED -> null", "completed provider follow-up is presentation-only")
require(SCREEN, "QuestlogWideButton", "shared Questlog provider button treatment")
require(SCREEN, "detailBackgroundLeft.blit", "shared Questlog provider parchment treatment")
require(PROVIDER_FIXTURE, '"completed": "[DEV]', "development completed provider dialogue fixture")

screen = SCREEN.read_text(encoding="utf-8")
if "if (y > maxY) return;" in screen:
    errors.append("QuestProviderScreen.java: silent fixed-height provider dialogue truncation remains")

errors.extend(narrative_facts.collect_errors())
errors.extend(presentation.collect_errors())

if errors:
    print(f"Provider/narrative/presentation runtime contract validation failed with {len(errors)} error(s):", file=sys.stderr)
    for error in errors:
        print(f"  * {error}", file=sys.stderr)
    raise SystemExit(1)

print("OVERLORD provider, narrative-fact and presentation runtime contracts: PASS")
