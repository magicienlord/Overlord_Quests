#!/usr/bin/env python3
"""Require every production narrative-fact reference to be documented.

The bundled quest manifest is the production authority for this check. Development
fixtures are intentionally excluded. A fact referenced by a bundled fact objective,
set_fact reward, or provider required/forbidden-fact gate must appear in
`docs/NARRATIVE_FACTS.md` so the durable save key has an implementation-facing
semantic record before it ships.
"""
from __future__ import annotations

import json
from pathlib import Path
import sys
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFINITIONS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
INDEX = DEFINITIONS / "index.json"
QUESTS = DEFINITIONS / "quests"
FACTS_DOC = ROOT / "docs/NARRATIVE_FACTS.md"


def collect_from_node(node: Any, facts: set[str]) -> None:
    if isinstance(node, list):
        for child in node:
            collect_from_node(child, facts)
        return
    if not isinstance(node, dict):
        return

    entry_type = node.get("type")
    if entry_type in {"questlog:fact", "fact", "questlog:set_fact", "set_fact"}:
        fact = node.get("fact")
        if isinstance(fact, str) and fact.strip():
            facts.add(fact.strip())

    provider = node.get("provider")
    if isinstance(provider, dict):
        for key in ("required_facts", "forbidden_facts"):
            values = provider.get(key, [])
            if isinstance(values, list):
                for value in values:
                    if isinstance(value, str) and value.strip():
                        facts.add(value.strip())

    for value in node.values():
        collect_from_node(value, facts)


def collect_errors() -> list[str]:
    errors: list[str] = []
    try:
        index = json.loads(INDEX.read_text(encoding="utf-8"))
    except Exception as exc:
        return [f"{INDEX.relative_to(ROOT)}: cannot load bundled index: {exc}"]

    entries = index.get("quests", [])
    if not isinstance(entries, list):
        return [f"{INDEX.relative_to(ROOT)}: quests must be a list"]

    production_facts: set[str] = set()
    for relative in entries:
        if not isinstance(relative, str):
            continue
        path = QUESTS / relative
        try:
            quest = json.loads(path.read_text(encoding="utf-8"))
        except Exception as exc:
            errors.append(f"{path.relative_to(ROOT)}: cannot load JSON: {exc}")
            continue
        collect_from_node(quest, production_facts)

    try:
        documentation = FACTS_DOC.read_text(encoding="utf-8")
    except OSError as exc:
        errors.append(f"{FACTS_DOC.relative_to(ROOT)}: cannot read: {exc}")
        return errors

    for fact in sorted(production_facts):
        if f"`{fact}`" not in documentation and fact not in documentation:
            errors.append(f"undocumented production narrative fact: {fact}")

    if not production_facts:
        errors.append("bundled production manifest yielded no narrative facts")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Narrative fact documentation validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("Bundled production narrative-fact documentation: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
