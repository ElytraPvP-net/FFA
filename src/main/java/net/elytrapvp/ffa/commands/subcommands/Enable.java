package net.elytrapvp.ffa.commands.subcommands;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.ffa.managers.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Enable implements CommandExecutor {
    private static final SettingsManager settings = SettingsManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if already enabled.
        if(settings.getConfig().getBoolean("Enabled")) {
            ChatUtils.chat(sender, "&c&lError &8» &cPlugin already enabled.");
            return true;
        }

        // Exit if spawn not set.
        if(!settings.getConfig().getBoolean("Spawn.Set")) {
            ChatUtils.chat(sender, "&c&lError &8» &cSpawn has not been set.");
            return true;
        }

        // Exit if positions not set.
        if(settings.getConfig().getInt("Arena.Pos1.y") == -1 || settings.getConfig().getInt("Arena.Pos2.y") == -1) {
            ChatUtils.chat(sender, "&c&lError &8» &cPositions have not been set.");
            return true;
        }

        settings.getConfig().set("Enabled", true);
        settings.reloadConfig();
        ChatUtils.chat(sender, "&a&l(&7!&a&l) &aPlugin has been enabled.");

        return true;
    }
}
