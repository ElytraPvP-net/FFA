package net.elytrapvp.ffa.enums;

public enum Event {
    CHRISTMAS,
    HALLOWEEN,
    NONE;

    private static Event currentEvent;
    public static Event getCurrentEvent() {
        return currentEvent;
    }

    public static void setCurrentEvent(Event event) {
        currentEvent = event;
    }
}