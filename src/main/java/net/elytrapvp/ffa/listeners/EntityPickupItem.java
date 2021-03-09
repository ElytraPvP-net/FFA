package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.managers.SettingsManager;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityPickupItem implements Listener {
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        if(!settings.getConfig().getBoolean("Enabled")) {
            return;
        }

        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();
        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        if(cp.getStatus() == Status.LOBBY) {
            e.setCancelled(true);
            return;
        }

        if(cp.getKit() == 12) {
            return;
        }

        if(cp.getStatus() == Status.ARENA) {
            e.setCancelled(true);
        }
    }
}