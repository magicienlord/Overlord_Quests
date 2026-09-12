# Goblin Engineer Sidequest Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: the principal-camp engineer workbench sidequest only.

## Preconditions

Use the target Forge 1.20.1 instance with Goblins Tyranny 1.2.3 and the candidate OVERLORD QUESTS artifact.

Establish the principal Goblin Camp contact fact through normal play or isolated test setup:

```text
/questlog narrative fact set overlord_reign:civilizations/goblins/contact_established
```

Choose one native engineer in the authored principal camp and apply:

```text
overlord_anchor:goblin_main_engineer
```

For isolated mechanics testing, either of the following exact entity types may be used:

```text
goblins_tyranny:engineer_goblin
goblins_tyranny:engineeress_goblin
```

## Check 1: locality

Interact with an untagged Engineer Goblin or Engineeress Goblin.

Expected result: `Goblin Engineering` is not offered.

Interact with the tagged principal-camp engineer after the Goblin contact fact is present.

Expected result: the sidequest is available.

## Check 2: contact gate

Clear `overlord_reign:civilizations/goblins/contact_established` and interact with the tagged engineer.

Expected result: the sidequest is unavailable.

Restore the contact fact before continuing.

## Check 3: native engineer progression

Accept the sidequest, then use Goblins Tyranny's native engineer workbench flow.

Expected result:

- the source mod awards `goblins_tyranny:engineer_success`;
- Questlog recognizes the completed advancement within its normal polling interval;
- the quest becomes ready for turn-in;
- no alternate Questlog engineering interface appears.

## Check 4: retrospective completion

On a fresh quest state, use the native engineer workbench and earn `goblins_tyranny:engineer_success` before accepting the sidequest.

Then accept `Goblin Engineering` from the tagged provider.

Expected result: the objective completes from durable advancement state without requiring another workbench activation.

## Check 5: same-provider turn-in

After objective completion, attempt turn-in from another engineer.

Expected result: only the exact issuing engineer can complete the turn-in.

## Check 6: state boundaries

After completion verify:

- Goblin disposition is unchanged;
- no new narrative fact was created by this sidequest;
- ordinary Quest completion persists;
- completed provider dialogue belongs to the issuing engineer.

## Check 7: save/reload

Save and reload after completion.

Expected result: quest completion and issuing-provider follow-up persist and the quest is not offered again as fresh content.

## Pass criteria

Pass only if the authored anchor scope, native workbench advancement, retrospective recognition, same-provider turn-in, and no-extra-political-state boundaries all behave as specified.
