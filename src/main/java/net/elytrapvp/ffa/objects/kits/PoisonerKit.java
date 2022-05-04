package net.elytrapvp.ffa.objects.kits;

import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Kit;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class PoisonerKit extends Kit {
    public PoisonerKit() {
        super("Poisoner", 11);
        setPrice(400);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aPoisoner Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 3)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(0, bow);

        ItemStack arrow = new ItemStack(Material.TIPPED_ARROW, 64);
        PotionMeta arrowMeta = (PotionMeta) arrow.getItemMeta();
        arrowMeta.setBasePotionData(new PotionData(PotionType.POISON));
        arrow.setItemMeta(arrowMeta);
        setArrow(arrow);
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.SPIDER_EYE)
                .setDisplayName("&aPoisoner")
                .addLore("&7Poison your opponents")
                .addLore("&7with tipped arrows.")
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
        item.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        return item;
    }
}