package org.infernalstudios.questlog.client.death;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.config.QuestlogConfig;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * Mechanically complete death-screen scaffold awaiting an OVERLORD visual pass.
 * The rejected reference presentation and audio are intentionally absent.
 */
public final class OverlordDeathScreen extends DeathScreen {
    private final OverlordDeathSceneClock clock;
    private final boolean hardcore;
    private final ClientLevel originalLevel;
    private final LocalPlayer originalPlayer;
    private final Component cause;
    private boolean sawDeadPlayer;
    private boolean controlsRevealed;
    private List<FormattedCharSequence> causeLines = List.of();

    public OverlordDeathScreen(Component cause, boolean hardcore) {
        super(cause, hardcore);
        QuestlogConfig.DeathScreen config = Questlog.getConfig().deathScreen;
        this.clock = new OverlordDeathSceneClock(config.sceneDurationTicks);
        this.hardcore = hardcore;
        this.cause = cause == null ? Component.empty() : cause;

        Minecraft minecraft = Minecraft.getInstance();
        this.originalLevel = minecraft.level;
        this.originalPlayer = minecraft.player;
    }

    @Override
    protected void init() {
        super.init();
        this.controlsRevealed = false;

        int buttonWidth = Math.min(200, this.width - 40);
        int y = Math.max(this.height / 2 + 38, this.height - 78);
        int index = 0;
        for (GuiEventListener child : this.children()) {
            if (child instanceof AbstractWidget widget) {
                widget.setX((this.width - buttonWidth) / 2);
                widget.setY(y + index++ * 24);
                widget.setWidth(buttonWidth);
            }
        }

        int wrapWidth = Math.max(80, this.width - 64);
        this.causeLines = this.font.split(this.cause, wrapWidth);
        if (this.causeLines.size() > 4) {
            this.causeLines = List.copyOf(this.causeLines.subList(0, 4));
        }
        this.updateControls();
    }

    @Override
    public void tick() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer currentPlayer = minecraft.player;

        if (currentPlayer != null && currentPlayer.isDeadOrDying()) {
            this.sawDeadPlayer = true;
        }

        if (minecraft.level != this.originalLevel
                || currentPlayer != this.originalPlayer
                || currentPlayer == null
                || (!currentPlayer.isDeadOrDying() && (this.sawDeadPlayer || this.clock.ticks() > 20))
                || OverlordDeathScreenHooks.isBlocked(currentPlayer)) {
            if (minecraft.screen == this) {
                minecraft.setScreen(null);
            }
            return;
        }

        super.tick();
        this.clock.tick();
        this.updateControls();

        QuestlogConfig.DeathScreen config = Questlog.getConfig().deathScreen;
        if (this.clock.requestAutoRespawn(config.autoRespawn, this.hardcore, currentPlayer.isDeadOrDying())) {
            for (GuiEventListener child : this.children()) {
                if (child instanceof AbstractWidget widget) {
                    widget.active = false;
                    break;
                }
            }
            currentPlayer.respawn();
        }
    }

    private void updateControls() {
        boolean show = this.clock.ready();
        for (GuiEventListener child : this.children()) {
            if (!(child instanceof AbstractWidget widget)) continue;
            widget.visible = show;
            if (!show) {
                widget.active = false;
            } else if (!this.controlsRevealed) {
                widget.active = true;
            }
        }
        this.controlsRevealed = show;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, this.width, this.height, 0xFF000000);

        int centerY = this.height / 2;
        graphics.drawCenteredString(this.font, Component.translatable("deathScreen.title"), this.width / 2, centerY - 42, 0xE8E8E8);

        int lineY = centerY - Math.max(0, (this.causeLines.size() - 1) * 5);
        for (FormattedCharSequence line : this.causeLines) {
            graphics.drawCenteredString(this.font, line, this.width / 2, lineY, 0xB8B8B8);
            lineY += 11;
        }

        if (this.clock.ready()) {
            for (GuiEventListener child : this.children()) {
                if (child instanceof Renderable renderable) {
                    renderable.render(graphics, mouseX, mouseY, partialTick);
                }
            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.clock.ready()) {
            QuestlogConfig.DeathScreen config = Questlog.getConfig().deathScreen;
            if (config.allowSkip && (keyCode == GLFW.GLFW_KEY_ESCAPE
                    || keyCode == GLFW.GLFW_KEY_ENTER
                    || keyCode == GLFW.GLFW_KEY_SPACE)) {
                this.clock.skip();
                this.updateControls();
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return !this.clock.ready() || super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
