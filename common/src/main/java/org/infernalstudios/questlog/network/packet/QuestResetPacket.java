package org.infernalstudios.questlog.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.network.IPacketContext;

public record QuestResetPacket(ResourceLocation id) {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.CLIENT_TO_SERVER;

    public static QuestResetPacket decode(FriendlyByteBuf buf) {
        return new QuestResetPacket(buf.readResourceLocation());
    }

    public static void handle(QuestResetPacket packet, IPacketContext ctx) {
        if (!(ctx.getSender() instanceof ServerPlayer sender)) {
            Questlog.LOGGER.warn("Ignoring quest reset request because no server player sender is available");
            return;
        }
        if (ServerPlayerManager.INSTANCE == null) {
            Questlog.LOGGER.warn("Ignoring reset request for {} because the server quest manager is unavailable", packet.id);
            return;
        }

        QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(sender);
        if (!manager.isActive()) {
            Questlog.LOGGER.warn("Ignoring reset request for {} from an inactive quest manager", packet.id);
            return;
        }

        Quest quest = manager.getQuest(packet.id);
        if (quest == null) {
            Questlog.LOGGER.warn("Ignoring reset request for unknown quest {}", packet.id);
            return;
        }
        if (!quest.isRepeatable()) {
            Questlog.LOGGER.warn("Ignoring reset request for non-repeatable quest {}", packet.id);
            return;
        }

        // The client only exposes reset after a repeatable quest has completed and
        // all rewards have been collected. Enforce the same contract on the server
        // so a forged packet cannot erase in-progress state or bypass a pending
        // reward choice.
        if (!quest.isCompleted() || !quest.isRewarded()) {
            Questlog.LOGGER.warn(
                    "Ignoring premature reset request for repeatable quest {} (completed={}, rewarded={})",
                    packet.id,
                    quest.isCompleted(),
                    quest.isRewarded()
            );
            return;
        }

        if (quest.isGlobal()) {
            ServerPlayerManager.INSTANCE.resetGlobalQuest(quest.getId());
        } else {
            quest.resetProgress();
            ServerPlayerManager.INSTANCE.save(manager);
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.id);
    }
}
