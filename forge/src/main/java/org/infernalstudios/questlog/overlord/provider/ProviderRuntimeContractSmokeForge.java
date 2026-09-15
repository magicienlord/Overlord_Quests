package org.infernalstudios.questlog.overlord.provider;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.overlord.narrative.OverlordNarrativeState;

import java.util.List;
import java.util.UUID;

/**
 * Opt-in dedicated-server runtime qualification for the NPC provider contract.
 *
 * This hook is dormant in normal gameplay and is registered only by the dedicated
 * provider runtime smoke run. The fake player is used only for the server-side
 * eligibility surface. Outbound quest sync is suppressed by the test manager so
 * a fake network connection cannot affect the result.
 */
public final class ProviderRuntimeContractSmokeForge {
    public static final String ENABLE_PROPERTY = "questlog.providerRuntimeSmoke";
    public static final String PASS_MARKER = "OVERLORD_PROVIDER_RUNTIME_SMOKE: PASS";

    private static final ResourceLocation QUEST_ID = new ResourceLocation(Questlog.MODID, "provider_runtime_smoke");
    private static final ResourceLocation REQUIRED_FACT = new ResourceLocation(Questlog.MODID, "provider_runtime_smoke_allowed");
    private static final ResourceLocation FORBIDDEN_FACT = new ResourceLocation(Questlog.MODID, "provider_runtime_smoke_blocked");

    private ProviderRuntimeContractSmokeForge() {
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        if (!Boolean.getBoolean(ENABLE_PROPERTY)) return;

        OverlordNarrativeState narrative = OverlordNarrativeState.get(event.getServer());
        narrative.clearFact(REQUIRED_FACT);
        narrative.clearFact(FORBIDDEN_FACT);

        try {
            run(event.getServer().overworld(), narrative);
            Questlog.LOGGER.info(PASS_MARKER);
        } catch (Throwable throwable) {
            Questlog.LOGGER.error("OVERLORD_PROVIDER_RUNTIME_SMOKE: FAIL", throwable);
            if (throwable instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            throw new IllegalStateException("Provider runtime contract smoke failed", throwable);
        } finally {
            narrative.clearFact(REQUIRED_FACT);
            narrative.clearFact(FORBIDDEN_FACT);
        }
    }

    private static void run(ServerLevel level, OverlordNarrativeState narrative) {
        FakePlayer player = FakePlayerFactory.get(
                level,
                new GameProfile(UUID.fromString("8fb98644-c2d0-4ed4-b4f7-79d41a7c86d3"), "ProviderSmoke")
        );
        player.setPos(0.5D, 80.0D, 0.5D);

        Villager providerA = createVillager(level, VillagerProfession.FARMER, 1.5D, 80.0D, 0.5D);
        Villager providerB = createVillager(level, VillagerProfession.FARMER, -1.5D, 80.0D, 0.5D);
        Villager wrongProfession = createVillager(level, VillagerProfession.LIBRARIAN, 2.5D, 80.0D, 0.5D);

        NoSyncQuestManager manager = new NoSyncQuestManager(player);
        Quest quest = createQuest(manager);

        require(!QuestProviderService.canAccept(quest, providerA), "required narrative fact did not gate acceptance");
        narrative.setFact(REQUIRED_FACT);
        require(QuestProviderService.canAccept(quest, providerA), "eligible farmer provider was rejected after required fact");
        require(!quest.getProviderRule().matchesEntity(wrongProfession), "vanilla villager profession role accepted a librarian as farmer");
        require(QuestProviderService.accept(quest, providerA), "provider acceptance failed");

        CompoundTag serialized = quest.serialize();
        NoSyncQuestManager reloadedManager = new NoSyncQuestManager(player);
        Quest reloaded = createQuest(reloadedManager);
        reloaded.deserialize(serialized);

        require(reloaded.getProviderBinding() != null, "provider binding was lost after quest serialization round-trip");
        require(reloaded.getProviderBinding().matches(providerA), "serialized provider UUID no longer matches the accepting provider");
        require(!reloaded.getProviderBinding().matches(providerB), "serialized provider UUID incorrectly matches a different provider");

        require(QuestProviderService.canTurnIn(reloaded, providerA), "same provider could not turn in ready quest");
        require(!QuestProviderService.canTurnIn(reloaded, providerB), "different provider bypassed SAME_PROVIDER UUID lock");
        require(QuestProviderService.turnIn(reloaded, providerA), "same-provider turn-in failed");

        List<QuestProviderService.InteractionEntry> completedEntries = QuestProviderService.interactionEntries(reloadedManager, providerA);
        require(hasState(completedEntries, QuestProviderService.InteractionState.COMPLETED), "accepting provider did not expose completed dialogue state");
        require(!hasState(QuestProviderService.interactionEntries(reloadedManager, providerB), QuestProviderService.InteractionState.COMPLETED),
                "completed dialogue leaked to a different provider UUID");

        NoSyncQuestManager blockedManager = new NoSyncQuestManager(player);
        Quest blockedQuest = createQuest(blockedManager);
        narrative.setFact(FORBIDDEN_FACT);
        require(!QuestProviderService.canAccept(blockedQuest, providerA), "forbidden narrative fact did not reject provider acceptance");
        narrative.clearFact(FORBIDDEN_FACT);

        providerA.setPos(1.5D, 80.0D, 0.5D);
        require(QuestProviderService.isWithinInteractionRange(player, providerA), "near provider failed interaction-distance check");
        providerA.setPos(9.5D, 80.0D, 0.5D);
        require(!QuestProviderService.isWithinInteractionRange(player, providerA), "provider beyond eight blocks passed interaction-distance check");
    }

    private static Quest createQuest(NoSyncQuestManager manager) {
        JsonObject definition = JsonParser.parseString("""
                {
                  "title": "Provider Runtime Smoke",
                  "description": "Disposable provider runtime qualification fixture.",
                  "chapter": "main",
                  "objectives": [],
                  "rewards": [],
                  "provider": {
                    "entity_types": ["minecraft:villager"],
                    "role": "minecraft:farmer",
                    "required_facts": ["questlog:provider_runtime_smoke_allowed"],
                    "forbidden_facts": ["questlog:provider_runtime_smoke_blocked"],
                    "lock_to_provider": true,
                    "turn_in": "same_provider",
                    "dialogue": {
                      "offer": "offer",
                      "ready_to_turn_in": "ready",
                      "completed": "completed"
                    }
                  }
                }
                """).getAsJsonObject();

        Quest quest = Quest.create(definition, QUEST_ID, manager);
        CompoundTag initialData = new CompoundTag();
        quest.writeInitialData(initialData);
        quest.deserialize(initialData);
        manager.addQuest(quest);
        return quest;
    }

    private static Villager createVillager(ServerLevel level, VillagerProfession profession, double x, double y, double z) {
        Villager villager = EntityType.VILLAGER.create(level);
        if (villager == null) {
            throw new IllegalStateException("Could not create villager provider fixture");
        }
        villager.setVillagerData(villager.getVillagerData().setProfession(profession));
        villager.setPos(x, y, z);
        return villager;
    }

    private static boolean hasState(List<QuestProviderService.InteractionEntry> entries, QuestProviderService.InteractionState state) {
        return entries.stream().anyMatch(entry -> QUEST_ID.equals(entry.questId()) && entry.state() == state);
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }

    private static final class NoSyncQuestManager extends QuestManager {
        private NoSyncQuestManager(Player player) {
            super(player);
        }

        @Override
        public void sync() {
        }

        @Override
        public void sync(ResourceLocation id) {
        }
    }
}
