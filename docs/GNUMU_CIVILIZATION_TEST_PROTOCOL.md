# Gnumu Civilization First-Contact Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: first formal contact with the designated Gnumu civilization anchor only.

This protocol validates `campaign/civilizations/gnumus/first_contact` without requiring final-world coordinates. It does not validate later Gnumu political branches, disposition outcomes, settlement services, tribute, destruction, hidden ancestry reveals, or final terrain integration.

## Authority boundary

The current lore authority establishes:

- the principal Gnumu civilization anchor is the selected Large Gnumus Settlement or equivalent main settlement;
- the civilization main quest begins through one designated Elder Shaman;
- Elder Shaman is a political and social role layered onto an existing Gnumu shaman rather than a new entity class;
- unrelated Gnumu settlements and ordinary Gnumus do not automatically become civilization quest starters;
- the Gnumus do not know that their regional population descends from Halflings altered by Gluttony magic;
- exact final-world placement may be assigned later.

The production definition therefore combines the source-backed entity type:

```text
gnumus:gnumus_shaman
```

with the authored anchor identity:

```text
overlord_anchor:gnumu_main_elder
```

and authored local social role:

```text
overlord_role:elder_shaman
```

Quest-critical protection is separate:

```text
overlord_quest_protected
```

None of these tags may be used to imply that all Gnumu shamans are Elder Shamans.

## Preconditions

Use the target OVERLORD REIGN Forge 1.20.1 instance with the validated OVERLORD QUESTS artifact and the installed Gnumus version represented by the current project technical source set.

Use an unpublished local single-player test world with commands available.

Back up the world or use a disposable validation world.

## Clean state

Reset the production contact fact if a previous validation run set it:

```text
/questlog narrative fact clear overlord_reign:civilizations/gnumus/contact_established
```

Clear Gnumu disposition:

```text
/questlog narrative disposition clear overlord_reign:gnumus
```

Expected effective state:

```text
questlog:unresolved
```

For isolated first-contact testing, establish the existing campaign foundation directly:

```text
/questlog narrative fact set overlord_reign:reign/initial_foundation_established
```

This administrative command is test setup only. Production play earns the foundation through the authored opening campaign.

## Create or identify the test anchor

Use a naturally generated shaman in the intended main Gnumu settlement when available. For isolated mechanics testing, a summoned shaman is acceptable:

```text
/summon gnumus:gnumus_shaman ~ ~ ~
```

Stand close to the intended test entity and apply the production anchor identity:

```text
/tag @e[type=gnumus:gnumus_shaman,sort=nearest,limit=1,distance=..8] add overlord_anchor:gnumu_main_elder
```

Apply the authored Elder Shaman role:

```text
/tag @e[type=gnumus:gnumus_shaman,sort=nearest,limit=1,distance=..8] add overlord_role:elder_shaman
```

Apply quest-critical protection separately:

```text
/tag @e[type=gnumus:gnumus_shaman,sort=nearest,limit=1,distance=..8] add overlord_quest_protected
```

For final-world integration, these tags must be assigned to the actual selected Elder Shaman at the principal Gnumu settlement rather than an arbitrary procedural shaman.

## Check 1: ordinary shamans do not expose the civilization quest

Find or summon a second `gnumus:gnumus_shaman` without the anchor and Elder Shaman role tags.

Sneak and main-hand interact with it.

Expected result:

- `The Gnumu Settlement` is not offered;
- no Gnumu contact fact is written;
- Gnumu disposition remains `questlog:unresolved`.

## Check 2: both authored identity tags are required

On a fresh test state, give a shaman only:

```text
overlord_anchor:gnumu_main_elder
```

but not `overlord_role:elder_shaman`.

Expected result: the production first-contact quest is not available.

Remove that setup or add the missing role tag and then test the inverse on another shaman: role tag present but principal-anchor tag absent.

Expected result: the quest is still not available.

Only the designated entity carrying both authored identity conditions may expose the production quest.

## Check 3: campaign foundation gate

Clear the foundation fact temporarily:

```text
/questlog narrative fact clear overlord_reign:reign/initial_foundation_established
```

Interact with the correctly tagged Elder Shaman.

Expected result: the production Gnumu first-contact quest is not available.

Restore the fact:

```text
/questlog narrative fact set overlord_reign:reign/initial_foundation_established
```

Interact again.

Expected result: `The Gnumu Settlement` is available.

## Check 4: provider-native conversation flow

Open the designated Elder Shaman's provider menu and select the production quest.

Expected initial state:

- authored offer dialogue is shown;
- `Accept` and `Decline` are available;
- declining does not change quest state or write contact history.

Accept the quest.

Expected result after server refresh:

- the same selected quest becomes `READY_TO_TURN_IN` immediately;
- authored ready-to-turn-in dialogue is shown;
- no separate journal acknowledgement or artificial gameplay objective is required;
- `Turn In` is available from the exact issuing Elder Shaman.

Turn it in.

Expected result:

- the quest completes;
- the contact fact is written automatically;
- completed provider follow-up belongs to the exact issuing Elder Shaman.

Verify:

```text
/questlog narrative fact get overlord_reign:civilizations/gnumus/contact_established
```

Expected result: `true`.

Verify disposition:

```text
/questlog narrative disposition get overlord_reign:gnumus
```

Expected result:

```text
questlog:unresolved
```

First contact must not settle Gnumu politics.

## Check 5: same-provider identity

Before turn-in on a fresh state, interact with another otherwise similar shaman, including one carrying the same generic Elder Shaman role if desired but not the issuing provider UUID.

Expected result: that second entity cannot complete the accepted quest.

Return to the original issuing Elder Shaman.

Expected result: the original provider can complete turn-in.

## Check 6: persistence

After completing first contact:

1. save and quit;
2. reopen the same world;
3. verify the Gnumu contact fact;
4. interact with the same surviving Elder Shaman.

Expected result:

- `overlord_reign:civilizations/gnumus/contact_established` remains true;
- Gnumu disposition remains unresolved unless another explicit test changed it;
- the quest remains completed;
- the exact issuing Elder Shaman can expose authored completed follow-up;
- the quest is not offered as fresh first contact again.

## Check 7: hidden ancestry firewall

Review the production quest title, journal description, provider dialogue, completion text, rewards, and recorded contact fact as presented in game.

Expected result:

- no text states or implies that the Gnumus descend from Halflings;
- no text states or implies that Gluttony magic transformed their ancestors;
- the contact fact records only formal contact with the designated polity;
- no hidden-ancestry fact is written by first contact.

This is a canon requirement, not merely a spoiler preference. The current Gnumus themselves do not possess that historical knowledge.

## Check 8: protection remains separate from identity

Attack the Elder Shaman while `overlord_quest_protected` is present.

Expected result: ordinary damage is canceled.

In a disposable test world only, the protection tag may be removed to validate the transition mechanism:

```text
/tag @e[type=gnumus:gnumus_shaman,sort=nearest,limit=1,distance=..8] remove overlord_quest_protected
```

The two provider identity tags must remain conceptually independent:

```text
overlord_anchor:gnumu_main_elder
overlord_role:elder_shaman
```

Removing protection must not redefine which settlement or shaman is the authored principal anchor.

## Pass criteria

This first-contact slice is runtime-valid only when all of the following are directly observed in the target instance:

- ordinary Gnumu shamans cannot expose the production civilization quest;
- both principal-anchor identity and Elder Shaman role are required;
- the designated provider is gated by the established campaign foundation;
- provider Accept transitions directly to same-provider ready-to-turn-in dialogue;
- same-provider identity is enforced;
- contact writes exactly the persistent Gnumu contact fact;
- Gnumu disposition remains unresolved;
- save/reload preserves contact and completed-provider follow-up;
- first contact does not reveal the hidden Halfling ancestry;
- quest-anchor protection remains separate from provider identity.

Passing repository CI is necessary but is not a substitute for this in-world validation.
