package org.infernalstudios.questlog.core.quests.display;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.util.JsonUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

/**
 * Presentation-only metadata for remotely presented / incorporeal Questlog speakers.
 *
 * The semantic reaction vocabulary is deliberately small and character-agnostic.
 * Individual speakers may realize the same state through different artwork later.
 */
public record SpeakerPresentation(
        ResourceLocation speakerId,
        Reaction reaction,
        int paneWidth,
        boolean alphaCleanup
) {
    public static final int DEFAULT_PANE_WIDTH = 170;
    private static final int MIN_PANE_WIDTH = 96;
    private static final int MAX_PANE_WIDTH = 512;

    public enum Reaction {
        NEUTRAL,
        DIRECTIVE,
        MOCKING,
        APPROVING,
        SEVERE;

        public String serializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public static Reaction fromSerializedName(String value) {
            if (value == null || value.isBlank()) {
                return NEUTRAL;
            }
            try {
                return Reaction.valueOf(value.trim().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ignored) {
                Questlog.LOGGER.warn("Unknown Questlog speaker reaction '{}'; falling back to neutral", value);
                return NEUTRAL;
            }
        }
    }

    @Nullable
    public static SpeakerPresentation fromDefinition(JsonObject data) {
        String speaker = JsonUtils.getOrDefault(data, "speaker_id", (String) null);
        if (speaker == null || speaker.isBlank()) {
            return null;
        }

        ResourceLocation speakerId = ResourceLocation.tryParse(speaker);
        if (speakerId == null) {
            Questlog.LOGGER.warn("Invalid Questlog speaker id '{}'; speaker presentation disabled", speaker);
            return null;
        }

        Reaction reaction = Reaction.fromSerializedName(
                JsonUtils.getOrDefault(data, "speaker_reaction", Reaction.NEUTRAL.serializedName())
        );
        int requestedPaneWidth = JsonUtils.getOrDefault(data, "speaker_pane_width", DEFAULT_PANE_WIDTH);
        int paneWidth = Math.max(MIN_PANE_WIDTH, Math.min(MAX_PANE_WIDTH, requestedPaneWidth));
        boolean alphaCleanup = JsonUtils.getOrDefault(data, "speaker_alpha_cleanup", false);
        return new SpeakerPresentation(speakerId, reaction, paneWidth, alphaCleanup);
    }
}
