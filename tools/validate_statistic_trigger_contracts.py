#!/usr/bin/env python3
"""Guard trigger-relative semantics for non-retroactive custom statistics."""
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
OBJECTIVE = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/Objective.java"
STATISTIC = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/quests/objectives/misc/StatisticObjective.java"
MANAGER = ROOT / "common/src/main/java/org/infernalstudios/questlog/core/QuestManager.java"
DIRECT = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions/quests/campaign/opening/make_an_impression.json"


def collect_errors() -> list[str]:
    errors: list[str] = []

    def require(path: Path, fragment: str, label: str) -> None:
        text = path.read_text(encoding="utf-8")
        if fragment not in text:
            errors.append(f"{path.relative_to(ROOT)}: missing {label}")

    require(OBJECTIVE, "protected boolean isPartOfPrerequisites()", "read-only prerequisite boundary")
    require(OBJECTIVE, "child.onQuestTriggered();", "recursive trigger-hook propagation")

    require(STATISTIC, "private boolean baselineCaptured = false", "persisted non-retroactive baseline state")
    require(STATISTIC, "public void onQuestTriggered()", "exact trigger-boundary initialization hook")
    require(STATISTIC, "!this.isPartOfPrerequisites() && !this.baselineCaptured", "post-trigger objective baseline guard")
    require(STATISTIC, "this.captureBaseline();", "trigger-time statistic baseline capture")
    require(STATISTIC, 'data.putBoolean("baselineCaptured", this.baselineCaptured)', "baseline persistence")
    require(STATISTIC, 'data.contains("baselineCaptured")', "new baseline-state reload")
    require(STATISTIC, 'data.contains("statAtStart")', "legacy baseline migration")
    require(STATISTIC, "this.getParent().hasSentTrigger", "legacy locked-objective migration boundary")
    require(STATISTIC, "public void forceSetUnits(int units)", "reset-aware statistic objective")
    require(STATISTIC, "this.baselineCaptured = false", "baseline reset")

    require(MANAGER, "boolean newlyTriggered = !quest.hasSentTrigger && quest.isTriggered();", "single trigger-transition decision")
    require(MANAGER, "quest.objectives.forEach(objective -> objective.onQuestTriggered());", "objective trigger initialization")

    manager = MANAGER.read_text(encoding="utf-8")
    hook = manager.find("quest.objectives.forEach(objective -> objective.onQuestTriggered());")
    snapshot = manager.find("CompoundTag data = this.getQuest(id).serialize();")
    if hook < 0 or snapshot < 0 or hook > snapshot:
        errors.append("QuestManager.java: trigger initialization must run before the synchronized quest snapshot")

    require(DIRECT, '"stat": "minecraft:mob_kills"', "production direct-action statistic")
    require(DIRECT, '"retroactive": false', "production direct-action non-retroactive contract")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Statistic trigger contract validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("OVERLORD non-retroactive statistic trigger contracts: PASS")
    print("locked objective progress: excluded until exact quest trigger")
    print("legacy premature baselines: discarded for still-locked post-trigger objectives")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
