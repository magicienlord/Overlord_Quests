#!/usr/bin/env python3
"""Reject cyclic quest_complete dependencies in repository-controlled definitions.

The runtime has an identity-based recursion guard so arbitrary external config
cannot overflow the stack. This static check catches authoring mistakes earlier
for OVERLORD development fixtures and approved bundled quest definitions.

Only dependencies between definitions visible to this repository are considered.
A quest_complete target supplied by a future external compatibility package is
therefore not assumed missing or invalid here.
"""

from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any, Iterable

ROOT = Path(__file__).resolve().parents[1]
EXAMPLE_ROOT = ROOT / "examples" / "questlog" / "quests"
BUNDLED_ROOT = ROOT / "common" / "src" / "main" / "resources" / "assets" / "questlog" / "overlord" / "definitions"
BUNDLED_INDEX = BUNDLED_ROOT / "index.json"
QUEST_SOURCE = ROOT / "common" / "src" / "main" / "java" / "org" / "infernalstudios" / "questlog" / "core" / "quests" / "Quest.java"


def normalize_type(value: Any) -> str:
    if not isinstance(value, str):
        return ""
    return value if ":" in value else f"questlog:{value}"


def normalize_quest_id(value: Any) -> str | None:
    if not isinstance(value, str) or not value.strip():
        return None
    value = value.strip()
    return value if ":" in value else f"questlog:{value}"


def collect_dependencies(entry: Any, found: set[str]) -> None:
    if not isinstance(entry, dict):
        return

    objective_type = normalize_type(entry.get("type"))
    if objective_type == "questlog:quest_complete":
        target = normalize_quest_id(entry.get("quest"))
        if target is not None:
            found.add(target)

    if objective_type in {"questlog:and", "questlog:or"}:
        children = entry.get("objectives", [])
        if isinstance(children, list):
            for child in children:
                collect_dependencies(child, found)
    elif objective_type == "questlog:not":
        collect_dependencies(entry.get("objective"), found)


def read_json(path: Path) -> dict[str, Any] | None:
    try:
        value = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        raise RuntimeError(f"{path.relative_to(ROOT)}: cannot parse JSON: {exc}") from exc
    return value if isinstance(value, dict) else None


def iter_definitions() -> Iterable[tuple[str, Path]]:
    if EXAMPLE_ROOT.is_dir():
        for path in sorted(EXAMPLE_ROOT.glob("overlord_*.json")):
            yield f"questlog:{path.stem}", path

    if not BUNDLED_INDEX.is_file():
        return

    index = read_json(BUNDLED_INDEX)
    if index is None:
        raise RuntimeError("bundled definition index must be a JSON object")

    quests = index.get("quests", [])
    if not isinstance(quests, list):
        raise RuntimeError("bundled definition index 'quests' must be a list")

    quest_root = (BUNDLED_ROOT / "quests").resolve()
    for entry in quests:
        if not isinstance(entry, str):
            continue
        path = (quest_root / entry).resolve()
        if quest_root not in path.parents or not path.is_file() or path.suffix != ".json":
            continue
        relative = path.relative_to(quest_root).as_posix()
        quest_id = f"questlog:{relative[:-5]}"
        yield quest_id, path


def find_cycle(graph: dict[str, set[str]]) -> list[str] | None:
    visiting: set[str] = set()
    visited: set[str] = set()
    stack: list[str] = []

    def visit(node: str) -> list[str] | None:
        if node in visiting:
            start = stack.index(node)
            return stack[start:] + [node]
        if node in visited:
            return None

        visiting.add(node)
        stack.append(node)
        for target in sorted(graph.get(node, ())):
            if target not in graph:
                continue
            cycle = visit(target)
            if cycle is not None:
                return cycle
        stack.pop()
        visiting.remove(node)
        visited.add(node)
        return None

    for node in sorted(graph):
        cycle = visit(node)
        if cycle is not None:
            return cycle
    return None


def validate_runtime_guard() -> list[str]:
    errors: list[str] = []
    source = QUEST_SOURCE.read_text(encoding="utf-8")
    required = {
        "ThreadLocal<Set<Quest>> COMPLETION_EVALUATION": "identity-based completion recursion guard",
        "if (!evaluating.add(this))": "cycle detection branch",
        "REPORTED_COMPLETION_CYCLES = ConcurrentHashMap.newKeySet()": "per-quest warning suppression set",
        "if (REPORTED_COMPLETION_CYCLES.add(this.id))": "log-once cycle warning gate",
        "COMPLETION_EVALUATION.remove();": "ThreadLocal cleanup",
    }
    for token, label in required.items():
        if token not in source:
            errors.append(f"Quest.java is missing {label}")
    return errors


def main() -> int:
    runtime_errors = validate_runtime_guard()
    if runtime_errors:
        print("Quest completion runtime guard validation failed:", file=sys.stderr)
        for error in runtime_errors:
            print(f" - {error}", file=sys.stderr)
        return 1

    try:
        definitions = list(iter_definitions())
    except RuntimeError as exc:
        print(f"ERROR: {exc}", file=sys.stderr)
        return 1

    graph: dict[str, set[str]] = {}
    locations: dict[str, Path] = {}

    for quest_id, path in definitions:
        try:
            data = read_json(path)
        except RuntimeError as exc:
            print(f"ERROR: {exc}", file=sys.stderr)
            return 1
        if data is None:
            continue

        dependencies: set[str] = set()
        for key in ("prerequisites", "requirements", "objectives", "failures"):
            entries = data.get(key, [])
            if isinstance(entries, list):
                for entry in entries:
                    collect_dependencies(entry, dependencies)
        graph[quest_id] = dependencies
        locations[quest_id] = path

    cycle = find_cycle(graph)
    if cycle is not None:
        print("Cyclic quest_complete dependency detected:", file=sys.stderr)
        print(" -> ".join(cycle), file=sys.stderr)
        for quest_id in dict.fromkeys(cycle):
            path = locations.get(quest_id)
            if path is not None:
                print(f" - {quest_id}: {path.relative_to(ROOT)}", file=sys.stderr)
        return 1

    edge_count = sum(len(edges) for edges in graph.values())
    print(
        f"Quest dependency graph valid across {len(graph)} repository-controlled quest(s); "
        f"{edge_count} quest_complete edge(s), no cycles; runtime recursion guard intact."
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
