package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.DeathType;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.cosmetics.Hat;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import net.elytrapvp.ffa.utilities.item.SkullBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
    private final FFA plugin;

    public PlayerRespawnListener(FFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayers().get(player.getUniqueId());

        customPlayer.setStatus(Status.LOBBY);
        DeathType.list.remove(player.getUniqueId());

        event.setRespawnLocation(plugin.getArenaManager().getSelectedArena().getSpawn());

        player.setMaxHealth(20);
        player.setHealth(20);

        player.getInventory().clear();
        player.getInventory().setItem(1, new ItemBuilder(Material.EMERALD).setDisplayName("&aCosmetics").build());
        player.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR).setDisplayName("&aKit Selector").build());
        player.getInventory().setItem(8, new SkullBuilder(player).setDisplayName("&aStats").build());

        player.setGameMode(GameMode.SURVIVAL);

        // Add a player's hat.
        if(customPlayer.getHat() != 0) {
            player.getInventory().setHelmet(Hat.getHats().get(customPlayer.getHat()).getHat());
        }
    }
}