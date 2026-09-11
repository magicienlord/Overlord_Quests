package org.infernalstudios.questlog.overlord.narrative;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.util.JsonUtils;

/** Reward that commits an authored civilization disposition state. */
public final class SetDispositionReward extends Reward {
    private final ResourceLocation civilization;
    private final ResourceLocation state;

    public SetDispositionReward(JsonObject definition) {
        super(definition);
        this.civilization = parseRequired(definition, "civilization");
        this.state = parseRequired(definition, "state");
    }

    private static ResourceLocation parseRequired(JsonObject definition, String key) {
        ResourceLocation id = ResourceLocation.tryParse(JsonUtils.getString(definition, key));
        if (id == null) {
            throw new IllegalArgumentException("Invalid set_disposition " + key + " resource id");
        }
        return id;
    }

    @Override
    public void applyReward(ServerPlayer player) {
        if (player == null || this.hasRewarded()) {
            return;
        }

        OverlordNarrativeState.get(player.server).setDisposition(this.civilization, this.state);
        super.applyReward(player);

        // Disposition objectives may be prerequisites in any quest. Re-evaluate
        // the existing authoritative quest state immediately after the world fact
        // changes so the client does not wait for an unrelated objective event.
        if (ServerPlayerManager.INSTANCE != null) {
            ServerPlayerManager.INSTANCE.syncAllQuestState();
        }
    }
}
