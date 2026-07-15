package world_wars.builds.farmings;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Farm extends Build implements Upgradable {
    public Farm() {
        super();
        this.type = BuildType.FARM;
        this.defence = 0;
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
    }
}
