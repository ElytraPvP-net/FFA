package net.elytrapvp.ffa.objects.kits;

import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HealerKit extends Kit {
    public HealerKit() {
        super("Healer", 8);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aHealer Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 3)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(0, bow);

        setPrice(400);

        addEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.GOLDEN_APPLE)
                .setDisplayName("&aHealer")
                .addLore("&7Regenerate health faster")
                .addLore("&7to outlive your opponents.")
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
