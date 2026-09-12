#!/usr/bin/env python3
"""Validate the narrow quest-anchor protection implementation contract."""
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
PROTECTION = ROOT / "common/src/main/java/org/infernalstudios/questlog/overlord/provider/QuestAnchorProtection.java"
FORGE = ROOT / "forge/src/main/java/org/infernalstudios/questlog/QuestlogForgeEventForwarder.java"
DOC = ROOT / "docs/QUEST_ANCHOR_PROTECTION.md"


def collect_errors() -> list[str]:
    errors: list[str] = []

    def require(path: Path, fragment: str, label: str) -> None:
        if not path.exists():
            errors.append(f"{path.relative_to(ROOT)}: missing file")
            return
        text = path.read_text(encoding="utf-8")
        if fragment not in text:
            errors.append(f"{path.relative_to(ROOT)}: missing {label}")

    require(PROTECTION, 'PROTECTED_TAG = "overlord_quest_protected"', "stable opt-in protection tag")
    require(PROTECTION, "entity.getTags().contains(PROTECTED_TAG)", "scoreboard-tag-only protection predicate")
    require(PROTECTION, "entity instanceof Mob mob", "Mob-only persistence bridge")
    require(PROTECTION, "mob.setPersistenceRequired()", "protected anchor persistence requirement")

    require(FORGE, "LivingAttackEvent", "server damage interception event")
    require(FORGE, "EventPriority.HIGHEST", "early quest-anchor damage interception")
    require(FORGE, "QuestAnchorProtection.isProtected(event.getEntity())", "explicit protection predicate")
    require(FORGE, "event.setCanceled(true)", "protected damage cancellation")
    require(FORGE, "EntityJoinLevelEvent", "entity-load persistence hook")
    require(FORGE, "QuestAnchorProtection.applyPersistence(event.getEntity())", "protected Mob persistence application")

    require(DOC, "reference/16_CIVILIZATION_QUEST_ANCHORS.md", "lore authority link")
    require(DOC, "Protection is not a universal rule", "destructive-route boundary")
    require(DOC, "must not be used as a provider identity selector", "anchor identity separation")
    require(DOC, "soft-lock", "sequence-break boundary")

    return errors


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Quest-anchor protection contract validation failed with {len(errors)} error(s):", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("OVERLORD quest-anchor protection contracts: PASS")
    print("protection scope: explicit scoreboard tag only")
    print("protected Mob persistence: enabled")
    print("destructive-route transition ownership: authored integration")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
