package net.elytrapvp.ffa.commands;

import net.elytrapvp.ffa.inventories.KitsGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class runs the /kits command.
 * This command opens up the kit selector gui,
 */
public class KitsCMD extends AbstractCommand {

    /**
     * Creates the command.
     */
    public KitsCMD() {
        super("kits", "", false);
    }

    /**
     * Runs when the command is executed.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        new KitsGUI(player).open(player);
    }
}