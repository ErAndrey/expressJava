package world_wars;

import world_wars.builds.Capital;
import world_wars.entity.Build;
import world_wars.entity.Unit;

import java.util.HashMap;
import java.util.Map;

public final class State {
    private static int counter = 1;

    private final int id;
    private final Capital capital;
    private Currency currentBalance;
    private Currency changeBalance;
    private final Map<Build, Integer> builds;
    private final Map<Unit, Integer> units;

    public State() {
        this.id = counter++;
        this.capital = new Capital();
        this.currentBalance = Currency.of(15, 10, 10, 10, 5, 0);
        this.changeBalance = Currency.of(0,0,0,0,0,0);
        this.builds = new HashMap<>();
        this.units = new HashMap<>();
        this.builds.put(this.capital, 1);
        //this.changeBalance.depositCurrency(this.capital.getChangeBalance());
    }

    public int getId() { return this.id; }
    public Capital getCapital() { return this.capital; }
    public Currency getChangeBalance() { return this.changeBalance; }
    public Currency getCurrentBalance() { return this.currentBalance; }
    public Map<Build, Integer> getBuilds() { return this.builds; }
    public Map<Unit, Integer> getUnits() { return this.units; }

    /*
    private boolean isHaveBalanceToSpend(Currency price) {
        return currentBalance.getGold() >= price.getGold() &&
                currentBalance.getFood() >= price.getFood() &&
                currentBalance.getStone() >= price.getStone() &&
                currentBalance.getTree() >= price.getTree() &&
                currentBalance.getOre() >= price.getOre() &&
                currentBalance.getOil() >= price.getOil();
    }
     */

    //private void applyChangeBalanceToCurrent() { this.currentBalance.depositCurrency(changeBalance); }

    public void addBuild(Build build) { this.builds.merge(build, 1, Integer::sum); }
    public void addUnit(Unit unit) { this.units.merge(unit, 1, Integer::sum); }

    public void endMove() {
        /**
         * 1. Начислить что приносят здания
         * 2. Списать что тратят войска
         * 3. Списать что тратит столица
         * 4. Списать что тратят башни // если не хватает, ? деф ставим = 0, пока денег не хватит
         * 5. Списать что тратят заводы юнитов // если не хватает, ? создавать юнитов нельзя
         * 6. Списать что тратят остальные здания (торговля / магазин)
         */

    }
}
