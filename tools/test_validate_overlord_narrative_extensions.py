#!/usr/bin/env python3
"""Self-tests for OVERLORD narrative and NPC-provider definition extensions."""
from pathlib import Path
import validate_overlord_quest_examples as validator

TEST_PATH = Path(__file__).resolve().parents[1] / "examples/questlog/quests/narrative_validator_self_test_dev.json"


def expect_valid(label, errors):
    if errors:
        raise AssertionError(f"{label} unexpectedly failed: {errors}")


def expect_invalid(label, errors, fragment):
    if not any(fragment in error for error in errors):
        raise AssertionError(f"{label} did not fail for {fragment!r}: {errors}")


def objective(entry):
    errors = []
    validator.validate_objective_entry(entry, "objectives[0]", TEST_PATH, errors)
    return errors


def reward(entry):
    errors = []
    validator.validate_reward_entry(entry, "rewards[0]", TEST_PATH, errors)
    return errors


def provider(value, *, development_fixture=False):
    errors = []
    validator.validate_provider_rule(
        {"provider": value},
        TEST_PATH,
        errors,
        development_fixture=development_fixture,
    )
    return errors


def main():
    expect_valid("disposition objective", objective({
        "type": "questlog:disposition",
        "civilization": "overlord_reign:test_civilization",
        "state": "overlord_reign:neutral",
    }))
    expect_invalid("disposition amount", objective({
        "type": "questlog:disposition",
        "civilization": "overlord_reign:test_civilization",
        "state": "overlord_reign:neutral",
        "required_amount": 2,
    }), "exactly 1")
    expect_invalid("missing disposition state", objective({
        "type": "questlog:disposition",
        "civilization": "overlord_reign:test_civilization",
    }), ".state")

    expect_valid("fact objective", objective({
        "type": "questlog:fact",
        "fact": "overlord_reign:test_fact",
    }))
    expect_invalid("fact amount", objective({
        "type": "questlog:fact",
        "fact": "overlord_reign:test_fact",
        "required_amount": 2,
    }), "exactly 1")
    expect_invalid("missing fact id", objective({
        "type": "questlog:fact",
    }), ".fact")

    expect_valid("set disposition reward", reward({
        "type": "questlog:set_disposition",
        "civilization": "overlord_reign:test_civilization",
        "state": "overlord_reign:dominated",
    }))
    expect_invalid("missing set disposition civilization", reward({
        "type": "questlog:set_disposition",
        "state": "overlord_reign:dominated",
    }), ".civilization")

    expect_valid("set fact reward", reward({
        "type": "questlog:set_fact",
        "fact": "overlord_reign:test_fact",
        "auto_claim": True,
    }))
    expect_invalid("missing set fact id", reward({
        "type": "questlog:set_fact",
    }), ".fact")

    expect_valid("provider entity selector", provider({
        "entity_types": ["minecraft:villager"],
        "turn_in": "same_provider",
        "required_dispositions": {
            "overlord_reign:test_civilization": [
                "overlord_reign:neutral",
                "overlord_reign:dominated",
            ]
        },
    }))
    expect_valid("provider fact gates", provider({
        "entity_types": ["minecraft:villager"],
        "required_facts": ["overlord_reign:gate_open"],
        "forbidden_facts": ["overlord_reign:provider_dead"],
    }))
    expect_invalid("contradictory provider fact gates", provider({
        "entity_types": ["minecraft:villager"],
        "required_facts": ["overlord_reign:gate_open"],
        "forbidden_facts": ["overlord_reign:gate_open"],
    }), "both require and forbid")
    expect_valid("provider tag selector", provider({
        "entity_type_tags": ["forge:villagers"],
        "turn_in": "any_eligible",
    }))
    expect_valid("provider location bounds", provider({
        "entity_types": ["minecraft:villager"],
        "location": {
            "min": [-32, 48, -32],
            "max": [32, 128, 32],
        },
    }))
    expect_invalid("provider location shape", provider({
        "entity_types": ["minecraft:villager"],
        "location": {
            "min": [0, 64],
            "max": [8, 72, 8],
        },
    }), "location.min")
    expect_invalid("provider location integer overflow", provider({
        "entity_types": ["minecraft:villager"],
        "location": {
            "min": [-2147483649, 0, 0],
            "max": [2147483648, 64, 0],
        },
    }), "signed 32-bit")
    expect_valid("provider dialogue", provider({
        "entity_types": ["minecraft:villager"],
        "dialogue": {
            "offer": ["[DEV] Offer line one.", "[DEV] Offer line two."],
            "in_progress": "[DEV] Still working.",
            "ready_to_turn_in": "[DEV] Ready.",
            "failed": "[DEV] Failed.",
        },
    }))
    expect_invalid("provider dialogue unknown phase", provider({
        "entity_types": ["minecraft:villager"],
        "dialogue": {"victory": "[DEV] Unsupported."},
    }), "not a supported dialogue phase")
    expect_invalid("provider dialogue empty line", provider({
        "entity_types": ["minecraft:villager"],
        "dialogue": {"offer": [""]},
    }), "non-empty string")
    expect_invalid("missing provider selector", provider({}), "requires at least one")
    expect_invalid("bad provider turn-in", provider({
        "entity_types": ["minecraft:villager"],
        "turn_in": "somewhere_else",
    }), "turn_in")
    expect_invalid("empty disposition set", provider({
        "entity_types": ["minecraft:villager"],
        "required_dispositions": {"overlord_reign:test_civilization": []},
    }), "non-empty list")

    expect_invalid("unscoped production civilization provider", provider({
        "entity_types": ["minecraft:villager"],
        "civilization": "overlord_reign:test_civilization",
    }), "anchor-scoped")
    expect_valid("tag-scoped production civilization provider", provider({
        "entity_types": ["minecraft:villager"],
        "civilization": "overlord_reign:test_civilization",
        "scoreboard_tags": ["overlord_anchor:test_settlement"],
    }))
    expect_valid("location-scoped production civilization provider", provider({
        "entity_types": ["minecraft:villager"],
        "civilization": "overlord_reign:test_civilization",
        "location": {
            "min": [-16, 48, -16],
            "max": [16, 128, 16],
        },
    }))
    expect_valid("unscoped development civilization provider", provider({
        "entity_types": ["minecraft:villager"],
        "civilization": "questlog:dev_civilization",
    }, development_fixture=True))

    print("OVERLORD narrative/provider validator self-tests: PASS (27 cases)")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
