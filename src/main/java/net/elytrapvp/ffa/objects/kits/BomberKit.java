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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BomberKit extends Kit {
    public BomberKit() {
        super("Bomber", 7);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aBomber Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(0, bow);

        ItemStack crossbow = new ItemBuilder(Material.CROSSBOW)
                .setDisplayName("&aBomber Crossbow")
                .setUnbreakable(true)
                .build();
        addItem(1, crossbow);
        addItem(2, crossbow);
        addItem(3, crossbow);

        FireworkEffect effect = FireworkEffect.builder()
                .withColor(Color.RED)
                .with(FireworkEffect.Type.BALL_LARGE)
                .build();

        ItemStack fireworks = new FireworkBuilder()
                .addEffect(effect)
                .setPower(3)
                .setDisplayName("&aExplosive Fireworks")
                .setAmount(64)
                .build();
        addItem(40, fireworks);
        addItem(4, new ItemBuilder(Material.FIREWORK_ROCKET, 64).build());

        PotionEffect slowFalling = new PotionEffect(PotionEffectType.SLOW_FALLING, 9999, 0);
        addEffect(slowFalling);

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
