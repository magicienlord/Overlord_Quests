package org.infernalstudios.questlog.core;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.QuestlogEvents;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.event.events.QuestEvent;
import org.infernalstudios.questlog.network.packet.QuestDataPacket;
import org.infernalstudios.questlog.network.packet.QuestRemovePacket;
import org.infernalstudios.questlog.overlord.minions.UnlockMinionReward;
import org.infernalstudios.questlog.platform.Services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuestManager {
    private final Map<ResourceLocation, Quest> quests = new LinkedHashMap<>();
    private final QuestManagerLifecycle lifecycle = new QuestManagerLifecycle();
    public Player player;
    private boolean editMode = false;

    public QuestManager(Player player) {
        this.player = player;
    }

    public boolean isEditMode() {
        return this.editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    /**
     * Returns whether this manager still belongs to the active client/server
     * lifecycle. Objective listeners use this in addition to quest identity so
     * callbacks retained by Triggers cannot mutate a superseded manager.
     */
    public boolean isActive() {
        return this.lifecycle.isActive();
    }

    /**
     * Permanently invalidates this manager generation and releases Questlog-owned
     * event listeners before the quest instances become unreachable.
     */
    public void deactivate() {
        if (!this.isActive()) return;
        this.disposeAllQuests();
        this.quests.clear();
        this.lifecycle.deactivate();
    }

    private void disposeAllQuests() {
        for (Quest quest : this.quests.values()) {
            quest.dispose();
        }
    }

    public void addQuest(Quest quest) {
        if (!this.isActive()) {
            quest.dispose();
            return;
        }
        Quest previous = this.quests.put(quest.getId(), quest);
        if (previous != null && previous != quest) {
            previous.dispose();
        }
    }

    public void clearQuests() {
        this.disposeAllQuests();
        this.quests.clear();
    }

    public void reload() {
        if (!this.isActive()) return;

        CompoundTag savedData = new CompoundTag();
        for (Quest quest : this.quests.values()) {
            savedData.put(quest.getId().toString(), quest.serialize());
        }

        this.clearQuests();
        this.createAllQuests();

        for (Quest quest : this.quests.values()) {
            if (savedData.contains(quest.getId().toString())) {
                quest.deserialize(savedData.getCompound(quest.getId().toString()));
            }
        }
    }

    /**
     * Removes a quest from the player's tracked quest list and releases its
     * Questlog-owned listeners.
     */
    public void removeQuest(ResourceLocation id) {
        Quest removed = this.quests.remove(id);
        if (removed != null) {
            removed.dispose();
        }
    }

    public Quest getQuest(ResourceLocation id) {
        return this.quests.get(id);
    }

    public boolean isClient() {
        return this.player.level().isClientSide();
    }

    /**
     * Returns all quests in insertion order (definition order).
     */
    public List<Quest> getAllQuests() {
        return new ArrayList<>(this.quests.values());
    }

    /**
     * Re-applies only the idempotent external progression rewards whose owning
     * mod remains authoritative for persistence. A completed Questlog milestone
     * may have been saved while the optional API owner was unavailable. The
     * Minion API explicitly treats an already-open tier as success, so login-time
     * reconciliation is safe and does not duplicate Minion state in Questlog.
     */
    public void reconcileExternalProgressionRewards(ServerPlayer serverPlayer) {
        if (!this.isActive() || serverPlayer == null) return;

        for (Quest quest : this.quests.values()) {
            if (!quest.isCompleted()) continue;
            for (Reward reward : quest.rewards) {
                if (reward instanceof UnlockMinionReward
                        && reward.isAutoClaim()
                        && !reward.hasRewarded()) {
                    reward.applyReward(serverPlayer);
                }
            }
        }
    }

    /**
     * This method is used to create all quests for the player.
     * It fetches all quest IDs from the cached keys and checks if the quest is already present in the player's quest list.
     * If not, it fetches the quest definition from the cache, creates a new quest instance and adds it to the player's quest list.
     */
    public void createAllQuests() {
        if (!this.isActive()) return;

        List<ResourceLocation> ids = DefinitionUtil.getCachedQuestKeys();

        for (ResourceLocation id : ids) {
            if (!this.quests.containsKey(id)) {
                JsonObject definition = DefinitionUtil.getCachedQuest(id);
                Quest quest;
                try {
                    quest = Quest.create(definition, id, this);
                    CompoundTag data = new CompoundTag();
                    quest.writeInitialData(data);
                    quest.deserialize(data);
                    this.addQuest(quest);
                } catch (Exception e) {
                    Questlog.LOGGER.error("=====================================================");
                    Questlog.LOGGER.error(" QUESTLOG ERROR: Failed to load quest '{}'", id);
                    Questlog.LOGGER.error(" The JSON file has a syntax error, typo, or missing field.");
                    Questlog.LOGGER.error(" Exception Details: ", e);
                    Questlog.LOGGER.error("=====================================================");
                    try {
                        JsonObject fallbackDef = new JsonObject();
                        fallbackDef.addProperty("title", "Broken Quest (" + id.getPath() + ")");
                        String errorMsg = e.getMessage() != null ? e.getMessage() : e.toString();
                        if (e.getCause() != null) {
                            errorMsg += "\nCaused by: " + e.getCause().getMessage();
                        }
                        fallbackDef.addProperty("description", "This quest failed to load properly. Edit it to fix errors.\n\nError details:\n" + errorMsg);
                        fallbackDef.addProperty("chapter", definition != null && definition.has("chapter") ? definition.get("chapter").getAsString() : "main");
                        quest = Quest.create(fallbackDef, id, this);
                        CompoundTag data = new CompoundTag();
                        quest.writeInitialData(data);
                        quest.deserialize(data);
                        this.addQuest(quest);
                    } catch (Exception fallbackEx) {
                        Questlog.LOGGER.error("Fallback load failed for quest '{}'", id, fallbackEx);
                    }
                }
            }
        }
    }

    /**
     * This method is used to sync all quests data to the client.
     * It iterates over all quests in the player's quest list and calls the sync method for each quest.
     */
    public void sync() {
        if (!this.isActive()) return;
        for (ResourceLocation id : this.quests.keySet()) {
            this.sync(id);
        }
    }

    /**
     * This method is used to sync specific quests data to the client.
     * If the quest does not exist, it sends a QuestRemovePacket to the client.
     * If the quest exists, it serializes the quest data and sends a QuestDataPacket to the client.
     * It also checks if the quest has been triggered or completed and sends the corresponding event to the client.
     *
     * @param id The ID of the quest to be synced.
     */
    public void sync(ResourceLocation id) {
        if (!this.isActive()) return;

        if (!this.isClient() && this.player instanceof ServerPlayer serverPlayer) {
            Questlog.LOGGER.trace("Syncing quest data for {} to client", id);
            Quest quest = this.quests.get(id);
            if (quest == null) {
                // This will only be called if a definition has been deleted while reloading the server
                Services.PLATFORM.sendPacketToClient(serverPlayer, new QuestRemovePacket(id));
                Questlog.LOGGER.warn("Quest {} not found in manager, removing from client", id);
            } else {
                boolean newlyTriggered = !quest.hasSentTrigger && quest.isTriggered();
                if (newlyTriggered) {
                    // Give specialized objectives an exact trigger boundary before
                    // the state snapshot is serialized. This is required for
                    // non-retroactive statistic baselines and remains a no-op for
                    // ordinary objectives.
                    quest.objectives.forEach(objective -> objective.onQuestTriggered());
                    quest.hasSentTrigger = true;
                }

                // Failure consequence outputs are server-authoritative and may
                // write durable world facts or other state. Apply them before the
                // outgoing snapshot so the client sees the failed quest only after
                // its persistent consequence state has crossed the same boundary.
                quest.applyFailureConsequences(serverPlayer);

                CompoundTag data = this.getQuest(id).serialize();
                Services.PLATFORM.sendPacketToClient(serverPlayer, new QuestDataPacket(id, data));
                Questlog.LOGGER.trace("Sent quest data for {} to client", id);

                if (newlyTriggered) {
                    QuestlogEvents.onQuestTriggered(new QuestEvent.Triggered(this.player, quest, true)); // This handles sending of packet
                    Questlog.LOGGER.trace("Sent quest triggered event for {}", id);
                }

                if (!quest.hasSentCompletion && quest.isCompleted()) {
                    quest.hasSentCompletion = true;
                    QuestlogEvents.onQuestCompleted(new QuestEvent.Completed(this.player, quest, true));
                    Questlog.LOGGER.trace("Sent quest completed event for {}", id);
                }

                if (quest.isGlobal() && ServerPlayerManager.INSTANCE != null) {
                    ServerPlayerManager.INSTANCE.onGlobalQuestUpdated(quest);
                }
            }
        }
    }
}
