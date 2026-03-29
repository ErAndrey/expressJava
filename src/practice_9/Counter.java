package practice_9;

public class Counter {
    private static int x;

    public static int getX() {
        return x;
    }

    public static synchronized void increment() {
        x++;
    }
}
