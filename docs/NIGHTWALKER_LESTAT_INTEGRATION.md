# NightWalker / Lestat Quest Integration

Status: IMPLEMENTED AGAINST SUPPLIED ALPHA.3

This integration implements the mandatory conditional vampire-transition sidequest established by the OVERLORD REIGN authority. It treats Nycto as the operative vampirism ruleset and Lestat as a REIGN-authored Tower character layered onto a real Nycto vampire entity.

## Exact technical source

Supplied build:

```text
nycto-forge-1.20.1-overlordreign-1.0.0-alpha.3.jar
SHA-256 a24de1fb23b83bc15263e40511782a8b46f6dd151b55e541e8727dfa0fe21503
mod id: nycto
version: 1.0.0-alpha.3
Minecraft: 1.20.1
Forge: 47.4.10+
```

The packaged artifact contains the real `VampireData`, `BloodBottleItem` and `VampireAltarBlock` classes. Source inspection of the matching alpha.3 implementation establishes the relevant owner-state contract:

```text
player persistent-data root: Nycto
completed vampire flag: Nycto.vampire
Vampire Altar purchase mask: Nycto.powerMask
choosable powers represented by mask: 13
runtime identity guard: nycto:vampirism effect
```

The temporary `nycto:vampirism` effect begins/transports the transformation state; it is not used as proof that transformation completed. The quest objective observes the durable `Nycto.vampire` flag instead.

Alpha.3 has no packaged `data/nycto/advancements` progression tree. Questlog therefore does not fabricate a Nycto advancement merely to obtain a convenient trigger.

## Optional compatibility architecture

Questlog does not link to Nycto Java classes and does not declare NightWalker as a hard dependency.

`NightwalkerState` checks for the registered `nycto:vampirism` effect and reads only Nycto-owned persistent data. Two custom objectives expose that state to Quest definitions:

```text
overlord_reign:nightwalker_vampire
overlord_reign:nightwalker_power_count
```

`nightwalker_vampire` recognizes a completed transformation and latches the observation. A later cure cannot retroactively erase that the transition legitimately occurred.

`nightwalker_power_count` reads the real `powerMask` and records the greatest observed purchased-power count. This allows sequence-breaking: a player who already bought a power before reaching Lestat's final lesson is not forced to buy another one.

## Lestat world anchor

Lestat is represented by one real:

```text
nycto:vampire
```

with both authored tags:

```text
overlord_anchor:lestat
overlord_quest_protected
```

This means only that this specific Nycto vampire is the REIGN-native Lestat. It does not mean Nycto contains a built-in Lestat character or that ordinary Nycto vampires share his biography or role.

Exact Tower coordinates are not stored in Questlog. World integration should place or otherwise provide this protected entity at the Dark Tower in a way consistent with the final Tower build. Lestat is a later court/personnel addition, not a formal Tower Restoration requirement.

## Production sidequest

The conditional sequence is:

1. `campaign/sidequests/nightwalker/lestat_arrives`
   - unlock condition: real completed NightWalker vampirism;
   - establishes `overlord_reign:personal/nightwalker/lestat_joined_tower`.
2. `campaign/sidequests/nightwalker/hunger_is_a_fact`
   - requires actual use of one supported Nycto blood-bottle variant while on the vampire path.
3. `campaign/sidequests/nightwalker/the_vampire_altar`
   - requires interaction with `nycto:vampire_altar`.
4. `campaign/sidequests/nightwalker/choose_the_price`
   - recognizes at least one real choosable Vampire Altar power in `Nycto.powerMask`;
   - establishes `overlord_reign:personal/nightwalker/transition_guided`.

The four blood-bottle IDs accepted by the hunger lesson are the alpha.3 registry surface:

```text
nycto:blood_bottle
nycto:player_blood_bottle
nycto:vampire_blood_bottle
nycto:player_vampire_blood_bottle
```

The arc intentionally stops after first deliberate progression. It does not duplicate NightWalker's deeper power, weakness, hunger, cure or combat systems inside Questlog.

## Character and continuity boundary

Dialogue follows the transcript-derived Lestat writing authority and the later REIGN continuity ledger. Lestat is native to OVERLORD REIGN. He has no awareness of television continuity, Minecraft, mods or game abstractions.

Nycto controls operative vampire mechanics. Source-character material controls characterization where adopted by REIGN authority; it does not import unsupported vampire powers or cosmology.

Gnarl remains the institutional Tower adviser. Lestat occupies the narrower domain of lived vampiric existence and does not replace Gnarl, command Minions, outrank the Overlord or automatically enter a romantic relationship with the player character.

## Runtime qualification protocol

1. Install the exact alpha.3 build identified above with the Quest build under test.
2. Confirm a non-vampire player does not unlock the Lestat arrival quest merely by possessing vampire blood.
3. Complete Nycto's transformation and verify `A Guest with Fangs` becomes available through the real `Nycto.vampire` state.
4. Use the tagged protected `nycto:vampire` Lestat anchor to complete the arrival interaction.
5. Drink one supported blood-bottle variant and confirm the hunger lesson advances.
6. Interact with `nycto:vampire_altar` and confirm the altar lesson advances.
7. Purchase at least one choosable altar power and confirm `Choose the Price` resolves.
8. Repeat with a save where the first power was purchased before the final lesson; confirm retrospective owner-state recognition.
9. Cure after the transition was already observed and confirm historical quest progress is not erased.
10. Remove Nycto and confirm Questlog still boots; the NightWalker objectives must remain unsatisfied rather than crash or fabricate state.
11. Confirm an untagged ordinary `nycto:vampire` does not act as Lestat.
12. Confirm the Lestat anchor remains protected from ordinary accidental combat.

Full-instance presentation and Tower placement still require manual qualification in the complete OVERLORD REIGN instance.
