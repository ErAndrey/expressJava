package new_task.task_7;

import new_task.task_7.bet.Bet;
import new_task.task_7.bet.BetResult;
import new_task.task_7.bonuses.*;
import new_task.task_7.action_panel.ActionPanel;
import new_task.task_7.action_panel.ActionPanels;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static new_task.task_7.bet.BetType.REGULAR;
import static new_task.task_7.bet.BetType.FREE_BET;

import static new_task.task_7.bet.BetResult.WIN;
import static new_task.task_7.bet.BetResult.LOSE;
import static new_task.task_7.bet.BetResult.RETURN;

public final class Casino {
    private static final Random RANDOM = new Random();

    public static final double COMMISSION_FOR_WITHDRAW = 4.95; // В %

    //toDo playWords
    private final int[] scorePlayers = new int[2];

    private final Player player;
    private double balance;

    @FunctionalInterface
    private interface GameRunnable {
        void run() throws InterruptedException;
    }

    public Casino(Player player, int balance) {
        this.player = player;
        this.balance = balance;
    }

    public void changeBalance(double amount) {
        this.balance += amount;
    }

    private Bet selectBet(Game game, double maxMultiplier) {
        int betAmount;
        int minBet = game.getMinBalanceForPlay() / 4;
        int maxBet = (int) (this.balance / maxMultiplier);
        while (true) {
            betAmount = Utils.nextInt("Укажите сумму ставки: ");
            if (betAmount < minBet) {
                System.out.println(Utils.toError("System: ") + "Минимальная сумма ставки " + Utils.formatCurrency(minBet));
                continue;
            }
            if (betAmount > player.getBalance()) {
                System.out.println(Utils.toError("System: ") + "Недостаточно средств! Ваш баланс: " + Utils.formatCurrency(player.getBalance()));
                continue;
            }
            if (betAmount > maxBet) {
                System.out.println(Utils.toError("System: ") + "Извините, мы не можем принять такую ставку. Максимальная ставка: " + Utils.formatCurrency(maxBet));
                continue;
            }
            return new Bet(game.getName(), betAmount);
        }
    }

    private Bet selectBetWithFreeBetCheck(Game game, double maxMultiplier) {
        TreeMap<Integer, FreeBetBonus> activatedFreeBetBonuses = player.getFreeBetBonuses();
        if (!activatedFreeBetBonuses.isEmpty()) {

            TreeMap<Integer, FreeBetBonus> availableFreeBet = activatedFreeBetBonuses.entrySet().stream()
                    .filter(entry -> entry.getValue().getMaxMultiplier() >= maxMultiplier)
                    .filter(entry -> entry.getValue().getFreeBetAmount() < ((int) (this.balance / Multipliers.getClearMultiplier(maxMultiplier - 1))))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (v1, v2) -> v1,
                            TreeMap::new
                    ));

            if (availableFreeBet.isEmpty()) return selectBet(game, maxMultiplier);

            boolean isOnlyOneAvailableFreeBet = availableFreeBet.size() == 1;

            if (isOnlyOneAvailableFreeBet) {
                FreeBetBonus freeBetBonus = availableFreeBet.firstEntry().getValue();
                System.out.println(Utils.toInfo("\nSystem: ") + "Для игры доступен единственный фрибет на " + Utils.toSuccess(Utils.formatCurrency(freeBetBonus.getFreeBetAmount())) + " с Кф до " + Utils.toAccent("x" + freeBetBonus.getMaxMultiplier())
                        + "\n" +
                        """
                        0. Пропустить
                        1. Использовать фрибет
                        """
                );
            } else {
                System.out.println(Utils.toInfo("\nSystem: ") + "У вас есть несколько доступных фрибетов для игры!"
                        + "\n" +
                        """
                        0. Пропустить
                        1. Выбрать фрибет
                        """
                );
            }

            int choice = Utils.whatToDoNext(1);

            if (choice == 1) {
                if (isOnlyOneAvailableFreeBet) {
                    player.removeFreeBetBonus(availableFreeBet.firstEntry().getValue());
                    return new Bet(game.getName(), availableFreeBet.firstEntry().getValue());
                } else {
                    System.out.println("\nВыберите один из бонусов:");
                    availableFreeBet.values().forEach(System.out::println);
                    System.out.println();
                    while (true) {
                        int selectedBonusNumber = Utils.nextInt("Номер бонуса: ");
                        if (availableFreeBet.containsKey(selectedBonusNumber)) {
                            FreeBetBonus selectedFreeBetBonus = availableFreeBet.get(selectedBonusNumber);
                            player.removeFreeBetBonus(selectedFreeBetBonus);
                            return new Bet(game.getName(), selectedFreeBetBonus);
                        }
                        System.out.println(Utils.toError("System: ") + "Выберите доступный бонус");
                    }
                }
            }
        }
        return selectBet(game, maxMultiplier);
    }

    private void applyBet(Bet bet) {
        switch (bet.getType()) {
            case REGULAR -> {
                System.out.print(Utils.toInfo("System: ") + "Списание ставки. Ваш баланс " + Utils.formatCurrency(this.player.getBalance()) + Utils.toError(" -> "));
                this.player.withdraw(bet.getAmount(), WhoChangePlayerBalance.CASINO);
                this.balance += bet.getAmount();
                System.out.println(Utils.formatCurrency(this.player.getBalance()));
            }
            case FREE_BET -> {
                bet.setAmount(bet.getFreeBetBonus().getFreeBetAmount());
            }
        }
    }

    private void printBet(Bet bet, String playerChoice) {
        String freeBetText = bet.getType() == FREE_BET ? Utils.toAccent("Фрибет ") : "";
        System.out.println(Utils.toInfo("\nВаша ставка: ") + freeBetText + playerChoice + ", " + Utils.formatCurrency(bet.getAmount()) + ", Кф " + Utils.toAccent("х" + bet.getMultiplier()) + "\n");
    }

    private void printBet(Bet bet) {
        String freeBetText = bet.getType() == FREE_BET ? Utils.toAccent("Фрибет ") : "";
        System.out.println(Utils.toInfo("\nВаша ставка: ") + freeBetText + Utils.formatCurrency(bet.getAmount()) + "\n");
    }

    private void returnBet(Bet bet) {
        if (bet.getType() == REGULAR) {
            System.out.print(Utils.toInfo("System: ") + "Возврат ставки. Ваш баланс " + Utils.formatCurrency(this.player.getBalance()) + Utils.toInfo(" -> "));
            player.deposit(bet.getAmount(), WhoChangePlayerBalance.CASINO);
            this.balance -= bet.getAmount();
            System.out.println(Utils.formatCurrency(this.player.getBalance()));
        } else {
            System.out.println(Utils.toInfo("System: ") + "Возврат ставки. Ваш фрибет на " + Utils.toSuccess(Utils.formatCurrency(bet.getFreeBetBonus().getFreeBetAmount())) + " был возвращен");
            this.player.addFreeBetBonus(bet.getFreeBetBonus());
        }
        updatePlayerStatistic(bet);
    }

    private void resolveBet(Bet bet) {
        switch (bet.getResult()) {
            case WIN -> {
                double winAmount = bet.getWinAmount();
                System.out.println(Utils.toSuccess("Ваш выигрыш: ") + Utils.formatCurrency(winAmount));

                if (winAmount > this.balance) {
                    if (bet.getType() == REGULAR) {
                        System.out.println(Utils.toError("System: ") + "У казино недостаточно средств для выплаты! Текущая ставка была возвращена.");
                    } else {
                        System.out.println(Utils.toError("System: ") + "У казино недостаточно средств для выплаты! Фрибет был возвращен.");
                    }
                    bet.setResult(RETURN);
                    returnBet(bet);
                    return;
                }

                System.out.print(Utils.toInfo("System: ") + "Зачисление выигрыша. Ваш баланс " + Utils.formatCurrency(this.player.getBalance()) + Utils.toSuccess(" -> "));

                this.player.deposit(winAmount, WhoChangePlayerBalance.CASINO);
                this.balance -= winAmount;
                System.out.println(Utils.formatCurrency(this.player.getBalance()));
                updatePlayerStatistic(bet);
            }
            case LOSE -> {
                if (bet.getType() == REGULAR) {
                    System.out.println(Utils.toError("Проигрыш: ") + Utils.formatCurrency(bet.getAmount()));
                } else {
                    System.out.println(Utils.toError("Проигрыш: ") + "Фрибет проиграл");
                }
                System.out.println(Utils.toInfo("System: ") + "Ваш баланс " + Utils.formatCurrency(this.player.getBalance()));
                updatePlayerStatistic(bet);
            }
            case RETURN -> returnBet(bet);
        }
        System.out.println();
    }

    private void updatePlayerStatistic(Bet bet) {
        switch (bet.getResult()) {
            case WIN -> {
                double winAmount = bet.getType() == FREE_BET ? bet.getWinAmount() : bet.getWinAmount() - bet.getAmount();
                this.player.updateTotalWinAmount(winAmount);
                this.player.updateTotalWins();
                this.player.updateCurrentWins();
                this.player.resetCurrentLose();
                if (this.player.getCurrentWins() > this.player.getMaxWins()) this.player.updateMaxWins(player.getCurrentWins());
                if (winAmount > this.player.getMaxWinAmount()) this.player.updateMaxWinAmount(winAmount);
            }
            case LOSE -> {
                double loseAmount = bet.getType() == FREE_BET ? 0 : bet.getAmount();
                this.player.updateTotalLoseAmount(loseAmount);
                this.player.updateTotalLose();
                this.player.updateCurrentLose();
                this.player.resetCurrentWins();
                if (this.player.getCurrentLose() > this.player.getMaxLose()) this.player.updateMaxLose(player.getCurrentLose());
                if (loseAmount > this.player.getMaxLoseAmount()) this.player.updateMaxLoseAmount(loseAmount);
            }
            case RETURN -> {
                if (bet.getType() == REGULAR) this.player.updateTotalReturnAmount(bet.getAmount());
                this.player.updateTotalReturns();
                this.player.resetCurrentWins();
                this.player.resetCurrentLose();
            }
        }
        this.player.addBetToHistory(bet);
        boolean isWin = bet.getResult() == WIN;
        canAddBonus(isWin);
    }

    private void canAddBonus(boolean isWin) {
        LocalDateTime fromData = LocalDateTime.now();
        int initialBonusSize = player.getAvailableBonuses().size();
        int totalGames = player.getTotalGames();

        switch (totalGames % 100) {
            case 16 -> player.addAvailableBonus(new FreeBetBonus(5_000, 3.0, fromData, 6, 0));
            case 32 -> player.addAvailableBonus(new DepositBonus(100.0, fromData, 1, 0));
            case 48 -> player.addAvailableBonus(new CashBonus(15_000, fromData, 2, 0));
            case 64 -> player.addAvailableBonus(new FreeBetBonus(15_000, 3.5, fromData, 6, 0));
            case 80 -> player.addAvailableBonus(new DepositBonus(200.0, fromData, 2, 0));
            case 96 -> player.addAvailableBonus(new CashBonus(45_000, fromData, 12, 0));
        }

        if (isWin) {
            int wins = player.getTotalWins();
            int currentWins = player.getCurrentWins();
            switch (wins % 100) {
                case 33 -> player.addAvailableBonus(new FreeBetBonus(10_000, 5.0, fromData, 0, 30));
                case 66 -> player.addAvailableBonus(new FreeBetBonus(20_000, 3.0, fromData, 0, 45));
                case 99 -> player.addAvailableBonus(new FreeBetBonus(30_000, 2.5, fromData, 1, 0));
            }
            switch (currentWins) {
                case 5 -> player.addAvailableBonus(new FreeBetBonus(5_555, 3.5, fromData, 1, 15));
                case 10 -> player.addAvailableBonus(new FreeBetBonus(55_555, 1.75 ,fromData, 0, 45));
            }
        } else {
            int lose = player.getTotalLose();
            int currentLose = player.getCurrentLose();
            switch (lose % 100) {
                case 33 -> player.addAvailableBonus(new FreeBetBonus(7_000, 4.0, fromData, 1, 0));
                case 66 -> player.addAvailableBonus(new FreeBetBonus(14_000, 3.5, fromData, 2, 0));
                case 99 -> player.addAvailableBonus(new FreeBetBonus(28_000, 3.0, fromData, 3, 0));
            }
            switch (currentLose) {
                case 5 -> player.addAvailableBonus(new DepositBonus(30.0, fromData, 0, 30));
                case 8 -> player.addAvailableBonus(new DepositBonus(50.0, fromData, 0, 45));
                case 11 -> player.addAvailableBonus(new DepositBonus(100.0, fromData, 1, 0));
            }
        }

        if (player.getAvailableBonuses().size() > initialBonusSize) System.out.println(Utils.toSuccess("System: ") + "Вам доступен новый бонус!");
    }

    public void play() throws InterruptedException {
        while (true) {
            System.out.println(ActionPanels.ACTION_PANELS.get(ActionPanel.PLAY));

            int action = Utils.whatToDoNext(7);

            switch (action) {
                case 0 -> {
                    String playerName = Utils.toAccent(player.getName());
                    int playerCurrentWin = player.getCurrentWins();
                    if (player.getBalance() < 1000.0) {
                        if (playerCurrentWin == 0) {
                            System.out.println(playerName + ", не переживай, в следующей игре обязательно повезет!");
                        } else {
                            System.out.println(playerName + ", пополняй баланс и возвращайся за победой!");
                        }
                    } else {
                        if (playerCurrentWin > 2) {
                            System.out.println(playerName + ", удача на твоей стороне, возвращайся скорее!");
                        } else {
                            System.out.println(playerName + ", ждем вас снова!");
                        }
                    }
                    return;
                }
                case 1 -> selectSlots();
                case 2 -> playGameLoop(Game.ODDS_OR_EVENS, this::playEven);
                case 3 -> playGameLoop(Game.FLIP_COIN, this::playCoinFlip);
                case 4 -> playGameLoop(Game.HIGHER_OR_LOWER, this::playHigherOrLower);
                case 5 -> playGameLoop(Game.GUESS_THE_WORDS, this::playWords);
                case 6 -> playGameLoop(Game.BLACKJACK, this::playBlackjack);
                case 7 -> selectRPS();
            }
        }
    }

    private void checkWinSlotsTable() {
        while (true) {
            int maxWidthFor5Slots = Utils.getMaxWidthForTableSlots(Multipliers.SLOTS_MULTIPLIERS, "5_");
            int maxWidthFor4Slots = Utils.getMaxWidthForTableSlots(Multipliers.SLOTS_MULTIPLIERS, "4_");
            int maxWidthFor3Slots = Utils.getMaxWidthForTableSlots(Multipliers.SLOTS_MULTIPLIERS, "3_");
            int maxWidthFor2Slots = Utils.getMaxWidthForTableSlots(Multipliers.SLOTS_MULTIPLIERS, "2_");

            System.out.println(Utils.toInfo("\nТаблица возможных выигрышей и их коэффициентов:"));

            System.out.println("|  "
                    + Utils.toCenter("5 слотов", maxWidthFor5Slots) + "  |  "
                    + Utils.toCenter("4 слота", maxWidthFor4Slots) + "  |  "
                    + Utils.toCenter("3 слота", maxWidthFor3Slots) + "  |  "
                    + Utils.toCenter("2 слота", maxWidthFor2Slots) + "  |"
            );

            System.out.println("|  "
                    + Utils.toCenter("5s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_5s"), maxWidthFor5Slots) + "  |  "
                    + Utils.toCenter("4s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("4_4s"), maxWidthFor4Slots) + "  |  "
                    + Utils.toCenter("3s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("3_3s"), maxWidthFor3Slots) + "  |  "
                    + Utils.toCenter("2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("2_2s"), maxWidthFor2Slots) + "  |"
            );

            System.out.println("|  "
                    + Utils.toCenter("4r+2s+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_4r+2s+2s"), maxWidthFor5Slots) + "  |  "
                    + Utils.toCenter("2r+2r -> x" + Multipliers.SLOTS_MULTIPLIERS.get("4_2r+2r"), maxWidthFor4Slots) + "  |  "
                    + Utils.toCenter("2r -> x" + Multipliers.SLOTS_MULTIPLIERS.get("3_2r"), maxWidthFor3Slots) + "  |"
            );

            System.out.println("|  "
                    + Utils.toCenter("4s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_4s"), maxWidthFor5Slots) + "  |  "
                    + Utils.toCenter("2r+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("4_2r+2s"), maxWidthFor4Slots) + "  |  "
                    + Utils.toCenter("2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("3_2s"), maxWidthFor3Slots) + "  |"
            );

            System.out.println("|  "
                    + Utils.toCenter("3s+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_3s+2s"), maxWidthFor5Slots) + "  |  "
                    + Utils.toCenter("3r+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("4_3r+2s"), maxWidthFor4Slots) + "  |"
            );

            System.out.println("|  "
                    + Utils.toCenter("3r+2r -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_3r+2r"), maxWidthFor5Slots) + "  |  "
                    + Utils.toCenter("2s+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("4_2s+2s"), maxWidthFor4Slots) + "  |"
            );

            System.out.println("|  "
                    + Utils.toCenter("4r+3s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_4r+3s"), maxWidthFor5Slots) + "  |  "
                    + Utils.toCenter("3s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("4_3s"), maxWidthFor4Slots) + "  |"
            );

            System.out.println("|  "
                    + Utils.toCenter("2r+3s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_2r+3s"), maxWidthFor5Slots) + "  |  "
                    + Utils.toCenter("2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("4_2s"), maxWidthFor4Slots) + "  |"
            );

            System.out.println("|  "
                    + Utils.toCenter("3r+2s+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_3r+2s+2s"), maxWidthFor5Slots) + "  |  "
                    + Utils.toCenter("2r -> x" + Multipliers.SLOTS_MULTIPLIERS.get("4_2r"), maxWidthFor4Slots) + "  |"
            );

            System.out.println("|  " + Utils.toCenter("3s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_3s"), maxWidthFor5Slots) + "  |");
            System.out.println("|  " + Utils.toCenter("2s+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_2s+2s"), maxWidthFor5Slots) + "  |");
            System.out.println("|  " + Utils.toCenter("3r+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_3r+2s"), maxWidthFor5Slots) + "  |");
            System.out.println("|  " + Utils.toCenter("2r+2r -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_2r+2r"), maxWidthFor5Slots) + "  |");
            System.out.println("|  " + Utils.toCenter("2r+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_2r+2s"), maxWidthFor5Slots) + "  |");
            System.out.println("|  " + Utils.toCenter("3r -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_3r"), maxWidthFor5Slots) + "  |");
            System.out.println("|  " + Utils.toCenter("2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_2s"), maxWidthFor5Slots) + "  |");
            System.out.println("|  " + Utils.toCenter("2r -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_2r"), maxWidthFor5Slots) + "  |");

            System.out.println(Utils.toInfo("\nПояснение:"));
            System.out.print(
                    """
                    5s / 4s / 3s - последовательность (в ряд) из - 5 / 4 / 3 чисел, примеры: [55555] / [04444] / [10333] / [333] / [22]
                    3r / 2r+2r - совпадение (наличие в результате) - 3 / 2 / 2ух пар чисел, примеры: [30313] - 3 совпадения (3) / [12012] - 2 совпадения (1 и 2)
                    3r+2s / 4r+2s+2s - совпадения + последовательности, примеры: [55015] - 3 повторения (5) + одна последовательность (5), [55055] - 4 повторения (5) + две последовательности по 2 (5)
                    """
            );

            System.out.println(Utils.toInfo("\nSystem: ") + "Чтобы вернуться назад выберите 0\n");

            if (Utils.whatToDoNext(0) == 0) return;
        }
    }

    private void selectSlots() throws InterruptedException {
        while (true) {
            System.out.println(Utils.toInfo("\nSystem: ") + "Для начала игры, выберите количество слотов!\n");

            System.out.println(
                    """
                    0. Назад
                    1. Посмотреть таблицу возможных выигрышей
                    2. Крутить 2 слота
                    3. Крутить 3 слота
                    4. Крутить 4 слота
                    5. Крутить 5 слотов
                    """
            );

            int choice = Utils.whatToDoNext(5);

            switch (choice) {
                case 0 -> { return; }
                case 1 -> checkWinSlotsTable();
                case 2 -> playGameLoop(Game.SLOTS_2, () -> playSlotsGame(choice));
                case 3 -> playGameLoop(Game.SLOTS_3, () -> playSlotsGame(choice));
                case 4 -> playGameLoop(Game.SLOTS_4, () -> playSlotsGame(choice));
                case 5 -> playGameLoop(Game.SLOTS_5, () -> playSlotsGame(choice));
            }
        }
    }

    private Map<Integer, Integer> getRepeatFromSlotResult(int[] results) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int r : results) {
            frequency.merge(r, 1, Integer::sum);
        }
        Map<Integer, Integer> repeats = new TreeMap<>();
        for (int count : frequency.values()) {
            if (count >= 2) {
                repeats.merge(count, 1, Integer::sum);
            }
        }
        return repeats;
    }

    private Map<Integer, Integer> getSequenceFromSlotResult(int[] results) {
        TreeMap<Integer, Integer> sequenceCount = new TreeMap<>();
        int currentSequenceLength = 1;
        for (int i = 0; i < results.length - 1; i++) {
            if (results[i] == results[i + 1]) {
                currentSequenceLength++;
            } else {
                if (currentSequenceLength >= 2) {
                    sequenceCount.merge(currentSequenceLength, 1, Integer::sum);
                }
                currentSequenceLength = 1;
            }
        }
        if (currentSequenceLength >= 2) {
            sequenceCount.merge(currentSequenceLength, 1, Integer::sum);
        }
        return sequenceCount;
    }

    private Map<Integer, Integer> getCleanRepeatFromSlotWithoutSequence(Map<Integer, Integer> repeat, Map<Integer, Integer> sequence) {
        Map<Integer, Integer> cleanRepeat = new TreeMap<>();
        for (Map.Entry<Integer, Integer> entry : repeat.entrySet()) {
            if (!sequence.containsKey(entry.getKey())) {
                cleanRepeat.put(entry.getKey(), entry.getValue());
                continue;
            }
            int currentRepeat = entry.getValue() - sequence.get(entry.getKey());
            if (currentRepeat == 0) continue;
            cleanRepeat.put(entry.getKey(), currentRepeat);
        }
        return cleanRepeat;
    }

    private void calculateSlotGameResult(Bet bet, int[] slotResults) {
        int slotCount = slotResults.length;

        Map<Integer, Integer> sequence = getSequenceFromSlotResult(slotResults);
        Map<Integer, Integer> clearRepeat = getCleanRepeatFromSlotWithoutSequence(getRepeatFromSlotResult(slotResults), sequence);

        double multiplier = Multipliers.getMultiplierForSlots(slotCount, sequence, clearRepeat);

        bet.setMultiplier(multiplier);

        if (multiplier > 0) {
            bet.setResult(WIN);
            System.out.println(" Кф " + Utils.toAccent("x" + multiplier));
        } else {
            bet.setResult(LOSE);
        }
    }

    private void playSlotsGame(int slotCount) throws InterruptedException {
        Game game = switch (slotCount) {
            case 2 -> Game.SLOTS_2;
            case 3 -> Game.SLOTS_3;
            case 4 -> Game.SLOTS_4;
            case 5 -> Game.SLOTS_5;
            default -> throw new IllegalArgumentException("Указано неверное количество слотов");
        };
        // Средне-ожидаемый выигрыш кроме 2ух слотов
        double expectedMultiplier = switch (slotCount) {
            case 2 -> Multipliers.SLOTS_MULTIPLIERS.get("2_2s");
            case 3 -> 15.0;
            case 4 -> 50.0;
            case 5 -> 200.0;
            default -> {
                System.out.println(Utils.toError("System: ") + "Ошибка при получении максимального коэффициента");
                yield -1;
            }
        };

        System.out.println(Utils.toInfo(game.getName() + ": ") + "Чтобы крутануть слоты, укажите ставку!\n");

        Bet bet = selectBet(game, expectedMultiplier);

        applyBet(bet);
        printBet(bet);

        int[] results = Utils.spinSlotsInARow(slotCount);
        calculateSlotGameResult(bet, results);

        resolveBet(bet);
    }

    private void playEven() throws InterruptedException {
        Game game = Game.ODDS_OR_EVENS;
        double multiplier = Multipliers.ODDS_OR_EVEN_MULTIPLIER;

        System.out.println(Utils.toInfo(game.getName() + ": ") + "Угадайте, выбрав четное или нечетное!\n");

        int selectNumber = Utils.selectNumber(1, 2);
        String parity = selectNumber % 2 == 0 ? "Четное" : "Нечетное";

        Bet bet = selectBetWithFreeBetCheck(game, multiplier);
        bet.setMultiplier(multiplier);

        applyBet(bet);
        printBet(bet, parity);

        Utils.waitAnimation("Крутим рулетку");

        int randomNumber = RANDOM.nextInt(0, 37);

        if (randomNumber == 0) {
            bet.setResult(LOSE);
            System.out.println("Выпало число 0\nУвы, ставка проиграна.");
        } else {
            String randomParity = randomNumber % 2 == 0 ? "Четное" : "Нечетное";
            boolean isEven = randomNumber % 2 == 0;
            System.out.println("Выпало число " + randomNumber + " - " + Utils.toAccent(randomParity) + "\n");
            boolean isWin = (selectNumber == 1 && !isEven) || (selectNumber == 2 && isEven);
            if (isWin) {
                bet.setResult(WIN);
                System.out.println("Поздравляем! Ваша ставка выиграла!");
            } else {
                bet.setResult(LOSE);
                System.out.println("К сожалению, вы не угадали.");
            }
        }

        resolveBet(bet);
    }

    //toDo
    private void playRoulette() throws InterruptedException {
        while (true) {
            //System.out.println(Utils.toInfo(this.GAME_NAME_ROULETTE + ": ") + "Выбери ставку по желанию!");

            System.out.println("\n|    <    | 1 число | 2 числа | 3 числа | 4 числа | 6 чисел | На цвет |  Чет/не |");
            System.out.println("|  Назад  |  Прямо  |  Сплит  |  Стрит  |  Уголл  |  Линия  |  Color  |  Evens  |");
            System.out.println("|    -    |   x35   |   x17   |   x11   |   x8    |   x5    |   x2    |   x2    |");
            System.out.println("|    0    |    1    |    2    |    3    |    4    |    5    |    6    |    7    |\n");

            int choice = Utils.whatToDoNext(7);

            switch (choice) {
                case 0 -> { return; }
                case 7 -> playGameLoop(Game.ODDS_OR_EVENS, this::playEven);
            }
        }

    }

    private void playCoinFlip() throws InterruptedException {
        Game game = Game.FLIP_COIN;
        double multiplier = Multipliers.FLIP_COIN_MULTIPLIER;

        System.out.println(Utils.toInfo(game.getName() + ": ") + "Орёл или Решка? Угадай сторону монетки!\n");

        System.out.println("1. Орёл");
        System.out.println("2. Решка");
        int playerChoice = Utils.selectNumber(1, 2);
        String playerSide = playerChoice == 1 ? "Орёл" : "Решка";


        Bet bet = selectBetWithFreeBetCheck(game, multiplier);
        bet.setMultiplier(multiplier);

        applyBet(bet);
        printBet(bet, playerSide);

        Utils.waitAnimation("Подбрасываем монетку");

        int result = RANDOM.nextInt(1, 3);
        String resultSide = result == 1 ? "Орёл" : "Решка";
        System.out.println("Выпало: " + Utils.toAccent(resultSide) + "!\n");

        if (playerChoice == result) {
            bet.setResult(WIN);
            System.out.println("Поздравляем! Вы угадали!");
        } else {
            bet.setResult(LOSE);
        }

        resolveBet(bet);
    }

    private void playHigherOrLower() throws InterruptedException {
        Game game = Game.HIGHER_OR_LOWER;

        System.out.println(Utils.toInfo(game.getName() + ": ") + "Угадай, следующее число больше или меньше!\n");

        int currentNumber = RANDOM.nextInt(2, 10);
        System.out.println(Utils.toAccent("System: ") + "Текущее число " + Utils.toAccent(currentNumber + "\n"));

        System.out.println("Следующее число (может быть от 1 до 10):");
        System.out.println("1. Больше");
        System.out.println("2. Меньше\n");

        boolean betOnHigher = Utils.selectNumber(1, 2) == 1;
        String choiceName = betOnHigher ? "Больше" : "Меньше";

        double multiplier = Multipliers.getMultiplierForHigherOrLower(currentNumber, betOnHigher);

        Bet bet = selectBetWithFreeBetCheck(game, multiplier);
        bet.setMultiplier(multiplier);

        applyBet(bet);
        printBet(bet, choiceName + " " + currentNumber);

        int nextNumber;
        do {
            nextNumber = Utils.spinSingleSlot(1, 11);
            System.out.println(Utils.toAccent("System: ") + "Выпало число " + Utils.toAccent(String.valueOf(nextNumber)) + "\n");
            if (nextNumber == currentNumber) {
                System.out.print(Utils.toInfo("System: "));
                Utils.dotAnimation("Выпало то же число, генерируем новое");
                System.out.println();
            }
        } while (nextNumber == currentNumber);

        boolean isWin = betOnHigher ? nextNumber > currentNumber : nextNumber < currentNumber;

        if (isWin) {
            bet.setResult(WIN);
            System.out.println("Поздравляем, Вы угадали!");
        } else {
            bet.setResult(LOSE);
        }

        resolveBet(bet);
    }

    private void playWords() throws InterruptedException {
        Game game = Game.GUESS_THE_WORDS;

        System.out.println(Utils.toInfo(game.getName() + ": ") + "Дается первая и последняя буква, угадывайте слова быстрее других!\n");

        //int countPlayers = Utils.nextInt("Выберите количество игроков: ");
        //int[] scorePlayers = new int[countPlayers];

        String[] first = {"А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш"};
        String[] second = {"а", "б", "в", "г", "д", "е", "ж", "з", "и", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "я"};
        int firstIndex = RANDOM.nextInt(0, first.length);
        int secondIndex = RANDOM.nextInt(0, second.length);

        System.out.println(Utils.toAccent("Очки: ") + "Игрок 1 - " + scorePlayers[0] + ", Игрок 2 - " + scorePlayers[1]);

        System.out.println(Utils.toInfo("Буквы: ") + first[firstIndex] + "—" + second[secondIndex]);

        Utils.dotAnimation("Запускаем таймер");

        System.out.print("\r");
        for (int i = 15; i > 0; i--) {
            Thread.sleep(500);
            System.out.print("\r");
            String timer;
            if (i > 8) {
                timer = Utils.toSuccess(i + "");
            } else if (i > 3) {
                timer = Utils.toInfo(i + "");
            } else {
                timer = Utils.toError(i + "");
            }
            System.out.print(timer);
            Thread.sleep(500);
        }
        System.out.print("\r" + " ".repeat(50) + "\r");

        System.out.println(Utils.toError("Время вышло!\n"));
        System.out.println("0. Не прибавлять никому");
        System.out.println("1. Прибавить 1-ому игроку");
        System.out.println("2. Прибавить 2-ому игроку\n");

        int choice = Utils.whatToDoNext(2);

        switch (choice) {
            case 1 -> scorePlayers[0]++;
            case 2 -> scorePlayers[1]++;
        }
    }

    private void playBlackjack() throws InterruptedException {
        Game game = Game.BLACKJACK;

        System.out.println(Utils.toInfo(game.getName() + ": ") + "Набери 21 очко, но не больше!\n");

        Bet bet = selectBet(game, Multipliers.getMultiplierForBlackjack(true, true));
        applyBet(bet);

        Utils.dotAnimation("\nПеремешиваем колоду");
        List<Card> deck = createDeck(1);
        Collections.shuffle(deck);

        Utils.dotAnimation("Раздаем карты");
        List<Card> playerHand = new ArrayList<>();
        List<Card> dealerHand = new ArrayList<>();

        playerHand.add(drawCard(deck));
        dealerHand.add(drawCard(deck));
        playerHand.add(drawCard(deck));
        dealerHand.add(drawCard(deck));

        boolean playerTurn = true;
        boolean isWin = false;
        boolean isBlackjack = false;
        boolean isDoubleDown = false;

        // Проверка на Blackjack у игрока
        if (getHandValue(playerHand) == 21 && playerHand.size() == 2) {
            showHands(playerHand, dealerHand, true);

            // Проверяем Blackjack у дилера
            if (getHandValue(dealerHand) == 21 && dealerHand.size() == 2) {
                System.out.println("У дилера тоже Black Jack! Ничья.");
                bet.setResult(RETURN);
                returnBet(bet);
                return;
            } else {
                System.out.println("Двадцать одно! Black Jack! 🎉");
                isWin = true;
                playerTurn = false;
                isBlackjack = true;
                bet.setMultiplier(Multipliers.getMultiplierForBlackjack(isBlackjack, isDoubleDown));
                bet.setResult(WIN);
            }
        }
        /*
            // Если игрок набрал 21 тремя и более картами
                else if (getHandValue(playerHand) == 21) {
                    showHands(playerHand, dealerHand, false);
                    System.out.println("\nДвадцать одно! 🎉");
                    // Не Blackjack, но сильная рука. Дилер может сыграть вничью позже.
                    isWin = true;
                    playerTurn = false;
                    isBlackjack = true;
                    bet.setMultiplier(Multipliers.getMultiplierForBlackjack(isBlackjack, isDoubleDown));
                    bet.setResult(WIN);
                }
         */

        while (playerTurn) {
            boolean isCanDoubleDown = playerHand.size() == 2 && !isDoubleDown;

            showHands(playerHand, dealerHand, false);

            System.out.println(Utils.toInfo("\nВаш ход:"));
            System.out.println("0. Остановиться");
            System.out.println("1. Взять карту");
            if (isCanDoubleDown) System.out.println("2. Удвоить ставку и взять карту");
            System.out.println();

            int choice = isCanDoubleDown ? Utils.whatToDoNext(2) : Utils.whatToDoNext(1);

            if (choice == 1) {
                Utils.dotAnimation("Достаем карту");
                playerHand.add(drawCard(deck));
                int playerValue = getHandValue(playerHand);

                if (playerValue > 21) {
                    showHands(playerHand, dealerHand, true);
                    System.out.println("Это перебор! Вы проиграли...");
                    bet.setResult(LOSE);
                    playerTurn = false;
                }
            } else if (choice == 2) {
                isDoubleDown = true;

                // Проверяем, хватает ли денег на удвоение
                if (player.getBalance() < bet.getAmount()) {
                    System.out.println(Utils.toError("\nSystem: ") + "Недостаточно средств для удвоения ставки!");
                    System.out.println(Utils.toInfo("System: ") + "Ваш баланс " + Utils.formatCurrency(this.player.getBalance()) + ", ваша ставка " + Utils.formatCurrency(bet.getAmount()));
                    continue;
                }

                applyBet(bet);
                System.out.print(Utils.toInfo("System: ") + "Ставка увеличена вдвое! " + Utils.formatCurrency(bet.getAmount()) + Utils.toAccent(" -> "));
                bet.setAmount(bet.getAmount() * 2);
                System.out.print(Utils.formatCurrency(bet.getAmount()) + "\n");

                Utils.dotAnimation("Достаем карту");
                playerHand.add(drawCard(deck));


                if (getHandValue(playerHand) > 21) {
                    showHands(playerHand, dealerHand, true);
                    System.out.println("Это перебор! Вы проиграли...");
                    bet.setResult(LOSE);
                    playerTurn = false;
                } else {
                    showHands(playerHand, dealerHand, false);
                    if (getHandValue(playerHand) == 21) { isBlackjack = true;}
                    playerTurn = false; // После Double Down ход сразу заканчивается
                }
            } else {
                if (getHandValue(playerHand) == 21) { isBlackjack = true;}
                playerTurn = false;
            }
        }

        // Ход дилера (если игрок не перебрал)
        //toDo
        // 1. надо чтоб дилер видел карты соперника, если у того 13, дилер добирает пока не >13
        // 2. или придумать так, что дилер не знает своей не раскрытой карты и по мат ожиданию берет или не берет
        if (getHandValue(playerHand) <= 21 && !isWin) {
            System.out.println(Utils.toInfo("\nХод дилера:"));
            Utils.dotAnimation("Смотрит карты и принимает решение");

            while (getHandValue(dealerHand) < 17) {
                Utils.dotAnimation("Дилер берет карту");
                dealerHand.add(drawCard(deck));
                System.out.println(getDealerHandWithFirstHidden(dealerHand));
            }

            Utils.dotAnimation("Дилер оставляет руку");

            showHands(playerHand, dealerHand, true);

            int playerValue = getHandValue(playerHand);
            int dealerValue = getHandValue(dealerHand);

            if (dealerValue > 21) {
                System.out.println("Дилер перебрал! Поздравляем, Вы выиграли!");
                bet.setMultiplier(Multipliers.getMultiplierForBlackjack(isBlackjack, isDoubleDown));
                bet.setResult(WIN);
            } else if (playerValue > dealerValue) {
                System.out.println("Поздравляем! Вы выиграли!");
                bet.setMultiplier(Multipliers.getMultiplierForBlackjack(isBlackjack, isDoubleDown));
                bet.setResult(WIN);
            } else if (playerValue == dealerValue) {
                System.out.println("Ничья. Ставка будет возвращена");
                bet.setResult(RETURN);
                returnBet(bet);
                return;
            } else {
                System.out.println("Увы... Дилер выиграл");
                bet.setResult(LOSE);
            }
        }

        resolveBet(bet);
    }

    private List<Card> createDeck(int decksCount) {
        List<Card> deck = new ArrayList<>();
        String[] suits = {"♥", "♦", "♣", "♠"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (int i = 0; i < decksCount; i++) {
            for (String suit : suits) {
                for (String rank : ranks) {
                    deck.add(new Card(rank, suit));
                }
            }
        }
        return deck;
    }

    private Card drawCard(List<Card> deck) {
        return deck.removeLast();
    }

    private int getHandValue(List<Card> hand) {
        long aces = hand.stream().filter(c -> c.rank().equals("A")).count();

        if (aces == 2 && hand.size() == 2) return 21;

        int value = hand.stream().mapToInt(Card::getValue).sum();

        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }
        return value;
    }

    private void showHands(List<Card> playerHand, List<Card> dealerHand, boolean revealDealer) {
        System.out.println(Utils.toAccent("\nВаши карты: ") + playerHand + " = " + getHandValue(playerHand));

        if (revealDealer) {
            System.out.println(Utils.toAccent("Карты дилера: ") + dealerHand + " = " + getHandValue(dealerHand) + "\n");
        } else {
            System.out.println(Utils.toAccent("Карты дилера: ") + getDealerHandWithFirstHidden(dealerHand));
        }
    }

    private String getDealerHandWithFirstHidden(List<Card> dealerHand) {
        StringBuilder hiddenHand = new StringBuilder("[?");

        List<Card> dealerHandWithFirstHidden = dealerHand.stream()
                .skip(1)
                .toList();

        for (Card card : dealerHandWithFirstHidden) {
            hiddenHand.append(", ").append(card);
        }

        return hiddenHand.append("]").toString();
    }

    private void selectRPS() throws InterruptedException {
        while (true) {
            System.out.println(Utils.toInfo("\nSystem: ") + "Камень, ножницы, бумага! Для начала, выберите вариант игры:\n");

            System.out.println(
                    "0. Назад\n" +
                            "1. До 1 победы (x" + Multipliers.getMultiplierForRPS(1) + ")\n" +
                            "2. До 3 побед (x" + Multipliers.getMultiplierForRPS(3) + ")\n" +
                            "3. До 5 побед (x" + Multipliers.getMultiplierForRPS(5) + ")\n" +
                            "4. До 7 побед (x" + Multipliers.getMultiplierForRPS(7) + ")\n"
            );

            int choice = Utils.whatToDoNext(4);

            switch (choice) {
                case 0 -> { return; }
                case 1 -> playGameLoop(Game.ROCK_PAPER_SCISSORS_1, this::playRPS);
                case 2 -> playGameLoop(Game.ROCK_PAPER_SCISSORS_3, () -> playRPSRounds(3));
                case 3 -> playGameLoop(Game.ROCK_PAPER_SCISSORS_5, () -> playRPSRounds(5));
                case 4 -> playGameLoop(Game.ROCK_PAPER_SCISSORS_7, () -> playRPSRounds(7));
            }
        }
    }

    private void playRPS() throws InterruptedException {
        Game game = Game.ROCK_PAPER_SCISSORS_1;
        double multiplier = Multipliers.getMultiplierForRPS(1);

        System.out.println(Utils.toInfo(game.getName() + ": ") + "Классика");

        Bet bet = selectBetWithFreeBetCheck(game, multiplier);
        bet.setMultiplier(multiplier);

        System.out.println("\n" +
                """
                Ваш выбор:
                1. Камень
                2. Ножницы
                3. Бумага
                """
        );

        int playerChoice = Utils.selectNumber(1, 3);
        String playerMove = getMoveNameForRPS(playerChoice);

        applyBet(bet);
        printBet(bet, playerMove);

        int dealerChoice = RANDOM.nextInt(1, 4);
        String dealerMove = getMoveNameForRPS(dealerChoice);

        Utils.dotAnimation("Дилер выбирает");
        BetResult result = getResultsForRPS(playerChoice, dealerChoice);
        bet.setResult(result);

        switch (result) {
            case WIN -> System.out.print("Поздравляем! Вы выиграли!");
            case LOSE -> System.out.print("К сожалению, вы проиграли...");
            case RETURN -> System.out.print("Ничья!");
        }

        System.out.println(" Дилер выбрал " + Utils.toAccent(dealerMove) + "\n");

        resolveBet(bet);
    }

    private void playRPSRounds(int toHowManyWins) throws InterruptedException {
        Game game = switch (toHowManyWins) {
            case 3 -> Game.ROCK_PAPER_SCISSORS_3;
            case 5 -> Game.ROCK_PAPER_SCISSORS_5;
            case 7 -> Game.ROCK_PAPER_SCISSORS_7;
            default -> throw new IllegalArgumentException();
        };
        double multiplier = Multipliers.getMultiplierForRPS(toHowManyWins);

        System.out.println(Utils.toInfo(game.getName() + ": ") + "До " + toHowManyWins + " побед" );

        Bet bet = selectBet(game, multiplier);
        bet.setMultiplier(multiplier);

        applyBet(bet);

        int playerWins = 0;
        int dealerWins = 0;
        int rounds = 0;

        while (playerWins < toHowManyWins && dealerWins < toHowManyWins) {
            rounds++;
            System.out.println(Utils.toAccent("\nРаунд #" + rounds));
            System.out.println("\n" +
                    """
                    Ваш выбор:
                    1. Камень
                    2. Ножницы
                    3. Бумага
                    """
            );
            int playerChoice = Utils.selectNumber(1, 3);
            int dealerChoice = RANDOM.nextInt(1, 4);

            Utils.dotAnimation("Дилер выбирает");
            BetResult result = getResultsForRPS(playerChoice, dealerChoice);

            System.out.println(
                    "\nВаш выбор: " + Utils.toAccent(getMoveNameForRPS(playerChoice)) +
                            "\nВыбор дилера: " + Utils.toAccent(getMoveNameForRPS(dealerChoice)) + "\n"
            );

            switch (result) {
                case WIN ->{
                    playerWins++;
                    System.out.print(Utils.toSuccess("Раунд #" + rounds) + " - выигран!");
                }
                case LOSE -> {
                    dealerWins++;
                    System.out.print(Utils.toError("Раунд #" + rounds) + " - проигран!");
                }
                case RETURN -> System.out.print(Utils.toInfo("Раунд #" + rounds) + " - ничья!");
            }

            System.out.println(" Счёт: " + playerWins + " - " + dealerWins);
            Thread.sleep(1_000);
        }

        if (playerWins == toHowManyWins) {
            if (dealerWins != 0) {
                System.out.println("Поздравляем! Вы победили!");
            } else {
                System.out.println("Поздравляем! Вы победили в сухую!");
            }
            bet.setResult(WIN);
        } else {
            if (dealerWins - playerWins == 1) {
                System.out.println("Увы... Вам не хватило всего лишь одного раунда!");
            } else {
                System.out.println("Увы... Вы проиграли");
            }
            bet.setResult(LOSE);
        }

        resolveBet(bet);
    }

    private String getMoveNameForRPS(int choice) {
        return switch (choice) {
            case 1 -> "Камень";
            case 2 -> "Ножницы";
            case 3 -> "Бумага";
            default -> "?";
        };
    }

    private BetResult getResultsForRPS(int player, int dealer) {
        if (player == dealer) return RETURN;
        if ((player == 1 && dealer == 2) || (player == 2 && dealer == 3) || (player == 3 && dealer == 1)) return WIN;
        return LOSE;
    }

    private void playGameLoop(Game game, GameRunnable runGame) throws InterruptedException {
        if (player.getBalance() < game.getMinBalanceForPlay()) {
            System.out.println(Utils.toError("System: ") + "Для входа в игру " + Utils.toInfo(game.getName()) + " нужно минимум " + Utils.formatCurrency(game.getMinBalanceForPlay()));
            return;
        }
        boolean continueGame = true;
        while (continueGame) {
            runGame.run();
            if (player.getBalance() < game.getMinBalanceForPlay()) {
                System.out.println(Utils.toError("System: ") + "Депозит для игры " + Utils.toInfo(game.getName()) + " ниже минимального - " + Utils.formatCurrency(game.getMinBalanceForPlay()));
                Utils.dotAnimation("Сворачиваем игру");
                break;
            }
            continueGame = Utils.askToContinue();
        }
    }
}