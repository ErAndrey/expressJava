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
    protected boolean isFarming;

    public static boolean buildIsMaxLvl(Build build) {
        return (build instanceof OilRig || build instanceof Technique || build instanceof Trade) ? build.getLvl() == 3 : build.getLvl() == 4;
    }

    public Build() { super(); }
    public Build(int lvl) { super(lvl); }

    public BuildType getType() { return this.type; }
    public Currency getProduce() { return this.produce; }
    public boolean isFarming() { return this.isFarming; }

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
