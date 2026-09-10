package org.infernalstudios.questlog.core;

/**
 * Small lifecycle token shared by QuestManager-owned objectives.
 *
 * Triggers 1.0.1 does not expose per-listener removal, so callbacks from old
 * quest instances can remain reachable after reload/logout. The token gives
 * those callbacks an explicit manager-generation boundary without changing
 * Questlog's event API.
 */
final class QuestManagerLifecycle {
    private boolean active = true;

    boolean isActive() {
        return this.active;
    }

    void deactivate() {
        this.active = false;
    }
}
