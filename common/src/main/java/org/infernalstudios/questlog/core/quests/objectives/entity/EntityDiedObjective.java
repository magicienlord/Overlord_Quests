package org.infernalstudios.questlog.core.quests.objectives.entity;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerEntityEvent;
import com.google.gson.JsonObject;

/**
 * Completes when the authored target entity dies, regardless of what caused the
 * death. This is deliberately distinct from the inherited `entity_death`
 * objective, whose historical Questlog meaning is "the player died to a matching
 * entity".
 *
 * The objective is primarily intended for explicit quest anchors selected by a
 * persistent scoreboard tag. It is event-driven rather than retrospective, so an
 * anchor that is legitimately killable must have its relevant quest/state active
 * before protection is removed unless another persistent world fact records an
 * earlier death.
 */
public class EntityDiedObjective extends AbstractEntityObjective {

    public EntityDiedObjective(JsonObject definition) {
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
                || event.entity == null) {
            return;
        }

        if (this.test(event.entity)) {
            this.setUnits(this.getUnits() + 1);
        }
    }
}
