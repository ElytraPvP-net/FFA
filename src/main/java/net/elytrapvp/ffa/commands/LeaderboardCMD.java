package net.elytrapvp.ffa.commands;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.ffa.inventories.LeaderboardGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaderboardCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if sender is not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cOnly players can use that command.");
            return true;
        }

        Player p = (Player) sender;
        new LeaderboardGUI().open(p);

        return true;
    }
}