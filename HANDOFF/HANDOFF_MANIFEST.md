# Handoff Manifest

Date: 2026-09-16
Repository: `magicienlord/Overlord_Quests`
Preservation branch: `conversation-handoff-2026-09-16-e0a0c866`
Exact implementation base: `e0a0c8667e389f735b8c138227cf62cc889e28c3`

## Preservation files

This branch intentionally adds only the following handoff records:

1. `HANDOFF/README_FIRST.md`
2. `HANDOFF/LIVE_REPOSITORY_STATE.md`
3. `HANDOFF/V5_DECISION_LEDGER.md`
4. `HANDOFF/NEXT_QUESTIONS.md`
5. `HANDOFF/CONTINUATION_INSTRUCTIONS.md`
6. `HANDOFF/RESTART_POINT.txt`
7. `HANDOFF/HANDOFF_MANIFEST.md`

## Content coverage

The package preserves:

- the exact live implementation checkpoint discovered after the rollback warning;
- the fact that `main` and the September 14 preservation checkpoint are stale relative to `gnarl-bootstrap`;
- exact-head CI/check evidence without re-running Actions;
- all material V5 decisions established during the current design interview;
- known divergences between current production source and newer V5 authority;
- the immediate unresolved civilization decision block supplied by the Overlord as the rollback continuation point;
- restart rules for continuing V5 without inventing missing design/canon;
- the prohibition on writing to the read-only lore repository.

## Boundary guarantee

No production implementation is intended in this preservation branch. The branch must compare against `e0a0c866...` as `HANDOFF/**` additions only.

The packaging pass must not alter:

- Java source;
- quest/resource definitions;
- configs;
- workflows;
- production documentation;
- gameplay assets;
- external integration code;
- lore authority content.

The comparison should be verified after the final handoff commit.
