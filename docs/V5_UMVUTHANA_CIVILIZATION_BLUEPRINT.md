# OVERLORD REIGN V5 Umvuthana Civilization Blueprint

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is a focused civilization blueprint under `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md` and `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md`. It records explicit decisions approved by the Overlord during V5 authoring. Technical source facts are supported by `docs/V5_UMVUTHANA_NATIVE_RELATIONSHIP_AUDIT_2026-09-16.md`.

## 1. Canonical anchor selection

The canonical Umvuthana anchor is one eligible generated Grove centered on a living Umvuthi.

The Grove is selected at runtime. No fixed coordinates or pre-authored world placement are required.

The deliberate anchor-start action is:

```text
find an eligible generated Umvuthana Grove
-> wear an Umvuthana mask
-> deliberately initiate legitimate interaction with that Grove's Umvuthi
-> permanently bind that Grove as the canonical Umvuthana anchor for the playthrough
-> write AUDIENCE_ESTABLISHED as an intermediary campaign fact
-> bind or spawn the required recurring Grove cast
-> expose the civilization Questlog and its inner provider progression
```

Mere proximity, accidental combat, ordinary Grove entry, or possession of a mask does not select an anchor.

`AUDIENCE_ESTABLISHED` is not a terminal disposition.

## 2. Recurring Grove cast

The approved recurring cast is deliberately small and uses exact native entity distinctions.

### 2.1 Grove Trader

Source entity basis:

```text
EntityUmvuthanaMinion
```

Authored role:

```text
Grove Trader / everyday local contact
```

Purpose:

- represents ordinary exchange and practical local interests;
- may expose provider content tied to trade, supply, local obligations, or the Grove's daily life;
- is not a mandatory political pillar of the SUBJUGATED route.

The source entity's persisted mask identity is not a political office. V5 does not define any mask as a trader caste or council rank.

### 2.2 Grove Healer

Source entity basis:

```text
EntityUmvuthanaCrane
```

Authored role:

```text
Grove Healer / healing and support authority
```

Purpose:

- represents the Grove's healing and support function;
- owns one of the two approved SUBJUGATED provider chains;
- that chain must end by compromising this pillar through an explicit allegiance, obligation, dependency, or equivalent personal commitment to the Overlord.

The Crane's healer identity is source-backed. No additional priestly or political caste is implied.

### 2.3 Grove Raptor

Source entity basis:

```text
EntityUmvuthanaRaptor
```

Authored role:

```text
canonical Grove martial and pack authority
```

Purpose:

- represents the Grove's organized martial force;
- commands its own native followers;
- remains source-classified as devoted to Umvuthi before authored progression changes its political commitment;
- owns one of the two approved SUBJUGATED provider chains;
- that chain must end by compromising this pillar through an explicit allegiance, obligation, dependency, or equivalent personal commitment to the Overlord.

This role is local to the selected canonical Grove.

V5 does not establish that all Raptors are Grove generals, that every Grove contains a formal Raptor office, or that Raptors form a civilization-wide military institution.

### 2.4 Missing-role spawning authority

Native Grove generation guarantees Umvuthi but does not guarantee that both required political provider entity types are present.

After the canonical Grove has been deliberately selected, later implementation is explicitly authorized to spawn a missing native Crane and/or native Raptor and bind each spawned entity to the approved quest role.

Existing suitable native entities should be reused where possible.

This authorization exists only to guarantee the authored provider cast for the selected canonical Grove. It does not change ordinary world generation or establish that every natural Grove always contains those roles.

## 3. Civilization Questlog structure

The Umvuthana civilization Questlog is the political anchor presentation.

Its terminal resolution is:

```text
NEUTRAL
OR
SUBJUGATED
OR
DESTROYED
```

The three routes share the legitimate-audience opening but then diverge through authored inner content.

The native mask, native trade state, native Sun's Blessing, provider quest completion, and native boss kill signals are implementation ingredients. None of them automatically chooses a terminal state without the authored route resolution.

## 4. NEUTRAL route

The independent-peace route uses Umvuthi's exact native peaceful relationship.

Required structure:

```text
AUDIENCE_ESTABLISHED
-> complete Umvuthi's configured first exchange of seven Gold Blocks
-> receive Sun's Blessing
-> complete the short authored Grove relationship sequence
-> deliberately recognize the canonical Grove as independent
-> NEUTRAL
```

The native successful trade causes Umvuthi to persistently remember the player and permits later Sun's Blessing replenishment without repeating the initial payment.

Those native mechanics express the settled peaceful relationship after the authored resolution.

The first exchange or Sun's Blessing alone must not write `NEUTRAL`.

## 5. SUBJUGATED route

Subjugation is political and religious rather than a special combat-state intervention.

The Overlord compromises the two approved practical pillars of Umvuthi's local control through separate meaningful provider chains:

```text
Grove Healer commitment
AND
Grove Raptor commitment
```

The Crane chain represents healing and support authority.

The Raptor chain represents martial and pack authority.

Each chain must end in an explicit authored allegiance, obligation, dependency, or equivalent personal commitment to the Overlord. The exact concrete objectives must be selected from source-backed mechanics in the later objective pass rather than invented here.

The Grove Trader may provide characterization or practical content, but no Trader completion or commitment is required for political submission.

This is not a hidden reputation system and not a generic requirement such as completing two arbitrary sidequests.

The route resolves as:

```text
AUDIENCE_ESTABLISHED
-> complete the Crane provider chain
-> record Grove Healer commitment
-> complete the Raptor provider chain
-> record Grove Raptor commitment
-> unlock final audience with the living Umvuthi
-> demonstrate that Umvuthi can no longer rely on unquestioned control of the Grove's support and martial pillars
-> Umvuthi chooses preservation of himself and the Grove over further loss of control
-> Umvuthi publicly acknowledges the Overlord as the superior ruler
-> SUBJUGATED
```

Umvuthi remains the Umvuthana creator-god and immediate local ruler.

Politically, Umvuthi and the canonical Grove now rule beneath the Overlord rather than independently of him.

The humiliation is intentional: a creator-god is forced to acknowledge a higher temporal sovereign because two critical practical pillars of the society he created have been brought under that sovereign's influence.

### 5.1 Prohibited implementation

The SUBJUGATED route must not require:

- intercepting Umvuthi's boss health;
- converting near-death into surrender;
- canceling his native death to preserve him;
- creating a synthetic defeated-but-alive boss state;
- obtaining the Sol Visage first.

The native Sol Visage is player-kill loot from Umvuthi and therefore belongs to the destructive route and its aftermath, not to peaceful submission.

## 6. DESTROYED route

The destructive route deliberately kills the canonical Umvuthi and irreversibly destroys that Grove as the selected polity.

Strong native signals include:

```text
mowziesmobs:kill_umvuthi
player-kill acquisition of mowziesmobs:sol_visage
```

The route applies only to the selected canonical Grove. Other Umvuthana Groves remain extant and independent.

The Sol Visage and its player-owned Umvuthana follower mechanic are post-destruction consequences, not evidence that all surviving Umvuthana have been globally subjugated.

## 7. Presenter system use

Umvuthi and any recurring provider character assigned direct Questlog speech may enter the final presenter roster.

The Crane and Raptor must be treated as presenter candidates because they own mandatory authored provider chains on the SUBJUGATED route.

Any such speaking character uses the universal five-state PNG vocabulary:

```text
neutral
pleased
assertive
concerned
hostile
```

Provider identity and presenter identity may be the same character without creating duplicate quest state.

The final asset-generation pass occurs only after the full V5 speaking roster is known.

## 8. Implementation discipline

The later implementation should use sparse explicit state:

```text
umvuthana_anchor_locked
AUDIENCE_ESTABLISHED
grove_healer_commitment
grove_raptor_commitment
terminal disposition
```

Trader state should persist only if a finalized authored chain genuinely needs later reference.

Only facts genuinely required by authored follow-up should persist.

Do not introduce:

- a hidden Grove loyalty meter;
- a generic provider completion score;
- a new continuous Umvuthana reputation simulation;
- a fabricated Grove council institution;
- civilization-wide political effects outside the selected anchor.

## 9. Production boundary

This blueprint is V5 campaign authority only.

Do not reconcile current production quests, provider bindings, NPC spawning, disposition code, presenter assets, or Mowzie compatibility code until V5 campaign authority is complete and approved.
