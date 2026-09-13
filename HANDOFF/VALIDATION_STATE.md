# VALIDATION STATE — preserved source 18dd600

Preserved source SHA: `18dd600ddbde3a12b5776d1c9998c5799c7f138d`

At handoff capture time, GitHub reported 13 workflow runs for this exact head SHA. All 13 were completed successfully; none were failed or still in progress.

| Workflow | Run # | Run ID | Result |
|---|---:|---:|---|
| Core Magic Questline Contract | 14 | 34788700180 | success |
| Tower Restoration Contract | 16 | 34788700157 | success |
| Ending Contract | 50 | 34788700167 | success |
| Optional Objectives Contract | 48 | 34788700154 | success |
| Minion Recovery Contract | 17 | 34788700158 | success |
| Adventure Questline Contract | 13 | 34788700171 | success |
| Civilization Validation Kit | 28 | 34788700165 | success |
| Quest Anchor Protection Smoke | 35 | 34788700159 | success |
| Narrative State Persistence Smoke | 40 | 34788700173 | success |
| Build Forge 1.20.1 | 725 | 34788700194 | success |
| Dedicated Server Smoke | 84 | 34788700207 | success |
| Client Bootstrap Smoke | 45 | 34788700186 | success |
| Illager Campaign Contract | 60 | 34788700170 | success |

## Preserved validation artifacts

### Forge build
- Workflow run ID: `34788700194`
- Artifact ID: `5914204399`
- Artifact name: `Overlord_Quests-Forge-1.20.1`
- Artifact size reported by GitHub: `841777` bytes
- Artifact-service digest: `sha256:8cb6ef0d0681a114aef441a47aa17a15556736737770432185e6f086b3ece177`
- Artifact expiry reported by GitHub: `2026-12-13T23:29:12Z`

### Civilization validation kit
- Workflow run ID: `34788700165`
- Artifact ID: `5914204313`
- Artifact name: `civilization-validation-kit`
- Artifact size reported by GitHub: `15322` bytes
- Artifact-service digest: `sha256:cc3ea27fa435c4e87ed0a27d399332accebbe412418d4ea82135c10b11979f17`
- Artifact expiry reported by GitHub: `2026-12-13T23:29:12Z`

The handoff packaging workflow downloads those artifacts by exact run ID and includes their extracted contents beside an exact `git archive` of the preserved implementation commit. The artifact-service digests above identify GitHub's stored artifacts; they are not incorrectly treated as hashes of individual extracted files.

## Preservation gate

The packaging branch is required to differ from the preserved implementation SHA only through:
- `HANDOFF/*`
- `.github/workflows/conversation-handoff.yml`

The handoff workflow enforces this before producing its downloadable package. No implementation workflow result above was created by the packaging branch; all listed validation belongs to the exact preserved source SHA.
