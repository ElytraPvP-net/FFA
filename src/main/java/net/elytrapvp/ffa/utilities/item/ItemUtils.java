package net.elytrapvp.ffa.utilities.item;

import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class ItemUtils {
    /**
     * Create an Item from a YML file.
     * @param config FileCOnfiguration to get.
     * @param path Path to item in configuration.
     * @return Item
     */
    public static ItemStack createFromYML(FileConfiguration config, String path) {
        Material m = Material.matchMaterial(config.getString(path + ".Material"));
        int n = config.getInt(path + ".Number");
        boolean unbreakable = config.getBoolean(path + ".Unbreakable");
        List<String> lore = config.getStringList(path + ".Lore");
        List<String> enchantments = config.getStringList(path + ".Enchantments");

        String name = config.getString(path + ".DisplayName");

        ItemStack item = new ItemStack(m, n);
        ItemMeta meta = item.getItemMeta();
        if(!name.equals("none"))
            meta.setDisplayName(ChatUtils.translate(name));
        meta.setUnbreakable(unbreakable);

        if(lore.size() > 0) {
            meta.setLore(Arrays.asList(ChatUtils.translate(lore.toArray(new String[lore.size()]))));
        }

        ConfigurationSection section = config.getConfigurationSection(path + "." + "Enchantments");

        if(section != null && section.getKeys(false).size() > 0) {
            for(String str : section.getKeys(false)) {
                String enchant = config.getString(path + ".Enchantments." + str + ".type");
                int level = config.getInt(path + ".Enchantments." + str + ".level");

                Enchantment e = Enchantment.getByName(enchant);
                meta.addEnchant(e, level, true);
            }
        }

        if(item.getType() == Material.POTION || item.getType() == Material.SPLASH_POTION) {
            ConfigurationSection section2 = config.getConfigurationSection(path + "." + "Effects");

            if(section2 != null && section2.getKeys(false).size() > 0) {
                for(String str : section2.getKeys(false)) {
                    String type = config.getString(path + ".Effects." + str + ".type");
                    int duration = config.getInt(path + ".Effects." + str + ".duration");
                    int amplifier = config.getInt(path + ".Effects." + str + ".amplifier");

                    PotionEffectType pet = PotionEffectType.getByName(type);
                    ((PotionMeta) meta).addCustomEffect(new PotionEffect(pet, duration, amplifier), true);
                }
            }
        }

        item.setItemMeta(meta);

        //TODO: Add FireworkEffect to Firework for Explosive Firework in Bomber Kit
        if(item.getType() == Material.FIREWORK_ROCKET) {
            boolean special = config.getBoolean(path + "." + "Special");

            if(special) {
                FireworkMeta fm = (FireworkMeta) meta;

                FireworkEffect effect = FireworkEffect.builder().withColor(Color.RED).build();
                fm.addEffect(effect);
                item.setItemMeta(fm);
            }
        }
        return item;
    }
}
