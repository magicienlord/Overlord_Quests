# Theurgy Native Progression Audit

Status: DEEP TECHNICAL AUDIT / VERIFIED NATIVE SIGNALS / PRODUCTION NARRATIVE ROLE NOT YET ASSIGNED

Date: 2026-09-12

## Purpose

This audit identifies sequence-break-safe native Theurgy progression signals that OVERLORD QUESTS may observe without replacing Theurgy's own progression loop.

Theurgy is treated as a deep-integration mod because it is a staged gameplay system with apparatus dependencies, persistent knowledge/progression surfaces, material-processing loops, and several later capabilities. It is not reduced to a single arbitrary quest checkbox.

This document does not establish a new Dark Tower room, facility, quest, or story fact by itself.

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

Therefore upstream 1.30.0 advancement resources and authored progression topology remain applicable to the installed A4R2 artifact except for the already documented behavior correction in those two machine classes.

Exact upstream source reference used by this audit:

```text
klikli-dev/theurgy
aa440153154ea1691010412a5d79dd96b85810e6
```

## Verified native advancement surface

The exact upstream advancement generator defines the following complete advancement set:

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

`theurgy:book_root` is a presence/root marker driven by a player tick and should not be treated as meaningful capability progression.

The rod milestones use vanilla inventory-change criteria for their corresponding native rod items. `theurgy:has_basic_rod` is an OR milestone satisfied by the tier-1 rod or the abundant/common sulfur-attuned rods. The remaining rod advancements represent specific later rod ownership milestones.

`theurgy:has_liquefaction_cauldron` uses the vanilla `minecraft:inventory_changed` trigger for:

```text
theurgy:liquefaction_cauldron
```

These completed advancements are durable player progression evidence and are directly observable through OVERLORD QUESTS' existing `questlog:advancement` objective. No reflection bridge, private NBT access, command shim, or new Theurgy dependency is required.

## Verified gameplay topology

The generated Hermetica book and exact source recipes show that Theurgy is a capability graph rather than a flat item list.

### 1. Divination branch

Theurgy provides tiered divination rods and sulfur-attuned variants. This branch has the strongest native advancement coverage because the mod exposes persistent advancement milestones for basic, amethyst, tier-2, tier-3, tier-4, rare, and precious rods.

If REIGN later needs to know that a rod capability tier has been reached, the corresponding Theurgy advancement should be preferred over a Questlog-local duplicate state.

### 2. Basic spagyrics and the three alchemical principles

The Hermetica places the basic apparatus stage before creation of the three core alchemical principles.

Verified apparatus semantics from the exact upstream language/source surface:

- Calcination Oven extracts Alchemical Salt from items. Salt represents physical matter/body.
- Liquefaction Cauldron extracts Alchemical Sulfur from items using solvent. Sulfur represents the idea/soul and participates in replication/transmutation.
- Mercury Distiller extracts Alchemical Mercury from items. Mercury is the energy/catalytic principle.
- Pyromantic Brazier supplies heat to apparatus.
- Sal Ammoniac Accumulator and Tank create/store the solvent used by Liquefaction.

The generated book makes `create_salt`, `create_mercury`, and `create_solvent` descend from the basic apparatus entry, while `create_sulfur` descends from solvent creation. This is native progression structure, not a REIGN-authored ordering.

Only Liquefaction currently has a dedicated vanilla advancement milestone. The absence of matching advancements for every apparatus is significant and must not be papered over by pretending they exist.

### 3. Incubation

The native book's `incubation` entry requires the Salt, Mercury, and Sulfur branches as parents. The Incubator then recombines the three principles into items and requires heat plus the relevant ingredient vessels.

This is a genuine later capability boundary. It should not be represented merely by possession of one early machine if a future REIGN quest actually depends on successful recombination capability.

### 4. Replication and reformation

The native book opens replication after the spagyrics/incubation foundation. The later reformation system includes source, target, and result pedestals plus Sulfuric Flux infrastructure.

Supporting apparatus includes:

- Mercury Catalyst, converting Mercury crystal material into Mercury Flux;
- Mercury Capacitor, storing larger quantities of Mercury Flux;
- Caloric Flux Emitter, remotely supplying heat;
- Mercury Flux Emitter, transferring Mercury Flux;
- Sulfuric Flux Emitter, powering the reformation array;
- source/target/result reformation pedestals.

The book places `reformation_array` after the within-type-and-tier conversion concept. This is another distinct capability boundary rather than an interchangeable duplicate of the early Liquefaction milestone.

### 5. Transmutation

The Fermentation Vat is the native apparatus used for conversion between different types of matter. Its book entry follows the `convert_to_other_type` concept, and the dedicated transmutation entry follows the Fermentation Vat.

The exact shaped recipe also depends on earlier Theurgy material, including Alchemical Sulfur, so this stage is mechanically downstream of the basic spagyrics loop.

### 6. Exaltation

The Digestion Vat is the native apparatus used for conversion between tiers of matter. Its book entry follows `convert_to_other_tier` and is later than the transmutation branch in the generated progression topology.

Its recipe requires Sal Ammoniac products and other later materials, making it another meaningful capability stage rather than a cosmetic machine acquisition.

## Signal hierarchy for Quest integration

Theurgy should be integrated using the strongest surviving evidence that actually represents the campaign requirement.

Preferred order:

1. **Native vanilla advancement**, when Theurgy already exposes the exact accomplishment. This is ideal because it is durable and naturally sequence-break safe.
2. **Persistent vanilla craft statistic**, when the intended capability is uniquely established by crafting a specific apparatus and no native advancement exists. OVERLORD QUESTS already provides `questlog:item_craft_stat` for this purpose.
3. **A stronger native persistent state or public API**, if a future Theurgy capability cannot be represented correctly by advancement/craft evidence and the installed version exposes such an authority.
4. **A REIGN narrative fact after native evidence resolves**, only when later story logic needs a stable interpretation of the accomplishment. The fact records REIGN meaning, not duplicate ownership of Theurgy progression.

Do not use as authoritative progression evidence merely because they are convenient:

- opening or reading a Hermetica page;
- transient GUI state;
- current inventory possession when prior possession matters;
- temporary machine contents/progress;
- arbitrary Questlog-only counters duplicating a Theurgy accomplishment;
- commands that grant a synthetic milestone instead of observing native play.

The Modonomicon book entries contain research/view conditions useful for Theurgy's own instructional flow, but page visibility/read state is not automatically equivalent to actual apparatus capability. OVERLORD QUESTS must not confuse knowledge presentation with mechanical accomplishment.

## Questlog compatibility

A future source-authorized definition may directly observe a native advancement, for example:

```json
{
  "type": "questlog:advancement",
  "advancement": "theurgy:has_liquefaction_cauldron"
}
```

For a later apparatus without a native advancement, a campaign definition may use the existing persistent craft-stat objective only when crafting that apparatus is the correct gameplay milestone. The exact item ID and required amount must be verified against the installed artifact at authoring time.

No new generic objective is justified by the current Theurgy audit. Existing advancement and persistent craft-stat surfaces cover the known durable milestone classes.

## Sequence-break behavior

If a native advancement is selected as a campaign milestone, the advancement remains authoritative. A player who legitimately completed it before the REIGN quest activates must receive credit without reacquiring or recrafting the apparatus.

If a craft-stat milestone is selected, the persistent Minecraft craft statistic provides the same retrospective property for actual crafted outputs.

The campaign must not require the player to:

- discard and reacquire an apparatus;
- craft a duplicate solely because the quest activated late;
- clear a native advancement;
- repeat a native process with no gameplay reason;
- maintain a parallel Questlog ownership flag for the same mechanical state.

## Lore and design boundary

The lore authority establishes that the damaged Dark Tower contains purpose-built rooms whose functions may be restored through relevant installed-mod progression. Theurgy's established REIGN domain is matter and occult material transformation.

Those facts make Theurgy a valid candidate for Tower infrastructure integration, but they do not by themselves define:

- the canonical name of a Theurgy-linked Tower facility;
- which physical Tower room hosts it;
- whether any one Theurgy apparatus is the activation milestone for that facility;
- which Theurgy capability stages belong to the central campaign versus optional/Tower development;
- when such milestones appear in the hidden campaign;
- what Gnarl says about them;
- what later quests depend on them.

Those points remain UNKNOWN until resolved by source-authorized campaign construction. OVERLORD QUESTS must not promote technical progression topology into canon merely because it is convenient.

## Current integration conclusions

Exact A4R2 compatibility with upstream advancement and book resources: **VERIFIED**.

Complete native advancement list above: **VERIFIED** from exact 1.30.0 datagen source.

`theurgy:has_liquefaction_cauldron` existence and criterion: **VERIFIED**.

Theurgy's staged gameplay topology across divination, basic spagyrics, incubation, reformation/replication, transmutation, and exaltation: **VERIFIED** from exact source recipes and generated Hermetica structure.

Use of native advancements as retrospective Questlog progression signals: **TECHNICALLY APPROVED**.

Use of persistent craft statistics for apparatus lacking an advancement: **TECHNICALLY AVAILABLE, SELECT ONLY WHEN A CONCRETE CAMPAIGN MILESTONE REQUIRES IT**.

Specific Dark Tower facility assignment: **UNKNOWN**.

Production quest wiring beyond already authorized campaign content: **DEFERRED UNTIL A CONCRETE SOURCE-AUTHORIZED CAMPAIGN ROLE IS SELECTED**.
