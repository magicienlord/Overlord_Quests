package org.infernalstudios.questlog.overlord.narrative;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.util.JsonUtils;

/** Reward that records one monotonic authored world narrative fact. */
public final class SetFactReward extends Reward {
    private final ResourceLocation fact;

    public SetFactReward(JsonObject definition) {
        super(definition);
        this.fact = ResourceLocation.tryParse(JsonUtils.getString(definition, "fact"));
        if (this.fact == null) {
            throw new IllegalArgumentException("Invalid set_fact narrative fact resource id");
        }
    }

    @Override
    public void applyReward(ServerPlayer player) {
        if (player == null || this.hasRewarded()) {
            return;
        }

        OverlordNarrativeState.get(player.server).setFact(this.fact);
        super.applyReward(player);

        // Fact objectives and provider gates may exist anywhere in the active
        // graph. Re-evaluate immediately after the world fact becomes true.
        if (ServerPlayerManager.INSTANCE != null) {
            ServerPlayerManager.INSTANCE.syncAllQuestState();
        }
    }
}
