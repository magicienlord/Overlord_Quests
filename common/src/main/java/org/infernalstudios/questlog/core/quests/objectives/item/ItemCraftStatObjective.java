package org.infernalstudios.questlog.core.quests.objectives.item;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.Item;
import org.infernalstudios.questlog.core.quests.objectives.Objective;

/**
 * Retrospective exact-item crafting objective backed by Minecraft's persistent
 * per-item crafted statistics.
 *
 * This exists for sequence-break-safe campaign milestones where crafting may
 * happen before the quest becomes active. Vanilla crafted statistics are keyed
 * by one exact item, so tags and NBT predicates are deliberately unsupported.
 */
public final class ItemCraftStatObjective extends Objective {
    private final Item item;
    private int ticksUntilCheck = 0;

    public ItemCraftStatObjective(JsonObject definition) {
        super(definition);

        JsonElement itemElement = definition.get("item");
        if (itemElement == null || !itemElement.isJsonPrimitive() || !itemElement.getAsJsonPrimitive().isString()) {
            throw new IllegalArgumentException("item_craft_stat requires an exact item resource id string");
        }

        String itemText = itemElement.getAsString().trim();
        if (itemText.startsWith("#")) {
            throw new IllegalArgumentException("item_craft_stat does not support item tags");
        }

        ResourceLocation itemId = ResourceLocation.tryParse(itemText);
        if (itemId == null || !BuiltInRegistries.ITEM.containsKey(itemId)) {
            throw new IllegalArgumentException("Unknown item for item_craft_stat: " + itemText);
        }
        this.item = BuiltInRegistries.ITEM.get(itemId);
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
            int historicalCrafts = player.getStats().getValue(Stats.ITEM_CRAFTED.get(this.item));
            if (historicalCrafts > this.getUnits()) {
                this.setUnits(historicalCrafts);
            }
            this.ticksUntilCheck = 20;
        }
    }
}
