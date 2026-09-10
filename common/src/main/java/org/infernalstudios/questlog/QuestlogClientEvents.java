package org.infernalstudios.questlog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import org.infernalstudios.questlog.client.gui.components.toasts.QuestAddedToast;
import org.infernalstudios.questlog.client.gui.components.toasts.QuestCompletedToast;
import org.infernalstudios.questlog.client.gui.screen.QuestDetails;
import org.infernalstudios.questlog.client.gui.screen.QuestlogScreen;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.event.events.QuestEvent;
import org.infernalstudios.questlog.network.ClientPacketHandler;

import java.util.ArrayList;
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
        QuestlogClient.destroyLocal();
        Questlog.EVENTS.removeAllListeners();
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
            if (QuestToastState.queuedPopups.stream().noneMatch(quest -> quest.getId().equals(event.quest.getId()))) {
                QuestToastState.queuedPopups.add(event.quest);
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

        // Re-check the runtime scope at consumption time. A popup queued in a
        // private integrated-server session must not later open after that world
        // has been published to LAN.
        if (!isLocalSingleplayerPopupSession()) {
            for (Quest queuedQuest : QuestToastState.queuedPopups) {
                if (queuedQuest.getDisplay().shouldToastOnUnlock()) {
                    QuestToastState.addedToasts.add(new QuestAddedToast(queuedQuest.getDisplay()));
                }
            }
            QuestToastState.queuedPopups.clear();
            QuestToastState.resetCheckDelay();
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen instanceof QuestDetails) {
            QuestToastState.resetCheckDelay();
            return;
        }

        if (minecraft.screen instanceof MenuAccess<?>) {
            // Opening a full-screen popup over a live container closes the
            // server-side menu. Returning to the old screen afterward can leave
            // a stale client menu, even when the cursor is not carrying a stack.
            // Wait until the container screen is closed instead.
            QuestToastState.resetCheckDelay();
            return;
        }

        Quest queuedQuest = QuestToastState.queuedPopups.remove(0);
        Quest currentQuest = QuestlogClient.getLocal().getQuest(queuedQuest.getId());
        if (currentQuest == null || !currentQuest.isTriggered()) {
            // Definitions and progress can be reloaded while a popup is waiting.
            // Never open a stale or reset quest instance from the queue.
            QuestToastState.resetCheckDelay();
            return;
        }

        minecraft.setScreen(new QuestDetails(minecraft.screen, currentQuest));
        QuestToastState.resetCheckDelay();
    }

    private static void displayQueuedToasts() {
        if (Minecraft.getInstance().screen instanceof QuestDetails) {
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
        public static List<Quest> queuedPopups = new ArrayList<>();

        public static void resetCheckDelay() {
            QuestToastState.tickDelayForCheck = 10;
        }
    }
}
