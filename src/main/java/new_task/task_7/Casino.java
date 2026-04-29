package new_task.task_7;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;
import java.util.Scanner;

public final class Casino {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    private static final int MIN_BALANCE_FOR_PLAY = 1000;
    private static final char CURRENCY = '₽';
    private static final String SPACE = "   ";
    private static final DecimalFormat FORMATTER;

    private static final String YELLOW = "\u001B[93m";
    private static final String PURPLE = "\u001B[35m";
    private static final String GREEN = "\u001B[92m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' '); // пробел как разделитель
        FORMATTER = new DecimalFormat("#,###", symbols);
    }

    public static Scanner getScanner() {
        return SCANNER;
    }

    public static int whatToDoNext(int maxActions) {
        int action;

        while (true) {
            System.out.print(PURPLE + "Что делаем: " + RESET);
            action = SCANNER.nextInt();
            if (action >= 0 && action <= maxActions) break;
            System.out.println("Выберите доступное действие: 0-" + maxActions);
        }

        return action;
    }

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
            System.out.println(RED + "Недостаточно средств! " + RESET + "Ваш баланс: " + formatCurrency(player.getBalance()));
            return -1;
        }

        if (this.balance < bet * 2) {
            System.out.println("Извините, казино не может принять такую ставку. Доступно для выплат: " + formatCurrency(this.balance));
            System.out.println("Максимальная ставка: " + formatCurrency((int) (this.balance / 2)));
            return -1;
        }

        return bet;
    }

    private static String formatCurrency(int amount) {
        return FORMATTER.format(amount) + " " + CURRENCY;
    }

    private static String formatCurrency(double amount) {
        return FORMATTER.format(amount) + " " + CURRENCY;
    }

    private boolean askToContinue() throws InterruptedException {
        System.out.println(
                """
                Желаете продолжить?
                0. Назад к играм
                1. Продолжить
                """
        );

        int choice = whatToDoNext(1);

        if (choice == 1) return true;


        System.out.print("Возвращаем вас на главную");
        for (int i = 1; i <= 3; i++) {
            Thread.sleep(1_000);
            System.out.print(".");
            if (i == 3) {
                Thread.sleep(500);
                System.out.println();
            }
        }
        return false;
    }

    private void playEven() throws InterruptedException {
        System.out.println(YELLOW + "Evens: " + RESET + "Угадайте, выбрав четное или нечетное!\n");

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
        System.out.println(YELLOW + "\nВаша ставка: " + RESET + parity + ", " + formatCurrency(bet) + "\n");

        String[] spinner = {"|", "/", "-", "\\"};
        String text = " Крутим рулетку";

        for (int i = 0; i < 24; i++) {
            int dotsCount = (i % 8) / 2;
            System.out.print("\r" + spinner[i % spinner.length] + text + ".".repeat(dotsCount));
            Thread.sleep(250);
        }

        Thread.sleep(250);
        System.out.print("\r" + " ".repeat(text.length() + 6) + "\r");
        Thread.sleep(250);

        int random = RANDOM.nextInt(0, 37);

        boolean playerWin;

        if (random == 0) {
            System.out.println("Выпало число 0");
            System.out.println("Увы, ставка проиграна!");
            playerWin = false;
        } else {
            String randomParity = random % 2 == 0 ? "Четное" : "Нечетное";
            System.out.println("Выпало число " + random + " - " + randomParity + "\n");

            boolean isEven = (random % 2 == 0);
            playerWin = (selectNumber == 1 && !isEven) || (selectNumber == 2 && isEven);

            if (playerWin) {
                System.out.println("Поздравляем! Ваша ставка выиграла!");
            } else {
                System.out.println("К сожалению, вы не угадали.");
            }
        }

        if (playerWin) {
            int winAmount = bet * 2;
            System.out.println(GREEN + "Ваш выигрыш: " + RESET + formatCurrency(winAmount));

            if (this.balance < winAmount) {
                System.out.println(RED + "Ошибка казино: " + RESET + "Недостаточно средств для выплаты!");
                this.player.deposit(bet);
                this.balance -= bet;
                return;
            }

            this.player.deposit(winAmount);
            this.balance -= winAmount;

        } else {
            System.out.println(RED + "Вы проиграли " + RESET + formatCurrency(bet));
        }
        System.out.println();
    }

    private void playSlot() throws InterruptedException {
        System.out.println(YELLOW + "Slots: " + RESET + "Чтобы крутануть слот, укажите ставку!\n");

        int bet;
        do {
            bet = selectBet();
        } while (bet == -1);

        this.player.withdraw(bet);
        this.balance += bet;

        System.out.println(YELLOW + "\nВаша ставка: " + RESET + formatCurrency(bet) + "\n");

        int[] results = new int[3];
        String[] spinner = {"|", "/", "-", "\\"};
        int spinDuration = 20;

        for (int step = 0; step < 3; step++) {
            results[step] = RANDOM.nextInt(0, 10);

            for (int i = 0; i <= spinDuration; i++) {
                System.out.print("\r");

                for (int s = 0; s < step; s++) {
                    System.out.print(results[s] + SPACE);
                }

                System.out.print(spinner[i % spinner.length] + SPACE);

                for (int s = step + 1; s < 3; s++) {
                    System.out.print(spinner[i % spinner.length] + SPACE);
                }

                Thread.sleep(70);
            }

            System.out.print("\r");
            for (int s = 0; s <= step; s++) {
                System.out.print(results[s] + SPACE);
            }
            for (int s = step + 1; s < 3; s++) {
                System.out.print(spinner[0] + SPACE);
            }

            Thread.sleep(200);
        }

        Thread.sleep(500);

        System.out.println();

        int winAmount = 0;
        if (results[0] == results[1] && results[1] == results[2]) {
            winAmount = bet * 10;
            System.out.println("\nДжекпот! Все три числа совпали!");
        } else if (results[0] == results[1] || results[1] == results[2] || results[0] == results[2]) {
            winAmount = bet * 3;
            System.out.println("\nПобеда! Два числа совпали!");
        } else {
            System.out.println("\nНи одно число не совпало. Повезет в другой раз!");
        }

        if (winAmount > 0) {
            if (this.balance < winAmount) {
                System.out.println(RED + "Ошибка казино: " + RESET + "Недостаточно средств для выплаты!");
                this.player.deposit(bet);
                this.balance -= bet;
                return;
            }
            System.out.println(GREEN + "Ваш выигрыш: " + RESET + formatCurrency(winAmount));
            this.player.deposit(winAmount);
            this.balance -= winAmount;
        } else {
            System.out.println(RED + "Вы проиграли: " + RESET + formatCurrency(bet));
        }
        System.out.println();
    }

    public void play() throws InterruptedException {
        while (true) {
            System.out.println(       "\n  ~    "         + "|    <    |   777   |   1/2   |" +          "    ~  "        );
            System.out.println(YELLOW + "Азарт  " + RESET + "|  Выход  |  Slots  |  Evens  |" + YELLOW + "  Азарт" + RESET);
            System.out.println(         "  ~    "         + "|    0    |    1    |    2    |" +          "    ~  \n"      );


            int action = whatToDoNext(2);

            switch (action) {
                case 0 -> {
                    System.out.println("До встречи, " + player.getName() + "! Ждем вас снова ;)");
                    return;
                }
                case 1 -> playGameLoop("Slots", this::playSlot);
                case 2 -> playGameLoop("Evens", this::playEven);
            }
        }
    }

    private void playGameLoop(String gameName, GameRunnable game) throws InterruptedException {
        if (player.getBalance() < MIN_BALANCE_FOR_PLAY) {
            System.out.println("Для входа в игру " + YELLOW + gameName + RESET + " нужно минимум " + formatCurrency(MIN_BALANCE_FOR_PLAY));
            return;
        }

        boolean continueGame = true;
        while (continueGame) {
            game.run();

            if (player.getBalance() < MIN_BALANCE_FOR_PLAY) {
                System.out.println(YELLOW + "\nВаш баланс: " + RESET + formatCurrency(player.getBalance()));
                System.out.println("Баланс ниже минимального (" + formatCurrency(MIN_BALANCE_FOR_PLAY) + "). Возврат к играм...");
                Thread.sleep(1_000);
                break;
            }

            continueGame = askToContinue();
        }
    }
}
