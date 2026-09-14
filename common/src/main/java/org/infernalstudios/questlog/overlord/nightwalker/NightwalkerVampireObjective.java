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
 * Player-specific NightWalker vampire-state objective.
 *
 * The objective observes Nycto's real completed-transformation flag and latches
 * success once seen. A later cure therefore cannot erase the historical fact that
 * this quest trigger legitimately occurred. The live owner state is also projected
 * during serialization so an already-transformed player is recognized immediately
 * after loading rather than waiting for the next one-second poll.
 */
public final class NightwalkerVampireObjective extends Objective {
    private int ticksUntilCheck;

    public static void register() {
        QuestObjectiveRegistry.register(
                new ResourceLocation("overlord_reign", "nightwalker_vampire"),
                NightwalkerVampireObjective::new,
                new EditorMetadata(null, null, "required_amount")
        );
    }

    public NightwalkerVampireObjective(JsonObject definition) {
        super(definition);
        if (this.getRequiredAmount() != 1) {
            throw new IllegalArgumentException("nightwalker_vampire required_amount must be exactly 1");
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

        if (NightwalkerState.isVampire(player)) {
            this.setUnits(1);
        }
    }

    @Override
    public boolean isCompleted() {
        return super.isCompleted() || liveVampireState();
    }

    @Override
    public void writeInitialData(CompoundTag data) {
        super.writeInitialData(data);
        if (liveVampireState()) data.putInt("units", 1);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag data = super.serialize();
        if (liveVampireState()) data.putInt("units", 1);
        return data;
    }

    private boolean liveVampireState() {
        Quest parent = this.getParent();
        if (parent == null || parent.manager.isClient() || !(parent.manager.player instanceof ServerPlayer player)) {
            return false;
        }
        return NightwalkerState.isVampire(player);
    }
}
