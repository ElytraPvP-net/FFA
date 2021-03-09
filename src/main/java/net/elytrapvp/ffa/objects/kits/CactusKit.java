package net.elytrapvp.ffa.objects.kits;

import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CactusKit extends Kit {
    public CactusKit() {
        super("Cactus", 13);

        ItemStack sword = new ItemBuilder(Material.WOODEN_SWORD)
                .setDisplayName("&aCactus Sword")
                .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                .build();
        addItem(0, sword);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aCactus Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 3)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(1, bow);

        ItemStack elytra = new ItemBuilder(Material.ELYTRA)
                .setDisplayName("&aElytra")
                .addEnchantment(Enchantment.THORNS, 5)
                .build();
        setElytra(elytra);

        setPrice(400);
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.CACTUS)
                .setDisplayName("&aCactus")
                .addLore("&7Damage your opponents")
                .addLore("&7every time they hit you.")
                .addLore("");

        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
        if(cp.getUnlockedKits().contains(getId())) {
            builder.addLore("&7Left Click to Select")
                    .addLore("&7Right Click to Preview");
        }
        else {
            builder.setMaterial(Material.GRAY_DYE)
                    .addLore("&6Price: 400 Coins")
                    .addLore("&7Left Click to Purchase")
                    .addLore("&7Right Click to Preview");
        }

        return builder.build();
    }
}
