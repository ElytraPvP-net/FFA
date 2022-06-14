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

public class ProjectileHitListener implements Listener {
    ArrayList<Material> panes;

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        panes = new ArrayList<>();
        panes.add(Material.GLASS_PANE);
        panes.add(Material.BLACK_STAINED_GLASS_PANE);

        // Exit if material is null.
        if(event.getHitBlock() == null) {
            return;
        }

        Material material = event.getHitBlock().getType();

        if(!panes.contains(material)) {
            return;
        }

        breakGlass(event.getEntity(), event.getHitBlock(), material);
    }

    private void breakGlass(Entity entity, Block block, Material material) {
       Projectile projectile = (Projectile) entity;

       if(!(projectile.getShooter() instanceof Player)) {
           return;
       }

       Player p = (Player) projectile.getShooter();
       CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
       cp.setWindowsBroken(cp.getWindowsBroken() + 1);

        block.setType(Material.AIR);
        projectile.getWorld().playSound(block.getLocation(), Sound.BLOCK_GLASS_BREAK, 3.0F, 0.5F);

        entity.getNearbyEntities(1,1,1).forEach(nearbyEntity -> {
            if(nearbyEntity instanceof Player) {
                Player player = (Player) entity;
                if(player.getHealth() >= 1) {
                    player.setHealth(player.getHealth() - 1);
                }
                else {
                    player.setHealth(0);
                }
            }
        });

        Bukkit.getScheduler().runTaskLater(FFA.getPlugin(), () -> {
            block.setType(material);

            getBlocks(block, 1).forEach(surroundingBlock -> {
                if(surroundingBlock.getType() == Material.QUARTZ_BLOCK) {
                    surroundingBlock.setType(Material.AIR);
                    surroundingBlock.setType(Material.QUARTZ_BLOCK);
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
