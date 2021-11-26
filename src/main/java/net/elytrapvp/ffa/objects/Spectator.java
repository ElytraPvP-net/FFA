package net.elytrapvp.ffa.objects;

import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.elytrapvp.items.SkullBuilder;
import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.managers.SettingsManager;
import net.elytrapvp.ffa.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Spectator {
    SettingsManager settings = SettingsManager.getInstance();
    private static Set<UUID> spectators = new HashSet<>();

    /**
     * Add a player to the spectator list.
     * @param p Player
     */
    public static void add(Player p) {
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
        ep.setStatus(Status.SPECTATOR);

        p.setAllowFlight(true);
        p.setFlying(true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 2));
        p.getInventory().clear();
        p.getInventory().setItem(0, new ItemBuilder(Material.BARRIER).setDisplayName("&cLeave").build());

        Bukkit.getOnlinePlayers().forEach(player -> player.showPlayer(FFA.getPlugin(), p));

        spectators.add(p.getUniqueId());
    }

    /**
     * Remove a player from the spectator list.
     * @param p Player
     */
    public static void remove(Player p) {
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
        ep.setStatus(Status.LOBBY);

        p.teleport(LocationUtils.getSpawn());
        p.setFlying(false);
        p.setAllowFlight(false);
        p.removePotionEffect(PotionEffectType.INVISIBILITY);

        p.getInventory().clear();
        p.getInventory().setItem(1, new ItemBuilder(Material.EMERALD).setDisplayName("&aCosmetics").build());
        p.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR).setDisplayName("&aKit Selector").build());
        p.getInventory().setItem(7, new ItemBuilder(Material.PAPER).setDisplayName("&aMaps").build());
        p.getInventory().setItem(8, new SkullBuilder(p).setDisplayName("&aStats").build());

        Bukkit.getOnlinePlayers().forEach(player -> player.showPlayer(FFA.getPlugin(), p));

        spectators.remove(p.getUniqueId());
    }

    /**
     * Get all spectators.
     * @return Set of Spectators.
     */
    public static Set<UUID> getSpectators() {
        return spectators;
    }
}