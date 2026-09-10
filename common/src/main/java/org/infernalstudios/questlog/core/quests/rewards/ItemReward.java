package org.infernalstudios.questlog.core.quests.rewards;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.util.CachedValue;
import org.infernalstudios.questlog.util.JsonUtils;
import org.infernalstudios.questlog.util.Util;
import org.jetbrains.annotations.Nullable;

public class ItemReward extends Reward {

    private final CachedValue<ItemStack> stack;

    public ItemReward(JsonObject definition) {
        super(definition);

        int count = JsonUtils.getOrDefault(definition, "count", 1);
        if (count < 1) {
            throw new IllegalArgumentException("Item reward count must be at least 1");
        }

        JsonElement itemDefinition = definition.get("item");
        this.stack = new CachedValue<>(() -> {
            ItemStack parsed = parseItemStack(itemDefinition);
            if (parsed.isEmpty()) {
                throw new IllegalArgumentException("Item reward must resolve to a non-empty registered item stack");
            }
            parsed.setCount(count);
            return parsed;
        });

        // Validate while Quest.create is still inside its definition-load error
        // boundary. Leaving this lazy allowed an unknown/defaulted item ID to turn
        // into AIR and only surface when the reward was displayed or claimed, at
        // which point the quest could silently mark an empty reward as collected.
        this.stack.get();
    }

    public static ItemStack parseItemStack(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return ItemStack.EMPTY;
        }
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            ResourceLocation id = ResourceLocation.tryParse(element.getAsString());
            if (id == null || !BuiltInRegistries.ITEM.containsKey(id)) {
                return ItemStack.EMPTY;
            }
            Item item = BuiltInRegistries.ITEM.get(id);
            return item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
        }
        if (element.isJsonObject()) {
            try {
                ItemStack stack = ItemStack.CODEC.parse(JsonOps.INSTANCE, element)
                        .result().orElse(ItemStack.EMPTY);
                if (!stack.isEmpty()) {
                    return stack;
                }
            } catch (Exception e) {
                Questlog.LOGGER.error("Failed to parse ItemStack from JSON", e);
            }

            JsonObject obj = element.getAsJsonObject();
            String idStr = obj.has("id") && obj.get("id").isJsonPrimitive()
                    ? obj.get("id").getAsString()
                    : (obj.has("item") && obj.get("item").isJsonPrimitive() ? obj.get("item").getAsString() : null);
            if (idStr != null) {
                ResourceLocation id = ResourceLocation.tryParse(idStr);
                if (id == null || !BuiltInRegistries.ITEM.containsKey(id)) {
                    return ItemStack.EMPTY;
                }
                Item item = BuiltInRegistries.ITEM.get(id);
                if (item != Items.AIR) {
                    return new ItemStack(item);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public ItemStack getStack() {
        return this.stack.get();
    }

    @Override
    public void applyReward(ServerPlayer player) {
        ItemStack itemStack = this.stack.get();
        if (!itemStack.isEmpty()) {
            Util.giveToPlayer(player, itemStack.copy());
        }
        super.applyReward(player);
    }
}
