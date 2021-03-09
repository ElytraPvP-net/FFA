package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;

public class ProjectileHit implements Listener {
    ArrayList<Material> panes;

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        panes = new ArrayList<>();
        panes.add(Material.GLASS_PANE);
        panes.add(Material.BLACK_STAINED_GLASS_PANE);

        // Exit if material is null.
        if(e.getHitBlock() == null) {
            return;
        }

        Material m = e.getHitBlock().getType();

        if(!panes.contains(m)) {
            return;
        }

        breakGlass(e.getEntity(), e.getHitBlock(), m);
    }

    private void breakGlass(Entity e, Block b, Material m) {
       Projectile projectile = (Projectile) e;

       if(!(projectile.getShooter() instanceof Player)) {
           return;
       }

       Player p = (Player) projectile.getShooter();
       CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
       cp.setWindowsBroken(cp.getWindowsBroken() + 1);

        b.setType(Material.AIR);
        projectile.getWorld().playSound(b.getLocation(), Sound.BLOCK_GLASS_BREAK, 3.0F, 0.5F);

        e.getNearbyEntities(1,1,1).forEach(entity -> {
            if(entity instanceof Player) {
                Player player = (Player) entity;
                if(player.getHealth() >= 1) {
                    player.setHealth(player.getHealth() - 1);
                }
                else {
                    p.setHealth(0);
                }
            }
        });

        Bukkit.getScheduler().runTaskLater(FFA.getPlugin(), () -> {
            b.setType(m);

            getBlocks(b, 1).forEach(block -> {
                if(block.getType() == Material.QUARTZ_BLOCK) {
                    block.setType(Material.AIR);
                    block.setType(Material.QUARTZ_BLOCK);
                }
            });
        }, 300);
    }

    private ArrayList<Block> getBlocks(Block start, int radius){
        ArrayList<Block> blocks = new ArrayList<>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }
}
