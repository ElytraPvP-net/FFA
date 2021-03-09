package net.elytrapvp.ffa.commands.subcommands;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.ffa.managers.SettingsManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {
    private static final SettingsManager settings = SettingsManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if sender is not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cOnly players can use that command.");
            return true;
        }

        Player p = (Player) sender;
        setSpawn(p.getLocation());
        ChatUtils.chat(p, "&a&l(&7!&a&l) &aSpawn has been set.");
        return true;
    }

    /**
     * Set the gamemode spawn to the current location.
     * @param loc Location
     */
    private void setSpawn(Location loc) {
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        float pitch = loc.getPitch();
        float yaw = loc.getYaw();

        settings.getConfig().set("Spawn.X", x);
        settings.getConfig().set("Spawn.Y", y);
        settings.getConfig().set("Spawn.Z", z);
        settings.getConfig().set("Spawn.Pitch", pitch);
        settings.getConfig().set("Spawn.Yaw", yaw);
        settings.getConfig().set("Spawn.Set", true);

        settings.reloadConfig();
    }
}