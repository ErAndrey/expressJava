package new_task.task_7;

import java.util.Random;

public final class Casino {
    private static final Random RANDOM = new Random();

    private static final int MIN_BALANCE_FOR_PLAY = 1000;
    private static final String SPACE = "   ";

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

    private int selectBet() {
        int bet = Utils.nextInt("Укажите сумму ставки: ");

        if (bet <= 0) {
            System.out.println(Utils.toError("System: ") + "Ставка должна быть положительным числом!");
            return -1;
        }

        if (player.getBalance() < bet) {
            System.out.println(Utils.toError("System: ") + "Недостаточно средств! Ваш баланс: " + Utils.formatCurrency(player.getBalance()));
            return -1;
        }

        if (this.balance < bet * 2) {
            System.out.println(Utils.toError("System: ") + "Извините, мы не можем принять такую ставку. Максимальная ставка: " + Utils.formatCurrency((int) (this.balance / 2)));
            return -1;
        }

        return bet;
    }

    public void play() throws InterruptedException {
        while (true) {
            System.out.println("\n  ~    " + "|    <    |   777   |   1/2   |" + "    ~  " );
            System.out.println(Utils.toInfo("Азарт  ") + "|  Выход  |  Slots  |  Evens  |" + Utils.toInfo("Азарт  "));
            System.out.println("  ~    " + "|    0    |    1    |    2    |" + "    ~  \n");

            int action = Utils.whatToDoNext(2);

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

    private void playSlot() throws InterruptedException {
        System.out.println(Utils.toInfo("Slots: ") + "Чтобы крутануть слот, укажите ставку!\n");

        int bet;
        do {
            bet = selectBet();
        } while (bet == -1);

        this.player.withdraw(bet);
        this.balance += bet;

        System.out.println(Utils.toInfo("\nВаша ставка: ") + Utils.formatCurrency(bet) + "\n");

        String[] spinner = {"|", "/", "-", "\\"};
        int spinDuration = 20;
        int[] results = new int[3];

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
                System.out.println(Utils.toError("System: ") + "У казино недостаточно средств для выплаты! Текущая ставка была возвращена.");
                this.player.deposit(bet);
                this.balance -= bet;
                return;
            }
            System.out.println(Utils.toSuccess("Ваш выигрыш: ") + Utils.formatCurrency(winAmount));
            this.player.deposit(winAmount);
            this.balance -= winAmount;
        } else {
            System.out.println(Utils.toError("Проигрыш: ") + Utils.formatCurrency(bet));
        }
        System.out.println();
    }

    private void playEven() throws InterruptedException {
        System.out.println(Utils.toInfo("Evens: ") + "Угадайте, выбрав четное или нечетное!\n");

        int selectNumber = Utils.selectNumber(1, 2);

        int bet;
        do {
            bet = selectBet();
        } while (bet == -1);

        this.player.withdraw(bet);
        this.balance += bet;

        String parity = selectNumber % 2 == 0 ? "Четное" : "Нечетное";
        System.out.println(Utils.toInfo("\nВаша ставка: ") + parity + ", " + Utils.formatCurrency(bet) + "\n");

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

            boolean isEven = random % 2 == 0;
            playerWin = (selectNumber == 1 && !isEven) || (selectNumber == 2 && isEven);

            if (playerWin) {
                System.out.println("Поздравляем! Ваша ставка выиграла!");
            } else {
                System.out.println("К сожалению, вы не угадали.");
            }
        }

        if (playerWin) {
            int winAmount = bet * 2;
            System.out.println(Utils.toSuccess("Ваш выигрыш: ") + Utils.formatCurrency(winAmount));
            if (this.balance < winAmount) {
                System.out.println(Utils.toError("System: ") + "У казино недостаточно средств для выплаты! Текущая ставка была возвращена.");
                this.player.deposit(bet);
                this.balance -= bet;
                return;
            }
            this.player.deposit(winAmount);
            this.balance -= winAmount;
        } else {
            System.out.println(Utils.toError("Проигрыш ") + Utils.formatCurrency(bet));
        }
        System.out.println();
    }

    private void playGameLoop(String gameName, GameRunnable game) throws InterruptedException {
        if (player.getBalance() < MIN_BALANCE_FOR_PLAY) {
            System.out.println("Для входа в игру " + Utils.toInfo(gameName) + " нужно минимум " + Utils.formatCurrency(MIN_BALANCE_FOR_PLAY));
            return;
        }

        boolean continueGame = true;
        while (continueGame) {
            game.run();

            if (player.getBalance() < MIN_BALANCE_FOR_PLAY) {
                System.out.println(Utils.toInfo("\nВаш баланс: ") + Utils.formatCurrency(player.getBalance()));
                System.out.println("Депозит для игр ниже минимального (" + Utils.formatCurrency(MIN_BALANCE_FOR_PLAY) + "). Сворачиваем игру");
                Utils.dotAnimation();
                break;
            }

            continueGame = Utils.askToContinue();
        }
    }
}
