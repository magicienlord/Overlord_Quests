#!/usr/bin/env python3
"""Static safety contracts for the OVERLORD REIGN ending preview scaffold."""
from __future__ import annotations

from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
CONFIG = ROOT / "common/src/main/java/org/infernalstudios/questlog/config/QuestlogConfig.java"
ACCESSOR = ROOT / "common/src/main/java/org/infernalstudios/questlog/mixin/client/WinScreenAccessor.java"
MIXINS = ROOT / "common/src/main/resources/questlog.mixins.json"
ROUTER = ROOT / "common/src/main/java/org/infernalstudios/questlog/client/ending/OverlordEndingScreens.java"
SCREEN = ROOT / "common/src/main/java/org/infernalstudios/questlog/client/ending/OverlordEndingScreen.java"
FORGE_EVENTS = ROOT / "forge/src/main/java/org/infernalstudios/questlog/QuestlogForgeEventForwarder.java"
DOC = ROOT / "docs/OVERLORD_ENDING_SCREEN_INTEGRATION.md"


def read(path: Path, errors: list[str]) -> str:
    try:
        return path.read_text(encoding="utf-8")
    except OSError as exc:
        errors.append(f"{path.relative_to(ROOT)}: {exc}")
        return ""


def require(condition: bool, message: str, errors: list[str]) -> None:
    if not condition:
        errors.append(message)


def main() -> int:
    errors: list[str] = []
    config = read(CONFIG, errors)
    accessor = read(ACCESSOR, errors)
    mixins = read(MIXINS, errors)
    router = read(ROUTER, errors)
    screen = read(SCREEN, errors)
    forge_events = read(FORGE_EVENTS, errors)
    doc = read(DOC, errors)

    ending_section = config[config.find("public static class EndingScreen"):] if "public static class EndingScreen" in config else ""
    require("public static class EndingScreen" in config, "ending-screen configuration section is missing", errors)
    require("public boolean developmentPreview = false;" in ending_section, "ending development preview must default to false", errors)
    require("public int minimumDisplayTicks = 80;" in ending_section, "ending preview dwell-time default changed unexpectedly", errors)

    require('@Mixin(WinScreen.class)' in accessor, "WinScreen accessor must target vanilla WinScreen", errors)
    require('@Accessor("poem")' in accessor, "WinScreen accessor must expose the poem discriminator", errors)
    require('@Accessor("onFinished")' in accessor, "WinScreen accessor must expose the vanilla completion callback", errors)
    require('"client.WinScreenAccessor"' in mixins, "WinScreen accessor must be registered as a client mixin", errors)

    require("instanceof WinScreen winScreen" in router, "ending router must only consider vanilla WinScreen", errors)
    require("!config.enabled || !config.developmentPreview" in router, "ending replacement must remain preview-gated", errors)
    require("!minecraft.hasSingleplayerServer()" in router, "ending replacement must require local single-player", errors)
    require("server.isPublished()" in router, "ending replacement must reject LAN-published sessions", errors)
    require("!accessor.questlog$isPoem()" in router, "ending replacement must reject ordinary credits", errors)
    require("accessor.questlog$getOnFinished()" in router, "ending replacement must preserve vanilla completion callback", errors)
    require("new OverlordEndingScreen(onFinished)" in router, "ending router must pass the vanilla callback into the replacement", errors)

    require("if (this.finished)" in screen, "ending screen must guard its completion callback against duplicate invocation", errors)
    require("this.finished = true;" in screen, "ending screen must latch completion before invoking vanilla callback", errors)
    require("this.onFinished.run();" in screen, "ending screen must invoke the preserved vanilla completion callback", errors)
    require("minimumDisplayTicks" in screen, "ending screen must respect the bounded preview dwell time", errors)
    require("ENDING PRESENTATION DEVELOPMENT SCAFFOLD" in screen, "preview must remain visibly marked as a development scaffold", errors)

    require("OverlordEndingScreens.replace(replacement)" in forge_events, "Forge screen-opening hook must route WinScreen through the ending boundary", errors)

    require("TECHNICAL PREVIEW SCAFFOLD" in doc, "ending integration doc must preserve preview-only status", errors)
    require("developmentPreview` defaults to `false`" in doc, "ending integration doc must record the fail-closed preview default", errors)
    require("production activation" in doc.lower(), "ending integration doc must record the missing production activation gate", errors)

    if errors:
        print(f"OVERLORD ending-screen contracts failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1

    print("OVERLORD ending-screen contracts: PASS")
    print("activation: development preview only, disabled by default")
    print("vanilla boundary: End poem only, menu credits untouched")
    print("world continuity: original WinScreen completion callback preserved")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
