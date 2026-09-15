package org.infernalstudios.questlog.overlord.commentary;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.infernalstudios.questlog.client.gui.components.toasts.SystemReactionToast;

/** Client-only presentation for lightweight Gnarl lifecycle commentary. */
public final class GnarlCommentaryClientHandler {
    private GnarlCommentaryClientHandler() {
    }

    public static void show(String message) {
        if (message == null || message.isBlank()) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null) return;
        minecraft.getToasts().addToast(new SystemReactionToast(
                Component.literal("Gnarl"),
                Component.literal(message)
        ));
    }
}
