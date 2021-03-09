package net.elytrapvp.ffa.commands.subcommands;

import net.elytrapvp.elytrapvp.SettingsManager;
import net.elytrapvp.elytrapvp.chat.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Disable implements CommandExecutor {
    private static final SettingsManager settings = SettingsManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Exit if already disabled.
        if(!settings.getConfig().getBoolean("Enabled")) {
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cPlugin already disabled.");
            return true;
        }

        settings.getConfig().set("Enabled", false);
        settings.reloadConfig();
        ChatUtils.chat(sender, "&a&l(&7!&a&l) &aPlugin has been disabled.");

        return true;
    }
}