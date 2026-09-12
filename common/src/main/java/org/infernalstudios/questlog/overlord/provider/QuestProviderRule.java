package org.infernalstudios.questlog.overlord.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.infernalstudios.questlog.Questlog;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Definition-owned rules for quests offered by physical NPC providers.
 *
 * The rule is intentionally generic. Civilization is narrative metadata, while
 * concrete provider eligibility is expressed through entity ids/tags, optional
 * role tags, dimensions, authored location bounds, quest completion markers,
 * explicit narrative facts, and authored disposition requirements.
 */
public final class QuestProviderRule {
    public enum TurnInMode {
        NONE,
        SAME_PROVIDER,
        ANY_ELIGIBLE;

        static TurnInMode parse(String value) {
            return switch (value == null ? "same_provider" : value.trim().toLowerCase()) {
                case "none" -> NONE;
                case "same_provider" -> SAME_PROVIDER;
                case "any_eligible" -> ANY_ELIGIBLE;
                default -> throw new IllegalArgumentException("Unknown provider turn_in mode: " + value);
            };
        }
    }

    /** Inclusive block-coordinate eligibility bounds, authored per provider rule. */
    public record LocationBounds(BlockPos min, BlockPos max) {
        public LocationBounds {
            if (min == null || max == null) {
                throw new IllegalArgumentException("provider location min and max are required");
            }
            BlockPos normalizedMin = new BlockPos(
                    Math.min(min.getX(), max.getX()),
                    Math.min(min.getY(), max.getY()),
                    Math.min(min.getZ(), max.getZ())
            );
            BlockPos normalizedMax = new BlockPos(
                    Math.max(min.getX(), max.getX()),
                    Math.max(min.getY(), max.getY()),
                    Math.max(min.getZ(), max.getZ())
            );
            min = normalizedMin;
            max = normalizedMax;
        }

        public boolean contains(Entity entity) {
            if (entity == null) return false;
            BlockPos pos = entity.blockPosition();
            return pos.getX() >= this.min.getX() && pos.getX() <= this.max.getX()
                    && pos.getY() >= this.min.getY() && pos.getY() <= this.max.getY()
                    && pos.getZ() >= this.min.getZ() && pos.getZ() <= this.max.getZ();
        }
    }

    @Nullable private final ResourceLocation pool;
    @Nullable private final ResourceLocation civilization;
    private final String role;
    private final Set<ResourceLocation> entityTypes;
    private final Set<ResourceLocation> entityTypeTags;
    private final Set<String> scoreboardTags;
    private final Set<ResourceLocation> dimensions;
    @Nullable private final LocationBounds location;
    private final QuestProviderDialogue dialogue;
    private final Set<ResourceLocation> unlockQuests;
    private final Set<ResourceLocation> requiredFacts;
    private final Set<ResourceLocation> forbiddenFacts;
    private final Map<ResourceLocation, Set<ResourceLocation>> requiredDispositions;
    private final boolean lockToProvider;
    private final TurnInMode turnInMode;

    private QuestProviderRule(
            @Nullable ResourceLocation pool,
            @Nullable ResourceLocation civilization,
            String role,
            Set<ResourceLocation> entityTypes,
            Set<ResourceLocation> entityTypeTags,
            Set<String> scoreboardTags,
            Set<ResourceLocation> dimensions,
            @Nullable LocationBounds location,
            QuestProviderDialogue dialogue,
            Set<ResourceLocation> unlockQuests,
            Set<ResourceLocation> requiredFacts,
            Set<ResourceLocation> forbiddenFacts,
            Map<ResourceLocation, Set<ResourceLocation>> requiredDispositions,
            boolean lockToProvider,
            TurnInMode turnInMode
    ) {
        this.pool = pool;
        this.civilization = civilization;
        this.role = role;
        this.entityTypes = Set.copyOf(entityTypes);
        this.entityTypeTags = Set.copyOf(entityTypeTags);
        this.scoreboardTags = Set.copyOf(scoreboardTags);
        this.dimensions = Set.copyOf(dimensions);
        this.location = location;
        this.dialogue = dialogue == null ? QuestProviderDialogue.EMPTY : dialogue;
        this.unlockQuests = Set.copyOf(unlockQuests);
        this.requiredFacts = Set.copyOf(requiredFacts);
        this.forbiddenFacts = Set.copyOf(forbiddenFacts);
        Map<ResourceLocation, Set<ResourceLocation>> copy = new LinkedHashMap<>();
        requiredDispositions.forEach((key, value) -> copy.put(key, Set.copyOf(value)));
        this.requiredDispositions = Collections.unmodifiableMap(copy);
        this.lockToProvider = lockToProvider;
        this.turnInMode = turnInMode;
    }

    @Nullable
    public static QuestProviderRule fromDefinition(JsonObject questDefinition) {
        if (questDefinition == null || !questDefinition.has("provider")) {
            return null;
        }
        if (!questDefinition.get("provider").isJsonObject()) {
            throw new IllegalArgumentException("provider must be a JSON object");
        }
        JsonObject json = questDefinition.getAsJsonObject("provider");

        ResourceLocation pool = optionalId(json, "pool");
        ResourceLocation civilization = optionalId(json, "civilization");
        String role = optionalString(json, "role");
        Set<ResourceLocation> entityTypes = idSet(json, "entity_types");
        Set<ResourceLocation> entityTypeTags = idSet(json, "entity_type_tags");
        Set<String> scoreboardTags = stringSet(json, "scoreboard_tags");
        Set<ResourceLocation> dimensions = idSet(json, "dimensions");
        LocationBounds location = locationBounds(json);
        QuestProviderDialogue dialogue = QuestProviderDialogue.fromProviderDefinition(json);
        Set<ResourceLocation> unlockQuests = questIdSet(json, "unlock_quests");
        Set<ResourceLocation> requiredFacts = idSet(json, "required_facts");
        Set<ResourceLocation> forbiddenFacts = idSet(json, "forbidden_facts");
        Map<ResourceLocation, Set<ResourceLocation>> dispositions = dispositionMap(json);
        boolean lockToProvider = optionalBoolean(json, "lock_to_provider", true);
        TurnInMode turnInMode = TurnInMode.parse(optionalString(json, "turn_in", "same_provider"));

        if (entityTypes.isEmpty() && entityTypeTags.isEmpty()) {
            throw new IllegalArgumentException("provider requires at least one entity_types or entity_type_tags selector");
        }
        Set<ResourceLocation> contradictoryFacts = new LinkedHashSet<>(requiredFacts);
        contradictoryFacts.retainAll(forbiddenFacts);
        if (!contradictoryFacts.isEmpty()) {
            throw new IllegalArgumentException("provider cannot both require and forbid narrative fact(s): " + contradictoryFacts);
        }

        return new QuestProviderRule(
                pool, civilization, role, entityTypes, entityTypeTags, scoreboardTags,
                dimensions, location, dialogue, unlockQuests, requiredFacts, forbiddenFacts,
                dispositions, lockToProvider, turnInMode
        );
    }

    public boolean matchesEntity(Entity entity) {
        if (entity == null) return false;

        ResourceLocation entityId = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        boolean typeMatches = this.entityTypes.contains(entityId);
        if (!typeMatches) {
            for (ResourceLocation tagId : this.entityTypeTags) {
                TagKey<EntityType<?>> tag = TagKey.create(Registries.ENTITY_TYPE, tagId);
                if (entity.getType().builtInRegistryHolder().is(tag)) {
                    typeMatches = true;
                    break;
                }
            }
        }
        if (!typeMatches) return false;

        if (!this.dimensions.isEmpty() && !this.dimensions.contains(entity.level().dimension().location())) {
            return false;
        }
        if (this.location != null && !this.location.contains(entity)) {
            return false;
        }
        if (!entity.getTags().containsAll(this.scoreboardTags)) {
            return false;
        }
        return this.role.isEmpty() || entity.getTags().contains("overlord_role:" + this.role);
    }

    @Nullable public ResourceLocation pool() { return this.pool; }
    @Nullable public ResourceLocation civilization() { return this.civilization; }
    public String role() { return this.role; }
    @Nullable public LocationBounds location() { return this.location; }
    public QuestProviderDialogue dialogue() { return this.dialogue; }
    public Set<ResourceLocation> unlockQuests() { return this.unlockQuests; }
    public Set<ResourceLocation> requiredFacts() { return this.requiredFacts; }
    public Set<ResourceLocation> forbiddenFacts() { return this.forbiddenFacts; }
    public Map<ResourceLocation, Set<ResourceLocation>> requiredDispositions() { return this.requiredDispositions; }
    public boolean lockToProvider() { return this.lockToProvider; }
    public TurnInMode turnInMode() { return this.turnInMode; }
    public boolean requiresTurnIn() { return this.turnInMode != TurnInMode.NONE; }

    @Nullable
    private static ResourceLocation optionalId(JsonObject json, String key) {
        String value = optionalString(json, key);
        if (value.isEmpty()) return null;
        ResourceLocation id = ResourceLocation.tryParse(value);
        if (id == null) throw new IllegalArgumentException("Invalid provider resource id for " + key + ": " + value);
        return id;
    }

    private static Set<ResourceLocation> idSet(JsonObject json, String key) {
        Set<ResourceLocation> result = new LinkedHashSet<>();
        if (!json.has(key)) return result;
        if (!json.get(key).isJsonArray()) throw new IllegalArgumentException("provider " + key + " must be an array");
        for (JsonElement element : json.getAsJsonArray(key)) {
            if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
                throw new IllegalArgumentException("provider " + key + " values must be strings");
            }
            ResourceLocation id = ResourceLocation.tryParse(element.getAsString());
            if (id == null) throw new IllegalArgumentException("Invalid provider resource id in " + key + ": " + element);
            result.add(id);
        }
        return result;
    }

    private static Set<ResourceLocation> questIdSet(JsonObject json, String key) {
        Set<ResourceLocation> result = new LinkedHashSet<>();
        if (!json.has(key)) return result;
        if (!json.get(key).isJsonArray()) throw new IllegalArgumentException("provider " + key + " must be an array");
        for (JsonElement element : json.getAsJsonArray(key)) {
            if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
                throw new IllegalArgumentException("provider " + key + " values must be strings");
            }
            String value = element.getAsString().trim();
            ResourceLocation id = value.contains(":")
                    ? ResourceLocation.tryParse(value)
                    : ResourceLocation.tryParse(Questlog.MODID + ":" + value);
            if (id == null) throw new IllegalArgumentException("Invalid provider quest id in " + key + ": " + element);
            result.add(id);
        }
        return result;
    }

    @Nullable
    private static LocationBounds locationBounds(JsonObject json) {
        if (!json.has("location")) return null;
        if (!json.get("location").isJsonObject()) {
            throw new IllegalArgumentException("provider location must be an object");
        }
        JsonObject location = json.getAsJsonObject("location");
        return new LocationBounds(
                blockPos(location, "min"),
                blockPos(location, "max")
        );
    }

    private static BlockPos blockPos(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonArray()) {
            throw new IllegalArgumentException("provider location " + key + " must be a three-integer array");
        }
        JsonArray values = json.getAsJsonArray(key);
        if (values.size() != 3) {
            throw new IllegalArgumentException("provider location " + key + " must contain exactly three integers");
        }
        int[] coordinates = new int[3];
        for (int i = 0; i < 3; i++) {
            JsonElement value = values.get(i);
            if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) {
                throw new IllegalArgumentException("provider location " + key + " values must be integers");
            }
            double numeric = value.getAsDouble();
            if (!Double.isFinite(numeric) || numeric != Math.rint(numeric) || numeric < Integer.MIN_VALUE || numeric > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("provider location " + key + " values must be 32-bit integers");
            }
            coordinates[i] = (int) numeric;
        }
        return new BlockPos(coordinates[0], coordinates[1], coordinates[2]);
    }

    private static Set<String> stringSet(JsonObject json, String key) {
        Set<String> result = new LinkedHashSet<>();
        if (!json.has(key)) return result;
        if (!json.get(key).isJsonArray()) throw new IllegalArgumentException("provider " + key + " must be an array");
        for (JsonElement element : json.getAsJsonArray(key)) {
            if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
                throw new IllegalArgumentException("provider " + key + " values must be strings");
            }
            String value = element.getAsString().trim();
            if (value.isEmpty()) throw new IllegalArgumentException("provider " + key + " values must not be empty");
            result.add(value);
        }
        return result;
    }

    private static Map<ResourceLocation, Set<ResourceLocation>> dispositionMap(JsonObject json) {
        Map<ResourceLocation, Set<ResourceLocation>> result = new LinkedHashMap<>();
        if (!json.has("required_dispositions")) return result;
        if (!json.get("required_dispositions").isJsonObject()) {
            throw new IllegalArgumentException("provider required_dispositions must be an object");
        }
        for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject("required_dispositions").entrySet()) {
            ResourceLocation civilization = ResourceLocation.tryParse(entry.getKey());
            if (civilization == null) throw new IllegalArgumentException("Invalid civilization id in required_dispositions: " + entry.getKey());
            Set<ResourceLocation> states = new LinkedHashSet<>();
            JsonElement value = entry.getValue();
            if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                ResourceLocation state = ResourceLocation.tryParse(value.getAsString());
                if (state == null) throw new IllegalArgumentException("Invalid disposition state: " + value);
                states.add(state);
            } else if (value.isJsonArray()) {
                JsonArray array = value.getAsJsonArray();
                for (JsonElement stateElement : array) {
                    if (!stateElement.isJsonPrimitive() || !stateElement.getAsJsonPrimitive().isString()) {
                        throw new IllegalArgumentException("Disposition state values must be strings");
                    }
                    ResourceLocation state = ResourceLocation.tryParse(stateElement.getAsString());
                    if (state == null) throw new IllegalArgumentException("Invalid disposition state: " + stateElement);
                    states.add(state);
                }
            } else {
                throw new IllegalArgumentException("required_dispositions values must be a string or string array");
            }
            if (states.isEmpty()) throw new IllegalArgumentException("required_dispositions state set must not be empty");
            result.put(civilization, states);
        }
        return result;
    }

    private static String optionalString(JsonObject json, String key) {
        return optionalString(json, key, "");
    }

    private static String optionalString(JsonObject json, String key, String fallback) {
        if (!json.has(key)) return fallback;
        JsonElement value = json.get(key);
        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) {
            throw new IllegalArgumentException("provider " + key + " must be a string");
        }
        return value.getAsString().trim();
    }

    private static boolean optionalBoolean(JsonObject json, String key, boolean fallback) {
        if (!json.has(key)) return fallback;
        JsonElement value = json.get(key);
        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isBoolean()) {
            throw new IllegalArgumentException("provider " + key + " must be a boolean");
        }
        return value.getAsBoolean();
    }
}
