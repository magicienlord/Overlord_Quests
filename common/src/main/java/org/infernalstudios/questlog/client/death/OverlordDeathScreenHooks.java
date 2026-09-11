package org.infernalstudios.questlog.client.death;

import net.minecraft.client.player.LocalPlayer;
import org.infernalstudios.questlog.Questlog;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/** Compatibility vetoes for revival and knockout systems. */
public final class OverlordDeathScreenHooks {
    private static final Map<String, Predicate<LocalPlayer>> BLOCKERS = new LinkedHashMap<>();
    private static final Set<String> FAILED = new HashSet<>();

    private OverlordDeathScreenHooks() {
    }

    public static void registerBlocker(String id, Predicate<LocalPlayer> blocker) {
        BLOCKERS.put(Objects.requireNonNull(id), Objects.requireNonNull(blocker));
        FAILED.remove(id);
    }

    public static boolean isBlocked(LocalPlayer player) {
        if (player == null) return true;

        for (Map.Entry<String, Predicate<LocalPlayer>> entry : BLOCKERS.entrySet()) {
            if (FAILED.contains(entry.getKey())) {
                return true;
            }
            try {
                if (entry.getValue().test(player)) {
                    return true;
                }
            } catch (LinkageError | RuntimeException error) {
                FAILED.add(entry.getKey());
                Questlog.LOGGER.warn(
                        "Overlord death screen compatibility hook {} failed; leaving the foreign death flow untouched",
                        entry.getKey(), error
                );
                return true;
            }
        }
        return false;
    }
}
