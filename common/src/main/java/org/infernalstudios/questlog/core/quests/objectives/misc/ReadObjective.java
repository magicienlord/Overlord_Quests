package org.infernalstudios.questlog.core.quests.objectives.misc;

import com.google.gson.JsonObject;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.event.events.QuestEvent;

import java.util.function.Consumer;

public class ReadObjective extends Objective {
    private final Consumer<QuestEvent.Read> readListener = this::onQuestRead;
    private boolean listenerRegistered = false;

    public ReadObjective(JsonObject definition) {
        super(definition);
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        if (!this.listenerRegistered) {
            Questlog.EVENTS.addListener(QuestEvent.Read.class, this.readListener);
            this.listenerRegistered = true;
        }
    }

    @Override
    public void unregisterEventListeners() {
        if (this.listenerRegistered) {
            Questlog.EVENTS.removeListener(QuestEvent.Read.class, this.readListener);
            this.listenerRegistered = false;
        }
        super.unregisterEventListeners();
    }

    private void onQuestRead(QuestEvent.Read event) {
        if (!this.isActiveQuestInstance() || this.isCompleted() || this.getParent() == null) return;
        if (event.player.equals(this.getParent().manager.player) && event.quest.getId().equals(this.getParent().getId())) {
            this.setUnits(this.getUnits() + 1);
        }
    }

    @Override
    public boolean isReadObjective() {
        return true;
    }
}
