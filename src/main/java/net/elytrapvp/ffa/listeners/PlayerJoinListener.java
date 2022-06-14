package net.elytrapvp.ffa.listeners;

import com.viaversion.viaversion.api.Via;
import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.cosmetics.Hat;
import net.elytrapvp.ffa.objects.Spectator;
import net.elytrapvp.ffa.scoreboards.FFAScoreboard;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import net.elytrapvp.ffa.utilities.item.SkullBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final FFA plugin;

    public PlayerJoinListener(FFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        CustomPlayer customPlayer = new CustomPlayer(player.getUniqueId());
        CustomPlayer.getCustomPlayers().put(player.getUniqueId(), customPlayer);

        // Set the status of the player.
        customPlayer.setStatus(Status.LOBBY);

        // Exit if plugin is disabled.
        if(!plugin.getSettingsManager().getConfig().getBoolean("Enabled")) {
            return;
        }

        if(plugin.getSettingsManager().getConfig().getBoolean("Spawn.Set")) {
            player.teleport(plugin.getArenaManager().getSelectedArena().getSpawn());
        }

        player.setMaxHealth(20);
        player.setHealth(20);

        player.getInventory().clear();
        player.getInventory().setItem(1, new ItemBuilder(Material.EMERALD).setDisplayName("&aCosmetics").build());
        player.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR).setDisplayName("&aKit Selector").build());
        player.getInventory().setItem(8, new SkullBuilder(player).setDisplayName("&aStats").build());

        player.setGameMode(GameMode.SURVIVAL);

        Spectator.getSpectators().forEach(u -> {
            Player spectator = Bukkit.getPlayer(u);
            player.hidePlayer(FFA.getPlugin(), spectator);
        });

        if(Via.getAPI().getPlayerVersion(player.getUniqueId()) < 316) {
            ChatUtils.chat(player, "&c&lThis mode requires 1.11.2 or newer!");
        }

        Bukkit.getScheduler().runTaskLater(FFA.getPlugin(), () -> {
            // Add a player's hat.
            if(customPlayer.getHat() != 0) {
                player.getInventory().setHelmet(Hat.getHats().get(customPlayer.getHat()).getHat());
            }

            new FFAScoreboard(player);
        }, 5);
    }
}