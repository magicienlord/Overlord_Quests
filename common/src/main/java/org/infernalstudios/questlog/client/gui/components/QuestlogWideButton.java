package org.infernalstudios.questlog.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.infernalstudios.questlog.client.gui.QuestlogGuiSet;
import org.infernalstudios.questlog.util.Callable;
import org.infernalstudios.questlog.util.texture.NineSliceTexture;
import org.jetbrains.annotations.NotNull;

/**
 * Stretchable version of the inherited Questlog parchment button treatment.
 * Used by provider rows and wide provider actions so that the provider surface
 * belongs to the same visual family as Questlog without reverting to vanilla
 * gray widgets.
 */
public final class QuestlogWideButton extends AbstractButton {
    private static final int HEIGHT = 20;
    private static final int SOURCE_U = 46;
    private static final int SOURCE_U_HOVERED = 122;
    private static final int SOURCE_V = 65;
    private static final int SOURCE_WIDTH = 54;
    private static final int SOURCE_HEIGHT = 18;
    private static final int TEXTURE_SIZE = 256;
    private static final int CORNER = 6;

    private final Callable onPress;
    private final int textColor;
    private final int hoveredTextColor;
    private final NineSliceTexture normal;
    private final NineSliceTexture hovered;

    public QuestlogWideButton(
            int x,
            int y,
            int width,
            int textColor,
            int hoveredTextColor,
            Component message,
            Callable onPress,
            QuestlogGuiSet guiSet
    ) {
        super(x, y, Math.max(24, width), HEIGHT, message);
        this.onPress = onPress;
        this.textColor = textColor;
        this.hoveredTextColor = hoveredTextColor;
        this.normal = new NineSliceTexture(
                guiSet.peripheralLoc,
                this.width,
                HEIGHT,
                SOURCE_U,
                SOURCE_V,
                SOURCE_WIDTH,
                SOURCE_HEIGHT,
                TEXTURE_SIZE,
                TEXTURE_SIZE,
                CORNER,
                CORNER
        );
        this.hovered = new NineSliceTexture(
                guiSet.peripheralLoc,
                this.width,
                HEIGHT,
                SOURCE_U_HOVERED,
                SOURCE_V,
                SOURCE_WIDTH,
                SOURCE_HEIGHT,
                TEXTURE_SIZE,
                TEXTURE_SIZE,
                CORNER,
                CORNER
        );
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        (this.isHovered ? this.hovered : this.normal).blit(graphics, this.getX(), this.getY());

        int availableWidth = Math.max(1, this.width - 12);
        int textWidth = minecraft.font.width(this.getMessage());
        int textY = this.getY() + (this.height - minecraft.font.lineHeight) / 2 + 1;
        int color = this.isHovered ? this.hoveredTextColor : this.textColor;

        if (textWidth > availableWidth) {
            graphics.pose().pushPose();
            float scale = (float) availableWidth / textWidth;
            float scaledWidth = textWidth * scale;
            float textX = this.getX() + (this.width - scaledWidth) / 2.0F;
            float scaledTextY = textY + minecraft.font.lineHeight * (1.0F - scale) / 2.0F;
            graphics.pose().translate(textX, scaledTextY, 0.0F);
            graphics.pose().scale(scale, scale, 1.0F);
            graphics.drawString(minecraft.font, this.getMessage(), 0, 0, color, false);
            graphics.pose().popPose();
        } else {
            graphics.drawString(
                    minecraft.font,
                    this.getMessage(),
                    this.getX() + (this.width - textWidth) / 2,
                    textY,
                    color,
                    false
            );
        }
    }

    @Override
    public void onPress() {
        this.onPress.call();
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }
}
