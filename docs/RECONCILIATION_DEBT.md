# OVERLORD QUESTS Reconciliation Debt

Status: OPEN - BLOCKS CONTENT-COMPLETE CLAIM

This is the durable engineering debt ledger for `magicienlord/Overlord_Quests`. It does not create OVERLORD REIGN canon. It exists so future Quest Maker iterations cannot infer completion from an older coverage pass while known authority-backed work remains unfinished.

Any older statement that static repository content is complete is superseded while this file is `OPEN`.

## Authority checkpoint

Current read-only lore authority reviewed for this reopening:

```text
repository: magicienlord/Overlord_Lore_and_Canon
branch: main
head: 649bae2fe49f9da210bcf6d7400316f3e4413464
```

The current head postdates the previous Quest reconciliation checkpoint `235845c4985b61cba9c106b2f4c4af8894bb98ae` and explicitly updates `reference/34_REIGN_TOWER_RESTORATION_DECISIONS.md`.

Relevant authority includes:

- `reference/GNARL_WRITING_RULES.md`;
- `reference/27_REIGN_QUEST_ARCHITECTURE_DECISIONS.md`;
- `reference/34_REIGN_TOWER_RESTORATION_DECISIONS.md`;
- `reference/36_REIGN_MOD_QUESTLINE_ASSIGNMENTS_FINAL.md`;
- `reference/38_REIGN_MINION_TYPE_UNLOCK_ANCHORS.md`;
- `reference/39_REIGN_QUEST_AUTHORITY_AND_INTENTIONAL_DISCRETION.md`;
- `reference/44_REIGN_LESTAT_CONTINUITY_AUTHORITY_FINAL.md`;
- `reference/45_REIGN_SILENT_ORDER_AND_LESTAT_RELIGION_CLARIFICATION.md`.

## Confirmed open implementation debt

### 1. Gnarl's Ramblings / generalized commentary layer

Status: OPEN

The existing `SYSTEM_REACTIONS` channel and ordinary quest-specific Gnarl popups are useful partial infrastructure, but they do not by themselves close the broader Gnarl commentary assignment.

`reference/GNARL_WRITING_RULES.md` requires major quests, where useful, to support lifecycle commentary beyond acceptance/completion, including objective clarification, first and repeated reminder variants, new-information/objective updates, warnings, branch framing, success variants, failure or retreat reactions, and post-quest world-state comments. `reference/27_REIGN_QUEST_ARCHITECTURE_DECISIONS.md` also identifies Gnarl reactions as an authored persistence consequence where appropriate.

Remaining work must therefore audit and implement the generalized Ramblings surface rather than equating a small set of one-shot system reactions with complete Gnarl coverage. Popup-only/sparse-commentary mod assignments must also be deliberately reconciled against the actual reaction catalogue instead of being assumed complete merely because the reaction mechanism exists.

This debt is closed only when the generalized implementation, its authored coverage, and its validation contract are present and source-reconciled.

### 2. Biomancy Tower activation

Status: OPEN

At lore head `649bae2fe49f9da210bcf6d7400316f3e4413464`, `reference/34_REIGN_TOWER_RESTORATION_DECISIONS.md` explicitly includes **Biomancy** in the selected magical Tower functions whose physical activation belongs to Tower Restoration.

The current Quest repository predates that authority change. It has a dedicated Biomancy magic arc but no Biomancy Tower activation, and the previous reconciliation validator actively forbade one. That prohibition is stale.

Remaining work is to add a restrained Biomancy room/facility activation milestone under Tower Restoration while keeping deeper Biomancy progression in the dedicated Biomancy arc. Formal Tower completion remains governed by the core operational completion boundary unless later authority explicitly changes that gate.

### 3. Source-authority cleanup and integration reconciliation

Status: OPEN

The following are documentation/validation errors or integration assumptions that must be removed or checked. They are not new lore blockers:

- Brown/Red/Green/Blue recovery anchors are authored Quest progression, not temporary technical `Hive proxies`. `reference/38_REIGN_MINION_TYPE_UNLOCK_ANCHORS.md` assigns the unlock narrative to Overlord Quests. The absence of a physical gameplay Hive object is not a missing dependency.
- Lestat is a popup/contextual Quest character for this implementation boundary, not a physical final-world NPC whose coordinates/tagging must block completion. Any Quest code or validator that models him as requiring physical provider placement must be reconciled.
- Villager and Illager civilization quest providers are resolved through local role/provider logic around the relevant quest context. Fixed final-world coordinate placement is not a completion requirement. Runtime/validator behavior must be checked against that local-role model rather than reintroducing fixed placement debt.

### 4. Runtime/full-instance qualification

Status: OPEN AFTER IMPLEMENTATION CLOSURE

Standalone CI does not substitute for a launched assembled-instance qualification. Fathoms presentation/native Historian handoff, optional-mod interaction paths, Gnarl presentation, and the final reconciled quest flow require full-instance/manual qualification after the source-authority and implementation debts above are closed.

Do not produce or present another test JAR as a final handoff merely because Forge compilation succeeds while this debt ledger remains open.

## Explicit non-debts

The following must not be reintroduced as blockers unless new authority changes them:

- implementing physical Brown/Red/Green/Blue Hive objects;
- assigning Lestat a permanent physical Dark Tower entity placement;
- hard-coding final coordinates for Villager or Illager civilization providers;
- creating dedicated questlines for mods whose final assignment is intentionally ambient, absorbed, systemic, popup-only, provider-support, Tower-substrate, quest-location, decorative, or no-treatment.

## Closure rule

`Status: OPEN` may become `Status: CLOSED` only after:

1. Gnarl's Ramblings has an implemented and validated generalized commentary contract with deliberate assigned coverage;
2. Biomancy has the authority-required Tower activation milestone without absorbing deeper Biomancy progression;
3. stale Hive/Lestat/civilization-placement assumptions are removed from active documentation and validators, with implementation corrected where necessary;
4. the final assignment reconciliation validator no longer encodes superseded authority;
5. exact-head repository CI is green;
6. required full-instance/manual qualification is recorded separately and truthfully.

Until then, `docs/FINAL_ASSIGNMENT_RECONCILIATION.md` must not claim content completion.
