package world_wars.builds.farmings;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class OilRig extends Build implements Upgradable {
    public OilRig() {
        super(2);
        this.defence = 0;
        this.type = BuildType.OIL_RIG;
    }
    @Override
    public void upgrade() { this.lvl++; }
}
