package world_wars.builds.tradings;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Shop extends Build implements Upgradable {
    public Shop() {
        super();
        this.defence = 0;
        this.type = BuildType.SHOP;
    }
    @Override
    public void upgrade() { this.lvl++; }
}
