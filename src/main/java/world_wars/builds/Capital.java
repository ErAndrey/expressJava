package world_wars.builds;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Capital extends Build implements Upgradable {
    public Capital() {
        super();
        this.type = BuildType.CAPITAL;
        this.defence = 1;
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
    }
}
