package org.infernalstudios.questlog.overlord.reaction;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * World-scoped presentation state for sparse OVERLORD REIGN system reactions.
 *
 * These flags are deliberately separate from narrative facts. A reaction records
 * that one player has already seen an acknowledgement, not that an in-universe
 * historical event occurred.
 */
public final class OverlordSystemReactionState extends SavedData {
    private static final String DATA_NAME = "overlord_quests_system_reactions";
    private final Map<UUID, Set<ResourceLocation>> shownByPlayer = new LinkedHashMap<>();

    public static OverlordSystemReactionState get(MinecraftServer server) {
        if (server == null) {
            throw new IllegalArgumentException("server must not be null");
        }
        return server.overworld().getDataStorage().computeIfAbsent(
                OverlordSystemReactionState::load,
                OverlordSystemReactionState::new,
                DATA_NAME
        );
    }

    public boolean hasShown(UUID playerId, ResourceLocation reactionId) {
        if (playerId == null || reactionId == null) return false;
        Set<ResourceLocation> shown = this.shownByPlayer.get(playerId);
        return shown != null && shown.contains(reactionId);
    }

    public boolean markShown(UUID playerId, ResourceLocation reactionId) {
        if (playerId == null || reactionId == null) {
            throw new IllegalArgumentException("playerId and reactionId must be non-null");
        }
        Set<ResourceLocation> shown = this.shownByPlayer.computeIfAbsent(playerId, ignored -> new LinkedHashSet<>());
        if (shown.add(reactionId)) {
            this.setDirty();
            return true;
        }
        return false;
    }

    public static OverlordSystemReactionState load(CompoundTag root) {
        OverlordSystemReactionState state = new OverlordSystemReactionState();
        if (root == null || !root.contains("players", Tag.TAG_COMPOUND)) {
            return state;
        }

        CompoundTag players = root.getCompound("players");
        for (String playerText : players.getAllKeys()) {
            UUID playerId;
            try {
                playerId = UUID.fromString(playerText);
            } catch (IllegalArgumentException ignored) {
                continue;
            }

            ListTag reactionTags = players.getList(playerText, Tag.TAG_STRING);
            Set<ResourceLocation> reactions = new LinkedHashSet<>();
            for (int i = 0; i < reactionTags.size(); i++) {
                ResourceLocation reactionId = ResourceLocation.tryParse(reactionTags.getString(i));
                if (reactionId != null) {
                    reactions.add(reactionId);
                }
            }
            if (!reactions.isEmpty()) {
                state.shownByPlayer.put(playerId, reactions);
            }
        }
        return state;
    }

    @Override
    public CompoundTag save(CompoundTag root) {
        CompoundTag players = new CompoundTag();
        for (Map.Entry<UUID, Set<ResourceLocation>> entry : this.shownByPlayer.entrySet()) {
            ListTag reactions = new ListTag();
            entry.getValue().stream()
                    .map(ResourceLocation::toString)
                    .sorted()
                    .map(StringTag::valueOf)
                    .forEach(reactions::add);
            players.put(entry.getKey().toString(), reactions);
        }
        root.put("players", players);
        return root;
    }
}
