package org.infernalstudios.questlog.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import org.infernalstudios.questlog.client.ending.OverlordEndingScreens;
import org.infernalstudios.questlog.network.IPacketContext;

/** Server-to-client projection of the central ending activation state. */
public record OverlordEndingStatePacket(boolean armed, boolean presented, boolean requestDirectPresentation) {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.SERVER_TO_CLIENT;

    public static OverlordEndingStatePacket decode(FriendlyByteBuf buf) {
        return new OverlordEndingStatePacket(buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
    }

    public static void handle(OverlordEndingStatePacket packet, IPacketContext ctx) {
        if (ctx.getDirection() != DIRECTION) {
            return;
        }
        OverlordEndingScreens.updateServerState(
                packet.armed,
                packet.presented,
                packet.requestDirectPresentation
        );
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.armed);
        buf.writeBoolean(this.presented);
        buf.writeBoolean(this.requestDirectPresentation);
    }
}
