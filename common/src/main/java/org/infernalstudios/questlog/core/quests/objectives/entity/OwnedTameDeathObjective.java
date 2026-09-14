package org.infernalstudios.questlog.core.quests.objectives.entity;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerEntityEvent;
import com.google.gson.JsonObject;
import net.minecraft.world.entity.TamableAnimal;

import java.util.UUID;

/**
 * Completes when a tameable entity owned by this Questlog player dies.
 *
 * This is intentionally an ownership-sensitive death signal rather than a kill
 * objective. It is suitable for conditional sidequests that should appear only
 * after the player's own companion is lost. An optional entity matcher can
 * narrow the accepted tameable types, including an entity-type tag supplied by
 * another installed mod.
 */
public class OwnedTameDeathObjective extends AbstractEntityObjective {

    public OwnedTameDeathObjective(JsonObject definition) {
        super(definition);
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onEntityDeath);
    }

    private void onEntityDeath(TriggerEntityEvent.Death event) {
        if (!this.isActiveQuestInstance()
                || this.getParent() == null
                || this.isCompleted()
                || !(event.entity instanceof TamableAnimal tameable)
                || this.getParent().manager.player == null) {
            return;
        }

        UUID ownerId = tameable.getOwnerUUID();
        if (ownerId == null
                || !ownerId.equals(this.getParent().manager.player.getUUID())
                || !this.test(tameable)) {
            return;
        }

        this.setUnits(this.getUnits() + 1);
    }
}
