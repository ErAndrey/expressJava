package world_wars.units;

import world_wars.ccpu.Consume;
import world_wars.entity.Build;
import world_wars.entity.Unit;
import world_wars.entity.UnitType;

public class Soldier extends Unit {
    public Soldier() {
        super(1);
        this.type = UnitType.SOLDIER;
        this.actionPoints = 1;
        this.defence = 1;
        this.power = 2;
        this.moveRadius = 3;
        this.attackRadius = 2;
        this.consume = Consume.getConsume(this);
    }

    @Override
    public void move(){}

    @Override
    public void attackUnit(Unit target) {}

    @Override
    public void attackBuild(Build target) {}
}
