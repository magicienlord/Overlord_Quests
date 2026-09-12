# Ribbit Civilization First-Contact Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: first formal contact with the designated Ribbit civilization anchor only.

This protocol validates `campaign/civilizations/ribbits/first_contact` and the optional Ribbits 3.0.5 native-profession bridge without requiring final-world coordinates. It does not validate later Ribbit political branches, disposition outcomes, tribute, destruction, services, or final terrain integration.

## Authority boundary

The current lore authority establishes:

- one designated Ribbit Village is the principal local civilization anchor;
- its main quest begins through one protected, explicitly marked Gardener assigned the local role of Elder;
- ordinary Ribbit Gardeners and unrelated Ribbit Villages do not automatically become civilization quest starters;
- Ribbits retain their peaceful, cheerful, agricultural, trading, musical source character;
- exact final-world placement may be assigned later.

The installed target build is:

```text
Ribbits-1.20.1-Forge-3.0.5.jar
```

The matching public 3.0.5 source exposes native profession through `RibbitData`, with Gardener ID:

```text
ribbits:gardener
```

The production provider therefore requires all of the following:

```text
entity: ribbits:ribbit
native role: ribbits:gardener
anchor tag: overlord_anchor:ribbit_main_elder
```

Quest-critical protection remains separate:

```text
overlord_quest_protected
```

## Preconditions

Use the target OVERLORD REIGN Forge 1.20.1 instance with the validated OVERLORD QUESTS artifact and Ribbits 3.0.5 installed.

Use an unpublished local single-player test world with commands available.

Back up the world or use a disposable validation world.

## Clean state

Reset the contact fact:

```text
/questlog narrative fact clear overlord_reign:civilizations/ribbits/contact_established
```

Clear Ribbit disposition:

```text
/questlog narrative disposition clear overlord_reign:ribbits
```

Expected effective state:

```text
questlog:unresolved
```

For an isolated first-contact test, establish the existing campaign foundation directly:

```text
/questlog narrative fact set overlord_reign:reign/initial_foundation_established
```

This command is test setup only. Production play earns the foundation through the opening campaign.

## Identify a native Gardener

Use a naturally generated Ribbit Village and locate a Gardener Ribbit. The exact native profession can be inspected with:

```text
/data get entity @e[type=ribbits:ribbit,sort=nearest,limit=1,distance=..8] RibbitData.profession
```

Expected value for the intended provider:

```text
ribbits:gardener
```

Do not assign the principal Elder anchor tag to a Ribbit whose native profession is not Gardener merely to make the test pass.

Apply the authored principal-anchor identity to the verified Gardener:

```text
/tag @e[type=ribbits:ribbit,sort=nearest,limit=1,distance=..8] add overlord_anchor:ribbit_main_elder
```

Apply protection separately:

```text
/tag @e[type=ribbits:ribbit,sort=nearest,limit=1,distance=..8] add overlord_quest_protected
```

For final-world integration, these tags must be assigned to the actual selected Gardener Elder at the designated Ribbit Village.

## Check 1: native profession enforcement

Find a non-Gardener Ribbit and temporarily give it only the principal Elder anchor tag:

```text
/tag @e[type=ribbits:ribbit,sort=nearest,limit=1,distance=..8] add overlord_anchor:ribbit_main_elder
```

Verify its `RibbitData.profession` is not `ribbits:gardener`.

Sneak and main-hand interact with an empty hand.

Expected result: `The Ribbit Village` is not offered.

This proves the compatibility bridge is reading native Ribbits profession state rather than trusting the authored anchor tag alone.

Remove the temporary anchor tag from that non-Gardener after the check.

## Check 2: ordinary Gardeners do not become civilization anchors

Find another native Gardener that does not carry `overlord_anchor:ribbit_main_elder`.

Sneak and main-hand interact with an empty hand.

Expected result:

- the production civilization quest is not offered;
- no contact fact is written;
- Ribbit disposition remains unresolved.

This proves native profession alone does not promote every Gardener or village into the principal polity.

## Check 3: campaign foundation gate

On the correctly tagged Gardener Elder, clear the campaign foundation temporarily:

```text
/questlog narrative fact clear overlord_reign:reign/initial_foundation_established
```

Sneak and main-hand interact with an empty hand.

Expected result: the production first-contact quest is not available.

Restore the foundation:

```text
/questlog narrative fact set overlord_reign:reign/initial_foundation_established
```

Interact again.

Expected result: `The Ribbit Village` is available.

## Check 4: native Ribbit interactions remain available

Do not sneak and interact with ordinary Ribbits as normal.

Expected result: Questlog does not replace the source mod's ordinary interaction behavior.

For Questlog provider testing, use an empty main hand. Ribbits has a separate secondary-use interaction involving an amethyst shard and home position, so the validation should not combine that source mechanic with the provider-menu gesture.

## Check 5: provider-native conversation flow

Sneak and main-hand interact with the designated Gardener Elder using an empty hand. Select `The Ribbit Village`.

Expected initial state:

- authored offer dialogue is shown;
- `Accept` and `Decline` are available;
- declining does not change quest state or write contact history.

Accept the quest.

Expected result after server refresh:

- the same quest becomes `READY_TO_TURN_IN` immediately;
- authored ready-to-turn-in dialogue is shown;
- no journal acknowledgement or artificial gameplay objective intervenes;
- `Turn In` is available from the exact issuing Elder.

Turn it in.

Verify:

```text
/questlog narrative fact get overlord_reign:civilizations/ribbits/contact_established
```

Expected result: `true`.

Verify disposition:

```text
/questlog narrative disposition get overlord_reign:ribbits
```

Expected result:

```text
questlog:unresolved
```

Formal contact must not settle Ribbit politics.

## Check 6: same-provider identity

Before turn-in on a fresh state, interact with another native Gardener.

Expected result: the second Gardener cannot complete the accepted production first-contact quest, even if it is otherwise an eligible Ribbit profession.

Return to the exact issuing Elder.

Expected result: the issuing provider can complete turn-in.

## Check 7: persistence

After completing first contact:

1. save and quit;
2. reopen the same world;
3. verify the Ribbit contact fact;
4. verify the Elder still has native profession `ribbits:gardener`;
5. interact with the same surviving Elder.

Expected result:

- `overlord_reign:civilizations/ribbits/contact_established` remains true;
- Ribbit disposition remains unresolved unless another explicit test changed it;
- the quest remains completed;
- the exact issuing Elder can expose its authored completed follow-up;
- the production first-contact quest is not offered again as fresh content.

## Check 8: protection remains separate from identity

Attack the Elder while `overlord_quest_protected` is present.

Expected result: ordinary damage is canceled.

In a disposable test world only, protection may be removed to validate the transition mechanism:

```text
/tag @e[type=ribbits:ribbit,sort=nearest,limit=1,distance=..8] remove overlord_quest_protected
```

The provider must still remain the authored Gardener Elder because its native profession and anchor identity are separate from protection.

## Pass criteria

This slice is runtime-valid only when all of the following are directly observed in the target instance:

- the optional role bridge accepts a native `ribbits:gardener` and rejects non-Gardener Ribbits;
- ordinary Gardeners without the principal anchor tag cannot expose the civilization quest;
- the designated Gardener Elder is gated by the established campaign foundation;
- ordinary Ribbits interactions remain available when the Questlog gesture is not used;
- provider Accept transitions directly to same-provider ready-to-turn-in dialogue;
- same-provider identity is enforced;
- contact writes exactly the persistent Ribbit contact fact;
- Ribbit disposition remains unresolved;
- save/reload preserves contact, native profession, and completed-provider follow-up;
- quest-anchor protection remains separate from both native profession and provider identity.

Passing repository CI is necessary but is not a substitute for this in-world validation.
