package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityPickupItemListener implements Listener {
    private final FFA plugin;

    public EntityPickupItemListener(FFA plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if(!plugin.getSettingsManager().getConfig().getBoolean("Enabled")) {
            return;
        }

        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayers().get(player.getUniqueId());

        if(customPlayer.getStatus() == Status.LOBBY) {
            event.setCancelled(true);
            return;
        }

        if(customPlayer.getKit() == 12) {
            return;
        }

        if(customPlayer.getStatus() == Status.ARENA) {
            event.setCancelled(true);
        }
    }
}