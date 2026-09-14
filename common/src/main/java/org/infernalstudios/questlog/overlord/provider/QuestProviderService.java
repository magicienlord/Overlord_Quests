package org.infernalstudios.questlog.overlord.provider;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.overlord.narrative.OverlordNarrativeState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Server-authoritative eligibility, accept, and turn-in operations for NPC sidequests. */
public final class QuestProviderService {
    public static final double MAX_INTERACTION_DISTANCE_SQR = 64.0D;

    public enum InteractionState {
        AVAILABLE(0),
        IN_PROGRESS(1),
        READY_TO_TURN_IN(2),
        FAILED(3),
        COMPLETED(4);

        private final int wireId;

        InteractionState(int wireId) {
            this.wireId = wireId;
        }

        public int wireId() {
            return this.wireId;
        }

        public static InteractionState fromWireId(int wireId) {
            for (InteractionState state : values()) {
                if (state.wireId == wireId) return state;
            }
            throw new IllegalArgumentException("Unknown provider quest interaction state id: " + wireId);
        }
    }

    public record InteractionEntry(ResourceLocation questId, InteractionState state) {
        public InteractionEntry {
            if (questId == null || state == null) {
                throw new IllegalArgumentException("provider interaction entries require quest id and state");
            }
        }
    }

    private QuestProviderService() {
    }

    public static List<Quest> availableQuests(QuestManager manager, Entity provider) {
        List<Quest> result = new ArrayList<>();
        if (manager == null || provider == null || manager.isClient() || !manager.isActive()) return result;
        for (Quest quest : manager.getAllQuests()) {
            if (canAccept(quest, provider)) result.add(quest);
        }
        result.sort(questOrder());
        return result;
    }

    public static List<InteractionEntry> interactionEntries(QuestManager manager, Entity provider) {
        List<InteractionEntry> result = new ArrayList<>();
        if (manager == null || provider == null || manager.isClient() || !manager.isActive()) return result;

        for (Quest quest : manager.getAllQuests()) {
            if (canTurnIn(quest, provider)) {
                result.add(new InteractionEntry(quest.getId(), InteractionState.READY_TO_TURN_IN));
                continue;
            }
            if (canAccept(quest, provider)) {
                result.add(new InteractionEntry(quest.getId(), InteractionState.AVAILABLE));
                continue;
            }

            QuestProviderRule rule = quest.getProviderRule();
            QuestProviderBinding binding = quest.getProviderBinding();
            if (rule == null || binding == null) continue;

            if (quest.isCompleted()) {
                if (binding.matches(provider) && rule.dialogue().hasLinesFor(InteractionState.COMPLETED)) {
                    result.add(new InteractionEntry(quest.getId(), InteractionState.COMPLETED));
                }
                continue;
            }

            boolean relatedProvider = binding.matches(provider)
                    || ((!rule.lockToProvider() || rule.turnInMode() == QuestProviderRule.TurnInMode.ANY_ELIGIBLE)
                    && rule.matchesEntity(provider));
            if (!relatedProvider) continue;

            result.add(new InteractionEntry(
                    quest.getId(),
                    quest.isFailed() ? InteractionState.FAILED : InteractionState.IN_PROGRESS
            ));
        }

        result.sort(Comparator
                .comparingInt((InteractionEntry entry) -> questSortOrder(manager, entry.questId()))
                .thenComparing(entry -> entry.questId().toString()));
        return result;
    }

    public static boolean canAccept(Quest quest, Entity provider) {
        if (quest == null || provider == null || quest.manager.isClient() || quest.isDisposed()) return false;
        QuestProviderRule rule = quest.getProviderRule();
        if (rule == null || quest.getProviderBinding() != null || quest.isFailed()) return false;
        if (!rule.matchesEntity(provider) || !quest.arePrerequisitesComplete()) return false;
        if (!(quest.manager.player instanceof ServerPlayer player)) return false;
        if (!rule.matchesPlayer(player)) return false;
        if (!UmvuthiAudienceBridge.allowsProviderInteraction(provider, player)) return false;
        if (!PiglinChieftainAudienceBridge.allowsProviderInteraction(provider, player)) return false;

        for (ResourceLocation unlockId : rule.unlockQuests()) {
            Quest unlock = quest.manager.getQuest(unlockId);
            if (unlock == null || !unlock.isCompleted()) return false;
        }

        OverlordNarrativeState narrative = OverlordNarrativeState.get(player.server);
        for (ResourceLocation fact : rule.requiredFacts()) {
            if (!narrative.hasFact(fact)) return false;
        }
        for (ResourceLocation fact : rule.forbiddenFacts()) {
            if (narrative.hasFact(fact)) return false;
        }
        for (Map.Entry<ResourceLocation, Set<ResourceLocation>> requirement : rule.requiredDispositions().entrySet()) {
            if (!requirement.getValue().contains(narrative.getDisposition(requirement.getKey()))) return false;
        }
        return true;
    }

    public static boolean accept(Quest quest, Entity provider) {
        if (!canAccept(quest, provider)) return false;
        quest.bindProvider(QuestProviderBinding.fromEntity(provider, quest.getProviderRule()));
        return true;
    }

    public static boolean canTurnIn(Quest quest, Entity provider) {
        if (quest == null || provider == null || quest.manager.isClient() || quest.isDisposed()) return false;
        QuestProviderRule rule = quest.getProviderRule();
        QuestProviderBinding binding = quest.getProviderBinding();
        if (rule == null || binding == null || !rule.requiresTurnIn()) return false;
        if (!quest.isReadyForProviderTurnIn()) return false;
        if (!(quest.manager.player instanceof ServerPlayer player)) return false;
        if (!rule.matchesPlayer(player)) return false;
        if (!UmvuthiAudienceBridge.allowsProviderInteraction(provider, player)) return false;
        if (!PiglinChieftainAudienceBridge.allowsProviderInteraction(provider, player)) return false;

        return switch (rule.turnInMode()) {
            case NONE -> false;
            case SAME_PROVIDER -> binding.matches(provider);
            case ANY_ELIGIBLE -> rule.matchesEntity(provider);
        };
    }

    public static boolean turnIn(Quest quest, Entity provider) {
        if (!canTurnIn(quest, provider)) return false;
        quest.completeProviderTurnIn();
        return true;
    }

    public static boolean isWithinInteractionRange(ServerPlayer player, Entity provider) {
        return player != null
                && provider != null
                && player.level() == provider.level()
                && player.distanceToSqr(provider) <= MAX_INTERACTION_DISTANCE_SQR;
    }

    private static Comparator<Quest> questOrder() {
        return Comparator
                .comparingInt((Quest quest) -> quest.getDisplay().getSortOrder())
                .thenComparing(quest -> quest.getId().toString());
    }

    private static int questSortOrder(QuestManager manager, ResourceLocation questId) {
        Quest quest = manager.getQuest(questId);
        return quest == null ? Integer.MAX_VALUE : quest.getDisplay().getSortOrder();
    }
}
