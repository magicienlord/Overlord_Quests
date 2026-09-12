package org.infernalstudios.questlog.overlord.narrative;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import org.infernalstudios.questlog.Questlog;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * World-scoped authored narrative state for OVERLORD REIGN.
 *
 * This deliberately stores explicit resource identifiers rather than numeric
 * reputation, morality, friendship, or corruption values. Civilization
 * dispositions are exclusive state values. Narrative facts are monotonic named
 * truths that record events and consequences the campaign may inspect later.
 */
public final class OverlordNarrativeState extends SavedData {
    private static final String DATA_NAME = "overlord_quests_narrative";
    public static final ResourceLocation UNRESOLVED = new ResourceLocation(Questlog.MODID, "unresolved");

    private final Map<ResourceLocation, ResourceLocation> dispositions = new LinkedHashMap<>();
    private final Set<ResourceLocation> facts = new LinkedHashSet<>();

    public static OverlordNarrativeState get(MinecraftServer server) {
        if (server == null) {
            throw new IllegalArgumentException("server must not be null");
        }
        return server.overworld().getDataStorage().computeIfAbsent(
                OverlordNarrativeState::load,
                OverlordNarrativeState::new,
                DATA_NAME
        );
    }

    public ResourceLocation getDisposition(ResourceLocation civilization) {
        if (civilization == null) {
            return UNRESOLVED;
        }
        return this.dispositions.getOrDefault(civilization, UNRESOLVED);
    }

    public boolean hasDisposition(ResourceLocation civilization, ResourceLocation state) {
        return civilization != null && state != null && state.equals(this.getDisposition(civilization));
    }

    public boolean setDisposition(ResourceLocation civilization, ResourceLocation state) {
        if (civilization == null || state == null) {
            throw new IllegalArgumentException("civilization and state must be non-null");
        }

        ResourceLocation previous;
        if (UNRESOLVED.equals(state)) {
            previous = this.dispositions.remove(civilization);
        } else {
            previous = this.dispositions.put(civilization, state);
        }

        ResourceLocation effectivePrevious = previous == null ? UNRESOLVED : previous;
        if (!effectivePrevious.equals(state)) {
            this.setDirty();
            return true;
        }
        return false;
    }

    /** Records one authored narrative fact. Production quest rewards only add facts. */
    public boolean setFact(ResourceLocation fact) {
        if (fact == null) {
            throw new IllegalArgumentException("fact must be non-null");
        }
        if (this.facts.add(fact)) {
            this.setDirty();
            return true;
        }
        return false;
    }

    public boolean hasFact(ResourceLocation fact) {
        return fact != null && this.facts.contains(fact);
    }

    /**
     * Administrative/testing escape hatch. Campaign rewards intentionally do not
     * expose fact removal because facts represent history, not reversible score.
     */
    public boolean clearFact(ResourceLocation fact) {
        if (fact == null) {
            throw new IllegalArgumentException("fact must be non-null");
        }
        if (this.facts.remove(fact)) {
            this.setDirty();
            return true;
        }
        return false;
    }

    public Map<ResourceLocation, ResourceLocation> snapshotDispositions() {
        return Map.copyOf(this.dispositions);
    }

    public Set<ResourceLocation> snapshotFacts() {
        return Set.copyOf(this.facts);
    }

    public static OverlordNarrativeState load(CompoundTag root) {
        OverlordNarrativeState state = new OverlordNarrativeState();
        if (root == null) {
            return state;
        }

        if (root.contains("dispositions", Tag.TAG_COMPOUND)) {
            CompoundTag dispositionsTag = root.getCompound("dispositions");
            for (String civilizationText : dispositionsTag.getAllKeys()) {
                ResourceLocation civilization = ResourceLocation.tryParse(civilizationText);
                ResourceLocation disposition = ResourceLocation.tryParse(dispositionsTag.getString(civilizationText));
                if (civilization == null || disposition == null || UNRESOLVED.equals(disposition)) {
                    continue;
                }
                state.dispositions.put(civilization, disposition);
            }
        }

        if (root.contains("facts", Tag.TAG_LIST)) {
            ListTag factsTag = root.getList("facts", Tag.TAG_STRING);
            for (int i = 0; i < factsTag.size(); i++) {
                ResourceLocation fact = ResourceLocation.tryParse(factsTag.getString(i));
                if (fact != null) {
                    state.facts.add(fact);
                }
            }
        }
        return state;
    }

    @Override
    public CompoundTag save(CompoundTag root) {
        CompoundTag dispositionsTag = new CompoundTag();
        for (Map.Entry<ResourceLocation, ResourceLocation> entry : this.dispositions.entrySet()) {
            dispositionsTag.putString(entry.getKey().toString(), entry.getValue().toString());
        }
        root.put("dispositions", dispositionsTag);

        ListTag factsTag = new ListTag();
        this.facts.stream()
                .map(ResourceLocation::toString)
                .sorted()
                .map(StringTag::valueOf)
                .forEach(factsTag::add);
        root.put("facts", factsTag);
        return root;
    }
}
