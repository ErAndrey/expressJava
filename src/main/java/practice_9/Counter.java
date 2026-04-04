package practice_9;

public class Counter {
    private int x;

    public int getX() {
        return x;
    }

    public synchronized void increment() {
        this.x++;
    }
}
