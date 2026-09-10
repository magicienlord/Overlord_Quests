package org.infernalstudios.questlog.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.QuestlogClient;
import org.infernalstudios.questlog.QuestlogEvents;
import org.infernalstudios.questlog.client.gui.screen.ChapterEditorScreen;
import org.infernalstudios.questlog.client.gui.screen.QuestDetails;
import org.infernalstudios.questlog.client.gui.screen.QuestEditorScreen;
import org.infernalstudios.questlog.client.gui.screen.QuestlogScreen;
import org.infernalstudios.questlog.core.DefinitionUtil;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.event.events.QuestEvent;
import org.infernalstudios.questlog.network.packet.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientPacketHandler {
    private static final Gson GSON = new GsonBuilder().create();
    private static final List<QuestDefinitionPacket> DEFERRED_DEFS = new CopyOnWriteArrayList<>();
    private static QuestSyncPacket DEFERRED_SYNC_PACKET = null;

    public static void handle(QuestCompletedPacket packet, IPacketContext ctx) {
        if (Minecraft.getInstance().player == null) {
            Questlog.LOGGER.warn("Ignoring quest completion packet for {} because no local player is available", packet.id());
            return;
        }
        QuestManager manager = QuestlogClient.getLocal();
        Quest quest = manager.getQuest(packet.id());
        if (quest == null) {
            Questlog.LOGGER.warn("Ignoring quest completion packet for unknown quest {}", packet.id());
            return;
        }
        QuestlogEvents.onQuestCompleted(new QuestEvent.Completed(manager.player, quest, false));
    }

    public static void handle(QuestDataPacket packet, IPacketContext ctx) {
        if (Minecraft.getInstance().player == null) {
            Questlog.LOGGER.warn("Ignoring quest data packet for {} because no local player is available", packet.id());
            return;
        }
        try {
            QuestManager manager = QuestlogClient.getLocal();
            Quest quest = manager.getQuest(packet.id());
            if (quest == null) {
                throw new IllegalStateException("Quest is null, likely definition not loaded yet");
            }
            if (packet.data() == null) {
                throw new IllegalArgumentException("Quest data payload is null");
            }
            quest.deserialize(packet.data());
        } catch (Throwable e) {
            Questlog.LOGGER.error("Failed to handle QuestDataPacket for {}", packet.id(), e);
        }
    }

    public static void handle(QuestDefinitionPacket packet, IPacketContext ctx) {
        if (Minecraft.getInstance().player == null) {
            // Keep at most the newest definition for an ID while the local player
            // is unavailable. This prevents repeated server reloads during login
            // from growing the deferred queue or replaying stale definitions.
            DEFERRED_DEFS.removeIf(existing -> existing.id().equals(packet.id()));
            DEFERRED_DEFS.add(packet);
            return;
        }
        try {
            QuestManager manager = QuestlogClient.getLocal();
            Quest existing = manager.getQuest(packet.id());
            CompoundTag savedData = existing != null ? existing.serialize() : null;
            JsonObject definition = GSON.fromJson(packet.getJsonString(), JsonObject.class);
            if (definition == null) {
                throw new IllegalArgumentException("Synced quest definition is JSON null");
            }
            Quest quest = Quest.create(definition, packet.id(), manager);
            if (savedData != null) {
                quest.deserialize(savedData);
            }
            manager.addQuest(quest);
        } catch (Throwable e) {
            Questlog.LOGGER.error("Failed to handle QuestDefinitionPacket for {}", packet.id(), e);
        }
    }

    public static void handleDeferredDefinitions() {
        if (Minecraft.getInstance().player == null) {
            return;
        }
        for (QuestDefinitionPacket packet : DEFERRED_DEFS) {
            handle(packet, null);
        }
        DEFERRED_DEFS.clear();
    }

    /**
     * Clears packet state that belongs to a client connection. A disconnect can
     * occur before the local-player login callback consumes deferred packets, so
     * leaving these statics populated would allow one server/world's definitions
     * to be applied to the next connection.
     */
    public static void clearDeferredState() {
        DEFERRED_DEFS.clear();
        DEFERRED_SYNC_PACKET = null;
    }

    public static void handle(QuestRemovePacket packet, IPacketContext ctx) {
        if (Minecraft.getInstance().player == null) {
            DEFERRED_DEFS.removeIf(existing -> existing.id().equals(packet.id()));
            return;
        }
        Questlog.LOGGER.trace("Received remove packet for quest {}", packet.id().toString());
        QuestManager manager = QuestlogClient.getLocal();
        manager.removeQuest(packet.id());
    }

    public static void handle(QuestTriggeredPacket packet, IPacketContext ctx) {
        if (Minecraft.getInstance().player == null) {
            Questlog.LOGGER.warn("Ignoring quest trigger packet for {} because no local player is available", packet.id());
            return;
        }
        QuestManager manager = QuestlogClient.getLocal();
        Quest quest = manager.getQuest(packet.id());
        if (quest == null) {
            Questlog.LOGGER.warn("Ignoring quest trigger packet for unknown quest {}", packet.id());
            return;
        }
        QuestlogEvents.onQuestTriggered(new QuestEvent.Triggered(manager.player, quest, false));
    }

    public static void handle(QuestOpenPacket packet, IPacketContext ctx) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        String target = packet.target();
        if (target != null && !target.isEmpty()) {
            ResourceLocation id = ResourceLocation.tryParse(target);
            if (id != null) {
                Quest quest = QuestlogClient.getLocal().getQuest(id);
                if (quest != null) {
                    mc.setScreen(new QuestDetails(mc.screen, quest));
                    return;
                }
            }
        }
        mc.setScreen(new QuestlogScreen(mc.screen));
    }

    public static void handle(QuestEditModePacket packet, IPacketContext ctx) {
        QuestlogClient.isEditModeActive = packet.enabled();
        Questlog.LOGGER.info("Questlog Edit Mode has been set to: {}", packet.enabled());
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen instanceof QuestlogScreen questlogScreen) {
            questlogScreen.init(mc, questlogScreen.width, questlogScreen.height);
        }
    }

    public static void handle(QuestSyncPacket packet, IPacketContext ctx) {
        if (Minecraft.getInstance().player == null) {
            // Full sync supersedes any earlier full sync. Keep only the latest one
            // until the local player exists.
            DEFERRED_SYNC_PACKET = packet;
            return;
        }
        processSync(packet);
        refreshOpenQuestScreenAfterSync();
    }

    private static void refreshOpenQuestScreenAfterSync() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen instanceof QuestlogScreen questlogScreen) {
            questlogScreen.init(mc, questlogScreen.width, questlogScreen.height);
        } else if (mc.screen instanceof QuestDetails detailsScreen) {
            Quest updatedQuest = QuestlogClient.getLocal().getQuest(detailsScreen.quest.getId());
            if (updatedQuest != null) {
                mc.setScreen(new QuestDetails(detailsScreen.getPreviousScreen(), updatedQuest));
            } else {
                mc.setScreen(new QuestlogScreen(detailsScreen.getPreviousScreen()));
            }
        } else if (mc.screen instanceof ChapterEditorScreen chapterEditorScreen) {
            chapterEditorScreen.saveTemporaryState();
            chapterEditorScreen.refreshScreen();
        } else if (mc.screen instanceof QuestEditorScreen questEditorScreen) {
            questEditorScreen.saveTemporaryState();
            questEditorScreen.refreshScreen();
        }
    }

    public static void handleDeferredSync() {
        if (DEFERRED_SYNC_PACKET != null && Minecraft.getInstance().player != null) {
            QuestSyncPacket packet = DEFERRED_SYNC_PACKET;
            DEFERRED_SYNC_PACKET = null;
            processSync(packet);
            refreshOpenQuestScreenAfterSync();
        }
    }

    private static void processSync(QuestSyncPacket packet) {
        Questlog.LOGGER.info("Received quest & chapter sync from server.");

        Minecraft minecraft = Minecraft.getInstance();
        // In an integrated single-player server, DefinitionUtil's static caches
        // are shared by the logical client and server in the same JVM. Clearing
        // and rebuilding them from the client thread creates a cross-thread empty
        // cache window for the still-running server. The server has already loaded
        // exactly the definitions represented by this sync packet, so the client
        // can consume the packet directly without mutating those shared caches.
        // Remote multiplayer clients have no local server and therefore maintain
        // their own static mirror from the received definitions.
        boolean sharesDefinitionCacheWithIntegratedServer = minecraft.hasSingleplayerServer();
        if (!sharesDefinitionCacheWithIntegratedServer) {
            DefinitionUtil.clearClientCaches();
        }

        QuestlogClient.ALL_ADVANCEMENTS = new java.util.ArrayList<>(packet.advancements());
        for (Map.Entry<ResourceLocation, String> entry : packet.chapterDefinitions().entrySet()) {
            try {
                JsonObject def = GSON.fromJson(entry.getValue(), JsonObject.class);
                if (def != null) {
                    if (!sharesDefinitionCacheWithIntegratedServer) {
                        DefinitionUtil.putClientMirrorChapter(entry.getKey(), def);
                    }
                } else {
                    Questlog.LOGGER.warn("Ignoring JSON-null synced chapter {}", entry.getKey());
                }
            } catch (Exception e) {
                Questlog.LOGGER.error("Failed to parse synced chapter {}", entry.getKey(), e);
            }
        }

        QuestManager manager = QuestlogClient.getLocal();
        manager.clearQuests();
        for (Map.Entry<ResourceLocation, String> entry : packet.definitions().entrySet()) {
            try {
                JsonObject def = GSON.fromJson(entry.getValue(), JsonObject.class);
                if (def == null) {
                    throw new IllegalArgumentException("Synced quest definition is JSON null");
                }
                if (!sharesDefinitionCacheWithIntegratedServer) {
                    DefinitionUtil.putClientMirrorQuest(entry.getKey(), def);
                }
                Quest quest = Quest.create(def, entry.getKey(), manager);
                manager.addQuest(quest);
            } catch (Exception e) {
                Questlog.LOGGER.error("Failed to parse synced quest {}", entry.getKey(), e);
                try {
                    JsonObject sourceDef = GSON.fromJson(entry.getValue(), JsonObject.class);
                    JsonObject fallbackDef = new JsonObject();
                    fallbackDef.addProperty("title", "Broken Quest (" + entry.getKey().getPath() + ")");
                    String errorMsg = e.getMessage() != null ? e.getMessage() : e.toString();
                    if (e.getCause() != null && e.getCause().getMessage() != null) {
                        errorMsg += "\nCaused by: " + e.getCause().getMessage();
                    }
                    fallbackDef.addProperty("description", "This quest failed to load properly. Edit it to fix errors.\n\nError details:\n" + errorMsg);
                    fallbackDef.addProperty("chapter", sourceDef != null && sourceDef.has("chapter") ? sourceDef.get("chapter").getAsString() : "main");
                    Quest quest = Quest.create(fallbackDef, entry.getKey(), manager);
                    manager.addQuest(quest);
                } catch (Exception ex) {
                    Questlog.LOGGER.error("Failed to load fallback for synced quest {}", entry.getKey(), ex);
                }
            }
        }
        for (Map.Entry<ResourceLocation, CompoundTag> entry : packet.data().entrySet()) {
            Quest quest = manager.getQuest(entry.getKey());
            if (quest != null) {
                quest.deserialize(entry.getValue());
            } else {
                Questlog.LOGGER.warn("Ignoring synced quest data for unknown quest {}", entry.getKey());
            }
        }
    }
}
