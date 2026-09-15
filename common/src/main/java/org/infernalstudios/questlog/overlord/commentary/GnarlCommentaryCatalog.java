package org.infernalstudios.questlog.overlord.commentary;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.Questlog;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Bundled authored lifecycle commentary for Gnarl.
 *
 * The catalogue is presentation data only. Questlog remains authoritative for
 * quest state, prerequisites, objectives, rewards and persistence.
 */
public final class GnarlCommentaryCatalog {
    private static final String RESOURCE = "/assets/questlog/overlord/gnarl_commentary.json";
    private static final Map<ResourceLocation, Definition> DEFINITIONS = load();

    private GnarlCommentaryCatalog() {
    }

    public static Definition get(ResourceLocation questId) {
        return DEFINITIONS.get(questId);
    }

    public static Set<ResourceLocation> questIds() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(DEFINITIONS.keySet()));
    }

    private static Map<ResourceLocation, Definition> load() {
        Map<ResourceLocation, Definition> result = new LinkedHashMap<>();
        try (InputStream stream = GnarlCommentaryCatalog.class.getResourceAsStream(RESOURCE)) {
            if (stream == null) {
                Questlog.LOGGER.error("Gnarl commentary catalogue is missing at {}", RESOURCE);
                return result;
            }

            JsonObject root = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).getAsJsonObject();
            JsonObject quests = root.has("quests") && root.get("quests").isJsonObject()
                    ? root.getAsJsonObject("quests")
                    : new JsonObject();

            for (Map.Entry<String, JsonElement> entry : quests.entrySet()) {
                ResourceLocation questId = ResourceLocation.tryParse(entry.getKey());
                if (questId == null || !entry.getValue().isJsonObject()) {
                    Questlog.LOGGER.warn("Ignoring invalid Gnarl commentary definition {}", entry.getKey());
                    continue;
                }
                result.put(questId, parse(entry.getValue().getAsJsonObject()));
            }
        } catch (Exception exception) {
            Questlog.LOGGER.error("Failed to load Gnarl commentary catalogue", exception);
        }
        return result;
    }

    private static Definition parse(JsonObject data) {
        Map<Integer, String> objectiveUpdates = new LinkedHashMap<>();
        if (data.has("objective_updates") && data.get("objective_updates").isJsonObject()) {
            for (Map.Entry<String, JsonElement> update : data.getAsJsonObject("objective_updates").entrySet()) {
                try {
                    int count = Integer.parseInt(update.getKey());
                    if (count > 0 && update.getValue().isJsonPrimitive()) {
                        objectiveUpdates.put(count, update.getValue().getAsString());
                    }
                } catch (NumberFormatException ignored) {
                    Questlog.LOGGER.warn("Ignoring invalid Gnarl objective-update key {}", update.getKey());
                }
            }
        }

        List<String> repeatReminders = new ArrayList<>();
        if (data.has("repeat_reminders") && data.get("repeat_reminders").isJsonArray()) {
            data.getAsJsonArray("repeat_reminders").forEach(element -> {
                if (element.isJsonPrimitive() && !element.getAsString().isBlank()) {
                    repeatReminders.add(element.getAsString());
                }
            });
        }

        JsonObject timingJson = data.has("timing") && data.get("timing").isJsonObject()
                ? data.getAsJsonObject("timing")
                : new JsonObject();
        Timing timing = new Timing(
                positiveInt(timingJson, "clarification_delay_ticks", 100),
                positiveInt(timingJson, "branch_framing_delay_ticks", 300),
                positiveInt(timingJson, "first_reminder_delay_ticks", 2400),
                positiveInt(timingJson, "repeat_reminder_delay_ticks", 4800),
                positiveInt(timingJson, "warning_delay_ticks", 12000),
                positiveInt(timingJson, "post_quest_delay_ticks", 100)
        );

        return new Definition(
                string(data, "register"),
                string(data, "delivery_direction"),
                string(data, "objective_clarification"),
                string(data, "branch_framing"),
                string(data, "first_reminder"),
                List.copyOf(repeatReminders),
                Collections.unmodifiableMap(objectiveUpdates),
                string(data, "warning"),
                string(data, "success"),
                string(data, "failure"),
                string(data, "post_quest"),
                timing
        );
    }

    private static String string(JsonObject data, String key) {
        if (!data.has(key) || !data.get(key).isJsonPrimitive()) return "";
        return data.get(key).getAsString();
    }

    private static int positiveInt(JsonObject data, String key, int fallback) {
        if (!data.has(key) || !data.get(key).isJsonPrimitive()) return fallback;
        try {
            return Math.max(1, data.get(key).getAsInt());
        } catch (RuntimeException ignored) {
            return fallback;
        }
    }

    public record Definition(
            String registerName,
            String deliveryDirection,
            String objectiveClarification,
            String branchFraming,
            String firstReminder,
            List<String> repeatReminders,
            Map<Integer, String> objectiveUpdates,
            String warning,
            String success,
            String failure,
            String postQuest,
            Timing timing
    ) {
    }

    public record Timing(
            int clarificationDelayTicks,
            int branchFramingDelayTicks,
            int firstReminderDelayTicks,
            int repeatReminderDelayTicks,
            int warningDelayTicks,
            int postQuestDelayTicks
    ) {
    }
}
