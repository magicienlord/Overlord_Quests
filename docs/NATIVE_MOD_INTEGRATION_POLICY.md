# Native Mod Integration Policy

Status: TECHNICAL AUTHORING POLICY

Date: 2026-09-12

## Purpose

OVERLORD QUESTS integrates a large modpack. Compatibility work must be complete enough to protect campaign state without applying the same forensic cost to every installed mod.

Audit depth therefore scales with the gameplay role and state complexity of the mod being integrated.

This policy changes investigation depth, not evidence quality. Every production integration still requires a verified installed mechanic or stable owning-mod contract.

## Integration depth classes

### LIGHTWEIGHT

Use for isolated content with a narrow authoritative state surface, for example:

- one boss or boss completion;
- one structure discovery;
- one durable advancement;
- one unique crafted/obtained capability;
- one dimension visit;
- a simple public API call with no branching progression ownership.

Expected work:

1. verify the exact installed content ID or public contract;
2. select the strongest durable signal already supported by Questlog;
3. confirm sequence-break behavior where relevant;
4. add only the narrow adapter/definition and validation needed by the campaign use.

Do not perform a whole-mod systems audit unless evidence shows the supposedly narrow integration is coupled to broader progression.

### STANDARD

Use for self-contained progression chains or mods contributing several related campaign milestones.

Typical examples include a dungeon sequence, specialist crafting chain, staged equipment progression, or a small dimension loop.

Expected work:

1. map the relevant native progression chain;
2. identify persistent milestones and ownership boundaries;
3. distinguish required campaign hooks from optional native content;
4. verify alternate/early completion paths;
5. add a bounded compatibility adapter only where existing Questlog objectives cannot represent the native state accurately.

The audit should stop once the relevant campaign-facing state graph is understood. Unrelated mod features do not need exhaustive analysis.

### DEEP

Use when a mod can become part of the campaign backbone or when incorrect integration can corrupt durable progression.

Examples include:

- Minions Remastered / OVERLORD Minions progression ownership;
- Theurgy and similarly broad magic/progression systems;
- major dimension progression;
- civilization-state systems;
- Tower capability/restoration systems;
- mods with multiple interdependent machines or progression currencies;
- systems whose state must survive destructive choices, save/reload, sequence breaking, or cross-mod reconciliation.

Expected work:

1. inspect the exact installed artifact or establish a defensible exact-source equivalence;
2. map the relevant native capability graph rather than selecting a convenient isolated item;
3. identify the owning mod's persistent state and public integration surfaces;
4. separate knowledge/UI state from actual gameplay capability;
5. test or structurally prove idempotence, ordering, save persistence, and sequence breaking where applicable;
6. document what remains native-owned and what, if anything, Questlog mirrors as REIGN narrative interpretation;
7. avoid promoting technical topology into story canon without lore authority.

Deep does not mean rewriting the mod or auditing every decorative feature. It means understanding enough of the progression/state model to integrate it safely.

## Evidence priority

When several signals could represent the same accomplishment, prefer the strongest authoritative surviving evidence in roughly this order:

1. stable public API or explicit owning-mod progression contract;
2. durable native vanilla advancement that exactly represents the accomplishment;
3. durable owning-mod saved state exposed through a stable supported interface;
4. persistent vanilla statistic, such as exact crafted-item or killed-entity history;
5. durable world evidence such as a verified boss/structure/native completion state;
6. bounded event observation maintained by Questlog only when no reliable native history exists.

A REIGN narrative fact may be written after native evidence resolves when later narrative logic needs a stable interpretation. It must not silently replace the owning mod's progression state.

## Signals to avoid as progression authority

Unless a concrete mechanic specifically requires them, do not use:

- client-only state;
- transient GUI state;
- current inventory possession when historical accomplishment matters;
- private implementation NBT;
- reflection into unstable internals;
- arbitrary commands used as synthetic progression;
- a book/page being viewed when actual mechanical capability is the requirement;
- Questlog counters duplicating a native persistent progression state;
- physical coordinates when the authored requirement can be represented by a stable generated structure or anchor identity.

## Sequence-break requirement

Minecraft exploration is not subordinate to quest activation timing.

For any campaign milestone that can legitimately happen early, integration should prefer evidence that survives until the quest becomes relevant. The player should not need to repeat a boss, dungeon, craft, structure visit, dimension visit, or native progression milestone solely because Questlog began observing later.

If no reliable historical evidence exists, document that limitation before selecting a fallback. Do not fabricate prior accomplishment.

## Ownership rule

The mod that implements a gameplay system owns its mechanical state unless an explicit project decision says otherwise.

OVERLORD QUESTS should normally:

- frame the native activity;
- observe authoritative milestones;
- write sparse REIGN facts describing narrative consequences;
- react through Gnarl/providers/campaign availability;
- invoke stable public progression APIs when the owning mod explicitly provides them.

It should normally not:

- duplicate the native save model;
- manipulate internal rosters or machine data directly;
- intercept an owning mod's core item merely to force a quest path;
- replace a meaningful native progression loop with Questlog objectives that simulate the same gameplay.

## Escalation rule

Increase audit depth only when the discovered mechanics justify it.

A LIGHTWEIGHT integration may be promoted to STANDARD or DEEP if inspection reveals hidden ordering, multiple persistent owners, destructive state, alternate progression, or cross-mod reconciliation.

A DEEP audit should stop when the relevant state graph and safe integration surface are understood. Do not keep investigating unrelated internals merely because source is available.

## Campaign-authoring consequence

Technical integration evidence does not itself decide campaign importance.

A verified native milestone becomes production campaign content only when the lore/source authority gives it a concrete role. Until then it remains a technically available hook.

This keeps compatibility work efficient while preventing implementation convenience from inventing OVERLORD REIGN canon.
