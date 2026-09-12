#!/usr/bin/env python3
"""Validate explicit scoreboard-tag entity matching and target-death tracking."""
from __future__ import annotations

import json
from pathlib import Path
import sys

import validate_overlord_quest_examples as validator

ROOT = Path(__file__).resolve().parents[1]
MATCHER = ROOT / "common/src/main/java/org/infernalstudios/questlog/util/EntityMatcher.java"
OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/entity/EntityDiedObjective.java"
REGISTRY = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/QuestObjectiveRegistry.java"
DEFINITION_VALIDATOR = ROOT / "tools/validate_overlord_quest_examples.py"
FIXTURE = ROOT / "examples/questlog/quests/overlord_anchor_death_dev.json"
DOC = ROOT / "docs/ENTITY_DEATH_TRACKING.md"
SELF_TEST_PATH = ROOT / "examples/questlog/quests/entity_death_tracking_validator_self_test_dev.json"


def collect_errors() -> list[str]:
    errors: list[str] = []

    def require(path: Path, fragment: str, label: str) -> None:
        if not path.exists():
            errors.append(f"{path.relative_to(ROOT)}: missing file")
            return
        text = path.read_text(encoding="utf-8")
        if fragment not in text:
            errors.append(f"{path.relative_to(ROOT)}: missing {label}")

    require(MATCHER, "private final String scoreboardTag", "scoreboard-tag matcher state")
    require(MATCHER, 'definition.has("scoreboard_tag")', "top-level scoreboard-tag parsing")
    require(MATCHER, 'entityObj.has("scoreboard_tag")', "nested scoreboard-tag parsing")
    require(MATCHER, "entity.getTags().contains(this.scoreboardTag)", "exact entity scoreboard-tag predicate")

    require(OBJECTIVE, "class EntityDiedObjective", "target entity death objective")
    require(OBJECTIVE, "Triggers.EVENTS.addListener(this::onEntityDeath)", "death-event listener registration")
    require(OBJECTIVE, "!this.isActiveQuestInstance()", "retained-listener lifecycle guard")
    require(OBJECTIVE, "this.test(event.entity)", "victim entity matcher")
    require(OBJECTIVE, "this.setUnits(this.getUnits() + 1)", "objective progression")

    require(REGISTRY, 'new ResourceLocation("questlog", "entity_died")', "entity_died registry entry")
    require(REGISTRY, "EntityDiedObjective::new", "entity_died objective factory")
    require(REGISTRY, 'new EditorMetadata("entity", "Target Entity ID:"', "target-death editor metadata")

    require(DEFINITION_VALIDATOR, '"questlog:entity_died"', "entity_died validator registration")
    require(DEFINITION_VALIDATOR, 'core.ENTITY_OBJECTIVES.add("questlog:entity_died")', "entity matcher validation inclusion")
    require(DEFINITION_VALIDATOR, "def validate_scoreboard_tag", "scoreboard-tag schema validation")
    require(DEFINITION_VALIDATOR, "requires an explicit entity matcher selector", "wildcard death rejection")

    require(FIXTURE, '"type": "questlog:entity_died"', "development target-death objective")
    require(FIXTURE, '"scoreboard_tag": "oq_anchor_death_target"', "development anchor identity tag")
    require(FIXTURE, '"fact": "questlog:dev_anchor_died"', "development death consequence fact")

    require(DOC, "reference/27_REIGN_QUEST_ARCHITECTURE_DECISIONS.md", "quest architecture authority link")
    require(DOC, "The damage source is irrelevant", "non-player-attributed death semantics")
    require(DOC, "event-driven, not retrospective", "sequence-break boundary")
    require(DOC, "Do not spawn a duplicate NPC", "no duplicate-target workaround")

    try:
        fixture_data = json.loads(FIXTURE.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{FIXTURE.relative_to(ROOT)}: unreadable JSON: {exc}")
    else:
        fixture_errors: list[str] = []
        validator.validate_objective_entry(
            fixture_data["failures"][0],
            "failures[0]",
            FIXTURE,
            fixture_errors,
        )
        if fixture_errors:
            errors.extend(fixture_errors)

    valid_errors: list[str] = []
    validator.validate_objective_entry({
        "type": "questlog:entity_died",
        "entity": "minecraft:villager",
        "scoreboard_tag": "oq_valid_target",
        "required_amount": 1,
    }, "failures[0]", SELF_TEST_PATH, valid_errors)
    if valid_errors:
        errors.append(f"valid entity_died self-test unexpectedly failed: {valid_errors}")

    wildcard_errors: list[str] = []
    validator.validate_objective_entry({
        "type": "questlog:entity_died",
        "required_amount": 1,
    }, "failures[0]", SELF_TEST_PATH, wildcard_errors)
    if not any("requires an explicit entity matcher selector" in error for error in wildcard_errors):
        errors.append("entity_died validator did not reject a wildcard death matcher")

    empty_tag_errors: list[str] = []
    validator.validate_objective_entry({
        "type": "questlog:entity_died",
        "entity": "minecraft:villager",
        "scoreboard_tag": "",
    }, "failures[0]", SELF_TEST_PATH, empty_tag_errors)
    if not any("scoreboard tag" in error for error in empty_tag_errors):
        errors.append("entity matcher validator did not reject an empty scoreboard_tag")

    nested_tag_errors: list[str] = []
    validator.validate_objective_entry({
        "type": "questlog:entity_died",
        "entity": {
            "id": "minecraft:villager",
            "scoreboard_tag": "oq_nested_target",
        },
    }, "failures[0]", SELF_TEST_PATH, nested_tag_errors)
    if nested_tag_errors:
        errors.append(f"valid nested scoreboard-tag matcher unexpectedly failed: {nested_tag_errors}")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Entity death tracking contract validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("OVERLORD targeted entity death tracking contracts: PASS")
    print("identity scope: explicit entity matcher and optional persistent scoreboard tag")
    print("death attribution: matching victim, independent of damage source")
    print("wildcard entity_died definitions: rejected")
    print("retrospective behavior: intentionally not provided")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
