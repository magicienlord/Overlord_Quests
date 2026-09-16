# OVERLORD REIGN V5 Overlord Approval Governance

Status: HIGHEST OPERATIONAL V5 AUTHORING GOVERNANCE

Date: 2026-09-16

Purpose: prevent any current or future author, agent, or implementation pass from treating an unapproved campaign-design choice as V5 authority.

## 1. Absolute approval rule

Only two classes of information may become authoritative in V5:

1. an explicit decision made or explicitly approved by the Overlord;
2. a verified technical fact established directly from the exact installed mod, source, JAR, API, registry, advancement, Questlog framework, or other implementation evidence.

A verified technical fact may establish what is possible, what an identifier is, what native state exists, how a mechanic behaves, or which source signal can be observed. It does not decide how V5 uses that fact.

Every authored campaign choice requires explicit Overlord approval before it becomes authority.

This includes, without limitation:

- campaign scope;
- questline existence or exclusion;
- quest beat count and order;
- prerequisites and unlock relationships;
- exact player-facing objectives;
- exact actions that start or anchor a questline;
- branch structure and terminal outcomes;
- persistent consequences;
- presenter assignment;
- presenter theme ownership;
- presenter visual-state rules;
- Rambling inclusion or exclusion;
- which advancement, statistic, item, structure, boss, or native event deserves a Rambling;
- Rambling speaker assignment;
- allocation between Questlog, provider dialogue, native progression, Ramblings, ambient content, or exclusion;
- arc culmination or capstone;
- reward meaning;
- historical interpretation;
- any player-experience rule not already explicitly approved.

Instructions such as `proceed`, `continue`, `keep going`, or `come back with questions` authorize investigation, source verification, audit work, and preparation of proposals. They do not authorize unresolved campaign design to be decided by the author.

If an authored choice cannot be traced to explicit Overlord approval, its status is `UNKNOWN / REQUIRES OVERLORD DECISION` or `PROPOSAL`, never authoritative V5 content.

## 2. No inference promotion

None of the following constitutes approval:

- a mod having an advancement;
- a framework supporting an objective type;
- an older production quest already existing;
- an assistant recommending an option;
- a source mechanic making one route convenient;
- an older lore file leaving Quest-Maker discretion;
- a choice appearing obvious or technically cheap;
- a file being named `AUTHORITY` or `AUTHORED_BLUEPRINT`;
- a choice already having been committed to the V5 branch.

Technical convenience cannot promote design into authority.

## 3. Provenance requirement

Every V5 clause must be classifiable as one of:

- `OVERLORD APPROVED`
- `VERIFIED TECHNICAL FACT`
- `PROPOSAL`
- `UNKNOWN / REQUIRES OVERLORD DECISION`
- `SUPERSEDED`

Only `OVERLORD APPROVED` and `VERIFIED TECHNICAL FACT` may be relied on as authority while V5 is being finalized.

The V5 branch contains documents written before this rule was enforced strictly enough. Nothing in those documents is grandfathered merely because it is committed. Any contradiction between an older V5 file and this governance file is resolved in favor of this governance file until the older file is corrected.

## 4. Required review workflow

For every unresolved V5 section:

1. inspect current canon, explicit V5 decisions, and exact technical sources;
2. separate technical facts from authored choices;
3. formulate the narrowest meaningful options where a choice remains;
4. bring every authored choice to the Overlord;
5. record the Overlord's answer;
6. only then promote the choice into final V5 authority;
7. preserve intentionally unknown lore as UNKNOWN.

Questions may be grouped for efficiency, but grouping must not hide individual decisions.

## 5. Presenter decisions currently established

The following are `OVERLORD APPROVED`:

- Questlog presentation is a multi-presenter system rather than a Gnarl-only system.
- PNG presenter treatment is for non-corporeal NPC presenters.
- The Historian is the single corporeal PNG-presenter exception in V5. V5 must not preserve an open-ended mechanism for approving additional corporeal presenter exceptions later.
- The complete presenter roster is to be settled inside V5 itself before production asset generation.
- Gnarl, Mortis, Quaver, the Historian, Lestat, Gristle, Grubbison Jr, and Giblet the Sixth remain in the V5 presenter-roster closure work.
- Gristle owns food-adjacent campaign presentation and Ramblings.
- Grubbison Jr owns mining-related campaign presentation and Ramblings.
- Giblet the Sixth owns forging-related campaign presentation and Ramblings.
- Presenter design uses thematic ownership to vary the pool of quest-presenting NPCs. Theme ownership is therefore an authored V5 decision, not an implementation convenience.
- Ordinary physical civilization providers do not automatically become PNG presenters.
- Registered presenters use five visual states.

The exact final five-state vocabulary and semantics remain `UNKNOWN / REQUIRES OVERLORD DECISION` until explicitly settled.

The assistant-authored vocabulary `neutral / pleased / assertive / concerned / hostile` is not approved authority.

The older Gnarl-specific vocabulary `neutral / directive / mocking / approving / severe` is historical implementation context only unless explicitly selected by the Overlord for the final V5 contract.

## 6. Dragon correction currently established

The Overlord clarified that the previously referenced `Option 3` concerned the dragon-mastery wording correction, not the presenter five-state question.

The final approved player-facing wording for the last dragon-mastery concept is exactly:

`Bond with a dragon and take flight on it`

Exact implementation signals remain technical work and must not silently alter that player-facing requirement.

## 7. Ramblings quarantine

The following are `OVERLORD APPROVED` at system level:

- Ramblings are sparse contextual character reactions, not quests, reminder systems, or a second completion track.
- Meaningful native achievements or discoveries may be used for Ramblings where the Overlord explicitly allocates them.
- Not every native advancement deserves a Rambling.
- L_Ender's Cataclysm may use meaningful native achievements as contextual popup Ramblings to complement its broader Questlog coverage.
- Gristle owns food-adjacent Ramblings.
- Grubbison Jr owns mining-related Ramblings.
- Giblet the Sixth owns forging-related Ramblings.

The following are not blanket-approved and remain unresolved until individually decided:

- exact advancement-to-Rambling mappings;
- exact required, optional, eligible, or excluded trigger lists;
- exact speaker assignment for any trigger not already explicitly assigned;
- popup quotas or milestone counts;
- per-mod Rambling selections created from source audits alone.

## 8. Civilization anchor-start quarantine

The Overlord approved runtime-selected civilization anchors. A qualifying generated settlement or structure becomes the canonical local polity when the civilization questline is deliberately started there, after which required local providers may be bound or spawned according to the approved civilization framework.

The exact deliberate player action that starts and locks each civilization anchor has not been blanket-approved.

Provider acceptance, commander kills, audience interactions, reputation thresholds, structure entry, or any other exact start signal remain `UNKNOWN / REQUIRES OVERLORD DECISION` unless that specific signal was explicitly approved for that civilization.

Technical support for an interaction establishes feasibility only.

## 9. Production boundary

No V5 audit or correction authorizes production quest implementation.

`gnarl-bootstrap` remains technical evidence only until the complete V5 campaign authority has been explicitly closed and approved by the Overlord.
