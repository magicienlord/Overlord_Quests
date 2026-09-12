package org.infernalstudios.questlog.client.gui;

import net.minecraft.client.gui.GuiGraphics;

/**
 * Shared geometry and rendering tokens for OVERLORD QUESTS presentation surfaces.
 *
 * This deliberately contains presentation-only values. Quest authority, provider
 * state and speaker reaction semantics remain owned by their existing systems.
 */
public final class OverlordPresentationTheme {
    public static final int OUTER_MARGIN = 12;
    public static final int PANEL_MIN_WIDTH = 220;
    public static final int PANEL_TOP_MIN = 8;
    public static final int PANEL_CONTENT_INSET = 18;
    public static final int TITLE_Y = 13;
    public static final int TITLE_HEIGHT = 16;
    public static final int BELOW_PANEL_GAP = 4;

    private OverlordPresentationTheme() {
    }

    public static QuestlogGuiSet resize(
            QuestlogGuiSet base,
            int panelWidth,
            int rightPanelWidth,
            int panelHeight
    ) {
        return new QuestlogGuiSet(
                base.backgroundLoc,
                base.rightPanelLoc,
                base.peripheralLoc,
                panelWidth,
                rightPanelWidth,
                panelHeight
        );
    }

    public static void renderHeaderSeparator(
            GuiGraphics graphics,
            QuestlogGuiSet guiSet,
            int panelX,
            int panelY,
            int panelWidth
    ) {
        guiSet.smallHR.blit(
                graphics,
                panelX + (panelWidth - guiSet.smallHR.width()) / 2,
                panelY + TITLE_Y + TITLE_HEIGHT - 2
        );
    }

    public static int centeredX(int panelX, int panelWidth, int elementWidth) {
        return panelX + (panelWidth - elementWidth) / 2;
    }
}
