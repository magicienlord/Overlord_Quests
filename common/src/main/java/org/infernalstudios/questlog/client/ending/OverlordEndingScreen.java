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
 * Development-only mechanical scaffold for the eventual OVERLORD REIGN ending.
 *
 * No final campaign prose, reveal, artwork, or audio is authored here. The only
 * production-relevant responsibility of this screen is preserving the vanilla
 * WinScreen completion callback exactly once so the same world remains playable.
 */
public final class OverlordEndingScreen extends Screen {
    private static final Component SCAFFOLD_TITLE = Component.literal("OVERLORD REIGN");
    private static final Component SCAFFOLD_STATUS = Component.literal("ENDING PRESENTATION DEVELOPMENT SCAFFOLD");
    private static final Component CONTINUE = Component.literal("Continue");

    private final Runnable onFinished;
    private int ticks;
    private boolean finished;
    private Button continueButton;

    public OverlordEndingScreen(Runnable onFinished) {
        super(SCAFFOLD_TITLE);
        this.onFinished = Objects.requireNonNull(onFinished, "onFinished");
    }

    @Override
    protected void init() {
        super.init();
        int buttonWidth = Math.min(200, Math.max(80, this.width - 40));
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
        graphics.drawCenteredString(this.font, SCAFFOLD_TITLE, this.width / 2, centerY - 20, 0xE8E8E8);
        graphics.drawCenteredString(this.font, SCAFFOLD_STATUS, this.width / 2, centerY + 2, 0x8F8F8F);
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
