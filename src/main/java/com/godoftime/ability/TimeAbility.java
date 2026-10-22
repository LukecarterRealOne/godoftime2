package com.godoftime.ability;

import java.util.ArrayList;
import java.util.List;

/**
 * All 50 abilities of the God of Time, 16-17 per mode.
 * cooldownTicks: 20 ticks = 1 second.
 */
public enum TimeAbility {

    // ===================== YELLOW — OFFENSE (17) =====================
    ASTRAL_SLASH(0, TimeMode.YELLOW, "Astral Slash", "Unleash a wave of astral energy in front of you.", 60),
    TIME_REND(1, TimeMode.YELLOW, "Time Rend", "Strike ignoring all armor for a few seconds.", 160),
    CHRONO_EXECUTE(2, TimeMode.YELLOW, "Chrono Execute", "Instantly end a foe below 15% health.", 200),
    SOLAR_FLARE(3, TimeMode.YELLOW, "Solar Flare", "Blind and burn everything nearby.", 140),
    BLADE_STORM(4, TimeMode.YELLOW, "Blade Storm", "Summon the astral blades to guard and attack for 10s.", 300),
    RAPID_STRIKES(5, TimeMode.YELLOW, "Rapid Strikes", "Greatly increase attack speed briefly.", 120),
    GRAVITY_CRUSH(6, TimeMode.YELLOW, "Gravity Crush", "Launch nearby foes up, then slam them down.", 160),
    TEMPORAL_PIERCE(7, TimeMode.YELLOW, "Temporal Pierce", "A strike that ignores invulnerability frames.", 100),
    ERASE(8, TimeMode.YELLOW, "Erase", "Delete the block or entity you are looking at.", 240),
    OVERCLOCK(9, TimeMode.YELLOW, "Overclock", "Speed and strength surge through you.", 180),
    METEOR_MARK(10, TimeMode.YELLOW, "Meteor Mark", "Mark your target; a blast erupts under them shortly after.", 160),
    JUDGEMENT_BEAM(11, TimeMode.YELLOW, "Judgement Beam", "Fire a beam of pure time-energy forward.", 200),
    BERSERKERS_HOUR(12, TimeMode.YELLOW, "Berserker's Hour", "Trade defense for devastating lifesteal and damage.", 260),
    SHATTER_TIME(13, TimeMode.YELLOW, "Shatter Time", "Freeze a single target in place.", 140),
    INFINITE_EDGE(14, TimeMode.YELLOW, "Infinite Edge", "Your next strike deals catastrophic bonus damage.", 220),
    WRATH_OF_HYPERVERSE(15, TimeMode.YELLOW, "Wrath of Hyperverse", "A nova of raw time-force erupts around you.", 400),
    GOLDEN_EXECUTION(16, TimeMode.YELLOW, "Golden Execution", "Deal damage proportional to the target's missing health.", 220),

    // ===================== GREEN — SUPPORT (17) =====================
    CHRONO_HEAL(17, TimeMode.GREEN, "Chrono Heal", "Heal yourself steadily for several seconds.", 160),
    TIME_LOOP_CHECKPOINT(18, TimeMode.GREEN, "Time Loop Checkpoint", "Save this moment in time to return to later.", 20),
    REWIND_SELF(19, TimeMode.GREEN, "Rewind Self", "Snap back to where you stood a few seconds ago.", 140),
    AGE_REVERSAL(20, TimeMode.GREEN, "Age Reversal", "Cleanse all negative effects from yourself.", 200),
    BLOOM_BURST(21, TimeMode.GREEN, "Bloom Burst", "Instantly grow crops and saplings around you.", 160),
    LIFE_THREAD(22, TimeMode.GREEN, "Life Thread", "Share a burst of healing with nearby allies.", 200),
    HASTE_FIELD(23, TimeMode.GREEN, "Haste Field", "Grant speed and haste to yourself and allies nearby.", 180),
    REGROWTH_AURA(24, TimeMode.GREEN, "Regrowth Aura", "A field of regeneration surrounds you and allies.", 220),
    SECOND_CHANCE(25, TimeMode.GREEN, "Second Chance", "Ward off the next fatal blow with a timely shield.", 480),
    SLOW_DECAY(26, TimeMode.GREEN, "Slow Decay", "Halt hunger loss and ease harmful effects for a while.", 200),
    FERTILE_TIME(27, TimeMode.GREEN, "Fertile Time", "Speed the growth of all plant life in the area.", 160),
    VITAL_SURGE(28, TimeMode.GREEN, "Vital Surge", "An instant burst of healing.", 100),
    GUARDIANS_PACE(29, TimeMode.GREEN, "Guardian's Pace", "Gain a shield of absorption.", 180),
    RESTORATION_PULSE(30, TimeMode.GREEN, "Restoration Pulse", "Cure poison and wither from yourself and allies.", 160),
    TEMPORAL_SHIELD(31, TimeMode.GREEN, "Temporal Shield", "Become briefly untouchable by time itself.", 300),
    NATURES_CLOCK(32, TimeMode.GREEN, "Nature's Clock", "Speed up the breeding and aging of animals nearby.", 160),
    SERENITY_FIELD(33, TimeMode.GREEN, "Serenity Field", "Calm hostile creatures around you, ending their aggression.", 220),

    // ===================== BLUE — TIME & SPACE CONTROL (16) =====================
    TIME_FREEZE(34, TimeMode.BLUE, "Time Freeze", "Freeze nearby enemies in place for a few seconds.", 260),
    TIME_SLOW(35, TimeMode.BLUE, "Time Slow", "Slow every hostile creature around you.", 160),
    BLINK(36, TimeMode.BLUE, "Blink", "Teleport a short distance forward instantly.", 60),
    WORLD_REWIND(37, TimeMode.BLUE, "World Rewind", "Restore the last block you broke to its place.", 100),
    HASTE_TIME(38, TimeMode.BLUE, "Haste Time", "Speed yourself far beyond normal limits.", 140),
    DAY_NIGHT_SHIFT(39, TimeMode.BLUE, "Day/Night Shift", "Push the world's clock forward to day or night.", 400),
    WEATHER_CONTROL(40, TimeMode.BLUE, "Weather Control", "Command the sky to clear or storm.", 400),
    PHASE_STEP(41, TimeMode.BLUE, "Phase Step", "Phase through the wall directly ahead of you.", 160),
    SLOW_FALL(42, TimeMode.BLUE, "Slow Fall", "Time cushions your fall for a while.", 120),
    CLOCK_STOP(43, TimeMode.BLUE, "Clock Stop", "Halt every nearby creature's sense of time.", 360),
    FAST_FORWARD(44, TimeMode.BLUE, "Fast Forward", "Speed, jump boost, and haste all at once.", 180),
    TIME_ANCHOR(45, TimeMode.BLUE, "Time Anchor", "Drop an anchor in time; use again to return to it.", 40),
    DILATION_FIELD(46, TimeMode.BLUE, "Dilation Field", "Slow time for foes while it speeds up for you.", 260),
    CHRONO_VISION(47, TimeMode.BLUE, "Chrono Vision", "See through time: night vision, and foes glow through walls.", 200),
    PARADOX_STEP(48, TimeMode.BLUE, "Paradox Step", "Swap places with the entity you are looking at.", 180),
    ETERNAL_MOMENT(49, TimeMode.BLUE, "Eternal Moment", "Freeze every creature in a wide radius and empower yourself.", 600);

    private final int id;
    private final TimeMode mode;
    private final String displayName;
    private final String description;
    private final int cooldownTicks;

    TimeAbility(int id, TimeMode mode, String displayName, String description, int cooldownTicks) {
        this.id = id;
        this.mode = mode;
        this.displayName = displayName;
        this.description = description;
        this.cooldownTicks = cooldownTicks;
    }

    public int getId() {
        return id;
    }

    public TimeMode getMode() {
        return mode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public int getCooldownTicks() {
        return cooldownTicks;
    }

    public static TimeAbility byId(int id) {
        for (TimeAbility a : values()) {
            if (a.id == id) return a;
        }
        return ASTRAL_SLASH;
    }

    public static List<TimeAbility> forMode(TimeMode mode) {
        List<TimeAbility> list = new ArrayList<>();
        for (TimeAbility a : values()) {
            if (a.mode == mode) list.add(a);
        }
        return list;
    }
}
