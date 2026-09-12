package org.infernalstudios.questlog.overlord.provider;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.infernalstudios.questlog.Questlog;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Optional, reflection-only native role matching for provider entities whose
 * source mods expose stable profession data but are not compile-time Questlog
 * dependencies.
 *
 * A failed optional bridge always fails closed. Explicit `overlord_role:*`
 * scoreboard tags remain available for authored roles that have no stable
 * owning-mod profession API.
 */
public final class QuestProviderNativeRoleBridge {
    private static final ResourceLocation RIBBIT_ENTITY = new ResourceLocation("ribbits", "ribbit");
    private static final String RIBBIT_ENTITY_CLASS = "com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity";
    private static final Set<String> REPORTED_FAILURES = ConcurrentHashMap.newKeySet();

    private QuestProviderNativeRoleBridge() {
    }

    public static boolean matches(Entity entity, String role) {
        if (entity == null || role == null || role.isBlank()) {
            return false;
        }

        ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (RIBBIT_ENTITY.equals(entityId)) {
            return matchesRibbitProfession(entity, role);
        }
        return false;
    }

    /**
     * Ribbits 3.0.5 exposes profession through:
     * RibbitEntity#getRibbitData() -> RibbitData#getProfession() ->
     * RibbitProfession#getId(). Resolve that public source contract lazily so
     * Questlog remains loadable when Ribbits is absent.
     */
    private static boolean matchesRibbitProfession(Entity entity, String role) {
        ResourceLocation expected = normalizeRole("ribbits", role);
        if (expected == null) {
            return false;
        }

        try {
            Class<?> ribbitClass = Class.forName(RIBBIT_ENTITY_CLASS);
            if (!ribbitClass.isInstance(entity)) {
                return false;
            }

            Method getRibbitData = ribbitClass.getMethod("getRibbitData");
            Object ribbitData = getRibbitData.invoke(entity);
            if (ribbitData == null) {
                reportOnce("ribbit_data_missing", null);
                return false;
            }

            Method getProfession = ribbitData.getClass().getMethod("getProfession");
            Object profession = getProfession.invoke(ribbitData);
            if (profession == null) {
                reportOnce("ribbit_profession_missing", null);
                return false;
            }

            Method getId = profession.getClass().getMethod("getId");
            Object rawId = getId.invoke(profession);
            if (!(rawId instanceof ResourceLocation professionId)) {
                reportOnce("ribbit_profession_id_invalid", null);
                return false;
            }
            return expected.equals(professionId);
        } catch (ClassNotFoundException exception) {
            return false;
        } catch (ReflectiveOperationException | LinkageError exception) {
            reportOnce("ribbit_reflection_error", exception);
            return false;
        }
    }

    private static ResourceLocation normalizeRole(String defaultNamespace, String role) {
        String value = role.trim();
        if (value.isEmpty()) {
            return null;
        }
        return value.contains(":")
                ? ResourceLocation.tryParse(value)
                : ResourceLocation.tryParse(defaultNamespace + ":" + value);
    }

    private static void reportOnce(String reason, Throwable throwable) {
        if (!REPORTED_FAILURES.add(reason)) {
            return;
        }
        if (throwable == null) {
            Questlog.LOGGER.error("Failed to resolve optional provider native role ({})", reason);
        } else {
            Questlog.LOGGER.error("Failed to resolve optional provider native role ({})", reason, throwable);
        }
    }
}
