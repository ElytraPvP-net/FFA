package net.elytrapvp.ffa.inventories;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.utilities.gui.CustomGUI;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LeaderboardGUI extends CustomGUI {
    private final FFA plugin;

    public LeaderboardGUI(FFA plugin) {
        super(45, "Leaderboard");
        this.plugin = plugin;

        filler();

        ItemBuilder kills = new ItemBuilder(Material.BOW).setDisplayName("&a&lKills");
        addLore(kills, "kills");
        setItem(19, kills.build());

        ItemBuilder deaths = new ItemBuilder(Material.SKELETON_SKULL).setDisplayName("&a&lDeaths");
        addLore(deaths, "deaths");
        setItem(21, deaths.build());

        ItemBuilder coins = new ItemBuilder(Material.GOLD_INGOT).setDisplayName("&a&lCoins");
        addLore(coins, "coins");
        setItem(23, coins.build());

        ItemBuilder killStreak = new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("&a&lKill Streak");
        addLore(killStreak, "highestKillStreak");
        setItem(25, killStreak.build());
    }

    private void filler() {
        List<Integer> slots = Arrays.asList(0,1,2,3,4,5,6,7,8,36,37,38,39,40,41,42,43,44);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("").build();
        slots.forEach(i -> setItem(i, filler));
    }

    private void addLore(ItemBuilder builder, String type) {
        Map<String, Integer> leaderboard = plugin.getLeaderboardManager().getLeaderboard(type);

        int i = 1;
        for(String player : leaderboard.keySet()) {
            builder.addLore("&a#" + i + ". &f" + player + "&a: " + leaderboard.get(player));
            i++;
        }
    }
}