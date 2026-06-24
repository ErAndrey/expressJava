package world_wars.builds;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Capital extends Build implements Upgradable {
    public Capital() {
        super();
        this.defence = 1;
        this.type = BuildType.CAPITAL;
    }

    @Override
    public void upgrade() { this.lvl++; }
}
