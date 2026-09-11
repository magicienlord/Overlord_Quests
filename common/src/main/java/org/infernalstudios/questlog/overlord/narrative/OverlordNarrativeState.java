package org.infernalstudios.questlog.overlord.narrative;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import org.infernalstudios.questlog.Questlog;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * World-scoped authored narrative state for OVERLORD REIGN.
 *
 * This deliberately stores explicit state identifiers rather than a numeric
 * reputation value. Civilizations may use different legal state sets, so the
 * runtime treats both civilization and state as data-driven ResourceLocations.
 */
public final class OverlordNarrativeState extends SavedData {
    private static final String DATA_NAME = "overlord_quests_narrative";
    public static final ResourceLocation UNRESOLVED = new ResourceLocation(Questlog.MODID, "unresolved");

    private final Map<ResourceLocation, ResourceLocation> dispositions = new LinkedHashMap<>();

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

    public Map<ResourceLocation, ResourceLocation> snapshotDispositions() {
        return Map.copyOf(this.dispositions);
    }

    public static OverlordNarrativeState load(CompoundTag root) {
        OverlordNarrativeState state = new OverlordNarrativeState();
        if (root == null || !root.contains("dispositions", Tag.TAG_COMPOUND)) {
            return state;
        }

        CompoundTag dispositionsTag = root.getCompound("dispositions");
        for (String civilizationText : dispositionsTag.getAllKeys()) {
            ResourceLocation civilization = ResourceLocation.tryParse(civilizationText);
            ResourceLocation disposition = ResourceLocation.tryParse(dispositionsTag.getString(civilizationText));
            if (civilization == null || disposition == null || UNRESOLVED.equals(disposition)) {
                continue;
            }
            state.dispositions.put(civilization, disposition);
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
        return root;
    }
}
