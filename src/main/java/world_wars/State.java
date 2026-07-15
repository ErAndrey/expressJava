package world_wars;

import world_wars.builds.Capital;
import world_wars.builds.farmings.*;
import world_wars.builds.tradings.Shop;
import world_wars.builds.tradings.Trade;
import world_wars.ccpu.CreateBuild;
import world_wars.entity.Build;
import world_wars.entity.Unit;
import world_wars.general.Currency;
import world_wars.general.ToString;
import world_wars.trading.CurrencyType;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class State {
    private static int counter = 1;

    private final int id;
    private final Capital capital;
    private Currency currentBalance;
    private Currency changeBalance;
    private final Map<Integer, Build> builds;
    private final Map<Integer, Unit> units;

    public State() {
        this.id = counter++;
        this.capital = new Capital();
        this.currentBalance = Currency.of(15, 10, 10, 10, 5, 0);
        this.changeBalance = Currency.of(0, 0, 0, 0, 0, 0);
        this.builds = new HashMap<>();
        this.units = new HashMap<>();
        this.builds.put(capital.getId(), capital);
        this.setActualChangeBalance();

        //toDo test
        Trade trade = new Trade();
        this.builds.put(trade.getId(), trade);
        this.currentBalance = Currency.of(50, 50, 50, 50, 50, 50);
    }

    public int getId() {
        return this.id;
    }
    public Capital getCapital() {
        return this.capital;
    }
    public Currency getChangeBalance() {
        return this.changeBalance;
    }
    public Currency getCurrentBalance() {
        return this.currentBalance;
    }

    public Map<Integer, Build> getBuilds() {
        return this.builds;
    }
    public int getBuildsSize() {
        return this.builds.size();
    }

    public Map<Integer, Unit> getUnits() {
        return this.units;
    }
    public int getUnitsSize() {
        return this.units.size();
    }

    public Map<Integer, Shop> getShops() { return this.builds.entrySet()
            .stream().filter(entry -> entry.getValue() instanceof Shop)
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> (Shop) entry.getValue()
            ));
    }
    public boolean isCanBuyFromShop() { return this.builds.values().stream().anyMatch(build -> build instanceof Shop); }

    public boolean isCanTrade() { return this.builds.values().stream().anyMatch(build -> build instanceof Trade); }
    public Trade getTrade() { return (Trade) this.builds.values().stream().filter(build -> build instanceof Trade).findFirst().get(); }
    private int getCountFarmingBuilds() {
        return (int) this.builds.values().stream()
                .filter(build ->
                        build instanceof Farm ||
                        build instanceof Factory ||
                        build instanceof Sawmill ||
                        build instanceof Mine ||
                        build instanceof OilRig
                )
                .count();
    }

    public boolean isHaveBalanceToSpend(Currency price) {
        return currentBalance.getGold() >= price.getGold() &&
                currentBalance.getFood() >= price.getFood() &&
                currentBalance.getStone() >= price.getStone() &&
                currentBalance.getTree() >= price.getTree() &&
                currentBalance.getOre() >= price.getOre() &&
                currentBalance.getOil() >= price.getOil();
    }

    public void addBuild(Build build) {
        this.builds.put(build.getId(), build);
        this.setActualChangeBalance();
    }
    public void upgradeBuild(Build build) {
        build.upgrade();
        this.setActualChangeBalance();
    }
    public void destroyBuild(Build build) {
        Currency toReturn = CreateBuild.getCreateBuildInfo(build.getType()).price();
        toReturn.depositCurrency(build.getProduce());
        this.currentBalance.depositCurrency(Currency.getMiddleCurrency(toReturn));
        this.builds.remove(build.getId());
        this.setActualChangeBalance();
    }
    public void addUnit(Unit unit) {
        this.units.put(unit.getId(), unit);
        this.setActualChangeBalance();
    }

    private void setActualChangeBalance() {
        Currency currency = Currency.of(0, 0, 0, 0, 0, 0);
        this.units.values().forEach(unit -> currency.withdrawCurrency(unit.getConsume()));
        this.builds.values().forEach(build -> currency.withdrawCurrency(build.getConsume()));
        this.builds.values().forEach(build -> currency.depositCurrency(build.getProduce()));
        this.changeBalance = currency;
    }

    public void endMove() {
        this.units.values().forEach(unit -> this.currentBalance.withdrawCurrency(unit.getConsume()));
        this.builds.values().forEach(build -> this.currentBalance.withdrawCurrency(build.getConsume()));
        this.builds.values().forEach(build -> this.currentBalance.depositCurrency(build.getProduce()));
        this.getShops().values().forEach(shop -> shop.supplyCurrency(this));
        this.builds.values().stream().filter(build -> build instanceof Trade).forEach(build -> ((Trade) build).resetDelivery(this.getCountFarmingBuilds()));
    }

    public int getCountCurrencyFarmBuild(CurrencyType farmedCurrency) {
        return switch (farmedCurrency) {
            case GOLD -> 0;
            case FOOD -> this.builds.values().stream().filter(build -> build instanceof Farm).toList().size();
            case STONE -> this.builds.values().stream().filter(build -> build instanceof Factory).toList().size();
            case TREE -> this.builds.values().stream().filter(build -> build instanceof Sawmill).toList().size();
            case ORE -> this.builds.values().stream().filter(build -> build instanceof Mine).toList().size();
            case OIL -> this.builds.values().stream().filter(build -> build instanceof OilRig).toList().size();
        };
    }

    @Override
    public String toString() {
        return ToString.forState(this) + " " + ToString.forStateBalance(this);
    }
}
