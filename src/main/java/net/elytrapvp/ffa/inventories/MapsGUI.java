package net.elytrapvp.ffa.inventories;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.elytrapvp.elytrapvp.gui.CustomGUI;
import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.elytrapvp.items.SkullBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class MapsGUI extends CustomGUI {

    public MapsGUI(Plugin plugin) {
        super(27,"Maps");
        filler();

        ItemStack jungle = new SkullBuilder("79ca3540621c1c79c32bf42438708ff1f5f7d0af9b14a074731107edfeb691c")
                .setDisplayName("&aJungle")
                .build();

        setItem(12, jungle, (p,a) -> {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("jungle");

            p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        });

        ItemStack christmas = new SkullBuilder("2b21617d2755bc20f8f7e388f49e48582745fec16bb14c776f7118f98c55e8")
                .setDisplayName("&aChristmas")
                .build();
        setItem(14, christmas, (p,a) -> {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("winter");

            p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        });
    }

    private void filler() {
        List<Integer> slots = Arrays.asList(0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        slots.forEach(i -> setItem(i, filler));
    }
}
