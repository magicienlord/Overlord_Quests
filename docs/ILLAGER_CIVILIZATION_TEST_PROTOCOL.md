# OVERLORD REIGN Illager Bastille Runtime Protocol

Status: TECHNICAL RUNTIME VALIDATION PROTOCOL

## Purpose

Validate both production phases of the designated Illager Bastille arc against Take a Pillage 1.0.3 and the approved local-warband architecture:

1. hostile commander defeat through `campaign/civilizations/illagers/break_the_bastille`;
2. the later fearful/cowed audience through `campaign/civilizations/illagers/the_bastille_bows`.

This protocol validates one designated Bastille only. It does not create a universal Illager leader, global Illager disposition, or global AI change.

## Required environment

Use the intended Forge 1.20.1 full modpack instance containing:

```text
takesapillage-1.0.3-1.20.1.jar
```

and the current OVERLORD QUESTS build under test.

The tested player must have an active Questlog manager before any early-kill sequence-break test begins. `questlog:entity_kill_history` cannot reconstruct a kill that predates the loaded definition.

## World setup

1. Identify the single designated Bastille used for the REIGN Illager anchor.
2. Choose exactly one native `takesapillage:legioner` inside that Bastille as the authored local commander.
3. Add only this tag to that Legioner during the hostile opening:

```text
overlord_anchor:illager_bastille_commander
```

4. Do not add `overlord_quest_protected` to the commander while the hostile opening is valid; the commander is intentionally killable.
5. Choose exactly one surviving native `minecraft:pillager` associated with the designated Bastille as the fearful intermediary.
6. Add both tags to that exact intermediary:

```text
overlord_anchor:illager_bastille_intermediary
overlord_quest_protected
```

7. Do not place either authored role tag on unrelated Illagers.
8. Reset these facts/dispositions as required between independent tests:

```text
overlord_reign:reign/initial_foundation_established
overlord_reign:civilizations/illagers/authority_established
overlord_reign:civilizations/illagers/bastille_cowed
```

The technical default disposition is `questlog:unresolved`.

## Test A: unrelated Illagers do not satisfy the opening

With the foundation fact present and the production opener visible:

1. Kill an ordinary Pillager outside the designated Bastille.
2. Kill an untagged Take a Pillage Legioner outside the designated Bastille if available.
3. Kill an untagged Legioner inside the designated Bastille.

Expected:

- none satisfies the marked commander objective;
- no Illager authority fact is written;
- unrelated Illager populations remain native and unchanged.

## Test B: native Bastille advancement remains independent

1. Enter any `takesapillage:bastille` structure and confirm the native Take a Pillage Bastille advancement is granted normally.
2. On a clean quest-state setup, enter an unrelated Bastille while leaving the marked commander alive.

Expected:

- Take a Pillage awards its native advancement normally;
- the REIGN opener does not complete merely because any Bastille was entered;
- the native advancement is not treated as identity proof for the designated Bastille.

## Test C: normal-order commander defeat

With the foundation fact present:

1. Kill the exact marked commander directly as the player.
2. Allow Questlog to process completion.

Expected:

- the tagged kill-history objective completes exactly once;
- `overlord_reign:civilizations/illagers/authority_established` becomes true;
- the opening quest itself writes no disposition;
- unrelated Illagers do not change behavior.

## Test D: pre-activation commander defeat

1. Ensure the foundation fact is absent while the Questlog definition is already loaded.
2. Kill the exact marked commander directly as the player.
3. Save and quit, then reload.
4. Establish the foundation fact.

Expected:

- no replacement commander is required;
- persisted kill history survives reload;
- the opener recognizes the prior marked kill and writes authority exactly once.

## Test E: non-player death does not satisfy player authority

On a clean setup, cause the marked commander to die without player kill attribution.

Expected:

- the historical player-kill objective remains unsatisfied;
- the authority fact remains absent.

## Test F: intermediary is hostile before authority

With the exact protected intermediary present and the authority fact absent:

1. Approach without completing the commander phase.
2. Attempt the Questlog provider gesture.

Expected:

- Questlog does not expose `The Bastille Bows`;
- the intermediary's native target/attack behavior is not suppressed by the political bridge merely because it is tagged;
- protection prevents accidental loss of the future authored provider but does not establish peace.

## Test G: first cowed audience while disposition is unresolved

After the authority fact exists, keep Illager disposition at technical `questlog:unresolved`.

1. Approach the exact protected intermediary.
2. Confirm it cannot target or damage the testing player while the first cowed audience is available.
3. Sneak/main-hand interact and accept `The Bastille Bows`.
4. Turn the provider-native quest in to the same exact intermediary.

Expected:

- restraint applies only to the protected tagged intermediary;
- `overlord_reign:civilizations/illagers/bastille_cowed` becomes true;
- Illager disposition becomes `overlord_reign:neutral`;
- the phase represents fear/restraint, not friendship or global surrender.

## Test H: neutral/subjugated continuation

With the cowed quest completed:

1. Confirm the designated intermediary remains safely interactable under `overlord_reign:neutral`.
2. In a disposable administrative test state, set the designated Illager polity to `overlord_reign:subjugated` and repeat.

Expected:

- the exact protected intermediary remains restrained in both recognized peaceful-access states;
- unrelated Pillagers, Legioners, Skirmishers, Archers, patrols, raids, outposts, mansions and other Bastilles remain native.

## Test I: later explicit non-peaceful disposition restores native hostility

This tests future branch compatibility without requiring a production hostile-outcome quest to exist yet.

1. Keep the historical authority fact present.
2. Administratively set the Illager disposition to any explicit test state other than `questlog:unresolved`, `overlord_reign:neutral`, or `overlord_reign:subjugated`.
3. Approach the exact protected intermediary.

Expected:

- the old monotonic authority fact does not permanently force restraint;
- the bridge no longer suppresses target acquisition or outgoing attacks solely because authority once occurred;
- the explicit later political state is authoritative;
- protection still prevents ordinary accidental damage to the authored quest anchor until world integration deliberately releases/removes that protection.

This test does not establish the test state's resource ID as a production political state. It validates precedence semantics only.

## Test J: persistence and locality

After completing both production phases:

1. Save and quit, then reload.
2. Verify opener completion, cowed-audience completion, authority fact, cowed fact and neutral disposition persist.
3. Visit unrelated Illager content.

Expected:

- no duplicate rewards or repeated transitions occur;
- unrelated Illagers retain native behavior;
- the designated Bastille state does not become a global Illager settlement state.

## Pass condition

The Illager integration is runtime-qualified only when the hostile opening, sequence-break handling, local cowed audience, disposition precedence, persistence and locality tests all pass in the intended full instance. Repository CI remains a narrower engineering boundary.
