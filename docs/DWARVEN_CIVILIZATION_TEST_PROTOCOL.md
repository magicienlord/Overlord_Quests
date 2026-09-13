# Dwarven Civilization First-Contact Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: first formal contact with the designated Dwarven successor hold plus a non-canon technical probe of the installed Dwarf reputation/price path.

This protocol validates `campaign/civilizations/dwarves/first_contact` against the exact installed The Dwarven Forge 1.0.0 entity/profession behavior without requiring final-world coordinates. It does not validate later Dwarven political branches, disposition outcomes, tribute, destruction, rune rewards, services, or final terrain integration.

## Authority boundary

The current lore authority establishes:

- one designated Golden Hills successor hold is the principal Dwarven civilization anchor;
- its main quest begins through one protected Dwarven Forger assigned the political title `Forge-Thane`;
- ordinary Dwarves elsewhere do not automatically become civilization quest starters;
- exact final-world placement may be assigned later.

The installed target build is:

```text
dwarven_forge-1.0.0.jar
```

The exact installed implementation confirms:

```text
entity: dwarven_forge:dwarf
native profession: minecraft:toolsmith
source display role: Dwarven Forger
```

The production provider therefore requires:

```text
entity: dwarven_forge:dwarf
native profession: minecraft:toolsmith
anchor tag: overlord_anchor:dwarf_forge_thane
```

Quest-critical protection remains separate:

```text
overlord_quest_protected
```

A source/bytecode audit also confirms that the installed Dwarf trade path reads inherited vanilla Villager player reputation and applies the resulting special-price adjustment to its native MerchantOffers. Hero of the Village is handled separately. The runtime price probe later in this protocol validates that mechanism only; it establishes no Dwarven disposition reward or reputation amount.

## Preconditions

Use the target OVERLORD REIGN Forge 1.20.1 instance with the validated OVERLORD QUESTS artifact and The Dwarven Forge 1.0.0 installed.

Use an unpublished local single-player test world with commands available.

Back up the world or use a disposable validation world.

## Clean state

Reset the contact fact:

```text
/questlog narrative fact clear overlord_reign:civilizations/dwarves/contact_established
```

Clear Dwarven disposition:

```text
/questlog narrative disposition clear overlord_reign:dwarves
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

## Select the Forge-Thane

Locate a `dwarven_forge:dwarf` whose native Villager profession is `minecraft:toolsmith`.

The profession can be inspected through the entity's `VillagerData` NBT in a disposable validation world. Do not assign the Forge-Thane anchor tag to a non-toolsmith Dwarf merely to make the test pass.

Apply the authored political identity to the verified Dwarven Forger:

```text
/tag @e[type=dwarven_forge:dwarf,sort=nearest,limit=1,distance=..8] add overlord_anchor:dwarf_forge_thane
```

Apply quest protection separately:

```text
/tag @e[type=dwarven_forge:dwarf,sort=nearest,limit=1,distance=..8] add overlord_quest_protected
```

For final-world integration, these tags must be assigned to the actual selected Dwarven Forger leading the designated successor hold.

## Check 1: native profession enforcement

Find a Dwarf whose native profession is not `minecraft:toolsmith` and temporarily give it only:

```text
overlord_anchor:dwarf_forge_thane
```

Sneak and main-hand interact.

Expected result: `The Successor Hold` is not offered.

This proves the provider is reading the Dwarf's native Villager profession rather than trusting the anchor tag alone.

Remove the temporary anchor tag after the check.

## Check 2: ordinary Dwarven Forgers do not become Forge-Thanes

Find another native `minecraft:toolsmith` Dwarf that does not carry `overlord_anchor:dwarf_forge_thane`.

Sneak and main-hand interact.

Expected result:

- the production civilization quest is not offered;
- no contact fact is written;
- Dwarven disposition remains unresolved.

This proves native profession alone does not promote every Dwarven Forger into the principal political anchor.

## Check 3: campaign foundation gate

On the correctly tagged Forge-Thane, clear the campaign foundation temporarily:

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

Expected result: `The Successor Hold` is available.

## Check 4: native Dwarf trading remains available

Do not sneak and interact with ordinary Dwarves through their normal source-mod trade path.

Expected result: Questlog does not replace The Dwarven Forge 1.0.0 trading behavior or its amethyst-centered economy.

For the Questlog provider flow, use the dedicated sneak + main-hand interaction gesture so native trading and provider interaction are tested independently.

## Check 5: provider-native conversation flow

Sneak and main-hand interact with the designated Forge-Thane. Select `The Successor Hold`.

Expected initial state:

- authored offer dialogue is shown;
- `Accept` and `Decline` are available;
- declining does not change quest state or write contact history.

Accept the quest.

Expected result after server refresh:

- the same quest becomes `READY_TO_TURN_IN` immediately;
- authored ready-to-turn-in dialogue is shown;
- no journal acknowledgement or artificial gameplay objective intervenes;
- `Turn In` is available from the exact issuing Forge-Thane.

Turn it in.

Verify:

```text
/questlog narrative fact get overlord_reign:civilizations/dwarves/contact_established
```

Expected result: `true`.

Verify disposition:

```text
/questlog narrative disposition get overlord_reign:dwarves
```

Expected result:

```text
questlog:unresolved
```

Formal contact must not settle Dwarven politics.

## Check 6: same-provider identity

Before turn-in on a fresh state, interact with another native Dwarven Forger.

Expected result: the second Forger cannot complete the accepted production first-contact quest, even if it is temporarily given the same anchor tag in a disposable validation setup.

Return to the exact issuing Forge-Thane.

Expected result: the issuing provider can complete turn-in.

## Check 7: persistence

After completing first contact:

1. save and quit;
2. reopen the same world;
3. verify the Dwarven contact fact;
4. verify the Forge-Thane remains `minecraft:toolsmith`;
5. verify the Forge-Thane retains the authored anchor and protection tags;
6. interact with the same surviving Forge-Thane.

Expected result:

- `overlord_reign:civilizations/dwarves/contact_established` remains true;
- Dwarven disposition remains unresolved unless another explicit test changed it;
- the quest remains completed;
- the exact issuing Forge-Thane can expose its authored completed follow-up;
- the production first-contact quest is not offered again as fresh content.

## Check 8: protection remains separate from identity

Attack the Forge-Thane while `overlord_quest_protected` is present.

Expected result: ordinary damage is canceled.

In a disposable test world only, remove protection:

```text
/tag @e[type=dwarven_forge:dwarf,sort=nearest,limit=1,distance=..8] remove overlord_quest_protected
```

The provider must still remain the authored Forge-Thane because native entity identity, native profession, and anchor identity are separate from protection.

## Check 9: inherited Villager reputation affects native Dwarven prices

This is a TECHNICAL probe only. It must be performed in a disposable validation world and must not be interpreted as a production subjugation reward.

Use a native Dwarven Forger that currently offers at least one trade whose input count can visibly change. Open its ordinary non-sneaking trade UI and record the displayed input cost for a stable offer. Close the UI before mutating gossip state.

Inspect the local player's UUID if needed:

```text
/data get entity @p UUID
```

Replace the Dwarf's gossip list with one temporary positive vanilla Villager gossip entry, then copy the actual local player's UUID into that entry:

```text
/data modify entity @e[type=dwarven_forge:dwarf,sort=nearest,limit=1,distance=..8] Gossips set value [{Target:[I;0,0,0,0],Type:"major_positive",Value:100}]
/data modify entity @e[type=dwarven_forge:dwarf,sort=nearest,limit=1,distance=..8] Gossips[0].Target set from entity @p UUID
```

Reopen the same native Dwarven trade UI and inspect the same offer.

Expected result: the offer's player-specific special price is more favorable than its recorded baseline where the offer and its price multiplier permit a visible integer adjustment. The native trade remains a Dwarven Forge MerchantOffer; Questlog must not replace the trade or create a parallel price table.

If the selected offer's multiplier and integer rounding produce no visible change, repeat against another native offer with a non-zero price multiplier rather than treating that single offer as a failure.

For a separate control, Hero of the Village may also be tested through the vanilla effect path. That result must not be confused with the gossip/reputation probe because the installed Dwarf implementation handles Hero discounts separately.

After the probe, remove the synthetic gossip state or discard the validation world. Do not carry this artificial reputation into normal play.

This check proves only that the source-audited inherited pricing mechanism functions in the complete target instance. It does not decide:

- which Dwarven political outcome should modify reputation;
- whether the change is positive or negative for a particular branch;
- the magnitude of the authored change;
- which Dwarves or settlement scope receive it;
- whether the effect is permanent or later reversible.

## Pass criteria

This slice is runtime-valid only when all of the following are directly observed in the target instance:

- a non-toolsmith Dwarf cannot qualify through the anchor tag alone;
- an ordinary toolsmith Dwarf cannot qualify without the Forge-Thane anchor tag;
- the campaign foundation gate works;
- native non-Questlog Dwarf trading remains available;
- provider acceptance transitions directly to ready-to-turn-in with no synthetic objective;
- only the exact issuing Forge-Thane can turn in the accepted quest;
- completion persists through save/reload;
- the contact fact persists;
- Dwarven disposition remains unresolved;
- quest protection remains independent from provider identity;
- inherited vanilla Villager gossip/reputation can alter the installed Dwarf's own native MerchantOffer pricing without a Questlog pricing simulator.
