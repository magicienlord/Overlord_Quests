package org.infernalstudios.questlog.core.quests.objectives.entity;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerEntityEvent;
import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;

public class EntityTameObjective extends AbstractEntityObjective {

    public EntityTameObjective(JsonObject definition) {
        super(definition);
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onAnimalTame);
    }

    private void onAnimalTame(TriggerEntityEvent.TameAnimal event) {
        if (!(event.causedByPlayer instanceof ServerPlayer player)
                || !this.isActiveForPlayer(player)
                || this.isCompleted()) {
            return;
        }
        if (this.test(event.entity)) {
            this.setUnits(this.getUnits() + 1);
        }
    }
}
