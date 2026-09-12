package org.infernalstudios.questlog.overlord.minions;

import net.minecraft.server.MinecraftServer;
import org.infernalstudios.questlog.Questlog;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bounded optional bridge to the stable OVERLORD Minions progression API.
 *
 * The public API contract is owned by the overlord_minions mod. Questlog keeps
 * the dependency optional at class-link time so development and validation can
 * still run without that mod installed. Only the documented public API classes
 * and methods are resolved here.
 */
public final class OverlordMinionProgressionBridge {
    private static final String API_CLASS = "com.overlordreign.minions.api.OverlordMinionProgression";
    private static final String SLOT_CLASS = "com.overlordreign.minions.progression.MinionSlot";
    private static final Set<String> REPORTED_QUERY_ERRORS = ConcurrentHashMap.newKeySet();

    private OverlordMinionProgressionBridge() {
    }

    public enum Slot {
        RED,
        GREEN,
        BLUE;

        public static Slot parse(String value) {
            if (value == null) {
                throw new IllegalArgumentException("Minion unlock slot must not be null");
            }
            try {
                return Slot.valueOf(value.trim().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("Minion unlock slot must be red, green, or blue", exception);
            }
        }

        public String serializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum UnlockResult {
        UNLOCKED,
        ALREADY_UNLOCKED,
        OUT_OF_ORDER,
        BOOTSTRAP_OWNED_BY_STAFF,
        API_UNAVAILABLE,
        API_ERROR
    }

    public static UnlockResult unlock(MinecraftServer server, Slot slot) {
        if (server == null || slot == null) {
            return UnlockResult.API_ERROR;
        }

        try {
            Class<?> slotClass = Class.forName(SLOT_CLASS);
            Class<?> apiClass = Class.forName(API_CLASS);
            Object slotValue = findEnumConstant(slotClass, slot.name());
            if (slotValue == null) {
                Questlog.LOGGER.error("OVERLORD Minions API is present but slot {} is missing", slot.name());
                return UnlockResult.API_ERROR;
            }

            Method unlockMethod = apiClass.getMethod("unlock", MinecraftServer.class, slotClass);
            Object rawResult = unlockMethod.invoke(null, server, slotValue);
            if (!(rawResult instanceof Enum<?> resultEnum)) {
                Questlog.LOGGER.error("OVERLORD Minions unlock API returned a non-enum result for slot {}", slot.serializedName());
                return UnlockResult.API_ERROR;
            }

            try {
                return UnlockResult.valueOf(resultEnum.name());
            } catch (IllegalArgumentException exception) {
                Questlog.LOGGER.error(
                        "OVERLORD Minions unlock API returned unknown result {} for slot {}",
                        resultEnum.name(),
                        slot.serializedName()
                );
                return UnlockResult.API_ERROR;
            }
        } catch (ClassNotFoundException exception) {
            return UnlockResult.API_UNAVAILABLE;
        } catch (ReflectiveOperationException | LinkageError exception) {
            Questlog.LOGGER.error("Failed to invoke OVERLORD Minions progression API for slot {}", slot.serializedName(), exception);
            return UnlockResult.API_ERROR;
        }
    }

    /**
     * Reads the owning mod's persistent slot state without copying it into
     * Questlog. A missing or incompatible optional API is treated as locked so
     * later campaign tiers cannot open merely because an earlier quest completed
     * while its external unlock reward was still pending.
     */
    public static boolean isUnlocked(MinecraftServer server, Slot slot) {
        if (server == null || slot == null) {
            return false;
        }

        try {
            Class<?> slotClass = Class.forName(SLOT_CLASS);
            Class<?> apiClass = Class.forName(API_CLASS);
            Object slotValue = findEnumConstant(slotClass, slot.name());
            if (slotValue == null) {
                reportQueryErrorOnce(slot, "slot_missing", null);
                return false;
            }

            Method isUnlockedMethod = apiClass.getMethod("isUnlocked", MinecraftServer.class, slotClass);
            Object rawResult = isUnlockedMethod.invoke(null, server, slotValue);
            if (rawResult instanceof Boolean unlocked) {
                return unlocked;
            }

            reportQueryErrorOnce(slot, "non_boolean_result", null);
            return false;
        } catch (ClassNotFoundException exception) {
            return false;
        } catch (ReflectiveOperationException | LinkageError exception) {
            reportQueryErrorOnce(slot, "reflection_error", exception);
            return false;
        }
    }

    private static void reportQueryErrorOnce(Slot slot, String reason, Throwable throwable) {
        String key = slot.name() + ":" + reason;
        if (!REPORTED_QUERY_ERRORS.add(key)) {
            return;
        }
        if (throwable == null) {
            Questlog.LOGGER.error(
                    "Failed to query OVERLORD Minions progression API for slot {} ({})",
                    slot.serializedName(),
                    reason
            );
        } else {
            Questlog.LOGGER.error(
                    "Failed to query OVERLORD Minions progression API for slot {} ({})",
                    slot.serializedName(),
                    reason,
                    throwable
            );
        }
    }

    private static Object findEnumConstant(Class<?> enumClass, String name) {
        if (!enumClass.isEnum()) {
            return null;
        }
        Object[] constants = enumClass.getEnumConstants();
        if (constants == null) {
            return null;
        }
        for (Object constant : constants) {
            if (constant instanceof Enum<?> enumConstant && enumConstant.name().equals(name)) {
                return constant;
            }
        }
        return null;
    }
}
