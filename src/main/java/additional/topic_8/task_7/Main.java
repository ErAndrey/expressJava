package additional.topic_8.task_7;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(divide(1, 0));
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }

    private static double divide(int a, int b) {
        if (b == 0) throw new ArithmeticException("/ by zero");
        return (double) a / b;
    }
}
