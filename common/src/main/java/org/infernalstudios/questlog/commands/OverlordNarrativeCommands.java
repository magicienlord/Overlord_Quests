package org.infernalstudios.questlog.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.core.ServerPlayerManager;
import org.infernalstudios.questlog.overlord.narrative.OverlordNarrativeState;

import java.util.Comparator;

/** Permission-gated administrative surface for authored world narrative state. */
public final class OverlordNarrativeCommands {
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_CIVILIZATIONS = (ctx, builder) -> {
        OverlordNarrativeState state = OverlordNarrativeState.get(ctx.getSource().getServer());
        return SharedSuggestionProvider.suggest(
                state.snapshotDispositions().keySet().stream()
                        .sorted(Comparator.comparing(ResourceLocation::toString))
                        .map(ResourceLocation::toString),
                builder
        );
    };

    private static final SuggestionProvider<CommandSourceStack> SUGGEST_FACTS = (ctx, builder) -> {
        OverlordNarrativeState state = OverlordNarrativeState.get(ctx.getSource().getServer());
        return SharedSuggestionProvider.suggest(
                state.snapshotFacts().stream()
                        .sorted(Comparator.comparing(ResourceLocation::toString))
                        .map(ResourceLocation::toString),
                builder
        );
    };

    private OverlordNarrativeCommands() {
    }

    /**
     * Attaches to the already-registered /questlog command root. This keeps the
     * inherited command surface and its permission requirement intact while
     * avoiding a second public command namespace.
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandNode<CommandSourceStack> questlogRoot = dispatcher.getRoot().getChild("questlog");
        if (questlogRoot == null) {
            throw new IllegalStateException("Questlog command root must be registered before narrative commands");
        }

        questlogRoot.addChild(
                Commands.literal("narrative")
                        .then(Commands.literal("disposition")
                                .then(Commands.literal("get")
                                        .then(Commands.argument("civilization", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_CIVILIZATIONS)
                                                .executes(OverlordNarrativeCommands::getDisposition)
                                        )
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("civilization", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_CIVILIZATIONS)
                                                .then(Commands.argument("state", ResourceLocationArgument.id())
                                                        .executes(OverlordNarrativeCommands::setDisposition)
                                                )
                                        )
                                )
                                .then(Commands.literal("clear")
                                        .then(Commands.argument("civilization", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_CIVILIZATIONS)
                                                .executes(OverlordNarrativeCommands::clearDisposition)
                                        )
                                )
                        )
                        .then(Commands.literal("fact")
                                .then(Commands.literal("get")
                                        .then(Commands.argument("fact", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_FACTS)
                                                .executes(OverlordNarrativeCommands::getFact)
                                        )
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("fact", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_FACTS)
                                                .executes(OverlordNarrativeCommands::setFact)
                                        )
                                )
                                .then(Commands.literal("clear")
                                        .then(Commands.argument("fact", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_FACTS)
                                                .executes(OverlordNarrativeCommands::clearFact)
                                        )
                                )
                        )
                        .build()
        );
    }

    private static int getDisposition(CommandContext<CommandSourceStack> ctx) {
        ResourceLocation civilization = ResourceLocationArgument.getId(ctx, "civilization");
        ResourceLocation state = OverlordNarrativeState.get(ctx.getSource().getServer()).getDisposition(civilization);
        ctx.getSource().sendSuccess(
                () -> Component.literal("Narrative disposition " + civilization + " = " + state),
                false
        );
        return 1;
    }

    private static int setDisposition(CommandContext<CommandSourceStack> ctx) {
        ResourceLocation civilization = ResourceLocationArgument.getId(ctx, "civilization");
        ResourceLocation state = ResourceLocationArgument.getId(ctx, "state");
        OverlordNarrativeState narrative = OverlordNarrativeState.get(ctx.getSource().getServer());
        boolean changed = narrative.setDisposition(civilization, state);
        syncQuestStateIfChanged(changed);
        ctx.getSource().sendSuccess(
                () -> Component.literal(
                        (changed ? "Set" : "Kept") + " narrative disposition " + civilization + " = " + narrative.getDisposition(civilization)
                ),
                true
        );
        return 1;
    }

    private static int clearDisposition(CommandContext<CommandSourceStack> ctx) {
        ResourceLocation civilization = ResourceLocationArgument.getId(ctx, "civilization");
        OverlordNarrativeState narrative = OverlordNarrativeState.get(ctx.getSource().getServer());
        boolean changed = narrative.setDisposition(civilization, OverlordNarrativeState.UNRESOLVED);
        syncQuestStateIfChanged(changed);
        ctx.getSource().sendSuccess(
                () -> Component.literal(
                        (changed ? "Cleared" : "Kept") + " narrative disposition " + civilization + " = " + OverlordNarrativeState.UNRESOLVED
                ),
                true
        );
        return 1;
    }

    private static int getFact(CommandContext<CommandSourceStack> ctx) {
        ResourceLocation fact = ResourceLocationArgument.getId(ctx, "fact");
        boolean present = OverlordNarrativeState.get(ctx.getSource().getServer()).hasFact(fact);
        ctx.getSource().sendSuccess(
                () -> Component.literal("Narrative fact " + fact + " = " + present),
                false
        );
        return present ? 1 : 0;
    }

    private static int setFact(CommandContext<CommandSourceStack> ctx) {
        ResourceLocation fact = ResourceLocationArgument.getId(ctx, "fact");
        OverlordNarrativeState narrative = OverlordNarrativeState.get(ctx.getSource().getServer());
        boolean changed = narrative.setFact(fact);
        syncQuestStateIfChanged(changed);
        ctx.getSource().sendSuccess(
                () -> Component.literal((changed ? "Set" : "Kept") + " narrative fact " + fact),
                true
        );
        return 1;
    }

    private static int clearFact(CommandContext<CommandSourceStack> ctx) {
        ResourceLocation fact = ResourceLocationArgument.getId(ctx, "fact");
        OverlordNarrativeState narrative = OverlordNarrativeState.get(ctx.getSource().getServer());
        boolean changed = narrative.clearFact(fact);
        syncQuestStateIfChanged(changed);
        ctx.getSource().sendSuccess(
                () -> Component.literal((changed ? "Cleared" : "Kept absent") + " narrative fact " + fact),
                true
        );
        return 1;
    }

    private static void syncQuestStateIfChanged(boolean changed) {
        if (changed && ServerPlayerManager.INSTANCE != null) {
            ServerPlayerManager.INSTANCE.syncAllQuestState();
        }
    }
}
