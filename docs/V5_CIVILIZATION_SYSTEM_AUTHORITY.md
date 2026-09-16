# OVERLORD REIGN V5 Civilization System Authority

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is a focused companion to `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md`. It records explicit V5 civilization decisions made after the foundation document was opened. It does not modify `magicienlord/Overlord_Lore_and_Canon`, which remains read-only.

## 1. Core civilization presentation

Each civilization is represented by one designated canonical anchor polity and one civilization Questlog presentation.

The civilization Questlog is the anchor presentation for the political arc. Its terminal resolution is an OR among three authored outcomes:

```text
NEUTRAL
OR
SUBJUGATED
OR
DESTROYED
```

`UNRESOLVED` is only the absence of a completed terminal route. It is not an outcome.

The three outcomes apply to the designated anchor polity. They do not mean that the entire species, culture, or every procedurally generated settlement in the world has changed state.

A completed terminal outcome is permanent for the canonical anchor unless a later explicit V5 decision creates a post-resolution exception.

## 2. Inner Villager Retaliation style architecture

The civilization Questlog is not itself a detached three-choice menu.

Inside the civilization are provider-driven quests inspired by the Villager Retaliation model. These local quests expose the civilization's people, institutions, problems, services, rivalries, fears, greed, weaknesses, and opportunities.

Provider quests may reveal or create the specific route needed to obtain one of the three terminal outcomes.

Example pattern:

```text
civilization contact
-> meet local providers
-> complete a meaningful provider chain
-> gain a named fact, leverage, service, weakness, alliance, or destructive opportunity
-> reveal or unlock one terminal route
-> complete that route
-> civilization Questlog resolves through the matching OR outcome
-> incompatible terminal routes lock permanently
```

A Dwarven blacksmith or Forger chain may, for example, reveal leverage over the Forge-Thane. That leverage may become part of the authored SUBJUGATED route.

The political meaning comes from the exact provider chain and its consequence, not from a generic count of completed errands.

## 3. No hidden sidequest score

There is no civilization reputation meter implemented by OVERLORD QUESTS and no rule such as:

```text
complete 3 sidequests -> unlock submission
```

Repeated unrelated sidequests do not silently accumulate into friendship, domination, corruption, or political progress.

Provider quests may instead write sparse explicit facts such as:

- a named authority has been embarrassed;
- a resource dependency has been exposed;
- a provider now owes the Overlord a favor;
- a defensive weakness has been learned;
- a service has been secured;
- a rival faction has been strengthened;
- a local sacred or economic asset is under the Overlord's control;
- a destructive opportunity has been created.

Only facts that matter to later authored content should persist.

## 4. Three route construction

Each civilization receives three coherent inner political routes leading to the three terminal outcomes.

The routes may share opening contact and investigation content, but each terminal path must have its own authored logic.

### 4.1 NEUTRAL

Neutrality is a deliberate final settlement of the local political problem.

It is not first contact, temporary non-hostility, a successful audience, or passive failure to conquer.

The route must contain an affirmative authored resolution that leaves the anchor independent.

### 4.2 SUBJUGATED

Subjugation is a deliberate political victory in which the anchor survives and accepts the Overlord's supremacy.

Its route should normally exploit civilization-specific leverage, authority, fear, economic dependence, religious humiliation, military defeat, or another source-backed mechanism.

Subjugation must preserve the civilization as useful subjects rather than merely duplicating destruction.

### 4.3 DESTROYED

Destruction is the authored elimination or irreversible ruin of the designated anchor polity.

It is local. Other settlements and members of the civilization may continue to exist elsewhere.

The route should use the civilization's actual political or structural vulnerability rather than reducing every destructive resolution to a generic kill counter.

## 5. Intermediary states are quest phases, not terminal dispositions

Older content sometimes wrote `NEUTRAL` too early. V5 supersedes that pattern.

Examples:

### Illagers

```text
HOSTILE native opening
-> Bastille authority broken
-> COWED internal phase/fact
-> provider and leverage content
-> terminal NEUTRAL / SUBJUGATED / DESTROYED resolution
```

`COWED` is not a civilization disposition.

### Umvuthana

```text
HOSTILE/native inaccessible opening
-> masked legitimate audience
-> AUDIENCE_ESTABLISHED internal phase/fact
-> Grove and Umvuthi progression
-> terminal NEUTRAL / SUBJUGATED / DESTROYED resolution
```

`AUDIENCE_ESTABLISHED` is not a civilization disposition.

The mask remains a native access mechanism, not the final political resolution.

## 6. Provider quest rules

Provider quests exist because a person or institution has a reason to involve the Overlord.

They may be driven by fear, greed, desperation, ambition, revenge, self-preservation, coercion, opportunism, genuine submission, or attempts to manipulate the Overlord.

A provider quest is valid inside a civilization arc when it does one or more of the following:

- characterizes the civilization;
- exposes a political pressure point;
- grants a service or resource;
- establishes a persistent relationship;
- reveals historical or local information;
- creates leverage;
- opens a terminal route;
- modifies the consequence of a later route;
- closes an incompatible possibility through a deliberate player action.

Do not create filler merely because a provider role exists.

## 7. Sparse branch implementation

The three terminal routes must remain implementable without a combinatorial state machine.

Use:

- explicit named facts;
- provider completion state;
- local anchor state;
- small prerequisite predicates;
- final mutually exclusive terminal markers.

Do not synchronize every sidequest with every other provider quest.

A terminal route should inspect only the facts it actually needs.

## 8. Dwarf and Kobold rivalry

The approved Dwarf-Kobold shared rivalry chain is optional and opens after discovery of both designated anchors.

Implementation rule:

- it writes a small set of shared rivalry facts;
- those facts may unlock, alter, strengthen, weaken, or change consequences inside later Dwarf or Kobold terminal routes;
- it does not directly write either terminal civilization disposition;
- it does not merge the two civilization state machines;
- it does not require continuous cross-civilization synchronization;
- each civilization can still be resolved independently if the optional rivalry chain is ignored.

This is the preferred implementation because it preserves meaningful cross-civilization consequence without materially increasing runtime or authoring complexity.

## 9. Gnumu ancestry choice

The approved Gnumu ancestry decision remains inside the one Gnumu civilization arc.

The Overlord may reveal the settlement's Halfling ancestry to the Elder Shaman or deliberately withhold it.

This is a remembered historical truth choice, not one of the three terminal political dispositions.

It may affect later dialogue, knowledge, or route consequences where explicitly authored.

## 10. Myrmex technical boundary pending final mapping

The exact supplied Ice & Fire artifact is `iceandfire-2.1.13-1.20.1-beta-5.jar`, SHA-256 `2b80245fc9b7d6fdc61d71f9892f4c6114eb7f303f65634845aaab25f84d1e82`.

Ice & Fire already owns a per-hive player reputation system:

- default opinion: 0;
- 25: colony becomes non-hostile;
- 50: colony trades with the player;
- 75: player may command the hive through a Myrmex Staff;
- a player-founded hive created from a hatched queen egg binds to the player and receives opinion 100.

Native opinion changes through Myrmex gameplay, including resin gifting, trading, attacking, and killing colony members.

V5 must not confuse this native access/opinion mechanic with the Questlog political terminal state.

The final mapping between native opinion thresholds and `NEUTRAL`, `SUBJUGATED`, and `DESTROYED` is intentionally left unresolved until the Overlord approves it.

## 11. Production reconciliation rule

This document is V5 campaign authority only.

Do not reconcile current production quests, Java, configs, provider code, or world-state persistence until the V5 campaign authority pass is complete and approved.
