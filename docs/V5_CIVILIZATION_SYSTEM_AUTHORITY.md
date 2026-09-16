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

The canonical Umvuthana Grove is selected through deliberate masked contact with its Umvuthi. Wearing an Umvuthana mask and deliberately initiating the legitimate Umvuthi interaction inside an otherwise eligible Grove is the approved anchor-start signal.

The shared opening is:

```text
find an eligible generated Umvuthana Grove
-> wear an Umvuthana mask
-> deliberately initiate legitimate contact with that Grove's Umvuthi
-> lock that Grove as the canonical Umvuthana anchor
-> AUDIENCE_ESTABLISHED internal phase/fact
-> bind the required local Umvuthana provider cast
-> begin the Grove political progression
```

`AUDIENCE_ESTABLISHED` is not a civilization disposition.

The mask remains a native access mechanism, not the final political resolution.

The previously proposed SUBJUGATED implementation based on intercepting the Umvuthi boss encounter and converting near-death into a nonlethal surrender is rejected.

V5 must not require:

- custom boss-health interception;
- a synthetic defeated-but-alive Umvuthi combat state;
- special cancellation of his native death solely to support submission.

The approved terminal architecture is:

#### NEUTRAL

The Overlord completes Umvuthi's native peaceful exchange, paying the configured seven Gold Blocks and receiving Sun's Blessing. The native persistent trade recognition then supports a short Grove relationship sequence.

The route ends with an explicit authored decision that recognizes the canonical Grove as independent. Umvuthi remains its creator-god and sovereign ruler. His persistent recognition of the Overlord and repeat access to replenished Sun's Blessing are the native gameplay expression of the settled peaceful relationship.

The native exchange alone does not automatically write `NEUTRAL`. The explicit authored independence resolution is still required.

#### SUBJUGATED

The Overlord does not defeat Umvuthi in combat.

Instead, he compromises two approved practical pillars of Umvuthi's local control:

```text
EntityUmvuthanaCrane -> Grove Healer / healing and support authority
EntityUmvuthanaRaptor -> Grove Raptor / martial and pack authority
```

Each receives a separate meaningful provider chain. Each chain must end in an explicit allegiance, obligation, dependency, or equivalent personal commitment to the Overlord. This is not a hidden count of generic Umvuthana errands.

The required structure is:

```text
legitimate audience established
-> complete the Crane provider chain
-> secure the Grove Healer commitment
-> complete the Raptor provider chain
-> secure the Grove Raptor commitment
-> demonstrate that Umvuthi can no longer rely on unquestioned control of the Grove's support and martial pillars
-> unlock a final audience with the living Umvuthi
-> Umvuthi chooses preservation of himself and the Grove over further loss of control
-> Umvuthi publicly acknowledges the Overlord as the superior ruler
-> SUBJUGATED
```

A Grove Trader may still exist as an everyday local contact, but no Trader commitment is required for political submission.

Native Grove generation guarantees Umvuthi but does not guarantee both approved provider types. Once the canonical Grove is deliberately selected, later implementation is explicitly authorized to spawn a missing native Crane and/or Raptor and bind it to the approved quest role. Existing suitable entities should be reused where possible.

Umvuthi remains the Umvuthana creator-god and their immediate local ruler. Politically, he and the canonical Grove now rule beneath the Overlord rather than independently of him.

The humiliation is political and religious: a creator-god is forced to recognize a higher temporal sovereign because two critical practical pillars of the society he created have been brought under that sovereign's influence.

The detailed provider authority is recorded in `docs/V5_UMVUTHANA_CIVILIZATION_BLUEPRINT.md`.

Any selected provider who speaks directly through Questlog uses the generic five-state presenter PNG system defined in `docs/V5_PRESENTER_SYSTEM_AUTHORITY.md`. Provider identity and presenter identity may be the same character without creating duplicate quest state.

#### DESTROYED

The Overlord rejects accommodation and deliberately kills the canonical Umvuthi.

The native `mowziesmobs:kill_umvuthi` advancement and player-kill acquisition of the Sol Visage are source-backed signals for the destructive terminal route.

The result is local to the canonical Grove. Other Umvuthana Groves remain extant and independent.

The Sol Visage and its player-owned Umvuthana follower mechanic are post-destruction consequences. They are not prerequisites or substitutes for the living-Umvuthi SUBJUGATED route.

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
-> suitable local Villager becomes Mayor of Spree
-> required local provider cast is bound or completed as needed
-> Spree civilization questline begins
```

The Mayor title continues source-faithful Spree civic vocabulary without asserting that one unchanged municipal institution survived for centuries.

The approved compact Spree cast is:

```text
Mayor of Spree -> civic authority
Vanilla Farmer -> food and agriculture pillar
VillagersPlus Miner -> commerce and specialist-work pillar
Guard Villager -> security pillar
```

V5 must not add filler providers merely to represent additional Villager professions.

The Guard Villager source surface includes native recruitment, the `guards_made` statistic, owner state, following state, equipment access, and patrol/checkpoint behavior. Those mechanics are preferred over a fabricated generic defense state when the final objective pass can use them cleanly.

The approved terminal architecture is:

- `NEUTRAL`: prove that all three civic pillars can remain viable without Overlord ownership, then explicitly recognize Spree's independence;
- `SUBJUGATED`: make selected critical parts of those same pillars materially dependent on Overlord resources, access, protection, opportunities or authority, then secure the Mayor's acceptance of supremacy while he remains civic administrator;
- `DESTROYED`: remove the Mayor and irreversibly break the exact indispensable civic and defensive functions selected by the source-backed objective pass, ending present-day Spree as the canonical polity without requiring every Villager or village block to be destroyed.

The detailed provider and objective authority is recorded in `docs/V5_SPREE_CIVILIZATION_BLUEPRINT.md`.

The remaining Spree questions are source/objective details: anchor-start action, Mayor eligibility and the smallest trackable Farmer, Miner and Guard actions that prove each route. They are not unresolved provider identities or political structure.

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

The Myrmex technical boundary and terminal political mapping are finalized across `docs/V5_ICE_AND_FIRE_CAMPAIGN_AUTHORITY.md` and `docs/V5_MYRMEX_CIVILIZATION_BLUEPRINT.md`.

Native Ice & Fire opinion remains source-owned and never automatically selects a terminal political state.

The approved inner routes are:

- `NEUTRAL`: learn native caste behavior, raise the canonical hive to 50+ opinion through source-backed actions, gain trade access, complete a meaningful authored trade/colony relationship sequence, then explicitly recognize the Queen and hive as independent;
- `SUBJUGATED`: reach 75+ opinion, gain legitimate Myrmex Staff command access for the existing canonical hive, perform at least one meaningful non-destructive hive command, then complete a Queen-facing submission resolution;
- `DESTROYED`: deliberately turn against the canonical hive and kill its Queen, with any additional colony target included only if source analysis proves it indispensable to the polity.

After terminal resolution, native opinion must not fall below 50 for `NEUTRAL` or 75 for `SUBJUGATED`.

A player-founded 100-opinion Queen colony is not used as a substitute for conquering the existing canonical hive.

The remaining Myrmex work is technical/objective detail, including exact opinion actions, trade surfaces, Staff commands, hive-state signals and the least invasive implementation of the opinion floors.

## 13. Piglins

The Piglin civilization anchor is one runtime-selected Piglin-built Nether Village. A suitable native Piglin Brute is bound to the authored title `Chieftain` and serves as local political authority.

The approved political spine is territorial inheritance versus the returning Master.

Piglins expanded during the Silence while Minion numbers and territorial dominance declined. Their canonical village is genuinely Piglin-built and Piglin-held present territory. Their descent from an ancient Minion and pig union, and their close recognition of Minions as cousin-people, are political context rather than automatic Overlord ownership.

The approved terminal architecture is:

- `NEUTRAL`: learn the village's gold, barter and protected-property customs, establish meaningful exchange, and explicitly recognize the legitimacy and independence of the Piglin-built polity;
- `SUBJUGATED`: establish meaningful control, access or indispensable leverage over gold access, barter wealth and village-resource foundations, then force the Chieftain to accept the returned Master as superior sovereign;
- `DESTROYED`: deliberately violate the village's protected gold/property order, defeat the Chieftain, remove any additional indispensable local function only if source-backed objective analysis proves it necessary, and end the selected village as a functioning polity.

Once `SUBJUGATED`, the canonical village must be safe for the Overlord regardless of gold armor and must permit local protected-container access and local gold extraction without native retaliation. Native barter and approved village services remain usable.

The installed Nether Villages mod adds village worldgen and functional-looking structures but no parallel Piglin profession or reputation system. V5 must not invent Piglin professions merely because the village contains a forge, mine, storage, farm or tower.

The detailed authority is recorded in `docs/V5_PIGLIN_CIVILIZATION_BLUEPRINT.md`, with the exact artifact and native mechanical audit in `docs/V5_PIGLIN_NATIVE_AUDIT_2026-09-16.md`.

The remaining Piglin work is technical/objective detail, not unresolved political structure.

## 14. Production reconciliation rule

This document is V5 campaign authority only.

Do not reconcile current production quests, Java, configs, provider code, anchor persistence, NPC spawning, or world-state persistence until the V5 campaign authority pass is complete and approved.
