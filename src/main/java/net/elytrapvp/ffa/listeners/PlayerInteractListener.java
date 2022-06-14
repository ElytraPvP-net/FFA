package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Spectator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    private final FFA plugin;

    public PlayerInteractListener(FFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        // Exit if not enabled.
        if(!plugin.getSettingsManager().getConfig().getBoolean("Enabled")) {
            return;
        }

        Player player = event.getPlayer();
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayers().get(player.getUniqueId());

        if(player.getOpenInventory().getType() != InventoryType.CRAFTING && player.getOpenInventory().getType() != InventoryType.CREATIVE) {
            if(player.getName().contains("*")) {
                if(player.getOpenInventory().getType() != InventoryType.PLAYER) {
                    event.setCancelled(true);
                    return;
                }
            }

            event.setCancelled(true);
            return;
        }

        // Exit if player is in other mode.
        if(customPlayer.getStatus() == Status.OTHER)
            return;

        // Exit if the item is null.
        if(event.getItem() == null)
            return;

        // Replace fireworks when used.
        if(event.getItem().getType() == Material.FIREWORK_ROCKET) {
            event.getItem().setAmount(64);
        }

        // Exit if item meta is null.
        if(event.getItem().getItemMeta() == null)
            return;

        String item = ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName());
        switch (item) {
            case "Kit Selector" -> {
                player.performCommand("kits");
                event.setCancelled(true);
            }
            case "Stats" -> {
                player.performCommand("stats");
                event.setCancelled(true);
            }
            case "Cosmetics" -> {
                player.performCommand("cosmetics");
                event.setCancelled(true);
            }
            case "Leave" -> {
                Spectator.remove(player);
                event.setCancelled(true);
            }
            case "Maps" -> {
                player.performCommand("maps");
                event.setCancelled(true);
            }
        }
    }
}
