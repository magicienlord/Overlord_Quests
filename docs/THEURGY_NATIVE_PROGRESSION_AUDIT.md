# Theurgy Native Progression Audit

Status: VERIFIED NATIVE SIGNAL / PRODUCTION NARRATIVE ROLE NOT YET ASSIGNED

Date: 2026-09-12

## Purpose

This audit identifies sequence-break-safe native Theurgy progression signals that OVERLORD QUESTS may observe without replacing Theurgy's own progression loop.

It does not establish a new Dark Tower room, facility, quest, or story fact by itself.

## Installed OVERLORD REIGN artifact

The validated Core System Unification authority identifies the active Theurgy artifact as:

```text
theurgy-1.20.1-1.30.0-OverlordReign-A4R2.jar
SHA-256 ff42cb0c12b19191d9923af7f136d820fce2ac41d4d2cd0d4c85e4a17d050755
```

A4R2 is not an unknown fork. The project forensic record establishes that the original installed `theurgy-1.20.1-1.30.0.jar` matched the published upstream 1.30.0 release and that A4R2 changed exactly two JAR entries:

```text
com/klikli_dev/theurgy/content/recipe/AccumulationRecipe.class
com/klikli_dev/theurgy/content/apparatus/salammoniacaccumulator/SalAmmoniacAccumulatorBlockEntity.class
```

The JAR entry set is unchanged. No resource, recipe JSON, configuration, metadata, advancement data, or unrelated class was changed by A4R2.

Therefore upstream 1.30.0 advancement resources remain applicable to the installed A4R2 artifact.

## Verified upstream advancement

The exact upstream 1.20.1 1.30.0 generated advancement contains:

```text
theurgy:has_liquefaction_cauldron
```

Its criterion is the vanilla `minecraft:inventory_changed` trigger for:

```text
theurgy:liquefaction_cauldron
```

The generated advancement has parent:

```text
theurgy:book_root
```

The source datagen implementation constructs the same milestone from `ItemRegistry.LIQUEFACTION_CAULDRON`.

This is a durable vanilla advancement and is therefore suitable for retrospective observation by OVERLORD QUESTS. If the player obtains the cauldron before a REIGN quest formally points toward that Theurgy milestone, an advancement objective can recognize the already-completed native state rather than requiring duplicate work.

## Other verified upstream progression signals

The same exact upstream datagen surface also defines:

```text
theurgy:book_root
theurgy:has_basic_rod
theurgy:has_amethyst_rod
theurgy:has_t2_rod
theurgy:has_t3_rod
theurgy:has_t4_rod
theurgy:has_rare_rod
theurgy:has_precious_rod
theurgy:has_liquefaction_cauldron
```

These are technically available native signals because A4R2 does not alter the advancement resources. This audit does not assign campaign meaning to all of them.

## Questlog compatibility

OVERLORD QUESTS already supports the `questlog:advancement` objective. No new objective type, reflection bridge, Theurgy hard dependency, private NBT access, or command shim is required to observe these milestones.

A future source-authorized definition may therefore use a native objective of the form:

```json
{
  "type": "questlog:advancement",
  "advancement": "theurgy:has_liquefaction_cauldron"
}
```

The exact field spelling must continue to follow the validated Questlog advancement-objective schema at authoring time.

## Lore and design boundary

The lore authority establishes that the damaged Dark Tower contains purpose-built rooms whose functions may be restored through relevant installed-mod progression. Theurgy's established REIGN domain is matter and occult material transformation.

Those facts make Theurgy a valid candidate for Tower infrastructure integration, but they do not by themselves define:

- the canonical name of a Theurgy-linked Tower facility;
- which physical Tower room hosts it;
- whether the Liquefaction Cauldron is the activation milestone for that facility;
- when that milestone appears in the hidden campaign;
- what Gnarl says about it;
- what later quests depend on it.

Those points remain UNKNOWN until resolved by source-authorized campaign construction. OVERLORD QUESTS must not promote this technical signal into canon merely because it is convenient.

## Sequence-break rule

If `theurgy:has_liquefaction_cauldron` is later adopted as a campaign milestone, the native advancement should remain authoritative evidence of the accomplishment.

The quest must not require the player to discard and reacquire the cauldron, craft a duplicate merely to satisfy Questlog, clear the native advancement, or use a parallel Questlog-only state when the advancement already expresses the required fact.

If a separate persistent REIGN fact is needed for later narrative gating, it should be written only after the native advancement objective resolves, so the REIGN fact records campaign interpretation while Theurgy remains the authority for the underlying mechanical accomplishment.

## Current decision

Exact A4R2 artifact compatibility with upstream advancement resources: VERIFIED.

`theurgy:has_liquefaction_cauldron` existence and criterion: VERIFIED.

Use as a retrospective Questlog progression signal: TECHNICALLY APPROVED.

Specific Dark Tower facility assignment: UNKNOWN.

Production quest wiring: NOT YET AUTHORIZED BY A CONCRETE CAMPAIGN MILESTONE.
