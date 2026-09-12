#!/usr/bin/env python3
"""Validate the narrow sequence-break entity-kill history contract."""
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/entity/EntityKillStatObjective.java"
REGISTRY = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/QuestObjectiveRegistry.java"
VALIDATOR = ROOT / "tools/validate_overlord_quest_examples.py"
FIXTURE = ROOT / "examples/questlog/quests/overlord_sequence_break_dev.json"


def collect_errors() -> list[str]:
    errors: list[str] = []

    def require(path: Path, fragment: str, label: str) -> None:
        text = path.read_text(encoding="utf-8")
        if fragment not in text:
            errors.append(f"{path.relative_to(ROOT)}: missing {label}")

    require(OBJECTIVE, "Stats.ENTITY_KILLED.get(this.entityType)", "vanilla persistent entity-kill statistic lookup")
    require(OBJECTIVE, "player.getStats().getValue", "server-player statistics authority")
    require(OBJECTIVE, "this.ticksUntilCheck = 20", "bounded one-second polling cadence")
    require(OBJECTIVE, "!this.isActiveForPlayer(player)", "active quest/player lifecycle guard")
    require(OBJECTIVE, "entityText.startsWith(\"#\")", "entity-tag rejection")
    require(OBJECTIVE, "BuiltInRegistries.ENTITY_TYPE.containsKey(entityId)", "exact registered entity validation")

    require(REGISTRY, 'new ResourceLocation("questlog", "entity_kill_stat")', "entity_kill_stat objective registration")
    require(REGISTRY, 'new EditorMetadata("entity", "Exact Entity ID:"', "exact-entity editor metadata")

    require(VALIDATOR, '"questlog:entity_kill_stat"', "definition validator registration")
    require(VALIDATOR, "core.validate_resource_id(entry[\"entity\"]", "exact resource-id schema validation")

    require(FIXTURE, '"type": "questlog:entity_kill_stat"', "development fixture objective")
    require(FIXTURE, '"entity": "minecraft:zombie"', "development fixture exact entity")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Sequence-break tracking contract validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("OVERLORD sequence-break tracking contracts: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
