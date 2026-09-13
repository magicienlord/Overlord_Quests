#!/usr/bin/env python3
"""Static safety contracts for the OVERLORD REIGN ending activation boundary."""
from __future__ import annotations

from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
CONFIG = ROOT / "common/src/main/java/org/infernalstudios/questlog/config/QuestlogConfig.java"
ACCESSOR = ROOT / "common/src/main/java/org/infernalstudios/questlog/mixin/client/WinScreenAccessor.java"
MIXINS = ROOT / "common/src/main/resources/questlog.mixins.json"
ROUTER = ROOT / "common/src/main/java/org/infernalstudios/questlog/client/ending/OverlordEndingScreens.java"
SCREEN = ROOT / "common/src/main/java/org/infernalstudios/questlog/client/ending/OverlordEndingScreen.java"
ACTIVATION = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/ending/OverlordEndingActivation.java"
STATE = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/ending/OverlordEndingPresentationState.java"
STATE_PACKET = ROOT / "common/src/main/java/org/infernalstudios/questlog/network/packet/OverlordEndingStatePacket.java"
ACK_PACKET = ROOT / "common/src/main/java/org/infernalstudios/questlog/network/packet/OverlordEndingPresentedPacket.java"
PACKETS = ROOT / "common/src/main/java/org/infernalstudios/questlog/network/QuestlogPackets.java"
EVENTS = ROOT / "common/src/main/java/org/infernalstudios/questlog/QuestlogEvents.java"
SET_FACT = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/narrative/SetFactReward.java"
FORGE_EVENTS = ROOT / "forge/src/main/java/org/infernalstudios/questlog/QuestlogForgeEventForwarder.java"
FORGE_PACKETS = ROOT / "forge/src/main/java/org/infernalstudios/questlog/networking/QuestlogPacketsForge.java"
DEV_QUEST = ROOT / "examples/questlog/quests/overlord_ending_arm_dev.json"
PRODUCTION_QUESTS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions/quests"
PRODUCTION_INDEX = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions/index.json"
ENDING_ARM_FACT = "overlord_reign:campaign/ending_armed"
DOC = ROOT / "docs/OVERLORD_ENDING_SCREEN_INTEGRATION.md"
PROTOCOL = ROOT / "docs/ENDING_SCREEN_TEST_PROTOCOL.md"


def read(path: Path, errors: list[str]) -> str:
    try:
        return path.read_text(encoding="utf-8")
    except OSError as exc:
        errors.append(f"{path.relative_to(ROOT)}: {exc}")
        return ""


def require(condition: bool, message: str, errors: list[str]) -> None:
    if not condition:
        errors.append(message)


def validate_dormant_production_activation(errors: list[str]) -> None:
    """Fail closed until the hidden production ending gate is explicitly authored."""
    if not PRODUCTION_QUESTS.is_dir():
        errors.append(f"{PRODUCTION_QUESTS.relative_to(ROOT)}: production quest directory is missing")
        return

    for quest_path in sorted(PRODUCTION_QUESTS.rglob("*.json")):
        try:
            quest_text = quest_path.read_text(encoding="utf-8")
        except OSError as exc:
            errors.append(f"{quest_path.relative_to(ROOT)}: {exc}")
            continue
        if ENDING_ARM_FACT in quest_text:
            errors.append(
                f"{quest_path.relative_to(ROOT)}: production ending activation is still dormant; "
                f"remove {ENDING_ARM_FACT!r} until the authoritative final campaign gate is approved"
            )

    index_text = read(PRODUCTION_INDEX, errors)
    require(
        "overlord_ending_arm_dev" not in index_text,
        "bundled production index must never reference the development ending-arm fixture",
        errors,
    )


def main() -> int:
    errors: list[str] = []
    config = read(CONFIG, errors)
    accessor = read(ACCESSOR, errors)
    mixins = read(MIXINS, errors)
    router = read(ROUTER, errors)
    screen = read(SCREEN, errors)
    activation = read(ACTIVATION, errors)
    state = read(STATE, errors)
    state_packet = read(STATE_PACKET, errors)
    ack_packet = read(ACK_PACKET, errors)
    packets = read(PACKETS, errors)
    events = read(EVENTS, errors)
    set_fact = read(SET_FACT, errors)
    forge_events = read(FORGE_EVENTS, errors)
    forge_packets = read(FORGE_PACKETS, errors)
    dev_quest = read(DEV_QUEST, errors)
    doc = read(DOC, errors)
    protocol = read(PROTOCOL, errors)

    ending_section = config[config.find("public static class EndingScreen"):] if "public static class EndingScreen" in config else ""
    require("public static class EndingScreen" in config, "ending-screen configuration section is missing", errors)
    require("public boolean developmentPreview = false;" in ending_section, "ending development preview must default to false", errors)
    require("public int minimumDisplayTicks = 80;" in ending_section, "ending dwell-time default changed unexpectedly", errors)

    require('@Mixin(WinScreen.class)' in accessor, "WinScreen accessor must target vanilla WinScreen", errors)
    require('@Accessor("poem")' in accessor, "WinScreen accessor must expose the poem discriminator", errors)
    require('@Accessor("onFinished")' in accessor, "WinScreen accessor must expose the vanilla completion callback", errors)
    require('"client.WinScreenAccessor"' in mixins, "WinScreen accessor must be registered as a client mixin", errors)

    require('"campaign/ending_armed"' in activation, "ending activation must use the reserved server-authoritative arm fact", errors)
    require("OverlordNarrativeState.get(server).hasFact(ENDING_ARMED_FACT)" in activation, "ending arm state must be derived from narrative state", errors)
    require("fight.hasPreviouslyKilledDragon()" in activation, "ending activation must recognize persistent prior Dragon defeat", errors)
    require("OverlordEndingPresentationState.get(server)" in activation, "ending activation must use the persistent presentation latch", errors)
    require("acknowledgePresentation" in activation and "!hasDragonBeenDefeated(server)" in activation, "server acknowledgement must validate Dragon defeat", errors)
    require("new OverlordEndingStatePacket(armed, presented, requestDirect)" in activation, "server must project minimal ending state to the client", errors)

    require('DATA_NAME = "overlord_quests_ending"' in state, "ending presentation latch must have stable SavedData identity", errors)
    require('root.putBoolean("presented", this.presented)' in state, "ending presentation latch must persist completion", errors)
    require("this.setDirty();" in state, "ending presentation latch mutations must dirty SavedData", errors)

    require("SERVER_TO_CLIENT" in state_packet, "ending state packet must be server-to-client", errors)
    require("CLIENT_TO_SERVER" in ack_packet, "ending acknowledgement packet must be client-to-server", errors)
    require("ctx.getSender() instanceof ServerPlayer sender" in ack_packet, "ending acknowledgement must require an authenticated server player", errors)
    require("ending_state" in packets and "ending_presented" in packets, "ending packets must be appended to the packet registry", errors)
    require('PROTOCOL_VERSION = "overlord-quests-4"' in forge_packets, "Forge protocol must advance for the new packet contract", errors)

    require("OverlordEndingActivation.onPlayerLogin(player);" in events, "server login must synchronize ending state", errors)
    require("OverlordEndingActivation.onNarrativeFactChanged(player.server, this.fact);" in set_fact, "set_fact must synchronize the ending arm transition", errors)

    require("instanceof WinScreen winScreen" in router, "ending router must only consider vanilla WinScreen", errors)
    require("serverArmed && !serverPresented" in router, "ending replacement must require server-projected production state", errors)
    require("!config.enabled || (!config.developmentPreview && !productionArmed)" in router, "ending replacement must fail closed without preview or production arm", errors)
    require("!accessor.questlog$isPoem()" in router, "ending replacement must reject ordinary credits", errors)
    require("accessor.questlog$getOnFinished()" in router, "ending replacement must preserve vanilla completion callback", errors)
    require("directPresentationRequested" in router and "minecraft.screen != null" in router, "sequence-break delivery must defer until screenless gameplay", errors)
    require("Services.PLATFORM.sendPacketToServer(new OverlordEndingPresentedPacket())" in router, "client must acknowledge a completed production presentation", errors)

    require("if (this.finished)" in screen, "ending screen must guard its completion callback against duplicate invocation", errors)
    require("this.finished = true;" in screen, "ending screen must latch completion before invoking callback", errors)
    require("this.onFinished.run();" in screen, "ending screen must invoke its continuation callback", errors)
    require("ENDING PRESENTATION DEVELOPMENT SCAFFOLD" in screen, "visible content must remain marked as a development scaffold", errors)

    require("OverlordEndingScreens.replace(replacement)" in forge_events, "Forge screen-opening hook must route WinScreen through the ending boundary", errors)
    require("OverlordEndingScreens.tick();" in forge_events, "Forge client tick must service direct sequence-break presentation", errors)
    require("OverlordEndingScreens.resetClientState();" in forge_events, "client logout must clear connection-scoped ending state", errors)

    require(f'"{ENDING_ARM_FACT}"' in dev_quest, "development ending fixture must arm only the reserved fact", errors)
    require("[DEV]" in dev_quest, "ending arm fixture must remain visibly development-only", errors)
    validate_dormant_production_activation(errors)

    require("TECHNICAL ACTIVATION INFRASTRUCTURE IMPLEMENTED" in doc, "ending integration doc must record the implemented activation boundary", errors)
    require("Prior-Dragon sequence-break path" in doc, "ending integration doc must describe prior-Dragon delivery", errors)
    require("No bundled production quest currently sets it" in doc, "ending integration doc must preserve dormant production activation", errors)
    require("prior-Dragon sequence break" in protocol, "runtime protocol must exercise the sequence-break path", errors)

    if errors:
        print(f"OVERLORD ending-screen contracts failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1

    print("OVERLORD ending-screen contracts: PASS")
    print("activation: server-authoritative arm fact, production setter remains CI-forbidden")
    print("sequence break: persistent Dragon state can request direct one-time presentation")
    print("world continuity: vanilla callback preserved on normal End-poem path")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())