package world_wars.builds.attacks;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Barracks extends Build implements Upgradable {
    public Barracks() {
        super();
        this.defence = 1;
        this.type = BuildType.BARRACKS;
    }
    @Override
    public void upgrade() { this.lvl++; }
}
