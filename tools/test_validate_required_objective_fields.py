#!/usr/bin/env python3
"""Self-test the narrow runtime quest-contract validator."""

from __future__ import annotations

import importlib.util
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
VALIDATOR = ROOT / "tools" / "validate_required_objective_fields.py"

spec = importlib.util.spec_from_file_location("runtime_contract_validator", VALIDATOR)
assert spec is not None and spec.loader is not None
module = importlib.util.module_from_spec(spec)
spec.loader.exec_module(module)


def objective_errors(entry: dict) -> list[str]:
    errors: list[str] = []
    module.validate_objective(entry, "objective", errors)
    return errors


def reward_errors(entry: dict) -> list[str]:
    errors: list[str] = []
    module.validate_reward(entry, "reward", errors)
    return errors


def require_valid(errors: list[str], label: str) -> None:
    if errors:
        raise AssertionError(f"{label} unexpectedly failed: {errors}")


def require_invalid(errors: list[str], label: str) -> None:
    if not errors:
        raise AssertionError(f"{label} unexpectedly passed")


def main() -> int:
    require_valid(
        objective_errors({"type": "questlog:item_equip", "slot": "mainhand"}),
        "valid equipment slot",
    )
    require_invalid(
        objective_errors({"type": "questlog:item_equip", "slot": "hands"}),
        "invalid equipment slot",
    )

    require_valid(
        objective_errors({"type": "questlog:entity_approach", "range": 4}),
        "positive entity approach range",
    )
    require_invalid(
        objective_errors({"type": "questlog:entity_approach", "range": 0}),
        "zero entity approach range",
    )

    require_valid(
        objective_errors({"type": "questlog:visit_position", "dimension": "minecraft:overworld"}),
        "valid visit-position dimension",
    )
    require_invalid(
        objective_errors({"type": "questlog:visit_position", "dimension": "Over World"}),
        "invalid visit-position dimension",
    )

    require_valid(
        objective_errors({"type": "questlog:minion_unlocked", "slot": "red", "required_amount": 1}),
        "valid Minion owner-state prerequisite",
    )
    require_invalid(
        objective_errors({"type": "questlog:minion_unlocked", "slot": "brown", "required_amount": 1}),
        "Brown must not use later-tier Minion owner-state prerequisite",
    )
    require_invalid(
        objective_errors({"type": "questlog:minion_unlocked", "slot": "green", "required_amount": 2}),
        "Minion owner-state prerequisite amount",
    )

    require_valid(
        reward_errors({"type": "questlog:command", "command": "say test", "permission_level": 2}),
        "valid command reward",
    )
    require_invalid(
        reward_errors({"type": "questlog:command", "command": "", "permission_level": 2}),
        "empty command reward",
    )
    require_invalid(
        reward_errors({"type": "questlog:command", "command": "say test", "permission_level": 5}),
        "overpowered command reward permission",
    )

    require_valid(
        reward_errors({"type": "questlog:unlock_minion", "slot": "blue", "auto_claim": True}),
        "valid Minion unlock reward",
    )
    require_invalid(
        reward_errors({"type": "questlog:unlock_minion", "slot": "brown", "auto_claim": True}),
        "Brown Minion unlock reward",
    )
    require_invalid(
        reward_errors({"type": "questlog:unlock_minion", "slot": "red", "auto_claim": False}),
        "manual Minion unlock reward",
    )

    require_valid(
        reward_errors({"type": "questlog:choice", "pick_count": module.MAX_CHOICE_SELECTIONS, "choices": []}),
        "protocol-maximum choice pick count",
    )
    require_invalid(
        reward_errors({"type": "questlog:choice", "pick_count": module.MAX_CHOICE_SELECTIONS + 1, "choices": []}),
        "choice pick count above protocol maximum",
    )

    nested_errors = objective_errors(
        {
            "type": "questlog:and",
            "objectives": [{"type": "questlog:item_equip", "slot": "invalid"}],
        }
    )
    require_invalid(nested_errors, "nested specialized objective")

    print("Runtime quest-contract validator self-test passed.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
