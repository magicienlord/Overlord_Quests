#!/usr/bin/env python3
"""Validate the finite Lost Castle REIGN expedition."""
from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUEST_ROOT = DEFS / "quests/campaign/adventures/lost_castle"
INDEX = DEFS / "index.json"
FILES = ["a_castle_off_the_map.json", "break_the_lost_court.json", "nothing_left_to_rule.json"]
FOUNDATION = "questlog:campaign/expansion/the_reign_takes_shape"
FINAL_FACT = "overlord_reign:adventure/lost_castle_expedition_completed"


def load(path: Path, errors: list[str]) -> dict[str, Any]:
    try:
        value = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: {exc}")
        return {}
    if not isinstance(value, dict):
        errors.append(f"{path.relative_to(ROOT)}: root must be an object")
        return {}
    return value


def main() -> int:
    errors: list[str] = []
    index = load(INDEX, errors)
    indexed = set(index.get("quests", [])) if isinstance(index.get("quests"), list) else set()
    docs = [load(QUEST_ROOT / filename, errors) for filename in FILES]

    for filename in FILES:
        rel = f"campaign/adventures/lost_castle/{filename}"
        if rel not in indexed:
            errors.append(f"{rel}: not indexed")

    first = docs[0]
    if not any(isinstance(p, dict) and p.get("type") == "questlog:quest_complete" and p.get("quest") == FOUNDATION for p in first.get("prerequisites", [])):
        errors.append(f"Lost Castle entry must branch from {FOUNDATION}")
    visits = [o for o in first.get("objectives", []) if isinstance(o, dict) and o.get("type") == "questlog:visit_structure_history"]
    if len(visits) != 1 or visits[0].get("structure") != "tlc:lost_castle" or visits[0].get("required_amount") != 1:
        errors.append("Lost Castle entry must use one retrospective tlc:lost_castle structure-history objective")

    assault = docs[1]
    expected_first = "questlog:campaign/adventures/lost_castle/a_castle_off_the_map"
    if not any(isinstance(p, dict) and p.get("type") == "questlog:quest_complete" and p.get("quest") == expected_first for p in assault.get("prerequisites", [])):
        errors.append("Lost Castle assault must unlock only after structure discovery")
    kills = {(o.get("entity"), o.get("required_amount")) for o in assault.get("objectives", []) if isinstance(o, dict) and o.get("type") == "questlog:entity_kill"}
    if kills != {("minecraft:vindicator", 1), ("minecraft:evoker", 1)}:
        errors.append(f"Lost Castle assault must use fresh Vindicator/Evoker kills; actual={sorted(kills)}")
    if any(isinstance(o, dict) and o.get("type") in {"questlog:entity_kill_history", "questlog:entity_kill_stat"} for o in assault.get("objectives", [])):
        errors.append("Lost Castle assault must not accept retrospective global kill history/statistics")

    close = docs[2]
    expected_second = "questlog:campaign/adventures/lost_castle/break_the_lost_court"
    if not any(isinstance(p, dict) and p.get("type") == "questlog:quest_complete" and p.get("quest") == expected_second for p in close.get("prerequisites", [])):
        errors.append("Lost Castle conclusion must follow the assault")
    reads = [o for o in close.get("objectives", []) if isinstance(o, dict) and o.get("type") == "questlog:read"]
    if len(reads) != 1 or reads[0].get("required_amount") != 1:
        errors.append("Lost Castle conclusion must be a single Gnarl read objective")
    if close.get("speaker_id") != "overlord_reign:gnarl":
        errors.append("Lost Castle conclusion must be presented by Gnarl")
    if not any(isinstance(r, dict) and r.get("type") == "questlog:set_fact" and r.get("fact") == FINAL_FACT and r.get("auto_claim") is True for r in close.get("rewards", [])):
        errors.append(f"Lost Castle conclusion must auto-claim {FINAL_FACT}")

    kitchen = load(DEFS / "quests/campaign/tower/magic/open_gluttony_kitchen.json", errors)
    kitchen_text = f"{kitchen.get('description', '')}\n{kitchen.get('description_completed', '')}"
    if "Gristle" not in kitchen_text or "Minion Cook" not in kitchen_text:
        errors.append("Gluttony Kitchen must persist Gristle as the named Minion Cook")

    if errors:
        print("Lost Castle questline contract FAILED:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1
    print("Lost Castle questline contract OK: finite discovery/assault/conclusion arc and Gristle naming are preserved.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
