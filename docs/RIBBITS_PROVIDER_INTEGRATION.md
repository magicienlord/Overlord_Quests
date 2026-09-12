# OVERLORD QUESTS Ribbits Provider Integration

Status: TECHNICAL SOURCE AUDIT / PRODUCTION PROVIDER SUPPORT

Date: 2026-09-12

## Authority

The current OVERLORD REIGN lore authority establishes a designated Ribbit Village as the Ribbit civilization anchor. Its civilization main quest begins through one protected, explicitly marked Gardener assigned the local social role of Elder. Ordinary Ribbit Gardeners and ordinary Ribbit Villages must not automatically become civilization quest starters.

Ribbits should retain the source mod's peaceful, cheerful, agricultural, trading, and musical character. This document defines only the technical provider-role bridge needed to identify an actual Gardener at the selected village. It does not define later Ribbit political outcomes or dialogue branches.

## Installed build and matching source

The captured target instance contains:

```text
Ribbits-1.20.1-Forge-3.0.5.jar
```

The public `yungnickyoung/Ribbits` `1.20.1` branch reports:

```text
version=3.0.5
mc_version=1.20.1
```

The public source therefore matches the installed Ribbits release used by this integration audit.

## Native profession model

Ribbits uses one entity type:

```text
ribbits:ribbit
```

Profession is not represented by a separate entity registry ID. `RibbitEntity` stores a synchronized and persistent `RibbitData` object. Its public source surface exposes:

```text
RibbitEntity#getRibbitData()
RibbitData#getProfession()
RibbitProfession#getId()
```

`RibbitProfessionModule` registers the built-in Gardener profession as:

```text
ribbits:gardener
```

`RibbitEntity` also uses that same profession object to install the Gardener crop-watering goal, so this is gameplay identity rather than a cosmetic label.

## Questlog compatibility strategy

Ribbits remains an optional mod dependency for Questlog. OVERLORD QUESTS does not directly link against Ribbits classes at compile time.

`QuestProviderNativeRoleBridge` resolves the documented public profession chain through reflection only when the candidate entity type is exactly `ribbits:ribbit`.

A provider definition may therefore use:

```json
"entity_types": ["ribbits:ribbit"],
"role": "ribbits:gardener"
```

and Questlog will require the candidate Ribbit's native profession ID to equal `ribbits:gardener`.

The bridge fails closed if the optional source class or expected public methods are unavailable. It does not silently treat every Ribbit as a Gardener.

The existing explicit scoreboard role bridge remains valid for authored social roles that do not exist as native professions. Native profession matching does not replace anchor identity.

## Elder identity

The lore role `Elder` is local political identity, not a native Ribbits profession.

Production civilization content therefore separates two conditions:

1. native profession: `ribbits:gardener`;
2. authored principal-anchor identity, carried by an explicit scoreboard tag on the one selected Elder at the designated village.

The recommended production anchor tag is:

```text
overlord_anchor:ribbit_main_elder
```

Quest-critical protection remains separate:

```text
overlord_quest_protected
```

This prevents three incorrect inferences:

- not every Gardener is the Elder;
- not every Ribbit Village is the principal civilization anchor;
- protection status does not define provider identity.

## Native interaction compatibility

`RibbitEntity` implements `Merchant` and handles ordinary interaction itself when trade offers are available. OVERLORD QUESTS retains its temporary non-invasive provider interaction contract of sneak plus main-hand interaction, so ordinary non-sneaking Ribbit behavior is not replaced by the quest provider layer.

Ribbits also uses a secondary-use interaction with an amethyst shard to update a Ribbit's home position. Runtime validation of the civilization provider should therefore use an empty main hand when opening the Questlog provider menu so that the test does not conflate two separate interaction contracts.

## Production boundary

This audit proves enough technical identity for a Ribbit first-contact provider:

- exact installed source version is matched;
- exact entity registry ID is known;
- exact native Gardener profession ID is known;
- public persistent profession access is known;
- optional reflection preserves Questlog's loader independence;
- local Elder identity can remain an authored anchor tag until final world placement.

It does NOT establish:

- exact final village coordinates;
- later Ribbit disposition outcomes;
- tribute, services, destruction, or branch rewards;
- any requirement to rewrite Ribbits AI or trade systems;
- a new global Ribbit leadership structure.

Those remain governed by the newer civilization decisions and later campaign authoring.
