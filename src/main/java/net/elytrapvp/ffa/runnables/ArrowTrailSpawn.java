package net.elytrapvp.ffa.runnables;

import net.elytrapvp.ffa.game.cosmetics.ArrowTrail;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowTrailSpawn extends BukkitRunnable {
    @Override
    public void run() {
        for (Arrow a : ArrowTrail.getArrows().keySet()) {
            ArrowTrail.getArrows().get(a).spawnParticle(a);

            if(a == null || a.isDead())
                ArrowTrail.getArrows().remove(a);
        }
    }
}
