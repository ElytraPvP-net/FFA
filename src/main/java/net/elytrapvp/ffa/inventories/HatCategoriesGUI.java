package net.elytrapvp.ffa.inventories;

import net.elytrapvp.elytrapvp.gui.CustomGUI;
import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.elytrapvp.items.SkullBuilder;
import net.elytrapvp.ffa.enums.HatType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class HatCategoriesGUI extends CustomGUI {
    public HatCategoriesGUI() {
        super(27, "Hats");
        filler();

        ItemStack animals = new SkullBuilder("5d6c6eda942f7f5f71c3161c7306f4aed307d82895f9d2b07ab4525718edc5").setDisplayName("&aAnimals").build();
        setItem(10, animals, (p,a) -> new HatsGUI(p, 1, HatType.ANIMAL).open(p));

        ItemStack blocks = new SkullBuilder("219e36a87baf0ac76314352f59a7f63bdb3f4c86bd9bba6927772c01d4d1").setDisplayName("&aBlocks").build();
        setItem(12, blocks, (p,a) -> new HatsGUI(p, 1, HatType.BLOCK).open(p));

        ItemStack food = new SkullBuilder("36c01bffecfdab6d3c0f1a7c6df6aa1936f2aa7a51b54a4d323e1cacbc539").setDisplayName("&aFood").build();
        setItem(14, food, (p,a) -> new HatsGUI(p, 1, HatType.FOOD).open(p));

        ItemStack christmas = new SkullBuilder("14e424b1676feec3a3f8ebade9e7d6a6f71f7756a869f36f7df0fc182d436e").setDisplayName("&aChristmas").build();
        setItem(16, christmas, (p,a) -> new HatsGUI(p, 1, HatType.CHRISTMAS).open(p));
    }

    private void filler() {
        List<Integer> slots = Arrays.asList(0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        slots.forEach(i -> setItem(i, filler));
    }
}
