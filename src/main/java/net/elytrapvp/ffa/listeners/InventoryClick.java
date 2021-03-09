package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.managers.SettingsManager;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!settings.getConfig().getBoolean("Enabled")) {
            return;
        }

        if(!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getWhoClicked();
        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        if(p.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if(e.getCurrentItem() == null) {
            return;
        }

        switch (e.getCurrentItem().getType()) {
            case PLAYER_HEAD:
            case EMERALD:
            case BOOK:
            case NETHER_STAR:
                break;

            default:
                return;
        }

        if(cp.getStatus() == Status.LOBBY) {
            e.setCancelled(true);
        }
    }
}