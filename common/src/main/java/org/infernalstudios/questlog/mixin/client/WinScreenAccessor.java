package org.infernalstudios.questlog.mixin.client;

import net.minecraft.client.gui.screens.WinScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Narrow accessor for the vanilla End poem screen.
 *
 * OVERLORD QUESTS needs the exact vanilla completion callback so a development
 * replacement can return the player through Minecraft's normal post-End flow
 * instead of inventing a second world-transition path.
 */
@Mixin(WinScreen.class)
public interface WinScreenAccessor {
    @Accessor("poem")
    boolean questlog$isPoem();

    @Accessor("onFinished")
    Runnable questlog$getOnFinished();
}
