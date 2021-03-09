package net.elytrapvp.ffa.events;

import net.elytrapvp.ffa.enums.ParkourLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ParkourCompleteEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private Player player;
    private ParkourLevel level;

    public ParkourCompleteEvent(Player player, ParkourLevel level) {
        this.player = player;
        this.level = level;
    }

    public ParkourLevel getLevel() {
        return level;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}