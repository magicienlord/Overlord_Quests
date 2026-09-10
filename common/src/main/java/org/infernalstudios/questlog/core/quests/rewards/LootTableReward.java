package org.infernalstudios.questlog.core.quests.rewards;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.util.JsonUtils;
import org.infernalstudios.questlog.util.Util;

import java.util.List;
import java.util.Objects;

public class LootTableReward extends Reward {

    private final ResourceLocation lootTable;

    public LootTableReward(JsonObject definition) {
        super(definition);
        this.lootTable = new ResourceLocation(JsonUtils.getString(definition, "loot_table"));
    }

    @Override
    public void applyReward(ServerPlayer player) {
        LootDataManager tables = Objects.requireNonNull(player.getServer()).getLootData();
        LootTable table = tables.getLootTable(this.lootTable);

        if (table == LootTable.EMPTY) {
            Questlog.LOGGER.error("Loot table not found: {}. Reward remains unclaimed.", this.lootTable);
            return;
        }

        // Quest rewards are semantically closest to vanilla advancement rewards.
        // The inherited implementation supplied entity/origin/killer parameters
        // while creating LootContextParamSets.EMPTY, which rejects non-empty
        // parameter sets. ADVANCEMENT_REWARD explicitly permits THIS_ENTITY and
        // ORIGIN and therefore gives loot conditions the expected player context.
        LootParams params = new LootParams.Builder(player.serverLevel())
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withParameter(LootContextParams.ORIGIN, player.position())
                .withLuck(player.getLuck())
                .create(LootContextParamSets.ADVANCEMENT_REWARD);

        List<ItemStack> stacks = table.getRandomItems(params);

        for (ItemStack stack : stacks) {
            Util.giveToPlayer(player, stack);
        }

        super.applyReward(player);
    }
}
