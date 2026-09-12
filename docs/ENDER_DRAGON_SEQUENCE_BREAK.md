# OVERLORD QUESTS Ender Dragon Sequence-Break Signal

Status: TECHNICAL IMPLEMENTATION CAPABILITY

## Authority

The current OVERLORD REIGN campaign authority establishes the Ender Dragon's defeat as the central campaign's mechanical climax and requires legitimate sequence breaking to be recognized if the Dragon has already been defeated before the final campaign stage becomes active.

This document implements only the technical observation primitive required by that rule. It does not define the final quest, its prerequisite chain, final dialogue, presentation, rewards, credits, or post-ending reactions.

## Why the vanilla advancement is not sufficient

`minecraft:end/kill_dragon` is persistent and can be observed retrospectively, but it belongs to the player credited by Minecraft with the advancement. That makes it an imperfect authority for the world-level question OVERLORD REIGN actually needs to answer: has this world's Ender Dragon already been defeated?

A multiplayer kill, unusual damage ownership, or another valid kill path must not require the campaign to recreate the Dragon merely because the current player lacks the advancement.

## Objective

OVERLORD QUESTS therefore exposes:

```json
{
  "type": "questlog:ender_dragon_defeated",
  "required_amount": 1
}
```

The objective polls once per second while active. It reads the server's End level, obtains Minecraft's `EndDragonFight`, and completes when `EndDragonFight.hasPreviouslyKilledDragon()` is true.

`required_amount` is a boolean contract and may be omitted or set to exactly `1`. Other values are rejected by repository validation.

## State ownership

Minecraft owns the authoritative state.

Questlog does not duplicate a Dragon-killed fact into its own narrative state merely to make the objective work. It reads the persistent End fight state when the objective is active. Because Minecraft preserves `previouslyKilled`, a Dragon defeated before quest activation is recognized after activation without replaying the fight.

The objective is read-only. It does not:

- summon or respawn the Ender Dragon;
- kill or damage the Dragon;
- modify End crystals;
- change exit portals or gateways;
- award or revoke vanilla advancements;
- infer who killed the Dragon;
- select an OVERLORD REIGN ending branch;
- authorize final-campaign presentation.

## Sequence-breaking semantics

This signal answers one narrow historical question: the End Dragon fight reports that the Dragon has been defeated at least once.

If the Dragon is later respawned, the historical first-defeat state remains true. That is intentional. The central campaign rule concerns recognition of a legitimate prior defeat, not whether a respawned Dragon is currently alive.

If later campaign design requires the Dragon to be alive at a particular stage, that is a separate condition and must not be inferred from this objective.

## Development fixture

`examples/questlog/quests/overlord_ender_dragon_defeated_dev.json` is a non-canon development fixture.

Expected behavior:

1. In a world whose Ender Dragon has never been defeated, the objective remains incomplete.
2. Defeat the Dragon normally. Within approximately one second of the authoritative End fight state changing, the active fixture completes.
3. In a copy of a world where the Dragon was defeated before the fixture became active, activating the fixture completes it without respawning or re-killing the Dragon.
4. Save and reload after a prior Dragon defeat. The objective must still complete from Minecraft's persistent End fight state.
5. The test does not require the testing player to possess `minecraft:end/kill_dragon`; the world state is authoritative.

This fixture establishes no story canon and must not be bundled into the runtime campaign definitions.

## Production boundary

The capability is ready for production use only as a technical signal. A production final-campaign quest must wait until its remaining authored prerequisites and presentation are established by the campaign authority. This implementation deliberately does not guess those unresolved elements.
