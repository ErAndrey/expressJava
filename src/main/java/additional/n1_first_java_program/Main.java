package additional.n1_first_java_program;

public class Main {

    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static void divide(int a, int b) {
        if (b == 0) {
            System.out.print("Ошибка: '/' на 0");
            return;
        }
        System.out.println((double) a / b);
    }

    public static int findMax(int a, int b) {
        return Math.max(a, b);
    }

    public static int findMin(int a, int b) {
        return Math.min(a, b);
    }

    public static void modulus(int a, int b) {
        if (b == 0) {
            System.out.print("Ошибка: '%' на 0");
            return;
        }
        System.out.println(a % b);
    }

    public static double average(int a, int b) {
        return (double) (a + b) / 2;
    }

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static boolean isOdd(int n) {
        return n % 2 != 0;
    }

    public static int square(int x) {
        return x * x;
    }

    public static int cube(int x) {
        return x * x * x;
    }

    public static double squareRoot(int x) {
        return Math.sqrt(x);
    }

    public static double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public static double roundUp(double x) {
        return Math.ceil(x);
    }

    public static double roundDown(double x) {
        return Math.floor(x);
    }

    public static double roundNearest(double x) {
        return Math.round(x);
    }

    public static int absolute(int x) {
        return Math.abs(x);
    }

    public static void swap(int a, int b) {
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        int temp = a;
        System.out.println("b (swap a) = " + temp);
        temp = b;
        System.out.println("a (swap b) = " + temp);
    }

    public static double minutesToHours(int minutes) {
        return minutes / 60.0;
    }

    public static double secondsToMinutes(int seconds) {
        return seconds / 60.0;
    }

    public static double celsiusToFahrenheit(double c) {
        return c * 9 / 5 + 32;
    }

    public static double kmToMiles(double km) {
        return km * 0.621371;
    }

    public static boolean isDivisible(int a, int b) {
        if (b == 0) {
            return false;
        }
        return a % b == 0;
    }

}
