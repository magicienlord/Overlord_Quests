package org.infernalstudios.questlog.core.quests.objectives.entity;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerEntityEvent;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

/**
 * Persistent player-attributed entity-kill history for sequence-break-safe
 * campaign milestones that need the full EntityMatcher surface.
 *
 * Unlike entity_kill_stat, this objective can match authored scoreboard tags,
 * custom names, entity predicates, or entity-type tags because it observes the
 * actual death event. It therefore cannot reconstruct kills from before this
 * Questlog definition was loaded, but it can record a matching kill while the
 * parent quest is still locked and preserve that observation until later
 * prerequisites expose the quest.
 */
public final class EntityKillHistoryObjective extends AbstractEntityObjective {
    private boolean seen = false;

    public EntityKillHistoryObjective(JsonObject definition) {
        super(definition);
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onEntityDeath);
    }

    private void onEntityDeath(TriggerEntityEvent.Death event) {
        if (!(event.damageSource.getEntity() instanceof ServerPlayer player)
                || !this.isActiveForPlayer(player)
                || this.seen
                || this.optionalProgressFrozen()
                || event.entity == null) {
            return;
        }

        if (this.test(event.entity)) {
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
