package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.events.PlayerDrownEvent;
import net.elytrapvp.ffa.events.PlayerEscapeEvent;
import net.elytrapvp.ffa.events.PlayerLandEvent;
import net.elytrapvp.ffa.managers.SettingsManager;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.kits.Kit;
import net.elytrapvp.ffa.objects.Spectator;
import net.elytrapvp.ffa.utilities.LocationUtils;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {
    private final FFA plugin;

    public PlayerMove(FFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        // Exit if not enabled.
        if(!plugin.getSettingsManager().getConfig().getBoolean("Enabled")) {
            return;
        }

        Player p = e.getPlayer();

        // Exit if player is dead;
        if(p.isDead()) {
            return;
        }

        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        switch (cp.getStatus()) {
            case SPECTATOR -> {
                spectatorMove(p);
                return;
            }
            case LOBBY ->  {
                lobbyMove(p, cp);
                return;
            }
            case OTHER -> {
                return;
            }
        }

        /*
        // Check if player tries to escape.
        if(!LocationUtils.inArena(p) && cp.getStatus() == Status.ARENA) {
            Bukkit.getPluginManager().callEvent(new PlayerEscapeEvent(p));
        }
         */

        // Check if the player tries to escape.
        if(!plugin.getArenaManager().getSelectedArena().hasPlayer(p) && cp.getStatus() == Status.ARENA) {
            Bukkit.getPluginManager().callEvent(new PlayerEscapeEvent(p));
        }

        // Exit if player is gliding.
        if(p.isGliding()) {
            return;
        }

        Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
        Block block2 = p.getLocation().getBlock();

        // Calls PlayerDrownEvent if player is touching the water.
        if(block.getType() == Material.WATER || block2.getType() == Material.WATER) {
            if(block.getBlockData() instanceof Levelled) {
                Levelled data = (Levelled) block.getBlockData();

                if(data.getLevel() > 8) {
                    return;
                }
            }
            Bukkit.getPluginManager().callEvent(new PlayerDrownEvent(p));
        }

        // Calls PlayerLandEvent is the player touches the ground.
        /*
        if(plugin.getSettingsManager().getConfig().getStringList("DeathBlocks").contains(block.getType().toString())) {
            Bukkit.getPluginManager().callEvent(new PlayerLandEvent(p));
        }

         */
        if(plugin.getArenaManager().getSelectedArena().getDeathBlocks().contains(block.getType())) {
            Bukkit.getPluginManager().callEvent(new PlayerLandEvent(p));
        }
    }

    private void spectatorMove(Player p) {
        /*
        if(!LocationUtils.inArena(p)) {
            Spectator.remove(p);
        }

         */
        if(!plugin.getArenaManager().getSelectedArena().hasPlayer(p)) {
            Spectator.remove(p);
        }
    }

    private void lobbyMove(Player p, CustomPlayer cp) {
        // Exit if kit is not selected.

        /*
        if(LocationUtils.inArena(p)) {
            switch (cp.getKit()) {
                case -1 -> {
                    Spectator.add(p);
                }
                case 0 -> {
                    p.teleport(LocationUtils.getSpawn());
                    ChatUtils.chat(p, "&c&lError &8» &cYou have not selected a kit yet.");
                }
                default -> {
                    cp.setStatus(Status.ARENA);
                    p.getInventory().clear();
                    Kit k = Kit.getKits().get(cp.getKit() - 1);
                    k.giveKit(p);
                    cp.setDrops(cp.getDrops() + 1);

                    Bukkit.getScheduler().runTaskLater(FFA.getPlugin(), () -> {
                        p.setGliding(true);
                    }, 20);
                }
            }
        }
         */

        if(plugin.getArenaManager().getSelectedArena().hasPlayer(p)) {
            switch (cp.getKit()) {
                case -1 -> {
                    Spectator.add(p);
                }
                case 0 -> {
                    p.teleport(LocationUtils.getSpawn());
                    ChatUtils.chat(p, "&c&lError &8» &cYou have not selected a kit yet.");
                }
                default -> {
                    cp.setStatus(Status.ARENA);
                    p.getInventory().clear();
                    Kit k = Kit.getKits().get(cp.getKit() - 1);
                    k.giveKit(p);
                    cp.setDrops(cp.getDrops() + 1);

                    Bukkit.getScheduler().runTaskLater(FFA.getPlugin(), () -> {
                        p.setGliding(true);
                    }, 20);
                }
            }
        }
    }
}