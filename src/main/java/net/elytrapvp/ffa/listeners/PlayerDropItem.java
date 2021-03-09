package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.managers.SettingsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener {
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if(!settings.getConfig().getBoolean("Enabled")) {
            return;
        }

        e.setCancelled(true);
    }

}