package world_wars.builds.defends;

import world_wars.entity.Build;
import world_wars.entity.BuildType;

public class Tower_2 extends Build {
    public Tower_2() {
        super(2);
        this.defence = 2;
        this.type = BuildType.TOWER_2;
    }
}
