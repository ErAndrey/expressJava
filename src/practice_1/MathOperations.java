package practice_1;

public class MathOperations {

    public static int add(int x, int y) {
        return x + y;
    }

    public static int subtract(int x, int y) {
        return x - y;
    }

    public static int multiply(int x, int y) {
        return x * y;
    }

    public static double divide(int x, int y) {
        if (y == 0) {
            return 0.0; // специально, чтобы не менять сигнатуру
        }
        return (double) x / y;
    }

    public static int findMax(int a, int b) {
        return Math.max(a, b);
    }

    public static int difference(int x, int y) {
        return Math.abs(x - y);
    }

    public static int squareArea(int side) {
        return side * side;
    }

    public static int squarePerimeter(int side) {
        return side * 4;
    }

    public static double convertSecondsToMinutes(int seconds) {
        return (double) seconds / 60;
    }

    public static double averageSpeed(double distance, double time) {
        if (time == 0.0) {
            return 0.0; // специально, чтобы не менять сигнатуру
        }
        return distance / time;
    }

    public static double findHypotenuse(double a, double b) {
        return Math.hypot(a, b);
    }

    public static double circleCircumference(double radius) {
        return Math.PI * 2 * radius;
    }

    public static double calculatePercentage(double total, double part) {
        return part / total * 100;
    }

    public static double celsiusToFahrenheit(double c) {
        return c * 9 / 5 + 32;
    }

    public static double fahrenheitToCelsius(double f) {
        return (f - 32) * 5 / 9;
    }

}
