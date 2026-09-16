# OVERLORD REIGN V5 Gnumu Civilization Blueprint

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is a focused civilization blueprint under `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md` and `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md`. It records explicit V5 decisions approved by the Overlord and exact source surfaces from the supplied Gnumus build.

## 1. Canonical anchor identity

The canonical Gnumu anchor is one eligible generated principal settlement, preferably the source-owned large settlement substrate:

```text
large_gnumus_settlement
```

The selected settlement occupies the old Mellow Hills / Halfling regional identity for REIGN continuity, but it is a present-day Gnumu polity rather than a preserved Halfling settlement.

The regional Gnumus descend from Halflings altered over generations by misuse of Gluttony magic.

The local Gnumus do not begin the campaign knowing this ancestry.

## 2. Runtime anchor lock

The deliberate anchor-start action is a Questlog provider interaction with a source-valid Gnumu Shaman inside an eligible principal settlement.

Required pattern:

```text
find an eligible principal Gnumu settlement
-> deliberately use the Questlog provider interaction on a gnumus:gnumus_shaman inside it
-> validate that no Gnumu anchor is already locked
-> bind that generated settlement as the canonical Gnumu anchor
-> bind the interacted Shaman as the Elder Shaman
-> bind or spawn the minimum additional recurring providers required by the blueprint
-> expose the Gnumu civilization Questlog
```

The interacted Shaman becomes Elder Shaman only for the canonical settlement. Ordinary shamans elsewhere remain ordinary shamans.

## 3. Political spine

The approved Gnumu model deliberately avoids a manufactured crisis.

The canonical settlement is a functioning society. Its civilization arc is about understanding how that society works, what it values, what sustains it, what it has forgotten and what the Overlord chooses to do with that knowledge.

The native mod provides enough ordinary social and material life to carry this structure:

- shamans;
- merchants;
- workers;
- hunters;
- farming and settlement infrastructure;
- Gnumus Doubloon commerce;
- Vintage technology;
- food and local creature progression;
- relic, totem and local-craft material.

These are ingredients, not automatic political outcomes.

## 4. Required recurring cast

### 4.1 Elder Shaman

Source basis:

```text
gnumus:gnumus_shaman
```

Authored role:

```text
Elder Shaman of the canonical settlement
```

The Elder Shaman is the political starter and final settlement authority for terminal resolution.

The role is local. V5 does not establish that every Gnumu shaman is an Elder Shaman.

### 4.2 Main Merchant

Source basis:

```text
gnumus:gnumus_merchant
```

Purpose:

- represents local exchange and settlement prosperity;
- provides source-backed commerce through the native Gnumus Doubloon system;
- may expose economic dependencies or opportunities relevant to political resolution.

The exact source-owned `gnumus:business_approach` advancement remains useful as a durable native commerce signal.

### 4.3 Hunter representative

Source basis may use the exact native Gnumu hunter entity family where a recurring provider is needed.

Purpose:

- represents settlement defense and hunting life;
- may carry content around Big Snout, local safety, food or hunting competence;
- does not create a new military caste or species-wide command structure.

The detailed authoring pass must select the persistence-safe native hunter variant rather than assuming every hunter instance is suitable for a recurring Questlog provider.

## 5. Common internal ancestry chain

The ancestry investigation is approved as common internal Gnumu civilization content before terminal political resolution.

It is not a separate questline outside the civilization Questlog.

Approved structure:

```text
Gnumu contact established
-> discover evidence that the settlement's ancestry is older and stranger than local memory admits
-> recover or interpret concrete Mellow Hills / Gluttony evidence
-> establish that the regional Gnumus descend from altered Halflings
-> player chooses whether to reveal the truth to the Elder Shaman or withhold it
-> write one remembered ancestry decision
-> continue toward NEUTRAL / SUBJUGATED / DESTROYED
```

The two remembered ancestry outcomes are:

```text
ANCESTRY_REVEALED
OR
ANCESTRY_WITHHELD
```

This choice is independent of civilization disposition.

It is not automatically Good or Evil and does not itself select a terminal political state.

Later dialogue or route consequences may inspect the remembered ancestry choice only where explicitly authored.

## 6. NEUTRAL route

The independent route demonstrates that the settlement can remain viable without Overlord rule.

Approved meaning:

```text
understand the settlement's ordinary economy, traditions and material life
-> help resolve or strengthen specific practical weaknesses without taking ownership of the result
-> complete the common ancestry chain
-> final audience with Elder Shaman
-> deliberately recognize the canonical settlement as independent
-> NEUTRAL
```

The route must feel like a functioning community successfully left to govern itself, not like a defeated enemy temporarily spared.

## 7. SUBJUGATED route

Subjugation uses knowledge gained through ordinary settlement life rather than a fabricated emergency.

Approved structure:

```text
understand the settlement's economy, technology, shamanic authority and defenses
-> expose one or more specific dependencies or leverage points through provider content
-> complete the common ancestry chain
-> bring the critical dependency, service, resource or political pressure under Overlord control
-> confront the Elder Shaman with the settlement's practical dependence on the Overlord
-> Elder Shaman accepts Overlord supremacy while remaining local leader
-> SUBJUGATED
```

Potential consequences already authorized at the civilization level include:

- safe settlement use;
- merchant benefits;
- Shaman services or quest rewards;
- tribute;
- continued civilization-specific provider access.

V5 does not require a continuous numeric Gnumu reputation system.

## 8. DESTROYED route

The destructive route deliberately dismantles the canonical settlement using knowledge accumulated through the same civilization arc.

Approved meaning:

```text
learn how the settlement sustains and defends itself
-> deliberately exploit those known vulnerabilities
-> destroy its leadership and functional local polity
-> DESTROYED
```

The exact gameplay actions remain to be selected from source-backed Gnumu mechanics. The route must not reduce to a generic kill counter.

The result is local to the canonical settlement. Other Gnumus and unaffected Halflings remain extant elsewhere.

## 9. Source-owned objective surfaces available for detailed authoring

The exact supplied Gnumus build provides durable actions including:

```text
gnumus:business_approach
gnumus:plan_boar
gnumus:vintage_blacksmith
gnumus:vintage_improvement
gnumus:vintage_technology
gnumus:hearty_dish
gnumus:weight_loss_protection
gnumus:worthy_traders_hat
```

The mod also provides workers, hunters, shamans, merchants, Large Gnumus Settlement, ordinary settlements, farms, ruins, Shaman huts, Trader's Rest and other local structures.

These signals may support the detailed quest skeleton only when the authored political meaning is clear.

V5 must not turn the civilization arc into an exhaustive native advancement checklist.

## 10. Presenter use

The Elder Shaman and any recurring provider who receives direct Questlog dialogue enter the final presenter roster.

They use the universal five-state PNG vocabulary:

```text
neutral
pleased
assertive
concerned
hostile
```

## 11. Remaining authoring boundary

The political model and common ancestry placement are approved.

Still unresolved are the exact gameplay actions that establish:

- which practical weaknesses are strengthened for NEUTRAL;
- the precise dependency or leverage package used by SUBJUGATED;
- the exact source-backed vulnerability sequence used by DESTROYED;
- the exact evidence objects/actions used to prove the Mellow Hills / Gluttony ancestry discovery.

Those must be derived from the supplied modpack and existing REIGN history where possible. Any remaining choice that creates new campaign meaning must return to the Overlord before becoming V5 authority.

## 12. Production boundary

This blueprint is V5 campaign authority only.

Do not reconcile current production quests, provider bindings, anchor tags, objective definitions or disposition code until the V5 campaign authority pass is complete and approved.