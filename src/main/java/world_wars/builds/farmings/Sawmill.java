package world_wars.builds.farmings;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Sawmill extends Build implements Upgradable {
    public Sawmill() {
        super();
        this.defence = 0;
        this.type = BuildType.SAWMILL;
    }
    @Override
    public void upgrade() { this.lvl++; }
}
