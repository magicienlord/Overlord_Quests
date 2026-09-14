package org.infernalstudios.questlog.core.quests;

import com.google.gson.JsonObject;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.core.quests.objectives.block.BlockInteractObjective;
import org.infernalstudios.questlog.core.quests.objectives.block.BlockMineObjective;
import org.infernalstudios.questlog.core.quests.objectives.block.BlockPlaceObjective;
import org.infernalstudios.questlog.core.quests.objectives.entity.*;
import org.infernalstudios.questlog.core.quests.objectives.item.*;
import org.infernalstudios.questlog.core.quests.objectives.logic.AndObjective;
import org.infernalstudios.questlog.core.quests.objectives.logic.NotObjective;
import org.infernalstudios.questlog.core.quests.objectives.logic.OrObjective;
import org.infernalstudios.questlog.core.quests.objectives.misc.*;
import org.infernalstudios.questlog.overlord.minions.MinionUnlockedObjective;
import org.infernalstudios.questlog.overlord.narrative.DispositionObjective;
import org.infernalstudios.questlog.overlord.narrative.FactObjective;
import org.infernalstudios.questlog.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class QuestObjectiveRegistry {

    private static final Map<ResourceLocation, Function<JsonObject, Objective>> REGISTRY = new HashMap<>();
    private static final Map<ResourceLocation, EditorMetadata> METADATA = new HashMap<>();

    static {
        // Block
        register(new ResourceLocation("questlog", "block_mine"), BlockMineObjective::new,
                new EditorMetadata("block", "Block ID:", "required_amount", EditorMetadata.SuggestionType.BLOCK));
        register(new ResourceLocation("questlog", "block_place"), BlockPlaceObjective::new,
                new EditorMetadata("block", "Block ID:", "required_amount", EditorMetadata.SuggestionType.BLOCK));
        register(new ResourceLocation("questlog", "block_interact"), BlockInteractObjective::new,
                new EditorMetadata("block", "Block ID:", "required_amount", EditorMetadata.SuggestionType.BLOCK));

        // Entity
        register(new ResourceLocation("questlog", "entity_breed"), EntityBreedObjective::new,
                new EditorMetadata("entity", "Entity ID:", "required_amount", EditorMetadata.SuggestionType.ENTITY_TYPE));
        // Historical upstream semantics: the player dies to the matching entity.
        register(new ResourceLocation("questlog", "entity_death"), EntityDeathObjective::new,
                new EditorMetadata("entity", "Killer Entity ID:", "required_amount", EditorMetadata.SuggestionType.ENTITY_TYPE));
        // OVERLORD extension: the matching target entity itself dies, regardless of cause.
        register(new ResourceLocation("questlog", "entity_died"), EntityDiedObjective::new,
                new EditorMetadata("entity", "Target Entity ID:", "required_amount", EditorMetadata.SuggestionType.ENTITY_TYPE));
        register(new ResourceLocation("questlog", "entity_kill"), EntityKillObjective::new,
                new EditorMetadata("entity", "Entity ID:", "required_amount", EditorMetadata.SuggestionType.ENTITY_TYPE));
        register(new ResourceLocation("questlog", "entity_kill_history"), EntityKillHistoryObjective::new,
                new EditorMetadata("entity", "Entity ID / Matcher:", null, EditorMetadata.SuggestionType.ENTITY_TYPE));
        register(new ResourceLocation("questlog", "entity_kill_stat"), EntityKillStatObjective::new,
                new EditorMetadata("entity", "Exact Entity ID:", "required_amount", EditorMetadata.SuggestionType.ENTITY_TYPE));
        register(new ResourceLocation("questlog", "entity_approach"), EntityApproachObjective::new,
                new EditorMetadata("entity", "Entity ID:", "required_amount", EditorMetadata.SuggestionType.ENTITY_TYPE));
        register(new ResourceLocation("questlog", "entity_tame"), EntityTameObjective::new,
                new EditorMetadata("entity", "Entity ID:", "required_amount", EditorMetadata.SuggestionType.ENTITY_TYPE));
        register(new ResourceLocation("overlord_reign", "owned_tame_death"), OwnedTameDeathObjective::new,
                new EditorMetadata("entity", "Owned tameable ID / tag:", "required_amount", EditorMetadata.SuggestionType.ENTITY_TYPE));

        // Logic
        register(new ResourceLocation("questlog", "and"), AndObjective::new,
                new EditorMetadata(null, null, "required_amount"));
        register(new ResourceLocation("questlog", "or"), OrObjective::new,
                new EditorMetadata(null, null, "required_amount"));
        register(new ResourceLocation("questlog", "not"), NotObjective::new,
                new EditorMetadata(null, null, "required_amount"));

        // Item
        register(new ResourceLocation("questlog", "item_craft"), ItemCraftObjective::new,
                new EditorMetadata("item", "Item ID:", "required_amount", EditorMetadata.SuggestionType.ITEM));
        register(new ResourceLocation("questlog", "item_craft_stat"), ItemCraftStatObjective::new,
                new EditorMetadata("item", "Exact Item ID:", "required_amount", EditorMetadata.SuggestionType.ITEM));
        register(new ResourceLocation("questlog", "item_drop"), ItemDropObjective::new,
                new EditorMetadata("item", "Item ID:", "required_amount", EditorMetadata.SuggestionType.ITEM));
        register(new ResourceLocation("questlog", "item_equip"), ItemEquipObjective::new,
                new EditorMetadata("item", "Item ID:", "required_amount", EditorMetadata.SuggestionType.ITEM));
        register(new ResourceLocation("questlog", "item_obtain"), ItemObtainObjective::new,
                new EditorMetadata("item", "Item ID:", "required_amount", EditorMetadata.SuggestionType.ITEM));
        register(new ResourceLocation("questlog", "item_use"), ItemUseObjective::new,
                new EditorMetadata("item", "Item ID:", "required_amount", EditorMetadata.SuggestionType.ITEM));

        // Misc
        register(new ResourceLocation("questlog", "stat"), StatisticObjective::new,
                new EditorMetadata("stat", "Stat ID:", "required_amount", EditorMetadata.SuggestionType.CUSTOM_STAT));
        register(new ResourceLocation("questlog", "trample"), TrampleObjective::new,
                new EditorMetadata(null, null, "required_amount"));
        register(new ResourceLocation("questlog", "enchant"), EnchantObjective::new,
                new EditorMetadata("enchantment", "Enchantment ID:", "required_amount", EditorMetadata.SuggestionType.ENCHANTMENT));
        register(new ResourceLocation("questlog", "effect_added"), EffectAddedObjective::new,
                new EditorMetadata("effect", "Effect ID:", "required_amount", EditorMetadata.SuggestionType.MOB_EFFECT));
        register(new ResourceLocation("questlog", "visit_biome"), VisitBiomeObjective::new,
                new EditorMetadata("biome", "Biome ID:", "required_amount", EditorMetadata.SuggestionType.BIOME));
        register(new ResourceLocation("questlog", "visit_dimension"), VisitDimensionObjective::new,
                new EditorMetadata("dimension", "Dimension:", "required_amount", EditorMetadata.SuggestionType.DIMENSION));
        register(new ResourceLocation("questlog", "visit_dimension_history"), VisitDimensionHistoryObjective::new,
                new EditorMetadata("dimension", "Exact Dimension ID:", null, EditorMetadata.SuggestionType.DIMENSION));
        register(new ResourceLocation("questlog", "visit_position"), VisitPositionObjective::new,
                new EditorMetadata("bounds", "Position / Bounds:", null));
        register(new ResourceLocation("questlog", "visit_position_history"), VisitPositionHistoryObjective::new,
                new EditorMetadata("bounds", "Position / Bounds:", null));
        register(new ResourceLocation("questlog", "visit_structure"), VisitStructureObjective::new,
                new EditorMetadata("structure", "Structure ID:", "required_amount", EditorMetadata.SuggestionType.STRUCTURE));
        register(new ResourceLocation("questlog", "visit_structure_history"), VisitStructureHistoryObjective::new,
                new EditorMetadata("structure", "Exact Structure ID:", null, EditorMetadata.SuggestionType.STRUCTURE));
        register(new ResourceLocation("questlog", "ender_dragon_defeated"), EnderDragonDefeatedObjective::new,
                new EditorMetadata(null, null, null));
        register(new ResourceLocation("questlog", "quest_complete"), QuestCompleteObjective::new,
                new EditorMetadata("quest", "Quest ID:", "required_amount", EditorMetadata.SuggestionType.QUEST));
        register(new ResourceLocation("questlog", "read"), ReadObjective::new,
                new EditorMetadata(null, null, "required_amount"));
        register(new ResourceLocation("questlog", "advancement"), AdvancementObjective::new,
                new EditorMetadata("advancement", "Advancement ID:", "required_amount", EditorMetadata.SuggestionType.ADVANCEMENT));
        register(new ResourceLocation("questlog", "unobtainable"), UnobtainableObjective::new,
                new EditorMetadata(null, null, "required_amount"));
        register(new ResourceLocation("questlog", "disposition"), DispositionObjective::new,
                new EditorMetadata("civilization", "Civilization ID:", null));
        register(new ResourceLocation("questlog", "fact"), FactObjective::new,
                new EditorMetadata("fact", "Narrative Fact ID:", null));
        register(new ResourceLocation("questlog", "minion_unlocked"), MinionUnlockedObjective::new,
                new EditorMetadata("slot", "Minion Slot:", null));

        // Origins
        register(new ResourceLocation("questlog", "origin"), OriginObjective::new,
                new EditorMetadata("origin", "Origin ID:", "required_amount", EditorMetadata.SuggestionType.ORIGIN));
    }

    public static Set<ResourceLocation> getRegisteredTypes() {
        return REGISTRY.keySet();
    }

    public static void register(ResourceLocation id, Function<JsonObject, Objective> factory, EditorMetadata metadata) {
        REGISTRY.put(id, factory);
        METADATA.put(id, metadata);
    }

    public static EditorMetadata getMetadata(ResourceLocation id) {
        return METADATA.get(id);
    }

    public static Objective create(JsonObject definition) {
        String typeString = JsonUtils.getString(definition, "type");
        if (!typeString.contains(":")) {
            typeString = "questlog:" + typeString;
        }

        ResourceLocation type;
        try {
            type = new ResourceLocation(typeString);
        } catch (ResourceLocationException e) {
            throw new IllegalStateException("Invalid quest type: " + typeString);
        }

        return QuestObjectiveRegistry.create(type, definition);
    }

    public static Objective create(ResourceLocation type, JsonObject definition) {
        if (!REGISTRY.containsKey(type)) {
            throw new NullPointerException("Objective type not found: " + type);
        }
        try {
            return REGISTRY.get(type).apply(definition);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create objective of type " + type, e);
        }
    }
}
