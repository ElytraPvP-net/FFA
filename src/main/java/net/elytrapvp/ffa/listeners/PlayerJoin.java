package net.elytrapvp.ffa.listeners;

import com.viaversion.viaversion.api.Via;
import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.managers.SettingsManager;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.cosmetics.Hat;
import net.elytrapvp.ffa.objects.Spectator;
import net.elytrapvp.ffa.scoreboards.FFAScoreboard;
import net.elytrapvp.ffa.utilities.LocationUtils;
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

public class PlayerJoin implements Listener {
    SettingsManager settings = SettingsManager.getInstance();
    private final FFA plugin;

    public PlayerJoin(FFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        CustomPlayer cp = new CustomPlayer(p.getUniqueId());
        CustomPlayer.getCustomPlayers().put(p.getUniqueId(), cp);

        // Set the status of the player.
        cp.setStatus(Status.LOBBY);

        // Exit if plugin is disabled.
        if(!settings.getConfig().getBoolean("Enabled")) {
            return;
        }

        if(settings.getConfig().getBoolean("Spawn.Set")) {
            //p.teleport(LocationUtils.getSpawn());
            p.teleport(plugin.getArenaManager().getSelectedArena().getSpawn());
        }

        p.setMaxHealth(20);
        p.setHealth(20);

        p.getInventory().clear();
        p.getInventory().setItem(1, new ItemBuilder(Material.EMERALD).setDisplayName("&aCosmetics").build());
        p.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR).setDisplayName("&aKit Selector").build());
        p.getInventory().setItem(8, new SkullBuilder(p).setDisplayName("&aStats").build());

        p.setGameMode(GameMode.SURVIVAL);

        Spectator.getSpectators().forEach(u -> {
            Player spectator = Bukkit.getPlayer(u);
            p.hidePlayer(FFA.getPlugin(), spectator);
        });

        if(Via.getAPI().getPlayerVersion(p.getUniqueId()) < 316) {
            ChatUtils.chat(p, "&c&lThis mode requires 1.11.2 or newer!");
        }

        Bukkit.getScheduler().runTaskLater(FFA.getPlugin(), () -> {
            // Add a player's hat.
            if(cp.getHat() != 0) {
                p.getInventory().setHelmet(Hat.getHats().get(cp.getHat()).getHat());
            }

            new FFAScoreboard(p);

            //LevelsPlayer lp = LevelsAPI.getLevelsPlayers().get(p.getUniqueId());
            //int level = lp.getLevel();
            //float exp = lp.getExperience();
            //float maxExp = LevelsAPI.getRequiredExp(level);

            //p.setLevel(level);
            //p.setExp(exp / maxExp);
        }, 5);
    }
}