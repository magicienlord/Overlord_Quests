package org.infernalstudios.questlog.client.death;

/**
 * Bounded client-side timing state for the custom death flow.
 *
 * Adapted from the MIT-licensed Epic Death Screen scene clock, but deliberately
 * contains no VHS, grain, cassette, soundscape, or other presentation logic.
 */
public final class OverlordDeathSceneClock {
    public static final int MIN_DURATION_TICKS = 40;
    public static final int MAX_DURATION_TICKS = 400;

    private final int duration;
    private int ticks;
    private boolean requestedRespawn;

    public OverlordDeathSceneClock(int durationTicks) {
        this.duration = Math.max(MIN_DURATION_TICKS, Math.min(MAX_DURATION_TICKS, durationTicks));
    }

    public void tick() {
        if (this.ticks < Integer.MAX_VALUE) {
            this.ticks++;
        }
    }

    public void skip() {
        this.ticks = Math.max(this.ticks, this.duration);
    }

    public int ticks() {
        return this.ticks;
    }

    public int duration() {
        return this.duration;
    }

    public boolean ready() {
        return this.ticks >= this.duration;
    }

    public float progress() {
        return Math.min(1.0F, (float) this.ticks / (float) this.duration);
    }

    public boolean requestAutoRespawn(boolean enabled, boolean hardcore, boolean dead) {
        if (!enabled || hardcore || !dead || !this.ready() || this.requestedRespawn) {
            return false;
        }
        this.requestedRespawn = true;
        return true;
    }
}
