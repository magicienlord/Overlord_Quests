# Sea Dweller Civilization First-Contact Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: first formal contact with the designated Sea Dweller civilization anchor only.

This protocol validates `campaign/civilizations/sea_dwellers/first_contact` against the exact installed Realm RPG: Sea Dwellers 2.9.9 entity/tag behavior without requiring final-world coordinates. It does not validate later Sea Dweller political branches, disposition outcomes, tribute, destruction, services, or final terrain integration.

The previously proposed Ocean Dragon handoff was removed as a false assumption. No Ocean Dragon milestone is part of this protocol.

## Authority boundary

The current lore authority establishes:

- one designated Sea Village is the principal local civilization anchor;
- its main quest begins through one protected, explicitly marked senior Sea Dweller trader assigned the social title `Sea Elder`;
- ordinary Sea Villages and Sea Dwellers do not automatically become civilization quest starters;
- exact final-world placement may be assigned later.

The installed target build is:

```text
realmrpg_seadwellers_2.9.9_forge_1.20.1.jar
```

The exact installed JAR publishes:

```text
#seadwellers:mermorphs
```

as the native entity family for the eleven normal Mermorph variants.

The production provider therefore requires all of the following:

```text
native entity family: #seadwellers:mermorphs
anchor tag: overlord_anchor:sea_dweller_main_elder
role tag: overlord_role:sea_elder
```

Quest-critical protection remains separate:

```text
overlord_quest_protected
```

## Preconditions

Use the target OVERLORD REIGN Forge 1.20.1 instance with the validated OVERLORD QUESTS artifact and Realm RPG: Sea Dwellers 2.9.9 installed.

Use an unpublished local single-player test world with commands available.

Back up the world or use a disposable validation world.

## Clean state

Reset the contact fact:

```text
/questlog narrative fact clear overlord_reign:civilizations/sea_dwellers/contact_established
```

Clear Sea Dweller disposition:

```text
/questlog narrative disposition clear overlord_reign:sea_dwellers
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

## Select the designated Sea Elder

Locate one normal Mermorph in the Sea Village selected for the campaign anchor.

The source-owned family can be targeted with:

```text
@e[type=#seadwellers:mermorphs]
```

Apply the principal-village anchor identity to the selected senior trader:

```text
/tag @e[type=#seadwellers:mermorphs,sort=nearest,limit=1,distance=..8] add overlord_anchor:sea_dweller_main_elder
```

Apply the authored social role independently:

```text
/tag @e[type=#seadwellers:mermorphs,sort=nearest,limit=1,distance=..8] add overlord_role:sea_elder
```

Apply quest protection separately:

```text
/tag @e[type=#seadwellers:mermorphs,sort=nearest,limit=1,distance=..8] add overlord_quest_protected
```

For final-world integration, all three authored tags must be applied to the actual selected senior trader at the designated Sea Village.

## Check 1: native family enforcement

Give the two authored provider tags to a nearby entity that is not in `#seadwellers:mermorphs`:

```text
overlord_anchor:sea_dweller_main_elder
overlord_role:sea_elder
```

Sneak and main-hand interact.

Expected result: `The Sea Village` is not offered.

This proves authored tags cannot turn an unrelated entity into the civilization provider.

Remove the temporary tags after the check.

## Check 2: anchor identity enforcement

Find an ordinary Mermorph that does not carry `overlord_anchor:sea_dweller_main_elder`.

Temporarily give it only:

```text
overlord_role:sea_elder
```

Sneak and main-hand interact.

Expected result:

- `The Sea Village` is not offered;
- no contact fact is written;
- Sea Dweller disposition remains unresolved.

This proves the social role alone does not promote every Mermorph into the principal polity.

Remove the temporary role tag after the check.

## Check 3: Sea Elder role enforcement

On a different ordinary Mermorph, temporarily give it only:

```text
overlord_anchor:sea_dweller_main_elder
```

Do not give it `overlord_role:sea_elder`.

Sneak and main-hand interact.

Expected result: the production civilization quest is not offered.

This proves principal-village membership alone is insufficient without the authored senior social role.

Remove the temporary anchor tag after the check.

## Check 4: campaign foundation gate

On the correctly tagged Sea Elder, clear the campaign foundation temporarily:

```text
/questlog narrative fact clear overlord_reign:reign/initial_foundation_established
```

Sneak and main-hand interact.

Expected result: the production first-contact quest is not available.

Restore the foundation:

```text
/questlog narrative fact set overlord_reign:reign/initial_foundation_established
```

Interact again.

Expected result: `The Sea Village` is available.

## Check 5: native Sea Dweller interactions remain available

Do not sneak and interact with ordinary Sea Dwellers through their normal source-mod interaction paths.

Expected result: Questlog does not replace ordinary Sea Dwellers 2.9.9 interaction or barter behavior.

For the Questlog provider flow, use the dedicated sneak + main-hand interaction gesture so the validation does not conflate a native trade interaction with the provider menu.

## Check 6: provider-native conversation flow

Sneak and main-hand interact with the designated Sea Elder. Select `The Sea Village`.

Expected initial state:

- authored offer dialogue is shown;
- `Accept` and `Decline` are available;
- declining does not change quest state or write contact history.

Accept the quest.

Expected result after server refresh:

- the same quest becomes `READY_TO_TURN_IN` immediately;
- authored ready-to-turn-in dialogue is shown;
- no journal acknowledgement or artificial gameplay objective intervenes;
- `Turn In` is available from the exact issuing Sea Elder.

Turn it in.

Verify:

```text
/questlog narrative fact get overlord_reign:civilizations/sea_dwellers/contact_established
```

Expected result: `true`.

Verify disposition:

```text
/questlog narrative disposition get overlord_reign:sea_dwellers
```

Expected result:

```text
questlog:unresolved
```

Formal contact must not settle Sea Dweller politics.

## Check 7: same-provider identity

Before turn-in on a fresh state, interact with another Mermorph, even one carrying the same role and anchor tags in a disposable test setup.

Expected result: the second entity cannot complete the accepted production first-contact quest.

Return to the exact issuing Sea Elder.

Expected result: the issuing provider can complete turn-in.

## Check 8: persistence

After completing first contact:

1. save and quit;
2. reopen the same world;
3. verify the Sea Dweller contact fact;
4. verify the selected Elder retains the anchor, role, and protection tags;
5. interact with the same surviving Elder.

Expected result:

- `overlord_reign:civilizations/sea_dwellers/contact_established` remains true;
- Sea Dweller disposition remains unresolved unless another explicit test changed it;
- the quest remains completed;
- the exact issuing Elder can expose its authored completed follow-up;
- the production first-contact quest is not offered again as fresh content.

## Check 9: protection remains separate from identity

Attack the Elder while `overlord_quest_protected` is present.

Expected result: ordinary damage is canceled.

In a disposable test world only, remove protection:

```text
/tag @e[type=#seadwellers:mermorphs,sort=nearest,limit=1,distance=..8] remove overlord_quest_protected
```

The provider must still remain the authored Sea Elder because source-native identity, anchor identity, and social role are separate from protection.

## Pass criteria

This slice is runtime-valid only when all of the following are directly observed in the target instance:

- non-Mermorph entities cannot qualify through authored tags alone;
- ordinary Mermorphs do not become the principal provider;
- the anchor tag without the Sea Elder role is insufficient;
- the Sea Elder role without the anchor tag is insufficient;
- the campaign foundation gate works;
- native non-Questlog Sea Dweller interaction remains available;
- provider acceptance transitions directly to ready-to-turn-in with no synthetic objective;
- only the exact issuing Sea Elder can turn in the accepted quest;
- completion persists through save/reload;
- the contact fact persists;
- Sea Dweller disposition remains unresolved;
- quest protection remains independent from provider identity;
- no Ocean Dragon progression or milestone appears anywhere in this first-contact flow.
