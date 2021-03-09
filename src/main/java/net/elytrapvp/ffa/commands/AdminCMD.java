package net.elytrapvp.ffa.commands;

import net.elytrapvp.ffa.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class AdminCMD implements CommandExecutor {
    private Disable disable;
    private Enable enable;
    private SetPosition setPosition;
    private SetSpawn setSpawn;
    private Coins coins;

    public AdminCMD() {
        this.disable = new Disable();
        this.enable = new Enable();
        this.setPosition = new SetPosition();
        this.setSpawn = new SetSpawn();
        this.coins = new Coins();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if no arguments.
        if(args.length == 0) {
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "disable" -> disable.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
            case "enable" -> enable.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
            case "setposition", "setpos" -> setPosition.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
            case "setspawn" -> setSpawn.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
            case "addcoins", "setcoins", "removecoins" -> coins.onCommand(sender, cmd, label, args);
        }
        return true;
    }
}