package org.infernalstudios.questlog.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.network.IPacketContext;
import org.infernalstudios.questlog.overlord.provider.QuestProviderInteraction;
import org.infernalstudios.questlog.overlord.provider.QuestProviderService;

import java.util.UUID;

/** Server-authoritative accept/turn-in action for an open provider menu. */
public record QuestProviderActionPacket(
        int providerEntityId,
        UUID providerId,
        ResourceLocation questId,
        Action action
) {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.CLIENT_TO_SERVER;

    public enum Action {
        ACCEPT,
        TURN_IN
    }

    public QuestProviderActionPacket {
        if (providerId == null || questId == null || action == null) {
            throw new IllegalArgumentException("provider action packet fields must be non-null");
        }
    }

    public static QuestProviderActionPacket decode(FriendlyByteBuf buf) {
        int entityId = buf.readVarInt();
        UUID providerId = buf.readUUID();
        ResourceLocation questId = buf.readResourceLocation();
        int actionOrdinal = buf.readUnsignedByte();
        Action[] actions = Action.values();
        if (actionOrdinal >= actions.length) {
            throw new IllegalArgumentException("Unknown provider action: " + actionOrdinal);
        }
        return new QuestProviderActionPacket(entityId, providerId, questId, actions[actionOrdinal]);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(this.providerEntityId);
        buf.writeUUID(this.providerId);
        buf.writeResourceLocation(this.questId);
        buf.writeByte(this.action.ordinal());
    }

    public static void handle(QuestProviderActionPacket packet, IPacketContext context) {
        if (!(context.getSender() instanceof ServerPlayer player) || ServerPlayerManager.INSTANCE == null) {
            return;
        }

        Entity provider = player.serverLevel().getEntity(packet.providerEntityId);
        if (provider == null
                || !packet.providerId.equals(provider.getUUID())
                || !provider.isAlive()
                || !QuestProviderService.isWithinInteractionRange(player, provider)) {
            Questlog.LOGGER.warn("Rejected stale or out-of-range provider action {} from {}", packet.questId, player.getGameProfile().getName());
            return;
        }

        QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(player);
        if (manager == null || !manager.isActive()) {
            return;
        }

        Quest quest = manager.getQuest(packet.questId);
        if (quest == null) {
            Questlog.LOGGER.warn("Rejected provider action for unknown quest {} from {}", packet.questId, player.getGameProfile().getName());
            QuestProviderInteraction.sendMenu(player, provider, true);
            return;
        }

        boolean changed = switch (packet.action) {
            case ACCEPT -> QuestProviderService.accept(quest, provider);
            case TURN_IN -> QuestProviderService.turnIn(quest, provider);
        };

        if (!changed) {
            Questlog.LOGGER.debug("Provider action {} for quest {} was no longer eligible", packet.action, packet.questId);
        }
        QuestProviderInteraction.sendMenu(player, provider, true);
    }
}
