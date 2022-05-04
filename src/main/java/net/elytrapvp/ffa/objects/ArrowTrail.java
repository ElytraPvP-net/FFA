package net.elytrapvp.ffa.objects;

import net.elytrapvp.ffa.managers.SettingsManager;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import net.elytrapvp.ffa.utilities.item.ItemUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ArrowTrail {
    private static final SettingsManager settings = SettingsManager.getInstance();
    private static HashMap<Arrow, ArrowTrail> arrows = new HashMap<>();
    private static HashMap<Integer, ArrowTrail> trails = new HashMap<>();

    private String name;
    private Particle particle;
    private int r;
    private int g;
    private int b;
    private int id;
    private int price;

    public ArrowTrail(int id) {
        this.id = id;
        name = settings.getArrowTrails().getString("ArrowTrails." + id + ".Name");
        particle = Particle.valueOf(settings.getArrowTrails().getString("ArrowTrails." + id + ".Particle"));
        price = settings.getArrowTrails().getInt("ArrowTrails." + id + ".Price");

        ConfigurationSection section = settings.getArrowTrails().getConfigurationSection("ArrowTrails." + id + ".Color");

        if(section != null) {
            r = settings.getArrowTrails().getInt("ArrowTrails." + id + ".Color.R");
            g = settings.getArrowTrails().getInt("ArrowTrails." + id + ".Color.G");
            b = settings.getArrowTrails().getInt("ArrowTrails." + id + ".Color.B");
        }
        else {
            r = 0;
            g = 0;
            b = 0;
        }

        getTrails().put(id, this);
    }

    public static HashMap<Arrow, ArrowTrail> getArrows() {
        return arrows;
    }

    public static HashMap<Integer, ArrowTrail> getTrails() {
        return trails;
    }

    public ItemStack getIcon(Player p) {
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        if(ep.getUnlockedArrowTrails().contains(id))
            return ItemUtils.createFromYML(settings.getArrowTrails(), "ArrowTrails." + id + ".Icon");

        ItemBuilder builder = new ItemBuilder(Material.GRAY_DYE)
                .setDisplayName("&c" + name)
                .addLore("&6Price: " + price + " Coins")
                .addLore("&7Click to purchase");
        return builder.build();
    }

    public String getName() {
        return name;
    }

    public void spawnParticle(Arrow a) {
        if(particle == Particle.REDSTONE) {
            a.getWorld().spawnParticle(particle, a.getLocation(), 0, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(r, g, b), 1));
        }
        else {
            a.getWorld().spawnParticle(particle, a.getLocation(), 0, 0, 0, 0, 1);
        }
    }

    /**
     * Unlock a Kill Message
     * @param p Player to unlock kill message from.
     */
    public void unlock(Player p) {
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        // Run if already unlocked.
        if(ep.getUnlockedArrowTrails().contains(id)) {
            ep.setArrowTrail(id);
            ChatUtils.chat(p, "&a&lCosmetics &8» &aArrow Trail has been equipped.");
            return;
        }

        if(price == 0) {
            return;
        }

        if(ep.getCoins() >= price) {
            ep.removeCoins(price);
            ep.unlockArrowTrail(id);
            ep.setArrowTrail(id);
            ChatUtils.chat(p, "&a&lCosmetics &8» &aArrow Trail has been purchased and equipped.");
            return;
        }
        ChatUtils.chat(p, "&c&lError &8» &cYou do not have enough coins for that.");
    }
}