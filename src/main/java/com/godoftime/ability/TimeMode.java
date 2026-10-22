package com.godoftime.ability;

import net.minecraft.ChatFormatting;

/**
 * The three stances of the God of Time.
 * YELLOW = offense / Astral Sword techniques
 * GREEN  = support / restoration / growth
 * BLUE   = time & space control
 */
public enum TimeMode {
    YELLOW(0, "Yellow Hour", ChatFormatting.YELLOW),
    GREEN(1, "Green Hour", ChatFormatting.GREEN),
    BLUE(2, "Blue Hour", ChatFormatting.AQUA);

    private final int id;
    private final String displayName;
    private final ChatFormatting color;

    TimeMode(int id, String displayName, ChatFormatting color) {
        this.id = id;
        this.displayName = displayName;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ChatFormatting getColor() {
        return color;
    }

    public TimeMode next() {
        return values()[(this.id + 1) % values().length];
    }

    public static TimeMode byId(int id) {
        for (TimeMode m : values()) {
            if (m.id == id) return m;
        }
        return YELLOW;
    }
}
