package world_wars.builds.defends;

import world_wars.entity.Build;
import world_wars.entity.BuildType;

public class Tower_3 extends Build  {
    public Tower_3() {
        super(3);
        this.defence = 3;
        this.type = BuildType.TOWER_3;
    }
}
