package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    private final FFA plugin;

    public InventoryClickListener(FFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!plugin.getSettingsManager().getConfig().getBoolean("Enabled")) {
            return;
        }

        if(!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayers().get(player.getUniqueId());

        if(player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if(customPlayer.getStatus() == Status.ARENA) {
            switch (event.getAction()) {
                case PLACE_ONE, PLACE_SOME, PICKUP_ONE, PICKUP_SOME, PICKUP_HALF -> {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        if(event.getCurrentItem() == null) {
            return;
        }

        switch (event.getCurrentItem().getType()) {
            case PLAYER_HEAD:
            case EMERALD:
            case BOOK:
            case NETHER_STAR:
                break;

            default:
                return;
        }

        if(customPlayer.getStatus() == Status.LOBBY) {
            event.setCancelled(true);
        }
    }
}