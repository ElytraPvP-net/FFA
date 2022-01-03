package net.elytrapvp.ffa.objects.kits;

import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LauncherKit extends Kit {
    public LauncherKit() {
        super("Launcher", 6);
        setPrice(400);

        ItemBuilder stick = new ItemBuilder(Material.STICK)
                .setDisplayName("&aLauncher Stick")
                .addEnchantment(Enchantment.KNOCKBACK, 10);
        addItem(0, stick.build());

        ItemBuilder bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aStickman Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 3)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .addEnchantment(Enchantment.ARROW_KNOCKBACK, 10);
        addItem(1, bow.build());
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.SLIME_BALL)
                .setDisplayName("&aLauncher")
                .addLore("&7Protect yourself with")
                .addLore("&7insane knockback.")
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
        ItemStack item = builder.build();
        return item;
    }
}