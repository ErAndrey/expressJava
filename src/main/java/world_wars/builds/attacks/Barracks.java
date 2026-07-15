package world_wars.builds.attacks;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Barracks extends Build implements Upgradable {
    public Barracks() {
        super();
        this.type = BuildType.BARRACKS;
        this.defence = 1;
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
    }
}
