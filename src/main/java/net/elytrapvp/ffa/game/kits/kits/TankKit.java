package net.elytrapvp.ffa.game.kits.kits;

import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.kits.Kit;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TankKit extends Kit {
    public TankKit() {
        super("Tank", 4);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aTank Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(0, bow);

        addEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 1));
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.IRON_CHESTPLATE)
                .setDisplayName("&aTank")
                .addLore("&7Increased health to help")
                .addLore("&7take the blows.")
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
