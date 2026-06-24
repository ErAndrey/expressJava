package world_wars.entity;

import world_wars.Icon;

public enum UnitType {
    SCOUT(Icon.SCOUT),
    SOLDIER(Icon.SOLDIER),
    SNIPER(Icon.SNIPER),

    TANK(Icon.TANK),
    PLANE(Icon.PLANE),

    DRONE(Icon.DRONE),
    FLARE_GUN(Icon.FLARE_GUN);

    private Icon icon;
    UnitType(Icon icon) { this.icon = icon; }

    @Override
    public String toString() { return this.icon.toString(); }
}
