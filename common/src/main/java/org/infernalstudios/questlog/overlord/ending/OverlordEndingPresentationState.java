package org.infernalstudios.questlog.overlord.ending;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * World-scoped delivery latch for the OVERLORD REIGN central ending screen.
 *
 * Campaign authorization remains owned by narrative state. This SavedData records
 * only whether the one-time presentation has already been acknowledged so a save,
 * relog, or sequence-break path cannot accidentally replay the ending forever.
 */
public final class OverlordEndingPresentationState extends SavedData {
    private static final String DATA_NAME = "overlord_quests_ending";
    private boolean presented;

    public static OverlordEndingPresentationState get(MinecraftServer server) {
        if (server == null) {
            throw new IllegalArgumentException("server must not be null");
        }
        return server.overworld().getDataStorage().computeIfAbsent(
                OverlordEndingPresentationState::load,
                OverlordEndingPresentationState::new,
                DATA_NAME
        );
    }

    public boolean isPresented() {
        return this.presented;
    }

    public boolean markPresented() {
        if (this.presented) {
            return false;
        }
        this.presented = true;
        this.setDirty();
        return true;
    }

    /** Administrative/test reset used only when the ending-arm fact is absent. */
    public boolean reset() {
        if (!this.presented) {
            return false;
        }
        this.presented = false;
        this.setDirty();
        return true;
    }

    public static OverlordEndingPresentationState load(CompoundTag root) {
        OverlordEndingPresentationState state = new OverlordEndingPresentationState();
        if (root != null) {
            state.presented = root.getBoolean("presented");
        }
        return state;
    }

    @Override
    public CompoundTag save(CompoundTag root) {
        root.putBoolean("presented", this.presented);
        return root;
    }
}
