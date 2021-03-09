package net.elytrapvp.ffa.objects;

import java.util.HashMap;
import java.util.Map;

public class Achievement {
    public static Map<Integer, Achievement> achievements = new HashMap<>();

    private String name;
    private String description;
    private int coinReward;
    private int expReward;
    private int id;

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