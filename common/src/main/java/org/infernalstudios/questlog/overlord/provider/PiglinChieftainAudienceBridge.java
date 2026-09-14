package org.infernalstudios.questlog.overlord.provider;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import org.infernalstudios.questlog.overlord.narrative.OverlordNarrativeState;

import java.util.Set;

/**
 * Narrow vanilla Piglin bridge for the designated local Brute Chieftain.
 *
 * Gold grants only enough restraint for a political audience. It does not alter
 * ordinary Piglins, ordinary Piglin Brutes, or the civilization's political
 * disposition. A later authored neutral or subjugated state may also sustain a
 * peaceful audience without gold while the exact Chieftain remains protected.
 */
public final class PiglinChieftainAudienceBridge {
    public static final ResourceLocation PIGLIN_BRUTE = new ResourceLocation("minecraft", "piglin_brute");
    public static final ResourceLocation CIVILIZATION = new ResourceLocation("overlord_reign", "piglins");
    public static final ResourceLocation NEUTRAL = new ResourceLocation("overlord_reign", "neutral");
    public static final ResourceLocation SUBJUGATED = new ResourceLocation("overlord_reign", "subjugated");
    public static final String ANCHOR_TAG = "overlord_anchor:piglin_main_chieftain";

    private static final Set<ResourceLocation> PEACEFUL_STATES = Set.of(NEUTRAL, SUBJUGATED);

    private PiglinChieftainAudienceBridge() {
    }

    public static boolean allowsProviderInteraction(Entity provider, ServerPlayer player) {
        if (!isDesignatedChieftain(provider) || player == null) return true;
        if (!QuestAnchorProtection.isProtected(provider)) return false;
        return isWearingGold(player) || hasPeacefulDisposition(player);
    }

    public static boolean shouldSuppressPlayerTarget(Entity source, ServerPlayer player) {
        if (!isDesignatedChieftain(source) || player == null || source.level().isClientSide()) return false;
        if (!QuestAnchorProtection.isProtected(source)) return false;
        return isWearingGold(player) || hasPeacefulDisposition(player);
    }

    private static boolean isDesignatedChieftain(Entity entity) {
        return entity != null
                && PIGLIN_BRUTE.equals(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()))
                && entity.getTags().contains(ANCHOR_TAG);
    }

    private static boolean isWearingGold(ServerPlayer player) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.getItem() instanceof ArmorItem armor && armor.getMaterial() == ArmorMaterials.GOLD) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasPeacefulDisposition(ServerPlayer player) {
        ResourceLocation disposition = OverlordNarrativeState.get(player.server).getDisposition(CIVILIZATION);
        return PEACEFUL_STATES.contains(disposition);
    }
}
