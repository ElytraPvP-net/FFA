package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.DeathType;
import net.elytrapvp.ffa.events.PlayerEscapeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerEscape implements Listener {
    @EventHandler
    public void onEscape(PlayerEscapeEvent e) {
        Player p = e.getPlayer();

        // Makes sure the player was previously alive.
        if(DeathType.list.containsKey(p.getUniqueId())) {
            return;
        }

        DeathType.list.put(p.getUniqueId(), DeathType.ESCAPE);
        p.setHealth(0);
    }
}
