package net.elytrapvp.ffa.runnables;

import net.elytrapvp.ffa.game.cosmetics.ArrowTrail;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowTrailSpawn extends BukkitRunnable {
    @Override
    public void run() {
        for (Arrow arrow : ArrowTrail.getArrows().keySet()) {
            ArrowTrail.getArrows().get(arrow).spawnParticle(arrow);

            if(arrow == null || arrow.isDead())
                ArrowTrail.getArrows().remove(arrow);
        }
    }
}