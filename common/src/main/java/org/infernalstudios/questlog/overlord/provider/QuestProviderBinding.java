package org.infernalstudios.questlog.overlord.provider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;
import java.util.UUID;

/** Durable identity of the NPC that accepted/issued a provider-bound sidequest. */
public record QuestProviderBinding(
        UUID providerId,
        ResourceLocation entityType,
        ResourceLocation dimension,
        BlockPos position,
        String displayName,
        @Nullable ResourceLocation civilization,
        String role
) {
    public QuestProviderBinding {
        if (providerId == null || entityType == null || dimension == null) {
            throw new IllegalArgumentException("provider id, entity type and dimension are required");
        }
        position = position == null ? BlockPos.ZERO : position.immutable();
        displayName = displayName == null ? "" : displayName;
        role = role == null ? "" : role;
    }

    public static QuestProviderBinding fromEntity(Entity entity, QuestProviderRule rule) {
        if (entity == null) {
            throw new IllegalArgumentException("provider entity must not be null");
        }
        return new QuestProviderBinding(
                entity.getUUID(),
                BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()),
                entity.level().dimension().location(),
                entity.blockPosition(),
                entity.getDisplayName().getString(),
                rule == null ? null : rule.civilization(),
                rule == null ? "" : rule.role()
        );
    }

    public boolean matches(Entity entity) {
        return entity != null && this.providerId.equals(entity.getUUID());
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("provider_id", this.providerId);
        tag.putString("entity_type", this.entityType.toString());
        tag.putString("dimension", this.dimension.toString());
        tag.putInt("x", this.position.getX());
        tag.putInt("y", this.position.getY());
        tag.putInt("z", this.position.getZ());
        tag.putString("display_name", this.displayName);
        if (this.civilization != null) {
            tag.putString("civilization", this.civilization.toString());
        }
        if (!this.role.isEmpty()) {
            tag.putString("role", this.role);
        }
        return tag;
    }

    @Nullable
    public static QuestProviderBinding load(CompoundTag tag) {
        if (tag == null || !tag.hasUUID("provider_id")) {
            return null;
        }
        ResourceLocation entityType = ResourceLocation.tryParse(tag.getString("entity_type"));
        ResourceLocation dimension = ResourceLocation.tryParse(tag.getString("dimension"));
        if (entityType == null || dimension == null) {
            return null;
        }
        ResourceLocation civilization = tag.contains("civilization")
                ? ResourceLocation.tryParse(tag.getString("civilization"))
                : null;
        return new QuestProviderBinding(
                tag.getUUID("provider_id"),
                entityType,
                dimension,
                new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z")),
                tag.getString("display_name"),
                civilization,
                tag.getString("role")
        );
    }
}
