# Goblin Civilization First-Contact Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: first formal contact with the designated Goblin civilization anchor only.

This protocol validates `campaign/civilizations/goblins/first_contact` without requiring final-world coordinates. It does not validate later Goblin political branches, disposition outcomes, camp services, tribute, destruction, or final terrain integration.

## Authority boundary

The current lore authority establishes:

- the principal Goblin civilization anchor is one major Goblin Camp;
- the civilization main quest begins through a designated `goblins_tyranny:leader_goblin`;
- ordinary Goblin leaders and other camps do not automatically become civilization quest starters;
- the chosen camp is a local polity and does not represent all Goblins globally;
- exact final-world placement may be assigned later.

The production definition therefore uses the authored identity tag:

```text
overlord_anchor:goblin_main
```

Quest-critical protection is a separate concern and uses:

```text
overlord_quest_protected
```

Do not substitute the protection tag for the anchor identity tag.

## Preconditions

Use the target OVERLORD REIGN Forge 1.20.1 instance with the validated OVERLORD QUESTS artifact and the installed Goblins Tyranny version used by the project source audit.

Use an unpublished local single-player test world with commands available.

Before testing, back up the world or use a disposable validation world.

## Clean state

Reset the production contact fact if a previous validation run set it:

```text
/questlog narrative fact clear overlord_reign:civilizations/goblins/contact_established
```

Clear Goblin disposition to the technical unresolved state:

```text
/questlog narrative disposition clear overlord_reign:goblins
```

The expected effective state is:

```text
questlog:unresolved
```

For an isolated first-contact test, establish the already-authored campaign foundation directly:

```text
/questlog narrative fact set overlord_reign:reign/initial_foundation_established
```

Using the administrative command here is test setup only. It does not replace normal campaign progression in production play.

## Create or identify the test anchor

Use a naturally generated leader from the intended Goblin Camp when available. For isolated mechanics testing, a summoned leader is acceptable:

```text
/summon goblins_tyranny:leader_goblin ~ ~ ~
```

Stand close to the intended test entity, then apply the production anchor identity:

```text
/tag @e[type=goblins_tyranny:leader_goblin,sort=nearest,limit=1,distance=..8] add overlord_anchor:goblin_main
```

Apply quest-critical protection separately:

```text
/tag @e[type=goblins_tyranny:leader_goblin,sort=nearest,limit=1,distance=..8] add overlord_quest_protected
```

For a final-world integration pass, the selected leader and camp must be the actual authored principal Goblin anchor rather than an arbitrary nearby spawn.

## Check 1: unrelated Goblins do not expose the civilization quest

Find or summon a second `goblins_tyranny:leader_goblin` without `overlord_anchor:goblin_main`.

Sneak and interact with it using the main hand.

Expected result:

- `The Goblin Camp` is not offered by the unrelated leader;
- no Goblin contact fact is written;
- Goblin disposition remains `questlog:unresolved`.

This is the primary locality guard. Entity type alone must never select the principal civilization polity.

## Check 2: designated leader exposes the quest only after foundation

Clear the foundation fact temporarily:

```text
/questlog narrative fact clear overlord_reign:reign/initial_foundation_established
```

Sneak and main-hand interact with the tagged principal leader.

Expected result: the production Goblin first-contact quest is not available.

Restore the foundation fact:

```text
/questlog narrative fact set overlord_reign:reign/initial_foundation_established
```

Interact again.

Expected result: `The Goblin Camp` is available from the designated leader.

## Check 3: provider-native conversation flow

Open the designated leader's provider menu and select `The Goblin Camp`.

Expected initial state:

- authored offer dialogue is shown;
- `Accept` and `Decline` are available;
- declining does not alter quest state or write narrative facts.

Accept the quest.

Expected result after the server refresh:

- the same selected quest immediately becomes `READY_TO_TURN_IN`;
- the authored ready-to-turn-in dialogue is shown;
- there is no artificial intermediate journal objective;
- `Turn In` is available from this same leader.

Use `Turn In`.

Expected result:

- the quest completes;
- the contact fact is written automatically;
- completed provider follow-up belongs to the exact issuing leader.

Verify:

```text
/questlog narrative fact get overlord_reign:civilizations/goblins/contact_established
```

Expected result: `true`.

Verify disposition:

```text
/questlog narrative disposition get overlord_reign:goblins
```

Expected result:

```text
questlog:unresolved
```

First contact must not silently settle Goblin politics.

## Check 4: same-provider identity

Before turning in on a fresh test state, interact with another otherwise similar leader.

Expected result: the second leader cannot complete the accepted first-contact quest.

Return to the original issuing leader.

Expected result: the original leader can complete the turn-in.

This proves that the production quest uses the durable provider UUID rather than merely matching the same entity type or anchor-like role.

## Check 5: persistence

After completing first contact:

1. save and quit;
2. reopen the same world;
3. verify the contact fact;
4. interact with the same surviving designated leader.

Expected result:

- `overlord_reign:civilizations/goblins/contact_established` remains true;
- Goblin disposition remains unresolved unless another explicit test changed it;
- the quest remains completed;
- the designated issuing leader can expose its authored completed follow-up;
- the provider is not offered as a fresh first-contact quest again.

## Check 6: protection remains separate from identity

Before any authored destructive-route testing, attack the leader while `overlord_quest_protected` is present.

Expected result: ordinary damage is canceled.

Do not remove the protection tag in a production world merely to prove that removal works. In a disposable test world, it may be removed with:

```text
/tag @e[type=goblins_tyranny:leader_goblin,sort=nearest,limit=1,distance=..8] remove overlord_quest_protected
```

The anchor identity tag should remain independent:

```text
overlord_anchor:goblin_main
```

Removing protection must not redefine which Goblin camp is the authored principal anchor.

## Pass criteria

This first-contact slice is runtime-valid only when all of the following are directly observed in the target instance:

- unrelated Goblin leaders cannot expose the production civilization quest;
- the designated leader is gated by the established campaign foundation;
- provider Accept transitions directly to same-provider ready-to-turn-in dialogue;
- no journal-only interaction is required between the two provider dialogue phases;
- same-provider identity is enforced;
- contact writes exactly the persistent Goblin contact fact;
- Goblin disposition remains unresolved;
- save/reload preserves contact and completed-provider follow-up;
- quest-anchor protection remains separate from anchor identity.

Passing repository CI is necessary but is not a substitute for this in-world validation.
