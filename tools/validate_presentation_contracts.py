#!/usr/bin/env python3
"""Validate OVERLORD QUESTS speaker/provider presentation boundaries.

This is an implementation contract, not a story-canon validator. It keeps the
five semantic reaction states stable and prevents in-world provider definitions
from accidentally acquiring the incorporeal Questlog portrait system.
"""
from __future__ import annotations

import json
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
QUEST_ROOTS = (
    ROOT / "examples" / "questlog" / "quests",
    ROOT / "common" / "src" / "main" / "resources" / "assets" / "questlog" / "overlord" / "definitions" / "quests",
)
SPEAKER_PRESENTATION = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/display/SpeakerPresentation.java"
CLIENT_EVENTS = ROOT / "common/src/main/java/org/infernalstudios/questlog/QuestlogClientEvents.java"
PROVIDER_SCREEN = ROOT / "common/src/main/java/org/infernalstudios/questlog/client/provider/QuestProviderScreen.java"
PORTRAIT_TEXTURES = ROOT / "common/src/main/java/org/infernalstudios/questlog/client/gui/SpeakerPortraitTextures.java"

RESOURCE_ID = re.compile(r"^[a-z0-9_.-]+:[a-z0-9_./-]+$")
REACTIONS = {"neutral", "directive", "mocking", "approving", "severe"}
SPEAKER_FIELDS = {"speaker_id", "speaker_reaction", "speaker_pane_width", "speaker_alpha_cleanup"}


def iter_quest_files():
    for root in QUEST_ROOTS:
        if root.is_dir():
            yield from sorted(root.rglob("*.json"))


def collect_errors() -> list[str]:
    errors: list[str] = []

    for path in iter_quest_files():
        try:
            data = json.loads(path.read_text(encoding="utf-8"))
        except (OSError, json.JSONDecodeError):
            continue
        if not isinstance(data, dict):
            continue

        present = SPEAKER_FIELDS.intersection(data)
        speaker_id = data.get("speaker_id")
        if speaker_id is None:
            if present:
                errors.append(
                    f"{path.relative_to(ROOT)}: speaker presentation fields require speaker_id"
                )
            continue

        if not isinstance(speaker_id, str) or not RESOURCE_ID.fullmatch(speaker_id):
            errors.append(f"{path.relative_to(ROOT)}: speaker_id must be a namespaced resource id")

        reaction = data.get("speaker_reaction", "neutral")
        if reaction not in REACTIONS:
            errors.append(
                f"{path.relative_to(ROOT)}: speaker_reaction must be one of {sorted(REACTIONS)}, got {reaction!r}"
            )

        pane_width = data.get("speaker_pane_width", 170)
        if not isinstance(pane_width, int) or isinstance(pane_width, bool) or not 96 <= pane_width <= 512:
            errors.append(f"{path.relative_to(ROOT)}: speaker_pane_width must be an integer from 96 through 512")

        alpha_cleanup = data.get("speaker_alpha_cleanup", False)
        if not isinstance(alpha_cleanup, bool):
            errors.append(f"{path.relative_to(ROOT)}: speaker_alpha_cleanup must be a boolean")

        overlay = data.get("overlay")
        if not isinstance(overlay, str) or not RESOURCE_ID.fullmatch(overlay):
            errors.append(f"{path.relative_to(ROOT)}: speaker presentation currently requires a valid overlay portrait resource")

        for key in ("overlay_width", "overlay_height"):
            value = data.get(key)
            if not isinstance(value, int) or isinstance(value, bool) or value <= 0:
                errors.append(f"{path.relative_to(ROOT)}: {key} must be a positive integer for speaker presentation")

        if data.get("provider") is not None:
            errors.append(
                f"{path.relative_to(ROOT)}: in-world provider quests cannot use the incorporeal speaker reaction pane"
            )

        if data.get("show_popup_on_unlock") is not True:
            errors.append(
                f"{path.relative_to(ROOT)}: speaker presentation is currently supported only on popup-on-unlock entries"
            )

    try:
        speaker_source = SPEAKER_PRESENTATION.read_text(encoding="utf-8")
        for reaction in REACTIONS:
            if reaction.upper() not in speaker_source:
                errors.append(f"SpeakerPresentation.java: missing reaction state {reaction}")
        if 'speaker_alpha_cleanup' not in speaker_source:
            errors.append("SpeakerPresentation.java: alpha-cleanup authoring flag is missing")
    except OSError as exc:
        errors.append(f"SpeakerPresentation.java: unreadable: {exc}")

    try:
        portrait_source = PORTRAIT_TEXTURES.read_text(encoding="utf-8")
        if "cleanMatteFringe" not in portrait_source or "DynamicTexture" not in portrait_source:
            errors.append("SpeakerPortraitTextures.java: opt-in runtime alpha cleanup is missing")
    except OSError as exc:
        errors.append(f"SpeakerPortraitTextures.java: unreadable: {exc}")

    try:
        events = CLIENT_EVENTS.read_text(encoding="utf-8")
        if "hasSpeakerPresentation()" not in events or "new OverlordSpeakerScreen(currentQuest)" not in events:
            errors.append("QuestlogClientEvents.java: speaker quests are not routed to the dedicated popup surface")
    except OSError as exc:
        errors.append(f"QuestlogClientEvents.java: unreadable: {exc}")

    try:
        provider = PROVIDER_SCREEN.read_text(encoding="utf-8")
        if "SpeakerPresentation" in provider or "speaker_reaction" in provider:
            errors.append("QuestProviderScreen.java: provider surface must not depend on incorporeal reaction visuals")
        if "QuestlogWideButton" not in provider or "detailBackgroundLeft.blit" not in provider:
            errors.append("QuestProviderScreen.java: shared Questlog parchment/button visual language is missing")
    except OSError as exc:
        errors.append(f"QuestProviderScreen.java: unreadable: {exc}")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"OVERLORD presentation contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1

    print("OVERLORD presentation contracts: PASS")
    print("reaction states: " + ", ".join(sorted(REACTIONS)))
    print("provider boundary: in-world provider UI remains portrait-roster independent")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
