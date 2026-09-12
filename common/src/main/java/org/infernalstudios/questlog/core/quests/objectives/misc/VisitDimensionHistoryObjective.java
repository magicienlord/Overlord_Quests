package org.infernalstudios.questlog.core.quests.objectives.misc;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.util.JsonUtils;

/**
 * Persistent exact-dimension visit history for sequence-break-safe campaign
 * milestones.
 *
 * The inherited visit_dimension objective uses the ordinary Questlog progress
 * boundary and therefore does not record progress while its parent quest is
 * still locked. This objective deliberately records an exact dimension visit as
 * soon as Questlog observes it, then preserves that observation until later
 * prerequisites expose the parent quest.
 *
 * This is Questlog-owned observation history. It does not claim that Minecraft
 * provides a universal historical dimension-visit statistic, and it cannot
 * reconstruct visits from before the relevant definition was loaded.
 */
public final class VisitDimensionHistoryObjective extends Objective {
    private final ResourceLocation dimension;
    private boolean seen = false;
    private int ticksUntilCheck = 0;

    public VisitDimensionHistoryObjective(JsonObject definition) {
        super(definition);
        this.dimension = new ResourceLocation(JsonUtils.getString(definition, "dimension"));
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onPlayerTick);
    }

    private void onPlayerTick(TriggerPlayerEvent.Tick event) {
        if (!(event.player instanceof ServerPlayer player)
                || !this.isActiveForPlayer(player)
                || this.seen) {
            return;
        }

        if (--this.ticksUntilCheck > 0) {
            return;
        }
        this.ticksUntilCheck = 20;

        if (player.level().dimension().location().equals(this.dimension)) {
            this.seen = true;
            if (this.getParent() != null) {
                this.getParent().markForUpdate();
            }
        }
    }

    @Override
    public int getUnits() {
        return this.seen ? this.getRequiredAmount() : 0;
    }

    @Override
    public boolean isCompleted() {
        return this.seen;
    }

    @Override
    public void setUnits(int units) {
        if (!this.isActiveQuestInstance()) {
            return;
        }
        this.seen = units > 0;
        if (this.getParent() != null) {
            this.getParent().markForUpdate();
        }
    }

    @Override
    public void forceSetUnits(int units) {
        if (!this.isActiveQuestInstance()) {
            return;
        }
        this.seen = units > 0;
        if (this.getParent() != null) {
            this.getParent().markForUpdate();
        }
    }

    @Override
    public void writeInitialData(CompoundTag data) {
        super.writeInitialData(data);
        data.putBoolean("seen", this.seen);
    }

    @Override
    public void deserialize(CompoundTag data) {
        super.deserialize(data);
        this.seen = data.getBoolean("seen") || data.getInt("units") > 0;
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag data = super.serialize();
        data.putBoolean("seen", this.seen);
        return data;
    }
}
