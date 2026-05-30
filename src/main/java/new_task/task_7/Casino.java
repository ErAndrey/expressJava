package new_task.task_7;

import new_task.task_7.bet.Bet;
import new_task.task_7.bet.BetType;
import new_task.task_7.bet_history.BetRecord;
import new_task.task_7.bet_history.BetResult;
import new_task.task_7.bonuses.*;
import new_task.task_7.action_panel.ActionPanel;
import new_task.task_7.action_panel.ActionPanels;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public final class Casino {
    private static final Random RANDOM = new Random();

    public static final double COMMISSION_FOR_WITHDRAW = 4.95; // В %
    private static final int MIN_BALANCE_FOR_PLAY = 1000;

    //toDo playWords
    private final int[] scorePlayers = new int[2];

    private final String GAME_NAME_ODDS_OR_EVEN = "Odds or Evens";
    private final String GAME_NAME_FLIP_COIN = "Flip coins";
    private final String GAME_NAME_HIGHER_PR_LOWER = "Higher or Lower";
    private final String GAME_NAME_GUESS_THE_WORDS = "Guess the Words";
    private final String GAME_NAME_BLACKJACK = "Blackjack 21";

    //toDo Roulette
    private final String GAME_NAME_ROULETTE = "European Roulette";

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

    private Bet selectBet(double maxMultiplier) {
        int betAmount;
        int minBet = MIN_BALANCE_FOR_PLAY / 2;
        int maxBet = (int) (this.balance / maxMultiplier);
        while (true) {
            betAmount = Utils.nextInt("Укажите сумму ставки: ");
            if (betAmount < minBet) {
                System.out.println(Utils.toError("System: ") + "Минимальная сумма ставки: " + Utils.formatCurrency(minBet));
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
            return new Bet(betAmount);
        }
    }

    private Bet selectBetWithFreeBetCheck(double maxMultiplier) {
        TreeMap<Integer, FreeBetBonus> activatedFreeBetBonuses = player.getFreeBetBonuses();
        if (!activatedFreeBetBonuses.isEmpty()) {

            TreeMap<Integer, FreeBetBonus> availableFreeBet = activatedFreeBetBonuses.entrySet().stream()
                    .filter(entry -> entry.getValue().getMaxMultiplier() >= maxMultiplier)
                    .filter(entry -> entry.getValue().getFreeBet() < ((int) (this.balance / Multipliers.getClearMultiplier(maxMultiplier - 1))))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (v1, v2) -> v1,
                            TreeMap::new
                    ));

            if (availableFreeBet.isEmpty()) return selectBet(maxMultiplier);

            boolean isOnlyOneAvailableFreeBet = availableFreeBet.size() == 1;

            if (isOnlyOneAvailableFreeBet) {
                FreeBetBonus freeBetBonus = availableFreeBet.firstEntry().getValue();
                System.out.println(Utils.toInfo("\nSystem: ") + "Для игры доступен единственный фрибет на " + Utils.toSuccess(Utils.formatCurrency(freeBetBonus.getFreeBet())) + " с Кф до " + Utils.toAccent("x" + freeBetBonus.getMaxMultiplier())
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
                    return new Bet(availableFreeBet.firstEntry().getValue());
                } else {
                    System.out.println("\nВыберите один из бонусов:");
                    availableFreeBet.values().forEach(System.out::println);
                    System.out.println();
                    while (true) {
                        int selectedBonusNumber = Utils.nextInt("Номер бонуса: ");
                        if (availableFreeBet.containsKey(selectedBonusNumber)) {
                            FreeBetBonus selectedFreeBetBonus = availableFreeBet.get(selectedBonusNumber);
                            player.removeFreeBetBonus(selectedFreeBetBonus);
                            return new Bet(selectedFreeBetBonus);
                        }
                        System.out.println(Utils.toError("System: ") + "Выберите доступный бонус");
                    }
                }
            }
        }
        return selectBet(maxMultiplier);
    }

    private void applyBet(Bet bet) {
        if (bet.getType() == BetType.REGULAR) {
            System.out.print(Utils.toInfo("System: ") + "Списание ставки. Ваш баланс " + Utils.formatCurrency(this.player.getBalance()) + Utils.toError(" -> "));
            this.player.withdraw(bet.getAmount(), WhoChangePlayerBalance.CASINO);
            this.balance += bet.getAmount();
            System.out.println(Utils.formatCurrency(this.player.getBalance()));
        }
    }

    private void printBet(Bet bet, double multiplier, String playerChoice) {
        String freeBetText = bet.getType() == BetType.FREE_BET ? Utils.toAccent("Фрибет ") : "";
        System.out.println(Utils.toInfo("\nВаша ставка: ") + freeBetText + playerChoice + ", " + Utils.formatCurrency(bet.getAmount()) + ", Кф " + Utils.toAccent("х" + multiplier) + "\n");
    }

    private void printBet(Bet bet) {
        String freeBetText = bet.getType() == BetType.FREE_BET ? Utils.toAccent("Фрибет ") : "";
        System.out.println(Utils.toInfo("\nВаша ставка: ") + freeBetText + Utils.formatCurrency(bet.getAmount()) + "\n");
    }

    //toDo перейти с boolean isWin на BetResult ?
    private void returnBet(String gameName, Bet bet) {
        if (bet.getType() == BetType.REGULAR) {
            System.out.print(Utils.toInfo("System: ") + "Возврат ставки. Ваш баланс " + Utils.formatCurrency(this.player.getBalance()) + Utils.toInfo(" -> "));
            player.deposit(bet.getAmount(), WhoChangePlayerBalance.CASINO);
            this.balance -= bet.getAmount();
            System.out.println(Utils.formatCurrency(this.player.getBalance()) + "\n");
        } else {
            this.player.addFreeBetBonus(bet.getFreeBetBonus());
        }
        this.player.addBetToHistory(new BetRecord(gameName, bet, BetResult.RETURN));
    }

    private void resolveBet(String gameName, boolean isWin, Bet bet, double winAmount) {
        if (isWin) {
            System.out.println(Utils.toSuccess("Ваш выигрыш: ") + Utils.formatCurrency(winAmount));

            if (winAmount > this.balance) {
                if (bet.getType() == BetType.REGULAR) {
                    System.out.println(Utils.toError("System: ") + "У казино недостаточно средств для выплаты! Текущая ставка была возвращена.");
                } else {
                    System.out.println(Utils.toError("System: ") + "У казино недостаточно средств для выплаты! Фрибет был возвращен.");
                }
                //toDo пока костыль
                this.player.updateTotalGames();

                returnBet(gameName, bet);
                return;
            }

            System.out.print(Utils.toInfo("System: ") + "Зачисление выигрыша. Ваш баланс " + Utils.formatCurrency(this.player.getBalance()) + Utils.toSuccess(" -> "));
            this.player.deposit(winAmount, WhoChangePlayerBalance.CASINO);
            this.balance -= winAmount;
            System.out.println(Utils.formatCurrency(this.player.getBalance()));

            if (bet.getType() == BetType.REGULAR) {
                updatePlayerStatistic(true, winAmount - bet.getAmount());
                this.player.addBetToHistory(new BetRecord(gameName, bet, winAmount));
            } else {
                updatePlayerStatistic(true, winAmount);
                this.player.addBetToHistory(new BetRecord(gameName, bet, winAmount - bet.getAmount()));
            }
        } else {
            if (bet.getType() == BetType.REGULAR) {
                System.out.println(Utils.toError("Проигрыш: ") + Utils.formatCurrency(bet.getAmount()));
                updatePlayerStatistic(false, bet.getAmount());
            } else {
                System.out.println(Utils.toError("Проигрыш: ") + "Фрибет проиграл");
                updatePlayerStatistic(false, 0);
            }
            this.player.addBetToHistory(new BetRecord(gameName, bet, BetResult.LOSE));
            System.out.println(Utils.toInfo("System: ") + "Ваш баланс " + Utils.formatCurrency(this.player.getBalance()));
        }
        System.out.println();
    }

    private void updatePlayerStatistic(boolean isWin, double amount) {
        if (isWin) {
            this.player.updateTotalWinAmount(amount);
            this.player.updateTotalWins();
            this.player.updateCurrentWins();
            this.player.resetCurrentLose();
            if (this.player.getCurrentWins() > this.player.getMaxWins()) this.player.updateMaxWins(player.getCurrentWins());
            if (amount > this.player.getMaxWinAmount()) this.player.updateMaxWinAmount(amount);
        } else {
            this.player.updateTotalLoseAmount(amount);
            this.player.updateTotalLose();
            this.player.updateCurrentLose();
            this.player.resetCurrentWins();
        }
        canAddBonus(isWin);
    }

    private void canAddBonus(boolean isWin) {
        LocalDateTime fromData = LocalDateTime.now();
        int initialBonusSize = player.getAvailableBonuses().size();
        int totalGames = player.getTotalGames();

        switch (totalGames % 100) {
            case 16 -> player.addAvailableBonus(new FreeBetBonus(5_000, 3.0, fromData, 0, 6));
            case 32 -> player.addAvailableBonus(new DepositBonus(100.0, fromData, 0, 1));
            case 48 -> player.addAvailableBonus(new CashBonus(15_000, fromData, 1, 0));
            case 64 -> player.addAvailableBonus(new FreeBetBonus(15_000, 3.5, fromData, 0, 6));
            case 80 -> player.addAvailableBonus(new DepositBonus(200.0, fromData, 0, 2));
            case 96 -> player.addAvailableBonus(new CashBonus(45_000, fromData, 1, 12));
        }

        if (isWin) {
            int wins = player.getTotalWins();
            int currentWins = player.getCurrentWins();
            switch (wins % 100) {
                case 33 -> player.addAvailableBonus(new FreeBetBonus(10_000, 5.0, fromData, 0, 4));
                case 66 -> player.addAvailableBonus(new FreeBetBonus(20_000, 3.0, fromData, 0, 6));
                case 99 -> player.addAvailableBonus(new FreeBetBonus(30_000, 2.5, fromData, 0, 8));
            }
            switch (currentWins) {
                case 5 -> player.addAvailableBonus(new FreeBetBonus(5_555, 3.5, fromData, 0, 1));
                case 10 -> player.addAvailableBonus(new FreeBetBonus(55_555, 1.75 ,fromData, 0, 1));
            }
        } else {
            int lose = player.getTotalLose();
            int currentLose = player.getCurrentLose();
            switch (lose % 100) {
                case 33 -> player.addAvailableBonus(new FreeBetBonus(7_000, 4.0, fromData, 0, 2));
                case 66 -> player.addAvailableBonus(new FreeBetBonus(14_000, 3.5, fromData, 0, 3));
                case 99 -> player.addAvailableBonus(new FreeBetBonus(28_000, 3.0, fromData, 0, 4));
            }
            switch (currentLose) {
                case 5 -> player.addAvailableBonus(new DepositBonus(30.0, fromData, 0, 1));
                case 8 -> player.addAvailableBonus(new DepositBonus(50.0, fromData, 0, 1));
                case 11 -> player.addAvailableBonus(new DepositBonus(100.0, fromData, 0, 1));
            }
        }

        if (player.getAvailableBonuses().size() > initialBonusSize) System.out.println(Utils.toSuccess("System: ") + "Вам доступен новый бонус!");
    }

    public void play() throws InterruptedException {
        while (true) {
            System.out.println(ActionPanels.ACTION_PANELS.get(ActionPanel.PLAY));

            int action = Utils.whatToDoNext(6);

            switch (action) {
                case 0 -> {
                    String playerName = Utils.toAccent(player.getName());
                    int playerCurrentWin = player.getCurrentWins();
                    if (player.getBalance() < MIN_BALANCE_FOR_PLAY) {
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
                case 2 -> playGameLoop(this.GAME_NAME_ODDS_OR_EVEN, this::playEven);
                case 3 -> playGameLoop(this.GAME_NAME_FLIP_COIN, this::playCoinFlip);
                case 4 -> playGameLoop(this.GAME_NAME_HIGHER_PR_LOWER, this::playHigherOrLower);
                case 5 -> playGameLoop(this.GAME_NAME_GUESS_THE_WORDS, this::playWords);
                case 6 -> playGameLoop(this.GAME_NAME_BLACKJACK, this::playBlackjack);
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

            int choice = Utils.whatToDoNext(0);

            if (choice == 0) return;
        }
    }

    private void selectSlots() throws InterruptedException {
        while (true) {
            System.out.println(Utils.toInfo("\nSlots: ") + "Для начала игры, выберите количество слотов!\n");

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
                case 2, 3, 4, 5 -> playGameLoop("x" + choice + " Slots", () -> playSlotsGame(choice));
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

    private double calculateSlotGameResult(Bet bet, int[] slotResults) {
        int slotCount = slotResults.length;

        Map<Integer, Integer> sequence = getSequenceFromSlotResult(slotResults);
        Map<Integer, Integer> clearRepeat = getCleanRepeatFromSlotWithoutSequence(getRepeatFromSlotResult(slotResults), sequence);

        double multiplier = Multipliers.getMultiplierForSlots(slotCount, sequence, clearRepeat);

        if (multiplier > 0) System.out.println(" Кф " + Utils.toAccent("x" + multiplier));

        return bet.getAmount() * Multipliers.getActualMultiplier(bet, multiplier);
    }

    private void playSlotsGame(int slotCount) throws InterruptedException {
        String gameName = "x" + slotCount + " Slots";
        System.out.println(Utils.toInfo(gameName + ": ") + "Чтобы крутануть слоты, укажите ставку!\n");

        // Средне-ожидаемый выигрыш кроме 2ух слотов
        double maxMultiplier = switch (slotCount) {
            case 2 -> Multipliers.SLOTS_MULTIPLIERS.get("2_2s");
            case 3 -> 15.0;
            case 4 -> 50.0;
            case 5 -> 200.0;
            default -> {
                System.out.println(Utils.toError("System: ") + "Ошибка при получении максимального коэффициента");
                yield -1;
            }
        };

        Bet bet = selectBet(maxMultiplier);

        applyBet(bet);
        printBet(bet);

        int[] results = Utils.spinSlotsInARow(slotCount);
        double winAmount = calculateSlotGameResult(bet, results);
        boolean isWin = winAmount > 0;

        resolveBet(gameName, isWin, bet, winAmount);
    }

    private void playEven() throws InterruptedException {
        System.out.println(Utils.toInfo(this.GAME_NAME_ODDS_OR_EVEN + ": ") + "Угадайте, выбрав четное или нечетное!\n");

        int selectNumber = Utils.selectNumber(1, 2);
        String parity = selectNumber % 2 == 0 ? "Четное" : "Нечетное";

        double multiplier = Multipliers.ODDS_OR_EVEN_MULTIPLIER;
        Bet bet = selectBetWithFreeBetCheck(multiplier);

        applyBet(bet);
        printBet(bet, multiplier, parity);

        Utils.waitAnimation("Крутим рулетку");

        int randomNumber = RANDOM.nextInt(0, 37);

        double winAmount = 0;
        boolean isWin = false;

        if (randomNumber == 0) {
            System.out.println("Выпало число 0\nУвы, ставка проиграна.");
        } else {
            String randomParity = randomNumber % 2 == 0 ? "Четное" : "Нечетное";
            boolean isEven = randomNumber % 2 == 0;
            System.out.println("Выпало число " + randomNumber + " - " + Utils.toAccent(randomParity) + "\n");
            isWin = (selectNumber == 1 && !isEven) || (selectNumber == 2 && isEven);
            if (isWin) {
                winAmount = bet.getAmount() * Multipliers.getActualMultiplier(bet, multiplier);
                System.out.println("Поздравляем! Ваша ставка выиграла!");
            } else {
                System.out.println("К сожалению, вы не угадали.");
            }
        }

        resolveBet(this.GAME_NAME_ODDS_OR_EVEN, isWin, bet, winAmount);
    }

    //toDo
    private void playRoulette() throws InterruptedException {
        while (true) {
            System.out.println(Utils.toInfo(this.GAME_NAME_ROULETTE + ": ") + "Выбери ставку по желанию!");

            System.out.println("\n|    <    | 1 число | 2 числа | 3 числа | 4 числа | 6 чисел | На цвет |  Чет/не |");
            System.out.println("|  Назад  |  Прямо  |  Сплит  |  Стрит  |  Уголл  |  Линия  |  Color  |  Evens  |");
            System.out.println("|    -    |   x35   |   x17   |   x11   |   x8    |   x5    |   x2    |   x2    |");
            System.out.println("|    0    |    1    |    2    |    3    |    4    |    5    |    6    |    7    |\n");

            int choice = Utils.whatToDoNext(7);

            switch (choice) {
                case 0 -> play();
                case 7 -> this.playGameLoop(this.GAME_NAME_ODDS_OR_EVEN, this::playEven);
            }
        }

    }

    private void playCoinFlip() throws InterruptedException {
        System.out.println(Utils.toInfo(this.GAME_NAME_FLIP_COIN + ": ") + "Орёл или Решка? Угадай сторону монетки!\n");

        System.out.println("1. Орёл");
        System.out.println("2. Решка");
        int playerChoice = Utils.selectNumber(1, 2);
        String playerSide = playerChoice == 1 ? "Орёл" : "Решка";

        double multiplier = Multipliers.FLIP_COIN_MULTIPLIER;
        Bet bet = selectBetWithFreeBetCheck(multiplier);

        applyBet(bet);
        printBet(bet, multiplier, playerSide);

        Utils.waitAnimation("Подбрасываем монетку");

        int result = RANDOM.nextInt(1, 3);
        String resultSide = result == 1 ? "Орёл" : "Решка";
        System.out.println("Выпало: " + Utils.toAccent(resultSide) + "!\n");

        double winAmount = 0;
        boolean isWin = playerChoice == result;

        if (isWin) {
            winAmount = bet.getAmount() * Multipliers.getActualMultiplier(bet, multiplier);;
            System.out.println("Поздравляем! Вы угадали!");
        }

        resolveBet(this.GAME_NAME_FLIP_COIN, isWin, bet, winAmount);
    }

    private void playHigherOrLower() throws InterruptedException {
        System.out.println(Utils.toInfo(this.GAME_NAME_HIGHER_PR_LOWER + ": ") + "Угадай, следующее число больше или меньше!\n");

        int currentNumber = RANDOM.nextInt(2, 10);
        System.out.println(Utils.toAccent("System: ") + "Текущее число " + Utils.toAccent(currentNumber + "\n"));

        System.out.println("Следующее число (может быть от 1 до 10):");
        System.out.println("1. Больше");
        System.out.println("2. Меньше\n");

        boolean betOnHigher = Utils.selectNumber(1, 2) == 1;
        String choiceName = betOnHigher ? "Больше" : "Меньше";

        double multiplier = Multipliers.getMultiplierForHigherOrLower(currentNumber, betOnHigher);
        Bet bet = selectBetWithFreeBetCheck(multiplier);

        applyBet(bet);
        printBet(bet, multiplier, choiceName + " " + currentNumber);

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

        double winAmount = 0;
        boolean isWin = betOnHigher ? nextNumber > currentNumber : nextNumber < currentNumber;

        if (isWin) {
            winAmount = bet.getAmount() * Multipliers.getActualMultiplier(bet, multiplier);
            System.out.println("Поздравляем, Вы угадали!");
        }

        resolveBet(this.GAME_NAME_HIGHER_PR_LOWER, isWin, bet, winAmount);
    }

    private void playWords() throws InterruptedException {
        System.out.println(Utils.toInfo(this.GAME_NAME_GUESS_THE_WORDS + ": ") + "Дается первая и последняя буква, угадывайте слова быстрее других!\n");

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
        System.out.println(Utils.toInfo(this.GAME_NAME_BLACKJACK + ": ") + "Набери 21 очко, но не больше!\n");

        Bet bet = selectBet(Multipliers.getMultiplierForBlackjack(true, true));
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
                returnBet(this.GAME_NAME_BLACKJACK, bet);
                return;
            } else {
                System.out.println("Двадцать одно! Black Jack! 🎉");
                isWin = true;
                playerTurn = false;
                isBlackjack = true;
            }
        }
        // Если игрок набрал 21 тремя и более картами
        else if (getHandValue(playerHand) == 21) {
            showHands(playerHand, dealerHand, false);
            System.out.println("\nДвадцать одно! 🎉");
            // Не Blackjack, но сильная рука. Дилер может сыграть вничью позже.
            isWin = true;
            playerTurn = false;
        }

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

                int playerValue = getHandValue(playerHand);

                if (playerValue > 21) {
                    showHands(playerHand, dealerHand, true);
                    System.out.println("Это перебор! Вы проиграли...");
                    playerTurn = false;
                } else {
                    showHands(playerHand, dealerHand, false);
                    playerTurn = false; // После Double Down ход сразу заканчивается
                }
            } else {
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
                isWin = true;
            } else if (playerValue > dealerValue) {
                System.out.println("Поздравляем! Вы выиграли!");
                isWin = true;
            } else if (playerValue == dealerValue) {
                System.out.println("Ничья. Ставка будет возвращена");
                returnBet(this.GAME_NAME_BLACKJACK, bet);
                return;
            } else {
                System.out.println("Увы... Дилер выиграл");
            }
        }

        double multiplier = Multipliers.getMultiplierForBlackjack(isBlackjack, isDoubleDown);
        double winAmount = bet.getAmount() * Multipliers.getActualMultiplier(bet, multiplier);

        resolveBet(this.GAME_NAME_BLACKJACK, isWin, bet, winAmount);
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
        int value = hand.stream().mapToInt(Card::getValue).sum();
        long aces = hand.stream().filter(c -> c.rank().equals("A")).count();

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

    private void playGameLoop(String gameName, GameRunnable game) throws InterruptedException {
        if (player.getBalance() < MIN_BALANCE_FOR_PLAY) {
            System.out.println(Utils.toError("System: ") + "Для входа в игру " + Utils.toInfo(gameName) + " нужно минимум " + Utils.formatCurrency(MIN_BALANCE_FOR_PLAY));
            return;
        }

        boolean continueGame = true;
        while (continueGame) {
            game.run();
            if (player.getBalance() < MIN_BALANCE_FOR_PLAY) {
                System.out.println(Utils.toError("System: ") + "Депозит для игр ниже минимального - " + Utils.formatCurrency(MIN_BALANCE_FOR_PLAY));
                Utils.dotAnimation("Сворачиваем игру");
                break;
            }
            continueGame = Utils.askToContinue();
        }
    }
}