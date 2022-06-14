package net.elytrapvp.ffa.commands;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.cosmetics.Hat;
import net.elytrapvp.ffa.utilities.LocationUtils;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import net.elytrapvp.ffa.utilities.item.SkullBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class SpawnCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Exit if not a player
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&c&lError &8» &cOnly players can use that command.");
            return true;
        }

        Player p = (Player) sender;
        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        // Run if player is not in arena.
        if(cp.getStatus() != Status.ARENA) {
            p.teleport(LocationUtils.getSpawn());
            if(cp.getStatus() != Status.OTHER) {
                if(cp.getHat() != 0) {
                    p.getInventory().setHelmet(Hat.getHats().get(cp.getHat()).getHat());
                }
            }
            return true;
        }

        ChatUtils.chat(p, "&a&lTeleport &8» &aTeleporting in &f5 &aseconds...");
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncDelayedTask(FFA.getPlugin(), () -> {
            cp.setStatus(Status.OTHER);

            p.closeInventory();
            p.teleport(LocationUtils.getSpawn());

            p.setMaxHealth(20);
            p.setHealth(20);

            p.getInventory().clear();
            p.getInventory().setItem(1, new ItemBuilder(Material.EMERALD).setDisplayName("&aCosmetics").build());
            p.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR).setDisplayName("&aKit Selector").build());
            p.getInventory().setItem(8, new SkullBuilder(p).setDisplayName("&aStats").build());

            p.getActivePotionEffects().forEach(effect -> p.removePotionEffect(effect.getType()));
            cp.setStatus(Status.LOBBY);

            // Add a player's hat.
            if(cp.getHat() != 0) {
                p.getInventory().setHelmet(Hat.getHats().get(cp.getHat()).getHat());
            }
        }, 100);

        return true;
    }
}
