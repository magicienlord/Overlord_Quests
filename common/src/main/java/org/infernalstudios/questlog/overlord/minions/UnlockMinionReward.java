package org.infernalstudios.questlog.overlord.minions;

import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.util.JsonUtils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Auto-claimed quest reward that delegates permanent type progression to the
 * public OVERLORD Minions API. Questlog never writes Minion roster or unlock
 * persistence itself.
 */
public final class UnlockMinionReward extends Reward {
    private static final Set<String> REPORTED_FAILURES = ConcurrentHashMap.newKeySet();

    private final OverlordMinionProgressionBridge.Slot slot;

    public UnlockMinionReward(JsonObject definition) {
        super(definition);
        this.slot = OverlordMinionProgressionBridge.Slot.parse(JsonUtils.getString(definition, "slot"));
        if (!this.isAutoClaim()) {
            throw new IllegalArgumentException("unlock_minion rewards must use auto_claim: true");
        }
    }

    @Override
    public void applyReward(ServerPlayer player) {
        if (player == null || this.hasRewarded()) {
            return;
        }

        OverlordMinionProgressionBridge.UnlockResult result =
                OverlordMinionProgressionBridge.unlock(player.server, this.slot);

        switch (result) {
            case UNLOCKED, ALREADY_UNLOCKED -> {
                super.applyReward(player);
                Questlog.LOGGER.info(
                        "Questlog reconciled OVERLORD Minions {} unlock through the public progression API ({})",
                        this.slot.serializedName(),
                        result.name()
                );
            }
            case OUT_OF_ORDER -> reportOnce(
                    result,
                    "Deferred OVERLORD Minions {} unlock because the owning mod rejected it as out of order",
                    false
            );
            case BOOTSTRAP_OWNED_BY_STAFF -> reportOnce(
                    result,
                    "Rejected OVERLORD Minions {} unlock because Brown bootstrap ownership belongs to the Master's Staff",
                    true
            );
            case API_UNAVAILABLE -> reportOnce(
                    result,
                    "Deferred OVERLORD Minions {} unlock because the stable progression API is unavailable",
                    false
            );
            case API_ERROR -> reportOnce(
                    result,
                    "Deferred OVERLORD Minions {} unlock because the stable progression API could not be invoked",
                    true
            );
        }
    }

    private void reportOnce(
            OverlordMinionProgressionBridge.UnlockResult result,
            String message,
            boolean error
    ) {
        String key = this.slot.name() + ":" + result.name();
        if (!REPORTED_FAILURES.add(key)) {
            return;
        }
        if (error) {
            Questlog.LOGGER.error(message, this.slot.serializedName());
        } else {
            Questlog.LOGGER.warn(message, this.slot.serializedName());
        }
    }
}
