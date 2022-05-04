package net.elytrapvp.ffa.inventories;

import net.elytrapvp.ffa.enums.HatType;
import net.elytrapvp.ffa.enums.TagType;
import net.elytrapvp.ffa.utilities.gui.CustomGUI;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import net.elytrapvp.ffa.utilities.item.SkullBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class EventShopGUI extends CustomGUI {
    public EventShopGUI() {
        super(27, "Event Shop");
        filler();

        ItemStack christmas = new SkullBuilder("14e424b1676feec3a3f8ebade9e7d6a6f71f7756a869f36f7df0fc182d436e").setDisplayName("&aHats").build();
        setItem(12, christmas, (p,a) -> new HatsGUI(p, 1, HatType.CHRISTMAS).open(p));

        ItemStack tags = new ItemBuilder(Material.NAME_TAG).setDisplayName("&aTags").build();
        setItem(14, tags, (p,a) -> new TagsGUI(p,1, TagType.CHRISTMAS).open(p));
    }

    private void filler() {
        List<Integer> slots = Arrays.asList(0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        slots.forEach(i -> setItem(i, filler));
    }
}
