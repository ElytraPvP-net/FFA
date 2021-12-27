package net.elytrapvp.ffa.commands;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.ffa.inventories.StatsGUI;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if sender is not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&lError &8» &cOnly players can use that command.");
            return true;
        }

        Player p = (Player) sender;

        if(args.length == 0) {
            new StatsGUI(p).open(p);
            return true;
        }

        OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);

        if(!CustomPlayer.exists(t.getUniqueId())) {
            ChatUtils.chat(p, "&lError &8» &cThat player has never joined.");
            return true;
        }

        new StatsGUI(t).open(p);

        return true;
    }
}