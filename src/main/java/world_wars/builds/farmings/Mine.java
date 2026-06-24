package world_wars.builds.farmings;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Mine extends Build implements Upgradable {
    public Mine() {
        super();
        this.defence = 0;
        this.type = BuildType.MINE;
    }
    @Override
    public void upgrade() { this.lvl++; }
}
