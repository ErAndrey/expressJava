package world_wars.builds.farmings;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Sawmill extends Build implements Upgradable {
    public Sawmill() {
        super();
        this.type = BuildType.SAWMILL;
        this.defence = 0;
        this.isFarming = true;
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
    }
}
