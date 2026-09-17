# OVERLORD REIGN V5 Restart Prompt

Use the following as the opening instruction in the next conversation.

---

We are continuing the OVERLORD REIGN V5 campaign-authority work for `magicienlord/Overlord_Quests`.

You are authorized to read and write `magicienlord/Overlord_Quests`.
You may consult `magicienlord/Overlord_Lore_and_Canon` as read-only when lore verification is required.

IMPORTANT: DO NOT USE GITHUB ACTIONS. The Actions budget is exhausted. Repository reads and writes through the GitHub API are fine, but do not trigger workflows.

This is NOT a continuation of the existing production quest implementation. We are finishing a single V5 campaign-system authority document that will later allow `Overlord_Quests` to be implemented without design deviation. Do not start production implementation.

The authoritative working branch at handoff was:

`v5-clean-authority-2026-09-16`

The exact preserved authoring checkpoint was:

`b4535613b838ccb85bf07cb710d86019851bbf43` - `Prepare V5 Decision Batch 04`

The handoff-only preservation branch is:

`conversation-handoff-2026-09-17-b453561`

FIRST, read the complete handoff record:

`HANDOFF/CONVERSATION_HANDOFF_2026-09-17.md`

Then read the complete frozen Batch 04 proposal:

`docs/V5_DECISION_BATCH_04_PREPARATION.md`

Also read the controlling V5 authorities it names, especially:

- `docs/V5_00_OVERLORD_APPROVAL_GOVERNANCE.md`
- `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md`
- `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md`
- `docs/V5_PRESENTER_SYSTEM_AUTHORITY.md`
- `docs/V5_RAMBLING_SYSTEM_AUTHORITY.md`
- `docs/V5_DECISION_BATCH_01_AUTHORITY.md`
- `docs/V5_DECISION_BATCH_02_AUTHORITY.md`
- `docs/V5_DECISION_BATCH_03_AUTHORITY.md`
- `docs/V5_BATCH_03_RECONCILIATION_2026-09-17.md`
- `docs/V5_BATCH_03_SUPPLIED_JAR_AUDIT_2026-09-17.md`

Do not trust stale closure registers over later approved Batch authority.

The immediate task is to FINISH PRODUCING the unfilled workbook:

`OVERLORD_REIGN_V5_Decision_Batch_04.xlsx`

Do not perform another broad authoring pass before producing it. The decision set is already frozen as Q161 through Q208 in `docs/V5_DECISION_BATCH_04_PREPARATION.md`.

The workbook must follow the established Batch 03 workbook conventions and include at minimum:

1. `Decision Batch`
2. `Locked Authority`
3. `Technical Queue`
4. `Rambling Audit`
5. `Instructions`

`Decision Batch` must contain all 48 questions Q161 through Q208 exactly from the frozen preparation record, with decision fields initially unanswered. Summary formulas must show 48 total and 48 unanswered before the Overlord edits it.

CRITICAL RAMBLING RULE:

All popup presenters use the same Rambling model. Quest overlap NEVER disqualifies an advancement or milestone from a Rambling. A relevant milestone may simultaneously support visible quest progression and a presenter Rambling.

Eligible Ramblings include:

- meaningful events where the presenter has worthwhile lore, characterization, specialist commentary, interpretation, humor, approval, criticism, or context;
- significant completion advancements or completion milestones even when the final completion event is narratively thin, because the player deliberately completed a substantial pursuit.

Exclude only trivial repetition, bookkeeping, hidden technical/debug/compatibility plumbing, insignificant incremental noise, or events with neither worthwhile commentary nor completion significance.

The locked popup presenter roster is:

- Gnarl
- Mortis
- Quaver
- Historian
- Lestat
- Gristle
- Grubbison Jr
- Giblet the Sixth

The `Rambling Audit` sheet must expose the full inspected candidate surface for review, not merely a selected subset. The interrupted source pass had a working total of 698 candidate events, provisionally classified as 307 INCLUDE and 391 EXCLUDE. The provisional INCLUDE presenter counts were Gnarl 223, Gristle 28, Mortis 15, Historian 13, Lestat 11, Giblet the Sixth 11, Quaver 5, Grubbison Jr 1. These are proposals, not authority.

If the exact 698-row working table is not recoverable from the previous runtime, reconstruct it from the installed advancement-bearing JARs plus relevant source-owned milestones lacking advancement JSON. Do not silently reduce it to a smaller hand-picked list.

The Overlord must be able to edit individual Rambling Audit rows. Q201 through Q208 are presenter-level catalog decisions, but individual row edits must take precedence.

The previous conversation directly inspected the supplied missing JARs and instance mod archives. Its technical findings are recorded in the handoff and `docs/V5_BATCH_03_SUPPLIED_JAR_AUDIT_2026-09-17.md`. Do not re-ask technical questions already resolved from exact source.

The previous workbook attempt failed only because the spreadsheet/container execution backend repeatedly returned `TransportTimeoutError` before artifact code could execute. The workbook does not currently exist. Do not claim otherwise.

Use the spreadsheet artifact workflow and the spreadsheet skill. Use Batch 03 as the visual and structural template. Validate formulas, inspect sheet structure, and render/visually verify the workbook before delivery.

Do not update V5 authority merely because the workbook has been generated. The workbook is PROPOSAL ONLY until the Overlord returns decisions.

When the Overlord later uploads the filled Batch 04 workbook, read the ENTIRE workbook. Do not merely inspect Approve/Modify/Reject. The Overlord may manually change questions, proposals, wording, rationale, scope, replacement answers, notes, Rambling Audit rows, or any other cells. Treat all such edits as deliberate.

One cleanup remains for later consolidation: there is at least one stale older Ice and Fire related authority sentence that reflects the obsolete quest-exclusive Rambling assumption. Locate and correct it before the final singular V5 document is consolidated. Do not alter the controlling global Rambling rule.

Before doing anything, verify the live repository state against the handoff checkpoint and report any legitimate newer commits. Then produce Batch 04.

---
