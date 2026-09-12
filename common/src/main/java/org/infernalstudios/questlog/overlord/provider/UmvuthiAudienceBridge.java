package org.infernalstudios.questlog.overlord.provider;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.infernalstudios.questlog.overlord.narrative.OverlordNarrativeState;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

/**
 * Optional Mowzie's Mobs bridge for the designated Umvuthana Grove.
 *
 * Mowzie's Mobs is not a loader dependency. The exact installed 1.8.2 Umvuthi
 * exposes getMisbehavedPlayerId() publicly; reflection keeps that compatibility
 * surface optional while preserving the source mod's own offence tracking.
 */
public final class UmvuthiAudienceBridge {
    public static final ResourceLocation UMVUTHI = new ResourceLocation("mowziesmobs", "umvuthi");
    public static final ResourceLocation CIVILIZATION = new ResourceLocation("overlord_reign", "umvuthana");
    public static final ResourceLocation NEUTRAL = new ResourceLocation("overlord_reign", "neutral");
    public static final ResourceLocation SUBJUGATED = new ResourceLocation("overlord_reign", "subjugated");
    public static final String ANCHOR_TAG = "overlord_anchor:umvuthana_main_umvuthi";

    private static final Set<ResourceLocation> PEACEFUL_STATES = Set.of(NEUTRAL, SUBJUGATED);

    private UmvuthiAudienceBridge() {
    }

    /**
     * Peaceful provider content on the designated Umvuthi remains unavailable to
     * the exact player Mowzie's Mobs has marked as an offender. Non-Umvuthi and
     * non-anchor providers are unaffected.
     */
    public static boolean allowsProviderInteraction(Entity provider, ServerPlayer player) {
        if (!isCanonicalUmvuthi(provider) || player == null) return true;
        Boolean misbehaving = isMisbehavingPlayer(provider, player.getUUID());
        return Boolean.FALSE.equals(misbehaving);
    }

    /**
     * Returns true only when Questlog should prevent the canonical Umvuthi from
     * acquiring this player as an ordinary hostile target.
     *
     * The bridge fails closed: if the exact Mowzie misbehaviour surface cannot be
     * read, Questlog does not suppress native hostility.
     */
    public static boolean shouldSuppressPlayerTarget(Entity source, ServerPlayer player) {
        if (!isCanonicalUmvuthi(source) || player == null || source.level().isClientSide()) return false;

        ResourceLocation disposition = OverlordNarrativeState.get(player.server).getDisposition(CIVILIZATION);
        if (!PEACEFUL_STATES.contains(disposition)) return false;

        Boolean misbehaving = isMisbehavingPlayer(source, player.getUUID());
        return Boolean.FALSE.equals(misbehaving);
    }

    private static boolean isCanonicalUmvuthi(Entity entity) {
        return entity != null
                && UMVUTHI.equals(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()))
                && entity.getTags().contains(ANCHOR_TAG);
    }

    /**
     * @return true/false when the exact public 1.8.2 API was readable, null when
     *         compatibility cannot be verified and native behavior must win.
     */
    private static Boolean isMisbehavingPlayer(Entity source, UUID playerId) {
        try {
            Method method = source.getClass().getMethod("getMisbehavedPlayerId");
            Object value = method.invoke(source);
            if (value == null) return false;
            if (!(value instanceof UUID misbehavedId)) return null;
            return misbehavedId.equals(playerId);
        } catch (ReflectiveOperationException | LinkageError exception) {
            return null;
        }
    }
}
