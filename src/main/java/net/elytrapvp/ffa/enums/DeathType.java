package net.elytrapvp.ffa.enums;

import java.util.HashMap;
import java.util.UUID;

public enum DeathType {
    ESCAPE,
    WATER,
    GROUND,
    PLAYER,
    SELF,
    OTHER;

    public static HashMap<UUID, DeathType> list = new HashMap<>();
}
