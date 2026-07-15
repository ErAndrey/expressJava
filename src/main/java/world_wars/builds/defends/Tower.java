package world_wars.builds.defends;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.interfaces.Upgradable;

public class Tower extends Build implements Upgradable {
    public Tower(int lvl) {
        super(lvl);
        this.type = getTowerType();
        this.defence = getTowerDefence();
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.type = getTowerType();
        this.defence = getTowerDefence();
    }
}
