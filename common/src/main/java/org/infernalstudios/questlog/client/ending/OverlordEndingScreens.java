package org.infernalstudios.questlog.client.ending;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.WinScreen;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.config.QuestlogConfig;
import org.infernalstudios.questlog.mixin.client.WinScreenAccessor;
import org.infernalstudios.questlog.network.packet.OverlordEndingPresentedPacket;
import org.infernalstudios.questlog.platform.Services;

/**
 * Client-side boundary for the OVERLORD REIGN central ending.
 *
 * Final presentation content is still a neutral scaffold. Production activation
 * is fail-closed unless the server projects the explicit ending-arm campaign fact.
 */
public final class OverlordEndingScreens {
    private static boolean serverArmed;
    private static boolean serverPresented;
    private static boolean directPresentationRequested;

    private OverlordEndingScreens() {
    }

    public static void updateServerState(boolean armed, boolean presented, boolean requestDirectPresentation) {
        serverArmed = armed;
        serverPresented = presented;
        if (!armed || presented) {
            directPresentationRequested = false;
        } else if (requestDirectPresentation) {
            directPresentationRequested = true;
        }
    }

    public static void resetClientState() {
        serverArmed = false;
        serverPresented = false;
        directPresentationRequested = false;
    }

    /**
     * Sequence-break delivery path. If the campaign becomes armed after the
     * Dragon was already defeated, there may be no future vanilla End poem to
     * intercept. Wait for screenless gameplay, then show the same scaffold
     * directly without replaying or modifying the Dragon fight.
     */
    public static void tick() {
        if (!directPresentationRequested || !serverArmed || serverPresented) {
            return;
        }

        QuestlogConfig.EndingScreen config = Questlog.getConfig().endingScreen;
        Minecraft minecraft = Minecraft.getInstance();
        if (!config.enabled || !isLocalSingleplayerSession(minecraft) || minecraft.screen != null) {
            return;
        }

        directPresentationRequested = false;
        minecraft.setScreen(new OverlordEndingScreen(() -> {
            acknowledgePresentation();
            Minecraft.getInstance().setScreen(null);
        }));
    }

    public static Screen replace(Screen current) {
        if (!(current instanceof WinScreen winScreen)) {
            return current;
        }

        QuestlogConfig.EndingScreen config = Questlog.getConfig().endingScreen;
        boolean productionArmed = serverArmed && !serverPresented;
        if (!config.enabled || (!config.developmentPreview && !productionArmed)) {
            return current;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (!isLocalSingleplayerSession(minecraft)) {
            return current;
        }

        WinScreenAccessor accessor = (WinScreenAccessor) (Object) winScreen;
        if (!accessor.questlog$isPoem()) {
            // The same vanilla screen class is also used for manually opened
            // credits. Those are not a campaign-ending event.
            return current;
        }

        Runnable onFinished = accessor.questlog$getOnFinished();
        if (onFinished == null) {
            Questlog.LOGGER.warn("OVERLORD ending screen could not preserve the vanilla WinScreen completion callback");
            return current;
        }

        if (!productionArmed) {
            return new OverlordEndingScreen(onFinished);
        }

        return new OverlordEndingScreen(() -> {
            acknowledgePresentation();
            onFinished.run();
        });
    }

    private static boolean isLocalSingleplayerSession(Minecraft minecraft) {
        var server = minecraft.getSingleplayerServer();
        return minecraft.hasSingleplayerServer() && server != null && !server.isPublished();
    }

    private static void acknowledgePresentation() {
        if (!serverArmed || serverPresented) {
            return;
        }
        serverPresented = true;
        directPresentationRequested = false;
        Services.PLATFORM.sendPacketToServer(new OverlordEndingPresentedPacket());
    }
}
