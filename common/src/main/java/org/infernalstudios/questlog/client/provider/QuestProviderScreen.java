package org.infernalstudios.questlog.client.provider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import org.infernalstudios.questlog.QuestlogClient;
import org.infernalstudios.questlog.client.gui.OverlordPresentationTheme;
import org.infernalstudios.questlog.client.gui.QuestlogGuiSet;
import org.infernalstudios.questlog.client.gui.components.QuestlogWideButton;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.display.Palette;
import org.infernalstudios.questlog.network.packet.QuestProviderActionPacket;
import org.infernalstudios.questlog.network.packet.QuestProviderOpenPacket;
import org.infernalstudios.questlog.overlord.provider.QuestProviderRule;
import org.infernalstudios.questlog.overlord.provider.QuestProviderService;
import org.infernalstudios.questlog.platform.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * In-world NPC provider presentation.
 *
 * Provider authority remains server-side. This screen only presents the bounded
 * server snapshot. Its parchment, spacing and controls intentionally share the
 * OVERLORD QUESTS presentation theme, while provider NPCs remain ordinary
 * in-world entities and do not use the incorporeal five-reaction portrait roster.
 */
public final class QuestProviderScreen extends Screen {
    private static final int MAX_PAGE_SIZE = 7;
    private static final int PANEL_MAX_WIDTH = 430;
    private static final int PANEL_MAX_HEIGHT = 286;
    private static final int PANEL_MIN_HEIGHT = 190;
    private static final int LIST_TOP = 45;
    private static final int LIST_ROW_STEP = 24;
    private static final int DIALOGUE_TOP = 54;
    private static final int DIALOGUE_LINE_SPACING = 11;
    private static final int DIALOGUE_PARAGRAPH_SPACING = 4;
    private static final int DIALOGUE_SCROLL_STEP = 33;
    private static final int DIALOGUE_BOTTOM_INSET = 38;
    private static final int BUTTON_GAP = 6;
    private static final double MAX_DISTANCE_SQR = QuestProviderService.MAX_INTERACTION_DISTANCE_SQR;
    private static final Palette DEFAULT_PALETTE = new Palette(null, null, null, null, null);

    private final int providerEntityId;
    private final UUID providerId;
    private String providerName;
    private List<QuestProviderService.InteractionEntry> entries;
    private int page;
    private int dialogueScrollPixels;
    private boolean pendingAction;
    private ResourceLocation selectedQuestId;

    private int panelX;
    private int panelY;
    private int panelWidth;
    private int panelHeight;
    private int pageSize;
    private QuestlogGuiSet guiSet = QuestlogGuiSet.DEFAULT;

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
        this.configurePanel();

        QuestProviderService.InteractionEntry selected = this.selectedEntry();
        if (selected != null) {
            this.initDialogueControls(selected);
        } else {
            this.initQuestList();
        }
    }

    private void configurePanel() {
        this.panelWidth = Math.max(
                OverlordPresentationTheme.PANEL_MIN_WIDTH,
                Math.min(PANEL_MAX_WIDTH, this.width - OverlordPresentationTheme.OUTER_MARGIN * 2)
        );
        this.panelHeight = Math.max(PANEL_MIN_HEIGHT, Math.min(PANEL_MAX_HEIGHT, this.height - 58));
        this.panelX = (this.width - this.panelWidth) / 2;
        this.panelY = Math.max(
                OverlordPresentationTheme.PANEL_TOP_MIN,
                (this.height - this.panelHeight - 22) / 2
        );
        this.pageSize = Math.max(3, Math.min(MAX_PAGE_SIZE, (this.panelHeight - 102) / LIST_ROW_STEP));

        this.guiSet = OverlordPresentationTheme.resize(
                QuestlogGuiSet.DEFAULT,
                this.panelWidth,
                170,
                this.panelHeight
        );
    }

    private void initQuestList() {
        int maxPage = Math.max(0, (this.entries.size() - 1) / this.pageSize);
        this.page = Math.max(0, Math.min(this.page, maxPage));

        int from = this.page * this.pageSize;
        int to = Math.min(this.entries.size(), from + this.pageSize);
        int inset = OverlordPresentationTheme.PANEL_CONTENT_INSET;
        int rowX = this.panelX + inset;
        int rowWidth = this.panelWidth - inset * 2;
        int firstY = this.panelY + LIST_TOP;

        for (int i = from; i < to; i++) {
            QuestProviderService.InteractionEntry entry = this.entries.get(i);
            QuestlogWideButton button = new QuestlogWideButton(
                    rowX,
                    firstY + (i - from) * LIST_ROW_STEP,
                    rowWidth,
                    DEFAULT_PALETTE.textColor(),
                    DEFAULT_PALETTE.hoveredTextColor(),
                    this.messageFor(entry),
                    () -> {
                        this.selectedQuestId = entry.questId();
                        this.dialogueScrollPixels = 0;
                        this.rebuildWidgets();
                    },
                    this.guiSet
            );
            button.active = !this.pendingAction;
            this.addRenderableWidget(button);
        }

        int navY = this.panelY + this.panelHeight - 27;
        if (maxPage > 0) {
            int navWidth = 54;
            QuestlogWideButton previous = new QuestlogWideButton(
                    this.panelX + inset,
                    navY,
                    navWidth,
                    DEFAULT_PALETTE.textColor(),
                    DEFAULT_PALETTE.hoveredTextColor(),
                    Component.literal("<"),
                    () -> {
                        this.page = Math.max(0, this.page - 1);
                        this.rebuildWidgets();
                    },
                    this.guiSet
            );
            previous.active = this.page > 0 && !this.pendingAction;
            this.addRenderableWidget(previous);

            QuestlogWideButton next = new QuestlogWideButton(
                    this.panelX + this.panelWidth - inset - navWidth,
                    navY,
                    navWidth,
                    DEFAULT_PALETTE.textColor(),
                    DEFAULT_PALETTE.hoveredTextColor(),
                    Component.literal(">"),
                    () -> {
                        this.page = Math.min(maxPage, this.page + 1);
                        this.rebuildWidgets();
                    },
                    this.guiSet
            );
            next.active = this.page < maxPage && !this.pendingAction;
            this.addRenderableWidget(next);
        }

        int doneWidth = Math.min(126, this.panelWidth - inset * 2);
        this.addRenderableWidget(new QuestlogWideButton(
                OverlordPresentationTheme.centeredX(this.panelX, this.panelWidth, doneWidth),
                this.panelY + this.panelHeight + OverlordPresentationTheme.BELOW_PANEL_GAP,
                doneWidth,
                DEFAULT_PALETTE.textColor(),
                DEFAULT_PALETTE.hoveredTextColor(),
                Component.translatable("gui.done"),
                this::onClose,
                this.guiSet
        ));
    }

    private void initDialogueControls(QuestProviderService.InteractionEntry entry) {
        int inset = OverlordPresentationTheme.PANEL_CONTENT_INSET;
        int actionY = this.panelY + this.panelHeight + OverlordPresentationTheme.BELOW_PANEL_GAP;
        int maxScroll = this.maxDialogueScroll(entry);
        this.dialogueScrollPixels = Math.max(0, Math.min(this.dialogueScrollPixels, maxScroll));

        if (maxScroll > 0) {
            int scrollWidth = Math.min(88, (this.panelWidth - inset * 2 - BUTTON_GAP) / 2);
            int totalScrollWidth = scrollWidth * 2 + BUTTON_GAP;
            int scrollX = OverlordPresentationTheme.centeredX(this.panelX, this.panelWidth, totalScrollWidth);
            int scrollY = this.panelY + this.panelHeight - 27;

            QuestlogWideButton up = new QuestlogWideButton(
                    scrollX,
                    scrollY,
                    scrollWidth,
                    DEFAULT_PALETTE.textColor(),
                    DEFAULT_PALETTE.hoveredTextColor(),
                    Component.literal("Up"),
                    () -> this.scrollDialogue(entry, -DIALOGUE_SCROLL_STEP),
                    this.guiSet
            );
            up.active = this.dialogueScrollPixels > 0 && !this.pendingAction;
            this.addRenderableWidget(up);

            QuestlogWideButton down = new QuestlogWideButton(
                    scrollX + scrollWidth + BUTTON_GAP,
                    scrollY,
                    scrollWidth,
                    DEFAULT_PALETTE.textColor(),
                    DEFAULT_PALETTE.hoveredTextColor(),
                    Component.literal("Down"),
                    () -> this.scrollDialogue(entry, DIALOGUE_SCROLL_STEP),
                    this.guiSet
            );
            down.active = this.dialogueScrollPixels < maxScroll && !this.pendingAction;
            this.addRenderableWidget(down);
        }

        boolean actionable = entry.state() == QuestProviderService.InteractionState.AVAILABLE
                || entry.state() == QuestProviderService.InteractionState.READY_TO_TURN_IN;

        if (actionable) {
            int actionWidth = (this.panelWidth - inset * 2 - BUTTON_GAP) / 2;
            int actionX = this.panelX + inset;
            Component actionText = entry.state() == QuestProviderService.InteractionState.AVAILABLE
                    ? Component.literal("Accept")
                    : Component.literal("Turn In");
            QuestlogWideButton action = new QuestlogWideButton(
                    actionX,
                    actionY,
                    actionWidth,
                    DEFAULT_PALETTE.textColor(),
                    DEFAULT_PALETTE.hoveredTextColor(),
                    actionText,
                    () -> this.perform(entry),
                    this.guiSet
            );
            action.active = !this.pendingAction;
            this.addRenderableWidget(action);

            Component returnText = entry.state() == QuestProviderService.InteractionState.AVAILABLE
                    ? Component.literal("Decline")
                    : Component.literal("Back");
            QuestlogWideButton back = new QuestlogWideButton(
                    actionX + actionWidth + BUTTON_GAP,
                    actionY,
                    actionWidth,
                    DEFAULT_PALETTE.textColor(),
                    DEFAULT_PALETTE.hoveredTextColor(),
                    returnText,
                    this::returnToList,
                    this.guiSet
            );
            back.active = !this.pendingAction;
            this.addRenderableWidget(back);
        } else {
            int backWidth = Math.min(126, this.panelWidth - inset * 2);
            QuestlogWideButton back = new QuestlogWideButton(
                    OverlordPresentationTheme.centeredX(this.panelX, this.panelWidth, backWidth),
                    actionY,
                    backWidth,
                    DEFAULT_PALETTE.textColor(),
                    DEFAULT_PALETTE.hoveredTextColor(),
                    Component.literal("Back"),
                    this::returnToList,
                    this.guiSet
            );
            back.active = !this.pendingAction;
            this.addRenderableWidget(back);
        }
    }

    private int dialogueViewportTop() {
        return this.panelY + DIALOGUE_TOP;
    }

    private int dialogueViewportBottom() {
        return Math.max(
                this.dialogueViewportTop() + this.font.lineHeight,
                this.panelY + this.panelHeight - DIALOGUE_BOTTOM_INSET
        );
    }

    private int dialogueWrapWidth() {
        return Math.max(120, this.panelWidth - 48);
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
            List<FormattedCharSequence> wrapped = this.font.split(
                    Component.literal(dialogue.get(paragraphIndex)),
                    wrapWidth
            );
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
        int viewportHeight = Math.max(
                this.font.lineHeight,
                this.dialogueViewportBottom() - this.dialogueViewportTop()
        );
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
        Component title = quest == null
                ? Component.literal(entry.questId().toString())
                : quest.getDisplay().getTitle();
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
        this.renderHeader(graphics);

        QuestProviderService.InteractionEntry selected = this.selectedEntry();
        if (selected == null) {
            if (this.entries.isEmpty()) {
                graphics.drawCenteredString(
                        this.font,
                        Component.literal("No quests available."),
                        this.panelX + this.panelWidth / 2,
                        this.panelY + this.panelHeight / 2,
                        DEFAULT_PALETTE.textColor()
                );
            }
        } else {
            this.renderDialogue(graphics, selected);
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderBackground(GuiGraphics graphics) {
        super.renderBackground(graphics);
        this.guiSet.detailBackgroundLeft.blit(graphics, this.panelX, this.panelY);
    }

    private void renderHeader(GuiGraphics graphics) {
        Component providerTitle = this.providerName.isBlank() ? this.title : Component.literal(this.providerName);
        int centerX = this.panelX + this.panelWidth / 2;
        int titleY = this.panelY
                + OverlordPresentationTheme.TITLE_Y
                + (OverlordPresentationTheme.TITLE_HEIGHT - this.font.lineHeight + 2) / 2;
        graphics.drawCenteredString(this.font, providerTitle, centerX, titleY, DEFAULT_PALETTE.titleColor());
        OverlordPresentationTheme.renderHeaderSeparator(
                graphics,
                this.guiSet,
                this.panelX,
                this.panelY,
                this.panelWidth
        );

        QuestProviderService.InteractionEntry selected = this.selectedEntry();
        if (selected == null) {
            graphics.drawCenteredString(
                    this.font,
                    Component.literal("Quests"),
                    centerX,
                    this.panelY + 33,
                    DEFAULT_PALETTE.textColor()
            );
        } else {
            Quest quest = QuestlogClient.getLocal().getQuest(selected.questId());
            Component questTitle = quest == null
                    ? Component.literal(selected.questId().toString())
                    : quest.getDisplay().getTitle();
            graphics.drawCenteredString(
                    this.font,
                    questTitle,
                    centerX,
                    this.panelY + 35,
                    DEFAULT_PALETTE.textColor()
            );
        }
    }

    private void renderDialogue(GuiGraphics graphics, QuestProviderService.InteractionEntry entry) {
        DialogueLayout layout = this.dialogueLayout(entry);
        if (layout.lines().isEmpty()) return;

        int viewportTop = this.dialogueViewportTop();
        int viewportBottom = this.dialogueViewportBottom();
        int maxScroll = this.maxDialogueScroll(entry);
        int scroll = Math.max(0, Math.min(this.dialogueScrollPixels, maxScroll));
        int centerX = this.panelX + this.panelWidth / 2;
        int inset = OverlordPresentationTheme.PANEL_CONTENT_INSET;

        graphics.enableScissor(
                this.panelX + inset,
                viewportTop,
                this.panelX + this.panelWidth - inset,
                viewportBottom
        );
        for (DialogueLine line : layout.lines()) {
            int y = viewportTop + line.yOffset() - scroll;
            if (y + this.font.lineHeight < viewportTop) continue;
            if (y > viewportBottom) continue;
            graphics.drawCenteredString(this.font, line.text(), centerX, y, DEFAULT_PALETTE.textColor());
        }
        graphics.disableScissor();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        QuestProviderService.InteractionEntry selected = this.selectedEntry();
        if (selected != null && this.maxDialogueScroll(selected) > 0 && delta != 0.0D) {
            this.scrollDialogue(
                    selected,
                    delta > 0.0D ? -DIALOGUE_SCROLL_STEP : DIALOGUE_SCROLL_STEP
            );
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
