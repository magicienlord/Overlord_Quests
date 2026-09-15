package org.infernalstudios.questlog.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import org.infernalstudios.questlog.network.IPacketContext;
import org.infernalstudios.questlog.overlord.commentary.GnarlCommentaryClientHandler;

public record GnarlCommentaryPacket(String message) {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.SERVER_TO_CLIENT;
    private static final int MAX_MESSAGE_LENGTH = 2048;

    public static GnarlCommentaryPacket decode(FriendlyByteBuf buf) {
        return new GnarlCommentaryPacket(buf.readUtf(MAX_MESSAGE_LENGTH));
    }

    public static void handle(GnarlCommentaryPacket packet, IPacketContext ctx) {
        GnarlCommentaryClientHandler.show(packet.message());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(this.message == null ? "" : this.message, MAX_MESSAGE_LENGTH);
    }
}
