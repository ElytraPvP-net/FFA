package net.elytrapvp.ffa.inventories;

import net.elytrapvp.ffa.game.kits.Kit;
import net.elytrapvp.ffa.utilities.gui.CustomGUI;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import net.elytrapvp.ffa.utilities.item.SkullBuilder;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the menu where a kit is previewed.
 */
public class PreviewKitGUI extends CustomGUI {
    private Kit k;

    /**
     * Creates a GUI to preview kits.
     * @param k Kit to preview.
     */
    public PreviewKitGUI(Kit k) {
        super(54, "Preview");

        this.k = k;
        fillers();

        for(int i : k.getItems().keySet()) {
            setItem(slotToGUI(i), k.getItems().get(i));
        }

        ItemStack back = new SkullBuilder("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6")
                .setDisplayName("&cBack")
                .build();

        setItem(0, back, (player, action) -> new KitsGUI(player).open(player));
        setItem(slotToGUI(38), k.getElytra());
        setItem(25, k.getArrow());
        setItem(8, getPotionEffects());
        setItem(7, new ItemBuilder(Material.APPLE).setDisplayName("&cHealth: " + k.getHealth()).build());
    }

    /**
     * Fills the gui with empty items. Used for details.
     */
    private void fillers() {
        List<Integer> slots = Arrays.asList(1,2,3,4,5,6,9,10,11,12,13,14,15,16,17,18,20,22,23,24,26,27,29,30,31,32,33,34,35,36,38,44,45,46,47,48,49,50,51,52,53);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        slots.forEach(i -> setItem(i, filler));
    }

    /**
     * Convert kit item slot to GUI slot.
     * @param slot Kit item slot.
     * @return GUI slot.
     */
    private int slotToGUI(int slot) {
        switch (slot) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return slot + 39;

            case 17:
                return 25;

            case 38:
                return 19;

            case 40:
                return 21;

            default:
                return slot;
        }
    }

    private ItemStack getPotionEffects() {
        ItemBuilder builder = new ItemBuilder(Material.POTION)
                .setDisplayName("&aPotion Effects")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_POTION_EFFECTS);

        for(PotionEffect effect : k.getEffects()) {
            String name = WordUtils.capitalize(effect.getType().getName().replace("_", " ").toLowerCase());
            builder.addLore("&7" + name);
        }

        return builder.build();
    }
}