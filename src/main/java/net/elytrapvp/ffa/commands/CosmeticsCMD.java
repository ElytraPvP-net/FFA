package net.elytrapvp.ffa.commands;

import net.elytrapvp.ffa.inventories.CosmeticsGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class runs the /cosmetics command.
 * This command opens the cosmetics gui.
 */
public class CosmeticsCMD extends AbstractCommand {

    /**
     * Creates the command.
     */
    public CosmeticsCMD() {
        super("cosmetics", "", false);
    }

    /**
     * Runs when the command is executed.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        new CosmeticsGUI().open(player);
    }
}
