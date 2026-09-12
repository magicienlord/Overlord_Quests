package org.infernalstudios.questlog;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.commands.QuestlogCommands;
import org.infernalstudios.questlog.core.DefinitionUtil;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.event.events.QuestEvent;
import org.infernalstudios.questlog.network.packet.QuestCompletedPacket;
import org.infernalstudios.questlog.network.packet.QuestTriggeredPacket;
import org.infernalstudios.questlog.overlord.minions.UnlockMinionReward;
import org.infernalstudios.questlog.platform.Services;
import org.infernalstudios.questlog.util.QuestlogMigrator;

public class QuestlogEvents {

    public static void onServerStart(MinecraftServer server) {
        // A normal server lifecycle stops the previous generation first. Keep an
        // explicit replacement guard so retained Triggers callbacks cannot remain
        // active if a loader/platform edge case initializes Questlog twice.
        if (ServerPlayerManager.INSTANCE != null) {
            Questlog.LOGGER.warn("Replacing an existing server quest manager generation during server start");
            ServerPlayerManager.INSTANCE.shutdown();
            ServerPlayerManager.INSTANCE = null;
        }

        QuestlogMigrator.attemptMigration(server);
        DefinitionUtil.loadFromConfig();
        ServerPlayerManager.INSTANCE = new ServerPlayerManager(server);
        ServerPlayerManager.INSTANCE.load();
    }

    public static void onPlayerSave(ServerPlayer player) {
        if (ServerPlayerManager.INSTANCE == null) return;
        QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(player);
        ServerPlayerManager.INSTANCE.save(manager);
    }

    public static void onServerStop() {
        if (ServerPlayerManager.INSTANCE != null) {
            ServerPlayerManager.INSTANCE.save();
            ServerPlayerManager.INSTANCE.shutdown();
            ServerPlayerManager.INSTANCE = null;
        }
        Questlog.EVENTS.removeAllListeners();
    }

    public static void onServerPlayerLogin(ServerPlayer player) {
        if (ServerPlayerManager.INSTANCE == null) return;
        QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(player);
        ServerPlayerManager.INSTANCE.load(manager);

        if (QuestlogMigrator.showDatapackWarning && player.hasPermissions(2)) {
            player.sendSystemMessage(Component.literal("§e[Questlog] Warning: Quests are now loaded from the config folder. Datapacks are no longer supported! Your old datapack quests were automatically migrated."));
        }
    }

    public static void onQuestTriggered(QuestEvent.Triggered event) {
        if (event == null || event.quest == null || event.player == null) {
            Questlog.LOGGER.warn("Ignoring invalid quest trigger event");
            return;
        }

        if (event.isServer) {
            if (!(event.player instanceof ServerPlayer serverPlayer)) {
                Questlog.LOGGER.warn("Ignoring server quest trigger event without a ServerPlayer for {}", event.quest.getId());
                return;
            }
            Services.PLATFORM.sendPacketToClient(serverPlayer, new QuestTriggeredPacket(event.quest.getId()));
        } else {
            QuestlogClientEvents.onQuestTriggered(event);
        }
    }

    public static void onQuestCompleted(QuestEvent.Completed event) {
        if (event == null || event.quest == null || event.player == null) {
            Questlog.LOGGER.warn("Ignoring invalid quest completion event");
            return;
        }

        if (event.isServer) {
            if (!(event.player instanceof ServerPlayer serverPlayer)) {
                Questlog.LOGGER.warn("Ignoring server quest completion event without a ServerPlayer for {}", event.quest.getId());
                return;
            }

            Questlog.EVENTS.post(event);
            Services.PLATFORM.sendPacketToClient(serverPlayer, new QuestCompletedPacket(event.quest.getId()));

            boolean minionProgressionCommitted = false;
            for (Reward reward : event.quest.rewards) {
                if (reward.isAutoClaim() && !reward.hasRewarded()) {
                    reward.applyReward(serverPlayer);
                    if (reward instanceof UnlockMinionReward && reward.hasRewarded()) {
                        minionProgressionCommitted = true;
                    }
                }
            }

            // QuestComplete listeners run before auto-claimed rewards. A later
            // Minion recovery quest can therefore observe the completed campaign
            // milestone while its owner-state prerequisite is still false. Once a
            // Minion unlock actually commits, refresh the active graph so those
            // owner-backed prerequisites become visible immediately instead of
            // waiting for a relog or unrelated quest update.
            if (minionProgressionCommitted && ServerPlayerManager.INSTANCE != null) {
                ServerPlayerManager.INSTANCE.syncAllQuestState();
            }
        } else {
            QuestlogClientEvents.onQuestCompleted(event);
        }
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        QuestlogCommands.register(dispatcher);
    }
}
