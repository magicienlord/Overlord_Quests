package org.infernalstudios.questlog.overlord.nightwalker;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * Narrow read-only bridge to the stable player-state surface exposed by the
 * supplied Overlord NightWalker / Nycto alpha.3 build.
 *
 * Questlog deliberately does not link against Nycto classes. The compatibility
 * contract is the packaged persistent player data owned by Nycto itself:
 * `Nycto.vampire` identifies a completed transformation and `Nycto.powerMask`
 * records the thirteen choosable Vampire Altar powers. Registry presence of the
 * real `nycto:vampirism` effect prevents stale NBT from impersonating an installed
 * NightWalker runtime.
 */
public final class NightwalkerState {
    static final String ROOT = "Nycto";
    static final String VAMPIRE_KEY = "vampire";
    static final String POWER_MASK_KEY = "powerMask";
    static final int CHOOSABLE_POWER_COUNT = 13;
    static final int CHOOSABLE_POWER_MASK = (1 << CHOOSABLE_POWER_COUNT) - 1;

    private static final ResourceLocation VAMPIRISM_EFFECT = new ResourceLocation("nycto", "vampirism");

    private NightwalkerState() {
    }

    public static boolean isAvailable() {
        return BuiltInRegistries.MOB_EFFECT.containsKey(VAMPIRISM_EFFECT);
    }

    public static boolean isVampire(ServerPlayer player) {
        if (player == null || !isAvailable()) return false;
        CompoundTag root = root(player);
        return root != null && root.getBoolean(VAMPIRE_KEY);
    }

    public static int purchasedPowerCount(ServerPlayer player) {
        if (player == null || !isAvailable()) return 0;
        CompoundTag root = root(player);
        if (root == null) return 0;
        return Integer.bitCount(root.getInt(POWER_MASK_KEY) & CHOOSABLE_POWER_MASK);
    }

    private static CompoundTag root(ServerPlayer player) {
        CompoundTag persistent = player.getPersistentData();
        if (!persistent.contains(ROOT, Tag.TAG_COMPOUND)) return null;
        return persistent.getCompound(ROOT);
    }
}
