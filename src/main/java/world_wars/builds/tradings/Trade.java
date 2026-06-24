package world_wars.builds.tradings;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Trade extends Build implements Upgradable {
    public Trade() {
        super(2);
        this.defence = 0;
        this.type = BuildType.TRADE;
    }
    @Override
    public void upgrade() { this.lvl++; }
}
