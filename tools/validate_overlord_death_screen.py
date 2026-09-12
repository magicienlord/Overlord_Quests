#!/usr/bin/env python3
"""Guard the approved OVERLORD death and ending presentation boundaries."""
from pathlib import Path
import sys

import validate_overlord_ending_screen as ending_contract

ROOT = Path(__file__).resolve().parents[1]
JAVA_ROOTS = [
    ROOT / "common/src/main/java/org/infernalstudios/questlog/client/death",
    ROOT / "forge/src/main/java/org/infernalstudios/questlog/OverlordDeathScreenCompatibilityForge.java",
]
FORBIDDEN_CODE = ("rendergrain", "scanline", "chromatic", "scenecue", "scenesound")
FORBIDDEN_ASSET_NAMES = ("cassette", "heartbeat", "breathing", "tape.ogg")


def main() -> int:
    errors: list[str] = []

    for root in JAVA_ROOTS:
        paths = [root] if root.is_file() else sorted(root.glob("*.java"))
        for path in paths:
            text = path.read_text(encoding="utf-8").lower()
            for token in FORBIDDEN_CODE:
                if token in text:
                    errors.append(f"{path.relative_to(ROOT)} contains rejected presentation token {token!r}")

    resource_root = ROOT / "common/src/main/resources/assets/questlog"
    if resource_root.exists():
        for path in resource_root.rglob("*"):
            if path.is_file() and any(token in path.name.lower() for token in FORBIDDEN_ASSET_NAMES):
                errors.append(f"rejected Epic Death Screen audio resource: {path.relative_to(ROOT)}")

    if errors:
        print("OVERLORD death-screen boundary validation failed:", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("OVERLORD death-screen boundary: PASS (no VHS or cassette soundscape implementation)")
    return ending_contract.main()


if __name__ == "__main__":
    raise SystemExit(main())
