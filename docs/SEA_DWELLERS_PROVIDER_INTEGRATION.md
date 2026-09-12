# OVERLORD QUESTS Sea Dwellers Provider Integration

Status: TECHNICAL SOURCE AUDIT / PRODUCTION PROVIDER SUPPORT

Date: 2026-09-12

## Authority

The current OVERLORD REIGN lore authority establishes one designated Sea Village as the principal local Sea Dweller civilization anchor. Its civilization main quest begins through one protected, explicitly marked senior Sea Dweller trader assigned the social title `Sea Elder`. Ordinary Sea Villages and Sea Dwellers elsewhere must not automatically become civilization quest starters.

This document records the exact installed Realm RPG: Sea Dwellers 2.9.9 behavior relevant to that provider. It does not define later Sea Dweller political branches or disposition outcomes.

The previously proposed Ocean Dragon handoff was based on a false assumption and has been removed from the project authority. No Ocean Dragon progression, dependency, milestone, or political state is part of this integration.

## Installed artifact authority

The supplied target instance contains:

```text
realmrpg_seadwellers_2.9.9_forge_1.20.1.jar
```

SHA-256:

```text
6cf9dd9c5ba8dfb9644b0598b1b3453fb3b4cefc1ffa3ae676e4be82309d90f5
```

Its packaged `META-INF/mods.toml` declares:

```text
modId="seadwellers"
version="2.9.9"
minecraft="[1.20.1]"
loaderVersion="[47,)"
```

The installed JAR is therefore the compatibility authority for this integration.

## Native Sea Dweller identity

The installed mod publishes the entity-type tag:

```text
seadwellers:mermorphs
```

Its exact 2.9.9 contents are:

```text
seadwellers:mermorph
seadwellers:mermorph_coral_yellow
seadwellers:mermorph_hunter
seadwellers:mermorph_architect
seadwellers:mermorph_cavern
seadwellers:mermorph_coral_orange
seadwellers:mermorph_blacksmith
seadwellers:mermorph_farmer
seadwellers:mermorph_collector
seadwellers:mermorph_polar
seadwellers:mermorph_river
```

The production provider should therefore use the source-owned entity tag rather than hard-code one arbitrary Mermorph subtype as if it were the only valid elder candidate.

The same installed artifact divides those eleven variants among native Mermorph trade tags. Together the trade tags cover all eleven normal Mermorph variants. The lore requirement that the selected Sea Elder be a senior trader is therefore compatible with selecting one of the source-native Mermorphs and assigning an authored social role on top of that native entity identity.

## Sea Elder role

`Sea Elder` is not a native Sea Dwellers profession or entity class. It is an OVERLORD REIGN social and political role.

The production provider therefore requires three independent identity layers:

```text
native entity family: #seadwellers:mermorphs
authored anchor tag: overlord_anchor:sea_dweller_main_elder
authored role tag: overlord_role:sea_elder
```

The quest definition expresses the authored role as:

```json
"role": "sea_elder"
```

Questlog's generic role bridge requires the matching scoreboard tag `overlord_role:sea_elder` on a non-Villager entity.

Quest-critical protection remains separate:

```text
overlord_quest_protected
```

The separation is mandatory:

- `#seadwellers:mermorphs` proves source-native Sea Dweller identity;
- `overlord_anchor:sea_dweller_main_elder` proves membership in the designated principal Sea Village and local anchor identity;
- `overlord_role:sea_elder` proves the authored senior social role;
- `overlord_quest_protected` prevents accidental loss before an authored route allows death.

None of these conditions gives the selected Elder political authority over every Sea Dweller or every Sea Village.

## Native interaction preservation

Sea Dwellers 2.9.9 contains native Mermorph barter behavior. OVERLORD QUESTS must not replace that ordinary interaction surface.

Questlog retains its non-invasive provider gesture:

```text
sneak + main-hand entity interaction
```

The provider event is consumed only when the selected entity currently exposes relevant Questlog provider content. Ordinary non-sneaking interaction remains the responsibility of Sea Dwellers 2.9.9.

## Production boundary

The exact installed audit establishes enough technical support for a first-contact provider:

- the installed mod/version and JAR identity are known;
- the mod itself publishes the complete normal Mermorph entity family as `seadwellers:mermorphs`;
- native barter behavior exists across that Mermorph family;
- one explicit anchor tag can narrow the provider to the designated Sea Village before final coordinates exist;
- one explicit role tag can assign the lore-authorized `Sea Elder` social title without inventing a native profession;
- Questlog can preserve source-native interactions by using the separate sneak-provider gesture.

It does NOT establish:

- exact final Sea Village coordinates;
- a native `Sea Elder` profession;
- any Ocean Dragon progression or questline;
- later Sea Dweller disposition outcomes;
- destruction, subjugation, tribute, expanded services, or branch rewards;
- global political authority over all Sea Dwellers;
- any rewrite of ordinary Sea Dweller AI or trade behavior.

Those later outcomes remain governed by explicit campaign authoring and the newer civilization decisions.
