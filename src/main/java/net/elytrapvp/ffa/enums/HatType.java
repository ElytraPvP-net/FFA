package net.elytrapvp.ffa.enums;

import net.elytrapvp.ffa.objects.Hat;

import java.util.ArrayList;
import java.util.List;

public enum HatType {
    ANIMAL,
    BLOCK,
    CHRISTMAS,
    FOOD,
    PEOPLE;

    private final List<Hat> hats = new ArrayList<>();

    public void addHat(int id, Hat hat) {
        hats.add(hat);
    }

    public List<Hat> getHats() {
        return hats;
    }
}
