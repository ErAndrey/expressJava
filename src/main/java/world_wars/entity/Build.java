package world_wars.entity;

import world_wars.builds.attacks.Technique;
import world_wars.builds.farmings.OilRig;
import world_wars.builds.tradings.Trade;
import world_wars.general.Currency;
import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.general.ToString;
import world_wars.interfaces.Upgradable;

import java.util.Objects;

public abstract class Build extends Entity implements Upgradable {
    protected BuildType type;
    protected Currency produce;
    protected boolean isWork; // Если денег на обеспечение не хватает, то здание перестает функционировать

    public static boolean buildIsMaxLvl(Build build) {
        return (build instanceof OilRig || build instanceof Technique || build instanceof Trade) ? build.getLvl() == 3 : build.getLvl() == 4;
    }

    public Build() { super(); }
    public Build(int lvl) { super(lvl); }

    public BuildType getType() { return this.type; }

    public boolean isWork() { return this.isWork; }
    public void stopWork() { this.isWork = false; }
    public void startWork() { this.isWork = true; }
    public Currency getProduce() { return this.produce; }

    protected int getTowerDefence() {
        return switch (this.lvl) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 4;
            default -> throw new IllegalStateException("Unexpected Tower lvl value: " + this.lvl);
        };
    }
    protected BuildType getTowerType() {
        return switch (this.lvl) {
            case 1 -> BuildType.TOWER_1;
            case 2 -> BuildType.TOWER_2;
            case 3 -> BuildType.TOWER_3;
            case 4 -> BuildType.TOWER_4;
            default -> throw new IllegalStateException("Unexpected Tower lvl value: " + lvl);
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Build build = (Build) o;
        return this.type.equals(build.type) && this.lvl == build.lvl;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.lvl);
    }

    @Override
    public void upgrade() {
        this.lvl++;
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
    }

    @Override
    public String toString() { return ToString.forBuild(this); }
}
