package org.infernalstudios.questlog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.infernalstudios.questlog.client.gui.components.toasts.QuestAddedToast;
import org.infernalstudios.questlog.client.gui.components.toasts.QuestCompletedToast;
import org.infernalstudios.questlog.client.gui.screen.OverlordSpeakerScreen;
import org.infernalstudios.questlog.client.gui.screen.QuestDetails;
import org.infernalstudios.questlog.client.gui.screen.QuestlogScreen;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.event.events.QuestEvent;
import org.infernalstudios.questlog.network.ClientPacketHandler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class QuestlogClientEvents {
    public static Quest mostRecentNotificationQuest = null;

    public static void onClientTick() {
        if (QuestToastState.tickDelayForCheck >= 0) {
            QuestToastState.tickDelayForCheck--;
        }

        if (QuestToastState.tickDelayForCheck == 0) {
            QuestlogClientEvents.displayQueuedPopups();
            QuestlogClientEvents.displayQueuedToasts();
        }

        Minecraft minecraft = Minecraft.getInstance();

        while (QuestlogClient.OPEN_SCREEN_KEY.consumeClick()) {
            if (minecraft.isWindowActive()) {
                LocalPlayer player = minecraft.player;
                if (player != null) {
                    minecraft.setScreen(new QuestlogScreen(minecraft.screen));
                }
            }
        }
    }

    public static void onClientPlayerLogin() {
        QuestlogClient.getLocal();
        ClientPacketHandler.handleDeferredDefinitions();
        ClientPacketHandler.handleDeferredSync();
    }

    public static void onClientPlayerLogout() {
        QuestlogClient.isEditModeActive = false;
        ClientPacketHandler.clearDeferredState();
        QuestlogClient.destroyLocal();
        // DefinitionUtil's caches and Questlog.EVENTS are static and therefore
        // shared with the integrated server in single-player. Client quest objects
        // never register the private Objective listeners, so the client logout hook
        // has nothing to clear from Questlog.EVENTS. Clearing it here could remove
        // the still-running integrated server's Read/QuestComplete listeners during
        // shutdown ordering. Server lifecycle cleanup owns that event bus.
        QuestlogClient.ALL_ADVANCEMENTS = new ArrayList<>();
        QuestToastState.addedToasts.clear();
        QuestToastState.completedToasts.clear();
        QuestToastState.queuedPopups.clear();
        QuestToastState.tickDelayForCheck = -1;
        mostRecentNotificationQuest = null;
    }

    public static void onQuestTriggered(QuestEvent.Triggered event) {
        if (event.quest == null) {
            Questlog.LOGGER.warn("Ignoring quest trigger notification with no quest instance");
            return;
        }

        mostRecentNotificationQuest = event.quest;
        if (event.quest.getDisplay().shouldShowPopupOnUnlock() && isLocalSingleplayerPopupSession()) {
            QuestToastState.resetCheckDelay();
            ResourceLocation questId = event.quest.getId();
            if (!QuestToastState.queuedPopups.contains(questId)) {
                QuestToastState.queuedPopups.addLast(questId);
            }
        } else if (event.quest.getDisplay().shouldToastOnUnlock()) {
            QuestToastState.resetCheckDelay();
            QuestToastState.addedToasts.add(new QuestAddedToast(event.quest.getDisplay()));
        }

        // Unlock audio belongs to the trigger event itself. The popup may be
        // displayed several ticks later, so replaying the same sound when the
        // screen opens would produce a duplicate cue for popup quests.
        SoundEvent triggeredSound = event.quest.getDisplay().getTriggeredSound();
        if (triggeredSound != null) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(triggeredSound, 1, 1));
        }
    }

    /**
     * OVERLORD REIGN is a single-player project. Automatic full-screen quest
     * popups are therefore intentionally limited to the local unpublished
     * integrated server. LAN-published and dedicated multiplayer sessions do not
     * enter or consume the popup queue.
     */
    private static boolean isLocalSingleplayerPopupSession() {
        Minecraft minecraft = Minecraft.getInstance();
        var server = minecraft.getSingleplayerServer();
        return minecraft.hasSingleplayerServer() && server != null && !server.isPublished();
    }

    public static void onQuestCompleted(QuestEvent.Completed event) {
        if (event.quest == null) {
            Questlog.LOGGER.warn("Ignoring quest completion notification with no quest instance");
            return;
        }

        mostRecentNotificationQuest = event.quest;
        if (event.quest.getDisplay().shouldToastOnComplete()) {
            QuestToastState.resetCheckDelay();
            QuestToastState.completedToasts.add(new QuestCompletedToast(event.quest.getDisplay()));
        }

        SoundEvent completedSound = event.quest.getDisplay().getCompletedSound();
        if (completedSound != null) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(completedSound, 1, 1));
        }

        for (Reward reward : event.quest.rewards) {
            if (reward.isAutoClaim()) {
                SoundEvent sound = reward.getDisplay().getClaimSound();
                if (sound != null) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, 1, 1));
                }
            }
        }
    }

    private static void displayQueuedPopups() {
        if (QuestToastState.queuedPopups.isEmpty()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            // A disconnect can race the normal logout callback by a client tick.
            // Do not recreate a local QuestManager or resolve queued quest IDs once
            // the connection has already lost its player. Logout owns final cleanup.
            QuestToastState.queuedPopups.clear();
            QuestToastState.tickDelayForCheck = -1;
            return;
        }

        // The queue stores IDs rather than Quest objects. Definitions can be
        // replaced while a popup waits, so every consumption path must resolve
        // the current quest instance and current display data from the manager.
        if (!isLocalSingleplayerPopupSession()) {
            for (ResourceLocation queuedQuestId : QuestToastState.queuedPopups) {
                Quest currentQuest = QuestlogClient.getLocal().getQuest(queuedQuestId);
                if (currentQuest != null
                        && currentQuest.isTriggered()
                        && currentQuest.getDisplay().shouldToastOnUnlock()) {
                    QuestToastState.addedToasts.add(new QuestAddedToast(currentQuest.getDisplay()));
                }
            }
            QuestToastState.queuedPopups.clear();
            QuestToastState.resetCheckDelay();
            return;
        }

        if (minecraft.screen != null) {
            // Automatic full-screen presentation must not replace an inventory,
            // container, chat, editor, or other active screen. Replacing live
            // screens can close server menus, discard typed input, or leave a
            // stale previous-screen reference. Retry after normal gameplay resumes.
            QuestToastState.resetCheckDelay();
            return;
        }

        ResourceLocation queuedQuestId = QuestToastState.queuedPopups.pollFirst();
        if (queuedQuestId == null) {
            return;
        }
        Quest currentQuest = QuestlogClient.getLocal().getQuest(queuedQuestId);
        if (currentQuest == null || !currentQuest.isTriggered()) {
            // Definitions and progress can be reloaded while a popup is waiting.
            // Never open a removed or reset quest from an obsolete queue entry.
            QuestToastState.resetCheckDelay();
            return;
        }

        if (currentQuest.getDisplay().hasSpeakerPresentation()) {
            minecraft.setScreen(new OverlordSpeakerScreen(currentQuest));
        } else {
            minecraft.setScreen(new QuestDetails(null, currentQuest));
        }
        QuestToastState.resetCheckDelay();
    }

    private static void displayQueuedToasts() {
        if (Minecraft.getInstance().screen instanceof QuestDetails
                || Minecraft.getInstance().screen instanceof OverlordSpeakerScreen) {
            return;
        }

        ToastComponent toasts = Minecraft.getInstance().getToasts();
        if (!QuestToastState.completedToasts.isEmpty()) {
            for (QuestCompletedToast toast : QuestToastState.completedToasts) {
                toasts.addToast(toast);
            }
            QuestToastState.completedToasts.clear();
        }

        if (toasts.getToast(QuestCompletedToast.class, Toast.NO_TOKEN) != null) {
            QuestToastState.resetCheckDelay();
            return;
        }

        if (!QuestToastState.addedToasts.isEmpty()) {
            for (QuestAddedToast toast : QuestToastState.addedToasts) {
                toasts.addToast(toast);
            }
            QuestToastState.addedToasts.clear();
        }
    }

    private static class QuestToastState {
        public static int tickDelayForCheck = -1;
        public static List<QuestAddedToast> addedToasts = new ArrayList<>();
        public static List<QuestCompletedToast> completedToasts = new ArrayList<>();
        public static Deque<ResourceLocation> queuedPopups = new ArrayDeque<>();

        public static void resetCheckDelay() {
            QuestToastState.tickDelayForCheck = 10;
        }
    }
}
