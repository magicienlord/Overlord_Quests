# OVERLORD REIGN V5 — Decision Record Q241–Q242

Status: V5 CAMPAIGN AUTHORITY
Date: 2026-09-18
Authority: explicit Overlord decision, recorded in conversation.

---

## Q241 — Campaign reward policy

Decision: APPROVED / LOCKED

Authoritative decision:

The V5 campaign authors **no quest rewards**. Every visible quest in the campaign carries
`reward: NONE`. Gameplay rewards the player — native boss loot, crafted outputs, items
obtained during the objective, access gained through native play, and the capabilities and
state unlocked by completion.

Authority effect:

- All 145 visible quests are recorded with `reward: NONE`.
- `Q154` (default reward is `NONE`) is fully exercised.
- `Q155` continues to govern: campaign state changes are recorded as completion
  consequences, never converted into rewards.
- `Q156` continues to govern: presenter acknowledgement is authored closure, not a reward.
- **`Q157` is retained authority but is not exercised by this campaign.** Its rule — prefer a
  relevant item or material appropriate to difficulty and campaign stage over generic XP or
  loot-table payment — remains the governing rule *if* a reward is ever justified. No reward
  is justified in the V5 campaign as approved.

Supersedes:

- Any implication in the Batch 03 reconciliation that Q157 requires reward population.
- Any later assumption that an empty reward column is an authoring gap.

Implementation instruction:

A later pass MUST NOT populate the reward column. An empty reward column is the approved
state, not an omission. Populating it requires an explicit Overlord decision superseding
this one.

---

## Q242 — Objective specification versus presenter order

Decision: APPROVED / LOCKED

Authoritative decision:

Every visible quest carries two distinct fields.

`Objective (specification)` is the machine-checkable condition the quest must verify. It is a
design specification. It is **never shown to the player** and must never be shipped as quest
text.

`Order (presenter voice)` is the player-facing line. It is an **order issued by the quest's
presenter**, written in that presenter's voice, not a checklist entry or a restatement of the
specification. It is authored during the campaign-writing pass from
`Overlord_Lore_and_Canon`, the presenter writing rules, and the locked presenter roster.

Authority effect:

- All 145 rows carry `Order (presenter voice): TO BE AUTHORED`.
- A quest shipped with specification text in the order field is a defect, not a placeholder.
- This applies to every presenter in the locked roster: Gnarl, Mortis, Quaver, Historian,
  Lestat, Gristle, Grubbison Jr, Giblet the Sixth.
- Quest names and titles follow the same rule and are authored in the same pass
  (Overlord decision, item 5 of the nine-item batch).

Example of the distinction:

```text
Objective (specification):  Restore the Throne: Necrolord Chair installed
Order (presenter voice):    TO BE AUTHORED
                            (a Gnarl order, in Gnarl's voice, commanding the Overlord to
                             seat himself properly — not "install the Necrolord Chair")
```

Supersedes:

- Nothing. This closes a gap rather than replacing an earlier decision.