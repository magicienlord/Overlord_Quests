package org.infernalstudios.questlog.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.core.QuestManager;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.rewards.ChoiceReward;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.network.IPacketContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestRewardCollectPacket {
    public static final IPacketContext.Direction DIRECTION = IPacketContext.Direction.CLIENT_TO_SERVER;
    private static final int MAX_SELECTIONS = 256;

    private final ResourceLocation id;
    private final int rewardIndex;
    private final List<Integer> selections;

    public QuestRewardCollectPacket(ResourceLocation id, int rewardIndex) {
        this(id, rewardIndex, Collections.emptyList());
    }

    public QuestRewardCollectPacket(ResourceLocation id, int rewardIndex, List<Integer> selections) {
        this.id = id;
        this.rewardIndex = rewardIndex;
        this.selections = selections == null ? Collections.emptyList() : List.copyOf(selections);
    }

    public ResourceLocation id() { return this.id; }
    public int rewardIndex() { return this.rewardIndex; }
    public List<Integer> selections() { return this.selections; }

    public static QuestRewardCollectPacket decode(FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation();
        int rewardIndex = buf.readInt();
        int selectionCount = buf.readVarInt();
        if (selectionCount < 0 || selectionCount > MAX_SELECTIONS) {
            throw new IllegalArgumentException("Invalid quest reward selection count: " + selectionCount);
        }
        List<Integer> selections = new ArrayList<>(selectionCount);
        for (int i = 0; i < selectionCount; i++) {
            selections.add(buf.readInt());
        }
        return new QuestRewardCollectPacket(id, rewardIndex, selections);
    }

    public static void handle(QuestRewardCollectPacket packet, IPacketContext ctx) {
        if (!(ctx.getSender() instanceof ServerPlayer sender)) {
            Questlog.LOGGER.warn("Ignoring reward collection request because no server player sender is available");
            return;
        }
        if (ServerPlayerManager.INSTANCE == null) {
            Questlog.LOGGER.warn("Ignoring reward collection for {} because the server quest manager is unavailable", packet.id);
            return;
        }

        QuestManager manager = ServerPlayerManager.INSTANCE.getManagerByPlayer(sender);
        Quest quest = manager.getQuest(packet.id);
        if (quest == null) {
            Questlog.LOGGER.warn("Quest {} not found", packet.id);
            return;
        }

        // Reward eligibility is authoritative on the server. A client packet must
        // never be able to claim a reward before its quest is actually complete.
        if (!quest.isCompleted()) {
            Questlog.LOGGER.warn("Ignoring early reward collection request for incomplete quest {}", packet.id);
            return;
        }

        if (packet.rewardIndex < 0 || packet.rewardIndex >= quest.rewards.size()) {
            Questlog.LOGGER.warn(
                    "Ignoring invalid reward index {} for quest {} with {} reward(s)",
                    packet.rewardIndex,
                    packet.id,
                    quest.rewards.size()
            );
            return;
        }

        Reward reward = quest.rewards.get(packet.rewardIndex);
        if (reward.hasRewarded()) {
            return;
        }

        if (reward instanceof ChoiceReward choiceReward) {
            if (!choiceReward.isValidSelection(packet.selections())) {
                Questlog.LOGGER.warn(
                        "Ignoring invalid choice reward selection for quest {} reward {}: expected {} unique valid selection(s), got {}",
                        packet.id,
                        packet.rewardIndex,
                        choiceReward.getPickCount(),
                        packet.selections().size()
                );
                return;
            }
            choiceReward.setSelectedIndices(packet.selections());
        } else if (!packet.selections().isEmpty()) {
            Questlog.LOGGER.warn(
                    "Ignoring unexpected choice selections for non-choice reward {} in quest {}",
                    packet.rewardIndex,
                    packet.id
            );
            return;
        }

        reward.applyReward(sender);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.id);
        buf.writeInt(this.rewardIndex);
        buf.writeVarInt(this.selections.size());
        for (int sel : this.selections) {
            buf.writeInt(sel);
        }
    }
}
