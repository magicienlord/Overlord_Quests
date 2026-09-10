package org.infernalstudios.questlog.core.quests.objectives.item;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;

public class ItemCraftObjective extends AbstractItemObjective {

    public ItemCraftObjective(JsonObject definition) {
        super(definition);
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onItemCraft);
    }

    private void onItemCraft(TriggerPlayerEvent.Craft event) {
        if (!(event.player instanceof ServerPlayer player)
                || !this.isActiveForPlayer(player)
                || this.isCompleted()) {
            return;
        }
        if (this.test(event.outputItem)) {
            this.setUnits(this.getUnits() + event.outputItem.getCount());
        }
    }
}
