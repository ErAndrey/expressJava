package world_wars.builds.attacks;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Technique extends Build implements Upgradable {
    public Technique() {
        super(1);
        this.type = BuildType.TECHNIQUE;
        this.defence = 0;
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
    }
}
