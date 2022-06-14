package net.elytrapvp.ffa.commands;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.inventories.LeaderboardGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class runs the /leaderboard command.
 * This commands opens up the leaderboard gui,
 */
public class LeaderboardCMD extends AbstractCommand {
    private final FFA plugin;

    /**
     * Creates the command.
     * @param plugin Instance of the plugin.
     */
    public LeaderboardCMD(FFA plugin) {
        super("leaderboard", "", false);
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
        new LeaderboardGUI(plugin).open(player);
    }
}