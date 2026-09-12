package org.infernalstudios.questlog.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.QuestlogClientEvents;
import org.infernalstudios.questlog.client.gui.OverlordPresentationTheme;
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
    private static final int SPEAKER_GAP = 14;
    private static final int MIN_SPEAKER_WIDTH = 72;
    private static final int MIN_PANEL_HEIGHT = 110;
    private static final int TITLE_WIDTH = 240;
    private static final int CONTENT_Y = 36;
    private static final int CONTENT_WIDTH_INSET = OverlordPresentationTheme.PANEL_CONTENT_INSET * 2 + 2;
    private static final int CONTENT_HEIGHT_INSET = 68;
    private static final int SPEAKER_INSET = 2;

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

        int desiredPanelWidth = Math.max(OverlordPresentationTheme.PANEL_MIN_WIDTH, display.getLeftPanelWidth());
        if (requestedSpeakerWidth > 0) {
            this.configureSplitWidth(desiredPanelWidth, requestedSpeakerWidth);
        } else {
            int availableWidth = Math.max(
                    OverlordPresentationTheme.PANEL_MIN_WIDTH,
                    this.width - OverlordPresentationTheme.OUTER_MARGIN * 2
            );
            this.speakerWidth = 0;
            this.panelWidth = Math.max(
                    OverlordPresentationTheme.PANEL_MIN_WIDTH,
                    Math.min(desiredPanelWidth, availableWidth)
            );
        }

        int totalWidth = this.panelWidth + (this.speakerWidth > 0 ? SPEAKER_GAP + this.speakerWidth : 0);
        this.panelX = (this.width - totalWidth) / 2;
        this.panelY = Math.max(
                OverlordPresentationTheme.PANEL_TOP_MIN,
                (this.height - this.panelHeight - 22) / 2
        );
        this.speakerX = this.panelX + this.panelWidth + SPEAKER_GAP;

        QuestlogGuiSet base = display.getGuiSet();
        this.guiSet = OverlordPresentationTheme.resize(
                base,
                this.panelWidth,
                display.getRightPanelWidth(),
                this.panelHeight
        );

        this.description = new ScrollableComponent(
                this.panelX + OverlordPresentationTheme.PANEL_CONTENT_INSET,
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
                this.panelY + this.panelHeight + OverlordPresentationTheme.BELOW_PANEL_GAP,
                display.getPalette().textColor(),
                display.getPalette().hoveredTextColor(),
                buttonText,
                this::handlePrimaryAction,
                this.guiSet
        );
        this.primaryButton.setX(OverlordPresentationTheme.centeredX(
                this.panelX,
                this.panelWidth,
                this.primaryButton.getExpectedWidth()
        ));
        this.addRenderableWidget(this.primaryButton);
    }

    /**
     * Preserve both surfaces when GUI scale becomes tight instead of collapsing the
     * speaker lane first. The authored widths are kept when they fit; otherwise the
     * remaining width is divided proportionally and then clamped to the parchment
     * minimum. This keeps reaction art readable while parchment remains dominant.
     */
    private void configureSplitWidth(int desiredPanelWidth, int requestedSpeakerWidth) {
        int minimumComposition = OverlordPresentationTheme.PANEL_MIN_WIDTH + SPEAKER_GAP + MIN_SPEAKER_WIDTH;
        int availableComposition = Math.max(
                minimumComposition,
                this.width - OverlordPresentationTheme.OUTER_MARGIN * 2
        );
        int desiredTotal = desiredPanelWidth + SPEAKER_GAP + requestedSpeakerWidth;

        if (desiredTotal <= availableComposition) {
            this.panelWidth = desiredPanelWidth;
            this.speakerWidth = requestedSpeakerWidth;
            return;
        }

        int usable = availableComposition - SPEAKER_GAP;
        float speakerShare = (float) requestedSpeakerWidth / (float) (desiredPanelWidth + requestedSpeakerWidth);
        int proportionalSpeaker = Math.round(usable * speakerShare);
        int maximumSpeaker = Math.max(MIN_SPEAKER_WIDTH, usable - OverlordPresentationTheme.PANEL_MIN_WIDTH);

        this.speakerWidth = Math.max(
                MIN_SPEAKER_WIDTH,
                Math.min(requestedSpeakerWidth, Math.min(proportionalSpeaker, maximumSpeaker))
        );
        this.panelWidth = usable - this.speakerWidth;

        if (this.panelWidth < OverlordPresentationTheme.PANEL_MIN_WIDTH) {
            int deficit = OverlordPresentationTheme.PANEL_MIN_WIDTH - this.panelWidth;
            this.panelWidth += deficit;
            this.speakerWidth = Math.max(MIN_SPEAKER_WIDTH, this.speakerWidth - deficit);
        }
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
        float y = this.panelY + OverlordPresentationTheme.TITLE_Y;

        if (display.getIcon() != null) {
            display.getIcon().blit(graphics, (int) x, this.panelY + OverlordPresentationTheme.TITLE_Y);
            x += iconWidth;
        }

        y += (float) (OverlordPresentationTheme.TITLE_HEIGHT - this.font.lineHeight + 2) / 2.0F;
        graphics.drawString(this.font, display.getTitle(), (int) x, (int) y, display.getPalette().titleColor(), false);
        OverlordPresentationTheme.renderHeaderSeparator(
                graphics,
                this.guiSet,
                this.panelX,
                this.panelY,
                this.panelWidth
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
        int maxWidth = Math.max(1, this.speakerWidth - SPEAKER_INSET * 2);
        int maxHeight = Math.max(1, this.panelHeight);
        float scale = Math.min(1.0F, Math.min((float) maxWidth / sourceWidth, (float) maxHeight / sourceHeight));
        int drawWidth = Math.max(1, Math.round(sourceWidth * scale));
        int drawHeight = Math.max(1, Math.round(sourceHeight * scale));

        int laneLeft = this.speakerX + SPEAKER_INSET;
        int laneRight = this.speakerX + this.speakerWidth - SPEAKER_INSET;
        int baseX = laneLeft + (maxWidth - drawWidth) / 2;
        int x = clamp(baseX + display.getOverlayXOffset(), laneLeft, laneRight - drawWidth);

        // Bottom anchoring gives character portraits a stable visual footing beside
        // the parchment instead of making them appear to float over its midpoint.
        int baseY = this.panelY + this.panelHeight - drawHeight;
        int y = baseY + display.getOverlayYOffset();

        graphics.blit(texture, x, y, 0, 0, drawWidth, drawHeight, drawWidth, drawHeight);
    }

    private static int clamp(int value, int min, int max) {
        if (max < min) {
            return min;
        }
        return Math.max(min, Math.min(max, value));
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
