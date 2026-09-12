package org.infernalstudios.questlog.overlord.minions;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.util.JsonUtils;

/**
 * Server-authoritative prerequisite/objective backed directly by the owning
 * OVERLORD Minions progression state.
 *
 * Questlog never persists a duplicate Minion ownership flag. Client snapshots
 * receive only the derived one-unit completion state needed for presentation.
 */
public final class MinionUnlockedObjective extends Objective {
    private final OverlordMinionProgressionBridge.Slot slot;

    public MinionUnlockedObjective(JsonObject definition) {
        super(definition);
        this.slot = OverlordMinionProgressionBridge.Slot.parse(JsonUtils.getString(definition, "slot"));
        if (this.getRequiredAmount() != 1) {
            throw new IllegalArgumentException("minion_unlocked objective required_amount must be 1");
        }
    }

    private boolean serverSatisfied() {
        Quest parent = this.getParent();
        if (parent == null || parent.manager.isClient() || !(parent.manager.player instanceof ServerPlayer player)) {
            return super.isCompleted();
        }
        return OverlordMinionProgressionBridge.isUnlocked(player.server, this.slot);
    }

    @Override
    public boolean isCompleted() {
        return this.serverSatisfied();
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = super.serialize();
        if (this.getParent() != null && !this.getParent().manager.isClient()) {
            tag.putInt("units", this.serverSatisfied() ? 1 : 0);
        }
        return tag;
    }

    @Override
    public void writeInitialData(CompoundTag data) {
        super.writeInitialData(data);
        if (this.getParent() != null && !this.getParent().manager.isClient()) {
            data.putInt("units", this.serverSatisfied() ? 1 : 0);
        }
    }
}
