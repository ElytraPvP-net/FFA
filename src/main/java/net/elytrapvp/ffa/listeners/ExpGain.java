package net.elytrapvp.ffa.listeners;

import net.elytrapvp.levels.api.LevelsAPI;
import net.elytrapvp.levels.api.LevelsPlayer;
import net.elytrapvp.levels.api.events.ExpGainEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ExpGain implements Listener {
    @EventHandler
    public void onExpGain(ExpGainEvent e) {
        Player p = e.getPlayer();
        LevelsPlayer lp = LevelsAPI.getLevelsPlayers().get(p.getUniqueId());
        int level = lp.getLevel();
        float exp = lp.getExperience();
        float maxExp = LevelsAPI.getRequiredExp(level);

        p.setLevel(level);
        p.setExp(exp / maxExp);
    }
}
