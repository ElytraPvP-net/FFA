package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.inventories.PracticeGUI;
import net.elytrapvp.ffa.managers.SettingsManager;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Spectator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        // Exit if not enabled.
        if(!settings.getConfig().getBoolean("Enabled")) {
            return;
        }

        Player p = e.getPlayer();
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        if(p.getOpenInventory().getType() != InventoryType.CRAFTING && p.getOpenInventory().getType() != InventoryType.CREATIVE) {
            if(p.getName().contains("*")) {
                if(p.getOpenInventory().getType() != InventoryType.PLAYER) {
                    e.setCancelled(true);
                    return;
                }
            }

            e.setCancelled(true);
            return;
        }

        // Exit if player is in other mode.
        if(ep.getStatus() == Status.OTHER)
            return;

        // Exit if the item is null.
        if(e.getItem() == null)
            return;

        // Replace fireworks when used.
        if(e.getItem().getType() == Material.FIREWORK_ROCKET) {
            e.getItem().setAmount(64);
        }

        // Exit if item meta is null.
        if(e.getItem().getItemMeta() == null)
            return;

        String item = ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName());
        switch (item) {
            case "Kit Selector" -> {
                p.performCommand("kits");
                e.setCancelled(true);
            }
            case "Stats" -> {
                p.performCommand("stats");
                e.setCancelled(true);
            }
            case "Cosmetics" -> {
                p.performCommand("cosmetics");
                e.setCancelled(true);
            }
            case "Leave" -> {
                Spectator.remove(p);
                e.setCancelled(true);
            }
            case "Maps" -> {
                p.performCommand("maps");
                e.setCancelled(true);
            }
            case "Practice" -> {
                new PracticeGUI(FFA.getPlugin()).open(p);
                e.setCancelled(true);
            }
        }
    }
}
