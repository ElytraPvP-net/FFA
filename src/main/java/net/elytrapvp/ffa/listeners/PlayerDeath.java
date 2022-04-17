package net.elytrapvp.ffa.listeners;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.enums.DeathType;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.KillMessage;
import net.elytrapvp.ffa.utils.LocationUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        p.setBedSpawnLocation(LocationUtils.getSpawn());

        cp.addDeaths(1);
        cp.setKillStreak(0);

        if(p.getKiller() != null) {
            Player k = p.getKiller();

            // Check if player is their own killer.
            if(p.getUniqueId().equals(k.getUniqueId())) {
                e.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + p.getName() + " &aattacked themselves."));
            }
            else {
                CustomPlayer ek = CustomPlayer.getCustomPlayers().get(k.getUniqueId());

                if(ek.getKillMessage() != 0) {
                    KillMessage km = KillMessage.getKillMessages().get(ek.getKillMessage());
                    String message = km.getKillMessage()
                            .replace("%player%", p.getName())
                            .replace("%killer%", k.getName());

                    e.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &r" + message));
                }
                else {
                    e.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + p.getName() + " &awas rekt by &f" + k.getName() + "&a."));
                }

                ek.addKills(1);
                ek.addKillStreak(1);

                // Set the killer's highest kill streak statistic if kill streak is highest.
                if(ek.getKillStreak() > ek.getHighestKillStreak()) {
                    ek.setHighestKillStreak(ek.getKillStreak());
                }

                int coins = 5 + (ek.getKillStreak() / 3) + cp.getBounty();
                k.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatUtils.translate("&6+ " + coins + " Coins")));
                ek.addCoins(coins);
                ek.setLifetimeCoins(ek.getLifetimeCoins() + coins);

                int ks = ek.getKillStreak();

                Bukkit.getScheduler().runTaskLater(FFA.getPlugin(), () -> {
                    if(cp.getBounty() > 0) {
                        ChatUtils.chat(k, "&a&lBounty &8» &aYou have killed a wanted player and collected &f" + cp.getBounty() + "&acoins.");
                        cp.setBounty(0);
                    }

                    if(ks % 3 == 0 && ks != 0) {
                        Bukkit.broadcastMessage(ChatUtils.translate("&a&lKill Streak &8» &f" + k.getName() + " &ais on a kill streak of &f" + ks + "&a!"));
                    }

                    if(ks % 5 == 0) {
                        Bukkit.broadcastMessage(ChatUtils.translate("&a&lBounty &8» &aA bounty of &f10 &acoin has been placed on &f" + k.getName() + " &afor high kill streak."));
                        ek.addBounty(10);
                    }
                }, 1);

                //LevelsPlayer lp = LevelsAPI.getLevelsPlayers().get(k.getUniqueId());
                //lp.addExperience(15);
            }

            return;
        }

        if(DeathType.list.containsKey(p.getUniqueId())) {
            switch(DeathType.list.get(p.getUniqueId())) {
                case ESCAPE:
                    e.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + p.getName() + " &atried to escape."));
                    break;

                case GROUND:
                    e.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + p.getName() + " &atried to land."));
                    break;

                case WATER:
                    e.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + p.getName() + " &adrowned."));
                    break;
            }
        }
        else {
            e.setDeathMessage(ChatUtils.translate("&a&lDeath &8» &f" + p.getName() + " &adied."));
        }
    }
}
