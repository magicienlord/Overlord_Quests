# Continuation Instructions

## Immediate restart

1. Read `README_FIRST.md`.
2. Treat `gnarl-bootstrap` at `e0a0c8667e389f735b8c138227cf62cc889e28c3` as the implementation checkpoint captured by this handoff, but re-read live refs before future writes because the repository may move after packaging.
3. Read `V5_DECISION_LEDGER.md` before proposing or drafting anything.
4. Ask the Overlord the three questions in `NEXT_QUESTIONS.md`.
5. Do **not** write the affected V5 civilization sections until those answers are received.

## V5 authoring mode

The next conversation should continue building the V5 campaign authority document, not resume production implementation immediately.

Rules:

- no quest names yet;
- objective/structure authoring only;
- fresh single-player campaign assumptions;
- no multiplayer/backfill/recovery design requirement;
- no repeated quest-reminder UI;
- unused meaningful native advancement content may become sparse NPC Ramblings;
- resolve technical questions from JAR/source where possible;
- ask the Overlord before establishing any missing lore, player-facing progression intent, terminal outcome, branch meaning, or new canon.

## Do not trust current production quest content as final V5

Current `Overlord_Quests` production source is repository-qualified but predates the latest V5 interview decisions. Known mismatches are documented in `LIVE_REPOSITORY_STATE.md`.

Examples that must be reconciled **later** after V5 approval:

- old generalized Gnarl reminder behavior vs new anti-reminder UI rule;
- seven-room Tower gate vs new full initial functional set;
- old Red/Green/Blue recovery anchors vs new authored feats;
- 10-civilization implementation vs 11-civilization V5 roster including Myrmex;
- Church of Sin dedicated questline vs later no-dedicated-line decision;
- Bumblezone full native-progression treatment vs later compact arc;
- old Tower completion visibility/structure vs silent aggregate readiness;
- alpha.3 NightWalker assumptions vs later alpha.4/live NightWalker state.

Do not repair any of these as part of handoff packaging.

## Source hierarchy

1. explicit latest Overlord decision;
2. current project/lore authority files, with later explicit decisions overriding stale entries;
3. live mod/source/JAR technical truth;
4. proposal only where requested.

`magicienlord/Overlord_Lore_and_Canon` remains READ-ONLY.

## Important source files already checked

Read-only lore authority previously used heavily includes:

- `reference/39_REIGN_QUEST_AUTHORITY_AND_INTENTIONAL_DISCRETION.md`
- `reference/34_REIGN_TOWER_RESTORATION_DECISIONS.md`
- `reference/37_REIGN_PERSONAL_MOD_SIDEQUEST_DECISIONS.md`
- `reference/38_REIGN_MINION_TYPE_UNLOCK_ANCHORS.md`
- `reference/31_REIGN_RUNTIME_AND_ENDGAME_DECISIONS.md`
- `reference/29_REIGN_CAMPAIGN_STRUCTURE_DECISIONS.md`
- `reference/30_REIGN_CAMPAIGN_PROGRESSION_CONSTRAINTS.md`
- `reference/36_REIGN_MOD_QUESTLINE_ASSIGNMENTS_FINAL.md`
- `reference/28_REIGN_RELIGION_AND_INFERNAL_ABYSS_DECISIONS.md`
- `reference/13_OVERLORD_REIGN_ADOPTION_LEDGER.md`

Some of those files contain stale statements superseded by `V5_DECISION_LEDGER.md`; do not blindly copy them.

## Live Questlog facts already verified

The live `gnarl-bootstrap` source includes `AndObjective`, `OrObjective` and `NotObjective`. `OrObjective` completes when any child objective is complete. Use this for the planned Iron's Spells and Eidolon single-quest two-path mastery closers.

Questlog still has no generic entity-interaction objective. Physical provider interaction belongs to the provider framework; visible objectives should be concrete tracked actions.

## Implementation after V5

Only after the V5 authority is complete/approved should production reconciliation begin.

At that point:

- re-read the live `gnarl-bootstrap` head;
- inspect whether another implementation branch superseded it;
- do not modify `main` unless the repository workflow/Overlord explicitly calls for it;
- preserve source-backed external integrations (Minions, NightWalker, Depths, provider framework);
- update project files/changelog alongside implementation changes;
- validate locally where possible before spending GitHub Actions minutes;
- establish exact-head CI evidence for any production change.

## Handoff packaging boundary

This branch contains preservation records only. Do not treat it as a production implementation branch and do not merge it merely to “carry forward” the decisions. Use the records as restart context, then work from the actual live implementation branch when implementation eventually resumes.
