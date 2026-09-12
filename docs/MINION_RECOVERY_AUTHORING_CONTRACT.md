# OVERLORD REIGN Minion Recovery Authoring Contract

Status: ACTIVE AUTHORING BOUNDARY

Implementation state: MINION CAPABILITY BRIDGE READY / RED, GREEN, AND BLUE PRODUCTION RECOVERY SCENARIOS NOT YET AUTHORED

## 1. Purpose

This document constrains production authoring for the Red, Green, and Blue Minion recovery milestones.

The technical unlock bridge is already implemented against `Overlord_Minions` Build #118. The remaining work is narrative and world-facing: determine source-faithful present-day recovery scenarios without inventing facts that have not been adopted into OVERLORD REIGN.

This document is deliberately a boundary record rather than a concealed campaign outline. It records what may safely be implemented and what must remain unresolved until supported by canon, source evidence, or validated world mechanics.

## 2. Authority

Authoring must reconcile all of the following:

1. `magicienlord/Overlord_Lore_and_Canon` as read-only lore and source authority;
2. `docs/CAMPAIGN_AUTHORING_CONTRACT.md` for campaign-state and spoiler rules;
3. `docs/MINION_UNLOCK_INTEGRATION.md` for cross-mod ownership;
4. the public `OverlordMinionProgression` API in `magicienlord/Overlord_Minions` Build #118;
5. verified current modpack mechanics before a native advancement, structure, item, biome, boss, or dimension signal is used as a production objective.

The implementation repository must not resolve lore UNKNOWNs merely because a technically convenient objective exists.

## 3. Established REIGN facts

The following constraints are established and may be relied upon by production content:

- Brown, Red, Green, and Blue remain the four traditional Minion tribes.
- The traditional Hives survive into the current era.
- During the Silence, the Minion population withdrew into the Netherworld and became scattered or dormant, with ordinary spawning impaired in the absence of an active Master.
- The vanilla Nether is the Netherworld in OVERLORD REIGN.
- The current Overlord begins the process of restoring the Minion forces.
- Brown is the bootstrap tribe and is restored through the Master's Staff path owned by the Minion implementation.
- Later capability order is fixed as Brown, Red, Green, Blue.
- Red, Green, and Blue are campaign-earned capabilities.
- In the original games, Hive recovery is associated with restoration of the corresponding tribe's availability.
- OVERLORD REIGN does not require the player to physically carry a Hive back as the implementation mechanic. The authored recovery milestone may establish the equivalent restored state through a REIGN-specific sequence once that sequence is actually defined.

The exact diegetic process for present-day Red, Green, and Blue reactivation remains unresolved.

## 4. Source precedents, not present-day location canon

Original-game recovery material may constrain tone, identity, ordering, and environmental logic. It does not automatically establish current REIGN geography.

### 4.1 Red source precedent

The Overlord 1 source corpus associates Red recovery with the Hells Kitchen sequence and state records including:

```text
D1_FINDREDS
D1_REDMINIONS
TOWER_REDHIVE
```

The Red tribe's source identity includes fire association, ranged combat, and fire immunity.

These references are evidence for the original recovery pattern. They do not establish a present-day REIGN location named Hells Kitchen, a mandatory fire-biome objective, or a specific mod integration.

### 4.2 Green source precedent

The Overlord 1 source corpus associates Green recovery with the Green Cave or Viridian Caverns sequence and state records including:

```text
D2_GREENLAIR
D2S1_GREENHIVE
TOWER_GREENHIVE
```

The Green tribe's source identity includes stealth, back attacks, and poison immunity.

These references are evidence for the original recovery pattern. They do not establish a present-day REIGN Viridian Caverns location, a mandatory poison-biome objective, or a specific mod integration.

### 4.3 Blue source precedent

The Overlord 1 source corpus associates Blue recovery with the Blue Cave or Moist Hollows sequence and local state records including:

```text
D3_BLUECAVE
D3S1_SAVEBLUES
D3S1_SERPENT
D3S1_GEYSER
D3S1_BLUEHIVE
TOWER_BLUEHIVE
```

The Blue tribe's source identity includes magical aptitude, water traversal, relative combat fragility, and resurrection.

The source sequence has a meaningful local order, but the source dependency evidence does not justify promoting every local event into a formal hard quest dependency. REIGN authoring must preserve meaningful environmental progression without converting source implementation details into an artificial checklist corridor.

These references do not establish a present-day REIGN Moist Hollows location, a mandatory aquatic boss, a mandatory geyser mechanic, or a specific mod integration.

## 5. Technical unlock contract

The permanent capability owner is `Overlord_Minions`.

Production unlock rewards must use the Questlog integration surface rather than modifying Minion state directly.

### Red

The final Red recovery milestone must grant:

```json
{
  "type": "questlog:unlock_minion",
  "slot": "red",
  "auto_claim": true
}
```

It may only become reachable after the Brown bootstrap state is established through the production campaign.

### Green

The final Green recovery milestone must require both:

- completion of the authored Red recovery milestone;
- authoritative owner state showing Red unlocked through `questlog:minion_unlocked`.

Its final capability reward must grant Green through `questlog:unlock_minion`.

### Blue

The final Blue recovery milestone must require both:

- completion of the authored Green recovery milestone;
- authoritative owner state showing Green unlocked through `questlog:minion_unlocked`.

Its final capability reward must grant Blue through `questlog:unlock_minion`.

### Ownership rules

Questlog must not:

- maintain a duplicate Red, Green, or Blue unlocked boolean;
- mutate Minion roster internals directly;
- assume the API call needs to be non-idempotent;
- manufacture a parallel narrative fact whose only meaning is that the Minion slot is unlocked.

Narrative facts remain available for distinct historical consequences, but Minion capability state itself belongs to `Overlord_Minions`.

## 6. Sequence-break policy

A recovery milestone should observe native world progress retroactively where the player could legitimately satisfy its underlying requirement before receiving the formal quest.

Use verified Questlog history surfaces where appropriate:

- exact item craft statistics;
- exact entity kill statistics;
- native advancements;
- dimension history;
- structure history;
- bounded position history;
- explicit narrative facts;
- authoritative Minion owner state.

Do not invent an artificial collectible or duplicate a native advancement merely to make a recovery sequence trackable.

If a local source-inspired sequence contains several environmental steps, distinguish between:

- actual hard prerequisites required by world logic;
- observations that can be recognized after the fact;
- presentation beats that do not need their own quest node.

## 7. Authoring prohibitions

Until a production recovery scenario is resolved, do not:

- assign Red, Green, or Blue to arbitrary color-themed biomes solely because the theme matches;
- import an original-game named location into current REIGN geography without explicit adoption;
- claim the player physically carries a Hive unless that mechanic is explicitly adopted;
- assign Theurgy, Ars Elixirum, Biomancy, Eidolon, or another native mod a mandatory recovery role simply because its theme is convenient;
- invent a boss, structure, advancement, item ID, dimension gate, or provider role that has not been verified;
- turn every source-local event into a hard quest prerequisite;
- create a hidden universal morality, friendship, domination, or reputation variable around Minion recovery;
- use a civilization disposition as a surrogate Minion progression flag;
- use a Questlog narrative fact as a duplicate Minion unlock state.

## 8. Remaining UNKNOWNs

The following remain intentionally unresolved:

- the exact present-day diegetic reactivation mechanism for Red;
- the exact present-day diegetic reactivation mechanism for Green;
- the exact present-day diegetic reactivation mechanism for Blue;
- the exact world anchors, structures, or local environments used for those recoveries;
- which verified native mod signals, if any, should serve as recovery objectives;
- the exact Gnarl dialogue for those milestones;
- whether each color uses one principal quest or a short local sequence;
- whether and how Hive restoration receives a visible world-state manifestation;
- whether any source location is deliberately adopted into current REIGN geography for a recovery.

These unknowns must not be filled by implementation convenience.

## 9. Promotion rule

A Red, Green, or Blue production recovery scenario may be authored when its defining scenario is constrained by at least one authoritative basis and remains compatible with the others:

- an explicit Overlord decision;
- an adopted decision in `Overlord_Lore_and_Canon`;
- source evidence strong enough to support the adapted event without importing unresolved geography;
- a verified current-modpack progression signal that fits already established lore;
- a validated final-world anchor created for that purpose.

Once a recovery scenario is promoted into production content, update together:

- bundled campaign definitions and index;
- `docs/MINION_UNLOCK_INTEGRATION.md`;
- this authoring contract;
- the production campaign validator;
- Minion progression validation fixtures or tests where necessary;
- `docs/NARRATIVE_FACTS.md` only if the recovery establishes a reusable historical truth distinct from the Minion capability itself.

## 10. Runtime evidence still required

Static integration is not the final validation boundary.

The completed production sequence must eventually be validated in the full modpack for:

```text
Brown bootstrap
-> Red recovery and unlock
-> save/reload
-> Green recovery and unlock
-> save/reload
-> Blue recovery and unlock
-> save/reload
```

Validation must also cover:

- rejected out-of-order unlock attempts;
- idempotent repeated handoff attempts;
- Questlog state reconciliation after login;
- owner-state prerequisites after reload;
- absence of duplicate Questlog Minion persistence;
- any world-state or Hive presentation added by the eventual recovery scenarios.

Until those production quests exist, Build #118 static bridge validation remains the authoritative technical baseline.
