package org.infernalstudios.questlog.network.packet;

import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.network.IPacketContext;

/**
 * Server-authoritative gate for packets that mutate quest/chapter definition
 * files through the in-game editor.
 */
final class EditorPacketGuard {
    private EditorPacketGuard() {
    }

    static AuthorizedEditor requireAuthorized(IPacketContext ctx, String action) {
        if (ctx == null || !(ctx.getSender() instanceof ServerPlayer player)) {
            Questlog.LOGGER.warn("Rejected {} without a server player sender", action);
            return null;
        }
        if (!player.hasPermissions(2)) {
            Questlog.LOGGER.warn("Rejected {} from {} without permission level 2", action, player.getGameProfile().getName());
            return null;
        }
        if (ServerPlayerManager.INSTANCE == null) {
            Questlog.LOGGER.warn("Rejected {} because the server quest manager is unavailable", action);
            return null;
        }

        QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(player);
        if (!manager.isActive()) {
            Questlog.LOGGER.warn("Rejected {} from an inactive quest manager", action);
            return null;
        }
        if (!manager.isEditMode()) {
            Questlog.LOGGER.warn("Rejected {} from {} because Questlog edit mode is disabled", action, player.getGameProfile().getName());
            return null;
        }

        return new AuthorizedEditor(player, manager);
    }

    record AuthorizedEditor(ServerPlayer player, QuestManager manager) {
    }
}
