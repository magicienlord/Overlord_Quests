#!/usr/bin/env python3
"""Inspect the exact Realm RPG: Sea Dwellers JAR for stable progression signals.

This is a source-audit helper, not runtime quest logic. It intentionally reads the
pinned 1.20.1 artifact as a ZIP and reports resources/class constants that can be
used to choose the narrowest reliable OVERLORD Quests integration hook.
"""
from __future__ import annotations

import hashlib
import json
import re
import sys
import zipfile
from pathlib import Path

KEYWORDS = (
    "dragon",
    "quest",
    "advancement",
    "persistentdata",
    "persistent_data",
    "playervariables",
    "modvariables",
    "objective",
    "aquamarine",
)

ASCII = re.compile(rb"[ -~]{4,}")


def text_matches(data: bytes) -> list[str]:
    matches: set[str] = set()
    for raw in ASCII.findall(data):
        text = raw.decode("ascii", errors="ignore")
        lowered = text.lower()
        if any(keyword in lowered for keyword in KEYWORDS):
            matches.add(text)
    return sorted(matches)


def main() -> int:
    if len(sys.argv) not in (2, 3):
        print("usage: inspect_seadwellers_progression.py <jar> [report]", file=sys.stderr)
        return 2

    jar = Path(sys.argv[1])
    report_path = Path(sys.argv[2]) if len(sys.argv) == 3 else None
    if not jar.is_file():
        print(f"missing JAR: {jar}", file=sys.stderr)
        return 2

    digest = hashlib.sha256(jar.read_bytes()).hexdigest()
    lines: list[str] = [
        "Realm RPG: Sea Dwellers 1.20.1 progression source audit",
        f"artifact={jar.name}",
        f"sha256={digest}",
        "",
    ]

    with zipfile.ZipFile(jar) as archive:
        names = sorted(archive.namelist())
        lower_names = {name.lower(): name for name in names}

        mods_toml = lower_names.get("meta-inf/mods.toml")
        if mods_toml:
            text = archive.read(mods_toml).decode("utf-8", errors="replace")
            lines.extend(["[mods.toml]", text.strip(), ""])
        else:
            lines.append("[mods.toml]\nMISSING\n")

        advancement_names = [
            name for name in names
            if "/advancements/" in name.lower() and name.lower().endswith(".json")
        ]
        lines.append(f"[advancements] count={len(advancement_names)}")
        for name in advancement_names:
            lines.append(name)
            try:
                parsed = json.loads(archive.read(name).decode("utf-8"))
                lines.append(json.dumps(parsed, indent=2, sort_keys=True))
            except Exception as exc:
                lines.append(f"<unreadable JSON: {exc}>")
        lines.append("")

        named_candidates = [
            name for name in names
            if any(keyword in name.lower() for keyword in KEYWORDS)
        ]
        lines.append(f"[keyword entry names] count={len(named_candidates)}")
        lines.extend(named_candidates)
        lines.append("")

        text_resource_candidates: list[tuple[str, list[str]]] = []
        class_candidates: list[tuple[str, list[str]]] = []
        for name in names:
            lowered = name.lower()
            if lowered.endswith((".json", ".mcmeta", ".toml", ".txt", ".lang")):
                found = text_matches(archive.read(name))
                if found:
                    text_resource_candidates.append((name, found))
            elif lowered.endswith(".class"):
                found = text_matches(archive.read(name))
                if found:
                    class_candidates.append((name, found))

        lines.append(f"[text resource keyword constants] files={len(text_resource_candidates)}")
        for name, found in text_resource_candidates:
            lines.append(f"-- {name}")
            lines.extend(f"   {entry}" for entry in found[:120])
            if len(found) > 120:
                lines.append(f"   <{len(found) - 120} additional matches omitted>")
        lines.append("")

        lines.append(f"[class keyword constants] files={len(class_candidates)}")
        for name, found in class_candidates:
            lines.append(f"-- {name}")
            lines.extend(f"   {entry}" for entry in found[:120])
            if len(found) > 120:
                lines.append(f"   <{len(found) - 120} additional matches omitted>")
        lines.append("")

        # A useful audit must at least prove the expected mod identity and expose
        # one progression-shaped resource or class candidate. Do not silently
        # convert an opaque artifact into an assumed integration contract.
        mods_blob = archive.read(mods_toml).lower() if mods_toml else b""
        if b'seadwellers' not in mods_blob:
            lines.append("AUDIT_RESULT=FAIL expected mod id 'seadwellers' not found")
            result = 1
        elif not (advancement_names or named_candidates or class_candidates):
            lines.append("AUDIT_RESULT=FAIL no progression-shaped candidates found")
            result = 1
        else:
            lines.append("AUDIT_RESULT=PASS source candidates enumerated")
            result = 0

    report = "\n".join(lines) + "\n"
    if report_path:
        report_path.write_text(report, encoding="utf-8")
    print(report, end="")
    return result


if __name__ == "__main__":
    raise SystemExit(main())
