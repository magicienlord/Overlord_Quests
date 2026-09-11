package org.infernalstudios.questlog.client.provider;

import net.minecraft.client.Minecraft;
import org.infernalstudios.questlog.network.packet.QuestProviderOpenPacket;

/** Client-only endpoint for server-authoritative provider menu snapshots. */
public final class QuestProviderClientHandler {
    private QuestProviderClientHandler() {
    }

    public static void handle(QuestProviderOpenPacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null) {
            return;
        }
        minecraft.setScreen(new QuestProviderScreen(packet));
    }
}
