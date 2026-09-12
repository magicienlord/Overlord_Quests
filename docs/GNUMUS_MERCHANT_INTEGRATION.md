# Gnumus Merchant Integration

Status: TECHNICAL / SOURCE-BACKED PRODUCTION INTEGRATION

## Exact installed authority

This integration uses the supplied `gnumus_settlement_[Forge]1.20.1_v1.0.jar`.

```text
SHA-256 e805b1fcf9c173ae7d488763d67cef7a49d658884da95f9268e87caed7a905cf
```

The exact binary registers:

```text
gnumus:gnumus_merchant
gnumus:gnumus_shaman
```

The native advancement:

```text
gnumus:business_approach
```

has installed English description:

```text
Trade with Gnumus Merchant using Gnumus Doubloons or Pile of Gnumus Doubloons
```

`GnumusMerchantClicProcedure` directly awards `gnumus:business_approach` through Minecraft's authoritative player advancement state during the native merchant interaction path.

## Production sidequest use

`campaign/civilizations/gnumus/merchant_business` is offered only by one merchant explicitly assigned to the principal authored settlement:

```text
overlord_anchor:gnumu_main_merchant
```

The provider itself is the exact native `gnumus:gnumus_merchant` entity. The quest becomes available after:

```text
overlord_reign:civilizations/gnumus/contact_established
```

Questlog observes `gnumus:business_approach` with its retrospective advancement objective. It does not recreate Gnumus Doubloons, the merchant interaction, trade effects, inventory logic, or the native advancement trigger.

## State boundary

Completion records ordinary Quest completion and issuing-provider follow-up only. It does not:

- set Gnumu disposition;
- create a numeric trade reputation;
- duplicate `business_approach` as a narrative fact;
- reveal the Gnumus' hidden ancestry;
- imply that every Gnumu merchant belongs to the principal settlement;
- assign final settlement coordinates.
