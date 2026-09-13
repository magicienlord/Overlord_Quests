package org.infernalstudios.questlog.overlord.ending;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.network.packet.OverlordEndingStatePacket;
import org.infernalstudios.questlog.overlord.narrative.OverlordNarrativeState;
import org.infernalstudios.questlog.platform.Services;

/**
 * Server-authoritative bridge between campaign state and the client ending surface.
 *
 * The campaign owns when ENDING_ARMED_FACT becomes true. Dragon defeat remains
 * Minecraft-owned world state. The client receives only the minimum derived state
 * needed to decide whether a vanilla End poem may be replaced or a prior Dragon
 * defeat needs the direct sequence-break presentation path.
 */
public final class OverlordEndingActivation {
    public static final ResourceLocation ENDING_ARMED_FACT = new ResourceLocation(
            "overlord_reign",
            "campaign/ending_armed"
    );

    private OverlordEndingActivation() {
    }

    public static void onPlayerLogin(ServerPlayer player) {
        if (player == null) return;
        sync(player, true);
    }

    public static void onNarrativeFactChanged(MinecraftServer server, ResourceLocation fact) {
        if (server == null || !ENDING_ARMED_FACT.equals(fact)) {
            return;
        }
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            sync(player, true);
        }
    }

    public static void acknowledgePresentation(ServerPlayer player) {
        if (player == null) return;

        MinecraftServer server = player.server;
        if (!isArmed(server)) {
            Questlog.LOGGER.warn("Ignoring OVERLORD ending acknowledgement because the campaign ending is not armed");
            sync(player, false);
            return;
        }
        if (!hasDragonBeenDefeated(server)) {
            Questlog.LOGGER.warn("Ignoring OVERLORD ending acknowledgement because the Ender Dragon has not been defeated");
            sync(player, false);
            return;
        }

        OverlordEndingPresentationState.get(server).markPresented();
        sync(player, false);
    }

    public static void sync(ServerPlayer player, boolean requestDirectWhenAlreadyDefeated) {
        if (player == null) return;

        MinecraftServer server = player.server;
        boolean armed = isArmed(server);
        OverlordEndingPresentationState presentation = OverlordEndingPresentationState.get(server);

        // Production facts are monotonic. Clearing the arm fact is an
        // administrative/testing action; disarming resets the presentation latch
        // so a later re-arm starts a clean validation cycle.
        if (!armed && presentation.isPresented()) {
            presentation.reset();
        }

        boolean presented = presentation.isPresented();
        boolean dragonDefeated = hasDragonBeenDefeated(server);
        boolean requestDirect = requestDirectWhenAlreadyDefeated
                && armed
                && !presented
                && dragonDefeated;

        Services.PLATFORM.sendPacketToClient(
                player,
                new OverlordEndingStatePacket(armed, presented, requestDirect)
        );
    }

    public static boolean isArmed(MinecraftServer server) {
        return server != null && OverlordNarrativeState.get(server).hasFact(ENDING_ARMED_FACT);
    }

    public static boolean hasDragonBeenDefeated(MinecraftServer server) {
        if (server == null) return false;
        ServerLevel end = server.getLevel(Level.END);
        if (end == null) return false;
        EndDragonFight fight = end.getDragonFight();
        return fight != null && fight.hasPreviouslyKilledDragon();
    }
}
