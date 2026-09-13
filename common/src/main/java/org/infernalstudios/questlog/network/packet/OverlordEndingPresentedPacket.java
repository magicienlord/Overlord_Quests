package org.infernalstudios.questlog.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.network.IPacketContext;
import org.infernalstudios.questlog.overlord.ending.OverlordEndingActivation;

/** Client acknowledgement that the one-time REIGN ending presentation completed. */
public record OverlordEndingPresentedPacket() {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.CLIENT_TO_SERVER;

    public static OverlordEndingPresentedPacket decode(FriendlyByteBuf buf) {
        return new OverlordEndingPresentedPacket();
    }

    public static void handle(OverlordEndingPresentedPacket packet, IPacketContext ctx) {
        if (!(ctx.getSender() instanceof ServerPlayer sender)) {
            Questlog.LOGGER.warn("Ignoring OVERLORD ending acknowledgement because no server player sender is available");
            return;
        }
        OverlordEndingActivation.acknowledgePresentation(sender);
    }

    public void encode(FriendlyByteBuf buf) {
        // No payload. Server authority validates the current campaign and Dragon state.
    }
}
