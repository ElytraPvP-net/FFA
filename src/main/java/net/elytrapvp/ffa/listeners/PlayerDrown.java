package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.DeathType;
import net.elytrapvp.ffa.events.PlayerDrownEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDrown implements Listener {
    @EventHandler
    public void onDrown(PlayerDrownEvent e) {
        Player p = e.getPlayer();
        DeathType.list.put(p.getUniqueId(), DeathType.WATER);
        p.setHealth(0);
    }
}
