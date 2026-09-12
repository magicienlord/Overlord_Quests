# Sea Dwellers Barter Integration

Status: TECHNICAL / SOURCE-BACKED PRODUCTION INTEGRATION

## Exact installed authority

This integration uses the supplied `realmrpg_seadwellers_2.9.9_forge_1.20.1.jar`.

```text
SHA-256 6cf9dd9c5ba8dfb9644b0598b1b3453fb3b4cefc1ffa3ae676e4be82309d90f5
```

The source-owned entity family used by the principal Sea Village remains:

```text
#seadwellers:mermorphs
```

The exact JAR defines the native advancement:

```text
seadwellers:adv_barter_aquamarine
```

with installed English description:

```text
Do an aquamarine barter with mermorph
```

`MermorphBarterProcedure` and the Collector barter path award `adv_barter_aquamarine` through Minecraft's authoritative player advancement state after a qualifying native barter.

## Production sidequest use

`campaign/civilizations/sea_dwellers/aquamarine_barter` is offered only by one Mermorph explicitly marked as the designated principal-village trader:

```text
overlord_anchor:sea_dweller_main_trader
```

The source mod remains owner of the barter interaction, accepted currency, returned loot, entity behavior, and advancement. Questlog only observes the durable advancement and records the authored sidequest relationship.

Because `questlog:advancement` polls existing ServerPlayer advancement state, a qualifying aquamarine barter completed before sidequest acceptance is recognized retrospectively.

## Rejected native signal

The exact 2.9.9 JAR also contains:

```text
seadwellers:adv_barter_nautilus
```

Its JSON criterion is `minecraft:impossible`, and the binary audit found no code reference that awards `adv_barter_nautilus`. It is therefore not a valid production quest milestone unless a later mod version or verified integration supplies an authoritative trigger.

The fish-barter advancement belongs to the wild Mermorph barter path and is not used as the principal Sea Village commerce milestone.

## State boundary

Completing the aquamarine barter sidequest writes no additional narrative fact and no Sea Dweller disposition. Ordinary Quest completion is sufficient history for this low-level sidequest.
