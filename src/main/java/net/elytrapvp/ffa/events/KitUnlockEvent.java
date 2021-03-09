package net.elytrapvp.ffa.events;

import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Kit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KitUnlockEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private CustomPlayer customPlayer;
    private Kit kit;

    public KitUnlockEvent(CustomPlayer customPlayer, Kit kit) {
        this.customPlayer = customPlayer;
        this.kit = kit;
    }

    public CustomPlayer getCustomPlayer() {
        return customPlayer;
    }

    public Kit getKit() {
        return kit;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
