package org.infernalstudios.questlog.overlord.reaction;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import org.infernalstudios.questlog.Questlog;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Optional Forge integrations for the small system-reaction assignments in the
 * OVERLORD REIGN quest authority. All external APIs are accessed reflectively so
 * none of these content mods becomes a Questlog hard dependency.
 */
public final class OverlordSystemReactionBridgeForge {
    private static final String ESO_MOD = "enchanting_system_overhaul";
    private static final String LEVELUP_MOD = "levelup";
    private static final String PUFFISH_SKILLS_MOD = "puffish_skills";
    private static final String RPG_SKILL_TREES_MOD = "rpg_skill_trees";
    private static final String SOL_CARROT_MOD = "solcarrot";

    private static Capability<?> levelUpCapability;
    private static Method levelUpStatsGetter;
    private static Method solCarrotGetFoodList;
    private static Method solCarrotGetEatenFoodCount;
    private static Object pufferfishSkillUnlockCallback;

    private static boolean levelUpWarningLogged;
    private static boolean solCarrotWarningLogged;
    private static boolean pufferfishWarningLogged;

    private OverlordSystemReactionBridgeForge() {
    }

    public static void initialize() {
        initializeLevelUp();
        initializeSolCarrot();
        initializePufferfishSkillTrees();
    }

    @SubscribeEvent
    public static void onEnchantingTableInteraction(PlayerInteractEvent.RightClickBlock event) {
        if (!ModList.get().isLoaded(ESO_MOD)
                || event.getHand() != InteractionHand.MAIN_HAND
                || event.getLevel().isClientSide()
                || !(event.getEntity() instanceof ServerPlayer player)
                || !event.getLevel().getBlockState(event.getPos()).is(Blocks.ENCHANTING_TABLE)) {
            return;
        }

        OverlordSystemReactions.trigger(player, OverlordSystemReactions.ENCHANTING_SYSTEM_OVERHAUL);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END
                || event.player.level().isClientSide()
                || !(event.player instanceof ServerPlayer player)
                || player.tickCount % 20 != 0
                || !ModList.get().isLoaded(LEVELUP_MOD)
                || OverlordSystemReactions.hasShown(player, OverlordSystemReactions.LEVELUP)) {
            return;
        }

        checkLevelUp(player);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onFoodFinished(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
                || !ModList.get().isLoaded(SOL_CARROT_MOD)) {
            return;
        }

        MinecraftServer server = player.getServer();
        if (server != null) {
            // Defer until the Finish event has completely unwound so SolCarrot's
            // own tracker has committed the newly eaten distinct food first.
            server.execute(() -> checkSolCarrot(player));
        }
    }

    private static void initializeLevelUp() {
        if (!ModList.get().isLoaded(LEVELUP_MOD)) return;
        try {
            Class<?> providerClass = Class.forName("github.catchaos8.levelup.stats.PlayerStatsProvider");
            Object capability = providerClass.getField("PLAYER_STATS").get(null);
            if (!(capability instanceof Capability<?> forgeCapability)) {
                throw new IllegalStateException("LevelUP PLAYER_STATS is not a Forge Capability");
            }
            levelUpCapability = forgeCapability;
            Class<?> statsClass = Class.forName("github.catchaos8.levelup.stats.PlayerStats");
            levelUpStatsGetter = statsClass.getMethod("getStatsBaseArr");
        } catch (ReflectiveOperationException | LinkageError | RuntimeException e) {
            warnLevelUpOnce(e);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void checkLevelUp(ServerPlayer player) {
        if (levelUpCapability == null || levelUpStatsGetter == null) {
            initializeLevelUp();
            if (levelUpCapability == null || levelUpStatsGetter == null) return;
        }

        try {
            LazyOptional<?> optional = player.getCapability((Capability) levelUpCapability);
            Object stats = optional.resolve().orElse(null);
            if (stats == null) return;
            Object values = levelUpStatsGetter.invoke(stats);
            if (!(values instanceof float[] baseStats)) return;
            for (float baseStat : baseStats) {
                if (baseStat > 0.0F) {
                    OverlordSystemReactions.trigger(player, OverlordSystemReactions.LEVELUP);
                    return;
                }
            }
        } catch (ReflectiveOperationException | LinkageError | RuntimeException e) {
            warnLevelUpOnce(e);
        }
    }

    private static void initializeSolCarrot() {
        if (!ModList.get().isLoaded(SOL_CARROT_MOD)) return;
        try {
            Class<?> foodListClass = Class.forName("com.cazsius.solcarrot.tracking.FoodList");
            solCarrotGetFoodList = foodListClass.getMethod("get", Player.class);
            solCarrotGetEatenFoodCount = foodListClass.getMethod("getEatenFoodCount");
        } catch (ReflectiveOperationException | LinkageError | RuntimeException e) {
            warnSolCarrotOnce(e);
        }
    }

    private static void checkSolCarrot(ServerPlayer player) {
        if (solCarrotGetFoodList == null || solCarrotGetEatenFoodCount == null) {
            initializeSolCarrot();
            if (solCarrotGetFoodList == null || solCarrotGetEatenFoodCount == null) return;
        }

        try {
            Object foodList = solCarrotGetFoodList.invoke(null, player);
            Object countValue = solCarrotGetEatenFoodCount.invoke(foodList);
            if (!(countValue instanceof Integer count)) return;

            ResourceLocation reactionId = switch (count) {
                case 10 -> OverlordSystemReactions.SPICE_OF_LIFE_10;
                case 25 -> OverlordSystemReactions.SPICE_OF_LIFE_25;
                case 50 -> OverlordSystemReactions.SPICE_OF_LIFE_50;
                case 75 -> OverlordSystemReactions.SPICE_OF_LIFE_75;
                case 100 -> OverlordSystemReactions.SPICE_OF_LIFE_100;
                default -> null;
            };
            if (reactionId != null) {
                OverlordSystemReactions.trigger(player, reactionId);
            }
        } catch (ReflectiveOperationException | LinkageError | RuntimeException e) {
            warnSolCarrotOnce(e);
        }
    }

    private static void initializePufferfishSkillTrees() {
        if (!ModList.get().isLoaded(PUFFISH_SKILLS_MOD)
                || !ModList.get().isLoaded(RPG_SKILL_TREES_MOD)
                || pufferfishSkillUnlockCallback != null) {
            return;
        }

        try {
            Class<?> apiClass = Class.forName("net.puffish.skillsmod.api.SkillsAPI");
            Class<?> callbackType = Class.forName("net.puffish.skillsmod.api.Events$SkillUnlock");
            Method register = apiClass.getMethod("registerSkillUnlockEvent", callbackType);

            pufferfishSkillUnlockCallback = Proxy.newProxyInstance(
                    callbackType.getClassLoader(),
                    new Class<?>[]{callbackType},
                    (proxy, method, args) -> {
                        if (method.getDeclaringClass() == Object.class) {
                            return switch (method.getName()) {
                                case "toString" -> "OverlordSystemReactionSkillUnlockCallback";
                                case "hashCode" -> System.identityHashCode(proxy);
                                case "equals" -> proxy == (args == null || args.length == 0 ? null : args[0]);
                                default -> null;
                            };
                        }

                        if ("onSkillUnlock".equals(method.getName())
                                && args != null
                                && args.length >= 2
                                && args[0] instanceof ServerPlayer player
                                && args[1] instanceof ResourceLocation category
                                && RPG_SKILL_TREES_MOD.equals(category.getNamespace())) {
                            OverlordSystemReactions.trigger(player, OverlordSystemReactions.RPG_SKILL_TREES);
                        }
                        return null;
                    }
            );
            register.invoke(null, pufferfishSkillUnlockCallback);
        } catch (ReflectiveOperationException | LinkageError | RuntimeException e) {
            warnPufferfishOnce(e);
        }
    }

    private static void warnLevelUpOnce(Throwable error) {
        if (!levelUpWarningLogged) {
            levelUpWarningLogged = true;
            Questlog.LOGGER.warn("LevelUP system reaction integration is unavailable", error);
        }
    }

    private static void warnSolCarrotOnce(Throwable error) {
        if (!solCarrotWarningLogged) {
            solCarrotWarningLogged = true;
            Questlog.LOGGER.warn("Spice of Life system reaction integration is unavailable", error);
        }
    }

    private static void warnPufferfishOnce(Throwable error) {
        if (!pufferfishWarningLogged) {
            pufferfishWarningLogged = true;
            Questlog.LOGGER.warn("RPG Skill Trees system reaction integration is unavailable", error);
        }
    }
}
