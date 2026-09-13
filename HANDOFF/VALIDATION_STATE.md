# OVERLORD QUESTS Handoff Validation State

Preserved implementation checkpoint: `c0faf694b969e94721fc56edc605496221f456b9`
Implementation branch at packaging: `gnarl-bootstrap`
Checkpoint message: `Make civilization validation packaging branch-local`

## Exact-head GitHub Actions state

All nine workflow runs currently associated with the preserved checkpoint are completed successfully.

| Workflow | Run number | Run ID | Successful attempt | Result |
| --- | ---: | ---: | ---: | --- |
| Build Forge 1.20.1 | 698 | 34768622343 | 2 | success |
| Dedicated Server Smoke | 57 | 34768622452 | 2 | success |
| Client Bootstrap Smoke | 18 | 34768622344 | 2 | success |
| Narrative State Persistence Smoke | 13 | 34768622393 | 2 | success |
| Quest Anchor Protection Smoke | 8 | 34768622351 | 2 | success |
| Illager Campaign Contract | 33 | 34768622407 | 3 | success |
| Ending Contract | 23 | 34768622360 | 2 | success |
| Optional Objectives Contract | 21 | 34768622453 | 2 | success |
| Civilization Validation Kit | 1 | 34768622380 | 2 | success |

The failed earlier attempts on this same checkpoint were runner-allocation/startup failures with `runner_id: 0` and no executed steps. They are not evidence of repository assertion or runtime failures. Later attempts on the unchanged source checkpoint succeeded.

## Build Forge #698 artifacts

All artifacts below were produced by run `34768622343` from exact source head `c0faf694b969e94721fc56edc605496221f456b9`.

| Artifact | Artifact ID | GitHub artifact SHA-256 |
| --- | ---: | --- |
| `overlord-quests-forge-1.20.1` | 10322365804 | `54f41541499a14e05194d615016c5d0f05aeff390fabad060360f2fbe915a20a` |
| `overlord-quests-provider-test-kit` | 10322261166 | `fe78a16aecb0f4ba66cda5bba2d597c899616c37c919f1ebe7928c4153f5ea81` |
| `overlord-quests-death-screen-test-kit` | 10322236203 | `00c2bfd9c84f4b5337c112936817841c8cae740598436cd84fba875b29c5f62a` |
| `overlord-quests-gnarl-popup-test-kit` | 10321966802 | `674278f73e27308d66db2bc71c6227788f8c56844c6c62f1e63ce91ba79d62eb` |
| `overlord-quests-minion-progression-test-kit` | 10321448003 | `b066f2a89995c4751d17aff4749d136b45b06a2036d6165266da04a9feff1234` |
| `overlord-quests-consequence-test-kit` | 10321422972 | `8fdceba92a18d08f1b56b4254338b2d74b82f052a9b171201f65ab89758e8525` |

## Civilization Validation Kit #1

Run `34768622380` succeeded on the same source head.

- Artifact: `overlord-quests-civilization-validation-kit`
- Artifact ID: `10321428155`
- GitHub artifact SHA-256: `b8c1b2886457291661a27031a4cdc5712d1eb301170caec02bb9611fedd58a83`

The kit consolidates eight production civilization-entry protocols and six production sidequest protocols plus shared provider, protection and state references. Villager and Piglin main-entry protocols are intentionally absent because their production entry mechanics remain unresolved.

## What repository-green means

The preserved checkpoint has passed its stated repository validation boundaries, including Forge build, packaged JAR checks, standalone server/client bootstrap, two-boot narrative-state persistence, anchor-protection runtime smoke and focused static campaign contracts.

It is not equivalent to a complete OVERLORD REIGN full-instance playthrough. Manual full-instance checks remain required where documented by `docs/CURRENT_IMPLEMENTATION_STATUS.md` and the focused test protocols.
