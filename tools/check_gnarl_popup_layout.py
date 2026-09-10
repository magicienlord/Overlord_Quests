#!/usr/bin/env python3
"""Report the screen-space geometry of the Gnarl popup development fixture.

This is a static implementation aid, not an in-game renderer test. It mirrors the
integer placement used by QuestDetails while its right details panel is closed.
The report makes clipping thresholds explicit before manual GUI-scale testing.
"""

from __future__ import annotations

import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
QUEST = ROOT / "examples" / "questlog" / "quests" / "overlord_gnarl_popup_dev.json"


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


def minimum_full_visibility_width(data: dict) -> int:
    for width in range(1, 4097):
        geo = horizontal_geometry(data, width)
        if geo["clip_left"] == 0 and geo["clip_right"] == 0:
            return width
    raise RuntimeError("no full-visibility width found <= 4096")


def main() -> int:
    data = json.loads(QUEST.read_text(encoding="utf-8"))

    left_width = require_int(data, "left_panel_width", 275)
    panel_height = require_int(data, "panel_height", 166)
    overlay_width = require_int(data, "overlay_width", left_width)
    overlay_height = require_int(data, "overlay_height", panel_height)
    overlay_x_offset = require_int(data, "overlay_x_offset", 0)
    overlay_y_offset = require_int(data, "overlay_y_offset", 0)

    panel_left = 0
    panel_right = left_width
    overlay_left = overlay_x_offset
    overlay_right = overlay_x_offset + overlay_width
    overlap = max(0, min(panel_right, overlay_right) - max(panel_left, overlay_left))

    min_width = minimum_full_visibility_width(data)
    vertical_top = min(0, overlay_y_offset)
    vertical_bottom = max(panel_height, overlay_y_offset + overlay_height)
    composition_height = vertical_bottom - vertical_top

    print("GNARL POPUP STATIC LAYOUT REPORT")
    print("status: implementation geometry only; manual in-game review still required")
    print(f"left panel: {left_width} x {panel_height}")
    print(f"overlay: {overlay_width} x {overlay_height}")
    print(f"overlay offset from left panel: x={overlay_x_offset:+d}, y={overlay_y_offset:+d}")
    print(f"horizontal portrait/panel overlap: {overlap} px")
    print(f"composition height: {composition_height} px")
    print(f"minimum scaled GUI width for full horizontal visibility: {min_width} px")
    print()
    print("scaled_width  panel_x  overlay_x  left_clip  right_clip")
    for width in (320, 360, 400, 426, 440, 480, 640, 854, 960):
        geo = horizontal_geometry(data, width)
        print(
            f"{width:12d}  {geo['panel_x']:7d}  {geo['overlay_x']:9d}"
            f"  {geo['clip_left']:9d}  {geo['clip_right']:10d}"
        )

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
