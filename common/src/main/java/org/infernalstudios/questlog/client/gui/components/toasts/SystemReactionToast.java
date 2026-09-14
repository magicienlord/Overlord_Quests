package org.infernalstudios.questlog.client.gui.components.toasts;

import net.minecraft.network.chat.Component;
import org.infernalstudios.questlog.util.texture.Blittable;

import javax.annotation.Nullable;

/** A neutral Questlog-styled toast for non-quest system acknowledgements. */
public final class SystemReactionToast extends QuestlogToast {
    private final Component title;
    private final Component description;

    public SystemReactionToast(Component title, Component description) {
        this.title = title;
        this.description = description;
    }

    @Override
    protected Component getTitle() {
        return this.title;
    }

    @Override
    protected Component getDescription() {
        return this.description;
    }

    @Override
    @Nullable
    protected Blittable getIcon() {
        return null;
    }
}
