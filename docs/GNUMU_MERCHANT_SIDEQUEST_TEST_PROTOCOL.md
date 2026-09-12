# Gnumu Merchant Sidequest Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: the principal-settlement Gnumu merchant sidequest only.

## Preconditions

Use the target Forge 1.20.1 instance with the installed Gnumus settlement 1.0 JAR and the candidate OVERLORD QUESTS artifact.

Establish:

```text
overlord_reign:civilizations/gnumus/contact_established
```

Select the intended merchant belonging to the principal authored Gnumu settlement and apply:

```text
overlord_anchor:gnumu_main_merchant
```

The provider entity must be exactly:

```text
gnumus:gnumus_merchant
```

## Check 1: locality and contact gate

An untagged Gnumu Merchant must not offer `Gnumu Business`.

The tagged merchant must not offer it while the Gnumu contact fact is absent.

With the contact fact present, the tagged principal-settlement merchant should offer the sidequest.

## Check 2: native trade path

Accept the sidequest, then trade through the Gnumus mod's own merchant interaction using Gnumus Doubloons or a Pile of Gnumus Doubloons.

Expected result:

- the source mod awards `gnumus:business_approach`;
- Questlog recognizes the advancement within its normal polling interval;
- the sidequest becomes ready for turn-in;
- Questlog does not replace the native currency, merchant interaction, inventory effects, or trade procedure.

## Check 3: retrospective recognition

On a fresh quest state, earn `gnumus:business_approach` before accepting `Gnumu Business`, then accept the sidequest from the tagged principal-settlement merchant.

Expected result: the objective completes from durable advancement state without requiring a duplicate first trade.

## Check 4: same-provider turn-in

A different Gnumu Merchant must not complete the accepted sidequest. The original issuing merchant must be able to turn it in.

## Check 5: state and lore boundary

After completion verify:

- Gnumu disposition is unchanged;
- no additional narrative fact was created;
- the sidequest does not reveal or imply the Gnumus' hidden ancestry;
- ordinary Quest completion remains the persistent historical record.

## Check 6: save/reload

Save and reload after completion.

Expected result: quest completion and issuing-provider follow-up persist, and the quest is not offered again as fresh content.

## Pass criteria

Pass only when anchor locality, formal-contact gating, native Doubloon trade, retrospective advancement recognition, same-provider turn-in, lore firewall, and no-extra-political-state behavior are all directly observed.
