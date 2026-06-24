package world_wars.builds.farmings;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Farm extends Build implements Upgradable {
    public Farm() {
        super();
        this.defence = 0;
        this.type = BuildType.FARM;
    }
    @Override
    public void upgrade() { this.lvl++; }
}
