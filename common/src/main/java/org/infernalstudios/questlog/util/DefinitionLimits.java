package org.infernalstudios.questlog.util;

import com.google.gson.JsonObject;

/**
 * Shared size contract for quest/chapter definitions that must cross Questlog's
 * FriendlyByteBuf UTF string fields. Minecraft 1.20.1's default UTF helper caps
 * strings at 32,767 Java characters. Keeping the same explicit limit at config
 * load, editor save, and packet encode/decode boundaries prevents an oversized
 * definition from loading successfully and then breaking player synchronization.
 */
public final class DefinitionLimits {
    public static final int MAX_SYNCED_JSON_CHARS = 32_767;

    private DefinitionLimits() {
    }

    public static String compactJson(JsonObject definition) {
        if (definition == null) {
            throw new IllegalArgumentException("Definition must be a JSON object");
        }
        return definition.toString();
    }

    public static void requireWireSafe(JsonObject definition, String label) {
        String json = compactJson(definition);
        if (json.length() > MAX_SYNCED_JSON_CHARS) {
            throw new IllegalArgumentException(
                    label + " is too large to synchronize: " + json.length()
                            + " characters; maximum is " + MAX_SYNCED_JSON_CHARS
            );
        }
    }

    public static void requireWireSafe(String json, String label) {
        if (json == null) {
            throw new IllegalArgumentException(label + " JSON must not be null");
        }
        if (json.length() > MAX_SYNCED_JSON_CHARS) {
            throw new IllegalArgumentException(
                    label + " is too large to synchronize: " + json.length()
                            + " characters; maximum is " + MAX_SYNCED_JSON_CHARS
            );
        }
    }
}
