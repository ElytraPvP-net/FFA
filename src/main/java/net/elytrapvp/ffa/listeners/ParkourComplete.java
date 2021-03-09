package net.elytrapvp.ffa.listeners;

import net.elytrapvp.ffa.events.ParkourCompleteEvent;
import net.elytrapvp.levels.api.LevelsAPI;
import net.elytrapvp.levels.api.LevelsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ParkourComplete implements Listener {
    @EventHandler
    public void onParkourComplete(ParkourCompleteEvent e) {
        //Bukkit.broadcastMessage("Completed Level: " + e.getLevel().toString());
        LevelsPlayer p = LevelsAPI.getLevelsPlayers().get(e.getPlayer().getUniqueId());
        switch (e.getLevel()) {
            case GREEN -> p.addExperience(10);
            case YELLOW -> p.addExperience(15);
            case RED -> p.addExperience(20);
        }
    }
}
