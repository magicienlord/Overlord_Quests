package org.infernalstudios.questlog.core.quests.objectives.misc;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.util.JsonUtils;
import org.infernalstudios.questlog.util.Util;
import org.jetbrains.annotations.Nullable;

/**
 * Persistent authored-area visit history for sequence-break-safe campaign
 * anchors that are not represented by one exact structure registry entry.
 *
 * The observation is recorded while the definition is loaded even when the
 * parent quest is still locked. An optional dimension guard prevents identical
 * coordinates in another dimension from satisfying a world-specific anchor.
 */
public final class VisitPositionHistoryObjective extends Objective {
    private final BoundingBox bounds;
    @Nullable private final ResourceLocation dimension;
    private boolean seen = false;
    private int ticksUntilCheck = 0;

    public VisitPositionHistoryObjective(JsonObject definition) {
        super(definition);
        this.bounds = Util.bbFromJson(definition.get("bounds"));
        String dimensionId = JsonUtils.getOrDefault(definition, "dimension", (String) null);
        this.dimension = dimensionId == null ? null : new ResourceLocation(dimensionId);
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

        if ((this.dimension == null || player.level().dimension().location().equals(this.dimension))
                && this.bounds.isInside(player.blockPosition())) {
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
