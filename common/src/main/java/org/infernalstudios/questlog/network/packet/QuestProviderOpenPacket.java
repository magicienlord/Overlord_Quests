package org.infernalstudios.questlog.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.client.provider.QuestProviderClientHandler;
import org.infernalstudios.questlog.network.IPacketContext;
import org.infernalstudios.questlog.overlord.provider.QuestProviderService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Server-authoritative snapshot of one NPC provider interaction. */
public record QuestProviderOpenPacket(
        int providerEntityId,
        UUID providerId,
        String providerName,
        List<QuestProviderService.InteractionEntry> entries
) {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.SERVER_TO_CLIENT;
    public static final int MAX_ENTRIES = 256;
    public static final int MAX_PROVIDER_NAME = 128;

    public QuestProviderOpenPacket {
        if (providerId == null || providerName == null || entries == null) {
            throw new IllegalArgumentException("provider menu packet fields must be non-null");
        }
        if (providerName.length() > MAX_PROVIDER_NAME) {
            throw new IllegalArgumentException("provider menu name exceeds " + MAX_PROVIDER_NAME + " characters");
        }
        if (entries.size() > MAX_ENTRIES) {
            throw new IllegalArgumentException("provider menu exceeds " + MAX_ENTRIES + " entries");
        }
        entries = List.copyOf(entries);
    }

    public static QuestProviderOpenPacket decode(FriendlyByteBuf buf) {
        int entityId = buf.readVarInt();
        UUID providerId = buf.readUUID();
        String providerName = buf.readUtf(MAX_PROVIDER_NAME);
        int count = buf.readVarInt();
        if (count < 0 || count > MAX_ENTRIES) {
            throw new IllegalArgumentException("Invalid provider menu entry count: " + count);
        }

        QuestProviderService.InteractionState[] states = QuestProviderService.InteractionState.values();
        List<QuestProviderService.InteractionEntry> entries = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ResourceLocation questId = buf.readResourceLocation();
            int stateOrdinal = buf.readUnsignedByte();
            if (stateOrdinal >= states.length) {
                throw new IllegalArgumentException("Unknown provider quest interaction state: " + stateOrdinal);
            }
            entries.add(new QuestProviderService.InteractionEntry(questId, states[stateOrdinal]));
        }
        return new QuestProviderOpenPacket(entityId, providerId, providerName, entries);
    }

    public void encode(FriendlyByteBuf buf) {
        if (this.providerName.length() > MAX_PROVIDER_NAME) {
            throw new IllegalArgumentException("provider menu name exceeds " + MAX_PROVIDER_NAME + " characters");
        }
        if (this.entries.size() > MAX_ENTRIES) {
            throw new IllegalArgumentException("provider menu exceeds " + MAX_ENTRIES + " entries");
        }
        buf.writeVarInt(this.providerEntityId);
        buf.writeUUID(this.providerId);
        buf.writeUtf(this.providerName, MAX_PROVIDER_NAME);
        buf.writeVarInt(this.entries.size());
        for (QuestProviderService.InteractionEntry entry : this.entries) {
            buf.writeResourceLocation(entry.questId());
            buf.writeByte(entry.state().ordinal());
        }
    }

    public static void handle(QuestProviderOpenPacket packet, IPacketContext context) {
        QuestProviderClientHandler.handle(packet);
    }
}
