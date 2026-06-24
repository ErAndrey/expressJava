package world_wars.units;

import world_wars.Currency;
import world_wars.entity.Build;
import world_wars.entity.Unit;
import world_wars.entity.UnitType;

public class Plane extends Unit {
    public Plane() {
        super(1);
        this.type = UnitType.PLANE;
        this.actionPoints = 1;
        this.defence = 3;
        this.power = 4;
        this.moveRadius = 3;
        this.attackRadius = 3;
    }

    @Override
    public void move(){}

    @Override
    public void attackUnit(Unit target) {}

    @Override
    public void attackBuild(Build target) {}
}
