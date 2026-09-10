package org.infernalstudios.questlog.core.quests.rewards;

import com.google.gson.JsonObject;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.util.JsonUtils;

import java.util.Objects;

public class CommandReward extends Reward {

    private final String command;
    private final int permissionLevel;

    public CommandReward(JsonObject definition) {
        super(definition);
        this.command = JsonUtils.getString(definition, "command").trim();
        if (this.command.isEmpty()) {
            throw new IllegalArgumentException("Command reward command must not be empty");
        }

        this.permissionLevel = JsonUtils.getOrDefault(definition, "permission_level", 2);
        if (this.permissionLevel < 0 || this.permissionLevel > 4) {
            throw new IllegalArgumentException("Command reward permission_level must be between 0 and 4");
        }
    }

    @Override
    public void applyReward(ServerPlayer player) {
        CommandSourceStack source = player
                .createCommandSourceStack()
                .withEntity(player)
                .withPosition(player.position())
                .withPermission(this.permissionLevel)
                .withSuppressedOutput();

        String formattedCommand = this.command
                .replace("{player}", player.getGameProfile().getName())
                .replace("%player%", player.getGameProfile().getName())
                .replaceAll("(?<!\\w)@p(?!\\w)", player.getGameProfile().getName())
                .replaceAll("(?<!\\w)@s(?!\\w)", player.getGameProfile().getName());

        Objects.requireNonNull(player.getServer()).getCommands().performPrefixedCommand(source, formattedCommand);

        super.applyReward(player);
    }
}
