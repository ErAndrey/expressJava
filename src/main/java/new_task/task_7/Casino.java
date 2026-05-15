package new_task.task_7;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public final class Casino {
    private static final Random RANDOM = new Random();

    public static final double COMMISSION_FOR_WITHDRAW = 1.5; // В %
    private static final int MIN_BALANCE_FOR_PLAY = 1000;

    //toDo playWords
    private int[] scorePlayers = new int[2];

    private final String GAME_NAME_2 = "Odds or Evens";
    private final String GAME_NAME_3 = "Flip coins";
    private final String GAME_NAME_4 = "Higher or Lower";
    private final String GAME_NAME_5 = "Guess the Words";
    private final String GAME_NAME_6 = "European Roulette";

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

    public double getBalance() {return this.balance;}

    public void changeBalance(double amount) {
        this.balance += amount;
    }

    private int selectBet() {
        int bet;
        while (true) {
            bet = Utils.nextInt("Укажите сумму ставки: ");
            if (bet <= 0) {
                System.out.println(Utils.toError("System: ") + "Ставка должна быть положительным числом!");
                continue;
            }
            if (player.getBalance() < bet) {
                System.out.println(Utils.toError("System: ") + "Недостаточно средств! Ваш баланс: " + Utils.formatCurrency(player.getBalance()));
                continue;
            }
            if (this.balance < bet * 2) {
                System.out.println(Utils.toError("System: ") + "Извините, мы не можем принять такую ставку. Максимальная ставка: " + Utils.formatCurrency((int) (this.balance / 2)));
                continue;
            }
            return bet;
        }
    }

    private void applyBet(int betAmount) {
        this.player.withdraw(betAmount, WhoChangePlayerBalance.CASINO);
        this.balance += betAmount;
    }

    private void resolveBet(boolean isWin, int betAmount, double winAmount) {
        if (isWin) {
            System.out.println(Utils.toSuccess("Ваш выигрыш: ") + Utils.formatCurrency(winAmount));
            if (winAmount > this.balance) {
                System.out.println(Utils.toError("System: ") + "У казино недостаточно средств для выплаты! Текущая ставка была возвращена.");
                this.player.deposit(betAmount, WhoChangePlayerBalance.CASINO);
                this.balance -= betAmount;
                return;
            }
            this.player.deposit(winAmount, WhoChangePlayerBalance.CASINO);
            this.balance -= winAmount;
            updatePlayerStatistic(true, winAmount - betAmount);
        } else {
            System.out.println(Utils.toError("Проигрыш: ") + Utils.formatCurrency(betAmount));
            updatePlayerStatistic(false, betAmount);
        }
    }

    private void updatePlayerStatistic(boolean isWin, double amount) {
        if (isWin) {
            this.player.updateTotalWinAmount(amount);
            this.player.updateTotalWins();
            this.player.updateCurrentWins();
            if (this.player.getCurrentWins() > this.player.getMaxWins()) this.player.updateMaxWins(player.getCurrentWins());
            if (amount > this.player.getMaxWinAmount()) this.player.updateMaxWinAmount(amount);
        } else {
            this.player.updateTotalLoseAmount(amount);
            this.player.updateTotalLose();
            this.player.resetCurrentWins();
        }
    }

    public void play() throws InterruptedException {
        while (true) {
            Utils.printActionPanel("play");

            int action = Utils.whatToDoNext(5);

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
                case 2 -> playGameLoop(this.GAME_NAME_2, this::playEven);
                case 3 -> playGameLoop(this.GAME_NAME_3, this::playCoinFlip);
                case 4 -> playGameLoop(this.GAME_NAME_4, this::playHigherOrLower);
                case 5 -> playGameLoop(this.GAME_NAME_5, this::playWords);
            }
        }
    }

    //toDo учитывать маржу для слотов для каждого кфа REDUCE_MULTIPLIER_FOR_SLOTS_GAME
    private void checkWinSlotsTable() throws InterruptedException {
        while (true) {
            System.out.println(Utils.toInfo("\nТаблица возможных выигрышей и их коэффициентов:"));
            System.out.print("""
            |       5 слотов      |     4 слота      |    3 слота    |   2 слота   |
            |     5s -> х10000    |   4s -> х1000    |   3s -> х100  |  2s -> x10  |
            |  4r+2s+2s -> x1111  |  2r+2r -> x111   |  2r -> x11.1  |
            |      4s -> х556     |  2r+2s -> x111   |  2s -> x5.56  |
            |     3s+2s -> х556   |  3r+2s -> x55.6  |
            |     3r+2r -> x556   |  2s+2s -> х55.6  |
            |     4r+3s -> x556   |   3s -> x55.6    |
            |     2r+3s -> x556   |   2s -> х4.63    |
            |   3r+2s+2s -> x556  |   2r -> x4.63    |
            |     3s -> x46.3     |
            |    2s+2s -> x46.3   |
            |    3r+2s -> x27.8   |
            |    2r+2r -> x23.1   |
            |    2r+2s -> x23.1   |
            |     3r -> x19.8     |
            |     2s -> 4.96      |
            |     2r -> 3.31      |
            """);

            System.out.println(Utils.toInfo("\nПояснение:"));
            System.out.print("""
            5s / 4s / 3s - последовательность (в ряд) из - 5 / 4 / 3 чисел, примеры: [55555] / [04444] / [10333] / [333] / [22]
            3r / 2r / 2r+2r - совпадение (наличие в результате) - 3 / 2 / 2ух пар чисел, примеры: [30310] - 3 совпадения (3) / [12012] - 2 совпадения (1 и 2) 
            3r+2s / 4r+2s+2s - совпадения + последовательности, примеры: [55015] - 3 повторения (5) + одна последовательность (5), [55055] - 4 повторения (5) + две последовательности по 2 (5)
            """);

            System.out.println(Utils.toInfo("\nSystem: ") + "Чтобы вернуться назад выберите 0\n");

            int choice = Utils.whatToDoNext(0);

            if (choice == 0) return;
        }
    }

    private void checkWinSlotsTableV2() {
        while (true) {
            String maxStringLineFor5Slots = "4r+2s+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_4r+2s+2s");
            String maxStringLineFor4Slots = "2r+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("4_2r+2s");
            String maxStringLineFor3Slots = "2r -> x" + Multipliers.SLOTS_MULTIPLIERS.get("3_2r");
            String maxStringLineFor2Slots = "2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("2_2s");

            int maxWidthFor5Slots = maxStringLineFor5Slots.length();
            int maxWidthFor4Slots = maxStringLineFor4Slots.length();
            int maxWidthFor3Slots = maxStringLineFor3Slots.length();
            int maxWidthFor2Slots = maxStringLineFor2Slots.length();

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

            System.out.println("|  "  + Utils.toCenter("3s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_3s"), maxWidthFor5Slots) + "  |");
            System.out.println("|  "  + Utils.toCenter("2s+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_2s+2s"), maxWidthFor5Slots) + "  |");
            System.out.println("|  "  + Utils.toCenter("3r+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_3r+2s"), maxWidthFor5Slots) + "  |");
            System.out.println("|  "  + Utils.toCenter("2r+2r -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_2r+2r"), maxWidthFor5Slots) + "  |");
            System.out.println("|  "  + Utils.toCenter("2r+2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_2r+2s"), maxWidthFor5Slots) + "  |");
            System.out.println("|  "  + Utils.toCenter("3r -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_3r"), maxWidthFor5Slots) + "  |");
            System.out.println("|  "  + Utils.toCenter("2s -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_2s"), maxWidthFor5Slots) + "  |");
            System.out.println("|  "  + Utils.toCenter("2r -> x" + Multipliers.SLOTS_MULTIPLIERS.get("5_2r"), maxWidthFor5Slots) + "  |");

            System.out.println(Utils.toInfo("\nПояснение:"));
            System.out.print("""
            5s / 4s / 3s - последовательность (в ряд) из - 5 / 4 / 3 чисел, примеры: [55555] / [04444] / [10333] / [333] / [22]
            3r / 2r / 2r+2r - совпадение (наличие в результате) - 3 / 2 / 2ух пар чисел, примеры: [30310] - 3 совпадения (3) / [12012] - 2 совпадения (1 и 2)
            3r+2s / 4r+2s+2s - совпадения + последовательности, примеры: [55015] - 3 повторения (5) + одна последовательность (5), [55055] - 4 повторения (5) + две последовательности по 2 (5)
            """);

            System.out.println(Utils.toInfo("\nSystem: ") + "Чтобы вернуться назад выберите 0\n");

            int choice = Utils.whatToDoNext(0);

            if (choice == 0) return;
        }
    }

    private void selectSlots() throws InterruptedException {
        while (true) {
            System.out.println(Utils.toInfo("\nSlots: ") + "Для начала игры, выберите количество слотов!\n");

            System.out.println("""
                0. Назад
                1. Посмотреть таблицу возможных выигрышей
                2. Крутить 2 слота
                3. Крутить 3 слота
                4. Крутить 4 слота
                5. Крутить 5 слотов
                """);

            int choice = Utils.whatToDoNext(5);

            switch (choice) {
                case 0 -> { return; }
                case 1 -> checkWinSlotsTableV2();
                case 2, 3, 4, 5 -> playGameLoop("x" + choice + " Slots", () -> playSlotsGame(choice));
            }
        }
    }

    private Map<Integer, Integer> getRepeatForSlotResult(int[] results) {
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

    private Map<Integer, Integer> getAllSequenceFromSlotResult(int[] results) {
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

    private Map<Integer, Integer> getCleanRepeatForSlotWithoutSequence(Map<Integer, Integer> repeat, Map<Integer, Integer> sequence) {
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

    private double getMultiplierForSlots(int slotCount, Map<Integer, Integer> sequence, Map<Integer, Integer> clearRepeat) {
        /**
         * clearRepeat.isEmpty == true
         *      sequence.isEmpty == true (ни повторений, ни тем более последовательности) -> Проигрыш -> x0
         *      sequence.isEmpty == false (только последовательности)
         *          5 :
         *              5 [5=1] - [5, 5, 5, 5, 5] = Джекпот +1 -> 10 / 100 000 = 0.01% -> x10 000
         *              4 [4=1] - [4, 4, 4, 4, 0] = Каре последовательности +2 -> 180 / 100 000 = 0.18% -> x556
         *              3+2 / 2+3 [2=1, 3=1] - [2, 2, 3, 3, 3] = Фул-хаус последовательности +3 -> 180 / 100 000 = 0.18% -> x556
         *              3 [3=1] - [0, 3, 3, 3, 0] = Сет последовательности +4 -> 2 160 / 100 000 = 2.16% -> x46.3
         *              2+2 [2=2] - [2, 2, 0, 3, 3] = Две пары последовательности +5 -> 2 160 / 100 000 = 2.16% -> x46.3
         *              2 [2=1] - [1, 2, 2, 3, 4] = Одна пара последовательности +6 -> 20 160 / 100 000 = 20.16% -> x4.96
         *          4 :
         *              4 [4=1] - [4, 4, 4, 4] = Джекпот +1 -> 10 / 10 000 = 0.1% -> x1 000
         *              3 [3=1] - [3, 3, 3, 0] = Сет последовательности +2 -> 180 / 10 000 = 1.8% -> x55.6
         *              2+2 [2=2] - [1, 1, 2, 2] = Две пары последовательности +5 -> 180 / 10 000 = 1.8% -> x55.6
         *              2 [2=1] - [1, 2, 2, 3] = Одна пара последовательности +6 -> 2 160 / 10 000 = 21.6% -> x4.63
         *          3 :
         *              3 [3=1] - [3, 3, 3] = Джекпот +1 -> 10 / 1 000 = 1% -> x100
         *              2 [2=1] - [2, 2, 0] = Одна пара последовательности +6 -> 180 / 1 000 = 18% -> x5.56
         *          2 :
         *              2 [2=1] - [2, 2] = Джекпот +1 -> 10 / 100 = 10% -> x10
         * clearRepeat.isEmpty == false
         *      sequence.isEmpty == true (только повторения)
         *          5 :
         *              3 [3=1] - [1, 2, 1, 3, 1] = Сет повторения +7 -> 5 040 / 100 000 = 5.04% -> x19.8
         *              3+2 / 2+3 [2=1, 3=1] - [1, 2, 1, 2, 1] = Фул-хаус повторения +8 -> 180 / 100 000 = 0.18% -> x556
         *              2+2 [2=2] - [1, 2, 0, 1, 2] = Две пары повторения +9 -> 4 320 / 100 000 = 4.32% -> x23.1
         *              2 [2=1] - [0, 1, 2, 3, 0] = Одна пара повторения +10 -> 30 240 / 100 000 = 30.24% -> x3.31
         *          4 :
         *              2+2 [1, 2, 1, 2] = Две пары повторения +9 -> 90 / 10 000 = 0.9% -> x111
         *              2 [0, 1, 2, 1] = Одна пара повторения +10 -> 2 160 / 10 000 = 21.6% -> x4.63
         *              // 10 * 9 * 9 * 1
         *              //
         *          3 :
         *              2 [1, 2, 1] = Одна пара повторения +10 -> 90 / 1 000 = 9% -> x11.1
         *      sequence.isEmpty == false (повторения + последовательности)
         *          5 :
         *              4пов1+3пос1 - [5,5,5,0,5] / [5,0,5,5,5] +11 -> 180 / 100 000 = 0.18% -> x556
         *              4пов1+2пос2 - [5,5,0,5,5] +12 -> 90 / 100 000 = 0.09% -> x1 111
         *              3пов1+2пос1 - [5,5,0,1,5] / [5,0,1,5,5] / [0,5,1,5,5]... +13 -> 3 600 / 100 000 = 3.6% -> x27.8
         *              3пов1+2пос2 - [5,5,0,0,5]... +14 -> 180 / 100 000 = 0.18% -> x556
         *              2пов1+3пос1 - [0,0,5,5,5] +15 -> 180 / 100 000 = 0.18% -> x556
         *              2пов1+2пос1 - [0,5,5,0,1] +16 -> 4 320 / 100 000 = 4.32% -> x23.1
         *          4 :
         *              3пов1+2пос1 - [4,4,0,4] +13 -> 180 / 10 000 = 1.8% -> x55.6
         *              2пов1+2пос1 - [4,0,0,4] +16 -> 90 / 10 000 = 0.9% -> x111
         */

        if (clearRepeat.isEmpty()) {
            // 0 Поражение
            if (sequence.isEmpty()) {
                System.out.println("\nУвы, ни одно число не совпало. Повезет в следующий раз!");
                return 0;
            }

            // 1 Джекпот
            if (sequence.containsKey(slotCount)) {
                System.out.print("\nДжекпот! Все числа совпали!");
                return switch (slotCount) {
                    case 5 -> 10000;
                    case 4 -> 1000;
                    case 3 -> 100;
                    case 2 -> 10;
                    default -> {
                        System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Джекпот\"");
                        yield -1;
                    }
                };
            }

            // 2 Почти джекпот последовательности
            if (sequence.containsKey(slotCount - 1)) {
                System.out.print("\nПоздравляем! Еще чуть-чуть и был бы джекпот!");
                return switch (slotCount) {
                    case 5 -> 556;
                    case 4 -> 55.6;
                    case 3 -> 5.56;
                    default -> {
                        System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Почти джекпот последовательности\"");
                        yield -1;
                    }
                };
            }

            if (sequence.containsKey(3) && sequence.get(3) == 1) {
                // 3 Фул-хаус последовательности
                if (sequence.containsKey(2) && sequence.get(2) == 1) {
                    System.out.print("\nВау, последовательный фул-хаус!");
                    return 556;
                }

                // 4 Сет последовательности
                System.out.print("\nПоследовательный сет, три в ряд!");
                return 46.3;
            }

            if (sequence.containsKey(2)) {
                // 5 Две пары последовательности
                if (sequence.get(2) == 2) {
                    System.out.print("\nОгонь! Две последовательные пары!");
                    return switch (slotCount) {
                        case 5 -> 46.3;
                        case 4 -> 55.6;
                        default -> {
                            System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Две пары последовательности\"");
                            yield -1;
                        }
                    };
                }

                // 6 Одна пара последовательности
                if (sequence.get(2) == 1) {
                    System.out.print("\nОдна последовательная пара!");
                    return switch (slotCount) {
                        case 5 -> 4.96;
                        case 4 -> 4.63;
                        case 3 -> 5.56;
                        default -> {
                            System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Одна пара последовательности\"");
                            yield -1;
                        }
                    };
                }
            }
        } else {
            if (sequence.isEmpty()) {
                if (clearRepeat.containsKey(3) && clearRepeat.get(3) == 1) {
                    // 8 Фул-хаус повторения
                    if (clearRepeat.containsKey(2) && clearRepeat.get(2) == 1) {
                        System.out.print("\nЕее, это классический фул-хаус!");
                        return 556;
                    }

                    // 7 Сет повторения
                    System.out.print("\nТри числа совпали! Это сет!");
                    return 19.8;
                }

                if (clearRepeat.containsKey(2)) {
                    // 9 Две пары повторения
                    if (clearRepeat.get(2) == 2) {
                        System.out.print("\nДве пары!");
                        return switch (slotCount) {
                            case 5 -> 23.1;
                            case 4 -> 111;
                            default -> {
                                System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Две пары повторения\"");
                                yield -1;
                            }
                        };
                    }

                    // 10 Одна пара повторения
                    if (clearRepeat.get(2) == 1) {
                        System.out.print("\nДва числа совпали! Одна пара!");
                        return switch (slotCount) {
                            case 5 -> 3.31;
                            case 4 -> 4.63;
                            case 3 -> 11.1;
                            default -> {
                                System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Одна пара повторения\"");
                                yield -1;
                            }
                        };
                    }
                }
            } else {
                if (clearRepeat.containsKey(4) && clearRepeat.get(4) == 1) {
                    // 11 4пов1+3пос1
                    if (sequence.containsKey(3) && sequence.get(3) == 1){
                        System.out.print("\nТри в ряд! Да еще и 4 числа совпали!");
                        return 556;
                    }

                    // 12 4пов1+2пос2
                    if (sequence.containsKey(2) && sequence.get(2) == 2) {
                        System.out.print("\nДве пары в ряд! Да еще и 4 числа совпали!");
                        return 1111;
                    }
                }

                if (clearRepeat.containsKey(3) && clearRepeat.get(3) == 1) {
                    if (sequence.containsKey(2)) {
                        // 13 3пов1+2пос1
                        if (sequence.get(2) == 1) {
                            System.out.print("\nПара в ряд! Да еще и 3 числа совпали!");
                            return switch (slotCount) {
                                case 5 -> 27.8;
                                case 4 -> 55.6;
                                default -> {
                                    System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"3пов1+2пос1\"");
                                    yield -1;
                                }
                            };
                        }

                        // 14 3пов1+2пос2
                        if (sequence.get(2) == 2) {
                            System.out.print("\nДве пары в ряд! Да еще и 3 числа совпали!");
                            return 556;
                        }
                    }
                }

                if (clearRepeat.containsKey(2) && clearRepeat.get(2) == 1) {
                    // 15 2пов1+3пос1
                    if (sequence.containsKey(3) && sequence.get(3) == 1) {
                        System.out.print("\nТри числа в ряд! Да еще и пара повторения!");
                        return 556;
                    }

                    // 16 2пов1+2пос1
                    if (sequence.containsKey(2) && sequence.get(2) == 1) {
                        System.out.print("\nДве пары! Одна в ряд, другая совпадение.");
                        return switch (slotCount) {
                            case 5 -> 23.1;
                            case 4 -> 111;
                            default -> {
                                System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"2пов1+2пос1\"");
                                yield -1;
                            }
                        };
                    }
                }
            }
        }
        System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "ни один из сценариев");
        return -1; // Не пойманный кейс
    }

    private double calculateSlotGameResult(int betAmount, int[] slotResults){
        int slotCount = slotResults.length;

        Map<Integer, Integer> sequence = getAllSequenceFromSlotResult(slotResults);
        Map<Integer, Integer> clearRepeat = getCleanRepeatForSlotWithoutSequence(getRepeatForSlotResult(slotResults), sequence);

        //toDo?
        double multiplier = Utils.getClearMultiplier(getMultiplierForSlots(slotCount, sequence, clearRepeat) * Multipliers.REDUCE_MULTIPLIER_FOR_SLOTS_GAME);

        if (multiplier > 0) System.out.println(" Кф " + Utils.toAccent("x" + multiplier));

        return betAmount * multiplier;
    }

    private void playSlotsGame(int slotCount) throws InterruptedException {
        System.out.println(Utils.toInfo("x" + slotCount + " Slots: ") + "Чтобы крутануть слоты, укажите ставку!\n");

        int betAmount = selectBet();
        applyBet(betAmount);

        System.out.println(Utils.toInfo("\nВаша ставка: ") + Utils.formatCurrency(betAmount) + "\n");

        int[] results = Utils.spinSlotsInARow(slotCount);
        double winAmount = calculateSlotGameResult(betAmount, results);
        boolean isWin = winAmount > 0;

        resolveBet(isWin, betAmount, winAmount);
        System.out.println();
    }

    private void playEven() throws InterruptedException {
        System.out.println(Utils.toInfo(this.GAME_NAME_2 + ": ") + "Угадайте, выбрав четное или нечетное!\n");

        int selectNumber = Utils.selectNumber(1, 2);
        String parity = selectNumber % 2 == 0 ? "Четное" : "Нечетное";

        int betAmount = selectBet();
        applyBet(betAmount);

        System.out.println(Utils.toInfo("\nВаша ставка: ") + parity + ", " + Utils.formatCurrency(betAmount) + ", Кф " + Utils.toAccent("х2.0") + "\n");

        Utils.waitAnimation("Крутим рулетку");

        int randomNumber = RANDOM.nextInt(0, 37);

        int winAmount = 0;
        boolean isWin = false;

        if (randomNumber == 0) {
            System.out.println("Выпало число 0\nУвы, ставка проиграна.");
        } else {
            String randomParity = randomNumber % 2 == 0 ? "Четное" : "Нечетное";
            boolean isEven = randomNumber % 2 == 0;
            System.out.println("Выпало число " + randomNumber + " - " + Utils.toAccent(randomParity) + "\n");
            isWin = (selectNumber == 1 && !isEven) || (selectNumber == 2 && isEven);
            if (isWin) {
                winAmount = betAmount * 2;
                System.out.println("Поздравляем! Ваша ставка выиграла!");
            } else {
                System.out.println("К сожалению, вы не угадали.");
            }
        }

        resolveBet(isWin, betAmount, winAmount);
        System.out.println();
    }

    //toDo
    private void playRoulette() throws InterruptedException {
        while (true) {
            System.out.println(Utils.toInfo(this.GAME_NAME_6 + ": ") + "Выбери ставку по желанию!");

            System.out.println("\n|    <    | 1 число | 2 числа | 3 числа | 4 числа | 6 чисел | На цвет |  Чет/не |");
            System.out.println("|  Назад  |  Прямо  |  Сплит  |  Стрит  |  Уголл  |  Линия  |  Color  |  Evens  |");
            System.out.println("|    -    |   x35   |   x17   |   x11   |   x8    |   x5    |   x2    |   x2    |");
            System.out.println("|    0    |    1    |    2    |    3    |    4    |    5    |    6    |    7    |\n");

            int choice = Utils.whatToDoNext(7);

            switch (choice) {
                case 0 -> play();
                case 7 -> this.playGameLoop(this.GAME_NAME_2, this::playEven);
            }
        }

    }

    private void playCoinFlip() throws InterruptedException {
        System.out.println(Utils.toInfo(this.GAME_NAME_3 + ": ") + "Орёл или Решка? Угадай сторону монетки!\n");

        System.out.println("1. Орёл");
        System.out.println("2. Решка");
        int playerChoice = Utils.selectNumber(1, 2);
        String playerSide = playerChoice == 1 ? "Орёл" : "Решка";

        int betAmount = selectBet();
        applyBet(betAmount);

        System.out.println(Utils.toInfo("\nВаша ставка: ") + playerSide + ", " + Utils.formatCurrency(betAmount) + ", Кф " + Utils.toAccent("х2.0") + "\n");

        Utils.waitAnimation("Подбрасываем монетку");

        int result = RANDOM.nextInt(1, 3);
        String resultSide = result == 1 ? "Орёл" : "Решка";
        System.out.println("Выпало: " + Utils.toAccent(resultSide) + "!\n");

        int winAmount = 0;
        boolean isWin = playerChoice == result;

        if (isWin) {
            winAmount = betAmount * 2;
            System.out.println("Поздравляем! Вы угадали!");
        }

        resolveBet(isWin, betAmount, winAmount);
        System.out.println();
    }

    private double getMultiplierForHigherOrLower(int currentNumber, boolean betOnHigher) {
        return switch (currentNumber) {
            case 2 -> betOnHigher ? 1.2 : 9.0;
            case 3 -> betOnHigher ? 1.35 : 4.5;
            case 4 -> betOnHigher ? 1.55 : 3.0;
            case 5 -> betOnHigher ? 1.85 : 2.3;
            case 6 -> betOnHigher ? 2.3 : 1.85;
            case 7 -> betOnHigher ? 3.0 : 1.55;
            case 8 -> betOnHigher ? 4.5 : 1.35;
            case 9 -> betOnHigher ? 9.0 : 1.2;
            default -> 1.0;
        };
    }

    private void playHigherOrLower() throws InterruptedException {
        System.out.println(Utils.toInfo(this.GAME_NAME_4 + ": ") + "Угадай, следующее число больше или меньше!\n");

        int currentNumber = RANDOM.nextInt(2, 10);
        System.out.println("Текущее число: " + Utils.toAccent(currentNumber + ""));

        System.out.println("Следующее число (может быть от 1 до 10):");
        System.out.println("1. Больше");
        System.out.println("2. Меньше");

        boolean betOnHigher = Utils.selectNumber(1, 2) == 1;
        String choiceName = betOnHigher ? "Больше" : "Меньше";

        int betAmount = selectBet();
        applyBet(betAmount);

        double multiplier = getMultiplierForHigherOrLower(currentNumber, betOnHigher);

        System.out.println(Utils.toInfo("\nВаша ставка: ") + choiceName + " " + currentNumber + ", " + Utils.formatCurrency(betAmount) + ", Кф " + Utils.toAccent("x" + multiplier) + "\n");

        int nextNumber;
        do {
            nextNumber = Utils.spinSingleSlot(1, 11);
            System.out.println("Выпало число: " + Utils.toAccent(String.valueOf(nextNumber)) + "\n");
            if (nextNumber == currentNumber) {
                System.out.print(Utils.toInfo("System: ") + "Выпало то же число, генерируем новое");
                Utils.dotAnimation();
                System.out.println();
            }
        } while (nextNumber == currentNumber);

        double winAmount = 0;
        boolean isWin = betOnHigher ? nextNumber > currentNumber : nextNumber < currentNumber;

        if (isWin) {
            winAmount = betAmount * multiplier;
            System.out.println("Поздравляем, Вы угадали!");
        }

        resolveBet(isWin, betAmount, winAmount);
        System.out.println();
    }

    private void playWords() throws InterruptedException {
        System.out.println(Utils.toInfo(this.GAME_NAME_5 + ": ") + "Дается первая и последняя буква, угадывайте слова быстрее других!\n");

        //int countPlayers = Utils.nextInt("Выберите количество игроков: ");
        //int[] scorePlayers = new int[countPlayers];

        String[] first = {"А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш"};
        String[] second = {"а", "б", "в", "г", "д", "е", "ж", "з", "и", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "я"};
        int firstIndex = RANDOM.nextInt(0, first.length);
        int secondIndex = RANDOM.nextInt(0, second.length);

        System.out.println(Utils.toAccent("Очки: ") + "Игрок 1 - " + scorePlayers[0] + ", Игрок 2 - " + scorePlayers[1]);

        System.out.println(Utils.toInfo("Буквы: ") + first[firstIndex] + "—" + second[secondIndex]);

        System.out.print("Запускаем таймер");
        Utils.dotAnimation();

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

    private void playGameLoop(String gameName, GameRunnable game) throws InterruptedException {
        if (player.getBalance() < MIN_BALANCE_FOR_PLAY) {
            System.out.println(Utils.toError("System: ") + "Для входа в игру " + Utils.toInfo(gameName) + " нужно минимум " + Utils.formatCurrency(MIN_BALANCE_FOR_PLAY));
            return;
        }

        boolean continueGame = true;
        while (continueGame) {
            game.run();
            if (player.getBalance() < MIN_BALANCE_FOR_PLAY) {
                System.out.println(Utils.toInfo("System: ") + "Ваш баланс " + Utils.formatCurrency(player.getBalance()));
                System.out.println(Utils.toError("System: ") + "Депозит для игр ниже минимального - " + Utils.formatCurrency(MIN_BALANCE_FOR_PLAY));
                System.out.print("Сворачиваем игру");
                Utils.dotAnimation();
                break;
            }
            continueGame = Utils.askToContinue();
        }
    }

}