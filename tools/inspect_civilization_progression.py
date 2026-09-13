#!/usr/bin/env python3
"""Inspect one exact civilization mod JAR for durable campaign-facing signals.

This is a source-audit helper only. It does not define campaign content and it
never treats inventory possession or transient UI state as historical progress.
The report prioritizes native advancements and then enumerates resource/class
candidates that may expose a stronger owning-mod contract.
"""
from __future__ import annotations

import argparse
import hashlib
import json
import re
import sys
import zipfile
from pathlib import Path

ASCII = re.compile(rb"[ -~]{4,}")
DEFAULT_KEYWORDS = (
    "advancement",
    "progress",
    "quest",
    "trade",
    "merchant",
    "profession",
    "reputation",
    "stat",
    "persistent",
    "saveddata",
    "capability",
    "rune",
    "forge",
    "smith",
    "dwarf",
    "ribbit",
    "gardener",
    "music",
    "maraca",
    "kobold",
    "captain",
    "den",
    "armor",
    "weapon",
    "ingot",
)
TEXT_SUFFIXES = (".json", ".toml", ".mcmeta", ".txt", ".lang")
RESOURCE_SECTIONS = (
    "/advancements/",
    "/recipes/",
    "/loot_tables/",
    "/tags/",
    "/worldgen/",
)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar", type=Path)
    parser.add_argument("expected_mod_id")
    parser.add_argument("report", type=Path)
    parser.add_argument("--keyword", action="append", default=[])
    return parser.parse_args()


def ascii_matches(data: bytes, keywords: tuple[str, ...]) -> list[str]:
    matches: set[str] = set()
    for raw in ASCII.findall(data):
        text = raw.decode("ascii", errors="ignore")
        lowered = text.lower()
        if any(keyword in lowered for keyword in keywords):
            matches.add(text)
    return sorted(matches)


def dump_json(archive: zipfile.ZipFile, name: str) -> list[str]:
    try:
        parsed = json.loads(archive.read(name).decode("utf-8"))
        return json.dumps(parsed, indent=2, sort_keys=True).splitlines()
    except Exception as exc:
        return [f"<unreadable JSON: {exc}>"]


def main() -> int:
    args = parse_args()
    if not args.jar.is_file():
        print(f"missing JAR: {args.jar}", file=sys.stderr)
        return 2

    keywords = tuple(dict.fromkeys(DEFAULT_KEYWORDS + tuple(k.lower() for k in args.keyword)))
    digest = hashlib.sha256(args.jar.read_bytes()).hexdigest()
    lines: list[str] = [
        "OVERLORD QUESTS native civilization progression audit",
        f"artifact={args.jar.name}",
        f"expected_mod_id={args.expected_mod_id}",
        f"sha256={digest}",
        "",
    ]

    result = 0
    with zipfile.ZipFile(args.jar) as archive:
        names = sorted(archive.namelist())
        lower_names = {name.lower(): name for name in names}
        mods_toml_name = lower_names.get("meta-inf/mods.toml")
        mods_blob = archive.read(mods_toml_name) if mods_toml_name else b""

        lines.append("[identity]")
        if mods_toml_name:
            mods_text = mods_blob.decode("utf-8", errors="replace")
            identity_lines = [
                line.strip()
                for line in mods_text.splitlines()
                if any(token in line for token in ("modId", "version", "displayName"))
            ]
            lines.extend(identity_lines or ["mods.toml present; no compact identity lines matched"])
        else:
            lines.append("MISSING META-INF/mods.toml")
        lines.append("")

        if args.expected_mod_id.lower().encode() not in mods_blob.lower():
            lines.append(f"AUDIT_RESULT=FAIL expected mod id {args.expected_mod_id!r} not found")
            result = 1

        advancement_names = [
            name for name in names
            if "/advancements/" in name.lower() and name.lower().endswith(".json")
        ]
        lines.append(f"[native advancements] count={len(advancement_names)}")
        for name in advancement_names:
            lines.append(f"-- {name}")
            lines.extend(dump_json(archive, name))
        lines.append("")

        resource_candidates = [
            name for name in names
            if any(section in name.lower() for section in RESOURCE_SECTIONS)
            and any(keyword in name.lower() for keyword in keywords)
        ]
        lines.append(f"[keyword resource paths] count={len(resource_candidates)}")
        lines.extend(resource_candidates)
        lines.append("")

        text_candidates: list[tuple[str, list[str]]] = []
        class_candidates: list[tuple[str, list[str]]] = []
        for name in names:
            lowered = name.lower()
            if lowered.endswith(TEXT_SUFFIXES):
                found = ascii_matches(archive.read(name), keywords)
                if found:
                    text_candidates.append((name, found))
            elif lowered.endswith(".class"):
                found = ascii_matches(archive.read(name), keywords)
                if found:
                    class_candidates.append((name, found))

        lines.append(f"[text keyword constants] files={len(text_candidates)}")
        for name, found in text_candidates:
            lines.append(f"-- {name}")
            lines.extend(f"   {entry}" for entry in found[:100])
            if len(found) > 100:
                lines.append(f"   <{len(found) - 100} additional matches omitted>")
        lines.append("")

        lines.append(f"[class keyword constants] files={len(class_candidates)}")
        for name, found in class_candidates:
            lines.append(f"-- {name}")
            lines.extend(f"   {entry}" for entry in found[:100])
            if len(found) > 100:
                lines.append(f"   <{len(found) - 100} additional matches omitted>")
        lines.append("")

        if result == 0:
            lines.append("AUDIT_RESULT=PASS candidates enumerated; campaign suitability requires review")

    report = "\n".join(lines) + "\n"
    args.report.write_text(report, encoding="utf-8")
    print(report, end="")
    return result


if __name__ == "__main__":
    raise SystemExit(main())
