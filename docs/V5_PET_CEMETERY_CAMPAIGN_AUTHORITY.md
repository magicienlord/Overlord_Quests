# OVERLORD REIGN V5 Pet Cemetery Campaign Authority

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is the focused V5 authority for the conditional Pet Cemetery adventure allocated by `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md`. The older `docs/PET_CEMETERY_SIDEQUEST.md` records current production behavior and remains implementation evidence only where it does not conflict with this V5 authority.

## 1. Campaign role

Pet Cemetery remains a small conditional Adventure arc.

It is not visible as an ordinary open questline before one of the Overlord's supported tamed pets actually dies.

Mortis is the principal presenter.

The purpose of the arc is to turn a specific pet death into one contained demonstration of the installed mod's own resurrection system. It does not establish routine resurrection for sapient beings and does not rewrite the wider REIGN rules for souls, ghosts, undeath, or necromancy.

## 2. Opening condition

The opening condition remains an actual death of a supported pet owned by the Overlord.

Production already has a narrow owner-aware death objective capable of expressing this condition through the Pet Cemetery supported-pet tag. V5 preserves that accomplishment rather than replacing it with inventory possession or a generic pet-death condition.

Required authored meaning:

```text
one supported tamed pet owned by the Overlord dies
-> Pet Cemetery arc becomes available
```

A different player's pet, an unsupported tameable, or merely possessing a Pet Collar must not satisfy the opening condition.

## 3. Approved resurrection sequence

The V5 closer is the full restoration of the same dead pet, not merely its first undead return.

Approved structure:

```text
supported owned pet dies
-> recover that pet's generated Pet Collar
-> use that collar through the native charged Respawn Anchor resurrection path
-> the same recorded pet returns in Pet Cemetery's native zombie-pet state
-> cure that zombie pet through the native Pet Cemetery cure path
-> the same pet returns to its living form
-> native cured_zombie_pet progression signal closes the authored arc
```

The sequence deliberately follows the mod's native identity-preserving collar loop.

The first undead resurrection is an intermediate campaign accomplishment, not the final V5 closer.

## 4. Objective and state discipline

V5 should prefer native/source-owned signals at each step where they prove the exact accomplishment.

Known usable surfaces include:

- owner-aware supported tame death for the opening;
- recovery of the generated Pet Collar;
- the native successful Respawn Anchor resurrection signal for the undead return;
- the native `cured_zombie_pet` progression signal for final restoration.

The exact registry path for the cure progression signal remains a technical-resolution detail if not already pinned by the implementation pass. The V5 campaign meaning is fixed regardless of that identifier.

Do not create a parallel generic resurrection state machine in Questlog when Pet Cemetery already owns the pet identity and transformation state.

Do not allow a collar from an unrelated pet to satisfy the intended same-pet narrative if the later technical pass can track the generated collar identity narrowly enough.

If exact same-pet correlation requires a narrow compatibility bridge, that is permitted as technical implementation work because the authored requirement is now explicit.

## 5. Skeleton-pet boundary

Pet Cemetery also contains a further native path in which an undead pet can be resurrected again into a skeleton-pet state.

That path is not required by the V5 Mortis arc.

It remains:

- optional native play;
- eligible for sparse contextual Rambling if later presenter allocation justifies it;
- prohibited as an added mandatory step merely to lengthen the questline.

## 6. Presenter role

Mortis presents the arc as a contained demonstration of restoring a lost servant-companion through the world's available funerary magic.

Mortis does not establish that every death in REIGN can or should be reversed.

Final dialogue is deferred. This authority fixes only the campaign role, sequence, and boundaries.

## 7. Persistent consequence

Ordinary quest completion is sufficient once the specific pet has returned alive.

No global resurrection-unlocked fact is required unless later V5 authoring identifies a concrete follow-up that must remember it.

The restored pet itself and Pet Cemetery's native state remain the principal world consequence.

## 8. Production boundary

This document is V5 campaign authority only.

Do not modify the current production Pet Cemetery definitions during V5 authoring. During later reconciliation, the existing production arc must be extended or rewritten so that its authored completion occurs after the approved cure-to-living closer rather than after undead resurrection alone.
