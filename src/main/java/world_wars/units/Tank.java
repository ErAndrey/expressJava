package world_wars.units;

import world_wars.Currency;
import world_wars.entity.Build;
import world_wars.entity.Unit;
import world_wars.entity.UnitType;

public class Tank extends Unit {
    public Tank() {
        super(1);
        this.type = UnitType.TANK;
        this.actionPoints = 1;
        this.defence = 2;
        this.power = 3;
        this.moveRadius = 2;
        this.attackRadius = 2;
    }

    @Override
    public void move(){}

    @Override
    public void attackUnit(Unit target) {}

    @Override
    public void attackBuild(Build target) {}
}
