package net.elytrapvp.ffa.events;

import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.kits.Kit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class KitUnlockEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final CustomPlayer customPlayer;
    private final Kit kit;

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

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
