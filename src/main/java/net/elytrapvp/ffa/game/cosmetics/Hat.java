package net.elytrapvp.ffa.game.cosmetics;

import net.elytrapvp.ffa.enums.HatType;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import net.elytrapvp.ffa.utilities.item.SkullBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Hat {
    private final String name;
    private final int price;
    private final String skin;
    private final int id;
    private final HatType type;

    private static final HashMap<Integer, Hat> hats = new HashMap<>();

    public Hat(String name, int price, int id, String skin, HatType type) {
        this.name = name;
        this.price = price;
        this.skin = skin;
        this.id = id;
        this.type = type;

        type.addHat(id, this);

        hats.put(id, this);
    }

    public static HashMap<Integer, Hat> getHats() {
        return hats;
    }

    public ItemStack getHat() {
        ItemStack hat = new SkullBuilder(skin)
                .setDisplayName("&a" + getName())
                .build();
        return hat;

    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon(Player p) {
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        if(ep.getUnlockedHats().contains(id)) {
            ItemStack hat = new SkullBuilder(skin)
                    .setDisplayName("&a" + getName())
                    .addLore("&7Click to equip")
                    .build();

            return hat;
        }

        ItemBuilder builder = new ItemBuilder(Material.GRAY_DYE)
                .setDisplayName("&c" + getName())
                .addLore("&6Price: " + getPrice())
                .addLore("&7Click to purchase");
        return builder.build();
    }

    private HatType getType() {
        return type;
    }

    /**
     * Unlock a hat
     * @param p Player to unlock tag from.
     */
    public void unlock(Player p) {
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        // Run if already unlocked.
        if(ep.getUnlockedHats().contains(id)) {
            p.getInventory().setHelmet(getHat());
            ep.setHat(id);
            ChatUtils.chat(p, "&a&lCosmetics &8» &aHat has been equipped.");
            return;
        }

        if(price == 0) {
            return;
        }

        if(ep.getCoins() >= price) {
            ep.removeCoins(price);
            ep.unlockHat(id);
            ep.setHat(id);
            p.getInventory().setHelmet(getHat());
            ChatUtils.chat(p, "&a&lCosmetics &8» &aHat has been purchased and equipped.");
            return;
        }
        ChatUtils.chat(p, "&c&lError &8» &cYou do not have enough coins for that.");
    }
}