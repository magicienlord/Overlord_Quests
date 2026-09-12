package org.infernalstudios.questlog.overlord.provider;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

/**
 * Narrow protection contract for deliberately authored quest-critical anchor NPCs.
 *
 * Protection is opt-in through one persistent entity scoreboard tag. It is not
 * inferred from entity type, profession, provider eligibility, civilization, or
 * quest state, so ordinary procedural mobs are never protected accidentally.
 *
 * World/campaign integration may remove the tag when an authored destructive
 * route intentionally makes the anchor killable. The quest engine deliberately
 * does not invent that transition on its own.
 */
public final class QuestAnchorProtection {
    public static final String PROTECTED_TAG = "overlord_quest_protected";

    private QuestAnchorProtection() {
    }

    public static boolean isProtected(Entity entity) {
        return entity != null && entity.getTags().contains(PROTECTED_TAG);
    }

    /**
     * Quest-critical Mob anchors should not disappear through ordinary despawn
     * while their protection tag is present. Scoreboard tags themselves persist
     * in entity NBT, so this can safely be re-applied whenever an entity loads.
     */
    public static void applyPersistence(Entity entity) {
        if (isProtected(entity) && entity instanceof Mob mob) {
            mob.setPersistenceRequired();
        }
    }
}
