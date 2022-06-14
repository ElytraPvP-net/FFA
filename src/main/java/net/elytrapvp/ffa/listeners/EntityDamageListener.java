package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        // Exit if not a player.
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) event.getEntity();
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        if(customPlayer == null) {
            return;
        }

        if(event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
            if(customPlayer.getKit() == 7) {
                event.setCancelled(true);
                return;
            }

            event.setDamage(event.getDamage() * 2);
        }

        if(customPlayer.getStatus() == Status.SPECTATOR) {
            event.setCancelled(true);
            return;
        }

        customPlayer.setDamageTaken(customPlayer.getDamageTaken() + event.getDamage());
    }
}
