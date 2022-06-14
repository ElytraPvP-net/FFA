package net.elytrapvp.ffa.enums;

import java.util.HashMap;
import java.util.UUID;

public enum Status {
    ARENA,
    LOBBY,
    SPECTATOR,
    OTHER;

    private static HashMap<UUID, Status> players = new HashMap<>();

    public static HashMap<UUID, Status> getPlayers() {
        return players;
    }
}