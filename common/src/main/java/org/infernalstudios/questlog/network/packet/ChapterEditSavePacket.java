package org.infernalstudios.questlog.network.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.DefinitionUtil;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.network.IPacketContext;
import org.infernalstudios.questlog.platform.Services;
import org.infernalstudios.questlog.util.AtomicJsonFileUtil;
import org.infernalstudios.questlog.util.DefinitionPathUtil;

import java.io.IOException;
import java.nio.file.Path;

public record ChapterEditSavePacket(ResourceLocation id, String json) {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.CLIENT_TO_SERVER;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static ChapterEditSavePacket decode(FriendlyByteBuf buf) {
        return new ChapterEditSavePacket(buf.readResourceLocation(), buf.readUtf());
    }

    public static void handle(ChapterEditSavePacket packet, IPacketContext ctx) {
        EditorPacketGuard.AuthorizedEditor authorization = EditorPacketGuard.requireAuthorized(ctx, "chapter editor save request");
        if (authorization == null) {
            return;
        }
        ServerPlayer player = authorization.player();

        if (!Questlog.MODID.equals(packet.id.getNamespace())) {
            Questlog.LOGGER.warn("Rejected chapter editor save for unsupported namespace: {}", packet.id);
            return;
        }

        final JsonObject definition;
        try {
            definition = GSON.fromJson(packet.json, JsonObject.class);
        } catch (JsonParseException e) {
            Questlog.LOGGER.warn("Rejected malformed chapter definition for {}: {}", packet.id, e.getMessage());
            return;
        }
        if (definition == null) {
            Questlog.LOGGER.warn("Rejected empty chapter definition for {}", packet.id);
            return;
        }

        try {
            Path configDir = Services.PLATFORM.getConfigDirectory().resolve("questlog");
            Path chapterDir = configDir.resolve("chapters");
            Path filePath = DefinitionPathUtil.resolveJsonDefinition(chapterDir, packet.id);
            AtomicJsonFileUtil.write(filePath, GSON, definition);
            Questlog.LOGGER.info("Saved chapter definition for {} to {}", packet.id, filePath);

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
            Questlog.LOGGER.error("Failed to save chapter definition", e);
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.id);
        buf.writeUtf(this.json);
    }
}
