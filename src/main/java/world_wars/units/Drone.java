package world_wars.units;

import world_wars.Currency;
import world_wars.entity.Build;
import world_wars.entity.Unit;
import world_wars.entity.UnitType;

public class Drone extends Unit {
    public Drone() {
        super(1);
        this.type = UnitType.DRONE;
        this.actionPoints = 1;
        this.defence = 0;
        this.power = 3;
        this.moveRadius = 4;
        this.attackRadius = 4;
    }

    @Override
    public void move(){}

    @Override
    public void attackUnit(Unit target) {}

    @Override
    public void attackBuild(Build target) {}
}
