package net.elytrapvp.ffa.commands;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.inventories.StatsGUI;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StatsCMD implements CommandExecutor {
    private final FFA plugin;

    public StatsCMD(FFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if sender is not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&c&lError &8» &cOnly players can use that command.");
            return true;
        }

        Player player = (Player) sender;
        String username = player.getName();
        CustomPlayer customPlayer;

        if(args.length == 0) {
            customPlayer = CustomPlayer.getCustomPlayers().get(player.getUniqueId());
        }
        else {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

            if(!CustomPlayer.exists(target.getUniqueId())) {
                ChatUtils.chat(player, "&c&lError &8» &cThat player has never joined.");
                return true;
            }

            if(Bukkit.getPlayer(args[0]) != null) {
                customPlayer = CustomPlayer.getCustomPlayers().get(Bukkit.getPlayer(args[0]).getUniqueId());
            }
            else {
                customPlayer = new CustomPlayer(target.getUniqueId());
            }
            username = target.getName();
        }

        final String string = username;


        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            double kdr = ((double) customPlayer.getKills()) / ((double) customPlayer.getDeaths());
            if(customPlayer.getDeaths() == 0) kdr = customPlayer.getKills();

            ChatUtils.chat(player, "&8&m+-----------------------***-----------------------+");
            ChatUtils.centeredChat(player, "&a&l" + string + "\'s Stats");
            ChatUtils.chat(player, "  &aKills &8» &7" + customPlayer.getKills());
            ChatUtils.chat(player, "  &aDeaths &8» &7" + customPlayer.getDeaths());
            ChatUtils.chat(player, "  &aKDR &8» &7" + round(kdr, 4));
            ChatUtils.chat(player, "  &aKill Streak &8» &7" + customPlayer.getKillStreak());
            ChatUtils.chat(player, "  &aBest Kill Streak &8» &7" + customPlayer.getHighestKillStreak());
            ChatUtils.chat(player, "  &aBounty &8» &7" + customPlayer.getBounty());
            ChatUtils.chat(player, "  &aCoins &8» &7" + customPlayer.getCoins());
            ChatUtils.chat(player, "&8&m+-----------------------***-----------------------+");
        }, 5);

        return true;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}