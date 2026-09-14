package org.infernalstudios.questlog.overlord.reaction;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.network.packet.SystemReactionPacket;
import org.infernalstudios.questlog.platform.Services;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Sparse one-time acknowledgements for external progression systems that the
 * authoritative quest assignment ledger explicitly says must not become quest
 * chapters of their own.
 */
public final class OverlordSystemReactions {
    public static final ResourceLocation ENCHANTING_SYSTEM_OVERHAUL = id("system/enchanting_system_overhaul");
    public static final ResourceLocation LEVELUP = id("system/levelup");
    public static final ResourceLocation RPG_SKILL_TREES = id("system/rpg_skill_trees");
    public static final ResourceLocation SPICE_OF_LIFE_10 = id("system/spice_of_life_10");
    public static final ResourceLocation SPICE_OF_LIFE_25 = id("system/spice_of_life_25");
    public static final ResourceLocation SPICE_OF_LIFE_50 = id("system/spice_of_life_50");
    public static final ResourceLocation SPICE_OF_LIFE_75 = id("system/spice_of_life_75");
    public static final ResourceLocation SPICE_OF_LIFE_100 = id("system/spice_of_life_100");

    private static final Map<ResourceLocation, Reaction> DEFINITIONS = definitions();

    private OverlordSystemReactions() {
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation("overlord_reign", path);
    }

    private static Map<ResourceLocation, Reaction> definitions() {
        Map<ResourceLocation, Reaction> definitions = new LinkedHashMap<>();
        definitions.put(ENCHANTING_SYSTEM_OVERHAUL, new Reaction(
                "Enchanting Reworked",
                "The enchanting table now follows the Enchanting System Overhaul rules. Its costs and requirements differ from vanilla."
        ));
        definitions.put(LEVELUP, new Reaction(
                "LevelUP",
                "Power can be strengthened directly through attribute investment. Spend earned points deliberately."
        ));
        definitions.put(RPG_SKILL_TREES, new Reaction(
                "RPG Skill Trees",
                "A new discipline has opened. Further progression remains within the skill tree."
        ));
        definitions.put(SPICE_OF_LIFE_10, new Reaction(
                "A Broader Appetite",
                "10 distinct foods tasted. Variety is becoming a strength."
        ));
        definitions.put(SPICE_OF_LIFE_25, new Reaction(
                "A Varied Table",
                "25 distinct foods tasted. Keep broadening the menu."
        ));
        definitions.put(SPICE_OF_LIFE_50, new Reaction(
                "Seasoned Appetite",
                "50 distinct foods tasted. Half a hundred meals now count toward your variety."
        ));
        definitions.put(SPICE_OF_LIFE_75, new Reaction(
                "Feast Without Repetition",
                "75 distinct foods tasted. Few tables can still offer something new."
        ));
        definitions.put(SPICE_OF_LIFE_100, new Reaction(
                "A Hundred Tastes",
                "100 distinct foods tasted. The milestone is complete."
        ));
        return Map.copyOf(definitions);
    }

    @Nullable
    public static Reaction getDefinition(ResourceLocation reactionId) {
        return reactionId == null ? null : DEFINITIONS.get(reactionId);
    }

    public static boolean hasShown(ServerPlayer player, ResourceLocation reactionId) {
        if (player == null || reactionId == null || player.getServer() == null) return false;
        return OverlordSystemReactionState.get(player.getServer()).hasShown(player.getUUID(), reactionId);
    }

    public static boolean trigger(ServerPlayer player, ResourceLocation reactionId) {
        if (player == null || reactionId == null || !DEFINITIONS.containsKey(reactionId)) {
            return false;
        }
        MinecraftServer server = player.getServer();
        if (server == null) return false;

        if (!OverlordSystemReactionState.get(server).markShown(player.getUUID(), reactionId)) {
            return false;
        }

        Services.PLATFORM.sendPacketToClient(player, new SystemReactionPacket(reactionId));
        Questlog.LOGGER.debug("Presented system reaction {} to {}", reactionId, player.getGameProfile().getName());
        return true;
    }

    public record Reaction(String title, String description) {
    }
}
