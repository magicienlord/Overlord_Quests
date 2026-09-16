# OVERLORD REIGN V5 Overlord Approval Governance

Status: HIGHEST OPERATIONAL V5 AUTHORING GOVERNANCE

Date: 2026-09-16

Purpose: ensure the final V5 campaign authority contains no authored campaign choice that was selected without explicit approval from the Overlord.

## 1. Absolute rule

Only two classes of information may become authoritative in V5:

1. an explicit decision made or explicitly approved by the Overlord;
2. a verified technical fact established directly from the exact installed mod, source, JAR, API, registry, advancement, Questlog framework, or other implementation evidence.

A verified technical fact may establish what exists, what is possible, how a native mechanic behaves, what state can be observed, or which implementation signal is available. It does not decide how V5 uses that fact.

Every authored campaign choice requires explicit Overlord approval before it becomes V5 authority.

This includes campaign scope, questline existence or exclusion, quest beat count and order, prerequisites, unlock relationships, exact player-facing objectives, questline start actions, civilization anchor-lock actions, branches, terminal outcomes, persistent consequences, presenter assignment, presenter theme ownership, presenter visual-state vocabulary, Rambling inclusion or exclusion, Rambling trigger and speaker selection, allocation between Questlog/provider/native/Rambling/ambient treatment, capstones, reward meaning, historical interpretation, and any other player-facing design choice.

Instructions such as `proceed`, `continue`, `keep going`, or `come back with questions` authorize investigation, source verification, technical audit work, and preparation of proposals. They do not authorize unresolved campaign design to be selected by the author.

If an authored choice cannot be traced to explicit Overlord approval, its status is `PROPOSAL` or `UNKNOWN / REQUIRES OVERLORD DECISION`, never authoritative V5 content.

### 1.1 Quest specification completeness

Every visible authored quest in the final V5 campaign authority must record, at minimum:

- the exact activation trigger that makes the quest become active or visible;
- its prerequisite state or prerequisite quest where that is distinct from the activation trigger;
- its exact intended player-facing objective or objective set;
- its completion condition;
- its completion consequence or unlock;
- its completion reward, or an explicit `NONE` when no reward is intended.

Activation triggers and rewards are authored campaign decisions. They may not be inferred later by the implementation pass merely because a technically convenient trigger or reward exists.

A quest reward may be mechanical, material, capability-based, state-based, access-based, presentational, or absent. The V5 authority must distinguish the reward itself from ordinary consequences such as unlocking the next quest, recording a remembered fact, or invoking a source-owned progression API.

Presentation-only transitions, Ramblings, native milestones that do not become visible quests, and silent gates do not require fabricated quest rewards. Their own trigger and consequence rules must still be explicit wherever V5 relies on them.

The final V5 document must therefore be sufficient for later implementation to translate quest activation and reward behavior without reopening campaign design.

## 2. No inference promotion

None of the following constitutes approval:

- a mod exposing an advancement, statistic, item, structure, boss, or mechanic;
- the Questlog framework supporting an objective type;
- an older production quest already existing;
- an assistant recommending an option;
- a technical choice being convenient or inexpensive;
- an older lore document leaving Quest Maker discretion;
- an authored choice appearing obvious;
- a file being named `AUTHORITY`, `BLUEPRINT`, or similar;
- a choice having existed on a later contaminated V5 branch.

Technical convenience cannot promote design into authority.

## 3. Required workflow

For every unresolved V5 section:

1. inspect current canon, prior explicit Overlord decisions, and exact technical sources;
2. separate technical facts from authored choices;
3. formulate the narrowest meaningful options or proposal;
4. stop and bring every authored choice to the Overlord;
5. record the Overlord's decision;
6. only then promote the approved choice into V5 authority;
7. preserve intentionally unknown lore as UNKNOWN.

Questions may be grouped when they are genuinely part of one decision batch, but grouping must not conceal individual authored choices.

For every visible quest that becomes sufficiently defined, the authority pass must also close its activation trigger and completion reward before that quest can be considered implementation-ready.

## 4. Restart boundary

This clean V5 branch starts from commit `7518a749181100ff6b46699ffd498b5f1931ca37`, the parent of the first later commit that began creating concrete quest graphs without obtaining approval for every authored choice.

The contaminated later branch is retained only as an audit/reference record. Its authored design must not be imported unless independently traceable to an explicit Overlord decision.

## 5. Production boundary

V5 authoring does not authorize production quest implementation.

Current `gnarl-bootstrap` material is technical evidence only until the complete V5 campaign authority has been explicitly closed and approved by the Overlord.