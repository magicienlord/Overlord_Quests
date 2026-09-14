package org.infernalstudios.questlog.client.ending;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.config.QuestlogConfig;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

/**
 * Minimal production ending presentation for the OVERLORD REIGN central campaign.
 *
 * The screen deliberately states only the already-established mechanical result:
 * the Ender Dragon is dead, the dimensional Wasteland remains accessible, and
 * the same persistent world continues under the Overlord's reign. Branch-specific
 * epilogues remain separate quest/world-state material rather than being flattened
 * into a global morality summary here.
 */
public final class OverlordEndingScreen extends Screen {
    private static final Component TITLE = Component.literal("OVERLORD REIGN");
    private static final Component STATUS = Component.literal("CENTRAL CAMPAIGN COMPLETE");
    private static final Component LINE_ONE = Component.literal("The Ender Dragon is dead.");
    private static final Component LINE_TWO = Component.literal("The dimensional Wasteland remains, but its Dragon does not.");
    private static final Component LINE_THREE = Component.literal("The world remains. Your reign continues.");
    private static final Component CONTINUE = Component.literal("Continue Your Reign");

    private final Runnable onFinished;
    private int ticks;
    private boolean finished;
    private Button continueButton;

    public OverlordEndingScreen(Runnable onFinished) {
        super(TITLE);
        this.onFinished = Objects.requireNonNull(onFinished, "onFinished");
    }

    @Override
    protected void init() {
        super.init();
        int buttonWidth = Math.min(220, Math.max(100, this.width - 40));
        this.continueButton = Button.builder(CONTINUE, button -> this.finish())
                .bounds((this.width - buttonWidth) / 2, Math.max(20, this.height - 52), buttonWidth, 20)
                .build();
        this.addRenderableWidget(this.continueButton);
        this.updateControlState();
    }

    @Override
    public void tick() {
        this.ticks++;
        this.updateControlState();
    }

    private boolean ready() {
        QuestlogConfig.EndingScreen config = Questlog.getConfig().endingScreen;
        return this.ticks >= Math.max(0, config.minimumDisplayTicks);
    }

    private void updateControlState() {
        if (this.continueButton == null) {
            return;
        }
        boolean ready = this.ready();
        this.continueButton.visible = ready;
        this.continueButton.active = ready && !this.finished;
    }

    private void finish() {
        if (this.finished) {
            return;
        }
        this.finished = true;
        if (this.continueButton != null) {
            this.continueButton.active = false;
        }
        this.onFinished.run();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, this.width, this.height, 0xFF000000);
        int centerY = this.height / 2;
        graphics.drawCenteredString(this.font, TITLE, this.width / 2, centerY - 48, 0xE8E8E8);
        graphics.drawCenteredString(this.font, STATUS, this.width / 2, centerY - 26, 0xAFAFAF);
        graphics.drawCenteredString(this.font, LINE_ONE, this.width / 2, centerY, 0xD8D8D8);
        graphics.drawCenteredString(this.font, LINE_TWO, this.width / 2, centerY + 14, 0xBEBEBE);
        graphics.drawCenteredString(this.font, LINE_THREE, this.width / 2, centerY + 28, 0xD8D8D8);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        QuestlogConfig.EndingScreen config = Questlog.getConfig().endingScreen;
        if (this.ready() && config.allowSkip && (keyCode == GLFW.GLFW_KEY_ESCAPE
                || keyCode == GLFW.GLFW_KEY_ENTER
                || keyCode == GLFW.GLFW_KEY_SPACE)) {
            this.finish();
            return true;
        }
        if (!this.ready()) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        QuestlogConfig.EndingScreen config = Questlog.getConfig().endingScreen;
        if (this.ready() && config.allowSkip) {
            this.finish();
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
