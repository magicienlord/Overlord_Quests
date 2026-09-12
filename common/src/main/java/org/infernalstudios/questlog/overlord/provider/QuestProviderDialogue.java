package org.infernalstudios.questlog.overlord.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition-owned provider dialogue for the neutral NPC interaction scaffold.
 *
 * Dialogue is authored content. This class only preserves state-specific lines;
 * it does not generate text, infer civilization speech, or establish story canon.
 */
public final class QuestProviderDialogue {
    public static final QuestProviderDialogue EMPTY = new QuestProviderDialogue(
            List.of(), List.of(), List.of(), List.of(), List.of()
    );

    private final List<String> offer;
    private final List<String> inProgress;
    private final List<String> readyToTurnIn;
    private final List<String> failed;
    private final List<String> completed;

    private QuestProviderDialogue(
            List<String> offer,
            List<String> inProgress,
            List<String> readyToTurnIn,
            List<String> failed,
            List<String> completed
    ) {
        this.offer = List.copyOf(offer);
        this.inProgress = List.copyOf(inProgress);
        this.readyToTurnIn = List.copyOf(readyToTurnIn);
        this.failed = List.copyOf(failed);
        this.completed = List.copyOf(completed);
    }

    public static QuestProviderDialogue fromProviderDefinition(JsonObject provider) {
        if (provider == null || !provider.has("dialogue")) {
            return EMPTY;
        }
        if (!provider.get("dialogue").isJsonObject()) {
            throw new IllegalArgumentException("provider dialogue must be an object");
        }

        JsonObject dialogue = provider.getAsJsonObject("dialogue");
        return new QuestProviderDialogue(
                parseLines(dialogue, "offer"),
                parseLines(dialogue, "in_progress"),
                parseLines(dialogue, "ready_to_turn_in"),
                parseLines(dialogue, "failed"),
                parseLines(dialogue, "completed")
        );
    }

    public List<String> linesFor(QuestProviderService.InteractionState state) {
        if (state == null) return List.of();
        return switch (state) {
            case AVAILABLE -> this.offer;
            case IN_PROGRESS -> this.inProgress;
            case READY_TO_TURN_IN -> this.readyToTurnIn;
            case FAILED -> this.failed;
            case COMPLETED -> this.completed;
        };
    }

    public boolean hasLinesFor(QuestProviderService.InteractionState state) {
        return !this.linesFor(state).isEmpty();
    }

    private static List<String> parseLines(JsonObject dialogue, String key) {
        if (!dialogue.has(key)) return List.of();
        JsonElement value = dialogue.get(key);
        List<String> result = new ArrayList<>();

        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
            result.add(requireLine(value.getAsString(), key));
            return result;
        }
        if (!value.isJsonArray()) {
            throw new IllegalArgumentException("provider dialogue " + key + " must be a string or string array");
        }

        JsonArray array = value.getAsJsonArray();
        for (JsonElement line : array) {
            if (!line.isJsonPrimitive() || !line.getAsJsonPrimitive().isString()) {
                throw new IllegalArgumentException("provider dialogue " + key + " lines must be strings");
            }
            result.add(requireLine(line.getAsString(), key));
        }
        return result;
    }

    private static String requireLine(String value, String key) {
        String line = value == null ? "" : value.trim();
        if (line.isEmpty()) {
            throw new IllegalArgumentException("provider dialogue " + key + " lines must not be empty");
        }
        return line;
    }
}
