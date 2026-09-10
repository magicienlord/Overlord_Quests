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
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
            Path chapterPath = DefinitionPathUtil.resolveJsonDefinition(chapterDir, packet.id);
            if (!Files.exists(chapterPath)) {
                // A definition can exist only in the bundled JAR and therefore
                // have no removable config file. Do not create quest overrides or
                // report a successful delete for immutable bundled content. A pack
                // author can override bundled content explicitly in config, but
                // deleting that override merely reveals the bundled base again.
                Questlog.LOGGER.warn(
                        "Rejected chapter deletion for {} because no removable config override exists at {}",
                        packet.id,
                        chapterPath
                );
                return;
            }

            // Chapter membership is server-authoritative. Upstream made the client
            // send one QuestEditSavePacket per member before deleting the chapter,
            // causing N full definition reloads/syncs and trusting a potentially
            // stale client cache to decide which quests needed reassignment.
            //
            // Preflight every rewrite before touching disk. In particular, a JSON
            // parse failure is represented in the runtime cache by a synthetic
            // Broken Quest fallback. Writing that fallback over the malformed source
            // file would destroy the user's original content, so existing config
            // files are parsed directly and the whole deletion is aborted if any
            // member cannot be read safely.
            List<QuestRewrite> rewrites = new ArrayList<>();
            for (ResourceLocation questId : DefinitionUtil.getCachedQuestKeys()) {
                JsonObject cachedDefinition = DefinitionUtil.getCachedQuest(questId);
                Path questPath = DefinitionPathUtil.resolveJsonDefinition(questDir, questId);

                JsonObject sourceDefinition = cachedDefinition;
                if (Files.exists(questPath)) {
                    try (Reader reader = Files.newBufferedReader(questPath, StandardCharsets.UTF_8)) {
                        sourceDefinition = GSON.fromJson(reader, JsonObject.class);
                    }
                    if (sourceDefinition == null) {
                        throw new IOException("Quest definition is JSON null: " + questPath);
                    }
                }

                if (!belongsToChapter(sourceDefinition, packet.id)) {
                    continue;
                }

                JsonObject reassigned = sourceDefinition.deepCopy();
                reassigned.addProperty("chapter", "main");
                rewrites.add(new QuestRewrite(questId, questPath, reassigned));
            }

            for (QuestRewrite rewrite : rewrites) {
                AtomicJsonFileUtil.write(rewrite.path(), GSON, rewrite.definition());
                diskMayHaveChanged = true;
                Questlog.LOGGER.info("Reassigned quest {} to main before deleting chapter {}", rewrite.id(), packet.id);
            }

            if (Files.deleteIfExists(chapterPath)) {
                diskMayHaveChanged = true;
                Questlog.LOGGER.info("Deleted chapter definition file for {}", packet.id);
            } else {
                // The existence preflight succeeded but the file disappeared before
                // deletion. Reload if member rewrites happened, otherwise leave the
                // current authoritative state untouched.
                Questlog.LOGGER.warn("Chapter definition file {} disappeared before deletion", chapterPath);
            }
        } catch (IllegalArgumentException e) {
            Questlog.LOGGER.warn("Rejected unsafe chapter definition operation for {}: {}", packet.id, e.getMessage());
        } catch (IOException | RuntimeException e) {
            Questlog.LOGGER.error("Failed to delete chapter or safely reassign its quests", e);
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

    private record QuestRewrite(ResourceLocation id, Path path, JsonObject definition) {
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.id);
    }
}
