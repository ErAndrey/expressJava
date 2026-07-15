package world_wars.builds.farmings;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Mine extends Build implements Upgradable {
    public Mine() {
        super();
        this.type = BuildType.MINE;
        this.defence = 0;
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
    }
}
