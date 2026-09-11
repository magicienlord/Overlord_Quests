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

import java.util.ArrayList;
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
    private static final int DIALOGUE_TOP = 62;
    private static final int DIALOGUE_LINE_SPACING = 11;
    private static final int DIALOGUE_PARAGRAPH_SPACING = 4;
    private static final int DIALOGUE_SCROLL_STEP = 33;
    private static final double MAX_DISTANCE_SQR = QuestProviderService.MAX_INTERACTION_DISTANCE_SQR;

    private final int providerEntityId;
    private final UUID providerId;
    private String providerName;
    private List<QuestProviderService.InteractionEntry> entries;
    private int page;
    private int dialogueScrollPixels;
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
        QuestProviderService.InteractionEntry previousSelection = this.selectedEntry();
        this.providerName = packet.providerName();
        this.entries = List.copyOf(packet.entries());
        this.pendingAction = false;

        QuestProviderService.InteractionEntry refreshedSelection = this.selectedEntry();
        if (this.selectedQuestId != null && refreshedSelection == null) {
            this.selectedQuestId = null;
            this.dialogueScrollPixels = 0;
        } else if (previousSelection != null
                && refreshedSelection != null
                && previousSelection.state() != refreshedSelection.state()) {
            this.dialogueScrollPixels = 0;
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
                        this.dialogueScrollPixels = 0;
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
        int y = this.dialogueActionY();
        int maxScroll = this.maxDialogueScroll(entry);
        this.dialogueScrollPixels = Math.max(0, Math.min(this.dialogueScrollPixels, maxScroll));

        if (maxScroll > 0) {
            int scrollY = y - 24;
            int scrollWidth = 70;
            int gap = 6;
            int scrollX = x + (panelWidth - (scrollWidth * 2 + gap)) / 2;

            Button up = Button.builder(Component.literal("Up"), ignored -> this.scrollDialogue(entry, -DIALOGUE_SCROLL_STEP))
                    .bounds(scrollX, scrollY, scrollWidth, 20)
                    .build();
            up.active = this.dialogueScrollPixels > 0 && !this.pendingAction;
            this.addRenderableWidget(up);

            Button down = Button.builder(Component.literal("Down"), ignored -> this.scrollDialogue(entry, DIALOGUE_SCROLL_STEP))
                    .bounds(scrollX + scrollWidth + gap, scrollY, scrollWidth, 20)
                    .build();
            down.active = this.dialogueScrollPixels < maxScroll && !this.pendingAction;
            this.addRenderableWidget(down);
        }

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

    private int dialogueActionY() {
        return Math.max(84, this.height - 54);
    }

    private int dialogueViewportBottom() {
        return Math.max(DIALOGUE_TOP + this.font.lineHeight, this.dialogueActionY() - 30);
    }

    private int dialogueWrapWidth() {
        return Math.min(320, Math.max(180, this.width - 60));
    }

    private DialogueLayout dialogueLayout(QuestProviderService.InteractionEntry entry) {
        Quest quest = QuestlogClient.getLocal().getQuest(entry.questId());
        if (quest == null) return DialogueLayout.EMPTY;
        QuestProviderRule rule = quest.getProviderRule();
        if (rule == null) return DialogueLayout.EMPTY;

        List<String> dialogue = rule.dialogue().linesFor(entry.state());
        if (dialogue.isEmpty()) return DialogueLayout.EMPTY;

        List<DialogueLine> lines = new ArrayList<>();
        int yOffset = 0;
        int wrapWidth = this.dialogueWrapWidth();
        for (int paragraphIndex = 0; paragraphIndex < dialogue.size(); paragraphIndex++) {
            List<FormattedCharSequence> wrapped = this.font.split(Component.literal(dialogue.get(paragraphIndex)), wrapWidth);
            for (FormattedCharSequence line : wrapped) {
                lines.add(new DialogueLine(line, yOffset));
                yOffset += DIALOGUE_LINE_SPACING;
            }
            if (paragraphIndex + 1 < dialogue.size()) {
                yOffset += DIALOGUE_PARAGRAPH_SPACING;
            }
        }
        return new DialogueLayout(List.copyOf(lines), yOffset);
    }

    private int maxDialogueScroll(QuestProviderService.InteractionEntry entry) {
        DialogueLayout layout = this.dialogueLayout(entry);
        int viewportHeight = Math.max(this.font.lineHeight, this.dialogueViewportBottom() - DIALOGUE_TOP);
        return Math.max(0, layout.contentHeight() - viewportHeight);
    }

    private void scrollDialogue(QuestProviderService.InteractionEntry entry, int amount) {
        if (this.pendingAction || amount == 0) return;
        int maxScroll = this.maxDialogueScroll(entry);
        int next = Math.max(0, Math.min(this.dialogueScrollPixels + amount, maxScroll));
        if (next == this.dialogueScrollPixels) return;
        this.dialogueScrollPixels = next;
        this.rebuildWidgets();
    }

    private void returnToList() {
        if (this.pendingAction) return;
        this.selectedQuestId = null;
        this.dialogueScrollPixels = 0;
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

        DialogueLayout layout = this.dialogueLayout(entry);
        if (layout.lines().isEmpty()) return;

        int viewportBottom = this.dialogueViewportBottom();
        int maxScroll = this.maxDialogueScroll(entry);
        int scroll = Math.max(0, Math.min(this.dialogueScrollPixels, maxScroll));
        for (DialogueLine line : layout.lines()) {
            int y = DIALOGUE_TOP + line.yOffset() - scroll;
            if (y + this.font.lineHeight < DIALOGUE_TOP) continue;
            if (y + this.font.lineHeight > viewportBottom) continue;
            graphics.drawCenteredString(this.font, line.text(), this.width / 2, y, 0xE0E0E0);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        QuestProviderService.InteractionEntry selected = this.selectedEntry();
        if (selected != null && this.maxDialogueScroll(selected) > 0 && delta != 0.0D) {
            this.scrollDialogue(selected, delta > 0.0D ? -DIALOGUE_SCROLL_STEP : DIALOGUE_SCROLL_STEP);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private record DialogueLine(FormattedCharSequence text, int yOffset) {
    }

    private record DialogueLayout(List<DialogueLine> lines, int contentHeight) {
        private static final DialogueLayout EMPTY = new DialogueLayout(List.of(), 0);
    }
}
