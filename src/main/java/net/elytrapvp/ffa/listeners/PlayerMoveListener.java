package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.events.PlayerDrownEvent;
import net.elytrapvp.ffa.events.PlayerEscapeEvent;
import net.elytrapvp.ffa.events.PlayerLandEvent;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.kits.Kit;
import net.elytrapvp.ffa.objects.Spectator;
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

public class PlayerMoveListener implements Listener {
    private final FFA plugin;

    public PlayerMoveListener(FFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        // Exit if not enabled.
        if(!plugin.getSettingsManager().getConfig().getBoolean("Enabled")) {
            return;
        }

        Player player = event.getPlayer();

        // Exit if player is dead;
        if(player.isDead()) {
            return;
        }

        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(player.getUniqueId());

        switch (cp.getStatus()) {
            case SPECTATOR -> {
                spectatorMove(player);
                return;
            }
            case LOBBY ->  {
                lobbyMove(player, cp);
                return;
            }
            case OTHER -> {
                return;
            }
        }

        // Check if the player tries to escape.
        if(!plugin.getArenaManager().getSelectedArena().hasPlayer(player) && cp.getStatus() == Status.ARENA) {
            Bukkit.getPluginManager().callEvent(new PlayerEscapeEvent(player));
        }

        // Exit if player is gliding.
        if(player.isGliding()) {
            return;
        }

        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        Block block2 = player.getLocation().getBlock();

        // Calls PlayerDrownEvent if player is touching the water.
        if(block.getType() == Material.WATER || block2.getType() == Material.WATER) {
            if(block.getBlockData() instanceof Levelled) {
                Levelled data = (Levelled) block.getBlockData();

                if(data.getLevel() > 8) {
                    return;
                }
            }
            Bukkit.getPluginManager().callEvent(new PlayerDrownEvent(player));
        }

        // Calls PlayerLandEvent is the player touches the ground.
        if(plugin.getArenaManager().getSelectedArena().getDeathBlocks().contains(block.getType())) {
            Bukkit.getPluginManager().callEvent(new PlayerLandEvent(player));
        }
    }

    private void spectatorMove(Player player) {
        if(!plugin.getArenaManager().getSelectedArena().hasPlayer(player)) {
            Spectator.remove(player);
        }
    }

    private void lobbyMove(Player player, CustomPlayer customPlayer) {
        // Exit if kit is not selected.
        if(plugin.getArenaManager().getSelectedArena().hasPlayer(player)) {
            switch (customPlayer.getKit()) {
                case -1 -> Spectator.add(player);
                case 0 -> {
                    player.teleport(plugin.getArenaManager().getSelectedArena().getSpawn());
                    ChatUtils.chat(player, "&c&lError &8» &cYou have not selected a kit yet.");
                }
                default -> {
                    customPlayer.setStatus(Status.ARENA);
                    player.getInventory().clear();
                    Kit k = Kit.getKits().get(customPlayer.getKit() - 1);
                    k.giveKit(player);
                    customPlayer.setDrops(customPlayer.getDrops() + 1);

                    Bukkit.getScheduler().runTaskLater(FFA.getPlugin(), () -> player.setGliding(true), 20);
                }
            }
        }
    }
}