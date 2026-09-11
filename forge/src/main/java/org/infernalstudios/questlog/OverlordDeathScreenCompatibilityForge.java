package org.infernalstudios.questlog;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import org.infernalstudios.questlog.client.death.OverlordDeathScreenHooks;

import java.lang.reflect.Method;

/** Optional Forge-side revival compatibility without hard dependencies. */
public final class OverlordDeathScreenCompatibilityForge {
    private OverlordDeathScreenCompatibilityForge() {
    }

    public static void initialize() {
        if (ModList.get().isLoaded("hardcorerevival")) {
            try {
                Method knockedOut = Class.forName("net.blay09.mods.hardcorerevival.PlayerHardcoreRevivalManager")
                        .getMethod("isKnockedOut", Player.class);
                OverlordDeathScreenHooks.registerBlocker(
                        "hardcorerevival",
                        player -> invokeBoolean(knockedOut, null, player)
                );
            } catch (LinkageError | ReflectiveOperationException error) {
                unavailable("hardcorerevival", error);
            }
        }

        if (ModList.get().isLoaded("playerrevive")) {
            try {
                Method getBleeding = Class.forName("team.creative.playerrevive.server.PlayerReviveServer")
                        .getMethod("getBleeding", Player.class);
                Method isBleeding = Class.forName("team.creative.playerrevive.api.IBleeding")
                        .getMethod("isBleeding");
                OverlordDeathScreenHooks.registerBlocker("playerrevive", player -> {
                    try {
                        Object bleeding = getBleeding.invoke(null, player);
                        return invokeBoolean(isBleeding, bleeding);
                    } catch (ReflectiveOperationException error) {
                        throw new IllegalStateException(error);
                    }
                });
            } catch (LinkageError | ReflectiveOperationException error) {
                unavailable("playerrevive", error);
            }
        }
    }

    private static boolean invokeBoolean(Method method, Object target, Object... args) {
        try {
            return Boolean.TRUE.equals(method.invoke(target, args));
        } catch (ReflectiveOperationException error) {
            throw new IllegalStateException(error);
        }
    }

    private static void unavailable(String modId, Throwable error) {
        Questlog.LOGGER.warn(
                "Overlord death screen: {} revival API unavailable; leaving its death flow untouched",
                modId, error
        );
        OverlordDeathScreenHooks.registerBlocker(modId, player -> true);
    }
}
