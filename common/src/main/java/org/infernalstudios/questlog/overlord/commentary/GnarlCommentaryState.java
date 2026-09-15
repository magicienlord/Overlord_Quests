package org.infernalstudios.questlog.overlord.commentary;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/** World-scoped presentation state for bounded Gnarl lifecycle commentary. */
public final class GnarlCommentaryState extends SavedData {
    private static final String DATA_NAME = "overlord_quests_gnarl_commentary";
    private final Map<UUID, Map<ResourceLocation, QuestState>> byPlayer = new LinkedHashMap<>();

    public static GnarlCommentaryState get(MinecraftServer server) {
        if (server == null) throw new IllegalArgumentException("server must not be null");
        return server.overworld().getDataStorage().computeIfAbsent(
                GnarlCommentaryState::load,
                GnarlCommentaryState::new,
                DATA_NAME
        );
    }

    public QuestState get(UUID playerId, ResourceLocation questId) {
        Map<ResourceLocation, QuestState> quests = this.byPlayer.get(playerId);
        return quests == null ? null : quests.get(questId);
    }

    public QuestState getOrCreate(UUID playerId, ResourceLocation questId) {
        Map<ResourceLocation, QuestState> quests = this.byPlayer.computeIfAbsent(playerId, ignored -> new LinkedHashMap<>());
        QuestState existing = quests.get(questId);
        if (existing != null) return existing;
        QuestState created = new QuestState();
        quests.put(questId, created);
        this.setDirty();
        return created;
    }

    public QuestState reset(UUID playerId, ResourceLocation questId) {
        QuestState fresh = new QuestState();
        this.byPlayer.computeIfAbsent(playerId, ignored -> new LinkedHashMap<>()).put(questId, fresh);
        this.setDirty();
        return fresh;
    }

    public void touch() {
        this.setDirty();
    }

    public static GnarlCommentaryState load(CompoundTag root) {
        GnarlCommentaryState state = new GnarlCommentaryState();
        if (root == null || !root.contains("players", Tag.TAG_COMPOUND)) return state;

        CompoundTag players = root.getCompound("players");
        for (String playerKey : players.getAllKeys()) {
            UUID playerId;
            try {
                playerId = UUID.fromString(playerKey);
            } catch (IllegalArgumentException ignored) {
                continue;
            }
            CompoundTag questTags = players.getCompound(playerKey);
            Map<ResourceLocation, QuestState> quests = new LinkedHashMap<>();
            for (String questKey : questTags.getAllKeys()) {
                ResourceLocation questId = ResourceLocation.tryParse(questKey);
                if (questId != null && questTags.contains(questKey, Tag.TAG_COMPOUND)) {
                    quests.put(questId, QuestState.load(questTags.getCompound(questKey)));
                }
            }
            if (!quests.isEmpty()) state.byPlayer.put(playerId, quests);
        }
        return state;
    }

    @Override
    public CompoundTag save(CompoundTag root) {
        CompoundTag players = new CompoundTag();
        for (Map.Entry<UUID, Map<ResourceLocation, QuestState>> playerEntry : this.byPlayer.entrySet()) {
            CompoundTag quests = new CompoundTag();
            for (Map.Entry<ResourceLocation, QuestState> questEntry : playerEntry.getValue().entrySet()) {
                quests.put(questEntry.getKey().toString(), questEntry.getValue().save());
            }
            players.put(playerEntry.getKey().toString(), quests);
        }
        root.put("players", players);
        return root;
    }

    public static final class QuestState {
        boolean initialized;
        boolean active;
        int completedObjectives;
        int remindersShown;
        boolean clarificationShown;
        boolean branchShown;
        boolean warningShown;
        boolean successShown;
        boolean failureShown;
        boolean postShown;
        long clarificationDueTick;
        long branchDueTick;
        long nextReminderTick;
        long warningDueTick;
        long postDueTick;

        private static QuestState load(CompoundTag tag) {
            QuestState state = new QuestState();
            state.initialized = tag.getBoolean("initialized");
            state.active = tag.getBoolean("active");
            state.completedObjectives = tag.getInt("completedObjectives");
            state.remindersShown = tag.getInt("remindersShown");
            state.clarificationShown = tag.getBoolean("clarificationShown");
            state.branchShown = tag.getBoolean("branchShown");
            state.warningShown = tag.getBoolean("warningShown");
            state.successShown = tag.getBoolean("successShown");
            state.failureShown = tag.getBoolean("failureShown");
            state.postShown = tag.getBoolean("postShown");
            state.clarificationDueTick = tag.getLong("clarificationDueTick");
            state.branchDueTick = tag.getLong("branchDueTick");
            state.nextReminderTick = tag.getLong("nextReminderTick");
            state.warningDueTick = tag.getLong("warningDueTick");
            state.postDueTick = tag.getLong("postDueTick");
            return state;
        }

        private CompoundTag save() {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean("initialized", this.initialized);
            tag.putBoolean("active", this.active);
            tag.putInt("completedObjectives", this.completedObjectives);
            tag.putInt("remindersShown", this.remindersShown);
            tag.putBoolean("clarificationShown", this.clarificationShown);
            tag.putBoolean("branchShown", this.branchShown);
            tag.putBoolean("warningShown", this.warningShown);
            tag.putBoolean("successShown", this.successShown);
            tag.putBoolean("failureShown", this.failureShown);
            tag.putBoolean("postShown", this.postShown);
            tag.putLong("clarificationDueTick", this.clarificationDueTick);
            tag.putLong("branchDueTick", this.branchDueTick);
            tag.putLong("nextReminderTick", this.nextReminderTick);
            tag.putLong("warningDueTick", this.warningDueTick);
            tag.putLong("postDueTick", this.postDueTick);
            return tag;
        }
    }
}
