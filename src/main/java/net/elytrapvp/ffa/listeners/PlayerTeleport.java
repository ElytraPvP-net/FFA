package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.ParkourLevel;
import net.elytrapvp.ffa.events.ParkourCompleteEvent;
import net.elytrapvp.ffa.managers.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleport implements Listener {
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Location from = e.getFrom();

        if(compareToGreen(from)) {
            Bukkit.getPluginManager().callEvent(new ParkourCompleteEvent(e.getPlayer(), ParkourLevel.GREEN));
        }
        else if(compareToYellow(from)) {
            Bukkit.getPluginManager().callEvent(new ParkourCompleteEvent(e.getPlayer(), ParkourLevel.YELLOW));
        }
        else if(compareToRed(from)) {
            Bukkit.getPluginManager().callEvent(new ParkourCompleteEvent(e.getPlayer(), ParkourLevel.RED));
        }
    }

    public boolean compareToGreen(Location from) {
        int fromX = from.getBlockX();
        int fromY = from.getBlockY();
        int fromZ = from.getBlockZ();

        int greenFromX = settings.getConfig().getInt("Parkour.Green.From.X");
        int greenFromY = settings.getConfig().getInt("Parkour.Green.From.Y");
        int greenFromZ = settings.getConfig().getInt("Parkour.Green.From.Z");

        return (isNear(fromX, greenFromX) && isNear(fromY, greenFromY) && isNear(fromZ, greenFromZ));
    }

    public boolean compareToYellow(Location from) {
        int fromX = from.getBlockX();
        int fromY = from.getBlockY();
        int fromZ = from.getBlockZ();

        int yellowFromX = settings.getConfig().getInt("Parkour.Yellow.From.X");
        int yellowFromY = settings.getConfig().getInt("Parkour.Yellow.From.Y");
        int yellowFromZ = settings.getConfig().getInt("Parkour.Yellow.From.Z");

        return (isNear(fromX, yellowFromX) && isNear(fromY, yellowFromY) && isNear(fromZ, yellowFromZ));
    }

    public boolean compareToRed(Location from) {
        int fromX = from.getBlockX();
        int fromY = from.getBlockY();
        int fromZ = from.getBlockZ();

        int redFromX = settings.getConfig().getInt("Parkour.Red.From.X");
        int redFromY = settings.getConfig().getInt("Parkour.Red.From.Y");
        int redFromZ = settings.getConfig().getInt("Parkour.Red.From.Z");

        return (isNear(fromX, redFromX) && isNear(fromY, redFromY) && isNear(fromZ, redFromZ));
    }

    private boolean isNear(int from, int goal) {
        return (from >= goal - 1 && from <= goal + 1);
    }
}
