# Kobold Civilization First-Contact Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: first formal contact with the designated principal Kobold Den only.

This protocol validates `campaign/civilizations/kobolds/first_contact` against the exact installed Kobolds 2.12.0 behavior documented in `docs/KOBOLDS_PROVIDER_INTEGRATION.md`. It does not validate later Kobold political branches, disposition outcomes, Pirate Kobold politics, destruction, tribute, services, or final terrain integration.

## Authority boundary

The current lore authority establishes:

- one designated Kobold Den is the principal local civilization anchor;
- its first formal contact is represented by one designated Captain;
- ordinary Captains and unrelated Dens do not automatically become civilization quest starters;
- Pirate Kobolds remain a separate subculture unless later authored otherwise;
- exact final-world coordinates may be assigned later.

The installed target artifact is:

```text
Kobolds-2.12.0.jar
```

The exact installed binary confirms the provider entity type:

```text
kobolds:kobold_captain
```

The production provider therefore requires both:

```text
entity: kobolds:kobold_captain
anchor tag: overlord_anchor:kobold_main_captain
```

Quest-critical protection remains separate:

```text
overlord_quest_protected
```

## Preconditions

Use the target OVERLORD REIGN Forge 1.20.1 instance with the validated OVERLORD QUESTS artifact and Kobolds 2.12.0 installed.

Use an unpublished local single-player test world with commands available.

Back up the world or use a disposable validation world.

## Clean state

Reset the contact fact:

```text
/questlog narrative fact clear overlord_reign:civilizations/kobolds/contact_established
```

Clear Kobold disposition:

```text
/questlog narrative disposition clear overlord_reign:kobolds
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

## Establish the designated Captain

Locate or summon a `kobolds:kobold_captain` for a disposable validation world.

Apply the authored principal-Den identity:

```text
/tag @e[type=kobolds:kobold_captain,sort=nearest,limit=1,distance=..8] add overlord_anchor:kobold_main_captain
```

Apply protection separately:

```text
/tag @e[type=kobolds:kobold_captain,sort=nearest,limit=1,distance=..8] add overlord_quest_protected
```

For final-world integration these tags must be assigned to the actual selected Captain at the designated principal Den.

## Check 1: ordinary Captains do not become civilization anchors

Find another `kobolds:kobold_captain` that does not carry `overlord_anchor:kobold_main_captain`.

Sneak and main-hand interact with an empty hand.

Expected result:

- `The Kobold Den` is not offered;
- no Kobold contact fact is written;
- Kobold disposition remains unresolved.

This proves the exact entity type alone does not promote every Captain or Den into the principal polity.

## Check 2: wrong entity type does not qualify

Apply `overlord_anchor:kobold_main_captain` temporarily to an ordinary Kobold or another non-Captain Kobolds entity.

Sneak and main-hand interact with an empty hand.

Expected result: `The Kobold Den` is not offered.

Remove the temporary anchor tag after the check.

This proves authored identity cannot broaden the source-backed entity selector.

## Check 3: campaign foundation gate

On the correctly tagged Captain, clear the campaign foundation temporarily:

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

Expected result: `The Kobold Den` is available.

## Check 4: source-native Captain interaction remains available

Do not sneak and interact with Kobold Captains as normal.

Expected result: Questlog does not replace the source mod's ordinary interaction behavior.

For deterministic Questlog provider testing, use an empty main hand. The installed Kobolds 2.12.0 Captain has its own item-transfer and trade behavior, so provider validation should not combine that source mechanic with the Questlog gesture.

## Check 5: provider-native conversation flow

Sneak and main-hand interact with the designated Captain using an empty hand. Select `The Kobold Den`.

Expected initial state:

- authored offer dialogue is shown;
- `Accept` and `Decline` are available;
- declining does not change quest state or write contact history.

Accept the quest.

Expected result after server refresh:

- the same quest becomes `READY_TO_TURN_IN` immediately;
- authored ready-to-turn-in dialogue is shown;
- no journal acknowledgement or artificial gameplay objective intervenes;
- `Turn In` is available from the exact issuing Captain.

Turn it in.

Verify:

```text
/questlog narrative fact get overlord_reign:civilizations/kobolds/contact_established
```

Expected result: `true`.

Verify disposition:

```text
/questlog narrative disposition get overlord_reign:kobolds
```

Expected result:

```text
questlog:unresolved
```

Formal contact must not settle Kobold politics.

## Check 6: same-provider identity

Before turn-in on a fresh state, interact with another Captain.

Expected result: the second Captain cannot complete the accepted production first-contact quest, even if it is independently given the same anchor tag for this negative test.

Return to the exact issuing Captain.

Expected result: the issuing provider can complete turn-in.

Remove any temporary duplicate anchor tag after the check.

## Check 7: persistence

After completing first contact:

1. save and quit;
2. reopen the same world;
3. verify the Kobold contact fact;
4. interact with the same surviving Captain.

Expected result:

- `overlord_reign:civilizations/kobolds/contact_established` remains true;
- Kobold disposition remains unresolved unless another explicit test changed it;
- the quest remains completed;
- the exact issuing Captain can expose its authored completed follow-up;
- the production first-contact quest is not offered again as fresh content.

## Check 8: protection remains separate from identity

Attack the Captain while `overlord_quest_protected` is present.

Expected result: ordinary damage is canceled.

In a disposable test world only, protection may be removed to validate the transition mechanism:

```text
/tag @e[type=kobolds:kobold_captain,sort=nearest,limit=1,distance=..8] remove overlord_quest_protected
```

The provider must still remain the authored principal-Den Captain because its entity identity and anchor identity are separate from protection.

## Check 9: source-native hostility boundary

Observe the designated Captain near a player without provoking revenge behavior.

Expected result from the installed 2.12.0 behavior: the Captain does not acquire the player merely because the player is nearby.

This check is deliberately narrow. It does not assert permanent peace after attacks or other source-native combat consequences.

## Pass criteria

This slice is runtime-valid only when all of the following are directly observed in the target instance:

- the production quest appears only after the initial foundation fact exists;
- only `kobolds:kobold_captain` with the designated principal-Den anchor tag exposes the civilization contact quest;
- ordinary Captains and wrong entity types do not expose it;
- normal non-sneaking Kobolds interaction remains owned by Kobolds 2.12.0;
- acceptance transitions directly to ready-to-turn-in without a synthetic journal objective;
- turn-in is bound to the exact issuing Captain;
- completion writes only `overlord_reign:civilizations/kobolds/contact_established`;
- Kobold disposition remains unresolved;
- contact completion and provider binding survive save/reload;
- quest-critical protection remains independent from provider identity;
- the designated Captain does not target an unprovoking player under the installed source behavior.

Passing this protocol validates only the first-contact slice. It does not authorize later Kobold branch content without separate lore and implementation decisions.
