package org.infernalstudios.questlog.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.QuestlogClientEvents;
import org.infernalstudios.questlog.client.gui.QuestlogGuiSet;
import org.infernalstudios.questlog.client.gui.SpeakerPortraitTextures;
import org.infernalstudios.questlog.client.gui.components.QuestlogButton;
import org.infernalstudios.questlog.client.gui.components.ScrollableComponent;
import org.infernalstudios.questlog.client.gui.components.scrollable.ScrollableText;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.display.QuestDisplayData;
import org.infernalstudios.questlog.core.quests.display.SpeakerPresentation;
import org.infernalstudios.questlog.network.packet.QuestReadPacket;
import org.infernalstudios.questlog.platform.Services;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Dedicated full-screen presentation for Questlog-delivered incorporeal speakers.
 *
 * The parchment remains the information/action surface. The speaker portrait owns
 * a separate right-hand visual lane and never intrudes into the parchment body.
 * Ordinary in-world quest providers use QuestProviderScreen instead.
 */
public final class OverlordSpeakerScreen extends Screen {
    private static final int OUTER_MARGIN = 12;
    private static final int SPEAKER_GAP = 10;
    private static final int MIN_PANEL_WIDTH = 220;
    private static final int MIN_SPEAKER_WIDTH = 72;
    private static final int MIN_PANEL_HEIGHT = 110;
    private static final int TITLE_Y = 13;
    private static final int TITLE_WIDTH = 180;
    private static final int TITLE_HEIGHT = 16;
    private static final int CONTENT_X = 18;
    private static final int CONTENT_Y = 36;
    private static final int CONTENT_WIDTH_INSET = 38;
    private static final int CONTENT_HEIGHT_INSET = 68;
    private static final int BUTTON_GAP = 2;

    private final Quest quest;
    private int panelX;
    private int panelY;
    private int panelWidth;
    private int panelHeight;
    private int speakerX;
    private int speakerWidth;
    private QuestlogGuiSet guiSet;

    @Nullable
    private ScrollableComponent description;
    @Nullable
    private QuestlogButton primaryButton;

    public OverlordSpeakerScreen(Quest quest) {
        super(quest.getDisplay().getTitle());
        this.quest = quest;
        this.guiSet = quest.getDisplay().getGuiSet();
    }

    @Override
    protected void init() {
        super.init();
        QuestlogClientEvents.mostRecentNotificationQuest = null;

        QuestDisplayData display = this.quest.getDisplay();
        SpeakerPresentation speaker = display.getSpeakerPresentation();
        int requestedSpeakerWidth = speaker == null ? 0 : speaker.paneWidth();

        int availablePanelHeight = Math.max(MIN_PANEL_HEIGHT, this.height - 54);
        this.panelHeight = Math.max(MIN_PANEL_HEIGHT, Math.min(display.getPanelHeight(), availablePanelHeight));
        this.speakerWidth = requestedSpeakerWidth;

        int desiredPanelWidth = display.getLeftPanelWidth();
        int desiredTotal = desiredPanelWidth + (this.speakerWidth > 0 ? SPEAKER_GAP + this.speakerWidth : 0);
        int availableWidth = Math.max(MIN_PANEL_WIDTH, this.width - OUTER_MARGIN * 2);

        if (desiredTotal > availableWidth && this.speakerWidth > 0) {
            int speakerBudget = availableWidth - desiredPanelWidth - SPEAKER_GAP;
            this.speakerWidth = Math.max(MIN_SPEAKER_WIDTH, Math.min(this.speakerWidth, speakerBudget));
        }

        int panelBudget = availableWidth - (this.speakerWidth > 0 ? SPEAKER_GAP + this.speakerWidth : 0);
        this.panelWidth = Math.max(MIN_PANEL_WIDTH, Math.min(desiredPanelWidth, panelBudget));

        int totalWidth = this.panelWidth + (this.speakerWidth > 0 ? SPEAKER_GAP + this.speakerWidth : 0);
        this.panelX = (this.width - totalWidth) / 2;
        this.panelY = Math.max(8, (this.height - this.panelHeight - 22) / 2);
        this.speakerX = this.panelX + this.panelWidth + SPEAKER_GAP;

        QuestlogGuiSet base = display.getGuiSet();
        this.guiSet = new QuestlogGuiSet(
                base.backgroundLoc,
                base.rightPanelLoc,
                base.peripheralLoc,
                this.panelWidth,
                display.getRightPanelWidth(),
                this.panelHeight
        );

        this.description = new ScrollableComponent(
                this.panelX + CONTENT_X,
                this.panelY + CONTENT_Y,
                Math.max(40, this.panelWidth - CONTENT_WIDTH_INSET),
                Math.max(24, this.panelHeight - CONTENT_HEIGHT_INSET),
                new ScrollableText(this.font, display.getDescription(this.quest), display.getPalette().textColor()),
                this.guiSet.scrollbar
        );
        this.addWidget(this.description);

        Component buttonText = this.needsRead()
                ? Component.translatable("questlog.button.read")
                : Component.translatable("gui.done");
        this.primaryButton = new QuestlogButton(
                0,
                this.panelY + this.panelHeight + BUTTON_GAP,
                display.getPalette().textColor(),
                display.getPalette().hoveredTextColor(),
                buttonText,
                this::handlePrimaryAction,
                this.guiSet
        );
        this.primaryButton.setX(this.panelX + this.panelWidth - 12 - this.primaryButton.getExpectedWidth());
        this.addRenderableWidget(this.primaryButton);
    }

    private boolean needsRead() {
        return !this.quest.isCompleted() && this.quest.objectives.stream()
                .anyMatch(objective -> !objective.isCompleted() && objective.isReadObjective());
    }

    private void handlePrimaryAction() {
        if (this.needsRead()) {
            Services.PLATFORM.sendPacketToServer(new QuestReadPacket(this.quest.getId()));
        } else {
            this.onClose();
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        this.renderTitle(graphics);
        if (this.description != null) {
            this.description.render(graphics, mouseX, mouseY, partialTick);
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics graphics) {
        super.renderBackground(graphics);
        this.guiSet.detailBackgroundLeft.blit(graphics, this.panelX, this.panelY);
        this.renderSpeaker(graphics);
    }

    private void renderTitle(GuiGraphics graphics) {
        QuestDisplayData display = this.quest.getDisplay();
        int titleAreaWidth = Math.min(TITLE_WIDTH, Math.max(80, this.panelWidth - 40));
        int titleAreaX = (this.panelWidth - titleAreaWidth) / 2;
        int iconWidth = display.getIcon() != null ? display.getIcon().width() + 4 : 0;
        float totalTitleWidth = this.font.width(display.getTitle()) + iconWidth;
        float x = this.panelX + titleAreaX + (titleAreaWidth - totalTitleWidth) / 2.0F;
        float y = this.panelY + TITLE_Y;

        if (display.getIcon() != null) {
            display.getIcon().blit(graphics, (int) x, this.panelY + TITLE_Y);
            x += iconWidth;
        }

        y += (float) (TITLE_HEIGHT - this.font.lineHeight + 2) / 2.0F;
        graphics.drawString(this.font, display.getTitle(), (int) x, (int) y, display.getPalette().titleColor(), false);
        this.guiSet.smallHR.blit(
                graphics,
                this.panelX + (this.panelWidth - this.guiSet.smallHR.width()) / 2,
                this.panelY + TITLE_Y + TITLE_HEIGHT - 2
        );
    }

    private void renderSpeaker(GuiGraphics graphics) {
        QuestDisplayData display = this.quest.getDisplay();
        SpeakerPresentation speaker = display.getSpeakerPresentation();
        ResourceLocation fallbackTexture = display.getOverlayTexture();
        if (speaker == null || fallbackTexture == null || this.speakerWidth <= 0) {
            return;
        }

        ResourceLocation texture = SpeakerPortraitTextures.resolve(speaker, fallbackTexture);
        int sourceWidth = Math.max(1, display.getOverlayWidth());
        int sourceHeight = Math.max(1, display.getOverlayHeight());
        int maxWidth = Math.max(1, this.speakerWidth);
        int maxHeight = Math.max(1, this.panelHeight + 18);
        float scale = Math.min(1.0F, Math.min((float) maxWidth / sourceWidth, (float) maxHeight / sourceHeight));
        int drawWidth = Math.max(1, Math.round(sourceWidth * scale));
        int drawHeight = Math.max(1, Math.round(sourceHeight * scale));

        int x = this.speakerX + (this.speakerWidth - drawWidth) / 2 + display.getOverlayXOffset();
        int y = this.panelY + (this.panelHeight - drawHeight) / 2 + display.getOverlayYOffset();
        graphics.blit(texture, x, y, 0, 0, drawWidth, drawHeight, drawWidth, drawHeight);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.primaryButton == null) {
            return;
        }

        Component expected = this.needsRead()
                ? Component.translatable("questlog.button.read")
                : Component.translatable("gui.done");
        if (!this.primaryButton.getMessage().equals(expected)) {
            this.rebuildWidgets();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
