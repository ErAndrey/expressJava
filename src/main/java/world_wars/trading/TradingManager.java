package world_wars.trading;

import world_wars.Player;
import world_wars.State;
import world_wars.builds.tradings.Trade;
import world_wars.diplomacy.RelationType;
import world_wars.general.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TradingManager {
    private TreeMap<Integer, TradingRequest> tradingRequests;

    public static Map<Integer, List<RelationType>> getAvailableRelationTypesForCreateTrade(Trade trade) {
        return switch (trade.getLvl()) {
            case 1 -> Map.of(
                    1, List.of(RelationType.FRIEND),
                    2, List.of(RelationType.WAR, RelationType.NEUTRAL, RelationType.FRIEND, RelationType.UNION)
            );
            case 2 -> Map.of(
                    1, List.of(RelationType.FRIEND),
                    2, List.of(RelationType.UNION),
                    3, List.of(RelationType.WAR, RelationType.NEUTRAL, RelationType.FRIEND, RelationType.UNION)
            );
            case 3 -> Map.of(
                    1, List.of(RelationType.FRIEND),
                    2, List.of(RelationType.UNION),
                    3, List.of(RelationType.FRIEND, RelationType.UNION),
                    4, List.of(RelationType.WAR, RelationType.NEUTRAL, RelationType.FRIEND, RelationType.UNION)
            );
            default -> throw new IllegalStateException("Unexpected trade lvl value");
        };
    }
    public static TradingType selectTradingTypeForTrade() {
        System.out.println(Utils.toYellow("\nSystem: ") + "Выберите тип торговли");
        System.out.println("\n1️⃣ Купить (1:N)\n2️⃣ Продать (1:N)\n3️⃣ Обменять (N:N)\n");
        while (true) {
            int choice = Utils.nextInt("#️⃣ тип: ");
            switch (choice) {
                case 1 -> { return TradingType.BUY; }
                case 2 -> { return TradingType.SELL; }
                case 3 -> { return TradingType.SWAP; }
                default -> System.out.println(Utils.toRed("System: ") + "Выберите доступный тип");
            }
        }
    }
    public static CurrencyType selectCurrencyTypeForTrade(State state, TradingType tradingType) {
        String tradingTypeText = null;
        List<CurrencyType> available = null;

        switch (tradingType) {
            case BUY -> tradingTypeText = "купить";
            case SELL -> tradingTypeText = "продать";
            case SWAP -> tradingTypeText = "обменять";
        }

        System.out.println(Utils.toYellow("\nSystem: ") + "Выберите, какой ресурс вы хотите " + tradingTypeText);

        int i = 1;

        switch (tradingType) {
            case BUY -> {
                available = List.of(CurrencyType.values());
                for (CurrencyType type : available) System.out.println(Utils.getNumberOfAction(i++) + " " + type);
            }
            case SELL, SWAP -> {
                available = CurrencyType.getAvailableCurrencyTypeFromCurrency(state.getCurrentBalance());
                for (CurrencyType type : available) System.out.println(Utils.getNumberOfAction(i++) + " " + state.getCurrentBalance().get(type) + type);
            }
        }

        System.out.println();

        while (true) {
            int choice = Utils.nextInt("#️⃣ ресурса: ");
            if (choice > 0 && choice <= available.size()) return available.get(choice - 1);
            System.out.println(Utils.toRed("System: ") + "Выберите доступный ресурс");
        }
    }
    public static int selectCountSelectedCurrencyForSellOrSwapTrade(CurrencyType selectedCurrency, State state, TradingType tradingType) {
        String tradingTypeText = switch (tradingType) {
            case SELL -> "продать";
            case SWAP -> "обменять";
            default -> throw new IllegalStateException();
        };

        System.out.println(Utils.toYellow("\nSystem: ") + "Укажите, какое количество " + selectedCurrency + " вы хотите " + tradingTypeText);

        return switch (tradingType) {
            case SELL, SWAP -> Utils.selectNumber(1, Math.min(state.getCurrentBalance().get(selectedCurrency), state.getTrade().getCountForExport()), "Количество");
            default -> throw new IllegalStateException();
        };
    }
    public static int selectCountSelectedCurrencyForBuyTrade(CurrencyType selectedCurrency, int canBuy, CurrencyType expectedCurrency, int price) {
        System.out.println(Utils.toYellow("\nSystem: ") + "Укажите, какое количество " + selectedCurrency + " вы хотите купить по цене " + price + expectedCurrency + "/шт");

        return Utils.selectNumber(1, canBuy, "Количество");
    }
    public static CurrencyType selectCurrencyTypeForTradeExcludeCurrencyType(CurrencyType exclude, TradingType tradingType) {
        String tradingTypeText = switch (tradingType) {
            case BUY -> " отдать при покупке ";
            case SELL -> " получить при продаже ";case SWAP -> " получить при обмене ";
        };

        System.out.println(Utils.toYellow("\nSystem: ") + "Выберите, какой ресурс вы хотите" + tradingTypeText + exclude);

        List<CurrencyType> available = new ArrayList<>(List.of(CurrencyType.values()));
        available.remove(exclude);

        int i = 1;
        for (CurrencyType type : available) System.out.println(Utils.getNumberOfAction(i++) + " " + type);
        System.out.println();

        while (true) {
            int choice = Utils.nextInt("#️⃣ ресурса: ");
            if (choice > 0 && choice <= available.size()) return available.get(choice - 1);
            System.out.println(Utils.toRed("System: ") + "Выберите доступный ресурс");
        }
    }
    public static int selectCountExpectedCurrencyForTrade(State state, CurrencyType selected, int selectedCount, CurrencyType expected, TradingType tradingType) {
        String tradingTypeText = switch (tradingType) {
            case BUY -> Utils.toYellow("\nSystem: ") + "Укажите цену за покупку " + selected + " в " + expected + "/шт";
            case SELL -> Utils.toYellow("\nSystem: ") + "Укажите цену за продажу " + selected + " в " + expected + "/шт";
            case SWAP -> Utils.toYellow("\nSystem: ") + "Укажите сколько вы хотите получить " + expected + " в обмен на ваши " + selectedCount + selected;
        };
        System.out.println(tradingTypeText);
        return switch (tradingType) {
            case BUY -> {
                int maxPrice = Math.min(state.getTrade().getCountForExport(), state.getCurrentBalance().get(expected));

                int priceForSelected;
                while (true) {
                    priceForSelected = Utils.selectNumber(1, maxPrice, "Цена за шт");

                    int canBuy = Math.floorDiv(maxPrice, priceForSelected);

                    System.out.println("\nПри цене " + priceForSelected + expected + "/шт вы сможете создать запрос на покупку " + canBuy + selected);
                    System.out.println("\n0️⃣ Изменить цену\n1️⃣ Продолжить\n");

                    if (Utils.whatToDoNext(1) == 1) break;
                }

                yield priceForSelected;
            }
            case SELL -> Utils.nextIntPositive("Цена за шт: ");
            case SWAP -> Utils.nextIntPositive("Количество: ");
        };
    }
    public static List<RelationType> selectAvailableRelationsForTrade(Trade trade) {
        System.out.println(Utils.toYellow("\nSystem: ") + "Выберите, для кого будет доступна сделка");
        TreeMap<Integer, List<RelationType>> available = new TreeMap<>(getAvailableRelationTypesForCreateTrade(trade));
        available.entrySet().forEach(entry -> {
            StringBuilder sb = new StringBuilder(" [");
            for (RelationType type : entry.getValue()) sb.append(type.getIcon());
            System.out.println(Utils.getNumberOfAction(entry.getKey()) + sb.append("]"));
        });
        System.out.println();
        while (true) {
            int choice = Utils.nextInt("#️⃣ вариант: ");
            if (available.containsKey(choice)) return available.get(choice);
            System.out.println(Utils.toRed("System: ") + "Выберите доступный вариант");
        }
    }

    public TradingManager() {
        this.tradingRequests = new TreeMap<>();
    }

    public TreeMap<Integer, TradingRequest> getAvailableTradingRequestsFor(Player player) {
        return this.tradingRequests.entrySet().stream()
                .filter(entry -> !entry.getValue().getFromPlayer().equals(player))
                .filter(entry -> entry.getValue().getAvailableFor().contains(player.getRelations().get(entry.getValue().getFromPlayer())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        TreeMap::new
                        )
                );
    }

    public TreeMap<Integer, TradingRequest> getMyTradingRequest(Player player) {
        return this.tradingRequests.entrySet().stream()
                .filter(entry -> entry.getValue().getFromPlayer().equals(player))
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (existing, replacement) -> existing,
                                TreeMap::new
                        )
                );
    }

    public void removeTradingRequest(TradingRequest tradingRequest) {
        this.tradingRequests.remove(tradingRequest.getId());
        switch (tradingRequest.getTradingType()) {
            case BUY -> tradingRequest.getFromPlayer().getWarehouseForTrade().deposit(tradingRequest.getExpectedCurrency(), tradingRequest.getCountExpected());
            case SELL, SWAP -> tradingRequest.getFromPlayer().getWarehouseForTrade().deposit(tradingRequest.getSelectedCurrency(), tradingRequest.getCountSelected());
        }
    }

    public void createTradingRequest(Player fromPlayer, List<RelationType> availableFor, CurrencyType selectedCurrency, int countSelected, TradingType tradingType, CurrencyType expectedCurrency, int countExpected) {
        TradingRequest tradingRequest = new TradingRequest(fromPlayer, availableFor, selectedCurrency, countSelected, tradingType, expectedCurrency, countExpected);
        this.tradingRequests.put(tradingRequest.getId(), tradingRequest);
    }

    public void acceptBuyRequest(Player seller, TradingRequest tradingRequest, int count) {
        Player buyer = tradingRequest.getFromPlayer();

        tradingRequest.withdrawCountSelected(count);

        seller.getWarehouseForTrade().withdraw(tradingRequest.getSelectedCurrency(), count);
        buyer.getWarehouseForTrade().deposit(tradingRequest.getSelectedCurrency(), count);
        seller.getWarehouseForTrade().deposit(tradingRequest.getExpectedCurrency(), count * tradingRequest.getCountExpected());

        if (tradingRequest.getCountSelected() == 0) tradingRequests.remove(tradingRequest.getId());

        System.out.println(Utils.toGreen("\nSystem: ") + "Вы продали " + count + tradingRequest.getSelectedCurrency() + " за " + count * tradingRequest.getCountExpected() + tradingRequest.getExpectedCurrency());
    }

    public void acceptSellRequest(Player buyer, TradingRequest tradingRequest, int count) {
        Player seller = tradingRequest.getFromPlayer();

        tradingRequest.withdrawCountSelected(count);

        buyer.getWarehouseForTrade().withdraw(tradingRequest.getExpectedCurrency(), count * tradingRequest.getCountExpected());
        seller.getWarehouseForTrade().deposit(tradingRequest.getExpectedCurrency(), count * tradingRequest.getCountExpected());
        buyer.getWarehouseForTrade().deposit(tradingRequest.getSelectedCurrency(), count);

        if (tradingRequest.getCountSelected() == 0) tradingRequests.remove(tradingRequest.getId());

        System.out.println(Utils.toGreen("\nSystem: ") + "Вы купили " + count + tradingRequest.getSelectedCurrency() + " за " + count * tradingRequest.getCountExpected() + tradingRequest.getExpectedCurrency());
    }

    public void acceptSwapRequest(Player buyer, TradingRequest tradingRequest) {
        Player seller = tradingRequest.getFromPlayer();

        buyer.getWarehouseForTrade().withdraw(tradingRequest.getExpectedCurrency(), tradingRequest.getCountExpected());
        seller.getWarehouseForTrade().deposit(tradingRequest.getExpectedCurrency(), tradingRequest.getCountExpected());
        buyer.getWarehouseForTrade().deposit(tradingRequest.getSelectedCurrency(), tradingRequest.getCountSelected());

        this.tradingRequests.remove(tradingRequest.getId());

        System.out.println(Utils.toGreen("\nSystem: ") + "Вы обменяли " + tradingRequest.getCountExpected() + tradingRequest.getExpectedCurrency() + " на " + tradingRequest.getCountSelected() + tradingRequest.getSelectedCurrency());
    }
}
