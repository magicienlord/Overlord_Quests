package org.infernalstudios.questlog.client.gui;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.quests.display.SpeakerPresentation;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Client-only portrait resolution/preparation for incorporeal Questlog speakers.
 *
 * Quest definitions author semantic speaker/reaction state rather than hard-wiring
 * every future expression asset. If a roster asset exists at the conventional
 * speaker path, it wins. Neutral is the second choice, and the quest's explicit
 * overlay remains a development/legacy fallback.
 *
 * Most speaker art should be authored with clean straight-alpha edges and pass
 * through untouched. A legacy/source image with visible matte colour can opt in
 * to conservative fringe cleanup through speaker_alpha_cleanup.
 */
public final class SpeakerPortraitTextures {
    private static final int TRANSPARENT_CUTOFF = 8;
    private static final int EDGE_ALPHA_CUTOFF = 208;
    private static final int OPAQUE_REFERENCE_ALPHA = 224;
    private static final int SEARCH_RADIUS = 4;

    private static final Map<ResourceLocation, ResourceLocation> CLEANED = new HashMap<>();

    private SpeakerPortraitTextures() {
    }

    public static ResourceLocation resolve(SpeakerPresentation speaker, ResourceLocation fallback) {
        Minecraft minecraft = Minecraft.getInstance();
        ResourceLocation requested = rosterPath(speaker, speaker.reaction());
        ResourceLocation neutral = rosterPath(speaker, SpeakerPresentation.Reaction.NEUTRAL);

        ResourceLocation source;
        if (minecraft.getResourceManager().getResource(requested).isPresent()) {
            source = requested;
        } else if (!requested.equals(neutral) && minecraft.getResourceManager().getResource(neutral).isPresent()) {
            source = neutral;
        } else {
            source = fallback;
        }

        return resolveAlpha(source, speaker.alphaCleanup());
    }

    public static ResourceLocation rosterPath(
            SpeakerPresentation speaker,
            SpeakerPresentation.Reaction reaction
    ) {
        ResourceLocation speakerId = speaker.speakerId();
        return new ResourceLocation(
                Questlog.MODID,
                "textures/gui/overlord/speakers/"
                        + speakerId.getNamespace() + "/"
                        + speakerId.getPath() + "/"
                        + reaction.serializedName() + ".png"
        );
    }

    private static ResourceLocation resolveAlpha(ResourceLocation source, boolean cleanAlpha) {
        if (!cleanAlpha) {
            return source;
        }

        ResourceLocation cached = CLEANED.get(source);
        if (cached != null) {
            return cached;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Optional<Resource> resource = minecraft.getResourceManager().getResource(source);
        if (resource.isEmpty()) {
            Questlog.LOGGER.warn("Speaker portrait resource {} was not found; rendering original id", source);
            return source;
        }

        try (InputStream stream = resource.get().open(); NativeImage image = NativeImage.read(stream)) {
            cleanMatteFringe(image);

            NativeImage uploadedImage = copyImage(image);
            DynamicTexture texture = new DynamicTexture(uploadedImage);
            ResourceLocation generated = new ResourceLocation(
                    Questlog.MODID,
                    "dynamic/speaker_portrait/" + Integer.toUnsignedString(source.toString().hashCode(), 16)
            );
            minecraft.getTextureManager().release(generated);
            minecraft.getTextureManager().register(generated, texture);
            CLEANED.put(source, generated);
            return generated;
        } catch (IOException | RuntimeException ex) {
            Questlog.LOGGER.warn("Could not clean speaker portrait {}; rendering source texture", source, ex);
            return source;
        }
    }

    private static NativeImage copyImage(NativeImage source) {
        NativeImage copy = new NativeImage(source.getWidth(), source.getHeight(), true);
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                copy.setPixelRGBA(x, y, source.getPixelRGBA(x, y));
            }
        }
        return copy;
    }

    private static void cleanMatteFringe(NativeImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] source = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                source[y * width + x] = image.getPixelRGBA(x, y);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = source[y * width + x];
                int alpha = alpha(pixel);
                if (alpha < TRANSPARENT_CUTOFF) {
                    image.setPixelRGBA(x, y, 0);
                    continue;
                }
                if (alpha >= EDGE_ALPHA_CUTOFF) {
                    continue;
                }

                int reference = nearestOpaquePixel(source, width, height, x, y);
                if (reference != -1) {
                    image.setPixelRGBA(x, y, (pixel & 0xFF000000) | (reference & 0x00FFFFFF));
                }
            }
        }
    }

    private static int nearestOpaquePixel(int[] pixels, int width, int height, int x, int y) {
        int best = -1;
        int bestDistance = Integer.MAX_VALUE;
        for (int dy = -SEARCH_RADIUS; dy <= SEARCH_RADIUS; dy++) {
            int sampleY = y + dy;
            if (sampleY < 0 || sampleY >= height) continue;
            for (int dx = -SEARCH_RADIUS; dx <= SEARCH_RADIUS; dx++) {
                int sampleX = x + dx;
                if (sampleX < 0 || sampleX >= width || (dx == 0 && dy == 0)) continue;
                int distance = dx * dx + dy * dy;
                if (distance >= bestDistance) continue;

                int candidate = pixels[sampleY * width + sampleX];
                if (alpha(candidate) >= OPAQUE_REFERENCE_ALPHA) {
                    best = candidate;
                    bestDistance = distance;
                }
            }
        }
        return best;
    }

    private static int alpha(int abgr) {
        return (abgr >>> 24) & 0xFF;
    }
}
