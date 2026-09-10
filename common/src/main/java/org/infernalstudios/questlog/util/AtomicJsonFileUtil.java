package org.infernalstudios.questlog.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Writes JSON through a same-directory temporary file before replacing the
 * destination. A crash during serialization therefore does not intentionally
 * truncate the previously valid definition file.
 */
public final class AtomicJsonFileUtil {
    private AtomicJsonFileUtil() {
    }

    public static void write(Path destination, Gson gson, JsonElement value) throws IOException {
        Path normalized = destination.toAbsolutePath().normalize();
        Path parent = normalized.getParent();
        if (parent == null) {
            throw new IOException("Definition path has no parent directory: " + normalized);
        }

        Files.createDirectories(parent);
        Path temp = Files.createTempFile(parent, normalized.getFileName().toString() + ".", ".tmp");
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
                gson.toJson(value, writer);
            }

            try {
                Files.move(temp, normalized, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ignored) {
                Files.move(temp, normalized, StandardCopyOption.REPLACE_EXISTING);
            }
            temp = null;
        } finally {
            if (temp != null) {
                Files.deleteIfExists(temp);
            }
        }
    }
}
