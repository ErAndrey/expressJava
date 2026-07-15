package world_wars.trading;

import world_wars.builds.tradings.Shop;
import world_wars.general.Currency;
import world_wars.general.Icon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CurrencyType {

    GOLD(Icon.GOLD), //На подом для trading
    FOOD(Icon.FOOD),
    STONE(Icon.STONE),
    TREE(Icon.TREE),
    ORE(Icon.ORE),
    OIL(Icon.OIL);

    public static List<CurrencyType> getAvailableCurrencyTypeFromCurrency(Currency currency) {
        List<CurrencyType> types = new ArrayList<>();
        if (currency.get(GOLD) > 0) types.add(GOLD);
        if (currency.get(FOOD) > 0) types.add(FOOD);
        if (currency.get(STONE) > 0) types.add(STONE);
        if (currency.get(TREE) > 0) types.add(TREE);
        if (currency.get(ORE) > 0) types.add(ORE);
        if (currency.get(OIL) > 0) types.add(OIL);
        return types;
    }

    private Icon icon;
    CurrencyType(Icon icon) { this.icon = icon; }

    @Override
    public String toString() { return this.icon.toString(); }

}
