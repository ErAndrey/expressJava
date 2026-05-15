package new_task.task_7;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public final class Utils {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final DecimalFormat CURRENCY_INT_FORMATTER;
    private static final DecimalFormat CURRENCY_DOUBLE_FORMATTER;

    private static final char CURRENCY = '₽';
    private static final String SPACE = "   ";
    private static final String[] SPINNER = {"|", "/", "-", "\\"};
    private static final String REGEX_FOR_VALIDATE_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private static final String YELLOW = "\u001B[93m";
    private static final String PURPLE = "\u001B[35m";
    private static final String GREEN = "\u001B[92m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');
        CURRENCY_INT_FORMATTER = new DecimalFormat("#,###", symbols);
        CURRENCY_DOUBLE_FORMATTER = new DecimalFormat("#,###.00", symbols);
    }

    public static boolean validateEmail(String email) {
        return email != null && email.matches(REGEX_FOR_VALIDATE_EMAIL);
    }

    public static String next(String inputMessage) {
        System.out.print(inputMessage);
        return SCANNER.next();
    }

    public static int nextInt(String inputMessage) {
        System.out.print(inputMessage);
        while (true) {
            try {
                return SCANNER.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("\r" + " ".repeat(50) + "\r");
                System.out.println(toError("System: ") + "Введите целое число");
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
            if (maxActions == 0) {
                System.out.println(toError("System: ") + "Выберите доступное действие: 0");
                continue;
            }
            System.out.println(toError("System: ") + "Выберите доступное действие: 0-" + maxActions);
        }
        return action;
    }

    public static int selectNumber(int from, int to) {
        int number;
        while (true) {
            number = nextInt("Выберите число от " + from + " до " + to + ": ");
            if (number >= from && number <= to) break;
            System.out.println(toError("System: ") + "Нужно выбрать число в диапазоне от " + from + " до " + to);
        }
        return number;
    }

    public static String formatCurrency(int amount) {
        return CURRENCY_INT_FORMATTER.format(amount) + " " + CURRENCY;
    }

    public static String formatCurrency(double amount) {
        if (amount == Math.floor(amount)) {
            return formatCurrency((int) amount); // 100.0 -> 100 (как инт)
        }
        return CURRENCY_DOUBLE_FORMATTER.format(amount) + " " + CURRENCY;
    }

    public static double getClearMultiplier(double multiplier) {
        return Math.floor(multiplier * 100.0) / 100.0;
    }

    public static boolean askToContinue() throws InterruptedException {
        System.out.println("""
        Желаете продолжить?
        0. Назад
        1. Продолжить
        """);
        int choice = whatToDoNext(1);
        if (choice == 1) return true;
        System.out.print("Возвращаемся"); dotAnimation();
        return false;
    }

    public static void dotAnimation() throws InterruptedException {
        for (int i = 1; i <= 3; i++) {
            Thread.sleep(500);
            System.out.print(".");
            if (i == 3) {
                Thread.sleep(500);
                System.out.println();
            }
        }
    }

    public static void waitAnimation(String text) throws InterruptedException {
        for (int i = 0; i < 24; i++) {
            int dotsCount = (i % 8) / 2;
            System.out.print("\r" + SPINNER[i % SPINNER.length] + " " + text + ".".repeat(dotsCount));
            Thread.sleep(250);
        }
        Thread.sleep(250);
        System.out.print("\r" + " ".repeat(text.length() + 6) + "\r");
        Thread.sleep(250);
    }

    public static int spinSingleSlot(int from, int to) throws InterruptedException {
        int spinDuration = 48;
        int result = 0;

        for (int i = 0; i <= spinDuration; i++) {
            System.out.print("\r");

            if (i < spinDuration) {
                System.out.print(SPINNER[i % SPINNER.length]);
            } else {
                result = RANDOM.nextInt(from, to);
            }
            Thread.sleep(70);
        }
        Thread.sleep(200);
        return result;
    }

    public static int[] spinSlotsInARow(int slotCount) throws InterruptedException {
        int spinDuration = 24;
        int[] results = new int[slotCount];

        for (int step = 0; step < slotCount; step++) {
            results[step] = RANDOM.nextInt(0, 10);

            for (int i = 0; i <= spinDuration; i++) {
                System.out.print("\r");

                for (int slot = 0; slot < slotCount; slot++) {
                    if (slot < step) {
                        System.out.print(results[slot] + SPACE);
                    } else {
                        String s = SPINNER[i % SPINNER.length] + SPACE;
                        if (slot == step) {
                            if (i < spinDuration - 5) {
                                System.out.print(s);
                            } else {
                                System.out.print(results[slot] + SPACE);
                            }
                        } else {
                            System.out.print(s);
                        }
                    }
                }
                Thread.sleep(70);
            }
            Thread.sleep(150);
        }
        System.out.println();
        return results;
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

    public static String toCenter(String string, int width) {
        if (string.length() >= width) return string;

        int leftPadding = (width - string.length()) / 2;
        int rightPadding = width - string.length() - leftPadding;

        return " ".repeat(leftPadding) + string + " ".repeat(rightPadding);
    }

    public static void printActionPanel(String place) {
        switch (place) {
            case "start" -> {
                System.out.println("\n     ~       " + "|    <    |       +       |    >    |" + "       ~     ");
                System.out.println(Utils.toInfo("Авторизация  ") + "|  Выход  |  Регистрация  |  Войти  |" + Utils.toInfo("  Авторизация"));
                System.out.println("     ~       " + "|    0    |       1       |    2    |" +"       ~    \n");
            }
            case "menu" -> {
                System.out.println("\n   ~     " + "|    <    |     #     |     ₽     |    >    |" +"     ~    ");
                System.out.println(Utils.toInfo("Главная  ") + "|  Выход  |  Профиль  |  Депозит  |  Азарт  |" + Utils.toInfo("  Главная "));
                System.out.println("   ~     " + "|    0    |     1     |     2     |    3    |" + "     ~    \n");
            }
            case "confirmedProfile" -> {
                System.out.println("\n   ~     " + "|    <    |" + "     ~    ");
                System.out.println(Utils.toInfo("Профиль  ") + "|  Назад  |" + Utils.toInfo("  Профиль "));
                System.out.println("   ~     " + "|    0    |" + "     ~    \n");
            }
            case "unconfirmedProfile" -> {
                System.out.println("\n   ~     " + "|    <    |       ^       |" + "     ~    ");
                System.out.println(Utils.toInfo("Профиль  ") + "|  Назад  |  Подтвердить  |" + Utils.toInfo("  Профиль "));
                System.out.println("   ~     " + "|    0    |       1       |" + "     ~    \n");
            }
            case "balance" -> {
                System.out.println("\n   ~     " + "|    <    |      ₽      |     >     |" + "     ~   " );
                System.out.println(Utils.toInfo("Депозит  ") + "|  Назад  |  Пополнить  |  Вывести  |" + Utils.toInfo("  Депозит"));
                System.out.println("   ~     " + "|    0    |      1      |     2     |" + "     ~    \n");
            }
            case "play" -> {
                System.out.println("\n  ~    " + "|    <    |   777   |   1/2   |    %    |   >/<   |   ...   |" + "    ~  ");
                System.out.println(Utils.toInfo("Азарт  ") + "|  Назад  |  Slots  |  Evens  |  Flips  |  Hg/Lw  |  Words  |" + Utils.toInfo("  Азарт"));
                System.out.println("  ~    " + "|    0    |    1    |    2    |    3    |    4    |    5    |" + "    ~  \n");
            }
        }
    }

}