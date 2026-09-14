# OVERLORD QUESTS Central Ending Presentation

Status: PRODUCTION CENTRAL END CAMPAIGN IMPLEMENTED / ENDING TRANSPORT IMPLEMENTED

## Authority

The REIGN runtime and campaign authorities establish these rules:

- the End is the dimensional Wasteland associated with the Great Cataclysm;
- the Ender Dragon defeat remains the central campaign's final mechanical resolution;
- the central campaign is independent from civilization-disposition completion;
- hard sequencing should exist only where real capability, story, world-state, or native mechanics require it;
- legitimate prior Dragon defeat must be recognized rather than undone;
- the same persistent world remains playable afterward;
- branch-specific political and sidequest outcomes are not flattened into a global morality summary.

The production implementation follows those boundaries without inventing an additional terminal boss, civilization checklist, full-Tower checklist, or disposable post-ending world state.

## Production central campaign

The production End route is intentionally narrow and sequence-break safe.

`campaign/end/the_wound_beyond_the_world.json`

- branches from `campaign/expansion/the_reign_takes_shape`;
- observes entry into `minecraft:the_end` through `questlog:visit_dimension_history`;
- treats Minecraft's End as the established dimensional Wasteland;
- sets `overlord_reign:campaign/ending_armed` only after the player has actually reached that dimension;
- does not require any civilization disposition, full Tower Restoration, or completion count.

`campaign/end/break_the_dragon.json`

- follows the dimensional-Wasteland entry;
- observes Minecraft's persistent Dragon-fight state through `questlog:ender_dragon_defeated`;
- records `overlord_reign:campaign/central_campaign_completed` after the Dragon has been defeated.

The history-aware dimension objective and persistent Dragon-state objective preserve legitimate sequence breaking. A player who reaches or defeats the Dragon early is not asked to repeat the event.

YUNG's Better End Island, Better End Cities, The Outer End, Enderman Overhaul, and related End extensions remain absorbed into the End/Wasteland context. Their existence does not create extra mandatory pre-ending checklists.

## Production activation boundary

The ending surface uses the server-authoritative narrative fact:

`overlord_reign:campaign/ending_armed`

Exactly one production quest sets this reserved fact: `campaign/end/the_wound_beyond_the_world.json`.

The fact is armed before the expected Dragon resolution, allowing the normal vanilla End-poem transition to be replaced. Dragon defeat by itself does not fabricate campaign readiness if the player has never reached the authored central route.

The client receives only a derived boolean projection of server state. Client configuration cannot arm the production ending.

## One-time presentation persistence

`OverlordEndingPresentationState` stores a separate world-scoped `presented` latch in `overlord_quests_ending` SavedData.

This latch is presentation state, not narrative canon. It prevents the ending screen from replaying on every login or later credits transition after the player has completed it once.

The client acknowledges completion through a dedicated server-bound packet. The server accepts that acknowledgement only when the ending-arm fact is present and Minecraft's End fight reports that the Dragon has previously been defeated.

## Normal Dragon-victory path

If the ending is armed before the final Dragon victory, `OverlordEndingScreens` replaces the vanilla End-poem `WinScreen` only when the feature is enabled, the server projects the ending as armed and not presented, the session is an unpublished local single-player integrated server, and the screen is the End-poem form rather than manually opened credits.

`WinScreenAccessor` preserves the original vanilla completion callback. The replacement invokes it at most once after acknowledging presentation, preserving Minecraft's normal post-End transition.

## Prior-Dragon sequence-break path

If the Dragon was defeated before the central campaign becomes ending-ready, the server checks `EndDragonFight.hasPreviouslyKilledDragon()` when the arm fact becomes true or the player logs in.

If the ending is armed and has not yet been presented, the server requests direct presentation. The client waits until screenless gameplay in an unpublished local single-player session before showing the same ending screen.

That path does not summon or respawn the Dragon, alter the End fight, replay vanilla credits, teleport the player, or rewrite unrelated quest and world state.

## Production presentation

The former development-scaffold copy has been replaced by a minimal production conclusion. It states only established results:

- the Ender Dragon is dead;
- the dimensional Wasteland remains accessible;
- the central campaign is complete;
- the persistent world and the Overlord's reign continue.

The continue control is labeled `Continue Your Reign` and preserves the vanilla continuation callback.

This presentation deliberately does not invent a global morality score, force civilization summaries, or close unresolved parallel content. Future artwork, audio, or branch-specific epilogues may enrich the presentation without changing the completed transport contract.

## Development preview

`ending_screen.developmentPreview` remains available and defaults to `false`.

Preview mode bypasses server arm state only for presentation testing. It does not write the arm fact or become campaign authority.

## Configuration

The client `ending_screen` configuration retains:

- `enabled`, default `true`;
- `developmentPreview`, default `false`;
- `minimumDisplayTicks`, default `80`;
- `allowSkip`, default `true`.

Disabling the screen does not mutate server campaign state.
