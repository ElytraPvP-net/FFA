package net.elytrapvp.ffa.objects;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.ffa.enums.TagType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Tag {
    private String tag;
    private int number;
    private int price;
    private TagType type;

    private static HashMap<Integer, Tag> tags = new HashMap<>();

    public Tag(String tag, int number, int price, TagType type) {
        this.tag = tag;
        this.number = number;
        this.price = price;
        this.type = type;

        type.addTag(this);
        tags.put(number, this);
    }

    public static HashMap<Integer, Tag> getTags() {
        return tags;
    }

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }

    public String getTag() {
        return ChatUtils.translate(tag);
    }

    /**
     * Get the icon for the tag.
     * @param p Player
     * @return Tag Icon
     */
    public ItemStack getIcon(Player p) {
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        if(ep.getUnlockedTags().contains(getNumber())) {
            ItemBuilder builder = new ItemBuilder(Material.NAME_TAG)
                    .setDisplayName(tag)
                    .addLore("&7Click to equip");
            return builder.build();
        }

        ItemBuilder builder = new ItemBuilder(Material.GRAY_DYE)
                .setDisplayName(tag)
                .addLore("&6Price: " + price + " Coins")
                .addLore("&7Click to Purchase");
        return builder.build();
    }

    public TagType getType() {
        return type;
    }

    /**
     * Unlock a tag
     * @param p Player to unlock tag from.
     */
    public void unlock(Player p) {
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        // Run if already unlocked.
        if(ep.getUnlockedTags().contains(getNumber())) {
            ep.setTag(number);
            ChatUtils.chat(p, "&2&lTags &8- &aTag has been equipped.");
            return;
        }

        if(price == 0) {
            return;
        }

        if(ep.getCoins() >= price) {
            ep.removeCoins(price);
            ep.unlockTag(number);
            ep.setTag(number);
            ChatUtils.chat(p, "&2&lTags &8- &aTag has been purchased and equipped.");
            return;
        }
        ChatUtils.chat(p, "&2&lError &8- &cYou do not have enough coins for that.");
    }
}