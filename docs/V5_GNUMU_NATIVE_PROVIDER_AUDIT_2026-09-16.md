# OVERLORD REIGN V5 Gnumu Native Provider Audit

Status: VERIFIED TECHNICAL FACT / V5 SUPPORTING AUDIT

Date: 2026-09-16

Purpose: close the recurring-Hunter provider persistence question against the exact supplied Gnumus JAR without creating new campaign meaning.

## 1. Audited artifact

The supplied modpack contains:

```text
gnumus_settlement_[Forge]1.20.1_v1.0.jar
```

The exact binary registers both:

```text
gnumus:gnumus_hunter
gnumus:gnumus_hunter_not_dispawn
```

The second entity has a dedicated source class:

```text
GnumusHunterNotDispawnEntity
```

This gives V5 a source-owned persistence-safe hunter entity for any recurring authored Hunter provider.

## 2. V5 consequence

If the Gnumu civilization blueprint requires one recurring Hunter representative, the later implementation should prefer:

```text
gnumus:gnumus_hunter_not_dispawn
```

rather than adding artificial persistence behavior to an ordinary hunter.

This is a technical provider-binding decision only.

It does not establish:

- a new Gnumu military office;
- a civilization-wide Hunter caste;
- a terminal political route;
- any new lore concerning Gnumu social hierarchy.

The authored role remains simply the canonical settlement's recurring Hunter representative.

## 3. Other verified source identities

The same exact binary registers the relevant recurring social entities:

```text
gnumus:gnumus_worker
gnumus:gnumus_shaman
gnumus:gnumus_merchant
gnumus:gnumus_hunter
gnumus:gnumus_hunter_not_dispawn
```

It also supplies generated settlement and supporting structure templates including:

```text
large_gnumus_settlement
gnumus_settlement
small_gnumus_settlement
shamans_hut
traders_rest
gnumus_farm
gnumus_ruins
gnumus_small_ruins
```

These are source surfaces only. They acquire campaign meaning only through the approved V5 Gnumu blueprint.
