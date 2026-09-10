package org.infernalstudios.questlog.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.platform.Services;
import org.infernalstudios.questlog.util.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DefinitionUtil {
    private static final Map<ResourceLocation, JsonObject> QUEST_DEFINITION_CACHE = new Object2ObjectOpenHashMap<>();
    private static final Map<ResourceLocation, JsonObject> CHAPTER_DEFINITION_CACHE = new Object2ObjectOpenHashMap<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final String BUNDLED_INDEX = "assets/questlog/overlord/definitions/index.json";
    private static final String BUNDLED_QUEST_ROOT = "assets/questlog/overlord/definitions/quests/";
    private static final String BUNDLED_CHAPTER_ROOT = "assets/questlog/overlord/definitions/chapters/";

    /**
     * Definition caches are static and therefore shared by the logical client and
     * integrated server in single-player. Keep readers on the same class monitor
     * as load/put/clear operations so a client GUI cannot iterate a fastutil map
     * while the server thread is rebuilding it after a definition reload.
     */
    public static synchronized List<ResourceLocation> getCachedQuestKeys() {
        List<ResourceLocation> keys = new ArrayList<>(QUEST_DEFINITION_CACHE.keySet());
        keys.sort((a, b) -> {
            JsonObject jsonA = QUEST_DEFINITION_CACHE.get(a);
            JsonObject jsonB = QUEST_DEFINITION_CACHE.get(b);
            int orderA = JsonUtils.getOrDefault(jsonA, "sort_order", JsonUtils.getOrDefault(jsonA, "order", 0));
            int orderB = JsonUtils.getOrDefault(jsonB, "sort_order", JsonUtils.getOrDefault(jsonB, "order", 0));
            if (orderA != orderB) {
                return Integer.compare(orderA, orderB);
            }
            String titleA = JsonUtils.getOrDefault(jsonA, "title", "");
            String titleB = JsonUtils.getOrDefault(jsonB, "title", "");
            int titleCompare = titleA.compareToIgnoreCase(titleB);
            if (titleCompare != 0) {
                return titleCompare;
            }
            return a.compareTo(b);
        });
        return keys;
    }

    /**
     * Returns an isolated definition snapshot. Callers, especially client editor
     * screens in an integrated server, must never receive the mutable JsonObject
     * stored in the shared authoritative cache.
     */
    public static synchronized JsonObject getCachedQuest(ResourceLocation path) {
        JsonObject definition = QUEST_DEFINITION_CACHE.get(path);
        if (definition == null) {
            throw new NullPointerException("Quest not found: " + path);
        }
        return definition.deepCopy();
    }

    /**
     * Legacy editor compatibility sink. Client editor screens inherited from
     * Questlog used this method for optimistic cache mutation before sending the
     * authoritative save packet. In integrated single-player that mutates the
     * server's static cache from the client thread, so direct mutation is ignored.
     * The server save/reload path and subsequent full sync are authoritative.
     */
    @Deprecated
    public static synchronized void putCachedQuest(ResourceLocation path, JsonObject definition) {
        if (path == null || definition == null) {
            throw new IllegalArgumentException("Quest cache id and definition must be non-null");
        }
    }

    /**
     * Replaces one definition in a remote client's mirror cache. This is reserved
     * for authoritative server sync handling. Integrated single-player never calls
     * it because client and server already share the server-owned static cache.
     */
    public static synchronized void putClientMirrorQuest(ResourceLocation path, JsonObject definition) {
        if (path == null || definition == null) {
            throw new IllegalArgumentException("Quest cache id and definition must be non-null");
        }
        QUEST_DEFINITION_CACHE.put(path, definition.deepCopy());
    }

    public static synchronized void clearClientCaches() {
        QUEST_DEFINITION_CACHE.clear();
        CHAPTER_DEFINITION_CACHE.clear();
    }

    public static synchronized List<ResourceLocation> getCachedChapterKeys() {
        List<ResourceLocation> keys = new ArrayList<>(CHAPTER_DEFINITION_CACHE.keySet());
        keys.sort((a, b) -> {
            boolean aMain = a.getNamespace().equals(Questlog.MODID) && a.getPath().equals("main");
            boolean bMain = b.getNamespace().equals(Questlog.MODID) && b.getPath().equals("main");
            if (aMain && !bMain) return -1;
            if (!aMain && bMain) return 1;

            JsonObject jsonA = CHAPTER_DEFINITION_CACHE.get(a);
            JsonObject jsonB = CHAPTER_DEFINITION_CACHE.get(b);
            int orderA = JsonUtils.getOrDefault(jsonA, "sort_order", JsonUtils.getOrDefault(jsonA, "order", 0));
            int orderB = JsonUtils.getOrDefault(jsonB, "sort_order", JsonUtils.getOrDefault(jsonB, "order", 0));

            if (orderA != orderB) {
                return Integer.compare(orderA, orderB);
            }
            return a.compareTo(b);
        });
        return keys;
    }

    /** Returns an isolated chapter snapshot rather than the mutable cache value. */
    public static synchronized JsonObject getCachedChapter(ResourceLocation path) {
        JsonObject definition = CHAPTER_DEFINITION_CACHE.get(path);
        return definition == null ? null : definition.deepCopy();
    }

    /** See {@link #putCachedQuest(ResourceLocation, JsonObject)}. */
    @Deprecated
    public static synchronized void putCachedChapter(ResourceLocation path, JsonObject definition) {
        if (path == null || definition == null) {
            throw new IllegalArgumentException("Chapter cache id and definition must be non-null");
        }
    }

    /** Authoritative remote-client mirror update; see {@link #putClientMirrorQuest(ResourceLocation, JsonObject)}. */
    public static synchronized void putClientMirrorChapter(ResourceLocation path, JsonObject definition) {
        if (path == null || definition == null) {
            throw new IllegalArgumentException("Chapter cache id and definition must be non-null");
        }
        CHAPTER_DEFINITION_CACHE.put(path, definition.deepCopy());
    }

    public static synchronized void loadFromConfig() {
        QUEST_DEFINITION_CACHE.clear();
        CHAPTER_DEFINITION_CACHE.clear();

        // OVERLORD QUESTS ships approved definitions in the mod jar. Load those
        // first, then layer config/questlog definitions on top so pack authors can
        // override any bundled definition without modifying the jar.
        loadBundledDefinitions();

        Path configDir = Services.PLATFORM.getConfigDirectory().resolve("questlog");
        Path questDir = configDir.resolve("quests");
        Path chapterDir = configDir.resolve("chapters");

        createDirIfNotExists(questDir);
        createDirIfNotExists(chapterDir);

        Path defaultMainChapter = chapterDir.resolve("main.json");
        ResourceLocation mainChapterId = new ResourceLocation(Questlog.MODID, "main");
        if (!Files.exists(defaultMainChapter) && !CHAPTER_DEFINITION_CACHE.containsKey(mainChapterId)) {
            try {
                JsonObject mainChapter = new JsonObject();
                JsonObject iconObj = new JsonObject();
                iconObj.addProperty("item", "minecraft:knowledge_book");
                mainChapter.add("icon", iconObj);
                mainChapter.addProperty("default_chapter", true);
                mainChapter.addProperty("hidden", false);

                Files.writeString(defaultMainChapter, GSON.toJson(mainChapter));
            } catch (IOException e) {
                Questlog.LOGGER.error("Failed to create default main.json chapter", e);
            }
        }

        loadFiles(questDir, QUEST_DEFINITION_CACHE);
        loadFiles(chapterDir, CHAPTER_DEFINITION_CACHE);

        Questlog.LOGGER.info("Loaded {} quests and {} chapters after bundled definitions and config overrides.", QUEST_DEFINITION_CACHE.size(), CHAPTER_DEFINITION_CACHE.size());
    }

    private static void loadBundledDefinitions() {
        ClassLoader loader = DefinitionUtil.class.getClassLoader();
        try (InputStream indexStream = loader.getResourceAsStream(BUNDLED_INDEX)) {
            if (indexStream == null) {
                Questlog.LOGGER.debug("No bundled OVERLORD QUESTS definition index found at {}.", BUNDLED_INDEX);
                return;
            }

            JsonObject index;
            try (Reader reader = new InputStreamReader(indexStream, StandardCharsets.UTF_8)) {
                index = GSON.fromJson(reader, JsonObject.class);
            }

            if (index == null) {
                Questlog.LOGGER.error("Bundled OVERLORD QUESTS definition index is empty: {}", BUNDLED_INDEX);
                return;
            }

            loadBundledCategory(loader, index, "quests", BUNDLED_QUEST_ROOT, QUEST_DEFINITION_CACHE);
            loadBundledCategory(loader, index, "chapters", BUNDLED_CHAPTER_ROOT, CHAPTER_DEFINITION_CACHE);
        } catch (Exception e) {
            Questlog.LOGGER.error("Failed to load bundled OVERLORD QUESTS definition index: {}", BUNDLED_INDEX, e);
        }
    }

    private static void loadBundledCategory(ClassLoader loader, JsonObject index, String key, String root,
                                            Map<ResourceLocation, JsonObject> cache) {
        JsonArray entries = index.has(key) && index.get(key).isJsonArray() ? index.getAsJsonArray(key) : new JsonArray();
        for (JsonElement element : entries) {
            if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
                Questlog.LOGGER.error("Invalid bundled {} definition entry in {}: {}", key, BUNDLED_INDEX, element);
                continue;
            }

            String relativePath = element.getAsString().replace('\\', '/');
            if (!relativePath.endsWith(".json") || relativePath.startsWith("/") || relativePath.contains("..")) {
                Questlog.LOGGER.error("Rejected unsafe bundled {} definition path: {}", key, relativePath);
                continue;
            }

            String idPath = relativePath.substring(0, relativePath.length() - ".json".length());
            ResourceLocation id;
            try {
                id = new ResourceLocation(Questlog.MODID, idPath);
            } catch (Exception e) {
                Questlog.LOGGER.error("Invalid bundled {} definition id derived from path: {}", key, relativePath, e);
                continue;
            }

            String resourcePath = root + relativePath;
            try (InputStream stream = loader.getResourceAsStream(resourcePath)) {
                if (stream == null) {
                    Questlog.LOGGER.error("Bundled {} definition listed in index is missing: {}", key, resourcePath);
                    continue;
                }

                try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    JsonObject json = GSON.fromJson(reader, JsonObject.class);
                    if (json == null) {
                        Questlog.LOGGER.error("Bundled {} definition is empty: {}", key, resourcePath);
                        continue;
                    }
                    cache.put(id, json);
                }
            } catch (Exception e) {
                Questlog.LOGGER.error("Failed to parse bundled {} definition: {}", key, resourcePath, e);
            }
        }
    }

    private static void createDirIfNotExists(Path dir) {
        if (!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                Questlog.LOGGER.error("Failed to create directory: {}", dir, e);
            }
        }
    }

    private static void loadFiles(Path dir, Map<ResourceLocation, JsonObject> cache) {
        try (Stream<Path> paths = Files.walk(dir)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".json"))
                    .sorted()
                    .forEach(path -> loadDefinitionFile(dir, path, cache));
        } catch (IOException e) {
            Questlog.LOGGER.error("Failed to read files from directory: {}", dir, e);
        }
    }

    private static void loadDefinitionFile(Path root, Path path, Map<ResourceLocation, JsonObject> cache) {
        Path relative = root.relativize(path);
        String relativePath = relative.toString().replace(File.separatorChar, '/');
        if (!relativePath.endsWith(".json")) {
            return;
        }

        String resourcePath = relativePath.substring(0, relativePath.length() - ".json".length());
        final ResourceLocation id;
        try {
            id = new ResourceLocation(Questlog.MODID, resourcePath);
        } catch (Exception e) {
            // A malformed filename must not abort the entire directory walk.
            Questlog.LOGGER.error("Skipping definition with invalid resource id derived from path: {}", path, e);
            return;
        }

        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            if (json == null) {
                throw new IllegalArgumentException("Definition root is JSON null");
            }
            cache.put(id, json);
        } catch (Exception e) {
            Questlog.LOGGER.error("Failed to parse file: {}", path, e);
            if (cache == QUEST_DEFINITION_CACHE) {
                cache.put(id, createBrokenQuestFallback(path, id, e));
            }
        }
    }

    private static JsonObject createBrokenQuestFallback(Path path, ResourceLocation id, Exception error) {
        String chapterVal = "main";
        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            java.util.regex.Matcher matcher = java.util.regex.Pattern
                    .compile("\\\"chapter\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"")
                    .matcher(content);
            if (matcher.find()) {
                chapterVal = matcher.group(1);
            }
        } catch (Exception ignored) {
        }

        String errorMsg = error.getMessage() != null ? error.getMessage() : error.toString();
        if (error.getCause() != null && error.getCause().getMessage() != null) {
            errorMsg += "\nCaused by: " + error.getCause().getMessage();
        }

        JsonObject fallback = new JsonObject();
        fallback.addProperty("title", "Broken Quest (" + id.getPath() + ")");
        fallback.addProperty(
                "description",
                "This quest failed to load properly. Edit it to fix errors.\n\nError details:\n" + errorMsg
        );
        fallback.addProperty("chapter", chapterVal);
        return fallback;
    }
}
