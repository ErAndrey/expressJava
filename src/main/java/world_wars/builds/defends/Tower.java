package world_wars.builds.defends;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.interfaces.Upgradable;

public class Tower extends Build implements Upgradable {
    public Tower(int lvl) {
        super(lvl);
        this.defence = getCurrentDefence();
        this.type = getCurrentType();
    }

    private int getCurrentDefence() {
        return switch (this.lvl) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 4;
            default -> throw new IllegalStateException("Unexpected Tower lvl value: " + this.lvl);
        };
    }
    private BuildType getCurrentType() {
        return switch (this.lvl) {
            case 1 -> BuildType.TOWER_1;
            case 2 -> BuildType.TOWER_2;
            case 3 -> BuildType.TOWER_3;
            case 4 -> BuildType.TOWER_4;
            default -> throw new IllegalStateException("Unexpected Tower lvl value: " + lvl);
        };
    }

    @Override
    public void upgrade() {
        this.lvl++;
        this.defence = getCurrentDefence();
        this.type = getCurrentType();
    }
}
