package org.infernalstudios.questlog.network.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.network.ClientPacketHandler;
import org.infernalstudios.questlog.network.IPacketContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public record QuestSyncPacket(Map<ResourceLocation, String> definitions,
                              Map<ResourceLocation, String> chapterDefinitions,
                              Map<ResourceLocation, CompoundTag> data,
                              List<ResourceLocation> advancements) {

    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.SERVER_TO_CLIENT;

    // Full sync is intentionally generous, but collection counts still need a
    // hard ceiling before allocation. The wire format is unchanged; these are
    // malformed/corrupt-packet guards rather than a protocol revision.
    private static final int MAX_QUEST_DEFINITIONS = 65_536;
    private static final int MAX_CHAPTER_DEFINITIONS = 8_192;
    private static final int MAX_QUEST_DATA_ENTRIES = 65_536;
    private static final int MAX_ADVANCEMENTS = 131_072;

    public static QuestSyncPacket decode(FriendlyByteBuf buf) {
        Map<ResourceLocation, String> defs = readMap(buf, FriendlyByteBuf::readUtf, MAX_QUEST_DEFINITIONS, "quest definitions");
        Map<ResourceLocation, String> chapters = readMap(buf, FriendlyByteBuf::readUtf, MAX_CHAPTER_DEFINITIONS, "chapter definitions");
        Map<ResourceLocation, CompoundTag> data = readMap(buf, FriendlyByteBuf::readNbt, MAX_QUEST_DATA_ENTRIES, "quest data");
        List<ResourceLocation> advancements = readList(buf, MAX_ADVANCEMENTS, "advancements");
        return new QuestSyncPacket(defs, chapters, data, advancements);
    }

    private static <V> Map<ResourceLocation, V> readMap(FriendlyByteBuf buf,
                                                         Function<FriendlyByteBuf, V> valueReader,
                                                         int maxEntries,
                                                         String label) {
        int size = readBoundedCount(buf, maxEntries, label);
        Map<ResourceLocation, V> map = new HashMap<>(Math.min(size, 16_384));
        for (int i = 0; i < size; i++) {
            ResourceLocation key = buf.readResourceLocation();
            V value = valueReader.apply(buf);
            if (map.put(key, value) != null) {
                throw new IllegalArgumentException("Duplicate " + label + " key in quest sync: " + key);
            }
        }
        return map;
    }

    private static <V> void writeMap(FriendlyByteBuf buf,
                                     Map<ResourceLocation, V> map,
                                     BiConsumer<FriendlyByteBuf, V> valueWriter,
                                     int maxEntries,
                                     String label) {
        requireBoundedCount(map.size(), maxEntries, label);
        buf.writeVarInt(map.size());
        for (Map.Entry<ResourceLocation, V> entry : map.entrySet()) {
            buf.writeResourceLocation(entry.getKey());
            valueWriter.accept(buf, entry.getValue());
        }
    }

    private static List<ResourceLocation> readList(FriendlyByteBuf buf, int maxEntries, String label) {
        int size = readBoundedCount(buf, maxEntries, label);
        List<ResourceLocation> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(buf.readResourceLocation());
        }
        return list;
    }

    private static void writeList(FriendlyByteBuf buf, List<ResourceLocation> list, int maxEntries, String label) {
        requireBoundedCount(list.size(), maxEntries, label);
        buf.writeVarInt(list.size());
        for (ResourceLocation rl : list) {
            buf.writeResourceLocation(rl);
        }
    }

    private static int readBoundedCount(FriendlyByteBuf buf, int maxEntries, String label) {
        int size = buf.readVarInt();
        requireBoundedCount(size, maxEntries, label);
        return size;
    }

    private static void requireBoundedCount(int size, int maxEntries, String label) {
        if (size < 0 || size > maxEntries) {
            throw new IllegalArgumentException(
                    "Invalid quest sync " + label + " count " + size + "; maximum is " + maxEntries
            );
        }
    }

    public static void handle(QuestSyncPacket packet, IPacketContext ctx) {
        ClientPacketHandler.handle(packet, ctx);
    }

    public void encode(FriendlyByteBuf buf) {
        writeMap(buf, this.definitions, FriendlyByteBuf::writeUtf, MAX_QUEST_DEFINITIONS, "quest definitions");
        writeMap(buf, this.chapterDefinitions, FriendlyByteBuf::writeUtf, MAX_CHAPTER_DEFINITIONS, "chapter definitions");
        writeMap(buf, this.data, FriendlyByteBuf::writeNbt, MAX_QUEST_DATA_ENTRIES, "quest data");
        writeList(buf, this.advancements, MAX_ADVANCEMENTS, "advancements");
    }
}
