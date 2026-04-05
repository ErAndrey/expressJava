package practice_11;

import java.util.*;

public class Main {
    private static int balance = 100;

    public static void main(String[] args) {
        //1

        int[] numbers = {10, 20, 30, 40, 50};
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i]);
        }

        //2

        int sum = calculateSum(5);
        int sum0 = calculateSum(0);
        int sum1 = calculateSum(-5);
        System.out.println("Sum: " + sum);
        System.out.println("Sum: " + sum0);
        System.out.println("Sum: " + sum1);

        //3

        int i = 1;
        while (i <= 5) {
            System.out.println("Number: " + i);
            i++;
        }

        //4

        try {
            System.out.println(isPalindrome(null));
        } catch (IllegalArgumentException e) {
            System.out.println("Обработали null в boolean isPalindrome(String str)");
        }
        System.out.println(isPalindrome("null"));

        //5

        Person person = new Person("Alice", 25);
        person.happyBirthday();
        System.out.println("Updated age: " + person.getAge());

        //6
        countdown(5);

        //7

        Thread t1 = new Thread(() -> withdraw(60));
        Thread t2 = new Thread(() -> withdraw(50));
        t1.start();
        t2.start();

        //8

        double a = 0.1 * 3;
        double b = 0.3;
        if (Math.abs(a - b) <= 0.0001) {
            System.out.println("Equal");
        } else {
            System.out.println("Not Equal");
        }

        //9

        String str1 = new String("hello");
        String str2 = new String("hello");
        if (str1.equals(str2)) {
            System.out.println("Equal");
        } else {
            System.out.println("Not Equal");
        }


        List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie"));
        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().startsWith("A")) {
                iterator.remove();
            }
        }
        System.out.println(names);
    }

    public static int calculateSum(int n) {
        int sum = 0;
        if (n >= 0) {
            for (int i = 1; i <= n; i++) {
                sum += i;
            }
        }
        for (int i = -1; i >= n; i--) {
            sum += i;
        }
        return sum;
    }

    public static boolean isPalindrome(String str) {
        if (str == null) throw new IllegalArgumentException("Для null не можем проверить");
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }

    public static void countdown(int n) {
        System.out.println(n);
        if (n == 1) return;
        countdown(n - 1);
    }

    public synchronized static void withdraw(int amount) {
        if (balance >= amount) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            balance -= amount;
            System.out.println("New balance: " + balance);
        }
    }
}
