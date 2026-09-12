package org.infernalstudios.questlog.overlord.narrative;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.util.JsonUtils;

/** Objective satisfied while one explicit authored world narrative fact is present. */
public final class FactObjective extends Objective {
    private final ResourceLocation fact;

    public FactObjective(JsonObject definition) {
        super(definition);
        this.fact = ResourceLocation.tryParse(JsonUtils.getString(definition, "fact"));
        if (this.fact == null) {
            throw new IllegalArgumentException("Invalid narrative fact resource id");
        }
        if (this.getRequiredAmount() != 1) {
            throw new IllegalArgumentException("fact objective required_amount must be 1");
        }
    }

    private boolean serverSatisfied() {
        Quest parent = this.getParent();
        if (parent == null || parent.manager.isClient() || !(parent.manager.player instanceof ServerPlayer player)) {
            return super.isCompleted();
        }
        return OverlordNarrativeState.get(player.server).hasFact(this.fact);
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
