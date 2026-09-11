package org.infernalstudios.questlog.client.provider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import org.infernalstudios.questlog.QuestlogClient;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.network.packet.QuestProviderActionPacket;
import org.infernalstudios.questlog.network.packet.QuestProviderOpenPacket;
import org.infernalstudios.questlog.overlord.provider.QuestProviderRule;
import org.infernalstudios.questlog.overlord.provider.QuestProviderService;
import org.infernalstudios.questlog.platform.Services;

import java.util.List;
import java.util.UUID;

/**
 * Temporary neutral provider interaction scaffold.
 *
 * The final NPC sidequest presentation remains a dedicated design pass. This
 * screen intentionally contains no invented civilization art or story dialogue.
 * Any dialogue rendered here comes directly from the authored quest definition.
 */
public final class QuestProviderScreen extends Screen {
    private static final int PAGE_SIZE = 7;
    private static final double MAX_DISTANCE_SQR = QuestProviderService.MAX_INTERACTION_DISTANCE_SQR;

    private final int providerEntityId;
    private final UUID providerId;
    private String providerName;
    private List<QuestProviderService.InteractionEntry> entries;
    private int page;
    private boolean pendingAction;
    private ResourceLocation selectedQuestId;

    public QuestProviderScreen(QuestProviderOpenPacket packet) {
        super(Component.literal("Quests"));
        this.providerEntityId = packet.providerEntityId();
        this.providerId = packet.providerId();
        this.providerName = packet.providerName();
        this.entries = List.copyOf(packet.entries());
    }

    public boolean matches(QuestProviderOpenPacket packet) {
        return packet != null
                && this.providerEntityId == packet.providerEntityId()
                && this.providerId.equals(packet.providerId());
    }

    public void refresh(QuestProviderOpenPacket packet) {
        if (!this.matches(packet)) {
            return;
        }
        this.providerName = packet.providerName();
        this.entries = List.copyOf(packet.entries());
        this.pendingAction = false;
        if (this.selectedQuestId != null && this.selectedEntry() == null) {
            this.selectedQuestId = null;
        }
        this.rebuildWidgets();
    }

    @Override
    protected void init() {
        super.init();

        int panelWidth = Math.min(340, Math.max(220, this.width - 40));
        int x = (this.width - panelWidth) / 2;
        QuestProviderService.InteractionEntry selected = this.selectedEntry();
        if (selected != null) {
            this.initDialogueControls(selected, x, panelWidth);
            return;
        }

        int firstY = Math.max(52, (this.height - (PAGE_SIZE * 24 + 78)) / 2 + 44);
        int maxPage = Math.max(0, (this.entries.size() - 1) / PAGE_SIZE);
        this.page = Math.max(0, Math.min(this.page, maxPage));

        int from = this.page * PAGE_SIZE;
        int to = Math.min(this.entries.size(), from + PAGE_SIZE);
        for (int i = from; i < to; i++) {
            QuestProviderService.InteractionEntry entry = this.entries.get(i);
            Button button = Button.builder(this.messageFor(entry), ignored -> {
                        this.selectedQuestId = entry.questId();
                        this.rebuildWidgets();
                    })
                    .bounds(x, firstY + (i - from) * 24, panelWidth, 20)
                    .build();
            button.active = !this.pendingAction;
            this.addRenderableWidget(button);
        }

        int navigationY = firstY + PAGE_SIZE * 24 + 4;
        if (maxPage > 0) {
            Button previous = Button.builder(Component.literal("<"), ignored -> {
                        this.page = Math.max(0, this.page - 1);
                        this.rebuildWidgets();
                    })
                    .bounds(x, navigationY, 30, 20)
                    .build();
            previous.active = this.page > 0 && !this.pendingAction;
            this.addRenderableWidget(previous);

            Button next = Button.builder(Component.literal(">"), ignored -> {
                        this.page = Math.min(maxPage, this.page + 1);
                        this.rebuildWidgets();
                    })
                    .bounds(x + panelWidth - 30, navigationY, 30, 20)
                    .build();
            next.active = this.page < maxPage && !this.pendingAction;
            this.addRenderableWidget(next);
        }

        this.addRenderableWidget(Button.builder(Component.translatable("gui.done"), ignored -> this.onClose())
                .bounds(x + (panelWidth - 120) / 2, navigationY + 26, 120, 20)
                .build());
    }

    private void initDialogueControls(QuestProviderService.InteractionEntry entry, int x, int panelWidth) {
        int y = Math.max(84, this.height - 54);
        boolean actionable = entry.state() == QuestProviderService.InteractionState.AVAILABLE
                || entry.state() == QuestProviderService.InteractionState.READY_TO_TURN_IN;

        if (actionable) {
            Component actionText = entry.state() == QuestProviderService.InteractionState.AVAILABLE
                    ? Component.literal("Accept")
                    : Component.literal("Turn In");
            Button action = Button.builder(actionText, ignored -> this.perform(entry))
                    .bounds(x, y, (panelWidth - 6) / 2, 20)
                    .build();
            action.active = !this.pendingAction;
            this.addRenderableWidget(action);

            Component returnText = entry.state() == QuestProviderService.InteractionState.AVAILABLE
                    ? Component.literal("Decline")
                    : Component.literal("Back");
            Button back = Button.builder(returnText, ignored -> this.returnToList())
                    .bounds(x + (panelWidth + 6) / 2, y, (panelWidth - 6) / 2, 20)
                    .build();
            back.active = !this.pendingAction;
            this.addRenderableWidget(back);
        } else {
            Button back = Button.builder(Component.literal("Back"), ignored -> this.returnToList())
                    .bounds(x + (panelWidth - 120) / 2, y, 120, 20)
                    .build();
            back.active = !this.pendingAction;
            this.addRenderableWidget(back);
        }
    }

    private void returnToList() {
        if (this.pendingAction) return;
        this.selectedQuestId = null;
        this.rebuildWidgets();
    }

    private QuestProviderService.InteractionEntry selectedEntry() {
        if (this.selectedQuestId == null) return null;
        for (QuestProviderService.InteractionEntry entry : this.entries) {
            if (this.selectedQuestId.equals(entry.questId())) {
                return entry;
            }
        }
        return null;
    }

    private Component messageFor(QuestProviderService.InteractionEntry entry) {
        Quest quest = QuestlogClient.getLocal().getQuest(entry.questId());
        Component title = quest == null ? Component.literal(entry.questId().toString()) : quest.getDisplay().getTitle();
        return switch (entry.state()) {
            case AVAILABLE -> Component.literal("Available: ").append(title.copy());
            case IN_PROGRESS -> Component.literal("In Progress: ").append(title.copy());
            case READY_TO_TURN_IN -> Component.literal("Ready: ").append(title.copy());
            case FAILED -> Component.literal("Failed: ").append(title.copy());
        };
    }

    private void perform(QuestProviderService.InteractionEntry entry) {
        if (this.pendingAction) return;

        QuestProviderActionPacket.Action action = switch (entry.state()) {
            case AVAILABLE -> QuestProviderActionPacket.Action.ACCEPT;
            case READY_TO_TURN_IN -> QuestProviderActionPacket.Action.TURN_IN;
            case IN_PROGRESS, FAILED -> null;
        };
        if (action == null) return;

        this.pendingAction = true;
        this.rebuildWidgets();
        Services.PLATFORM.sendPacketToServer(new QuestProviderActionPacket(
                this.providerEntityId,
                this.providerId,
                entry.questId(),
                action
        ));
    }

    @Override
    public void tick() {
        super.tick();
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null) {
            this.onClose();
            return;
        }

        Entity provider = minecraft.level.getEntity(this.providerEntityId);
        if (provider == null
                || !provider.isAlive()
                || !this.providerId.equals(provider.getUUID())
                || minecraft.player.distanceToSqr(provider) > MAX_DISTANCE_SQR) {
            this.onClose();
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(
                this.font,
                this.providerName.isBlank() ? this.title : Component.literal(this.providerName),
                this.width / 2,
                22,
                0xFFFFFF
        );

        QuestProviderService.InteractionEntry selected = this.selectedEntry();
        if (selected == null) {
            graphics.drawCenteredString(this.font, this.title, this.width / 2, 36, 0xB8B8B8);
            if (this.entries.isEmpty()) {
                graphics.drawCenteredString(
                        this.font,
                        Component.literal("No quests available."),
                        this.width / 2,
                        this.height / 2,
                        0xB8B8B8
                );
            }
        } else {
            this.renderDialogue(graphics, selected);
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void renderDialogue(GuiGraphics graphics, QuestProviderService.InteractionEntry entry) {
        Quest quest = QuestlogClient.getLocal().getQuest(entry.questId());
        Component questTitle = quest == null ? Component.literal(entry.questId().toString()) : quest.getDisplay().getTitle();
        graphics.drawCenteredString(this.font, questTitle, this.width / 2, 40, 0xFFFFFF);

        if (quest == null) return;
        QuestProviderRule rule = quest.getProviderRule();
        if (rule == null) return;

        List<String> dialogue = rule.dialogue().linesFor(entry.state());
        if (dialogue.isEmpty()) return;

        int wrapWidth = Math.min(320, Math.max(180, this.width - 60));
        int y = 62;
        int maxY = Math.max(y, this.height - 72);
        for (String paragraph : dialogue) {
            List<FormattedCharSequence> lines = this.font.split(Component.literal(paragraph), wrapWidth);
            for (FormattedCharSequence line : lines) {
                if (y > maxY) return;
                graphics.drawCenteredString(this.font, line, this.width / 2, y, 0xE0E0E0);
                y += 11;
            }
            y += 4;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
