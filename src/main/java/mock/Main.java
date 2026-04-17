package mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {


        /**
         * map: d2
         * anyMatch: D2
         * map: a2
         * anyMatch: A2
         */
        //task1();

        /**
         * 1
         * 3
         * 5
         * IllegalStateException
         */
        //task2();

        /**
         * Не скомпилируется
         */
        //prepareCollection();

        /**
         * a
         * b
         * true
         */
        /*
        List<List<String>> lists =
                List.of(List.of("a", "b", "c"), List.of("d", "e", "f"));

        System.out.println(
                lists.stream()
                        .flatMap(list -> list.stream())
                        .peek(System.out::println)
                        .anyMatch(s -> s.equals("b"))
        );
        */

        /**
         * false
         */
        /*
        Boolean b = new Boolean("/true");
        System.out.println(b);
        */

        /**
         * ConcurrentModifiedException
         */
        /*
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        for (String elem : list) {
            if (elem.equals("a")) {
                list.remove(elem);
            }
        }
        */

        /**
         *
         */
        /*
        B b = new B();
        b.show();
        */

        /**
         * 2456
         */
        /*
        try {
            try {
                throw new Exception("");
            } catch (RuntimeException e) {
                System.out.print("1");
            } finally {
                System.out.print("2");
            }
            System.out.print("3");
        } catch (Exception e) {
            System.out.print("4");
        } finally {
            System.out.print("5");
        }
        System.out.print("6");
        */
        /*
        var a = new int[1];
        a[0] = 21;
        Integer b = Integer.valueOf(17);

        inc(a[0]);
        inc(b);
        System.out.println(a[0]);
        System.out.println(b);
        */

        /**
         *
         */

        A1 a = null;
        a.print();

        A1 b;
        //b.print();

        A1 c = new A1();
        c.print();
    }

    public static void inc(int[] a) {
        a[0]++;
    }

    public static void inc(int a) {
        a++;
    }

    public static void inc(Integer b) {
        b++;
    }

    public static void task1() {
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A");
                });
    }
    public static void task2() {
        Stream<Integer> stream =
                Stream.of(1, 2, 3, 4, 5).filter(i -> i % 2 != 0);
        stream.forEach(System.out::println);
        System.out.println(stream.reduce(5, Integer::sum));
    }
    /*
    public static int prepareCollection() {
        Set<A> set = new HashSet<>();
        set.add(new A());
        set.add(new B());
        set.add(new B());
        return set.size();
    }
     */

}
