package world_wars.builds.farmings;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class OilRig extends Build implements Upgradable {
    public OilRig() {
        super(1);
        this.type = BuildType.OIL_RIG;
        this.defence = 0;
        this.isFarming = true;
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
    }
}
