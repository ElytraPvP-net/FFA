package net.elytrapvp.ffa.commands.subcommands;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.ffa.managers.SettingsManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPosition implements CommandExecutor {
    private static final SettingsManager settings = SettingsManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Return if sender is not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cOnly players can use that command.");
            return true;
        }

        // Exit if no arguments.
        if(args.length == 0) {
            ChatUtils.chat(sender, "&2&lUsage &8- &c/ep setposition [number]");
            return true;
        }

        int pos = Integer.parseInt(args[0]);

        // Exit if position is not equal to 1 or 2.
        if(pos != 1 && pos != 2) {
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cPosition must be either 1 or 2.");
            return true;
        }

        Player p = (Player) sender;
        setPosition(p.getLocation(), pos);
        ChatUtils.chat(p, "&a&l(&7!&a&l) &aPosition &f" + pos + " &ahas been set.");
        return true;
    }

    /**
     * Set the positions of the arena
     * @param loc Location
     * @param position position number
     */
    private void setPosition(Location loc, int position) {
        String path = "Arena.Pos" + position + ".";

        settings.getConfig().set(path + "x", loc.getBlockX());
        settings.getConfig().set(path + "y", loc.getBlockY());
        settings.getConfig().set(path + "z", loc.getBlockZ());

        settings.reloadConfig();
    }
}
