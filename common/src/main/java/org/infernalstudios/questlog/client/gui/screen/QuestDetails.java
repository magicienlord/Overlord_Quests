package org.infernalstudios.questlog.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarrationSupplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.infernalstudios.questlog.QuestlogClient;
import org.infernalstudios.questlog.QuestlogClientEvents;
import org.infernalstudios.questlog.client.gui.QuestlogGuiSet;
import org.infernalstudios.questlog.client.gui.components.QuestlogButton;
import org.infernalstudios.questlog.client.gui.components.ScrollableComponent;
import org.infernalstudios.questlog.client.gui.components.scrollable.ScrollableInfo;
import org.infernalstudios.questlog.client.gui.components.scrollable.ScrollableText;
import org.infernalstudios.questlog.core.quests.Quest;
import org.infernalstudios.questlog.core.quests.display.Palette;
import org.infernalstudios.questlog.core.quests.display.QuestDisplayData;
import org.infernalstudios.questlog.core.quests.rewards.ChoiceReward;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.network.packet.QuestReadPacket;
import org.infernalstudios.questlog.network.packet.QuestResetPacket;
import org.infernalstudios.questlog.network.packet.QuestRewardCollectPacket;
import org.infernalstudios.questlog.platform.Services;
import org.infernalstudios.questlog.util.texture.AnimatedTexture;
import org.infernalstudios.questlog.util.texture.Blittable;
import org.infernalstudios.questlog.util.texture.Texture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.Collections;
import java.util.List;

public class QuestDetails extends Screen implements NarrationSupplier {

    private static final int PANEL_SPACING = 6;
    private static final int BUTTON_SPACING = 6;
    private static final int TITLE_Y = 13;
    private static final int TITLE_WIDTH = 132;
    private static final int TITLE_HEIGHT = 16;
    private static final int CONTENT_X = 18;
    private static final int CONTENT_Y = 36;
    private static final int HR_Y_OFFSET = -2;
    private static final int MAX_TOOLTIP_DIMENSION = 2048;
    private static final int MAX_TOOLTIP_FRAMES = 256;

    private static boolean showDetails = true;

    public final Quest quest;

    @Nullable
    private final Screen previousScreen;
    public Component pendingTooltip = null;
    private int panel1X;
    private int panel2X;
    private int panel1Y;
    private int panel2Y;
    @Nullable
    private QuestlogButton backButton;
    @Nullable
    private QuestlogButton objectivesButton;
    @Nullable
    private ScrollableComponent description;
    @Nullable
    private ScrollableComponent info;
    private long handCursor = 0L;
    private boolean changedCursor = false;

    public QuestDetails(@Nullable Screen previousScreen, Quest quest) {
        super(quest.getDisplay().getTitle());
        this.previousScreen = previousScreen;
        this.quest = quest;
    }

    @Nullable
    public Screen getPreviousScreen() {
        return this.previousScreen;
    }

    public QuestDisplayData getDisplay() {
        return this.quest.getDisplay();
    }

    public Palette getPalette() {
        return this.getDisplay().getPalette();
    }

    public QuestlogGuiSet getGuiSet() {
        return this.getDisplay().getGuiSet();
    }

    @Override
    protected void init() {
        super.init();
        QuestlogClientEvents.mostRecentNotificationQuest = null;

        boolean hasDetails = !this.quest.objectives.isEmpty() || !this.quest.rewards.isEmpty();
        if (!hasDetails) {
            showDetails = false;
        } else if (this.getDisplay().isDetailsButtonDisabled()) {
            showDetails = this.getDisplay().isDetailsOpenByDefault();
        } else if (this.getDisplay().isDetailsOpenByDefault()) {
            showDetails = true;
        }

        int leftWidth = this.getDisplay().getLeftPanelWidth();
        int rightWidth = this.getDisplay().getRightPanelWidth();
        int height = this.getDisplay().getPanelHeight();

        int totalWidth = showDetails ? (leftWidth + rightWidth + PANEL_SPACING) : leftWidth;
        int baseX = (this.width - totalWidth) / 2;
        int baseY = (this.height - height) / 2;

        this.panel1X = baseX + this.getDisplay().getLeftPanelXOffset();
        this.panel1Y = baseY + this.getDisplay().getLeftPanelYOffset();

        this.panel2X = baseX + leftWidth + PANEL_SPACING + this.getDisplay().getRightPanelXOffset();
        this.panel2Y = baseY + this.getDisplay().getRightPanelYOffset();

        this.setupButtons();
        this.setupContent();
    }

    private void setupButtons() {
        int height = this.getDisplay().getPanelHeight();

        int buttonY = this.panel1Y + height + 2;

        this.backButton = new QuestlogButton(
                0, buttonY,
                this.getPalette().textColor(),
                this.getPalette().hoveredTextColor(),
                Component.empty(),
                this::handlePrimaryAction,
                this.getGuiSet()
        );

        boolean hasDetails = !this.quest.objectives.isEmpty() || !this.quest.rewards.isEmpty();

        if (hasDetails && !this.getDisplay().isDetailsButtonDisabled()) {
            this.objectivesButton = new QuestlogButton(
                    0, buttonY,
                    this.getPalette().textColor(),
                    this.getPalette().hoveredTextColor(),
                    Component.translatable("questlog.info.details"),
                    () -> {
                        showDetails = !showDetails;
                        this.rebuildWidgets();
                    },
                    this.getGuiSet()
            );
        } else {
            this.objectivesButton = null;
        }

        this.updateButtonLayout(this.getPrimaryRightBoundary());

        this.addRenderableWidget(this.backButton);
        if (this.objectivesButton != null) {
            this.addRenderableWidget(this.objectivesButton);
        }
    }

    private int getPrimaryRightBoundary() {
        return this.panel1X + this.getDisplay().getLeftPanelWidth() - 12;
    }

    private boolean hasIncompleteChoiceRewards() {
        for (Reward reward : this.quest.rewards) {
            if (!reward.hasRewarded() && reward instanceof ChoiceReward choiceReward && !choiceReward.canClaim()) {
                return true;
            }
        }
        return false;
    }

    private void updateButtonLayout(int rightBoundary) {
        if (this.backButton == null) return;
        Component backText = this.getDisplay().getBackButtonText();
        boolean primaryActionEnabled = true;

        if (this.quest.isCompleted() && !this.quest.isRewarded()) {
            if (this.hasIncompleteChoiceRewards()) {
                backText = Component.translatable("questlog.reward.make_choices");
                primaryActionEnabled = false;
            } else {
                backText = this.getDisplay().getCollectButtonText();
            }
        } else if (this.quest.isCompleted() && this.quest.isRewarded() && this.quest.isRepeatable()) {
            backText = Component.translatable("questlog.reward.reset");
        } else if (this.needsRead()) {
            backText = Component.translatable("questlog.button.read");
        }

        this.backButton.setMessage(backText);
        this.backButton.active = primaryActionEnabled;

        int backWidth = this.backButton.getExpectedWidth();
        this.backButton.setX(rightBoundary - backWidth);

        if (this.objectivesButton != null) {
            int objWidth = this.objectivesButton.getExpectedWidth();
            this.objectivesButton.setX(this.backButton.getX() - objWidth - BUTTON_SPACING);
        }
    }

    private void handlePrimaryAction() {
        if (this.quest.isCompleted() && !this.quest.isRewarded()) {
            // The button is disabled while a choice is incomplete, but keep this
            // guard at the action boundary as well so keyboard/programmatic calls
            // cannot send a claim that the server will reject.
            if (!this.hasIncompleteChoiceRewards()) {
                this.claimAllRewards();
            }
        } else if (this.quest.isCompleted() && this.quest.isRewarded() && this.quest.isRepeatable()) {
            Services.PLATFORM.sendPacketToServer(new QuestResetPacket(this.quest.getId()));
        } else if (this.needsRead()) {
            Services.PLATFORM.sendPacketToServer(new QuestReadPacket(this.quest.getId()));
        } else if (this.minecraft != null) {
            this.minecraft.setScreen(this.previousScreen);
        }
    }

    private void claimAllRewards() {
        for (int i = 0; i < this.quest.rewards.size(); i++) {
            Reward reward = this.quest.rewards.get(i);
            if (reward.hasRewarded()) {
                continue;
            }

            List<Integer> selections = Collections.emptyList();
            if (reward instanceof ChoiceReward choiceReward) {
                if (!choiceReward.canClaim()) {
                    continue;
                }
                selections = choiceReward.getSelectedIndicesList();
            }

            Services.PLATFORM.sendPacketToServer(new QuestRewardCollectPacket(this.quest.getId(), i, selections));
            SoundEvent sound = reward.getDisplay() != null ? reward.getDisplay().getClaimSound() : null;
            if (sound != null && this.minecraft != null) {
                this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(sound, 1, 1));
            }
        }
    }

    private boolean needsRead() {
        return !this.quest.isCompleted() && this.quest.objectives.stream()
                .anyMatch(obj -> !obj.isCompleted() && obj.isReadObjective());
    }

    private void setupContent() {
        int leftWidth = this.getDisplay().getLeftPanelWidth();
        int rightWidth = this.getDisplay().getRightPanelWidth();
        int height = this.getDisplay().getPanelHeight();

        this.description = new ScrollableComponent(
                this.panel1X + CONTENT_X,
                this.panel1Y + CONTENT_Y,
                leftWidth - 38,
                height - 68,
                new ScrollableText(this.minecraft.font, this.getDisplay().getDescription(this.quest), this.getPalette().textColor())
        );

        this.addWidget(this.description);

        if (showDetails) {
            this.info = new ScrollableComponent(
                    this.panel2X + CONTENT_X,
                    this.panel2Y + CONTENT_Y,
                    rightWidth - 36,
                    height - 68,
                    new ScrollableInfo(this, this.getDisplay())
            );

            this.addWidget(this.info);
        } else {
            this.info = null;
        }
    }

    @Override
    public void render(@NotNull GuiGraphics ps, int mouseX, int mouseY, float partialTicks) {
        this.pendingTooltip = null;
        this.renderBackground(ps);
        super.render(ps, mouseX, mouseY, partialTicks);
        this.renderTitle(ps);
        if (this.description != null) this.description.render(ps, mouseX, mouseY, partialTicks);
        if (showDetails) {
            this.renderInfo(ps, mouseX, mouseY, partialTicks);
        }

        this.handleMouseOverLinks(mouseX, mouseY, ps);

        if (this.pendingTooltip != null && this.info != null && this.info.isMouseOver(mouseX, mouseY)) {
            ps.renderTooltip(this.font, this.pendingTooltip, mouseX, mouseY);
        }
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics ps) {
        super.renderBackground(ps);
        this.getGuiSet().detailBackgroundLeft.blit(ps, this.panel1X, this.panel1Y);
        if (showDetails) {
            this.getGuiSet().detailBackgroundRight.blit(ps, this.panel2X, this.panel2Y);
        }

        ResourceLocation overlay = this.getDisplay().getOverlayTexture();
        if (overlay != null) {
            int overlayX = this.panel1X + this.getDisplay().getOverlayXOffset();
            int overlayY = this.panel1Y + this.getDisplay().getOverlayYOffset();
            int overlayWidth = this.getDisplay().getOverlayWidth();
            int overlayHeight = this.getDisplay().getOverlayHeight();
            ps.blit(overlay, overlayX, overlayY, 0, 0, overlayWidth, overlayHeight, overlayWidth, overlayHeight);
        }
    }

    private void handleMouseOverLinks(int mouseX, int mouseY, GuiGraphics ps) {
        if (this.description == null || this.minecraft == null) return;

        long window = this.minecraft.getWindow().getWindow();
        boolean isHoveringLink = false;

        if (this.description.isMouseOver(mouseX, mouseY)) {
            ScrollableText scrollableText = (ScrollableText) this.description.scrollable;
            Style style = scrollableText.getStyleAt(mouseX - this.description.getXOffset(), mouseY - this.description.getYOffset());

            if (style != null) {
                if (style.getClickEvent() != null) {
                    isHoveringLink = true;
                    if (!this.changedCursor) {
                        if (this.handCursor == 0L) {
                            this.handCursor = GLFW.glfwCreateStandardCursor(GLFW.GLFW_HAND_CURSOR);
                        }
                        if (this.handCursor != 0L) {
                            GLFW.glfwSetCursor(window, this.handCursor);
                            this.changedCursor = true;
                        }
                    }
                }
                this.renderHoverEffect(ps, style, mouseX, mouseY);
            }
        }

        if (!isHoveringLink && this.changedCursor) {
            GLFW.glfwSetCursor(window, 0L);
            this.changedCursor = false;
        }
    }

    private void renderHoverEffect(GuiGraphics ps, Style style, int mouseX, int mouseY) {
        HoverEvent hover = style.getHoverEvent();
        if (hover != null && hover.getAction() == HoverEvent.Action.SHOW_TEXT) {
            Component hoverComponent = (Component) hover.getValue(hover.getAction());
            if (hoverComponent != null) {
                String text = hoverComponent.getString();
                if (text.startsWith("image:")) {
                    this.renderImageTooltip(ps, text, mouseX, mouseY);
                } else {
                    ps.renderComponentHoverEffect(this.font, style, mouseX, mouseY);
                }
            }
        }
    }

    private void renderImageTooltip(GuiGraphics ps, String data, int mouseX, int mouseY) {
        String[] parts = data.split(":", -1);
        if (parts.length < 3) {
            return;
        }

        ResourceLocation loc = ResourceLocation.tryParse(parts[1] + ":" + parts[2]);
        if (loc == null) {
            return;
        }

        final int width;
        final int height;
        final int frames;
        final int frameTime;
        try {
            width = parts.length >= 4 ? Integer.parseInt(parts[3]) : 16;
            height = parts.length >= 5 ? Integer.parseInt(parts[4]) : 16;
            frames = parts.length >= 7 ? Integer.parseInt(parts[5]) : 1;
            frameTime = parts.length >= 7 ? Integer.parseInt(parts[6]) : 100;
        } catch (NumberFormatException ignored) {
            return;
        }

        if (width < 1 || width > MAX_TOOLTIP_DIMENSION ||
                height < 1 || height > MAX_TOOLTIP_DIMENSION ||
                frames < 1 || frames > MAX_TOOLTIP_FRAMES ||
                frameTime < 1) {
            return;
        }

        ps.pose().pushPose();
        try {
            ps.pose().translate(0.0F, 0.0F, 400.0F);
            ps.fill(mouseX + 8, mouseY - 8, mouseX + 8 + width + 4, mouseY - 8 + height + 4, 0xDD000000);

            Blittable textureToRender;
            if (frames > 1) {
                textureToRender = new AnimatedTexture(
                        loc,
                        width,
                        height,
                        0,
                        0,
                        width,
                        height * frames,
                        frames,
                        frameTime
                );
            } else {
                textureToRender = new Texture(loc, width, height, 0, 0, width, height);
            }
            textureToRender.blit(ps, mouseX + 10, mouseY - 6);
        } finally {
            ps.pose().popPose();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.description != null && this.description.isMouseOver(mouseX, mouseY)) {
            ScrollableText scrollableText = (ScrollableText) this.description.scrollable;
            Style style = scrollableText.getStyleAt(mouseX - this.description.getXOffset(), mouseY - this.description.getYOffset());

            if (style != null && style.getClickEvent() != null) {
                ClickEvent click = style.getClickEvent();
                if (click.getAction() == ClickEvent.Action.CHANGE_PAGE) {
                    ResourceLocation id = ResourceLocation.tryParse(click.getValue());
                    if (id != null) {
                        Quest target = QuestlogClient.getLocal().getQuest(id);
                        if (target != null && this.minecraft != null) {
                            this.minecraft.setScreen(new QuestDetails(this, target));
                            return true;
                        }
                    }
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void renderTitle(GuiGraphics ps) {
        QuestDisplayData display = this.getDisplay();
        int leftWidth = display.getLeftPanelWidth();

        int titleAreaX = (leftWidth - TITLE_WIDTH) / 2;
        int iconWidth = display.getIcon() != null ? display.getIcon().width() + 4 : 0;
        float totalTitleWidth = this.font.width(display.getTitle()) + iconWidth;

        float x = this.panel1X + titleAreaX + (TITLE_WIDTH - totalTitleWidth) / 2;
        float y = this.panel1Y + TITLE_Y;

        if (display.getIcon() != null) {
            display.getIcon().blit(ps, (int) x, this.panel1Y + TITLE_Y);
            x += iconWidth;
        }

        y += (float) (TITLE_HEIGHT - this.font.lineHeight + 2) / 2;
        ps.drawString(font, display.getTitle(), (int) x, (int) y, this.getPalette().titleColor(), false);

        this.getGuiSet().smallHR.blit(ps, this.panel1X + titleAreaX - 60, this.panel1Y + TITLE_Y + TITLE_HEIGHT + HR_Y_OFFSET);
    }

    private void renderInfo(GuiGraphics ps, int mouseX, int mouseY, float partialTicks) {
        if (this.info == null) return;

        int rightWidth = this.getDisplay().getRightPanelWidth();

        Component title = this.quest.isCompleted() ? Component.translatable("questlog.info.rewards") : Component.translatable("questlog.info.objectives");

        float x = this.panel2X + (rightWidth - this.font.width(title)) / 2f;
        float y = this.panel2Y + TITLE_Y + (float) (TITLE_HEIGHT - this.font.lineHeight + 2) / 2;

        ps.drawString(font, title, (int) x, (int) y, this.getPalette().titleColor(), false);
        this.getGuiSet().panelHR.blit(ps, this.panel2X + (rightWidth - 140) / 2, this.panel2Y + TITLE_Y + TITLE_HEIGHT + HR_Y_OFFSET);

        this.info.render(ps, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();

        boolean isShowingCollect = this.backButton != null &&
                (this.backButton.getMessage().equals(this.getDisplay().getCollectButtonText()) ||
                        this.backButton.getMessage().equals(Component.translatable("questlog.reward.make_choices")));

        if (isShowingCollect && this.quest.isRewarded()) {
            this.rebuildWidgets();
        } else if (this.backButton != null && isShowingCollect) {
            boolean canClaim = !this.hasIncompleteChoiceRewards();
            this.backButton.active = canClaim;
            Component expectedText = canClaim
                    ? this.getDisplay().getCollectButtonText()
                    : Component.translatable("questlog.reward.make_choices");
            if (!this.backButton.getMessage().equals(expectedText)) {
                this.backButton.setMessage(expectedText);
                this.updateButtonLayout(this.getPrimaryRightBoundary());
            }
        }

        if (this.backButton != null && !this.needsRead() &&
                this.backButton.getMessage().equals(Component.translatable("questlog.button.read"))) {
            this.rebuildWidgets();
        }

        if (this.backButton != null && !this.quest.isCompleted() &&
                this.backButton.getMessage().equals(Component.translatable("questlog.reward.reset"))) {
            this.rebuildWidgets();
        }
    }

    @Override
    public void removed() {
        if (this.minecraft != null && this.changedCursor) {
            long window = this.minecraft.getWindow().getWindow();
            GLFW.glfwSetCursor(window, 0L);
            this.changedCursor = false;
        }
        if (this.handCursor != 0L) {
            GLFW.glfwDestroyCursor(this.handCursor);
            this.handCursor = 0L;
        }
        super.removed();
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput output) {
    }
}
