# Sea Dweller Barter Sidequest Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: the principal Sea Village aquamarine-barter sidequest only.

## Preconditions

Use the target Forge 1.20.1 instance with Sea Dwellers 2.9.9 and the candidate OVERLORD QUESTS artifact.

Establish:

```text
overlord_reign:civilizations/sea_dwellers/contact_established
```

Select one actual Mermorph belonging to the authored principal Sea Village and apply:

```text
overlord_anchor:sea_dweller_main_trader
```

The provider must also belong to the source-owned `#seadwellers:mermorphs` entity tag.

## Check 1: locality and contact gate

An untagged Mermorph must not offer `Sea Village Barter`.

The tagged trader must not offer it before the Sea Dweller contact fact exists.

With the contact fact present, the tagged trader should offer the sidequest.

## Check 2: native aquamarine barter

Accept the sidequest and complete Sea Dwellers' own aquamarine barter flow with a qualifying Mermorph.

Expected result:

- Sea Dwellers awards `seadwellers:adv_barter_aquamarine`;
- Questlog recognizes the advancement within its polling interval;
- the sidequest becomes ready for turn-in;
- barter currency, loot and interaction behavior remain source-mod owned.

## Check 3: retrospective recognition

On a fresh sidequest state, earn `seadwellers:adv_barter_aquamarine` before accepting the quest. Then accept from the tagged principal-village trader.

Expected result: the objective completes from durable advancement state without requiring an artificial second first-barter milestone.

## Check 4: same-provider turn-in

A different Mermorph must not turn in the accepted sidequest. The issuing tagged trader must be able to complete it.

## Check 5: rejected nautilus signal

Do not use `seadwellers:adv_barter_nautilus` as test setup or a substitute objective. The audited 2.9.9 binary does not provide a verified award path for that impossible-criterion advancement.

## Check 6: state boundary and persistence

Completion must not alter Sea Dweller disposition or create a redundant narrative fact. Save/reload must preserve ordinary Quest completion and issuing-provider follow-up.

## Pass criteria

Pass only if anchor locality, contact gating, native aquamarine barter, retrospective advancement recognition, same-provider turn-in, rejected-nautilus boundary, and no-extra-political-state behavior all hold.
