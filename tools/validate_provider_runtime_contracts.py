#!/usr/bin/env python3
"""Validate provider runtime/reset invariants that are easy to regress."""
from pathlib import Path
import sys
import validate_kobold_campaign_contract as kobold_campaign
import validate_narrative_fact_runtime_contracts as narrative_facts
import validate_presentation_contracts as presentation

ROOT = Path(__file__).resolve().parents[1]
COMMANDS = ROOT / "common/src/main/java/org/infernalstudios/questlog/commands/QuestlogCommands.java"
FORWARDER = ROOT / "forge/src/main/java/org/infernalstudios/questlog/QuestlogForgeEventForwarder.java"
NETWORKING = ROOT / "forge/src/main/java/org/infernalstudios/questlog/networking/QuestlogPacketsForge.java"
OPEN_PACKET = ROOT / "common/src/main/java/org/infernalstudios/questlog/network/packet/QuestProviderOpenPacket.java"
ACTION_PACKET = ROOT / "common/src/main/java/org/infernalstudios/questlog/network/packet/QuestProviderActionPacket.java"
INTERACTION = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderInteraction.java"
SERVICE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderService.java"
RULE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderRule.java"
BINDING = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestProviderBinding.java"
QUEST = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/Quest.java"
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
require(NETWORKING, 'PROTOCOL_VERSION = "overlord-quests-3"', "provider completed-state network protocol bump")
require(NETWORKING, ".clientAcceptedVersions(PROTOCOL_VERSION::equals)", "exact client protocol compatibility")
require(NETWORKING, ".serverAcceptedVersions(PROTOCOL_VERSION::equals)", "exact server protocol compatibility")
require(OPEN_PACKET, "MAX_ENTRIES = 256", "bounded provider-menu entry count")
require(OPEN_PACKET, "MAX_PROVIDER_NAME = 128", "bounded provider display name")
require(OPEN_PACKET, "InteractionState.fromWireId", "explicit provider state packet decoding")
require(OPEN_PACKET, "buf.writeByte(entry.state().wireId())", "explicit provider state packet encoding")
require(ACTION_PACKET, "QuestProviderInteraction.sendMenu(player, provider, true)", "authoritative menu refresh after provider action")
require(ACTION_PACKET, "!packet.providerId.equals(provider.getUUID())", "provider action UUID identity check")
require(ACTION_PACKET, "!QuestProviderService.isWithinInteractionRange(player, provider)", "server-side provider action distance rejection")
require(INTERACTION, "!QuestProviderService.isWithinInteractionRange(player, provider)", "server-side provider-menu distance guard")
require(INTERACTION, "List.copyOf(entries.subList(0, QuestProviderOpenPacket.MAX_ENTRIES))", "server-side provider snapshot truncation")
require(SERVICE, "AVAILABLE(0),\n        IN_PROGRESS(1),\n        READY_TO_TURN_IN(2),\n        FAILED(3),\n        COMPLETED(4)", "stable provider interaction wire ids")
require(SERVICE, "public static InteractionState fromWireId", "provider interaction wire-id decoder")
require(SERVICE, ".comparingInt((InteractionEntry entry) -> questSortOrder(manager, entry.questId()))", "deterministic provider interaction ordering")
require(SERVICE, ".thenComparing(entry -> entry.questId().toString())", "provider interaction id tie-breaker")
require(SERVICE, "for (ResourceLocation fact : rule.requiredFacts())", "required narrative fact provider gating")
require(SERVICE, "for (ResourceLocation fact : rule.forbiddenFacts())", "forbidden narrative fact provider gating")
require(SERVICE, "narrative.hasFact(fact)", "world narrative fact eligibility lookup")
require(SERVICE, "for (Map.Entry<ResourceLocation, Set<ResourceLocation>> requirement : rule.requiredDispositions().entrySet())", "required disposition provider gating")
require(SERVICE, "narrative.getDisposition(requirement.getKey())", "world narrative disposition eligibility lookup")
require(SERVICE, "binding.matches(provider)", "durable provider identity matching")
require(SERVICE, "case SAME_PROVIDER -> binding.matches(provider);", "same-provider turn-in enforcement")
require(SERVICE, "case ANY_ELIGIBLE -> rule.matchesEntity(provider);", "any-eligible turn-in enforcement")
require(SERVICE, "player.distanceToSqr(provider) <= MAX_INTERACTION_DISTANCE_SQR", "shared provider interaction distance contract")
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
require(RULE, 'entityId.toString().equals("ribbits:ribbit")', "optional Ribbits native-role entity boundary")
require(RULE, 'Class.forName("com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity")', "optional Ribbits class-link isolation")
require(RULE, 'getMethod("getRibbitData")', "Ribbits profession data accessor bridge")
require(RULE, 'getMethod("getProfession")', "Ribbits native profession accessor bridge")
require(RULE, 'getMethod("getId")', "Ribbits native profession ID accessor bridge")
require(BINDING, 'tag.putUUID("provider_id", this.providerId);', "provider UUID persistence write")
require(BINDING, 'tag.getUUID("provider_id")', "provider UUID persistence read")
require(BINDING, "this.providerId.equals(entity.getUUID())", "provider UUID match semantics")
require(QUEST, 'tag.put("provider_binding", this.providerBinding.save());', "provider binding persistence write")
require(QUEST, 'this.providerBinding = QuestProviderBinding.load(data.getCompound("provider_binding"));', "provider binding persistence read")
require(QUEST, 'tag.putBoolean("provider_turned_in", this.providerTurnedIn);', "provider turn-in persistence write")
require(QUEST, 'this.providerTurnedIn = this.providerBinding != null && data.getBoolean("provider_turned_in");', "provider turn-in persistence read")
require(QUEST, "this.providerBinding = null;", "provider reset clears durable binding")
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
require(SCREEN, "minecraft.player.distanceToSqr(provider) > MAX_DISTANCE_SQR", "client provider screen distance closure")
require(SCREEN, "QuestlogWideButton", "shared Questlog provider button treatment")
require(SCREEN, "detailBackgroundLeft.blit", "shared Questlog provider parchment treatment")
require(PROVIDER_FIXTURE, '"completed": "[DEV]', "development completed provider dialogue fixture")

screen = SCREEN.read_text(encoding="utf-8")
if "if (y > maxY) return;" in screen:
    errors.append("QuestProviderScreen.java: silent fixed-height provider dialogue truncation remains")

errors.extend(kobold_campaign.collect_errors())
errors.extend(narrative_facts.collect_errors())
errors.extend(presentation.collect_errors())

if errors:
    print(f"Provider/narrative/presentation runtime contract validation failed with {len(errors)} error(s):", file=sys.stderr)
    for error in errors:
        print(f"  * {error}", file=sys.stderr)
    raise SystemExit(1)

print("OVERLORD provider, narrative-fact and presentation runtime contracts: PASS")
