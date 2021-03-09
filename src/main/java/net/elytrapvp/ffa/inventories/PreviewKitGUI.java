package net.elytrapvp.ffa.inventories;

import net.elytrapvp.elytrapvp.gui.CustomGUI;
import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.ffa.objects.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PreviewKitGUI extends CustomGUI {
    public PreviewKitGUI(Kit k) {
        super(54, "Preview");

        fillers();

        for(int i : k.getItems().keySet()) {
            setItem(slotToGUI(i), k.getItems().get(i));
        }


        setItem(49, new ItemBuilder(Material.BARRIER).setDisplayName("&cBack").build(), (player, action) -> new KitsGUI(player).open(player));
        setItem(slotToGUI(38), k.getElytra());
    }

    private void fillers() {
        List<Integer> slots = Arrays.asList(0,1,2,3,4,5,6,7,8,9,11,13,14,15,17,18,20,21,22,23,24,25,26,27,29,35,36,37,38,39,40,41,42,43,44,45,46,47,48,50,51,52,53);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        slots.forEach(i -> setItem(i, filler));
    }

    private int slotToGUI(int slot) {
        switch (slot) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return slot + 30;

            case 17:
                return 16;

            case 38:
                return 10;

            case 40:
                return 12;

            default:
                return slot;
        }
    }
}