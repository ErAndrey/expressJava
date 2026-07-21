package world_wars.builds.defends;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Tower extends Build implements Upgradable {
    private static int getTowerDefence(Tower tower) {
        return switch (tower.lvl) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 4;
            default -> throw new IllegalStateException("Unexpected Tower lvl value: " + tower.lvl);
        };
    }
    private static BuildType getTowerType(Tower tower) {
        return switch (tower.lvl) {
            case 1 -> BuildType.TOWER_1;
            case 2 -> BuildType.TOWER_2;
            case 3 -> BuildType.TOWER_3;
            case 4 -> BuildType.TOWER_4;
            default -> throw new IllegalStateException("Unexpected Tower lvl value: " + tower.lvl);
        };
    }

    public Tower(int lvl) {
        super(lvl);
        this.type = getTowerType(this);
        this.defence = getTowerDefence(this);
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.type = getTowerType(this);
        this.defence = getTowerDefence(this);
    }
}
