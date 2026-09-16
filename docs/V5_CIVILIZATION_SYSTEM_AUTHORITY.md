# OVERLORD REIGN V5 Civilization System Authority

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is a focused companion to `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md`. It records explicit V5 civilization decisions made after the foundation document was opened. It does not modify `magicienlord/Overlord_Lore_and_Canon`, which remains read-only.

## 1. Core civilization presentation

Each civilization is represented by one canonical anchor polity for the current playthrough and one civilization Questlog presentation.

The civilization Questlog is the anchor presentation for the political arc. Its terminal resolution is an OR among three authored outcomes:

```text
NEUTRAL
OR
SUBJUGATED
OR
DESTROYED
```

`UNRESOLVED` is only the absence of a completed terminal route. It is not an outcome.

The three outcomes apply to the selected anchor polity. They do not mean that the entire species, culture, or every procedurally generated settlement in the world has changed state.

A completed terminal outcome is permanent for the canonical anchor unless a later explicit V5 decision creates a post-resolution exception.

## 2. Runtime anchor selection

Civilization anchors are not preplaced by authored coordinates and are not chosen during a separate world-integration pass.

The world generates eligible settlements and structures normally. The canonical anchor is selected at runtime when the player deliberately starts that civilization questline from one qualifying settlement or structure.

The required pattern is:

```text
find a qualifying settlement or structure
-> perform the civilization's explicit anchor-start action inside it
-> validate that the candidate is eligible and that no anchor for this civilization is already locked
-> permanently bind that generated settlement or structure as the civilization anchor for this playthrough
-> identify suitable local NPCs for authored quest roles
-> bind those existing NPCs to the required roles where possible
-> spawn any required quest-role NPCs that are missing
-> distribute the civilization's authored provider quests among the bound local cast
-> begin the civilization arc
```

The exact anchor-start action may differ by civilization and should use the simplest source-appropriate signal available.

The action must be deliberate and difficult to trigger accidentally. Mere proximity, entering a settlement, incidental combat, ordinary looting, or encountering a generic member of the civilization is not sufficient.

Once an anchor is locked, later settlements or structures of the same civilization remain ordinary world populations unless an authored sidequest explicitly uses them.

This runtime selection model supersedes older wording that implied a later coordinate-placement step for civilization anchors.

### 2.1 Missing required NPCs

A generated candidate does not need to contain every authored provider naturally before it can qualify.

After anchor lock:

- reuse appropriate existing NPCs when possible;
- assign protected authored roles to those selected NPCs where required;
- spawn missing required quest-role NPCs inside the selected anchor when the native population does not provide them;
- do not duplicate roles merely because more generic NPCs later appear.

The V5 campaign blueprint must define the roles that are required for each civilization. The later implementation determines the least invasive source-compatible way to bind or spawn them.

## 3. Inner Villager Retaliation style architecture

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

## 4. No hidden sidequest score

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

## 5. Three route construction

Each civilization receives three coherent inner political routes leading to the three terminal outcomes.

The routes may share opening contact and investigation content, but each terminal path must have its own authored logic.

### 5.1 NEUTRAL

Neutrality is a deliberate final settlement of the local political problem.

It is not first contact, temporary non-hostility, a successful audience, or passive failure to conquer.

The route must contain an affirmative authored resolution that leaves the anchor independent.

### 5.2 SUBJUGATED

Subjugation is a deliberate political victory in which the anchor survives and accepts the Overlord's supremacy.

Its route should normally exploit civilization-specific leverage, authority, fear, economic dependence, religious humiliation, military defeat, or another source-backed mechanism.

Subjugation must preserve the civilization as useful subjects rather than merely duplicating destruction.

A subjugation route does not need to culminate in combat. Political, economic, social, religious, or provider-driven submission is valid when it fits the civilization better and avoids unnecessary compatibility work.

### 5.3 DESTROYED

Destruction is the authored elimination or irreversible ruin of the designated anchor polity.

It is local. Other settlements and members of the civilization may continue to exist elsewhere.

The route should use the civilization's actual political or structural vulnerability rather than reducing every destructive resolution to a generic kill counter.

## 6. Intermediary states are quest phases, not terminal dispositions

Older content sometimes wrote `NEUTRAL` too early. V5 supersedes that pattern.

### 6.1 Illagers

```text
HOSTILE native opening
-> Bastille authority broken
-> COWED internal phase/fact
-> provider and leverage content
-> terminal NEUTRAL / SUBJUGATED / DESTROYED resolution
```

`COWED` is not a civilization disposition.

The approved terminal architecture after the canonical Bastille is cowed is:

#### NEUTRAL

The Overlord forces a lasting nonaggression settlement while leaving the Bastille under its own Illager authority.

The Bastille leadership understands that challenging the Overlord again is untenable, but the Illagers remain politically independent and are not his subjects.

#### SUBJUGATED

The Overlord exploits the broken military hierarchy and the Bastille's surviving useful infrastructure until its leadership accepts his supremacy.

The anchor survives as an Overlord-aligned Illager warband and military asset. This does not pacify unrelated Illagers or erase their ordinary hostility toward Villagers.

#### DESTROYED

The Overlord deliberately continues beyond the cowed state and eliminates the Bastille's leadership and functional polity.

The result applies only to the selected canonical Bastille.

### 6.2 Umvuthana

```text
HOSTILE/native inaccessible opening
-> masked legitimate audience
-> AUDIENCE_ESTABLISHED internal phase/fact
-> Grove and Umvuthi progression
-> terminal NEUTRAL / SUBJUGATED / DESTROYED resolution
```

`AUDIENCE_ESTABLISHED` is not a civilization disposition.

The mask remains a native access mechanism, not the final political resolution.

The previously proposed SUBJUGATED implementation based on intercepting the Umvuthi boss encounter and converting near-death into a nonlethal surrender is rejected.

V5 must not require:

- custom boss-health interception;
- a synthetic defeated-but-alive Umvuthi combat state;
- special cancellation of his native death solely to support submission.

The Umvuthana SUBJUGATED route must instead be a quest-driven political or religious submission that leaves Umvuthi alive without requiring a fight against him as its terminal action.

The exact submission chain remains under authoring and must be supported by the native Grove, mask, Umvuthi interaction, and provider surfaces wherever possible.

## 7. Provider quest rules

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

## 8. Sparse branch implementation

The three terminal routes must remain implementable without a combinatorial state machine.

Use:

- explicit named facts;
- provider completion state;
- local anchor state;
- small prerequisite predicates;
- final mutually exclusive terminal markers.

Do not synchronize every sidequest with every other provider quest.

A terminal route should inspect only the facts it actually needs.

## 9. Villagers and Spree

The Villager civilization anchor identity is Spree.

This decision is fixed for V5 and should not be reopened as a question of whether another historical human settlement should replace it.

Spree does not mean that the campaign requires a pre-authored world location or a reconstruction at fixed placement.

Instead:

```text
eligible generated Villager settlement
-> deliberate Villager anchor-start action
-> that settlement becomes the canonical present-day Spree for this playthrough
-> local Villager cast is bound or completed as required
-> Spree civilization questline begins
```

The selected settlement may reflect its generated environment and layout. The narrative identity and civilization role are Spree.

The exact deliberate start action and the internal provider blueprint remain to be authored from the Villager campaign design.

## 10. Dwarf and Kobold rivalry

The approved Dwarf-Kobold shared rivalry chain is optional and opens after discovery of both designated anchors.

Implementation rule:

- it writes a small set of shared rivalry facts;
- those facts may unlock, alter, strengthen, weaken, or change consequences inside later Dwarf or Kobold terminal routes;
- it does not directly write either terminal civilization disposition;
- it does not merge the two civilization state machines;
- it does not require continuous cross-civilization synchronization;
- each civilization can still be resolved independently if the optional rivalry chain is ignored.

This preserves meaningful cross-civilization consequence without materially increasing runtime or authoring complexity.

## 11. Gnumu ancestry choice

The approved Gnumu ancestry decision remains inside the one Gnumu civilization arc.

The Overlord may reveal the settlement's Halfling ancestry to the Elder Shaman or deliberately withhold it.

This is a remembered historical truth choice, not one of the three terminal political dispositions.

It may affect later dialogue, knowledge, or route consequences where explicitly authored.

## 12. Myrmex mapping

The Myrmex technical boundary and terminal political mapping are finalized in `docs/V5_ICE_AND_FIRE_CAMPAIGN_AUTHORITY.md`.

Summary:

- native Ice & Fire opinion remains source-owned;
- native opinion does not automatically select a terminal political state;
- authored NEUTRAL requires the completed independent route and native opinion 50 or higher;
- authored SUBJUGATED requires the completed submission route and native opinion 75 or higher;
- DESTROYED is a local authored hive-destruction result;
- a player-founded 100-opinion hive is not used as a substitute for conquering the existing canonical hive.

This section supersedes the earlier pending Myrmex mapping note.

## 13. Production reconciliation rule

This document is V5 campaign authority only.

Do not reconcile current production quests, Java, configs, provider code, anchor persistence, NPC spawning, or world-state persistence until the V5 campaign authority pass is complete and approved.
