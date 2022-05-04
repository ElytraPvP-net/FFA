package net.elytrapvp.ffa.commands;

import net.elytrapvp.ffa.inventories.CosmeticsGUI;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CosmeticsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if sender is not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&c&lError &8» &cOnly players can use that command.");
            return true;
        }

        Player p = (Player) sender;
        new CosmeticsGUI().open(p);

        return true;
    }
}
