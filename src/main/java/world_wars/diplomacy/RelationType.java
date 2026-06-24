package world_wars.diplomacy;

import world_wars.Icon;

public enum RelationType {
    NEUTRAL(Icon.NEUTRAL),
    FRIEND(Icon.FRIEND),
    UNION(Icon.UNION),
    WAR(Icon.WAR);

    private Icon icon;
    RelationType(Icon icon) { this.icon = icon; }
    @Override
    public String toString() { return this.icon.toString(); }
}
