#!/usr/bin/env python3
"""Validate provider runtime/reset invariants that are easy to regress."""
from pathlib import Path
import sys

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
require(ACTION_PACKET, "QuestProviderInteraction.sendMenu(player, provider, true)", "authoritative menu refresh after provider action")
require(INTERACTION, "List.copyOf(entries.subList(0, QuestProviderOpenPacket.MAX_ENTRIES))", "server-side provider snapshot truncation")
require(SERVICE, ".comparingInt((InteractionEntry entry) -> questSortOrder(manager, entry.questId()))", "deterministic provider interaction ordering")
require(SERVICE, ".thenComparing(entry -> entry.questId().toString())", "provider interaction id tie-breaker")
require(RULE, "record LocationBounds", "authored provider location contract")
require(RULE, "this.location != null && !this.location.contains(entity)", "provider location eligibility check")
require(RULE, "Questlog.MODID + \":\" + value", "bare provider unlock quest normalization")
require(RULE, "QuestProviderDialogue.fromProviderDefinition(json)", "definition-owned provider dialogue attachment")
require(DIALOGUE, "case READY_TO_TURN_IN -> this.readyToTurnIn", "state-specific provider dialogue mapping")
require(SCREEN, "Component.literal(\"Decline\")", "explicit non-persistent decline action")
require(SCREEN, "rule.dialogue().linesFor(entry.state())", "authored dialogue rendering")
require(SCREEN, "this.selectedQuestId = entry.questId()", "offer selection before acceptance")
require(SCREEN, "private int dialogueScrollPixels;", "provider dialogue scroll state")
require(SCREEN, "private int maxDialogueScroll", "bounded provider dialogue overflow calculation")
require(SCREEN, "public boolean mouseScrolled", "mouse-wheel provider dialogue scrolling")
require(SCREEN, "Component.literal(\"Up\")", "explicit provider dialogue scroll-up control")
require(SCREEN, "Component.literal(\"Down\")", "explicit provider dialogue scroll-down control")

screen = SCREEN.read_text(encoding="utf-8")
if "if (y > maxY) return;" in screen:
    errors.append("QuestProviderScreen.java: silent fixed-height provider dialogue truncation remains")

if errors:
    print(f"Provider runtime contract validation failed with {len(errors)} error(s):", file=sys.stderr)
    for error in errors:
        print(f"  * {error}", file=sys.stderr)
    raise SystemExit(1)

print("OVERLORD provider runtime contracts: PASS")
