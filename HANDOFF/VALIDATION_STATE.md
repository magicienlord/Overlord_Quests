# Validation state

Implementation checkpoint: `423ab3290b743633be22580e01629af1dd3c74e1`
Workflow: `Build Forge 1.20.1`
Run: #565
Run ID: `34687293557`
Successful attempt: 2
Job ID: `103538074352`
Result: SUCCESS

## Attempt 1

The first attempt failed during `:common:createMinecraftArtifacts` because NeoForged Maven returned HTTP 502 for:

`https://maven.neoforged.net/releases/org/jetbrains/annotations/maven-metadata.xml`

The failing dependency resolution was `org.jetbrains:annotations:20.1.+`, required through ForgeFlower. This was external repository failure evidence, not a source assertion or compile failure.

The failed jobs were rerun unchanged. No source patch was made to obtain the green result.

## Attempt 2 evidence

All static validators passed, including provider runtime contracts, quest-anchor protection, failure consequences, targeted entity death tracking, sequence-break tracking, Minion progression, quest definitions, death and ending presentation boundaries, runtime objective fields, quest dependency cycles, definition-cache authority, event-bus lifecycle, definition wire size, Gnarl popup asset validation, and static speaker layout.

Forge build result:

`BUILD SUCCESSFUL in 50s`

`18 actionable tasks: 18 executed`

Assembled runtime artifact inspected successfully:

`forge/build/libs/overlord-quests-forge-1.20.1-0.1.0-alpha.1.jar`

Repository-controlled definition evidence from the run:

- validator self-tests: 17 PASS cases
- narrative/provider self-tests: 44 PASS cases
- development quest definitions: 18
- bundled definitions: 8
- optional-objective contract checked across 25 repository-controlled quest definitions
- authored optional objectives: 1
- runtime quest contracts valid across 25 quest definitions
- dependency graph: 25 quests, 9 completion-gating edges, no cycles
- wire-size validation: 26 repository-controlled definitions

Presentation validation:

- reaction states: `approving`, `directive`, `mocking`, `neutral`, `severe`
- shared parchment presentation contract: PASS
- in-world provider portrait-roster separation: PASS
- death-screen boundary: PASS, restrained frame and vanilla death copy, no VHS or cassette treatment
- ending-screen boundary: PASS, development preview only, disabled by default, End poem only, vanilla completion callback preserved

Opening campaign validation:

- concurrent opening preserved
- Gnarl lifecycle reactions preserved for both opening directions and first Tower restoration
- Brown bootstrap remains owned by Minions Remastered
- Brown craft observation uses retrospective exact-item statistics
- first Tower convergence uses native Hot Iron progression with persistent restoration fact

Minion progression validation:

- Brown = staff bootstrap
- Red = slot 1
- Green = slot 2
- Blue = slot 3
- Quest-side persistence delegated to public `OverlordMinionProgression` API
- later tiers require campaign milestone plus authoritative previous-slot owner state
- successful handoff immediately refreshes active quest graph
- completed pending milestones reconcile idempotently on login

Gnarl popup asset evidence:

- path: `common/src/main/resources/assets/questlog/textures/gui/overlord/gnarl_popup.png`
- bytes: 1,154,559
- dimensions: 1254 x 1254 RGBA
- SHA-256: `699140666288f84fea0e916c0f77ad719e25acdec60f65d3f8838a0e616444ed`

## Uploaded artifacts from run #565 attempt 2

| Artifact | ID | ZIP SHA-256 |
| --- | ---: | --- |
| `overlord-quests-forge-1.20.1` | 10295619605 | `c4f9c93f7bbc9928101d5f3ece8dfb23a5b1d056fc7f03b938fbd6de0615db05` |
| `overlord-quests-gnarl-popup-test-kit` | 10295959081 | `5dc94df70ba7a2c22545b712bc38447b687d8d0bb76c8e2dff4bb754a85fd235` |
| `overlord-quests-provider-test-kit` | 10295724404 | `967b058c0d2676012665e068f3f959c8195480762fdccea6e0dd3f0cb91da10c` |
| `overlord-quests-consequence-test-kit` | 10295874134 | `e72d664ed65ae45e3a01b6f8a799d7f312beadce213b3375618a675c85da7ec0` |
| `overlord-quests-minion-progression-test-kit` | 10295764297 | `f6ab7cfc71b6fe6fa45540492b703ceab7cc2161c5665403380618990777330c` |
| `overlord-quests-death-screen-test-kit` | 10295794320 | `54115b14c81b9fcdc1874fa71131690c742c5eaa56551c1cc0dbbba2c37f40cc` |

These artifact digests are the GitHub upload ZIP digests recorded by the successful run.