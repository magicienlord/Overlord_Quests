package org.infernalstudios.questlog.core.quests.objectives.entity;

import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import org.infernalstudios.questlog.util.JsonUtils;

public class EntityApproachObjective extends AbstractEntityObjective {

    private final int range;
    private int ticksUntilCheck = 0;

    public EntityApproachObjective(JsonObject definition) {
        super(definition);
        this.range = JsonUtils.getInt(definition, "range");
        if (this.range < 1) {
            throw new IllegalArgumentException("Entity approach range must be at least 1");
        }
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        com.evandev.triggers.Triggers.EVENTS.addListener(this::onPlayerMove);
    }

    private void onPlayerMove(TriggerPlayerEvent.Tick event) {
        if (!(event.player instanceof ServerPlayer player)
                || !this.isActiveForPlayer(player)
                || this.isCompleted()) {
            return;
        }

        if (--ticksUntilCheck <= 0) {
            if (!player.level().getEntities((Entity) null, player.getBoundingBox().inflate(this.range), this::test).isEmpty()) {
                this.setUnits(this.getUnits() + 1);
            }
            ticksUntilCheck = 20;
        }
    }
}
