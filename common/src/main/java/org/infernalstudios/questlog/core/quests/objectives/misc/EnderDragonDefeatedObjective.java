package org.infernalstudios.questlog.core.quests.objectives.misc;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import org.infernalstudios.questlog.core.quests.objectives.Objective;

/**
 * Retrospective world-state objective for the vanilla Ender Dragon's first defeat.
 *
 * The vanilla minecraft:end/kill_dragon advancement belongs to the player credited
 * with the final blow. OVERLORD REIGN instead needs to recognize the persistent
 * End fight state so a legitimate prior Dragon defeat is not invalidated by who
 * dealt that blow or by the quest becoming active later.
 *
 * This objective reads only Minecraft's authoritative EndDragonFight state. It
 * does not summon, respawn, kill, or otherwise modify the Dragon fight.
 */
public final class EnderDragonDefeatedObjective extends Objective {
    private int ticksUntilCheck = 0;

    public EnderDragonDefeatedObjective(JsonObject definition) {
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

        ServerLevel end = player.getServer().getLevel(Level.END);
        if (end == null) {
            return;
        }

        EndDragonFight fight = end.getDragonFight();
        if (fight != null && fight.hasPreviouslyKilledDragon()) {
            this.setUnits(1);
        }
    }
}
