# OVERLORD REIGN V5 Sea Dweller Civilization Blueprint

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is a focused civilization blueprint under `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md` and `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md`. It records explicit V5 decisions approved by the Overlord and source-backed mechanics from Realm RPG Sea Dwellers 2.9.9.

## 1. Canonical anchor

The canonical Sea Dweller polity is one runtime-selected Sea Village.

The village is one independent underwater community. Other Sea Villages and wild Sea Dwellers remain unaffected by its political resolution.

The civilization starts through one suitable barter-capable native Sea Dweller inside an eligible Sea Village. That individual receives the local authored title:

```text
Sea Elder
```

The exact native profession used for the final Sea Elder binding remains an implementation choice only if the source permits more than one suitable senior trader. V5 does not invent a new entity class.

Required pattern:

```text
find eligible Sea Village
-> deliberately use Questlog provider interaction on suitable local senior trader
-> verify no Sea Dweller anchor is already locked
-> bind village as canonical Sea Dweller anchor
-> bind interacted provider as Sea Elder
-> bind or spawn required local profession providers
-> expose Sea Dweller civilization Questlog
```

## 2. Political spine

The approved Sea Dweller political identity centers on local sovereignty over:

```text
underwater resources
professions and civic labor
protected property and settlement customs
trade relationships
```

The source already expresses these themes mechanically through Aquamarine, fish and Nautilus barter, profession Seashells, Depth metallurgy, profession-specific caskets and native retaliation against violations of protected Sea Lanterns.

V5 must use those systems rather than adding a numeric Sea Dweller reputation simulation.

## 3. Required recurring cast

### 3.1 Sea Elder

Authored role:

- civilization starter;
- local political authority;
- final authority for NEUTRAL and SUBJUGATED resolution.

### 3.2 Blacksmith

Source profession:

```text
Blacksmith
```

Purpose:

- represents Depth metallurgy and equipment production;
- is one of the two mandatory personal commitment pillars used by SUBJUGATED.

### 3.3 Collector

Source profession:

```text
Collector
```

Purpose:

- source-owned Nautilus barter gives this role a distinct resource function;
- represents acquisition and circulation of valuable underwater resources;
- is one of the two mandatory personal commitment pillars used by SUBJUGATED.

### 3.4 No third mandatory submission provider

V5 does not require a third recurring provider merely to create symmetry with other civilization arcs.

Worker, Architect, Hunter, Farmer or other native professions may appear in local characterization, NEUTRAL content, optional provider content or technical support where justified, but none is a mandatory SUBJUGATED political pillar by default.

The Sea Dweller submission route instead combines two explicit personal commitments with broader institutional leverage over the village's profession and material systems.

## 4. NEUTRAL route

The independent route establishes mutual trade while respecting Sea Dweller sovereignty.

Approved structure:

```text
anchor locked
-> learn and respect local protected-property customs
-> complete meaningful native Aquamarine barter
-> complete meaningful fish and/or Nautilus barter through appropriate providers
-> learn how profession and resource systems sustain the Sea Village
-> avoid converting those systems into Overlord control
-> final agreement with Sea Elder
-> establish independent trade and nonaggression
-> NEUTRAL
```

A native barter milestone alone does not choose the political state. The explicit agreement is required.

## 5. SUBJUGATED route

The Overlord gains leverage over the village by controlling critical professions and material flows.

Approved principal ingredients:

```text
profession Seashell system
Aquamarine commerce
Depth metallurgy
Blacksmith obligation
Collector obligation
```

The personal political commitments are exactly:

```text
Blacksmith commitment
AND
Collector commitment
```

The profession Seashell system, Aquamarine commerce and Depth metallurgy provide the broader institutional leverage that makes those two commitments politically consequential. They are not additional provider-count requirements.

Approved meaning:

```text
anchor locked
-> learn the village's professions and resource economy
-> use or master the native profession-assignment system through authored content
-> establish control, access or indispensable patronage over key material flows through Aquamarine commerce and Depth metallurgy
-> secure explicit Blacksmith obligation tied to the village's productive/metallurgical function
-> secure explicit Collector obligation tied to valuable resource acquisition/circulation
-> demonstrate that the village's profession and material systems now operate under decisive Overlord leverage
-> confront Sea Elder with the village's practical dependence on those compromised systems
-> Sea Elder retains local office but accepts Overlord supremacy
-> SUBJUGATED
```

The profession Seashell mechanic is political leverage only through the authored route. Simply using a Seashell does not automatically subjugate anyone.

V5 must not add a Worker, Architect, Hunter, Farmer or other third provider as a mandatory commitment merely to reach a fixed provider count.

## 6. DESTROYED route

The destructive route deliberately violates the village's protected order and turns native retaliation into part of the confrontation.

The source-backed opening act is the deliberate desecration of protected Sea Dweller property through a Sea Lantern violation where the detailed quest sequence calls for it.

Strong source signal:

```text
seadwellers:adv_break_sea_lantern
```

Native behavior already responds with Mermorph Rage.

Approved structure:

```text
choose destructive route
-> deliberately violate protected civic property through authored Sea Lantern objective
-> trigger native hostility and retaliation
-> fight through the resulting confrontation
-> remove Sea Elder authority and the civilization-specific indispensable functions selected by final objective authoring
-> Sea Village ceases to function as the canonical polity
-> DESTROYED
```

This is deliberate desecration, not accidental griefing. A random Sea Lantern broken before the destructive route is active must not silently settle civilization politics.

The route does not require block-by-block village destruction.

## 7. Possible Elven ancestry

Sea Dwellers may descend from Elves and may preserve some memory of that ancestry.

This remains historical characterization within the Sea Dweller civilization content. It is not a separate questline, does not determine terminal disposition, and does not automatically tie the canonical Sea Village to Everlight, Empire Harbor or another named historical coast.

Any new claim about the exact migration, transformation process, ancient settlement or historical institution must return to the Overlord before becoming V5 authority.

## 8. Native objective surfaces

Exact source-owned accomplishment signals include:

```text
seadwellers:adv_barter_aquamarine
seadwellers:adv_barter_fish
seadwellers:adv_barter_nautilus
seadwellers:adv_break_sea_lantern
seadwellers:adv_depth_ingot
seadwellers:adv_open_casket
seadwellers:adv_turn_villager_into_mermorph
seadwellers:adv_use_seashell
```

These are objective ingredients, not a checklist.

V5 should use only those that express the approved political structure. In particular, turning a Villager into a Mermorph is not automatically part of the civilization arc merely because the advancement exists.

## 9. Presenter use

The Sea Elder, Blacksmith and Collector enter the final presenter roster if they receive direct Questlog speech.

Other native professions enter the roster only if finalized optional content assigns them direct Questlog speech. They are not mandatory submission presenters.

All use:

```text
neutral
pleased
assertive
concerned
hostile
```

## 10. Remaining authoring boundary

The political spine and submission cast are approved.

Still unresolved are:

- the exact source-native profession selected for the Sea Elder if multiple candidates remain viable;
- the exact objective sequence linking profession control, Aquamarine commerce and Depth metallurgy into the approved institutional leverage;
- the exact Blacksmith obligation that expresses metallurgical/productive dependence;
- the exact Collector obligation that expresses resource-circulation dependence;
- the exact indispensable functions removed after the Sea Lantern-triggered destructive confrontation;
- the extent and presentation of remembered Elven ancestry beyond the already-approved broad possibility.

A third mandatory submission provider is not an open question.

Purely technical questions should be solved from source. New political or historical meaning must return to the Overlord.

## 11. Production boundary

This document is V5 campaign authority only. Do not reconcile production content, profession binding, provider spawning, disposition handling, native Rage compatibility or presenter assets until V5 authority is complete and approved.
