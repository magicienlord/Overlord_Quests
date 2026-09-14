# OVERLORD QUESTS Conversation Handoff

This package preserves the live repository state observed after THE OVERLORD warned that the conversation had been rolled back and repository files could be newer than prior context.

## Authoritative restart point

Repository: `magicienlord/Overlord_Quests`

Implementation branch: `gnarl-bootstrap`

Exact implementation commit: `526aeef195dbb7530ae1c0302a68c8ce42a459da`

Exact implementation tree: `ddab2e8f85aae81cb7cf5052f507fddedcf7d082`

Commit message: `Validate full Illager Bastille arc`

The live repository was re-read before packaging. This checkpoint supersedes stale conversation assumptions from before the rollback.

## Validation at packaging time

The exact implementation SHA had 16 observed GitHub Actions workflow runs and all 16 succeeded. There were zero failed exact-head runs and no observed queued/in-progress runs when the package was prepared.

Exact Forge artifact source:
- workflow run `34890868260`
- artifact `overlord-quests-forge-1.20.1`
- artifact ID `10366262611`
- artifact digest `sha256:cff98f3e5b96a5ef8c982d31b77edb15d272b0ff525697cae2ca0e93653e320e`

Exact civilization-validation source:
- workflow run `34890868284`
- artifact `overlord-quests-civilization-validation-kit`
- artifact ID `10366249461`
- artifact digest `sha256:06ef447c83f7a76b612a136020d980b9af49a03020399b4aef5f4232a816c6d5`

## Lore authority

Read-only repository: `magicienlord/Overlord_Lore_and_Canon`

Observed `main` commit during packaging: `235845c4985b61cba9c106b2f4c4af8894bb98ae`

## Preservation rule

This preservation branch must differ from the exact implementation checkpoint only through files under `HANDOFF/` and the dedicated `.github/workflows/conversation-handoff.yml` packaging workflow. No gameplay, Java, resources, quest definitions, configs, validators, lore, or other implementation material belongs in the handoff delta.

Start the next conversation with `NEXT_CONVERSATION_HANDOFF.md`, then re-fetch the live `gnarl-bootstrap` branch before doing implementation work. Do not assume this preservation branch is the active development branch.