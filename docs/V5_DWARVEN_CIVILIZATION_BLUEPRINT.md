# OVERLORD REIGN V5 Dwarven Civilization Blueprint

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is a focused civilization blueprint under `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md` and `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md`. It records explicit V5 decisions approved by the Overlord and source-backed implementation constraints from the exact installed Dwarven Forge 1.0.0 build.

## 1. Canonical anchor identity

The canonical Dwarven polity is the present Golden Hills / Golden Halls successor hold.

It is not Goldo's restored kingdom. It is a reduced successor community descended from Dwarven survivors whose institutions, records and visible political continuity were broken by Glorious Empire anti-magic persecution.

The exact installed mod provides the generated structure:

```text
dwarven_forge:dwarven_village
```

One eligible generated Dwarven village becomes the canonical successor hold for the playthrough.

No fixed coordinates or pre-authored world placement are required.

## 2. Runtime anchor lock

The deliberate anchor-start action uses the existing Questlog provider gesture on a source-valid Dwarven Forger inside an eligible generated Dwarven village.

Required pattern:

```text
find an eligible dwarven_forge:dwarven_village
-> identify a native dwarven_forge:dwarf with minecraft:toolsmith profession inside the settlement
-> deliberately use the Questlog provider interaction on that Forger
-> validate that no Dwarven anchor is already locked
-> bind the generated settlement as the canonical Golden Hills successor hold
-> bind the interacted Forger as the local Forge-Thane
-> bind or spawn the minimum additional required recurring cast
-> expose the Dwarven civilization Questlog
```

The interacted Forger becomes Forge-Thane only for the selected anchor. Ordinary Dwarven Forgers elsewhere remain ordinary Forgers.

## 3. Required recurring cast

### 3.1 Forge-Thane

Source basis:

```text
entity: dwarven_forge:dwarf
native profession: minecraft:toolsmith
source display role: Dwarven Forger
```

Authored political role:

```text
Forge-Thane of the canonical successor hold
```

The Forge-Thane is the political starter and final authority for the anchor's independent or submitted resolution.

### 3.2 Historical-record provider

Established REIGN authority requires one designated Dwarf who retains the surviving serious records of the Dwarven glory days.

This is a recurring authored role, not a new species-wide Dwarven profession.

The exact native profession or entity presentation for this individual remains to be selected from the installed Dwarven population during detailed objective authoring. V5 must not claim that ordinary Dwarves retain complete institutional memory.

## 4. Political spine

The approved Dwarven political problem is heritage and legitimacy.

The successor hold possesses fragments of Golden Hills identity but has not fully recovered the political legitimacy, institutional memory or material security of the old Dwarven state.

Inner provider content must expose two broad pressure families:

```text
historical legitimacy
AND
economic / forge viability
```

These are not hidden numeric meters. The campaign writes only explicit named facts needed by later route logic.

The optional Dwarf-Kobold rivalry chain may create additional leverage or alter route consequences, but it is never required to resolve the Dwarven civilization.

## 5. NEUTRAL route

The independent route helps the successor hold become credible enough to stand on its own.

Approved structure:

```text
anchor locked
-> recover or validate meaningful surviving Golden Hills history
-> strengthen or demonstrate the hold's current forge/economic viability
-> resolve any required local legitimacy problem without placing the result under Overlord ownership
-> final audience with Forge-Thane
-> deliberately recognize the successor hold as independent
-> NEUTRAL
```

The route must make independence an affirmative political result, not merely a failure to conquer the Dwarves.

The Overlord may have materially helped restore the hold and still choose to leave it autonomous.

## 6. SUBJUGATED route

Subjugation uses the same weaknesses differently.

Approved structure:

```text
anchor locked
-> expose the sources of Forge-Thane legitimacy
-> expose the economic / forge dependencies that keep the successor hold viable
-> secure specific leverage over one or both
-> optional Dwarf-Kobold rivalry facts may strengthen or modify the available leverage
-> confront Forge-Thane with the hold's dependence on Overlord-controlled legitimacy, security, resources or opportunity
-> Forge-Thane retains office but accepts Overlord supremacy
-> SUBJUGATED
```

The hold survives as a useful subject polity rather than being reduced to ruins.

Established low-overhead consequences may include:

- safe access to the canonical hold;
- improved or unlocked Dwarven trades;
- Dwarven rune/equipment access where specifically authored;
- tribute;
- provider and service access.

The installed Dwarf trade path inherits native Villager reputation pricing, so an authored favorable-price consequence may reuse source-owned mechanics rather than creating a parallel Dwarven reputation system.

Native reputation never chooses the political state by itself.

## 7. DESTROYED route

The destructive route deliberately extinguishes the canonical successor hold as a functioning political continuation of Golden Hills.

Approved meaning:

```text
learn the hold's political and functional vulnerabilities
-> deliberately attack the anchor through those vulnerabilities
-> remove its governing continuity and functional center
-> DESTROYED
```

The exact gameplay actions that constitute destruction must be selected from source-backed Dwarven mechanics during detailed objective authoring. V5 must not collapse this route into an arbitrary Dwarf kill counter.

The result is local to the canonical successor hold. Dwarves continue to exist elsewhere.

## 8. Source-owned mechanics available for detailed authoring

The exact installed Dwarven Forge build provides:

- `dwarven_forge:dwarven_village` as the generated settlement substrate;
- native Dwarf professions, including the `minecraft:toolsmith` Dwarven Forger;
- Dwarven armor, tools and weapons;
- lesser, normal and greater rune smithing recipes;
- amethyst-centered native commerce;
- player-specific Villager-derived reputation pricing;
- Dwarven farms, barracks, mines, forge houses and other generated village components.

Its packaged advancements are recipe-unlock advancements rather than strong accomplishment signals. Detailed V5 objectives should therefore use exact craft statistics, item state, provider actions or other Questlog-supported signals instead of treating recipe unlocks as mastery proof.

## 9. Presenter use

The Forge-Thane and historical-record provider enter the final presenter roster if V5 assigns them direct Questlog speech.

They use the universal five-state presenter vocabulary:

```text
neutral
pleased
assertive
concerned
hostile
```

## 10. Remaining authoring boundary

The political spine is approved and must not be reopened without an explicit change.

Still unresolved at this stage are only the exact gameplay actions used to prove:

- recovered historical legitimacy;
- restored or demonstrated forge/economic viability;
- the specific leverage used by SUBJUGATED;
- the exact irreversible destructive acts used by DESTROYED.

Those choices must be derived from the installed mod surfaces and existing REIGN history where possible. Any choice that establishes new campaign meaning rather than a technical implementation detail must return to the Overlord before being promoted to V5 authority.

## 11. Production boundary

This blueprint is V5 campaign authority only.

Do not reconcile current production quests, provider tags, NPC protection, price mutation, objective definitions or world-state code until V5 campaign authority is complete and approved.