package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.game.cosmetics.ArrowTrail;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class EntityShootBowListener implements Listener {
    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        if(!(event.getProjectile() instanceof Arrow)) {
            return;
        }

        Player player = (Player) event.getEntity();
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayers().get(player.getUniqueId());
        Arrow arrow = (Arrow) event.getProjectile();

        // Update stats
        customPlayer.setArrowsShot(customPlayer.getArrowsShot() + 1);

        // If using Spectator Kit, replace arrow
        if(customPlayer.getKit() == 10 && customPlayer.getStatus() == Status.ARENA) {
            int slot = player.getInventory().first(Material.SPECTRAL_ARROW);
            player.getInventory().getItem(slot).setAmount(64);
        }

        // If using Poisoner Kit, replace arrow
        if(customPlayer.getKit() == 11 && customPlayer.getStatus() == Status.ARENA) {
            int slot = player.getInventory().first(Material.TIPPED_ARROW);
            player.getInventory().getItem(slot).setAmount(64);
        }

        // Remove critical particles without lowering damage.
        double damage = arrow.getDamage();
        arrow.setCritical(false);
        arrow.setDamage(damage);

        if(customPlayer.getArrowTrail() != 0) {
            ArrowTrail.getArrows().put(arrow, ArrowTrail.getTrails().get(customPlayer.getArrowTrail()));
        }
    }
}