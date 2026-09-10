package org.infernalstudios.questlog.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.event.events.QuestEvent;
import org.infernalstudios.questlog.network.IPacketContext;

public record QuestReadPacket(ResourceLocation id) {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.CLIENT_TO_SERVER;

    public static QuestReadPacket decode(FriendlyByteBuf buf) {
        return new QuestReadPacket(buf.readResourceLocation());
    }

    public static void handle(QuestReadPacket packet, IPacketContext ctx) {
        if (!(ctx.getSender() instanceof ServerPlayer sender)) {
            Questlog.LOGGER.warn("Ignoring quest read request because no server player sender is available");
            return;
        }
        if (ServerPlayerManager.INSTANCE == null) {
            Questlog.LOGGER.warn("Ignoring read request for {} because the server quest manager is unavailable", packet.id);
            return;
        }

        QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(sender);
        Quest quest = manager.getQuest(packet.id);
        if (quest == null) {
            Questlog.LOGGER.warn("Ignoring read request for unknown quest {}", packet.id);
            return;
        }

        Questlog.EVENTS.post(new QuestEvent.Read(manager.player, quest, true));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.id);
    }
}
