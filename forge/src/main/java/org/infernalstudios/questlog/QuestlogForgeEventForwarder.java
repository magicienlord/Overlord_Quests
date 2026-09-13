package org.infernalstudios.questlog;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.infernalstudios.questlog.client.death.OverlordDeathScreens;
import org.infernalstudios.questlog.client.ending.OverlordEndingScreens;
import org.infernalstudios.questlog.commands.OverlordNarrativeCommands;
import org.infernalstudios.questlog.overlord.provider.QuestAnchorProtection;
import org.infernalstudios.questlog.overlord.provider.QuestProviderInteraction;
import org.infernalstudios.questlog.overlord.provider.UmvuthiAudienceBridge;

public class QuestlogForgeEventForwarder {
    @SubscribeEvent
    public static void onServerStart(ServerStartingEvent event) {
        QuestlogEvents.onServerStart(event.getServer());
    }

    @SubscribeEvent
    public static void onPlayerSave(PlayerEvent.SaveToFile event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            QuestlogEvents.onPlayerSave(player);
        }
    }

    @SubscribeEvent
    public static void onServerStop(ServerStoppingEvent event) {
        QuestlogEvents.onServerStop();
    }

    @SubscribeEvent
    public static void onServerPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            QuestlogEvents.onServerPlayerLogin(player);
        }
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        QuestlogEvents.registerCommands(event.getDispatcher());
        OverlordNarrativeCommands.register(event.getDispatcher());
    }

    /**
     * A deliberately marked quest anchor is protected from ordinary damage while
     * its opt-in scoreboard tag remains present. Campaign/world integration owns
     * removal of that tag when an authored destructive route makes the NPC
     * intentionally killable.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onQuestAnchorAttack(LivingAttackEvent event) {
        if (!event.getEntity().level().isClientSide() && QuestAnchorProtection.isProtected(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    /**
     * After the designated Umvuthi has entered an authored peaceful political
     * state, suppress only his ordinary player-target acquisition. Mowzie's own
     * misbehaviour tracking remains authoritative, so a player it has explicitly
     * marked as an offender can still become a native combat target.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getNewTarget() instanceof ServerPlayer player)) return;
        if (UmvuthiAudienceBridge.shouldSuppressPlayerTarget(event.getEntity(), player)) {
            event.setCanceled(true);
        }
    }

    /**
     * Protected Mob anchors also become persistent whenever they load so an
     * explicitly authored quest giver cannot silently disappear through normal
     * despawn rules. This does not affect unmarked procedural mobs.
     */
    @SubscribeEvent
    public static void onQuestAnchorJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            QuestAnchorProtection.applyPersistence(event.getEntity());
        }
    }

    /**
     * Temporary non-invasive provider interaction scaffold. Sneak + main-hand
     * interaction avoids stealing ordinary villager trading or another mod's
     * entity interaction while the final quest-provider presentation is pending.
     */
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
                || event.getHand() != InteractionHand.MAIN_HAND
                || !player.isShiftKeyDown()) {
            return;
        }

        if (QuestProviderInteraction.sendMenu(player, event.getTarget(), false)) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            QuestlogClientEvents.onClientTick();
            OverlordDeathScreens.tick();
            OverlordEndingScreens.tick();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @OnlyIn(Dist.CLIENT)
    public static void onScreenOpening(ScreenEvent.Opening event) {
        var current = event.getNewScreen();
        var replacement = OverlordDeathScreens.replace(current);
        replacement = OverlordEndingScreens.replace(replacement);
        if (replacement != current) {
            event.setNewScreen(replacement);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        QuestlogClientEvents.onClientPlayerLogin();
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        OverlordEndingScreens.resetClientState();
        QuestlogClientEvents.onClientPlayerLogout();
    }
}
