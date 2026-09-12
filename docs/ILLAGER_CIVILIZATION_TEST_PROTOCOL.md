# OVERLORD REIGN Illager Bastille Runtime Protocol

Status: TECHNICAL RUNTIME VALIDATION PROTOCOL

## Purpose

Validate the production Illager opening against the exact installed Take a Pillage 1.0.3 behavior and the approved local-Bastille quest architecture.

This protocol validates one designated Bastille only. It does not establish a global Illager disposition, universal leader, or global AI change.

## Required environment

Use the intended Forge 1.20.1 full modpack instance containing:

```text
takesapillage-1.0.3-1.20.1.jar
```

and the current OVERLORD QUESTS build under test.

The tested player must have an active Questlog manager from before the early-kill sequence-break test begins. `questlog:entity_kill_history` cannot reconstruct a kill that predates the loaded definition.

## World setup

1. Identify the single designated Bastille used for the REIGN Illager anchor.
2. Choose exactly one native `takesapillage:legioner` inside that Bastille as the authored local commander.
3. Add this scoreboard tag to that Legioner:

```text
overlord_anchor:illager_bastille_commander
```

4. Do not add that tag to any other Legioner or Illager.
5. Do not add `overlord_quest_protected` to this commander during the hostile opening test. Killing the commander before formal quest activation is a legitimate sequence break.
6. Clear the production authority fact before each independent test run:

```text
overlord_reign:civilizations/illagers/authority_established
```

7. For tests that require the campaign gate to remain closed, clear or withhold:

```text
overlord_reign:reign/initial_foundation_established
```

## Test A: unrelated Illagers do not satisfy the opening

With the foundation fact present and the production quest visible:

1. Kill an ordinary Pillager outside the designated Bastille.
2. Kill an untagged Take a Pillage Legioner outside the designated Bastille if available.
3. Kill an untagged Legioner inside the designated Bastille.

Expected:

- none of these kills satisfies the marked commander objective;
- no Illager authority fact is written;
- unrelated Illager populations remain native and unchanged.

## Test B: Bastille discovery is source-native

With the production quest available:

1. Enter the designated `takesapillage:bastille` structure.
2. Confirm the native Take a Pillage Bastille advancement is granted normally.
3. Leave the Bastille and save/reload.

Expected:

- the Bastille objective remains satisfied through the native advancement;
- OVERLORD QUESTS does not replace or duplicate Take a Pillage structure discovery.

## Test C: normal-order commander defeat

With the foundation fact present:

1. Enter the designated Bastille.
2. Kill the marked `takesapillage:legioner` directly as the player.
3. Allow Questlog to process the completion.

Expected:

- the tagged kill-history objective completes exactly once;
- `overlord_reign:civilizations/illagers/authority_established` becomes true;
- no Illager disposition is written by this quest;
- no unrelated Illager changes behavior because of this fact alone.

## Test D: pre-activation commander defeat

This is the critical sequence-break test.

1. Ensure the foundation fact is absent, so `Break the Bastille` remains locked.
2. Ensure the Questlog definition is already loaded for the player.
3. Enter the designated Bastille so the native Bastille advancement is obtained.
4. Kill the marked Legioner directly as the player while the quest is still locked.
5. Save and quit.
6. Reload the same world.
7. Establish or administratively set the initial foundation fact.

Expected:

- Questlog does not require a replacement commander;
- the persisted `entity_kill_history` observation survives reload;
- once the foundation prerequisite becomes true, the Illager opener recognizes both prior Bastille entry and the prior marked commander kill;
- the quest can complete without repeating either event;
- the authority fact is written exactly once.

## Test E: non-player death does not satisfy player authority

On a clean copy or reset setup with a live marked commander:

1. Cause the marked Legioner to die without the player being credited as the damage source, for example through an environmental cause.

Expected:

- `questlog:entity_kill_history` does not record the kill;
- the authority fact remains absent;
- this behavior distinguishes the authored hostile assertion of authority from an incidental death.

## Test F: persistence and idempotency

After completing the production quest:

1. save and quit;
2. reload;
3. inspect the quest and narrative fact;
4. kill unrelated Legioners afterward.

Expected:

- the quest remains completed;
- the authority fact remains present;
- unrelated later kills do not create new campaign effects;
- no duplicate reward or repeated transition occurs.

## Test G: locality

After completion, visit unrelated Illager content including another Bastille, an outpost, patrol, raid, or mansion where available.

Expected:

- those populations remain native Illagers;
- the completed local Bastille opener does not imply their surrender, neutrality, or subjugation;
- any later political effect remains confined to separately authored content.

## Pass condition

The Illager opening is runtime-qualified only when all tests above pass in the intended full instance. Static CI and the standalone development server smoke are necessary but are not substitutes for this full-modpack validation.
