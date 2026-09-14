package org.infernalstudios.questlog.overlord.reaction;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.client.gui.components.toasts.SystemReactionToast;

/** Client-only presentation endpoint for server-authoritative system reactions. */
public final class OverlordSystemReactionClientHandler {
    private OverlordSystemReactionClientHandler() {
    }

    public static void show(ResourceLocation reactionId) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            Questlog.LOGGER.warn("Ignoring system reaction {} because no local player is available", reactionId);
            return;
        }

        OverlordSystemReactions.Reaction reaction = OverlordSystemReactions.getDefinition(reactionId);
        if (reaction == null) {
            Questlog.LOGGER.warn("Ignoring unknown system reaction {}", reactionId);
            return;
        }

        minecraft.getToasts().addToast(new SystemReactionToast(
                Component.literal(reaction.title()),
                Component.literal(reaction.description())
        ));
    }
}
