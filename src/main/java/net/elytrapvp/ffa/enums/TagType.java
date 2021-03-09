package net.elytrapvp.ffa.enums;

import net.elytrapvp.ffa.objects.Tag;

import java.util.ArrayList;
import java.util.List;

public enum TagType {
    CHRISTMAS,
    NONE;

    private final List<Tag> tags = new ArrayList<>();

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public List<Tag> getTags() {
        return tags;
    }
}
