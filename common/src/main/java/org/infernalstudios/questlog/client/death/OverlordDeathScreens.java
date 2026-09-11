package org.infernalstudios.questlog.client.death;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import org.infernalstudios.questlog.Questlog;

/** Client lifecycle and exact-vanilla-screen replacement gate. */
public final class OverlordDeathScreens {
    private static Screen checkedVanillaScreen;

    private OverlordDeathScreens() {
    }

    public static void initialize() {
        checkedVanillaScreen = null;
    }

    public static void tick() {
        Minecraft minecraft = Minecraft.getInstance();
        Screen current = minecraft.screen;
        if (current == null || current.getClass() != DeathScreen.class) {
            checkedVanillaScreen = null;
            return;
        }

        if (current != checkedVanillaScreen
                && minecraft.player != null
                && minecraft.player.isDeadOrDying()
                && !OverlordDeathScreenHooks.isBlocked(minecraft.player)) {
            checkedVanillaScreen = current;
            Screen replacement = replace(current);
            if (replacement != current) {
                minecraft.setScreen(replacement);
            }
        }
    }

    /** Replace only the exact vanilla DeathScreen. Subclasses belong to their own mods. */
    public static Screen replace(Screen next) {
        Minecraft minecraft = Minecraft.getInstance();
        if (next == null
                || next.getClass() != DeathScreen.class
                || minecraft.player == null
                || minecraft.level == null
                || OverlordDeathScreenHooks.isBlocked(minecraft.player)) {
            return next;
        }

        if (!Questlog.getConfig().deathScreen.enabled) {
            return next;
        }

        DamageSource source = minecraft.player.getLastDamageSource();
        Component cause = source == null
                ? minecraft.player.getCombatTracker().getDeathMessage()
                : source.getLocalizedDeathMessage(minecraft.player);
        boolean hardcore = minecraft.level.getLevelData().isHardcore();
        return new OverlordDeathScreen(cause, hardcore);
    }
}
