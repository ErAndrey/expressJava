package world_wars.units;

import world_wars.Currency;
import world_wars.entity.Build;
import world_wars.entity.Unit;
import world_wars.entity.UnitType;

public class FlareGun extends Unit {
    public FlareGun() {
        super(1);
        this.type = UnitType.FLARE_GUN;
        this.actionPoints = 2;
        this.defence = 2;
        this.power = 5;
        this.moveRadius = 1;
        this.attackRadius = 5;
    }

    @Override
    public void move() {}

    @Override
    public void attackUnit(Unit target) {}

    @Override
    public void attackBuild(Build target) {}
}
