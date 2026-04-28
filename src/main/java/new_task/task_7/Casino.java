package new_task.task_7;

import java.util.*;

public final class Casino {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final char CURRENCY = '₽';
    private static final String YELLOW = "\u001B[93m";
    private static final String GREEN = "\u001B[92m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    private final Player player;
    private double balance;

    public Casino(Player player, int balance) {
        this.player = player;
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    private int selectNumber(int from, int to) {
        System.out.print("Выберите число [" + from + ", " + to + "]: ");
        int number = SCANNER.nextInt();
        if (number >= from && number <= to) return number;
        System.out.println("Нужно выбрать число в диапазоне от " + from + " до " + to);
        return -1;
    }

    private int selectBet() {
        System.out.print("Выберите сумму ставки: ");
        int bet = SCANNER.nextInt();

        if (bet <= 0) {
            System.out.println("Ставка должна быть положительным числом!");
            return -1;
        }

        if (player.getBalance() < bet) {
            System.out.println("Недостаточно средств! Ваш баланс: " + player.getBalance() + "₽");
            return -1;
        }

        if (this.balance < bet * 2) {
            System.out.println("Извините, казино не может принять такую ставку. Доступно для выплат: " + this.balance + "₽");
            System.out.println("Максимальная ставка: " + (int) (this.balance / 2) + "₽");
            return -1;
        }

        return bet;
    }

    private boolean isNotAvailableBalanceForPlay() {
        System.out.println("Ваш баланс: " + player.getBalance() + "₽\n");

        if (player.getBalance() < 100.0) {
            System.out.println("\nНа вашем балансе недостаточно средств, для начала игры должно быть минимум 100.0₽");
            return true;
        }
        return false;
    }

    public void playEven() throws InterruptedException {
        if (isNotAvailableBalanceForPlay()) return;

        System.out.println(YELLOW + "Игра \"isEven\": " + RESET + "Угадайте, выбрав чет или нечет!\n");

        int selectNumber;
        do {
            selectNumber = selectNumber(1, 2);
        } while (selectNumber == -1);

        int bet;
        do {
            bet = selectBet();
        } while (bet == -1);

        this.player.withdraw(bet);
        this.balance += bet;

        String parity = (selectNumber == 1) ? "Нечетное" : "Четное";

        System.out.println(YELLOW + "\nВаша ставка: " + RESET + parity + ", " + bet + "₽\n");

        String[] spinner = {"|", "/", "-", "\\"};
        String text = " Крутим рулетку";
        int totalFrames = 24;

        for (int i = 0; i < totalFrames; i++) {
            int dotsCount = (i % 8) / 2;

            String dots = ".".repeat(dotsCount);

            System.out.print("\r" + spinner[i % spinner.length] + text + dots);
            Thread.sleep(250);
        }

        System.out.print("\r" + " ".repeat(text.length() + 6) + "\r");

        int random = RANDOM.nextInt(0, 37);
        String randomParity = random % 2 == 0 ? "Четное" : "Нечетное";

        if (random == 0) {
            System.out.println("Выпало число " + random);
        } else {
            System.out.println("Выпало число " + random + " - " + randomParity + "\n");
        }

        Thread.sleep(500);

        boolean playerWin;
        if (random == 0) {
            playerWin = false;
            System.out.println("Увы, ставка проиграна!");
        } else {
            boolean isEven = (random % 2 == 0);
            if ((selectNumber == 1 && !isEven) || (selectNumber == 2 && isEven)) {
                playerWin = true;

                System.out.println("Поздравляем! Ваша ставка выиграла!");
            } else {
                playerWin = false;
                System.out.println("К сожалению, вы не угадали.");
            }
        }

        if (playerWin) {
            int winAmount = bet * 2;
            System.out.println(GREEN + "Ваш выигрыш: " + RESET + bet + "₽");

            if (this.balance < winAmount) {
                System.out.println("Ошибка казино: Недостаточно средств для выплаты!");
                this.player.deposit(bet);
                this.balance -= bet;
                return;
            }

            this.player.deposit(winAmount);
            this.balance -= winAmount;

        } else {
            System.out.println(RED + "Вы проиграли " + RESET + bet + "₽");
        }
    }

    public void playSlot() throws InterruptedException {
        if (isNotAvailableBalanceForPlay()) return;

        System.out.println(YELLOW + "Игра \"Slots\": " + RESET + "Чтобы крутануть слот, укажите ставку!\n");

        int bet;
        do {
            bet = selectBet();
        } while (bet == -1);

        this.player.withdraw(bet);
        this.balance += bet;

        System.out.println(YELLOW + "\nВаша ставка: " + RESET + bet + CURRENCY + "\n");

        int[] results = new int[3];

        String[] spinner = {"|", "/", "-", "\\"};
        int spinDuration = 20;

        for (int step = 0; step < 3; step++) {
            results[step] = RANDOM.nextInt(0, 10);

            for (int i = 0; i <= spinDuration; i++) {
                System.out.print("\r");

                for (int s = 0; s < step; s++) {
                    System.out.print(results[s] + "   ");
                }

                System.out.print(spinner[i % spinner.length] + "   ");

                for (int s = step + 1; s < 3; s++) {
                    System.out.print(spinner[i % spinner.length] + "   ");
                }

                Thread.sleep(70);
            }

            System.out.print("\r");
            for (int s = 0; s <= step; s++) {
                System.out.print(results[s] + "   ");
            }
            for (int s = step + 1; s < 3; s++) {
                System.out.print(spinner[0] + "   ");
            }

            Thread.sleep(200);
        }

        System.out.println();

        int winAmount = 0;
        String winMessage = "";

        if (results[0] == results[1] && results[1] == results[2]) {
            winAmount = bet * 10;
            winMessage = "Джекпот! Все три числа совпали!";
            System.out.println("\n" + winMessage);
        } else if (results[0] == results[1] || results[1] == results[2] || results[0] == results[2]) {
            winAmount = bet * 3;
            winMessage = "Победа! Два числа совпали!";
            System.out.println("\n" + winMessage);
        } else {
            winMessage = "Ни одно число не совпало. Повезет в другой раз!";
            System.out.println("\n" + winMessage);
        }

        if (winAmount > 0) {
            if (this.balance < winAmount) {
                System.out.println("Ошибка казино: Недостаточно средств для выплаты!");
                this.player.deposit(bet);
                this.balance -= bet;
                return;
            }
            System.out.println(GREEN + "Ваш выигрыш: " + RESET + winAmount + "₽");
            this.player.deposit(winAmount);
            this.balance -= winAmount;
        } else {
            System.out.println(RED + "Вы проиграли: " + RESET + bet + "₽");
        }
        System.out.println();
    }

    public void play() {} //toDo

    /**
     * Хочу один метод play(), который позволяет выбирать игры [playEven(), playSlot()]
     * При запуске play() показывается список доступных действий :
     * 0. Выйти
     * 1. Проверить баланс
     * 2. Играть Slots
     * 3. Играть OddEven
     *
     * Юзеру выводится сообщение: "Что делаем: " - в это время запрашивается SCANNER .nextInt()
     * При вводе 0 -> завершает работу метода play()
     * При вводе 1 -> выводит баланс player-а
     * При вводе 2 -> запускает игру playSlot()
     * При вводе 3 -> запускает игру playEven()
     *
     * После игр, playSlot(), playEven() спрашивает, Желаете сыграть еще?
     */
}
