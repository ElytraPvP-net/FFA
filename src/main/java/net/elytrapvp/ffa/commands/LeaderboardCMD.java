package net.elytrapvp.ffa.commands;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.inventories.LeaderboardGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaderboardCMD implements CommandExecutor {
    private final FFA plugin;

    public LeaderboardCMD(FFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if sender is not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&c&lError &8» &cOnly players can use that command!");
            return true;
        }

        Player p = (Player) sender;
        new LeaderboardGUI(plugin).open(p);

        return true;
    }
}