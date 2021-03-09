package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener {
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        // Exit if not a player.
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        // Exit if damager is not an arrow.
        if(!(e.getDamager() instanceof Arrow)) {
            if(e.getDamager() instanceof Player) {
                Player d = (Player) e.getDamager();
                CustomPlayer ep = CustomPlayer.getCustomPlayers().get(d.getUniqueId());
                if(ep.getStatus() == Status.SPECTATOR)
                    e.setCancelled(true);
            }
            return;
        }

        Arrow a = (Arrow) e.getDamager();

        if(a.getShooter() instanceof Player) {
            Player s = (Player) a.getShooter();
            CustomPlayer es = CustomPlayer.getCustomPlayers().get(s.getUniqueId());
            es.setArrowsHit(es.getArrowsHit() + 1);
            es.setDamageDealt(es.getDamageDealt() + e.getDamage());
        }
    }
}