package practice_9;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static volatile boolean stop;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            printSomeForNRepeatWithSleep("Привет из потока!", 5, 1);
        });

        t1.start();
        t1.join();

        //

        Thread t2 = new Thread(() -> {
            printSomeForNRepeatWithSleep("A", 5, 1);
        });
        Thread t3 = new Thread(() -> {
            printSomeForNRepeatWithSleep("B", 5, 1);
        });

        t2.start();
        t3.start();
        t2.join();
        t3.join();

        //

        AtomicInteger x = new AtomicInteger();
        stop = false;

        Thread t4 = new Thread(() -> {
            System.out.println("Расчитываю AtomicInteger \"x\"");
            while (!stop) {
                x.incrementAndGet();
            }
        });

        t4.start();

        Thread.sleep(2_000);
        stop = true;
        t4.join();

        System.out.println("Что успел насчитать за 2 секунды: " + x.get());

        //

        Counter counter = new Counter();

        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 1_000; i++)
                counter.increment();
        });
        Thread t6 = new Thread(() -> {
            for (int i = 0; i < 1_000; i++)
                counter.increment();
        });

        System.out.println("До инкрементации потоками: " + counter.getX());

        t5.start();
        t6.start();
        t5.join();
        t6.join();

        System.out.println("После инкрементации потоками: " + counter.getX());
    }
    public static void printSomeForNRepeatWithSleep(String whatAPrint, int repeat, int eachSleepSeconds) {
        int absRepeat = Math.abs(repeat);
        for (int i = 1; i <= absRepeat; i++) {
            System.out.println("Цикл " + i + ": " + whatAPrint);
            if (i < absRepeat - 1) {
                try {
                    Thread.sleep(eachSleepSeconds * 1_000L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
