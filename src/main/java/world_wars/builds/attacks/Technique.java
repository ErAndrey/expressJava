package world_wars.builds.attacks;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Technique extends Build implements Upgradable {
    public Technique() {
        super(2);
        this.defence = 0;
        this.type = BuildType.TECHNIQUE;
    }
    @Override
    public void upgrade() { this.lvl++; }
}
