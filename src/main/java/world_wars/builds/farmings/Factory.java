package world_wars.builds.farmings;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Factory extends Build implements Upgradable {
    public Factory() {
        super();
        this.defence = 0;
        this.type = BuildType.FACTORY;
    }
    @Override
    public void upgrade() { this.lvl++; }
}
