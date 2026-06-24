package world_wars.entity;

import world_wars.Currency;
import world_wars.ccpu.Consume;
import world_wars.interfaces.BehaviorAttack;
import world_wars.interfaces.BehaviorMove;
import world_wars.interfaces.Consuming;

public abstract class Unit extends Entity implements Consuming, BehaviorMove, BehaviorAttack {
    protected UnitType type;
    protected int actionPoints; // Количество дейстий. Сходить = 1, Атаковать = ренж атака ? 2 : 1;
    protected int power;
    protected int moveRadius;
    protected int attackRadius;
    protected boolean isAlive = true;

    public Unit(int lvl) { super(lvl); }

    public UnitType getType() { return this.type; }
    public int getActionPoints() { return this.actionPoints; }
    public int getPower() { return this.power; }
    public int getMoveRadius() { return this.moveRadius; }
    public int getAttackRadius() { return this.attackRadius; }
    public boolean isAlive() { return this.isAlive; }

    public boolean isCanMove() { return this.actionPoints != 0; }

    public void die() { this.isAlive = false; }

    @Override
    public Currency consume() { return Consume.getConsume(this); }

    @Override
    public String toString() { return this.type.toString(); }
}
