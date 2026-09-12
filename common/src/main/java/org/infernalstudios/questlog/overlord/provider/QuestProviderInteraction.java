package org.infernalstudios.questlog.overlord.provider;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.network.packet.QuestProviderOpenPacket;
import org.infernalstudios.questlog.platform.Services;

import java.util.List;

/** Server boundary for opening and refreshing the temporary NPC sidequest menu. */
public final class QuestProviderInteraction {
    private QuestProviderInteraction() {
    }

    public static boolean sendMenu(ServerPlayer player, Entity provider, boolean allowEmpty) {
        if (player == null
                || provider == null
                || !provider.isAlive()
                || ServerPlayerManager.INSTANCE == null
                || !QuestProviderService.isWithinInteractionRange(player, provider)
                || !UmvuthiAudienceBridge.allowsProviderInteraction(provider, player)) {
            return false;
        }

        QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(player);
        if (manager == null || !manager.isActive()) {
            return false;
        }

        List<QuestProviderService.InteractionEntry> entries = QuestProviderService.interactionEntries(manager, provider);
        if (entries.isEmpty() && !allowEmpty) {
            return false;
        }
        if (entries.size() > QuestProviderOpenPacket.MAX_ENTRIES) {
            Questlog.LOGGER.warn(
                    "Provider {} exposed {} quest entries; truncating the temporary menu to {}",
                    provider.getUUID(),
                    entries.size(),
                    QuestProviderOpenPacket.MAX_ENTRIES
            );
            entries = List.copyOf(entries.subList(0, QuestProviderOpenPacket.MAX_ENTRIES));
        }

        String name = provider.getDisplayName().getString();
        if (name.length() > QuestProviderOpenPacket.MAX_PROVIDER_NAME) {
            name = name.substring(0, QuestProviderOpenPacket.MAX_PROVIDER_NAME);
        }

        Services.PLATFORM.sendPacketToClient(
                player,
                new QuestProviderOpenPacket(provider.getId(), provider.getUUID(), name, entries)
        );
        return true;
    }
}
