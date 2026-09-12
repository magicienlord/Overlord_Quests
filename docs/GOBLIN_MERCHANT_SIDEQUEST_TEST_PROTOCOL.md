# Goblin Merchant Sidequest Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: the first post-contact Goblin merchant sidequest only.

## Preconditions

Use the target OVERLORD REIGN Forge 1.20.1 instance with Goblins Tyranny 1.2.3 and the candidate OVERLORD QUESTS artifact.

Establish the production Goblin contact fact through normal campaign play or, for isolated validation only:

```text
/questlog narrative fact set overlord_reign:civilizations/goblins/contact_established
```

Prepare one principal-camp merchant:

```text
/summon goblins_tyranny:merchant ~ ~ ~
/tag @e[type=goblins_tyranny:merchant,sort=nearest,limit=1,distance=..8] add overlord_anchor:goblin_main_merchant
```

A naturally generated merchant in the authored principal camp is required for final world integration. The summon is only a mechanics fixture.

## Check 1: unrelated merchant rejection

Interact with a `goblins_tyranny:merchant` that does not carry `overlord_anchor:goblin_main_merchant`.

Expected result: `Goblin Business` is not offered.

## Check 2: contact gate

Clear the Goblin contact fact and interact with the tagged principal-camp merchant.

Expected result: the sidequest is unavailable.

Restore the contact fact and interact again.

Expected result: `Goblin Business` is offered.

## Check 3: native transaction path

Accept the quest, then complete a normal successful Goblins Tyranny merchant transaction using the source mod's own interaction and purchase flow.

Expected result: the native `goblins_tyranny:merchant_success` advancement becomes complete and, within the advancement objective's polling interval, the sidequest becomes ready for turn-in.

Questlog must not replace the native merchant screen, price logic, stock, currency, or advancement procedure.

## Check 4: retrospective sequence break

On a clean quest state, earn `goblins_tyranny:merchant_success` before accepting `Goblin Business`.

Then establish Goblin contact if necessary, accept the sidequest from the tagged principal-camp merchant, and wait at least one objective polling interval.

Expected result: the advancement objective recognizes the already-complete native advancement and becomes complete without requiring another artificial first-success event.

## Check 5: same-provider turn-in

After the objective is complete, attempt turn-in through a different Goblin merchant.

Expected result: the different merchant cannot complete this accepted sidequest.

Return to the original issuing merchant.

Expected result: same-provider turn-in succeeds and authored completion dialogue remains attached to that issuer.

## Check 6: no invented political state

After completion, verify Goblin disposition.

```text
/questlog narrative disposition get overlord_reign:goblins
```

Expected result: completing this commerce sidequest does not itself set or change Goblin disposition.

The quest also writes no independent narrative fact. Its ordinary persistent completion state is the historical record.

## Check 7: save/reload

Save and quit after completion, then reopen the world.

Expected result:

- the sidequest remains completed;
- it is not offered again as a fresh quest;
- the issuing merchant retains authored completed follow-up if that entity survives;
- Goblin political state is unchanged by the reload.

## Pass criteria

The slice is runtime-valid only when:

- only the designated principal-camp merchant offers it;
- formal Goblin contact gates acceptance;
- native Goblins Tyranny commerce satisfies the objective;
- pre-earned `merchant_success` is recognized retrospectively;
- same-provider turn-in is enforced;
- no duplicate commerce system, reputation value, disposition change, or unnecessary narrative fact is introduced;
- completion survives save/reload.
