#!/usr/bin/env python3
"""Validate the source-backed Rats, Church of Sin, Oddities and Quaver quest assignments."""
from __future__ import annotations
import json
import sys
from pathlib import Path
from typing import Any
ROOT = Path(__file__).resolve().parents[1]
DEFS = ROOT / "common/src/main/resources/assets/questlog/overlord/definitions"
QUESTS = DEFS / "quests"
INDEX = DEFS / "index.json"
FOUNDATION = "questlog:campaign/expansion/the_reign_takes_shape"
FILES = [
    "campaign/adventures/rats/empire_beneath_the_cheese.json",
    "campaign/adventures/rats/wealth_of_a_fallen_empire.json",
    "campaign/adventures/rats/break_the_ratlantean_powers.json",
    "campaign/adventures/church_of_sin/find_the_cursed_cathedral.json",
    "campaign/adventures/church_of_sin/break_the_dead_congregation.json",
    "campaign/adventures/oddities/find_the_orchid_shrine.json",
    "campaign/adventures/oddities/heart_for_the_altar.json",
    "campaign/adventures/oddities/cut_down_the_queen.json",
    "campaign/personnel/quaver/instruments_for_the_court.json",
    "campaign/personnel/quaver/fill_out_the_band.json",
    "campaign/personnel/quaver/first_tower_performance.json",
]
def load(path: Path, errors: list[str]) -> dict[str, Any]:
    try: value = json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path.relative_to(ROOT)}: unreadable JSON: {exc}"); return {}
    if not isinstance(value, dict): errors.append(f"{path.relative_to(ROOT)}: root must be an object"); return {}
    return value
def entries(data: dict[str, Any], field: str, type_id: str) -> list[dict[str, Any]]:
    value=data.get(field,[])
    return [entry for entry in value if isinstance(entry,dict) and entry.get("type")==type_id] if isinstance(value,list) else []
def has_quest_prereq(data: dict[str, Any], quest: str) -> bool:
    return any(entry.get("quest")==quest and entry.get("required_amount",1)==1 for entry in entries(data,"prerequisites","questlog:quest_complete"))
def exact_fact(data: dict[str, Any], fact: str) -> bool:
    rewards=data.get("rewards",[])
    return isinstance(rewards,list) and any(isinstance(entry,dict) and entry.get("type")=="questlog:set_fact" and entry.get("fact")==fact and entry.get("auto_claim") is True for entry in rewards)
def main() -> int:
    errors=[]
    index=load(INDEX,errors); indexed=set(index.get("quests",[])) if isinstance(index.get("quests",[]),list) else set(); docs={rel:load(QUESTS/rel,errors) for rel in FILES}
    for rel in FILES:
        if rel not in indexed: errors.append(f"{rel}: production quest is not indexed")
    rats=[docs[rel] for rel in FILES[:3]]
    if not has_quest_prereq(rats[0],FOUNDATION): errors.append("Rats opening must branch from the reign foundation")
    if not has_quest_prereq(rats[1],"questlog:campaign/adventures/rats/empire_beneath_the_cheese"): errors.append("Rats material stage must follow Ratlantis entry")
    if not has_quest_prereq(rats[2],"questlog:campaign/adventures/rats/wealth_of_a_fallen_empire"): errors.append("Rats capstone must follow the material stage")
    rat_advancements={entry.get("advancement") for data in rats for entry in entries(data,"objectives","questlog:advancement")}
    expected={"ratlantis:ratlantis","ratlantis:gem_of_ratlantis","ratlantis:oratchalcum_ingot","ratlantis:defeat_rat_baron","ratlantis:defeat_dutchrat"}
    if rat_advancements!=expected: errors.append(f"Rats native advancement set drifted: {sorted(x for x in rat_advancements if x)}")
    if not exact_fact(rats[-1],"overlord_reign:adventure/ratlantis_campaign_completed"): errors.append("Rats capstone must persist ratlantis_campaign_completed")
    church=[docs[FILES[3]],docs[FILES[4]]]
    if not has_quest_prereq(church[0],FOUNDATION): errors.append("Church of Sin opening must branch from the reign foundation")
    visits=entries(church[0],"objectives","questlog:visit_structure_history")
    if len(visits)!=1 or visits[0].get("structure")!="church_of_sin:cursedcathedral": errors.append("Church of Sin must discover church_of_sin:cursedcathedral")
    if not has_quest_prereq(church[1],"questlog:campaign/adventures/church_of_sin/find_the_cursed_cathedral"): errors.append("Church combat stage must follow cathedral discovery")
    church_kills=entries(church[1],"objectives","questlog:entity_kill")
    kills={(entry.get("entity"),entry.get("required_amount")) for entry in church_kills}
    if kills!={("minecraft:zombie",4),("minecraft:skeleton",2)}: errors.append("Church combat stage must retain its narrow embedded-undead sample")
    cathedral_predicate={"location":{"structure":"church_of_sin:cursedcathedral"}}
    for entry in church_kills:
        if entry.get("predicate")!=cathedral_predicate:
            errors.append(f"Church combat objective {entry.get('entity')}: kill must be structure-bound to church_of_sin:cursedcathedral")
    if entries(church[1],"objectives","questlog:entity_kill_history"): errors.append("Church defenders must be live post-discovery kills, not retrospective global kill history")
    if not exact_fact(church[-1],"overlord_reign:adventure/church_of_sin_expedition_completed"): errors.append("Church capstone must persist church_of_sin_expedition_completed")
    oddities=[docs[FILES[5]],docs[FILES[6]],docs[FILES[7]]]
    if not has_quest_prereq(oddities[0],FOUNDATION): errors.append("Oddities opening must branch from the reign foundation")
    visits=entries(oddities[0],"objectives","questlog:visit_structure_history")
    if len(visits)!=1 or visits[0].get("structure")!="oddities:orchid_shrine": errors.append("Oddities opening must discover oddities:orchid_shrine")
    if not has_quest_prereq(oddities[1],"questlog:campaign/adventures/oddities/find_the_orchid_shrine"): errors.append("Oddities Heart stage must follow Shrine discovery")
    hearts=entries(oddities[1],"objectives","questlog:item_obtain")
    if len(hearts)!=1 or hearts[0].get("item")!="oddities:orchid_heart": errors.append("Oddities middle stage must obtain the native Orchid Heart")
    if not has_quest_prereq(oddities[2],"questlog:campaign/adventures/oddities/heart_for_the_altar"): errors.append("Oddities Queen stage must follow the Orchid Heart")
    queens=entries(oddities[2],"objectives","questlog:entity_kill_history")
    if len(queens)!=1 or queens[0].get("entity")!="oddities:queen_of_orchid": errors.append("Oddities capstone must prove Queen of Orchid defeat")
    if not exact_fact(oddities[-1],"overlord_reign:adventure/oddities_orchid_queen_defeated"): errors.append("Oddities capstone must persist oddities_orchid_queen_defeated")
    quaver=[docs[FILES[8]],docs[FILES[9]],docs[FILES[10]]]
    if not has_quest_prereq(quaver[0],"questlog:campaign/tower/claim_the_throne"): errors.append("Quaver arc must branch from claiming the throne")
    if has_quest_prereq(quaver[0],"questlog:campaign/tower/restoration_complete"): errors.append("Quaver arc must not require formal Tower Restoration completion")
    if {e.get("item") for e in entries(quaver[0],"objectives","questlog:item_obtain")}!={"immersive_melodies:lute","immersive_melodies:tiny_drum"}: errors.append("Quaver opening instrument pair drifted")
    if {e.get("item") for e in entries(quaver[1],"objectives","questlog:item_obtain")}!={"immersive_melodies:flute","immersive_melodies:trumpet"}: errors.append("Quaver full-band instrument pair drifted")
    if not has_quest_prereq(quaver[1],"questlog:campaign/personnel/quaver/instruments_for_the_court"): errors.append("Quaver band expansion must follow the first instruments")
    if not has_quest_prereq(quaver[2],"questlog:campaign/personnel/quaver/fill_out_the_band"): errors.append("Quaver performance must follow full-band assembly")
    if {e.get("item") for e in entries(quaver[2],"objectives","questlog:item_use")}!={"immersive_melodies:lute","immersive_melodies:tiny_drum"}: errors.append("Quaver finale must require live use of the source-nod string/percussion pair")
    if not exact_fact(quaver[-1],"overlord_reign:tower/quaver_band_established"): errors.append("Quaver finale must persist quaver_band_established")
    if errors:
        print("Remaining assigned questline contract FAILED:",file=sys.stderr)
        for error in errors: print(f" - {error}",file=sys.stderr)
        return 1
    print("Remaining assigned questline contract OK: Rats, Church of Sin, Oddities and Quaver are source-backed and indexed."); return 0
if __name__ == "__main__": raise SystemExit(main())
