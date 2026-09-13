# OVERLORD QUESTS Conversation Handoff

Date: 2026-09-13
Repository: `magicienlord/Overlord_Quests`
Implementation branch: `gnarl-bootstrap`
Preserved implementation checkpoint: `c0faf694b969e94721fc56edc605496221f456b9`
Checkpoint message: `Make civilization validation packaging branch-local`
Preservation branch: `conversation-handoff-2026-09-13-c0faf69`

## Read this first

This handoff was created after a conversation rollback. The live GitHub repository was re-read before packaging and was treated as authoritative. Do not reconstruct state from the rolled-back conversation when it conflicts with the repository.

The implementation branch was not modified for this handoff. The preservation branch starts exactly at `c0faf694b969e94721fc56edc605496221f456b9`. Handoff packaging may add only files under `HANDOFF/` plus `.github/workflows/conversation-handoff.yml` on the preservation branch. No gameplay, quest definition, Java implementation, resource, configuration, or production documentation file may be changed by packaging.

## Current validated checkpoint

The preserved checkpoint is green after GitHub Actions runner-allocation retries. The initial failed attempts terminated before any workflow steps ran. Subsequent attempts succeeded on the same source commit.

Key exact-head evidence:

- Build Forge 1.20.1 #698, run `34768622343`: success
- Dedicated Server Smoke #57, run `34768622452`: success
- Client Bootstrap Smoke #18, run `34768622344`: success
- Narrative State Persistence Smoke #13, run `34768622393`: success
- Quest Anchor Protection Smoke #8, run `34768622351`: success
- Illager Campaign Contract #33, run `34768622407`: success
- Ending Contract #23, run `34768622360`: success
- Optional Objectives Contract #21, run `34768622453`: success
- Civilization Validation Kit #1, run `34768622380`: success

The exact Forge build artifact is `overlord-quests-forge-1.20.1`, artifact ID `10322365804`, artifact SHA-256 `54f41541499a14e05194d615016c5d0f05aeff390fabad060360f2fbe915a20a`.

The exact civilization full-instance validation artifact is `overlord-quests-civilization-validation-kit`, artifact ID `10321428155`, artifact SHA-256 `b8c1b2886457291661a27031a4cdc5712d1eb301170caec02bb9611fedd58a83`.

## Restart rule

A future conversation must first re-fetch the live `gnarl-bootstrap` branch. If it has advanced beyond this checkpoint, the newer live repository is implementation authority. Use this preservation branch only as a continuity and recovery record unless explicitly instructed otherwise.

Read next:

1. `HANDOFF/NEXT_CONVERSATION_HANDOFF.md`
2. `HANDOFF/VALIDATION_STATE.md`
3. `HANDOFF/RECENT_WORK_LEDGER.md`
4. `HANDOFF/RESTART_POINT.txt`
5. `HANDOFF/HANDOFF_MANIFEST.json`
