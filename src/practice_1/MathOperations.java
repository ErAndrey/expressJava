package practice_1;

public class MathOperations {

    public static void main (String[] args) {

        System.out.println(add(1, 4));
        System.out.println(subtract(2, 3));
        System.out.println(multiply(3, 2));
        System.out.println(divide(4, 1));

        System.out.println(findMax(4, 1));

        System.out.println(difference(4, 6));

        System.out.println(squareArea(4));
        System.out.println(squarePerimeter(5));

        System.out.println(convertSecondsToMinutes(5));
        System.out.println(convertSecondsToMinutes(120));

        System.out.println(averageSpeed(120, 5));


        System.out.println(findHypotenuse(3, 4));

        System.out.println(circleCircumference(20));

        System.out.println(calculatePercentage(100, 20));

        System.out.println(celsiusToFahrenheit(38.0));
        System.out.println(fahrenheitToCelsius(celsiusToFahrenheit(38.0)));

    }

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

    public static float convertSecondsToMinutes(int seconds) {
        return (float) seconds / 60;
    }

    public static double averageSpeed(double distance, double time) {
        return distance / time;
    }

    public static double findHypotenuse(double a, double b) {
        return Math.sqrt((a * a + b * b));
    }

    public static double circleCircumference(double radius) {
        return Math.PI * 2 * radius;
    }

    public static String calculatePercentage(double total, double part) {
        return part / total * 100 + "%";
    }

    public static double celsiusToFahrenheit(double c) {
        return c * 9 / 5 + 32;
    }

    public static double fahrenheitToCelsius(double f) {
        return (f - 32) * 5 / 9;
    }

}
