# OVERLORD REIGN V5 Kobold Civilization Blueprint

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is a focused civilization blueprint under `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md` and `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md`. It records explicit V5 decisions approved by the Overlord and source-backed mechanics from the exact installed Kobolds 2.12.0 build.

## 1. Canonical anchor

The canonical Kobold polity is one runtime-selected Kobold Den.

The Den is one independent underground Kobold community. It is not the capital of all Kobolds and does not govern Pirate Kobolds by default.

The anchor is selected when the player deliberately uses the Questlog provider interaction on a native Kobold Captain inside an eligible Den.

Required pattern:

```text
find eligible Kobold Den
-> deliberately interact with local Kobold Captain through Questlog provider flow
-> verify no Kobold anchor is already locked
-> bind this Den as the canonical Kobold anchor
-> bind this Captain as the local political authority
-> bind or spawn the required Engineer, Enchanter and Warrior roles
-> expose the Kobold civilization Questlog
```

Ordinary Captains and Dens elsewhere remain independent.

## 2. Political spine

The approved Kobold political problem is competence and dependency.

Kobold society is generally limited in intelligence and strategic sophistication, but several specialists are materially sharper within their own domains. The canonical Den therefore depends disproportionately on a small number of competent roles.

The exact source-backed pillars are:

```text
Captain -> political authority and valuable-resource exchange
Engineer -> machinery, redstone and practical technical competence
Enchanter -> magical and enchanting competence
Warrior -> organized physical defense
```

This asymmetry is the political pressure point. It must not become a numeric competence meter or a claim that ordinary Kobolds are incapable of all independent life.

## 3. Required recurring cast

### 3.1 Captain

Source entity:

```text
kobolds:kobold_captain
```

Role:

- civilization starter;
- local political ruler;
- final authority for NEUTRAL or SUBJUGATED resolution;
- source-owned valuables exchange provides a real material relationship with the player.

### 3.2 Engineer

Source entity:

```text
kobolds:kobold_engineer
```

Role:

- technical specialist;
- represents the Den's machinery and practical infrastructure;
- required political pillar for the approved NEUTRAL and SUBJUGATED structures.

### 3.3 Enchanter

Source entity:

```text
kobolds:kobold_enchanter
```

Role:

- magical specialist;
- represents the Den's enchanting and potion capability;
- required political pillar for the approved NEUTRAL and SUBJUGATED structures.

### 3.4 Warrior

Source entity:

```text
kobolds:kobold_warrior
```

Role:

- martial provider or obstacle;
- exposes defensive and command vulnerabilities relevant to DESTROYED.

## 4. NEUTRAL route

The independent route strengthens the Den without taking ownership of its critical specialists.

Approved structure:

```text
anchor locked
-> work with Captain and learn the Den's resource economy
-> complete meaningful Engineer content that strengthens or demonstrates technical self-sufficiency
-> complete meaningful Enchanter content that strengthens or demonstrates magical self-sufficiency
-> establish that the Den can continue functioning without Overlord control
-> final Captain settlement
-> deliberately recognize the Den as independent
-> NEUTRAL
```

The route is an affirmative independence resolution. It is not merely friendly contact.

## 5. SUBJUGATED route

The Overlord uses the Den's dependence on its smartest specialists as political leverage.

Approved structure:

```text
anchor locked
-> identify the Engineer and Enchanter as critical specialist pillars
-> complete their distinct inner provider chains
-> secure explicit obligation or dependency from Engineer
-> secure explicit obligation or dependency from Enchanter
-> gain leverage over important treasure or resource flows through the Captain's native economy
-> optional Dwarf-Kobold rivalry facts may strengthen or alter leverage
-> confront Captain with the Den's dependence on Overlord-backed specialists and resources
-> Captain retains local office but accepts Overlord supremacy
-> SUBJUGATED
```

The specialist chains are named dependencies, not a generic requirement to complete arbitrary sidequests.

No new automated Kobold mining or military simulation is required.

## 6. DESTROYED route

The destructive route is Warrior-centered and uses knowledge of the Den's defenses and command structure.

Approved meaning:

```text
learn the Den's defensive organization through the Warrior route
-> reveal the destructive political option
-> deliberately remove the Captain and the civilization-specific indispensable functions selected by the final objective pass
-> canonical Den can no longer operate as the authored polity
-> DESTROYED
```

The exact indispensable targets beyond the Captain remain to be selected from source-backed mechanics. V5 must not reduce this route to a generic Kobold kill count or require block-by-block demolition.

Other Kobold Dens and Pirate Kobolds remain extant.

## 7. Dwarf-Kobold rivalry

The approved shared rivalry chain is optional.

It may become available after discovering both the Golden Hills successor hold and the canonical Kobold Den.

Its outcomes may:

- favor one side;
- force an arrangement;
- extort both sides;
- worsen the dispute for Overlord advantage;
- unlock or alter leverage and consequences inside later Dwarven or Kobold resolutions.

It must not directly write either civilization's terminal disposition and must not be required to finish either civilization arc.

## 8. Technical objective rule

Kobolds 2.12.0 does not expose a useful native advancement layer for Captain, Engineer or Enchanter transactions.

Where V5 requires proof of a specific provider interaction, the later implementation may use a narrow Questlog compatibility bridge tied to the exact source interaction. Do not replace the native transaction system with a parallel simulation.

## 9. Presenter use

Captain, Engineer, Enchanter and Warrior enter the final presenter roster if their finalized chains assign direct Questlog speech.

All use the universal five-state presenter vocabulary:

```text
neutral
pleased
assertive
concerned
hostile
```

## 10. Remaining authoring boundary

The political spine and provider roles are approved.

Still unresolved are only the exact objective actions that establish:

- technical self-sufficiency for NEUTRAL;
- Engineer obligation for SUBJUGATED;
- Enchanter obligation for SUBJUGATED;
- the exact Captain resource leverage used by SUBJUGATED;
- the exact indispensable functions removed by DESTROYED;
- the detailed optional Dwarf-Kobold rivalry quest sequence.

Those are to be resolved from source and existing authority where possible. Any remaining choice that creates new campaign meaning must return to the Overlord.

## 11. Production boundary

This document is V5 campaign authority only. Do not reconcile production quest definitions, provider binding code, compatibility bridges, disposition code or NPC spawning until V5 authority is complete and approved.