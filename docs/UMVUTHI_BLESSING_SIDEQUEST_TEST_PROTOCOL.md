# Umvuthi Blessing Sidequest Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: the designated Grove's neutral Sun's Blessing service only.

## Preconditions

Use the target Forge 1.20.1 instance with Mowzie's Mobs 1.8.2 and the candidate OVERLORD QUESTS artifact.

Use the exact designated Umvuthi carrying:

```text
overlord_anchor:umvuthana_main_umvuthi
```

Establish the formal audience fact and neutral disposition through normal campaign progression or isolated test setup:

```text
/questlog narrative fact set overlord_reign:civilizations/umvuthana/contact_established
/questlog narrative disposition set overlord_reign:umvuthana overlord_reign:neutral
```

## Check 1: political gating

With contact present but disposition cleared or set to a non-neutral test value, interact with the designated Umvuthi.

Expected result: `The Sun's Blessing` is unavailable.

Restore `overlord_reign:neutral`.

Expected result: the sidequest becomes available from the designated Umvuthi.

An unrelated Umvuthi without the anchor tag must not offer the sidequest.

## Check 2: native trade path

Accept the sidequest and use Mowzie's Mobs' own Umvuthi trade GUI. Fulfill Umvuthi's native desire according to the source-mod interaction.

Expected result:

- the native server trade validates the exact Umvuthi customer and container;
- `fulfillDesire` succeeds;
- the source mod remembers the trade for the player;
- Mowzie's Mobs applies `mowziesmobs:suns_blessing`;
- the native `mowziesmobs:suns_blessing` advancement becomes complete;
- Questlog recognizes that advancement and moves the sidequest to ready-for-turn-in.

Questlog must not grant the blessing effect itself or replace the native offering logic.

## Check 3: retrospective recognition

On a test state where the player has already legitimately earned `mowziesmobs:suns_blessing`, activate the production sidequest afterward while the Grove is neutral.

Expected result: the advancement objective recognizes the already-complete native advancement without requiring Umvuthi to repeat a one-time trade.

## Check 4: same-provider turn-in

A different Umvuthi must not complete the accepted sidequest. The original designated issuing Umvuthi must complete the turn-in.

## Check 5: hostility boundary

After a fresh audience state, deliberately test the source-mod misbehavior route in a disposable world according to the existing Umvuthi audience protocol.

Expected result: Questlog's peace integration must not erase Mowzie's own offender state. The blessing sidequest's neutral political gate is not a generic immunity from native retaliation.

## Check 6: destructive route separation

Do not complete `mowziesmobs:kill_umvuthi` as part of this sidequest. Killing Umvuthi belongs to the separate destructive route and must not count as a substitute for receiving the blessing.

## Check 7: persistence and state boundary

After successful completion, save and reload.

Expected result:

- ordinary Quest completion persists;
- the issuing Umvuthi retains authored completed follow-up if still present;
- Questlog has not written another narrative fact or changed disposition merely for receiving the blessing;
- Mowzie's own blessing/trade state remains authoritative.

## Pass criteria

Pass only when neutral-Grove gating, exact-anchor scope, native Umvuthi trade ownership, native blessing advancement, retrospective recognition, same-provider turn-in, offender-state preservation, and destructive-route separation are all directly observed.
