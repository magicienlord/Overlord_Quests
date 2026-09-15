# NightWalker / Lestat Quest Integration

Status: IMPLEMENTED AGAINST SUPPLIED ALPHA.3

This integration implements the conditional vampire-transition sidequest established by OVERLORD REIGN authority. Nycto remains the operative vampirism ruleset. Lestat is REIGN-native contextual quest presentation and does not require a permanent physical provider entity.

## Exact technical source

```text
nycto-forge-1.20.1-overlordreign-1.0.0-alpha.3.jar
SHA-256 a24de1fb23b83bc15263e40511782a8b46f6dd151b55e541e8727dfa0fe21503
mod id: nycto
version: 1.0.0-alpha.3
Minecraft: 1.20.1
Forge: 47.4.10+
```

The supplied implementation establishes the owner-state contract:

```text
player persistent-data root: Nycto
completed vampire flag: Nycto.vampire
Vampire Altar purchase mask: Nycto.powerMask
choosable powers represented by mask: 13
runtime identity guard: nycto:vampirism effect
```

The temporary `nycto:vampirism` effect is not used as proof of completed transformation. Questlog observes the durable `Nycto.vampire` flag.

## Optional compatibility architecture

Questlog does not link to Nycto Java classes and does not declare NightWalker as a hard dependency.

`NightwalkerState` reads only Nycto-owned persistent data behind a runtime-presence guard. Two custom objectives expose the owner state to Quest definitions:

```text
overlord_reign:nightwalker_vampire
overlord_reign:nightwalker_power_count
```

`nightwalker_vampire` latches a legitimately observed completed transformation so a later cure does not erase historical quest progress.

`nightwalker_power_count` reads the real `powerMask` and records the greatest observed purchased-power count, allowing sequence-breaking if the player bought a power before Lestat's final lesson.

## Contextual Lestat boundary

The final Lore authority does not require Lestat to exist as a permanently spawned quest-provider entity in the Dark Tower. Questlog therefore does not require a Nycto vampire entity, a dedicated Lestat anchor tag, a protection tag or fixed coordinates to progress this arc.

Lestat enters through contextual quest presentation after the player becomes a vampire. His continuing presence is represented by the authored historical fact `overlord_reign:personal/nightwalker/lestat_joined_tower`, not by an entity UUID contract.

This prevents world-placement details from becoming a technical blocker while preserving the in-universe result that Lestat voluntarily remains around the Tower as a vampire adviser.

## Production sidequest

The conditional sequence is:

1. `campaign/sidequests/nightwalker/lestat_arrives`
   - unlock condition: campaign foundation plus real completed NightWalker vampirism;
   - contextual read acknowledgement presents Lestat's arrival;
   - establishes `overlord_reign:personal/nightwalker/lestat_joined_tower`.
2. `campaign/sidequests/nightwalker/hunger_is_a_fact`
   - requires actual use of one supported Nycto blood-bottle variant while on the vampire path.
3. `campaign/sidequests/nightwalker/the_vampire_altar`
   - requires interaction with `nycto:vampire_altar`.
4. `campaign/sidequests/nightwalker/choose_the_price`
   - recognizes at least one real choosable Vampire Altar power in `Nycto.powerMask`;
   - establishes `overlord_reign:personal/nightwalker/transition_guided`.

Supported alpha.3 blood-bottle IDs:

```text
nycto:blood_bottle
nycto:player_blood_bottle
nycto:vampire_blood_bottle
nycto:player_vampire_blood_bottle
```

The arc intentionally stops after first deliberate progression. It does not duplicate NightWalker's deeper power, weakness, hunger, cure or combat systems inside Questlog.

## Character and continuity boundary

Lestat is native to OVERLORD REIGN. He has no awareness of television continuity, Minecraft, mods or game abstractions.

Nycto controls operative vampire mechanics. Adopted character authority controls characterization without importing unsupported vampire powers or cosmology.

Gnarl remains the institutional Tower adviser. Lestat occupies the narrower domain of lived vampiric existence and does not replace Gnarl, command Minions, outrank the Overlord or automatically enter a romantic relationship with the player character.

## Runtime qualification protocol

1. Install the exact alpha.3 build above with the Quest build under test.
2. Confirm a non-vampire player does not unlock `A Guest with Fangs` merely by possessing vampire blood.
3. Complete Nycto transformation and verify the quest becomes available from real `Nycto.vampire` state without spawning a Lestat entity.
4. Acknowledge the contextual arrival and confirm `overlord_reign:personal/nightwalker/lestat_joined_tower` is written.
5. Drink one supported blood-bottle variant and confirm the hunger lesson advances.
6. Interact with `nycto:vampire_altar` and confirm the altar lesson advances.
7. Purchase at least one choosable altar power and confirm `Choose the Price` resolves.
8. Repeat with a save where the first power was purchased before the final lesson and confirm retrospective recognition.
9. Cure after transition was already observed and confirm historical quest progress is not erased.
10. Remove Nycto and confirm Questlog still boots; NightWalker objectives must remain unsatisfied rather than crash or fabricate state.

Full-instance presentation remains a manual qualification item in the complete OVERLORD REIGN instance.
