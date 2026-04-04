package practice_1;

public class Main {
    public static void main(String[] args) {

        int a = 3, b = 4;
        System.out.println("Сложение: " + a + " + " + b + " = " + MathOperations.add(a, b));
        System.out.println("Вычитание: " + a + " - " + b + " = " + MathOperations.subtract(a, b));
        System.out.println("Умножение: " + a + " * " + b + " = " + MathOperations.multiply(a, b));
        System.out.println("Деление: " + a + " / " + b + " = " + MathOperations.divide(a, b));

        System.out.println("Максимальное среди: [" + a + ", " + b + "] = " + MathOperations.findMax(a, b));

        System.out.println("Модуль разности |" + a + " - " + b + "| = " + MathOperations.difference(a, b));

        System.out.println("Площадь квадрата со стороной " + a + " = " + MathOperations.squareArea(a));
        System.out.println("Периметр квадрата со стороной " + b + " = " + MathOperations.squarePerimeter(b));

        int seconds = 50;
        System.out.println(seconds + " секунд = " + MathOperations.convertSecondsToMinutes(seconds) + " минут");

        int distance = 120, time = 5;
        System.out.println("Средняя скорость при дистанции " + distance + "м, проходимому за " + time + "с = " + MathOperations.averageSpeed(distance, time) + "м/с");

        System.out.println("Гипотенуза треугольника с катетами " + a + ", " + b + " = " + MathOperations.findHypotenuse(a, b));

        int radius = 20;
        System.out.println("Длина окружности с радиусом " + radius + " = " + MathOperations.circleCircumference(radius));

        int total = 100, part = 23;
        System.out.println(part + " составляет " + MathOperations.calculatePercentage(total, part) + "% от " + total);

        double celsius = 38.0;
        System.out.println(celsius + "C цельсий = " + MathOperations.celsiusToFahrenheit(celsius) + "C фарингейт");
        System.out.println(MathOperations.fahrenheitToCelsius(MathOperations.celsiusToFahrenheit(celsius))); // Ожидаем заданный 'celsius'

    }
}