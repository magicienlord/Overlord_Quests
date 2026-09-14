package org.infernalstudios.questlog.overlord.nightwalker;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.quests.EditorMetadata;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.QuestObjectiveRegistry;
import org.infernalstudios.questlog.core.quests.objectives.Objective;

/**
 * Observes Nycto alpha.3's real Vampire Altar purchase state without becoming a
 * hard NightWalker dependency. Progress is the greatest purchased-power count
 * ever observed while the quest is active; removing powers or curing vampirism
 * cannot erase already demonstrated progression.
 */
public final class NightwalkerPowerCountObjective extends Objective {
    private int ticksUntilCheck;

    public static void register() {
        QuestObjectiveRegistry.register(
                new ResourceLocation("overlord_reign", "nightwalker_power_count"),
                NightwalkerPowerCountObjective::new,
                new EditorMetadata(null, null, "required_amount")
        );
    }

    public NightwalkerPowerCountObjective(JsonObject definition) {
        super(definition);
        if (this.getRequiredAmount() < 1 || this.getRequiredAmount() > NightwalkerState.CHOOSABLE_POWER_COUNT) {
            throw new IllegalArgumentException("nightwalker_power_count required_amount must be between 1 and 13");
        }
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onPlayerTick);
    }

    private void onPlayerTick(TriggerPlayerEvent.Tick event) {
        if (!(event.player instanceof ServerPlayer player)
                || !this.isActiveForPlayer(player)
                || super.isCompleted()) {
            return;
        }
        if (--this.ticksUntilCheck > 0) return;
        this.ticksUntilCheck = 20;

        int observed = NightwalkerState.purchasedPowerCount(player);
        if (observed > this.getUnits()) {
            this.setUnits(observed);
        }
    }

    @Override
    public boolean isCompleted() {
        return Math.max(this.getUnits(), livePowerCount()) >= this.getRequiredAmount();
    }

    @Override
    public void writeInitialData(CompoundTag data) {
        super.writeInitialData(data);
        data.putInt("units", Math.min(this.getRequiredAmount(), Math.max(this.getUnits(), livePowerCount())));
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag data = super.serialize();
        data.putInt("units", Math.min(this.getRequiredAmount(), Math.max(this.getUnits(), livePowerCount())));
        return data;
    }

    private int livePowerCount() {
        Quest parent = this.getParent();
        if (parent == null || parent.manager.isClient() || !(parent.manager.player instanceof ServerPlayer player)) {
            return 0;
        }
        return NightwalkerState.purchasedPowerCount(player);
    }
}
