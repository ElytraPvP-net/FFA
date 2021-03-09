package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        // Exit if not a player.
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();
        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        if(cp == null) {
            return;
        }

        if(cp.getKit() == 7  && e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
            e.setCancelled(true);
            return;
        }

        if(cp.getStatus() == Status.SPECTATOR) {
            e.setCancelled(true);
            return;
        }

        cp.setDamageTaken(cp.getDamageTaken() + e.getDamage());
    }
}
