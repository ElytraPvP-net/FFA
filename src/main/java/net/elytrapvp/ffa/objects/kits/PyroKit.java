package net.elytrapvp.ffa.objects.kits;

import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Kit;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PyroKit extends Kit {
    public PyroKit() {
        super("Pyro", 3);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aPyro Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 2)
                .addEnchantment(Enchantment.ARROW_FIRE, 1)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(0, bow);

        addEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.FLINT_AND_STEEL)
                .setDisplayName("&aPyro")
                .addLore("&7Light up the night,")
                .addLore("&7or your enemies.")
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

        return builder.build();
    }
}
