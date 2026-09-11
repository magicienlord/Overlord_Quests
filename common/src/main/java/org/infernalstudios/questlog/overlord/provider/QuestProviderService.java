package org.infernalstudios.questlog.overlord.provider;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.overlord.narrative.OverlordNarrativeState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Server-authoritative eligibility, accept, and turn-in operations for NPC sidequests. */
public final class QuestProviderService {
    public static final double MAX_INTERACTION_DISTANCE_SQR = 64.0D;

    private QuestProviderService() {
    }

    public static List<Quest> availableQuests(QuestManager manager, Entity provider) {
        List<Quest> result = new ArrayList<>();
        if (manager == null || provider == null || manager.isClient() || !manager.isActive()) {
            return result;
        }
        for (Quest quest : manager.getAllQuests()) {
            if (canAccept(quest, provider)) {
                result.add(quest);
            }
        }
        return result;
    }

    public static boolean canAccept(Quest quest, Entity provider) {
        if (quest == null || provider == null || quest.manager.isClient() || quest.isDisposed()) return false;
        QuestProviderRule rule = quest.getProviderRule();
        if (rule == null || quest.getProviderBinding() != null || quest.isFailed()) return false;
        if (!rule.matchesEntity(provider) || !quest.arePrerequisitesComplete()) return false;
        if (!(quest.manager.player instanceof ServerPlayer player)) return false;

        for (ResourceLocation unlockId : rule.unlockQuests()) {
            Quest unlock = quest.manager.getQuest(unlockId);
            if (unlock == null || !unlock.isCompleted()) return false;
        }

        OverlordNarrativeState narrative = OverlordNarrativeState.get(player.server);
        for (Map.Entry<ResourceLocation, Set<ResourceLocation>> requirement : rule.requiredDispositions().entrySet()) {
            if (!requirement.getValue().contains(narrative.getDisposition(requirement.getKey()))) {
                return false;
            }
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
}
