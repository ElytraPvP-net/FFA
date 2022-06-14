package net.elytrapvp.ffa.game.cosmetics;

import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KillMessage {
    private int id;
    private String message;
    private int price;

    private static HashMap<Integer, KillMessage> killMessages = new HashMap<>();

    public KillMessage(String message, int id, int price) {
        this.message = message;
        this.id = id;
        this.price = price;

        getKillMessages().put(id, this);
    }

    public static HashMap<Integer, KillMessage> getKillMessages() {
        return killMessages;
    }

    public ItemStack getIcon(Player p) {
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        if(ep.getUnlockedKillMessages().contains(id)) {
            ItemBuilder builder = new ItemBuilder(Material.OAK_SIGN)
                    .setDisplayName(message)
                    .addLore("&7Click to Select");
            return builder.build();
        }

        ItemBuilder builder = new ItemBuilder(Material.GRAY_DYE)
                .setDisplayName(message)
                .addLore("&6Price: " + price + " Coins")
                .addLore("&7Click to purchase.");
        return builder.build();
    }

    public int getID() {
        return id;
    }

    public String getKillMessage() {
        return ChatUtils.translate(message);
    }

    public int getPrice() {
        return price;
    }

    /**
     * Unlock a Kill Message
     * @param p Player to unlock kill message from.
     */
    public void unlock(Player p) {
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        // Run if already unlocked.
        if(ep.getUnlockedKillMessages().contains(getID())) {
            ep.setKillMessage(getID());
            ChatUtils.chat(p, "&a&lCosmetics &8» &aKill Message has been equipped.");
            return;
        }

        if(price == 0) {
            return;
        }

        if(ep.getCoins() >= price) {
            ep.removeCoins(price);
            ep.unlockKillMessage(getID());
            ep.setKillMessage(getID());
            ChatUtils.chat(p, "&a&lCosmetics &8» &aKill Message has been purchased and equipped.");
            return;
        }
        ChatUtils.chat(p, "&c&lError &8» &cYou do not have enough coins for that.");
    }
}
