package org.infernalstudios.questlog.core.quests.objectives.entity;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import org.infernalstudios.questlog.core.quests.objectives.Objective;

/**
 * Retrospective player-kill objective backed by Minecraft's persistent entity
 * kill statistics.
 *
 * Unlike {@code entity_kill}, this objective can recognize a kill that occurred
 * before the quest became active. The tradeoff is deliberate: vanilla statistics
 * are keyed by one exact entity type, so this objective does not accept entity
 * tags, custom-name filters, NBT predicates, or other EntityMatcher features.
 */
public final class EntityKillStatObjective extends Objective {
    private final EntityType<?> entityType;
    private int ticksUntilCheck = 0;

    public EntityKillStatObjective(JsonObject definition) {
        super(definition);

        JsonElement entityElement = definition.get("entity");
        if (entityElement == null || !entityElement.isJsonPrimitive() || !entityElement.getAsJsonPrimitive().isString()) {
            throw new IllegalArgumentException("entity_kill_stat requires an exact entity resource id string");
        }

        String entityText = entityElement.getAsString().trim();
        if (entityText.startsWith("#")) {
            throw new IllegalArgumentException("entity_kill_stat does not support entity tags");
        }

        ResourceLocation entityId = ResourceLocation.tryParse(entityText);
        if (entityId == null || !BuiltInRegistries.ENTITY_TYPE.containsKey(entityId)) {
            throw new IllegalArgumentException("Unknown entity type for entity_kill_stat: " + entityText);
        }
        this.entityType = BuiltInRegistries.ENTITY_TYPE.get(entityId);
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onPlayerTick);
    }

    private void onPlayerTick(TriggerPlayerEvent.Tick event) {
        if (!(event.player instanceof ServerPlayer player)
                || !this.isActiveForPlayer(player)
                || this.isCompleted()) {
            return;
        }

        if (--this.ticksUntilCheck <= 0) {
            int historicalKills = player.getStats().getValue(Stats.ENTITY_KILLED.get(this.entityType));
            if (historicalKills > this.getUnits()) {
                this.setUnits(historicalKills);
            }
            this.ticksUntilCheck = 20;
        }
    }
}
