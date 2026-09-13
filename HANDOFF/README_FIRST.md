# OVERLORD REIGN — Overlord_Quests Conversation Handoff

Created: 2026-09-14

## Preserved implementation checkpoint

Repository: `magicienlord/Overlord_Quests`
Implementation branch: `gnarl-bootstrap`
Preserved implementation commit: `18dd600ddbde3a12b5776d1c9998c5799c7f138d`
Preserved implementation tree: `ea5a75025c09ffd4e5a2faa7dc156e58a4159264`
Commit message: `Make narrative smoke quest-count agnostic`

This handoff was created after a conversation rollback. The live repository was re-read and treated as authoritative. The implementation branch was not modified during packaging. This preservation branch is based on the source commit above and changes only `HANDOFF/*` records plus `.github/workflows/conversation-handoff.yml`.

## Lore authority

Repository: `magicienlord/Overlord_Lore_and_Canon`
Branch: `main`
Observed authority commit: `235845c4985b61cba9c106b2f4c4af8894bb98ae`
Commit message: `Add Silent Order clarification authority`

The lore repository is read-only from this project. Re-fetch it before future quest authoring because it may have advanced after this package was created.

## Exact-head validation state

All 13 GitHub Actions workflows attached to the preserved implementation SHA were completed successfully. No failed or in-progress exact-head workflow was present when this package was assembled.

Key reproducible artifacts, re-read directly from their exact source-head workflow runs during packaging:
- Forge build: run `34788700194`, artifact `10326674468`, name `overlord-quests-forge-1.20.1`, artifact-service digest `sha256:4e208bbf9f84153878e95b3f680783182fe3e8acbee8c19a6671a14cb148e384`.
- Civilization validation kit: run `34788700165`, artifact `10327029218`, name `overlord-quests-civilization-validation-kit`, artifact-service digest `sha256:bd8867fa26a815b54719cc9e13e4b5bd90d925167a083247119205bc2c7076aa`.

See `VALIDATION_STATE.md` for the full exact-head run ledger.

## Important rollback/status warning

`docs/CURRENT_IMPLEMENTATION_STATUS.md` is not exhaustive at this checkpoint and contains stale implementation statements. Do not use it alone to decide what exists. For production quest presence, use `common/src/main/resources/assets/questlog/overlord/definitions/index.json`, focused contract/status documents, exact Git history, and current CI evidence.

In particular, the preserved source already contains:
- the Tower Restoration framework including the Waystones-backed Gates Room and formal restoration completion;
- six core magic questlines;
- Twilight Forest, Cataclysm, Graveyard, Bumblezone, Knight Quest, and Lost Castle authored adventure wrappers;
- Brown bootstrap plus Red → Green → Blue Minion recovery progression;
- the current partial civilization quest coverage;
- the Tower cook's proper name **Gristle**. “Minion Cook” is his role, not his proper name.

Lost Castle was already authored, documented, and placed under CI before this checkpoint. Do not recreate it from stale conversation context.

## Restart rule

The first action in a new conversation must be to re-fetch `gnarl-bootstrap` and `magicienlord/Overlord_Lore_and_Canon@main`. If either live repository is newer, the newer live repository state overrides this preservation package. Do not resume from conversation memory alone.

Recommended read order:
1. `HANDOFF/RESTART_POINT.txt`
2. `HANDOFF/NEXT_CONVERSATION_HANDOFF.md`
3. `HANDOFF/VALIDATION_STATE.md`
4. `HANDOFF/RECENT_WORK_LEDGER.md`
5. `HANDOFF/HANDOFF_MANIFEST.json`
6. live production `definitions/index.json` and relevant focused docs/contracts after re-fetching the live branch.
