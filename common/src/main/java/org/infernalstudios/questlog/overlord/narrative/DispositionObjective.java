package org.infernalstudios.questlog.overlord.narrative;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.util.JsonUtils;

/** Objective satisfied while one civilization has one explicit authored state. */
public final class DispositionObjective extends Objective {
    private final ResourceLocation civilization;
    private final ResourceLocation state;

    public DispositionObjective(JsonObject definition) {
        super(definition);
        this.civilization = parseRequired(definition, "civilization");
        this.state = parseRequired(definition, "state");
        if (this.getRequiredAmount() != 1) {
            throw new IllegalArgumentException("disposition objective required_amount must be 1");
        }
    }

    private static ResourceLocation parseRequired(JsonObject definition, String key) {
        ResourceLocation id = ResourceLocation.tryParse(JsonUtils.getString(definition, key));
        if (id == null) {
            throw new IllegalArgumentException("Invalid disposition " + key + " resource id");
        }
        return id;
    }

    private boolean serverSatisfied() {
        Quest parent = this.getParent();
        if (parent == null || parent.manager.isClient() || !(parent.manager.player instanceof ServerPlayer player)) {
            return super.isCompleted();
        }
        return OverlordNarrativeState.get(player.server).hasDisposition(this.civilization, this.state);
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
