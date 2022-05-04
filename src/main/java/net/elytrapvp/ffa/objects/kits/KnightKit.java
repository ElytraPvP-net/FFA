package net.elytrapvp.ffa.objects.kits;

import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Kit;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class KnightKit extends Kit {
    public KnightKit() {
        super("Knight", 2);

        ItemStack sword = new ItemBuilder(Material.STONE_SWORD)
                .setDisplayName("&aKnight Sword")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                .build();
        addItem(0, sword);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aKnight Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 3)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(1, bow);
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.STONE_SWORD)
                .setDisplayName("&aKnight")
                .addLore("&7Slash your way into battle")
                .addLore("&7with a stone sword.")
                .addLore("");

        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
        if(cp.getUnlockedKits().contains(getId())) {
            builder.addLore("&7Left Click to Select")
                    .addLore("&7Right Click to Preview");
        }
        else {
            builder.setMaterial(Material.GRAY_DYE)
                    .addLore("&6Price: 0 Coins")
                    .addLore("&7Left Click to Purchase")
                    .addLore("&7Right Click to Preview");
        }

        builder.addFlag(ItemFlag.HIDE_ATTRIBUTES);
        return builder.build();
    }
}
