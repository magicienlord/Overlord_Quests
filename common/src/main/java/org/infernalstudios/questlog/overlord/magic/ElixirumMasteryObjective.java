package org.infernalstudios.questlog.overlord.magic;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.infernalstudios.questlog.core.quests.EditorMetadata;
import org.infernalstudios.questlog.core.quests.QuestObjectiveRegistry;
import org.infernalstudios.questlog.core.quests.objectives.Objective;

import java.lang.reflect.Method;

/**
 * Observes Ars Elixirum's own persistent per-player mastery level without taking
 * ownership of that progression system or adding a hard Elixirum dependency.
 *
 * The installed Elixirum 0.12.0 OVERLORD REIGN build persists an AlchemyProfile
 * through Archivist. ServerAlchemy exposes profileOf(Player), AlchemyProfile
 * exposes mastery(), and AlchemyMastery exposes level(). This objective reads
 * that durable owner state once per second and treats required_amount as the
 * minimum mastery level.
 */
public final class ElixirumMasteryObjective extends Objective {

    private static final String SERVER_ALCHEMY = "dev.obscuria.elixirum.server.alchemy.ServerAlchemy";
    private static final String ALCHEMY_PROFILE = "dev.obscuria.elixirum.api.codex.AlchemyProfile";
    private static final String ALCHEMY_MASTERY = "dev.obscuria.elixirum.api.codex.profile.AlchemyMastery";

    private static volatile Access access;
    private static volatile boolean accessResolved;

    private int ticksUntilCheck;

    public static void register() {
        QuestObjectiveRegistry.register(
                new ResourceLocation("questlog", "elixirum_mastery"),
                ElixirumMasteryObjective::new,
                new EditorMetadata(null, null, "required_amount")
        );
    }

    public ElixirumMasteryObjective(JsonObject definition) {
        super(definition);
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onPlayerTick);
    }

    private void onPlayerTick(TriggerPlayerEvent.Tick event) {
        if (!(event.player instanceof ServerPlayer player)
                || !this.isActiveForPlayer(player)
                || this.isCompleted()) {
            return;
        }

        if (--this.ticksUntilCheck > 0) {
            return;
        }
        this.ticksUntilCheck = 20;

        Integer level = masteryLevel(player);
        if (level != null) {
            this.setUnits(level);
        }
    }

    static Integer masteryLevel(ServerPlayer player) {
        Access resolved = resolveAccess();
        if (resolved == null) {
            return null;
        }

        try {
            Object alchemy = resolved.getAlchemy.invoke(null, player.getServer());
            if (alchemy == null) {
                return null;
            }

            Object profile = resolved.profileOf.invoke(alchemy, player);
            if (profile == null) {
                return null;
            }

            Object mastery = resolved.mastery.invoke(profile);
            if (mastery == null) {
                return null;
            }

            Object value = resolved.level.invoke(mastery);
            return value instanceof Integer level ? Math.max(0, level) : null;
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            // Elixirum is optional. A changed/absent API must leave the objective
            // unsatisfied rather than crash Questlog or fabricate campaign state.
            return null;
        }
    }

    private static Access resolveAccess() {
        if (accessResolved) {
            return access;
        }

        synchronized (ElixirumMasteryObjective.class) {
            if (accessResolved) {
                return access;
            }

            try {
                Class<?> serverAlchemy = Class.forName(SERVER_ALCHEMY);
                Class<?> alchemyProfile = Class.forName(ALCHEMY_PROFILE);
                Class<?> alchemyMastery = Class.forName(ALCHEMY_MASTERY);

                Method getAlchemy = serverAlchemy.getMethod("get", MinecraftServer.class);
                Method profileOf = serverAlchemy.getMethod("profileOf", Player.class);
                Method mastery = alchemyProfile.getMethod("mastery");
                Method level = alchemyMastery.getMethod("level");

                access = new Access(getAlchemy, profileOf, mastery, level);
            } catch (ClassNotFoundException | NoSuchMethodException | LinkageError ignored) {
                access = null;
            }

            accessResolved = true;
            return access;
        }
    }

    private record Access(Method getAlchemy, Method profileOf, Method mastery, Method level) {
    }
}
