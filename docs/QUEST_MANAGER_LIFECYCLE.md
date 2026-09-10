# Quest Manager Lifecycle Hardening

Status: TECHNICAL / PREPARATORY - NOT STORY CANON

Quest objectives register callbacks through two different mechanisms: the shared Triggers 1.0.1 event library and Questlog's own private event bus. They have different cleanup capabilities and must not be treated as the same lifecycle.

## Active manager generations

OVERLORD QUESTS treats manager lifetime as an explicit validity boundary.

A `QuestManager` starts active. It is deactivated when the local client manager is destroyed, when a server manager generation is shut down, or if an unexpected server-start path replaces an existing manager container. Objective mutation accepts events only when both conditions remain true:

1. the owning manager is active;
2. the objective's parent quest is still the exact Quest instance installed under that quest ID in the manager.

The second condition protects ordinary definition reloads, which recreate Quest objects inside the same manager. The first condition protects callbacks retained across integrated-server shutdown or whole manager-generation replacement.

A manager also rejects add/reload/sync operations after deactivation. Deactivation disposes its current quests before clearing them.

## Questlog private event bus

Questlog owns its `QuestlogEventBus`, so individual listeners can be managed without affecting unrelated mods.

The bus now supports removal of one exact listener. Event delivery iterates a snapshot so a callback may cause quest disposal/reload without invalidating the active delivery loop.

`ReadObjective` and `QuestCompleteObjective` retain their exact listener instances. A Quest disposes its complete objective tree when it is removed, replaced, reloaded, or its manager is deactivated. These internal listeners are therefore actually removed rather than merely made inert.

## Shared Triggers event bus

Triggers 1.0.1 exposes listener registration and a global `removeAllListeners`, but no supported individual-listener removal API.

OVERLORD QUESTS must not call that global clear during a quest reload. Triggers is an embedded library that may have other consumers, and clearing the shared bus from Questlog could silently disable callbacks owned by another system.

For Triggers-backed objectives, old callbacks can therefore remain reachable after a hot reload. The active-manager and exact-quest-instance checks make those callbacks inert before they can mutate objective state or synchronize stale quest data.

This leaves a bounded correctness distinction:

- stale Triggers callbacks cannot change current quest state;
- repeated hot reloads can still accumulate retained callback objects and dispatch overhead until the Triggers bus itself is cleared by its lifecycle.

That retention is a technical watch item. It is not justification for clearing a shared event bus unsafely.

## Persistence interaction

Quest replacement serializes compatible progress first, disposes the old Quest listener tree, creates the current definition, and then restores progress by quest ID. Listener lifetime and persisted progress are therefore separate concerns.

This hardening changes lifecycle safety only. It does not define OVERLORD REIGN quest content, progression, chronology, locations, rewards, or canon.
