# Goblin Tavern Sidequest Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: the principal-camp bartender liquor-purchase sidequest only.

## Preconditions

Use the target Forge 1.20.1 instance with Goblins Tyranny 1.2.3 and the candidate OVERLORD QUESTS artifact.

Establish the principal Goblin Camp contact fact, then select the intended local bartender and apply:

```text
overlord_anchor:goblin_main_bartender
```

The provider entity must be exactly:

```text
goblins_tyranny:bartender_goblin
```

## Check 1: locality and contact gate

An untagged bartender must not offer `A Goblin Drink`.

The tagged bartender must not offer it while `overlord_reign:civilizations/goblins/contact_established` is absent.

Once the contact fact is present, the tagged bartender should offer the sidequest.

## Check 2: native purchase

Accept the sidequest and purchase liquor through Goblins Tyranny's normal bartender system.

Expected result:

- the source mod awards `goblins_tyranny:liquor_success`;
- Questlog recognizes that advancement within its normal polling interval;
- the quest becomes ready for same-provider turn-in;
- Questlog does not replace the native purchase GUI, currency check, liquor item, or effects.

## Check 3: retrospective completion

On a fresh quest state, earn `goblins_tyranny:liquor_success` before accepting the sidequest. Then accept it from the tagged bartender.

Expected result: the advancement objective completes retrospectively without requiring a duplicate first purchase.

## Check 4: same-provider turn-in

A different bartender must not complete the accepted quest. The original issuing bartender must be able to turn it in.

## Check 5: political-state boundary

Completing the tavern sidequest must not alter Goblin disposition or create an independent narrative fact. Ordinary quest completion is the persistent record.

## Check 6: save/reload

After completion, save and reload. Completion and issuing-provider follow-up must persist, and the quest must not be offered again as fresh content.

## Pass criteria

Pass only when anchor locality, contact gating, native liquor purchase, retrospective advancement recognition, same-provider turn-in, and no-extra-state behavior are all directly observed.
