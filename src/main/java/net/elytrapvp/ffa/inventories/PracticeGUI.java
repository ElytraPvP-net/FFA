package net.elytrapvp.ffa.inventories;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.elytrapvp.elytrapvp.gui.CustomGUI;
import net.elytrapvp.elytrapvp.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class PracticeGUI extends CustomGUI {

    public PracticeGUI(Plugin plugin) {
        super(27,"Practice");
        filler();

        ItemStack jungle = new ItemBuilder(Material.IRON_SWORD)
                .setDisplayName("&aPractice")
                .addLore("&7Duel others in a variety of kits!")
                .addLore("&7Features both ranked and ranked gameplay")
                .addLore(" ")
                .addLore("&aSupports 1.7 and later")
                .build();

        setItem(13, jungle, (p,a) -> {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("practice");

            p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        });
    }

    private void filler() {
        List<Integer> slots = Arrays.asList(0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        slots.forEach(i -> setItem(i, filler));
    }
}