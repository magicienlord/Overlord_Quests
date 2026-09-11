package org.infernalstudios.questlog.core.quests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.quests.display.QuestDisplayData;
import org.infernalstudios.questlog.core.quests.display.WithDisplayData;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.overlord.provider.QuestProviderBinding;
import org.infernalstudios.questlog.overlord.provider.QuestProviderRule;
import org.jetbrains.annotations.Nullable;
import org.infernalstudios.questlog.util.JsonUtils;
import org.infernalstudios.questlog.util.NbtSaveable;
import org.infernalstudios.questlog.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Quest implements NbtSaveable, WithDisplayData<QuestDisplayData> {

    /**
     * `quest_complete` objectives can form self-references or multi-quest cycles
     * in external config. Their source-faithful dynamic completion fallback calls
     * the target Quest's isCompleted(), so an unguarded A -> B -> A dependency
     * recurses until StackOverflowError. Track the active completion call graph by
     * Quest object identity and treat a cycle as incomplete.
     */
    private static final ThreadLocal<Set<Quest>> COMPLETION_EVALUATION = ThreadLocal.withInitial(
            () -> Collections.newSetFromMap(new IdentityHashMap<>())
    );
    private static final Set<ResourceLocation> REPORTED_COMPLETION_CYCLES = ConcurrentHashMap.newKeySet();

    public final List<Objective> prerequisites;
    public final List<Objective> objectives;
    public final List<Objective> failureConditions;
    public final List<Reward> rewards;
    public final QuestManager manager;
    private final QuestDisplayData display;
    private final ResourceLocation id;
    public boolean hasSentCompletion = false;
    public boolean hasSentTrigger = false;
    private final boolean repeatable;
    private final boolean global;
    @Nullable private final QuestProviderRule providerRule;
    @Nullable private QuestProviderBinding providerBinding;
    private boolean providerTurnedIn = false;
    private boolean disposed = false;

    public Quest(
            QuestDisplayData display,
            List<Objective> prerequisites,
            List<Objective> objectives,
            List<Objective> failureConditions,
            List<Reward> rewards,
            ResourceLocation id,
            QuestManager manager,
            boolean repeatable,
            boolean global,
            @Nullable QuestProviderRule providerRule
    ) {
        this.display = display;
        this.prerequisites = prerequisites;
        this.objectives = objectives;
        this.failureConditions = failureConditions;
        this.rewards = rewards;
        this.id = id;
        this.manager = manager;
        this.repeatable = repeatable;
        this.global = global;
        this.providerRule = providerRule;

        if (this.prerequisites.isEmpty() && this.providerRule == null) {
            this.hasSentTrigger = true;
        }

        this.prerequisites.forEach(prerequisite -> {
            prerequisite.markAsPrerequisite();
            prerequisite.setParent(this);
            if (!this.manager.isClient()) {
                prerequisite.registerEventListeners();
            }
        });
        this.objectives.forEach(objective -> {
            objective.setParent(this);
            if (!this.manager.isClient()) {
                objective.registerEventListeners();
            }
        });
        this.failureConditions.forEach(failureCondition -> {
            failureCondition.setParent(this);
            if (!this.manager.isClient()) {
                failureCondition.registerEventListeners();
            }
        });
        this.rewards.forEach(reward -> reward.setParent(this));
        display.setQuest(this);
    }

    public static Quest create(JsonObject definition, ResourceLocation id, QuestManager manager) {
        QuestDisplayData display = new QuestDisplayData(definition);
        List<Objective> prerequisites = new ArrayList<>();
        List<Objective> objectives = new ArrayList<>();
        List<Objective> failureConditions = new ArrayList<>();
        List<Reward> rewards = new ArrayList<>();

        JsonArray reqArray = definition.has("prerequisites") ? definition.getAsJsonArray("prerequisites")
                : (definition.has("requirements") ? definition.getAsJsonArray("requirements") : new JsonArray());
        for (JsonElement reqElement : reqArray) {
            if (reqElement.isJsonObject()) {
                prerequisites.add(QuestObjectiveRegistry.create(reqElement.getAsJsonObject()));
            }
        }

        for (JsonElement objectiveElement : JsonUtils.getOrDefault(definition, "objectives", new JsonArray())) {
            if (objectiveElement.isJsonObject()) {
                objectives.add(QuestObjectiveRegistry.create(objectiveElement.getAsJsonObject()));
            }
        }

        for (JsonElement failElement : JsonUtils.getOrDefault(definition, "failures", new JsonArray())) {
            if (failElement.isJsonObject()) {
                failureConditions.add(QuestObjectiveRegistry.create(failElement.getAsJsonObject()));
            }
        }

        for (JsonElement rewardElement : JsonUtils.getOrDefault(definition, "rewards", new JsonArray())) {
            if (rewardElement.isJsonObject()) {
                rewards.add(QuestRewardRegistry.create(rewardElement.getAsJsonObject()));
            }
        }

        boolean repeatable = JsonUtils.getOrDefault(definition, "repeatable", false);
        boolean global = JsonUtils.getOrDefault(definition, "global", false);
        QuestProviderRule providerRule = QuestProviderRule.fromDefinition(definition);

        return new Quest(display, prerequisites, objectives, failureConditions, rewards, id, manager, repeatable, global, providerRule);
    }

    /**
     * Releases listeners owned by this quest from Questlog's private event bus.
     * The method is idempotent because managers can be cleared as part of both a
     * reload path and a later lifecycle shutdown.
     */
    public void dispose() {
        if (this.disposed) return;
        this.disposed = true;

        this.prerequisites.forEach(Objective::unregisterEventListeners);
        this.objectives.forEach(Objective::unregisterEventListeners);
        this.failureConditions.forEach(Objective::unregisterEventListeners);
    }

    public boolean isDisposed() {
        return this.disposed;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public boolean isRepeatable() {
        return this.repeatable;
    }

    public boolean isGlobal() {
        return this.global;
    }

    @Nullable
    public QuestProviderRule getProviderRule() {
        return this.providerRule;
    }

    @Nullable
    public QuestProviderBinding getProviderBinding() {
        return this.providerBinding;
    }

    public boolean arePrerequisitesComplete() {
        if (this.disposed || !this.manager.isActive()) return false;
        for (Objective prerequisite : this.prerequisites) {
            if (!prerequisite.isCompleted()) return false;
        }
        return true;
    }

    public boolean areObjectivesComplete() {
        if (this.disposed || !this.manager.isActive() || this.isFailed()) return false;
        for (Objective objective : this.objectives) {
            if (!objective.isCompleted()) return false;
        }
        return true;
    }

    public boolean isReadyForProviderTurnIn() {
        return this.providerRule != null
                && this.providerBinding != null
                && this.providerRule.requiresTurnIn()
                && this.arePrerequisitesComplete()
                && this.areObjectivesComplete()
                && !this.providerTurnedIn;
    }

    public void bindProvider(QuestProviderBinding binding) {
        if (this.disposed || !this.manager.isActive() || this.providerRule == null || binding == null || this.providerBinding != null) {
            return;
        }
        this.providerBinding = binding;
        this.providerTurnedIn = false;
        this.markForUpdate();
    }

    public void completeProviderTurnIn() {
        if (!this.isReadyForProviderTurnIn()) return;
        this.providerTurnedIn = true;
        this.markForUpdate();
    }

    public void resetProgress() {
        if (this.disposed || !this.manager.isActive()) return;
        this.prerequisites.forEach(trigger -> trigger.forceSetUnits(0));
        this.objectives.forEach(obj -> obj.forceSetUnits(0));
        this.failureConditions.forEach(obj -> obj.forceSetUnits(0));
        this.rewards.forEach(Reward::revokeReward);
        this.providerBinding = null;
        this.providerTurnedIn = false;
        this.hasSentTrigger = this.prerequisites.isEmpty() && this.providerRule == null;
        this.hasSentCompletion = false;
        this.markForUpdate();
    }

    @Override
    public QuestDisplayData getDisplay() {
        return this.display;
    }

    public boolean isTriggered() {
        if (this.disposed || !this.manager.isActive()) return false;
        if (this.providerRule != null && this.providerBinding == null) return false;
        return this.arePrerequisitesComplete();
    }

    public boolean isFailed() {
        return !this.disposed && this.manager.isActive()
                && !this.failureConditions.isEmpty()
                && this.failureConditions.stream().anyMatch(Objective::isCompleted);
    }

    public boolean isCompleted() {
        if (this.disposed || !this.manager.isActive()) return false;

        Set<Quest> evaluating = COMPLETION_EVALUATION.get();
        if (!evaluating.add(this)) {
            if (REPORTED_COMPLETION_CYCLES.add(this.id)) {
                Questlog.LOGGER.warn("Detected cyclic quest_complete dependency while evaluating {}. Further warnings for this quest ID are suppressed.", this.id);
            }
            return false;
        }

        try {
            // A quest with prerequisites but no objectives must not be considered
            // complete while it is still locked. Once its prerequisites trigger, an
            // empty objective list can legitimately complete immediately.
            if (!this.isTriggered() || this.isFailed()) return false;

            if (!this.areObjectivesComplete()) return false;
            return this.providerRule == null || !this.providerRule.requiresTurnIn() || this.providerTurnedIn;
        } finally {
            evaluating.remove(this);
            if (evaluating.isEmpty()) {
                COMPLETION_EVALUATION.remove();
            }
        }
    }

    public boolean isRewarded() {
        if (this.disposed || !this.manager.isActive()) return false;
        for (Reward reward : this.rewards) {
            if (!reward.hasRewarded()) {
                return false;
            }
        }
        return true;
    }

    public void markForUpdate() {
        if (!this.disposed && this.manager.isActive()) {
            this.manager.sync(this.id);
        }
    }

    @Override
    public void writeInitialData(CompoundTag data) {
        data.putBoolean("completed", this.hasSentCompletion);
        data.putBoolean("triggered", this.hasSentTrigger);
        // Keep these legacy metadata keys in serialized state for compatibility
        // with older Questlog data readers. This fork does not deserialize them:
        // repeatable/global are definition-owned behavior, not player progress.
        data.putBoolean("repeatable", this.repeatable);
        data.putBoolean("global", this.global);
        data.putBoolean("provider_turned_in", false);

        data.put(
                "prerequisites",
                Util.toNbtList(this.prerequisites, prerequisite -> {
                    CompoundTag tag = new CompoundTag();
                    prerequisite.writeInitialData(tag);
                    return tag;
                })
        );

        data.put(
                "objectives",
                Util.toNbtList(this.objectives, objective -> {
                    CompoundTag tag = new CompoundTag();
                    objective.writeInitialData(tag);
                    return tag;
                })
        );

        data.put(
                "failures",
                Util.toNbtList(this.failureConditions, condition -> {
                    CompoundTag tag = new CompoundTag();
                    condition.writeInitialData(tag);
                    return tag;
                })
        );

        data.put(
                "rewards",
                Util.toNbtList(this.rewards, reward -> {
                    CompoundTag tag = new CompoundTag();
                    reward.writeInitialData(tag);
                    return tag;
                })
        );
    }

    @Override
    public void deserialize(CompoundTag data) {
        if (this.disposed) return;
        this.hasSentCompletion = data.getBoolean("completed");
        this.hasSentTrigger = data.getBoolean("triggered");
        if (this.providerRule != null && data.contains("provider_binding", Tag.TAG_COMPOUND)) {
            this.providerBinding = QuestProviderBinding.load(data.getCompound("provider_binding"));
            this.providerTurnedIn = this.providerBinding != null && data.getBoolean("provider_turned_in");
        } else {
            this.providerBinding = null;
            this.providerTurnedIn = false;
        }

        // `repeatable` and `global` may exist in inherited saves, but current
        // definition data is authoritative. Applying persisted values here made
        // an editor/config change to either flag silently revert on reload and
        // could keep a quest globally synchronized after its definition stopped
        // being global.

        if (this.providerRule != null && this.providerBinding == null) {
            this.hasSentTrigger = false;
        } else if (this.prerequisites.isEmpty()) {
            this.hasSentTrigger = true;
        }

        List<Tag> reqData = data.contains("prerequisites") ? data.getList("prerequisites", Tag.TAG_COMPOUND)
                : data.getList("requirements", Tag.TAG_COMPOUND);
        for (int i = 0; i < Math.min(reqData.size(), this.prerequisites.size()); i++) {
            this.prerequisites.get(i).deserialize((CompoundTag) reqData.get(i));
        }

        List<Tag> objectiveData = data.getList("objectives", Tag.TAG_COMPOUND);
        for (int i = 0; i < Math.min(objectiveData.size(), this.objectives.size()); i++) {
            this.objectives.get(i).deserialize((CompoundTag) objectiveData.get(i));
        }

        List<Tag> failureData = data.getList("failures", Tag.TAG_COMPOUND);
        for (int i = 0; i < Math.min(failureData.size(), this.failureConditions.size()); i++) {
            this.failureConditions.get(i).deserialize((CompoundTag) failureData.get(i));
        }

        List<Tag> rewardData = data.getList("rewards", Tag.TAG_COMPOUND);
        for (int i = 0; i < Math.min(rewardData.size(), this.rewards.size()); i++) {
            this.rewards.get(i).deserialize((CompoundTag) rewardData.get(i));
        }
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("completed", this.hasSentCompletion);
        tag.putBoolean("triggered", this.hasSentTrigger);
        tag.putBoolean("repeatable", this.repeatable);
        tag.putBoolean("global", this.global);
        tag.putBoolean("provider_turned_in", this.providerTurnedIn);
        if (this.providerRule != null && this.providerBinding != null) {
            tag.put("provider_binding", this.providerBinding.save());
        }
        tag.put("prerequisites", Util.toNbtList(this.prerequisites, Objective::serialize));
        tag.put("objectives", Util.toNbtList(this.objectives, Objective::serialize));
        tag.put("failures", Util.toNbtList(this.failureConditions, Objective::serialize));
        tag.put("rewards", Util.toNbtList(this.rewards, Reward::serialize));
        return tag;
    }
}
