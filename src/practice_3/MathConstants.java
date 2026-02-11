package practice_3;

public class MathConstants {
    final static double PI;
    final static double E;

    static {
        PI = 3.14159;
        E = 2.71828;
    }

    static double calculateCircleArea(double r) {
        return PI * r * r;
    }
    static double calculateCircumference(double r) {
        return 2 * PI * r;
    }
}
