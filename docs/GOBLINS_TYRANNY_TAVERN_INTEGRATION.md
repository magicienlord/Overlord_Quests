# Goblins Tyranny Tavern Integration

Status: TECHNICAL / SOURCE-BACKED PRODUCTION INTEGRATION

## Exact installed authority

This integration uses the supplied Goblins Tyranny 1.2.3 JAR:

```text
SHA-256 aa9d337c58a0bfeb0378ab205fb2c70c82a84c85a6e21e5c5c56ee025ec48174
```

The exact binary registers:

```text
goblins_tyranny:bartender_goblin
```

The native advancement:

```text
goblins_tyranny:liquor_success
```

has the installed English description:

```text
Purchase some liquor from the bartender
```

Bytecode inspection confirms the native Goblin liquor purchase procedures award `liquor_success` through Minecraft's player advancement state after a successful purchase. The relevant procedures include `GoblinLiquorProcedure`, `BlazingLiquorProcedure`, and `DeadlyLiquorProcedure`.

## Production sidequest use

`campaign/civilizations/goblins/tavern_business` is available only from a bartender explicitly assigned to the principal camp with:

```text
overlord_anchor:goblin_main_bartender
```

The sidequest observes `goblins_tyranny:liquor_success` with `questlog:advancement`. Questlog does not recreate the bartender inventory, currency check, liquor items, purchase procedure, or drinking effects.

Because the advancement is durable player state, a qualifying purchase made before sidequest acceptance is recognized retrospectively once the quest becomes active.

## State boundary

The tavern sidequest records ordinary quest completion and same-provider follow-up only. It does not create reputation, disposition, a redundant liquor fact, or a global relationship with every Goblin bartender.
