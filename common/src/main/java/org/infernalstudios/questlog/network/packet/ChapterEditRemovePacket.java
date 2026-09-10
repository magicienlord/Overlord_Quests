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

public record ChapterEditRemovePacket(ResourceLocation id) {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.CLIENT_TO_SERVER;

    public static ChapterEditRemovePacket decode(FriendlyByteBuf buf) {
        return new ChapterEditRemovePacket(buf.readResourceLocation());
    }

    public static void handle(ChapterEditRemovePacket packet, IPacketContext ctx) {
        EditorPacketGuard.AuthorizedEditor authorization = EditorPacketGuard.requireAuthorized(ctx, "chapter editor remove request");
        if (authorization == null) {
            return;
        }
        ServerPlayer player = authorization.player();

        if (!Questlog.MODID.equals(packet.id.getNamespace())) {
            Questlog.LOGGER.warn("Rejected chapter editor remove for unsupported namespace: {}", packet.id);
            return;
        }

        try {
            Path configDir = Services.PLATFORM.getConfigDirectory().resolve("questlog");
            Path chapterDir = configDir.resolve("chapters");
            Path filePath = DefinitionPathUtil.resolveJsonDefinition(chapterDir, packet.id);
            if (Files.deleteIfExists(filePath)) {
                Questlog.LOGGER.info("Deleted chapter definition file for {}", packet.id);
            } else {
                Questlog.LOGGER.warn("Tried to delete chapter definition file {} but it did not exist", filePath);
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
            Questlog.LOGGER.warn("Rejected unsafe chapter definition path for {}: {}", packet.id, e.getMessage());
        } catch (IOException e) {
            Questlog.LOGGER.error("Failed to delete chapter definition", e);
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.id);
    }
}
