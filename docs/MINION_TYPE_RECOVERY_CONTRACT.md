# Traditional Minion Recovery Contract

Status: IMPLEMENTATION CONTRACT

This document records how `Overlord_Quests` implements the four traditional Minion-type recovery responsibility defined by the current OVERLORD REIGN quest authorities while leaving durable capability state in the owning Minion implementation.

## Ownership boundary

`Overlord_Quests` owns the authored recovery path, prerequisites, dialogue, narrative facts, and the decision to invoke an unlock hook.

`Overlord_Minions` owns the durable Minion slot state and summon gating. Questlog does not persist a duplicate Red / Green / Blue unlock bit.

The current Minion API also deliberately treats Brown differently: Brown is the Master's Staff bootstrap and rejects `unlock(BROWN)`. Questlog therefore does not manufacture a second Brown unlock path.

## Brown

Production path:

- `campaign/opening/restore_browns.json`
- `campaign/opening/browns_return.json`

The player forges the Master's Staff, which is the implemented Brown bootstrap. Gnarl's existing confirmation now records `overlord_reign:minions/brown_recovered` as narrative state. No `questlog:unlock_minion` reward is used for Brown.

## Red, Green, and Blue

The owning Minion API currently enforces strict slot order:

`RED -> GREEN -> BLUE`

Questlog mirrors that technical invariant rather than pretending the hooks are semi-open.

Each non-Brown recovery uses two definitions:

1. a gameplay recovery task ending in an auto-claimed `questlog:unlock_minion` reward;
2. a Gnarl confirmation that requires both completion of that task and `questlog:minion_unlocked` for the same slot before writing the narrative recovery fact.

This matters when the optional Minion API is absent, temporarily incompatible, or rejects an unlock. A quest may have reached its objective boundary, but the narrative confirmation cannot claim the tribe has returned until the owning server-side Minion state says that slot is actually unlocked.

Production chain:

- `campaign/expansion/restore_reds.json` -> `campaign/expansion/reds_return.json`
- `campaign/expansion/restore_greens.json` -> `campaign/expansion/greens_return.json`
- `campaign/expansion/restore_blues.json` -> `campaign/expansion/blues_return.json`

The field tests use ordinary Minecraft materials associated with the established specialties of the three traditional tribes. They are progression tests, not fictional ingredients consumed by the Minion implementation and not claims about new Minion mechanics.

## Narrative facts

The confirmation quests write:

- `overlord_reign:minions/brown_recovered`
- `overlord_reign:minions/red_recovered`
- `overlord_reign:minions/green_recovered`
- `overlord_reign:minions/blue_recovered`

These facts are narrative/history markers only. They do not replace Minion slot state.

## Validation

`tools/validate_minion_recovery_contract.py` guards the production definitions and the local bridge contract. CI runs it through `.github/workflows/minion-recovery-contract.yml`.

The validator rejects, among other things:

- a production `unlock_minion` reward targeting Brown;
- missing Red / Green / Blue API unlock rewards;
- broken Red -> Green -> Blue quest ordering;
- a recovery confirmation that omits the server-authoritative `minion_unlocked` prerequisite;
- narrative recovery facts written before the corresponding API state is confirmed;
- production definitions omitted from the bundled index;
- accidental expansion of the Questlog bridge to make Brown a second API-controlled slot.
