package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        // Exit if not a player.
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        // Exit if damager is not an arrow.
        if(!(event.getDamager() instanceof Arrow)) {
            if(event.getDamager() instanceof Player) {
                Player damager = (Player) event.getDamager();
                CustomPlayer customDamager = CustomPlayer.getCustomPlayers().get(damager.getUniqueId());
                if(customDamager.getStatus() == Status.SPECTATOR)
                    event.setCancelled(true);
            }
            return;
        }

        Arrow arrow = (Arrow) event.getDamager();

        if(arrow.getShooter() instanceof Player) {
            Player shooter = (Player) arrow.getShooter();
            CustomPlayer customShooter = CustomPlayer.getCustomPlayers().get(shooter.getUniqueId());
            customShooter.setArrowsHit(customShooter.getArrowsHit() + 1);
            customShooter.setDamageDealt(customShooter.getDamageDealt() + event.getDamage());
        }
    }
}