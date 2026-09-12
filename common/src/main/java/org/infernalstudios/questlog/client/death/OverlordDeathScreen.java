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
 * OVERLORD death presentation layered over the mechanically validated death flow.
 * The presentation remains deliberately text-light: vanilla owns the death title
 * and cause text, while OVERLORD QUESTS owns only composition and timing.
 */
public final class OverlordDeathScreen extends DeathScreen {
    private static final int BACKGROUND = 0xFF050303;
    private static final int PANEL_FILL = 0xFF0B0808;
    private static final int FRAME_OUTER = 0xFF241919;
    private static final int FRAME_INNER = 0xFF120D0D;
    private static final int ACCENT = 0xFF651818;
    private static final int ACCENT_DIM = 0xFF321010;
    private static final int TITLE_COLOR = 0xFFE6DEDA;
    private static final int CAUSE_COLOR = 0xFFBEB6B2;
    private static final int PANEL_MAX_WIDTH = 560;
    private static final int PANEL_HEIGHT = 112;
    private static final int CONTENT_INSET = 32;

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

        int panelWidth = this.panelWidth();
        int wrapWidth = Math.max(80, panelWidth - CONTENT_INSET * 2);
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

    private int panelWidth() {
        return Math.min(PANEL_MAX_WIDTH, Math.max(80, this.width - 64));
    }

    private void renderPresentationFrame(GuiGraphics graphics, int panelX, int panelY, int panelWidth) {
        int panelRight = panelX + panelWidth;
        int panelBottom = panelY + PANEL_HEIGHT;

        graphics.fill(panelX, panelY, panelRight, panelBottom, PANEL_FILL);

        graphics.fill(panelX, panelY, panelRight, panelY + 1, FRAME_OUTER);
        graphics.fill(panelX, panelBottom - 1, panelRight, panelBottom, FRAME_OUTER);
        graphics.fill(panelX, panelY, panelX + 1, panelBottom, FRAME_OUTER);
        graphics.fill(panelRight - 1, panelY, panelRight, panelBottom, FRAME_OUTER);

        graphics.fill(panelX + 4, panelY + 4, panelRight - 4, panelY + 5, FRAME_INNER);
        graphics.fill(panelX + 4, panelBottom - 5, panelRight - 4, panelBottom - 4, FRAME_INNER);

        int centerX = this.width / 2;
        int accentHalfWidth = Math.min(72, Math.max(24, panelWidth / 6));
        graphics.fill(centerX - accentHalfWidth, panelY, centerX + accentHalfWidth, panelY + 2, ACCENT);
        graphics.fill(centerX - accentHalfWidth, panelBottom - 2, centerX + accentHalfWidth, panelBottom, ACCENT_DIM);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, this.width, this.height, BACKGROUND);

        int panelWidth = this.panelWidth();
        int panelX = (this.width - panelWidth) / 2;
        int panelY = Math.max(18, (this.height - PANEL_HEIGHT) / 2 - 22);
        this.renderPresentationFrame(graphics, panelX, panelY, panelWidth);

        int titleY = panelY + 20;
        graphics.drawCenteredString(this.font, Component.translatable("deathScreen.title"), this.width / 2, titleY, TITLE_COLOR);

        int dividerY = panelY + 39;
        int dividerInset = Math.max(28, panelWidth / 5);
        graphics.fill(panelX + dividerInset, dividerY, panelX + panelWidth - dividerInset, dividerY + 1, ACCENT_DIM);
        graphics.fill(this.width / 2 - 18, dividerY, this.width / 2 + 18, dividerY + 1, ACCENT);

        int lineY = panelY + 55 - Math.max(0, (this.causeLines.size() - 1) * 5);
        for (FormattedCharSequence line : this.causeLines) {
            graphics.drawCenteredString(this.font, line, this.width / 2, lineY, CAUSE_COLOR);
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
