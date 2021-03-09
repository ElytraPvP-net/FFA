package net.elytrapvp.ffa.objects.kits;

import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Kit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PoseidonKit extends Kit {
    public PoseidonKit() {
        super("Poseidon", 12);

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        ItemStack trident = new ItemBuilder(Material.TRIDENT)
                .setDisplayName("&aPoseidon's Trident")
                .addEnchantment(Enchantment.LOYALTY, 5)
                .addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier)
                .setUnbreakable(true)
                .build();
        addItem(0, trident);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aPoseidon's Bow")
                .setUnbreakable(true)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 3)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
        addItem(1, bow);

        setHealth(16);
        setPrice(400);
    }

    public ItemStack getIcon(Player p) {
        ItemBuilder builder = new ItemBuilder(Material.TRIDENT)
                .setDisplayName("&aPoseidon")
                .addLore("&7Obliterate your opponents")
                .addLore("&7with a trident.")
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
