package net.elytrapvp.ffa.commands;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.cosmetics.Hat;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import net.elytrapvp.ffa.utilities.item.SkullBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * This class runs the /spawn command.
 * This command teleports the player to the arena spawn.
 */
public class SpawnCMD extends AbstractCommand {
    private final FFA plugin;

    /**
     * Creates the command.
     * @param plugin Instance of the plugin.
     */
    public SpawnCMD(FFA plugin) {
        super("spawn", "", false);
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
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayers().get(player.getUniqueId());

        // Run if player is not in arena.
        if(customPlayer.getStatus() != Status.ARENA) {
            player.teleport(plugin.getArenaManager().getSelectedArena().getSpawn());
            if(customPlayer.getStatus() != Status.OTHER) {
                if(customPlayer.getHat() != 0) {
                    player.getInventory().setHelmet(Hat.getHats().get(customPlayer.getHat()).getHat());
                }
            }
            return;
        }

        ChatUtils.chat(player, "&a&lTeleport &8» &aTeleporting in &f5 &aseconds...");
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncDelayedTask(FFA.getPlugin(), () -> {
            customPlayer.setStatus(Status.OTHER);

            player.closeInventory();
            player.teleport(plugin.getArenaManager().getSelectedArena().getSpawn());

            player.setMaxHealth(20);
            player.setHealth(20);

            player.getInventory().clear();
            player.getInventory().setItem(1, new ItemBuilder(Material.EMERALD).setDisplayName("&aCosmetics").build());
            player.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR).setDisplayName("&aKit Selector").build());
            player.getInventory().setItem(8, new SkullBuilder(player).setDisplayName("&aStats").build());

            player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
            customPlayer.setStatus(Status.LOBBY);

            // Add a player's hat.
            if(customPlayer.getHat() != 0) {
                player.getInventory().setHelmet(Hat.getHats().get(customPlayer.getHat()).getHat());
            }
        }, 100);
    }
}
