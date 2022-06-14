package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.DeathType;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.cosmetics.KillMessage;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final FFA plugin;

    public PlayerDeathListener(FFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayers().get(player.getUniqueId());

        player.setBedSpawnLocation(plugin.getArenaManager().getSelectedArena().getSpawn());

        customPlayer.addDeaths(1);
        customPlayer.setKillStreak(0);

        if(player.getKiller() != null) {
            Player killer = player.getKiller();

            // Check if player is their own killer.
            if(player.getUniqueId().equals(killer.getUniqueId())) {
                event.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + player.getName() + " &aattacked themselves."));
            }
            else {
                CustomPlayer customKiller = CustomPlayer.getCustomPlayers().get(killer.getUniqueId());

                if(customKiller.getKillMessage() != 0) {
                    KillMessage km = KillMessage.getKillMessages().get(customKiller.getKillMessage());
                    String message = km.getKillMessage()
                            .replace("%player%", player.getName())
                            .replace("%killer%", killer.getName());

                    event.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &r" + message));
                }
                else {
                    event.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + player.getName() + " &awas rekt by &f" + killer.getName() + "&a."));
                }

                customKiller.addKills(1);
                customKiller.addKillStreak(1);

                // Set the killer's highest kill streak statistic if kill streak is highest.
                if(customKiller.getKillStreak() > customKiller.getHighestKillStreak()) {
                    customKiller.setHighestKillStreak(customKiller.getKillStreak());
                }

                int coins = 5 + (customKiller.getKillStreak() / 3) + customPlayer.getBounty();
                killer.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatUtils.translate("&6+ " + coins + " Coins")));
                customKiller.addCoins(coins);
                customKiller.setLifetimeCoins(customKiller.getLifetimeCoins() + coins);

                int killStreak = customKiller.getKillStreak();

                Bukkit.getScheduler().runTaskLater(FFA.getPlugin(), () -> {
                    if(customPlayer.getBounty() > 0) {
                        ChatUtils.chat(killer, "&a&lBounty &8» &aYou have killed a wanted player and collected &f" + customPlayer.getBounty() + "&acoins.");
                        customPlayer.setBounty(0);
                    }

                    if(killStreak % 3 == 0 && killStreak != 0) {
                        Bukkit.broadcastMessage(ChatUtils.translate("&a&lKill Streak &8» &f" + killer.getName() + " &ais on a kill streak of &f" + killStreak + "&a!"));
                    }

                    if(killStreak % 5 == 0) {
                        Bukkit.broadcastMessage(ChatUtils.translate("&a&lBounty &8» &aA bounty of &f10 &acoin has been placed on &f" + killer.getName() + " &afor high kill streak."));
                        customKiller.addBounty(10);
                    }
                }, 1);
            }

            return;
        }

        if(DeathType.list.containsKey(player.getUniqueId())) {
            switch (DeathType.list.get(player.getUniqueId())) {
                case ESCAPE ->
                        event.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + player.getName() + " &atried to escape."));
                case GROUND ->
                        event.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + player.getName() + " &atried to land."));
                case WATER ->
                        event.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + player.getName() + " &adrowned."));
            }
        }
        else {
            event.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + player.getName() + " &adied."));
        }
    }
}
