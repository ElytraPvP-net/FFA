package net.elytrapvp.ffa.commands;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StatsCMD extends AbstractCommand {
    private final FFA plugin;

    /**
     * Creates the command.
     * @param plugin Instance of the plugin.
     */
    public StatsCMD(FFA plugin) {
        super("stats", "", false);
        this.plugin = plugin;
    }

    /**
     * Runs when the command is executed.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
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
                return;
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
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}