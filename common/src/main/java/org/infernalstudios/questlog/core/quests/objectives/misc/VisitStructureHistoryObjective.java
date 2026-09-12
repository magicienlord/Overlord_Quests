package org.infernalstudios.questlog.core.quests.objectives.misc;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.util.JsonUtils;

/**
 * Persistent exact-structure visit history for sequence-break-safe campaign
 * discovery milestones.
 *
 * Unlike the inherited visit_structure objective, this objective deliberately
 * records a qualifying visit even while its parent quest is still locked. The
 * observation is stored in the quest's normal persistent objective state, so a
 * later prerequisite transition can recognize that the player already visited
 * the structure instead of forcing a duplicate expedition.
 *
 * This is Questlog history, not a claim that vanilla Minecraft stores arbitrary
 * historical structure visits. It can recognize visits observed while this
 * objective exists in the loaded definition set; it cannot reconstruct visits
 * from before the definition/mod was present in the world.
 */
public final class VisitStructureHistoryObjective extends Objective {
    private final ResourceKey<Structure> structure;
    private boolean seen = false;
    private int ticksUntilCheck = 0;

    public VisitStructureHistoryObjective(JsonObject definition) {
        super(definition);
        this.structure = ResourceKey.create(
                Registries.STRUCTURE,
                new ResourceLocation(JsonUtils.getString(definition, "structure"))
        );
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onPlayerTick);
    }

    private void onPlayerTick(TriggerPlayerEvent.Tick event) {
        if (!(event.player instanceof ServerPlayer player)
                || !this.isActiveForPlayer(player)
                || this.seen
                || this.optionalProgressFrozen()) {
            return;
        }

        if (--this.ticksUntilCheck > 0) {
            return;
        }
        this.ticksUntilCheck = 20;

        if (!player.serverLevel().isLoaded(player.blockPosition())) {
            return;
        }

        Structure target = player.serverLevel()
                .registryAccess()
                .registryOrThrow(Registries.STRUCTURE)
                .get(this.structure);
        if (target == null) {
            return;
        }

        if (player.serverLevel().structureManager()
                .getStructureWithPieceAt(player.blockPosition(), target)
                .isValid()) {
            this.seen = true;
            if (this.getParent() != null) {
                this.getParent().markForUpdate();
            }
        }
    }

    private boolean optionalProgressFrozen() {
        return this.isPartOfOptionalObjective()
                && this.getParent() != null
                && this.getParent().hasSentCompletion;
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
