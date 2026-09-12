#!/usr/bin/env python3
"""Report screen-space geometry for the Gnarl speaker popup fixture.

This mirrors OverlordSpeakerScreen's layout policy closely enough to catch
regressions before in-game review. It deliberately treats the parchment body and
speaker lane as disjoint surfaces and keeps the primary action under parchment.
"""
from __future__ import annotations

import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
QUEST = ROOT / "examples" / "questlog" / "quests" / "overlord_gnarl_popup_dev.json"

OUTER_MARGIN = 12
SPEAKER_GAP = 10
MIN_PANEL_WIDTH = 220
MIN_SPEAKER_WIDTH = 72
BUTTON_GAP = 2
BUTTON_HEIGHT = 18


def require_int(data: dict, key: str, default: int | None = None) -> int:
    value = data.get(key, default)
    if value is None or not isinstance(value, int) or isinstance(value, bool):
        raise ValueError(f"{key} must be an integer")
    return value


def horizontal_geometry(data: dict, screen_width: int) -> dict[str, int]:
    desired_panel = require_int(data, "left_panel_width", 275)
    requested_speaker = require_int(data, "speaker_pane_width", 170)
    available = max(MIN_PANEL_WIDTH, screen_width - OUTER_MARGIN * 2)

    speaker = requested_speaker
    desired_total = desired_panel + SPEAKER_GAP + speaker
    if desired_total > available:
        speaker_budget = available - desired_panel - SPEAKER_GAP
        speaker = max(MIN_SPEAKER_WIDTH, min(speaker, speaker_budget))

    panel_budget = available - SPEAKER_GAP - speaker
    panel = max(MIN_PANEL_WIDTH, min(desired_panel, panel_budget))
    total = panel + SPEAKER_GAP + speaker
    panel_x = (screen_width - total) // 2
    speaker_x = panel_x + panel + SPEAKER_GAP

    return {
        "panel_width": panel,
        "speaker_width": speaker,
        "panel_x": panel_x,
        "speaker_x": speaker_x,
        "composition_left": panel_x,
        "composition_right": speaker_x + speaker,
        "clip_left": max(0, -panel_x),
        "clip_right": max(0, speaker_x + speaker - screen_width),
        "surface_gap": speaker_x - (panel_x + panel),
    }


def vertical_geometry(data: dict, screen_height: int) -> dict[str, int]:
    requested_height = require_int(data, "panel_height", 166)
    panel_height = min(requested_height, max(140, screen_height - 54))
    panel_y = max(8, (screen_height - panel_height - 22) // 2)
    button_y = panel_y + panel_height + BUTTON_GAP
    bottom = button_y + BUTTON_HEIGHT
    return {
        "panel_height": panel_height,
        "panel_y": panel_y,
        "button_y": button_y,
        "clip_top": max(0, -panel_y),
        "clip_bottom": max(0, bottom - screen_height),
    }


def main() -> int:
    data = json.loads(QUEST.read_text(encoding="utf-8"))
    errors: list[str] = []

    if data.get("speaker_id") != "overlord_reign:gnarl":
        errors.append("Gnarl fixture must declare speaker_id=overlord_reign:gnarl")
    if data.get("speaker_reaction") not in {"neutral", "directive", "mocking", "approving", "severe"}:
        errors.append("Gnarl fixture reaction is outside the locked five-state vocabulary")
    if data.get("disable_details_button") is not True:
        errors.append("Gnarl popup fixture must keep the inherited details panel disabled")

    overlay_width = require_int(data, "overlay_width", 160)
    overlay_height = require_int(data, "overlay_height", 160)
    speaker_width = require_int(data, "speaker_pane_width", 170)
    panel_width = require_int(data, "left_panel_width", 275)
    panel_height = require_int(data, "panel_height", 166)

    if overlay_width > speaker_width:
        errors.append("portrait width exceeds its declared speaker lane")

    print("GNARL SPEAKER POPUP STATIC LAYOUT REPORT")
    print("status: presentation geometry only; in-game visual approval remains required")
    print(f"parchment request: {panel_width} x {panel_height}")
    print(f"speaker lane request: {speaker_width} px")
    print(f"portrait request: {overlay_width} x {overlay_height}")
    print(f"locked surface gap: {SPEAKER_GAP} px")
    print("primary action: below parchment body, never below speaker lane")
    print()
    print("scaled_width  panel_w  speaker_w  panel_x  speaker_x  gap  left_clip  right_clip")
    for width in (320, 360, 400, 426, 480, 540, 640, 854, 960):
        geo = horizontal_geometry(data, width)
        print(
            f"{width:12d}  {geo['panel_width']:7d}  {geo['speaker_width']:9d}"
            f"  {geo['panel_x']:7d}  {geo['speaker_x']:9d}  {geo['surface_gap']:3d}"
            f"  {geo['clip_left']:9d}  {geo['clip_right']:10d}"
        )

    print()
    print("scaled_height  panel_h  panel_y  button_y  top_clip  bottom_clip")
    for height in (180, 200, 228, 240, 270, 360, 480):
        geo = vertical_geometry(data, height)
        print(
            f"{height:13d}  {geo['panel_height']:7d}  {geo['panel_y']:7d}"
            f"  {geo['button_y']:8d}  {geo['clip_top']:8d}  {geo['clip_bottom']:11d}"
        )

    if errors:
        print()
        print("GNARL SPEAKER POPUP LAYOUT CHECK FAILED:")
        for error in errors:
            print(f"  * {error}")
        return 1

    print()
    print("GNARL SPEAKER POPUP LAYOUT CHECK: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
