package new_task.task_7;

import java.util.*;

public final class Roulette {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    private final Player player;
    private double balance;

    public Roulette(Player player, int balance) {
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

    public void playEven() throws InterruptedException {
        System.out.print("Привет, " + player.getName() + "!");
        System.out.println("\nВаш баланс: " + player.getBalance() + "₽");

        if (player.getBalance() < 100) {
            System.out.println("\nНа вашем балансе недостаточно средств, чтобы начать игру, минимум - 100.0₽");
            return;
        }

        System.out.println("Угадайте, выпадет четное или нечетное число\n");

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

        System.out.println("\nВаш выбор: [" + parity + ", " + bet + "₽]\n");

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

        System.out.println("Выпало число " + random + " - " + parity + "\n");
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
                System.out.println("К сожалению, вы не угадали");
            }
        }

        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        String RESET = "\u001B[0m";

        if (playerWin) {
            int winAmount = bet * 2;
            System.out.println("Выигрыш: " + GREEN + bet + "₽" + RESET);

            if (this.balance < winAmount) {
                System.out.println("Ошибка казино: Недостаточно средств для выплаты!");
                this.player.deposit(bet);
                this.balance -= bet;
                return;
            }

            this.player.deposit(winAmount);
            this.balance -= winAmount;

        } else {
            System.out.println("Вы проиграли " + RED + bet + "₽" + RESET);
        }
        System.out.println();
    }

}
