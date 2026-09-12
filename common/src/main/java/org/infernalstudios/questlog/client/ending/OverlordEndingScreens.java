package org.infernalstudios.questlog.client.ending;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.WinScreen;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.config.QuestlogConfig;
import org.infernalstudios.questlog.mixin.client.WinScreenAccessor;

/**
 * Client-side boundary for the future OVERLORD REIGN central ending.
 *
 * The current implementation is intentionally preview-only. Production campaign
 * activation is not inferred from End entry or Dragon defeat alone because the
 * hidden campaign may impose additional authored prerequisites before its final
 * resolution is legitimately armed.
 */
public final class OverlordEndingScreens {
    private OverlordEndingScreens() {
    }

    public static Screen replace(Screen current) {
        if (!(current instanceof WinScreen winScreen)) {
            return current;
        }

        QuestlogConfig.EndingScreen config = Questlog.getConfig().endingScreen;
        if (!config.enabled || !config.developmentPreview) {
            return current;
        }

        Minecraft minecraft = Minecraft.getInstance();
        var server = minecraft.getSingleplayerServer();
        if (!minecraft.hasSingleplayerServer() || server == null || server.isPublished()) {
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
            Questlog.LOGGER.warn("OVERLORD ending preview could not preserve the vanilla WinScreen completion callback");
            return current;
        }

        return new OverlordEndingScreen(onFinished);
    }
}
