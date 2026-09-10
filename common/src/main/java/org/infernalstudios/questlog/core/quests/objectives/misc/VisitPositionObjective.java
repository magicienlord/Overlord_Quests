package org.infernalstudios.questlog.core.quests.objectives.misc;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.util.JsonUtils;
import org.infernalstudios.questlog.util.Util;
import org.jetbrains.annotations.Nullable;

public class VisitPositionObjective extends Objective {

    private final BoundingBox bounds;

    /**
     * Optional dimension guard. Upstream visit_position matched coordinates in
     * every dimension, which makes a world-specific coordinate objective unsafe
     * because identical x/y/z positions can exist in multiple dimensions.
     * Leaving this field absent preserves the original cross-dimensional behavior.
     */
    @Nullable
    private final ResourceLocation dimension;

    // Checks every second for performance
    private int ticksUntilCheck = 0;

    public VisitPositionObjective(JsonObject definition) {
        super(definition);
        this.bounds = Util.bbFromJson(definition.get("bounds"));
        String dimensionId = JsonUtils.getOrDefault(definition, "dimension", (String) null);
        this.dimension = dimensionId == null ? null : new ResourceLocation(dimensionId);
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onPlayerMove);
    }

    private void onPlayerMove(TriggerPlayerEvent.Tick event) {
        if (this.isCompleted() || this.getParent() == null) return;
        if (event.player instanceof ServerPlayer player
                && this.getParent().manager.player != null
                && this.getParent().manager.player.getUUID().equals(player.getUUID())
                && --ticksUntilCheck <= 0) {
            if ((this.dimension == null || player.level().dimension().location().equals(this.dimension))
                    && this.bounds.isInside(event.player.blockPosition())) {
                this.setUnits(this.getUnits() + 1);
            }
            ticksUntilCheck = 20;
        }
    }
}
