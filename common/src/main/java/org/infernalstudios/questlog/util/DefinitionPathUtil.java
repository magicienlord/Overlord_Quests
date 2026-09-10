package org.infernalstudios.questlog.util;

import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;

/**
 * Filesystem boundary checks for the in-game quest/chapter editor.
 *
 * ResourceLocation paths permit '/' and '.' characters. That is useful for
 * nested definition folders, but resolving an unchecked path such as '../x'
 * against the config directory can escape the intended questlog subtree. Keep
 * every editor write/delete operation inside its assigned definition root.
 */
public final class DefinitionPathUtil {
    private DefinitionPathUtil() {
    }

    public static Path resolveJsonDefinition(Path root, ResourceLocation id) {
        Path normalizedRoot = root.toAbsolutePath().normalize();
        Path target = normalizedRoot.resolve(id.getPath() + ".json").normalize();
        if (!target.startsWith(normalizedRoot) || target.equals(normalizedRoot)) {
            throw new IllegalArgumentException("Definition path escapes root: " + id);
        }
        return target;
    }
}
