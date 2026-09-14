package org.infernalstudios.questlog.overlord.provider;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.overlord.narrative.OverlordNarrativeState;

import java.util.Set;

/**
 * Pacifies only the protected Pillager selected as the designated Bastille's
 * fearful intermediary after the local command structure has been broken.
 *
 * This is intentionally not a global Illager AI rewrite. Other Pillagers,
 * Bastilles, patrols, raids and warbands retain their native hostility.
 */
@Mod.EventBusSubscriber(modid = Questlog.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class IllagerBastilleAudienceBridgeForge {
    public static final ResourceLocation PILLAGER = new ResourceLocation("minecraft", "pillager");
    public static final ResourceLocation CIVILIZATION = new ResourceLocation("overlord_reign", "illagers");
    public static final ResourceLocation AUTHORITY_FACT = new ResourceLocation("overlord_reign", "civilizations/illagers/authority_established");
    public static final ResourceLocation NEUTRAL = new ResourceLocation("overlord_reign", "neutral");
    public static final ResourceLocation SUBJUGATED = new ResourceLocation("overlord_reign", "subjugated");
    public static final String ANCHOR_TAG = "overlord_anchor:illager_bastille_intermediary";

    private static final Set<ResourceLocation> PEACEFUL_STATES = Set.of(NEUTRAL, SUBJUGATED);

    private IllagerBastilleAudienceBridgeForge() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onChangeTarget(LivingChangeTargetEvent event) {
        if (!(event.getNewTarget() instanceof ServerPlayer player)) return;
        if (shouldRestrain(event.getEntity(), player)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAttack(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        Entity source = event.getSource().getEntity();
        if (shouldRestrain(source, player)) {
            event.setCanceled(true);
        }
    }

    static boolean shouldRestrain(Entity source, ServerPlayer player) {
        if (!isDesignatedIntermediary(source) || player == null || source.level().isClientSide()) return false;
        if (!QuestAnchorProtection.isProtected(source)) return false;

        OverlordNarrativeState narrative = OverlordNarrativeState.get(player.server);
        if (narrative.hasFact(AUTHORITY_FACT)) return true;
        return PEACEFUL_STATES.contains(narrative.getDisposition(CIVILIZATION));
    }

    private static boolean isDesignatedIntermediary(Entity entity) {
        return entity != null
                && PILLAGER.equals(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()))
                && entity.getTags().contains(ANCHOR_TAG);
    }
}
