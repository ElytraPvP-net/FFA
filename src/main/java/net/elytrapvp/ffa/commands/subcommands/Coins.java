package net.elytrapvp.ffa.commands.subcommands;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Coins implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Exit if player never joined.
        OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
        int i = Integer.parseInt(args[2]);

        if(!CustomPlayer.exists(p.getUniqueId())) {
            return true;
        }

        CustomPlayer ep = new CustomPlayer(p.getUniqueId());

        switch (args[0]) {
            case "addcoins" -> {
                if (CustomPlayer.getCustomPlayers().containsKey(p.getUniqueId())) {
                    ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
                    //ChatUtils.chat(p.getPlayer(), "&2&lCoins &8- &aYou have received &f" + i + " &acoins.");
                } else {
                    ep = new CustomPlayer(p.getUniqueId());
                }
                ep.addCoins(i);
                ep.setLifetimeCoins(ep.getLifetimeCoins() + i);
                ChatUtils.chat(sender, "&2&lElytraPvP &8- &aGave &f" + p.getName() + " " + i + " &acoins.");
            }
            case "removecoins" -> {
                if (CustomPlayer.getCustomPlayers().containsKey(p.getUniqueId())) {
                    ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
                    //ChatUtils.chat(p.getPlayer(), "&2&lCoins &8- &aYou have received &f" + i + " &acoins.");
                } else {
                    ep = new CustomPlayer(p.getUniqueId());
                }
                ep.removeCoins(i);
                ChatUtils.chat(sender, "&2&lElytraPvP &8- &aRemoved &f" + i + " &acoins from &f" + p.getName() + "&a.");
            }
            case "setcoins" -> {
                if (CustomPlayer.getCustomPlayers().containsKey(p.getUniqueId())) {
                    ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
                } else {
                    ep = new CustomPlayer(p.getUniqueId());
                }
                ep.setCoins(i);
                ChatUtils.chat(sender, "&2&lElytraPvP &8- &aSet &f" + p.getName() + "&a's coins to &f" + i + "&a.");
            }
        }

        return true;
    }
}
