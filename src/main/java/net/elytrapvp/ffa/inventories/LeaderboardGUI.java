package net.elytrapvp.ffa.inventories;

import net.elytrapvp.elytrapvp.gui.CustomGUI;
import net.elytrapvp.elytrapvp.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class LeaderboardGUI extends CustomGUI {
    public LeaderboardGUI() {
        super(27, "Leaderboard");
        filler();

        setItem(10, new ItemBuilder(Material.BOW).setDisplayName("&aKills").build(), (p,a) -> new TopKillsGUI(p));
        setItem(12, new ItemBuilder(Material.SKELETON_SKULL).setDisplayName("&aDeaths").build(), (p,a) -> new TopDeathsGUI(p));
        setItem(14, new ItemBuilder(Material.GOLD_INGOT).setDisplayName("&aCoins").build(), (p,a) -> new TopCoinsGUI(p));
        setItem(16, new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("&aKill Streak").build(), (p,a) -> new TopKillStreakGUI(p));
    }

    private void filler() {
        List<Integer> slots = Arrays.asList(0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("").build();
        slots.forEach(i -> setItem(i, filler));
    }
}
