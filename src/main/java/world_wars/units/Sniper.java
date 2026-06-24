package world_wars.units;

import world_wars.Currency;
import world_wars.entity.Build;
import world_wars.entity.Unit;
import world_wars.entity.UnitType;

public class Sniper extends Unit {
    public Sniper() {
        super(1);
        this.type = UnitType.SNIPER;
        this.actionPoints = 2;
        this.defence = 0;
        this.power = 2;
        this.moveRadius = 1;
        this.attackRadius = 3;
    }

    @Override
    public void move(){}

    @Override
    public void attackUnit(Unit target) {}
    // учесть, чтобы именно снайпер мог атаковат только юнитов, а в радиусе 1 и здания
    @Override
    public void attackBuild(Build target) {}
}
