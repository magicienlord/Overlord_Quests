# OVERLORD REIGN V5 Dwarf, Gnumu and Goblin Route Allocation Authority

Status: APPROVED V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this file records explicit Overlord approvals that refine `V5_DWARVEN_CIVILIZATION_BLUEPRINT.md`, `V5_GNUMU_CIVILIZATION_BLUEPRINT.md`, and `V5_GOBLIN_CIVILIZATION_BLUEPRINT.md`. It is campaign authority, not production implementation.

## 1. Dwarven route allocation

The Forge-Thane remains the canonical hold's political starter and terminal political authority.

The approved inner-route allocation uses three distinct recurring functions:

1. Historical-record keeper
   - preserves the surviving serious record of Golden Hills continuity;
   - reveals the independent-legitimacy path;
   - supports recovery or validation of enough historical continuity for the successor hold to stand credibly on its own.

2. Second Dwarven Forger
   - distinct from the Forge-Thane;
   - exposes the hold's productive, rune-craft and forge dependencies;
   - reveals leverage suitable for SUBJUGATED.

3. Dwarven Warrior
   - exposes military, defensive and command vulnerabilities of the selected hold;
   - reveals the destructive route without reducing it to an arbitrary Dwarf kill counter.

The terminal meanings are therefore:

```text
historical continuity restored without Overlord ownership -> NEUTRAL
forge / productive legitimacy placed under Overlord leverage -> SUBJUGATED
defensive and governing continuity deliberately broken -> DESTROYED
```

The optional Dwarf-Kobold rivalry chain may add leverage, consequences or alternate details to later Dwarven choices, but remains non-mandatory and does not set Dwarven disposition by itself.

The exact source-native presentation of the historical-record keeper remains a technical/cast-selection question. V5 must not invent a species-wide archivist profession unless separately approved.

## 2. Gnumu Vintage authority

Vintage technology is established as genuine ancestral Gnumu technology.

The present canonical Gnumu settlement retains artifacts, materials and partial practical knowledge, but no longer understands or produces the technology at its former level.

The exact supplied Gnumus build supports this through source-owned surfaces including:

```text
gnumus:vintage_metal
gnumus:vintage_alloy_ingot
gnumus:vintage_gnumus_pickaxe
gnumus:vintage_gnumus_cutlass
gnumus:hammer_vintage_universal_tool
gnumus:gnumus_totem
```

and native advancement/progression signals including:

```text
gnumus:antiquity_shard
gnumus:vintage_blacksmith
gnumus:vintage_improvement
gnumus:vintage_technology
gnumus:weight_loss_protection
```

`gnumus:antiquity_shard` is technically verified as an inventory trigger for obtaining at least one `gnumus:vintage_metal`. Several later Vintage advancements are source-awarded through mod procedures rather than ordinary vanilla criteria.

### 2.1 Ancestry-chain use

Vintage remains are approved as the material entry point for the common Gnumu ancestry investigation.

Campaign meaning:

```text
recover genuinely old Gnumu Vintage material
-> establish that present Gnumu technology descends from an older regional tradition
-> examine surviving magical / cultural evidence such as the Gnumus Totem where technically appropriate
-> combine that evidence with Gnarl's surviving Mellow Hills institutional memory
-> establish the approved truth that regional Gnumus descend from Halflings altered over generations by Gluttony magic
-> choose ANCESTRY_REVEALED or ANCESTRY_WITHHELD
```

The exact proof sequence must remain source-compatible. The mod's own Vintage items prove antiquity and technological continuity, not the Halfling/Gluttony origin by themselves. That historical interpretation belongs to established REIGN canon and Gnarl's institutional memory.

### 2.2 Political use

The partial loss of Vintage knowledge may support later NEUTRAL and SUBJUGATED provider content because it is a real settlement dependency and opportunity.

It must not become the only thing that defines Gnumu society, and completing native Vintage progression must not automatically choose a terminal disposition.

## 3. Goblin route allocation

The Goblin Leader remains political starter and terminal authority until a destructive route explicitly permits removal.

### 3.1 NEUTRAL providers

The principal NEUTRAL provider pair is:

```text
Main Merchant
AND
Camp Bartender
```

Their native commerce and tavern systems establish the practical case that peaceful coexistence with the Overlord is profitable.

The route culminates in an explicit independent trade/nonaggression settlement with the Leader. Merchant or liquor accomplishments alone do not write NEUTRAL.

### 3.2 SUBJUGATED providers

The principal SUBJUGATED pillars are:

```text
Camp Engineer
AND
Camp Blacksmith
AND
Main Merchant
```

They represent, respectively:

```text
technology / engineering
arms / productive martial supply
commerce / material circulation
```

Each pillar must culminate in a specific authored dependency, obligation or patronage fact rather than a generic provider count.

The Leader submits when the camp's important material institutions are demonstrably better served by, or dependent upon, Overlord patronage and refusing him has become materially irrational.

The installed Goblins Tyranny build contains a real `blacksmith_goblin` entity and native Blacksmith GUI/service surface. V5 therefore does not invent the profession merely to fill the route.

### 3.3 DESTROYED provider or obstacle

The `champion_goblin` is approved as the key inner martial provider/obstacle that reveals the camp's destructive military path.

The exact final sabotage/combat sequence remains to be selected from source-backed camp, engineering, bomb and combat mechanics.

The route must deliberately break the selected camp's governing and functional continuity. It must not be an arbitrary Goblin kill quota.

## 4. Presenter consequence

Any of the above recurring NPCs that receive direct Questlog speech enter the final presenter roster and use the universal visual states:

```text
neutral
pleased
assertive
concerned
hostile
```

Exact presenter art remains deferred until the complete V5 speaking roster is frozen.

## 5. Production boundary

These decisions refine V5 campaign authority only.

Do not reconcile current production quest definitions, provider bindings, NPC spawning, disposition mutations or objective code until the V5 authority pass is complete and explicitly approved for implementation.
