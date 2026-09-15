package org.infernalstudios.questlog.overlord.commentary;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.network.packet.GnarlCommentaryPacket;
import org.infernalstudios.questlog.platform.Services;

import java.util.Map;

/**
 * Server-authoritative bounded lifecycle commentary for authored major quests.
 *
 * This layer never changes quest state. It observes Questlog's authoritative
 * state and persists only which presentation beats have already been delivered.
 */
public final class GnarlCommentaryEngine {
    private GnarlCommentaryEngine() {
    }

    public static void onQuestTriggered(ServerPlayer player, Quest quest) {
        if (player == null || quest == null) return;
        GnarlCommentaryCatalog.Definition definition = GnarlCommentaryCatalog.get(quest.getId());
        if (definition == null) return;

        MinecraftServer server = player.getServer();
        if (server == null) return;
        long now = server.overworld().getGameTime();
        GnarlCommentaryState state = GnarlCommentaryState.get(server);
        GnarlCommentaryState.QuestState questState = state.reset(player.getUUID(), quest.getId());
        questState.initialized = true;
        questState.active = true;
        questState.completedObjectives = completedRequiredObjectives(quest);
        questState.clarificationDueTick = now + definition.timing().clarificationDelayTicks();
        questState.branchDueTick = now + definition.timing().branchFramingDelayTicks();
        questState.nextReminderTick = now + definition.timing().firstReminderDelayTicks();
        questState.warningDueTick = definition.warning().isBlank()
                ? 0L
                : now + definition.timing().warningDelayTicks();
        state.touch();
    }

    public static void onQuestCompleted(ServerPlayer player, Quest quest) {
        if (player == null || quest == null) return;
        GnarlCommentaryCatalog.Definition definition = GnarlCommentaryCatalog.get(quest.getId());
        if (definition == null) return;

        MinecraftServer server = player.getServer();
        if (server == null) return;
        long now = server.overworld().getGameTime();
        GnarlCommentaryState state = GnarlCommentaryState.get(server);
        GnarlCommentaryState.QuestState questState = state.getOrCreate(player.getUUID(), quest.getId());
        questState.initialized = true;
        questState.active = false;
        questState.completedObjectives = completedRequiredObjectives(quest);
        if (!questState.successShown) {
            send(player, definition.success());
            questState.successShown = true;
        }
        if (!definition.postQuest().isBlank()) {
            questState.postDueTick = now + definition.timing().postQuestDelayTicks();
        } else {
            questState.postShown = true;
        }
        state.touch();
    }

    /** Called from the loader bridge on server tick. Work runs once per second. */
    public static void tick(MinecraftServer server) {
        if (server == null || server.overworld().getGameTime() % 20L != 0L) return;
        if (ServerPlayerManager.INSTANCE == null) return;

        long now = server.overworld().getGameTime();
        GnarlCommentaryState state = GnarlCommentaryState.get(server);
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(player);
            if (manager == null || !manager.isActive()) continue;
            tickPlayer(player, manager, state, now);
        }
    }

    private static void tickPlayer(ServerPlayer player, QuestManager manager, GnarlCommentaryState state, long now) {
        for (ResourceLocation questId : GnarlCommentaryCatalog.questIds()) {
            Quest quest = manager.getQuest(questId);
            if (quest == null) continue;
            GnarlCommentaryCatalog.Definition definition = GnarlCommentaryCatalog.get(questId);
            if (definition == null) continue;

            GnarlCommentaryState.QuestState questState = state.get(player.getUUID(), questId);
            if (questState == null || !questState.initialized) {
                baselineExistingQuest(state, player, quest, definition, now);
                continue;
            }

            if (quest.isFailed() && !questState.failureShown) {
                questState.active = false;
                questState.failureShown = true;
                send(player, definition.failure());
                state.touch();
                return;
            }

            if (quest.isCompleted() && !questState.successShown) {
                questState.active = false;
                questState.successShown = true;
                questState.completedObjectives = completedRequiredObjectives(quest);
                send(player, definition.success());
                if (!definition.postQuest().isBlank()) {
                    questState.postDueTick = now + definition.timing().postQuestDelayTicks();
                } else {
                    questState.postShown = true;
                }
                state.touch();
                return;
            }

            if (quest.isCompleted() && questState.successShown && !questState.postShown
                    && questState.postDueTick > 0L && now >= questState.postDueTick) {
                questState.postShown = true;
                send(player, definition.postQuest());
                state.touch();
                return;
            }

            if (!questState.active || !quest.isTriggered() || quest.isCompleted() || quest.isFailed()) continue;

            int completed = completedRequiredObjectives(quest);
            if (completed > questState.completedObjectives) {
                String update = latestObjectiveUpdate(definition, questState.completedObjectives, completed);
                questState.completedObjectives = completed;
                questState.nextReminderTick = now + definition.timing().repeatReminderDelayTicks();
                if (!update.isBlank()) {
                    send(player, update);
                    state.touch();
                    return;
                }
                state.touch();
            }

            if (!questState.clarificationShown && now >= questState.clarificationDueTick) {
                questState.clarificationShown = true;
                if (!definition.objectiveClarification().isBlank()) {
                    send(player, definition.objectiveClarification());
                    state.touch();
                    return;
                }
                state.touch();
            }

            if (!questState.branchShown && now >= questState.branchDueTick) {
                questState.branchShown = true;
                if (!definition.branchFraming().isBlank()) {
                    send(player, definition.branchFraming());
                    state.touch();
                    return;
                }
                state.touch();
            }

            if (!questState.warningShown && questState.warningDueTick > 0L && now >= questState.warningDueTick) {
                questState.warningShown = true;
                if (!definition.warning().isBlank()) {
                    send(player, definition.warning());
                    state.touch();
                    return;
                }
                state.touch();
            }

            if (questState.nextReminderTick > 0L && now >= questState.nextReminderTick) {
                String reminder = nextReminder(definition, questState.remindersShown);
                if (!reminder.isBlank()) {
                    questState.remindersShown++;
                    questState.nextReminderTick = hasAnotherReminder(definition, questState.remindersShown)
                            ? now + definition.timing().repeatReminderDelayTicks()
                            : 0L;
                    send(player, reminder);
                    state.touch();
                    return;
                }
                questState.nextReminderTick = 0L;
                state.touch();
            }
        }
    }

    private static void baselineExistingQuest(
            GnarlCommentaryState state,
            ServerPlayer player,
            Quest quest,
            GnarlCommentaryCatalog.Definition definition,
            long now
    ) {
        GnarlCommentaryState.QuestState questState = state.getOrCreate(player.getUUID(), quest.getId());
        questState.initialized = true;
        questState.completedObjectives = completedRequiredObjectives(quest);

        if (quest.isCompleted()) {
            questState.active = false;
            questState.clarificationShown = true;
            questState.branchShown = true;
            questState.warningShown = true;
            questState.successShown = true;
            questState.postShown = true;
        } else if (quest.isFailed()) {
            questState.active = false;
            questState.clarificationShown = true;
            questState.branchShown = true;
            questState.warningShown = true;
            questState.failureShown = true;
        } else if (quest.isTriggered()) {
            // Migration-safe behavior: do not replay old introductions. Existing
            // active quests enter only the bounded reminder/update schedule.
            questState.active = true;
            questState.clarificationShown = true;
            questState.branchShown = true;
            questState.nextReminderTick = now + definition.timing().firstReminderDelayTicks();
            questState.warningDueTick = definition.warning().isBlank()
                    ? 0L
                    : now + definition.timing().warningDelayTicks();
        }
        state.touch();
    }

    private static int completedRequiredObjectives(Quest quest) {
        int completed = 0;
        for (Objective objective : quest.objectives) {
            if (!objective.isOptional() && objective.isCompleted()) completed++;
        }
        return completed;
    }

    private static String latestObjectiveUpdate(GnarlCommentaryCatalog.Definition definition, int oldCount, int newCount) {
        String latest = "";
        for (Map.Entry<Integer, String> entry : definition.objectiveUpdates().entrySet()) {
            if (entry.getKey() > oldCount && entry.getKey() <= newCount && !entry.getValue().isBlank()) {
                latest = entry.getValue();
            }
        }
        return latest;
    }

    private static String nextReminder(GnarlCommentaryCatalog.Definition definition, int remindersShown) {
        if (remindersShown == 0) return definition.firstReminder();
        int repeatIndex = remindersShown - 1;
        return repeatIndex >= 0 && repeatIndex < definition.repeatReminders().size()
                ? definition.repeatReminders().get(repeatIndex)
                : "";
    }

    private static boolean hasAnotherReminder(GnarlCommentaryCatalog.Definition definition, int remindersShown) {
        if (remindersShown == 0) return !definition.firstReminder().isBlank();
        return remindersShown - 1 < definition.repeatReminders().size();
    }

    private static void send(ServerPlayer player, String message) {
        if (message == null || message.isBlank()) return;
        Services.PLATFORM.sendPacketToClient(player, new GnarlCommentaryPacket(message));
    }
}
