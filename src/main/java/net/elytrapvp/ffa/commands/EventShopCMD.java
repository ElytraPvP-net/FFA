package net.elytrapvp.ffa.commands;

import net.elytrapvp.ffa.inventories.EventShopGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class runs the /eventshop command.
 * This command opens the event shop gui.
 */
public class EventShopCMD extends AbstractCommand {

    /**
     * Creates the command.
     */
    public EventShopCMD() {
        super("eventshop", "", false);
    }

    /**
     * Runs when the command is executed.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        new EventShopGUI().open(player);
    }
}