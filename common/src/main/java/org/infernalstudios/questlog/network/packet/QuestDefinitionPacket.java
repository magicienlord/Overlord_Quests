package org.infernalstudios.questlog.network.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.network.ClientPacketHandler;
import org.infernalstudios.questlog.network.IPacketContext;
import org.infernalstudios.questlog.util.DefinitionLimits;

public class QuestDefinitionPacket {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.SERVER_TO_CLIENT;
    private static final Gson GSON = new GsonBuilder().create();

    private final ResourceLocation id;
    private final JsonObject definition;

    public QuestDefinitionPacket(ResourceLocation id, String json) {
        DefinitionLimits.requireWireSafe(json, "Quest definition " + id);
        JsonObject parsed = GSON.fromJson(json, JsonObject.class);
        if (parsed == null) {
            throw new IllegalArgumentException("Quest definition " + id + " is JSON null");
        }
        this.id = id;
        this.definition = parsed;
    }

    public QuestDefinitionPacket(ResourceLocation id, JsonObject definition) {
        DefinitionLimits.requireWireSafe(definition, "Quest definition " + id);
        this.id = id;
        this.definition = definition.deepCopy();
    }

    public static QuestDefinitionPacket decode(FriendlyByteBuf buf) {
        return new QuestDefinitionPacket(
                buf.readResourceLocation(),
                buf.readUtf(DefinitionLimits.MAX_SYNCED_JSON_CHARS)
        );
    }

    public static void handle(QuestDefinitionPacket packet, IPacketContext ctx) {
        ClientPacketHandler.handle(packet, ctx);
    }

    public void encode(FriendlyByteBuf buf) {
        String json = this.getJsonString();
        DefinitionLimits.requireWireSafe(json, "Quest definition " + this.id);
        buf.writeResourceLocation(this.id);
        buf.writeUtf(json, DefinitionLimits.MAX_SYNCED_JSON_CHARS);
    }

    public ResourceLocation id() {
        return id;
    }

    public String getJsonString() {
        return GSON.toJson(this.definition);
    }
}
