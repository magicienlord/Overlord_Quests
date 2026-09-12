# Goblins Tyranny Provider Integration

Status: TECHNICAL / SOURCE-BACKED PRODUCTION INTEGRATION

## Exact installed authority

The production integration was audited against the supplied OVERLORD REIGN instance copy of Goblins Tyranny 1.2.3.

Exact JAR SHA-256:

```text
aa9d337c58a0bfeb0378ab205fb2c70c82a84c85a6e21e5c5c56ee025ec48174
```

Relevant installed identities:

```text
goblins_tyranny:leader_goblin
goblins_tyranny:merchant
goblins_tyranny:merchant_success
```

`goblins_tyranny:merchant_success` is a native advancement awarded by the mod's successful merchant-purchase procedure. Questlog does not recreate the merchant inventory, currency, purchase procedure, or advancement trigger.

## First production sidequest use

`campaign/civilizations/goblins/merchant_business` is available only after the principal Goblin Camp contact fact exists and only from a merchant explicitly marked as belonging to that authored anchor:

```text
overlord_anchor:goblin_main_merchant
```

The quest objective reads the native `goblins_tyranny:merchant_success` advancement through the inherited `questlog:advancement` objective.

`AdvancementObjective` polls authoritative `ServerPlayer` advancement progress. The objective is therefore retrospective: if the player already earned `merchant_success` before accepting the sidequest, the objective recognizes that durable native state after activation instead of requiring an impossible duplicate first-success advancement.

## Scope limitation

The advancement proves that a successful Goblins Tyranny merchant transaction occurred. It does not identify which merchant performed the transaction.

Accordingly, production text must not claim that the required purchase happened specifically with the issuing provider. The provider is scoped to the principal camp and same-provider turn-in preserves the authored local relationship, while the native accomplishment itself remains a Goblins Tyranny-wide merchant milestone.

This limitation is preferable to duplicating or replacing the source mod's commerce system.

## State ownership

Questlog owns only:

- whether this authored sidequest has been accepted/completed;
- its durable issuing-provider binding;
- the requirement that the principal Goblin Camp contact fact already exists.

Goblins Tyranny owns the merchant transaction and `merchant_success` advancement.

The sidequest writes no additional narrative fact and no civilization disposition. Ordinary Quest completion is sufficient persistent history for this low-level sidequest unless later authored content genuinely needs an independent semantic fact.
