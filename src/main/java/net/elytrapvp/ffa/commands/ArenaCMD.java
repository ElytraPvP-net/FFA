package net.elytrapvp.ffa.commands;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.game.arenas.Arena;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * This class runs the /arena command.
 * This command manages the multi-arena system.
 */
public class ArenaCMD extends AbstractCommand {
    private final FFA plugin;

    /**
     * Creates the command.
     * @param plugin Instance of the plugin.
     */
    public ArenaCMD(FFA plugin) {
        super("arena", "", false);

        this.plugin = plugin;
    }

    /**
     * Runs when the command is executed.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            args = new String[]{"help"};
        }

        switch (args[0]) {
            default -> {
                ChatUtils.chat(sender, "&8&m+-----------------------***-----------------------+");
                ChatUtils.centeredChat(sender, "&a&lArena");
                ChatUtils.chat(sender, "  &8» &a/arena list");
                ChatUtils.chat(sender, "  &8» &a/arena set <arena>");
                ChatUtils.chat(sender, "  &8» &a/arena tp <arena>");
                ChatUtils.chat(sender, "&8&m+-----------------------***-----------------------+");
            }
            case "list" -> {
                ChatUtils.chat(sender, "&8&m+-----------------------***-----------------------+");
                ChatUtils.centeredChat(sender, "&a&lAvailable Arenas");
                for (Arena arena : plugin.getArenaManager().getArenas()) {
                    ChatUtils.chat(sender, "  &8» &a" + arena.getName());
                }
                ChatUtils.chat(sender, "&8&m+-----------------------***-----------------------+");
            }
            case "set" -> {
                if (args.length == 1) {
                    ChatUtils.chat(sender, "&c&lUsage &8» &c/arena set <arena>");
                    return;
                }

                String name = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");
                Arena arena = plugin.getArenaManager().getArena(name);

                if (arena == null) {
                    ChatUtils.chat(sender, "&c&lError &8» &cThat arena does not exist!");
                    return;
                }

                // TODO: FINISH /arena set
            }
            case "tp", "teleport" -> {
                if (args.length == 1) {
                    ChatUtils.chat(sender, "&c&lUsage &8» &c/arena tp <arena>");
                    return;
                }

                String name = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");
                Arena arena = plugin.getArenaManager().getArena(name);

                if (arena == null) {
                    ChatUtils.chat(sender, "&c&lError &8» &cThat arena does not exist!");
                    return;
                }

                Player player = (Player) sender;
                player.teleport(arena.getSpawn());
                ChatUtils.chat(sender, "&a&lArena &8» &aTeleporting to &7" + arena.getName() + "&a.");
            }
        }
    }
}
