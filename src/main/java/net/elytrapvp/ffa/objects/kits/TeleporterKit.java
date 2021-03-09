package net.elytrapvp.ffa.objects.kits;

import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TeleporterKit extends Kit {
    public TeleporterKit() {
        super("Teleporter", 9);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aTeleporter Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 3)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(0, bow);

        ItemStack enderpearls = new ItemBuilder(Material.ENDER_PEARL, 64)
                .setDisplayName("&aEnder pearl")
                .build();
        addItem(2, enderpearls);

        setPrice(400);
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.ENDER_PEARL)
                .setDisplayName("&aTeleporter")
                .addLore("&7Confuse your enemies")
                .addLore("&7with ender pearls.")
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
