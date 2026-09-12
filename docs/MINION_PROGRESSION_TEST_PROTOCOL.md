# OVERLORD QUESTS Minion Progression Runtime Test Protocol

Status: DEVELOPMENT VALIDATION

Purpose: validate the Questlog to OVERLORD Minions progression handoff against the stable public progression API without treating development fixtures as campaign canon.

Target runtime:

- Minecraft Java 1.20.1
- Forge 47.4.10
- current OVERLORD QUESTS `gnarl-bootstrap` artifact
- validated OVERLORD Minions Build #118 progression contract, or a newer build that preserves the same public API
- Minions Remastered installed as required by the OVERLORD Minions runtime

The progression owner is `overlord_minions`. Questlog only requests Red, Green, and Blue unlocks. Brown remains owned by the Master's Staff bootstrap.

## Test world boundary

Use a new disposable single-player integration world with commands enabled.

The OVERLORD Minions progression state is intentionally persistent and production-irreversible. There is no progression-reset command in the stable API. A fresh world is therefore the authoritative clean baseline for an ordered progression test.

Do not infer Brown gameplay availability from `/overlord_minions status`. The progression SavedData starts at the Brown slot because Brown is the bootstrap baseline, while actual Brown access remains controlled by Minions Remastered and the Master's Staff path.

## Development quest installation

Place these development definitions in `config/questlog/quests/`:

- `overlord_minion_unlock_red_dev.json`
- `overlord_minion_unlock_green_dev.json`
- `overlord_minion_unlock_blue_dev.json`

They are integration fixtures only. They are not OVERLORD REIGN story quests.

After installing or replacing them, run:

```mcfunction
/questlog reset_all_progress_and_reload
```

## 1. Clean baseline and owner-side sequence rejection

Run:

```mcfunction
/overlord_minions status
```

Expected result: highest unlocked type reports Brown, slot 0.

Then deliberately attempt an invalid skip:

```mcfunction
/overlord_minions unlock green
```

Expected result: the command rejects Green because Red is not yet unlocked.

Re-run:

```mcfunction
/overlord_minions status
```

Expected result: the owner remains at Brown, slot 0.

This proves the owning mod still enforces the fixed progression order independently of Questlog prerequisites.

## 2. Quest-owned Red handoff

Ensure the Red development quest is fresh:

```mcfunction
/questlog progress reset questlog:overlord_minion_unlock_red_dev
/clear @s minecraft:debug_stick
```

Then satisfy its objective:

```mcfunction
/give @s minecraft:debug_stick 1
```

After the quest completes, run:

```mcfunction
/overlord_minions status
```

Expected result: highest unlocked type reports Red, slot 1.

Questlog must not report the reward as successfully applied unless the public progression API returned `UNLOCKED` or `ALREADY_UNLOCKED`.

## 3. Save and reload after Red

Exit to the title screen, reload the same world, then run:

```mcfunction
/overlord_minions status
```

Expected result: Red, slot 1 remains the highest unlocked type.

This verifies owner-side world persistence independently of Questlog's reward flag.

## 4. Quest-owned Green handoff

Ensure the Green development quest is fresh and remove any pre-existing objective item:

```mcfunction
/questlog progress reset questlog:overlord_minion_unlock_green_dev
/clear @s minecraft:blaze_rod
```

The Green fixture is structurally gated behind completion of the Red fixture. Satisfy its objective:

```mcfunction
/give @s minecraft:blaze_rod 1
```

Then run:

```mcfunction
/overlord_minions status
```

Expected result: Green, slot 2.

Exit and reload the world once more. The status must remain Green, slot 2.

## 5. Quest-owned Blue handoff

Ensure the Blue development quest is fresh and remove any pre-existing objective item:

```mcfunction
/questlog progress reset questlog:overlord_minion_unlock_blue_dev
/clear @s minecraft:prismarine_crystals
```

The Blue fixture is structurally gated behind completion of the Green fixture. Satisfy its objective:

```mcfunction
/give @s minecraft:prismarine_crystals 1
```

Then run:

```mcfunction
/overlord_minions status
```

Expected result: Blue, slot 3.

Exit and reload the world. The status must remain Blue, slot 3.

## 6. Idempotent reconciliation

Reset only the Red development quest after the owner-side state has already progressed beyond Red:

```mcfunction
/questlog progress reset questlog:overlord_minion_unlock_red_dev
/clear @s minecraft:debug_stick
/give @s minecraft:debug_stick 1
```

Expected behavior:

- the Red quest may complete again for test purposes;
- the public Minion API returns an already-unlocked success for Red;
- Questlog records the reward as successfully reconciled;
- owner-side progression remains Blue, slot 3;
- no Minion slot regresses or duplicates.

Confirm with:

```mcfunction
/overlord_minions status
```

## 7. Missing-owner reconciliation boundary

This is a separate disposable-world pass because removing an installed mod from an existing world can create unrelated modpack damage.

With the development fixture present but `overlord_minions` intentionally absent, complete a later-tier development quest only in a minimal controlled Questlog test environment. Questlog must leave `questlog:unlock_minion` unapplied because the stable API is unavailable.

Reinstall the compatible `overlord_minions` build and reload the same controlled test world. Player-load reconciliation should retry completed pending external progression rewards. An owner-side `UNLOCKED` or `ALREADY_UNLOCKED` result should then mark the Questlog reward applied.

Do not perform this pass in the normal OVERLORD REIGN world.

## Acceptance criteria

The integration is runtime-validated only when all of the following are directly evidenced:

1. owner-side Green-before-Red rejection works;
2. Red development quest advances owner state to slot 1;
3. Green development quest advances owner state to slot 2 only after Red;
4. Blue development quest advances owner state to slot 3 only after Green;
5. Red, Green, and Blue survive save and reload;
6. repeated already-unlocked handoff is harmless;
7. Questlog never grants Brown through `questlog:unlock_minion`;
8. Questlog does not rewrite the Minion roster, summon counts, or Minions Remastered staff state.

Until those runtime checks are performed, the public API integration is implementation-complete and statically validated, but not yet full-modpack runtime-proven.
