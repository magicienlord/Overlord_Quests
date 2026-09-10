#!/usr/bin/env python3
"""Self-tests for OVERLORD QUESTS definition validation.

These tests exercise repository policy and the source-derived Questlog schema checks
without creating production quest content.
"""

from __future__ import annotations

from pathlib import Path

import validate_overlord_quest_examples as validator

TEST_PATH = Path(__file__).resolve().parents[1] / "examples" / "questlog" / "quests" / "validator_self_test_dev.json"


def objective_errors(entry: dict) -> list[str]:
    errors: list[str] = []
    validator.validate_objective_entry(entry, "objectives[0]", TEST_PATH, errors)
    return errors


def reward_errors(entry: dict) -> list[str]:
    errors: list[str] = []
    validator.validate_reward_entry(entry, "rewards[0]", TEST_PATH, errors)
    return errors


def expect_valid(label: str, errors: list[str]) -> None:
    if errors:
        raise AssertionError(f"{label} unexpectedly failed: {errors}")


def expect_invalid(label: str, errors: list[str], fragment: str) -> None:
    if not errors:
        raise AssertionError(f"{label} unexpectedly passed")
    if not any(fragment in error for error in errors):
        raise AssertionError(f"{label} failed for the wrong reason: {errors}")


def main() -> int:
    expect_valid(
        "item tag matcher",
        objective_errors({
            "type": "questlog:item_obtain",
            "item": "#forge:ingots/iron",
            "required_amount": 2,
        }),
    )

    expect_valid(
        "entity tag matcher",
        objective_errors({
            "type": "questlog:entity_kill",
            "entity": "#minecraft:skeletons",
        }),
    )

    expect_valid(
        "custom extension objective",
        objective_errors({
            "type": "overlord_quests:future_bridge",
            "arbitrary_extension_payload": True,
        }),
    )

    expect_invalid(
        "unknown built-in objective",
        objective_errors({"type": "questlog:not_a_real_objective"}),
        "not a registered Questlog objective",
    )

    expect_invalid(
        "missing block predicate",
        objective_errors({"type": "questlog:block_mine"}),
        ".block' is required",
    )

    expect_invalid(
        "nested invalid objective",
        objective_errors({
            "type": "questlog:and",
            "objectives": [
                {"type": "questlog:entity_approach", "entity": "minecraft:villager"}
            ],
        }),
        ".range' must be an integer >= 1",
    )

    expect_valid(
        "position bounds array",
        objective_errors({
            "type": "questlog:visit_position",
            "bounds": [-10, 60, -10, 10, 80, 10],
        }),
    )

    expect_invalid(
        "malformed position bounds",
        objective_errors({
            "type": "questlog:visit_position",
            "bounds": [0, 64, 0, 5],
        }),
        "coordinate array must contain exactly 3 or 6 integers",
    )

    expect_valid(
        "nested choice reward",
        reward_errors({
            "type": "questlog:choice",
            "pick_count": 1,
            "choices": [
                {"type": "questlog:item", "item": "minecraft:gold_ingot", "count": 2},
                {"type": "questlog:experience", "experience": 50},
            ],
        }),
    )

    expect_invalid(
        "impossible choice reward",
        reward_errors({
            "type": "questlog:choice",
            "pick_count": 2,
            "choices": [
                {"type": "questlog:item", "item": "minecraft:gold_ingot"}
            ],
        }),
        "pick_count' cannot exceed the number of choices",
    )

    expect_invalid(
        "missing command reward command",
        reward_errors({"type": "questlog:command"}),
        ".command' must be a non-empty string",
    )

    expect_invalid(
        "unknown built-in reward",
        reward_errors({"type": "questlog:not_a_real_reward"}),
        "not a registered Questlog reward",
    )

    print("OVERLORD QUESTS validator self-tests: PASS (12 cases)")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
