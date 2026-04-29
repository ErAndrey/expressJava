package new_task.task_7;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class Utils {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final DecimalFormat FORMATTER;

    private static final char CURRENCY = '₽';

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

    public static String next() {
        return SCANNER.next();
    }

    public static int nextInt(String inputMessage) {
        // Сначала выводим приглашение один раз
        System.out.print(inputMessage);

        while (true) {
            try {
                return SCANNER.nextInt();
            } catch (InputMismatchException e) {
                // Затираем строку с ошибочным вводом
                System.out.print("\r" + " ".repeat(50) + "\r");
                // Выводим сообщение об ошибке
                System.out.println(toError("System: ") + "Введите целое число");


                // Затираем ошибку
                //System.out.print("\r" + " ".repeat(50) + "\r");

                // Заново выводим приглашение
                System.out.print(inputMessage);

                SCANNER.next();
            }
        }
    }

    public static int whatToDoNext(int maxActions) {
        int action;
        while (true) {
            action = nextInt(Utils.toAccent("Что делаем: "));
            if (action >= 0 && action <= maxActions) break;
            System.out.println(toError("System: ") + "Выберите доступное действие: 0-" + maxActions);
        }
        return action;
    }

    public static int selectNumber(int from, int to) {
        int number;
        while (true) {
            number = nextInt("Выберите число от " + from + " до " + to + ": ");
            if (number >= from && number <= to) break;
            System.out.println("Нужно выбрать число в диапазоне от " + from + " до " + to);
        }
        return number;
    }

    public static String formatCurrency(int amount) {
        return FORMATTER.format(amount) + " " + CURRENCY;
    }

    public static String formatCurrency(double amount) {
        return FORMATTER.format(amount) + " " + CURRENCY;
    }

    public static boolean askToContinue() throws InterruptedException {
        System.out.println(
                """
                Желаете продолжить?
                0. Назад к играм
                1. Продолжить
                """
        );

        int choice = whatToDoNext(1);
        if (choice == 1) return true;

        System.out.print("Возвращаемся к играм"); dotAnimation();

        return false;
    }

    public static void dotAnimation() throws InterruptedException {
        for (int i = 1; i <= 3; i++) {
            Thread.sleep(1_000);
            System.out.print(".");
            if (i == 3) {
                Thread.sleep(500);
                System.out.println();
            }
        }
    }

    public static String toInfo(String string) {
        return YELLOW + string + RESET;
    }

    public static String toSuccess(String string) {
        return GREEN + string + RESET;
    }

    public static String toError(String string) {
        return RED + string + RESET;
    }

    public static String toAccent(String string) {
        return PURPLE + string + RESET;
    }
}
