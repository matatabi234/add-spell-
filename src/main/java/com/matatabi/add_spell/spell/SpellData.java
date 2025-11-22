package com.matatabi.add_spell.spell;

public class SpellData {
    public final String name;
    public final String targetType; // "area" / "single" / "raycast"
    public final int range;
    public final int power;

    public SpellData(String name, String targetType, int range, int power) {
        this.name = name;
        this.targetType = targetType;
        this.range = range;
        this.power = power;
    }
}
