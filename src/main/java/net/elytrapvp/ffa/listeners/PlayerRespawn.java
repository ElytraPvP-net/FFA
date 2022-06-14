package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.DeathType;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.cosmetics.Hat;
import net.elytrapvp.ffa.utilities.LocationUtils;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import net.elytrapvp.ffa.utilities.item.SkullBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {
    private final FFA plugin;

    public PlayerRespawn(FFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        cp.setStatus(Status.LOBBY);
        DeathType.list.remove(p.getUniqueId());

        //e.setRespawnLocation(LocationUtils.getSpawn());
        e.setRespawnLocation(plugin.getArenaManager().getSelectedArena().getSpawn());

        p.setMaxHealth(20);
        p.setHealth(20);

        p.getInventory().clear();
        p.getInventory().setItem(1, new ItemBuilder(Material.EMERALD).setDisplayName("&aCosmetics").build());
        p.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR).setDisplayName("&aKit Selector").build());
        p.getInventory().setItem(8, new SkullBuilder(p).setDisplayName("&aStats").build());

        p.setGameMode(GameMode.SURVIVAL);

        // Add a player's hat.
        if(cp.getHat() != 0) {
            p.getInventory().setHelmet(Hat.getHats().get(cp.getHat()).getHat());
        }
    }
}