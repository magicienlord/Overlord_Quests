package org.infernalstudios.questlog.client.gui.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Concrete callback-driven button used by the 1.20.1 Forge adaptation.
 *
 * <p>Questlog's editor screens used anonymous {@link AbstractButton} subclasses.
 * Under the current Forge 47.4.10 + ModDevGradle mapping pipeline those anonymous
 * subclasses can produce duplicate synthetic constructor parameter names during
 * javac compilation. Keeping the behavior in a named concrete class avoids that
 * compiler/mapping edge case without changing editor semantics.</p>
 */
public final class FunctionalButton extends AbstractButton {

    @FunctionalInterface
    public interface Renderer {
        void render(FunctionalButton button, GuiGraphics graphics, int mouseX, int mouseY, float partialTicks);
    }

    @FunctionalInterface
    public interface MouseClickHandler {
        boolean mouseClicked(FunctionalButton button, double mouseX, double mouseY, int mouseButton);
    }

    private final Runnable pressAction;
    private final Renderer renderer;
    @Nullable
    private final MouseClickHandler mouseClickHandler;

    public FunctionalButton(int x, int y, int width, int height, Component message,
                            Runnable pressAction, Renderer renderer) {
        this(x, y, width, height, message, pressAction, renderer, null);
    }

    public FunctionalButton(int x, int y, int width, int height, Component message,
                            Runnable pressAction, Renderer renderer,
                            @Nullable MouseClickHandler mouseClickHandler) {
        super(x, y, width, height, message);
        this.pressAction = pressAction;
        this.renderer = renderer;
        this.mouseClickHandler = mouseClickHandler;
    }

    @Override
    public void onPress() {
        this.pressAction.run();
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderer.render(this, graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.mouseClickHandler != null) {
            return this.mouseClickHandler.mouseClicked(this, mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }
}
