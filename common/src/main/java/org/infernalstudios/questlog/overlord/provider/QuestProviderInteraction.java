package org.infernalstudios.questlog.overlord.provider;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.network.packet.QuestProviderOpenPacket;
import org.infernalstudios.questlog.platform.Services;

import java.util.List;

/** Server boundary for opening and refreshing the temporary NPC sidequest menu. */
public final class QuestProviderInteraction {
    private static final int MAX_PROVIDER_NAME = 128;

    private QuestProviderInteraction() {
    }

    public static boolean sendMenu(ServerPlayer player, Entity provider, boolean allowEmpty) {
        if (player == null
                || provider == null
                || !provider.isAlive()
                || ServerPlayerManager.INSTANCE == null
                || !QuestProviderService.isWithinInteractionRange(player, provider)) {
            return false;
        }

        QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(player);
        List<QuestProviderService.InteractionEntry> entries = QuestProviderService.interactionEntries(manager, provider);
        if (entries.isEmpty() && !allowEmpty) {
            return false;
        }

        String name = provider.getDisplayName().getString();
        if (name.length() > MAX_PROVIDER_NAME) {
            name = name.substring(0, MAX_PROVIDER_NAME);
        }

        Services.PLATFORM.sendPacketToClient(
                player,
                new QuestProviderOpenPacket(provider.getId(), provider.getUUID(), name, entries)
        );
        return true;
    }
}
