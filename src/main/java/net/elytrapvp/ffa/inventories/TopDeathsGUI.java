package net.elytrapvp.ffa.inventories;

import net.elytrapvp.elytrapvp.gui.CustomGUI;
import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.elytrapvp.items.SkullBuilder;
import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.runnables.LeaderboardUpdate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TopDeathsGUI extends CustomGUI {
    private static final int[] slots = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43};

    public TopDeathsGUI(Player player) {
        super(54, "Top Deaths");
        fillers();
        ItemStack back = new SkullBuilder("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6")
                .setDisplayName("&cBack")
                .build();
        setItem(0, back, (p, a) -> new LeaderboardGUI().open(p));

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            for(int place : LeaderboardUpdate.deaths.keySet()) {
                UUID u = LeaderboardUpdate.deaths.get(place).u;
                int num = LeaderboardUpdate.deaths.get(place).i;

                OfflinePlayer p = Bukkit.getOfflinePlayer(u);
                ItemStack item = new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("&a" + place + ". " + p.getName() + " - " + num + " Deaths").build();
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                meta.setOwningPlayer(p);
                item.setItemMeta(meta);

                setItem(slots[place - 1], item, (user,action) -> {
                    user.chat("/stats " + p.getName());
                });
            }
        });

        open(player);
    }

    private void fillers() {
        List<Integer> slots = Arrays.asList(1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("").build();
        slots.forEach(i -> setItem(i, filler));
    }
}
