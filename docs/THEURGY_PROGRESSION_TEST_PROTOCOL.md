# OVERLORD QUESTS Theurgy Progression Runtime Test Protocol

Status: TECHNICAL VALIDATION / DEVELOPMENT FIXTURE / NOT STORY CANON

## Purpose

This protocol validates the integration surfaces selected by `THEURGY_NATIVE_PROGRESSION_AUDIT.md` against the actual OVERLORD REIGN client instance.

It does not validate the balance or intended play sequence of Theurgy itself, and it does not assign a Dark Tower room or campaign role to Theurgy.

The development fixture is:

```text
examples/questlog/quests/overlord_theurgy_progression_dev.json
```

It checks three kinds of evidence behind one delayed activation gate:

```text
theurgy:has_basic_rod
theurgy:has_liquefaction_cauldron
minecraft crafted statistic for theurgy:fermentation_vat
```

The debug-stick prerequisite exists only so all three native accomplishments can be performed before the Questlog fixture activates.

## Environment

Use a disposable unpublished local single-player world with commands enabled and the normal OVERLORD REIGN 1.20.1 modpack installed.

Required installed artifacts include:

- the OVERLORD QUESTS build under test;
- `theurgy-1.20.1-1.30.0-OverlordReign-A4R2.jar` or a later explicitly validated replacement;
- Theurgy's normal required libraries.

Copy the development fixture to:

```text
config/questlog/quests/overlord_theurgy_progression_dev.json
```

Do not place the fixture into a production world.

## Clean start

Run:

```mcfunction
/clear @s minecraft:debug_stick
/questlog reset_all_progress_and_reload
```

Expected state:

- `[DEV] Theurgy Native Progression` is not active because its debug-stick prerequisite is unsatisfied;
- no Theurgy accomplishment performed in the following steps should require Questlog to be active at the time it occurs.

For the cleanest craft-stat result, use a fresh disposable player/world that has never crafted a Fermentation Vat.

## Pass A: native advancement evidence before quest activation

The purpose of this pass is signal validation, not a full Theurgy progression playthrough. Commands may place the exact native items into the inventory so Theurgy's own vanilla inventory-change criteria fire.

Run:

```mcfunction
/give @s theurgy:divination_rod_t1 1
/give @s theurgy:liquefaction_cauldron 1
```

Wait several seconds.

Expected behavior:

- Theurgy records `theurgy:has_basic_rod` from the tier-1 Divination Rod;
- Theurgy records `theurgy:has_liquefaction_cauldron` from the Liquefaction Cauldron;
- the Questlog fixture remains locked because the debug stick is still absent.

The commands deliberately exercise Theurgy's own advancement criteria. Questlog must not grant or fabricate those advancements.

## Pass B: historical crafted-apparatus evidence

Craft one `theurgy:fermentation_vat` using the normal crafting table recipe before activating the Questlog fixture.

The exact upstream 1.30.0 recipe, preserved by A4R2, is:

```text
c s c
c b c
c C c

c = copper ingot
s = any item in #theurgy:alchemical_sulfurs
b = wooden barrel
C = copper storage block
```

For a command-assisted integration test, the required ingredients can be supplied without bypassing the actual craft:

```mcfunction
/give @s minecraft:copper_ingot 6
/give @s minecraft:barrel 1
/give @s minecraft:copper_block 1
/give @s theurgy:alchemical_sulfur_iron 1
```

Use a crafting table to craft the Fermentation Vat normally. Do not use `/give` for the vat in this pass, because `questlog:item_craft_stat` intentionally reads Minecraft's persistent crafted-item statistic.

Expected behavior:

- the vanilla `ITEM_CRAFTED` statistic for `theurgy:fermentation_vat` increases;
- the Questlog fixture is still locked.

## Pass C: retrospective Questlog activation

Only after Passes A and B are complete, run:

```mcfunction
/give @s minecraft:debug_stick 1
```

Wait at least two seconds, then open the Questlog if necessary.

Expected behavior:

- `[DEV] Theurgy Native Progression` activates;
- both advancement objectives resolve from Theurgy's already-completed native advancements;
- the Fermentation Vat objective resolves from the already-recorded vanilla crafted-item statistic;
- the quest completes without reacquiring the rod or cauldron and without crafting a second vat.

Failure of any objective to recognize its pre-activation state is a sequence-break compatibility defect.

## Pass D: save/reload persistence

After Pass C completes:

1. save and quit to the title screen;
2. reopen the same disposable world;
3. confirm the fixture remains complete;
4. run `/questlog reset_all_progress_and_reload` while leaving native Theurgy advancements and vanilla statistics untouched;
5. satisfy the debug-stick prerequisite again if necessary.

Expected behavior after the Questlog-only reset:

- the native advancements are still owned by Theurgy/Minecraft;
- the Fermentation Vat craft statistic is still present;
- once the fixture becomes active again, all three objectives can reconstruct completion from native/persistent evidence.

This proves Questlog is observing owner-side history rather than being the sole keeper of the accomplishments.

## Pass E: fail-closed check without Theurgy

This pass is optional and must use a separate disposable test instance.

Remove Theurgy and its fixture together for ordinary gameplay. OVERLORD QUESTS must continue loading normally because Theurgy is not a hard dependency of the quest engine.

Do not deliberately load `overlord_theurgy_progression_dev.json` without Theurgy and treat failure to resolve its custom item IDs as a bug. The fixture is explicitly an integration test definition whose environment requires Theurgy.

## Evidence to retain

Retain:

- `latest.log` covering activation and completion;
- a screenshot of the completed development fixture;
- the exact OVERLORD QUESTS artifact SHA/commit;
- confirmation of the exact Theurgy JAR in the tested instance;
- any exception mentioning an unknown Theurgy item, missing advancement, or objective construction failure.

## Acceptance criteria

The integration passes when the exact installed Theurgy build and OVERLORD QUESTS together demonstrate all of the following:

- native rod advancement is recognized after late Questlog activation;
- native Liquefaction Cauldron advancement is recognized after late Questlog activation;
- historical Fermentation Vat crafting is recognized after late Questlog activation;
- save/reload preserves the underlying evidence;
- a Questlog-only progress reset can reconstruct the three accomplishments from native/persistent state;
- Questlog does not grant, clear, or duplicate Theurgy's progression state.

Passing this protocol approves these technical observation surfaces only. It does not authorize a production Theurgy quest, Tower assignment, or narrative interpretation.
