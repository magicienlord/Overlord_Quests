# OVERLORD REIGN V5 Civilization Anchor-Start Reconciliation

Status: V5 AUTHORITY RECONCILIATION / BATCH 01

Date: 2026-09-16

Purpose: remove any remaining ambiguity created by older civilization-authority wording after the Overlord's explicit Batch 01 decisions Q050-Q052.

## 1. Authoritative rule

For every civilization covered by the V5 civilization system, the canonical local anchor is selected through the same authored line-start action:

```text
find a viable generated local anchor
-> deliberately place the approved civilization-start banner at that anchor
-> validate that the candidate is eligible and that no canonical anchor for that civilization is already bound
-> permanently bind that generated settlement / structure / hive as the canonical local anchor for the playthrough
-> select and bind suitable existing quest-role NPCs where possible
-> spawn any required quest-role NPCs that are missing
-> begin that civilization's authored questline
```

This universal banner-placement action applies across peaceful civilizations, initially hostile civilizations, and Myrmex.

The banner event is an authored civilization-line activation trigger. It is not merely an implementation convenience.

## 2. Explicit supersession

The following older formulations in `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md` are superseded and must not be used as authority:

- `perform the civilization's explicit anchor-start action inside it` where that wording implies a civilization-specific start action;
- `The exact anchor-start action may differ by civilization`;
- the Umvuthana-specific claim that mask-mediated Umvuthi contact itself is the canonical anchor-start signal;
- the Spree placeholder `deliberate Villager anchor-start action` where it leaves the action undefined;
- any statement that anchor-start action remains an open source/objective question.

Source-specific contact mechanics may still occur after banner placement as the first local quest beat, prerequisite, provider interaction, or route content if separately approved. They do not replace the universal banner placement as the civilization-line start and anchor-binding trigger.

## 3. Scope

This reconciliation does not decide:

- the banner item's exact registry ID or visual design;
- the exact viable-anchor validation predicate for each civilization;
- the activation trigger of later provider or route quests;
- any terminal disposition objective;
- rewards.

Those remain subject to their existing V5 authority and later explicit decisions.

## 4. Implementation boundary

This is campaign-authoring authority only. It does not authorize production implementation.

Later implementation must translate the approved universal banner action into the least invasive reliable detector without replacing it with a different player-facing event.