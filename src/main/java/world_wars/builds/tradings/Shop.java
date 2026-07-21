package world_wars.builds.tradings;

import world_wars.State;
import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.general.Utils;
import world_wars.trading.CurrencyType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop extends Build {

    private static int maxShopStorage(State state, Shop shop) {
        return state.getBuildsSize() * 2 + switch (shop.lvl) {
          case 1 -> 20;
          case 2 -> 55;
          case 3 -> 115;
          case 4 -> 180;
          default -> throw new IllegalStateException("Unexpected lvl shop value");
        };
    }
    private static List<CurrencyType> currencyAvailable(Shop shop) {
        return switch (shop.lvl) {
            case 1 -> List.of(CurrencyType.FOOD, CurrencyType.TREE, CurrencyType.STONE);
            case 2 -> List.of(CurrencyType.ORE, CurrencyType.FOOD, CurrencyType.TREE, CurrencyType.STONE);
            case 3, 4 -> List.of(CurrencyType.OIL, CurrencyType.FOOD, CurrencyType.ORE, CurrencyType.TREE, CurrencyType.STONE);
            default -> throw new IllegalStateException("Unexpected lvl shop value");
        };
    }
    public static Map<CurrencyType, Integer> currencyPriceInGold(Shop shop) {
        return switch (shop.getLvl()) {
            case 1 -> Map.of(
                    CurrencyType.FOOD, 5,
                    CurrencyType.TREE, 7,
                    CurrencyType.STONE, 7
            );
            case 2 -> Map.of(
                    CurrencyType.FOOD, 4,
                    CurrencyType.TREE, 6,
                    CurrencyType.STONE, 6,
                    CurrencyType.ORE, 7
            );
            case 3 -> Map.of(
                    CurrencyType.FOOD, 3,
                    CurrencyType.TREE, 5,
                    CurrencyType.STONE, 5,
                    CurrencyType.ORE, 6,
                    CurrencyType.OIL, 7
            );
            case 4 -> Map.of(
                    CurrencyType.FOOD, 2,
                    CurrencyType.TREE, 3,
                    CurrencyType.STONE, 3,
                    CurrencyType.ORE, 4,
                    CurrencyType.OIL, 5
            );
            default -> throw new IllegalStateException("Unexpected lvl shop value");
        };
    }

    private Map<CurrencyType, Integer> currencyPriceInGold;
    private Map<CurrencyType, Integer> currencyCountAvailable;

    public Shop() {
        super();
        this.type = BuildType.SHOP;
        this.defence = 0;
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
        this.currencyPriceInGold = new HashMap<>();
        this.currencyCountAvailable = new HashMap<>();
    }

    public void supplyCurrency(State state) {
        this.currencyCountAvailable.clear();
        int alreadySupply = 0;
        while (maxShopStorage(state, this) > alreadySupply) {
            int supplyCurrency;
            for (CurrencyType currency : currencyAvailable(this)) {
                int buildFarm = state.getCountCurrencyFarmBuild(currency);
                if (buildFarm != 0) buildFarm /= 4;
                supplyCurrency = Utils.getNextRandom(1, buildFarm + 2);
                this.currencyCountAvailable.merge(currency, supplyCurrency, Integer::sum);
                alreadySupply += supplyCurrency;
            }
        }
        this.currencyPriceInGold = currencyPriceInGold(this);
    }

    public Map<CurrencyType, Integer> getCurrencyPriceInGold() { return this.currencyPriceInGold; }
    public Map<CurrencyType, Integer> getCurrencyCountAvailable() { return this.currencyCountAvailable; }
    public void buyCurrency(CurrencyType currency, int count) {
        this.currencyCountAvailable.put(currency, currencyCountAvailable.getOrDefault(currency, 0) - count);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        System.out.println(Utils.toGreen("System: ") + "Магазин улучшен! Ожидайте снижения цен и новую партию товаров к следующему ходу!");
    }
}
