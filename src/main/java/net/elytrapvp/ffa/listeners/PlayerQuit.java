package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.enums.DeathType;
import net.elytrapvp.ffa.enums.Status;
import net.elytrapvp.ffa.objects.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerQuit implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        CustomPlayer.getCustomPlayers().remove(p.getUniqueId());
        DeathType.list.remove(p.getUniqueId());

        Status.getPlayers().remove(p.getUniqueId());

        for(PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());
    }
}
