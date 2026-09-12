#!/usr/bin/env python3
"""Validate authored quest failure-consequence runtime and definition contracts."""
from __future__ import annotations

import json
from pathlib import Path
import sys

import validate_overlord_quest_examples as validator

ROOT = Path(__file__).resolve().parents[1]
QUEST = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/Quest.java"
MANAGER = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/QuestManager.java"
DEFINITION_VALIDATOR = ROOT / "tools/validate_overlord_quest_examples.py"
FIXTURE = ROOT / "examples/questlog/quests/overlord_failure_consequence_dev.json"
DOC = ROOT / "docs/FAILURE_CONSEQUENCES.md"
SELF_TEST_PATH = ROOT / "examples/questlog/quests/failure_consequence_validator_self_test_dev.json"


def collect_errors() -> list[str]:
    errors: list[str] = []

    def require(path: Path, fragment: str, label: str) -> None:
        if not path.exists():
            errors.append(f"{path.relative_to(ROOT)}: missing file")
            return
        text = path.read_text(encoding="utf-8")
        if fragment not in text:
            errors.append(f"{path.relative_to(ROOT)}: missing {label}")

    require(QUEST, "public final List<Reward> failureRewards", "persistent failure reward collection")
    require(QUEST, 'JsonUtils.getOrDefault(definition, "failure_rewards", new JsonArray())', "failure_rewards definition parsing")
    require(QUEST, "if (!reward.isAutoClaim())", "runtime auto-claim enforcement")
    require(QUEST, "public void applyFailureConsequences(ServerPlayer player)", "server failure consequence application method")
    require(QUEST, "|| !this.isTriggered()", "trigger boundary guard")
    require(QUEST, "|| !this.isFailed()", "failed-state guard")
    require(QUEST, "this.applyingFailureConsequences", "failure consequence re-entry guard")
    require(QUEST, "this.failureRewards.forEach(Reward::revokeReward)", "administrative reset of consequence application bits")
    require(QUEST, 'data.put(\n                "failure_rewards"', "initial failure consequence persistence")
    require(QUEST, 'data.getList("failure_rewards", Tag.TAG_COMPOUND)', "failure consequence reload")
    require(QUEST, 'tag.put("failure_rewards", Util.toNbtList(this.failureRewards, Reward::serialize))', "failure consequence serialization")

    require(MANAGER, "quest.applyFailureConsequences(serverPlayer)", "authoritative server sync application boundary")

    require(DEFINITION_VALIDATOR, "def validate_failure_rewards", "failure consequence definition validation")
    require(DEFINITION_VALIDATOR, "must be true for an automatic failure consequence", "auto-claim schema requirement")
    require(DEFINITION_VALIDATOR, "cannot be a choice reward", "choice consequence rejection")

    require(FIXTURE, '"failure_rewards"', "development failure consequence fixture")
    require(FIXTURE, '"fact": "questlog:dev_failure_consequence"', "synthetic failure fact")
    require(FIXTURE, '"auto_claim": true', "automatic fixture consequence")

    require(DOC, "reference/27_REIGN_QUEST_ARCHITECTURE_DECISIONS.md", "quest architecture authority link")
    require(DOC, "A reset is not a world rollback", "reset boundary")
    require(DOC, "only when", "server failure application boundary documentation")

    try:
        fixture_data = json.loads(FIXTURE.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{FIXTURE.relative_to(ROOT)}: unreadable JSON: {exc}")
    else:
        fixture_errors: list[str] = []
        validator.validate_failure_rewards(fixture_data, FIXTURE, fixture_errors)
        if fixture_errors:
            errors.extend(fixture_errors)

    valid_errors: list[str] = []
    validator.validate_failure_rewards({
        "failure_rewards": [{
            "type": "questlog:set_fact",
            "fact": "questlog:dev_failure_valid",
            "auto_claim": True,
        }]
    }, SELF_TEST_PATH, valid_errors)
    if valid_errors:
        errors.append(f"valid failure consequence self-test unexpectedly failed: {valid_errors}")

    missing_auto_errors: list[str] = []
    validator.validate_failure_rewards({
        "failure_rewards": [{
            "type": "questlog:set_fact",
            "fact": "questlog:dev_failure_missing_auto",
        }]
    }, SELF_TEST_PATH, missing_auto_errors)
    if not any("auto_claim" in error and "must be true" in error for error in missing_auto_errors):
        errors.append("failure consequence validator did not reject missing auto_claim=true")

    choice_errors: list[str] = []
    validator.validate_failure_rewards({
        "failure_rewards": [{
            "type": "questlog:choice",
            "auto_claim": True,
            "pick_count": 1,
            "choices": [{
                "type": "questlog:set_fact",
                "fact": "questlog:dev_failure_choice",
            }],
        }]
    }, SELF_TEST_PATH, choice_errors)
    if not any("cannot be a choice reward" in error for error in choice_errors):
        errors.append("failure consequence validator did not reject choice rewards")

    shape_errors: list[str] = []
    validator.validate_failure_rewards({"failure_rewards": {}}, SELF_TEST_PATH, shape_errors)
    if not any("must be a list" in error for error in shape_errors):
        errors.append("failure consequence validator did not reject non-list failure_rewards")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Failure consequence contract validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("OVERLORD quest failure consequence contracts: PASS")
    print("failure outputs: explicit, automatic, persistent")
    print("locked-quest consequence writes: blocked")
    print("choice reward failure outputs: rejected")
    print("admin reset: progress reset only, not world rollback")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
