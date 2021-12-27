package net.elytrapvp.ffa.inventories;

import net.elytrapvp.elytrapvp.gui.CustomGUI;
import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.ffa.enums.TagType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CosmeticsGUI extends CustomGUI {
    public CosmeticsGUI() {
        super(27,"Cosmetics");

        filler();
        setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("&aHats").build(), (p,a) -> new HatsGUI().open(p));
        setItem(12, new ItemBuilder(Material.OAK_SIGN).setDisplayName("&aKill Messages").build(), (p,a) -> new KillMessagesGUI(p, 1).open(p));
        setItem(14, new ItemBuilder(Material.NAME_TAG).setDisplayName("&aTags").build(), (p,a) -> new TagsGUI(p, 1, TagType.NONE).open(p));
        setItem(16, new ItemBuilder(Material.ARROW).setDisplayName("&aArrow Trails").build(), (p,a) -> new ArrowTrailsGUI(p, 1).open(p));
    }

    private void filler() {
        List<Integer> slots = Arrays.asList(0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        slots.forEach(i -> setItem(i, filler));
    }
}