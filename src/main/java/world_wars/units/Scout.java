package world_wars.units;

import world_wars.ccpu.Consume;
import world_wars.entity.Build;
import world_wars.entity.Unit;
import world_wars.entity.UnitType;

public class Scout extends Unit {
    public Scout() {
        super(1);
        this.type = UnitType.SCOUT;
        this.actionPoints = 1;
        this.defence = 0;
        this.power = 1;
        this.moveRadius = 3;
        this.attackRadius = 1;
        this.consume = Consume.getConsume(this);
    }

    @Override
    public void move(){}

    @Override
    public void attackUnit(Unit target) {}

    @Override
    public void attackBuild(Build target) {}
}
