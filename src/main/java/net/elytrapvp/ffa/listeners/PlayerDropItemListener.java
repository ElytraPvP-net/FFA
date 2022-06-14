package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.FFA;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {
    private final FFA plugin;

    public PlayerDropItemListener(FFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if(!plugin.getSettingsManager().getConfig().getBoolean("Enabled")) {
            return;
        }

        event.setCancelled(true);
    }

}