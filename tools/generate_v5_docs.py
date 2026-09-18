#!/usr/bin/env python3
"""
Regenerate the OVERLORD REIGN V5 campaign documents from V5_CAMPAIGN_DATA.json.

The authority document and its companions are GENERATED. Edit the JSON and re-run
this script; do not hand-edit the markdown, or the two will drift apart — which has
already happened twice and cost a full reconciliation pass each time.

    python3 generate_v5_docs.py V5_CAMPAIGN_DATA.json ./out

Writes:
    V5_DERIVED_QUEST_INVENTORY.md    the 147 quests, one record each
    V5_PROVIDER_QUEST_INVENTORY.md   the 43 provider quests
    V5_RAMBLING_CATALOG.md           the 537 Ramblings by presenter

The authority document (V5_CAMPAIGN_SYSTEM_AUTHORITY.md) additionally contains authored
prose sections 0-7 and 10-12 that are NOT generated. Its generated portions are §8 (quests),
§7.5 (providers), §9 (facts) and §10.2.3 (Ramblings); paste the corresponding output over
those sections, or extend this script if the prose is ever moved into the JSON too.
"""
import json, sys, collections
from pathlib import Path

def quest_record(x, h="###"):
    pu = x.get('presenter_unlock', x.get('presenter'))
    pc = x.get('presenter_complete', x.get('presenter'))
    L = [f"{h} `{x['id']}` — {x['slug']}", "",
         f"- **Role:** {x.get('quest_role','')}",
         f"- **Presenter:** {pu if pu==pc else pu+' (unlock) / '+pc+' (completion)'}",
         f"- **Register vocabulary:** {x.get('register_vocabulary','')}",
         f"- **Activation:** {x['activation']}",
         f"- **Prerequisite:** {x['prerequisite']}",
         f"- **Objective (specification):** {x['objective_spec']}",
         f"- **Order (presenter voice):** {x['order_presenter_voice']}",
         f"- **Completion condition:** {x['completion']}",
         f"- **Unlock popup:** `{x['visual_unlock']}` · `{x['register_unlock']}` — {x['visual_unlock_rationale']}",
         f"- **Completion popup:** `{x['visual_complete']}` · `{x['register_complete']}` — {x['visual_complete_rationale']}",
         f"- **Consequence:** {x['consequence']}",
         f"- **Persistent fact:** {x.get('persistent_fact','NONE')}",
         f"- **Sequence-break handling:** {x.get('sequence_break','')}",
         f"- **Reward:** {x['reward']}"]
    if x.get('legal_states'):
        L.append(f"- **Legal terminal states:** {x['legal_states']}")
    L += [f"- **Source:** {x['source']}", ""]
    return L

def main(src, outdir):
    d = json.load(open(src))
    out = Path(outdir); out.mkdir(parents=True, exist_ok=True)
    Q, P, R = d['quests'], d['provider_quests'], d['ramblings']

    # integrity gate — fail loudly rather than emit a wrong document
    states = set(d['visual_states']) | {'PER-OUTCOME', 'PER-BRANCH'}
    bad = [q['id'] for q in Q if q['visual_unlock'] not in states or q['visual_complete'] not in states]
    assert not bad, f"invalid visual state on {bad}"
    assert all(r['proposed_presenter'] in d['presenters'] for r in R), "unassigned Rambling presenter"
    assert all(q['reward'].startswith('NONE') for q in Q), "a quest carries a reward (Q241)"

    # quests
    L = ["# OVERLORD REIGN V5 — Derived Quest Inventory", "",
         "Status: GENERATED from `V5_CAMPAIGN_DATA.json`. Do not hand-edit.", "",
         f"Visible Questlog quests: **{len(Q)}**. Presenter popups: **{len(Q)*2}**. "
         f"Provider quests: **{len(P)}**.", ""]
    cur = None
    for x in Q:
        if x['category'] != cur:
            cur = x['category']; L += ["", f"## {cur}", ""]
        L += quest_record(x)
    (out/'V5_DERIVED_QUEST_INVENTORY.md').write_text("\n".join(L))

    # providers
    L = ["# OVERLORD REIGN V5 — Provider Quest Inventory", "",
         "Status: GENERATED from `V5_CAMPAIGN_DATA.json`. Do not hand-edit.", "",
         f"Total: **{len(P)}** across 11 civilizations. No Questlog entries, no presenter popups (Q243).", ""]
    cur = None
    for p in P:
        if p['civilization'] != cur:
            cur = p['civilization']; L += ["", f"## {cur}", ""]
        L += [f"### `{p['id']}` — {p['role']} / {p['route']}", "",
              f"- **Bound provider:** {p['binding']}",
              f"- **Objective (specification):** {p['objective_spec']}",
              f"- **Order (provider voice):** {p['order_presenter_voice']}",
              f"- **Recorded fact:** `{p['recorded_fact']}`",
              f"- **Turn-in:** {p['turn_in']}",
              f"- **Reward:** {p['reward']}",
              f"- **Source:** {p['source']}", ""]
    (out/'V5_PROVIDER_QUEST_INVENTORY.md').write_text("\n".join(L))

    # ramblings
    L = ["# OVERLORD REIGN V5 — Rambling Catalog", "",
         "Status: GENERATED from `V5_CAMPAIGN_DATA.json`. Do not hand-edit.", "",
         f"**{len(R)}** approved Ramblings. No Rambling uses `STRICKEN` (authority §10.2.3).", "",
         "| Presenter | Ramblings |", "| --- | --- |"]
    c = collections.Counter(r['proposed_presenter'] for r in R)
    for p in d['presenters']:
        L.append(f"| {p} | {c[p]} |")
    for p in d['presenters']:
        rows = sorted([r for r in R if r['proposed_presenter'] == p], key=lambda x: (x['source'], x['adv_id']))
        L += ["", f"## {p} — {len(rows)}", "",
              "| Advancement | Native title | State | Why that state |", "| --- | --- | --- | --- |"]
        for r in rows:
            L.append(f"| `{r['adv_id']}` | {(r['title'] or '').replace('|','/')[:46]} | "
                     f"`{r['rambling_visual']}` | {r['rambling_visual_rationale'].replace('|','/')} |")
    (out/'V5_RAMBLING_CATALOG.md').write_text("\n".join(L))

    print(f"quests {len(Q)} · providers {len(P)} · ramblings {len(R)} · "
          f"popups {len(Q)*2+len(R)} → {out}")

if __name__ == '__main__':
    main(sys.argv[1] if len(sys.argv) > 1 else 'V5_CAMPAIGN_DATA.json',
         sys.argv[2] if len(sys.argv) > 2 else '.')
