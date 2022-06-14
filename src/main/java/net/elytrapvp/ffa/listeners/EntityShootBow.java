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

public class EntityShootBow implements Listener {
    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        if(!(e.getProjectile() instanceof Arrow)) {
            return;
        }

        Player p = (Player) e.getEntity();
        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
        Arrow a = (Arrow) e.getProjectile();

        // Update stats
        cp.setArrowsShot(cp.getArrowsShot() + 1);

        // If using Spectator Kit, replace arrow
        if(cp.getKit() == 10 && cp.getStatus() == Status.ARENA) {
            int slot = p.getInventory().first(Material.SPECTRAL_ARROW);
            p.getInventory().getItem(slot).setAmount(64);
        }

        // If using Poisoner Kit, replace arrow
        if(cp.getKit() == 11 && cp.getStatus() == Status.ARENA) {
            int slot = p.getInventory().first(Material.TIPPED_ARROW);
            p.getInventory().getItem(slot).setAmount(64);
        }

        // Remove critical particles without lowering damage.
        double damage = a.getDamage();
        a.setCritical(false);
        a.setDamage(damage);

        if(cp.getArrowTrail() != 0) {
            ArrowTrail.getArrows().put(a, ArrowTrail.getTrails().get(cp.getArrowTrail()));
        }
    }
}