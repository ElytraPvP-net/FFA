package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.DeathType;
import net.elytrapvp.ffa.events.PlayerLandEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLand implements Listener {
    @EventHandler
    public void onLand(PlayerLandEvent e) {
        Player p = e.getPlayer();
        DeathType.list.put(p.getUniqueId(), DeathType.GROUND);
        p.setHealth(0);
    }
}
