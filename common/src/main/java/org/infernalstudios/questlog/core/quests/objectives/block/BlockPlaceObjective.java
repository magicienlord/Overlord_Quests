package org.infernalstudios.questlog.core.quests.objectives.block;

import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;
import com.evandev.triggers.event.events.TriggerBlockEvent;

public class BlockPlaceObjective extends AbstractBlockObjective {

    public BlockPlaceObjective(JsonObject definition) {
        super(definition);
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        com.evandev.triggers.Triggers.EVENTS.addListener(this::onBlockPlace);
    }

    private void onBlockPlace(TriggerBlockEvent.Place event) {
        if (!(event.entity instanceof ServerPlayer player)
                || !this.isActiveForPlayer(player)
                || this.isCompleted()) {
            return;
        }
        if (this.test(event.state)) {
            this.setUnits(this.getUnits() + 1);
        }
    }
}
