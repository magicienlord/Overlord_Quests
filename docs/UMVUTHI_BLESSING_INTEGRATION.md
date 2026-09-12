# Umvuthi Blessing Integration

Status: TECHNICAL / SOURCE-BACKED PRODUCTION INTEGRATION

## Exact installed authority

This integration uses the supplied Mowzie's Mobs 1.8.2 JAR.

```text
SHA-256 e8ce1768cda6f1e1fadd2321c92921b473bd0cee45ba4b8387fb30f8326cb31c
```

Relevant exact identities:

```text
mowziesmobs:umvuthi
mowziesmobs:suns_blessing
```

The native `mowziesmobs:suns_blessing` advancement uses the ordinary `minecraft:effects_changed` trigger and requires the `mowziesmobs:suns_blessing` effect.

## Native trade ownership

Bytecode inspection of `MessageUmvuthiTrade$Handler` establishes the authoritative server path:

1. resolve the packet entity and require an actual `EntityUmvuthi`;
2. require that the packet sender is Umvuthi's current customer;
3. require the native `ContainerUmvuthiTrade` menu;
4. check `EntityUmvuthi.hasTradedWith(player)`;
5. call `EntityUmvuthi.fulfillDesire(slot)`;
6. on success call `EntityUmvuthi.rememberTrade(player)`;
7. apply the native `SUNS_BLESSING` mob effect to the player;
8. trigger Umvuthi's native blessing ability and sound.

Questlog does not duplicate or bypass any of these checks. It does not decide the desired item, consume the offering, grant the effect, remember the trade, or replace the source GUI.

## Production sidequest use

`campaign/civilizations/umvuthana/suns_blessing` is offered by the same exact designated Grove Umvuthi used by first audience:

```text
overlord_anchor:umvuthana_main_umvuthi
```

Acceptance requires both:

```text
overlord_reign:civilizations/umvuthana/contact_established
overlord_reign:umvuthana = overlord_reign:neutral
```

The disposition gate is deliberate. A historical audience fact alone must not keep peaceful Umvuthi services available after an authored later hostile transition.

The sidequest observes the native `mowziesmobs:suns_blessing` advancement. Because the Questlog advancement objective reads durable player advancement state, an already-earned blessing is recognized retrospectively after the sidequest activates.

## Native destructive route remains separate

Mowzie's Mobs also owns the native `mowziesmobs:kill_umvuthi` advancement. This sidequest does not alter, suppress, or reinterpret that destructive boss path.

The existing designated-Grove peace bridge separately preserves Mowzie's own misbehaving-player exception. A player marked by the exact Umvuthi as an offender does not regain a peaceful route merely because old contact history exists.

## State boundary

Completing the blessing sidequest writes no additional narrative fact and no new disposition. The durable outputs are:

- Mowzie's own remembered trade and Sun's Blessing state;
- the native `mowziesmobs:suns_blessing` advancement;
- ordinary Quest completion and provider-specific follow-up.
