#!/usr/bin/env python3
"""Report screen-space geometry for the Gnarl popup development fixture.

This is a static implementation aid, not an in-game renderer test. It mirrors the
relevant QuestDetails placement constants while the right details panel is closed.
The report makes clipping and content-overlap thresholds explicit before manual
GUI-scale testing.
"""

from __future__ import annotations

import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
QUEST = ROOT / "examples" / "questlog" / "quests" / "overlord_gnarl_popup_dev.json"

# QuestDetails constants that affect the current single-panel prototype.
CONTENT_X = 18
CONTENT_Y = 36
CONTENT_WIDTH_INSET = 38
CONTENT_HEIGHT_INSET = 68
BUTTON_Y_GAP = 2
BUTTON_HEIGHT = 18


def require_int(data: dict, key: str, default: int | None = None) -> int:
    if key in data:
        value = data[key]
    elif default is not None:
        value = default
    else:
        raise ValueError(f"missing required integer field: {key}")
    if not isinstance(value, int) or isinstance(value, bool):
        raise ValueError(f"{key} must be an integer")
    return value


def intersection(a0: int, a1: int, b0: int, b1: int) -> int:
    return max(0, min(a1, b1) - max(a0, b0))


def horizontal_geometry(data: dict, screen_width: int) -> dict[str, int]:
    left_width = require_int(data, "left_panel_width", 275)
    panel_offset = require_int(data, "left_panel_x_offset", 0)
    overlay_width = require_int(data, "overlay_width", left_width)
    overlay_offset = require_int(data, "overlay_x_offset", 0)

    base_x = (screen_width - left_width) // 2
    panel_x = base_x + panel_offset
    overlay_x = panel_x + overlay_offset
    left = min(panel_x, overlay_x)
    right = max(panel_x + left_width, overlay_x + overlay_width)

    return {
        "panel_x": panel_x,
        "overlay_x": overlay_x,
        "composition_left": left,
        "composition_right": right,
        "clip_left": max(0, -left),
        "clip_right": max(0, right - screen_width),
    }


def vertical_geometry(data: dict, screen_height: int) -> dict[str, int]:
    panel_height = require_int(data, "panel_height", 166)
    panel_offset = require_int(data, "left_panel_y_offset", 0)
    overlay_height = require_int(data, "overlay_height", panel_height)
    overlay_offset = require_int(data, "overlay_y_offset", 0)

    base_y = (screen_height - panel_height) // 2
    panel_y = base_y + panel_offset
    overlay_y = panel_y + overlay_offset
    button_y = panel_y + panel_height + BUTTON_Y_GAP

    top = min(panel_y, overlay_y)
    bottom = max(
        panel_y + panel_height,
        overlay_y + overlay_height,
        button_y + BUTTON_HEIGHT,
    )

    return {
        "panel_y": panel_y,
        "overlay_y": overlay_y,
        "button_y": button_y,
        "composition_top": top,
        "composition_bottom": bottom,
        "clip_top": max(0, -top),
        "clip_bottom": max(0, bottom - screen_height),
    }


def minimum_full_visibility_width(data: dict) -> int:
    for width in range(1, 4097):
        geo = horizontal_geometry(data, width)
        if geo["clip_left"] == 0 and geo["clip_right"] == 0:
            return width
    raise RuntimeError("no full-visibility width found <= 4096")


def minimum_full_visibility_height(data: dict) -> int:
    for height in range(1, 4097):
        geo = vertical_geometry(data, height)
        if geo["clip_top"] == 0 and geo["clip_bottom"] == 0:
            return height
    raise RuntimeError("no full-visibility height found <= 4096")


def main() -> int:
    data = json.loads(QUEST.read_text(encoding="utf-8"))

    if data.get("disable_details_button") is not True or data.get("details_open_by_default") is not False:
        raise ValueError(
            "static report assumes the Gnarl fixture opens with the right details panel closed"
        )

    left_width = require_int(data, "left_panel_width", 275)
    panel_height = require_int(data, "panel_height", 166)
    overlay_width = require_int(data, "overlay_width", left_width)
    overlay_height = require_int(data, "overlay_height", panel_height)
    overlay_x_offset = require_int(data, "overlay_x_offset", 0)
    overlay_y_offset = require_int(data, "overlay_y_offset", 0)

    panel_left = 0
    panel_right = left_width
    panel_top = 0
    panel_bottom = panel_height

    overlay_left = overlay_x_offset
    overlay_right = overlay_x_offset + overlay_width
    overlay_top = overlay_y_offset
    overlay_bottom = overlay_y_offset + overlay_height

    panel_overlap_x = intersection(panel_left, panel_right, overlay_left, overlay_right)
    panel_overlap_y = intersection(panel_top, panel_bottom, overlay_top, overlay_bottom)

    description_left = CONTENT_X
    description_right = CONTENT_X + (left_width - CONTENT_WIDTH_INSET)
    description_top = CONTENT_Y
    description_bottom = CONTENT_Y + (panel_height - CONTENT_HEIGHT_INSET)

    description_overlap_x = intersection(
        description_left, description_right, overlay_left, overlay_right
    )
    description_overlap_y = intersection(
        description_top, description_bottom, overlay_top, overlay_bottom
    )

    min_width = minimum_full_visibility_width(data)
    min_height = minimum_full_visibility_height(data)

    print("GNARL POPUP STATIC LAYOUT REPORT")
    print("status: implementation geometry only; manual in-game review still required")
    print("runtime scope: unpublished local single-player world")
    print(f"left panel: {left_width} x {panel_height}")
    print(f"overlay: {overlay_width} x {overlay_height}")
    print(f"overlay offset from left panel: x={overlay_x_offset:+d}, y={overlay_y_offset:+d}")
    print(f"horizontal portrait/panel overlap: {panel_overlap_x} px")
    print(f"vertical portrait/panel overlap: {panel_overlap_y} px")
    print(
        "description rectangle: "
        f"x={description_left}..{description_right}, y={description_top}..{description_bottom}"
    )
    print(
        "portrait/description geometric intrusion: "
        f"{description_overlap_x} x {description_overlap_y} px "
        f"({description_overlap_x * description_overlap_y} px^2)"
    )
    print(f"primary button extends {BUTTON_Y_GAP + BUTTON_HEIGHT} px below the panel")
    print(f"minimum scaled GUI width for full horizontal visibility: {min_width} px")
    print(f"minimum scaled GUI height for panel/overlay/button visibility: {min_height} px")
    print()
    print("scaled_width  panel_x  overlay_x  left_clip  right_clip")
    for width in (320, 360, 400, 426, 440, 480, 640, 854, 960):
        geo = horizontal_geometry(data, width)
        print(
            f"{width:12d}  {geo['panel_x']:7d}  {geo['overlay_x']:9d}"
            f"  {geo['clip_left']:9d}  {geo['clip_right']:10d}"
        )

    print()
    print("scaled_height  panel_y  overlay_y  button_y  top_clip  bottom_clip")
    for height in (180, 200, 228, 229, 240, 270, 360, 480):
        geo = vertical_geometry(data, height)
        print(
            f"{height:13d}  {geo['panel_y']:7d}  {geo['overlay_y']:9d}"
            f"  {geo['button_y']:8d}  {geo['clip_top']:8d}  {geo['clip_bottom']:11d}"
        )

    if description_overlap_x > 0 and description_overlap_y > 0:
        print()
        print(
            "NOTE: the current overlay reaches into the description rectangle. "
            "This is a geometric warning only; alpha in the portrait and actual text glyph placement "
            "must be judged in game."
        )

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
