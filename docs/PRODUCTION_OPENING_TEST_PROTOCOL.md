# OVERLORD QUESTS Production Opening Runtime Protocol

Status: TARGET-MODPACK RUNTIME ACCEPTANCE PROCEDURE

This protocol validates the first bundled production campaign slice. It is not a design review and should not be used to reveal or rewrite hidden campaign content.

## Test environment

Use the normal OVERLORD REIGN Forge 1.20.1 test instance with:

- Forge 47.4.10;
- the current OVERLORD QUESTS build;
- the target Minions Remastered fork/build that still exposes `minionsremastered:masters_staff`;
- Hot Iron 1.0.4 plus the already-approved Hot Iron recipe repair;
- no external development quest JSON overriding the bundled production definitions.

Back up the world before destructive reset commands.

## A. Fresh-world opening delivery

1. Enter a fresh unpublished local single-player world.
2. Do not run a Questlog progress command before the first normal gameplay tick.
3. Confirm that the initial Gnarl speaker popup appears automatically once.
4. Read/acknowledge it.
5. Confirm that the two approved opening directions become available concurrently rather than serializing one behind the other.
6. Re-enter ordinary gameplay and confirm the initial popup does not repeat without an explicit administrative reset.

Acceptance:

- automatic opening delivery works from bundled production content;
- the popup remains unpublished-local-single-player scoped;
- both opening directions are independently available after acknowledgement;
- no development fixture appears in the production quest list.

## B. Brown bootstrap after quest activation

1. Start from the opening state with the Brown recovery branch available.
2. Craft `minionsremastered:masters_staff` through the normal Minions Remastered recipe.
3. Confirm Minions Remastered performs its own Brown slot `0` bootstrap behavior.
4. Confirm OVERLORD QUESTS recognizes the craft and advances the Brown recovery branch.
5. Confirm the follow-up Gnarl reaction is delivered once.

Acceptance:

- Questlog observes the craft but does not independently grant Brown;
- no physical-hive transport objective appears;
- the recovery branch completes from the real craft event/statistic;
- the follow-up presentation does not duplicate on normal reload.

## C. Brown sequence-break recognition

This verifies `questlog:item_craft_stat` rather than the ordinary event-only crafting objective.

1. In a backed-up test world, arrange for the Master's Staff to be legitimately crafted before the Brown recovery quest becomes active.
2. Then expose/activate the opening campaign state normally.
3. Wait at least one second after the Brown recovery objective becomes active.
4. Confirm it recognizes the already-persisted item-craft statistic without requiring another staff.

Acceptance:

- legitimate prior crafting is recognized;
- no duplicate staff craft is required;
- Minions Remastered remains the capability authority.

## D. Independent direct-action route

1. Reset progress in a backed-up test world if necessary.
2. Acknowledge the opening popup.
3. Complete the direct-action opening objective without completing the Brown branch first.
4. Confirm that the direct-action branch completes independently.
5. Confirm that the Brown recovery branch remains available afterward.

Acceptance:

- neither opening route gates the other;
- ordinary Minecraft action can satisfy the direct route;
- completing one opening direction does not silently close the other.

## E. First Tower infrastructure convergence

1. Complete either accepted opening direction far enough to expose the first Tower infrastructure quest.
2. Progress Hot Iron normally until the installed `hot_iron:local_smithery` advancement is complete.
3. Confirm the Tower infrastructure quest recognizes the native advancement, including when that advancement was completed before the quest became active.
4. Query the persistent narrative state:

```text
/questlog narrative fact get overlord_reign:tower/forge_prepared
```

5. Confirm the fact is present after quest completion.
6. Save, exit, reload, and query it again.

Acceptance:

- native Hot Iron progression remains authoritative;
- the quest does not substitute a parallel fake smithing progression;
- `overlord_reign:tower/forge_prepared` is set automatically and survives reload;
- the fact does not claim that fixed-coordinate workstation placement or later forge upgrades are already complete.

## F. Reload and definition integrity

After completing any subset of the above:

1. Save and exit normally.
2. Reload the same world.
3. Confirm completed quest state remains completed.
4. Confirm active parallel quests remain active with preserved progress.
5. Confirm no already-acknowledged automatic popup repeats unexpectedly.
6. Confirm no definition enters the Broken Quest fallback in the full target modpack.

## Evidence threshold

A successful pass needs only concise confirmation of each section and any relevant error log if a section fails. Screenshots are optional unless the failure is visual or the observed quest state is ambiguous.

This protocol validates implementation behavior. It does not request a new visual-foundation review and does not reopen the accepted presentation baseline.
