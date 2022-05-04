package net.elytrapvp.ffa.objects.kits;

import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Kit;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SniperKit extends Kit {
    public SniperKit() {
        super("Sniper", 1);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aSniper Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 5)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(0, bow);

        setHealth(12);
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.BOW)
                .setDisplayName("&aSniper")
                .addLore("&7A strong bow to snipe")
                .addLore("&7your opponents.")
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