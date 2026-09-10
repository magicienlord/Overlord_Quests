package org.infernalstudios.questlog.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.DefinitionUtil;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.network.IPacketContext;
import org.infernalstudios.questlog.platform.Services;
import org.infernalstudios.questlog.util.DefinitionPathUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class QuestEditRemovePacket {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.CLIENT_TO_SERVER;

    private final ResourceLocation id;

    public QuestEditRemovePacket(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation id() { return this.id; }

    public static QuestEditRemovePacket decode(FriendlyByteBuf buf) {
        return new QuestEditRemovePacket(buf.readResourceLocation());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.id);
    }

    public static void handle(QuestEditRemovePacket packet, IPacketContext ctx) {
        EditorPacketGuard.AuthorizedEditor authorization = EditorPacketGuard.requireAuthorized(ctx, "quest editor remove request");
        if (authorization == null) {
            return;
        }
        ServerPlayer player = authorization.player();

        if (!Questlog.MODID.equals(packet.id.getNamespace())) {
            Questlog.LOGGER.warn("Rejected quest editor remove for unsupported namespace: {}", packet.id);
            return;
        }
        if (DefinitionUtil.isBundledQuest(packet.id)) {
            // Production definitions bundled in OVERLORD QUESTS are immutable
            // content. Saving the same ID may deliberately create a config override,
            // but Delete must not ambiguously mean "remove override and reveal the
            // bundled base". Keep deletion restricted to config-owned quests.
            Questlog.LOGGER.warn("Rejected editor deletion of bundled quest {}", packet.id);
            return;
        }

        try {
            Path configDir = Services.PLATFORM.getConfigDirectory().resolve("questlog");
            Path questDir = configDir.resolve("quests");
            Path filePath = DefinitionPathUtil.resolveJsonDefinition(questDir, packet.id);
            if (Files.deleteIfExists(filePath)) {
                Questlog.LOGGER.info("Deleted quest definition file for {}", packet.id);
            } else {
                Questlog.LOGGER.warn("Tried to delete quest definition file {} but it did not exist", filePath);
                return;
            }

            DefinitionUtil.loadFromConfig();

            if (ServerPlayerManager.INSTANCE != null) {
                for (ServerPlayer onlinePlayer : player.getServer().getPlayerList().getPlayers()) {
                    QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(onlinePlayer);
                    manager.reload();
                    ServerPlayerManager.INSTANCE.syncPlayer(manager);
                }
            }
        } catch (IllegalArgumentException e) {
            Questlog.LOGGER.warn("Rejected unsafe quest definition path for {}: {}", packet.id, e.getMessage());
        } catch (IOException e) {
            Questlog.LOGGER.error("Failed to delete quest definition", e);
        }
    }
}
