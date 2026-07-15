package world_wars.general;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Utils {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    private static final String YELLOW = "\u001B[93m";
    private static final String PURPLE = "\u001B[35m";
    private static final String GREEN = "\u001B[92m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    public static String toYellow(String string) { return YELLOW + string + RESET; }
    public static String toYellow(int value) { return YELLOW + value + RESET; }

    public static String toPurple(String string) { return PURPLE + string + RESET; }
    public static String toPurple(int value) { return PURPLE + value + RESET; }

    public static String toGreen(String string) { return GREEN + string + RESET; }
    public static String toGreen(int value) { return GREEN + value + RESET; }

    public static String toRed(String string) { return RED + string + RESET; }
    public static String toRed(int value) { return RED + value + RESET; }

    public static String nextString(String inputMessage) {
        System.out.print(inputMessage);
        return SCANNER.next();
    }
    public static int nextIntPositive(String inputMessage) {
        System.out.print(inputMessage);
        while (true) {
            try {
                int x = SCANNER.nextInt();
                if (x > 0) {
                    return x;
                } else {
                    System.out.println(Utils.toRed("System: ") + "Введите число больше 0");
                }
            } catch (InputMismatchException e) {
                System.out.print("\r" + " ".repeat(50) + "\r");
                System.out.println(toRed("System: ") + "Введите целое число");
                System.out.println(inputMessage);
                SCANNER.next();
            }
        }
    }
    public static int nextInt(String inputMessage) {
        System.out.print(inputMessage);
        while (true) {
            try {
                return SCANNER.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("\r" + " ".repeat(50) + "\r");
                System.out.println(toRed("System: ") + "Введите целое число");
                System.out.println(inputMessage);
                SCANNER.next();
            }
        }
    }
    public static int whatToDoNext(int maxAction) {
        int action;
        while (true) {
            action = nextInt(toPurple("Что делаем: "));
            if (action >= 0 && action <= maxAction) break;
            if (maxAction == 0) {
                System.out.println(toRed("System: ") + "Выберите доступное действие: 0");
                continue;
            }
            System.out.println(toRed("System: ") + "Выберите доступное действие: 0-" + maxAction);
        }
        return action;
    }
    public static int selectNumber(int from, int to, String inputMessage) {
        int number;
        while (true) {
            number = nextInt(inputMessage + " от " + from + " до " + to + " : ");
            if (number >= from && number <= to) break;
            System.out.println(Utils.toRed("System: ") + "Нужно выбрать число в диапазоне от " + from + " до " + to);
        }
        return number;
    }
    public static String getNumberOfAction(int i) {
        return switch (i) {
            case 0 -> "0️⃣";
            case 1 -> "1️⃣";
            case 2 -> "2️⃣";
            case 3 -> "3️⃣";
            case 4 -> "4️⃣";
            case 5 -> "5️⃣";
            case 6 -> "6️⃣";
            case 7 -> "7️⃣";
            case 8 -> "8️⃣";
            case 9 -> "9️⃣";
            case 10 -> "1️⃣0️⃣";
            case 11 -> "1️⃣1️⃣";
            case 12 -> "1️⃣2️⃣";
            case 13 -> "1️⃣3️⃣";
            case 14 -> "1️⃣4️⃣";
            case 15 -> "1️⃣5️⃣";
            default -> throw new IllegalStateException("Unexpected action value: " + i);
        };
    }

    public static int getNextRandom(int from, int to) {
        return RANDOM.nextInt(from, to + 1);
    }
}
