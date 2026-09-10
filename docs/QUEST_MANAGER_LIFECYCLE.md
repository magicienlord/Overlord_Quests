# Quest Manager Lifecycle Hardening

Status: TECHNICAL / PREPARATORY - NOT STORY CANON

Quest objectives register callbacks with the Triggers event library. Triggers 1.0.1 does not expose removal of a single listener, so callbacks owned by superseded quest instances can remain reachable after a quest reload or manager replacement.

OVERLORD QUESTS therefore treats manager lifetime as an explicit validity boundary.

A `QuestManager` starts active. It is deactivated when the local client manager is destroyed, when a server-side manager is replaced for the same player UUID, or when the server quest-manager container shuts down. Objective mutation accepts events only when both conditions remain true:

1. the owning manager is active;
2. the objective's parent quest is still the exact quest instance installed under that quest ID in the manager.

The second condition protects ordinary definition reloads, which recreate Quest objects inside the same manager. The first condition protects callbacks retained across logout, integrated-server shutdown, or manager replacement.

This hardening changes lifecycle safety only. It does not define OVERLORD REIGN quest content, progression, chronology, locations, rewards, or canon.
