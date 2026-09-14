package org.infernalstudios.questlog.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.network.IPacketContext;
import org.infernalstudios.questlog.overlord.reaction.OverlordSystemReactionClientHandler;

public record SystemReactionPacket(ResourceLocation id) {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.SERVER_TO_CLIENT;

    public static SystemReactionPacket decode(FriendlyByteBuf buf) {
        return new SystemReactionPacket(buf.readResourceLocation());
    }

    public static void handle(SystemReactionPacket packet, IPacketContext ctx) {
        OverlordSystemReactionClientHandler.show(packet.id());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.id);
    }
}
