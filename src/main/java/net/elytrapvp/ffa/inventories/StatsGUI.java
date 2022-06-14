package net.elytrapvp.ffa.inventories;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.utilities.gui.CustomGUI;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class StatsGUI extends CustomGUI {
    public StatsGUI(Player player) {
        super(54, player.getName() + "'s Stats");
        fillers();

        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(player.getUniqueId());
        setItems(cp);
    }

    public StatsGUI(OfflinePlayer player) {
        super(54, player.getName() + "'s Stats");
        fillers();

        CustomPlayer cp = new CustomPlayer(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(FFA.getPlugin(), () -> setItems(cp), 5);
    }

    private void setItems(CustomPlayer cp) {
        double kdr = ((double) cp.getKills()) / ((double) cp.getDeaths());
        if(cp.getDeaths() == 0) kdr = cp.getKills();

        ItemBuilder sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .setDisplayName("&aKills: &f" + cp.getKills())
                .addLore("&aDeaths: &f" + cp.getDeaths())
                .addLore("&aKDR: &f" + round(kdr, 4))
                .addLore(" ")
                .addLore("&aDamage Dealt: &f" + round(cp.getDamageDealt(), 2))
                .addLore("&aDamage Taken: &f" + round(cp.getDamageTaken(), 2))
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        setItem(20, sword.build());

        double shotRatio = ((double) cp.getArrowsHit()) / ((double) cp.getArrowsShot());
        if(cp.getArrowsShot() == 0) shotRatio = 0;

        ItemBuilder bow = new ItemBuilder(Material.BOW)
                .setDisplayName("&aArrows Shot: &f" + cp.getArrowsShot())
                .addLore("&aArrows Hit: &f" + cp.getArrowsHit())
                .addLore("&aRatio: &f" + round(shotRatio, 4));
        setItem(22, bow.build());

        ItemBuilder gold = new ItemBuilder(Material.GOLD_INGOT)
                .setDisplayName("&aCoins: &f" + cp.getCoins())
                .addLore("&aLifetime Coins: &f" + cp.getLifetimeCoins())
                .addLore("&aBounty: &f" + cp.getBounty());
        setItem(24, gold.build());

        ItemBuilder book = new ItemBuilder(Material.BOOK)
                .setDisplayName("&aDrops: &f" + cp.getDrops())
                .addLore("&aWindows Broken: &f" + cp.getWindowsBroken())
                .addLore("&aHighest Kill Streak: &f" + cp.getHighestKillStreak());
        setItem(30, book.build());
    }

    private void fillers() {
        List<Integer> slots = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,21,23,25,26,27,28,29,31,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        slots.forEach(i -> setItem(i, filler));
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}