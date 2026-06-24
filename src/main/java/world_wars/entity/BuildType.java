package world_wars.entity;

import world_wars.Icon;

public enum BuildType {
    CAPITAL(Icon.CAPITAL),

    FARM(Icon.FARM),
    FACTORY(Icon.FACTORY),
    SAWMILL(Icon.SAWMILL),
    MINE(Icon.MINE),
    OIL_RIG(Icon.OIL_RIG),

    SHOP(Icon.SHOP),
    BARRACKS(Icon.BARRACKS),
    TECHNIQUE(Icon.TECHNIQUE),
    TRADE(Icon.TRADE),

    TOWER_1(Icon.TOWER_1),
    TOWER_2(Icon.TOWER_2),
    TOWER_3(Icon.TOWER_3),
    TOWER_4(Icon.TOWER_4);

    private Icon icon;
    BuildType(Icon icon) { this.icon = icon; }

    @Override
    public String toString() { return this.icon.toString(); }
}
