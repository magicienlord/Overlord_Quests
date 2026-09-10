package org.infernalstudios.questlog.core.quests.rewards;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.quests.QuestRewardRegistry;
import org.infernalstudios.questlog.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChoiceReward extends Reward {

    /**
     * The claim packet transfers one int per selected choice and deliberately caps
     * that selection list before allocation. A definition requiring more picks
     * than the packet can represent would be permanently unclaimable, so keep the
     * definition/runtime contract on the same boundary as the wire contract.
     */
    public static final int MAX_SELECTIONS = 256;

    private final List<Reward> choices;
    private final int pickCount;
    private final List<Integer> selectedIndices = new ArrayList<>();

    public ChoiceReward(JsonObject definition) {
        super(definition);
        this.pickCount = JsonUtils.getOrDefault(definition, "pick_count", 1);
        this.choices = new ArrayList<>();

        JsonArray choiceDefinitions = JsonUtils.getOrDefault(definition, "choices", new JsonArray());
        for (JsonElement element : choiceDefinitions) {
            if (!element.isJsonObject()) {
                throw new IllegalArgumentException("Choice reward entries must be JSON objects");
            }
            Reward choice = QuestRewardRegistry.create(element.getAsJsonObject());
            if (choice instanceof ChoiceReward) {
                // The current claim packet transfers the selected indices of each
                // top-level choice reward only. A nested ChoiceReward would have
                // client-side selections that are never transmitted to the server
                // and could therefore be marked collected without granting its
                // intended child reward.
                throw new IllegalArgumentException("Nested choice rewards are not supported by the current claim protocol");
            }
            choice.setContainer(this);
            this.choices.add(choice);
        }

        if (this.pickCount < 1 || this.pickCount > this.choices.size()) {
            throw new IllegalArgumentException(
                    "Choice reward pick_count must be between 1 and the number of choices (" + this.choices.size() + ")"
            );
        }
        if (this.pickCount > MAX_SELECTIONS) {
            throw new IllegalArgumentException(
                    "Choice reward pick_count cannot exceed the claim protocol maximum of " + MAX_SELECTIONS
            );
        }
        if (this.isAutoClaim()) {
            throw new IllegalArgumentException("Choice rewards cannot use auto_claim because they require player selection");
        }
    }

    public List<Reward> getChoices() {
        return this.choices;
    }

    public int getPickCount() {
        return this.pickCount;
    }

    public List<Integer> getSelectedIndicesList() {
        return List.copyOf(this.selectedIndices);
    }

    /**
     * Returns true only when the supplied selection can be claimed as-is.
     * Validation is deliberately pure so a malformed client packet cannot mutate
     * the authoritative server selection state before it is rejected.
     */
    public boolean isValidSelection(List<Integer> indices) {
        if (indices == null || indices.size() != this.pickCount || indices.size() > MAX_SELECTIONS) {
            return false;
        }

        Set<Integer> unique = new HashSet<>();
        for (Integer index : indices) {
            if (index == null || index < 0 || index >= this.choices.size() || !unique.add(index)) {
                return false;
            }
        }
        return true;
    }

    public void setSelectedIndices(List<Integer> indices) {
        this.selectedIndices.clear();
        if (indices != null) {
            for (Integer index : indices) {
                if (this.selectedIndices.size() >= MAX_SELECTIONS) {
                    break;
                }
                if (index != null && index >= 0 && index < this.choices.size() && !this.selectedIndices.contains(index)) {
                    this.selectedIndices.add(index);
                }
            }
        }
    }

    public boolean isChoiceSelected(Reward choice) {
        int index = this.choices.indexOf(choice);
        return index != -1 && this.selectedIndices.contains(index);
    }

    public void toggleChoice(Reward choice) {
        int index = this.choices.indexOf(choice);
        if (index == -1) return;

        if (this.selectedIndices.contains(index)) {
            this.selectedIndices.remove((Integer) index);
        } else {
            if (this.selectedIndices.size() >= this.pickCount) {
                this.selectedIndices.remove(0);
            }
            this.selectedIndices.add(index);
        }
        if (this.getParent() != null) {
            this.getParent().markForUpdate();
        }
    }

    public boolean canClaim() {
        return this.isValidSelection(this.selectedIndices);
    }

    @Override
    public void applyReward(ServerPlayer player) {
        // The server-side packet handler verifies the exact selection before
        // invoking this reward. Keep a second guard here for future call sites.
        if (!this.canClaim()) {
            return;
        }

        for (int index : this.selectedIndices) {
            this.choices.get(index).applyReward(player);
        }
        for (Reward choice : this.choices) {
            choice.setRewarded(true);
        }
        super.applyReward(player);
    }

    @Override
    public void revokeReward() {
        super.revokeReward();
        this.selectedIndices.clear();
        for (Reward choice : this.choices) {
            choice.setRewarded(false);
        }
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = super.serialize();
        tag.putIntArray("selected_indices", this.selectedIndices.stream().mapToInt(Integer::intValue).toArray());
        return tag;
    }

    @Override
    public void deserialize(CompoundTag data) {
        super.deserialize(data);

        // Treat persisted indices as untrusted compatibility data. Definitions
        // can change between saves, so discard duplicates and indices that no
        // longer address a choice instead of allowing stale selections to satisfy
        // pick_count without awarding the intended number of rewards.
        List<Integer> persistedSelections = new ArrayList<>();
        for (int index : data.getIntArray("selected_indices")) {
            persistedSelections.add(index);
        }
        this.setSelectedIndices(persistedSelections);

        // If an old save claims a choice reward but its persisted selection no
        // longer satisfies the current definition, reopen the reward instead of
        // preserving a claimed state that cannot describe what was granted.
        if (this.hasRewarded() && !this.canClaim()) {
            this.setRewarded(false);
        }

        for (Reward choice : this.choices) {
            choice.setRewarded(this.hasRewarded());
        }
    }
}
