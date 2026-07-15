package world_wars;

import world_wars.builds.Capital;
import world_wars.builds.tradings.Shop;
import world_wars.builds.tradings.Trade;
import world_wars.ccpu.CreateBuild;
import world_wars.ccpu.Upgrade;
import world_wars.diplomacy.DiplomacyManager;
import world_wars.diplomacy.Notification;
import world_wars.diplomacy.RelationRequest;
import world_wars.diplomacy.RelationType;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.general.*;
import world_wars.general.Currency;
import world_wars.trading.CurrencyType;
import world_wars.trading.TradingManager;
import world_wars.trading.TradingRequest;
import world_wars.trading.TradingType;

import java.util.*;
import java.util.stream.Collectors;

public class Player implements Comparable<Player> {
    private static int counter = 1;
    private final int id;
    private final Color color;
    private Map<Integer, State> states;
    private DiplomacyManager diplomacyManager;
    private Map<Player, RelationType> relations;
    private Map<Integer, Notification> notifications;
    private List<Player> alreadySendRelationRequest;
    private TradingManager tradingManager;
    private Currency warehouseForTrade;

    public Player(DiplomacyManager diplomacyManager, TradingManager tradingManager, Color color) {
        this.id = counter++;
        this.color = color;
        this.states = new HashMap<>();
        State state = new State();
        this.states.put(state.getId(), state);
        this.diplomacyManager = diplomacyManager;
        this.relations = new TreeMap<>();
        this.notifications = new TreeMap<>();
        this.alreadySendRelationRequest = new ArrayList<>();
        this.tradingManager = tradingManager;
        this.warehouseForTrade = Currency.of(0, 0, 0, 0, 0, 0);
    }

    public void depositCurrencyForTest(world_wars.general.Currency currency) {
        for (State state : states.values()) {
            state.getCurrentBalance().depositCurrency(currency);
        }
    }

    public int getId() {
        return this.id;
    }
    public Map<Player, RelationType> getRelations() {
        return this.relations;
    }
    public void setRelation(Player withPlayer, RelationType type) {
        this.relations.put(withPlayer, type);
    }
    public void addNotification(int id, Notification notification) {
        this.notifications.put(id, notification);
    }
    public Map<Integer, Notification> getNotifications() {
        return this.notifications;
    }
    public Currency getWarehouseForTrade() { return this.warehouseForTrade; }

    public int move() {
        System.out.println("\n➖➖➖➖➖➖➖➖➖➖➖");
        System.out.println(Utils.toYellow("System: ") + "Ход игрока " + this + "!");
        System.out.println("➖➖➖➖➖➖➖➖➖➖➖");
        while (true) {
            System.out.println(Utils.toYellow("\nSystem: ") + "Ваши штаты");
            this.states.values().forEach(System.out::println);

            String notificationBar = notifications.isEmpty() ?
                    "4️⃣ Новости       💤" :
                    "4️⃣ Новости     " + Utils.toRed("x" + notifications.size()) + "🔔";


            System.out.print("\n" +
                    """
                    0️⃣ Покинуть игру ⛔
                    1️⃣ Посетить штат ⭕
                    2️⃣ Закончить ход ✅
                    3️⃣ Дипломатия    🤝
                    """
            );
            System.out.println(notificationBar);
            System.out.println("5️⃣ Торговля      💼\n");

            // 🔎
            // Нанять шпиона 🔎

            int choice = Utils.whatToDoNext(5);

            switch (choice) {
                case 0 -> {
                    diplomacyManager.diePlayer(this);
                    return 0;
                }
                case 1 -> whatToDoWithState(selectState());
                case 2 -> {
                    endMove();
                    return 2;
                }
                case 3 -> diplomacy();
                case 4 -> notifications();
                case 5 -> trading();
            }
        }
    }

    private void endMove() {
        this.states.values().forEach(State::endMove);
        // Удаляем те, что нельзя принять
        this.notifications.entrySet().removeIf(entry -> !entry.getValue().isCanAccept());
        // Отклоняем, те, что он мог принять
        for (Notification notification : notifications.values().stream().filter(Notification::isCanAccept).toList())
            diplomacyManager.rejectRelationRequestFromNotification(false, this, notification);
        this.alreadySendRelationRequest.clear();
    }

    private State selectState() {
        while (true) {
            int choice = Utils.nextInt("🆔 штата: ");
            if (this.states.containsKey(choice)) return this.states.get(choice);
            System.out.println(Utils.toRed("System: ") + "Такого штата у вас нет");
        }
    }

    private Build selectBuild(State state) {
        while (true) {
            int choice = Utils.nextInt("🆔 здания: ");
            if (state.getBuilds().containsKey(choice)) return state.getBuilds().get(choice);
            System.out.println(Utils.toRed("System: ") + "Такого здания у вас нет");
        }
    }

    private Shop selectShop(State state) {
        System.out.println(Utils.toYellow("\nSystem: ") + "Ваши магащины");
        state.getShops().values().forEach(System.out::println);
        System.out.println();
        while (true) {
            int choice = Utils.nextInt("🆔 магазина: ");
            if (state.getShops().containsKey(choice)) return state.getShops().get(choice);
            System.out.println(Utils.toRed("System: ") + "Такого магазина у вас нет");
        }
    }

    private Notification selectNotification() {
        while (true) {
            int choice = Utils.nextInt("🆔 запроса: ");
            if (this.notifications.containsKey(choice)) {
                Notification notification = notifications.get(choice);
                if (!notification.isCanAccept()) {
                    System.out.println(Utils.toRed("System: ") + "Этот запрос как данность, его нельзя обработать, выберите другой");
                    continue;
                }
                return notification;
            }
            System.out.println(Utils.toRed("System: ") + "Такого запроса к вам не поступало");
        }
    }

    private void whatToDoWithState(State state) {
        while (true) {
            System.out.println(Utils.toYellow("\nSystem: ") + "Выбран штат " + state);

            System.out.print("\n" +
                    """
                            0️⃣ К штатам 🏰
                            1️⃣ Здания   🏘️
                            2️⃣ Юниты    👥
                            """
            );
            //4️⃣ Торговля 💒

            if (state.isCanBuyFromShop()) System.out.println("3️⃣ Магазин  🛒");

            System.out.println();

            int choice = state.isCanBuyFromShop() ? Utils.whatToDoNext(3) : Utils.whatToDoNext(2);

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> buildings(state);
                case 3 -> {
                    Shop selectedShop = selectShop(state);
                    if (selectedShop.isWork()) {
                        shop(state, selectedShop);
                    } else {
                        System.out.println(Utils.toRed("\nSystem: ") + "Товары поступят в магазин к следующему ходу!");
                    }
                }
            }
        }
    }

    private void buildings(State state) {
        while (true) {
            System.out.println(Utils.toYellow("\nSystem: ") + "Здания в штате");
            state.getBuilds().values().forEach(System.out::println);

            System.out.println("\n" +
                    """
                            0️⃣ К штату   🏰
                            1️⃣ Выбрать   🔎
                            2️⃣ Построить ➕
                            3️⃣ Разрушить 🧨
                            """
            );

            int choice = Utils.whatToDoNext(3);

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> checkBuildInfo(state, selectBuild(state));
                case 2 -> createBuildInState(state);
                case 3 -> destroyBuildInState(state);
            }
        }
    }

    private void checkBuildInfo(State state, Build build) {
        while (true) {
            System.out.println(Utils.toYellow("\nSystem: ") + "Выбрано здание - " + build);

            boolean isCanBeUpgrade = false;

            if (Build.buildIsMaxLvl(build)) {
                System.out.println("Здание максимального уровня!");
            } else {
                Upgrade upgradeInfo = Upgrade.getUpgradeInfo(build);
                boolean isHaveLvl = Upgrade.haveLvlForUpgrade(state, build);
                isCanBeUpgrade = isHaveLvl && Upgrade.haveCurrencyForUpgrade(state, build);

                String lvl = isHaveLvl ? Utils.toGreen(upgradeInfo.requiredCapitalLvl()) : Utils.toRed(upgradeInfo.requiredCapitalLvl());

                String produce = build.getProduce().isEmptyCurrency() ? "Нет" : build.getProduce().toString();
                String consume = build.getConsume().isEmptyCurrency() ? "Нет" : build.getProduce().toString();
                String newProduce = upgradeInfo.newProduce().isEmptyCurrency() ? "Нет" : upgradeInfo.newProduce().toString();
                String newConsume = upgradeInfo.newConsume().isEmptyCurrency() ? "Нет" : upgradeInfo.newConsume().toString();

                boolean isProduceChanged = !produce.equals(newProduce);
                boolean isConsumeChanged = !consume.equals(newConsume);

                System.out.println("Улучшение требует: " + Icon.TECH + "#" + lvl);

                if (isProduceChanged) {
                    System.out.println("Производство: " + produce + " ➡️ " + newProduce);
                } else {
                    System.out.println("Производство: " + produce + " ➡️ Не изменилось");
                }

                if (isConsumeChanged) {
                    System.out.println("Потребление: " + consume + " ➡️ " + newConsume);
                } else {
                    System.out.println("Потребление: " + consume + " ➡️ Не изменилось");
                }

                System.out.println("Цена: " + ToString.havePriceToSpend(upgradeInfo.price(), state.getCurrentBalance()));
            }

            System.out.println("\n0️⃣ К зданиям 🏘️");
            if (isCanBeUpgrade) {
                System.out.println("1️⃣ Улучшить  🔧\n");
            } else {
                System.out.println();
            }

            int choice = isCanBeUpgrade ? Utils.whatToDoNext(1) : Utils.whatToDoNext(0);

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    Upgrade.upgradeBuild(state, build);
                    System.out.println(Utils.toGreen("System: ") + "Здание улучшено до " + build.getLvl() + " уровня! Баланс штата: " + ToString.forStateBalance(state));
                }
            }
        }
    }

    private void createBuildInState(State state) {
        while (true) {
            System.out.println(Utils.toYellow("System: ") + "Построить здание\n");
            List<BuildType> availableForCapitalLvl = CreateBuild.getAvailableBuildsToCreate(state);

            List<BuildType> availableToCreate = new ArrayList<>();
            List<BuildType> unavailableToCreate = new ArrayList<>();

            for (BuildType type : availableForCapitalLvl) {
                if (state.isCanTrade() && type == BuildType.TRADE) continue;
                CreateBuild createBuild = CreateBuild.getCreateBuildInfo(type);
                if (state.isHaveBalanceToSpend(createBuild.price())) {
                    availableToCreate.add(type);
                    continue;
                }
                unavailableToCreate.add(type);
            }

            System.out.println("0️⃣ К зданиям 🏘️");
            int i = 1;
            for (BuildType type : availableToCreate) {
                System.out.println(Utils.getNumberOfAction(i++) + " " + type + " : " + ToString.havePriceToSpend(CreateBuild.getCreateBuildInfo(type).price(), state.getCurrentBalance()));
            }
            for (BuildType type : unavailableToCreate) {
                System.out.println(Utils.getNumberOfAction(i++) + " " + type + " : " + ToString.havePriceToSpend(CreateBuild.getCreateBuildInfo(type).price(), state.getCurrentBalance()));
            }

            System.out.println();

            int choice = Utils.whatToDoNext(availableToCreate.size());

            if (choice == 0) return;

            BuildType type = availableToCreate.get(choice - 1);
            CreateBuild.createBuild(state, type);
            System.out.println(Utils.toGreen("System: ") + "Здание " + type + " построено! Баланс штата: " + ToString.forStateBalance(state));
        }
    }

    private void destroyBuildInState(State state) {
        while (true) {
            Build selectedToDestroy = selectBuild(state);
            if (selectedToDestroy instanceof Capital) {
                System.out.println(Utils.toRed("System: ") + "Столицу штата нельзя разрушить");
                if (state.getBuilds().size() == 1) break;
                continue;
            }
            state.destroyBuild(selectedToDestroy);
            System.out.println(Utils.toGreen("System: ") + "Здание разрушено, вы получите часть ресурсов назад");
            return;
        }
    }

    private void shop(State state, Shop shop) {
        System.out.println(Utils.toYellow("\nSystem: ") + "Выбран магазин " + shop);
        while (true) {
            System.out.println(Utils.toYellow("\nАсортимент:"));

            Map<CurrencyType, Integer> count = shop.getCurrencyCountAvailable();
            Map<CurrencyType, Integer> price = shop.getCurrencyPriceInGold();
            List<CurrencyType> currencies = new ArrayList<>(count.keySet());

            int gold = state.getCurrentBalance().get(CurrencyType.GOLD);

            System.out.println("\n0️⃣ К штату  🏰");
            int i = 1;
            for (CurrencyType currency : currencies) {
                String currencyPrice = price.get(currency) <= gold ? Utils.toGreen(price.get(currency)) : Utils.toRed(price.get(currency));
                String currencyCount = count.get(currency) == 0 ? Utils.toRed(count.get(currency)) : Utils.toGreen(count.get(currency));
                System.out.println(Utils.getNumberOfAction(i++) + " " + currency + "x" + currencyCount + " = " + currencyPrice + CurrencyType.GOLD + "/шт");
            }

            System.out.println();

            int choice = Utils.whatToDoNext(currencies.size());

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1, 2, 3, 4, 5, 6 -> { // всего 6 ресурсов
                    CurrencyType selectedCurrency = currencies.get(choice - 1);
                    if (price.get(selectedCurrency) > gold) {
                        System.out.println(Utils.toRed("\nSystem: ") + "Недостаточно золота для покупки");
                    } else {
                        if (count.get(selectedCurrency) == 0) {
                            System.out.println(Utils.toRed("\nSystem: ") + "Товар " + selectedCurrency + " закончился!");
                        } else {
                            state.getCurrentBalance().withdraw(CurrencyType.GOLD, price.get(selectedCurrency));
                            shop.buyCurrency(selectedCurrency, 1);
                            state.getCurrentBalance().deposit(selectedCurrency, 1);
                            System.out.println(Utils.toGreen("\n+1") + selectedCurrency);
                        }
                    }
                }
            }
        }
    }

    private void diplomacy() {
        while (true) {
            System.out.println(Utils.toYellow("\nSystem: ") + "Дипломатия");
            this.relations.forEach((player, type) -> System.out.println("С " + player + " - " + type));

            System.out.println("\n" +
                    """
                            0️⃣ Назад
                            1️⃣ Выбрать игрока
                            """
            );

            int choice = Utils.whatToDoNext(1);

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> whatToDoWithPlayer(diplomacyManager.selectPlayer(this));
            }
        }
    }

    private void whatToDoWithPlayer(Player player) {
        if (this.alreadySendRelationRequest.contains(player)) {
            System.out.println(Utils.toRed("\nSystem: ") + "Вы уже взаимодейстовали с " + player + " на этом ходу");
            return;
        }

        System.out.println(Utils.toYellow("\nSystem: ") + "Возможные действия с игроком " + player);
        List<RelationRequest> requests = diplomacyManager.whatICanDoWithPlayer(this, player);

        System.out.println("0️⃣ Назад");

        for (int i = 0; i < requests.size(); i++)
            System.out.println(Utils.getNumberOfAction(i + 1) + " " + requests.get(i));

        System.out.println();

        int choice = Utils.whatToDoNext(requests.size());

        if (choice == 0) return;

        RelationRequest request = requests.get(choice - 1);
        diplomacyManager.sendRequestToPlayer(this, request, player);
        this.alreadySendRelationRequest.add(player);
    }

    private void notifications() {
        while (true) {
            System.out.println(Utils.toYellow("\nSystem: ") + "Новости");

            boolean isCanAccept = false;
            boolean isCantAccept = false;

            if (this.notifications.isEmpty()) {
                System.out.println("Новостей пока что нет 💤");
            } else {
                isCanAccept = this.notifications.values().stream().anyMatch(Notification::isCanAccept);
                isCantAccept = this.notifications.values().stream().anyMatch(notification -> !notification.isCanAccept());
                this.notifications.values().forEach(System.out::println);
            }

            System.out.println("\n0️⃣ Назад");

            if (isCantAccept) System.out.println("1️⃣ Очистить");
            if (isCanAccept) System.out.println("2️⃣ Ответить");

            System.out.println();

            int choice = isCanAccept ? Utils.whatToDoNext(2) : Utils.whatToDoNext(1);

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> this.notifications.entrySet().removeIf(entry -> !entry.getValue().isCanAccept());
                case 2 -> acceptRelationRequest(selectNotification());
            }
        }
    }

    private void acceptRelationRequest(Notification notification) {
        while (true) {
            System.out.println(Utils.toYellow("\nSystem: ") + "Вы выбрали - " + notification);

            System.out.println("\n0️⃣ Назад");

            if (notification.isNeedApproval()) {
                System.out.println("1️⃣ Одобрить");
            } else {
                System.out.println("1️⃣ Принять");
            }

            System.out.println("2️⃣ Отклонить\n");

            int choice = Utils.whatToDoNext(2);

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    diplomacyManager.acceptRelationRequestFromNotification(this, notification);
                    return;
                }
                case 2 -> {
                    diplomacyManager.rejectRelationRequestFromNotification(true, this, notification);
                    return;
                }
            }
        }
    }

    public void trading() {
        while (true) {
            Map<Integer, State> statesCanTrade = this.states.entrySet().stream()
                    .filter(entry -> entry.getValue().isCanTrade() && !entry.getValue().getCurrentBalance().isEmptyCurrency())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            Map<Integer, State> canImport = statesCanTrade.entrySet().stream()
                    .filter(entry -> entry.getValue().getTrade().getCountForImport() > 0)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            Map<Integer, State> canExport = statesCanTrade.entrySet().stream()
                    .filter(entry -> entry.getValue().getTrade().getCountForExport() > 0)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            System.out.print(Utils.toYellow("\nSystem: "));
            if (warehouseForTrade.isEmptyCurrency()) {
                System.out.println("❕ На складе пусто");
            } else {
                System.out.println("На складе: " + warehouseForTrade);
            }

            if (statesCanTrade.isEmpty()) {
                System.out.println("❗ Для того, чтобы иметь возможность торговать, постойте здание для торговли");
                System.out.println("\n0️⃣ Назад\n1️⃣ Купить\n");
            } else {
                System.out.println(
                        """
                        \n0️⃣ Назад
                        1️⃣ Сделки
                        2️⃣ Мои сделки
                        3️⃣ Импортировать
                        4️⃣ Экспортировать
                        """
                );
            }

            int choice = statesCanTrade.isEmpty() ? Utils.whatToDoNext(1) : Utils.whatToDoNext(4);

            switch (choice) {
                case 0 -> { return; }
                case 1 -> otherTrades();
                case 2 -> myTrades(statesCanTrade);
                case 3 -> {
                    if (warehouseForTrade.isEmptyCurrency()) {
                        System.out.println(Utils.toRed("\nSystem: ") + "Вам нечего импортировать, у вас пустой склад");
                    } else if (canImport.isEmpty()) {
                        System.out.println(Utils.toRed("\nSystem: ") + "Имопртные мощности востановятся к следующему ходу");
                    } else {
                        importTo(selectStateFromForTrading(canImport, "Выберите штат для импорта:"));
                    }
                }
                case 4 -> {
                    if (canExport.isEmpty()) {
                        System.out.println(Utils.toRed("\nSystem: ") + "Экспортные мощности востановятся к следующему ходу");
                    } else {
                        exportFrom(selectStateFromForTrading(canExport, "Выберите штат для экспорта:"));
                    }
                }
            }
        }
    }
    private State selectStateFromForTrading(Map<Integer, State> states, String message) {
        System.out.println();
        states.values().forEach(state -> {
            Trade trade = state.getTrade();
            System.out.println(Icon.CAPITAL + "#" + state.getId() + " : " + Icon.TRADE + "#" + trade.getLvl() + " (⏬ Лимит импорта: " + trade.getCountForImport() + ", ⏫ Лимит экспорта: " + trade.getCountForExport() + ")");
        });
        System.out.println("\n" + message);
        while (true) {
            int choice = Utils.nextInt("🆔 штата: ");
            if (states.containsKey(choice)) return this.states.get(choice);
            System.out.println(Utils.toRed("System: ") + "Такого штата нет");
        }
    }

    private void otherTrades() {
        while (true) {
            TreeMap<Integer, TradingRequest> otherTrades = tradingManager.getAvailableTradingRequestsFor(this);

            boolean iCanAcceptOtherOne = otherTrades.entrySet().stream()
                    .anyMatch(entry ->
                            switch (entry.getValue().getTradingType()) {
                                case BUY -> warehouseForTrade.get(entry.getValue().getSelectedCurrency()) > 0;
                                case SELL, SWAP -> warehouseForTrade.get(entry.getValue().getExpectedCurrency()) >= entry.getValue().getCountExpected();
                            }
                    );


            System.out.println(Utils.toYellow("\nSystem: ") + "Сделки");
            System.out.println("❗ Чтобы принять сделку, убедитесь, что на вашем складе достаточно ресурсов");
            if (warehouseForTrade.isEmptyCurrency()) {
                System.out.println("❕ На складе пусто");
            } else {
                System.out.println("На складе: " + warehouseForTrade);
            }

            System.out.println();

            if (otherTrades.isEmpty()) {
                System.out.println("Пока что сделок нет 💤");
            } else {
                otherTrades.values().forEach(tradingRequest -> System.out.println(this.relations.get(tradingRequest.getFromPlayer()).getIcon() + " - " + tradingRequest));
            }

            System.out.println("\n0️⃣ Назад");
            if (iCanAcceptOtherOne) System.out.println("1️⃣ Выбрать предложение");
            System.out.println();

            int choice = iCanAcceptOtherOne ? Utils.whatToDoNext(1) : Utils.whatToDoNext(0);

            switch (choice) {
                case 0 -> { return; }
                case 1 -> {
                    if (warehouseForTrade.isEmptyCurrency()) {
                        System.out.println(Utils.toRed("System: ") + "Ваш склад пуст, вы не можете торговать, импортируйте ресурсы из штата");
                    } else {
                        acceptTradingRequest(selectAvailableToTrade(otherTrades));
                    }
                }
            }
        }
    }
    private TradingRequest selectAvailableToTrade(Map<Integer, TradingRequest> otherTrades) {
        while (true) {
            int choice = Utils.nextInt("🆔 предложения: ");
            if (otherTrades.containsKey(choice)) {
                TradingRequest tradingRequest = otherTrades.get(choice);

                // Чтобы я мог ПРОДАТЬ -> для BUY, мне нужно warehouseForTrade.get(tradingRequest.getSelectedCurrency()) >= tradingRequest.getSelectedCurrency()
                // Чтобы я мог КУПИТЬ / ОБМЕНЯТЬ -> для SELL / SWAP, мне нужно warehouseForTrade.get(tradingRequest.getExpectedCurrency()) >= tradingRequest.getExpectedCurrency()

                boolean canAcceptTrade = switch (tradingRequest.getTradingType()) {
                    case BUY -> warehouseForTrade.get(tradingRequest.getSelectedCurrency()) >= tradingRequest.getCountSelected();
                    case SELL, SWAP -> warehouseForTrade.get(tradingRequest.getExpectedCurrency()) >= tradingRequest.getCountExpected();
                };

                if (canAcceptTrade) {
                    return tradingRequest;
                } else {
                    System.out.println(Utils.toRed("System: ") + "Вам не хватает ресурсов для торговли по этому предложению");
                }
            } else {
                System.out.println(Utils.toRed("System: ") + "Такого предложения нет");
            }
        }
    }
    private void acceptTradingRequest(TradingRequest tradingRequest) {
        while (true) {
            int countSelected = tradingRequest.getCountSelected();
            CurrencyType selected = tradingRequest.getSelectedCurrency();

            int countExpected = tradingRequest.getCountExpected();
            CurrencyType expected = tradingRequest.getExpectedCurrency();

            TradingType tradingType = tradingRequest.getTradingType();

            System.out.println(Utils.toYellow("\nSystem: ") + "Вы выбрали - " + tradingRequest);

            int countCurrencyForTrade = switch (tradingType) {
                case BUY -> warehouseForTrade.get(selected);
                case SELL, SWAP -> warehouseForTrade.get(expected);
            };

            int howICanBuy = 0;
            int howICanSell = 0;

            System.out.println("\n0️⃣ Назад");
            switch (tradingType) {
                case BUY -> {
                    howICanSell = Math.min(countCurrencyForTrade, countSelected);
                    System.out.println("1️⃣ Продать " + selected + " по " + countExpected + expected + "/шт (до " + howICanSell + " шт)");
                }
                case SELL -> {
                    howICanBuy = countCurrencyForTrade >= (countExpected * countSelected) ? countSelected : Math.floorDiv(countCurrencyForTrade, countExpected);
                    System.out.println("1️⃣ Купить " + selected + " по " + countExpected + expected + "/шт (до " + howICanBuy + " шт)");
                }
                case SWAP -> System.out.println("1️⃣ Обменять " + countExpected + expected + " за " + countSelected + selected);
            }
            System.out.println();

            /*
            boolean canBuyAll = myExpectedCurrencyCount >= (tradingRequest.getCountExpected() * tradingRequest.getCountSelected());
            int howICanBuy1 = canBuyAll ? tradingRequest.getCountSelected() : Math.floorDiv(myExpectedCurrencyCount, tradingRequest.getCountExpected()); // Доступно для ONE_TO_N

            System.out.println("\n0️⃣ Назад");
            switch (tradingRequest.getTradingType()) {
                case BUY -> System.out.println("1️⃣ Продать " + selected + "/" + tradingRequest.getCountExpected() + tradingRequest.getExpectedCurrency() + " (до " + howICanBuy1 + " шт)");
                case SELL -> System.out.println("1️⃣ Купить " + selected + "/" + tradingRequest.getCountExpected() + tradingRequest.getExpectedCurrency() + " (до " + howICanBuy1 + " шт)");
                case ALL_TO_ALL -> System.out.println("1️⃣ Обменять " + tradingRequest.getCountSelected() + tradingRequest.getSelectedCurrency() + " на " + tradingRequest.getCountExpected() + tradingRequest.getExpectedCurrency());
            }
            System.out.println();
            */

            int choice = Utils.whatToDoNext(1);

            switch (choice) {
                case 0 -> { return; }
                case 1 -> {
                    switch (tradingType) {
                        case BUY -> tradingManager.acceptBuyRequest(this, tradingRequest, Utils.selectNumber(1, howICanSell, "Количество " + selected + " для продажи"));
                        case SELL -> tradingManager.acceptSellRequest(this, tradingRequest, Utils.selectNumber(1, howICanBuy, "Количество " + selected + " для покупки"));
                        case SWAP -> tradingManager.acceptSwapRequest(this, tradingRequest);
                    }
                    return;
                }
            }
        }
    }

    private void myTrades(Map<Integer, State> statesCanTrade) {
        while (true) {
            Map<Integer, State> canExport = statesCanTrade.entrySet().stream()
                    .filter(entry -> entry.getValue().getTrade().getCountForExport() > 0)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            TreeMap<Integer, TradingRequest> myTrades = tradingManager.getMyTradingRequest(this);
            boolean canCreateTrade = !canExport.isEmpty();
            boolean haveTradingRequest = !myTrades.isEmpty();

            System.out.println(Utils.toYellow("\nSystem: ") + "Мои сделки");
            System.out.println("❗ Чтобы создать сделку, убедитесь, что ваш штат может экспортировать");
            if (warehouseForTrade.isEmptyCurrency()) {
                System.out.println("❕ На складе пусто");
            } else {
                System.out.println("На складе: " + warehouseForTrade);
            }

            System.out.println();

            if (haveTradingRequest) {
                myTrades.values().forEach(tradingRequest -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("📜#").append(tradingRequest.getId()).append(" для [");
                    for (RelationType type : tradingRequest.getAvailableFor()) sb.append(type.getIcon());
                    sb.append("]");
                    switch (tradingRequest.getTradingType()) {
                        case BUY -> sb.append(" ⏪ покупка ").append(tradingRequest.getCountSelected()).append(tradingRequest.getSelectedCurrency()).append(" по ").append(tradingRequest.getCountExpected()).append(tradingRequest.getExpectedCurrency()).append("/шт");
                        case SELL -> sb.append(" ⏩ продажа ").append(tradingRequest.getCountSelected()).append(tradingRequest.getSelectedCurrency()).append(" по ").append(tradingRequest.getCountExpected()).append(tradingRequest.getExpectedCurrency()).append("/шт");
                        case SWAP -> sb.append(" 🔄 обмен ").append(tradingRequest.getCountSelected()).append(tradingRequest.getSelectedCurrency()).append(" на ").append(tradingRequest.getCountExpected()).append(tradingRequest.getExpectedCurrency());
                    }
                    System.out.println(sb);
                });
            } else {
                System.out.println("Ваших сделок нет 💤");
            }

            int choice;

            System.out.println("\n0️⃣ Назад");
            if (canCreateTrade) {
                if (haveTradingRequest) {
                    System.out.println("1️⃣ Создать сделку\n2️⃣ Отменить сделку\n");
                    choice = Utils.whatToDoNext(2);
                } else {
                    System.out.println("1️⃣ Создать сделку\n");
                    choice = Utils.whatToDoNext(1);
                }
            } else {
                if (haveTradingRequest) {
                    System.out.println("1️⃣ Отменить сделку\n");
                    choice = Utils.whatToDoNext(1);
                } else {
                    System.out.println();
                    choice = Utils.whatToDoNext(0);
                }
            }

            switch (choice) {
                case 0 -> { return; }
                case 1 -> {
                    if (canCreateTrade) {
                        createTradingRequest(selectStateFromForTrading(canExport, "Выберите штат для экспорта:"));
                    } else {
                        tradingManager.removeTradingRequest(selectTradingRequestById(myTrades));
                    }
                }
                case 2 -> tradingManager.removeTradingRequest(selectTradingRequestById(myTrades));
            }
        }
    }
    private TradingRequest selectTradingRequestById(Map<Integer, TradingRequest> trades) {
        while (true) {
            int choice = Utils.nextInt("🆔 предложения: ");
            if (trades.containsKey(choice)) return trades.get(choice);
            System.out.println(Utils.toRed("System: ") + "Такого предложения нет");
        }
    }
    private void createTradingRequest(State state) {
        TradingType tradingType = TradingManager.selectTradingTypeForTrade();

        CurrencyType selectedCurrency = null;
        int countSelected = 0;
        CurrencyType expectedCurrency = null;
        int countExpected = 0;

        switch (tradingType) {
            case BUY -> {
                selectedCurrency = TradingManager.selectCurrencyTypeForTrade(state, tradingType);
                expectedCurrency = TradingManager.selectCurrencyTypeForTradeExcludeCurrencyType(selectedCurrency, tradingType);

                countExpected = TradingManager.selectCountExpectedCurrencyForTrade(state, selectedCurrency, countSelected, expectedCurrency, tradingType);

                int maxPrice = Math.min(state.getTrade().getCountForExport(), state.getCurrentBalance().get(expectedCurrency));
                int canBuy = Math.floorDiv(maxPrice, countExpected);
                //тут пришла цена

                countSelected = TradingManager.selectCountSelectedCurrencyForBuyTrade(selectedCurrency, canBuy, expectedCurrency, countExpected);
            }
            case SELL, SWAP -> {
                selectedCurrency = TradingManager.selectCurrencyTypeForTrade(state, tradingType);
                countSelected = TradingManager.selectCountSelectedCurrencyForSellOrSwapTrade(selectedCurrency, state, tradingType);

                expectedCurrency = TradingManager.selectCurrencyTypeForTradeExcludeCurrencyType(selectedCurrency, tradingType);
                countExpected = TradingManager.selectCountExpectedCurrencyForTrade(state, selectedCurrency, countSelected, expectedCurrency, tradingType);
            }
        }

        Trade whoCreateTrade = state.getTrade();

        List<RelationType> availableFor = TradingManager.selectAvailableRelationsForTrade(whoCreateTrade);

        /**
         * Покупка:
         * 1. selectedCurrency - Что купить 🛢️
         * 2. expectedCurrency - За что купить [💰🍒🌳🧱💎] + количество -> 50💰
         * 3. countExpectedCurrency - Цена - Сколько готов отдать 💰 за 🛢️/шт
         *      Нужно рассчитать диапазон цены, учитывая:
         *      - state.getCurrentBalance.get(💰) - сколько у тебя 💰
         *      - countExport - сколько ты его можешь поставить
         *
         *      if (countExport >= state.getCurrentBalance.get(💰))
         *           если countExport - то верхний диапазон цены это state.getCurrentBalance.get(💰)
         *           если state.getCurrentBalance.get(💰) - то верхний диапазон цены это countExport
         *
         *           Пример 1. countExport >= state.getCurrentBalance.get(💰) -> countExport = [1, 30]
         *                  Вводим 30 -> говорим, что ты можешь купить Math.floorDiv(state.getCurrentBalance.get(💰), 30);
         *                  Вводим 10 -> говорим, что ты можешь купить Math.floorDiv(state.getCurrentBalance.get(💰), 10);
         *                  Вводим 7 -> говорим, что ты можешь купить Math.floorDiv(state.getCurrentBalance.get(💰), 7);
         *
         *           Пример 2. state.getCurrentBalance.get(💰) > countExport
         *                  Вводим 30 -> говорим, что ты можешь купить Math.floorDiv(countExport, 30);
         *                  Вводим 17 -> говорим, что ты можешь купить Math.floorDiv(countExport, 17);
         *                  Вводим 3 -> говорим, что ты можешь купить Math.floorDiv(countExport, 3);
         *
         * 4. countSelectedCurrency - Должно расчитаться
         */

        /**
         * Продажа
         * 1. selectedCurrency - Что 🛢️
         * 2. countSelectedCurrency - Сколько
         * 3. expectedCurrency - По чем
         * 4. countExpectedCurrency
         */

        switch (tradingType) {
            case BUY -> {
                state.getCurrentBalance().withdraw(expectedCurrency, countSelected * countExpected);
                whoCreateTrade.deliveryExport(countSelected * countExpected);
            }
            case SELL, SWAP -> {
                state.getCurrentBalance().withdraw(selectedCurrency, countSelected);
                whoCreateTrade.deliveryExport(countSelected);
            }
        }

        tradingManager.createTradingRequest(this, availableFor, selectedCurrency, countSelected, tradingType, expectedCurrency, countExpected);
    }

    private void importTo(State state) {
        while (true) {
            System.out.println(Utils.toYellow("\nSystem: ") + "⏬ Импорт в " + Icon.CAPITAL + "#" + state.getId() + " (Лимит: " + state.getTrade().getCountForImport() + ")");
            System.out.println("\nВыберите ресурс, который хотите импортировать:\n0️⃣ Назад");

            List<CurrencyType> availableToImport = CurrencyType.getAvailableCurrencyTypeFromCurrency(warehouseForTrade);

            int i = 1;
            for (CurrencyType type : availableToImport) {
                System.out.println(Utils.getNumberOfAction(i++) + " " + warehouseForTrade.get(type) + type);
            }
            System.out.println();

            int choice = Utils.whatToDoNext(availableToImport.size());

            switch (choice) {
                case 0 -> { return; }
                case 1, 2, 3, 4, 5, 6 -> {
                    CurrencyType selectedCurrencyType = availableToImport.get(choice - 1);

                    int maxToImport = Math.min(warehouseForTrade.get(selectedCurrencyType), state.getTrade().getCountForImport());
                    System.out.println();
                    int toImport = Utils.selectNumber(1, maxToImport, "Количество " + selectedCurrencyType + " для импорта");

                    warehouseForTrade.withdraw(selectedCurrencyType, toImport);
                    state.getCurrentBalance().deposit(selectedCurrencyType, toImport);
                    state.getTrade().deliveryImport(toImport);

                    System.out.println(Utils.toGreen("\nSystem: ") + "В штат поставлено " + toImport + selectedCurrencyType);

                    if (state.getTrade().getCountForImport() < 1) {
                        System.out.println(Utils.toRed("\nSystem: ") + "Импортные мощности исчерпаны, дождитесь востановления на следующем ходу");
                        return;
                    }
                    if (warehouseForTrade.isEmptyCurrency()) {
                        System.out.println(Utils.toRed("\nSystem: ") + "Склад пуст, импортировать больше нечего");
                        return;
                    }
                }
            }
        }
    }
    private void exportFrom(State state) {
        while (true) {
            System.out.println(Utils.toYellow("\nSystem: ") + "⏫ Экспорт из " + Icon.CAPITAL + "#" + state.getId() + " (Лилит: " + state.getTrade().getCountForExport() + ")");
            System.out.println("\nВыберите ресурс, который хотите экспортировать:\n0️⃣ Назад");

            List<CurrencyType> availableToExport = CurrencyType.getAvailableCurrencyTypeFromCurrency(state.getCurrentBalance());

            int i = 1;
            for (CurrencyType type : availableToExport) {
                System.out.println(Utils.getNumberOfAction(i++) + " " + state.getCurrentBalance().get(type) + type);
            }
            System.out.println();

            int choice = Utils.whatToDoNext(availableToExport.size());

            switch (choice) {
                case 0 -> { return; }
                case 1, 2, 3, 4, 5, 6 -> {
                    CurrencyType selectedCurrencyType = availableToExport.get(choice - 1);

                    int maxToExport = Math.min(state.getCurrentBalance().get(selectedCurrencyType), state.getTrade().getCountForExport());
                    System.out.println();
                    int toExport = Utils.selectNumber(1, maxToExport, "Количество " + selectedCurrencyType + " для экспорта");

                    state.getCurrentBalance().withdraw(selectedCurrencyType, toExport);
                    state.getTrade().deliveryExport(toExport);
                    warehouseForTrade.deposit(selectedCurrencyType, toExport);

                    System.out.println(Utils.toGreen("\nSystem: ") + "На склад поставлено " + toExport + selectedCurrencyType);

                    if (state.getTrade().getCountForExport() < 1) {
                        System.out.println(Utils.toRed("\nSystem: ") + "Экспортные мощности исчерпаны, дождитесь востановления на следующем ходу");
                        return;
                    }
                    if (state.getCurrentBalance().isEmptyCurrency()) {
                        System.out.println(Utils.toRed("\nSystem: ") + "Баланс штата исчерпан, экспортировать больше нечего");
                        return;
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && color.equals(player.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color);
    }

    @Override
    public String toString() {
        return this.color.toString() + "#" + id;
    }


    @Override
    public int compareTo(Player o) {
        if (this.id < o.id) return -1;
        if (this.id > o.id) return 1;
        return 0;
    }
}
