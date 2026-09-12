package org.infernalstudios.questlog.core.quests.objectives.misc;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.util.JsonUtils;
import org.infernalstudios.questlog.util.Util;

import java.util.Objects;

public class StatisticObjective extends Objective {
    private final ResourceLocation stat;
    private int statAtStart = 0;
    private boolean retroactive = true;
    private boolean baselineCaptured = false;
    private int ticksUntilCheck = 0;

    public StatisticObjective(JsonObject definition) {
        super(definition);
        ResourceLocation parsedLocation = new ResourceLocation(JsonUtils.getString(definition, "stat"));
        if (!BuiltInRegistries.CUSTOM_STAT.containsKey(parsedLocation)) {
            throw new IllegalArgumentException("Unknown custom statistic: " + parsedLocation);
        }
        this.stat = parsedLocation;
        if (definition.has("retroactive")) {
            this.retroactive = JsonUtils.getBoolean(definition, "retroactive");
        }
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onPlayerTick);
    }

    /**
     * Non-retroactive objective statistics must begin at the quest trigger, not at
     * manager creation. Otherwise actions performed while a quest is still locked
     * can be counted as soon as that quest later becomes visible.
     */
    @Override
    public void onQuestTriggered() {
        if (!this.retroactive && !this.isPartOfPrerequisites() && !this.baselineCaptured) {
            this.captureBaseline();
        }
    }

    private void onPlayerTick(TriggerPlayerEvent.Tick event) {
        if (!(event.player instanceof ServerPlayer player)
                || !this.isActiveForPlayer(player)
                || this.isCompleted()) {
            return;
        }

        if (--ticksUntilCheck <= 0) {
            int currentStatValue = this.getStatValue();

            if (!this.retroactive && !this.baselineCaptured) {
                // Prerequisites are themselves the trigger condition, so their
                // non-retroactive baseline begins when the definition is loaded.
                // Ordinary objectives wait for the parent quest to trigger.
                if (this.isPartOfPrerequisites()
                        || (this.getParent() != null && this.getParent().isTriggered())) {
                    this.statAtStart = currentStatValue;
                    this.baselineCaptured = true;
                }
                ticksUntilCheck = 20;
                return;
            }

            int progress = this.retroactive
                    ? currentStatValue
                    : Math.max(0, currentStatValue - this.statAtStart);

            if (progress > this.getUnits()) {
                this.setUnits(progress);
            }
            ticksUntilCheck = 20;
        }
    }

    private void captureBaseline() {
        if (this.retroactive || this.baselineCaptured || !this.isActiveQuestInstance()) {
            return;
        }
        this.statAtStart = this.getStatValue();
        this.baselineCaptured = true;
    }

    private Stat<ResourceLocation> getStat() {
        return Stats.CUSTOM.get(this.stat);
    }

    private int getStatValue() {
        return Objects.requireNonNull(Util.getStats(Objects.requireNonNull(this.getParent()).manager.player)).getValue(this.getStat());
    }

    @Override
    public void writeInitialData(CompoundTag data) {
        super.writeInitialData(data);
        if (!this.retroactive) {
            // During QuestManager creation the Quest has not yet been inserted into
            // the manager map, so isActiveQuestInstance() intentionally returns
            // false. Initial server baselines therefore have to be captured here
            // directly instead of going through the live-instance trigger helper.
            if (!this.baselineCaptured
                    && this.getParent() != null
                    && !this.getParent().manager.isClient()
                    && (this.isPartOfPrerequisites() || this.getParent().isTriggered())) {
                this.statAtStart = this.getStatValue();
                this.baselineCaptured = true;
            }
            data.putInt("statAtStart", this.statAtStart);
            data.putBoolean("baselineCaptured", this.baselineCaptured);
        }
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag data = super.serialize();
        if (!this.retroactive) {
            data.putInt("statAtStart", this.statAtStart);
            data.putBoolean("baselineCaptured", this.baselineCaptured);
        }
        return data;
    }

    @Override
    public void deserialize(CompoundTag data) {
        super.deserialize(data);
        if (!this.retroactive) {
            this.statAtStart = Math.max(0, data.getInt("statAtStart"));
            if (data.contains("baselineCaptured")) {
                this.baselineCaptured = data.getBoolean("baselineCaptured");
            } else if (data.contains("statAtStart")) {
                // Compatibility with saves produced before baselineCaptured was
                // persisted. Preserve baselines for prerequisite/already-triggered
                // quests, but discard the old premature baseline of a locked
                // post-trigger objective so pre-trigger actions cannot leak in.
                this.baselineCaptured = this.isPartOfPrerequisites()
                        || (this.getParent() != null && this.getParent().hasSentTrigger);
                if (!this.baselineCaptured) {
                    this.statAtStart = 0;
                }
            } else {
                this.baselineCaptured = false;
            }
        }
    }

    @Override
    public void forceSetUnits(int units) {
        super.forceSetUnits(units);
        if (!this.retroactive && units <= 0) {
            this.statAtStart = 0;
            this.baselineCaptured = false;
        }
    }
}
