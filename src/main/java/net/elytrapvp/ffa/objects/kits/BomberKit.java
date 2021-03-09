package net.elytrapvp.ffa.objects.kits;

import net.elytrapvp.elytrapvp.items.FireworkBuilder;
import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Kit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BomberKit extends Kit {
    public BomberKit() {
        super("Bomber", 7);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aBomber Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 3)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(0, bow);

        FireworkEffect effect = FireworkEffect.builder()
                .withColor(Color.RED)
                .build();
        ItemStack fireworks = new FireworkBuilder()
                .addEffect(effect)
                .setDisplayName("&aExplosive Fireworks")
                .build();
        addItem(2, fireworks);

        setPrice(400);
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.TNT)
                .setDisplayName("&aBomber")
                .addLore("&7Blow your competition away")
                .addLore("&7with explosive fireworks.")
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
