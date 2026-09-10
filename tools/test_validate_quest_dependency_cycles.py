#!/usr/bin/env python3
"""Small self-test for validate_quest_dependency_cycles.py."""

from __future__ import annotations

import importlib.util
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
VALIDATOR = ROOT / "tools" / "validate_quest_dependency_cycles.py"

spec = importlib.util.spec_from_file_location("quest_cycle_validator", VALIDATOR)
assert spec is not None and spec.loader is not None
module = importlib.util.module_from_spec(spec)
spec.loader.exec_module(module)


def require(condition: bool, message: str) -> None:
    if not condition:
        raise AssertionError(message)


def main() -> int:
    require(module.find_cycle({"questlog:a": set()}) is None, "single node should be acyclic")
    require(
        module.find_cycle({"questlog:a": {"questlog:external"}}) is None,
        "external target should not be treated as an in-repository cycle",
    )

    self_cycle = module.find_cycle({"questlog:a": {"questlog:a"}})
    require(self_cycle == ["questlog:a", "questlog:a"], f"unexpected self-cycle path: {self_cycle}")

    two_cycle = module.find_cycle(
        {
            "questlog:a": {"questlog:b"},
            "questlog:b": {"questlog:a"},
        }
    )
    require(two_cycle is not None, "two-node cycle was not detected")
    require(two_cycle[0] == two_cycle[-1], f"cycle path should close: {two_cycle}")
    require(set(two_cycle[:-1]) == {"questlog:a", "questlog:b"}, f"wrong cycle members: {two_cycle}")

    acyclic = module.find_cycle(
        {
            "questlog:a": {"questlog:b"},
            "questlog:b": {"questlog:c"},
            "questlog:c": set(),
        }
    )
    require(acyclic is None, f"acyclic chain was rejected: {acyclic}")

    found: set[str] = set()
    module.collect_dependencies(
        {
            "type": "questlog:and",
            "objectives": [
                {"type": "questlog:quest_complete", "quest": "alpha"},
                {
                    "type": "questlog:not",
                    "objective": {"type": "questlog:quest_complete", "quest": "other:beta"},
                },
            ],
        },
        found,
    )
    require(found == {"questlog:alpha", "other:beta"}, f"nested dependency collection failed: {found}")

    print("Quest dependency cycle validator self-test passed.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
