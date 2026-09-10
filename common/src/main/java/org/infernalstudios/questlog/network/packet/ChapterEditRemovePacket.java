package org.infernalstudios.questlog.network.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
import java.nio.file.Files;
import java.nio.file.Path;

public record ChapterEditRemovePacket(ResourceLocation id) {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.CLIENT_TO_SERVER;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

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
        if (packet.id.getPath().equals("main")) {
            // The main chapter is a structural invariant. The GUI already hides
            // its delete button, but the server must reject a forged packet too.
            Questlog.LOGGER.warn("Rejected attempt to delete the main Questlog chapter");
            return;
        }

        Path configDir = Services.PLATFORM.getConfigDirectory().resolve("questlog");
        Path chapterDir = configDir.resolve("chapters");
        Path questDir = configDir.resolve("quests");
        boolean diskMayHaveChanged = false;

        try {
            // Chapter membership is server-authoritative. Upstream made the client
            // send one QuestEditSavePacket per member before deleting the chapter,
            // causing N full definition reloads/syncs and trusting a potentially
            // stale client cache to decide which quests needed reassignment.
            // Resolve membership against the server cache and rewrite every
            // matching quest override first, then remove the chapter in one pass.
            for (ResourceLocation questId : DefinitionUtil.getCachedQuestKeys()) {
                JsonObject questDefinition = DefinitionUtil.getCachedQuest(questId);
                if (!belongsToChapter(questDefinition, packet.id)) {
                    continue;
                }

                questDefinition.addProperty("chapter", "main");
                Path questPath = DefinitionPathUtil.resolveJsonDefinition(questDir, questId);
                AtomicJsonFileUtil.write(questPath, GSON, questDefinition);
                diskMayHaveChanged = true;
                Questlog.LOGGER.info("Reassigned quest {} to main before deleting chapter {}", questId, packet.id);
            }

            Path filePath = DefinitionPathUtil.resolveJsonDefinition(chapterDir, packet.id);
            if (Files.deleteIfExists(filePath)) {
                diskMayHaveChanged = true;
                Questlog.LOGGER.info("Deleted chapter definition file for {}", packet.id);
            } else {
                Questlog.LOGGER.warn("Tried to delete chapter definition file {} but it did not exist", filePath);
            }
        } catch (IllegalArgumentException e) {
            Questlog.LOGGER.warn("Rejected unsafe chapter definition operation for {}: {}", packet.id, e.getMessage());
        } catch (IOException e) {
            Questlog.LOGGER.error("Failed to delete chapter or reassign its quests", e);
        } finally {
            // A multi-file chapter removal can partially update disk if an I/O
            // failure occurs after one quest rewrite. Reload whenever any write
            // succeeded so in-memory authority never diverges from disk state.
            if (diskMayHaveChanged) {
                DefinitionUtil.loadFromConfig();
                if (ServerPlayerManager.INSTANCE != null) {
                    for (ServerPlayer onlinePlayer : player.getServer().getPlayerList().getPlayers()) {
                        QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(onlinePlayer);
                        manager.reload();
                        ServerPlayerManager.INSTANCE.syncPlayer(manager);
                    }
                }
            }
        }
    }

    private static boolean belongsToChapter(JsonObject questDefinition, ResourceLocation chapterId) {
        String rawChapter = questDefinition.has("chapter") && questDefinition.get("chapter").isJsonPrimitive()
                ? questDefinition.get("chapter").getAsString()
                : "main";

        if (rawChapter.isBlank()) {
            rawChapter = "main";
        }

        ResourceLocation normalized;
        try {
            normalized = rawChapter.contains(":")
                    ? ResourceLocation.tryParse(rawChapter)
                    : new ResourceLocation(Questlog.MODID, rawChapter);
        } catch (Exception ignored) {
            return false;
        }
        return chapterId.equals(normalized);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.id);
    }
}
