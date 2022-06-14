package net.elytrapvp.ffa.game.kits.kits;

import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.kits.Kit;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpectralKit extends Kit {
    public SpectralKit() {
        super("Spectral", 10);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aSpectral Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 3)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(0, bow);

        ItemStack arrow = new ItemBuilder(Material.SPECTRAL_ARROW, 64)
                .setDisplayName("&aSpectral Arrow")
                .build();
        setArrow(arrow);

        setPrice(400);
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.SPECTRAL_ARROW)
                .setDisplayName("&aSpectral")
                .addLore("&7Keep track of your enemies with")
                .addLore("&7spectral arrows.")
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
