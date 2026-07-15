package world_wars.entity;

import world_wars.general.Icon;
import world_wars.general.ToString;
import world_wars.interfaces.BehaviorAttack;
import world_wars.interfaces.BehaviorMove;

import java.util.Objects;

public abstract class Unit extends Entity implements BehaviorMove, BehaviorAttack {
    protected UnitType type;
    protected int actionPoints; // Количество дейстий. Сходить = 1, Атаковать = ренж атака ? 2 : 1;
    protected int power;
    protected int moveRadius;
    protected int attackRadius;
    protected boolean isAlive = true;

    public Unit(int lvl) {
        super(lvl);
    }

    public UnitType getType() { return this.type; }
    public int getActionPoints() { return this.actionPoints; }
    public int getPower() { return this.power; }
    public int getMoveRadius() { return this.moveRadius; }
    public int getAttackRadius() { return this.attackRadius; }
    public boolean isAlive() { return this.isAlive; }

    public boolean isCanMove() { return this.actionPoints != 0; }

    public void die() { this.isAlive = false; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return super.equals(unit) && this.type.equals(unit.type);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(this.type);
    }

    @Override
    public String toString() { return ToString.forUnit(this); }
}
