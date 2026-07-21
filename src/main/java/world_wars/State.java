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
    private int exportPower;
    private int importPower;

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
        this.currentBalance = Currency.of(200, 50, 50, 50, 50, 50);
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
    public boolean isCanBuy() { return this.builds.values().stream().anyMatch(build -> build instanceof Shop); }

    public Map<Integer, Trade> getTrades() {
        return this.builds.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Trade)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (Trade) entry.getValue()
                ));
    }
    public boolean isCanTrade() {
        return this.capital.getLvl() >= 2;
    }
    public boolean isCanExport() {
        return this.exportPower > 0;
    }
    public int getExportPower() {
        return this.exportPower;
    }
    public void deliveryExport(int count) {
        this.exportPower -= count;
    }
    public boolean isCanImport() {
        return this.importPower > 0;
    }
    public int getImportPower() {
        return this.importPower;
    }
    public void deliveryImport(int count) {
        this.importPower -= count;
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

    public void startMove() {
        this.setActualChangeBalance();

        if (!this.getTrades().isEmpty()) {
            int countFarmingBuild = (int) this.builds.values().stream()
                    .filter(Build::isFarming)
                    .count();

            this.exportPower = (int) (countFarmingBuild * 2.5) + this.getTrades().values().stream()
                    .mapToInt(Trade::getExportPower)
                    .sum();

            this.importPower = (int) (countFarmingBuild * 1.25) + this.getTrades().values().stream()
                    .mapToInt(Trade::getImportPower)
                    .sum();
        }

        this.getShops().values().forEach(shop -> shop.supplyCurrency(this));
    }

    public void endMove() {
        this.units.values().forEach(unit -> this.currentBalance.withdrawCurrency(unit.getConsume()));

        this.builds.values().stream().filter(build -> !build.isFarming()).forEach(build -> this.currentBalance.withdrawCurrency(build.getConsume()));

        this.builds.values().stream().filter(Build::isFarming).forEach(build -> {
            if (this.currentBalance.isHaveCurrencyToSpendOn(build.getConsume())) {
                this.currentBalance.withdrawCurrency(build.getConsume());
                this.currentBalance.depositCurrency(build.getProduce());
            }
        });

        Currency.checkMinusAndSetZero(this.currentBalance);
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
