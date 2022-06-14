package net.elytrapvp.ffa.objects;

import java.util.HashMap;
import java.util.Map;

public class Achievement {
    public static final Map<Integer, Achievement> achievements = new HashMap<>();

    private final String name;
    private final String description;
    private final int coinReward;
    private final int expReward;
    private final int id;

    public Achievement(String name, String description, int id, int coinReward, int expReward) {
        this.name = name;
        this.description = description;
        this.coinReward = coinReward;
        this.expReward = expReward;
        this.id = id;
    }

    public int getCoinReward() {
        return coinReward;
    }

    public String getDescription() {
        return description;
    }

    public int getExpReward() {
        return expReward;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}