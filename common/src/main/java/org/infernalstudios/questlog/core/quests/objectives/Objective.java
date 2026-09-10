package org.infernalstudios.questlog.core.quests.objectives;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.display.ObjectiveDisplayData;
import org.infernalstudios.questlog.core.quests.display.WithDisplayData;
import org.infernalstudios.questlog.util.JsonUtils;
import org.infernalstudios.questlog.util.NbtSaveable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Objective implements NbtSaveable, WithDisplayData<ObjectiveDisplayData> {

    private final ObjectiveDisplayData display;
    private final int requiredAmount;
    private Quest parent;
    private int units;
    private boolean isPartOfPrerequisites = false;

    public Objective(JsonObject definition) {
        // Repository validation rejects non-positive required amounts, but config
        // files and old saves are external inputs at runtime. Keep every objective
        // in a representable state even when those inputs are malformed.
        this.requiredAmount = Math.max(1, JsonUtils.getOrDefault(definition, "required_amount", 1));
        this.units = 0;

        this.display = new ObjectiveDisplayData(definition);
        this.display.setObjective(this);
    }

    public void registerEventListeners() {
    }

    /**
     * Releases listeners owned by this objective from Questlog's private event
     * bus. Logic objectives inherit recursive cleanup for their child tree.
     * External Triggers callbacks cannot currently be removed individually, so
     * those are made inert by the manager/quest lifecycle checks instead.
     */
    public void unregisterEventListeners() {
        for (Objective child : this.getChildren()) {
            child.unregisterEventListeners();
        }
    }

    public void markAsPrerequisite() {
        this.isPartOfPrerequisites = true;
    }

    @Deprecated
    public void markAsRequirement() {
        this.markAsPrerequisite();
    }

    @Nullable
    public final Quest getParent() {
        return this.parent;
    }

    public void setParent(@Nullable Quest parent) {
        this.parent = parent;
    }

    public int getUnits() {
        return this.units;
    }

    /**
     * Returns whether this objective still belongs to the active quest instance
     * currently installed in its manager.
     *
     * Questlog recreates Quest objects when definitions are reloaded. Triggers
     * 1.0.1 exposes only addListener and removeAllListeners, not removal of one
     * listener. Old callbacks can therefore remain reachable after reload or even
     * after a whole manager generation is replaced. Require both an active manager
     * generation and exact quest identity before accepting objective mutation.
     */
    protected boolean isActiveQuestInstance() {
        return this.parent == null || (
                this.parent.manager.isActive()
                        && this.parent.manager.getQuest(this.parent.getId()) == this.parent
        );
    }

    private int clampUnits(int units) {
        return Math.max(0, Math.min(units, this.requiredAmount));
    }

    public void setUnits(int units) {
        if (!this.isActiveQuestInstance()) {
            return;
        }

        if (this.getParent() != null && !this.getParent().isTriggered() && !this.isPartOfPrerequisites) {
            return;
        }

        this.units = this.clampUnits(units);
        if (this.getParent() != null) {
            this.getParent().markForUpdate();
        }
    }

    public int getRequiredAmount() {
        return this.requiredAmount;
    }

    public boolean isCompleted() {
        return this.units >= this.requiredAmount;
    }

    public boolean isHidden() {
        return false;
    }

    @Override
    public ObjectiveDisplayData getDisplay() {
        return this.display;
    }

    @Override
    public void writeInitialData(CompoundTag data) {
        data.putInt("units", this.units);
    }

    @Override
    public void deserialize(CompoundTag data) {
        // Persisted NBT is compatibility input, not an authority boundary. Clamp
        // stale/corrupt values so negative progress or values above the current
        // definition cannot escape the objective's runtime invariant.
        this.units = this.clampUnits(data.getInt("units"));
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("units", this.units);
        return tag;
    }

    /**
     * Read objectives can be nested inside logic objectives. Propagating this
     * capability through the objective tree lets the existing details-screen
     * read action remain reachable for nested read requirements as well as for a
     * top-level ReadObjective.
     */
    public boolean isReadObjective() {
        for (Objective child : this.getChildren()) {
            if (child.isReadObjective()) {
                return true;
            }
        }
        return false;
    }

    public void forceSetUnits(int units) {
        if (!this.isActiveQuestInstance()) {
            return;
        }
        this.units = this.clampUnits(units);
        if (this.getParent() != null) {
            this.getParent().markForUpdate();
        }
    }

    public List<Objective> getChildren() {
        return List.of();
    }
}
