package net.elytrapvp.ffa.commands;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BountyCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if not a player.
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&2&lError &8- &cYou must be a player to use that command.");
            return true;
        }

        Player p = (Player) sender;
        CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        // Exit if no args
        if(args.length == 0) {
            ChatUtils.chat(p, "&2&lBounty &8- &aThere is currently a bounty of &f" + ep.getBounty() + " &acoins for you.");
            return true;
        }

        // Exit if not enough args
        if(args.length < 3 || args[1].equalsIgnoreCase("add")) {
            ChatUtils.chat(p, "&2&lUsage &8- &c/bounty add [player] [amount]");
            return true;
        }

        Player t = Bukkit.getPlayer(args[1]);

        int coins = 0;
        try {
            coins = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e) {
            ChatUtils.chat(p, "&2&lUsage &8- &c/bounty add [player] [amount]");
            return true;
        }

        // Exit if player is not online
        if(t == null) {
            ChatUtils.chat(p, "&2&lError &8- &cThat player is not online.");
            return true;
        }

        // Exit if invalid coin amount.
        if(coins < 1) {
            ChatUtils.chat(p, "&2&lError &8- &cMust be at least 1 coin.");
            return true;
        }

        // Exit if not enough coins.
        if(ep.getCoins() < coins) {
            ChatUtils.chat(p, "&2&lError &8- &cYou do not have enough coins.");
            return true;
        }

        CustomPlayer et = CustomPlayer.getCustomPlayers().get(t.getUniqueId());
        et.addBounty(coins);
        ep.removeCoins(coins);

        Bukkit.broadcastMessage(ChatUtils.translate("&2&lBounty &8- &f" + p.getName() + " &ahas placed a bounty of &f" + coins + " &acoins for &f" + t.getName() + "&a."));
        return true;
    }
}
